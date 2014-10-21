#include <machine.h>
#include <alarm.h>
#include <sensor.h>
#include <battery.h>
#include <uart.h>
#include <active.h>

__USING_SYS

const unsigned int UART_SIZE = 32;
const unsigned int PACKET_SIZE = 32;

NIC * nic;
OStream cout;
UART uart;

//Node parameters
const char SENSOR=1;
const char SINK = 0;
const char NODE_TYPE=SINK; //0 = sink, 1 = sensor
char myId;
char x,y;
char lastIdSet=1; //if sink, store the last id set to a sensor
bool printReceivedPacketsOn=true;

const char ID_SINK = 1;
const char ID_BROADCAST = 0xFF;
const char ID_NONE = 0; //no ID set yet

char _msg[PACKET_SIZE];

//node's information table (only for sink)
const char NODESTABLE_SIZE=10;
char nodesTable[NODESTABLE_SIZE+1][3]; //col0=isActive,col1=x,col2=y. col0=0 node deactivated, col1=1 node active



void printNodesTable(){
	cout<<"\nNode's Table:\n";
	cout<<"| ID | X | Y |\n"; 	
	for(int i=1;i<NODESTABLE_SIZE;i++)
	{
		if(nodesTable[i][0]==1)
			cout<<"| "<<i<<" | "<<(int)nodesTable[i][1]<<" | "<<(int)nodesTable[i][2]<<" |\n";
	}
}

void sendPacket(char destinationId,char commandType,char* parameter)
{
	_msg[0] = '#'; //start delimiter
	_msg[1] = 7;  //packet size
	_msg[2] = destinationId;
	_msg[3] = myId; 
	_msg[4] = commandType;
	for(int i=0;parameter[i]!=0;i++)
	_msg[i+5]=parameter[i];
	int r;
	while ((r = nic->send(NIC::BROADCAST, (NIC::Protocol) 1, &_msg, sizeof(_msg))) != 11)
	{}
}

void sendPacket(char destinationId,char commandType,char parameter0,char parameter1)
{
	_msg[0] = '#'; //start delimiter
	_msg[1] = 7;  //packet size
	_msg[2] = destinationId;
	_msg[3] = myId; 
	_msg[4] = commandType;
	_msg[5] = parameter0;
	_msg[6] = parameter1;
	_msg[7] = '\0';
	int r;
	while ((r = nic->send(NIC::BROADCAST, (NIC::Protocol) 1, &_msg, sizeof(_msg))) != 11)
	{}
}

void sendPacket(char destinationId,char commandType,char parameter)
{
	_msg[0] = '#'; //start delimiter
	_msg[1] = 7;  //packet size
	_msg[2] = destinationId;
	_msg[3] = myId; 
	_msg[4] = commandType;
	_msg[5] = parameter;
	_msg[6] = '\0';
	int r;
	while ((r = nic->send(NIC::BROADCAST, (NIC::Protocol) 1, &_msg, sizeof(_msg))) != 11)
	{}
}

void sendPacket(char destinationId,char commandType)
{
	_msg[0] = '#'; //start delimiter
	_msg[1] = 6;  //packet size
	_msg[2] = destinationId;
	_msg[3] = myId; 
	_msg[4] = commandType;
	_msg[5] = '\0';
	int r;
	while ((r = nic->send(NIC::BROADCAST, (NIC::Protocol) 1, &_msg, sizeof(_msg))) != 11)
	{}
}

//Maximum size=4
char strToInt(char* str,char size){
	int i=0;
	int sign=1;
	if(str[0]=='-') {
		i++;
		sign=-1;
		size--;
	}
	if(size==1)
		return (str[i]-'0')*sign;
	if(size==2)
		return ((str[i]-'0')*10+(str[i+1]-'0'))*sign;
	if(size==3)
		return ((str[i]-'0')*100+(str[i+1]-'0')*10+(str[i+2]-'0'))*sign;	
}

bool strEquals(char *str1,char *str2,int n)
{
	for(int i=0;i<n;i++)
		if(str1[i]!=str2[i])
			return false;
	return true;
}

class UartListener : public Active
{
public:
	UartListener() {}
	virtual ~Uart_Listener() {}

