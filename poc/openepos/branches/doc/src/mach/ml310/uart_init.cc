/*! @file
 *  @brief EPOS ML310 UART Mediator Initialization
 *
 *  CVS Log for this file:
 *  \verbinclude src/mach/ml310/uart_init_cc.log
 */
#include <machine.h>

__BEGIN_SYS

void ML310_UART::init()
{
    db<ML310_UART>(TRC) << "ML310_UART::init()\n";

    //Enable interrupt...
    ML310_IC::enable(ML310_IC::INT_UART_INTR);
}

__END_SYS
