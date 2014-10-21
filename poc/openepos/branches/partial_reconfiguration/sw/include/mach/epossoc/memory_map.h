// EPOS Memory Map for the EPOSSOC

#ifndef __epossoc_memory_map_h
#define __epossoc_memory_map_h

#include <system/memory_map.h>

__BEGIN_SYS

template <>
struct Memory_Map<EPOSSOC>
{
    enum Memory {
        MEM_BASE =              0x10000010,
        MEM_SIZE =              (1 * 64 * 1024) - 16,
    };

    enum Map {
        BASE =          0x10000010,
        TOP =           0x10010000,
        APP_LO =        0x10000010,
        APP_CODE =      0x10000010,
        APP_DATA =      0x10007100,
        APP_HI =        0x10008000,
        PHY_MEM =       0x10000004,
        IO_MEM =        0x20000000,
        INT_VEC =       0x0000003C,
        SYS_INFO =      0x1FFFF000,
        SYS =           0x10008000,
        SYS_CODE =      0x10008000,
        SYS_DATA =      0x1000A000,
        SYS_STACK =     0x1FFFE000
    };
};

__END_SYS

#endif
