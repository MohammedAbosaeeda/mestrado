// EPOS-- SoftMIPS Mediator Declarations

#ifndef __softmips_h
#define __softmips_h

#include <system/config.h>
#include <cpu.h>
#include <mmu.h>
#include <tsc.h>
#include <machine.h>

__BEGIN_SYS

#define MASK_SHIFT(v,m,s)	((v & m) >> s)

class SoftMIPS: public Machine_Common
{
private:
    static const unsigned int LAST_HARD_INT = 4;
    static const unsigned int INT_VECTOR_SIZE = 32;

public:
    // Interrupts
    /*enum {
		INT_BASE        = 0,
		//INT_TIMER       = INT_BASE + 7,
	};*/

    typedef void (int_handler)(unsigned int);

public:
    SoftMIPS() {}

    static int_handler * int_vector(unsigned int i) {
		return (i < INT_VECTOR_SIZE) ? _int_vector[i] : 0;
    }
    static void int_vector(unsigned int i, int_handler * h) {
		db<PC>(INF) << "SoftMIPS::int_vector(int=" << i << ",h=" << h <<")\n";
		if(i < INT_VECTOR_SIZE) _int_vector[i] = h;
    }

    static void panic();
    static void reboot();
    static void poweroff();

    static int irq2int(int i) { return i; }
    static int int2irq(int i) { return i; }

    static int init(System_Info * si);
	static void int_wrap(unsigned int);
	static void int_dispatch(unsigned int);
private:
    
	
    //static void exc_dispatch();

    static void int_not(unsigned int interrupt);
    //static void exc_not(unsigned int interrupt);

private:
    static int_handler * _int_vector[INT_VECTOR_SIZE];
};

__END_SYS

#include "memory_map.h"
#include "ic.h"
#include "timer.h"
#include "rtc.h"
#include "uart.h"
#include "display.h"

#endif
