// EPOS-- MC13224V Mediator Implementation

#include <mach/mc13224v/machine.h>

__BEGIN_SYS

void MC13224V::init()
{
    /* Enables the voltage regulators for the NVM */
    volatile unsigned int i;
    CPU::out32(IO::CRM_SYS_CNTL, 0x00000018);	/* set default state */
    CPU::out32(IO::CRM_VREG_CNTL, 0x00000f04);	/* bypass the buck */

    for (i = 0; i< 0x161a8; i++) { continue; }	/* wait for the bypass to take */

    CPU::out32(IO::CRM_VREG_CNTL, 0x00000ff8);	/* start the regulators */

    if (Traits<MC13224V_NIC>::enabled)
		MC13224V_NIC::init();
}

__END_SYS

