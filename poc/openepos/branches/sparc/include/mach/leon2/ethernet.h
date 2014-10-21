// EPOS-- LEON2 Ethernet NIC Mediator Declarations

#ifndef __leon2_ethernet_h
#define __leon2_ethernet_h

#include <nic.h>

__BEGIN_SYS

class LEON2_Ethernet: public Ethernet_NIC
{
private:
    static const unsigned int UNITS =
	Traits<LEON2_NIC>::ETHERNET_UNITS;

    // Share control and interrupt dispatiching info
    struct Device
    {
	LEON2_Ethernet * device;
	unsigned int interrupt;
	bool in_use;
    };
	
public:
    LEON2_Ethernet(unsigned int unit = 0);
    ~LEON2_Ethernet();

    int send(const Address & dst, const Protocol & prot,
 	     const void * data, unsigned int size);
    int receive(Address * src, Protocol * prot,
		void * data, unsigned int size);

    void reset();

    unsigned int mtu() { return MTU; }

    const Address & address() { return _address; }

    const Statistics & statistics() { return _statistics; }

    static int init(unsigned int unit, System_Info * si);

private:
    unsigned int _unit;

    Address _address;
    Statistics _statistics;

    static Device _devices[UNITS];
};

__END_SYS

#endif
