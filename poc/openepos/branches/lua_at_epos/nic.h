// EPOS-- Network Interface Mediator Common Package

#ifndef __nic_h
#define __nic_h

#include <system/config.h>
#include <utility/debug.h>
#include <utility/string.h>
#include <cpu.h>	
#include <utility/list.h>
#include <utility/crc.h>

__BEGIN_SYS

class NIC_Common
{
protected:
    NIC_Common() {}

public:
    template<unsigned int LENGTH>
    class Meta_Address {
    private:
	typedef unsigned char String[LENGTH];

    public:
	Meta_Address() {}

	Meta_Address(const Meta_Address & a): _address(a) {}

// 	Meta_Address(const unsigned char a[LENGTH]) {
// 	    memcpy(_address, a, LENGTH);
// 	}

	Meta_Address(unsigned char a0, unsigned char a1 = 0,
		     unsigned char a2 = 0, unsigned char a3 = 0,
		     unsigned char a4 = 0, unsigned char a5 = 0,
		     unsigned char a6 = 0, unsigned char a7 = 0)
	{
	    _address[0] =  a0;
	    if(LENGTH > 1) _address[1] = a1;
	    if(LENGTH > 2) _address[2] = a2;
	    if(LENGTH > 3) _address[3] = a3;
	    if(LENGTH > 4) _address[4] = a4;
	    if(LENGTH > 5) _address[5] = a5;
	    if(LENGTH > 6) _address[6] = a6;
	    if(LENGTH > 7) _address[7] = a7;
	}

	operator bool() { 
	    return _address[0] 
		|| (LENGTH > 1) ? _address[1] : 0
		|| (LENGTH > 2) ? _address[2] : 0
		|| (LENGTH > 3) ? _address[3] : 0
		|| (LENGTH > 4) ? _address[4] : 0
		|| (LENGTH > 5) ? _address[5] : 0
		|| (LENGTH > 6) ? _address[6] : 0
		|| (LENGTH > 7) ? _address[7] : 0;
	}

	friend Debug & operator << (Debug & db, const Meta_Address & a) {
	    db << hex;
	    for(int i = 0; i < LENGTH; i++) {
		db << a.b[i];
		if(i < (LENGTH - 1))
		    db << ":";
	    }
	    db << dec;
	    return db;
	}

    public:
	unsigned char _address[LENGTH];
    };
    //    static const unsigned long long BROADCAST = ~0;

    typedef Meta_Address<6> Address;
    typedef unsigned short Protocol;

    struct Statistics {
	Statistics() : rx_packets(0), tx_packets(0), 
		       rx_bytes(0), tx_bytes(0) {}

	unsigned int rx_packets;
	unsigned int tx_packets;
	unsigned int rx_bytes;
	unsigned int tx_bytes;
    };

    // Polymorphic (or not) NIC wrapper
    class NIC_Base {
    public:
	NIC_Base(unsigned int unit = 0) {}
	virtual ~NIC_Base() {}
    
	virtual int send(const Address & dst, const Protocol & prot, 
			 const void * data, unsigned int size) = 0; 
	virtual int receive(Address * src, Protocol * prot,
			    void * data, unsigned int size) = 0;
    
	virtual void reset() = 0;
    
	virtual unsigned int mtu() = 0;

	virtual const Address & address() = 0;

	virtual const Statistics & statistics() = 0;
    };

    template<typename NIC, bool polymorphic>
    class NIC_Wrapper: public NIC_Base, private NIC {
    public:
	NIC_Wrapper(unsigned int unit = 0): NIC(unit) {}
	virtual ~NIC_Wrapper() {}

	virtual int send(const Address & dst, const Protocol & prot, 
			 const void * data, unsigned int size) {
	    return NIC::send(dst, prot, data, size); 
	}
	virtual int receive(Address * src, Protocol * prot,
			    void * data, unsigned int size) {
	    return NIC::receive(src, prot, data, size); 
	}
    
