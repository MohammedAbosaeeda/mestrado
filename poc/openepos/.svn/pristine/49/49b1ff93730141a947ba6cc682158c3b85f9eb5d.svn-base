/*
 * traced.h
 *
 *  Created on: Sep 3, 2011
 *      Author: tiago
 */

#ifndef TRACED_H_
#define TRACED_H_

#include "../aspect/aspect.h"

template<int OPERATION_SIZE, int PARAM_RETURN_SIZE>
class Aspect_Traced: public Aspect_Common<Aspect_Traced<OPERATION_SIZE,PARAM_RETURN_SIZE> > {
public:
	typedef Aspect_Common<Aspect_Traced<OPERATION_SIZE,PARAM_RETURN_SIZE> > Base;
	static const unsigned int INVOCATION = 0x0;
	static const unsigned int RETURN = 0x1;

	sc_out<bool> trigger_out;
	sc_out<sc_uint<1+OPERATION_SIZE+PARAM_RETURN_SIZE> > data_out;

	sc_in<bool> invoc_return_in;
	sc_in<sc_uint<OPERATION_SIZE> > operation_in;
	sc_in<sc_uint<PARAM_RETURN_SIZE> > param_return_in;


	Aspect_Traced(sc_module_name nm) :Base(nm) {

	}

	void aspect_reset(){
		trigger_out = false;
		data_out = 0;
	}

	void aspect_trigger_behavior(){
		trigger_out = true;
		data_out = ((invoc_return_in.read() ? RETURN : INVOCATION),
					operation_in.read(),
					param_return_in.read());

	}

	void aspect_idle_behavior(){
		trigger_out = false;
		data_out = 0;
	}

};

#endif /* TRACED_H_ */
