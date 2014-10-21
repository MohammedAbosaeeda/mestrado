#include <machine.h>
#include <alarm.h>
#include <sensor.h>
#include <utility/string.h>
#include <mutex.h>
#include <tsc.h>
#include <diffiehellman.h>
#include <utility/observer.h>
#include <utility/random.h>
#include <nic.h>
#include <tcp.h>
#include <network.h>
#include <string.h>
#include <chronometer.h>
#include <mach/mc13224v/aes_controller.h>

#define SINK_ADDRESS 4
#define SENSOR_ADDRESS 3
#define DIFFIE_PROT 0
#define DIFFIE_ASK_HEADER 'A'
#define DIFFIE_RESP_HEADER 'R'
#define ENC_MSG_HEADER 'E'

__USING_SYS

OStream cout;

static bool bleh = false;
void toggle_led() {
	unsigned int *GPIO_BASE = (unsigned int*)0x80000000;
	if(bleh)
		*GPIO_BASE = 1 << 23;
	else
		*GPIO_BASE = 1 << 24;
	bleh=!bleh;
}

class Diffie_Node: public Conditional_Observer, public Diffie_Hellman
{
	struct Msg {
		char header;
		union {
			char c[16];
			llint l;
		} data;
    };
	Msg msg;
	Msg to_send;

	NIC::Address sink_address;
	NIC::Address sensor_address;

	public:
		struct {
			NIC::Address addr;
			union {
				char c[16];
				llint l;
			} key;
		} neighbour;

		volatile bool resp;
		volatile bool waiting_resp;
		Mutex * nic_mutex;
		NIC * nic;
		NIC::Address my_address;
		Diffie_Node(bool is_sink)
			: Diffie_Hellman(false), sink_address(SINK_ADDRESS), sensor_address(SENSOR_ADDRESS) 
		{
			Pseudo_Random::seed(is_sink?105:97);
			calculate_private();
			my_address = (is_sink?sink_address:sensor_address);
			nic_mutex = new Mutex();
			nic = new NIC();
			nic->attach(this,DIFFIE_PROT);
			nic->address(my_address);
			neighbour.addr = my_address;
			for(int i=0;i<16;i++)
			{
				neighbour.key.c[i] = '\0';
				msg.data.c[i] = '\0';
				to_send.data.c[i] = '\0';
			}
			resp = false;
			waiting_resp = false;
			//nic_mutex->unlock();
			if(is_sink) sink();
			else sensor();
		}
		void sink()
		{
			cout<<"Sink running"<<endl;
			while(neighbour.addr==my_address)
				Alarm::delay(5000000);

			cout<<"Neighbour: " << neighbour.addr << " key: " << neighbour.key.l << endl;
			while(true)
			{
				Alarm::delay(3000000 + (Pseudo_Random::random()%10000000));
				cout<<send_encrypted(neighbour.addr, "Hi Bob!")<<endl;
			}
		}
		void sensor()
		{
			cout<<"Sensor running"<<endl;
			while(!establish_key(sink_address))
				Alarm::delay(5000000 + (Pseudo_Random::random()%5000000));

			cout<<"Neighbour: " << neighbour.addr << " key: " << neighbour.key.l << endl;
			while(1)
			{
				Alarm::delay(5000000 + (Pseudo_Random::random()%10000000));
				cout<<send_encrypted(neighbour.addr, "Hi Alice!")<<endl;
			}
		}

