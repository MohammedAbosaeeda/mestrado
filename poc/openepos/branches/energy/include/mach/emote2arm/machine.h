// OpenEPOS EMote2ARM Machine Mediator Declarations

#ifndef __emote2arm_h
#define __emote2arm_h

#include <machine.h>
#include <cpu.h>
#include <mmu.h>
#include <tsc.h>
#include "memory_map.h"

__BEGIN_SYS

class EMote2ARM: public Machine_Common
{
public:
    typedef IO_Map<EMote2ARM> IO;
    typedef void (int_handler)(unsigned int);

public:
    EMote2ARM() {}

    static void smp_init(unsigned int n_cpus) {}
    static unsigned int n_cpus() { return 1; }
    static unsigned int cpu_id() { return 0; }
    static void smp_barrier(unsigned int n_cpus = 1) { }

    static int_handler * int_vector(unsigned int i) {
    	return 0;
    }
    static void int_vector(unsigned int i, int_handler * h) {
    }

    template<typename Dev>
    static Dev * seize(const Type_Id & type, unsigned int unit) {
    	return 0;
    }

    static void release(const Type_Id & type, unsigned int unit) {
    }

    static void panic() { 
		db<EMote2ARM>(ERR) << "PANIC!\n";
		CPU::int_disable();
		for(;;);
	}
    static void reboot() {
        CPU::halt();
    }
    static void poweroff();

    static int irq2int(int i) { return i; }
    static int int2irq(int i) { return i; }

    static void init();

    static unsigned int clock() { return Traits<Machine>::CLOCK; }

    static void check_flash_erase();
    static void unbrick();

private:

};

__END_SYS

#include "flash.h"
#include "info.h"
#include "uart.h"
#include "timer.h"
#include "adc.h"
#include "nic.h"
#include "spi.h"
#include "sensor.h"

#endif
