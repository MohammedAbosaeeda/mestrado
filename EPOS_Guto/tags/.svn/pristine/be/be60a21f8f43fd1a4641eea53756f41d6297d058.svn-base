#ifndef __system_traits_h
#define __system_traits_h

#include <system/config.h>

__BEGIN_SYS

template <typename T>
struct Traits
{
    static const bool enabled = true;
    static const bool debugged = false;
};


// Utilities
template <> struct Traits<Debug>
{
    static const bool error   = true;
    static const bool warning = true;
    static const bool info    = true;
    static const bool trace   = true;
};

template <> struct Traits<Lists>: public Traits<void>
{
    static const bool debugged = false;
};

template <> struct Traits<Spin>: public Traits<void>
{
    static const bool debugged = false;
};

template <> struct Traits<Heap>: public Traits<void>
{
};


// System Parts
template <> struct Traits<Boot>: public Traits<void>
{
};

template <> struct Traits<Setup>: public Traits<void>
{
};

template <> struct Traits<Init>: public Traits<void>
{
};

template <> struct Traits<Application>: public Traits<void>
{
    static const unsigned int STACK_SIZE = 16 * 1024;
    static const unsigned int HEAP_SIZE = 16 * 1024 * 1024;
};

template <> struct Traits<System>: public Traits<void>
{
    static const bool reboot = true;

    static const unsigned int STACK_SIZE = 4 * 1024;
    static const unsigned int HEAP_SIZE = 128 * Traits<Application>::STACK_SIZE;
};


// Common Mediators
template <> struct Traits<Serial_Display>: public Traits<void>
{
    static const bool enabled = true;
    static const int COLUMNS = 80;
    static const int LINES = 24;
    static const int TAB_SIZE = 8;
};


// Abstractions
template <> struct Traits<Thread>: public Traits<void>
{
    static const bool smp = false;
    typedef Scheduling_Criteria::RM Criterion;
    static const bool trace_idle = true;
    static const unsigned int QUANTUM = 10000; // us
};

template <> struct Traits<Alarm>: public Traits<void>
{
    static const bool visible = false;
};

template <> struct Traits<Synchronizer>: public Traits<void>
{
};

__END_SYS

#endif
