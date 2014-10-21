// EPOS-- Imprecise Thread Abstraction Declarations

#ifndef __imprecise_thread_h
#define __imprecise_thread_h

#include <periodic_thread.h>

__BEGIN_SYS


// Optional Thread - Parte precisa
class Optional_Thread: public Aperiodic_Thread
{
    public:
        Optional_Thread(int (* entry)(),
                const State & state = READY,
                const Criterion & criterion = NORMAL,
		int times,
                unsigned int stack_size = STACK_SIZE)
        : Thread(entry, BEGINNING, criterion, stack_size),
	_times(times),
	_entry(entry)
        {
            _state = state;
            if(state == READY)
                _scheduler.resume(this);
        }

	void restore()
	{
	    _context = CPU::restore_context(_stack, STACK_SIZE, _entry);
	}


        static void wait_next()
        {
	    self()->activations();
            self()->suspend();
        }
		
	static Optional_Thread * self()
	{
	    return reinterpret_cast<Optional_Thread *>(Thread::self());
	}
			
	void activate()
	{
	    if(_times != Scheduling_Criteria::INFINITE) {
	        _times--;
	
	    } 
	    db<Optional_Thread>(TRC) << "\nOptional_Thread::activate times " << _times << "\n";
	}

    private:
	void activations()
	{
	    if(!_times){
		db<Optional_Thread>(TRC) << "\nOptional_Thread::activations finalize times " << _times << "\n";
		exit();
	    }
	}

    private:
	int (* _entry)();
	int _times;

};

class Imprecise_Thread: public Periodic_Thread
{
    public:
	Imprecise_Thread(int (* entry)(),
	    int (* entry2)(),
	    Criterion & criterion,
	    const State & state = READY,
	    unsigned int stack_size = STACK_SIZE)
	        : Periodic_Thread(entry, criterion, BEGINNING, stack_size)
	{
	    //optional
	    _optional_thread = new Optional_Thread(entry2, SUSPENDED, NORMAL, criterion.times());

	    _state = state;
	    if(!criterion.phase() && state == READY){
		_scheduler.resume(this);
	    }
	}
		
	~Imprecise_Thread() {
	    delete _optional_thread;
	} 
	
    public:

	int join()
	{
	    db<Imprecise_Thread>(TRC) << "Imprecise_Thread::join(this=" << this
	        << ",state=" << _state << ")\n";
	    return _optional_thread->join();
	}

	static void wait_next()
	{
	    self()->_optional_thread->activate();
	    //Poderia apenas inserir na ready
	    self()->_optional_thread->resume(); 
	    Periodic_Thread::wait_next();
	    self()->_optional_thread->suspend(); 
	    self()->_optional_thread->restore(); 
	}

	static Imprecise_Thread * self()
	{
	    return reinterpret_cast<Imprecise_Thread *>(Thread::self());
	}
		
    private:
	//optional
	Optional_Thread * _optional_thread;
		
};


__END_SYS

#endif
