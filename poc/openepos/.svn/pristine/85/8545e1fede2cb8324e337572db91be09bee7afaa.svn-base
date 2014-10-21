// EPOS-- MC13224V (Transceiver) NIC Mediator Declarations

#ifndef __transceiver_nic_h
#define __transceiver_nic_h

#include "transceiver.h"

__BEGIN_SYS

class Radio
{
public:
    static const int FRAME_BUFFER_SIZE = Transceiver::MAX_PACKET_SIZE;

    enum Event {
	SFD_DETECTED,
	FRAME_END
    };

    typedef Transceiver::Event Event;
    typedef unsigned char Address;

    Radio() {}
    ~Radio() {}

    static void init();

    static void set_event_handler(Transceiver::event_handler * handler);

    int send(unsigned char * data, unsigned int size);

    int receive(unsigned char * data);

    void on();

    void off();

    void sleep();

    void listen();

    void reset();

    bool cca();

private:
    static Transceiver device;
};

__END_SYS

#endif

