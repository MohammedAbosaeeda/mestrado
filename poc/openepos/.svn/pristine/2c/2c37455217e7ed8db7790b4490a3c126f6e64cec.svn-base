// EPOS-- Battery Lifetime Estimate Abstraction Implementations


#include <cpu.h>
#include <machine.h>
#include <battery.h>
#include <battery_lifetime.h>
#include <adc.h>


__BEGIN_SYS


unsigned int Battery_Lifetime::remaining_charge()
{ 

// ATmega128 has Battery
#if defined (__atmega128)

    if(Traits<Battery>::enabled) {
        //ADC disable... 
        Battery::disable_in_use();
        return Battery::voltage();
    }

#endif

    return Traits<Battery_Lifetime>::boundary + 1; 
}

__END_SYS

