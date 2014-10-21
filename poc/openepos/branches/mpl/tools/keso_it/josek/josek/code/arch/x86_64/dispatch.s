/* TODO pass params in registers and merge the dispatchers */
.text
.globl switchtask
	.type switchtask, @function
switchtask:
	pushf

	push %rbx
	push %r12
	push %r13
	push %r14
	push %r15
	push %rbp

	mov  %rsp, (%rdi);

	/* switch stack and restore context */
	mov  %rsi, %rsp

	pop %rbp
	pop %r15
	pop %r14
	pop %r13
	pop %r12
	pop %rbx

	popf

	/* TODO unblock signals */

	/* transfer control */
ret

.globl dispatch
	.type dispatch, @function
dispatch:
	mov  %rdi, %rsp

	pop %rbp
	pop %r15
	pop %r14
	pop %r13
	pop %r12
	pop %rbx
	
	popf
ret

.globl ChainTask
	.type ChainTask, @function
ChainTask:
	call __ChainTask
ret

.globl TerminateTask
	.type TerminateTask, @function
TerminateTask:
	call __TerminateTask
ret

