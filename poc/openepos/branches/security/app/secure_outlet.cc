#include <gpio_pin.h>
#include <ptp.h>
#include <utility/observer.h>
#include <secure_nic.h>
#include <diffie_hellman.h>
#include <bignum.h>
#include <poly1305.h>
#include <mach/mc13224v/aes_controller.h>
#include <modbus_ascii.h>
#include <nic.h>

#define SERVER_ADDR NIC::Address(40)

#define MY_ID 0xA6

__USING_SYS

NIC * nic;
PTP * ptp;
OStream cout;

// 128-bit key parameters
const unsigned char Diffie_Hellman::default_base_point_x[] = {134, 91, 44, 165, 124, 96, 40, 12, 45, 155, 137, 139, 82, 247, 31, 22};
const unsigned char Diffie_Hellman::default_base_point_y[] = {131, 122, 237, 221, 146, 162, 45, 192, 19, 235, 175, 91, 57, 200, 90, 207};
const unsigned int Bignum::default_mod[4] = {4294967295U, 4294967295U, 4294967295U, 4294967293U};
const unsigned int Bignum::default_barrett_u[5] = {17,8,4,2,1};

class Receiver : public Modbus_ASCII::Modbus_ASCII_Feeder, public Conditional_Observer
{
public:
	Receiver(Secure_NIC * n) : _nic(n) {}
	virtual ~Receiver() {}

	void update(Conditionally_Observed * o, int p)
	{
	    NIC::Address from;
		int sz = _nic->receive(from, _msg, Modbus_ASCII::MSG_LEN);
		if (sz<=0) return;
		Modbus_ASCII::Modbus_ASCII_Feeder::notify(_msg);
	}

	// to debug only
	void notify(const char * c)
	{
		Modbus_ASCII::Modbus_ASCII_Feeder::notify(c);
	}

private:
	Secure_NIC * _nic;
    char _msg[Modbus_ASCII::MSG_LEN];
};

class Sender : public Modbus_ASCII::Modbus_ASCII_Sender
{
public:
	Sender(Secure_NIC * n) : _nic(n) {}
	virtual ~Sender() {}

	virtual void send(char * c, int len)
	{
		memcpy(_msg, c, len);
		memset(_msg+len, 0x00, Modbus_ASCII::MSG_LEN-len);

        int r;
        while ((r = _nic->send(NIC::Address(Modbus_ASCII::decode(_msg[1],_msg[2])), _msg, Modbus_ASCII::MSG_LEN)) != 11)
            cout << "Send fail: " << r << "\n";
	}

private:
	Secure_NIC * _nic;
	char _msg[Modbus_ASCII::MSG_LEN];
};

class Modbus : public Modbus_ASCII
{
private:
	enum {
		LAMP0_PIN = 12,
		LAMP1_PIN = 13
//		LAMP0_PIN = 8,
//		LAMP1_PIN = 9
	};

public:
	Modbus(Modbus_ASCII_Sender * sender, unsigned char addr)
      : Modbus_ASCII(sender, addr)
     {
		 _temperature_sensor = new Temperature_Sensor();
		_gpio[0] = new GPIO_Pin(LAMP0_PIN);
		_gpio[1] = new GPIO_Pin(LAMP1_PIN);
     }

	virtual ~Modbus() {}

	virtual void handle_command(unsigned char cmd, unsigned char * data, int data_len)
	{
		kout << "received command: " << hex << (int)cmd;
		for (int i = 0; i < data_len; ++i)
			kout << (int)data[i];
		kout << dec;
		unsigned short coil, value;
		switch(cmd)
		{
		case WRITE_SINGLE_COIL:
			coil = (((unsigned short)data[0]) << 8) | data[1];
			value = (((unsigned short)data[2]) << 8) | data[3];
			write(coil, value);
			break;
		case READ_HOLDING_REGISTER:
			read(data[1]);
			break;
//		case WRITE_MULTIPLE_COILS:
//			for(int i = 0; i < data[0]; ++i)
//				write(data[1+i*2], data[2+i*2]);
//			break;
//		case READ_MULTIPLE_REGISTERS:
//			send(myAddress(), READ_MULTIPLE_REGISTERS, _state, 2);
//			break;
		}
	}
private:
	void write(unsigned short output, unsigned short value)
	{
		kout << "write: " << output << " , " << value << endl;
		if(value) _gpio[output]->set();
		else _gpio[output]->clear();
	}

	void read(unsigned char reg)
	{
		union
		{
			float f;
			unsigned char uc[sizeof(float)];
		} response;
		
		bool ok = false;

		switch(reg)
		{
//		case 0x00:
//			response.f = _sensor->co2();
//			break;
		case 0x04:
			response.f = _temperature_sensor->sample();
			ok = true;
			break;
//		case 0x08:
//			response.f = _sensor->humidity();
//			break;
//		case 0x0c:
//			response.f = _battery->charge();
		case 0x10:
			response.f = _temperature_sensor->sample();
			ok = true;
			break;
		}
		if(ok)
			send(myAddress(), READ_HOLDING_REGISTER, reg, response.uc, sizeof(float));
	}

	GPIO_Pin * _gpio[2];
	char _state[2];
    Temperature_Sensor * _temperature_sensor;
};

int main()
{
	cout << "Lamp actuator" << endl;
	nic = new NIC();
	ptp = new PTP();
	ptp->setNIC(nic);
	nic->address(MY_ID);
	ptp->_ptp_parameters._original_clock_stratum = PTP::PTP_DataSet::STRATUM_SLAVE;
	ptp->_ptp_parameters._clock_stratum = PTP::PTP_DataSet::STRATUM_SLAVE;
	ptp->_ptp_parameters._state = PTP::INITIALIZING;
	ptp->execute();

	char id[2];
	Modbus_ASCII::encode(MY_ID, &id[0], &id[1]);
	Pseudo_Random::seed(id[0]+id[1]);

	Secure_NIC * s = new Secure_NIC(false, new AES_Controller(), new Poly1305(new AES_Controller(Cipher::CBC)), nic);
	s->set_id(id);

	do
	{
		if(!s->authenticated())
		{
			cout << "Trying to negotiate key\n";
			// Try to authenticate
			s->send_key_request(SERVER_ADDR);
		}
		Alarm::delay(5000000 + (Pseudo_Random::random() % 10000000));
	}
	while(!s->authenticated());
	
	cout << "Key Set!\n";

    Receiver receiver(s);
    Sender sender(s);
    Modbus modbus(&sender, MY_ID);
    receiver.registerModbus(&modbus);

	Thread::exit();

    return 0;
}
