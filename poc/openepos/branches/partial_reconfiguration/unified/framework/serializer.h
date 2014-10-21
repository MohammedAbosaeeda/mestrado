/*
 * serializer.h
 *
 *  Created on: Jan 22, 2013
 *      Author: tiago
 */

#ifndef SERIALIZER_H_
#define SERIALIZER_H_

#ifdef HIGH_LEVEL_SYNTHESIS
#include "../../hw/framework/catapult/serializer.h"
#else
//EPOS_SoC software serializer
#include "../../sw/include/framework/serializer.h"
#endif


namespace Implementation {

template<unsigned int BUFFER_SIZE>
class Serializer : public serializer_imp::Serializer<BUFFER_SIZE,Traits<Sys>::Platform,Traits<Sys>::hardware_domain>{};

};



#endif /* SERIALIZER_H_ */
