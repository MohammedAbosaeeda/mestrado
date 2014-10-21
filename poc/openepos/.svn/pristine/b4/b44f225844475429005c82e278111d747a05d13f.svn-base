/*
 * serializer.h
 *
 *  Created on: Jan 22, 2013
 *      Author: tiago
 */

#ifndef SERIALIZER_EPOS_SOC_HW_H_
#define SERIALIZER_EPOS_SOC_HW_H_

#include "../../../unified/framework/serializer_common.h"
#include "catapult.h"

//Specialization for pkt_type 'ac_int<32,false>'
namespace Implementation { namespace serializer_imp {

//Specializations for unsigned int
template<> template<>
inline void Serialization<ac_int<32,false> >::serialize(ac_int<32,false> pkts[1], unsigned int &arg){
    pkts[0] = arg;
}
template<> template<>
inline void Serialization<ac_int<32,false> >::deserialize(ac_int<32,false> pkts[1], unsigned int &arg){
    arg = pkts[0];
}

//Specializations for unsigned char
template<> template<>
inline void Serialization<ac_int<32,false> >::serialize(ac_int<32,false> pkts[1], unsigned char &arg){
    pkts[0] = arg;
}
template<> template<>
inline void Serialization<ac_int<32,false> >::deserialize(ac_int<32,false> pkts[1], unsigned char &arg){
    arg = pkts[0];
}

//Specializations for int
template<> template<>
inline void Serialization<ac_int<32,false> >::serialize(ac_int<32,false> pkts[1], int &arg){
    pkts[0] = arg;
}
template<> template<>
inline void Serialization<ac_int<32,false> >::deserialize(ac_int<32,false> pkts[1], int &arg){
    arg = pkts[0];
}

//Specializations for safe_pkt
template<> template<>
inline void Serialization<ac_int<32,false> >::serialize(ac_int<32,false> pkts[sizeof(safe_pkt_t)/4], safe_pkt_t &arg){
    pkts[0].set_slc(0,ac_int<8,false>(arg.data[0]));
    pkts[0].set_slc(8,ac_int<8,false>(arg.data[1]));
    pkts[0].set_slc(16,ac_int<8,false>(arg.data[2]));
    pkts[0].set_slc(24,ac_int<8,false>(arg.data[3]));

    pkts[1].set_slc(0,ac_int<8,false>(arg.data[4]));
    pkts[1].set_slc(8,ac_int<8,false>(arg.data[5]));
    pkts[1].set_slc(16,ac_int<8,false>(arg.data[6]));
    pkts[1].set_slc(24,ac_int<8,false>(arg.data[7]));

    pkts[2].set_slc(0,ac_int<8,false>(arg.data[8]));
    pkts[2].set_slc(8,ac_int<8,false>(arg.data[9]));
    pkts[2].set_slc(16,ac_int<8,false>(arg.data[10]));
    pkts[2].set_slc(24,ac_int<8,false>(arg.data[11]));

    pkts[3].set_slc(0,ac_int<8,false>(arg.data[12]));
    pkts[3].set_slc(8,ac_int<8,false>(arg.data[13]));
    pkts[3].set_slc(16,ac_int<8,false>(arg.data[14]));
    pkts[3].set_slc(24,ac_int<8,false>(arg.data[15]));
}
template<> template<>
inline void Serialization<ac_int<32,false> >::deserialize(ac_int<32,false> pkts[sizeof(safe_pkt_t)/4], safe_pkt_t &arg){
    arg.data[0] = pkts[0].slc<8>(0);
    arg.data[1] = pkts[0].slc<8>(8);
    arg.data[2] = pkts[0].slc<8>(16);
    arg.data[3] = pkts[0].slc<8>(24);

    arg.data[4] = pkts[1].slc<8>(0);
    arg.data[5] = pkts[1].slc<8>(8);
    arg.data[6] = pkts[1].slc<8>(16);
    arg.data[7] = pkts[1].slc<8>(24);

    arg.data[8] = pkts[2].slc<8>(0);
    arg.data[9] = pkts[2].slc<8>(8);
    arg.data[10] = pkts[2].slc<8>(16);
    arg.data[11] = pkts[2].slc<8>(24);

    arg.data[12] = pkts[3].slc<8>(0);
    arg.data[13] = pkts[3].slc<8>(8);
    arg.data[14] = pkts[3].slc<8>(16);
    arg.data[15] = pkts[3].slc<8>(24);
}

//Specializations for decoded_pkt
template<> template<>
inline void Serialization<ac_int<32,false> >::serialize(ac_int<32,false> pkts[sizeof(decoded_pkt_t)/4], decoded_pkt_t &arg){
    #pragma hls_unroll yes
    serialize_decoded_pkt: for (int i = 0; i < sizeof(decoded_pkt_t)/4; ++i) {
        pkts[i].set_slc(0,ac_int<16,false>(arg.data[(i*2)]));
        pkts[i].set_slc(16,ac_int<16,false>(arg.data[(i*2)+1]));
    }
}
template<> template<>
inline void Serialization<ac_int<32,false> >::deserialize(ac_int<32,false> pkts[sizeof(decoded_pkt_t)/4], decoded_pkt_t &arg){
    #pragma hls_unroll yes
    deserialize_decoded_pkt: for (int i = 0; i < sizeof(decoded_pkt_t)/4; ++i) {
        arg.data[(i*2)] = pkts[i].slc<16>(0);
        arg.data[(i*2)+1] = pkts[i].slc<16>(16);
    }


}

};};

namespace Implementation { namespace serializer_imp {

template<unsigned int BUFFER_SIZE>
class Serializer<BUFFER_SIZE,Configurations::EPOS_SOC_Catapult,true> {

public:
    enum{
        PKT_SIZE_BITS = 8*Traits<Sys>::serdes_pkt_size,
        PKT_SIZE_BITS_m1 = PKT_SIZE_BITS-1
    };
    typedef ac_int<PKT_SIZE_BITS,false> pkt_type;

private:
    pkt_type* _begin;
    unsigned int _data_cnt;
    pkt_type _buffer[DIV_ROUNDUP<BUFFER_SIZE,Traits<Sys>::serdes_pkt_size>::Result];



public:
    Serializer() :_begin(&_buffer[0]), _data_cnt(0){
    }


public:

    pkt_type* get_pkt_buffer(){
        return &_buffer[0];
    }

    int get_pkt_cnt(){return _data_cnt;}
    void set_pkt_cnt(int d){_data_cnt = d;}

    void reset(){_begin = &_buffer[0];}

    template<typename ARG0>
    void serialize(ARG0 &arg0){
        Serialization<pkt_type>::serialize(_begin,arg0);
        _begin = &_begin[data_to_pkt<ARG0>::Result];
        _data_cnt += data_to_pkt<ARG0>::Result;
    }

    template<typename ARG0>
    void deserialize(ARG0 &arg0){
        Serialization<pkt_type>::deserialize(_begin,arg0);
        _begin = &_begin[data_to_pkt<ARG0>::Result];
        _data_cnt -= data_to_pkt<ARG0>::Result;
    }

};

}; };

#endif /* SERIALIZER_H_ */
