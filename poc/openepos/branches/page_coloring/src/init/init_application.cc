// EPOS Application Initializer

#include <utility/heap.h>
#include <mmu.h>
#include <machine.h>
#include <application.h>
#include <segment.h>
#include <address_space.h>

__BEGIN_SYS

class Init_Application
{
public:
    Init_Application() {
	db<Init>(TRC) << "\nInit_Application(CPU="
		      << Machine::cpu_id() << ")\n";

	// Only the boot CPU runs INIT_APPLICATION
	Machine::smp_barrier();
	if(Machine::cpu_id() != 0)
	    return;

	// Initialize Application's heap
	db<Init>(INF) << "Initializing application's heap \n";
    kout << "Initializing application's heap \n";
    
    if(Traits<MMU>::page_coloring) {  
        //System_Info<Machine> * si = System::info();
      //kout << "Init_Application() Allocating memory for heap\n";
      unsigned int size = Traits<Machine>::APPLICATION_HEAP_SIZE;
      for(unsigned int i = 0; i < Traits<MMU>::colors; i++) {
                        
        if(i == 0)
            new (&Application::_heap_segment[i]) Segment(size/20, i+1, Segment::Flags::APP);
        else
            new (&Application::_heap_segment[i]) Segment(size, i+1, Segment::Flags::APP);
                
        new (Application::heap(i)) Heap(Address_Space(Address_Space::SELF).attach(Application::_heap_segment[i]),
                                        Application::_heap_segment[i].size());
        
        /*Segment *app_heap_seg;
        
        if(i == 0)
            app_heap_seg = new (reinterpret_cast<void *>(si->pmm.page_col_base)) Segment(size/2, i, Segment::Flags::APP);
        else
            app_heap_seg = new (reinterpret_cast<void *>(si->pmm.page_col_base)) Segment(size, i, Segment::Flags::APP);
        
        si->pmm.page_col_base += sizeof(Segment);

        CPU::Log_Addr * app_heap_base = Address_Space(Address_Space::SELF).attach(*app_heap_seg);
      
        Application::heap(i)->free(app_heap_base, app_heap_seg->size());*/
        
        Application::heap(i)->set_color(i);
        
        if(i < 4)
        kout << "Init_Application(" << (void *) Application::heap(i) << ") Heap " 
          << i << " grouped_size = " << Application::heap(i)->grouped_size() << 
          " size = " << Application::heap(i)->size() << 
        " head = " << (void *) Application::heap(i)->head() << " head size = " << 
        Application::heap(i)->head()->size() << "\n";
        //if(i == 3) while(1) ;
      }
    } else {
      Application::heap()->free(MMU::alloc(0, MMU::pages(Traits<Machine>::APPLICATION_HEAP_SIZE)),
          Traits<Machine>::APPLICATION_HEAP_SIZE);
    }
	//if(Traits<Heap>::priority_alloc){
	  //  db<Init>(INF) << "Initializing application's priority heap \n";
	    /*if(Traits<Heap>::shared_data)
            Application::priority_heap()->
        free(MMU::alloc(MMU::pages(Traits<Machine>::PRIORITY_HEAP_SIZE)),
             Traits<Machine>::PRIORITY_HEAP_SIZE);
        else
            Application::priority_heap()->free(reinterpret_cast<void*>(Traits<Machine>::PRIORITY_HEAP_BASE_ADDR), Traits<Machine>::PRIORITY_HEAP_SIZE);
        Application::priority_heap()->allocated(0);
        Application::heap()->allocated(0);*/
	//}
	db<Init>(INF) << "done!\n\n";
    kout << "done!\n";
    }
};

// Global object "init_application"  must be linked to the application (not 
// to the system) and there constructed at first.
Init_Application init_application;

__END_SYS
