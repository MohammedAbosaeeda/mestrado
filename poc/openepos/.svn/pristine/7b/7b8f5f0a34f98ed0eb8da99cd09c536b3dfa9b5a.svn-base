/*
 * profiled.h
 *
 *  Created on: Sep 3, 2011
 *      Author: tiago
 */

#ifndef PROFILED_H_
#define PROFILED_H_

#include "../aspect/aspect.h"

template<int OPERATION_SIZE, int PRECISION>
class Aspect_Profiled: public Aspect_Common<Aspect_Profiled<OPERATION_SIZE,PRECISION> > {
public:
	typedef Aspect_Common<Aspect_Profiled<OPERATION_SIZE,PRECISION> > Base;

	static const unsigned int START = 0x0;
	static const unsigned int STOP = 0x1;

	sc_out<bool> trigger_out;
	sc_out<sc_uint<OPERATION_SIZE+PRECISION> > data_out;

	sc_in<bool> start_stop_in;
	sc_in<sc_uint<OPERATION_SIZE> > operation_in;

	sc_signal<sc_uint<PRECISION> > tsc;
	sc_signal<sc_uint<OPERATION_SIZE> > operation;

	Aspect_Profiled(sc_module_name nm) :Base(nm) {

	}

	void aspect_reset(){
		trigger_out = false;
		data_out = 0;
		tsc = 0;
		operation = 0;
	}

	void aspect_trigger_behavior(){
		if(start_stop_in == START){
			tsc = 1;
			operation = operation_in.read();
		}
		else { //start_stop_in == STOP
			trigger_out = 1;
			data_out = (operation, tsc);
			operation = 0;
		}
	}

	void aspect_idle_behavior(){
		trigger_out = 0;
		data_out = 0;
		tsc = tsc.read() + 1;
	}

};



template<int OPERATION_SIZE, int PRECISION>
class Aspect_Profiled_Go_Horse: public sc_module {
public:
	sc_in<bool> clk_in;
	sc_in<bool> rst_in;

	sc_in<bool> op_req_in;
	sc_out<bool> op_rdy_out;

	static const unsigned int START = 0x0;
		static const unsigned int STOP = 0x1;

		sc_out<bool> trigger_out;
		sc_out<sc_uint<OPERATION_SIZE+PRECISION> > data_out;

		sc_in<bool> start_stop_in;
		sc_in<sc_uint<OPERATION_SIZE> > operation_in;

		sc_signal<sc_uint<PRECISION> > tsc;
		sc_signal<sc_uint<OPERATION_SIZE> > operation;

	SC_HAS_PROCESS(Aspect_Profiled_Go_Horse);
	Aspect_Profiled_Go_Horse(sc_module_name nm) :sc_module(nm){
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
		aspect_reset();
	}

	void main_behavior(){
		if (op_req_in.read()){
			op_rdy_out = false;
			aspect_trigger_behavior();
			op_rdy_out = true;
		}
		else{
			op_rdy_out = true;
			aspect_idle_behavior();
		}

	}


	void aspect_reset(){
			trigger_out = false;
			data_out = 0;
			tsc = 0;
			operation = 0;
		}

		void aspect_trigger_behavior(){
			if(start_stop_in == START){
				tsc = 1;
				operation = operation_in.read();
			}
			else { //start_stop_in == STOP
				trigger_out = 1;
				data_out = (operation, tsc);
				operation = 0;
			}
		}

		void aspect_idle_behavior(){
			trigger_out = 0;
			data_out = 0;
			tsc = tsc.read() + 1;
		}

};



#endif /* PROFILED_H_ */
