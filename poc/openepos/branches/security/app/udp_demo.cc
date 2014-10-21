/*
 * UDP Sample Application
 * Author: Rodrigo V. Raimundo <rodrigovr@lisha.ufsc.br>
 *
 * Compile this application with a different IP for each node.
 *
 */

#include <alarm.h>
#include <utility/random.h>
#include <udp.h>

#include <utility/ostream.h>

__USING_SYS

OStream cout;

class MyUDPSocket : public UDP::Socket {
public:
    MyUDPSocket(UDP::Address addr) : 
        UDP::Socket(addr, UDP::Address(~0,555)) 
    { 
        cout << "Created socket\n";
        remote(UDP::Address(0x0a00020f, 555));
		send_n = 0;
		rec_n = 0;
		lost = -1;
    }

    void bcast() {
		char msg[1] = {send_n};
//        cout << "Sending " << (int)msg[0];
        //remote(UDP::Address(IP::instance()->broadcast(), 555));
        send(msg, 1);
		send_n++;
    }

	char send_n;
	volatile char rec_n;
	volatile int lost;

protected:
    void received(const UDP::Address& from, const char* msg, unsigned int len) {
		if(lost != -1)
			lost += (msg[0] - rec_n);
		else
			lost = 0;
		cout << "Received: " << msg << '\n';
//        cout << "Received " << (int)msg[0] << " from " << from << endl;
//		cout << "Lost " << msg[0] - rec_n - 1 << '\n';
		rec_n = msg[0]+1;
    }
};

int main() {
    IP * ip = IP::instance();
        Alarm::delay(3000000);
    cout << "UDP Demo running on: " << ip->address() << endl;

    UDP::Address addr(ip->address(), 555);
    MyUDPSocket socket(addr);

    while (1) {
        Alarm::delay(5000000);//(Pseudo_Random::random() % 30000));
		cout << "Lost " << socket.lost << '\n';
//        socket.bcast();
    }
}
