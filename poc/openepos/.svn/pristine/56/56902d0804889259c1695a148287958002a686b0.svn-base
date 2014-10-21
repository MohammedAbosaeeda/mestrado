/*
 * temperature_sensing_app.cc
 *
 *  Created on: Dec 15, 2011
 *      Author: mateus
 */
#include <machine.h>
#include <alarm.h>
#include <sensor.h>

#include <mach/atmega1281/mesh_bean2_led_master.h>

__USING_SYS

const unsigned char SINK_ID  = 0x01;
const unsigned int DATA_SIZE = 32;

char msg[DATA_SIZE];

NIC * nic;
OStream cout;

void sensor(unsigned char id) {
    // turn_on_led1();
    cout << "Sensor id = " << (int) id << "\n";

    Temperature_Sensor * temp = new Temperature_Sensor();

    for (unsigned int i = 0; i < DATA_SIZE; i++) {
        msg[i] = i;
    }

    msg[0] = id;

    char c = 0;


    while (true) {
        msg[1] = c++;

        MeshBean2_LED_Master::turn_on_rr1_led();
		for (unsigned long imerus = 0; imerus < 1000; imerus++) {
			msg[2] = temp->sample();
		}
        MeshBean2_LED_Master::turn_off_rr1_led();

        int r;
        while ((r = nic->send(NIC::BROADCAST, (NIC::Protocol) 1, &msg, sizeof(msg))) != 11)
            cout << "failed " << r << "\n";

        cout << "tx done\n";

        Alarm::delay(1000000);
    }
}

void sink() {
    NIC::Protocol prot;
    NIC::Address src;

    // turn_on_led2();
    cout << "Sink\n";

    while (true) {
        while(!(nic->receive(&src, &prot, &msg, sizeof(msg)) > 0))
            cout << "failed\n";

        cout << "\n##########\n";
        cout << "Sender id: "   << (int) msg[0] << "\n";
        cout << "Msg number: "  << (int) msg[1] << "\n";
        cout << "Temperature: " << (int) msg[2] << " C\n";
        cout << "Protocol:"     << (int) prot   << "\n";
    }
}

int main() {
    nic = new NIC();

    // sink();
    sensor(1);
}

