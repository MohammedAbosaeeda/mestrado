
#include "uart_listener.h"
#include <active.h>
//#include "packet.h"

class Packet_Handler : public Active
{
public:
    Packet_Handler();
    virtual ~Packet_Handler();
    virtual int run();
private:
    Uart_Listener uartListener;
    //Radio_Listener radioListener;
};

//---------------------------------------------------------------
//Class Packet_Handler: Methods implementation
    Packet_Handler::Packet_Handler() {
        uartListener.start();
    }
    Packet_Handler::~Packet_Handler() {
        
    }

    int Packet_Handler::run()
    {
        Packet* packet;
        while(true){
            uartListener.condition_packet_available.wait();
            //cout<<"\nPassou do wait() na condition!";
            packet=uartListener.retrieveReceivedPacket();
            cout<<"\nPacket Handler: "<<packet->packet;
        }
    }

