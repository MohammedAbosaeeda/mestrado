// OpenEPOS ARMv4TDMI CPU Mediator Implementation

#include <cpu.h>
#include <mach/emote2arm/memory_map.h>

__BEGIN_SYS

ARMv4TDMI::OP_Mode ARMv4TDMI::_mode = ARMv4TDMI::FULL;

void ARMv4TDMI::Context::save() volatile {

}

void ARMv4TDMI::Context::load() const volatile {
	db < CPU > (TRC) << "CPU::Context::load(this=" << (void*) this << ")\n";

	ASMV("ldr r0, [%0, #64]\n"
			"msr spsr_cfsx, r0\n"
			"ldmfd %0, {r0-r12,sp,lr,pc}^\n"
			:
			: "r" (this)
			: "r0");
}

void ARMv4TDMI::switch_context(Context * volatile * o, Context * volatile n) {
	Context * old = *o;

	db<CPU>(TRC) << "ARMv4TDMI::switch_context(o=" << old << ",n=" << *n
			<< ")\n";

	old->_cpsr = CPU::flags();

	ASMV("ldr r2, [%0, #64]" : : "r"(n) : "r2");
	ASMV("msr spsr_cfsx, r2");
	ASMV("stmia %0, {r0-r12,sp,lr,pc} \n" // pc is always read with a +8 offset
			"ldmfd %1, {r0-r12,sp,lr,pc}^"
			: : "r"(old), "r"(n) :);
	ASMV("nop");
	// so the pc read above is poiting here
}

void ARMv4TDMI::power(ARMv4TDMI::OP_Mode mode) {
	if (mode == _mode)
		return;
	_mode = mode;

#ifdef __emote2arm__
	typedef IO_Map<Machine> IO; // TODO: remove reference to Machine mediator.
	switch(mode)
	{
		case OFF:
		db<CPU>(WRN) << "Shutting system down! No way back from here!\n";
		int_disable();
		case HIBERNATE:
		out32(IO::CRM_SLEEP_CNTL, in32(IO::CRM_SLEEP_CNTL) | 0x1);
		break;
		case DOZE:
		out32(IO::CRM_WU_CNTL, in32(IO::CRM_WU_CNTL) | 0x1);
		out32(IO::CRM_SLEEP_CNTL, in32(IO::CRM_SLEEP_CNTL) | 0x1<<6 | 0x3<<4 | 0x1 );
		while(!(in32(IO::CRM_STATUS) & 0x1));
		out32(IO::CRM_STATUS, 0x1); //writing 1 clears the SLEEP_SYNC bit
		break;
		case FULL:
		default:
		break;
	}

#endif
}
extern "C" void __cxa_guard_acquire() {
	CPU::int_disable();
}
extern "C" void __cxa_guard_release() {
	CPU::int_enable();
}

__END_SYS

