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

	sc_out<bool> db_scenario_trigger_out;
	sc_out<sc_uint<64> > db_scenario_data_out;

	Aspect_Traced<OPERATION_SIZE, PARAM_RETURN_SIZE> db_scenario_traced;
	Aspect_Profiled<OPERATION_SIZE,TSC_PRECISION> db_scenario_profiled;

	sc_signal<sc_uint<OPERATION_SIZE> > db_scenario_operation;
	sc_signal<sc_uint<PARAM_RETURN_SIZE> > db_scenario_param_return;

	sc_signal<bool> db_scenario_traced_op_rdy;
	sc_signal<bool> db_scenario_traced_op_req;
	sc_signal<bool> db_scenario_traced_trigger;
	sc_signal<sc_uint<1+OPERATION_SIZE+PARAM_RETURN_SIZE> > db_scenario_traced_data;
	sc_signal<bool> db_scenario_traced_invoc_return;

	sc_signal<bool> db_scenario_profiled_op_rdy;
	sc_signal<bool> db_scenario_profiled_op_req;
	sc_signal<bool> db_scenario_profiled_trigger;
	sc_signal<sc_uint<OPERATION_SIZE+TSC_PRECISION> > db_scenario_profiled_data;
	sc_signal<bool> db_scenario_profiled_start_stop;


	Scenario_Debugged()
		:db_scenario_traced("Traced"),
				db_scenario_profiled("Profiled")
	{
		db_scenario_traced.clk_in(clk_in);
		db_scenario_traced.rst_in(rst_in);
		db_scenario_traced.op_req_in(db_scenario_traced_op_req);
		db_scenario_traced.op_rdy_out(db_scenario_traced_op_rdy);
		db_scenario_traced.trigger_out(db_scenario_traced_trigger);
		db_scenario_traced.data_out(db_scenario_traced_data);
		db_scenario_traced.invoc_return_in(db_scenario_traced_invoc_return);
		db_scenario_traced.operation_in(db_scenario_operation);
		db_scenario_traced.param_return_in(db_scenario_param_return);

		db_scenario_profiled.clk_in(clk_in);
		db_scenario_profiled.rst_in(rst_in);
		db_scenario_profiled.op_req_in(db_scenario_profiled_op_req);
		db_scenario_profiled.op_rdy_out(db_scenario_profiled_op_rdy);
		db_scenario_profiled.trigger_out(db_scenario_profiled_trigger);
		db_scenario_profiled.data_out(db_scenario_profiled_data);
		db_scenario_profiled.operation_in(db_scenario_operation);
		db_scenario_profiled.start_stop_in(db_scenario_profiled_start_stop);

	}

	void db_scenario_reset(){
		db_scenario_traced_op_req = false;
		db_scenario_traced_invoc_return = false;
		db_scenario_operation = 0;
		db_scenario_param_return = 0;
		db_scenario_profiled_op_req = false;
		db_scenario_profiled_start_stop = false;
	}

	void db_scenario_wait_operation(){
		while(!db_scenario_traced_op_rdy.read() || !db_scenario_profiled_op_rdy.read()) wait();
	}

	void db_scenario_start_operation(){
		db_scenario_traced_op_req = true;
		db_scenario_profiled_op_req = true;
		wait();
		db_scenario_traced_op_req = false;
		db_scenario_profiled_op_req = false;
	}

	void db_scenario_enter(sc_uint<OPERATION_SIZE> op,
			   sc_uint<PARAM_RETURN_SIZE> param){

		db_scenario_wait_operation();

		db_scenario_traced_invoc_return = Aspect_Traced<OPERATION_SIZE, PARAM_RETURN_SIZE>::INVOCATION;
		db_scenario_profiled_start_stop = Aspect_Profiled<OPERATION_SIZE,TSC_PRECISION>::START;
		db_scenario_operation = op;
		db_scenario_param_return = param;

		db_scenario_start_operation();

		db_scenario_wait_operation();

	}

	void db_scenario_leave(sc_uint<OPERATION_SIZE> op,
			   sc_uint<PARAM_RETURN_SIZE> ret){

		db_scenario_wait_operation();

		db_scenario_traced_invoc_return = Aspect_Traced<OPERATION_SIZE, PARAM_RETURN_SIZE>::RETURN;
		db_scenario_profiled_start_stop = Aspect_Profiled<OPERATION_SIZE,TSC_PRECISION>::STOP;
		db_scenario_operation = op;
		db_scenario_param_return = ret;

		db_scenario_start_operation();

		db_scenario_wait_operation();
	}

};

#endif /* DEBUGGED_HL_H_ */
