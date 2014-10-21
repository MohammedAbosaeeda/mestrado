// EPOS Add Abstraction Declarations

#ifndef __add_unified_h
#define __add_unified_h

#include "component.h"

namespace Unified {

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

}

#endif
