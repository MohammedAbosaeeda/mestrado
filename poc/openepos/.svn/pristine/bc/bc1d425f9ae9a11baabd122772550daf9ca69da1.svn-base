// EPOS IA32 CPU Mediator Implementation

#include <arch/ia32/cpu.h>
#include <utility/ostream.h>
#include <system.h>

extern "C" { void _exec(void *); }

__BEGIN_SYS

// Class attributes
unsigned int IA32::_cpu_clock;
unsigned int IA32::_bus_clock;

// Class methods
void IA32::Context::save() volatile
{
    ASM("	pushl	%ebp						\n"
 	"	movl	%esp, %ebp					\n"
        "	movl    8(%ebp), %esp	# sp = this	 		\n"
        "	addl    $40, %esp      	# sp += sizeof(Context)		\n"
        "	pushl	4(%ebp)		# push eip			\n"
        "	pushfl							\n"
        "	pushl	%eax						\n"
        "	pushl	%ecx						\n"
        "	pushl	%edx						\n"
        "	pushl	%ebx						\n"
        "	pushl   %ebp		# push esp			\n"
        "	pushl   (%ebp)		# push ebp			\n"
        "	pushl	%esi						\n"
        "	pushl	%edi						\n"
        "	movl    %ebp, %esp					\n"
        "	popl    %ebp						\n");
}

void IA32::Context::load() const volatile
{
    if(Traits<System>::mode == Traits<Build>::KERNEL) {
        // Setup a sysexit to leave kernel mode
        ASM("       movl    4(%esp), %esp       # sp = this             \n"
            "       popal                       # pop context           \n"
            "       popfl                                               \n"
            "       popl    %edx                # dx = this->_eip       \n");

        // The user stack is in the data segment just before the heap
        ASM("       movl    %0, %%ecx           # cx = user stack       \n"
            : : "r"(System::info()->lm.app_heap));

//        // Reload segment registers not handled by sysexit with user mode selectors
//        ASM("       mov    %0, %%ds                                    \n"
//            "       mov    %0, %%es                                    \n"
//            "       mov    %0, %%fs                                    \n"
//            "       mov    %0, %%gs                                    \n"
//            : : "a"(SEL_APP_DATA));

        ASM("       sysexit                     # to (MSR[IA32_SYSENTER_CS]+8, DX, CX) \n");
    } else
        // POPA ignores the ESP saved by PUSHA. ESP is just normally incremented
        ASM("	    movl    4(%esp), %esp	# sp = this     	\n"
            "       popal				        	\n"
            "	    popfl					        \n");
}

void IA32::switch_context(Context * volatile * o, Context * volatile n)
{
    // PUSHA saves an extra "esp" (which is always "this"),
    // but saves several instruction fetchs.
    ASM("	pushfl				\n"
        "	pushal				\n"
        "	movl    40(%esp), %eax	# old	\n" 
        "	movl    %esp, (%eax)		\n"
        "	movl    44(%esp), %esp	# new	\n"
        "	popal				\n"
        "	popfl				\n");
}

int IA32::syscall(void * m)
{
    int ret;

    ASM("pushal");
    ASM("" : : "a"(m));
    ASM("movl %esp, %ecx");
    ASM("leal __ret, %edx");
    ASM("sysenter # to (MSR[CS],MSR[EIP],MSR[ESP])" : "=a"(ret) : );
    ASM("__ret: popal");

    return ret;
}

void IA32::syscall_entry()
{
    // Save return info for sysexit
    ASM("       pushl   %ecx                                            \n"
        "       pushl   %edx                                            \n");

    // Do the system call by calling _exec with the massage pointed by AX
    ASM("       pushl   %eax                                            \n"
        "       call    _exec                                           \n"
        "       popl    %eax                                            \n");

    // Restore return info and sysexit
    ASM("       popl    %edx                                            \n"
        "       popl    %ecx                                            \n"
        "       sysexit                                                 \n");
}

__END_SYS
