/*
 * compressed.h
 *
 *  Created on: Jun 27, 2011
 *      Author: tiago
 */

#ifndef COMPRESSED2_H_
#define COMPRESSED2_H_

#include "aspect.h"

#include "../dynamic_range/dynamic_range_compression.h"

#include "../adpcm/adpcm_rtl/adpcm_encoder.h"
#include "../adpcm/adpcm_rtl/adpcm_decoder.h"


template<class Super>
class Scenario_Compressed2 {
public:
	Aspect_ADPCM_Decoder adpcm_dec;

	sc_signal<sc_int<16> > adpcm_dec_pcm_out;
    sc_signal<sc_uint<4> > adpcm_dec_adpcm_in;
    sc_signal<bool> adpcm_dec_op_rdy;
    sc_signal<bool> adpcm_dec_op_req;

	Scenario_Compressed2()
		:adpcm_dec("ADPCM_Decoder")
	{
		adpcm_dec.clk_in(static_cast<Super*>(this)->clk_in);
		adpcm_dec.rst_in(static_cast<Super*>(this)->rst_in);
		adpcm_dec.op_req_in(adpcm_dec_op_req);
		adpcm_dec.op_rdy_out(adpcm_dec_op_rdy);
	}

	void scenario_reset(){
		adpcm_dec_op_req = false;
		adpcm_dec_adpcm_in = 0;
	}

	void wait_operation(){
		while(!adpcm_dec_op_rdy.read()) static_cast<Super*>(this)->wait();
	}

	void start_operation(){
		adpcm_dec_op_req = true;
		static_cast<Super*>(this)->wait();
		adpcm_dec_op_req = false;
	}

	void scenario_compress(const sc_int<16> &input, sc_uint<4> &output){

	}

	void scenario_expand(const sc_uint<4> &input, sc_int<16> &output){

		wait_operation();

		adpcm_dec_adpcm_in = input;
		start_operation();

		wait_operation();
		output = adpcm_dec_pcm_out.read();
	}

};


#endif /* COMPRESSED2_H_ */
