/*
 * testbench.h
 *
 *  Created on: Jun 29, 2011
 *      Author: tiago
 */

#ifndef TESTBENCH_H_
#define TESTBENCH_H_

#include <systemc.h>

#include <systemc.h>
#include <cmath>
#include <iostream>
using std::cout;
using std::endl;
using std::ifstream;
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>

#define PI 3.14159265

SC_MODULE(Testbench){
	sc_in<bool> clk_in;
	sc_in<bool> rst_in;
	sc_out<bool> rst_out;

	sc_in<bool> op_rdy_in_enc;
	sc_out<bool> op_req_out_enc;

	sc_in<bool> op_rdy_in_dec;
	sc_out<bool> op_req_out_dec;

	sc_out<sc_int<16> > pcm_out;
	sc_in<sc_uint<4> > adpcm_in;
	sc_in<sc_int<16> > pcm_in;
	sc_out<sc_uint<4> > adpcm_out;

	int* src_samples;
	sc_uint<4>* enc_samples;
	sc_int<16>* dec_samples;
	int samples_size;


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
		op_req_out_enc = false;
		op_req_out_dec = false;
		pcm_out = 0;
		adpcm_out = 0;
		get_samples();

		wait_cycles(6);
		rst_out = false;

		wait_cycles(2);

		cout << "\nTesting Encoder\n" << endl;

		for(int i = 0; i < samples_size; ++i){
			//cout << "Encoding sample " << src_samples[i];
			enc_samples[i] = encode_sample(src_samples[i]);
			//cout << " | Result = " << enc_samples[i] << "\n";
		}

		cout << "\nTesting Decoder\n" << endl;

		for(int i = 0; i < samples_size; ++i){
			//cout << "Decoding sample " << enc_samples[i];
			dec_samples[i] = decode_sample(enc_samples[i]);
			//cout << " | Result = " << dec_samples[i] << "\n";
		}

		cout << "\nGetting error\n";

		double error_acc = 0;
		for(int i = 0; i < samples_size; ++i) {
			double error_real = (std::fabs((double)src_samples[i] - dec_samples[i]) / 65536)*100;
			error_acc += error_real;
			cout << "Sample " << src_samples[i] << " -> " << enc_samples[i] << " -> " << dec_samples[i] << " | Error = " << error_real << " %\n";
		}
		cout << "Average error: " << error_acc/samples_size << "\n";

		dump_samples();


		sc_stop();
	}

	sc_uint<4> encode_sample(sc_int<16> sample){
		while(!op_rdy_in_enc.read()) wait_cycles(1);
		op_req_out_enc = true;
		pcm_out = sample;
		wait_cycles(1);
		op_req_out_enc = false;
		while(!op_rdy_in_enc.read()) wait_cycles(1);
		return adpcm_in.read();
	}

	sc_int<16> decode_sample(sc_uint<4> sample){
		while(!op_rdy_in_dec.read()) wait_cycles(1);
		op_req_out_dec = true;
		adpcm_out = sample;
		wait_cycles(1);
		op_req_out_dec = false;
		while(!op_rdy_in_dec.read()) wait_cycles(1);
		return pcm_in.read();
	}

	void dump_samples(){
		ofstream file_samples;
		file_samples.open("samples_dump.csv");
		for (int i = 0; i < samples_size; ++i) {
			file_samples << i << ";" << src_samples[i] << ";" << enc_samples[i] << ";" << dec_samples[i] << "\n";
		}
		file_samples.close();
	}

	void get_samples(){
		struct stat filestatus;
		stat("adpcm/base_files/samples.dat", &filestatus );
		samples_size = filestatus.st_size/4;

		//cout << "Reading " << samples_size << " samples\n";

		if (samples_size < 1)
			sc_stop();

		ifstream file;
		file.open("adpcm/base_files/samples.dat");

		src_samples = new int[samples_size];
		enc_samples = new sc_uint<4>[samples_size];
		dec_samples = new sc_int<16>[samples_size];
		for (int i = 0; i < samples_size; ++i) {
			file.read((char*)&src_samples[i], 4);
		}

		file.close();
	}

};

#endif /* TESTBENCH_H_ */
