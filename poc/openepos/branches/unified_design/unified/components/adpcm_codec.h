/*
 * adpcm_codec.h
 *
 *  Created on: Dec 3, 2011
 *      Author: tiago
 */

#ifndef __adpcm_codec_unified_h
#define __adpcm_codec_unified_h

#include "component.h"

#include "adpcm_encoder.h"
#include "adpcm_decoder.h"

namespace Implementation {

class ADPCM_Codec: public ADPCM_Encoder, public ADPCM_Decoder, public Component{
public:
	enum {
		OP_ENCODE = 0,
		OP_DECODE = 1
	};

public:
	ADPCM_Codec(Channel_t &rx_ch, Channel_t &tx_ch, unsigned char iid[Traits<ADPCM_Codec>::n_ids])
        :ADPCM_Encoder(), ADPCM_Decoder(), Component(rx_ch, tx_ch, iid[0]){}

public:

    int decode(unsigned int adpcm_in){
        return ADPCM_Decoder::decode(adpcm_in);
    }

    unsigned int encode(int pcm_in){
        return ADPCM_Encoder::encode(pcm_in);
    }

};

PROXY_BEGIN(ADPCM_Codec)
    int decode(unsigned int adpcm_in){
        return Base::call_r<ADPCM_Codec::OP_DECODE,int>(adpcm_in);
    }
    unsigned int encode(int pcm_in){
        return Base::call_r<ADPCM_Codec::OP_ENCODE,unsigned int>(pcm_in);
    }
PROXY_END

AGENT_BEGIN(ADPCM_Codec)
    D_CALL_R_1(decode, OP_DECODE, int, unsigned int)
    D_CALL_R_1(encode, OP_ENCODE, unsigned int, int)
AGENT_END

};

DECLARE_COMPONENT(ADPCM_Codec);

#endif /* ADPCM_CODEC_H_ */
