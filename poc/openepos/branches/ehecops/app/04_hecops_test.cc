/*
 * hecops.cc
 *
 *  Created on: 25 juin 2013
 *      Author: thibault
 */
#include <location_algorithm.h>
#include <node.h>
#include <periodic_thread.h>
#include <mutex.h>

#define N_SENDER -1

__USING_SYS

NIC * nic;
OStream cout;
RSSI* node;
Mutex mut_display;

int main() {
    cout << "\n**** Test of HECOPS@EPOS, the location algorithm! ****\n\n\n";

    nic = new NIC();
    HECOPS* hecops = new HECOPS();

    if (N_SENDER==0) {
while (true) {
        node = new RSSI(new Anchor('A',0,0, hecops, nic));
        node->location(node);
        Alarm::delay(2000000);

        node = new RSSI(new Anchor('B',45,-45, hecops, nic));
        node->location(node);
        Alarm::delay(2000000);

        node = new RSSI(new Anchor('C',45,45, hecops, nic));
        node->location(node);
        Alarm::delay(2000000);
}
        node = new RSSI(new Anchor('D',0,180, hecops, nic));
        node->location(node);
        Alarm::delay(1000000);

        node = new RSSI(new Anchor('E',-45,180, hecops, nic));
        node->location(node);
        Alarm::delay(1000000);

        node = new RSSI(new Anchor('F',-45,315, hecops, nic));
        node->location(node);
        Alarm::delay(1000000);

        node = new RSSI(new Anchor('G',135,90, hecops, nic));
        node->location(node);
        Alarm::delay(1000000);

        node = new RSSI(new Anchor('H',315,135, hecops, nic));
        node->location(node);
        Alarm::delay(1000000);

        node = new RSSI(new Anchor('I',225,90, hecops, nic));
        node->location(node);
        Alarm::delay(1000000);

        node = new RSSI(new Anchor('J',350,90, hecops, nic));
        node->location(node);
        delete node;

        kout << "done";
    } else {
        switch (N_SENDER) {
        case 1 :
            cout << "Creation of an anchor node 'A' in (0,0)\n";
            node = new RSSI(new Anchor('A',0,0, hecops, nic));
            node->location(node);
            break;
        case 2 :
            cout << "Creation of an anchor node 'B' in (0,180)\n";
            node = new RSSI(new Anchor('B',0,180, hecops, nic));
            node->location(node);
            break;
        case 3:
            cout << "Creation of anchor node 'C' in (100,140)\n";
            node = new RSSI(new Anchor('C',100,140,hecops, nic));
            node->location(node);
            break;
        default :
            cout << "Creation of mobile node 'b'";
            node = new RSSI(new Mobile('b',hecops, nic));
            node->location(node);
            break;
        }

        cout << "Destruction of an anchor node\n";
        delete node;
        cout << endl;
    }
    return 0;
}
