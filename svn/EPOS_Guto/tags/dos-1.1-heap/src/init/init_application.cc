// EPOS Application Initializer

#include <utility/heap.h>
#include <mmu.h>
#include <machine.h>
#include <application.h>
#include <address_space.h>
#include <segment.h>

__BEGIN_SYS

class Init_Application
{
private:
    static const unsigned int HEAP_SIZE = Traits<Application>::HEAP_SIZE;

public:
    Init_Application() {
	db<Init>(TRC) << "\nInit_Application()\n";

        // Initialize Application's heap
        db<Init>(INF) << "Initializing application's heap: \n";
        if(Traits<System>::multiheap) {
            new (&Application::_heap_segment) Segment(HEAP_SIZE);
            new (&Application::_heap) Heap(
                Address_Space(SELF).attach(Application::_heap_segment),
                Application::_heap_segment.size());
        } else {
            for(unsigned int frames = MMU::allocable(); frames; frames = MMU::allocable())
                System::_heap.free(MMU::alloc(frames), frames * sizeof(MMU::Page));
        }
        db<Init>(INF) << "done!\n\n";
    }
};

// Global object "init_application"  must be linked to the application (not 
// to the system) and there constructed at first.
Init_Application init_application;

__END_SYS
