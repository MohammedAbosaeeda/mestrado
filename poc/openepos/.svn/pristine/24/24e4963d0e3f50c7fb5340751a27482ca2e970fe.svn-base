// EPOS-- SoftMIPS Memory Map


// from http://www.cs.hmc.edu/courses/2004/spring/cs182/sys161/mips.html

// The MIPS divides its address space into several regions that have hardwired
// properties. These are: 
//
// kseg2, TLB-mapped cacheable kernel space 
// kseg1, direct-mapped uncached kernel space 
// kseg0, direct-mapped cached kernel space 
// kuseg, TLB-mapped cacheable user space 
//
// Both direct-mapped segments map to the first 512 megabytes of the physical
// address space. The memory map thus looks like this: 
//
// +------------+---------------+---------------------------------------+
// + Address	| Segment	| Special properties			|
// +------------+---------------+---------------------------------------+
// | 0xffffffff	| kseg2		|					|
// | 0xc0000000	| 		|					| 
// +------------+---------------+---------------------------------------+
// | 0xbfffffff	| kseg1		|					|
// | 0xbfc00180	| 		| Exception address if BEV set.		|
// | 0xbfc00100	|		| UTLB exception address if BEV set.	|
// | 0xbfc00000	|		| IP after processor reset.		|
// | 0xa0000000 |		|					|  
// +------------+---------------+---------------------------------------+
// | 0x9fffffff | kseg0		|					|
// | 0x80000080 |            	| Exception address if BEV not set.	|
// | 0x80000000 |		| UTLB exception address if BEV not set.|
// +------------+---------------+---------------------------------------+
// | 0x7fffffff | kuseg		|					|
// | 0x00000000 |		|					|
// +------------+---------------+---------------------------------------+

#ifndef __memory_map_softmips_h
#define __memory_map_softmips_h

__BEGIN_SYS

template <>
struct Memory_Map<SoftMIPS>
{
    enum { // Physical memory
		MEM_BASE	= Traits<SoftMIPS>::MEMORY_BASE,
		MEM_SIZE	= Traits<SoftMIPS>::MEMORY_SIZE
    };

    enum {
		BASE		= 0x10000000, // 1MB de mem
		TOP			= 0x10100000,
		//APP_LO		= BASE,
		APP_LO		= 0x10000000,
		APP_HI		= 0x10080000,
		//APP_CODE	= BASE,
		APP_CODE	= 0x10000000,
		//APP_DATA	= BASE + 64 * 1024, //4k // * 1024,
		APP_DATA	= 0x10050000, //4k // * 1024,
		PHY_MEM		= 0x10000000,
		IO_MEM		= 0x20000000,
		SYS			= TOP - 64 * 1024,// * 1024,
		//SYS_INTR	= SYS + 0 * 1024,
		SYS_INTR	= 0x0000003c,
		SYS_INFO	= 0x10090000,
		SYS_CODE	= SYS + 8 * 1024,
		SYS_DATA	= SYS + 32 * 1024,
		SYS_STACK	= TOP - Traits<SoftMIPS>::SYSTEM_STACK_SIZE,
		MACH1		= TOP,
		MACH2		= TOP,
		MACH3		= TOP
    };
};

__END_SYS

#endif
