// EPOS-- TestMIPS Interrupt Controller Mediator Initialization

#include <mach/softmips/ic.h>

__BEGIN_SYS

int SoftMIPS_IC::init(System_Info * si)
{
    db<SoftMIPS_IC>(TRC) << "SoftMIPS_IC::init()\n";

    disable();
	//enable();

    return 0;
}

__END_SYS
