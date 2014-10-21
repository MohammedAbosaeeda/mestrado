// EPOS Thread Abstraction Declarations

#ifndef __thread_h
#define __thread_h

#include <system/kmalloc.h>
#include <utility/queue.h>
#include <utility/handler.h>
#include <cpu.h>
#include <scheduler.h>
#include <scheduler_factory.h>

__BEGIN_SYS

class Thread;

namespace Imp
{
class Thread
{
public:
    // Friendship
    friend class Scheduler<Thread>;
    // friend class Synchronizer_Common;

protected:
    // Constants
    static const int NO_ID = -99;
    static const int MAIN_ID = -1;
    static const int IDLE_ID = -2;

protected:
    static const bool active_scheduler = Traits<Thread>::Criterion::timed;
    static const bool preemptive = Traits<Thread>::Criterion::preemptive;
    static const bool energy_aware = Traits<Thread>::Criterion::energy_aware;
    static const bool smp = Traits<Thread>::smp;

    static const unsigned int QUANTUM = Traits<Thread>::QUANTUM;
    static const unsigned int STACK_SIZE =
        Traits<Machine>::APPLICATION_STACK_SIZE;

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

    // Thread Priority
    typedef Scheduling_Criteria::Priority Priority;
    
    // Thread Scheduling Criterion
    typedef Traits<Thread>::Criterion Criterion;
    enum {
        NORMAL = Criterion::NORMAL,
        MAIN = Criterion::MAIN,
        IDLE = Criterion::IDLE
    };

public:
    // Constructors
    Thread(int id,
            int (* entry)(),
           const State & state = READY,
           const Criterion & criterion = NORMAL,
           unsigned int stack_size = STACK_SIZE)
    : _state(state)
    {
        lock();

        _stack = kmalloc(stack_size);
    }

    template<typename T1>
    Thread(int id,
            int (* entry)(T1 a1), T1 a1,
           const State & state = READY,
           const Criterion & criterion = NORMAL,
           unsigned int stack_size = STACK_SIZE)
    : _state(state)
    {
        lock();

        _stack = kmalloc(stack_size);
    }

    template<typename T1, typename T2>
    Thread(int id,
            int (* entry)(T1 a1, T2 a2), T1 a1, T2 a2,
           const State & state = READY,
           const Criterion & criterion = NORMAL,
           unsigned int stack_size = STACK_SIZE)
    : _state(state)
    {
        lock();

        _stack = kmalloc(stack_size);
    }

    template<typename T1, typename T2, typename T3>
    Thread(int id,
            int (* entry)(T1 a1, T2 a2, T3 a3), T1 a1, T2 a2, T3 a3,
           const State & state = READY,
           const Criterion & criterion = NORMAL,
           unsigned int stack_size = STACK_SIZE)
    : _state(state)
    {
        lock();

        _stack = kmalloc(stack_size);
    }

    const volatile State & state() const { return _state; }

public:
    // Public methods
    int id()
    {
        return NO_ID;
    }


protected:
    // Protected methods

protected:
    static void lock()
    {
        CPU::int_disable();
        if(smp)
            _lock.acquire();
    }

    static void unlock()
    {
        if(smp)
            _lock.release();
        CPU::int_enable();
    }


    static void dispatch(Thread * prev, Thread * next, bool charge = true)
    {
        //	if(charge) {
        //  	    if(active_scheduler)
        //  		_timer->reset();
        //  	    if(energy_aware)
        //  		account_energy();
        //	}

        if(prev != next) {
            if(prev->_state == RUNNING)
                prev->_state = READY;
            next->_state = RUNNING;

            db<Thread>(TRC) << "Thread::dispatch(prev=" << prev
                            << ",next=" << next << ")\n";

            if(smp)
                _lock.release();
            CPU::switch_context(&prev->_context, next->_context);
        } else
            if(smp)
                _lock.release();

        CPU::int_enable();
    }



protected:
    // Attributes
    Log_Addr _stack;
    Context* volatile _context;
    volatile State _state; 

protected:
    // Protected class atributes
    static Spin _lock;
    static Scheduler_Timer* _timer;
    static unsigned int _thread_count;

};

} // namespace Imp

__END_SYS

#include "verified_thread.h"

__BEGIN_SYS

