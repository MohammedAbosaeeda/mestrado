// EPOS PC Memory Map

#ifndef __pc_memory_map_h
#define __pc_memory_map_h

#include <system/memory_map.h>

__BEGIN_SYS

template <>
struct Memory_Map<PC>
{
    enum {
        MEM_BASE =	0,
        MEM_SIZE =	64 * 1024 * 1024
    };

    enum {
        BASE =		0x00000000,
        TOP =		MEM_SIZE,
        APP_LO =	0x00000000,
        APP_CODE =	0x00000000,
        APP_DATA =	0x00400000,
        APP_HI =	0x0fffffff,
        PHY_MEM =	0x10000000,
        IO_MEM =	0x20000000,
        APIC_MEM =	0x2f400000,
        SYS =		0xff400000,
        IDT =		SYS + 0x00000000,
        GDT =		SYS + 0x00001000,
        SYS_PT =	SYS + 0x00002000,
        SYS_PD =	SYS + 0x00003000,
        SYS_INFO =	SYS + 0x00004000,
        SYS_CODE =	SYS + 0x00300000,	//0xFF700000
        SYS_DATA =	SYS + 0x00340000,
        SYS_STACK =	SYS + 0x003c0000,	//0xFF7C0000
        SYS_HEAP =      SYS + 0x00400000
    };
};

__END_SYS

#endif
