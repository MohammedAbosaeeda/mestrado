// EPOS IA32 CPU Mediator Implementation

#include <arch/ia32/cpu.h>
#include <syscall_handler.h>
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

/**
 * OLD load member-function, preserved
 */
/*
void IA32::Context::load() const volatile
{
    // POPA ignores the ESP saved by PUSHA. ESP is just normally incremented. 
    ASM("	movl    4(%esp), %esp	# sp = this			\n"
        "	popal							\n"
 	"	popfl							\n");
}
*/

/**
 * New code wrote by Guto needs test in this version of EPOS
 */
void IA32::Context::load() const volatile
{
    if(Traits<System>::mode == Traits<Build>::BUILTIN) {
        // Setup a sysexit to leave kernel mode
        wrmsr(0x174, SEL_APP_CODE); // USER mode CS (RPL = 3)
        ASM("       movl    4(%esp), %esp       # sp = this             \n"
            "       popal                       # pop context           \n"
            "       popfl                                               \n"
//            "       popl    %edx                # dx = this->_eip       \n"
            "       movl    $0x00000028, %edx   # cx = esp              \n"
            "       movl    $0x00400000, %esp                           \n"
            "       movl    %esp, %ecx          # cx = esp              \n");

        // Reload segment registers not handled by sysexit with user mode selectors
        ASM("       movw    %ax, %ds                                    \n"
            "       movw    %ax, %es                                    \n"
            "       movw    %ax, %fs                                    \n"
            "       movw    %ax, %gs                                    \n"
            : : "a"(SEL_APP_DATA));

//        ASM("cli");

        ASM("       sysexit                     # to (MSC[0x174],dx,cx) \n");
    } else
        // POPA ignores the ESP saved by PUSHA. ESP is just normally incremented.
        ASM("	    movl    4(%esp), %esp	# sp = this     	\n"
            "       popal				        	\n"
            "	    popfl					        \n");
}

/**
 * New code wrote by Guto needs test in this version of EPOS
 */
void IA32::system_entry()
{
    ASMV("pushal");
    // do system call

    // Setup MSRs
    // IA32_SYSENTER_CS (MSR address 174H)
    wrmsr(0x174, SEL_APP_CODE);
    ASMV("popal"); // Sets EDX and ECX
    ASMV("sysexit");
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

void IA32::system_entry()
{
    ASMV("pushal");
    // do system call

    // Setup MSRs
    // IA32_SYSENTER_CS (MSR address 174H)
    Syscall_Handler::wrmsr(0x174, SEL_APP_CODE);
    ASMV("popal"); // Sets EDX and ECX
    ASMV("sysexit");
}

__END_SYS
