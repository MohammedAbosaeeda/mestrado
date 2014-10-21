// EPOS System Initializer

#include <machine.h>
#include <system.h>
#include <segment.h>
#include <address_space.h>

extern "C" { void __epos_library_app_entry(void); }

__BEGIN_SYS

class Init_System
{
public:
    Init_System() {
	db<Init>(TRC) << "\nInit_System(CPU=" << Machine::cpu_id() << ")\n";

	Machine::smp_barrier();

	// Only the boot CPU runs INIT_SYSTEM fully
	if(Machine::cpu_id() != 0) {
	    // Wait until the boot CPU has initialized the machine
	    Machine::smp_barrier();
	    // For IA-32, timer is CPU-local. What about other SMPs?
	    Timer::init();
	    Machine::smp_barrier();
	    return;
	}
	
	// Initialize the processor
	db<Init>(INF) << "Initializing the CPU: \n";
	CPU::init();
	db<Init>(INF) << "done!\n\n";

	// If EPOS is a library then adjust the application entry point (that
	// was set by SETUP) based on the ELF SYSTEM+APPLICATION image
	System_Info<Machine> * si = System::info();
	if(!si->lm.has_sys)
	    si->lmm.app_entry =
		reinterpret_cast<unsigned int>(&__epos_library_app_entry);

	// Initialize System's heap
	db<Init>(INF) << "Initializing system's heap \n";
    kout << "Initializing system's heap\n";
    if(Traits<MMU>::page_coloring) {
        //System_Info<Machine> * si = System::info();
            
        unsigned int size = Traits<Machine>::SYSTEM_HEAP_SIZE;
        //for(unsigned int i = 0; i < 2; i++) {
        unsigned int i; //color
        
        //while(1);
        
        if(Traits<IA32_MMU>::uncolored_system_heap) {
            i = 0; // allocate from an uncolored MMU list
        } else {
            i = 1; //allocate from the MMU list number 1 (color 1)
        }
        new (&System::_heap_segment) Segment(size, i, Segment::Flags::SYS);
        
        
        kout << "Segment = " << (void *) &System::_heap_segment << " Segment size = " 
            << System::_heap_segment.size() << "\n";
                       
        new (&System::_heap) Heap(Address_Space(Address_Space::SELF).attach(System::_heap_segment),
                System::_heap_segment.size());
        
        /*Segment *app_heap_seg = new (reinterpret_cast<void *>(si->pmm.page_col_base)) Segment(size, i, Segment::Flags::SYS);
        
        si->pmm.page_col_base += sizeof(Segment);
         
        CPU::Log_Addr * app_heap_base = Address_Space(Address_Space::SELF).attach(*app_heap_seg);
        
        System::heap()->free(app_heap_base, app_heap_seg->size());*/
        
        //System::heap()->set_color(0);
        
        kout << "Init_System(" << (void *) System::heap() << ") heap grouped_size = " << System::heap()->grouped_size() << 
        " size = " << System::heap()->size() << " head = " << (void *) System::heap()->head() <<
        " head size= " << System::heap()->head()->size() << "\n";
      //}
    } else {
      System::heap()->
	    free(MMU::alloc(0, MMU::pages(Traits<Machine>::SYSTEM_HEAP_SIZE)),
		 Traits<Machine>::SYSTEM_HEAP_SIZE);
      //kout << "Init_System() heap size = " << System::heap()->grouped_size() << "\n";
    }
	db<Init>(INF) << "done!\n\n";
    kout << "done!\n";
    //while(1);

	// Initialize the machine
	db<Init>(INF) << "Initializing the machine: \n";
    kout << "Initializing the machine: \n";
	Machine::init();
	db<Init>(INF) << "done!\n\n";
    kout << "done!\n\n";

    //while(1);
    
	Machine::smp_barrier(); // signalizes "machine ready" to other CPUs
	Machine::smp_barrier(); // wait for them to fihish Machine::init()

	// Initialize system abstractions 
	db<Init>(INF) << "Initializing system abstractions: \n";
    kout << "Initializing system abstractions: \n";
	System::init();
	db<Init>(INF) << "done!\n\n";
    kout << "done!\n\n";

	// Initialization continues at init_first
    }
};

// Global object "init_system" must be constructed first.
Init_System init_system;

__END_SYS
