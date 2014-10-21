/*
 * adpcm_encoder.h
 *
 *	4:1 ADPCM encoder
 *
 *  Created on: Jun 28, 2011
 *      Author: tiago
 */

#ifndef ADPCM_ENCODER_H_
#define ADPCM_ENCODER_H_

#include "../../aspect/aspect.h"
#include "adpcm_common.h"

class Aspect_ADPCM_Encoder: public Aspect_Common<Aspect_ADPCM_Encoder> {
public:
	typedef Aspect_Common<Aspect_ADPCM_Encoder> Base;

	sc_in<sc_int<16> > pcm_in;
	sc_out<sc_uint<4> > adpcm_out;

	//Internals
private:
	sc_signal<sc_int<16> > prev_sample;
	sc_signal<sc_uint<7> > prev_index;

public:
	Aspect_ADPCM_Encoder(sc_module_name nm) :Base(nm) {

	}

	void aspect_reset(){
		prev_sample = 0;
		prev_index = 0;
		adpcm_out = 0;
	}

	void aspect_trigger_behavior(){
		sc_int<16> pred_sample = prev_sample;
		sc_int<16> step = ADPCM_Common::STEP_SIZE_TABLE[prev_index.read()];

		sc_int<16> diff = pcm_in.read() - pred_sample;

		sc_uint<4> code = 0;
		if (diff >= 0)
			code = 0;
		else {
			code = 8;
			diff = -diff;
		}

		sc_int<16> temp_step = step;
		if (diff >= temp_step){
			code = code | 4;
			diff = diff - temp_step;
		}
		temp_step = temp_step >> 1;
		if (diff >= temp_step) {
			code = code | 2;
			diff = diff - temp_step;
		}
		temp_step = temp_step >> 1;
		if (diff >= temp_step)
			code = code | 1;

		sc_int<16> diffq = step >> 3;
		if (code & 4)
			diffq = diffq + step;
		if (code & 2)
			diffq = diffq + (step >> 1);
		if (code & 1)
			diffq = diffq + (step >> 2);

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

		adpcm_out = code;

	}

	void aspect_idle_behavior(){
	}

};

#endif /* ADPCM_ENCODER_H_ */
