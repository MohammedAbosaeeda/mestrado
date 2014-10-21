// EPOS Mult Abstraction Test Program

#include "../../unified/components/component.h"
#include <framework/proxy.h>
#include <framework/agent.h>
#include <component_manager.h>
#include <component_controller.h>

#define METHOD_ONE
#define METHOD_TWO

#define CREATE_PROXY

#define CREATE_AGENT

namespace Implementation {

template <> struct Traits<Dummy_Component_For_Proxy>: public Traits<void>
{
    static const bool hardware = true;

    static const bool singleton = false;

    const static bool static_alloc = false;
    typedef void Alloc_Obj_Type;
    typedef void Alloc_Idx;
    const static unsigned int Alloc_Max = 0;

#ifdef METHOD_TWO
    const static unsigned int serdes_buffer = 8;
#else
    const static unsigned int serdes_buffer = 4;
#endif

    static const unsigned int n_ids = 1;
};

template <> struct Traits<Dummy_Component_For_Agent>: public Traits<void>
{
    static const bool hardware = false;

    static const bool singleton = false;

    const static bool static_alloc = false;
    typedef void Alloc_Obj_Type;
    typedef void Alloc_Idx;
    const static unsigned int Alloc_Max = 0;

#ifdef METHOD_TWO
    const static unsigned int serdes_buffer = 8;
#else
    const static unsigned int serdes_buffer = 4;
#endif

    static const unsigned int n_ids = 1;
};

#ifdef CREATE_PROXY

class Dummy_Component_For_Proxy : public Component{
public:
    enum {
        OP_FUNC_0 = 0,
        OP_FUNC_1
    };
public:
    Dummy_Component_For_Proxy(Channel_t &rx_ch, Channel_t &tx_ch, unsigned char iid[Traits<Dummy_Component_For_Proxy>::n_ids])
        :Component(rx_ch, tx_ch, iid[0]){}
};

PROXY_BEGIN(Dummy_Component_For_Proxy)
#ifdef METHOD_ONE
    void func_0(unsigned int);
#endif
#ifdef METHOD_TWO
    unsigned int func_1(unsigned int,unsigned int);
#endif
PROXY_END

#ifdef METHOD_ONE
void Implementation::Proxy<Implementation::Dummy_Component_For_Proxy>::func_0(unsigned int arg){
    Base::call<Dummy_Component_For_Proxy::OP_FUNC_0>(arg);
}
#endif
#ifdef METHOD_TWO
unsigned int Implementation::Proxy<Implementation::Dummy_Component_For_Proxy>::func_1(unsigned int arg0,unsigned int arg1){
    return Base::call_r<Dummy_Component_For_Proxy::OP_FUNC_1,unsigned int>(arg0,arg1);
}
#endif

#endif





#ifdef CREATE_AGENT

class Dummy_Component_For_Agent : public Component{
public:
    enum {
        OP_FUNC_0 = 0,
        OP_FUNC_1
    };
public:
    Dummy_Component_For_Agent(Channel_t &rx_ch, Channel_t &tx_ch, unsigned char iid[Traits<Dummy_Component_For_Agent>::n_ids])
        :Component(rx_ch, tx_ch, iid[0]){}

#ifdef METHOD_ONE
    void func_0(unsigned int arg0){}
#endif
#ifdef METHOD_TWO
    unsigned int func_1(unsigned int arg0,unsigned arg1){}
#endif
};


AGENT_BEGIN(Dummy_Component_For_Agent)
#ifdef METHOD_ONE
    D_CALL_1(func_0,OP_FUNC_0, unsigned int)
#endif
#ifdef METHOD_TWO
    D_CALL_R_2(func_1,OP_FUNC_1, unsigned int,unsigned int,unsigned int)
#endif
AGENT_END

#endif

};


void manager_dummy_init()
{

    unsigned int buffer = 0;

#ifdef CREATE_PROXY
    buffer =
    System::Component_Controller::alloc_proxy(0,0,0,0, 0);
    if(buffer != System::Component_Controller::CMD_RESULT_ERR){
        System::Component_Manager::HW_Resource *rsc = new(System::kmalloc(sizeof(System::Component_Manager::HW_Resource))) System::Component_Manager::HW_Resource(buffer, (System::Type_Id)0);
        System::Component_Manager::HW_Resource_Elem *rsc_elem = new(System::kmalloc(sizeof(System::Component_Manager::HW_Resource_Elem))) System::Component_Manager::HW_Resource_Elem(rsc);
        System::Component_Manager::_hw_resources.insert(rsc_elem);
    }
#endif

#ifdef CREATE_AGENT
    System::Component_Controller::alloc_agent((unsigned int)0, 0,
                                      (unsigned int)0,
                                      (unsigned int)0);
#endif

}

#ifdef CREATE_PROXY
Implementation::Proxy<Implementation::Dummy_Component_For_Proxy>
proxy(System::Component_Manager::dummy_channel,System::Component_Manager::dummy_channel,0);
#endif

#ifdef CREATE_AGENT
Implementation::Agent<Implementation::Dummy_Component_For_Agent>
agent(System::Component_Manager::dummy_channel,System::Component_Manager::dummy_channel,0);
#endif

int main(){
    manager_dummy_init();
#ifdef CREATE_AGENT
    System::Component_Controller::agent_call_info call_info;
    agent.top_level(call_info);
#endif
    return 0;
}
