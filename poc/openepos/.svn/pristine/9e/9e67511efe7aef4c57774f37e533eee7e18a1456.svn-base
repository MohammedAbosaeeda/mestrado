// EPOS Unified Design Utility Declarations

#ifndef __types_sw_h
#define __types_sw_h

#include <noc.h>


__BEGIN_SYS
//TODO see what can be shared between this file and its catapult version
enum{
    MSG_TYPE_CALL = 0,
    MSG_TYPE_RESP,//FIXME RESP init msgs are not really needed
    MSG_TYPE_CALL_DATA,
    MSG_TYPE_RESP_DATA,
    MSG_TYPE_ERROR
};

typedef unsigned int Channel_t;//Channel is a dummy type in software

class Comp_Msg {
public:

    inline static unsigned int get_msg_type(const NOC::Packet &pkt){ return pkt.data[1] & 0xFF; }
    inline static unsigned int get_instance_id(const NOC::Packet &pkt){ return (pkt.data[1] & 0xFF00) >> 8; }
    inline static unsigned int get_type_id(const NOC::Packet &pkt){ return (pkt.data[1] & 0xFF0000) >> 16 ; }
    inline static unsigned int get_op_id(const NOC::Packet &pkt){ return pkt.data[0]; }
    inline static unsigned int get_data(const NOC::Packet &pkt){ return pkt.data[0]; }

    /*void init_pkt(NOC::Packet &pkt){
        pkt.data[1] = header.msg_type;
        pkt.data[1] |= (header.instance_id << 8) & 0xFF00;
        pkt.data[1] |= (header.type_id << 16) & 0xFF0000;

        if((header.msg_type == MSG_TYPE_CALL_DATA) || (header.msg_type == MSG_TYPE_RESP_DATA))
            pkt.data[0] = payload.data;
        else
            pkt.data[0] = payload.op_id;
    }*/

    //resets header
    inline static void set_msg_type(NOC::Packet &pkt, unsigned int val) {
        pkt.data[1] &= 0xFFFFFF00;
        pkt.data[1] |= val & 0xFF;
    }
    inline static void set_instance_id(NOC::Packet &pkt, unsigned int val) {
        pkt.data[1] &= 0xFFFF00FF;
        pkt.data[1] |= (val << 8) & 0xFF00;
    }
    inline static void set_type_id(NOC::Packet &pkt, unsigned int val) {
        pkt.data[1] &= 0xFF00FFFF;
        pkt.data[1] |= (val << 16) & 0xFF0000;
    }
    inline static void set_op_id(NOC::Packet &pkt, unsigned int val) {pkt.data[0] = val; }
    inline static void set_data(NOC::Packet &pkt, unsigned int val) {pkt.data[0] = val; }

};



#define SMASH2(x,y) x##y
#define SMASH3(x,y,z) x##y##z

#define QMAKESTR(x) #x
#define MAKESTR(x) QMAKESTR(x)

#define PROXY(name) SMASH2(name,_Proxy)

#define IMP(name) SMASH2(name,_Imp)

//#define IPxx(name) SMASH(name,_IP)


#define DEFINE_COMPONENT(name)\
template <bool hardware>\
class IMP(name);\
\
template <>\
class IMP(name)<false> : public Scenario_Adapter<Unified::name> {\
public:\
    IMP(name)()\
        :Scenario_Adapter<Unified::name>(\
                Component_Manager::dummy_channel,Component_Manager::dummy_channel,\
                const_cast<unsigned char*>(Unified::Resource_Table_Array<Unified::name>::IID[Unified::Resource_Table_Array<Unified::name>::id_count++])),\
         using_static_id(true){}\
\
    IMP(name)(Channel_t &rx_ch, Channel_t &tx_ch,\
             unsigned char iid[Traits<Unified::name>::n_ids])\
         :Scenario_Adapter<Unified::name>(rx_ch,tx_ch,iid),\
          using_static_id(false){}\
\
    ~IMP(name)(){\
        if(using_static_id) --Unified::Resource_Table_Array<Unified::name>::id_count;\
    }\
private:\
    bool using_static_id;\
};\
\
template <>\
class IMP(name)<true> : public PROXY(name) {\
public:\
    IMP(name)() :PROXY(name)(){}\
    IMP(name)(Channel_t &rx_ch, Channel_t &tx_ch,\
             unsigned char iid[Traits<Unified::name>::n_ids])\
        :PROXY(name)(){}\
};\
\
class name: public IMP(name)<Traits<Unified::name>::hardware> {\
public:\
    name() :IMP(name)<Traits<Unified::name>::hardware>(){}\
    name(Channel_t &rx_ch, Channel_t &tx_ch,\
         unsigned char iid[Traits<Unified::name>::n_ids])\
       :IMP(name)<Traits<Unified::name>::hardware>(rx_ch,tx_ch,iid){}\
}

#define C_INCLUDE(name) MAKESTR(../../sw/include/name.h)



__END_SYS


#endif
