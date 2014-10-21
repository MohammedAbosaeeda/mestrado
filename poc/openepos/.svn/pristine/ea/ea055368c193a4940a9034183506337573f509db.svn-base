	.file "armv7_crt0.S"

        .section .init
        // Interrupt Vector Table
        .type   _vector_table, object
_vector_table:
        .word   0x20002000                              // Stack pointer at reset
        .word   _start + 1                              // Reset  (lsb must be 1 in thumb mode)
        .word   _ZN4EPOS9eMote3_IC8dispatchEv + 1       // NMI
        .word   _ZN4EPOS9eMote3_IC8dispatchEv + 1       // Hard fault
        .word   _ZN4EPOS9eMote3_IC8dispatchEv + 1       // Memory management fault
        .word   _ZN4EPOS9eMote3_IC8dispatchEv + 1       // Bus fault
        .word   _ZN4EPOS9eMote3_IC8dispatchEv + 1       // Usage fault
        .word   _ZN4EPOS9eMote3_IC8dispatchEv + 1       // Reserved
        .word   _ZN4EPOS9eMote3_IC8dispatchEv + 1       // Reserved
        .word   _ZN4EPOS9eMote3_IC8dispatchEv + 1       // Reserved
        .word   _ZN4EPOS9eMote3_IC8dispatchEv + 1       // Reserved
        .word   _ZN4EPOS9eMote3_IC8dispatchEv + 1       // SVCall
        .word   _ZN4EPOS9eMote3_IC8dispatchEv + 1       // Reserved
        .word   _ZN4EPOS9eMote3_IC8dispatchEv + 1       // Reserved
        .word   _ZN4EPOS9eMote3_IC8dispatchEv + 1       // PendSV
        .word   _ZN4EPOS9eMote3_IC8dispatchEv + 1       // Systick
        .word   _ZN4EPOS9eMote3_IC8dispatchEv + 1       // IRQ0
        .word   _ZN4EPOS9eMote3_IC8dispatchEv + 1       // IRQ1
        .word   _ZN4EPOS9eMote3_IC8dispatchEv + 1       // IRQ2
        .word   _ZN4EPOS9eMote3_IC8dispatchEv + 1       // IRQ3
        .word   _ZN4EPOS9eMote3_IC8dispatchEv + 1       // IRQ4
        .word   _ZN4EPOS9eMote3_IC8dispatchEv + 1       // IRQ5
        .word   _ZN4EPOS9eMote3_IC8dispatchEv + 1       // IRQ6
        .word   _ZN4EPOS9eMote3_IC8dispatchEv + 1       // IRQ7
        .word   _ZN4EPOS9eMote3_IC8dispatchEv + 1       // IRQ8
        .word   _ZN4EPOS9eMote3_IC8dispatchEv + 1       // IRQ9
        .word   _ZN4EPOS9eMote3_IC8dispatchEv + 1       // IRQ10
        .word   _ZN4EPOS9eMote3_IC8dispatchEv + 1       // IRQ11
        .word   _ZN4EPOS9eMote3_IC8dispatchEv + 1       // IRQ12
        .word   _ZN4EPOS9eMote3_IC8dispatchEv + 1       // IRQ13
        .word   _ZN4EPOS9eMote3_IC8dispatchEv + 1       // IRQ14
        .word   _ZN4EPOS9eMote3_IC8dispatchEv + 1       // IRQ15
        .word   _ZN4EPOS9eMote3_IC8dispatchEv + 1       // IRQ16
        .word   _ZN4EPOS9eMote3_IC8dispatchEv + 1       // IRQ17
        .word   _ZN4EPOS9eMote3_IC8dispatchEv + 1       // IRQ18
        .word   _ZN4EPOS9eMote3_IC8dispatchEv + 1       // IRQ19
        .word   _ZN4EPOS9eMote3_IC8dispatchEv + 1       // IRQ20
        .word   _ZN4EPOS9eMote3_IC8dispatchEv + 1       // IRQ21
        .word   _ZN4EPOS9eMote3_IC8dispatchEv + 1       // IRQ22
        .word   _ZN4EPOS9eMote3_IC8dispatchEv + 1       // IRQ23
        .word   _ZN4EPOS9eMote3_IC8dispatchEv + 1       // IRQ24
        .word   _ZN4EPOS9eMote3_IC8dispatchEv + 1       // IRQ25
        .word   _ZN4EPOS9eMote3_IC8dispatchEv + 1       // IRQ26
        .word   _ZN4EPOS9eMote3_IC8dispatchEv + 1       // IRQ27
        .word   _ZN4EPOS9eMote3_IC8dispatchEv + 1       // IRQ28
        .word   _ZN4EPOS9eMote3_IC8dispatchEv + 1       // IRQ29

	.section .init
	.align  2
	.global _start
	.type   _start, function
_start:
        // Clear BSS
        mov     r0, #0
        ldr     r1, =__bss_start__
        ldr     r2, =__bss_end__
  .L1:  str     r0, [r1]
        add     r1, #4
        cmp     r1, r2
        blt     .L1

	bl      _init

        .align  2
        .globl  __epos_app_entry
        .type   __epos_app_entry, function
__epos_app_entry:
        mov     r0, #1
        msr     CONTROL, r0
        bl      main
        push    {r0}         // save main's return value to be used by exit()
    	bl	_fini
    	bl	_exit
