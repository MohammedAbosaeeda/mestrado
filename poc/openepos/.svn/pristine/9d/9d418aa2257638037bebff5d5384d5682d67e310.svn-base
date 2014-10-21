// EPOS SPARC32 Implementation
//
// Author: fauze
// Documentation: $EPOS/doc/cpu                 Date: 09 May 2005

#include <arch/sparc32/cpu.h>

__BEGIN_SYS

	ASM("	.file	 \"cpu.cc\" 							\n"
	    "	.section \".text\"	  						  	");

/**************************************************************************************************/

	ASM("	.align 4									\n"
	    "	.global _ZNV6System7SPARC327Context4saveEv					\n"
	    "	.type	_ZNV6System7SPARC327Context4saveEv,#function				\n"
	    "	.proc	020									\n"
"_ZNV6System7SPARC327Context4saveEv:								\n"

            "   st      %g7, [%o0 + 23 * 4] ! To have a place where we can put %psr             \n"
            "   rd      %psr, %g7           ! Reading %psr                                      \n"
            "   st      %g7, [%o0 + 16 * 4] ! Storing %psr                                      \n"
            "   or      %g7, 0xf00, %g7     ! Disabling interrupts                              \n"
            "   wr      %g7, %psr           ! Interrupts disabled                               \n"
            "   nop; nop; nop;                                                                  \n"

            "   std     %l0, [%o0 + 0  * 4] ! save L & I registers                              \n"
            "   std     %l2, [%o0 + 2  * 4]                                                     \n"
            "   std     %l4, [%o0 + 4  * 4]                                                     \n"
            "   std     %l6, [%o0 + 6  * 4]                                                     \n"

            "   std     %i0, [%o0 + 8  * 4]                                                     \n"
            "   std     %i2, [%o0 + 10 * 4]                                                     \n"
            "   std     %i4, [%o0 + 12 * 4]                                                     \n"
            "   std     %i6, [%o0 + 14 * 4]                                                     \n"

            "   st      %g1, [%o0 + 17 * 4] ! save G & O registers                              \n"
            "   std     %g2, [%o0 + 18 * 4]                                                     \n"
            "   std     %g4, [%o0 + 20 * 4]                                                     \n"
            "   st      %g6, [%o0 + 22 * 4] ! %g7 is already saved                              \n"

            "   std     %o0, [%o0 + 24 * 4]                                                     \n"
            "   std     %o2, [%o0 + 26 * 4]                                                     \n"
            "   std     %o4, [%o0 + 28 * 4]                                                     \n"
            "   std     %o6, [%o0 + 30 * 4]                                                     \n"

            "   mov     %o0, %g7            ! backup the reference to the current thread        \n"

            "   ! - Taking care of other windows - !                                            \n"

            "   rd %wim, %g6                                                                    \n"
	    "   wr %g0, %wim									\n"
	    "   nop; nop; nop									\n"
"1:                                                                                             \n"
            "   restore                     ! Get into the previous window                      \n"
            "   rd  %psr, %g5                                                                   \n"
            "   and %g5, 0x1f, %g5                                                              \n"
            "   srl %g6, %g5, %g4                                                               \n"

            "   cmp %g4,0x1                 ! Switch if no more windows to save                 \n"
            "   be 2f                                                                           \n"
	    "   nop										\n"

            "   std     %l0, [%sp + 0  * 4] ! save L & I registers                              \n"
            "   std     %l2, [%sp + 2  * 4]                                                     \n"
            "   std     %l4, [%sp + 4  * 4]                                                     \n"
            "   std     %l6, [%sp + 6  * 4]                                                     \n"

            "   std     %i0, [%sp + 8  * 4]                                                     \n"
            "   std     %i2, [%sp + 10 * 4]                                                     \n"
            "   std     %i4, [%sp + 12 * 4]                                                     \n"
            "   std     %i6, [%sp + 14 * 4]                                                     \n"

            "   ba 1b                                                                           \n"
	    "   nop										\n"

            "   ! --- backing to current context -- !                                           \n"
"2:												\n"
            "   mov     %g7, %sp            ! current sp (%sp = %o6)                            \n"

            "   ldd     [%sp + 20 * 4], %g4                                                     \n"
            "   ld      [%sp + 16 * 4], %g7 ! restore current CWP, ET and PIL  		        \n"
            "   wr      %g7, %psr                                                               \n"
	    "   wr	%g6, %wim								\n"
            "   nop; nop; nop                                                                   \n"

            "   ldd     [%sp + 22 * 4], %g6                                                     \n"
            "   retl										\n"
	    "   nop										\n"

".LLfe1:											\n"
	    "	.size	_ZNV6System7SPARC327Context4saveEv,.LLfe1-_ZNV6System7SPARC327Context4saveEv");