class Thread : public IF<Traits<Imp::Thread>::enable_verification,
                            Verified_Thread,
                            Imp::Thread>::Result
{
public:
    // Friendship
    friend class Scheduler<Thread>;
    friend class Imp::Scheduler<Thread>;
    friend class Synchronizer_Common;

protected:
    // Protected types
    typedef IF<Traits<Imp::Thread>::enable_verification,
                                Verified_Thread,
                                Imp::Thread>::Result Base;

public:
    // Types, enumerations, and inner classes

    // Thread Queue
    typedef Ordered_Queue<Thread, Criterion, false,
                          Scheduler<Thread>::Element> Queue;

public:
    // Constructors
    Thread(int id,
            int (* entry)(),
            const State & state = READY,
            const Criterion & criterion = NORMAL,
            unsigned int stack_size = STACK_SIZE)
            : Base(id, entry, state, criterion, stack_size),
              _waiting(0), _joining(0), _link(this, criterion)
    {
        _context = CPU::init_stack(_stack, stack_size, &implicit_exit, entry);
        common_constructor(entry, stack_size);
    }

    template<typename T1>
    Thread(int id,
            int (* entry)(T1 a1), T1 a1,
            const State & state = READY,
            const Criterion & criterion = NORMAL,
            unsigned int stack_size = STACK_SIZE)
            : Base(id, entry, a1, state, criterion, stack_size),
              _waiting(0), _joining(0), _link(this, criterion)
    {
        _context = CPU::init_stack(_stack, stack_size, &implicit_exit, entry, a1);
        common_constructor(entry, stack_size);
    }

  template<typename T1, typename T2>
  Thread(int id,
          int (* entry)(T1 a1, T2 a2), T1 a1, T2 a2,
          const State & state = READY,
          const Criterion & criterion = NORMAL,
          unsigned int stack_size = STACK_SIZE)
          : Base(id, entry, a1, a2, state, criterion, stack_size),
            _waiting(0), _joining(0), _link(this, criterion)
  {
      _context = CPU::init_stack(_stack, stack_size, &implicit_exit, entry, a1, a2);
      common_constructor(entry, stack_size);
  }

  template<typename T1, typename T2, typename T3>
  Thread(int id,
          int (* entry)(T1 a1, T2 a2, T3 a3), T1 a1, T2 a2, T3 a3,
          const State & state = READY,
          const Criterion & criterion = NORMAL,
          unsigned int stack_size = STACK_SIZE)
          : Base(id, entry, a1, a2, a3, state, criterion, stack_size),
            _waiting(0), _joining(0), _link(this, criterion)
  {
      _context = CPU::init_stack(_stack, stack_size, &implicit_exit, entry, a1, a2, a3);
      common_constructor(entry, stack_size);
  }

public:
    // Destructor
    ~Thread();

public:
    // Public methods
    const volatile Criterion & criterion() const { return _link.rank(); }

    Priority  priority() const { return int(_link.rank()); }
    void priority(const Priority & p);

    int join();
    void pass();
    void resume();

    void suspend() { suspend(false); }

public:
    // Public static methods
    static Thread * self() { return running(); }
    static void yield();
    static void exit(int status = 0);

    static void init();

protected:
    // Attributes
    Queue* _waiting;
    Thread* volatile _joining;
    Queue::Element _link;


protected:
    // Protected methods
    void common_constructor(Log_Addr entry, unsigned int stack_size);

    Queue::Element * link() { return &_link; }

    void link(Queue::Element link) { _link = link; }

    void suspend(bool locked);

protected:
    // Class attributes
    static Scheduler<Thread>* _scheduler;

protected:
    // Protected class methods
    static Thread * volatile running() { return scheduler()->chosen(); }

    static void sleep(Queue * q);
    static void wakeup(Queue * q);
    static void wakeup_all(Queue * q);

    static Scheduler<Thread>* scheduler()
    {
        if (! _scheduler)
        {
            _scheduler = (Scheduler<Thread> *) (Scheduler_Factory::create_scheduler());
        }

        return _scheduler;
    }

    static void reschedule(bool preempt = preemptive);

    static int idle();

    static void time_slicer();

    static void implicit_exit();


};



// An event handler that triggers a thread (see handler.h)
class Thread_Handler : public Handler
{
public:
    Thread_Handler(Thread * h) : _handler(h) {}
    ~Thread_Handler() {}

    void operator()() { _handler->resume(); }

private:
    Thread * _handler;
};


__END_SYS

#endif
