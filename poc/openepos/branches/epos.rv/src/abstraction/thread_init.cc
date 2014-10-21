// EPOS Thread Abstraction Initialization

#include <system/kmalloc.h>
#include <system.h>
#include <thread.h>
#include <alarm.h>

__BEGIN_SYS

void Thread::init()
{
    int (* entry)() =
	reinterpret_cast<int (*)()>(System::info()->lmm.app_entry);

    db<Init, Imp::Thread>(TRC) << "Thread::init(entry=" << (void *)entry << ")\n";

    Machine::smp_barrier();

    Thread* first;
    if(Machine::cpu_id() == 0)
    {
        // Create the application's main thread
        // This must precede idle, thus avoiding implicit rescheduling
        first = new(kmalloc(sizeof(Thread))) Thread(Thread::MAIN_ID, entry, RUNNING, MAIN);

        new(kmalloc(sizeof(Thread))) Thread(Thread::IDLE_ID, &idle, READY, IDLE);
    }
    else
    {
        first = new(kmalloc(sizeof(Thread))) Thread(Thread::IDLE_ID, &idle, READY, IDLE);
    }

    Machine::smp_barrier();

    if(Machine::cpu_id() == 0)
    {
        if(active_scheduler)
            _timer = new (kmalloc(sizeof(Scheduler_Timer)))
            Scheduler_Timer(QUANTUM, &time_slicer);
    } 

    db<Init, Imp::Thread>(INF) << "Dispatching the first thread: " << first
			  << "\n";

    This_Thread::not_booting();

    first->_context->load();
}

__END_SYS
