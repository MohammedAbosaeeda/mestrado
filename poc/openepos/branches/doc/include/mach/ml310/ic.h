/*! @file
 *  @brief EPOS Interrupt Controller Mediator Declarations
 *
 *  CVS Log for this file:
 *  \verbinclude include/mach/ml310/ic_h.log
 */
#ifndef __ml310_ic_h
#define __ml310_ic_h

#include <cpu.h>
#include <ic.h>

__BEGIN_SYS

class ML310_IC: public IC_Common
{
private:
    typedef CPU::Reg32 Mask;

public:
    enum {
        ISR = Traits<ML310_IC>::BASE_ADDRESS + 0,
        IPR = Traits<ML310_IC>::BASE_ADDRESS + 4,
        IER = Traits<ML310_IC>::BASE_ADDRESS + 8,
        IAR = Traits<ML310_IC>::BASE_ADDRESS + 12,
        SIE = Traits<ML310_IC>::BASE_ADDRESS + 16,
        CIE = Traits<ML310_IC>::BASE_ADDRESS + 20,
        IVR = Traits<ML310_IC>::BASE_ADDRESS + 24,
        MER = Traits<ML310_IC>::BASE_ADDRESS + 28
    };

    /// ML310 Interrupts
    enum {
        //Processor exceptions....
        INT_MACHINE         = 0, ///< Machine Exception
        INT_DEBUG           = 1, ///< Debug Exception
        INT_WATCHDOG        = 2, ///< Watchdog Exception
        INT_INSTTBLMISS     = 3, ///< Instruction TLB Miss Exception
        INT_INSTSTORE       = 4, ///< Instruction Store Exception
        INT_PROGRAM         = 5, ///< Program Exception
        INT_SYSCALL         = 6, ///< Syscall Exception
        INT_DATATBLMISS     = 7, ///< Data TLB Miss Exception
        INT_DATASTORE       = 8, ///< Data Store Exception
        INT_ALIGNMENT       = 9, ///< Alignment Exception

        //Irqs ...
        INT_FITIMER         = 10, ///< Fixed Interval Timer
        INT_PITIMER         = 11, ///< Programmable Interval Timer

        //IntC Irqs
        INT_PCI_INT_OR      = 12, ///< PCI Interrupt
        INT_OPB_PCI_IRPT    = 13, ///< OPB_PCI Interrupt (unused)
        INT_SPI_INTR        = 14, ///< SPI Interrupt (unused)
        INT_IIC_TEMP_CRIT   = 15, ///< IIC BUS Interrupt (unused)
        INT_IIC_IRQ         = 16, ///< IIC BUS Interrupt (unused)
        INT_IIC_INTR        = 17, ///< IIC BUS Interrupt (unused)
        INT_SYSACE_INTR     = 18, ///< SysAce Component Interrupt (unused)
        INT_UART_INTR       = 19, ///< UART Interrupt

    };

    // IRQs - Review!
    static const unsigned int IRQS = 32;
    enum {
        IRQ_TIMER = INT_PITIMER,
    };

public:
    ML310_IC() {}

    static void enable(IRQ irq) {
        db<ML310_IC>(TRC) << "ML310_IC::enable(irq=" << irq << ")\n";
        if(irq > 11 && irq < 20) {
          irq -= 12;
          sie((1 << irq));
        } else {//PCI Interrupts
          //kout << "External Interrupt !!????\n";
        }
    }

    static void disable() {
        db<ML310_IC>(TRC) << "Disabling all IC interrupts!\n";
        cie(0xFFFFFFFF);
    }

    static void disable(IRQ irq) {
        db<ML310_IC>(TRC) << "ML310_IC::disable(irq=" << irq << ")\n";
        if(irq > 11 && irq < 20) {
          irq -= 12;
          cie(1 << irq);
        } else { //PCI Interrupts
          //kout << "External Interrupt !!????\n";
        }
    }

    static void init();

private:
    static void sie(const Mask & mask) {
        *((volatile Mask *)SIE) = mask;
    }
    static void cie(const Mask & mask) {
        *((volatile Mask *)CIE) = mask;
    }

};

__END_SYS

#endif
