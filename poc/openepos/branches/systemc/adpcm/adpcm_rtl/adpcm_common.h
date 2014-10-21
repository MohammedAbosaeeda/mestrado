/*
 * adpcm_common.h
 *
 *  Created on: Jun 28, 2011
 *      Author: tiago
 */

#ifndef ADPCM_COMMON_H_
#define ADPCM_COMMON_H_

#include <systemc.h>

class ADPCM_Common {
public:
	const static sc_int<8> INDEX_TABLE[16];
	const static sc_int<16> STEP_SIZE_TABLE[89];
};

#endif /* ADPCM_COMMON_H_ */
