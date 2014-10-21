// EPOS eMote3 Memory Map

#ifndef __emote3_memory_map_h
#define __emote3_memory_map_h

#include <system/memory_map.h>

__BEGIN_SYS

template <>
struct Memory_Map<eMote3>
{
    // Physical Memory
    enum {
        MEM_BASE        = Traits<eMote3>::MEM_BASE,
        MEM_TOP         = Traits<eMote3>::MEM_TOP
    };

    // Logical Address Space
    enum {
        APP_LOW         = Traits<eMote3>::APP_LOW,
        APP_CODE        = Traits<eMote3>::APP_CODE,
        APP_DATA        = Traits<eMote3>::APP_DATA,
        APP_HIGH        = Traits<eMote3>::APP_HIGH,

        PHY_MEM         = Traits<eMote3>::PHY_MEM,
        IO              = Traits<eMote3>::IO_BASE,

        SYS             = Traits<eMote3>::SYS,
        SYS_INFO        = unsigned(-1),                 // Not used during boot. Dynamically built during initialization.
        SYS_CODE        = Traits<eMote3>::SYS_CODE,
        SYS_DATA        = Traits<eMote3>::SYS_DATA,
        SYS_HEAP        = SYS_DATA,                     // Not used (because multiheap can only be enable with an MMU)
        SYS_STACK       = MEM_TOP + 1 - Traits<eMote3>::STACK_SIZE      // This stack is used before main(). The stack pointer is initialized at crt0.S
    };
};

/*
template <>
struct IO_Map<eMote3>
{
    enum {
        ITC_BASE                = 0x80020000,
        ITC_NIPEND              = ITC_BASE + 0x38,
    };
};
*/

__END_SYS

#endif
