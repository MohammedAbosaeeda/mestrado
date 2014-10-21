/*
 * Main.cc
 *
 *  Created on: Nov 15, 2010
 *      Author: tiago
 */

#include <systemc.h>
#include "../../adpcm/adpcm_rtl/adpcm_encoder.h"
#include "../../adpcm/adpcm_rtl/adpcm_decoder.h"
#include "../../adpcm/testbench/testbench.h"
#include <iostream>
#include <string>


SC_MODULE(Top_ADPCM) {

	Aspect_ADPCM_Encoder enconder;
	Aspect_ADPCM_Decoder decoder;
	Testbench testbench;

	sc_time clk_period;
	sc_clock clk;

	sc_signal<bool> reset;
	sc_signal<bool> req_enc;
	sc_signal<bool> rdy_enc;
	sc_signal<bool> req_dec;
	sc_signal<bool> rdy_dec;
	sc_signal<sc_int<16> > pcm_out;
	sc_signal<sc_uint<4> > adpcm_in;
	sc_signal<sc_int<16> > pcm_in;
	sc_signal<sc_uint<4> > adpcm_out;

	SC_CTOR(Top_ADPCM) :
		enconder("ADPCM_Encoder"),
		decoder("ADPCM_Decoder"),
		testbench("Testbench"),
		clk_period(30, SC_NS), clk("clk", clk_period) {

		enconder.clk_in(clk);
		enconder.rst_in(reset);
		enconder.op_req_in(req_enc);
		enconder.op_rdy_out(rdy_enc);
		enconder.pcm_in(pcm_out);
		enconder.adpcm_out(adpcm_in);

		decoder.clk_in(clk);
		decoder.rst_in(reset);
		decoder.op_req_in(req_dec);
		decoder.op_rdy_out(rdy_dec);
		decoder.pcm_out(pcm_in);
		decoder.adpcm_in(adpcm_out);

		testbench.clk_in(clk);
		testbench.rst_in(reset);
		testbench.rst_out(reset);
		testbench.op_rdy_in_enc(rdy_enc);
		testbench.op_req_out_enc(req_enc);
		testbench.op_rdy_in_dec(rdy_dec);
		testbench.op_req_out_dec(req_dec);
		testbench.pcm_out(pcm_out);
		testbench.adpcm_in(adpcm_in);
		testbench.pcm_in(pcm_in);
		testbench.adpcm_out(adpcm_out);
	}
};



#ifdef MTI_SYSTEMC

SC_MODULE_EXPORT(Top_ADPCM);

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

	Top_ADPCM simulation("Simulation");

	sc_trace_file *trcf = sc_create_vcd_trace_file("trace");
	if (trcf) {
		//Put tracing here
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

