// OpenEPOS EMote2ARM_Timer Mediator Implementation

#include <timer.h>

__BEGIN_SYS

EMote2ARM_Timer::Handler* EMote2ARM_Timer::handlers[4];

__END_SYS

__USING_SYS

void EMote2ARM_Timer::handler_wrapper()
{
    db<EMote2ARM_IC>(TRC) << "EMote2ARM_Timer::handler_wrapper\n";
    Reg16 r;
    if((r = CPU::in16(IO::TIMER0_CSCTRL)) & 0x0010){
        CPU::out16(IO::TIMER0_CSCTRL, r & ~0x0010);
        handlers[TIMER0]();
        return;
    }
    if((r = CPU::in16(IO::TIMER1_CSCTRL)) & 0x0010){
        CPU::out16(IO::TIMER1_CSCTRL, r & ~0x0010);
        handlers[TIMER1]();
        return;
    }
    if((r = CPU::in16(IO::TIMER2_CSCTRL)) & 0x0010){
        CPU::out16(IO::TIMER2_CSCTRL, r & ~0x0010);
        handlers[TIMER2]();
        return;
    }
    if((r = CPU::in16(IO::TIMER3_CSCTRL)) & 0x0010){
        CPU::out16(IO::TIMER3_CSCTRL, r & ~0x0010);
        handlers[TIMER3]();
        return;
    }
}
