/*
 * Main.cc
 *
 *  Created on: Nov 15, 2010
 *      Author: tiago
 */
#define SC_INCLUDE_FX
#include <systemc.h>
#include "../../dtmf_detector/rtl/dtmf_detector.h"
#include "../../dtmf_detector/rtl/dtmf_detector_adapted.h"
#include "../../dtmf_detector/rtl/dtmf_detector_hardcodded.h"
#include "../../dtmf_detector/testbench/testbench.h"
#include <iostream>
#include <string>


SC_MODULE(Top_DTMF) {

	DTMF_Detector dtmf_detector;
	DTMF_Detector_Adapted dtmf_detector_adapted;
	DTMF_Detector_Hardcodded dtmf_detector_hardcodded;
	Testbench testbench;

	sc_time clk_period;
	sc_clock clk;

	sc_signal<bool> reset;
	sc_signal<bool> req;
	sc_signal<bool> rdy;
	sc_signal<DTMF_Detector::sample_t> sample;
	sc_signal<sc_uint<8> > dtmf;

	SC_CTOR(Top_DTMF) :
		dtmf_detector("DTMF_Detector"),
		dtmf_detector_adapted("DTMF_Detector_Adapted"),
		dtmf_detector_hardcodded("DTMF_Detector_Hardcodded"),
		testbench("Testbench"),
		clk_period(30, SC_NS), clk("clk", clk_period) {

		dtmf_detector.clk_in(clk);
		dtmf_detector.rst_in(reset);
		dtmf_detector.req_in(req);
		dtmf_detector.rdy_out(rdy);
		dtmf_detector.sample_in(sample);
		dtmf_detector.dtmf_out(dtmf);

		testbench.clk_in(clk);
		testbench.rst_in(reset);
		testbench.rst_out(reset);
		testbench.rdy_in(rdy);
		testbench.req_out(req);
		testbench.sample_out(sample);
		testbench.dtmf_in(dtmf);

	}
};



#ifdef MTI_SYSTEMC

SC_MODULE_EXPORT(Top_DTMF);

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

	Top_DTMF simulation("Simulation");

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

