/* TODO: rename dispatch.s into dipatch.S, define __SREG__, __SP_L__ and __SP_H__ */
.text
.globl switchtask
	.type switchtask, @function
switchtask:
	/* store status register via r0 */
	in   r0, __SREG__
	
	/* disable interrupts */
	cli

	/* save old SREG */
	push r0
	
	/* save registers */
	push r2
	push r3
	push r4
	push r5
	push r6
	push r7
	push r8
	push r9
	push r10
	push r11
	push r12
	push r13
	push r14
	push r15
	push r16
	push r17

	/* save stack pointer */
	movw r26, r22 /* move word r27:r26 <- r23:r22 */
	in r0, __SP_L__
	st x+, r0
	in r0, __SP_H__
	st x, r0
	
	/* switch stack and restore context */
	out __SP_L__, r24
	out __SP_H__, r25
	
	pop r17
	pop r16
	pop r15
	pop r14
	pop r13
	pop r12
	pop r11
	pop r10
	pop r9
	pop r8
	pop r7
	pop r6
	pop r5
	pop r4
	pop r3
	pop r2

	/* restore status register */
	pop r0
	out __SREG__, r0

	/* transfer control */
	ret

.globl dispatch
	.type dispatch, @function
dispatch:
	/* switch stack and restore context */
	out __SP_L__, r24
	out __SP_H__, r25
	
	pop r17
	pop r16
	pop r15
	pop r14
	pop r13
	pop r12
	pop r11
	pop r10
	pop r9
	pop r8
	pop r7
	pop r6
	pop r5
	pop r4
	pop r3
	pop r2

	/* restore status register */
	pop r0
	out __SREG__, r0

	ret

