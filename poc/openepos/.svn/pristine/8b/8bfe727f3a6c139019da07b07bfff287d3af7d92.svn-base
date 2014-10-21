// OpenEPOS MC13224V NIC Mediator Initialization

#include <nic.h>
#include <machine.h>
#include <system/kmalloc.h>

__BEGIN_SYS

EMote2ARM_NIC::OP_Mode EMote2ARM_NIC::_mode = 0;

template <int unit>
inline static void call_init()
{
    typedef typename Traits<EMote2ARM_NIC>::NICS::template Get<unit>::Result NIC;
    if(Traits<NIC>::enabled)
        NIC::init(unit);
    call_init<unit + 1>();
};

template <> 
inline void call_init<Traits<EMote2ARM_NIC>::NICS::Length>()
{
};

void EMote2ARM_NIC::init()
{
    call_init<0>();
}

__END_SYS

