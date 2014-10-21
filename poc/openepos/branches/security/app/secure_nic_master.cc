#include <periodic_thread.h>
#include <gpio_pin.h>
#include <ptp.h>
#include <utility/observer.h>
#include <secure_nic.h>
#include <alarm.h>
#include <diffie_hellman.h>
#include <bignum.h>
#include <poly1305.h>
#include <mach/mc13224v/aes_controller.h>
#include <cipher.h>
#include <chronometer.h>

//------------------------------------------------------
// Model application for the Secure_NIC
// For more information, refer to doc/security/howto.pdf
//------------------------------------------------------

#define SERVER_ADDR NIC::Address(40)

__USING_SYS
OStream cout;

NIC * nic;
PTP * ptp;

// 128-bit key parameters
const unsigned char Diffie_Hellman::default_base_point_x[] = {134, 91, 44, 165, 124, 96, 40, 12, 45, 155, 137, 139, 82, 247, 31, 22};
const unsigned char Diffie_Hellman::default_base_point_y[] = {131, 122, 237, 221, 146, 162, 45, 192, 19, 235, 175, 91, 57, 200, 90, 207};
const unsigned int Bignum::default_mod[4] = {4294967295U, 4294967295U, 4294967295U, 4294967293U};
const unsigned int Bignum::default_barrett_u[5] = {17,8,4,2,1};
/*
// 160-bit key parameters
const unsigned char Diffie_Hellman::default_base_point_x[] = {
	0x82, 0xFC, 0xCB, 0x13, 0xB9, 0x8B, 0xC3, 0x68, 
	0x89, 0x69, 0x64, 0x46, 0x28, 0x73, 0xF5, 0x8E, 
	0x68, 0xB5, 0x96, 0x4A, 
};
const unsigned char Diffie_Hellman::default_base_point_y[] = {
	0x32, 0xFB, 0xC5, 0x7A, 0x37, 0x51, 0x23, 0x04,
	0x12, 0xC9, 0xDC, 0x59, 0x7D, 0x94, 0x68, 0x31,
	0x55, 0x28, 0xA6, 0x23,
};
const unsigned int Bignum::default_mod[5] = {2147483647U, 4294967295U, 4294967295U, 4294967295U, 4294967295U};
const unsigned int Bignum::default_barrett_u[6] = {2147483649, 0, 0, 0, 0, 1};
*/
/*
// 192-bit key parameters
const unsigned char Diffie_Hellman::default_base_point_x[] = {
	0x12, 0x10, 0xFF, 0x82, 0xFD, 0x0A, 0xFF, 0xF4,
	0x00, 0x88, 0xA1, 0x43, 0xEB, 0x20, 0xBF, 0x7C,
	0xF6, 0x90, 0x30, 0xB0, 0x0E, 0xA8, 0x8D, 0x18,
};
const unsigned char Diffie_Hellman::default_base_point_y[] = {
	0x11, 0x48, 0x79, 0x1E, 0xA1, 0x77, 0xF9, 0x73,
	0xD5, 0xCD, 0x24, 0x6B, 0xED, 0x11, 0x10, 0x63,
	0x78, 0xDA, 0xC8, 0xFF, 0x95, 0x2B, 0x19, 0x07,
};
const unsigned int Bignum::default_mod[6] = {4294967295U, 4294967295U, 4294967294U, 4294967295U, 4294967295U, 4294967295U};
const unsigned int Bignum::default_barrett_u[7] = {1, 0, 1, 0, 0, 0, 1};
*/

class Secure_NIC_Listener : public Conditional_Observer
{
	public:
	Secure_NIC_Listener(Secure_NIC * s)
	{
		_s = s;
		_s->attach(this, Traits<Secure_NIC>::PROTOCOL_ID);
	}

	// This method will be called when a secure message arrives.
	// The message is delivered to the application already decrypted.
	void update(Conditionally_Observed * o, int p)
	{
		int sz = _s->receive(from, msg, 32);
		if (sz<=0) return;
		cout << "Received encrypted: "<< msg << '\n';
		cout<<"Sending hi\n";
		// Reply securely
		_s->send(from, "Hi Sensor!",11);
	}

	private:
	Secure_NIC * _s;
	NIC::Address from;
	char msg[32];
};

void led_rgb_r(bool on_off=true)
{
	GPIO_Pin led(10);
	led.put(on_off);
}

// Send a PTP synchronization message
int sendSyncMessage(){
	while(true)
	{
		cout << "Sending sync message" << endl;
		led_rgb_r(true);
		ptp->doState();
		led_rgb_r(false);
		Periodic_Thread::wait_next();
	}
}
int sink()
{
	cout << "Sink running \n";
	cout << "Key size: " << Secure_NIC::SECRET_SIZE << endl;
	cout << "ID size: " << Secure_NIC::ID_SIZE << endl;

	// Initialize PTP
	// A sync message will be sent every _sync_interval
	ptp->_ptp_parameters._sync_interval = 30000000; //microsecond
	// This is a master node, which will send sync messages
	ptp->_ptp_parameters._clock_stratum = PTP::PTP_DataSet::STRATUM_MASTER;
	ptp->_ptp_parameters._original_clock_stratum = PTP::PTP_DataSet::STRATUM_MASTER;
	ptp->_ptp_parameters._state = PTP::INITIALIZING;
	ptp->execute();
	
	Pseudo_Random::seed(127);
	
	// Initialize the secure NIC
	Secure_NIC * s = new Secure_NIC(true, new AES_Controller(), new Poly1305(new AES_Controller(Cipher::CBC)), nic);

	// This ID is to be trusted
	char id[Secure_NIC::ID_SIZE];
	for(int i=0;i<sizeof(id);i++)
		id[i] = '1';
	s->insert_trusted_id(id);

	// This ID is to be trusted
	for(int i=0;i<sizeof(id);i++)
		id[i] = '2';
	s->insert_trusted_id(id);

	// Start accepting authentication requests
	s->accepting_connections = true;

	// Listen to secure messages
	Secure_NIC_Listener * l = new Secure_NIC_Listener(s);

	// Thread to send PTP sync messages
	Periodic_Thread * sync_messenger = new Periodic_Thread(&sendSyncMessage, ptp->_ptp_parameters._sync_interval);//, -1, Thread::READY, 1300);

	cout << "Done!\n";
	sync_messenger->join();
	return 0;
}

int main()
{
	nic = new NIC();
	ptp = new PTP();
	ptp->setNIC(nic);

	nic->address(SERVER_ADDR);
	cout << "Address: " << nic->address() << '\n';
	sink();
	return 0;
}
