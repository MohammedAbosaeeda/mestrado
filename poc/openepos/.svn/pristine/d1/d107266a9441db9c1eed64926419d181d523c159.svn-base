/*
 * debugged.h
 *
 *  Created on: Jan 13, 2011
 *      Author: tiago
 */

#ifndef DEBUGGED_H_
#define DEBUGGED_H_

#include <systemc.h>


template<int OPERATION_SIZE, int PARAM_RETURN_SIZE>
SC_MODULE(Traced) {

	static const unsigned int INVOCATION = 0x0;
	static const unsigned int RETURN = 0x1;

	sc_in<bool> clk_in;
	sc_in<bool> rst_in;

	sc_out<bool> trigger_out;
	sc_out<sc_uint<1+OPERATION_SIZE+PARAM_RETURN_SIZE> > data_out;

	sc_in<bool> trigger_in;
	sc_in<bool> invoc_return_in;

	sc_in<sc_uint<OPERATION_SIZE> > operation_in;
	sc_in<sc_uint<PARAM_RETURN_SIZE> > param_return_in;

	SC_CTOR(Traced) {
		SC_CTHREAD(action, clk_in.pos());
		reset_signal_is(rst_in, true);
	}

	void action(){
		trigger_out = 0;
		data_out = 0;

		wait();
		while(true){
			if(trigger_in.read()){
				trigger_out = 1;
				data_out =
						((invoc_return_in.read() ? RETURN : INVOCATION),
						 operation_in.read(),
						 param_return_in.read());
			}
			else{
				trigger_out = 0;
				data_out = 0;
			}
			wait();
		}
	}

};

template<int OPERATION_SIZE, int PRECISION>
SC_MODULE(Profiled) {

	static const unsigned int START = 0x0;
	static const unsigned int STOP = 0x1;

	sc_in<bool> clk_in;
	sc_in<bool> rst_in;

	sc_out<bool> trigger_out;
	sc_out<sc_uint<OPERATION_SIZE+PRECISION> > data_out;

	sc_in<bool> trigger_in;
	sc_in<bool> start_stop_in;

	sc_in<sc_uint<OPERATION_SIZE> > operation_in;

	sc_signal<sc_uint<PRECISION> > tsc;
	sc_signal<sc_uint<OPERATION_SIZE> > operation;

	SC_CTOR(Profiled){

		SC_CTHREAD(action, clk_in.pos());
		reset_signal_is(rst_in, true);
	}

	void action(){
		trigger_out = 0;
		data_out = 0;
		tsc = 0;
		operation = 0;

		wait();
		while(true){
			if(trigger_in.read()){
				if(start_stop_in == START){
					tsc = 1;
					operation = operation_in;
				}
				else { //start_stop_in == STOP
					trigger_out = 1;
					data_out = (operation, tsc);
					operation = 0;
				}
			}
			else{
				trigger_out = 0;
				data_out = 0;
				tsc = tsc.read() + 1;
			}
			wait();
		}
	}

};

template<int STATE_SIZE>
SC_MODULE(Watched) {

	sc_in<bool> clk_in;
	sc_in<bool> rst_in;

	sc_out<bool> trigger_out;
	sc_out<sc_uint<STATE_SIZE> > data_out;

	sc_in<bool> trigger_in;
	sc_in<sc_uint<STATE_SIZE> > state_in;

	SC_CTOR(Watched){

		SC_CTHREAD(action, clk_in.pos());
		reset_signal_is(rst_in, true);
	}


	void action(){
		trigger_out = 0;
		data_out = 0;

		wait();
		while(true){
			if(trigger_in.read()){
				trigger_out = 1;
				data_out = state_in;
			}
			else{
				trigger_out = 0;
				data_out = 0;
			}
			wait();
		}
	}

};

SC_MODULE(Clk_Rst_Bind) {
	sc_in<bool> clk_in;
	sc_in<bool> rst_in;

	sc_out<bool> clk_out;
	sc_out<bool> rst_out;

	SC_CTOR(Clk_Rst_Bind) {
		SC_METHOD(clk_bind); sensitive << clk_in;
		SC_METHOD(rst_bind); sensitive << rst_in;
	}

	void clk_bind() { clk_out.write(clk_in.read()); }

	void rst_bind() { rst_out.write(rst_in.read()); }
};

template<int OPERATION_SIZE, int PARAM_RETURN_SIZE, int PRECISION, int STATE_SIZE>
class Debugged {

public:
	sc_out<bool> trigger_out;
	sc_out<sc_uint<1+OPERATION_SIZE+PARAM_RETURN_SIZE+PRECISION+STATE_SIZE> > data_out;

	Watched<STATE_SIZE> watched;
	Traced<OPERATION_SIZE, PARAM_RETURN_SIZE> traced;
	Profiled<OPERATION_SIZE, PRECISION> profiled;
	//Clk_Rst_Bind clk_rst_bind;

	sc_signal<bool> clk;
	sc_signal<bool> rst;

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

protected:
	Debugged()
		:watched("Watched"),
		 traced("Traced"),
		 profiled("Profiled")//,
		 //clk_rst_bind("Clk_Rst_Bind")
	{
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

		//watched.clk_in(clk);
		//watched.rst_in(rst);
		//traced.clk_in(clk);
		//traced.rst_in(rst);
		//profiled.clk_in(clk);
		//profiled.rst_in(rst);

		//clk_rst_bind.clk_out(clk);
		//clk_rst_bind.rst_out(rst);


	}

	/*
	void bind_clk_rst(sc_in<bool> *clk, sc_in<bool> *rst){
		watched.clk_in(*clk);
		watched.rst_in(*rst);
		traced.clk_in(*clk);
		traced.rst_in(*rst);
		profiled.clk_in(*clk);
		profiled.rst_in(*rst);
	}
	*/

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
};

#endif /* DEBUGGED_H_ */
