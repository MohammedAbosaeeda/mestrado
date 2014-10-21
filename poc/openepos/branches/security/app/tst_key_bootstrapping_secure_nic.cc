//#include <utility/observer.h>
//#include <secure_nic.h>
//#include <alarm.h>
//#include <diffie_hellman.h>
//#include <bignum.h>
//#include <poly1305.h>
//#include <mach/mc13224v/aes_controller.h>
//#include <cipher.h>
//#include <chronometer.h>
//#include <key_database.h>
#include <thread.h>

//#define SINK_NODE

#define SERVER_ADDR NIC::Address(42)

__USING_SYS

/*
const unsigned char Diffie_Hellman::default_base_point_x[] = {134, 91, 44, 165, 124, 96, 40, 12, 45, 155, 137, 139, 82, 247, 31, 22};
const unsigned char Diffie_Hellman::default_base_point_y[] = {131, 122, 237, 221, 146, 162, 45, 192, 19, 235, 175, 91, 57, 200, 90, 207};
const unsigned int Bignum::default_mod[4] = {4294967295U, 4294967295U, 4294967295U, 4294967293U};
const unsigned int Bignum::default_barrett_u[5] = {17,8,4,2,1};
*/
/*
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
const unsigned long Bignum::default_mod[6] = {4294967295U, 4294967295U, 4294967294U, 4294967295U, 4294967295U, 4294967295U};
const unsigned long Bignum::default_barrett_u[7] = {1, 0, 1, 0, 0, 0, 1};
*/
 
/*
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


int sensor(NIC * nic)
{
	OStream cout;
	cout << "Sensor running \n";

	nic->address(NIC::Address(20));
	cout << "Sensor still running \n";
	while(1);
	/*
	cout << "Address: " << nic->address() << '\n';

	Chronometer c;
	cout << "Key size: " << Secure_NIC::SECRET_SIZE << endl;
	cout << "ID size: " << Secure_NIC::ID_SIZE << endl;
	c.start();
	NIC::Address from;
	char msg[32];
	Pseudo_Random::seed(100);
	Secure_NIC s(new AES_Controller(), new Poly1305(new AES_Controller(Cipher::CBC)));

	cout << "Trying to negotiate key\n";

	s.send_key_request(SERVER_ADDR);
	while(!s.authenticated());
	c.stop();

	cout << "Key set!\nTotal time: " << c.read() << endl;

	for(volatile unsigned int i=0;i<0x200000;i++);

	cout << "Sending hi\n";
	s.send(SERVER_ADDR, "Hi, server!", 11);
	cout << "Receiving\n";
	do
	{
		s.receive(from,msg,16);
	} while(!(from == SERVER_ADDR));

	cout << "Received: "<< msg << '\n';

	cout << "Done!\n";
	*/
	return 0;
}

int sink(NIC * nic)
{
	/*
	OStream cout;
	nic->address(SERVER_ADDR);
	cout << "Address: " << nic->address() << '\n';

	NIC::Address from;
	char msg[32];
	cout << "Sink running \n";
	Pseudo_Random::seed(127);
	Secure_NIC s(new AES_Controller(), new Poly1305(new AES_Controller(Cipher::CBC)));
	cout << "Key size: " << s.SECRET_SIZE << endl;
	cout << "ID size: " << s.ID_SIZE << endl;
	s.accepting_connections = true;
	int sz;
	while(true)
	{
		do
		{
			sz = s.receive(from, msg, 32);
		} while(sz<=0);
		cout << "Received encrypted: "<< msg << '\n';
		//	for(volatile unsigned int i=0;i<0x200000;i++);
		cout<<"Sending hi\n";
		s.send(from, "Hi Sensor!",10);
	}

	cout << "Done!\n";
	return 0;
	*/
}

int main()
{
//	Alarm::delay(3000000); cout<<"Delay done"<<endl;
	NIC * nic = new NIC();
//#ifdef SINK_NODE
//	Thread * t = new Thread(&sink, nic);
//#else
	Thread * t = new Thread(&sensor, nic);
//#endif
	t->join();
	while(1);
}
