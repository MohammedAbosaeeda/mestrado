/* TODO pass params in registers and merge the dispatchers */
.text
.globl switchtask
	.type switchtask, @function
switchtask:
	pushf
	pushl %ebp
	pushl %edi
	pushl %esi
	pushl %ebx

	movl  24(%esp), %eax /* old sp */
	movl  %esp, (%eax);

	/* switch stack and restore context */
	movl  28(%esp), %esp /* new sp */
	popl %ebx
	popl %esi
	popl %edi
	popl %ebp
	popf

	/* TODO unblock signals */

	/* transfer control */
	ret

.globl dispatch
	.type dispatch, @function
dispatch:
	movl  4(%esp), %esp
	popl %ebx
	popl %esi
	popl %edi
	popl %ebp

	popf
	ret

.globl ChainTask
	.type ChainTask, @function
ChainTask:
	push 4(%esp)
	call __ChainTask
ret

.globl TerminateTask
	.type TerminateTask, @function
TerminateTask:
	call __TerminateTask
ret

