/*
 * Scheduler.h
 *
 *  Created on: Nov 23, 2010
 *      Author: tiago
 */

#ifndef SCHEDULER_HL_DEBUGGED_H_
#define SCHEDULER_HL_DEBUGGED_H_

#include <systemc.h>
#include "priority_linked_list.h"

//MAX_THREADS = 2^C_MAX_THREADS
template<unsigned int C_MAX_THREADS = 3, unsigned int C_DWIDTH = 32>
SC_MODULE(Scheduler_HL_Debugged){

	//Ports

	sc_in<bool> clk_in;
	sc_in<bool> rst_in;

	sc_in<sc_bv<4> > command_in;
	sc_in<sc_bv<16> > priority_in;
	sc_in<sc_bv<C_DWIDTH> > paremeter_in;

	sc_out<sc_bv<C_DWIDTH> > return_out;
	sc_out<sc_bv<6> > status_out;
	sc_out<sc_bv<1> > interrupt_out;

	sc_out<bool> trigger_out;
	sc_out<sc_uint<64> > data_out;

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

	static const int OPERATION_SIZE = 4;
	static const int PARAM_RETURN_SIZE = C_DWIDTH;
	static const int PRECISION = 8;
	static const int STATE_SIZE = 8;


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

	//debug related signals
	sc_signal<sc_uint<OPERATION_SIZE> > operation;
	sc_signal<sc_uint<4> > command;
	sc_signal<sc_uint<PARAM_RETURN_SIZE> > param_return;
	sc_signal<sc_uint<STATE_SIZE> > state;
	sc_signal<bool> start_stop;
	sc_signal<sc_uint<PRECISION> > profiled_counter;

	//COnstructor
	SC_CTOR(Scheduler_HL_Debugged) :list("Priority_List"){

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

		SC_METHOD(set_debug_data);
		sensitive << start_stop
				  << operation
				  << param_return
				  << profiled_counter
				  << state;

		SC_METHOD(count);
		sensitive << clk_in.pos();

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


	void count(){
		if(start_stop){
			profiled_counter = 0;
		}
		else
			profiled_counter = profiled_counter.read() + 1;
	}

	void set_debug_data(){
		sc_uint<11> aux(0);
		data_out = (aux,
				start_stop.read(),
				operation.read(),
				param_return.read(),
				profiled_counter.read(),
				state.read());
	}

	void debug_enter(){
		command = command_in.read();
		trigger_out = 1;
		state = 0;
		operation = command_in.read();
		param_return = paremeter_in.read();
		start_stop = 1;
	}

	void debug_leave(){
		trigger_out = 1;
		state = 0;
		operation = command.read();
		param_return = return_out.read();
		start_stop = 0;
		wait();
	}

	void debug_go_down(){
		trigger_out = 0;
		state = 0;
		operation = 0;
		param_return = 0;
		start_stop = 0;
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

		command = 0;
		operation = 0;
		param_return = 0;
		state = 0;
		start_stop = 0;*/
	}

	void main_process_behavior(){
		idle = false;
		debug_go_down();
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
		debug_go_down();
		list_request = 0;
		while(!list_ready.read()) wait();
	}

	virtual void create(){
		debug_enter();
		start_status();

		if(list_alloc_full.read()){
			exit_error_status();
		}
		else{
			list_operation = OP_ALLOCATE;
			list_object_in = paremeter_in.read();
			list_priority_in = priority_in.read();
			list_request_and_wait();
			return_out = list_idx_out.read();

			exit_ok_status();

		}
		wait();
		debug_leave();

	}

	virtual void destroy(){
		start_status();
		debug_enter();

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
		debug_leave();
	}

	virtual void insert(){
		debug_enter();
		start_status();

		list_operation = OP_INSERT;
		list_idx_in = paremeter_in.read();
		list_priority_in = priority_in.read();
		list_request_and_wait();

		return_out = list_idx_in.read();

		exit_ok_status();
		wait();
		debug_leave();
	}

	virtual void remove(){
		debug_enter();
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
		debug_leave();
	}

	virtual void remove_head(){
		debug_enter();
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
		debug_leave();
	}

	virtual void update_running(){
		debug_enter();
		start_status();
		running_idx = paremeter_in.read();
		running_priority = priority_in.read();
		running_idx_valid = true;
		wait();
		debug_go_down();
		exit_ok_status();
		wait();
		debug_leave();
	}

	virtual void invalidate_running(){
		debug_enter();
		start_status();
		running_idx_valid = false;
		wait();
		debug_go_down();
		exit_ok_status();
		wait();
		debug_leave();
	}

	virtual void enable(){
		debug_enter();
		start_status();
		enabled = true;
		wait();
		debug_go_down();

		exit_ok_status();
		wait();
		debug_leave();
	}

	virtual void disable(){
		debug_enter();
		start_status();
		enabled = false;
		wait();
		debug_go_down();

		exit_ok_status();
		wait();
		debug_leave();
	}

	virtual void set_quantum(){
		debug_enter();
		start_status();
		quantum_ticks = paremeter_in.read();
		time_reset = true;
		wait();
		debug_go_down();

		time_reset = false;
		exit_ok_status();
		wait();
		debug_leave();
	}

	virtual void int_ack(){
		debug_enter();
		start_status();
		signal_int_ack = true;
		wait();
		debug_go_down();
		signal_int_ack = false;
		exit_ok_status();
		wait();
		debug_leave();

	}

	virtual void get_id(){
		debug_enter();
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
		debug_leave();
	}

	virtual void chosen(){
		debug_enter();
		start_status();
		if(running_idx_valid.read()){
			list_operation = OP_GET_OBJECT;
			list_idx_in = running_idx.read();
			list_request_and_wait();
			return_out = list_object_out.read();
		}
		else{
			return_out = 0;
			debug_go_down();
			wait();
		}
		exit_ok_status();
		wait();
		debug_leave();

	}

	virtual void size(){
		debug_enter();
		start_status();
		return_out = list_count.read() + 1;
		wait();
		debug_go_down();
		exit_ok_status();
		wait();
		debug_leave();
	}

	virtual void rsticks(){
		debug_enter();
		start_status();
		time_reset = true;
		wait();
		debug_go_down();
		time_reset = false;
		exit_ok_status();
		wait();
		debug_leave();

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

#endif /* SCHEDULER_HL_DEBUGGED_H_ */


