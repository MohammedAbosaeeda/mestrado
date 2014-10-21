// EPOS Mult Abstraction Declarations

#ifndef __mult_h
#define __mult_h

#include <aspects/dispatcher.h>
#include <aspects/proxy.h>
#include <aspects/adapter.h>
#include "../../components/include/mult.h"

__BEGIN_SYS

DISPATCHER_BEGIN(Mult,2)
    DISPATCH_BEGIN
        CALL_R_2(mult, Unified::Mult::OP_MULT, unsigned int, unsigned int)
        CALL_R_2(mult_square, Unified::Mult::OP_MULT_SQUARE, unsigned int, unsigned int)
    DISPATCH_END
DISPATCHER_END;

PROXY_BEGIN(Mult)
    unsigned int mult(unsigned int a, unsigned int b){
        return call_r<Unified::Mult::OP_MULT,unsigned int>(a,b);
    }
    unsigned int mult_square(unsigned int a, unsigned int b){
        return call_r<Unified::Mult::OP_MULT_SQUARE,unsigned int>(a,b);
    }
PROXY_END

DEFINE_COMPONENT(Mult);

__END_SYS

#endif
