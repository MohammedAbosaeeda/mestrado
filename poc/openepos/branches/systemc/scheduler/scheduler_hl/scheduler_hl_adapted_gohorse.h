/*
 * Scheduler.h
 *
 *  Created on: Nov 23, 2010
 *      Author: tiago
 */

#ifndef SCHEDULER_HL_ADAPTED_GOHORSE_H_
#define SCHEDULER_HL_ADAPTED_GOHORSE_H_

#include <systemc.h>
#include "priority_linked_list.h"

#include "../../aspect/aspect.h"

#include "../../debug/profiled.h"
#include "../../debug/traced.h"

//MAX_THREADS = 2^C_MAX_THREADS
template<unsigned int C_MAX_THREADS = 3, unsigned int C_DWIDTH = 32>
SC_MODULE(Scheduler_HL_Adapted_Go_Horse){

	static const unsigned int OPERATION_SIZE = 4;
	static const unsigned int PARAM_RETURN_SIZE = C_DWIDTH;
	static const unsigned int TSC_PRECISION = 8;

	//Ports

	sc_in<bool> clk_in;
	sc_in<bool> rst_in;

	sc_in<sc_bv<4> > command_in;
	sc_in<sc_bv<16> > priority_in;
	sc_in<sc_bv<C_DWIDTH> > paremeter_in;

	sc_out<sc_bv<C_DWIDTH> > return_out;
	sc_out<sc_bv<6> > status_out;
	sc_out<sc_bv<1> > interrupt_out;

	sc_out<bool> db_scenario_trigger_out;
	sc_out<sc_uint<64> > db_scenario_data_out;

	Aspect_Traced<OPERATION_SIZE, PARAM_RETURN_SIZE> db_scenario_traced;
	Aspect_Profiled_Go_Horse<OPERATION_SIZE,TSC_PRECISION> db_scenario_profiled;

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

	//sc_out<bool> list_debug_in;


	//Constants
	static const unsigned int MAX_THREADS_AUX = 1 << C_MAX_THREADS;

	static const unsigned int CMD_CREATE = 0x1;
	static const unsigned int CMD_DESTROY = 0x2;
	static const unsigned int CMD_INSERT = 0x3;
	static const unsigned int CMD_REMOVE = 0x4;
	static const unsigned int CMD_REMOVE_HEAD = 0x5;
	static const unsigned int CMD_UPDATE_RUNNING = 0x6;
	static const unsigned int CMD_SET_QUANTUM = 0x7;
	static const unsigned int CMD_ENABLE = 0x8;
	static const unsigned int CMD_DISABLE = 0x9;
	static const unsigned int CMD_INT_ACK = 0xA;
	static const unsigned int CMD_GETID = 0xB;
	static const unsigned int CMD_CHOSEN = 0xC;
	static const unsigned int CMD_SIZE = 0xD;
	static const unsigned int CMD_RSTICKS = 0xE;
	static const unsigned int CMD_INVALIDATE_RUNNING = 0xF;

	static const unsigned int OP_INSERT = Priority_Linked_List<C_MAX_THREADS,16,C_DWIDTH>::OP_INSERT;
	static const unsigned int OP_REMOVE = Priority_Linked_List<C_MAX_THREADS,16,C_DWIDTH>::OP_REMOVE;
	static const unsigned int OP_REMOVE_HEAD = Priority_Linked_List<C_MAX_THREADS,16,C_DWIDTH>::OP_REMOVE_HEAD;
	static const unsigned int OP_GET_OBJECT = Priority_Linked_List<C_MAX_THREADS,16,C_DWIDTH>::OP_GET_OBJECT;
	static const unsigned int OP_ALLOCATE = Priority_Linked_List<C_MAX_THREADS,16,C_DWIDTH>::OP_ALLOCATE;
	static const unsigned int OP_DEALLOCATE = Priority_Linked_List<C_MAX_THREADS,16,C_DWIDTH>::OP_DEALLOCATE;
	static const unsigned int OP_GET_IDX = Priority_Linked_List<C_MAX_THREADS,16,C_DWIDTH>::OP_GET_IDX;
	static const unsigned int OP_SEARCH_IDX = Priority_Linked_List<C_MAX_THREADS,16,C_DWIDTH>::OP_SEARCH_IDX;


	//internal modules
	Priority_Linked_List<C_MAX_THREADS,16,C_DWIDTH> list;

	sc_signal<sc_uint<C_MAX_THREADS+1> > list_head;
	sc_signal<bool> list_empty;
	sc_signal<bool> list_full;
	sc_signal<bool> list_alloc_empty;
	sc_signal<bool> list_alloc_full;
	sc_signal<sc_uint<C_MAX_THREADS+1> > list_count;
	sc_signal<sc_uint<C_MAX_THREADS> > list_idx_in;
	sc_signal<sc_uint<C_MAX_THREADS+1> > list_idx_out;
	sc_signal<sc_uint<C_DWIDTH> > list_object_in;
	sc_signal<sc_uint<C_DWIDTH> > list_object_out;
	sc_signal<bool> list_found;
	sc_signal<sc_uint<16> > list_priority_in;
	sc_signal<sc_uint<16> > list_priority_out;
	sc_signal<bool> list_ready;
	sc_signal<bool> list_request;
	sc_signal<sc_uint<3> > list_operation;
	sc_signal<bool> list_debug;


	//internal signals

	sc_signal<bool> enabled;
	sc_signal<bool> reschedule;
	sc_signal<bool> done;
	sc_signal<bool> error;

	sc_signal<sc_uint<C_MAX_THREADS+1> > running_idx;
	sc_signal<bool> running_idx_valid;
	sc_signal<sc_uint<16> > running_priority;

	sc_signal<sc_uint<C_DWIDTH> > quantum_ticks;
	sc_signal<bool> time_reset;
	sc_signal<sc_uint<C_DWIDTH> > timer_counter;
	sc_signal<bool> signal_int_ack;

	sc_signal<bool> idle;

	//COnstructor
	SC_CTOR(Scheduler_HL_Adapted_Go_Horse) :db_scenario_traced("Traced"),
			db_scenario_profiled("Profiled"),list("Priority_List"){

		list.clk_in(clk_in);
		list.rst_in(rst_in);
		list.head_out(list_head);
		list.list_empty_out(list_empty);
		list.list_full_out(list_full);
		list.alloc_empty_out(list_alloc_empty);
		list.alloc_full_out(list_alloc_full);
		list.count_out(list_count);
		list.idx_in(list_idx_in);
		list.idx_out(list_idx_out);
		list.object_in(list_object_in);
		list.object_out(list_object_out);
		list.found_out(list_found);
		list.priority_in(list_priority_in);
		list.priority_out(list_priority_out);
		list.request_in(list_request);
		list.ready_out(list_ready);
		list.operation_in(list_operation);
		list.sim_debug_in(list_debug);
		list_debug = false;

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

				enjambre_set_signals();

		SC_CTHREAD(main_process, clk_in.pos());
		reset_signal_is(rst_in, true);

		SC_CTHREAD(timer_process, clk_in.pos());
		reset_signal_is(time_reset, true);

		SC_METHOD(status);
		sensitive << reschedule
				  << enabled
				  << done
				  << list_alloc_full
				  << list_empty
				  << error;

		SC_METHOD(interrupt);
		sensitive << reschedule
				  << enabled
				  << idle;

	}

	//set status_out
	void status(){
		sc_bv<6> aux;
		aux[5] = reschedule.read();
		aux[4] = enabled.read();
		aux[3] = done.read();
	    aux[2] = list_alloc_full.read();
	    aux[1] = list_empty.read();
	    aux[0] = error.read();
	    status_out = aux;
	    //status_out = (reschedule.read(),
		//		enabled.read(),
		//		done.read(),
		//		list_alloc_full.read(),
		//		list_empty.read(),
		//		error.read());
	}



	void wrapper_enter() {
		sc_uint<4> aux = command_in.read();
		db_scenario_enter(aux, paremeter_in.read());
	}

	void wrapper_leave() {
			sc_uint<4> aux = 0;
			db_scenario_leave(aux, return_out.read());
		}

	//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	//Main processes responsible to control the state machine
	void main_process() {
		main_process_reset();
		wait();
		while (true) {
			main_process_behavior();
			wait();
		}
	}

	virtual void main_process_reset(){
		//TODO commented for synthesis with agility (uncomment for a working model)
		/*enabled = 0;
		error = 0;
		done = 0;

		list_object_in = 0;
		list_priority_in = 0;
		list_idx_in = 0;
		list_request = 0;
		list_operation = 0;

		return_out = 0;

		running_idx = Priority_Linked_List<C_MAX_THREADS,16,C_DWIDTH>::null();
		running_idx_valid = 0;
		running_priority = 0;

		idle = true;

		quantum_ticks = 0;
		time_reset = 0;
		signal_int_ack = 0;

		db_scenario_reset();

		*/
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

	void main_process_behavior(){
		idle = false;
		switch(command_in.read().to_uint()){
		case CMD_CREATE:
			create();
			break;
		case CMD_DESTROY:
			destroy();
			break;
		case CMD_INSERT:
			insert();
			break;
		case CMD_REMOVE:
			remove();
			break;
		case CMD_REMOVE_HEAD:
			remove_head();
			break;
		case CMD_UPDATE_RUNNING:
			update_running();
			break;
		case CMD_INVALIDATE_RUNNING:
			invalidate_running();
			break;
		case CMD_ENABLE:
			enable();
			break;
		case CMD_DISABLE:
			disable();
			break;
		case CMD_SET_QUANTUM:
			set_quantum();
			break;
		case CMD_INT_ACK:
			int_ack();
			break;
		case CMD_GETID:
			get_id();
			break;
		case CMD_CHOSEN:
			chosen();
			break;
		case CMD_SIZE:
			size();
			break;
		case CMD_RSTICKS:
			rsticks();
			break;
		default:
			break;
		}
		idle = true;

	}

		void enjambre_set_signals(){
			SC_METHOD(enjambre_behavior);
			sensitive << db_scenario_profiled_trigger
							<< db_scenario_profiled_data
							<< db_scenario_traced_trigger
							<< db_scenario_traced_data;
		}

		//TODO remove this
		void enjambre_behavior(){
			sc_uint<4+1+C_DWIDTH+8> aux = 0;
			if(db_scenario_traced_trigger.read()){
				aux.range(4+C_DWIDTH+8,1+C_DWIDTH+8) = db_scenario_traced_data.read().range(C_DWIDTH+4-1,C_DWIDTH);//1+OPERATION_SIZE+PARAM_RETURN_SIZE
				aux[C_DWIDTH+8] = db_scenario_traced_data.read()[4+C_DWIDTH];
				aux.range(C_DWIDTH+8-1,8) = db_scenario_traced_data.read().range(C_DWIDTH-1,0);
			}
			if(db_scenario_profiled_trigger.read()){
				aux.range(4+C_DWIDTH+8,1+C_DWIDTH+8) = db_scenario_profiled_data.read().range(4+8-1,8);//OPERATION_SIZE+TSC_PRECISION
				aux.range(8-1,0) = db_scenario_traced_data.read().range(8-1,0);
			}

			sc_uint<64> tmp = 0;
			tmp.range(4+1+C_DWIDTH+8-1,0) = aux;
			db_scenario_data_out = tmp;
			db_scenario_trigger_out = db_scenario_profiled_trigger.read() || db_scenario_traced_trigger.read();
		}

	template<int size>
	void set_bv(sc_signal<sc_bv<size> > *bv, unsigned int idx, bool val){
		sc_bv<size> aux = bv->read();
		aux[idx] = val;
		bv->write(aux);
	}

	void start_status(){
		done = 0;
		error = 0;
		return_out = 0;
	}

	void exit_ok_status(){
		done = 1;
		error = 0;
	}

	void exit_error_status(){
		done = 1;
		error = 1;
	}

	void list_request_and_wait(){
		list_request = 1;
		wait();
		list_request = 0;
		while(!list_ready.read()) wait();
	}

	virtual void create(){
		wrapper_enter();
		start_status();

		if(list_alloc_full.read()){
			exit_error_status();
			wait();
		}
		else{
			list_operation = OP_ALLOCATE;
			list_object_in = paremeter_in.read();
			list_priority_in = priority_in.read();
			list_request_and_wait();
			return_out = list_idx_out.read();

			exit_ok_status();
			wait();
		}
		wrapper_leave();
	}

	virtual void destroy(){
		wrapper_enter();
		start_status();

		sc_uint<C_MAX_THREADS> aux_idx = paremeter_in.read().to_uint();

		list_operation = OP_SEARCH_IDX;
		list_idx_in = aux_idx;
		list_request_and_wait();

		if(list_found.read()){
			list_operation = OP_REMOVE;
			list_idx_in = aux_idx;
			list_request_and_wait();
		}

		list_operation = OP_DEALLOCATE;
		list_idx_in = aux_idx;
		list_request_and_wait();

		exit_ok_status();
		wait();
		wrapper_leave();
	}

	virtual void insert(){
		wrapper_enter();
		start_status();

		list_operation = OP_INSERT;
		list_idx_in = paremeter_in.read();
		list_priority_in = priority_in.read();
		list_request_and_wait();

		return_out = list_idx_in.read();

		exit_ok_status();
		wait();
		wrapper_leave();
	}

	virtual void remove(){
		wrapper_enter();
		start_status();


		sc_uint<C_MAX_THREADS> aux_idx = paremeter_in.read().to_uint();

		list_operation = OP_SEARCH_IDX;
		list_idx_in = aux_idx;
		list_request_and_wait();

		if(list_found.read()){
			return_out = list_object_out.read();
			list_operation = OP_REMOVE;
			list_idx_in = aux_idx;
			list_request_and_wait();
		}
		else{
			return_out = 0;
		}

		exit_ok_status();
		wait();
		wrapper_leave();
	}

	virtual void remove_head(){
		wrapper_enter();
		start_status();

		list_operation = OP_SEARCH_IDX;
		list_idx_in = list_head.read().to_uint();;
		list_request_and_wait();

		if(list_found.read()){
			return_out = list_object_out.read();
			list_operation = OP_REMOVE_HEAD;
			list_request_and_wait();
		}
		else{
			return_out = 0;
		}

		exit_ok_status();
		wait();
		wrapper_leave();
	}

	virtual void update_running(){
		wrapper_enter();
		start_status();
		running_idx = paremeter_in.read();
		running_priority = priority_in.read();
		running_idx_valid = true;
		wait();
		exit_ok_status();
		wait();
		wrapper_leave();
	}

	virtual void invalidate_running(){
		wrapper_enter();
		start_status();
		running_idx_valid = false;
		wait();
		exit_ok_status();
		wait();
		wrapper_leave();
	}

	virtual void enable(){
		wrapper_enter();
		start_status();
		enabled = true;
		wait();

		exit_ok_status();
		wait();
		wrapper_leave();
	}

	virtual void disable(){
		wrapper_enter();
		start_status();
		enabled = false;
		wait();

		exit_ok_status();
		wait();
		wrapper_leave();
	}

	virtual void set_quantum(){
		wrapper_enter();
		start_status();
		quantum_ticks = paremeter_in.read();
		time_reset = true;
		wait();

		time_reset = false;
		exit_ok_status();
		wait();
		wrapper_leave();
	}

	virtual void int_ack(){
		wrapper_enter();
		start_status();
		signal_int_ack = true;
		wait();
		signal_int_ack = false;
		exit_ok_status();
		wait();
		wrapper_leave();
	}

	virtual void get_id(){
		wrapper_enter();
		start_status();

		list_operation = OP_GET_IDX;
		list_object_in = paremeter_in.read();
		list_request_and_wait();

		if(list_found.read()){
			return_out = list_idx_out.read();
			exit_ok_status();
			wait();
		}
		else {
			exit_error_status();
			wait();
		}
		wrapper_leave();
	}

	virtual void chosen(){
		wrapper_enter();
		start_status();
		if(running_idx_valid.read()){
			list_operation = OP_GET_OBJECT;
			list_idx_in = running_idx.read();
			list_request_and_wait();
			return_out = list_object_out.read();
		}
		else{
			return_out = 0;
		}
		exit_ok_status();
		wait();
		wrapper_leave();
	}

	virtual void size(){
		wrapper_enter();
		start_status();
		return_out = list_count.read() + 1;
		wait();
		exit_ok_status();
		wait();
		wrapper_leave();
	}

	virtual void rsticks(){
		wrapper_enter();
		start_status();
		time_reset = true;
		wait();
		time_reset = false;
		exit_ok_status();
		wait();
		wrapper_leave();

	}

	//Gambi wait
	//TODO commented for synthesis with agility (uncomment for a working model)
	/*void wait(){
		sc_module::wait();
	}*/


	//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	//Timer processes
	void timer_process() {
		timer_process_reset();
		wait();
		while (true) {
			timer_process_behavior();
			wait();
		}
	}

	void timer_process_reset(){
		//TODO commented for synthesis with agility (uncomment for a working model)
		/*reschedule = 0;
		timer_counter = quantum_ticks.read();*/
	}

	void timer_process_behavior(){
		if(timer_counter.read().to_uint() == 0){
			reschedule =
					running_idx_valid.read() &&
					!list_empty.read() ;//&&
					//(running_priority.read() >= list_priority_out.read()); //TODO uncomment this
			timer_counter = quantum_ticks.read();
		}
		else{
			timer_counter = timer_counter.read() - 1;
			reschedule = 0;
		}
		//if(signal_int_ack.read()){
		//	reschedule = 0;
		//	timer_counter = quantum_ticks.read();
		//}
	}

	void interrupt(){
		interrupt_out = reschedule.read() && enabled.read() && idle.read();
	}

};


#endif /* SCHEDULER_HL_ADAPTED_GOHORSE_H_ */


