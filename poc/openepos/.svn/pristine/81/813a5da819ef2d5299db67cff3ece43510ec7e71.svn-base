//This class parses a text command into a packet
//Debugger packets (all based on Id):
//    -setLocation id x y z
//    -getInfo id
//    -getLocationResponse x y z
//    -setId oldId newId
//    -getNodeInfo (id) : 
//    -getNodeInfoResponse 
//    -discoverNeighbors
//    -discoverNeighborsResponse
//    -setNodeInfo id x y z "variable" minFrequency precision

//Space time packets:
//    -interest
//    -response

//Router packets:
//    -getNeighborInfo  
//    -neighborInfo

//Text packets:
//    -sendTextToId(id)
//    -sendTextToLocation(x,y,z)


//Software Operations:
//Debugger
//  *discoverAllNodes (bool printResultOnUart) : nodeList
//    -broadcasts getInfo packet
//    -check all getInfoReponses after 2 seconds and put it into a list
//    -return the list
//  *renewId()
//   *findGreatestId () : int newId
//    -nodeList=discoverAllNodes (false)
//    -look for the greatest id in the list
//    -ID = greatestId

//Router
//Methods:
//  route(Packet p)
  
//*getNeighborsLocation(sourceNode) : nodeList
//    -broadcasts a getNeighborInfo (hops)
//    -checks neighborInfoResponses

//PACKET class
//Members:
//  _packet char*
//  _size char
//Methods:
//  getByte(char index) : char
//  setByte(char index,char byte) : void
//  getSize() : int

//SENSOR Class
//members:
//  _variable (celcius, m/s^2, %RH)
//  _maxfrequency 
//  _x
//  _y
//  _z
//  _id (only used for debug)
//Methods:
//  getters & setters

//UART Request Operations
// -setLocation id
// -setNodeInfo id x y z "variable" minFrequency precision
// -discoverNodes hops
// -sendText X1 Y1 Z1 X2 Y2 Z2 message
// -interest Xi Yi Zi Ti Xf Yf Zf Tf celcius precision frequency



class StringUtil {
    public:
        bool equals (char* str1,char* str2,int begin,int endInclusive){
            for(int i=begin;i<=endInclusive;i++)
                if(str1[i]!=str2[i]) return false;
            return true;
        }
};
