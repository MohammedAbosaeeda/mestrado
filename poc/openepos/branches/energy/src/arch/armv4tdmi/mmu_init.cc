// OpenEPOS ARMv4TDMI_MMU Mediator Initialization

#include <mmu.h>

extern "C" unsigned __bss_end__;

__BEGIN_SYS

void ARMv4TDMI_MMU::init() {
	db<Init, ARMv4TDMI_MMU>(TRC) << "ARMv4TDMI_MMU::init()\n";

	// Sticking to ARM memory alignment conventions
	unsigned int base = (unsigned int) &__bss_end__ + 1;
	if (base % 4 != 0) {
		base = base + (4 - base % 4);
	}

	// let our stack breath!
	const unsigned int limit = Memory_Map<Machine>::TOP
			- Traits<Machine>::APPLICATION_STACK_SIZE;
	ARMv4TDMI_MMU::free(base, limit - base);
}

__END_SYS

