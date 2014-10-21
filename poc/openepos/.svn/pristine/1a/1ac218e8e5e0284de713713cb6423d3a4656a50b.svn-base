// EPOS Global System Abstraction Declarations

#ifndef __system_h
#define __system_h

#include <utility/heap.h>
#include <segment.h>

__BEGIN_SYS

//class Machine;

class System
{
    friend class Init_System;
    friend class Init_Application;
public:
    static System_Info<Machine> * const info();
    static Heap * const heap() { return &_heap; }

    static void init();

private:
    static System_Info<Machine> * _si;
    static Heap _heap;
    static Segment _heap_segment;
};

__END_SYS

#endif
