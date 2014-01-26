#include <alarm.h>
#include <machine.h>
#include <nic.h>
#include <thread.h>

__USING_SYS

#define INFINITE -1

#define PUBLISH 0

unsigned int version;
NIC nic;

struct Message {
    unsigned int type;  // if it is a publish, subscribe or size message
    unsigned int content;       
};

void notify_new_version() { 
    Message msg;
    msg.type    = PUBLISH;
    msg.content = version;

    nic.send(0, 0, &msg, sizeof(Message));
}

int verify_new_version() {
    Message msg;

    while (1) {
        if (nic.receive(0, 0, &msg, sizeof(Message)) && (msg.type == PUBLISH) && (msg.content > version)) {
            version = msg.content;
            return 0;
        }
    }
}

int main() {
    version = 1;
    CPU::out8(Machine::IO::DDRA, 0x7);
    CPU::out8(Machine::IO::PORTA, ~version);

    Handler_Function nn(&notify_new_version);
    Alarm nn_version(50000, &nn, INFINITE);

    Thread * vnv = new Thread(&verify_new_version, Thread::READY, (short) 0);

    while (1) {
        CPU::out8(Machine::IO::PORTA, ~version);
        Alarm::delay(500000);
    }

    return 0;
}

