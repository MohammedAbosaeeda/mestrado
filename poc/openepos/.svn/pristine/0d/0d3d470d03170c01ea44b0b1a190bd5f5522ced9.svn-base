// OpenEPOS EMote2ARM_Radio Mediator Declarations

#ifndef __emote2arm_radio_h
#define __emote2arm_radio_h

#include <radio.h>
#include <cmac.h>
#include "transceiver.h"

__BEGIN_SYS

class Radio_Wrapper
{
public:
    static const int FRAME_BUFFER_SIZE = EMote2ARM_Transceiver::MAX_PACKET_SIZE;

    typedef EMote2ARM_Transceiver Radio;
    typedef EMote2ARM_Transceiver::Event Event;
    typedef Radio_Common::Address Address;

    Radio_Wrapper() {}
    ~Radio_Wrapper() {}

    static void init();

    static void set_event_handler(EMote2ARM_Transceiver::event_handler * handler);

    int send(unsigned char * data, unsigned int size);

    int receive(unsigned char * data);

    void on();

    void off();

    void sleep();

    void listen();

    void reset();

    bool cca();

    unsigned int lqi();

    unsigned int rssi();

private:
    static EMote2ARM_Transceiver * device;
};

class EMote2ARM_Radio: public CMAC<Radio_Wrapper>, Radio_Common
{
public:
	EMote2ARM_Radio(int unit = 0): CMAC<Radio_Wrapper>(unit) {}

    ~EMote2ARM_Radio() {}

    static void init(unsigned int n) {}

    void reset() {}
};

__END_SYS

#endif

