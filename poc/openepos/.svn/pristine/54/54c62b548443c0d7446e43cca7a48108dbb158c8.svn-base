// EPOS-- ATMega128 NIC Mediator Initialization

#include <system/kmalloc.h>
#include <mach/atmega128/machine.h>
#include <utility/power_manager.h>

__BEGIN_SYS

POWER_MANAGER_DEFINITIONS(ATMega128_NIC)
{
    //values needs to be verified
    /*[power_mode][tension]*/
    /*{2.5V, 3.0V, 3.5V, 4.0V, 4.5V, 5.0V, 5.5V}*/

    { 0, 13800, 0, 0, 0, 0, 0, }, //FULL (transmit)
    { 0,  9600, 0, 0, 0, 0, 0, }, //RECEIVE
    { 0,    96, 0, 0, 0, 0, 0, }, //LISTEN
    { 0,     1, 0, 0, 0, 0, 0, } //OFF
};

template <int unit>
inline static void call_init()
{
    Traits<ATMega128_NIC>::NICS::template Get<unit>::Result::init(unit);
    call_init<unit + 1>();
};

template <> 
inline static void call_init<Traits<ATMega128_NIC>::NICS::Length>() {};

void ATMega128_NIC::init()
{
    call_init<0>();
}

__END_SYS
