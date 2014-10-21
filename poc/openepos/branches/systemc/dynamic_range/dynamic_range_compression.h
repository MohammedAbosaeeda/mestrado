/*
 * dynamic_range_compression.h
 *
 *  Created on: Sep 3, 2011
 *      Author: tiago
 */

#ifndef DYNAMIC_RANGE_COMPRESSION_H_
#define DYNAMIC_RANGE_COMPRESSION_H_

#include "../aspect/aspect.h"

template<class COMP_TYPE, unsigned int COMP_BITS, class EXP_TYPE, unsigned int EXP_BITS>
class Aspect_Dynamic_Range_Compression: public Aspect_Common<Aspect_Dynamic_Range_Compression<COMP_TYPE,COMP_BITS,EXP_TYPE,EXP_BITS> > {
public:
	typedef Aspect_Common<Aspect_Dynamic_Range_Compression<COMP_TYPE,COMP_BITS,EXP_TYPE,EXP_BITS> > Base;

	sc_in<bool> compress_in;//0 = expand, 1 = compress
	sc_in<EXP_TYPE> sample_in;
	sc_out<EXP_TYPE> sample_out;

	Aspect_Dynamic_Range_Compression(sc_module_name nm) :Base(nm) {

	}

	void aspect_reset(){
		sample_out = EXP_TYPE(0);
	}

	void aspect_trigger_behavior(){
		if(compress_in.read()){
			sample_out = sample_in.read() >> (EXP_BITS-COMP_BITS);
		}
		else{
			EXP_TYPE aux(0);
			for (unsigned int i = 0; i < COMP_BITS; ++i)
				aux[i+(EXP_BITS-COMP_BITS)] = sample_in.read()[i];
			sample_out = aux;
		}
	}

	void aspect_idle_behavior(){

	}

};

#endif /* DYNAMIC_RANGE_COMPRESSION_H_ */
