/*
 * proxy.h
 *
 *  Created on: Jan 18, 2013
 *      Author: tiago
 */

#ifndef PROXY_EPOS_SOC_SW_H_
#define PROXY_EPOS_SOC_SW_H_

#include "../../../unified/framework/proxy.h"
#include <component_manager.h>
#include <machine.h>

using System::db;
using System::ERR;
using System::Machine;
using System::Component_Manager;

namespace Implementation {

template<typename Component>
class Proxy_Common<Component, Configurations::EPOS_SOC_Catapult, false> : Serializer<Traits<Component>::serdes_buffer> {
protected:
    Proxy_Common(Channel_t &rx_ch, Channel_t &tx_ch,
                 unsigned char iid[Traits<Component>::n_ids]) :_resource(0) {
        _resource = Component_Manager::allocate(Type2Id<Component>::ID);
        if(!_resource){
            //db<Component>(ERR) << "Cannot allocate resource. Type ID: " << (unsigned int)Type2Id<Component>::ID << "\n";
            Machine::panic();
        }
    }
    ~Proxy_Common(){
        Component_Manager::deallocate(_resource);
    }

private:
    Component_Manager::HW_Resource_Elem *_resource;
    typedef Serializer<Traits<Component>::serdes_buffer> Base;

public:
    template<unsigned int OP, typename RET>  // no arguments, return
    RET call_r(){
        Component_Manager::call(_resource, OP,
                                0,
                                type_to_npkt_1<RET>::Result,
                                Base::get_pkt_buffer());
        Base::set_pkt_cnt(type_to_npkt_1<RET>::Result);
        Base::reset();
        RET ret;
        Base::deserialize(ret);
        return ret;
    }

    template<unsigned int OP>  // no arguments, no return
    void call(){
        Component_Manager::call(_resource, OP, 0, 0, 0);
    }

    template<unsigned int OP, typename RET, typename ARG0>  // one argument, return
    RET call_r(ARG0 &arg0){
        Base::reset();
        Base::serialize(arg0);
        Component_Manager::call(_resource, OP,
                                type_to_npkt_1<ARG0>::Result,
                                type_to_npkt_1<RET>::Result,
                                Base::get_pkt_buffer());
        Base::set_pkt_cnt(type_to_npkt_1<RET>::Result);
        Base::reset();
        RET ret;
        Base::deserialize(ret);
        return ret;
    }

    template<unsigned int OP, typename RET, typename ARG0, typename ARG1>  // two arguments, return
    RET call_r(ARG0 &arg0, ARG1 &arg1){
        Base::reset();
        Base::serialize(arg0);
        Base::serialize(arg1);
        Component_Manager::call(_resource, OP,
                                type_to_npkt_2<ARG0,ARG1>::Result,
                                type_to_npkt_1<RET>::Result,
                                Base::get_pkt_buffer());
        Base::set_pkt_cnt(type_to_npkt_1<RET>::Result);
        Base::reset();
        RET ret;
        Base::deserialize(ret);
        return ret;
    }

    template<unsigned int OP, typename RET, typename ARG0, typename ARG1, typename ARG2, typename ARG3>  // four arguments, return
    RET call_r(ARG0 &arg0, ARG1 &arg1, ARG2 &arg2, ARG3 &arg3){
        Base::reset();
        Base::serialize(arg0);
        Base::serialize(arg1);
        Base::serialize(arg2);
        Base::serialize(arg3);
        Component_Manager::call(_resource, OP,
                                type_to_npkt_4<ARG0,ARG1,ARG2,ARG3>::Result,
                                type_to_npkt_1<RET>::Result,
                                Base::get_pkt_buffer());
        Base::set_pkt_cnt(type_to_npkt_1<RET>::Result);
        Base::reset();
        RET ret;
        Base::deserialize(ret);
        return ret;
    }

    template<unsigned int OP,
             typename RET,
             typename ARG0, typename ARG1, typename ARG2, typename ARG3,
             typename ARG4, typename ARG5, typename ARG6, typename ARG7>  // eight arguments, return
    RET call_r(ARG0 &arg0, ARG1 &arg1, ARG2 &arg2, ARG3 &arg3,
               ARG4 &arg4, ARG5 &arg5, ARG6 &arg6, ARG7 &arg7){
        Base::reset();
        Base::serialize(arg0);
        Base::serialize(arg1);
        Base::serialize(arg2);
        Base::serialize(arg3);
        Base::serialize(arg4);
        Base::serialize(arg5);
        Base::serialize(arg6);
        Base::serialize(arg7);
        Component_Manager::call(_resource, OP,
                                type_to_npkt_8<ARG0,ARG1,ARG2,ARG3,ARG4,ARG5,ARG6,ARG7>::Result,
                                type_to_npkt_1<RET>::Result,
                                Base::get_pkt_buffer());
        Base::set_pkt_cnt(type_to_npkt_1<RET>::Result);
        Base::reset();
        RET ret;
        Base::deserialize(ret);
        return ret;
    }

    template<unsigned int OP, typename ARG0>  // one argument, no return
    void call(ARG0 &arg0){
        Base::reset();
        Base::serialize(arg0);
        Component_Manager::call(_resource, OP,
                                type_to_npkt_1<ARG0>::Result,
                                0,
                                Base::get_pkt_buffer());
    }

    template<unsigned int OP, typename ARG0, typename ARG1>  // two arguments, no return
    void call(ARG0 &arg0, ARG1 &arg1){
        Base::reset();
        Base::serialize(arg0);
        Base::serialize(arg1);
        Component_Manager::call(_resource, OP,
                                type_to_npkt_2<ARG0,ARG1>::Result,
                                0,
                                Base::get_pkt_buffer());
    }
};
};

#endif /* PROXY_H_ */
