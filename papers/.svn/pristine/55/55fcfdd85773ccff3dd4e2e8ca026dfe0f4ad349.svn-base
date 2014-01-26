#include <alarm.h>
#include <machine.h>
#include <nic.h>
#include <thread.h>
#include "flash_util.h"

__USING_SYS

#define INFINITE -1

void notify_new_version();

static unsigned int PUBLISH   = 0x00;
static unsigned int SUBSCRIBE = 0x01;
static unsigned int SIZE      = 0x02;
static unsigned int M_PKT     = 0x03;      // M_PKT stands for missing packet

NIC nic;
unsigned int version;
Handler_Function nn(&notify_new_version);
Alarm * nn_version;

struct Packet {
    unsigned int pkt;   // packet content
    unsigned int num;   // packet number
    unsigned int ver;   // packet version
};

struct Message {
    unsigned int type;  // if it is a publish, subscribe, size or missing packet message
    unsigned int content;       
};

void send_packets(Packet p[], int number, int size) {
    Message m_pkt;
    while (p[number].num <= size) {
        for (int i = 0; i < 10000; i++) 
            nic.send(0, 0, &p[number], sizeof(Packet));

        for (int i = 0; i < 10000; i++) {
            if (nic.receive(0, 0, &m_pkt, sizeof(Message)) && (m_pkt.type == M_PKT) && (m_pkt.content <= p[number].num)) {
                send_packets(p, m_pkt.content - 1, size);
                return;
            }
        }

        number++;
    }
}

void listen_requests() {
    Packet p[2];
    p[0].pkt = 0xAA;
    p[0].num = 1;
    p[0].ver = version;

    p[1].pkt = 0xFF;
    p[1].num = 2;
    p[1].ver = version;

    Message msg;
    Message m_pkt;
    if (nic.receive(0, 0, &msg, sizeof(Message)) && (msg.type == SUBSCRIBE) && (msg.content == version)) {
        Message s_msg;
        s_msg.type    = SIZE;
        s_msg.content = 0x02;  // number of packets to be sent

        for (int i = 0; i < 10000; i++)
            nic.send(0, 0, &s_msg, sizeof(Message));

        send_packets(p, 0, 2);

    } else if (nic.receive(0, 0, &m_pkt, sizeof(Message)) && (m_pkt.type == M_PKT)) {
        version = m_pkt.content;
        send_packets(p, m_pkt.content - 1, 2);
    }
}

void update(int v) {
    Message msg;
    msg.type    = SUBSCRIBE;
    msg.content = v;

    Message s_msg;
    bool t = true;
    do {
        nic.send(0, 0, &msg, sizeof(Message)); 

        if (nic.receive(0, 0, &s_msg, sizeof(Message)) && (s_msg.type == SIZE))
            t = false;

    } while (t);

    unsigned int packets[s_msg.content];
    int last_pkt = 0;
    Packet p;
    Message m_pkt;
    m_pkt.type = M_PKT;
    
    while (last_pkt < s_msg.content) {
        if (nic.receive(0, 0, &p, sizeof(Packet)) && (p.ver == v) && (p.num == last_pkt + 1)) {
            packets[last_pkt] = p.pkt;
            last_pkt++;

        } else {
            m_pkt.content = last_pkt + 1;
            nic.send(0, 0, &m_pkt, sizeof(Message));
        }
    }

    version = v;
}

void notify_new_version() { 
    delete nn_version;

    Message msg;
    msg.type    = PUBLISH;
    msg.content = version;

    nic.send(0, 0, &msg, sizeof(Message));
    listen_requests();

    nn_version = new Alarm(50000, &nn, INFINITE);
}

int verify_new_version() {
    Message msg;

    while (1) {
        if (nic.receive(0, 0, &msg, sizeof(Message)) && (msg.type == PUBLISH) && (msg.content > version)) {
            delete nn_version;
            update(msg.content);
            nn_version = new Alarm(50000, &nn, INFINITE);
        }
    }
}

int main() {
    version = 1;
    //CPU::out8(Machine::IO::DDRA, 0x7);
    //CPU::out8(Machine::IO::PORTA, ~version);

    nn_version = new Alarm(50000, &nn, INFINITE);

    Thread * vnv = new Thread(&verify_new_version, Thread::READY, (short) 0);

    while (1) {
        //CPU::out8(Machine::IO::PORTA, ~version);
        Alarm::delay(500000);
    }

    return 0;
}

