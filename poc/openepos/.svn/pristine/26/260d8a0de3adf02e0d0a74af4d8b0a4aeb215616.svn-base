// EPOS Application-level Dynamic Memory Utility Declarations

#ifndef __malloc_h
#define __malloc_h

#include <application.h>
#include <mmu.h>
//#include <thread.h>

extern "C"
{
    inline void * malloc(size_t bytes) {
	return __SYS(Application)::heap()->alloc(bytes);
    }

    inline void * calloc(size_t n, unsigned int bytes) {
	return __SYS(Application)::heap()->calloc(n * bytes);
    }

    inline void free(void * ptr) {
	__SYS(Application)::heap()->free(ptr);
    }

    inline void * p_malloc(size_t bytes) {
        return __SYS(Application)::priority_heap()->alloc(bytes);
    }

    inline void * p_calloc(size_t n, unsigned int bytes) {
        return __SYS(Application)::priority_heap()->calloc(n * bytes);
    }

    inline void p_free(void * ptr) {
        __SYS(Application)::priority_heap()->free(ptr);
    }
    
    inline void * malloc_colored(size_t bytes, int color) {
      //__SYS(kout) << "malloc_colored(" <<  __SYS(Application)::heap(color) << ") color = " 
      //            << color << " bytes = " << bytes << "\n";
    return __SYS(Application)::heap(color)->alloc(bytes);
    }
    
    inline void free_colored(void * ptr, int color) {
        __SYS(Application)::heap(color)->free(ptr);
    }
    
    inline void * calloc_colored(size_t n, unsigned int bytes, int color) {
        return __SYS(Application)::heap(color)->calloc(n * bytes);
    }
}

inline void * decide_malloc(unsigned int bytes,
        __SYS(alloc_priority) p = __SYS(ALLOC_P_NORMAL)) 
{
    void *rtn;
        
    /*if(__SYS(Traits)<__SYS(Heap)>::shared_data == false) {
      if(__SYS(Heap)::to_priority_heap(bytes, p)){
          rtn = p_malloc(bytes);
          if(rtn == 0) rtn = malloc(bytes);
      }
      else {
          rtn = malloc(bytes);
          if(rtn == 0) rtn = p_malloc(bytes);
      }
    } else {
        if(p <= __SYS(ALLOC_P_NORMAL)) {
            rtn = malloc(bytes);
            } else if(p == __SYS(ALLOC_WR)) {
            rtn = p_malloc(bytes);
            //__SYS(kout) << "decide_malloc ALLOC_WR\n";
            __SYS(MMU)::change_page_flags(rtn, bytes, __SYS(MMU)::Flags::CD);
        } else { //ALLOC_R
            __SYS(kout) << "decide_malloc ALLOC_R not handle yet\n";
        }
    }*/
    
    rtn = malloc(bytes);
    
    return rtn;
}

/*inline unsigned int page_color()
{
    __SYS(Thread) *thr = __SYS(Thread)::self();
    
    if(thr->priority() == __SYS(Thread)::MAIN || thr->priority() == __SYS(Thread)::NORMAL)
      return __SYS(Traits<__SYS(MMU)>)::colors;
    
    return thr->thread_id();
}*/