	virtual void reset() { NIC::reset(); }
    
	virtual unsigned int mtu() { return NIC::mtu(); }

	virtual const Address & address() { return NIC::address(); }

	virtual const Statistics & statistics() { return NIC::statistics(); }
    };

    template<typename NIC>
    class NIC_Wrapper<NIC, false>: public NIC {
    public:
	NIC_Wrapper(unsigned int unit = 0): NIC(unit) {}
    };

    template<typename NICS>
    class Meta_NIC {
    private:
	static const bool polymorphic = NICS::Polymorphic;

    public:
	typedef 	
	typename IF<polymorphic,
		    NIC_Base, 
		    typename NICS::template Get<0>::Result>::Result Base;

	template<int Index>
	struct Get
	{ 
	    typedef
	    NIC_Wrapper<typename NICS::template Get<Index>::Result,
			polymorphic> Result;
	};
    };

public:
    // Abstract class to be implemented by NIC's observers
    class Observer {
    public:
	virtual void received(void * data, unsigned int size) = 0;
	virtual ~Observer() {}
    };

private:
    class Entry {
    public:
	Entry(const Protocol &prot, Observer &obs): 
	    _prot(prot), _o(obs), _link(this) {}

	Protocol _prot;
	Observer &_o;
	Simple_List<Entry>::Element _link;
    };

    static Simple_List<Entry> _nic_observers;

public:
    // Method to be called when a NIC Observer wants to subscribe itself
    // to receive packets sent to some protocol number (e.g. IP, ARP)
    static void attach(const Protocol & prot, Observer & o) { 
	_nic_observers.insert(&(new Entry(prot,o))->_link);
	db<NIC_Common>(INF) << "NIC_Common: Observer Attached ";
	db<NIC_Common>(INF) << "Protocol: " << prot << "\n";
    }

    // To be called by the NIC when a packet is received.
    // This method demultiplex the message according to the protocol number, 
    // delivering it to the right Observer.
    static void notify(const Protocol & prot, void * data, int size) {
	Simple_List<Entry>::Element * e = _nic_observers.head();
	unsigned char calls = 0;
	for(; e; e=e->next())
	    if(e->object()->_prot == prot) {
		calls++;
		e->object()->_o.received(data, size);
	    }
	db<NIC_Common>(INF) << "NIC::Commom::notify() " << calls 
			    << " handler(s) called for protocol "
			    << prot << "\n";	
    }
};

class Ethernet: public NIC_Common
{
protected:
    Ethernet() {}

public:
    static const unsigned int MTU = 1500;
    static const unsigned int HEADER_SIZE = 14;

    // Ethernet data structures
    typedef Meta_Address<6> Address;
    typedef unsigned short Protocol;
    typedef char PDU[MTU];
    typedef unsigned int CRC;
    static const Address BROADCAST; //(0xff,0xff,0xff,0xff, 0xff, 0xff);
    static const Address NULL; //(0xff,0xff,0xff,0xff, 0xff, 0xff);

    // The Ethernet Frame (RFC 894)
    class Frame {
    public:
	Frame(const Address & src, const Address & dst,
	      const Protocol & prot): _src(src), _dst(dst), _prot(prot) { }

	Frame(const Address & src, const Address & dst,
	      const Protocol & prot, const void * data, unsigned int size)
	    : _src(src), _dst(dst), _prot(prot)
	{
	    memcpy(_data, data, size);
	}
	
	friend Debug & operator << (Debug & db, const Frame & f) {
	    db << "{" << Address(f._dst)
	       << "," << Address(f._src)
	       << "," << f._prot
	       << "," << f._data << "}";
	    return db;
	}
	
    public:
	Address _src;
	Address _dst;
	Protocol _prot;
	PDU _data;
	CRC _crc;
    };

    // Some valid Ethernet frame types
    enum {
	IP   = 0x0800,
	ARP  = 0x0806,
	RARP = 0x8035
    };

