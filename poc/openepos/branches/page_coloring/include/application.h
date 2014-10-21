// EPOS Global Application Abstraction Declarations

#ifndef __application_h
#define __application_h

#include <utility/heap.h>
#include <segment.h>

__BEGIN_SYS

class Application
{
    friend class Init_Application;
    
public:
    static Heap * const heap() { return &_heap[0]._heap[0]; }
    static Heap * const priority_heap() { return &_heap[1]._heap[0]; }

    static Heap * const heap(unsigned int color) { 
      if(Traits<MMU>::page_coloring)
        return &_heap[0]._heap[color]; 
      else
          return heap();
    }

    static void init();

private:
    //static Heap _heap[IF_INT<Traits<Heap>::priority_alloc, 2, 1>::Result];
    static Multi_Heap<Traits<MMU>::page_coloring> _heap[IF_INT<Traits<Heap>::priority_alloc, 2, 1>::Result];
    static Segment _heap_segment[IF_INT<Traits<MMU>::page_coloring, Traits<MMU>::colors, 1>::Result];
};

__END_SYS

#include <utility/malloc.h>

#endif
