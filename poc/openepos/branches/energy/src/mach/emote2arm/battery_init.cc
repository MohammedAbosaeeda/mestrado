// OpenEPOS EMote2ARM_Battery Mediator Initialization

#include <battery.h>
#include <mach/emote2arm/buck_regulator.h>
#include <system/kmalloc.h>

__BEGIN_SYS

void EMote2ARM_Battery::init()
{
    db<Init, EMote2ARM_Battery>(TRC) << "EMote2ARM_Battery::init()\n";

    system_battery = new(kmalloc(sizeof(EMote2ARM_Battery))) EMote2ARM_Battery();

    if (Traits<EMote2ARM_Battery>::buck_enabled)
    {
    	EMote2ARM_Buck_Regulator::enable();
        system_battery->check_buck();
    }
}

__END_SYS