/**************************************************************************************************/

        ASM("   .align 4                                                        		\n"
            "   .global _ZNVK6System7SPARC327Context4loadEv                                    	\n"
            "   .type   _ZNVK6System7SPARC327Context4loadEv,#function                             \n"
            "   .proc   020                                                              	\n"     
"_ZNVK6System7SPARC327Context4loadEv:                                                             \n"

            "   rd      %psr, %g7           ! Reading %psr                                      \n"
            "   or      %g7, 0xf00, %g7     ! Disabling interrupts                              \n"
            "   wr      %g7, %psr           ! Interrupts disabled                               \n"
            "   nop; nop; nop;  								\n"

            "   mov     %o0, %g7            ! backup the reference to the next thread           \n"

            "   ! - Taking care of other windows - !                                            \n"

            "   rd %wim, %g6                                                                    \n"
            "   wr %g0, %wim                                                                    \n"
	    "   nop; nop; nop									\n"
"1:                                                                                             \n"
            "   restore                     ! Get into the previous window                      \n"
            "   rd  %psr, %g5                                                                   \n"
            "   and %g5, 0x1f, %g5                                                              \n"
            "   srl %g6, %g5, %g4                                                               \n"

            "   cmp %g4,0x1                 ! Switch if no more windows to save                 \n"
            "   be 2f                                                                           \n"
            "   nop                                                                             \n"

            "   std     %l0, [%sp + 0  * 4] ! save L & I registers                              \n"
            "   std     %l2, [%sp + 2  * 4]                                                     \n"
            "   std     %l4, [%sp + 4  * 4]                                                     \n"
            "   std     %l6, [%sp + 6  * 4]                                                     \n"

            "   std     %i0, [%sp + 8  * 4]                                                     \n"
            "   std     %i2, [%sp + 10 * 4]                                                     \n"
            "   std     %i4, [%sp + 12 * 4]                                                     \n"
            "   std     %i6, [%sp + 14 * 4]                                                     \n"

            "   ba 1b                                                                           \n"
            "   nop                                                                             \n"

            "   ! ------------ loading ----------- !                                            \n"		
"2:												\n"
            "   rd      %psr, %g6           ! Setting CWP to window 0 and                       \n"
            "   andn    %g6, 0x1f, %g6                                                          \n"
            "   wr      %g6, %psr                                                               \n"
            "   nop; nop; nop                                                                   \n"

 	    "   mov     %g7, %sp            ! target sp (%sp = %o6)                             \n"

            "   ldd     [%sp + 0  * 4], %l0 ! restore L & I registers                           \n"
            "   ldd     [%sp + 2  * 4], %l2                                                     \n"
            "   ldd     [%sp + 4  * 4], %l4                                                     \n"
            "   ldd     [%sp + 6  * 4], %l6                                                     \n"

            "   ldd     [%sp + 8  * 4], %i0                                                     \n"
            "   ldd     [%sp + 10 * 4], %i2                                                     \n"
            "   ldd     [%sp + 12 * 4], %i4                                                     \n"
            "   ldd     [%sp + 14 * 4], %i6                                                     \n"

            "   ld      [%sp + 17 * 4], %g1                                                     \n"
            "   ldd     [%sp + 18 * 4], %g2                                                     \n"
            "   ldd     [%sp + 20 * 4], %g4                                                     \n"
            "   ld      [%sp + 22 * 4], %g6                                                     \n"

            "   ldd     [%sp + 24 * 4], %o0                                                     \n"
            "   ldd     [%sp + 26 * 4], %o2                                                     \n"
            "   ldd     [%sp + 28 * 4], %o4                                                     \n"
            "   ld      [%sp + 31 * 4], %o7 ! %o6 (== %sp) is already restored                  \n"

            "   ld      [%sp + 16 * 4], %g7 ! Set new CWP (0) and old ET and PIL                \n"
            "   andn    %g7, 0x1f, %g7                                                          \n"
            "   wr      %g0,0x2, %wim       ! Setting WIM to window 1                           \n"	
            "   wr      %g7, %psr                                                               \n"
            "   nop; nop; nop                                                                   \n"

            "   ld      [%sp + 23 * 4], %g7                                                     \n"
            "   retl                                                                            \n"
            "   add     %sp, 0x80, %sp      ! Set the stack back                                \n"

".LLfe2:                                                                                 	\n"     
            "   .size   _ZNVK6System7SPARC327Context4loadEv,.LLfe2-_ZNVK6System7SPARC327Context4loadEv ");

