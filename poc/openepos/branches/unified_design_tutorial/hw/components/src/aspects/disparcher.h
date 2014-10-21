/*
 * disparcher.h
 *
 *  Created on: Jun 27, 2012
 *      Author: tiago
 */

#ifndef DISPARCHER_H_
#define DISPARCHER_H_

#include "../system/types_hw.h"
#include "msg_parser.h"

namespace System {

class Dummy_Dispatcher{

private:

    Channel_t &rx_ch;
    Channel_t &tx_ch;

    Msg_t   msg;

protected:

    Dummy_Dispatcher(Channel_t &_rx_ch, Channel_t &_tx_ch)
        :rx_ch(_rx_ch),
         tx_ch(_tx_ch){
    }

public:
    void dispatch(){
        rx_ch.read(msg);
        tx_ch.write(msg);
    }

};

class Error_Dispatcher{

protected:
    Error_Dispatcher(Channel_t &_rx_ch, Channel_t &_tx_ch){ }

public:
    void dispatch(){
        //The design will be completely optimized away and generate an error
    }

};


template<class T, unsigned int ARG_SIZE, unsigned int RET_SIZE>
class Dispatcher: public Msg_Parser<ARG_SIZE,RET_SIZE>{
private:
    typedef Msg_Parser<ARG_SIZE,RET_SIZE> Base;

private:
    Channel_t &call_ch;
    Channel_t &return_ch;

    unsigned char iid;

protected:

    Dispatcher(Channel_t &rx_ch, Channel_t &tx_ch,
               unsigned char _iid[Traits<T>::n_ids])
        :Base(),
         call_ch(rx_ch),
         return_ch(tx_ch),
         iid(_iid[0])
         {  }

public:

    unsigned int read_header(){
        call_ch.read(Base::msg);
        return Base::msg_payload_get_op_id();

    }

    template<int n_args> void read_args(){
        for (int i = 0; i < n_args; ++i) {
            call_ch.read(Base::msg);
            unsigned int aux = Base::msg_payload_get_data();
            Base::arg(i, aux);
        }
     }

    template<int n_return> void write_return(){
        Base::msg.header.type_id = Unified::Type2Id<T>::ID;
        Base::msg.header.instance_id = iid;
        //FIXME RESP initiation msgs are not really needed
        //ret_msg.header.msg_type = MSG_TYPE_RESP;
        //Msg_Payload_Init init(ret_msg.payload); init.set_op_id(op_id);
        //return_ch.write(ret_msg);

        Base::msg.header.msg_type = MSG_TYPE_RESP_DATA;
        for (int i = 0; i < n_return; ++i) {
            Base::msg_payload_set_data(Base::ret(i));
            return_ch.write(Base::msg);
        }
    }

    void write_default_return(){
        Base::msg.header.type_id = Unified::Type2Id<T>::ID;
        Base::msg.header.instance_id = iid;
        Base::msg.header.msg_type = MSG_TYPE_ERROR;
        Base::msg_payload_set_op_id(0);
        return_ch.write(Base::msg);
    }

};


};

#endif /* DISPARCHER_H_ */
