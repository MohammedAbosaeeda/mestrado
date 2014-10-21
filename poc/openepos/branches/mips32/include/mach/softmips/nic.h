// EPOS-- SoftMIPS NIC Mediator Declarations

#ifndef __softmips_nic_h
#define __softmips_nic_h

#include "ethernet.h"

__BEGIN_SYS

class SoftMIPS_NIC: public NIC_Common
{
private:
    typedef Traits<SoftMIPS_NIC>::NICS NICS;
    static const unsigned int UNITS = NICS::Length;

public:
    SoftMIPS_NIC() {
	_dev = new Meta_NIC<NICS>::Get<0>::Result;
    }
    template<unsigned int UNIT>
    SoftMIPS_NIC(unsigned int u) {
	_dev = new typename Meta_NIC<NICS>::Get<UNIT>::Result(UNIT);
    }
    ~SoftMIPS_NIC() {
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

    static int init(System_Info * si) { return 0; }

private:
    Meta_NIC<NICS>::Base * _dev;
};

__END_SYS

#endif
