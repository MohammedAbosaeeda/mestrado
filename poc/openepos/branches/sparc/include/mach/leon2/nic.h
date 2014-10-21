// EPOS-- LEON2 NIC Mediator Declarations

#ifndef __leon2_nic_h
#define __leon2_nic_h

#include "ethernet.h"

__BEGIN_SYS

class LEON2_NIC: public Ethernet_NIC
{
private:
    typedef Traits<LEON2_NIC>::NICS NICS;
    static const unsigned int UNITS = NICS::Length;

public:
//     LEON2_NIC() {
// 	_dev = new Meta_NIC<NICS>::Get<0>::Result;
//     }
//     template<unsigned int UNIT>
//     LEON2_NIC(unsigned int u) {
// 	_dev = new typename Meta_NIC<NICS>::Get<UNIT>::Result(UNIT);
//     }
//     ~LEON2_NIC() {
// 	delete _dev;
//     }
    
//     int send(const Address & dst, const Protocol & prot, 
// 	     const void * data, unsigned int size) {
// 	return _dev->send(dst, prot, data, size); 
//     }
//     int receive(Address * src, Protocol * prot,
// 		void * data, unsigned int size) {
// 	return _dev->receive(src, prot, data, size); 
//     }
    
//     void reset() { _dev->reset(); }

//     unsigned int mtu() const { return _dev->mtu(); }
    
//     const Address & address() { return _dev->address(); }

//     const Statistics & statistics() { return _dev->statistics(); }
    LEON2_NIC();
    template<unsigned int UNIT>
    LEON2_NIC(unsigned int u);
    ~LEON2_NIC();
    
    int send(const Address & dst, const Protocol & prot, 
	     const void * data, unsigned int size) ;
    int receive(Address * src, Protocol * prot,
		void * data, unsigned int size);
    
    void reset();

    unsigned int mtu() const;
    
    const Address & address();

    const Statistics & statistics();

    static int init(System_Info * si);

private:
    Meta_NIC<NICS>::Base * _dev;
};

__END_SYS

#endif
