/*
 * Testbench.h
 *
 *  Created on: Jun 17, 2011
 *      Author: tiago
 */

#ifndef TESTBENCH_H_
#define TESTBENCH_H_

#include <systemc.h>
#include "../common/fir_coefs.h"
#include <cmath>
#include <iostream>
#include <fstream>
#include "../common/cplxopsphasor.sc.h"
#include "nco.h"
using std::cout;
using std::endl;
using std::ofstream;

#define PI 3.14159265

template<unsigned int SAMPLE_SIZE, unsigned int N_TAPS>
SC_MODULE(Testbench){
	sc_in<bool> clk_in;
	sc_in<bool> rst_in;
	sc_out<bool> rst_out;

	sc_in<bool> op_rdy_in_real;
	sc_out<bool> op_req_out_real;

	sc_in<bool> op_rdy_in_complex;
	sc_out<bool> op_req_out_complex;

	sc_in<sc_int<SAMPLE_SIZE> > sample_in;
	sc_in<sc_int<SAMPLE_SIZE> > sample_in_real;
	sc_in<sc_int<SAMPLE_SIZE> > sample_in_imag;
	sc_out<sc_int<SAMPLE_SIZE> > sample_out;
	sc_out<sc_int<SAMPLE_SIZE> > sample_out_real;
	sc_out<sc_int<SAMPLE_SIZE> > sample_out_imag;

	int shift_real[N_TAPS];
	complex<int> shift_comlex[N_TAPS];

	NCO nco200;
	NCO nco500;

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
		op_req_out_real = false;
		op_req_out_complex = false;
		sample_out = 0;
		sample_out_real = 0;
		sample_out_imag = 0;
		nco200.set_freq (2 * PI * 200 / 1200);
		nco500.set_freq (2 * PI * 500 / 1200);
		for (unsigned int i = 0; i < N_TAPS; ++i) {
			shift_real[i] = 0;
			shift_comlex[i] = complex<int>(0,0);
		}

		wait_cycles(6);
		rst_out = false;

		wait_cycles(2);

		cout << "Testing REAL FIR" << endl;
		ofstream file_real;
		file_real.open("samples.csv");
		for (int i = 0; i < 128; ++i) {

			unsigned int current_tsc = tsc;
			sc_int<SAMPLE_SIZE> sample = get_sample().get_real();

			cout << "Filtering sample: " << sample << " | ";
			sc_int<SAMPLE_SIZE> sample_rtl = filter_sample_rtl(sample);
			cout << " RTL = " << sample_rtl;
			int sample_tb = filter_sample_tb(sample);
			cout << " TB = " << sample_tb;

			double aux0 = std::fabs((double)sample_rtl);
			double aux1 = std::fabs((double)sample_tb);
			double error = (aux0 > aux1) ? (aux1/aux0) : (aux0/aux1);
			error = (1 - error) * 100;
			cout << " error = " << (int)error << "%";
			cout << " runtime = " << tsc-current_tsc << " cycles" << endl;

			if(error > 5)
				cout << "ERROR TO BIG" << endl;
			else
				file_real << tsc << ";" << sample << ";" << sample_rtl << "\n";

		}
		file_real.close();

		sc_stop();

		cout << "\n\nTesting COMLEX FIR" << endl;
		for (int i = 0; i < 128; ++i) {

			unsigned int current_tsc = tsc;
			complex<int> sample_tmp = get_sample();
			complex<sc_int<SAMPLE_SIZE> > sample(sample_tmp.get_real(), sample_tmp.get_imag());

			cout << "Filtering sample: " << "(" << sample.get_real() << "," << sample.get_imag() << ") | ";
			complex<sc_int<SAMPLE_SIZE> > sample_rtl = filter_sample_rtl(sample);
			cout << " RTL = " << "(" << sample_rtl.get_real() << "," << sample_rtl.get_imag() << ")";
			complex<int> sample_tb = filter_sample_tb(sample_tmp);
			cout << " TB = " << "(" << sample_tb.get_real() << "," << sample_tb.get_imag() << ")";

			double aux0 = std::fabs((double)sample_rtl.get_real());
			double aux1 = std::fabs((double)sample_tb.get_real());
			double error_real = (aux0 > aux1) ? (aux1/aux0) : (aux0/aux1);
			error_real = (1 - error_real) * 100;

			aux0 = std::fabs((double)sample_rtl.get_imag());
			aux1 = std::fabs((double)sample_tb.get_imag());
			double error_imag = (aux0 > aux1) ? (aux1/aux0) : (aux0/aux1);
			error_imag = (1 - error_real) * 100;
			double error = (error_imag+error_real)/2;

			cout << " error = " << (int)error << "%";
			cout << " runtime = " << tsc-current_tsc << " cycles" << endl;

			if(error > 5)
				cout << "ERROR TO BIG" << endl;

		}

		//sc_stop();

	}

	sc_int<SAMPLE_SIZE> filter_sample_rtl(sc_int<SAMPLE_SIZE> sample){
		while(!op_rdy_in_real.read()) wait_cycles(1);
		op_req_out_real = true;
		sample_out = sample;
		wait_cycles(1);
		op_req_out_real = false;
		while(!op_rdy_in_real.read()) wait_cycles(1);
		return sample_in.read();
	}

	complex<sc_int<SAMPLE_SIZE> > filter_sample_rtl(complex<sc_int<SAMPLE_SIZE> > sample){
		while(!op_rdy_in_complex.read()) wait_cycles(1);
		op_req_out_complex = true;
		sample_out_real = sample.get_real();
		sample_out_imag = sample.get_imag();
		wait_cycles(1);
		op_req_out_complex = false;
		while(!op_rdy_in_complex.read()) wait_cycles(1);
		complex<sc_int<SAMPLE_SIZE> > ret;
		ret.set_real(sample_in_real.read());
		ret.set_imag(sample_in_imag.read());
		return ret;
	}

	int filter_sample_tb(int sample){
		//std::cout << "##shift=";
		//for (int i = 0; i < N_TAPS; ++i) std::cout << shift_real[i] << ","; std::cout << "##";
		//std::cout << "##coefs=";
		//for (int i = 0; i < N_TAPS; ++i) std::cout << FIR_Coefs<int,17>::COEFS[i] << ","; std::cout << "##";

		for (int i = N_TAPS-1; i >= 1; --i) {
			shift_real[i] = shift_real[i-1];
		}
		shift_real[0] = sample;

		int acc = 0;
		for (unsigned int i = 0; i < N_TAPS; ++i) {
			acc += shift_real[i] * FIR_Coefs<int,N_TAPS>::COEFS[i];
		}

		return acc;
	}

	complex<int> filter_sample_tb(complex<int> &sample){
		for (int i = N_TAPS-1; i >= 1; --i) {
			shift_comlex[i] = shift_comlex[i-1];
		}
		shift_comlex[0] = sample;

		complex<int> acc(0,0);
		for (unsigned int i = 0; i < N_TAPS; ++i) {
			acc += shift_comlex[i] * complex<int>(FIR_Coefs<int,N_TAPS>::COEFS[i], 0);
		}

		return acc;
	}


	complex<int> get_sample(){

		complex<int> result200;
		complex<int> result500;

		nco200.sincos(&result200, 1);
		nco500.sincos(&result500, 1);

		return result200 + result500;
		//return result500;
	}
};


#endif /* TESTBENCH_H_ */
