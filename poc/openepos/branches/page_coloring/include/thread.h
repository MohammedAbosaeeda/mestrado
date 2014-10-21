// EPOS Thread Abstraction Declarations

#ifndef __thread_h
#define __thread_h

#include <system/kmalloc.h>
#include <utility/queue.h>
#include <utility/handler.h>
#include <cpu.h>
#include <scheduler.h>
#include <tsc.h>
#include <chronometer.h>
#include <perf_mon.h>

__BEGIN_SYS

class Thread
{
    friend class Scheduler<Thread>;
    friend class Synchronizer_Common;

protected:
    static const bool active_scheduler = Traits<Thread>::Criterion::timed;
    static const bool preemptive = Traits<Thread>::Criterion::preemptive;
    static const bool energy_aware = Traits<Thread>::Criterion::energy_aware;
    static const bool global_scheduler = Traits<Thread>::Criterion::GLOBAL_SCHEDULER;
    static const bool smp = Traits<Thread>::smp;
    static const bool dynamic = Traits<Thread>::Criterion::dynamic;

    static const unsigned int QUANTUM = Traits<Thread>::QUANTUM;
    static const unsigned int STACK_SIZE =
        Traits<Machine>::APPLICATION_STACK_SIZE;
    static const unsigned int IDLE_STACK_SIZE =
        Traits<Machine>::IDLE_STACK_SIZE;    
    static const unsigned int MAX_CPUS = Traits<Machine>::MAX_CPUS;

    typedef CPU::Log_Addr Log_Addr;
    typedef CPU::Context Context;
    typedef Timer::Tick Tick;
    typedef RTC::Microsecond Microsecond;

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

    // Thread Queue
    typedef Ordered_Queue<Thread, Criterion, false,
                          Scheduler<Thread>::Element> Queue;

public:
    Thread(int (* entry)(), 
           const State & state = READY,
           const Criterion & criterion = NORMAL,
           unsigned int stack_size = STACK_SIZE, 
           bool data_sharing = false)
    : _state(state), _waiting(0), _joining(0), _link(this, criterion)//, _has_shared_data(data_sharing)
    {
        lock();

        //_scheduled = false;
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
        lock();

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
        lock();

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
        lock();

        _stack = kmalloc(stack_size);
        _context = CPU::init_stack(_stack, stack_size, &implicit_exit, entry, 
                                   a1, a2, a3);

        common_constructor(entry, stack_size);
    }

    ~Thread();

    const volatile State & state() const { return _state; }
    const volatile Criterion & criterion() const { return _link.rank(); }
    Microsecond deadline() const { return _link.rank().get_deadline(); }

    Priority  priority() const { return int(_link.rank()); }
    void priority(const Priority & p);    

    int join();
    void pass();
    void suspend() { suspend(false); }
    void resume();
    void set_affinity(int affinity);

    static Thread * self() { return running(); }
    static void yield();
    static void exit(int status = 0);
    unsigned int id() { return _thread_id; }
    
    static void init();
    
    static void add_thread_to_bitmap(Thread *t, unsigned int color) { 
        Criterion::add_resource_to_bitmap(t->id(), color);
    }

protected:
    void common_constructor(Log_Addr entry, unsigned int stack_size);

    static Thread * volatile running() { return _scheduler.chosen(); }

    Queue::Element * link() { return &_link; }

    static void lock() {
        CPU::int_disable();
        if(smp) {
            //TSC::Time_Stamp start = TSC::time_stamp();
            _lock.acquire();
            //TSC::Time_Stamp end = TSC::time_stamp();
            
            
            /*if(end - start > 10000) {
                kout << "ID [" << Machine::cpu_id() << "] counter = " << _lock_counter[Machine::cpu_id()] 
                << " schedulables = " << _scheduler.schedulables() << " TSC = " << (end - start) 
                << "\n";
            } else {
                _lock_tsc[Machine::cpu_id()][_lock_counter[Machine::cpu_id()]] = end - start;
                _lock_counter[Machine::cpu_id()]++;
            }*/
        }
    }

    static void unlock() {
        if(smp)
            _lock.release();
        CPU::int_enable();
    }

    void suspend(bool locked);
    static void sleep(Queue * q);
    static void wakeup(Queue * q);
    static void wakeup_all(Queue * q);

    static void reschedule(bool preempt = preemptive);
    static void reschedule_resume(bool preempt = preemptive);

