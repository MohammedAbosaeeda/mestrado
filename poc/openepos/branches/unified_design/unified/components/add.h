// EPOS Add Abstraction Declarations

#ifndef __add_unified_h
#define __add_unified_h

#include "component.h"

namespace Implementation {

class Add : public Component{

public:
    enum {
        OP_ADD = 0xF0
    };


public:

    Add(Channel_t &rx_ch, Channel_t &tx_ch, unsigned char iid[Traits<Add>::n_ids])
        :Component(rx_ch, tx_ch, iid[0]){}

    unsigned int add(unsigned int a, unsigned int b){
        return a + b;
    }

};

PROXY_BEGIN(Add)
    unsigned int add(unsigned int a, unsigned int b){
        return Base::call_r<Add::OP_ADD,unsigned int>(a,b);
    }
PROXY_END

AGENT_BEGIN(Add)
    D_CALL_R_2(add,OP_ADD, unsigned int, unsigned int,unsigned int)
AGENT_END

};

DECLARE_COMPONENT(Add);

#endif
