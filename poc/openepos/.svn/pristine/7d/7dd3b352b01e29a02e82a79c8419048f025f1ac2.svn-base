// EPOS-- MC13224V NIC Mediator Declarations

#ifndef __mc13224v_nic_h
#define __mc13224v_nic_h

#include "radio.h"
#include <mach/common/cmac.h>

__BEGIN_SYS

class MC13224V_NIC: public Low_Power_Radio
{
private:
    typedef Traits<MC13224V_NIC>::NICS NICS;
    static const unsigned int UNITS = NICS::Length;

public:
    MC13224V_NIC() {    
	_dev = new Meta_NIC<NICS>::Get<0>::Result;
    }

    template<unsigned int UNIT>
    MC13224V_NIC(unsigned int u) {    
	_dev = new typename Meta_NIC<NICS>::Get<UNIT>::Result(UNIT);
    }
    ~MC13224V_NIC() {    
	delete _dev;
    }

    int send(const Address & dst, const Protocol & prot, 
	    const void * data, unsigned int size) {
	return _dev->send(dst, prot, data, size); 
    }

    int receive(Address * src, Protocol * prot,
	    void * data, unsigned int size) {
	return _dev->receive(src, prot, data, size); 
    }

    void reset() { _dev->reset(); }

    unsigned int mtu() const { return _dev->mtu(); }
    
    const Address & address() { return _dev->address(); }

    const Statistics & statistics() { return _dev->statistics(); }

    static void init();

private:
    Meta_NIC<NICS>::Base * _dev;
};

__END_SYS

#endif

