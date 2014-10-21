#ifndef __PARSER_H
#define	__PARSER_H


namespace all {  
    
    class Parser {
    public:
        //Convert a string to a packet
        static char* parseToPacket(char* str);
    };
    
    class StringUtil {
    public:
        static char* strCpy(char* destination,const char *origin);
        static char* strCpy(char* dest,int beginOrigin,int endOrigin,const char* origin,int beginDest,int endDest);
        static int getIndex(char x,const char* str);
        static bool strEquals (const char* str1,const char* str2,int begin,int endInclusive);
        static bool strEquals (const char* str1,const char* str2);
        static int strLen(const char* str);
    };
    
    class Tokenizer {
    private:
        const char* _str;
        int _length;
        int _currentIndex;
        char* _nextToken;
        char _separator;
    public:
        Tokenizer(const char* str, char separator);
        bool hasNext();
        char* nextToken();
        
    };
    
    class Packet {
        /*
         * Packet format:
         * packet [0] = Start delimiter
         * packet [1] = Packet size
         * packet [2] = Packet type
         * packet [3..(n-1)] = subframe
         */
    private:
        char START_DELIMITER;
        char packetScale;
        int packetSize;
        char packetType;
        float nextForwarderX,
              nextForwarderY,
              nextForwarderZ; //next forwarder location (sphere centered in x,y,z and radius=TOLERANCE_RADIUS)            
        float destinationX,
              destinationY,
              destinationZ; //destination location (sphere centered in x,y,z and radius=TOLERANCE_RADIUS)
        float sourceX,
              sourceY,
              sourceZ; //source location (sphere centered in x,y,z and radius=TOLERANCE_RADIUS)
        char checksum;
    public:
        Packet();
        ~Packet();
        void setPacketType(char packetType);
        char getPacketType();
        void copyToPacket(char* source,int beginIndexSource,int endIndexSource,int beginIndexPacket,int endIndexPacket);
        char size();
               
        //debug packets
        static const char SET_ID=0x05;        // -setId oldId newId
//        const char SET_LOCATION=0x06;  // -setLocation id
//        const char SET_NODE_INFO=0x07; // -setNodeInfo id x y z "variable" minFrequency precision
//        const char GET_NODE_INFO=0x08; // -getNodeInfo id
//        const char TEXT_MESSAGE=0x09;  //
//        
//        //Router packets
//        const char LOCATION=0x31;
//        const char GET_LOCATION=0x30;
//        
//        
//             
//        //space time packet
//        const char SPACETIME_INTEREST=0x20;
//        const char SPACETIME_SAMPLE=0x21;
//        
//        const char TOLERANCE_RADIUS=2;
//        const char WIRELESS_RADIUS=10;
        
    };
    
    class UartListener : public Active
    {
    public:
	UartListener();
	virtual ~UartListener();
	virtual int run();
    private:
        char _msg[40];
    };
    
//---------------------------------------------------------------    
    //Class Tokenizer: Methods implementation
    Tokenizer::Tokenizer(const char* str, char separator){
        _str=str;
        _separator=separator;
        _currentIndex=0;
        _nextToken=0;
        _length=StringUtil::strLen(str);
    }
    bool Tokenizer::hasNext(){
        if(_currentIndex < _length){
            int i=_currentIndex;
            //Remove separators before token
            while((_str[i]==_separator)&&(i<_length))
                i++;
            if(i<_length)
                return true;
        }
        return false;       
    }
    char* Tokenizer::nextToken(){
        if(hasNext())
        {
            //Remove all separators before token
            while((_str[_currentIndex]==_separator)&&(_currentIndex<_length))
                _currentIndex++;
            
            int tokenBegin=_currentIndex;
            //Advances _currentIndex to the end of this token
            while((_str[_currentIndex]!=_separator)&&(_currentIndex<_length))
                _currentIndex++;
            _nextToken=new char[_currentIndex-tokenBegin+1];
            for(int i=tokenBegin;i<_currentIndex;i++)
                _nextToken[i-tokenBegin]=_str[i];
            _nextToken[_currentIndex]='\0';
            return _nextToken;
        }
        return 0;   
    }
//---------------------------------------------------------------
    //Class StringUtil: Methods implementation
    char* StringUtil::strCpy(char* destination,const char *origin){
            int i=0;
            do{
                destination[i]=origin[i];
            }while(origin[i++]!='\0');
        }
    char* StringUtil::strCpy(char* dest,int beginOrigin,int endOrigin,const char* origin,int beginDest,int endDest){
        int i;
        for(i=0;i<=(endOrigin-beginOrigin);i++) 
            dest[i+beginDest]=origin[i+beginOrigin];
        dest[i]='\0';
    }
    int StringUtil::strLen(const char* str){
        int i;
        for(i=0;str[i]!='\0';i++);
        return i;
    }
    int StringUtil::getIndex(char x,const char* str){
        for(int i=0;str[i]!=x;i++)
            if(str[i]==x)
                return i;
        return -1;
    }
    bool StringUtil::strEquals (const char* str1,const char* str2,int begin,int endInclusive){
        for(int i=begin;i<=endInclusive;i++)
            if(str1[i]!=str2[i]) return false;
        return true;
    }
    bool StringUtil::strEquals (const char* str1,const char* str2){
        int i;
        for(i=0;(str1[i]==str2[i])&&(str1[i]!='\0');i++);
        return str1[i]==str2[i];        
    }

//---------------------------------------------------------------
    //Class Parser: Methods implementation
    char* Parser::parseToPacket(char* str){
            Tokenizer tokenizer = Tokenizer(str,' ');
            char* token = tokenizer.nextToken();
            if(StringUtil::strEquals("setId",token)){
                cout<<"\n\nTRATAR SET ID PACKET\n\n";
            }
            else if(StringUtil::strEquals("setLocation",token)){
                cout<<"\n\nTRATAR SET LOCATION\n\n";
            }
            else {
                cout<<"\n\nINVALID COMMAND\n\n";
            }
        }
    
//---------------------------------------------------------------
    //Class UartListener: Methods implementation
	UartListener::UartListener() {}
	UartListener::~UartListener() {}

	int UartListener::run()
	{
	    int i;
		
		cout << "Started UART Listener!\n";
		while(true)
		{
                    
//                    Alarm::delay(1000000);
//                    yield();
                    i = 0;
                    while(!uart.has_data()) yield();
                    _msg[i++] = uart.get();

                    while(!(_msg[i-1] == '\n')) {
                            while(!uart.has_data()) 
                                    yield();
                            _msg[i++] = uart.get();
                            uart.put(_msg[i-1]);
                    }
                    _msg[i-1]='\0';
                    cout<<"Received message: "<<_msg;
//                
//		
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
};


#endif	/* PARSER_H */