    static void time_slicer();
    static void ipi_reschedule(unsigned int i);
    
    static void get_hpc();

    static void implicit_exit();

    static void dispatch(Thread * prev, Thread * next, bool charge = true) {
//	if(charge) {
//  	    if(active_scheduler)
//  		_timer->reset();
//  	    if(energy_aware)
//  		account_energy();
// atualizar as duas variaveis CPU lowest and running
//	}
        if(prev != next) {
            //bool merge = false;
            
            if(prev->_state == RUNNING) {
                prev->_state = READY;
                //prev->_preempted = true;
            } /*else {
              if(prev->_preempted) {
                merge = true;
              }
              prev->_preempted = false;
            }*/
            
            next->_state = RUNNING;

            db<Thread>(TRC) << "Thread::dispatch(prev=" << prev
                            << ",next=" << next << ")\n";
                            
            /*if(prev->criterion() != IDLE && prev->criterion() != MAIN) {
              //kout << "CPU ID = " << Machine::cpu_id() << " perf\n";
              _perf[Machine::cpu_id()]->get_pebs_test(prev->_buffer, merge);
            } */
            
            //if(next->criterion() != IDLE && next->criterion() != MAIN) {
            //  _perf[Machine::cpu_id()]->pebs_test();
            //}

            if(smp)
                _lock.release();
            //_n_context_switches++;
            //prev->_context_switch_time = TSC::time_stamp();
            
            CPU::switch_context(&prev->_context, next->_context);
        } else
            if(smp)
                _lock.release();

        CPU::int_enable();
    }

    static int idle();

protected:
    Log_Addr _stack;
    Context * volatile _context;
    volatile State _state; 
    Queue * _waiting;
    Thread * volatile _joining;
    Queue::Element _link;
    
    static Spin _lock;
    static Scheduler_Timer * _timer;
    static unsigned int _thread_count;
    
    static unsigned int _thread_count_ids;
    unsigned int _thread_id;
    static volatile int _init_done;
public: //test
    static Scheduler<Thread> _scheduler;
    //volatile bool _prevented;
    //volatile unsigned int _context_switch_time;
    //volatile int _prevented_times;
    //volatile unsigned char _conflicted_threads;
    //volatile bool _has_shared_data;
    //static volatile float _data_sharing[Traits<Machine>::MAX_CPUS];
    //static volatile float _contested_accesses[Traits<Machine>::MAX_CPUS];
    //static PMU_Sampling_Timer * _pmu_sampling_timer[Traits<Machine>::MAX_CPUS];
    //static volatile unsigned char _metrics_bitmap;
    //static Perf_Mon *_perf[MAX_CPUS];
    //static Queue _suspended;
    //static volatile unsigned long long _n_context_switches;
    /*static const unsigned int MAX_COUNTING = 75000;
    static volatile TSC::Time_Stamp _sleep_tsc[MAX_COUNTING];
    static volatile unsigned int _sleep_counter;
    static volatile TSC::Time_Stamp _wakeup_tsc[MAX_COUNTING];
    static volatile unsigned int _wakeup_counter;
    static volatile TSC::Time_Stamp _reschedule_tsc[MAX_COUNTING];
    static volatile unsigned int _reschedule_counter;*/
    //static volatile unsigned int _lock_counter[Traits<Machine>::MAX_CPUS];
    //static volatile TSC::Time_Stamp _lock_tsc[Traits<Machine>::MAX_CPUS][MAX_COUNTING];
    //volatile Perf_Mon::perf_data_buffers *_buffer;
    //static const unsigned int MAX_COUNTING = 100000;
    /*static volatile TSC::Time_Stamp _ipi_sender_tsc[MAX_COUNTING];
    static volatile TSC::Time_Stamp _ipi_receiver_tsc[MAX_COUNTING];
    static volatile unsigned int _ipi_sender;
    static volatile unsigned int _ipi_receiver;*/
    /*static volatile bool _start;
    static volatile TSC::Time_Stamp _ipi_per_cpu[Traits<Machine>::MAX_CPUS];
    static volatile int _cpu_ipi_receiver[Traits<Machine>::MAX_CPUS];
    static volatile int _cpu_ipi_sender[Traits<Machine>::MAX_CPUS];
    static volatile TSC::Time_Stamp _ipi_tsc[MAX_COUNTING];
    static volatile unsigned int _ipi_counter;*/
    //bool _preempted;
    //Chronometer c;
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
