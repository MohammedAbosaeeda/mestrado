// EPOS Thread Abstraction Implementation

#include <system/kmalloc.h>
#include <machine.h>
#include <thread.h>
#include <scheduler.h>
#include <alarm.h>
#include <pmu.h>
#include <perf_mon.h>

__BEGIN_SYS

// Class attributes
Spin Thread::_lock;
unsigned int Thread::_thread_count;
Scheduler<Thread> Thread::_scheduler;
Scheduler_Timer * Thread::_timer;
unsigned int Thread::_thread_count_ids = 0;
volatile int Thread::_init_done = 0;

//Perf_Mon *Thread::_perf[MAX_CPUS];
//Thread::Queue Thread::_suspended;
//volatile float Thread::_data_sharing[Traits<Machine>::MAX_CPUS];
//volatile float Thread::_contested_accesses[Traits<Machine>::MAX_CPUS];
//PMU_Sampling_Timer * Thread::_pmu_sampling_timer[Traits<Machine>::MAX_CPUS];
//volatile unsigned char Thread::_metrics_bitmap = 0;

//volatile unsigned long long Thread::_n_context_switches = 0;
/*volatile TSC::Time_Stamp Thread::_sleep_tsc[MAX_COUNTING];
volatile unsigned int Thread::_sleep_counter = 0;
volatile TSC::Time_Stamp Thread::_wakeup_tsc[MAX_COUNTING];
volatile unsigned int Thread::_wakeup_counter = 0;
volatile TSC::Time_Stamp Thread::_reschedule_tsc[MAX_COUNTING];
volatile unsigned int Thread::_reschedule_counter = 0;*/
//volatile TSC::Time_Stamp Thread::_lock_tsc[Traits<Machine>::MAX_CPUS][MAX_COUNTING];
//volatile unsigned int Thread::_lock_counter[Traits<Machine>::MAX_CPUS];

/*volatile TSC::Time_Stamp Thread::_ipi_sender_tsc[MAX_COUNTING];
volatile TSC::Time_Stamp Thread::_ipi_receiver_tsc[MAX_COUNTING];
volatile unsigned int Thread::_ipi_sender;
volatile unsigned int Thread::_ipi_receiver;*/
/*volatile bool Thread::_start = false;
volatile TSC::Time_Stamp Thread::_ipi_per_cpu[Traits<Machine>::MAX_CPUS];
volatile int Thread::_cpu_ipi_receiver[Traits<Machine>::MAX_CPUS];
volatile int Thread::_cpu_ipi_sender[Traits<Machine>::MAX_CPUS];
volatile TSC::Time_Stamp Thread::_ipi_tsc[MAX_COUNTING];
volatile unsigned int Thread::_ipi_counter;*/

// This_Thread class attributes
bool This_Thread::_not_booting;
//static Thread *tmp = 0;

// Methods
void Thread::common_constructor(Log_Addr entry, unsigned int stack_size) 
{
    db<Thread>(TRC) << "Thread(entry=" << (void *)entry 
		    << ",state=" << _state
		    << ",rank=" << _link.rank()
		    << ",stack={b=" << _stack
		    << ",s=" << stack_size
		    << "},context={b=" << _context
		    << "," << *_context << "}) => " << this << "\n";
          
    /*kout << "Thread(entry=" << (void *)entry 
            << ",state=" << _state
            << ",rank=" << _link.rank()
            << ",stack={b=" << (void *) _stack
            << ",s=" << (void *) stack_size
            << "},context={b=" << (void *) _context << "}) => " << this << "\n";*/
            //<< "," << *_context << "}) => " << this << "\n";

    _thread_id = _thread_count;
    _thread_count++;
    
    //if(criterion != NORMAL && criterion != MAIN)
    //    _thread_id = _thread_count_ids++;
    //else
    //    _thread_id = 0;
    
    //_conflicted_threads = 0;
    //_prevented = 0;
    //_prevented_times = 0;
    //_perf_counter = 0;
    //_buffer = (volatile Perf_Mon::perf_data_buffers *) kmalloc(sizeof(Perf_Mon::perf_data_buffers));
    //_preempted = false;
    
    _scheduler.insert(this);
    if((_state != READY) && (_state != RUNNING))
	_scheduler.suspend(this);
    
    //kout << "Thread() schedulables = " << _scheduler.schedulables() << "\n";

    reschedule_resume();
}

