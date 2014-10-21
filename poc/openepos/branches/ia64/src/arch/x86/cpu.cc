// EPOS x86 CPU Mediator Implementation

#include <arch/x86/cpu.h>

__BEGIN_SYS

// Class attributes
unsigned int CPU::_cpu_clock;
unsigned int CPU::_bus_clock;

// Class methods
void CPU::Context::save() volatile
{
    // TODO: Context save
    ASMV("movq $0xDEADCAFEDEADCAFE, %rax\n hlt\n");
}

void CPU::Context::load() const volatile
{
    ASMV(
    "   movq  %rdi, %rsp        \n" // Stack = this
    "   popq  %r10              \n"
    "   popq  %r11              \n"
    "   popq  %r12              \n"
    "   popq  %r13              \n"
    "   popq  %r14              \n"
    "   popq  %r15              \n"
    "   popq   %r8              \n"
    "   popq   %r9              \n"
    "   popq   %rdi             \n"
    "   popq   %rsi             \n"
    "   popq   %rbp             \n"
    "   addq   $8, %rsp         \n" // Stack was already setup :)
    "   popq   %rbx             \n"
    "   popq   %rdx             \n"
    "   popq   %rcx             \n"
    "   popq   %rax             \n"
    "   popfq                   \n"
    );
}

void CPU::switch_context(Context * volatile * o, Context * volatile n)
{
    ASMV(
    // Context save
    "   pushfq                  \n"
    "   pushq  %rax             \n"
    "   pushq  %rcx             \n"
    "   pushq  %rdx             \n"
    "   pushq  %rbx             \n"
    "   subq   $8, %rsp         \n" // WARNING: Not really used!
    "   pushq  %rbp             \n"
    "   pushq  %rsi             \n"
    "   pushq  %rdi             \n"
    "   pushq  %r9              \n"
    "   pushq  %r8              \n"
    "   pushq  %r15             \n"
    "   pushq  %r14             \n"
    "   pushq  %r13             \n"
    "   pushq  %r12             \n"
    "   pushq  %r11             \n"
    "   pushq  %r10             \n"

    // switch context
    "   movq %rsp, (%rdi)       \n" // This is tricky: Update the Context *
                                    // passed as argument with the value of the
                                    // stack pointer!
    "   movq %rsi, %rsp         \n" // rsp = new

    // Context load
    "   popq  %r10              \n"
    "   popq  %r11              \n"
    "   popq  %r12              \n"
    "   popq  %r13              \n"
    "   popq  %r14              \n"
    "   popq  %r15              \n"
    "   popq   %r8              \n"
    "   popq   %r9              \n"
    "   popq   %rdi             \n"
    "   popq   %rsi             \n"
    "   popq   %rbp             \n"
    "   addq   $8, %rsp         \n" // Stack was already setup :)
    "   popq   %rbx             \n"
    "   popq   %rdx             \n"
    "   popq   %rcx             \n"
    "   popq   %rax             \n"
    "   popfq                   \n"
    );
}

__END_SYS
