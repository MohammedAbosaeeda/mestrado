/*
 * Scheduler.h
 *
 *  Created on: Nov 23, 2010
 *      Author: tiago
 *
 *
 *
 *
 *      GAMBI Scheduler
 *
 *      main_process is was commented out and is implemented in the adapter
 *
 *      main_process_behavior is not virtual anymore
 *
 */

#ifndef SCHEDULER_ADAPTED_H_
#define SCHEDULER_ADAPTED_H_

#include <systemc.h>
#include "debugged.h"

//MAX_THREADS = 2^C_MAX_THREADS
template<unsigned int C_MAX_THREADS = 3, unsigned int C_DWIDTH = 32>
class Scheduler_Adapted: public sc_module {

public:

	static const int OPERATION_SIZE = 4;
	static const int PARAM_RETURN_SIZE = C_DWIDTH;
	static const int PRECISION = 8;
	static const int STATE_SIZE = 8;

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
	sc_out<sc_uint<1+OPERATION_SIZE+PARAM_RETURN_SIZE+PRECISION+STATE_SIZE> > data_out;

	//Sub modules
	Watched<STATE_SIZE> watched;
	Traced<OPERATION_SIZE, PARAM_RETURN_SIZE> traced;
	Profiled<OPERATION_SIZE, PRECISION> profiled;

	//sub modules related signals
	sc_signal<sc_uint<OPERATION_SIZE> > operation;
	sc_signal<sc_uint<PARAM_RETURN_SIZE> > param_return;
	sc_signal<sc_uint<STATE_SIZE> > state;
	sc_signal<bool> watched_trig;
	sc_signal<bool> watched_trig_out;
	sc_signal<sc_uint<STATE_SIZE> > watched_data_out;
	sc_signal<bool> traced_trig;
	sc_signal<bool> traced_invoc_return;
	sc_signal<bool> traced_trig_out;
	sc_signal<sc_uint<1+OPERATION_SIZE+PARAM_RETURN_SIZE> > traced_data_out;
	sc_signal<bool> profiled_trig;
	sc_signal<bool> profiled_start_stop;
	sc_signal<bool> profiled_trig_out;
	sc_signal<sc_uint<OPERATION_SIZE+PRECISION> > profiled_data_out;

	//Constants
	static const unsigned int C_MAX_THREADS_AUX = 1 << C_MAX_THREADS;

	static const unsigned int C_CMD_CREATE = 0x1;
	static const unsigned int C_CMD_DESTROY = 0x2;
	static const unsigned int C_CMD_INSERT = 0x3;
	static const unsigned int C_CMD_REMOVE = 0x4;
	static const unsigned int C_CMD_REMOVE_HEAD = 0x5;
	static const unsigned int C_CMD_UPDATE_RUNNING = 0x6;
	static const unsigned int C_CMD_SET_QUANTUM = 0x7;
	static const unsigned int C_CMD_ENABLE = 0x8;
	static const unsigned int C_CMD_DISABLE = 0x9;
	static const unsigned int C_CMD_INT_ACK = 0xA;
	static const unsigned int C_CMD_GETID = 0xB;
	static const unsigned int C_CMD_CHOSEN = 0xC;
	static const unsigned int C_CMD_SIZE = 0xD;
	static const unsigned int C_CMD_RSTICKS = 0xE;
	static const unsigned int C_CMD_SET_TID_BITMAP = 0xF;

	//Internal signals

	//State Machine Type
	typedef enum {
		idle,
		destroy,
		preinsert,
		insert,
		remove,
		exit_ok,
		exit_error,
		acknowledge_int,
		reset_time,
		pregetid,
		getid
	} t_state;
	sc_signal<t_state> s_state;

	//Internal Memory
	sc_bv<C_DWIDTH> s_obj_table[C_MAX_THREADS_AUX];
	sc_bv<16> s_order_table[C_MAX_THREADS_AUX];
	sc_uint<C_MAX_THREADS+1> s_prev_table[C_MAX_THREADS_AUX];
	sc_uint<C_MAX_THREADS+1> s_next_table[C_MAX_THREADS_AUX];
	sc_signal<sc_bv<C_MAX_THREADS_AUX> > s_tid_bitmap;
	sc_signal<sc_bv<C_MAX_THREADS_AUX> > s_enqueue_bitmap;

