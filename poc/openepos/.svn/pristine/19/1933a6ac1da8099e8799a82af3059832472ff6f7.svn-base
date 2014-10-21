#include <modbus_ascii.h>
#include <periodic_thread.h>
#include <ptp.h>
#include <utility/observer.h>
#include <secure_nic.h>
#include <diffie_hellman.h>
#include <bignum.h>
#include <poly1305.h>
#include <mach/mc13224v/aes_controller.h>
#include <active.h>
#include <nic.h>
#include <uart.h>

#define SERVER_ADDR NIC::Address(40)

char trusted_ids[] = {0xA0,0xA1,0xA2,0xA3,0xA4,0xA5,0xA6,0xA7,0xA8,0xA9};

__USING_SYS
PTP * ptp;

// 128-bit key parameters
const unsigned char Diffie_Hellman::default_base_point_x[] = {134, 91, 44, 165, 124, 96, 40, 12, 45, 155, 137, 139, 82, 247, 31, 22};
const unsigned char Diffie_Hellman::default_base_point_y[] = {131, 122, 237, 221, 146, 162, 45, 192, 19, 235, 175, 91, 57, 200, 90, 207};
const unsigned int Bignum::default_mod[4] = {4294967295U, 4294967295U, 4294967295U, 4294967293U};
const unsigned int Bignum::default_barrett_u[5] = {17,8,4,2,1};

NIC *nic;
UART uart;

class Receiver : public Conditional_Observer, public Active
{
	public:
	Receiver(Secure_NIC * s)
	{
		_s = s;
		_s->attach(this, Traits<Secure_NIC>::PROTOCOL_ID);
	}

	// Workaround for CMAC
	virtual int run()
	{
		while(true)
			yield();
	}

	void update(Conditionally_Observed * o, int p)
	{
		NIC::Address from;
		char msg[Modbus_ASCII::MSG_LEN];
		memset(msg, 0x00, Modbus_ASCII::MSG_LEN);
		int sz = _s->receive(from, msg, Modbus_ASCII::MSG_LEN);
		if (sz<=0) return;
		for(int i=0; i<sz; i++)
			uart.put(msg[i]);
	}
	private:
	Secure_NIC * _s;
};

class Sender : public Active
{
public:
	Sender(Secure_NIC * n) {_nic = n;}
	virtual ~Sender() {}

	virtual int run()
	{
	    int i;
		while(true)
		{
			i = 0;
			while(!uart.has_data()) yield();
			_msg[i++] = uart.get();
			while(!uart.has_data()) yield();
			_msg[i++] = uart.get();
	        while(!(_msg[i-2] == '\r' && _msg[i-1] == '\n')) {
				while(!uart.has_data()) yield();
	        	_msg[i++] = uart.get();
	        }
			memset(_msg+i, 0x00, Modbus_ASCII::MSG_LEN-i);
	        int r;
	        while ((r = _nic->send(NIC::Address(Modbus_ASCII::decode(_msg[1],_msg[2])),(char *)_msg, Modbus_ASCII::MSG_LEN)) != 11)
	            yield();
		}

		return 0;
	}

private:
    unsigned char _msg[Modbus_ASCII::MSG_LEN];
	Secure_NIC * _nic;
};

int send_sync_message(PTP * ptp)
{
	while(true)
	{
		kout << "Sending PTP sync message..." << endl;
		ptp->doState();
		kout << "PTP sync message sent" << endl;
		Periodic_Thread::wait_next();
	}
}

int main()
{
    nic = new NIC();
	ptp = new PTP();
	ptp->setNIC(nic);
	nic->address(SERVER_ADDR);
	Secure_NIC * s = new Secure_NIC(true, new AES_Controller(), new Poly1305(new AES_Controller(Cipher::CBC)), nic);

	ptp->_ptp_parameters._sync_interval = 30000000; //microsecond
	ptp->_ptp_parameters._clock_stratum = PTP::PTP_DataSet::STRATUM_MASTER; //Master
	ptp->_ptp_parameters._original_clock_stratum = PTP::PTP_DataSet::STRATUM_MASTER;
	ptp->_ptp_parameters._state = PTP::INITIALIZING;	

	for(int i=0; i<sizeof trusted_ids; i++)
	{
		char id[2];
		Modbus_ASCII::encode(trusted_ids[i], &id[0], &id[1]);
		s->insert_trusted_id(id);
	}
	s->accepting_connections = true;

	ptp->execute();
	Periodic_Thread * sync_messenger = new Periodic_Thread(&send_sync_message, ptp, ptp->_ptp_parameters._sync_interval);//, -1, Thread::READY, 1800);

    Receiver receiver(s);
    Sender sender(s);

    sender.start();

    sender.join();
	sync_messenger->join();

    return 0;
}
