#include <machine.h>

__USING_SYS

const unsigned char SINK_ID  = 0x01;
const unsigned int DATA_SIZE = 64;

char msg[DATA_SIZE];

NIC * nic;
OStream cout;

void sensor(unsigned char id) {
    cout << "Sensor id = " << (int) id << "\n";

    for (int i = 0; i < DATA_SIZE; i++) {
	msg[i] = i;
    }

    msg[0] = id;

    char c = 0;

    while (true) {
	msg[1] = c++;

        while (nic->send(SINK_ID, 0, &msg, sizeof(msg)) != CMAC::TX_OK)
	    cout << "failed\n";

	cout << "tx done\n";

        for (int x = 0; x < 0x100000; x++);
        for (int x = 0; x < 0x100000; x++);
    }
}

void sink() {
    unsigned char src, prot;

    cout << "Sink\n";

    while (true) {
        while(!(nic->receive(&src, &prot, &msg, sizeof(msg)) > 0))
	    cout << "failed\n";

	cout << "##########\n";
	cout << "# Sender id = " << (int) msg[0] << "\n";
	cout << "# msg number = " << (int) msg[1] << "\n";
	cout << "##########\n\n";
    }
}

int main() {
    nic = new NIC();

//    sink();
    sensor(1);
}