	sc_signal<sc_uint<C_MAX_THREADS+1> > s_running_tid;
	sc_signal<sc_uint<C_MAX_THREADS+1> > s_command_tid;
	sc_signal<sc_uint<C_MAX_THREADS+1> > s_free_tid;
	sc_signal<sc_uint<C_MAX_THREADS+1> > s_head_tid;
	sc_signal<sc_uint<C_MAX_THREADS+1> > s_tail_tid;
	sc_signal<sc_uint<C_MAX_THREADS+1> > s_size;
	sc_signal<sc_bv<1> > s_list_empty;

	//Status Signals
	sc_signal<sc_bv<1> > s_done;
	sc_signal<sc_bv<1> > s_error;
	sc_signal<sc_bv<1> > s_full;

	//search TID signals
	sc_signal<sc_bv<C_DWIDTH> > s_stid_obj_ptr;
	sc_signal<sc_bv<1> > s_stid_done;
	sc_signal<sc_bv<1> > s_stid_start;
	sc_signal<sc_uint<C_MAX_THREADS+1> > s_stid_found;

	//searchingLinkList signals
	sc_signal<sc_bv<1> > s_search_reset;
	sc_signal<sc_bv<16> > s_search_order;
	sc_signal<sc_uint<C_MAX_THREADS+1> > s_found_tid;
	sc_signal<sc_bv<1> > s_search_done;

	//timeManagement signals
	sc_signal<sc_bv<C_DWIDTH> > s_quantum_ticks;
	sc_signal<sc_bv<1> > s_schedule_enabled;
	sc_signal<sc_bv<1> > s_reschedule;
	sc_signal<sc_bv<1> > s_int_ack;
	sc_signal<sc_bv<1> > s_time_reset;
	sc_signal<sc_uint<C_DWIDTH> > s_counter;
	sc_signal<sc_uint<4> > command;

	SC_HAS_PROCESS(Scheduler_Adapted);
	Scheduler_Adapted(sc_module_name nm) :sc_module(nm),
			watched("Watched"),
			traced("Traced"),
			profiled("Profiled"){

		watched.state_in(state);
		watched.trigger_in(watched_trig);
		watched.trigger_out(watched_trig_out);
		watched.data_out(watched_data_out);

		traced.operation_in(operation);
		traced.param_return_in(param_return);
		traced.invoc_return_in(traced_invoc_return);
		traced.trigger_in(traced_trig);
		traced.trigger_out(traced_trig_out);
		traced.data_out(traced_data_out);

		profiled.operation_in(operation);
		profiled.start_stop_in(profiled_start_stop);
		profiled.trigger_in(profiled_trig);
		profiled.trigger_out(profiled_trig_out);
		profiled.data_out(profiled_data_out);

		watched.clk_in(clk_in);
		watched.rst_in(rst_in);
		traced.clk_in(clk_in);
		traced.rst_in(rst_in);
		profiled.clk_in(clk_in);
		profiled.rst_in(rst_in);

		SC_METHOD(list_empty);
		sensitive << s_head_tid;

		SC_METHOD(status);
		sensitive << s_reschedule
				  << s_schedule_enabled
				  << s_done
				  << s_full
				  << s_list_empty
				  << s_error;

		SC_METHOD(search_free_TID);
		sensitive << clk_in.pos();

		SC_CTHREAD(main_process, clk_in.pos());
		reset_signal_is(rst_in, true);

		SC_CTHREAD(search_TID, clk_in.pos());
		reset_signal_is(rst_in, true);

		SC_CTHREAD(searching_link_list, clk_in.pos());
		reset_signal_is(rst_in, true);

		SC_METHOD(set_interrupt);
		sensitive << s_reschedule
				  << s_schedule_enabled
				  << s_state;

		SC_CTHREAD(time_management, clk_in.pos());
		reset_signal_is(rst_in, true);

		//Elaboration init and reset init must be the same
		s_free_tid = 1;
		s_quantum_ticks = 0xFFFFFFFF;
		s_stid_found = 1;
	}

