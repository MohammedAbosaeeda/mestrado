// EPOS-- MIPS Interrupt Controller Mediator Declarations

#ifndef __softmips_ic_h
#define __softmips_ic_h

#include <ic.h>

__BEGIN_SYS

class SoftMIPS_IC: public IC_Common
{
    
public:
    // IRQs
//    typedef CPU::IRQ	IRQ;

    // IRQs
    static const unsigned int IRQS = 8;
    enum {
		IRQ_UART_READ_AVAILABLE 	= 0,
		IRQ_UART_WRITE_AVAILABLE	= 1,
		IRQ_COUNTER_NOT				= 2,
		IRQ_COUNTER					= 3,
		IRQ_GPIO30_NOT				= 4,
		IRQ_GPIO31_NOT				= 5,
		IRQ_GPIO30					= 6,
		IRQ_GPIO31					= 7,
    };

public:
    SoftMIPS_IC() {}

	static void enable(unsigned int mask = CPU::IRQ_MASK_DEFAULT)
	{
		db<SoftMIPS_IC>(TRC) << "SoftMIPS_IC::enable()\n";
		/*ASMV(	".set noreorder		\n"
			"add	$26, $0,0x001	\n"
			"mtc0  $26, $12	\n"
			"nop			\n"
			".set reorder		\n"
			);*/
		CPU::regs<CPU::IRQ_MASK>(mask);
    }

    static void disable()
	{
		//unsigned int mask = CPU::IRQ_MASK_DISABLED;
		db<SoftMIPS_IC>(TRC) << "SoftMIPS_IC::disable()\n";
		/*ASMV(	".set noreorder		\n"
				"mtc0  $0, $12	\n"
				"nop			\n"
				".set reorder		\n"
			);*/
		CPU::regs<CPU::IRQ_MASK>(CPU::IRQ_MASK_DISABLED);
	}

    static unsigned int pending()
	{
		return (CPU::regs<CPU::IRQ_MASK>() & CPU::regs<CPU::IRQ_STATUS>());
		//return ((CPU::flags<CPU::CP0_CAUSE>() & CPU::INT_MASK) >> CPU::INT_SHIFT);
	}

    static unsigned int servicing()
	{
		return (CPU::regs<CPU::IRQ_STATUS>()); // ??
    }

    static unsigned int enabled()
	{
		return (CPU::regs<CPU::IRQ_MASK>() != CPU::IRQ_MASK_DISABLED);
		//return (CPU::flags<CPU::CP0_SR>() & CPU::INT_MASK) >> CPU::INT_SHIFT;
    }

    static unsigned int disabled()
	{
		return ~enabled();
    }

    static int init(System_Info *si);
};

__END_SYS

#endif
