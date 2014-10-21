#ifndef __tcp_h
#define __tcp_h

#include <alarm.h>
#include <arp.h>
#include <chronometer.h>
#include <ip.h>
#include <mutex.h>
#include <utility/list.h>
#include <semaphore.h>
#include <nic.h>
#include <udp.h>
#include <utility/random.h>
#include <system/config.h>

#define MIN(x,y) ((x) < (y) ? (x) : (y))

// alteração do estado da conexão
#define CHANGE_STATE(x) _state = x ; state_handler = &TCP::Socket::__ ## x ;

__BEGIN_SYS

class TCP : public IP::Observer, public Protocol_Common {
public:
	typedef UDP::Address Address;
	class Socket;
	class Header;
	class PDU;

	static TCP::Socket * connect(const Address &remote); // Factory-like pattern
	static TCP::Socket * listen(const Address &local);   // idem
	static void init();

	static const int ERR_NOT_CONNECTED = -1;
	static const int ERR_TIMEOUT = -2;
	static const int ERR_RESET = -3;
	static const int ERR_CLOSING = -4;
	static const int ERR_NO_ROUTE = -5;
	static const int ERR_NOT_STARTED = -6;
	static const int ERR_REFUSED = -7;

	/* Daqui em diante todos os elementos são de uso interno */

	static const unsigned int ID_TCP = 6;
	
protected:
	void received(u32 src, void *data, u16  size);

	TCP();
	~TCP();

	// static stuff here
	static TCP* manager;
	Mutex _big_lock; // protege os dados compartilhados entre todos os objetos TCP
	NIC  *_nic;
	ARP  *_arp;
	IP   *_ip;
	Simple_List<Socket> _busy;

	bool busy(int);
	u16 get_free_port();
};

class TCP::Socket {
	friend class TCP;

	public:
	void close();
	int  read(char *data,unsigned int size);
	int  send(const char *data,unsigned int size);
	int  status() { return _state; }
	~Socket();

	protected:
	bool connect_helper();
	bool listen_helper();
	
	void mac_update();
	void send_ack();
	void send_fin();
	void sendPDU(PDU& pdu,u16 data_len);
	void wait();
	void signal();
	void enter();
	void leave();
	void set(char* b,u16 s) { buffer=b; buffer_size=s; complete=0;};

	Socket(const Address &remote,const Address &local);

	// state-processing functions
	void __LISTEN(const Header&,const char*,u16);
	void __SYN_SENT(const Header&,const char*,u16);
	void __SYN_RCVD(const Header&,const char*,u16);
	void __ESTABLISHED(const Header&,const char*,u16);
	void __FIN_WAIT1(const Header&,const char*,u16);
	void __FIN_WAIT2(const Header&,const char*,u16);		
	void __CLOSE_WAIT(const Header&,const char*,u16);
	void __CLOSING(const Header&,const char*,u16);
	void __LAST_ACK(const Header&,const char*,u16);
	void __TIME_WAIT(const Header&,const char*,u16);
	void __CLOSED(const Header&,const char*,u16) {}

	void __SNDING(const Header&,const char*,u16);
	void __RCVING(const Header&,const char*,u16);
	bool check_seq(const Header&,u16);

	// atributes
	Mutex _lock;
	Semaphore _sync;
	RTC::Microsecond rtt;
	Chronometer _counter;
	volatile bool signaled;
	volatile int _error;

	TCP::Address _remote, _local;
	NIC::Address _dst_mac;

	char *buffer;
	volatile u16 buffer_size, complete;
	volatile u32 snd_una, snd_nxt, snd_ini, snd_wnd; 
	volatile u32 rcv_una, rcv_nxt, rcv_ini, rcv_wnd; 

	Simple_List<Socket>::Element _link;