	void list_empty(){
		s_list_empty = (s_head_tid.read() == 0) ? 1 : 0;
	}

	void status(){
		status_out = (s_reschedule.read(),
					  s_schedule_enabled.read(),
					  s_done.read(),
					  s_full.read(),
					  s_list_empty.read(),
					  s_error.read());
	}

	template<int size>
	void set_bv(sc_signal<sc_bv<size> > *bv, unsigned int idx, bool val){
		sc_bv<size> aux = bv->read();
		aux[idx] = val;
		bv->write(aux);
	}

	//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	//Controls the allocation of tid to new threads.
	//SC_CTHREAD version
	/*
	void search_free_TID() {
		s_free_tid = 1;
		s_full = 0;

		wait();
		while (true) {
			s_full = s_tid_bitmap.and_reduce();

			for (unsigned int i = 1; i <= C_MAX_THREADS_AUX; ++i) {
				if (s_tid_bitmap[i-1] == 0)
					s_free_tid = i;
			}

			wait();
		}
	}*/
	//SC_METHOD version
	void search_free_TID() {
		if(rst_in == true){
			s_free_tid = 1;
			s_full = 0;
		}
		else{
			s_full = s_tid_bitmap.read().and_reduce();

			for (unsigned int i = 1; i <= C_MAX_THREADS_AUX; ++i) {
				if (s_tid_bitmap.read()[i - 1] == 0)
					s_free_tid = i;
			}
		}
	}
	//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

	//Adapter methods
	void enter(sc_uint<OPERATION_SIZE> op,
			sc_uint<PARAM_RETURN_SIZE> param){

		watched_trig = 1;
		traced_trig = 1;
		traced_invoc_return = Traced<OPERATION_SIZE, PARAM_RETURN_SIZE>::INVOCATION;
		profiled_trig = 1;
		profiled_start_stop = Profiled<OPERATION_SIZE, PRECISION>::START;

		state = 0;
		operation = op;
		param_return = param;
	}

	void leave(sc_uint<OPERATION_SIZE> op,
			sc_uint<PARAM_RETURN_SIZE> ret){
		watched_trig = 1;
		traced_trig = 1;
		traced_invoc_return = Traced<OPERATION_SIZE, PARAM_RETURN_SIZE>::RETURN;
		profiled_trig = 1;
		profiled_start_stop = Profiled<OPERATION_SIZE, PRECISION>::STOP;

		state = 0;
		operation = op;
		param_return = ret;
	}

	void go_down(){
		watched_trig = 0;
		traced_trig = 0;
		profiled_trig = 0;

		if(watched_trig_out.read() || traced_trig_out.read() || profiled_trig_out.read()){
			trigger_out = 1;
			data_out = (traced_data_out.read(),
					profiled_data_out.read().range(PRECISION-1, 0),
					watched_data_out.read());
		}
		else{
			trigger_out = 0;
			data_out = 0;
		}
	}


	//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	//Main processes responsible to control the state machine
	void main_process() {
		main_process_reset();
		wait();
		while (true) {
			main_process_behavior_adapted();
			wait();
		}
	}

	void main_process_reset(){
		for (unsigned int i = 0; i < C_MAX_THREADS_AUX; ++i) {
			s_obj_table[i] = 0;
			s_order_table[i] = 0;
			s_prev_table[i] = 0;
			s_next_table[i] = 0;
		}
		s_tid_bitmap = 0;
		s_enqueue_bitmap = 0;
		s_running_tid = 0;
		s_head_tid = 0;
		s_tail_tid = 0;
		return_out = 0;
		s_done = 0;
		s_error = 0;
		s_schedule_enabled = 0;
		s_quantum_ticks = 0xFFFFFFFF;
		s_int_ack = 0;
		s_state = idle;
		command = 0;

		operation = 0;
		param_return = 0;
		state = 0;
		watched_trig = 0;
		traced_trig = 0;
		traced_invoc_return = 0;
		profiled_trig = 0;
		profiled_start_stop = 0;
	}

