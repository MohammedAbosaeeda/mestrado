/*
 * Testbench.h
 *
 *  Created on: Nov 18, 2010
 *      Author: tiago
 */

#ifndef TESTBENCH_H_
#define TESTBENCH_H_

#include <systemc.h>
#include <iostream>
#include "epos/epos_scheduler.h"
#include "bus_model.h"


//MAX_THREADS = 2^C_MAX_THREADS
template<unsigned int C_MAX_THREADS = 3, unsigned int C_DWIDTH = 32>
SC_MODULE(Testbench) {
	//Aux constants
	static const unsigned int C_MAX_THREADS_AUX = 1 << C_MAX_THREADS;

	//Ports
	sc_in<bool> clk_in;
	sc_out<bool> rst_out;
	sc_in<bool> rst_in;

	sc_out<sc_bv<4> > command_out;
	sc_out<sc_bv<16> > priority_out;
	sc_out<sc_bv<C_DWIDTH> > paremeter_out;

	sc_in<sc_bv<C_DWIDTH> > return_in;
	sc_in<sc_bv<6> > status_in;
	sc_in<sc_bv<1> > interrupt_in;

	//sc_out<bool> list_debug_out;

	sc_in<sc_uint<64> > debug_data_in;
	sc_in<bool> debug_trig_in;

	//Variables, modules & etc
	Bus_Model<C_MAX_THREADS, C_DWIDTH> bus_model;
	EPOS_Scheduler<C_MAX_THREADS, C_DWIDTH> sched;

	sc_event sim_finsh;

	unsigned int tsc;


	SC_CTOR(Testbench) :bus_model("Bus_Model"), sched(&bus_model){

		tsc = 0;

		bus_model.clk_in(clk_in);
		bus_model.rst_in(rst_in);
		bus_model.command_out(command_out);
		bus_model.priority_out(priority_out);
		bus_model.paremeter_out(paremeter_out);
		bus_model.return_in(return_in);
		bus_model.status_in(status_in);

		SC_THREAD(exec_testbench);

		SC_THREAD(interrupt_signal);

		SC_METHOD(clk_counter);
		sensitive << clk_in.pos();

		SC_METHOD(debug);
		sensitive << clk_in.pos();
	}

	//1+OPERATION_SIZE+PARAM_RETURN_SIZE+PRECISION+STATE_SIZE
	void debug(){
		if(debug_trig_in.read()){
			//sc_uint<4+1+C_DWIDTH+8> aux = 0;
			std::cout << "JTAG_OUT"<<
					" op: " << debug_data_in.read().range(4+C_DWIDTH+8,1+C_DWIDTH+8) <<
					" dir: " << debug_data_in.read()[C_DWIDTH+8] <<
					//" param_ret: " << (void*)(debug_data_in.read().range(C_DWIDTH+8-1,8)).to_uint() <<
					" tsc: " << debug_data_in.read().range(8-1,0) <<
					std::endl;
		}
	}

	void debug_list(){
		//list_debug_out = true;
		//wait(clk_in.posedge_event());
		//list_debug_out = false;
	}

	void exec_testbench(){
		//list_debug_out = false;
		std::cout << "\n\nStarting testbench" << std::endl;
		std::cout << "Setting reset to true" << std::endl;
		rst_out.write(true);

		std::cout << "Wait some clk cycles" << std::endl;
		bus_model.wait_cycles(5);

		std::cout << "Setting reset to false" << std::endl;
		rst_out.write(false);

		std::cout << "Wait some clk cycles" << std::endl;
		bus_model.wait_cycles(4);

		std::cout << "Starting scheduler" << std::endl;
		sched.init();

		std::cout << "Creating threads" << std::endl;
		unsigned int NUM_THREADS = 8;
		Thread threads[NUM_THREADS];
		for (unsigned int i = 0; i < NUM_THREADS; ++i) {
			threads[i].criterion(i);
			threads[i].id(i);
		}

		std::cout << "Wait 100 cycles" << std::endl;
		bus_model.wait_cycles(100);

		std::cout << "Inserting threads" << std::endl;
		for (unsigned int i = 0; i < NUM_THREADS; ++i) {
			sched.insert(&threads[i]);
		}
		//debug_list();

		std::cout << "Schedulables: " << sched.schedulables() << std::endl;

		std::cout << "Wait until all threads are scheduled at least once" << std::endl;
		wait(sim_finsh);

		std::cout << "Schedulables: " << sched.schedulables() << std::endl;

		sc_stop();

	}

	void interrupt_signal(){
		while(true){
			if(interrupt_in.read().to_uint() == 1)
				handle_scheduler_int();
			else
				bus_model.wait_cycles(1);
		}
	}


	void clk_counter(){
		//std::cout << "#CLK#" << std::endl;
		++tsc;
	}

	void handle_scheduler_int(){
		static int state = 0;
		std::cout << "SCHED INT - tsc: " << tsc << std::endl;

		Thread* t_old = 0;
		Thread* t_new = 0;

		t_old = sched.chosen();

		if(state == 0) {//use choose()
			sched.choose();
			state = 1;
		}else if (state == 1){ //use choose_another()
			sched.choose_another();
			state = 2;
		}else if(state == 2){ //use choose(obj)
			sched.choose(t_old);
			state = 0;
		}

		t_new = sched.chosen();

		std::cout << "Chosen was (" << t_old->id() << ", " << t_old->criterion() << ")"
				  << " new one is (" << t_new->id() << ", " << t_new->criterion() << ")" << std::endl;

		if(t_new->id() == 7)
			sim_finsh.notify();

		if(t_old != t_new){
			unsigned int val = change_priority(t_old->criterion());
			t_old->criterion(val);
			sched.remove(t_old);
			sched.insert(t_old);
			//sched.reset_quantum();
		}
	}

	unsigned int change_priority(unsigned int param){
		unsigned int ret;
		if(param >= 255)
			ret = 0;
		else
			ret = param + 1;
		return ret;
	}

};

#endif /* TESTBENCH_H_ */
