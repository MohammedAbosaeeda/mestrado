// EPOS Add Abstraction Declarations

#ifndef __add_h
#define __add_h

#include "aspects/proxy.h"
#include "aspects/adapter.h"
#include "../../../components/include/add.h"

namespace System {

PROXY_BEGIN(Add,2)
    unsigned int add(unsigned int a, unsigned int b){
        return call_r<Unified::Add::OP_ADD,unsigned int>(a,b);
    }
PROXY_END

SCENARIO_ADAPTER_NOALLOC_BEGIN(Add,2)
    DISPATCH_BEGIN
        CALL_R_2(add, Unified::Add::OP_ADD, unsigned int, unsigned int)
    DISPATCH_END
SCENARIO_ADAPTER_END

DEFINE_COMPONENT(Add);

};


#endif
