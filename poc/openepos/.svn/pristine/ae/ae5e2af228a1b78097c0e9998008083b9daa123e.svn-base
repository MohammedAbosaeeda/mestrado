/*
 * stub.h
 *
 *  Created on: Jun 27, 2012
 *      Author: tiago
 */

#ifndef STUB_H_
#define STUB_H_

#include "../system/types_hw.h"
#include "msg_parser.h"

namespace System{

template<class T, unsigned int ARG_SIZE, unsigned int RET_SIZE>
class Stub: public Msg_Parser<ARG_SIZE,RET_SIZE>{
private:
    typedef Msg_Parser<ARG_SIZE,RET_SIZE> Base;

private:
    Channel_t &call_ch;
    Channel_t &return_ch;

    unsigned char iid;

protected:
    Stub(Channel_t &rx_ch, Channel_t &tx_ch,
         unsigned char _iid[Traits<T>::n_ids])
        :Msg_Parser<ARG_SIZE,RET_SIZE>(),
         call_ch(tx_ch),
         return_ch(rx_ch),
         iid(_iid[0]){
    }

protected:

    template<unsigned int OP, class RET>  // no arguments, return
    RET call_r(){
        send_call<0>(OP);
        read_return<1>();
        return (RET)Base::ret(0);
    }

    template<unsigned int OP>  // no arguments, no return
    void call(){
        send_call<0>(OP);
    }

    template<unsigned int OP, class RET, class ARG0>  // one argument, return
    RET call_r(ARG0 &arg0){
        Base::arg(0, arg0);
        send_call<1>(OP);
        read_return<1>();
        return (RET)Base::ret(0);
    }

    template<unsigned int OP, class RET, class ARG0, class ARG1>  // two arguments, return
    RET call_r(ARG0 &arg0, ARG1 &arg1){
        Base::arg(0, arg0);
        Base::arg(1, arg1);
        send_call<2>(OP);
        read_return<1>();
        return (RET)Base::ret(0);
    }

    template<unsigned int OP, class RET, class ARG0, class ARG1, class ARG2, class ARG3>  // four arguments, return
    RET call_r(ARG0 &arg0, ARG1 &arg1, ARG2 &arg2, ARG3 &arg3){
        Base::arg(0, arg0);
        Base::arg(1, arg1);
        Base::arg(2, arg2);
        Base::arg(3, arg3);
        send_call<4>(OP);
        read_return<1>();
        return (RET)Base::ret(0);
    }

    template<unsigned int OP, class RET, class ARG0, class ARG1, class ARG2, class ARG3, class ARG4, class ARG5, class ARG6, class ARG7>  // eight arguments, return
    RET call_r(ARG0 &arg0, ARG1 &arg1, ARG2 &arg2, ARG3 &arg3, ARG4 &arg4, ARG5 &arg5, ARG6 &arg6, ARG7 &arg7){
        Base::arg(0, arg0);
        Base::arg(1, arg1);
        Base::arg(2, arg2);
        Base::arg(3, arg3);
        Base::arg(4, arg4);
        Base::arg(5, arg5);
        Base::arg(6, arg6);
        Base::arg(7, arg7);
        send_call<8>(OP);
        read_return<1>();
        return (RET)Base::ret(0);
    }

    template<unsigned int OP, class ARG0>  // one argument, no return
    void call(ARG0 &arg0){
        Base::arg(0, arg0);
        send_call<1>(OP);
    }

    template<unsigned int OP, class ARG0, class ARG1>  // two arguments, no return
    void call(ARG0 &arg0, ARG1 &arg1){
        Base::arg(0, arg0);
        Base::arg(1, arg1);
        send_call<2>(OP);
    }

private:
    template<int N_ARGS> void send_call(unsigned int OP_ID){
        Base::msg.header.type_id = Unified::Type2Id<T>::ID;
        Base::msg.header.instance_id = iid;
        Base::msg.header.msg_type = MSG_TYPE_CALL;
        Base::msg_payload_set_op_id(OP_ID);
        call_ch.write(Base::msg);

        Base::msg.header.msg_type = MSG_TYPE_CALL_DATA;
        for (int i = 0; i < N_ARGS; ++i) {
            Base::msg_payload_set_data(Base::arg(i));
            call_ch.write(Base::msg);
        }
    }

    template<int n_return> void read_return(){
        //return_ch.read(ret_msg); //FIXME RESP initiation msgs are not really needed
        for (int i = 0; i < n_return; ++i) {
            return_ch.read(Base::msg);
            unsigned int aux = Base::msg_payload_get_data();
            Base::ret(i, aux);
        }
    }

};


#define PROXY(name) name##_Proxy
//#define HW_STUB_T(name,templ) name##_##templ##_HW_Stub

#define PROXY_BEGIN(name, MAX_ARG_N)\
class PROXY(name): public Stub<Unified::name, MAX_ARG_N, 1> {\
protected:\
    PROXY(name)(Channel_t &rx_ch, Channel_t &tx_ch, unsigned char iid[Traits<Unified::name>::n_ids])\
        :Stub<Unified::name, MAX_ARG_N, 1>(rx_ch, tx_ch, iid){\
    }\
public:

#define PROXY_END };

/*
#define DECLARE_HW_STUB_T(name, templ)\
class HW_STUB_T(name,templ): public Stub_Common<NAME_T(name,templ), Traits<NAME_T(name,templ) >::n_ids> {\
protected:\
    HW_STUB_T(name,templ)(Channel_t &rx_ch, Channel_t &tx_ch, unsigned char iid[Traits<NAME_T(name,templ) >::n_ids])\
        :Stub_Common<NAME_T(name,templ), Traits<NAME_T(name,templ) >::n_ids>(rx_ch, tx_ch, iid){\
    }\
public:
*/


};


#endif /* STUB_H_ */