    // Meaningful statistics for Ethernet
    struct Statistics: public NIC_Common::Statistics {
	Statistics() : rx_overruns(0), tx_overruns(0), frame_errors(0),
		       carrier_errors(0), collisions(0) {}

	friend Debug & operator << (Debug & db, const Statistics & s) {
	    db << "{rxp=" << s.rx_packets
	       << ",rxb=" <<  s.rx_bytes
	       << ",rxorun=" <<  s.rx_overruns
	       << ",txp=" <<  s.tx_packets
	       << ",txb=" <<  s.tx_bytes
	       << ",txorun=" <<  s.tx_overruns
	       << ",frm=" <<  s.frame_errors
	       << ",car=" <<  s.carrier_errors
	       << ",col=" <<  s.collisions
	       << "}";
	    return db;
	}
	
	unsigned int rx_overruns;
	unsigned int tx_overruns;
	unsigned int frame_errors;
	unsigned int carrier_errors;
	unsigned int collisions;
    };
};

class Low_Power_Radio: public NIC_Common
{
protected:
    Low_Power_Radio() {}

public:
    static const unsigned int MTU = 24;  
    static const unsigned int HEADER_SIZE = 6;
    static const unsigned int TRAILER_SIZE = 2;

    typedef unsigned char Address;
    typedef unsigned char Protocol;

    class Frame {
    public:
	Frame(){}

	Frame(const Address & dst, const Address & src,
	      const Protocol & prot, const unsigned char & size) {
	    header(dst, src, prot, size);
	}

	Frame(const Address & dst, const Address & src,
	      const Protocol & prot, const void * data, 
	      const unsigned char & size)
	{
	    header(dst, src, prot, size);
	    memcpy(_data, data, size);
	    _crc = CRC::crc16((char*)this, sizeof(Frame)-2);
	}
	
	Frame(const Address & dst, const Address & src,
	      const Protocol & prot, const void * data, 
	      const unsigned char & size, 
	      const char & tx_pow, const char & rss)
	{
	    header(dst, src, prot, size);
	    memcpy(_data, data, size);
	    _tx_pow = tx_pow;
	    _rss = rss;
	    _crc = CRC::crc16((char*)this, sizeof(Frame)-2);
	}

	friend Debug & operator << (Debug & db, const Frame & f) {
	    db << "{" << Address(f._dst)
	       << "," << Address(f._src)
	       << "," << f._prot
	       << "," << f._len
	       << "," << f._data << "}";
	    return db;
	}
	
    private:
	void header(const unsigned char & dst, const unsigned char & src,
		    const unsigned char & prot, const unsigned char & len) {
	    _src = src;
	    _dst = dst;
	    _prot = prot;
	    _len = len;
	}

    public:
	unsigned char _src;
	unsigned char _dst;
	unsigned char _prot;
	unsigned char _len;
	unsigned char _tx_pow;
	unsigned char _rss;
	char _data[MTU];
	unsigned short _crc;
    };

    // Frame types
    enum {
	EPOS_LP = 0x01,
    };

    struct Statistics: public NIC_Common::Statistics {
	Statistics() : dropped_packets(0) {}

	friend Debug & operator << (Debug & db, const Statistics & s) {
	    db << "{rxp=" << s.rx_packets
	       << ",rxb=" <<  s.rx_bytes
	       << ",txp=" <<  s.tx_packets
	       << ",txb=" <<  s.tx_bytes
	       << ",drop=" <<  s.dropped_packets
	       << "}";
	    return db;
	}
	
	unsigned int dropped_packets;
    };

};

class Protocol_Common {
public:
    typedef CPU::Reg8  u8;
    typedef CPU::Reg16 u16;
    typedef CPU::Reg32 u32;
    typedef CPU::Reg64 u64;
};

__END_SYS

#ifdef __NIC_H
#include __NIC_H
#endif

#endif
