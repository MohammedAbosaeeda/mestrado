/*
 * Testbench.h
 *
 *  Created on: Nov 18, 2010
 *      Author: tiago
 */

#ifndef BUS_MODEL_H_
#define BUS_MODEL_H_

#include <systemc.h>
#include <iostream>

//MAX_THREADS = 2^C_MAX_THREADS
template<unsigned int C_MAX_THREADS = 3, unsigned int C_DWIDTH = 32>
SC_MODULE(Bus_Model) {
	//Aux constants
	static const unsigned int C_MAX_THREADS_AUX = 1 << C_MAX_THREADS;

	//Ports
	sc_in<bool> clk_in;
	sc_in<bool> rst_in;

	sc_out<sc_bv<4> > command_out;
	sc_out<sc_bv<16> > priority_out;
	sc_out<sc_bv<C_DWIDTH> > paremeter_out;

	sc_in<sc_bv<C_DWIDTH> > return_in;
	sc_in<sc_bv<6> > status_in;

	enum{
		OP_WR_CMD,
		OP_WR_PARAM,
		OP_RD_STATUS,
		OP_RD_RETURN
	};
	sc_uint<32> op;
	sc_uint<32> data;
	bool op_exec;
	sc_event op_finsh;

	SC_CTOR(Bus_Model){
		op = 0;
		data = 0;
		op_exec = false;

		SC_CTHREAD(decoder, clk_in.pos())
		reset_signal_is(rst_in, true);
	}


	void some_delay(){
		wait(clk_in.posedge_event());
		wait(clk_in.posedge_event());
		wait(clk_in.posedge_event());
	}

	void wait_cycles(unsigned int cycles){
		for (unsigned int i = 0; i < cycles; ++i) {
			wait(clk_in.posedge_event());
		}
	}

	void write_cmd(unsigned int cmd){
		op = OP_WR_CMD;
		data = cmd;
		op_exec = true;
		wait(op_finsh);
		some_delay();
	}

	void write_param(unsigned int param) {
		op = OP_WR_PARAM;
		data = param;
		op_exec = true;
		wait(op_finsh);
		some_delay();
	}

	unsigned int read_status() {
		op = OP_RD_STATUS;
		op_exec = true;
		wait(op_finsh);
		some_delay();
		return data;
	}

	unsigned int read_return() {
		op = OP_RD_RETURN;
		op_exec = true;
		wait(op_finsh);
		some_delay();
		return data;
	}


	void decoder(){
		command_out = 0;
		priority_out = 0;
		paremeter_out = 0;

		op = 0;
		data = 0;
		op_exec = false;

		wait();
		while(true){
			command_out = 0;
			priority_out = 0;
			unsigned int aux = 0;
			if(op_exec){
				switch (op.to_uint()) {
					case OP_WR_CMD:
						command_out = data.range(27, 24).to_uint();
						priority_out = data.range(15, 0).to_uint();
						break;
					case OP_WR_PARAM:
						paremeter_out = data.range(C_DWIDTH-1, 0).to_uint();
						break;
					case OP_RD_STATUS:
						aux = status_in.read().to_uint();
						data.range(21, 16) = aux;
						break;
					case OP_RD_RETURN:
						data.range(C_DWIDTH-1, 0) = return_in.read();
						break;
					default:
						break;
				}
				op_exec = false;
				op_finsh.notify();
			}
			wait();
		}
	}

};

#endif /* TESTBENCH_H_ */
