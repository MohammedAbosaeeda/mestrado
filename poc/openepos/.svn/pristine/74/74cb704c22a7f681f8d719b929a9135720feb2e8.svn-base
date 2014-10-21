/*
 * testbench.h
 *
 *  Created on: Jun 29, 2011
 *      Author: tiago
 */

#ifndef TESTBENCH_H_
#define TESTBENCH_H_

#include <systemc.h>
#include <cmath>
#include <iostream>
using std::cout;
using std::endl;
using std::ifstream;
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>

#include "../rtl/dtmf_detector.h"

#define PI 3.14159265

SC_MODULE(Testbench){
	sc_in<bool> clk_in;
	sc_in<bool> rst_in;
	sc_out<bool> rst_out;

	sc_in<bool> rdy_in;
	sc_out<bool> req_out;

	sc_out<DTMF_Detector::sample_t> sample_out;
	sc_in<sc_uint<8> > dtmf_in;


	SC_CTOR(Testbench){
		SC_THREAD(testbench);

		SC_METHOD(tsc_method);
		sensitive << clk_in.pos();
	}

	unsigned int tsc;

	void tsc_method(){
		if(rst_in.read())
			tsc = 0;
		else
			++tsc;
	}

	void wait_cycles(int n){
		for (int i = 0; i < n; ++i) {
			wait(clk_in.posedge_event());
		}
	}

	void testbench(){
		rst_out = true;
		req_out = false;
		sample_out = 0;

		wait_cycles(6);
		rst_out = false;

		wait_cycles(2);

		cout << "\nTesting DTMF\n" << endl;

		for(int i = 0; i < 20; ++i){
			sc_bv<32> sample = rand();
			cout << "Encoding sample " << sample;
			unsigned int dtmf = detect(sample).to_uint();
			cout << " | Result = " << dtmf << "\n";
		}

		sc_stop();
	}

	sc_bv<8> detect(sc_bv<32> sample){
		while(!rdy_in.read()) wait_cycles(1);
		req_out = true;
		sample_out = sample;
		while(!rdy_in.read()){ wait_cycles(1); req_out = false;}
		req_out = false;
		return dtmf_in.read();
	}


};

#endif /* TESTBENCH_H_ */
