// EPOS Thread Abstraction Initialization

#include <system/kmalloc.h>
#include <system.h>
#include <thread.h>
#include <alarm.h>
#include <pmu.h>

__BEGIN_SYS

void Thread::init()
{
    int (* entry)() =
	reinterpret_cast<int (*)()>(System::info()->lmm.app_entry);

    db<Init, Thread>(TRC) << "Thread::init(entry=" << (void *)entry << ")\n";
    
    if(Machine::cpu_id() == 0) {
        if(global_scheduler) {
            // The interrupt handler method must be set before the creation of
            // the first thread, because the thread constructor may call a reschedule
            IC::int_vector(IC::INT_RESCHEDULER, (IC::Interrupt_Handler) &ipi_reschedule);
            IC::enable(IC::INT_RESCHEDULER);
        }
    
        // The installation of the scheduler timer handler must precede the
        // creation of threads, since the constructor can induce a reeschedule
        // and this in turn can call timer->reset()
        // Letting reschedule() happen during thread creating is harmless, since
        // MAIN is created first and dispatch won't replace it nor by itself
        // neither by IDLE (that has a lower priority)
        if(active_scheduler)
            _timer = new (kmalloc(sizeof(Scheduler_Timer))) 
                     Scheduler_Timer(QUANTUM, &time_slicer);
    }

    Machine::smp_barrier();

    Thread * first;
    if(Machine::cpu_id() == 0) {
	// Create the application's main thread
	// This must precede idle, thus avoiding implicit rescheduling
	first = new(kmalloc(sizeof(Thread))) Thread(entry, RUNNING, MAIN);
	new(kmalloc(sizeof(Thread))) Thread(&idle, READY, IDLE, IDLE_STACK_SIZE);
    } else 
	first = new(kmalloc(sizeof(Thread))) Thread(&idle, READY, IDLE, IDLE_STACK_SIZE);

    CPU::finc(_init_done);
    Machine::smp_barrier();
    
    //_perf[Machine::cpu_id()] = new(kmalloc(sizeof(Perf_Mon))) Perf_Mon();
    
    /*if(Traits<Thread>::pmu_sampling) {
        _pmu_sampling_timer[Machine::cpu_id()] = new (kmalloc(sizeof(PMU_Sampling_Timer))) 
        PMU_Sampling_Timer(Traits<Thread>::SAMPLING_PERIOD, &get_hpc);
        PMU::config(PMU::EVTSEL0, (Intel_Sandy_Bridge_PMU::XSNP_HITM | PMU::USR | PMU::OS | PMU::ENABLE));
        PMU::config(PMU::EVTSEL1, (Intel_Sandy_Bridge_PMU::XSNP_HIT | PMU::USR | PMU::OS | PMU::ENABLE));
        Intel_PMU_Version3::enable_fixed_ctr1();
    }*/

    db<Init, Thread>(INF) << "Dispatching the first thread: " << first
			  << "\n";

    This_Thread::not_booting();

    first->_context->load();
}

__END_SYS
