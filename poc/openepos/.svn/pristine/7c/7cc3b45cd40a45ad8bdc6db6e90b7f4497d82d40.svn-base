/*
 * debugged_hl.h
 *
 *  Created on: Jan 13, 2011
 *      Author: tiago
 */

#ifndef DEBUGGED_HL_H_
#define DEBUGGED_HL_H_

#include <systemc.h>
#include "aspect.h"

#include "../debug/profiled.h"
#include "../debug/traced.h"

template<class Super, int OPERATION_SIZE, int PARAM_RETURN_SIZE, int TSC_PRECISION>
class Scenario_Debugged {
public:

	sc_out<bool> trigger_out;
	sc_out<sc_uint<64> > data_out;

	Aspect_Traced<OPERATION_SIZE, PARAM_RETURN_SIZE> traced;
	Aspect_Profiled<OPERATION_SIZE,TSC_PRECISION> profiled;

	sc_signal<sc_uint<OPERATION_SIZE> > operation;
	sc_signal<sc_uint<PARAM_RETURN_SIZE> > param_return;

	sc_signal<bool> traced_op_rdy;
	sc_signal<bool> traced_op_req;
	sc_signal<bool> traced_trigger;
	sc_signal<sc_uint<1+OPERATION_SIZE+PARAM_RETURN_SIZE> > traced_data;
	sc_signal<bool> traced_invoc_return;

	sc_signal<bool> profiled_op_rdy;
	sc_signal<bool> profiled_op_req;
	sc_signal<bool> profiled_trigger;
	sc_signal<sc_uint<OPERATION_SIZE+TSC_PRECISION> > profiled_data;
	sc_signal<bool> profiled_start_stop;


	Scenario_Debugged()
		:traced("Traced"),
		 profiled("Profiled")
	{
		traced.clk_in(static_cast<Super*>(this)->clk_in);
		traced.rst_in(static_cast<Super*>(this)->rst_in);
		traced.op_req_in(traced_op_req);
		traced.op_rdy_out(traced_op_rdy);
		traced.trigger_out(traced_trigger);
		traced.data_out(traced_data);
		traced.invoc_return_in(traced_invoc_return);
		traced.operation_in(operation);
		traced.param_return_in(param_return);

		profiled.clk_in(static_cast<Super*>(this)->clk_in);
		profiled.rst_in(static_cast<Super*>(this)->rst_in);
		profiled.op_req_in(profiled_op_req);
		profiled.op_rdy_out(profiled_op_rdy);
		profiled.trigger_out(profiled_trigger);
		profiled.data_out(profiled_data);
		profiled.operation_in(operation);
		profiled.start_stop_in(profiled_start_stop);

	}

	void scenario_reset(){
		traced_op_req = false;
		traced_invoc_return = false;
		operation = 0;
		param_return = 0;
		profiled_op_req = false;
		profiled_start_stop = false;
	}

	void wait_operation(){
		while(!traced_op_rdy.read() || !profiled_op_rdy.read()) static_cast<Super*>(this)->wait();
	}

	void start_operation(){
		traced_op_req = true;
		profiled_op_req = true;
		static_cast<Super*>(this)->wait();
		traced_op_req = false;
		profiled_op_req = false;
	}

	void scenario_enter(sc_uint<OPERATION_SIZE> op,
			   sc_uint<PARAM_RETURN_SIZE> param){

		wait_operation();

		traced_invoc_return = Aspect_Traced<OPERATION_SIZE, PARAM_RETURN_SIZE>::INVOCATION;
		profiled_start_stop = Aspect_Profiled<OPERATION_SIZE,TSC_PRECISION>::START;
		operation = op;
		param_return = param;

		start_operation();

		wait_operation();

	}

	void scenario_leave(sc_uint<OPERATION_SIZE> op,
			   sc_uint<PARAM_RETURN_SIZE> ret){

		wait_operation();

		traced_invoc_return = Aspect_Traced<OPERATION_SIZE, PARAM_RETURN_SIZE>::RETURN;
		profiled_start_stop = Aspect_Profiled<OPERATION_SIZE,TSC_PRECISION>::STOP;
		operation = op;
		param_return = ret;

		start_operation();

		wait_operation();
	}

};

#endif /* DEBUGGED_HL_H_ */
