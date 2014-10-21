
#include <active.h>
#include <condition.h>
#include <mutex.h>
#include "packet.h"
//#include "canneal/include/epos_wrappers.h"

class Uart_Listener : public Active
{
public:
    Uart_Listener();
    virtual ~Uart_Listener();
    virtual int run();
    Packet* retrieveReceivedPacket();
    Condition condition_packet_available;
    Mutex mutex_packet;    
private:
    char _msg[40];
    Packet* receivedPacket;
    UART _uart;
};
//---------------------------------------------------------------
//Class UartListener: Methods implementation
    Uart_Listener::Uart_Listener() {
        mutex_packet.lock();
        receivedPacket=0;
        mutex_packet.unlock();
    }
    Uart_Listener::~Uart_Listener() {}
    Packet* Uart_Listener::retrieveReceivedPacket(){
        mutex_packet.lock();
        Packet* temp=receivedPacket;
        receivedPacket=0;
        mutex_packet.unlock();
        return temp;
    }
    int Uart_Listener::run()
    {
        int i;
        cout << "Started UART Listener!\n";
        
            while(true)
            {  
                i = 0;
                while(!_uart.has_data()) yield();
                _msg[i++] = _uart.get();

                while(!(_msg[i-1] == '\n')) {
                        while(!_uart.has_data()) 
                                yield();
                        _msg[i++] = _uart.get();
                        //uart.put(_msg[i-1]);
                }
                _msg[i-1]='\0';
                cout<<"\nReceived message (UART): "<<_msg;
                Packet* temp = new Packet();
                for(int j=0;j<i;j++)
                    temp->packet[j]=_msg[j];
                
                mutex_packet.lock();
                if(receivedPacket != 0){ //nobody read that before I write another packet
                    delete receivedPacket;
                    cout<<"ERROR: LOST PACKET ON UART BUFFER!";
                }
                receivedPacket=temp;
                //cout<<"\nDeu lock e copiou!";
                mutex_packet.unlock();
                condition_packet_available.signal();
                //cout<<"\nDeu signal na condition!";

//		_msg[i-1]='\0'; //remove \n and close the string
//
//		cout << "UART: " << _msg << "\n";
//		if(strEquals(_msg,"PT",2))
//			printNodesTable();
//		else if(strEquals(_msg,"UT",2)){
//			for(int i=1;i<=NODESTABLE_SIZE;i++)
//				nodesTable[i][0]=0;
//			nodesTable[myId][0]=1; //Set me as active
//			nodesTable[myId][1]=x; //set my coordinates in the table
//			nodesTable[myId][2]=y;	
//			char nodeId=strToInt(&_msg[3],2);
//			if(nodeId==myId)
//				sendPacket(ID_BROADCAST,3); //3 is the command type GI	
//			else
//				sendPacket(nodeId,4); //4 is the UT Update Table command
//		}else if(strEquals(_msg,"SI",2)){
//			char nodeId=strToInt(&_msg[3],2);
//			char newId=strToInt(&_msg[6],2);
//			if(nodeId == myId){ 
//				if(NODE_TYPE == SENSOR)
//				{
//					myId=newId;
//					cout<<"\nMy ID updated successfully to "<<newId<<"\n";
//				}
//				else
//					cout<<"\nERROR! Not allowed to change SINK address.\n";
//				
//			}
//			else
//				sendPacket(nodeId,0,newId); //0 is the command type SI	
//		}else if(strEquals(_msg,"GI",2)){
//			char nodeId=strToInt(&_msg[3],2);
//			if(nodeId==myId)
//				cout<<"\nMy info: id="<<(int)myId<<" x="<<(int)x<<" y="<<(int)y<<"\n";	
//			else
//				sendPacket(nodeId,4); //4 is the UT Update Table command
//		}
//		else if(strEquals(_msg,"GI",2)){
//			char nodeId=strToInt(&_msg[3],2);
//			if(nodeId==myId)
//				cout<<"\nMy info: id="<<(int)myId<<" x="<<(int)x<<" y="<<(int)y<<"\n";	
//			else
//				sendPacket(nodeId,4); //4 is the UT Update Table command
//		}
//		else if(strEquals(_msg,"TM",2)){ //Transmit text message
//			char destinationId=strToInt(&_msg[3],2);
//			cout<<"\nSending message=("<< (const char*)(_msg+6)<<") destination ID="<<destinationId<<"\n";
//			sendPacket(destinationId,5,(_msg+6));
//		}
//
//		//	memset(_msg+i, 0x00, Modbus_ASCII::MSG_LEN-i);
//	        //int r;
//	        //while ((r = nic->send(NIC::BROADCAST, (NIC::Protocol) 1, &_msg, Modbus_ASCII::MSG_LEN)) != 11)
//	        //    

            }
            return 0;
    }

