// EPOS-- System Initializer

#include <machine.h>
#include <system.h>

extern "C" { void __epos_library_app_entry(void); }

__BEGIN_SYS

class Init_System
{
public:
    Init_System() {
	db<Init>(TRC) << "\nInit_System()\n";

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
	System::heap()->
	    free(MMU::alloc(MMU::pages(Traits<Machine>::SYSTEM_HEAP_SIZE)),
		 Traits<Machine>::SYSTEM_HEAP_SIZE);
	db<Init>(INF) << "done!\n\n";

	// Initialize the machine
	db<Init>(INF) << "Initializing the machine: \n";
	Machine::init();
	db<Init>(INF) << "done!\n\n";

	// Initialize system abstractions 
	db<Init>(INF) << "Initializing system abstractions: \n";
	System::init();
	db<Init>(INF) << "done!\n\n";

	// Initialization continues at init_first
    }
};

// Global object "init_system"  must be constructed first.
Init_System init_system;

__END_SYS