Thread::~Thread()
{
    lock();

    db<Thread>(TRC) << "~Thread(this=" << this 
		    << ",state=" << _state
		    << ",rank=" << _link.rank()
		    << ",stack={b=" << _stack
		    << ",context={b=" << _context
		    << "," << *_context << "})\n";
            
    /*kout << "~Thread(this=" << this 
            << ",state=" << _state
            << ",rank=" << _link.rank()
            << ",stack={b=" << (void *) _stack
            << ",context={b=" << (void *) _context << "})\n";*/
            //<< "," << *_context << "})\n";

    switch(_state) {
    case BEGINNING:
	_scheduler.resume(this);
	_thread_count--;
	break;
    case RUNNING:  // Self deleted itself!
	exit(-1); 
	break;
    case READY:
	_thread_count--;
	break;
    case SUSPENDED: 
	_scheduler.resume(this);
	_thread_count--;
	break;
    case WAITING:
	_waiting->remove(this);
	_scheduler.resume(this);
	_thread_count--;
	break;
    case FINISHING: // Already called exit()
	break;
    }
    
    _scheduler.remove(this);
    
    unlock();

    kfree(_stack);
}

void Thread::priority(const Priority & p)
{
    lock();

    db<Thread>(TRC) << "Thread::priority(this=" << this
		    << ",prio=" << p << ")\n";

    _scheduler.remove(this);
    _link.rank(int(p));
    _scheduler.insert(this);

    reschedule();
}

int Thread::join()
{
    lock();

    db<Thread>(TRC) << "Thread::join(this=" << this
		    << ",state=" << _state << ")\n";

    if(_state != FINISHING) {
	_joining = running();
	_joining->suspend(true);
    } else
	unlock();

    return *static_cast<int *>(_stack);
}

void Thread::pass()
{
    lock();

    db<Thread>(TRC) << "Thread::pass(this=" << this << ")\n";

    Thread * prev = running();
    Thread * next = _scheduler.choose(this);

    if(next)
	dispatch(prev, next, false);
    else {
 	db<Thread>(WRN) << "Thread::pass => thread (" << this 
 			<< ") not ready\n";
	unlock();
    }
}

void Thread::suspend(bool locked)
{
    if(!locked)
	lock();

    db<Thread>(TRC) << "Thread::suspend(this=" << this << ")\n";

    Thread * prev = running();

    _scheduler.suspend(this);
    _state = SUSPENDED;

    Thread * next = running();

    dispatch(prev, next);
}

void Thread::resume()
{
    lock();

    db<Thread>(TRC) << "Thread::resume(this=" << this << ")\n";
    
    if(_state == SUSPENDED) {
	_state = READY;
	_scheduler.resume(this);
    } else
	db<Thread>(WRN) << "Resume called for unsuspended object!\n";
    
    reschedule_resume();
}


// Class methods

void Thread::yield()
{
    lock();

    db<Thread>(TRC) << "Thread::yield(running=" << running() << ")\n";
	
    Thread * prev = running();
    Thread * next = _scheduler.choose_another();

    dispatch(prev, next);
}

void Thread::exit(int status)
{
    lock();

    db<Thread>(TRC) << "Thread::exit(running=" << running() 
		    <<",status=" << status << ")\n";

    Thread * thr = running();
    _scheduler.remove(thr);
    *static_cast<int *>(thr->_stack) = status;
    thr->_state = FINISHING;

    _thread_count--;

    if(thr->_joining) {
	thr->_joining->_state = READY;
	_scheduler.resume(thr->_joining);
	thr->_joining = 0;
    }

    dispatch(thr, _scheduler.choose());
}

void Thread::sleep(Queue * q)
{
    db<Thread>(TRC) << "Thread::sleep(running=" << running()
		    << ",q=" << q << ")\n";
    //kout  << "Thread::sleep(running=" << running() << ",q=" << q << ")\n";

    //register TSC::Time_Stamp end = 0;
    //register TSC::Time_Stamp start = TSC::time_stamp();
        
    Thread * thr = running();
    _scheduler.suspend(thr); 
    thr->_state = WAITING;
    q->insert(&thr->_link);
    thr->_waiting = q;
    
    //end = TSC::time_stamp();
    
    //if(_sleep_counter < MAX_COUNTING) {
    //_sleep_tsc[_sleep_counter] = end - start;
    //}
    
    //if(end - start > 9000)
        //_sleep_tsc[_sleep_counter] = _sleep_tsc[_sleep_counter-1];
      //kout << "sleep(" << _sleep_counter << ") = " << " time = " 
        //  <<  _sleep_tsc[_sleep_counter] << " schedulables = " << _scheduler.schedulables() <<  endl;
    
    //_sleep_counter++;
    
    dispatch(thr, _scheduler.chosen());
}

