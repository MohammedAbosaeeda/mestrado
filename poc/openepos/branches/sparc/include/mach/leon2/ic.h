// EPOS-- LEON2 Interrupt Controller Mediator Declarations

#ifndef __leon2_ic_h
#define __leon2_ic_h

#include <ic.h>

__BEGIN_SYS


class LEON2_IC: public IC_Common
{
private:
    static const unsigned int HARD_INT = Traits<LEON2>::HARDWARE_INT_OFFSET;

    typedef CPU::Reg32 Reg32;

public:
    // IC Registers
    enum {
      MASK_PRIO = Traits<LEON2_IC>::BASE_ADDRESS + 0x0,
      PENDING   = Traits<LEON2_IC>::BASE_ADDRESS + 0x4,
      FORCE     = Traits<LEON2_IC>::BASE_ADDRESS + 0x8,
      CLEAR     = Traits<LEON2_IC>::BASE_ADDRESS + 0xC,
    };

    // IRQs
    enum {
        IRQ_NONE        = 0x0000,
        IRQ1            = 0x0002,
        IRQ2            = 0x0004,
        IRQ3            = 0x0008,
        IRQ4            = 0x0010,
        IRQ5            = 0x0020,
        IRQ6            = 0x0040,
        IRQ7            = 0x0080,
        IRQ8            = 0x0100,
        IRQ9            = 0x0200,
        IRQ10           = 0x0400,
        IRQ11           = 0x0800,
        IRQ12           = 0x1000,
        IRQ13           = 0x2000,
        IRQ14           = 0x4000,
        IRQ15           = 0x8000,
        IRQ_ALL         = 0xffff
     };

     enum {
        IRQ_BUSERR      = IRQ1,
        IRQ_UART1       = IRQ2,
        IRQ_UART0       = IRQ3,
        IRQ_PIO0        = IRQ4,
        IRQ_PIO1        = IRQ5,
        IRQ_PIO2        = IRQ6,
        IRQ_PIO3        = IRQ7,
        IRQ_TIMER0      = IRQ8,
        IRQ_TIMER1      = IRQ9,
        IRQ_2NDIC       = IRQ10,
        IRQ_DSU         = IRQ11,
        IRQ_OPENETH     = IRQ12,
        IRQ_LAN91C911   = IRQ12,
        IRQ_AC97        = IRQ13,
        IRQ_OCIDEC      = IRQ13,
        IRQ_PCIBRIDGE   = IRQ14,
        IRQ_NOTMSK      = IRQ15
    };

    enum {
        IRQ_UART        = IRQ_UART0,
        IRQ_TIMER       = IRQ_TIMER0,
        IRQ_PCM_DECODER = IRQ_AC97
    };

public:
    LEON2_IC() {}

    static void enable(IRQ irq) { 
        mask(mask() | (1 << irq)); 
    }

    static void disable() { 
	mask(mask() & 0xffff0000);
    }

    static void disable(IRQ irq) { 
	mask(mask() & ~(1 << irq)); 
    }

    static int init(System_Info * si);

private:
    static Reg32 mask() {
        return *(reinterpret_cast<Reg32 *>(MASK_PRIO));
    }
    static void mask(Reg32 m) {
        *(reinterpret_cast<Reg32 *>(MASK_PRIO)) = m;
    }


};

__END_SYS

#endif