	void main_process_behavior_adapted(){
		if((s_state.read() == idle) && (command_in.read() != 0)) {
			//std::cout << "Entering cmd " << Scheduler<C_MAX_THREADS, C_DWIDTH>::command_in.read().to_uint() << "\n";
			command = command_in.read();
			enter(command_in.read(), paremeter_in.read());
		}
		else
			go_down();

		main_process_behavior();

		if(s_state.read() == exit_ok) {
			//std::cout << "Leaving cmd" << command.read().to_uint() << "\n";
			leave(command.read(), return_out.read());
		}
		else
			go_down();

	}

	void main_process_behavior(){
		switch (s_state.read()) {
		case idle:
			switch (command_in.read().to_uint()) {
			case C_CMD_CREATE:
				s_done = 0;
				s_error = 0;
				if (s_full.read() == 0) {
					s_command_tid = s_free_tid.read();
					//s_tid_bitmap.read()[s_free_tid.read()-1] = 1;
					set_bv(&s_tid_bitmap, s_free_tid.read()-1, 1);
					s_order_table[s_free_tid.read()-1] = priority_in;
					s_obj_table[s_free_tid.read()-1] = paremeter_in;
					return_out = s_free_tid.read();
					s_state = exit_ok;
				} else {
					s_state = exit_error;
				}
				break;

			case C_CMD_DESTROY:
				s_done = 0;
				s_error = 0;
				s_command_tid = paremeter_in.read();
				s_state = destroy;
				break;

			case C_CMD_INSERT:
				s_done = 0;
				s_error = 0;
				s_command_tid = paremeter_in.read();
				s_order_table[paremeter_in.read().to_uint() - 1] = priority_in;
				//s_enqueue_bitmap.read()[paremeter_in.read().to_uint()-1] = 1;
				set_bv(&s_enqueue_bitmap, paremeter_in.read().to_uint()-1, 1);
				if (s_head_tid.read() == 0) {
					//Insiro direto na cabeca da fila
					s_head_tid = paremeter_in.read();
					s_tail_tid = paremeter_in.read();
					s_size = s_size.read() + 1;
					s_state = exit_ok;
				} else { //Procuro posicao
					s_search_order = priority_in;
					s_search_reset = 1;
					s_state = preinsert;
				}
				break;

			case C_CMD_REMOVE:
				s_done = 0;
				s_error = 0;
				if (s_enqueue_bitmap.read()[paremeter_in.read().to_uint()-1] == 0) { //Not in queue!
					return_out = 0;
					s_state = exit_ok;
				} else {
					s_command_tid = paremeter_in.read();
					s_state = remove;
				}
				break;

			case C_CMD_REMOVE_HEAD:
				s_done = 0;
				s_error = 0;
				if (s_head_tid.read() == 0) { //There is no HEAD !
					return_out = 0;
					s_state = exit_ok;
				} else {
					s_command_tid = s_head_tid;
					s_state = remove;
				}
				break;

			case C_CMD_UPDATE_RUNNING:
				s_done = 0;
				s_error = 0;
				s_running_tid = paremeter_in.read();
				s_state = exit_ok;
				break;

			case C_CMD_SET_QUANTUM:
				s_done = 0;
				s_error = 0;
				s_quantum_ticks = paremeter_in;
				s_time_reset = 1;
				s_state = reset_time;
				break;

			case C_CMD_RSTICKS:
				s_done = 0;
				s_error = 0;
				s_time_reset = 1;
				s_state = reset_time;
				break;

			case C_CMD_ENABLE:
				s_done = 0;
				s_error = 0;
				s_schedule_enabled = 1;
				s_state = exit_ok;
				break;

			case C_CMD_DISABLE:
				s_done = 0;
				s_error = 0;
				s_schedule_enabled = 0;
				s_state = exit_ok;
				break;

			case C_CMD_INT_ACK:
				s_done = 0;
				s_error = 0;
				s_int_ack = 1;
				s_state = acknowledge_int;
				break;

			case C_CMD_GETID:
				s_done = 0;
				s_error = 0;
				s_stid_obj_ptr = paremeter_in;
				s_stid_start = 1;
				s_state = pregetid;
				break;

			case C_CMD_CHOSEN:
				s_done = 0;
				s_error = 0;
				if (s_running_tid.read() == 0) {
					return_out = 0;
				} else {
					return_out = s_obj_table[s_running_tid.read()-1];
				}
				s_state = exit_ok;
				break;

			case C_CMD_SIZE:
				s_done = 0;
				s_error = 0;
				return_out = s_size.read() + 1;
				s_state = exit_ok;
				break;

			case C_CMD_SET_TID_BITMAP:
				s_done = 0;
				s_error = 0;
				return_out = s_tid_bitmap.read();
				s_tid_bitmap = paremeter_in.read();
				s_state = exit_ok;
				break;

			default:
				break;
			}
			break;

			case preinsert:
				s_search_reset = 0;
				s_state = insert;
				break;

			case insert:
				if (s_search_done.read() == 1) {
					if (s_found_tid.read() == 0) {//Insert in tail
						s_tail_tid = s_command_tid;
						s_next_table[s_tail_tid.read()-1] = s_command_tid;
						s_prev_table[s_command_tid.read()-1] = s_tail_tid;
						s_next_table[s_command_tid.read()-1] = 0;
					} else {//Insert in middle
						if (s_prev_table[s_found_tid.read()-1] == 0) { //inserting in the HEAD
							s_head_tid = s_command_tid;
						}
						s_next_table[s_command_tid.read()-1] = s_found_tid;
						s_prev_table[s_command_tid.read()-1] = s_prev_table[s_found_tid.read() - 1];
						if (s_prev_table[s_found_tid.read()-1] > 0) {
							s_next_table[s_prev_table[s_found_tid.read()-1]-1] = s_command_tid;
						}
						s_prev_table[s_found_tid.read()-1] = s_command_tid;
					}
					return_out = s_command_tid.read();
					s_size = s_size.read() + 1;
					s_state = exit_ok;
				}
				break;

			case destroy:
				//s_tid_bitmap[s_command_tid.read()- 1] = 0; //Free tid
				set_bv(&s_tid_bitmap, s_command_tid.read()- 1, 0);
				s_obj_table[s_command_tid.read()-1] = 0;
				s_order_table[s_command_tid.read()-1] = 0;
				if (s_enqueue_bitmap.read()[s_command_tid.read()-1] == 1) {//TID enqueue
					s_state = remove;
				} else {
					s_state = exit_ok;
				}
				break;

			case remove:
				return_out = s_obj_table[s_command_tid.read()-1];
				//s_enqueue_bitmap[s_command_tid.read()-1] = 0;
				set_bv(&s_enqueue_bitmap, s_command_tid.read()-1, 0);
				if (s_prev_table[s_command_tid.read()-1] == 0) {//is head
					s_head_tid = s_next_table[s_command_tid.read()-1];
				} else {
					s_next_table[s_prev_table[s_command_tid.read()-1]-1] = s_next_table[s_command_tid.read()-1];
				}
				if (s_next_table[s_command_tid.read()-1] == 0) { //is tail
					s_tail_tid = s_prev_table[s_command_tid.read()-1];
				} else {
					s_prev_table[s_next_table[s_command_tid.read()-1]-1] = s_prev_table[s_command_tid.read()-1];
				}
				s_next_table[s_command_tid.read()-1] = 0;
				s_prev_table[s_command_tid.read()-1] = 0;
				s_size = s_size.read() - 1;
				s_state = exit_ok;
				break;

			case exit_ok:
				s_done = 1;
				s_state = idle;
				break;

			case exit_error:
				s_done = 1;
				s_error = 1;
				s_state = idle;
				break;

			case acknowledge_int:
				s_done = 1;
				s_int_ack = 0;
				s_state = idle;
				break;

			case reset_time:
				s_done = 1;
				s_time_reset = 0;
				s_state = idle;
				break;

			case pregetid:
				s_stid_start = 0;
				s_state = getid;
				break;

			case getid:
				if (s_stid_done.read() == 1) {
					if (s_stid_found.read() == 0) {
						return_out = 0;
						s_state = exit_error;
					} else {
						return_out = s_stid_found.read();
						s_state = exit_ok;
					}
				}
				break;

			default:
				break;
		}

	}
	//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

