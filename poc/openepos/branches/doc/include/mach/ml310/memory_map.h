/*! @file
 *  @brief EPOS Memory Map for the ML310
 *
 *  CVS Log for this file:
 *  \verbinclude include/mach/ml310/memory_map_h.log
 */
#ifndef __ml310_memory_map_h
#define __ml310_memory_map_h

#include <system/memory_map.h>

__BEGIN_SYS

/// Defines the memory map for the ML310 platform.
template <>
struct Memory_Map<ML310>
{
    enum Memory {
        MEM_BASE =              0, ///< Base address of platform physical memory
        MEM_SIZE =              64 * 1024 * 1024, ///< Size of the physical memory
    };

    enum Map {
        BASE =          0x00000000, ///< Base address
        TOP =           0xFFFFFFFF, ///< Top address
        APP_LO =        0x00000000, ///< Lowest address of Application Memory
        APP_CODE =      0x00000000, ///< Base address of Application Code
        APP_DATA =      0x00200000, ///< Base address of Application Data
        APP_HI =        0x03800000, ///< Higher address of Application Memory
        PHY_MEM =       0x00000000, ///< Base address of the physical memory (mapped)
        IO_MEM =        Traits<ML310_PCI>::MEM_SPACE_LIMIT,  ///< Base address of IO address space
        INT_VEC =       0x03ff0000, ///< Base address of the interrupt vector
        SYS_INFO =      0x03ff4000, ///< System Info base address
        SYS =           0x03ff5000, ///< System base address
        SYS_CODE =      0x03ff5000, ///< System code base address
        SYS_DATA =      0x03ff9000, ///< System data base address
        SYS_STACK =     0x03fff000  ///< System stack base address
    };
};

__END_SYS

#endif