inline void * decide_page_coloring_malloc(unsigned int bytes, unsigned int color = __SYS(COLOR_0))
{
    void *rtn;
    
    //unsigned int color = page_color();
    
    //__SYS(kout) << "decide_page_coloring_malloc color = " << color << "\n";
    
    //int size = __SYS(Application)::heap(color)->grouped_size();
    //__SYS(kout) << "Heap " << color << " size = " << size << "\n";
    
    if(color >= __SYS(Traits)<__SYS(MMU)>::colors) {
      __SYS(kout) << "Error: color " << color << " should be less than = " << __SYS(Traits)<__SYS(MMU)>::colors << "\n";
      return 0;
    }
    
    rtn = malloc_colored(bytes, color);
    
    //__SYS(kout) << "decide_page_coloring_malloc bytes " << bytes << " color = " << color << "\n";
    
    /*unsigned int addr = (unsigned int) rtn;
    
    __SYS(CPU)::Log_Addr laddr(addr);
    __SYS(CPU)::Phy_Addr paddr = __SYS(MMU)::physical(laddr);
        
    for(unsigned int i = 0; i < bytes; i += __SYS(MMU)::PAGE_SIZE) {
        paddr = __SYS(MMU)::align_page(paddr);
        paddr = (paddr & 0xFFF00FFF) | (color << 12);
      
        //__SYS(kout) << "Page coloring Log_Addr = " << (void *) laddr
        //<< " Phy_Addr = " << (void *) paddr << " color = " << color 
        //<< " heap size = " << __SYS(Application)::heap(color)->grouped_size() << "\n";
        
        //__SYS(kout) << " before..";
        
        __SYS(MMU)::map_page_log_to_phy(laddr, paddr);
        //__SYS(kout) << "after";
        
        //for(int j = 0; j < 25; j++) ;
        //paddr = __SYS(MMU)::physical(laddr);
        //__SYS(kout) << "Phy_Addr = " << (void *) paddr << "\n";
        
        laddr += __SYS(MMU)::PAGE_SIZE;
        paddr = __SYS(MMU)::physical(laddr);
    }*/
      
    return rtn;
}

inline void * decide_calloc(unsigned int n, unsigned int bytes,
        __SYS(alloc_priority) p = __SYS(ALLOC_P_NORMAL)) {
    if(__SYS(Traits)<__SYS(MMU)>::page_coloring)
        return calloc_colored(n, bytes, p);
    else if(__SYS(Heap)::to_priority_heap(n*bytes, p))
        return p_calloc(n, bytes);
    else
        return calloc(n, bytes);
}

inline void decide_free(void * ptr) {
    if(__SYS(Traits)<__SYS(MMU)>::page_coloring) {
        int * addr = reinterpret_cast<int *>(ptr);
        unsigned char color = (unsigned char) addr[-1];
        //__SYS(kout) << "free = " << (void *) ptr << " from color = " << color << "\n";
        return free_colored(ptr, color);
    } else if(__SYS(Heap)::from_priority_heap(ptr))
        p_free(ptr);
    else
        free(ptr);
}

inline void * operator new(size_t bytes, __SYS(alloc_priority) p) {
    if(__SYS(Traits)<__SYS(MMU)>::page_coloring) {
        //__SYS(kout) << "operator new(bytes, p)\n";
        return decide_page_coloring_malloc(bytes, p);
    } else if(__SYS(Traits)<__SYS(Heap)>::priority_alloc)
        return decide_malloc(bytes, p);
    else
        return malloc(bytes);
}

inline void * operator new[](size_t bytes, __SYS(alloc_priority) p) {
    if(__SYS(Traits)<__SYS(MMU)>::page_coloring) {
        //__SYS(kout) << "operator new[](bytes=" << bytes << ", p=" << (int) p << ")\n";
        return decide_page_coloring_malloc(bytes, p);
    } else if(__SYS(Traits)<__SYS(Heap)>::priority_alloc)
        return decide_malloc(bytes, p);
    else
        return malloc(bytes);
}

inline void * operator new(size_t bytes) {
    if(__SYS(Traits)<__SYS(MMU)>::page_coloring) {
        //__SYS(kout) << "operator new(bytes)\n";
        return decide_page_coloring_malloc(bytes);
    } else if(__SYS(Traits)<__SYS(Heap)>::priority_alloc)
        return decide_malloc(bytes);
    else
        return malloc(bytes);
}

inline void * operator new[](size_t bytes) {
    if(__SYS(Traits)<__SYS(MMU)>::page_coloring) {
        //__SYS(kout) << "operator new[](bytes)\n";
        return decide_page_coloring_malloc(bytes);
    } else if(__SYS(Traits)<__SYS(Heap)>::priority_alloc)
        return decide_malloc(bytes);
    else
        return malloc(bytes);
}

inline void operator delete(void * object) {
    if(__SYS(Traits)<__SYS(Heap)>::priority_alloc || __SYS(Traits)<__SYS(MMU)>::page_coloring)
        return decide_free(object);
    else
        return free(object);
}

inline void operator delete[](void * object) {
    if(__SYS(Traits)<__SYS(Heap)>::priority_alloc || __SYS(Traits)<__SYS(MMU)>::page_coloring)
        return decide_free(object);
    else
        return free(object);
}

#endif
