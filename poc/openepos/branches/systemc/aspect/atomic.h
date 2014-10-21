/*
 * atomic.h
 *
 *  Created on: Jun 21, 2011
 *      Author: tiago
 */

#ifndef ATOMIC_H_
#define ATOMIC_H_

#include "aspect.h"

class Aspect_Coarse_Atomic: public Aspect_Common<Aspect_Coarse_Atomic> {
public:
	typedef Aspect_Common<Aspect_Coarse_Atomic> Base;

	sc_in<bool> enter_i;

	sc_signal<bool> free;

	Aspect_Coarse_Atomic(sc_module_name nm) :Base(nm) {

	}

	void aspect_reset(){
		free = true;
	}

	void aspect_trigger_behavior(){
		if(enter_i.read()){
			while(!free.read()) wait();
			free = false;
		}
		else
			free = true;
	}

	void aspect_idle_behavior(){

	}

};

template<class Super>
class Scenario_Atomic {
public:
	Aspect_Coarse_Atomic coarse_atomic;

	sc_signal<bool> coarse_atomic_op_rdy;
	sc_signal<bool> coarse_atomic_op_req;
	sc_signal<bool> coarse_atomic_enter;

	Scenario_Atomic()
		:coarse_atomic("Coarse_Atomic")
	{
		coarse_atomic.clk_in(static_cast<Super*>(this)->clk_in);
		coarse_atomic.rst_in(static_cast<Super*>(this)->rst_in);
		coarse_atomic.op_req_in(coarse_atomic_op_req);
		coarse_atomic.op_rdy_out(coarse_atomic_op_rdy);
		coarse_atomic.enter_i(coarse_atomic_enter);
	}

	void scenario_reset(){
		coarse_atomic_op_req = false;
		coarse_atomic_enter = false;
	}

	void wait_operation(){
		while(!coarse_atomic_op_rdy.read()) static_cast<Super*>(this)->wait();
	}

	void start_operation(){
		coarse_atomic_op_req = true;
		static_cast<Super*>(this)->wait();
		coarse_atomic_op_req = false;
	}

	void scenario_enter(){

		wait_operation();

		coarse_atomic_enter = true;
		start_operation();

		wait_operation();

	}

	void scenario_leave(){

		wait_operation();

		coarse_atomic_enter = false;
		start_operation();

		wait_operation();
	}

};



#endif /* ATOMIC_H_ */
