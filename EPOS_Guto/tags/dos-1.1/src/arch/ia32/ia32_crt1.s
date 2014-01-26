# 1 "ia32_crt1.S"
# 1 "/home/tinha/Mestrado/EPOS_Guto/tags/dos-1.1/src/arch/ia32//"
# 1 "<built-in>"
# 1 "<command-line>"
# 1 "ia32_crt1.S"
 .file "crt1.s"

 .text
 .align 4
 .globl _start
 .type _start,@function
_start:
 call _init
 .align 4
 .globl __epos_library_app_entry
__epos_library_app_entry:
 call main
 push %eax
 call _fini
 call _exit
