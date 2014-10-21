#include "linux_syscalls.h"

.text

.type write,@function
.weak write
write: 
.type __libc_write,@function
.global __libc_write
__libc_write:
	push   %ebp
	mov    %esp,%ebp
        push   %edi
        push   %esi
        push   %ebx
        movl   0x08(%ebp),%ebx
        movl   0x0c(%ebp),%ecx
        movl   0x10(%ebp),%edx
        movl   $__NR_write, %eax
        int    $0x80
        pop     %ebx
        pop     %esi
        pop     %edi
	leave 
	ret
.Lend__libc_write: 
.size __libc_write,.Lend__libc_write-__libc_write

.type   exit,@function
.weak exit
exit:
.type   _exit,@function
.global _exit
_exit:  
	push   %ebp
	mov    %esp,%ebp
        movl  $__NR_exit, %eax
        push    %edi
        push    %esi
        push    %ebx
        movl    0x08(%ebp),%ebx
        int     $0x80
        pop     %ebx
        pop     %esi
        pop     %edi
	leave
        ret
_exit_end:
.size _exit,exit_end-_exit

.global _start
.type _start,@function
_start:
	push   %ebp
	mov    %esp,%ebp
	push $0x0
	push $0x0
      	call main 
	call _exit
_start_end:
.size _start,_start_end-_start
