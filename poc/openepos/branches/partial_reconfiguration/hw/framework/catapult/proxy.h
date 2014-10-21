/*
 * proxy.h
 *
 *  Created on: Jan 18, 2013
 *      Author: tiago
 */

#ifndef PROXY_EPOS_SOC_HW_H_
#define PROXY_EPOS_SOC_HW_H_

#include "../../../unified/framework/proxy.h"
#include "catapult.h"
#include "../../../unified/framework/ctti.h"
#include "../../../unified/framework/resource_table.h"
#include "../../../unified/framework/resource_table_iids.h"

namespace Implementation {

template<typename Component>
class Proxy_Common<Component, Configurations::EPOS_SOC_Catapult, true>
        : public Serializer<Traits<Component>::serdes_buffer> {

private:
    typedef Serializer<Traits<Component>::serdes_buffer> Base;

private:
    Channel_t &call_ch;
    Channel_t &return_ch;

    unsigned char iid;

    Catapult::RMI_Msg msg;

protected:
    Proxy_Common(Channel_t &rx_ch, Channel_t &tx_ch,
                 unsigned char _iid[Traits<Component>::n_ids])
                 :Base(),
                  call_ch(tx_ch),
                  return_ch(rx_ch),
                  iid(_iid[0]){   }

protected:

    template<unsigned int OP, typename RET>  // no arguments, return
    RET call_r(){
        send_call<0>(OP);
        read_return<type_to_npkt_1<RET>::Result>();
        Base::reset();
        RET ret;
        Base::deserialize(ret);
        return ret;
    }

    template<unsigned int OP>  // no arguments, no return
    void call(){
        send_call<0>(OP);
    }

    template<unsigned int OP, typename RET, typename ARG0>  // one argument, return
    RET call_r(ARG0 &arg0){
        Base::reset();
        Base::serialize(arg0);
        send_call<type_to_npkt_1<ARG0>::Result>(OP);
        read_return<type_to_npkt_1<RET>::Result>();
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
        send_call<type_to_npkt_2<ARG0,ARG1>::Result>(OP);
        read_return<type_to_npkt_1<RET>::Result>();
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
        send_call<type_to_npkt_4<ARG0,ARG1,ARG2,ARG3>::Result>(OP);
        read_return<type_to_npkt_1<RET>::Result>();
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
        send_call<type_to_npkt_8<ARG0,ARG1,ARG2,ARG3,ARG4,ARG5,ARG6,ARG7>::Result>(OP);
        read_return<type_to_npkt_1<RET>::Result>();
        Base::reset();
        RET ret;
        Base::deserialize(ret);
        return ret;
    }

    template<unsigned int OP, typename ARG0>  // one argument, no return
    void call(ARG0 &arg0){
        Base::reset();
        Base::serialize(arg0);
        send_call<type_to_npkt_1<ARG0>::Result>(OP);
    }

    template<unsigned int OP, typename ARG0, typename ARG1>  // two arguments, no return
    void call(ARG0 &arg0, ARG1 &arg1){
        Base::reset();
        Base::serialize(arg0);
        Base::serialize(arg1);
        send_call<type_to_npkt_2<ARG0,ARG1>::Result>(OP);
    }

private:
    template<int N_ARGS> void send_call(unsigned int OP_ID){

        int idx = PHY_Table::type2IDX(Type2Id<Component>::ID, iid);
        msg.phy_addr.local = PHY_Table::LOCAL[idx];
        msg.phy_addr.Y = PHY_Table::Y[idx];
        msg.phy_addr.X = PHY_Table::X[idx];

        msg.phy_data.header.type_id = Type2Id<Component>::ID;
        msg.phy_data.header.instance_id = iid;
        msg.phy_data.header.msg_type = MSG_TYPE_CALL;
        msg.phy_data.payload = OP_ID;
        call_ch.write(msg);

        msg.phy_data.header.msg_type = MSG_TYPE_CALL_DATA;
        typename Base::pkt_type* buff = Base::get_pkt_buffer();
        for (int i = 0; i < N_ARGS; ++i) {
            msg.phy_data.payload = buff[i];
            call_ch.write(msg);
        }
    }

    template<int n_return> void read_return(){
        //return_ch.read(ret_msg); //FIXME RESP initiation msgs are not really needed
        typename Base::pkt_type* buff = Base::get_pkt_buffer();
        for (int i = 0; i < n_return; ++i) {
            return_ch.read(msg);
            buff[i] = msg.phy_data.payload.to_uint();
        }
        Base::set_pkt_cnt(n_return);
    }
};


};

#endif /* PROXY_H_ */
