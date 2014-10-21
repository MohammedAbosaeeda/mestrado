// EPOS-- SoftMIPS Ethernet NIC Mediator Declarations

#ifndef __softmips_ethernet_h
#define __softmips_ethernet_h

__BEGIN_SYS

class Ethernet: public Ethernet_NIC
{
private:
    static const unsigned int UNITS =
	Traits<SoftMIPS_NIC>::ETHERNET_UNITS;

    // Share control and interrupt dispatiching info
    struct Device
    {
	Ethernet * device;
	unsigned int interrupt;
	bool in_use;
    };
	
public:
    Ethernet(unsigned int unit = 0);
    ~Ethernet();

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
    Ethernet(unsigned int unit, int io_port, int irq, void * dma);

    void handle_int();

    static void int_handler(unsigned int interrupt);

    static Ethernet * get(unsigned int interrupt) {
	for(unsigned int i = 0; i < UNITS; i++)
	    if(_devices[i].interrupt == interrupt)
		return _devices[i].device;
	return 0;
    };

private:
    unsigned int _unit;

    Address _address;
    Statistics _statistics;

    int _irq;
    void * _dma_buf;

    static Device _devices[UNITS];
};

__END_SYS

#endif
