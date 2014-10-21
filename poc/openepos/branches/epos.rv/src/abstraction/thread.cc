// EPOS Thread Abstraction Implementation

#include <system/kmalloc.h>
#include <machine.h>
#include <thread.h>
#include <scheduler.h>
#include <alarm.h>

__BEGIN_SYS

/* Thread base class */

// Class attributes
Spin Imp::Thread::_lock;
unsigned int Imp::Thread::_thread_count;
Scheduler_Timer * Imp::Thread::_timer;

// This_Thread class attributes
bool This_Thread::_not_booting;

// Methods
void Thread::common_constructor(Log_Addr entry, unsigned int stack_size)
{
    db<Imp::Thread>(TRC) << "Thread(entry=" << (void *)entry
		    << ",state=" << _state
		    << ",rank=" << _link.rank()
		    << ",stack={b=" << _stack
		    << ",s=" << stack_size
		    << "},context={b=" << _context
		    << "," << *_context << "}) => " << this << "\n";

    _thread_count++;

    scheduler()->insert(this);
    if((_state != READY) && (_state != RUNNING))
    {
        scheduler()->suspend(this);
    }

    reschedule();
}


Thread::~Thread()
{
    lock();

    db<Imp::Thread>(TRC) << "~Thread(this=" << this
		    << ",state=" << _state
		    << ",rank=" << _link.rank()
		    << ",stack={b=" << _stack
		    << ",context={b=" << _context
		    << "," << *_context << "})\n";

    switch(_state)
    {
        case BEGINNING:
        scheduler()->resume(this);
        _thread_count--;
        break;
        case RUNNING:  // Self deleted itself!
        exit(-1);
        break;
        case READY:
        _thread_count--;
        break;
        case SUSPENDED:
        scheduler()->resume(this);
        _thread_count--;
        break;
        case WAITING:
        _waiting->remove(this);
        scheduler()->resume(this);
        _thread_count--;
        break;
        case FINISHING: // Already called exit()
        break;
    }
    
    scheduler()->remove(this);
    
    unlock();

    kfree(_stack);
}

void Thread::priority(const Priority & p)
{
    lock();

    db<Imp::Thread>(TRC) << "Thread::priority(this=" << this
		    << ",prio=" << p << ")\n";

    scheduler()->remove(this);
    _link.rank(int(p));
    scheduler()->insert(this);

    reschedule();
}

int Thread::join()
{
    lock();

    db<Imp::Thread>(TRC) << "Thread::join(this=" << this
		    << ",state=" << _state << ")\n";

    if(_state != FINISHING)
    {
        _joining = running();
        _joining->suspend(true);
    } else
	unlock();

    return *static_cast<int *>(_stack);
}

void Thread::pass()
{
    lock();

    db<Imp::Thread>(TRC) << "Thread::pass(this=" << this << ")\n";

    Thread* prev = running();
    Thread* next = scheduler()->choose(this);

    if(next)
    {
        dispatch(prev, next, false);
    }
    else
    {
        db<Imp::Thread>(WRN) << "Thread::pass => thread (" << this
 			<< ") not ready\n";
        unlock();
    }
}

void Thread::suspend(bool locked)
{
    if(!locked)
	lock();

    db<Imp::Thread>(TRC) << "Thread::suspend(this=" << this << ")\n";

    Thread * prev = running();

    scheduler()->suspend(this);
    _state = SUSPENDED;

    Thread * next = running();

    dispatch(prev, next);

}	    

void Thread::resume()
{
    lock();

    db<Imp::Thread>(TRC) << "Thread::resume(this=" << this << ")\n";

    if(_state == SUSPENDED)
    {
        _state = READY;
        scheduler()->resume(this);
    }
    else
    {
        db<Imp::Thread>(WRN) << "Resume called for unsuspended object!\n";
    }

    reschedule();
}


// Class methods

void Thread::yield()
{
    lock();

    db<Imp::Thread>(TRC) << "Thread::yield(running=" << running() << ")\n";
	
    Thread * prev = running();
    Thread * next = scheduler()->choose_another();

    dispatch(prev, next);
}

void Thread::exit(int status)
{
    lock();

    db<Imp::Thread>(TRC) << "Thread::exit(running=" << running()
		    <<",status=" << status << ")\n";

    Thread * thr = running();
    scheduler()->remove(thr);
    *static_cast<int *>(thr->_stack) = status;
    thr->_state = FINISHING;

    _thread_count--;

    if(thr->_joining)
    {
        thr->_joining->_state = READY;
        scheduler()->resume(thr->_joining);
        thr->_joining = 0;
    }

    dispatch(thr, scheduler()->choose());
}

void Thread::sleep(Queue * q)
{
    db<Imp::Thread>(TRC) << "Thread::sleep(running=" << running()
		    << ",q=" << q << ")\n";

    Thread * thr = running();

    scheduler()->suspend(thr);
    thr->_state = WAITING;
    q->insert(&thr->_link);
    thr->_waiting = q;

    dispatch(thr, scheduler()->chosen());
}

void Thread::wakeup(Queue * q)
{
    db<Imp::Thread>(TRC) << "Thread::wakeup(running=" << running()
		    << ",q=" << q << ")\n";

    if(!q->empty())
    {
        Thread* t = q->remove()->object();
        t->_state = READY;
        t->_waiting = 0;
        scheduler()->resume(t);
    }

    reschedule();
}

void Thread::wakeup_all(Queue* q)
{
    db<Imp::Thread>(TRC) << "Thread::wakeup_all(running=" << running()
		    << ",q=" << q << ")\n";

    while(!q->empty())
    {
        Thread* t = q->remove()->object();
        t->_state = READY;
        t->_waiting = 0;
        scheduler()->resume(t);
    }

    reschedule();
}

void Thread::reschedule(bool preempt)
{
    if(preempt)
    {
        db<Imp::Thread>(TRC) << "Thread::reschedule()\n";
    
        Thread * prev = running();
        Thread * next = scheduler()->choose();
	
        dispatch(prev, next);
    }
    else
    {
        unlock();
    }
}

void Thread::time_slicer()
{
    lock();

    reschedule(true);
}

void Thread::implicit_exit()
{
    exit(CPU::fr()); 
}

int Thread::idle()
{
    while(true)
    {
        if(Traits<Imp::Thread>::trace_idle)
        {
            db<Imp::Thread>(TRC) << "Thread::idle()\n";
        }

        if(_thread_count <= Machine::n_cpus())
        {
            CPU::int_disable();
            if(Machine::cpu_id() == 0)
            {
                db<Imp::Thread>(WRN) << "The last thread has exited!\n";
                db<Imp::Thread>(WRN) << "Rebooting the machine ...\n";
                Machine::reboot();
            }
            else
            {
                CPU::halt();
            }
        }

        if(energy_aware)
        {
            if((scheduler()->schedulables() > 1) /* && enough_energy()*/)
            {
                yield();
            }
            else
            {
                CPU::halt();
            }
        }
        else
        {
            CPU::halt();
            if(scheduler()->schedulables() > 1)
            {
                yield();
            }
        }
    }

    return 0;
}


// Id forwarder to the spin lock
unsigned int This_Thread::id() 
{ 
    return _not_booting ?
	reinterpret_cast<unsigned int>(Thread::self()) :
	Machine::cpu_id() + 1;
}

/* --- */


/* "Real" Thread */
// Class attributes
Scheduler<Thread>* Thread::_scheduler = 0;

/* --- */

__END_SYS
