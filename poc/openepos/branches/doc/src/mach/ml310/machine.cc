/*! @file
 *  @brief EPOS ML310 Mediator Implementation
 *
 *  CVS Log for this file:
 *  \verbinclude src/mach/ml310/machine_cc.log
 */
#include <mach/ml310/machine.h>

extern "C" { void _exit(int s); }

__BEGIN_SYS

// Class attributes

// Class methods
void ML310::panic()
{
    kout << "PANIC!\n";
    CPU::int_disable(); 
    CPU::halt();
}

void ML310::int_not(unsigned int i)
{
    db<ML310>(WRN) << "\nInt " << i
                   << " occurred, but no handler installed\n";
    panic();
}

void ML310::exc_program(unsigned int i)
{
    Reg32 esr = CPU::spr(CPU::ESR);
    Reg32 addr = CPU::spr(CPU::SSR0);
    if(esr & CPU::ESR_PIL){
        db<ML310>(ERR) << "Illegal Instruction Executed! (addr=" << (void*)addr << ", data=" << (void*)(*((unsigned int*)addr)) << ")\n";
        panic();
    }
    if(esr & CPU::ESR_PPR){
        db<ML310>(ERR) << "Privileged Instruction executed in ProblemState! (" << (void*)addr << ")\n";
        panic();
    }
    if(esr & CPU::ESR_PTR){
        db<ML310>(ERR) << "Trap executed, but not implemented! (" << (void*)addr << ")\n";
        panic();
    }
}

__END_SYS
