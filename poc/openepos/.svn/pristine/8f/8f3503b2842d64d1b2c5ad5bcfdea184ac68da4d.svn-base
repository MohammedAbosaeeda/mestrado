#ifndef __packet_h
#define __packet_h

#include <utility/queue.h>

class Packet {
private:
    char packetType;
public:
    char packet[40];
    Queue<Packet>::Element* e;
    Packet() : e(new Queue<Packet>::Element(this)){
    }
    
    //spacetime packets
    static const char INTEREST=0x10;
    static const char RESPONSE=0x11;
    
    //debug packets
    static const char TEXT=0x90;
    static const char GET_INFO=0x91;
        
    //router packets
    static const char RTS=0x05;
    static const char CTS=0x06;
};

#endif