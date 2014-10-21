// EPOS eMote3 Mediators Configuration

#ifndef __emote3_config_h
#define __emote3_config_h

#include <system/meta.h>
#include __APPLICATION_TRAITS_H

#define __CPU_H         __HEADER_ARCH(cpu)
#define __TSC_H         __HEADER_ARCH(tsc)
#define __MMU_H         __HEADER_ARCH(mmu)

#define __MACH_H        __HEADER_MACH(machine)
#define __IC_H          __HEADER_MACH(ic)
#define __TIMER_H       __HEADER_MACH(timer)
#define __RTC_H         __HEADER_MACH(rtc)
#define __EEPROM_H      __HEADER_MACH(eeprom)
#define __UART_H        __HEADER_MACH(uart)
//#define __DISPLAY_H     __HEADER_MACH(display)
//#define __NIC_H         __HEADER_MACH(nic)
//#define __SCRATCHPAD_H  __HEADER_MACH(scratchpad)

__BEGIN_SYS

typedef ARMv7              CPU;
typedef ARMv7_MMU          MMU;
typedef ARMv7_TSC          TSC;

typedef eMote3             Machine;
typedef eMote3_IC          IC;
typedef eMote3_Timer       Timer;
typedef eMote3_RTC         RTC;
typedef eMote3_EEPROM      EEPROM;
typedef eMote3_UART        UART;
typedef IF<Traits<Serial_Display>::enabled, Serial_Display, eMote3_Display>::Result Display;
typedef eMote3_Scratchpad  Scratchpad;

__END_SYS

#endif
