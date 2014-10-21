// EPOS-- Periodic Thread Abstraction Declarations

#ifndef __periodic_thread_h
#define __periodic_thread_h

#include <utility/handler.h>
#include <thread.h>
#include <alarm.h>

__BEGIN_SYS

// Aperiodic Thread
typedef Thread Aperiodic_Thread;

// Periodic Thread
class Periodic_Thread: public Thread
{
protected:
    typedef RTC::Microsecond Microsecond;

public:
    Periodic_Thread(int (* entry)(), 
	Criterion & criterion,
	const State & state = READY,
	unsigned int stack_size = STACK_SIZE)
	    : Thread(entry, BEGINNING, criterion, stack_size),
	    _handler(this),
	    _times(criterion.times()),
            _alarm(criterion.phase(), criterion.period(), &_handler, criterion.times()) 
    {
        _state = state;
        if(!criterion.phase() && state == READY)
	    _scheduler.resume(this);
    }

    template<class T1>
    Periodic_Thread(int (* entry)(T1 a1), T1 a1,
	Criterion & criterion,
	const State & state = READY,
	unsigned int stack_size = STACK_SIZE)
	    : Thread(entry, a1, BEGINNING, criterion, stack_size),
	    _handler(this),
	    _times(criterion.times()),
            _alarm(criterion.phase(), criterion.period(), &_handler, criterion.times()) 
    {
	_state = state;
        if(!criterion.phase() && state == READY)
	    _scheduler.resume(this);
    }

    template<class T1, class T2>
    Periodic_Thread(int (* entry)(T1 a1, T2 a2), T1 a1, T2 a2, 
	Criterion & criterion, 
	const State & state = READY,
	unsigned int stack_size = STACK_SIZE)
	    : Thread(entry, a1, a2, BEGINNING, criterion, stack_size),
	    _handler(this),
	    _times(criterion.times()),
            _alarm(criterion.phase(), criterion.period(), &_handler, criterion.times())
    {
        _state = state;
        if(!criterion.phase() && state == READY)
	    _scheduler.resume(this);
    }

    template<class T1, class T2, class T3>
    Periodic_Thread(int (* entry)(T1 a1, T2 a2, T3 a3), T1 a1, T2 a2, T3 a3,
	Criterion & criterion, 
	const State & state = READY,
	unsigned int stack_size = STACK_SIZE)
	    : Thread(entry, a1, a2, a3, BEGINNING, criterion, stack_size),
	    _handler(this),
	    _times(criterion.times()),
            _alarm(criterion.phase(), criterion.period(), &_handler, criterion.times())
    {
        _state = state;
        if(!criterion.phase() && state == READY)
	    _scheduler.resume(this);
    }

    static void wait_next() { 
	if(!self()->activations())
	    self()->exit();
	self()->suspend(); 
   }

   static Periodic_Thread * self()
   {
       return reinterpret_cast<Periodic_Thread *>(Thread::self());
   }

private:

   unsigned int activations()
   {	
	if(_times != Scheduling_Criteria::INFINITE) {
      	    _times--;
	}
        return _times; 
   }


private:
    Handler_Thread _handler;
    int _times;
    Alarm _alarm;
};



__END_SYS

#endif
