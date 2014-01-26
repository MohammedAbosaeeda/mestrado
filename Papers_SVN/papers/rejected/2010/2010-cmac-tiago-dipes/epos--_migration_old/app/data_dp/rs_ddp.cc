#include <alarm.h>
#include <machine.h>
#include <nic.h>
#include <thread.h>

__USING_SYS

#define PUBLISH 0

unsigned int version;

struct Message {
    unsigned int type;  // if it is a publish, subscribe or size message
    unsigned int content;       
};

void notify_new_version() { 
    NIC nic;
    Message msg;
    msg.type    = PUBLISH;
    msg.content = version;

    while (1) {
        nic.send(0, 0, &msg, sizeof(Message));
    }
}

int verify_new_version() {
    NIC nic;
    Message msg;

    while (1) {
        if (nic.receive(0, 0, &msg, sizeof(Message))) {
           if ((msg.type == PUBLISH) && (msg.content > version)) {
               CPU::out8(Machine::IO::PORTA, ~msg.content);
               return 0;
           }
        }
    }
}

int main() {
    version = 1;
    CPU::out8(Machine::IO::DDRA, 0x7);
    CPU::out8(Machine::IO::PORTA, ~version);

//    notify_new_version();
    verify_new_version();

    return 0;
}

