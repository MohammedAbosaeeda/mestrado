/*
 * agent.h
 *
 *  Created on: Jan 18, 2013
 *      Author: tiago
 */

#ifndef AGENT_COMMON_H_
#define AGENT_COMMON_H_

#include "../traits.h"
#include "serializer.h"
#include "scenario_adapter.h"

namespace Implementation {


template<class C>
class Agent;

template<class Component,
         class Platform,
         bool hardware>
class Agent_Common;

};

#ifdef HIGH_LEVEL_SYNTHESIS
#include "../../hw/framework/catapult/agent.h"
#else
//EPOS_SoC software proxy
#include "../../sw/include/framework/agent.h"
#endif


//Helpers
#define AGENT_BEGIN(name)\
template<>\
class Agent<name> : public Scenario_Adapter<name>,\
                    public Agent_Common<name, Traits<Sys>::Platform, Traits<Sys>::hardware_domain>{\
public:\
    Agent(Channel_t &rx_ch, Channel_t &tx_ch, unsigned char iid[Traits<name>::n_ids])\
        :Scenario_Adapter<name>(rx_ch,tx_ch,iid),\
         Agent_Common<name,Traits<Sys>::Platform, Traits<Sys>::hardware_domain>(rx_ch, tx_ch, iid){ }\
\
    typedef name _Component;\
    typedef Scenario_Adapter<name> Component;\
    typedef Agent_Common<name, Traits<Sys>::Platform, Traits<Sys>::hardware_domain> Base;\
\
    void dispatch(unsigned int op){\
        Base::reset();\
        switch (op) {

#define AGENT_END \
        default:\
            break;\
    }\
}\
};


#define D_CALL_R_4(operation, OP_ID, ret_type_0, type_0, type_1, type_2, type_3)\
case _Component::OP_ID: {\
    if(!Base::read_args<type_to_npkt_4<type_0,type_1,type_2,type_3>::Result>()) return;\
\
    Base::reset();\
    type_0 t0; Base::deserialize(t0);\
    type_1 t1; Base::deserialize(t1);\
    type_2 t2; Base::deserialize(t2);\
    type_3 t3; Base::deserialize(t3);\
    ret_type_0 resp = Component::operation(t0,t1,t2,t3);\
    Base::reset();\
    Base::serialize(resp);\
    Base::write_return<type_to_npkt_1<ret_type_0>::Result>();\
    Base::finish();\
    \
    break;\
}

#define D_CALL_R_8(operation, OP_ID, ret_type_0, type_0, type_1, type_2, type_3, type_4, type_5, type_6, type_7)\
case _Component::OP_ID: {\
    if(!Base::read_args<type_to_npkt_8<type_0,type_1,type_2,type_3,type_4,type_5,type_6,type_7>::Result>()) return;\
\
    Base::reset();\
    type_0 t0; Base::deserialize(t0);\
    type_1 t1; Base::deserialize(t1);\
    type_2 t2; Base::deserialize(t2);\
    type_3 t3; Base::deserialize(t3);\
    type_4 t4; Base::deserialize(t4);\
    type_5 t5; Base::deserialize(t5);\
    type_6 t6; Base::deserialize(t6);\
    type_7 t7; Base::deserialize(t7);\
    ret_type_0 resp = Component::operation(t0,t1,t2,t3,t4,t5,t6,t7);\
    Base::reset();\
    Base::serialize(resp);\
\
    Base::write_return<type_to_npkt_1<ret_type_0>::Result>();\
    Base::finish();\
    break;\
}

#define D_CALL_R_2(operation, OP_ID, ret_type_0, type_0, type_1)\
case _Component::OP_ID: {\
    if(!Base::read_args<type_to_npkt_2<type_0,type_1>::Result>()) return;\
\
    Base::reset();\
    type_0 t0; Base::deserialize(t0);\
    type_1 t1; Base::deserialize(t1);\
    ret_type_0 resp = Component::operation(t0,t1);\
    Base::reset();\
    Base::serialize(resp);\
\
    Base::write_return<type_to_npkt_1<ret_type_0>::Result>();\
    Base::finish();\
    break;\
}

#define D_CALL_2(operation, OP_ID, type_0, type_1)\
case _Component::OP_ID: {\
    if(!Base::read_args<type_to_npkt_2<type_0,type_1>::Result>()) return;\
\
    Base::reset();\
    type_0 t0; Base::deserialize(t0);\
    type_1 t1; Base::deserialize(t1);\
    Component::operation(t0,t1);\
    Base::finish();\
    break;\
}

#define D_CALL_R_1(operation, OP_ID, ret_type_0, type_0)\
case _Component::OP_ID: {\
    if(!Base::read_args<type_to_npkt_1<type_0>::Result>()) return;\
\
    Base::reset();\
    type_0 t0; Base::deserialize(t0);\
    ret_type_0 resp = Component::operation(t0);\
    Base::reset();\
    Base::serialize(resp);\
\
    Base::write_return<type_to_npkt_1<ret_type_0>::Result>();\
    Base::finish();\
    break;\
}

#define D_CALL_1(operation, OP_ID, type_0)\
case _Component::OP_ID: {\
    if(!Base::read_args<type_to_npkt_1<type_0>::Result>()) return;\
\
    Base::reset();\
    type_0 t0; Base::deserialize(t0);\
    Component::operation(t0);\
    Base::finish();\
    break;\
}

#define D_CALL_R_0(operation, OP_ID, ret_type_0)\
case _Component::OP_ID: {\
    ret_type_0 resp = Component::operation();\
    Base::reset();\
    Base::serialize(resp);\
\
    Base::write_return<type_to_npkt_1<ret_type_0>::Result>();\
    Base::finish();\
    break;\
}

#define D_CALL_0(operation, OP_ID)\
case _Component::OP_ID: {\
    Component::operation();\
    Base::finish();\
    break;\
}


#endif /* AGENT_H_ */
