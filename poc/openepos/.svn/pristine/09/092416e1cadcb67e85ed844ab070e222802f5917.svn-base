	.file	"crtstuff.c"

	.section	.ctors,"aw",@progbits
	.align 4
	.type	__CTOR_END__, @object
__CTOR_END__:
	.long	0

#	.section	.dtors,"aw",@progbits
#	.align 4
#	.type	__DTOR_END__, @object
#__DTOR_END__:
#	.long	0
#
#	.section	.eh_frame,"a",@progbits
#	.align 4
#	.type	__FRAME_END__, @object
#__FRAME_END__:
#	.long	0
#
#	.section	.jcr,"aw",@progbits
#	.align 4
#	.type	__JCR_END__, @object
#__JCR_END__:
#	.long	0
#
	.text
	.p2align 4,,15
	.globl	__do_global_ctors_aux
	.type	__do_global_ctors_aux, @function
__do_global_ctors_aux:
	push	%rbp
	mov	%rsp, %rbp
	push	%rbx
	sub	$8, %rsp
	mov	$__CTOR_END__-8, %rbx
	mov	__CTOR_END__-8, %rax
	jmp	.L8
	.p2align 4,,7
.L10:
	sub	$8, %rbx
	call	*%rax
	mov	(%rbx), %rax
.L8:
	cmp	$-1, %rax
	jne	.L10
	pop	%rax
	pop	%rbx
	pop	%rbp
	ret

	.section	.init
	call __do_global_ctors_aux
#
#	.text
#	.section	.note.GNU-stack,"",@progbits
