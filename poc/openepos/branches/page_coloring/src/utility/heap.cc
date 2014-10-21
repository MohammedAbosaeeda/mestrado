// EPOS Heap Utility Implementation

#include <utility/heap.h>
#include <machine.h>
#include <application.h>
#include <thread.h>

__BEGIN_SYS

// Methods
void Heap_Common::out_of_memory()
{
    db<Heap>(ERR) << "Heap::alloc(this=" << this
				  << "): out of memory!\n";
    //kout << "Heap::alloc(this=" << this
    //              << "): out of memory! size = " <<  size() 
    //              << " grouped_size = " << grouped_size() << "\n";
    Machine::panic();
}

void * Heap_Common::alloc(unsigned int bytes) {
    if(!bytes)
        return 0;

    while ((bytes % sizeof(void*)) != 0) ++bytes;
    
    if(Traits<MMU>::page_coloring)
      bytes += (sizeof(int) * 2);
    else
      bytes += sizeof(int);
    
    if(bytes < sizeof(Element))
        bytes = sizeof(Element);
    
    //kout << "Allocating " << bytes << " bytes from = " << this 
    //<< " heap grouped_size = " << grouped_size() << " size = " << size();
    
    //kout << " head = " << (void *) head() << " head size = " << head()->size() << "\n";
    
    Element * e = search_decrementing(bytes);
    //kout << "search_decrementing e = " << (void *) e << "\n";
    if(!e) {
        kout << "Allocating " << bytes << " bytes from = " << this 
        << " heap grouped_size = " << grouped_size() << " size = " << size() 
        << " head size = "<< head()->size() << " next size = " << 
        head()->next()->size() << "\n";
        out_of_memory();
        return 0;
    }
    int * addr = reinterpret_cast<int *>(e->object() + e->size());
        
    db<Heap>(TRC) << "Heap::alloc(this=" << this
              << ",bytes=" << bytes 
              << ") => " << (void *)addr << "\n";

    addr[0] = bytes;
    
    if(Traits<MMU>::page_coloring)
      return &addr[2];
    else
      return &addr[1];
}

void * Heap_Common::calloc(unsigned int bytes) {
    void * addr = alloc(bytes);
    memset(addr, bytes, 0);
    return addr;    
}

void Heap_Common::free(void * ptr) {
    int * addr = reinterpret_cast<int *>(ptr);
    free(&addr[-1], addr[-1]);
}

void Heap_Common::free(void * ptr, unsigned int bytes) {
    db<Heap>(TRC) << "Heap::free(this=" << this
              << ",ptr=" << ptr
              << ",bytes=" << bytes << ")\n";
              
    //kout << "Heap::free(this=" << this << ",ptr=" << ptr 
    //<< ",bytes=" << bytes << ")\n";

    if(ptr && (bytes >= sizeof(Element))) {
        Element * e = new (ptr) Element(reinterpret_cast<char *>(ptr), bytes);
        Element * m1, * m2;
        insert_merging(e, &m1, &m2);
    }
}

bool Heap_Profiled::to_priority_heap(unsigned int bytes, alloc_priority p){

    bool ret = false;
    switch (p) {
        case ALLOC_P_HIGH:
            if(bytes < (Traits<Machine>::PRIORITY_HEAP_SIZE - Application::priority_heap()->allocated()))
                ret = true;
            else
                ret = false;
            break;
        case ALLOC_P_LOW:
            if(bytes < (Traits<Machine>::APPLICATION_HEAP_SIZE - Application::heap()->allocated()))
                ret = false;
            else
                ret = true;
            break;
        case ALLOC_P_NORMAL:{
            unsigned int spm_used =
                    100 / (Traits<Machine>::PRIORITY_HEAP_SIZE / Application::priority_heap()->allocated());
            unsigned int main_used =
                    100 / (Traits<Machine>::APPLICATION_HEAP_SIZE / Application::heap()->allocated());

            bool spm_fit =
                    bytes < (Traits<Machine>::PRIORITY_HEAP_SIZE - Application::priority_heap()->allocated());
            bool main_fit =
                    bytes < (Traits<Machine>::APPLICATION_HEAP_SIZE - Application::heap()->allocated());
            bool spm_more_free = spm_used <= main_used;

            if(spm_more_free)
                ret = spm_fit;
            else
                ret = !main_fit;

            break;
        }
        default:
            break;
    }
    return ret;
}

bool Heap_Profiled::from_priority_heap(void * ptr) {
    return (reinterpret_cast<unsigned int>(ptr) >= Traits<Machine>::PRIORITY_HEAP_BASE_ADDR) &&
            (reinterpret_cast<unsigned int>(ptr) <= Traits<Machine>::PRIORITY_HEAP_TOP_ADDR);
}

void * Colored_Heap::alloc(unsigned int bytes) {
        //kout << "Colored_Heap = " << _color << "\n";
        void *ptr = Heap_Common::alloc(bytes);
        int * addr = reinterpret_cast<int *>(ptr);
        addr[-1] = _color;
        
        if(Traits<Thread>::Criterion::shared_data && Thread::self()->criterion() != Thread::Criterion::MAIN && _color >= 2)
            Thread::add_thread_to_bitmap(Thread::self(), _color);
        
        return ptr;
    }

__END_SYS
