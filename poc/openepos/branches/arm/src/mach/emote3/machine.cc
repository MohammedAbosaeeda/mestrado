// EPOS eMote3 Mediator Implementation

#include <mach/emote3/machine.h>
#include <display.h>

__BEGIN_SYS

void eMote3::panic()
{
    CPU::int_disable();
    Display::puts("PANIC!\n");
    if(Traits<System>::reboot)
        reboot();
    else
        CPU::halt();
}
__END_SYS
