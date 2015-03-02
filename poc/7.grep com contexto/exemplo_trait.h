// Common Mediators
template <> struct Traits<Serial_Display>: public Traits<void>
{
    static const bool enabled = true;
    static const int COLUMNS = 80;
    static const int LINES = 24;
    static const int TAB_SIZE = 8;
    static const unsigned int STACK_SIZE = 1024;
};

__END_SYS

#include __ARCH_TRAITS_H
#include __HEADER_MACH(config)
#include __MACH_TRAITS_H

__BEGIN_SYS

template <> struct Traits<Application>: public Traits<void>
{
    static const unsigned int STACK_SIZE = 1024;
    static const unsigned int HEAP_SIZE = 1024;
    static const unsigned int MAX_THREADS = Traits<Machine>::MAX_THREADS;
};

template <> struct Traits<System>: public Traits<void>
{
    static const unsigned int STACK_SIZE = a31;
    static const unsigned int mode = Traits<Build>::MODE;
    static const bool multithread = (Traits<Application>::MAX_THREADS > 1);
    static const bool multitask = (mode != Traits<Build>::LIBRARY);
    static const bool multicore = (Traits<Build>::CPUS > 1) && multithread;
    static const bool multiheap = (mode != Traits<Build>::LIBRARY);

    enum {FOREVER = 0, SECOND = 1, MINUTE = 60, HOUR = 3600, DAY = 86400, WEEK = 604800, MONTH = 2592000, YEAR = 31536000};
    static const unsigned long LIFE_SPAN = 1 * HOUR; // in seconds

    static const bool reboot = true;

    static const unsigned int STACK_SIZE = ;
    static const unsigned int STACK_SIZE = ;
    static const unsigned int HEAP_SIZE = (Traits<Application>::MAX_THREADS + 1) * Traits<Application>::STACK_SIZE;
};


// Abstractions
template <> struct Traits<Thread>: public Traits<void>
{
    static const bool smp = Traits<System>::multicore;

    static const bool preemptive = true;
    static const unsigned int QUANTUM = 200000;

    static const bool trace_idle = hysterically_debugged;
};

template <> struct Traits<Alarm>: public Traits<void>
{
    static const bool visible = hysterically_debugged;
};

template <> struct Traits<Synchronizer>: public Traits<void>
{
    static const bool enabled = Traits<System>::multithread;
};

__END_SYS

#endif
