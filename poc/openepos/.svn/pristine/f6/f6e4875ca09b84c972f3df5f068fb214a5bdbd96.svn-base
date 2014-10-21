// OpenEPOS EMote2ARM Mediator Initialization

#include <machine.h>
#include <battery.h>

__BEGIN_SYS

void EMote2ARM::init() {
	db<Init, EMote2ARM>(TRC) << "EMote2ARM::init()\n";

	if (Traits<Flash>::enabled || Traits<Machine>::flash_erase_checking)
		Flash::init();

	if (Traits<Machine>::flash_erase_checking)
		check_flash_erase();

	if (Traits<Battery>::enabled)
		Battery::init();

	if (Traits<NIC>::enabled)
		NIC::init();

}

__END_SYS
