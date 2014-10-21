/* TODO pass params in registers and merge the dispatchers */
.text
.globl switchtask
	.type switchtask, @function
switchtask:
	pushf

	push %rax
	push %rbx
	push %rcx
	push %rdx
	push %rsi
	push %rdi
	push %r8
	push %r9
	push %r10
	push %r11
	push %r12
	push %r13
	push %r14
	push %r15
	push %rbp

	mov  %rsp, (%rdi);

	/* switch stack and restore context */
	mov  (%rsi), %rsp

    pop %rbp
	pop %r15
	pop %r14
	pop %r13
	pop %r12
	pop %r11
	pop %r10
	pop %r9
	pop %r8
	pop %rdi
	pop %rsi
	pop %rdx
	pop %rcx
	pop %rbx
	pop %rax

	popf

	/* TODO unblock signals */

	/* transfer control */
	ret

.globl dispatch
	.type dispatch, @function
dispatch:
	mov  (%rdi), %rsp

    pop %rbp
	pop %r15
	pop %r14
	pop %r13
	pop %r12
	pop %r11
	pop %r10
	pop %r9
	pop %r8
	pop %rdi
	pop %rsi
	pop %rdx
	pop %rcx
	pop %rbx
	pop %rax

	popf
	ret

