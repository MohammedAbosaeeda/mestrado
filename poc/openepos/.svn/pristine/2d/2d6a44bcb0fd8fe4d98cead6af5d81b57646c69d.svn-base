// EPOS Heap Utility Declarations

#ifndef __heap_h
#define __heap_h

#include <utility/debug.h>
#include <utility/string.h>
#include <utility/list.h>
#include <utility/spin.h>
//#include <thread.h>

__BEGIN_SYS

// Priority allocation type definitions
typedef enum {
    ALLOC_P_HIGH,
    ALLOC_P_LOW,
    ALLOC_P_NORMAL,
    ALLOC_WR, // multiple writers/readers
    ALLOC_R, // one writer, multiple readers
    COLOR_0 = 0,
    COLOR_1 = 1,
    COLOR_2 = 2,
    COLOR_3 = 3,
    COLOR_4 = 4,
    COLOR_5 = 5,
    COLOR_6 = 6,
    COLOR_7 = 7,
    COLOR_8 = 8,
    COLOR_9 = 9,
    COLOR_10 = 10,
    COLOR_11 = 11,
    COLOR_12 = 12,
    COLOR_13 = 13,
    COLOR_14 = 14,
    COLOR_15 = 15,
    COLOR_16 = 16,
} alloc_priority;

// Heap Common Packages (actually the non-atomic heap)
class Heap_Common: private Grouping_List<char>
{
public:
    using Grouping_List<char>::empty;
    using Grouping_List<char>::size;
    using Grouping_List<char>::grouped_size;
    using Grouping_List<char>::head;

    Heap_Common() {
	db<Init, Heap>(TRC) << "Heap() => " << this << "\n";
    }

    Heap_Common(void * addr, unsigned int bytes) {
	db<Init, Heap>(TRC) << "Heap(addr=" << addr << ",bytes=" << bytes 
			    << ") => " << this << "\n";  
    }

    void * alloc(unsigned int bytes); 
    void * alloc(unsigned int bytes, unsigned int color) { return 0; } 

    void * calloc(unsigned int bytes); 
    void * calloc(unsigned int bytes, unsigned int color) { return 0; } 

    void free(void * ptr); 

    void free(void * ptr, unsigned int bytes);
    void free(void * ptr, unsigned int bytes, unsigned int color) { } 

    unsigned int allocated();
    void allocated(unsigned int bytes);

    unsigned int max_depth();
    void max_depth(unsigned int bytes);

    static bool to_priority_heap(unsigned int bytes, alloc_priority p);
    static bool from_priority_heap(void * ptr);
    
    void set_color(unsigned int color) { }
    unsigned int get_color() { return 0; }

private:
    void out_of_memory();
};


//Heap used when priority allocation is enabled
class Heap_Profiled : public Heap_Common {

public:
    Heap_Profiled()
        :Heap_Common(),
         _allocated(0),
         _max_depth(0)
    {}

    Heap_Profiled(void * addr, unsigned int bytes)
        :Heap_Common(addr, bytes),
        _allocated(0),
        _max_depth(0)
    {}

    void * alloc(unsigned int bytes){
        void* aux = Heap_Common::alloc(bytes);
        if(aux){
            _allocated += bytes;
            _max_depth = _allocated > _max_depth ? _allocated : _max_depth;
        }
        return aux;
    }

    void free(void * ptr, unsigned int bytes){
        Heap_Common::free(ptr, bytes);
        _allocated -= bytes;
    }

    void * calloc(unsigned int bytes) {return Heap_Common::calloc(bytes);}

    void free(void * ptr){
        int * addr = reinterpret_cast<int *>(ptr);
        free(&addr[-1], addr[-1]);
    }

    static bool to_priority_heap(unsigned int bytes, alloc_priority p);
    static bool from_priority_heap(void * ptr);

    unsigned int allocated() { return _allocated; }
    void allocated(unsigned int bytes) { _allocated = bytes; }

    unsigned int max_depth() { return _max_depth; }
    void max_depth(unsigned int bytes) { _max_depth = bytes; }

private:
    unsigned int _allocated;
    unsigned int _max_depth;
};

class Colored_Heap : public Heap_Common {
public:
    Colored_Heap()
        :Heap_Common(),
         _color(0)
    {}

    Colored_Heap(void * addr, unsigned int bytes)
        :Heap_Common(addr, bytes),
        _color(0)
    {}
    
    void * alloc(unsigned int bytes);
    /*{
        //kout << "Colored_Heap = " << _color << "\n";
        void *ptr = Heap_Common::alloc(bytes);
        int * addr = reinterpret_cast<int *>(ptr);
        addr[-1] = _color;
        return ptr;
    }*/

    void free(void * ptr, unsigned int bytes){
        Heap_Common::free(ptr, bytes);
    }

    void * calloc(unsigned int bytes) {
        void *ptr = Heap_Common::calloc(bytes);
        int * addr = reinterpret_cast<int *>(ptr);
        addr[-1] = _color;
        return ptr;
    }

    void free(void * ptr){
        int * addr = reinterpret_cast<int *>(ptr);
        free(&addr[-2], addr[-2]);
    }
  
    void set_color(unsigned char color) { _color = color; }
    unsigned char get_color() { return _color; }
  
private:
    unsigned char _color;
};


// Wrapper for non-atomic heap  
template <bool atomic>
class Heap_Wrapper: public IF<Traits<MMU>::page_coloring, Colored_Heap,
            IF<Traits<Heap>::priority_alloc, Heap_Profiled, Heap_Common>::Result>::Result {};


// Wrapper for atomic heap
template<>
class Heap_Wrapper<true>: public IF<Traits<MMU>::page_coloring, Colored_Heap,
            IF<Traits<Heap>::priority_alloc, Heap_Profiled, Heap_Common>::Result>::Result
{
public:
    typedef IF<Traits<MMU>::page_coloring, Colored_Heap,
    IF<Traits<Heap>::priority_alloc, Heap_Profiled, Heap_Common>::Result>::Result Base;

    Heap_Wrapper() {}

    Heap_Wrapper(void * addr, unsigned int bytes): Base(addr, bytes) {
	free(addr, bytes); 
    }

    void * alloc(unsigned int bytes) {
	_lock.acquire();
	void * tmp = Base::alloc(bytes);
	_lock.release();
	return tmp;
    }

    void * calloc(unsigned int bytes) {
	_lock.acquire();
	void * tmp = Base::calloc(bytes);
	_lock.release();
	return tmp;
    }

    void free(void * ptr) {
	_lock.acquire();
	Base::free(ptr);
	_lock.release();
    }

    void free(void * ptr, unsigned int bytes) {
	_lock.acquire();
	Base::free(ptr, bytes);
	_lock.release();
    }

private:
    Spin _lock;
};

// Heap
class Heap: public Heap_Wrapper<Traits<Thread>::smp> {
public:
        
    Heap() { }
    Heap(void * addr, unsigned int bytes): Heap_Wrapper<Traits<Thread>::smp>(addr, bytes) { }
};

// Heap specialization for one List Heap
template <bool coloring>
class Multi_Heap: public Heap {
public:
    Heap _heap[1];
};

// Heap specializarion for Multi List Heap (used by page coloring mechanism)
template <>
class Multi_Heap<true>: public Heap {
public:
    Heap _heap[Traits<MMU>::colors];
  
};

__END_SYS

#endif
