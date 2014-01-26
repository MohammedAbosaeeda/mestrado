#include <alarm.h>
#include <machine.h>
#include <nic.h>
#include <thread.h>

__USING_SYS

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

    while (1) {
        CPU::out8(Machine::IO::PORTA, ~version);
        nic.send(0, 0, &msg, sizeof(Message));
    }
}

int verify_new_version() {
    Message msg;

    while (1) {
        if (nic.receive(0, 0, &msg, sizeof(Message))) {
           if ((msg.type == PUBLISH) && (msg.content > version)) {
               version = msg.content;
               return 0;
           }
        }
    }
}

int main() {
    version = 2;
    CPU::out8(Machine::IO::DDRA, 0x7);
    CPU::out8(Machine::IO::PORTA, ~version);

    Thread * vnv = new Thread(&verify_new_version, Thread::READY, (short) 0);

    notify_new_version();

    return 0;
}