void Thread::wakeup(Queue * q) 
{
    db<Thread>(TRC) << "Thread::wakeup(running=" << running()
		    << ",q=" << q << ")\n";
    //kout << "Thread::wakeup(running=" << running() << ",q=" << q << ")\n";
    
    //register TSC::Time_Stamp end = 0;
    //register TSC::Time_Stamp start = TSC::time_stamp();
                
    if(!q->empty()) {
        Thread * t = q->remove()->object();
        
        t->_state = READY;
        t->_waiting = 0;
        //change the affinity -- CPMD test
        //t->_link.rank(Criterion((t->priority() + 1) % MAX_CPUS));
        
        if(dynamic) {
            t->_link.rank(Criterion((int) Alarm::get_elapsed() + t->deadline(), t->deadline()));
        }
        
        _scheduler.resume(t);
    }
    
    //if(_wakeup_counter < MAX_COUNTING) {
    //end = TSC::time_stamp();
    //_wakeup_tsc[_wakeup_counter] = end - start;
    //}
    
    //if(end - start > 9000)
    //    _wakeup_tsc[_wakeup_counter] = _wakeup_tsc[_wakeup_counter-1];
        //kout << "wakeup(" << _wakeup_counter << ") = " << " time = " 
        //<<  _wakeup_tsc[_wakeup_counter] << " schedulables = " << _scheduler.schedulables() <<  endl;
    
    //_wakeup_counter++;

    reschedule();
}

void Thread::wakeup_all(Queue * q) 
{
    db<Thread>(TRC) << "Thread::wakeup_all(running=" << running()
		    << ",q=" << q << ")\n";
    
    while(!q->empty()) {
	Thread * t = q->remove()->object();
	t->_state = READY;
	t->_waiting = 0;
	_scheduler.resume(t);
    }
    
    reschedule();
}

