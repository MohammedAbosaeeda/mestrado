// EPOS eMote3 Mediator Declarations

#ifndef __emote3_h
#define __emote3_h

#include <utility/list.h>
#include <cpu.h>
#include <mmu.h>
#include <tsc.h>
#include <machine.h>
#include <rtc.h>
#include "info.h"
#include "memory_map.h"
#include "ic.h"

__BEGIN_SYS

class eMote3: private Machine_Common, private Cortex_M3
{
    friend class Init_System;

public:
    eMote3() {}

    static unsigned int cpu_id() { return 0; }
    static unsigned int n_cpus() { return 1; }

    static void panic();
    static void reboot() { 
        db<eMote3>(WRN) << "Machine::reboot()" << endl;
        scs(AIRCR) |=  SYSRESREQ ;
        for(;;); // TODO: the above is not working!
    }
    static void poweroff() { reboot(); }

    static void smp_barrier() {};
    static void smp_init(unsigned int) {};

private:
    static void init();
};

__END_SYS

#include "timer.h"
#include "rtc.h"
#include "uart.h"

#endif
