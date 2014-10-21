/*
 * adpcm_encoder.h
 *
 *	4:1 ADPCM encoder
 *
 *  Created on: Jun 28, 2011
 *      Author: tiago
 */

#ifndef ADPCM_DECODER_H_
#define ADPCM_DECODER_H_

//#include "../../aspect/aspect.h"
#include "adpcm_common.h"

/*
class Aspect_ADPCM_Decoder: public Aspect_Common<Aspect_ADPCM_Decoder> {
public:
	typedef Aspect_Common<Aspect_ADPCM_Decoder> Base;

	sc_out<sc_int<16> > pcm_out;
	sc_in<sc_uint<4> > adpcm_in;

	//Internals
private:
	sc_signal<sc_int<16> > prev_sample;
	sc_signal<sc_uint<7> > prev_index;


public:
	Aspect_ADPCM_Decoder(sc_module_name nm) :Base(nm) {

	}

	void aspect_reset(){
		prev_sample = 0;
		prev_index = 0;
		pcm_out = 0;
	}

	void aspect_trigger_behavior(){
		sc_int<16> pred_sample = prev_sample;
		sc_int<16> step = ADPCM_Common::STEP_SIZE_TABLE[prev_index.read()];
		sc_uint<4> code = adpcm_in.read();

		sc_int<16> diffq = step >> 3;
		if (code & 4) {
			diffq = diffq + step;
		}
		if (code & 2) {
			diffq = diffq + (step >> 1);
		}
		if (code & 1){
			diffq = diffq + (step >> 2);
		}

		if (code & 8)
			pred_sample = pred_sample - diffq;
		else
			pred_sample = pred_sample + diffq;

		if (pred_sample > 32767)
			pred_sample = 32767;
		else if (pred_sample < -32768)
			pred_sample = -32768;

		sc_int<8> index(prev_index.read());
		index = index + ADPCM_Common::INDEX_TABLE[code];

		if (index >= 89) index = 88;
		else if (index < 0) index = 0;

		prev_sample = pred_sample;
		prev_index = index.range(7, 0).to_uint();

		pcm_out = pred_sample;

	}

	void aspect_idle_behavior(){
	}

};*/

SC_MODULE(Aspect_ADPCM_Decoder_Go_Horse){
public:
	sc_in<bool> clk_in;
	sc_in<bool> rst_in;

	sc_in<bool> op_req_in;
	sc_out<bool> op_rdy_out;

	sc_out<sc_int<16> > pcm_out;
	sc_in<sc_uint<4> > adpcm_in;

		//Internals
	private:
		sc_signal<sc_int<16> > prev_sample;
		sc_signal<sc_uint<7> > prev_index;

	public:
	SC_CTOR(Aspect_ADPCM_Decoder_Go_Horse){
		SC_CTHREAD(behavior, clk_in.pos());
		reset_signal_is(rst_in, true);
	}

	void behavior(){
		reset_behavior();
		wait();
		while(true){
			main_behavior();
			wait();
		}
	}

	void reset_behavior(){
		op_rdy_out = false;
		aspect_reset();
	}

	void main_behavior(){
		if (op_req_in.read()){
			op_rdy_out = false;
			aspect_trigger_behavior();
			op_rdy_out = true;
		}
		else{
			op_rdy_out = true;
			aspect_idle_behavior();
		}

	}

	void aspect_reset(){
			prev_sample = 0;
			prev_index = 0;
			pcm_out = 0;
		}

	void aspect_trigger_behavior(){
#pragma HLS resource variable=ADPCM_Common::INDEX_TABLE core=ROM_1P_1S

			sc_int<16> pred_sample = prev_sample;
			sc_int<16> step = ADPCM_Common::STEP_SIZE_TABLE[prev_index.read()];
			sc_uint<4> code = adpcm_in.read();

			sc_int<16> diffq = step >> 3;
			if (code & 4) {
				diffq = diffq + step;
			}
			if (code & 2) {
				diffq = diffq + (step >> 1);
			}
			if (code & 1){
				diffq = diffq + (step >> 2);
			}

			if (code & 8)
				pred_sample = pred_sample - diffq;
			else
				pred_sample = pred_sample + diffq;

			if (pred_sample > 32767)
				pred_sample = 32767;
			else if (pred_sample < -32768)
				pred_sample = -32768;

			sc_int<8> index(prev_index.read());
			index = index + ADPCM_Common::INDEX_TABLE[code];

			if (index >= 89) index = 88;
			else if (index < 0) index = 0;

			prev_sample = pred_sample;
			prev_index = index.range(7, 0).to_uint();

			pcm_out = pred_sample;

		}

		void aspect_idle_behavior(){
		}

};

#endif /* ADPCM_ENCODER_H_ */
