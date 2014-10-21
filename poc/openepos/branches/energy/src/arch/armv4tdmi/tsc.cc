// OpenEPOS ARMv4TDMI_TSC Implementation

#include <tsc.h>
#include <machine.h>

__BEGIN_SYS

volatile ARMv4TDMI_TSC::Time_Stamp ARMv4TDMI_TSC::_ts = 0;

ARMv4TDMI_TSC::Time_Stamp ARMv4TDMI_TSC::time_stamp() {
	return cntr() | (_ts << 16);
}

void ARMv4TDMI_TSC::tsc_timer_handler() {
	_ts++;
}

ARMv4TDMI_TSC::ARMv4TDMI_TSC() {
}

void ARMv4TDMI_TSC::ctrl(CPU::Reg16 value) {
	CPU::out16(Machine::IO::TIMER0_CTRL + offset, value);
}
CPU::Reg16 ARMv4TDMI_TSC::ctrl() {
	return CPU::in16(Machine::IO::TIMER0_CTRL + offset);
}

void ARMv4TDMI_TSC::sctrl(CPU::Reg16 value) {
	CPU::out16(Machine::IO::TIMER0_SCTRL + offset, value);
}
CPU::Reg16 ARMv4TDMI_TSC::sctrl() {
	return CPU::in16(Machine::IO::TIMER0_SCTRL + offset);
}

void ARMv4TDMI_TSC::comp1(CPU::Reg16 value) {
	CPU::out16(Machine::IO::TIMER0_COMP1 + offset, value);
}
CPU::Reg16 ARMv4TDMI_TSC::comp1() {
	return CPU::in16(Machine::IO::TIMER0_COMP1 + offset);
}

void ARMv4TDMI_TSC::comp2(CPU::Reg16 value) {
	CPU::out16(Machine::IO::TIMER0_COMP2 + offset, value);
}
CPU::Reg16 ARMv4TDMI_TSC::comp2() {
	return CPU::in16(Machine::IO::TIMER0_COMP2 + offset);
}

void ARMv4TDMI_TSC::capt(CPU::Reg16 value) {
	CPU::out16(Machine::IO::TIMER0_CAPT + offset, value);
}
CPU::Reg16 ARMv4TDMI_TSC::capt() {
	return CPU::in16(Machine::IO::TIMER0_CAPT + offset);
}

void ARMv4TDMI_TSC::load(CPU::Reg16 value) {
	CPU::out16(Machine::IO::TIMER0_LOAD + offset, value);
}
CPU::Reg16 ARMv4TDMI_TSC::load() {
	return CPU::in16(Machine::IO::TIMER0_LOAD + offset);
}

void ARMv4TDMI_TSC::hold(CPU::Reg16 value) {
	CPU::out16(Machine::IO::TIMER0_HOLD + offset, value);
}
CPU::Reg16 ARMv4TDMI_TSC::hold() {
	return CPU::in16(Machine::IO::TIMER0_HOLD + offset);
}

void ARMv4TDMI_TSC::cntr(CPU::Reg16 value) {
	CPU::out16(Machine::IO::TIMER0_CNTR + offset, value);
}
CPU::Reg16 ARMv4TDMI_TSC::cntr() {
	return CPU::in16(Machine::IO::TIMER0_CNTR + offset);
}

void ARMv4TDMI_TSC::cmpld1(CPU::Reg16 value) {
	CPU::out16(Machine::IO::TIMER0_CMPLD1 + offset, value);
}
CPU::Reg16 ARMv4TDMI_TSC::cmpld1() {
	return CPU::in16(Machine::IO::TIMER0_CMPLD1 + offset);
}

void ARMv4TDMI_TSC::cmpld2(CPU::Reg16 value) {
	CPU::out16(Machine::IO::TIMER0_CMPLD2 + offset, value);
}
CPU::Reg16 ARMv4TDMI_TSC::cmpld2() {
	return CPU::in16(Machine::IO::TIMER0_CMPLD2 + offset);
}

void ARMv4TDMI_TSC::csctrl(CPU::Reg16 value) {
	CPU::out16(Machine::IO::TIMER0_CSCTRL + offset, value);
}
CPU::Reg16 ARMv4TDMI_TSC::csctrl() {
	return CPU::in16(Machine::IO::TIMER0_CSCTRL + offset);
}

void ARMv4TDMI_TSC::enbl(CPU::Reg16 value) {
	CPU::out16(Machine::IO::TIMER0_ENBL, value);
}
CPU::Reg16 ARMv4TDMI_TSC::enbl() {
	return CPU::in16(Machine::IO::TIMER0_ENBL);
}

__END_SYS
