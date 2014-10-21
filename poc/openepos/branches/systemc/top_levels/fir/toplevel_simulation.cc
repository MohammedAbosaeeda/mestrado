/*
 * Main.cc
 *
 *  Created on: Nov 15, 2010
 *      Author: tiago
 */


#define C_SAMPLE_SIZE 32
#define C_N_TAPS 17
#define DWIDTH 32

#include <systemc.h>
#include "../../fir/fir_rtl/fir_top.h"
#include "../../fir/testbench/stimulus.h"
#include "../../fir/testbench/display.h"
#include "../../fir/testbench/testbench.h"
#include "../../fir/fir_hl/fir.h"
#include "../../fir/fir_hl/fir_adapted.h"
#include "../../fir/fir_hl/fir_debugged_comp.h"
#include <iostream>
#include <string>


SC_MODULE(Top_RTL_FIR) {

	FIR_RTL   fir_top;

	Stimulus stimulus;
	Display  display;

	sc_time clk_period;
	sc_clock clk;

	sc_signal<bool> reset;
	sc_signal<bool> input_valid;
	sc_signal<int>  sample;
	sc_signal<bool> output_data_ready;
	sc_signal<int>  result;

	SC_CTOR(Top_RTL_FIR) :
		fir_top( "process_body"),
		stimulus("stimulus_block"),
		display( "display"),
		clk_period(30, SC_NS), clk("clk", clk_period) {

		stimulus.reset(reset);
		stimulus.input_valid(input_valid);
		stimulus.sample(sample);
		stimulus.CLK(clk);

		fir_top.RESET(reset);
		fir_top.IN_VALID(input_valid);
		fir_top.SAMPLE(sample);
		fir_top.OUTPUT_DATA_READY(output_data_ready);
		fir_top.RESULT(result);
		fir_top.CLK(clk);

		display.output_data_ready(output_data_ready);
		display.result(result);
	}
};

SC_MODULE(Top_FIR) {

	//FIR<C_SAMPLE_SIZE, C_N_TAPS, FIR_Coefs<int, C_N_TAPS>, false> fir_real;
	//FIR_Adapted<C_SAMPLE_SIZE, C_N_TAPS, FIR_Coefs<int, C_N_TAPS>, false> fir_real;
	FIR_Debugged_Comp<C_SAMPLE_SIZE, C_N_TAPS, FIR_Coefs<int, C_N_TAPS>, false> fir_real;
	FIR<C_SAMPLE_SIZE, C_N_TAPS, FIR_Coefs<int, C_N_TAPS>, true> fir_complex;

	Testbench<C_SAMPLE_SIZE, C_N_TAPS> testbench;

	sc_time clk_period;
	sc_clock clk;

	sc_signal<bool> reset;
	sc_signal<bool> req_real;
	sc_signal<bool> rdy_real;
	sc_signal<bool> req_complex;
	sc_signal<bool> rdy_complex;
	sc_signal<sc_int<C_SAMPLE_SIZE> >  sample_in;
	sc_signal<sc_int<C_SAMPLE_SIZE> >  sample_out;
	sc_signal<sc_int<C_SAMPLE_SIZE> >  sample_in_real;
	sc_signal<sc_int<C_SAMPLE_SIZE> >  sample_out_real;
	sc_signal<sc_int<C_SAMPLE_SIZE> >  sample_in_imag;
	sc_signal<sc_int<C_SAMPLE_SIZE> >  sample_out_imag;
	sc_signal<bool>  debug_trigger;
	sc_signal<sc_uint<64> >  debug_data;

	SC_CTOR(Top_FIR) :
		fir_real("FIR_Real"),
		fir_complex("FIR_Complex"),
		testbench("Testbench"),
		clk_period(30, SC_NS), clk("clk", clk_period) {

		testbench.clk_in(clk);
		testbench.rst_in(reset);
		testbench.rst_out(reset);
		testbench.op_rdy_in_real(rdy_real);
		testbench.op_req_out_real(req_real);
		testbench.op_rdy_in_complex(rdy_complex);
		testbench.op_req_out_complex(req_complex);
		testbench.sample_in(sample_out);
		testbench.sample_out(sample_in);
		testbench.sample_in_real(sample_out_real);
		testbench.sample_in_imag(sample_out_imag);
		testbench.sample_out_real(sample_in_real);
		testbench.sample_out_imag(sample_in_imag);

		fir_real.clk_in(clk);
		fir_real.rst_in(reset);
		fir_real.req_in(req_real);
		fir_real.rdy_out(rdy_real);
		fir_real.sample_in(sample_in);
		fir_real.sample_out(sample_out);
		fir_real.data_out(debug_data);
		fir_real.trigger_out(debug_trigger);

		fir_complex.clk_in(clk);
		fir_complex.rst_in(reset);
		fir_complex.req_in(req_complex);
		fir_complex.rdy_out(rdy_complex);
		fir_complex.sample_in_real(sample_in_real);
		fir_complex.sample_in_imag(sample_in_imag);
		fir_complex.sample_out_real(sample_out_real);
		fir_complex.sample_out_imag(sample_out_imag);
	}
};


#ifdef MTI_SYSTEMC

SC_MODULE_EXPORT(Top_RTL_FIR);
SC_MODULE_EXPORT(Top_FIR);

#else

template<class T>
void sc_trace_array(sc_trace_file *tf, sc_signal<T> *v, const std::string& name, int len) {
	char stbuf[20];
	for (int i = 0; i < len; i++) {
		sprintf(stbuf, "(%d)", i);
		sc_trace(tf, v[i], name + stbuf);
	}
}


int sc_main (int argc, char *argv[]){


	//Top_RTL_FIR simulation("Simulation");
	Top_FIR simulation("Simulation");

	sc_trace_file *trcf = sc_create_vcd_trace_file("trace");
	if (trcf) {
		sc_trace(trcf, simulation.clk, "clk");
		sc_trace(trcf, simulation.reset, "reset");
		sc_trace(trcf, simulation.req_real, "req");
		sc_trace(trcf, simulation.rdy_real, "rdy");
		sc_trace(trcf, simulation.sample_in, "sample_in");
		sc_trace(trcf, simulation.sample_out, "sample_out");

		trcf->set_time_unit(-15);
	} else {
		std::cout << "Deu pau no trace" << std::endl;
	}

	sc_start();

	if (trcf)
		sc_close_vcd_trace_file(trcf);


	return 0;
}

#endif

