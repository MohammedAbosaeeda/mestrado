// EPOS-- LEON2 Boot

#include <cpu.h>
#include <utility/string.h>

ASM(".section \".init\"							\n"
     ".align 4									\n"
     ".global _trap_table	 ! Firstly we set a minimal trap table		\n"
"_trap_table:									\n"
     "	wr %g0, 0xfe0, %psr;				 ! 00 reset trap	\n"
     "	wr %g0, %wim; ba (__reset); nop 		 ! 00 reset trap 	\n"
     "	ta 0; nop; nop; nop 	 			 ! 01 inst_access_exc	\n"
     "  ta 0; nop; nop; nop      			 ! 02 illegal_inst	\n"
     " 	ta 0; nop; nop; nop    	 			 ! 03 priveleged_inst	\n"
     " 	ta 0; nop; nop; nop 	 			 ! 04 fp_disabled	\n"
     "  rd %psr, %l0; sethi %hi(_Z17__window_overflowv),%l4  ! 05 win_overflow  \n"
     "  jmp %l4+%lo(_Z17__window_overflowv); nop             ! 05 win_overflow  \n"
     "	rd %psr, %l0; sethi %hi(_Z18__window_underflowv),%l4 ! 06 win_underflow	\n"
     "  jmp %l4+%lo(_Z18__window_underflowv); nop	     ! 06 win_underflow ");


__USING_SYS

// System_Info Imports
typedef Memory_Map<LEON2> MM;

// Prototypes
extern "C" { void __reset(); }
extern "C" { int * __bss_start; }
extern "C" { static int * _end; }
void __setup_bm();
void __clear_bss();
void __window_overflow();
void __window_underflow();


//========================================================================
// reset                                                                
//                                                                      
// Desc: 
// 
//------------------------------------------------------------------------

void __reset() {

ASM( "  flush                    ! Flush Inst. Memory				\n"
     "  wr 	%g0, 0xfe0, %psr ! Enabling TRAPS, PIL 1111, CWP=0, FLGs (0000)	\n"
     "  wr 	%g0, 0x002, %wim ! Setting WIM to window 1			\n"
     "	set     _trap_table, %g7 ! Set trap table base				\n"
     "	wr     	%g7, %tbr							\n"
     "  nop; nop; nop								\n"	

     "  set	0x80000000, %g5  !set mcfg2 to 0x00001020			\n"
     "  set 	0x00001020, %g4							\n"
     "  st	%g4, [%g5 + 4]  						\n"

     "  sub     %g7, 0x8, %i7    ! Return address if necessary (reset)          \n"
     "  mov     %i7, %o7                                                        \n"
     "  set     0x4077b000, %g6  ! Set a temporary stack (512K from trap_tbl)   \n"
     "  mov     %g6, %fp                                                        \n"
     "  sub     %fp, 128, %sp                                                   \n"

     "   call    _Z10__setup_bmv, 0						\n"
     "   nop									\n"

     "   call     _Z9clear_bssv							\n"
     "   nop									\n"

     "	clr     %g1              ! Clear global regs.				\n"
     "  clr     %g2								\n"
     "  clr     %g3								\n"
     "  clr     %g4								\n"
     "  clr     %g5								\n"
     "  clr     %g6								\n"
     "  clr     %g7								\n"
     "  mov	%g0, %y		 ! Clear Y register				\n"
     "	ba __setup_entry							\n"
     "  nop; nop; nop								\n"
     " 	ta 	0		 ! Halt if SETUP returns somehow ...		\n"
     "	nop									");
}


void __setup_bm() {

    System_Info * si = (System_Info *) MM::SYS_INFO;

    memset((void *)(si), 0, sizeof(System_Info));    

    si->bm.mem_base   = 0x40000000;
    si->bm.mem_size   = 8388608;
    si->bm.img_size   = unsigned(-1);
    si->bm.setup_off  = unsigned(-1); 
    si->bm.system_off = unsigned(-1);
    si->bm.loader_off = unsigned(-1);
    si->bm.app_off    = unsigned(-1);

}


void clear_bss() {    
    memset(((void*) &__bss_start), 0, (((int) &_end) - ((int) &__bss_start)));
}


void __window_overflow() {

ASM( "  rd  %wim, %l3		! Calculate next WIM				");
ASM( "  add %%g0, %0, %%l7" : : "i"(Traits<SPARC32>::NWINDOWS-1)		 );
ASM( "  sll %l3, %l7, %l4							\n"
     "  srl %l3, 1, %l3								\n"
     "  or  %l3, %l4, %l3							\n"

     "  mov %g0, %wim		! Disable WIM traps				\n"
     "  nop; nop; nop								\n"
     "  save			! Get into window to be saved			\n"

     "  std %l0, [%sp +  0]							\n"
     "  std %l2, [%sp +  8]							\n"
     "  std %l4, [%sp + 16]							\n"
     "  std %l6, [%sp + 24]							\n"
     "  std %i0, [%sp + 32]							\n"
     "  std %i2, [%sp + 40]							\n"
     "  std %i4, [%sp + 48]							\n"
     "  std %i6, [%sp + 56]							\n"

     "  restore                 ! Go back to trap window			\n"
     "  mov %l3, %wim		! Set new value of window 			\n"
     "  nop; nop; nop								\n"
     "  jmp  %l1                ! Re-execute save				\n"
     "  rett %l2								\n"
     "  nop; nop; nop								");
}


void __window_underflow() {

ASM( "	mov %wim,%l3		 ! Calculate next WIM				");
ASM( "  add %%g0, %0, %%l7" : : "i"(Traits<SPARC32>::NWINDOWS-1)             	 );
ASM( "  srl %l3,%l7,%l4								\n"
     "  sll %l3,1,%l3								\n"	
     "  or  %l3,%l4,%l3								\n"
     "  and %l3,0xff,%l3							\n"

     "  mov %g0,%wim		 ! Disable WIM trap				\n"
     "  nop; nop; nop								\n"
     "  restore			 ! Two restores to get into the			\n"
     "  restore                  ! window to restore				\n"

     "  ldd [%sp +  0], %l0							\n"
     "  ldd [%sp +  8], %l2							\n"
     "  ldd [%sp + 16], %l4							\n"
     "  ldd [%sp + 24], %l6							\n"
     "  ldd [%sp + 32], %i0							\n"
     "  ldd [%sp + 40], %i2							\n"	
     "  ldd [%sp + 48], %i4							\n"
     "  ldd [%sp + 56], %i6							\n"

     "  save			! Get back to the trap window			\n"	
     "  save									\n"
     "  mov %l3,%wim		! Set new value of window			\n"
     "  nop; nop; nop								\n"

     "	jmp %l1			! Re-execute restore				\n"
     "  rett %l2								\n"
     "  nop; nop; nop								");
}
