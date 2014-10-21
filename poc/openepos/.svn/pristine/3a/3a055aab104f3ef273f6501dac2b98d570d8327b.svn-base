/*
 * proxy.h
 *
 *  Created on: Jan 18, 2013
 *      Author: tiago
 */

#ifndef AGENT_EPOS_SOC_SW_H_
#define AGENT_EPOS_SOC_SW_H_

#include "../../../unified/framework/agent.h"
#include <component_controller.h>

namespace Implementation {

template<class T>
class Agent_Common<T, Configurations::EPOS_SOC_Catapult, false> : public Serializer<Traits<T>::serdes_buffer>{

public:
    typedef Serializer<Traits<T>::serdes_buffer> Base;

private:
    bool idle;
    unsigned int op_id;
    unsigned int buffer;

public:
    Agent_Common(Channel_t &rx_ch, Channel_t &tx_ch, unsigned char iid[Traits<T>::n_ids]) :Base(),
                  idle(true), op_id(0), buffer(0){

    }

    void state_machine(System::Component_Controller::agent_call_info &call_info){
        if(idle){
            idle = false;
            op_id = System::Component_Controller::receive_call(call_info.buffer);
            buffer = call_info.buffer;
        }
        else {
            typename Base::pkt_type* data_buffer = Base::get_pkt_buffer();
            data_buffer[Base::get_pkt_cnt()] = System::Component_Controller::receive_call_data(buffer);
            Base::set_pkt_cnt(Base::get_pkt_cnt()+1);
        }
    }

    template<unsigned int ARGS>
    bool read_args(){
        return ARGS == Base::get_pkt_cnt();
    }

    void finish(){
        idle = true;
    }

    template<unsigned int N_RET>
    void write_return(){
        typename Base::pkt_type* data_buffer = Base::get_pkt_buffer();

        System::Component_Controller::send_return_data(buffer, N_RET, data_buffer);

        Base::set_pkt_cnt(0);
    }

     void top_level(System::Component_Controller::agent_call_info &call_info){
         state_machine(call_info);
         static_cast<Agent<T>*>(this)->dispatch(op_id);
     }

     static void static_top_level(System::Component_Controller::agent_call_info &call_info){
        reinterpret_cast<Agent<T>*>((void*)call_info.object_address)->top_level(call_info);
    }

};


};

#endif /* PROXY_H_ */
