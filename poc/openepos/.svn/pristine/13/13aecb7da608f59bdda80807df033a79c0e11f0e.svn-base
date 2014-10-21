/*! @file
 *  @brief EPOS ML310 Timer Mediator Initialization
 *
 *  CVS Log for this file:
 *  \verbinclude src/mach/ml310/timer_init_cc.log
 */
#include <timer.h>

__BEGIN_SYS

void ML310_Timer::init()
{
    db<ML310_Timer>(TRC) << "ML310_Timer::init()\n";

    CPU::spr(CPU::PIT, 0); //PIT = 0
    CPU::spr(CPU::TSR, 0xFFFFFFFF); //Disable all status (TSR)
    CPU::spr(CPU::TCR, 0x04400000); //Enable Timer with PIT AUTO RELOAD ENABLED. (TCR)
}

__END_SYS
