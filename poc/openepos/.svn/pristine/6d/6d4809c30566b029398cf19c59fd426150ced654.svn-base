// EPOS-- LEON2 Mediator Declarations

#ifndef __leon2_h
#define __leon2_h

#include <utility/list.h>
#include <cpu.h>
#include <mmu.h>
#include <tsc.h>
#include <machine.h>

__BEGIN_SYS

class LEON2: public Machine_Common
{
private:
    static const unsigned int HARD_INT = Traits<LEON2>::HARDWARE_INT_OFFSET;
////    static const unsigned int SYSCALL_INT = Traits<LEON2>::SYSCALL_INT;
    static const unsigned int INT_VECTOR_SIZE = 64;
    
    typedef SPARC32::Reg32 Reg32;
    typedef SPARC32::Log_Addr Log_Addr;

    typedef SPARC32::Context  Context;

public:
    typedef void (IntHandler)(unsigned int);


    // Interrupts
    enum {
        INT_BASE        = Traits<LEON2>::INT_BASE,
        INT_BUSERR      = INT_BASE + Traits<LEON2>::INT_BUSERR,    //0
        INT_UART1       = INT_BASE + Traits<LEON2>::INT_UART1,     //1 
        INT_UART0       = INT_BASE + Traits<LEON2>::INT_UART0,     //2
        INT_PIO0        = INT_BASE + Traits<LEON2>::INT_PIO0,      //3
        INT_PIO1        = INT_BASE + Traits<LEON2>::INT_PIO1,      //4
        INT_PIO2        = INT_BASE + Traits<LEON2>::INT_PIO2,      //5
        INT_PIO3        = INT_BASE + Traits<LEON2>::INT_PIO3,      //6
        INT_TIMER0      = INT_BASE + Traits<LEON2>::INT_TIMER0,    //7
        INT_TIMER1      = INT_BASE + Traits<LEON2>::INT_TIMER1,    //8
        INT_2NDIC       = INT_BASE + Traits<LEON2>::INT_2NDIC,     //9
        INT_DSU         = INT_BASE + Traits<LEON2>::INT_DSU,       //10
        INT_OPENETH     = INT_BASE + Traits<LEON2>::INT_OPENETH,   //11 
        INT_LAN91C911   = INT_BASE + Traits<LEON2>::INT_LAN91C911, //11
        INT_AC97        = INT_BASE + Traits<LEON2>::INT_AC97,      //12 
        INT_OCIDEC      = INT_BASE + Traits<LEON2>::INT_OCIDEC,    //12
        INT_PCIBRIDGE   = INT_BASE + Traits<LEON2>::INT_PCIBRIDGE, //13
        INT_NOTMSK      = INT_BASE + Traits<LEON2>::INT_NOTMSK,    //14
    };

    enum {
        INT_UART        = INT_UART0,
        INT_TIMER       = INT_TIMER0,
        INT_PCM_DECODER = INT_AC97,
    };


public:
    LEON2() {}
  
    static IntHandler * int_vector(unsigned int i) {
        SPARC32::TTBL_Entry * ttbl = (SPARC32::TTBL_Entry *) Memory_Map<LEON2>::INT_VEC;
        if((unsigned int)i < SPARC32::TTBL_ENTRIES)
            return ((IntHandler *) ttbl[i].handler());
    }
    static void int_vector(unsigned int i, IntHandler * h) {
        SPARC32::TTBL_Entry * ttbl = (SPARC32::TTBL_Entry *) Memory_Map<LEON2>::INT_VEC;
        if((unsigned int)i < SPARC32::TTBL_ENTRIES)
            ttbl[i] = SPARC32::TTBL_Entry((SPARC32::Reg32) h);
    }

/* ------------------------------------
    template<typename Dev>
    static Dev * seize(const Type_Id & type, unsigned int unit) { 
	return reinterpret_cast<Dev *>(LEON2_Device::seize(type, unit));
    }

    static void release(const Type_Id & type, unsigned int unit) { 
	LEON2_Device::release(type, unit); 
    }
------------------------------------ */

    static void panic();
    static void reboot();
    static void poweroff();

    static int irq2int(int i) { return i + HARD_INT; }
    static int int2irq(int i) { return i - HARD_INT; }
    
    static int init(System_Info * si) { return 0; }

private:
    static void int_dispatch();

    static void int_not(unsigned int interrupt);
    static void exc_not(unsigned int interrupt,
			Reg32 error, Reg32 eip, Reg32 cs, Reg32 eflags);
    static void exc_pf (unsigned int interrupt,
			Reg32 error, Reg32 eip, Reg32 cs, Reg32 eflags);
    static void exc_gpf(unsigned int interrupt,
			Reg32 error, Reg32 eip, Reg32 cs, Reg32 eflags);
    static void exc_fpu(unsigned int interrupt,
			Reg32 error, Reg32 eip, Reg32 cs, Reg32 eflags);

private:
    static IntHandler * _int_vector[INT_VECTOR_SIZE];
};

__END_SYS

#include "ic.h"
#include "timer.h"
#include "rtc.h"
#include "uart.h"
#include "display.h"
#include "nic.h"

#endif