/**************************************************************************************************/

        ASM("   .align 4                                                                	\n"
            "   .global _ZN6System7SPARC3214switch_contextEPVPNS0_7ContextES2_                  \n"
            "   .type   _ZN6System7SPARC3214switch_contextEPVPNS0_7ContextES2_,#function        \n"
            "   .proc   020                                                             	\n"     
"_ZN6System7SPARC3214switch_contextEPVPNS0_7ContextES2_:                                        \n"

	    "	! -- Saving the current windows -- !						\n"

	    "   ! For now this can be used to disable traps					\n"
	    "	! but after a Ticc Trap may be launched						\n"
	    "	! in the beggining of the routine - manual pg. 133				\n"
	    "	! We lost time doing everything until disable traps				\n"

            "   sub     %sp, 0x80, %sp      ! Reserving space - 4 bytes * 32 registers          \n"
	    "	st      %g7, [%sp + 23 * 4] ! To have a place where we can put %psr             \n"
	    "	rd      %psr, %g7	    ! Reading %psr					\n"
	    "	st      %g7, [%sp + 16 * 4] ! Storing %psr					\n"
	    "	or	%g7, 0xf00, %g7	    ! Disabling interrupts				\n"
	    "	wr	%g7, %psr	    ! Interrupts disabled				\n"
	    " 	nop; nop; nop;									\n"

	    "	st      %sp, [ %o0 ]        ! return SP for this thread                         \n"

	    "   std     %l0, [%sp + 0  * 4] ! save L & I registers				\n"
	    "   std     %l2, [%sp + 2  * 4]							\n"
	    "   std     %l4, [%sp + 4  * 4]							\n"
	    "   std     %l6, [%sp + 6  * 4]							\n"
	
            "	std     %i0, [%sp + 8  * 4]							\n"
	    "   std     %i2, [%sp + 10 * 4]							\n"	
	    "   std     %i4, [%sp + 12 * 4]							\n"	
	    "   std     %i6, [%sp + 14 * 4]							\n"

	    "	st      %g1, [%sp + 17 * 4] ! save G & O registers				\n"
	    "   std     %g2, [%sp + 18 * 4]							\n"
	    "   std     %g4, [%sp + 20 * 4]							\n"
	    "	st      %g6, [%sp + 22 * 4] ! %g7 is already saved				\n"
		
	    "	std     %o0, [%sp + 24 * 4]							\n"
	    "   std     %o2, [%sp + 26 * 4]							\n"
	    "   std     %o4, [%sp + 28 * 4]							\n"
	    "   std     %o6, [%sp + 30 * 4]							\n"

	    "	mov	%o1, %g7	    ! backup the reference to the next thread		\n"

	    "	! - Taking care of other windows - !						\n"

            "   rd %wim, %g6									\n"
	    "   wr %g0, %wim									\n"	
	    "   nop; nop; nop									\n"
"1:												\n"
	    "	restore			    ! Get into the previous window			\n"
	    "	rd  %psr, %g5									\n"
	    "	and %g5, 0x1f, %g5								\n"
	    "	srl %g6, %g5, %g4	    							\n"
		
	    "	cmp %g4,0x1		    ! Switch if no more windows to save			\n"
	    "	be 2f										\n"
            "   nop                                                                             \n"

            "   std     %l0, [%sp + 0  * 4] ! save L & I registers				\n"
            "   std     %l2, [%sp + 2  * 4]							\n"
            "   std     %l4, [%sp + 4  * 4]							\n"
            "   std     %l6, [%sp + 6  * 4]							\n"

            "   std     %i0, [%sp + 8  * 4]							\n"
            "   std     %i2, [%sp + 10 * 4]							\n"
            "   std     %i4, [%sp + 12 * 4]							\n"
            "   std     %i6, [%sp + 14 * 4]							\n"
		
	    "	ba 1b										\n"
            "   nop                                                                             \n"
		
	    "	! ----------- switching ---------- !						\n"
"2:												\n"
            "   rd      %psr, %g6 	    ! Setting CWP to window 0 and 			\n"
            "   andn    %g6, 0x1f, %g6	                                         		\n"
	    "	wr	%g6, %psr								\n"
            "   nop; nop; nop									\n"

            "   mov     %g7, %sp            ! target sp (%sp = %o6)                             \n"

	    "   ldd     [%sp + 0  * 4], %l0 ! restore L & I registers				\n"
	    "	ldd     [%sp + 2  * 4], %l2							\n"
	    "	ldd     [%sp + 4  * 4], %l4							\n"
	    "	ldd     [%sp + 6  * 4], %l6							\n"

	    "	ldd     [%sp + 8  * 4], %i0							\n"
	    "   ldd     [%sp + 10 * 4], %i2							\n"
            "	ldd     [%sp + 12 * 4], %i4							\n"
	    "   ldd     [%sp + 14 * 4], %i6							\n"

	    "	ld      [%sp + 17 * 4], %g1							\n"
	    "   ldd     [%sp + 18 * 4], %g2							\n"
	    "   ldd     [%sp + 20 * 4], %g4							\n"
	    "	ld      [%sp + 22 * 4], %g6							\n"

	    "   ldd     [%sp + 24 * 4], %o0							\n"
	    "   ldd     [%sp + 26 * 4], %o2							\n"
	    "   ldd     [%sp + 28 * 4], %o4							\n"
	    "   ld      [%sp + 31 * 4], %o7 ! %o6 (== %sp) is already restored			\n"
		
	    "	ld	[%sp + 16 * 4], %g7 ! Set new CWP (0) and old ET and PIL		\n"
	    "	andn	%g7, 0x1f, %g7								\n"
            "	wr      %g0,0x2, %wim       ! Setting WIM to window 1                           \n"
	    "   wr      %g7, %psr           							\n"
	    "	nop; nop; nop									\n"

	    "	ld      [%sp + 23 * 4], %g7							\n"
	    "	retl										\n"
	    "	add     %sp, 0x80, %sp      ! Set the stack back				\n"

".LLfe3:                                                                                	\n"     
            "   .size   _ZN6System7SPARC3214switch_contextEPVPNS0_7ContextES2_,.LLfe3-_ZN6System7SPARC3214switch_contextEPVPNS0_7ContextES2_");

/**************************************************************************************************/

	ASM("	.ident	\"GCC: (GNU) 3.2.3\"							");


__END_SYS

