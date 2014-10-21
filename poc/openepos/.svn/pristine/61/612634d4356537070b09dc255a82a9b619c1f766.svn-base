# 1 "x86_crti.S"
# 1 "<built-in>"
# 1 "<command-line>"
# 1 "x86_crti.S"
    .file "crti.s"

    .section .init
    .weak __epos_library_app_entry
    .type __epos_library_app_entry,@function
__epos_library_app_entry:

    .globl _init
    .type _init,@function
_init:
 push %rbp
 mov %rsp,%rbp
 sub $0x8,%rsp

        .section .fini
        .globl _fini
        .type _fini,@function
_fini:
 push %rbp
 mov %rsp,%rbp