	//short _state;	
	enum {
		LISTEN,		SYN_SENT,
		SYN_RCVD,	ESTABLISHED,
		FIN_WAIT1,	FIN_WAIT2,		
		CLOSE_WAIT,	CLOSING,
		LAST_ACK,	TIME_WAIT,
		CLOSED,
	} volatile _state;

	enum { 
		IDLE,
		SNDING,
		RCVING
	} volatile _transfer;

	inline volatile bool in_state(short s) { return _state == s; }

	void (Socket::* state_handler)(const Header&,const char*,u16);	
};


class TCP::Header {
	public:
	void init();
	void init(u32 s, u32 a);

	u16 _src_port, _dst_port;
	u32 _seq_num, _ack_num;
	#ifndef BIG_ENDIAN
	u8  _un1:4, _hdr_off:4;
	bool  _fin:1, _syn:1, _rst:1, _psh:1, _ack:1, _urg:1;
	bool  _un2:1, _un3:1; 
	#else
	u8  _hdr_off:4, _un1:4; 
	bool  _un2:1, _un3:1; 
	bool  _urg:1, _ack:1, _psh:1, _rst:1, _syn:1, _fin:1;
	#endif
	u16 _wnd, _chksum, _urgptr;

	unsigned int size() const { return _hdr_off * 4; }
	inline u16 dst_port() const { return CPU::ntohs(_dst_port); }
	inline u16 src_port() const { return CPU::ntohs(_src_port); }
	inline u32 seq_num() const { return CPU::ntohl(_seq_num); }
	inline u32 ack_num() const { return CPU::ntohl(_ack_num); }
	inline u16 wnd() const { return CPU::ntohs(_wnd); }
	inline u16 chksum() const { return CPU::ntohs(_chksum); }
	inline u16 urgptr() const { return CPU::ntohs(_urgptr); }
	inline void seq_num(u32 v) { _seq_num = CPU::htonl(v); }
	inline void ack_num(u32 v) { _ack_num = CPU::htonl(v); }
	inline void wnd(u16 v) { _wnd = CPU::htons(v); }
	inline void chksum(u16 v) { _chksum = CPU::htons(v); }
	inline void urgptr(u16 v) { _urgptr = CPU::htons(v); }
	inline void dst_port(u16 v) { _dst_port = CPU::htons(v); }
	inline void src_port(u16 v) { _src_port = CPU::htons(v); }
	u16 _checksum(IP::Address &src,IP::Address &dst,unsigned len);
	void checksum(IP::Address &src,IP::Address &dst,unsigned len);
	bool validate_checksum(IP::Address &src,IP::Address &dst,unsigned len);
	// Ultimate unreadable coding style!
	friend Debug& operator<< (Debug & db, const Header& s)
	{
	db << "Header[SRC="<<s.src_port()<<",DST="<<s.dst_port()<<"] SEQ="<<s.seq_num()<<
	",ACK="<<s.ack_num()<<",off="<<s._hdr_off<<
	" CTL=["<<(s._urg ? "U" : "") <<(s._ack ? "A" : "") <<
	(s._psh ? "P" : "") <<(s._rst ? "R" : "") <<(s._syn ? "S" : "") <<
	(s._fin ? "F" : "") <<"],wnd="<<s.wnd()<<",chk="<<s.chksum()<<"]\n";
	return db;
	}

	// Pseudo header for checksum calculations
    	struct Pseudo_Header {
		u32 src_ip, dst_ip;
		u8 zero, protocol;
		u16 length;
	};
} __attribute__((packed));

class TCP::PDU {
	public:
	static PDU& grab();
	void release();
	TCP::Header& getTCP() { return _tcp; }
	IP::Header&  getIP() { return _ip; }
	char*        getData() { return _data; }	

	private:
	IP::Header _ip;
	TCP::Header _tcp;
	char _data[512];

	static PDU* instance;
	static Mutex* mutex;

	PDU() {}
	~PDU() {}
	PDU& operator= (PDU& p);
};


__END_SYS

#endif

