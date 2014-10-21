/* TODO pass params in registers and merge the dispatchers */
.text
.globl switchtask
	.type switchtask, @function
switchtask:
	pushf
	pusha
	movl  40(%esp), %eax /* old sp */
	movl  %esp, (%eax);

	/* switch stack and restore context */
	movl  44(%esp), %eax /* new sp */
	movl  (%eax), %esp
	popa
	popf

	/* TODO unblock signals */

	/* transfer control */
	ret

.globl dispatch
	.type dispatch, @function
dispatch:
	movl  4(%esp), %eax
	movl  (%eax), %esp
	popa
	popf
	ret

