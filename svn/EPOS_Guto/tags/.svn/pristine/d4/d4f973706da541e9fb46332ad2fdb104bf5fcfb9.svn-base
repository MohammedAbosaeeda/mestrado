// EPOS Heap Utility Declarations

#ifndef __heap_h
#define __heap_h

#include <utility/debug.h>
#include <utility/list.h>
#include <utility/spin.h>

__BEGIN_SYS

// Heap Common Package (actually the non-atomic heap for single heap
// configurations, i.e., library mode, no scratchpad)
class Heap_Common: private Grouping_List<char>
{
protected:
    static const bool typed = !Traits<Config>::untyped_heap;

public:
    using Grouping_List<char>::empty;
    using Grouping_List<char>::size;

    Heap_Common() {
        db<Init, Heap>(TRC) << "Heap() => " << this << "\n";
    }

    Heap_Common(void * addr, unsigned int bytes) {
        db<Init, Heap>(TRC) << "Heap(addr=" << addr << ",bytes=" << bytes
                            << ") => " << this << "\n";

        free(addr, bytes);
    }

    void * alloc(unsigned int bytes) {
        db<Heap>(TRC) << "Heap::alloc(this=" << this
                      << ",bytes=" << bytes;

        if(!bytes)
            return 0;

        if(!Traits<CPU>::unaligned_memory_access)
            while((bytes % sizeof(void *)) != 0)
                ++bytes;

        if(typed)
            bytes += sizeof(void *);  // add room for heap pointer
        bytes += sizeof(int);         // add room for size
        if(bytes < sizeof(Element))
            bytes = sizeof(Element);

        Element * e = search_decrementing(bytes);
        if(!e) {
            out_of_memory();
            return 0;
        }

        int * addr = reinterpret_cast<int *>(e->object() + e->size());

        if(typed)
            *addr++ = reinterpret_cast<int>(this);
        *addr++ = bytes;

        db<Heap>(TRC) << ") => " << reinterpret_cast<void *>(addr) << "\n";

        return addr;
    }

    void free(void * ptr, unsigned int bytes) {
        db<Heap>(TRC) << "Heap::free(this=" << this
                      << ",ptr=" << ptr
                      << ",bytes=" << bytes << ")\n";

        if(ptr && (bytes >= sizeof(Element))) {
            Element * e = new (ptr)
                    Element(reinterpret_cast<char *>(ptr), bytes);
            Element * m1, * m2;
            insert_merging(e, &m1, &m2);
        }
    }

    static void typed_free(void * ptr) {
        int * addr = reinterpret_cast<int *>(ptr);
        unsigned int bytes = *--addr;
        Heap_Common * heap = reinterpret_cast<Heap_Common *>(*--addr);
        heap->free(addr, bytes);
    }

    static void untyped_free(Heap_Common * heap, void * ptr) {
        int * addr = reinterpret_cast<int *>(ptr);
        unsigned int bytes = *--addr;
        heap->free(addr, bytes);
    }

private:
    void out_of_memory();
};


// Wrapper for non-atomic heap
template <bool atomic>
class Heap_Wrapper: public Heap_Common
{
public:
    Heap_Wrapper() {}
    Heap_Wrapper(void * addr, unsigned int bytes): Heap_Common(addr, bytes) {}

    static void untyped_free(Heap_Wrapper * heap, void * ptr) {
        Heap_Common::untyped_free(heap, ptr);
    }
};


// Wrapper for atomic heap
template <>
class Heap_Wrapper<true>: public Heap_Common
{
public:
    Heap_Wrapper() {}

    Heap_Wrapper(void * addr, unsigned int bytes) {
        free(addr, bytes);
    }

    void * alloc(unsigned int bytes) {
        _lock.acquire();
        void * tmp = Heap_Common::alloc(bytes);
        _lock.release();
        return tmp;
    }

    void free(void * ptr, unsigned int bytes) {
        _lock.acquire();
        Heap_Common::free(ptr, bytes);
        _lock.release();
    }

    static void typed_free(void * ptr) {
        int * addr = reinterpret_cast<int *>(ptr);
        unsigned int bytes = *--addr;
        Heap_Wrapper * heap = reinterpret_cast<Heap_Wrapper *>(*--addr);
        heap->free(addr, bytes);
    }

    static void untyped_free(Heap_Wrapper * heap, void * ptr) {
        int * addr = reinterpret_cast<int *>(ptr);
        unsigned int bytes = *--addr;
        heap->free(addr, bytes);
    }

private:
    Spin _lock;
};


// Heap
class Heap: public Heap_Wrapper<Traits<Thread>::smp>
{
public:
    Heap() {}
    Heap(void * addr, unsigned int bytes):
        Heap_Wrapper<Traits<Thread>::smp>(addr, bytes) {}
};

__END_SYS

#endif
