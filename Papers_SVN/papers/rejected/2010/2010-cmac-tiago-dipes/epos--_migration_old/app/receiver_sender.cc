#include <alarm.h>
#include <machine.h>
#include <nic.h>

__USING_SYS

static unsigned int MY_ID  = 0x1;
static unsigned int DST_ID = 0x2;

struct Message {
    unsigned int src;
    unsigned int dst;
    unsigned int data;
};

int sender() {
    NIC nic;
    Message msg;

    msg.src  = MY_ID;
    msg.dst  = DST_ID;
    msg.data = 7;

    int i = 0;

    while (1) {
        (i > 0) ? i = 0 : i = 1;
        CPU::out8(Machine::IO::DDRA, i);   

        nic.send(msg.dst, 0, &msg, sizeof(msg));
        Alarm::delay(500000);
    }

    return 0;
}

int receiver() {
    NIC nic;
    Message msg;

    int i = 0;

    while (1) {
        if (nic.receive(0, 0, &msg, sizeof(msg)) && (msg.dst == DST_ID)) { 
            CPU::out8(Machine::IO::DDRA, msg.data);
            Alarm::delay(1000000);
        }

        (i > 0) ? i = 0 : i = 2;
        CPU::out8(Machine::IO::DDRA, i);   
        Alarm::delay(500000);
    }

    return msg.data;
}

int main() {
//    sender();
    receiver();
}

