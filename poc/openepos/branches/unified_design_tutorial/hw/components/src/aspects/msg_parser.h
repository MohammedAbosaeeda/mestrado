/*
 * msg_parser.h
 *
 *  Created on: Jun 29, 2012
 *      Author: tiago
 */

#ifndef MSG_PARSER_H_
#define MSG_PARSER_H_

#include "../system/types_hw.h"

namespace System {

template<unsigned int ARG_SIZE, unsigned int RET_SIZE>
class Msg_Parser {
protected:
    Msg_t msg;

private:
    enum{
        BUFFER_SIZE = (ARG_SIZE > RET_SIZE) ? ARG_SIZE : RET_SIZE
    };
    unsigned int buffer[BUFFER_SIZE];

public:
    unsigned int& arg(int i){ return buffer[i];}
    unsigned int& ret(int i){ return buffer[i];}
    template<typename T> void arg(int i, T& val){ buffer[i] = (unsigned int)val;}
    void ret(int i, unsigned int& val){ buffer[i] = val;}

protected:
    unsigned int msg_payload_get_op_id(){
        return msg.payload.to_uint();
    }
    void msg_payload_set_op_id(unsigned int val){
        ac_int<32,false> tmp(val);
        msg.payload.set_slc(0,tmp);
    }

   unsigned int msg_payload_get_data(){
        return msg.payload.to_uint();
    }
    void msg_payload_set_data(unsigned int val){
        ac_int<32,false> tmp(val);
        msg.payload.set_slc(0,tmp);
    }
};

};


#endif /* MSG_PARSER_H_ */