void Thread::reschedule(bool preempt)
{
    if(preempt) {
	db<Thread>(TRC) << "Thread::reschedule()\n";
    //kout << "Thread::reschedule(TRUE) CPU ID = " <<  Machine::cpu_id() 
    //<< " schedulables = " << _scheduler.schedulables() << "\n";
    
    Thread * next, * prev;
    //register TSC::Time_Stamp end = 0;
    //register TSC::Time_Stamp start = TSC::time_stamp();
            
    prev = running();
    
    if(global_scheduler) {
        if(_scheduler.schedulables() > 1 && prev->criterion() != IDLE && _init_done >= MAX_CPUS) {
            register int unsigned cpu_lowest = _scheduler.get_lowest_priority_running();        
            // call reschedule in another processor
            // if the current CPU is not the lowest priority CPU and the running thread is not IDLE 
            // (scheduler may return another IDLE CPU) and there is thread to be scheduled
            if(Machine::cpu_id() != cpu_lowest) {
                //if(_reschedule_counter < MAX_COUNTING) {
                //end = TSC::time_stamp();
                //_reschedule_tsc[_reschedule_counter] = end - start;
                
                //outlier for 5 tasks
                //if(end - start > 7000)
                //    _reschedule_tsc[_reschedule_counter] = _reschedule_tsc[_reschedule_counter-1];
                
                /*if(end - start > 9000)
                _reschedule_tsc[_reschedule_counter] = _reschedule_tsc[_reschedule_counter-1];
                
                _reschedule_counter++;*/
                
                //if(end - start > 30000)
                //    kout << "reschedule(" << _reschedule_counter-1 << ") [1] = " << " time = " 
                //    <<  _reschedule_tsc[_reschedule_counter-1] << " schedulables = " << _scheduler.schedulables() <<  endl;
               
                unlock();
                IC::ipi_send(cpu_lowest, IC::INT_RESCHEDULER);
                return;
            }
        }
    }
        
    next = _scheduler.choose();
    
    /*end = TSC::time_stamp();
    _reschedule_tsc[_reschedule_counter] = end - start;
        
    if(end - start > 9000)
        _reschedule_tsc[_reschedule_counter] = _reschedule_tsc[_reschedule_counter-1];
        
    _reschedule_counter++;*/
    
    dispatch(prev, next);
    
    /*} else {
      next = _scheduler.choose();
      
      //end = TSC::time_stamp();
      //_reschedule_tsc[_reschedule_counter] = end - start;
      
      //outlier for 5 tasks
      //if(end - start > 7000)
      //      _reschedule_tsc[_reschedule_counter] = _reschedule_tsc[_reschedule_counter-1];
            
      //_reschedule_counter++;
      dispatch(prev, next);
    }*/
    
    //Thread *tmp = 0;
    
    // if PMU sampling is enable, verify the correlation among threads
    /*if(Traits<Thread>::pmu_sampling) {
        //kout << "TEST\n";
        for(unsigned int i = 0; i < Traits<Machine>::MAX_CPUS; i++) {
            Thread *t = _scheduler.head_in_queue(i)->object();
            t->_conflicted_threads |= _metrics_bitmap;
            
              //get the next's CPU number (_affinity)
              if(next->_has_shared_data && i != next->_link.rank().queue() && !next->_prevented && t->_has_shared_data) {
                  // if next was prevented to be scheduled before, let it run
                  if(next->_prevented_times == 1) {
                    next->_prevented_times = 0;
                  } else {
                    // there was data sharing between next's CPU and CPU i
                    if((next->_metrics_bitmap & (1 << i)) && !next->_prevented_times) {
                        //kout << "prevening thread = " <<   next->_link.rank().queue() << endl;
                        next->_prevented_times++;
                        next->_prevented = 1;
                        tmp = _scheduler.remove(next);
                        next = _scheduler.choose();
                        _scheduler.insert(tmp);
                        //break;
                    }
                  }   
              } 
         } //for 
    }
    
    if(tmp) tmp->_prevented = 0;*/
    //kout << "Thread::reschedule(FALSE) CPU ID = " <<  Machine::cpu_id() <<"\n";
   
    } else {
        unlock();
    }
}

void Thread::reschedule_resume(bool preempt)
{
    if(preempt) {
    db<Thread>(TRC) << "Thread::reschedule()\n";
    
    Thread * prev = running();
    Thread * next = _scheduler.choose();
    
    dispatch(prev, next);
    } else {
    unlock();
    }
}

void Thread::ipi_reschedule(unsigned int i)
{
    //IC::eoi();
    db<Thread>(TRC) << "Thread::ipi_reschedule()\n";
    /*if(_cpu_ipi_receiver[Machine::cpu_id()] != -1 && _cpu_ipi_sender[_cpu_ipi_receiver[Machine::cpu_id()]] != -1)
        if(Machine::cpu_id() == _cpu_ipi_sender[_cpu_ipi_receiver[Machine::cpu_id()]]) {
            TSC::Time_Stamp time = TSC::time_stamp() - _ipi_per_cpu[_cpu_ipi_receiver[Machine::cpu_id()]];
            if(time < 30000)
                _ipi_tsc[_ipi_counter++] = time;
            _ipi_per_cpu[_cpu_ipi_receiver[Machine::cpu_id()]] = 0;
            _cpu_ipi_receiver[Machine::cpu_id()] = -1;
            _cpu_ipi_sender[_cpu_ipi_receiver[Machine::cpu_id()]] = -1;
        }*/
    //if(_start)
    //    _ipi_receiver_tsc[_ipi_receiver++] = TSC::time_stamp();
    lock();

    Thread * prev = running();
    Thread * next = _scheduler.choose();
    
    //if(prev != next)
    //kout << "Thread::ipi_reschedule() CPU = " << Machine::cpu_id()
    //<< " prev = " << prev->priority() << " next = " << next->priority() << endl;
    
    dispatch(prev, next); //unlock()
    //reschedule();
}

void Thread::time_slicer() 
{
    lock();
    reschedule(true);
}

