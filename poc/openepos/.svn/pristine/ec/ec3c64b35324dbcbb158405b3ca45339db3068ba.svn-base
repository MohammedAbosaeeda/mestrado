// EPOS-- LEON2 Memory Map

#ifndef __memory_map_leon2_h
#define __memory_map_leon2_h

__BEGIN_SYS

template <>
struct Memory_Map<LEON2>
{
    enum {
	MEM_BASE =	0x40000000,
	MEM_SIZE =	0x800000   //8388608
    };

    enum {
	BASE =		0x00000000,
	TOP =		0xffffffff,
	APP_LO =	0x40000000,
	APP_CODE =	0x40000000,
	APP_DATA =	0x40040000,
	APP_HI =	0x40779FF8,
	PHY_MEM =	0x00000000,
	IO_MEM =	0x80000000,
	SYS =		0x4077a000,
	INT_VEC =	SYS + 0x4000,
	SYS_PT =	SYS + 0x3000,
	SYS_PD =	SYS + 0x2000,
	SYS_INFO =	SYS + 0x5000,
	SYS_CODE =	SYS + 0x86000,
	SYS_DATA =	SYS + 0xc6000,
	SYS_STACK =	SYS + 0x0000,
	MACH1 =		SYS + 0x00000000,
 	MACH2 =		SYS + 0x90000000,
	MACH3 =		SYS + 0xb0000000
    };
};

__END_SYS

#endif
