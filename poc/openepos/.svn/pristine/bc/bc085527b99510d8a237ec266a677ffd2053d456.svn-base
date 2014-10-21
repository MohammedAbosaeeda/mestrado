// EPOS-- AVR_ADC Mediator Implementation

#include <mach/atmega128/adc.h>
#include <utility/power_manager.h>

__BEGIN_SYS

bool AVR_ADC::_in_use = false;

POWER_MANAGER_DEFINITIONS(ATMega128_ADC)
{
    //values needs to be verified
    /*[power_mode][tension]*/
    /*{2.5V, 3.0V, 3.5V, 4.0V, 4.5V, 5.0V, 5.5V}*/

    { 900, 1338, 0, 0, 0, 0, 0, },//FULL // 90 = 2.5 V (at 1MHz - for 50 KHz it is 300)
    {   0,    1, 0, 0, 0, 0, 0, } //OFF
};

__END_SYS



