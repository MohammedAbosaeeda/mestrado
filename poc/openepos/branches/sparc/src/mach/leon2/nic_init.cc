// EPOS-- LEON2 NIC Mediator Initialization

#include <system/kmalloc.h>
#include <machine.h>
#include <nic.h>

__BEGIN_SYS

template <int unit>
inline static int call_init(System_Info * si)
{
    int status = 
	Traits<LEON2_NIC>::NICS::template Get<unit>::Result::init(unit, si);
    status |= call_init<unit + 1>(si);
    return status;
};

template <>
inline static int call_init<Traits<LEON2_NIC>::NICS::Length>(System_Info * si) 
{
    return 0;
};

int LEON2_NIC::init(System_Info * si)
{
    return call_init<0>(si);
}

__END_SYS
