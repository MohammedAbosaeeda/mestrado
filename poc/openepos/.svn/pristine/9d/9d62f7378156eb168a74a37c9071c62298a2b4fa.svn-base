/*
 * adpcm_encoder.h
 *
 *	4:1 ADPCM encoder
 *
 *  Created on: Jun 28, 2011
 *      Author: tiago
 */

#ifndef ADPCM_ENCODER_H_
#define ADPCM_ENCODER_H_

#include "adpcm_common.h"

namespace Implementation {

class ADPCM_Encoder {

private:
	int prev_sample;
	unsigned int prev_index;

public:
	ADPCM_Encoder() :prev_sample(0), prev_index(0) {

	}

	unsigned int encode(int pcm_in);

};

};

#endif /* ADPCM_ENCODER_H_ */
