// EPOS System Scaffold and System Abstraction Implementation

#include <utility/ostream.h>
#include <utility/heap.h>
#include <machine.h>
#include <thread.h>
#include <system.h>
#include <display.h>

__BEGIN_SYS

// This class purpose is simply to define a well-known entry point for 
// the system. It must be declared as the first global object in
// system_scaffold.cc
class First_Object
{
public:
    First_Object() {
        Display::remap();

        if(Traits<Thread>::smp) {
            System_Info<Machine> * si =
                reinterpret_cast<System_Info<Machine> *>(
                    Memory_Map<Machine>::SYS_INFO);

            Machine::smp_init(si->bm.n_cpus);
        }
    }
};

// Global objects
// These objects might be reconstructed several times in smp configurations,
// so their constructors must be stateless!
First_Object __entry;
OStream kout;
OStream kerr;

// System class attributes
System_Info<Machine> * System::_si =
    reinterpret_cast<System_Info<Machine> *>(Memory_Map<Machine>::SYS_INFO);

Segment System::_heap_segment;
Heap System::_heap;
System_Info<Machine> * const System::info() { return _si; }

__END_SYS


__USING_SYS

// C++ dynamic memory allocators and deallocators
void * operator new(size_t bytes, const System_Allocator & allocator) {
    return System::_heap.alloc(bytes);
}

void * operator new[](size_t bytes, const System_Allocator & allocator) {
    return System::_heap.alloc(bytes);
}

void operator delete(void * ptr) {
    if(Traits<Config>::untyped_heap)
        Heap::untyped_free(&System::_heap, ptr);
    else
        Heap::typed_free(ptr);
}

void operator delete[](void * ptr) {
    if(Traits<Config>::untyped_heap)
        Heap::untyped_free(&System::_heap, ptr);
    else
        Heap::typed_free(ptr);
}


// LIBC Heritage
extern "C" {
    void _exit(int s) {
        Thread::exit(s); for(;;);
    }

    void __cxa_pure_virtual() {
        db<void>(ERR) << "__cxa_pure_virtual() called!\n";
        Machine::panic();
    }
}
