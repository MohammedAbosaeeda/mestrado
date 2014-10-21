// EPOS Application Scaffold and Application Abstraction Implementation

#include <utility/ostream.h>
#include <system.h>
#include <application.h>

__BEGIN_SYS

// Global objects
OStream cout;
OStream cerr;

// Application class attributes
Segment Application::_heap_segment;
Heap Application::_heap;

__END_SYS

__USING_SYS;

// Standard C Library dynamic memory allocators and deallocators
extern "C"
{
    void * malloc(size_t bytes) {
        if(Traits<Config>::untyped_heap)
            return System::_heap.alloc(bytes);
        else
            return Application::_heap.alloc(bytes);
    }

    void * calloc(size_t n, unsigned int bytes) {
        void * addr = malloc(n * bytes);
        memset(addr, n * bytes, 0);
        return addr;
    }

    void free(void * ptr) {
        if(Traits<Config>::untyped_heap)
            Heap::untyped_free(&System::_heap, ptr);
        else
            Heap::typed_free(ptr);
    }
}


// C++ dynamic memory allocators and deallocators
void * operator new(size_t bytes) {
    return malloc(bytes);
}

void * operator new[](size_t bytes) {
    return malloc(bytes);
}

void operator delete(void * ptr) {
    free(ptr);
}

void operator delete[](void * ptr) {
    free(ptr);
}