	//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	//Searchs the TID from the command obj
	//s stid obj ptr : std logic vector (0 to C DWIDTH
	//s stid done: std logic ;
	//s stid start : std logic ;
	//s stid found: integer range 0 to C MAX THREADS := 0;
	//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	void search_TID() {
		s_stid_found = 1;
		s_stid_done = 0;

		wait();
		while (true) {
			if (s_stid_start.read() == 1) {
				s_stid_found = 1;
				s_stid_done = 0;
			} else if (s_stid_done.read() == 0) {
				if (s_obj_table[s_stid_found.read()-1] == s_stid_obj_ptr.read()) { //Achou
					s_stid_done = 1; //Achou o TID da thread
				} else {
					if (s_stid_found.read() == C_MAX_THREADS) { //Nao tem
						s_stid_found = 0;
						s_stid_done = 0;
					} else {
						s_stid_found = s_stid_found.read() + 1; //Vou para o próximo
					}
				}
			}

			wait();
		}
	}
	//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

	//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	//Searchs the position of insertion in the queue
	//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	void searching_link_list() {
		s_found_tid = 0;
		s_search_done = 0;

		wait();
		while (true) {
			//s_search_done <= '0';
			if (s_search_reset.read() == 1) {
				s_found_tid = s_head_tid;
				s_search_done = 0;
			} else if (s_search_done.read() == 0) {
				if (s_order_table[s_found_tid.read()-1].to_uint() <= s_search_order.read().to_uint()) { //Increment s_found_tid
					if (s_next_table[s_found_tid.read()-1] == 0) { //Se estou no tail ... sai com 0
						s_found_tid = 0;
						s_search_done = 1;
					} else { //Vai para o proximo da fila ...
						s_found_tid = s_next_table[s_found_tid.read()-1];
					}
				} else {
					s_search_done = 1;
				}
			}

			wait();
		}
	}
	//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


	//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	//Controls Rescheduling Interrupts
	//s_quantum_ticks:		std_logic_vector(0 to C_DWIDTH-1);
	//s_schedule_enabled:  std_logic;
	//s_reschedule:			std_logic;
	//s_int_ack:			   std_logic;
	//s_time_reset:			std_logic;
	//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

	//Don't interrupt in the middle of an command! Wait command completation.
	void set_interrupt() {
		interrupt_out = ((s_reschedule.read() == 1) && (s_schedule_enabled.read() == 1)
				&& (s_state.read() == idle)) ? 1 : 0;
	}

	void time_management() {
		s_counter = 0;
		s_reschedule = 0;

		wait();
		while (true) {
			if (s_time_reset.read() == 1) {
				s_counter = s_quantum_ticks.read();
				s_reschedule = 0;
			} else {
				if (s_counter.read() == 0) {
					if ((s_running_tid.read() > 0) && (s_head_tid.read() > 0)
							&& (s_order_table[s_running_tid.read() - 1].to_uint()
									>= s_order_table[s_head_tid.read() - 1].to_uint())) {
						//Reschedule CPU
						s_reschedule = 1;
					}
					s_counter = s_quantum_ticks.read();
				} else {
					s_counter = s_counter.read() - 1;
					s_reschedule = 0;
				}
				//if s_int_ack = '1' then
				//	s_reschedule <= '0';
				//	s_counter <= s_quantum_ticks;
				//end if;
			}
			wait();
		}
	}
	//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

};

#endif /* SCHEDULER_ADAPTED_H_ */


