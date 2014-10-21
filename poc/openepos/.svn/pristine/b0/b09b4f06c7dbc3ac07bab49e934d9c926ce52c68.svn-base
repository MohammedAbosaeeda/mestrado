/*
 * aspect_base.h
 *
 *  Created on: Jun 13, 2011
 *      Author: tiago
 */

#ifndef ASPECT_BASE_H_
#define ASPECT_BASE_H_

#include <systemc.h>

/*
class Aspect_Template: public Aspect_Common<Aspect_Template> {
public:
	typedef Aspect_Common<Aspect_Template> Base;

	Aspect_Template(sc_module_name nm) :Base(nm) {

	}

	void aspect_reset(){

	}

	void aspect_trigger_behavior(){

	}

	void aspect_idle_behavior(){

	}

};
*/

template<class Aspect>
class Aspect_Common: public sc_module {
public:
	sc_in<bool> clk_in;
	sc_in<bool> rst_in;

	sc_in<bool> op_req_in;
	sc_out<bool> op_rdy_out;

	SC_HAS_PROCESS(Aspect_Common);
	Aspect_Common(sc_module_name nm) :sc_module(nm){
		SC_CTHREAD(behavior, clk_in.pos());
		reset_signal_is(rst_in, true);
	}

	void behavior(){
		reset_behavior();
		wait();
		while(true){
			main_behavior();
			wait();
		}
	}

	void reset_behavior(){
		op_rdy_out = false;
		static_cast<Aspect*>(this)->aspect_reset();
	}

	void main_behavior(){
		if (op_req_in.read()){
			op_rdy_out = false;
			static_cast<Aspect*>(this)->aspect_trigger_behavior();
			op_rdy_out = true;
		}
		else{
			op_rdy_out = true;
			static_cast<Aspect*>(this)->aspect_idle_behavior();
		}

	}

};


#endif /* ASPECT_BASE_H_ */
