// EPOS CPU CPU Mediator Initialization

#include <cpu.h>
#include <mmu.h>
#include <pmu.h>
#include <system.h>
#include <system/info.h>

extern "C" { void __epos_library_app_entry(void); }

__BEGIN_SYS

void CPU::init()
{
    db<Init, CPU>(TRC) << "CPU::init()\n";

    _cpu_clock = System::info()->tm.cpu_clock;
    _bus_clock = System::info()->tm.bus_clock;

    // Initialize the MMU
    if(Traits<CPU_MMU>::enabled)
    	CPU_MMU::init();
    else
    	db<Init, CPU>(WRN) << "MMU is disabled!\n";

	// Initialize the PMU
    if(Traits<CPU_PMU>::enabled)
    	CPU_PMU::init();
    else
    	db<Init, CPU_PMU>(WRN) << "PMU is disabled!\n";
}

__END_SYS