// PMC0 -> XSNP_HITM
// PMC1 -> XSNP_HIT
// FIXED_CTR1 -> CPU_CLK_UNHALTED.THREAD
void Thread::get_hpc()
{
    /*
    //if(Machine::cpu_id() == 3) {
      //  kout << "unhalted = " << unhalted << " PMC0 = " << PMU::rdpmc(PMU::PMC0) << " PMC1 = " << PMU::rdpmc(PMU::PMC1) << endl;
      //kout << "CA = " << (((float) (60 * PMU::rdpmc(PMU::PMC0))) / unhalted)
      //<< " DS = " << (((float)(43 * PMU::rdpmc(PMU::PMC1))) / unhalted) << endl;
      
     // kout << "Crit = " << _scheduler.head_in_queue(Machine::cpu_id())->object()->priority() 
      //<< " idle = " << Criterion::IDLE << endl;      
    //}
    
    if(_scheduler.head_in_queue(Machine::cpu_id())->object()->priority() != Criterion::IDLE) {
      
    CPU::Reg64 unhalted = Intel_PMU_Version3::rdmsr(PMU::FIXED_CTR1);
    
    //if(Machine::cpu_id() == 1) {
        //kout << "CA = " << (((float) (60 * PMU::rdpmc(PMU::PMC0))) / unhalted)
      //<< " DS = " << (((float)(43 * PMU::rdpmc(PMU::PMC1))) / unhalted) << endl;
      //kout << "XNSP_HITM = " << PMU::rdpmc(PMU::PMC0) << " XNSP_HIT = " << PMU::rdpmc(PMU::PMC1) << endl;
    //}

    _contested_accesses[Machine::cpu_id()] = (((float) (60 * PMU::rdpmc(PMU::PMC0))) / unhalted);
    _data_sharing[Machine::cpu_id()] = (((float)(43 * PMU::rdpmc(PMU::PMC1))) / unhalted);
    
    if(_contested_accesses[Machine::cpu_id()] >= Traits<Thread>::THRESHOLD || 
        _data_sharing[Machine::cpu_id()] >= Traits<Thread>::THRESHOLD) {
        
        //kout << "Setting bit " << Machine::cpu_id() << endl;
        CPU::bts((unsigned int *) &_metrics_bitmap, Machine::cpu_id());
    } else
        CPU::btr((unsigned int *) &_metrics_bitmap, Machine::cpu_id());
    
    }
    
    //Thread *t = _scheduler.head_in_queue(Machine::cpu_id())->object();
    //t->_conflicted_threads = _metrics_bitmap;
    
    //reset FIXED_CTR1
    Intel_PMU_Version3::disable_fixed_ctr1();
    PMU::reset(PMU::FIXED_CTR1);
    Intel_PMU_Version3::enable_fixed_ctr1();
    
    //reset PMC0 and PMC1
    Intel_Sandy_Bridge_PMU::disable(PMU::EVTSEL0);
    Intel_Sandy_Bridge_PMU::disable(PMU::EVTSEL1);
    PMU::reset(PMU::PMC0 + PMU::PMC_BASE_ADDR);
    PMU::reset(PMU::PMC1 + PMU::PMC_BASE_ADDR);
    Intel_Sandy_Bridge_PMU::enable(PMU::EVTSEL0);
    Intel_Sandy_Bridge_PMU::enable(PMU::EVTSEL1);
    //kout << "Thread::get_hpc() id = " << Machine::cpu_id() << endl;
    */
}

void Thread::set_affinity(int affinity)
{
    //kout << "Thread::set_affinity affinity = " << affinity << endl;
    _scheduler.remove(this);
    _link.rank(Criterion(affinity));
    _scheduler.insert(this);
}

void Thread::implicit_exit() 
{
    exit(CPU::fr()); 
}

int Thread::idle()
{
    while(true) {
	if(Traits<Thread>::trace_idle)
	    db<Thread>(TRC) << "Thread::idle()\n";
    //kout << "Thread::idle() cpu id = " << Machine::cpu_id() << "\n";

	if(_thread_count <= Machine::n_cpus()) {
	    CPU::int_disable();
	    if(Machine::cpu_id() == 0) {
		db<Thread>(WRN) << "The last thread has exited!\n";
		db<Thread>(WRN) << "Rebooting the machine ...\n";
		Machine::reboot();
	    } else
		CPU::halt();
	}
	
	if(energy_aware)
	    if((_scheduler.schedulables() > 1) /* && enough_energy()*/)
		yield();
	    else
		CPU::halt();
	else {
        //kout << "Idle CPU = " << Machine::cpu_id() << "\n";
	    CPU::halt();
	    if(_scheduler.schedulables() > 1)
		yield();
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

__END_SYS
