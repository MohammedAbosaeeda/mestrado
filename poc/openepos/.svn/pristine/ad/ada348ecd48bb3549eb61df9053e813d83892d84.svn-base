/*
 * adpcm_encoder.h
 *
 *	4:1 ADPCM encoder
 *
 *  Created on: Jun 28, 2011
 *      Author: tiago
 */

#ifndef ADPCM_DECODER_H_
#define ADPCM_DECODER_H_

#include "adpcm_common.h"

namespace Implementation {

class ADPCM_Decoder {

private:
	int prev_sample;
	unsigned int prev_index;


public:
	ADPCM_Decoder() :prev_sample(0), prev_index(0) {

	}

	int decode(unsigned int adpcm_in);
};

}

#endif /* ADPCM_ENCODER_H_ */
