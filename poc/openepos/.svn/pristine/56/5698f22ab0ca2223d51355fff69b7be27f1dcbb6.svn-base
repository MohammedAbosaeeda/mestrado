	.file "sparcv8_crt1.s"	

	.text
        .align  4
	.global _start, __epos_app_library_entry
        .type   _start,#function

_start:
	call	_init
	nop

__epos_app_library_entry:
        call	main
        nop
	/* here the return value must be set if necessary*/
	nop
	call	_fini
	nop
	call	_exit
	nop
	retl
	nop
	nop
