// OpenEPOS ARMv4TDMI CPU Mediator Initialization

#include <cpu.h>
#include <mmu.h>
#include <tsc.h>

__BEGIN_SYS

void ARMv4TDMI::init() {
	db<Init, ARMv4TDMI>(TRC) << "ARMv4TDMI::init()\n";

	if (Traits<MMU>::enabled)
		MMU::init();
	if (Traits<TSC>::enabled)
		TSC::init();

	return;
}

__END_SYS
