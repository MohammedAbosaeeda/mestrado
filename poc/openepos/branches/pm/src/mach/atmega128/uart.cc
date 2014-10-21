// EPOS-- ATMega128 UART Mediator Implementation

#include <mach/atmega128/uart.h>

__BEGIN_SYS

POWER_MANAGER_DEFINITIONS(ATMega128_UART)
{
        //values needs to be verified
        /*[power_mode][tension]*/
        /*{2.5V, 3.0V, 3.5V, 4.0V, 4.5V, 5.0V, 5.5V}*/
        {10000 ,0,0,0,0,0,0 },//FULL
        {5000 ,0,0,0,0,0,0 }, //SEND_ONLY
        {5000 ,0,0,0,0,0,0 }, //RECEIVE_ONLY
        {0 ,0,0,0,0,0,0 }     //OFF
};

__END_SYS

