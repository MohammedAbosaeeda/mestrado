// EPOS-- PandaBoard Memory Map

#ifndef __memory_map_panda_h
#define __memory_map_panda_h

#include <system/memory_map.h>

__BEGIN_SYS

template <>
struct Memory_Map<PandaBoard>
{
    enum {
        //MEM_BASE =  0,
        //MEM_SIZE =  32 * 1024 * 1024
    };

    enum {
        //BASE =      0x82000000,
        TOP =       0x1fffffff,
		/*
        APP_LO =    0x82000000,
        APP_CODE =  0x82000000,
        APP_DATA =  0x83000000,
        APP_HI =    0x83EFFFFF,
		*/
        PHY_MEM =	0x20000000,
        //IO_MEM =    0x48000000,
        //APIC_MEM =  0x00000000,
        SYS =       0x00100000,
		/*
        IDT =       SYS + 0x00010000,
        GDT =       SYS + 0x00020000,
        SYS_PT =    SYS + 0x00030000,
        SYS_PD =    SYS + 0x00040000,
		*/
        SYS_INFO =  SYS + 0x00050000, //Used in machine_init.cc
		/*
        SYS_CODE =  SYS + 0x00060000,
        SYS_DATA =  SYS + 0x00070000,
        SYS_STACK = SYS + 0x00080000
		*/
    };
};

__END_SYS

#endif