	virtual int run()
	{
	    int i;
		cout << "Started UART Listener!\n";
		
		while(true)
		{
			i = 0;
			while(!uart.has_data()) yield();
			_msg[i++] = uart.get();
			
	        	while(!(_msg[i-1] == '\n')) {
				while(!uart.has_data()) 
					yield();
	        		_msg[i++] = uart.get();
	        	}
		
		
		_msg[i-1]='\0'; //remove \n and close the string

		cout << "UART: " << _msg << "\n";
		if(strEquals(_msg,"PT",2))
			printNodesTable();
		else if(strEquals(_msg,"UT",2)){
			for(int i=1;i<=NODESTABLE_SIZE;i++)
				nodesTable[i][0]=0;
			nodesTable[myId][0]=1; //Set me as active
			nodesTable[myId][1]=x; //set my coordinates in the table
			nodesTable[myId][2]=y;	
			char nodeId=strToInt(&_msg[3],2);
			if(nodeId==myId)
				sendPacket(ID_BROADCAST,3); //3 is the command type GI	
			else
				sendPacket(nodeId,4); //4 is the UT Update Table command
		}else if(strEquals(_msg,"SI",2)){
			char nodeId=strToInt(&_msg[3],2);
			char newId=strToInt(&_msg[6],2);
			if(nodeId == myId){ 
				if(NODE_TYPE == SENSOR)
				{
					myId=newId;
					cout<<"\nMy ID updated successfully to "<<newId<<"\n";
				}
				else
					cout<<"\nERROR! Not allowed to change SINK address.\n";
				
			}
			else
				sendPacket(nodeId,0,newId); //0 is the command type SI	
		}else if(strEquals(_msg,"GI",2)){
			char nodeId=strToInt(&_msg[3],2);
			if(nodeId==myId)
				cout<<"\nMy info: id="<<(int)myId<<" x="<<(int)x<<" y="<<(int)y<<"\n";	
			else
				sendPacket(nodeId,4); //4 is the UT Update Table command
		}
		else if(strEquals(_msg,"GI",2)){
			char nodeId=strToInt(&_msg[3],2);
			if(nodeId==myId)
				cout<<"\nMy info: id="<<(int)myId<<" x="<<(int)x<<" y="<<(int)y<<"\n";	
			else
				sendPacket(nodeId,4); //4 is the UT Update Table command
		}
		else if(strEquals(_msg,"TM",2)){ //Transmit text message
			char destinationId=strToInt(&_msg[3],2);
			cout<<"\nSending message=("<< (const char*)(_msg+6)<<") destination ID="<<destinationId<<"\n";
			sendPacket(destinationId,5,(_msg+6));
		}

		//	memset(_msg+i, 0x00, Modbus_ASCII::MSG_LEN-i);
	        //int r;
	        //while ((r = nic->send(NIC::BROADCAST, (NIC::Protocol) 1, &_msg, Modbus_ASCII::MSG_LEN)) != 11)
	        //    

		}
		return 0;
	}

private:
    char _msg[UART_SIZE];
};

class RadioListener : public Active
{
public:
	RadioListener() {}
	virtual ~RadioListener() {}

