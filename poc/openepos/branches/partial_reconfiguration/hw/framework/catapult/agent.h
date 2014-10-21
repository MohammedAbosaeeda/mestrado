/*
 * proxy.h
 *
 *  Created on: Jan 18, 2013
 *      Author: tiago
 */

#ifndef AGENT_EPOS_SOC_HW_H_
#define AGENT_EPOS_SOC_HW_H_

#include "../../../unified/framework/meta.h"
#include "../../../unified/framework/agent.h"
#include "catapult.h"
#include "../../../unified/framework/resource_table.h"
#include "../../../unified/framework/resource_table_iids.h"

namespace Implementation {

template<class T>
class Agent_Common<T, Configurations::EPOS_SOC_Catapult, true> : public Serializer<Traits<T>::serdes_buffer>{

private:
    typedef Serializer<Traits<T>::serdes_buffer> Base;

    Channel_t &call_ch;
    Channel_t &return_ch;

    unsigned char iid;

    unsigned char last_call_X;
    unsigned char last_call_Y;
    unsigned char last_call_local;

    Catapult::RMI_Msg msg;

public:
    Agent_Common(Channel_t &rx_ch, Channel_t &tx_ch, unsigned char _iid[Traits<T>::n_ids])
        :Base(),
         call_ch(rx_ch),
         return_ch(tx_ch),
         iid(_iid[0]),
         last_call_X(0xFF),
         last_call_Y(0xFF),
         last_call_local(0xFF){

    }

private:
    unsigned int read_header(){
        call_ch.read(msg);

        last_call_X = msg.phy_addr.X;
        last_call_Y = msg.phy_addr.Y;
        last_call_local = msg.phy_addr.local;

        return msg.phy_data.payload.to_uint();
    }

public:
    template<unsigned int ARGS>
    bool read_args(){
        typename Base::pkt_type* buff = Base::get_pkt_buffer();
        for (int i = 0; i < ARGS; ++i) {
            call_ch.read(msg);
            buff[i] = msg.phy_data.payload;
        }
        Base::set_pkt_cnt(ARGS);

        return true;
    }

    void finish(){}

    template<unsigned int N_RET>
    void write_return(){
        msg.phy_addr.X = last_call_X;
        msg.phy_addr.Y = last_call_Y;
        msg.phy_addr.local = last_call_local;

        msg.phy_data.header.type_id = Type2Id<T>::ID;
        msg.phy_data.header.instance_id = iid;
        //FIXME RESP initiation msgs are not really needed
        //ret_msg.header.msg_type = MSG_TYPE_RESP;
        //Msg_Payload_Init init(ret_msg.payload); init.set_op_id(op_id);
        //return_ch.write(ret_msg);

        msg.phy_data.header.msg_type = MSG_TYPE_RESP_DATA;
        typename Base::pkt_type* buff = Base::get_pkt_buffer();
        for (int i = 0; i < N_RET; ++i) {
            msg.phy_data.payload = buff[i];
            return_ch.write(msg);
        }
    }

    void top_level(){
        static_cast<Agent<T>*>(this)->dispatch(read_header());
    }

};

template<typename T>
class Agent_Dummy{

private:

    Channel_t &rx_ch;
    Channel_t &tx_ch;

    Catapult::RMI_Msg   msg;

public:

    Agent_Dummy(Channel_t &_rx_ch, Channel_t &_tx_ch, unsigned char _iid[Traits<T>::n_ids])
        :rx_ch(_rx_ch),
         tx_ch(_tx_ch){
    }

public:
    void top_level(){
        rx_ch.read(msg);
        tx_ch.write(msg);
    }

};


#define DECLARE_HLS_TOP_LEVEL(name)\
void HW_NODE(name) (Implementation::Channel_t &rx_ch, Implementation::Channel_t &tx_ch, unsigned char iid[Implementation::Traits<Implementation::name>::n_ids]) {\
    static Implementation::IF<Implementation::Traits<Implementation::name>::hardware,\
                              Implementation::Agent<Implementation::name>,\
                              Implementation::Agent_Dummy<Implementation::name> >::Result\
        agent(rx_ch,tx_ch,iid);\
    agent.top_level();\
}

};

#endif /* PROXY_H_ */
