// EPOS-- ATMega128_Battery Abstraction Initialization

#include <battery.h>
#include <system/config.h>
#include <utility/malloc.h>

__BEGIN_SYS

void ATMega128_Battery::init()
{
    config(CHANNEL, SYSTEM_REF, SINGLE_CONVERSION_MODE,CLOCK >> 7);

    if(Traits<ATMega128_Battery>::accounting) {
        charge();
    }
}

__END_SYS