	virtual int run()
	{
	 	NIC::Protocol prot;
	 	NIC::Address src;

	    	int i = 0;
		while(true)
		{
			memset(_msg, 0x00, PACKET_SIZE);
	        	while(!(nic->receive(&src, &prot, &_msg, PACKET_SIZE) > 0))
	        		yield();
	        	handleReceivedPacket();
		}
		return 0;
	}

private:
    unsigned char _msg[PACKET_SIZE];
	void handleReceivedPacket(){
		char sourceId = _msg[3];
		char destinationId = _msg[2];
		char commandType   = _msg[4];
		char parameter0    = _msg[5];
		char parameter1    = _msg[6];		
		/*cout<<"\nPACKET RECEIVED:\n"; 
		cout<<"Start Delimiter: "<< ((char)_msg[0]) <<"\n";
		cout<<"Packet size    : "<< ((int)_msg[1]) <<"\n";
		cout<<"Destination ID : "<< ((int)_msg[2]) <<"\n";
		cout<<"Source ID      : "<< ((int)_msg[3]) <<"\n";
		cout<<"Command Type   : "<< ((int)_msg[4]) <<"\n";
		cout<<"Parameter      : "<< (const char*)(_msg+5) <<"\n";*/

		switch(commandType){
			case 0: //set id
			if(((destinationId == myId) || (destinationId == ID_BROADCAST)) && (NODE_TYPE == SENSOR)){
				myId=parameter0;
				cout<<"\nCOMMAND RECEIVED(SI, set ID): My ID was updated to "<<(int)myId<<". Broadcasting Information...\n";
				nodesTable[myId][0]=1; //Set me as active
				nodesTable[myId][1]=x; //set my coordinates in the table
				nodesTable[myId][2]=y;
				sendPacket(ID_BROADCAST,2,x,y); //2 is the command type NI			
			}
			break;
			case 1: //Request ID. Some sensor requested a new id. If I'm sink I respond
			if((destinationId == myId) && (NODE_TYPE == SINK)){
				lastIdSet++;
				cout<<"\nCOMMAND RECEIVED(RI, Request New ID): Changing remote sensor ID from "<<(int)sourceId<<" to "<<(int)lastIdSet<<"\n";
				sendPacket(sourceId,0,lastIdSet); //0 is the command type SI 					
			}
			break;
			case 2: //Node Info. Some node sent me its information
			if((destinationId == myId) || (destinationId == ID_BROADCAST)){
				cout<<"\nCOMMAND RECEIVED(NI, Node Info): ID="<<(int)sourceId<<" X="<<(int)parameter0<<" Y="<<(int)parameter1<<" Updating node's table...\n";		nodesTable[sourceId][0]=1;
				nodesTable[sourceId][1]=parameter0;
				nodesTable[sourceId][2]=parameter1;
			}
			break;
			case 3: //Get Info. Some sensor requested my info (id and location). Reply with NI message
			if(((destinationId == myId) || (destinationId == ID_BROADCAST)) && (sourceId != myId)){
				cout<<"\nCOMMAND RECEIVED(GI, Get Info): responding with my info id="<<(int)myId<<" x="<<(int)x<<" y="<<(int)y<<"\n";
				sendPacket(sourceId,2,x,y); //2 is the command type NI	
			}
			break;
			case 4: //Update Table. Some sensor requested me to update my table. I will broadcast a GI message
			if(((destinationId == myId) || (destinationId == ID_BROADCAST)) && (sourceId != myId)){
				cout<<"\nCOMMAND RECEIVED(UT, Update Table): broadcasting GI command...\n";
				for(int i=1;i<=NODESTABLE_SIZE;i++)
					nodesTable[i][0]=0;
				nodesTable[myId][0]=1; //Set me as active
				nodesTable[myId][1]=x; //set my coordinates in the table
				nodesTable[myId][2]=y;				
				sendPacket(ID_BROADCAST,3); //3 is the command type GI	
			}
			break;
			case 5: //Update Table. Some sensor requested me to update my table. I will broadcast a GI message
			if(((destinationId == myId) || (destinationId == ID_BROADCAST)) && (sourceId != myId)){
				cout<<"\nMESSAGE RECEIVED(TM:) "<<(const char*)(_msg+5)<<" source ID="<<sourceId<<"\n";
			}
			break;				
		}
	}
};

void init(){
	if(NODE_TYPE == 0){ //SINK
		cout<< "I am the Sink.\n";
		myId=ID_SINK;
		lastIdSet=1;
		x=0;
		y=0;
	}
	else { //SENSOR
		cout<< "I am a Sensor.\n";
		myId=ID_NONE;
		x=1;
		y=1;
		cout<<"Requested an ID from the Sink...";
		sendPacket(ID_SINK,1);
	}
	for(int i=1;i<=NODESTABLE_SIZE;i++)
		nodesTable[i][0]=0;
	nodesTable[myId][0]=1; //Set me as active
	nodesTable[myId][1]=x; //set my coordinates in the table
	nodesTable[myId][2]=y;
}

int main() {
    nic = new NIC();
	init();
//    sink();
//    sensor(1);

	
	
	UartListener uartListener;
	uartListener.start();
	RadioListener radioListener;
	radioListener.start();

	uartListener.join();
	radioListener.join();
	
	

	cout << "Something went WRONG! Threads finished!\n";

}


