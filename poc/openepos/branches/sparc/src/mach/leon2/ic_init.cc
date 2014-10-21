// EPOS-- LEON2 Interrupt Controller Mediator Initialization

#include <mach/leon2/ic.h>

__BEGIN_SYS

// Class initialization
int LEON2_IC::init(System_Info * si)
{
    db<LEON2_IC>(TRC) << "LEON2_IC::init()\n";

    //cancian:por que inicializar com 0? Não são enuns com offsets?

    *(reinterpret_cast<volatile unsigned int *>(MASK_PRIO))  = 0x0;
    *(reinterpret_cast<volatile unsigned int *>(PENDING))  = 0x0;
    *(reinterpret_cast<volatile unsigned int *>(FORCE)) = 0x0;
    *(reinterpret_cast<volatile unsigned int *>(CLEAR)) = 0x0;

    disable();

    return 0;
}

__END_SYS
