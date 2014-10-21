// EPOS Add Abstraction Declarations

#ifndef __add_h
#define __add_h

#include <aspects/dispatcher.h>
#include <aspects/proxy.h>
#include <aspects/adapter.h>
#include "../../components/include/add.h"

__BEGIN_SYS

DISPATCHER_BEGIN(Add,2)
    DISPATCH_BEGIN
        CALL_R_2(add, Unified::Add::OP_ADD, unsigned int, unsigned int)
    DISPATCH_END
DISPATCHER_END


PROXY_BEGIN(Add)
    unsigned int add(unsigned int a, unsigned int b){
        //HW_CALL(Add_Unified::OP_ADD, a, b);
        //return HW_CALL_RETURN;
        return call_r<Unified::Add::OP_ADD,unsigned int>(a,b);
    }
PROXY_END

DEFINE_COMPONENT(Add);

__END_SYS

#endif
