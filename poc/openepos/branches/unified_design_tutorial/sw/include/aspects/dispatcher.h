/*
 * disparcher.h
 *
 *  Created on: Jun 27, 2012
 *      Author: tiago
 */

#ifndef DISPARCHER_H_
#define DISPARCHER_H_

#include <system/types_sw.h>
#include <component_manager.h>

__BEGIN_SYS

template<int BUFFER_SIZE>
class Dispatcher{
public:
    //enum{
        //TODO this should be a template parameter.
        //Check impact in code size when this class is reused in more cases
      //  BUFFER_SIZE = 3
    //};

    bool idle;
    unsigned int op_id;
    int data_available;
    unsigned int data[BUFFER_SIZE];

    Dispatcher() :idle(true), op_id(0), data_available(0){

    }

    int state_machine(NOC::Packet &pkt){
        unsigned int msg_type = Comp_Msg::get_msg_type(pkt);
        if(idle && (msg_type == MSG_TYPE_CALL)){
            idle = false;
            op_id = Comp_Msg::get_op_id(pkt);
            return 0;
        }
        else if(!idle && (msg_type == MSG_TYPE_CALL_DATA)){
            data[data_available++] = Comp_Msg::get_data(pkt);
            return 0;
        }
        else
            return -1;
    }

    bool has_data(int n_data){
        if(n_data == data_available){
            data_available = 0;
            return true;
        }
        else return false;
    }

    unsigned int& arg(int i) {return data[i];}

    void resp(unsigned int& resp) {data[0] = resp;}
    unsigned int& resp() {return data[0];}

    void send_resp(NOC::Address &src_phy, NOC::Packet &pkt){
        //This one executes in the context of Comp_Mangm's ISR which already has the lock for the NOC
        NOC &_noc = NOC::get_instance();
        _noc.send_header(&src_phy);

        Comp_Msg::set_msg_type(pkt, MSG_TYPE_RESP_DATA);
        Comp_Msg::set_data(pkt, resp());

        _noc.send(&pkt);
    }

};

#define DISPATCHER(name) SMASH2(name,_Dispatcher)


#define MAKEDB2(x) SMASH2(x,_Dispatcher::dispatch:)
#define DISPATCHER_WRN_STR(name) MAKESTR(MAKEDB2(name))


#define DISPATCHER_BEGIN(name, maxargs)\
class DISPATCHER(name) : public Dispatcher<maxargs>, public Scenario_Adapter<Unified::name>{\
public:\
    typedef Unified::name Component_No_Adapter;\
    typedef Scenario_Adapter<Unified::name> Component;\
    typedef Dispatcher<maxargs> Disp;\
    typedef DISPATCHER(name) It_Self;\
\
    DISPATCHER(name)(unsigned char iid[Traits<Unified::name>::n_ids])\
        :Disp(),\
         Component(Component_Manager::dummy_channel,Component_Manager::dummy_channel,iid)\
    {}\
public:

#define DISPATCHER_END };

#define DISPATCH_BEGIN\
    static void dispatch(NOC::Address &src_phy, NOC::Packet &pkt, void *this_obj){\
        reinterpret_cast<It_Self*>(this_obj)->local_dispatch(src_phy, pkt);\
    }\
public:\
    void local_dispatch(NOC::Address &src_phy, NOC::Packet &pkt){\
\
        if(Disp::state_machine(pkt) < 0)\
            db<Component>(WRN) << " Protocol error on dispatcher comp. ID " << (unsigned int)Unified::Type2Id<Component_No_Adapter>::ID << " no one knows the IID\n";\
\
        switch(Disp::op_id){

#define DISPATCH_END \
            default:{\
                break;\
            }\
        }\
        Disp::idle = true;\
    }


#define CALL_R_4(operation, OP_ID, type_0, type_1, type_2, type_3)\
case OP_ID: {\
    if(!Disp::has_data(4)) return;\
\
    unsigned int resp = (unsigned int)Component::operation(\
                                          (type_0)Disp::arg(0),\
                                          (type_1)Disp::arg(1),\
                                          (type_2)Disp::arg(2),\
                                          (type_3)Disp::arg(3));\
    Disp::resp(resp);\
\
    Disp::send_resp(src_phy,pkt);\
    break;\
}

#define CALL_R_8(operation, OP_ID, type_0, type_1, type_2, type_3, type_4, type_5, type_6, type_7)\
case OP_ID: {\
    if(!Disp::has_data(4)) return;\
\
    unsigned int resp = (unsigned int)Component::operation(\
                                          (type_0)Disp::arg(0),\
                                          (type_1)Disp::arg(1),\
                                          (type_2)Disp::arg(2),\
                                          (type_3)Disp::arg(3),\
                                          (type_4)Disp::arg(4),\
                                          (type_5)Disp::arg(5),\
                                          (type_6)Disp::arg(6),\
                                          (type_7)Disp::arg(7));\
    Disp::resp(resp);\
\
    Disp::send_resp(src_phy,pkt);\
    break;\
}

#define CALL_R_2(operation, OP_ID, type_0, type_1)\
case OP_ID: {\
    if(!Disp::has_data(2)) return;\
\
    unsigned int resp = (unsigned int)Component::operation((type_0)Disp::arg(0),(type_1)Disp::arg(1));\
    Disp::resp(resp);\
\
    Disp::send_resp(src_phy,pkt);\
    break;\
}

#define CALL_2(operation, OP_ID, type_0, type_1)\
case OP_ID: {\
    if(!Disp::has_data(2)) return;\
\
    Component::operation((type_0)Disp::arg(0),(type_1)Disp::arg(1));\
    break;\
}

#define CALL_R_1(operation, OP_ID, type_0)\
case OP_ID: {\
    if(!Disp::has_data(1)) return;\
\
    unsigned int resp = (unsigned int)Component::operation((type_0)Disp::arg(0));\
    Disp::resp(resp);\
\
    Disp::send_resp(src_phy,pkt);\
    break;\
}

#define CALL_1(operation, OP_ID, type_0)\
case OP_ID: {\
    if(!Disp::has_data(1)) return;\
\
    Component::operation((type_0)Disp::arg(0));\
    break;\
}

#define CALL_R_0(operation, OP_ID)\
case OP_ID: {\
    unsigned int resp = (unsigned int)Component::operation();\
    Disp::resp(resp);\
\
    Disp::send_resp(src_phy,pkt);\
    break;\
}


__END_SYS

#endif /* DISPARCHER_H_ */
