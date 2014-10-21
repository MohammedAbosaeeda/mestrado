// TestMIPS_SETUP
//
// This work is licensed under the Creative Commons 
// Attribution-NonCommercial-NoDerivs License. To view a copy of this license, 
// visit http://creativecommons.org/licenses/by-nc-nd/2.0/ or send a letter to 
// Creative Commons, 559 Nathan Abbott Way, Stanford, California 94305, USA.

// SETUP is responsible for bringing the machine into a usable state. In
// MIPS, it just setups some stuff, load drivers in memory, and copy exception
// handling functions to SYS_INTR (we cannot setup registers to point somewhere
// else, unfortunatelly).

#include <utility/elf.h>
#include <utility/string.h>
#include <utility/ostream.h>
#include <utility/debug.h>
#include <mach/testmips/testmips.h>

__USING_SYS

// System_Info Imports
typedef System_Info::Physical_Memory_Map PMM;
typedef System_Info::Logical_Memory_Map LMM;
typedef System_Info::Boot_Map BM;
typedef Memory_Map<TestMIPS> MM;
typedef Traits<TestMIPS> TR;

typedef MMU::Page Page;

typedef CPU::Log_Addr Log_Addr;
typedef CPU::Phy_Addr Phy_Addr;

// Prototypes
extern "C" { void _start(); }

int		main(char *, unsigned int, char *);

/* 
__BEGIN_SYS

OStream kout, kerr;
bool has_system;

__END_SYS
*/

//========================================================================
// _start	          		                                
//                                                                      
//	 "_start" MUST BE TESTMIPS_SETUP's entry point, so, if your compiler
//	 doesn't assume "_start" to be the entry point (GCC does), you
//	 somehow have to arrange for this.			
//	 THIS FUNCTION MUST BE RELOCATABLE, because it won't run at the
//	 address it has been compiled for.
//------------------------------------------------------------------------

/*
 * Don't know how this works on IA32, but memory position is
 * obtained considering .text loaded at final entry
 * point (SETUP_ADDR). And there's nothing there, yet!
 *
 * We have a simple, dirty and quick solution: don't make any
 * function calls until we have setup at right location. This
 * wastes some space and duplicates some code, but the cost is
 * almost insignificant.
 */

void _start()
{		
    // The boot strap loaded the boot image.
    char * bi = reinterpret_cast<char *>(TR::BOOT_IMG_START);
    
    // Get the System_Info  (first thing in the boot image)
    System_Info * si = reinterpret_cast<System_Info *>(bi);

    // Get ELF structure of SETUP.
    ELF *elf = reinterpret_cast<ELF *>(&bi[si->bm.setup_off]);

	// Get SETUP entry point.
	char * entry = reinterpret_cast<char *>(elf->entry());

//	  TODO: Remake this check so it tests any possibility of overwriting.
//    if (addr <= &bi[si->bm.img_size] && addr +size) goto panic;

	/*** Borrowed from elf.cc, as we cannot call it yet. ***/

	/* Segments (code and data) */
	Elf32_Phdr * segs = (Elf32_Phdr *)(((char *) elf) + ((Elf32_Ehdr *)elf)->e_phoff);

	// Setup loading address and size.
	char * addr = 0, * data = 0;
	int    size = 0;

	// Check SETUP integrity.
	if (!elf->valid() || elf->segments() < 2)
		goto panic;

    // Test if we can access the address for which SETUP has been compiled
    *entry = 'G';
	if (*entry != 'G')
		goto panic;

	// Check segments type.
   	if (segs[0].p_type != PT_LOAD || segs[1].p_type != PT_LOAD)
		goto panic;

	// memcpy and memset hand-made.
	for (int n = 0; n < 2; n++)
	{
		char * src = ((char *) elf) + segs[n].p_offset;
		char * dst = (char *)elf->segment_address(n);
		int    siz = segs[n].p_filesz;

		if (n == 0) { addr = dst; size = siz; };
		if (n == 1) { data = dst; };

		for (int i = 0; i < siz; i++)
			dst[i] = src[i];

		dst = (char *)dst + segs[n].p_filesz;
		siz = segs[n].p_memsz - segs[n].p_filesz;

		for (int i = 0; i < siz; i++)
			dst[i] = 0;
	}

	/***********************************************************
	 *	NOW WE CAN panic() (and much more)! /o/ \o\ \o/
	 *
	 *	The funny thing it that we don't need panic() anymore...
	 ***********************************************************/

    // Setup a stack for SETUP.
    // Be carefull: we'll lost our stack, so everything must be in regs!
    ASM("add $29, $0, %0" : : "r" (MM::SYS_STACK));

	// Adjust global pointer for SETUP.
	ASM("add $28, $0, %0" : : "r" (data));

    // Pass SETUP its loading address
    ASM("add $4, $0, %0" : : "r" (addr));

    // Pass SETUP its size
    ASM("add $5, $0, %0" : : "r" (size));

    // Pass the boot image to SETUP
    ASM("add $6, $0, %0" : : "r" (MM::SYS_INFO));

    // Call main() (the assembly is necessary because the compiler generates
    // relative calls and we need an absolute)
    ASM("jr %0" : : "r" (&main));

	// Our own panic().
	panic:

	unsigned char *video, *halt;

	video = reinterpret_cast<unsigned char *>(0xb0000000);
	halt = reinterpret_cast<unsigned char *>(0xb0000010);

	*video = '\n';	*video = '\n';
	*video = 'P';	*video = 'A';
	*video = 'N';	*video = 'I';
	*video = 'C';	*video = '!';
	*video = '\n';	*video = '\n';

	*halt = 0;
};
