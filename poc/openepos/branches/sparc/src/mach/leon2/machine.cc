// EPOS-- LEON2 Mediator Implementation

#include <mach/leon2/machine.h>

__BEGIN_SYS

// Class methods
void LEON2::panic()
{
    CPU::int_disable(); 
    Display display;
    display.position(24, 73);
    display.puts("PANIC!");
    CPU::halt();
}

__END_SYS
