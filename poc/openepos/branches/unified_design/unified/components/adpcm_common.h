/*
 * adpcm_common.h
 *
 *  Created on: Jun 28, 2011
 *      Author: tiago
 */

#ifndef ADPCM_COMMON_H_
#define ADPCM_COMMON_H_

namespace Implementation{

class ADPCM_Common {
public:
	const static int INDEX_TABLE[16];
	const static int STEP_SIZE_TABLE[89];
};

}

#endif /* ADPCM_COMMON_H_ */
