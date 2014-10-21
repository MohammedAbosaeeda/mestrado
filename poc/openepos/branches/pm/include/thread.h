// EPOS-- Thread Abstraction Declarations

#ifndef __thread_h
#define __thread_h

#include <system/kmalloc.h>
#include <utility/queue.h>
#include <utility/handler.h>
#include <cpu.h>
#include <scheduler.h>

__BEGIN_SYS

class Thread
{

    friend class Handler_Thread;    // to provide access to _preemptive attribute

protected:
    static const unsigned int STACK_SIZE 
    = Traits<Machine>::APPLICATION_STACK_SIZE;

    static const bool idle_waiting = Traits<Thread>::idle_waiting;
    static const bool active_scheduler = Traits<Thread>::active_scheduler;
    static const bool preemptive = Traits<Thread>::preemptive;
    static const bool smp = Traits<Thread>::smp;

    static const unsigned int QUANTUM = Traits<Thread>::QUANTUM;
    typedef CPU::Log_Addr Log_Addr;
    typedef CPU::Context Context;

public:
    // Thread State
    enum State {
	BEGINNING,
	READY,
	RUNNING,
	SUSPENDED,
	WAITING,
	FINISHING
    };
	
	typedef Traits<Thread>::Scheduler Scheduler;

    // Thread Scheduling Criterion
    typedef Traits<Thread>::Criterion Criterion;
    enum {
	NORMAL = Criterion::NORMAL,
	MAIN = Criterion::MAIN,
	IDLE = Criterion::IDLE
    };

    typedef Criterion Priority;


    typedef Ordered_Queue<Thread, Criterion, smp, Scheduler::Element> Queue;
	
public:
    Thread(int (* entry)(), 
	   const State & state = READY,
	   const Criterion & criterion = NORMAL,
	   unsigned int stack_size = STACK_SIZE)
	: _state(state), _waiting(0), _joining(0), _link(this, criterion)

    {
	prevent_scheduling();
	
	_stack = kmalloc(stack_size);
	_context = CPU::init_stack(_stack, stack_size, &implicit_exit, entry);

	common_constructor(entry, stack_size);
    }
    template<typename T1>
    Thread(int (* entry)(T1 a1), T1 a1,
	   const State & state = READY,
	   const Criterion & criterion = NORMAL,
	   unsigned int stack_size = STACK_SIZE)
	: _state(state), _waiting(0), _joining(0), _link(this, criterion)
    {
	prevent_scheduling();

	_stack = kmalloc(stack_size);
	_context = CPU::init_stack(_stack, stack_size, &implicit_exit, entry, 
				   a1);

	common_constructor(entry, stack_size);
    }
    template<typename T1, typename T2>
    Thread(int (* entry)(T1 a1, T2 a2), T1 a1, T2 a2,
	   const State & state = READY,
	   const Criterion & criterion = NORMAL,
	   unsigned int stack_size = STACK_SIZE)
	: _state(state), _waiting(0), _joining(0), _link(this, criterion)
    {
	prevent_scheduling();

	_stack = kmalloc(stack_size);
	_context = CPU::init_stack(_stack, stack_size, &implicit_exit, entry,
				   a1, a2);

	common_constructor(entry, stack_size);
    }
    template<typename T1, typename T2, typename T3>
    Thread(int (* entry)(T1 a1, T2 a2, T3 a3), T1 a1, T2 a2, T3 a3,
	   const State & state = READY,
	   const Criterion & criterion = NORMAL,
	   unsigned int stack_size = STACK_SIZE)
	: _state(state), _waiting(0), _joining(0), _link(this, criterion)
    {
	prevent_scheduling();

	_stack = kmalloc(stack_size);
	_context = CPU::init_stack(_stack, stack_size, &implicit_exit, entry, 
				   a1, a2, a3);

	common_constructor(entry, stack_size);
    }
    ~Thread();

    const volatile State & state() const { return _state; }
    const volatile Criterion & criterion() const { return _link.rank(); }

    Priority  priority() const { return _link.rank(); }
    void priority(const Priority & p);    

    int join();
    void pass();
    void suspend();
    void resume();
    void ready_enqueue();  // resume without reschedule

    static Thread * self() { return running(); }
    static void yield();
    static void sleep(Queue * q);
    static void wakeup(Queue * q);
    static void wakeup_all(Queue * q);
    static void exit(int status = 0);

    static void init();

public:
    Queue::Element * link() { return &_link; }

protected:
    void common_constructor(Log_Addr entry, unsigned int stack_size);

    static Thread * volatile running() { return _scheduler.chosen(); }

    static void prevent_scheduling() {
	if(active_scheduler)
	    CPU::int_disable();
    }
    static void allow_scheduling() {
	if(active_scheduler)
	    CPU::int_enable();
    }

    static void reschedule();
    static void time_reschedule(); // this is the master alarm handler

    static void implicit_exit();

    static void switch_threads(Thread * prev, Thread * next) {
	// scheduling must be disabled at this point!
	if(next != prev) {
	    if(prev->_state == RUNNING)
		prev->_state = READY;
	    next->_state = RUNNING;
	    db<Thread>(TRC) << "Thread::switch_threads(prev=" << prev
			    << ",next=" << next << "\n";
	    CPU::switch_context(&prev->_context, next->_context);
	}
	allow_scheduling();
    }

    static int idle();

protected:
    Log_Addr _stack;
    Context * volatile _context;
    volatile State _state; 
    Queue * _waiting;
    Thread * volatile _joining;
    Queue::Element _link;

    static unsigned int _thread_count;
    static Scheduler _scheduler;
};

// A thread event handler (see handler.h)
class Handler_Thread : public Handler
{
public:
    Handler_Thread(Thread * h) : _handler(h) {}
    ~Handler_Thread() {}

    void operator()() { 
      _handler->ready_enqueue(); 
    }
	
private:
    Thread * _handler;
};

__END_SYS

#endif