		void update(Conditionally_Observed * o, int p)
		{
			NIC::Protocol prot;
			NIC::Address from;
			cout<<"update called"<<endl;
			//nic_mutex->lock();
			int sz = nic->receive(&from,&prot,&msg,sizeof(Msg));
			//nic_mutex->unlock();
			cout<<"received: "<<msg.header << msg.data.c<<endl;
			cout<<"size: "<<sz<<endl;

			int tries = 3;
			llint y;
			AES_Controller *aes; 
			switch(msg.header)
			{
				case DIFFIE_ASK_HEADER:	
					Msg rmsg;
					cout<<"Diffie ask: "<<msg.data.c<<endl;
					y = msg.data.l;
					cout<<"Got y: " << y <<endl;
					calculate_key(y);
					cout<<"Key: " << key()<<endl;
					rmsg.header = DIFFIE_RESP_HEADER;
					rmsg.data.l = Y();

					while(tries-- > 0)
					{
						// For some reason this delay hangs forever
						//Alarm::delay(1000000 + (Pseudo_Random::random()%1000000));
						//nic_mutex->lock();
						cout<<"Sending: "<<rmsg.header<<rmsg.data.c<<endl;
						nic->send(from,DIFFIE_PROT,&rmsg,sizeof(Msg));
						//nic_mutex->unlock();
					}
					neighbour.addr = from; 
					neighbour.key.l = key();
					cout<<"Sent diffie rmsg"<<endl;
					break;
				case DIFFIE_RESP_HEADER:
					cout<<"Diffie resp: "<<msg.data.c<<endl;
					if(!waiting_resp) return;
					calculate_key(msg.data.l);
					neighbour.addr = from;
					neighbour.key.l = key(); 
					cout<<"Key with " << neighbour.addr << " set to " << neighbour.key.c << endl;
					resp = true;
					break;
				case ENC_MSG_HEADER:
					cout<<"Encrypted msg: "<<msg.data.c<<endl;
					if(!(neighbour.addr == from))
						return; 
					char decrypted[16];

					aes = AES_Controller::get_instance();
					if(!(aes->decrypt(msg.data.c,neighbour.key.c,decrypted))) return; 

					cout<<"Decrypted: " << decrypted << endl;
					break;
				default:
					cout<<"Header not recognized. Skipping."<<endl;
					break;
			}
		}
		bool establish_key(NIC::Address dest)
		{
			cout<<"Establishing key with "<<dest<<endl;
			NIC::Protocol prot;
			NIC::Address from;
			Msg to_send;
			to_send.header = DIFFIE_ASK_HEADER;
			to_send.data.l = Y();
			int tries = 5;
			waiting_resp = true;
			resp = false;
			do
			{
				cout<<"Sending msg: "<<to_send.header<<to_send.data.c<<endl;
				//nic_mutex->lock();
				cout<<nic->send(dest,DIFFIE_PROT,&to_send,sizeof(Msg))<<endl;
				Alarm::delay(3000000 + (Pseudo_Random::random()%5000000));
				//nic_mutex->unlock();
			} while(!resp && --tries);
			waiting_resp = false;
			cout<<"establish_key Result: "<<resp<<endl;
			return resp;
		}
		int send_encrypted(NIC::Address dest, char * msg)
		{
			if(!(neighbour.addr == dest)) return -1;
			AES_Controller * aes = AES_Controller::get_instance();
	
			char resized_msg[16];
			int i;
			for(i=0;i<16&&msg[i];resized_msg[i]=msg[i],i++);
			for(;i<16;resized_msg[i]='\0',i++);

			cout<<"Encrypting: "<<resized_msg<<endl;
			if(!aes->encrypt(resized_msg, neighbour.key.c, to_send.data.c)) return -2;		

			cout<<"Encrypted msg: "<<to_send.data.c<<endl;
			to_send.header = ENC_MSG_HEADER;

			cout<<"Sending: "<<to_send.header<<to_send.data.c<< " size: "<<sizeof(to_send)<<endl;
			//nic_mutex->lock();
			return nic->send(dest,DIFFIE_PROT,&to_send,sizeof(Msg));
			//nic_mutex->unlock();
		}
};

int main()
{
	Alarm::delay(5000000);
	cout<<sizeof(llint)<<endl;
	cout<<"Delay done"<<endl;
	Pseudo_Random::seed(100);

	Diffie_Node *k = new Diffie_Node(false);
}
