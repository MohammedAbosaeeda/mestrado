// OpenEPOS EMote2ARM_IC Mediator Implementation

#include <ic.h>
#include <machine.h>
#include <system/memory_map.h>

__BEGIN_SYS

EMote2ARM_IC::Interrupt_Handler EMote2ARM_IC::vector[12];

void EMote2ARM_IC::int_handler() {
    CPU::Reg16 pending = CPU::in16(IO_Map<Machine>::ITC_NIPEND);
    
    db<EMote2ARM_IC> (TRC) << "EMote2ARM_IC::int_handler(" << (int)pending << ")\n";

    if((1 << IRQ_TIMER) & pending)
    {
        CPU::Reg16 r;
        if((Traits<TSC>::enabled) && ((r = CPU::in16(IO::TIMER3_CSCTRL)) & 0x0010)) {
            CPU::out16(IO::TIMER3_CSCTRL, r & ~0x0010);
            db<EMote2ARM_IC> (INF) << "Interrupt! IRQ_TSC\n";
            vector[IRQ_TSC]();
            return;
        }
        db<EMote2ARM_IC> (INF) << "Interrupt! IRQ_TIMER\n";
        vector[IRQ_TIMER]();
    } else if((1 << IRQ_UART1) & pending)
    {
        db<EMote2ARM_IC> (INF) << "Interrupt! IRQ_UART1\n";
        vector[IRQ_UART1]();
    } else if((1 << IRQ_UART2) & pending)
    {
        db<EMote2ARM_IC> (INF) << "Interrupt! IRQ_UART2\n";
        vector[IRQ_UART2]();
    } else if((1 << IRQ_MACA) & pending)
    {
        db<EMote2ARM_IC> (INF) << "Interrupt! IRQ_MACA\n";
        vector[IRQ_MACA]();
    } else if((1 << IRQ_CRM) & pending)
    {
        db<EMote2ARM_IC> (INF) << "Interrupt! IRQ_CRM\n";
        vector[IRQ_CRM]();
    } else
    {
        db<EMote2ARM_IC> (INF) << "Unexpected Interrupt! => " << (unsigned int)pending << "\n";
//        Machine::panic();
    }
}

__END_SYS
