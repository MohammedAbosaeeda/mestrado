/*
 * adapter.h
 *
 *  Created on: Jun 28, 2012
 *      Author: tiago
 */

#ifndef ADAPTER_H_
#define ADAPTER_H_

#include "scenario.h"

namespace System {

template<class T>
class Scenario_Adapter;


#define SCENARIO_ADAPTER_BEGIN(component, MAX_ARG_N, ALLOC_TYPE, ALLOC_IDX, ALLOC_SIZE)\
template<>\
class Scenario_Adapter<Unified::component>: public Unified::component,\
                                            public HW_Scenario<Unified::component,\
                                                                Dispatcher<Unified::component,MAX_ARG_N,1>,\
                                                                Static_Allocation<ALLOC_TYPE, ALLOC_IDX, ALLOC_SIZE> >{\
public:\
    typedef Unified::component Component;\
    typedef HW_Scenario<Unified::component,\
            Dispatcher<Unified::component,MAX_ARG_N,1>,\
            Static_Allocation<ALLOC_TYPE, ALLOC_IDX, ALLOC_SIZE> > Scenario;\
\
    Scenario_Adapter(Channel_t &rx_ch, Channel_t &tx_ch, unsigned char iid[Traits<Component>::n_ids])\
        :Component(rx_ch, tx_ch, iid),\
         Scenario(rx_ch, tx_ch, iid){\
    }

#define SCENARIO_ADAPTER_NOALLOC_BEGIN(component, MAX_ARG_N)\
template<>\
class Scenario_Adapter<Unified::component>: public Unified::component,\
                                            public HW_Scenario<Unified::component,Dispatcher<Unified::component,MAX_ARG_N,1>, void>{\
public:\
    typedef Unified::component Component;\
    typedef HW_Scenario<Unified::component,Dispatcher<Unified::component,MAX_ARG_N,1>, void> Scenario;\
\
    Scenario_Adapter(Channel_t &rx_ch, Channel_t &tx_ch, unsigned char iid[Traits<Component>::n_ids])\
        :Component(rx_ch, tx_ch, iid),\
         Scenario(rx_ch, tx_ch, iid){\
    }

#define DISPATCH_BEGIN \
    void dispatch(){\
        switch (Scenario::read_header()) {

#define DISPATCH_END \
            default:\
                Scenario::write_default_return();\
                break;\
        }\
    }

#define SCENARIO_ADAPTER_END };


#define CALL_R_8(operation, OP_ID, type_0, type_1, type_2, type_3, type_4, type_5, type_6, type_7)\
    case OP_ID:{\
        Scenario::read_args<8>();\
        unsigned int aux = (unsigned int)operation(\
                                                        (type_0) Scenario::arg(0),\
                                                        (type_1) Scenario::arg(1),\
                                                        (type_2) Scenario::arg(2),\
                                                        (type_3) Scenario::arg(3),\
                                                        (type_4) Scenario::arg(4),\
                                                        (type_5) Scenario::arg(5),\
                                                        (type_6) Scenario::arg(6),\
                                                        (type_7) Scenario::arg(7));\
        Scenario::ret(0, aux);\
        Scenario::write_return<1>();\
        break;\
    }


#define CALL_R_4(operation, OP_ID, type_0, type_1, type_2, type_3)\
    case OP_ID:{\
        Scenario::read_args<4>();\
        unsigned int aux = (unsigned int)operation(\
                                                        (type_0) Scenario::arg(0),\
                                                        (type_1) Scenario::arg(1),\
                                                        (type_2) Scenario::arg(2),\
                                                        (type_3) Scenario::arg(3));\
        Scenario::ret(0, aux);\
        Scenario::write_return<1>();\
        break;\
    }

#define CALL_R_2(operation, OP_ID, type_0, type_1)\
    case OP_ID:{\
        Scenario::read_args<2>();\
        unsigned int aux = (unsigned int)operation(\
                                                        (type_0) Scenario::arg(0),\
                                                        (type_1) Scenario::arg(1));\
        Scenario::ret(0, aux);\
        Scenario::write_return<1>();\
        break;\
    }

#define CALL_2(operation, OP_ID, type_0, type_1)\
    case OP_ID:{\
        Scenario::read_args<2>();\
        operation(\
                  (type_0) Scenario::arg(0),\
                  (type_1) Scenario::arg(1));\
        break;\
    }

#define CALL_R_1(operation, OP_ID, type_0)\
    case OP_ID:{\
        Scenario::read_args<1>();\
        unsigned int aux = (unsigned int)operation(\
                                                        (type_0) Scenario::arg(0));\
        Scenario::ret(0, aux);\
        Scenario::write_return<1>();\
        break;\
    }

#define CALL_1(operation, OP_ID, type_0)\
    case OP_ID:{\
        Scenario::read_args<1>();\
        operation(\
                  (type_0) Scenario::arg(0));\
        break;\
    }

#define CALL_R_0(operation, OP_ID)\
    case OP_ID:{\
        unsigned int aux = (unsigned int)operation();\
        Scenario::ret(0, aux);\
        Scenario::write_return<1>();\
        break;\
    }




#define IMP(name) name##_Imp
#define IP(name) name##_IP


#define DEFINE_COMPONENT(name)\
template <int imp_type>\
class IMP(name);\
\
template <>\
class IMP(name)<IMP_DISPATCHER> : public Scenario_Adapter<Unified::name>{\
public:\
    IMP(name)(Channel_t &rx_ch, Channel_t &tx_ch, unsigned char iid[Traits<Unified::name>::n_ids])\
            :Scenario_Adapter<Unified::name>(rx_ch, tx_ch, iid){}\
};\
\
template <>\
class IMP(name)<IMP_DUMMY> : public Dummy_Dispatcher {\
public:\
    IMP(name)(Channel_t &rx_ch, Channel_t &tx_ch, unsigned char iid[Traits<Unified::name>::n_ids])\
            :Dummy_Dispatcher(rx_ch, tx_ch){}\
};\
\
template <>\
class IMP(name)<IMP_STUB> : public PROXY(name) {\
public:\
    IMP(name)(Channel_t &rx_ch, Channel_t &tx_ch, unsigned char iid[Traits<Unified::name>::n_ids])\
            :PROXY(name)(rx_ch, tx_ch, iid){}\
};\
\
template <>\
class IMP(name)<IMP_UNIFIED> : public Unified::name {\
public:\
    IMP(name)(Channel_t &rx_ch, Channel_t &tx_ch, unsigned char iid[Traits<Unified::name>::n_ids])\
            :Unified::name(rx_ch, tx_ch, iid){}\
};\
\
template <>\
class IMP(name)<IMP_ERROR> : public Error_Dispatcher {\
public:\
    IMP(name)(Channel_t &rx_ch, Channel_t &tx_ch, unsigned char iid[Traits<Unified::name>::n_ids])\
            :Error_Dispatcher(rx_ch, tx_ch){}\
};\
\
template <bool top_level>\
class IP(name): public IMP(name)<IMP_TYPE<top_level,Traits<Unified::name>::hardware,Traits<Unified::name>::singleton>::Result>{\
public:\
    IP(name)(Channel_t &rx_ch, Channel_t &tx_ch, unsigned char iid[Traits<Unified::name>::n_ids])\
    :IMP(name)<IMP_TYPE<top_level,Traits<Unified::name>::hardware,Traits<Unified::name>::singleton>::Result>(rx_ch, tx_ch, iid){}\
};\
\
class name: public IP(name)<false>  {\
public:\
    name(Channel_t &rx_ch, Channel_t &tx_ch, unsigned char iid[Traits<Unified::name>::n_ids])\
    :IP(name)<false>(rx_ch, tx_ch, iid){}\
}


#define DEFINE_TOP_LEVEL(name)\
void top_level (Channel_t &rx_ch, Channel_t &tx_ch, unsigned char iid[Traits<Unified::name>::n_ids]) {\
    static IP(name)<true> dispatcher(rx_ch,tx_ch,iid);\
    dispatcher.dispatch();\
}


};

#endif /* ADAPTER_H_ */
