/*
 * Scheduler.h
 *
 *  Created on: Nov 23, 2010
 *      Author: tiago
 */

#ifndef SCHEDULER_HL_ADAPTED_H_
#define SCHEDULER_HL_ADAPTED_H_

#include <systemc.h>
#include "scheduler_hl.h"
#include "../../aspect/debugged.h"
#include "../../aspect/atomic.h"

template<unsigned int C_MAX_THREADS = 3, unsigned int C_DWIDTH = 32>
class Scheduler_HL_Adapted : public Scheduler_HL<C_MAX_THREADS, C_DWIDTH>,
							 public Scenario_Debugged<Scheduler_HL_Adapted<C_MAX_THREADS,C_DWIDTH>, 4, C_DWIDTH, 8>{
							 //public Scenario_Atomic<Scheduler_HL_Adapted<C_MAX_THREADS,C_DWIDTH> >{
public:
	typedef Scenario_Debugged<Scheduler_HL_Adapted<C_MAX_THREADS,C_DWIDTH>, 4, C_DWIDTH, 8> Debugged;
	//typedef Scenario_Atomic<Scheduler_HL_Adapted<C_MAX_THREADS,C_DWIDTH> > Atomic;
	typedef Scheduler_HL<C_MAX_THREADS, C_DWIDTH> Base;

	Scheduler_HL_Adapted(sc_module_name nm)
		:Base(nm),
		 Debugged(){
		 //Atomic(){
		enjambre_set_signals(); //TODO remove this
	}

	void main_process_reset(){
		//Atomic::scenario_reset();
		Debugged::scenario_reset();
		Base::main_process_reset();
	}

	#define WRAPPER(method_name) 														\
	void method_name() {																\
		sc_uint<4> aux = Base::command_in.read();										\
		Debugged::scenario_enter(aux, Base::paremeter_in.read());						\
		Base::method_name();															\
		Debugged::scenario_leave(aux, Base::return_out.read());							\
	}

	WRAPPER(create)
	WRAPPER(destroy)
	WRAPPER(rsticks)
	WRAPPER(size)
	WRAPPER(chosen)
	WRAPPER(get_id)
	WRAPPER(int_ack)
	WRAPPER(set_quantum)
	WRAPPER(disable)
	WRAPPER(enable)
	WRAPPER(invalidate_running)
	WRAPPER(update_running)
	WRAPPER(remove_head)
	WRAPPER(remove)
	WRAPPER(insert)

	//TODO remove this
	SC_HAS_PROCESS(Scheduler_HL_Adapted);
	void enjambre_set_signals(){
		SC_METHOD(enjambre_behavior);
		Base::sensitive << Debugged::profiled_trigger
						<< Debugged::profiled_data
						<< Debugged::traced_trigger
						<< Debugged::traced_data;
	}

	//TODO remove this
	void enjambre_behavior(){
		sc_uint<4+1+C_DWIDTH+8> aux = 0;
		if(Debugged::traced_trigger.read()){
			aux.range(4+C_DWIDTH+8,1+C_DWIDTH+8) = Debugged::traced_data.read().range(C_DWIDTH+4-1,C_DWIDTH);//1+OPERATION_SIZE+PARAM_RETURN_SIZE
			aux[C_DWIDTH+8] = Debugged::traced_data.read()[4+C_DWIDTH];
			aux.range(C_DWIDTH+8-1,8) = Debugged::traced_data.read().range(C_DWIDTH-1,0);
		}
		if(Debugged::profiled_trigger.read()){
			aux.range(4+C_DWIDTH+8,1+C_DWIDTH+8) = Debugged::profiled_data.read().range(4+8-1,8);//OPERATION_SIZE+TSC_PRECISION
			aux.range(8-1,0) = Debugged::traced_data.read().range(8-1,0);
		}

		sc_uint<64> tmp = 0;
		tmp.range(4+1+C_DWIDTH+8-1,0) = aux;
		Debugged::data_out = tmp;
		Debugged::trigger_out = Debugged::profiled_trigger.read() || Debugged::traced_trigger.read();
	}

};


#endif /* SCHEDULER_HL_ADAPTED_H_ */


