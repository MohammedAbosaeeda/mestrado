/*
 * Main.cc
 *
 *  Created on: Nov 15, 2010
 *      Author: tiago
 */

#include <systemc.h>
//#include "../../adpcm/adpcm_rtl/adpcm_encoder.h"
#include "../../adpcm/adpcm_rtl/adpcm_decoder.h"

SC_MODULE(Top_ADPCM) {

	//Aspect_ADPCM_Encoder enconder;
	Aspect_ADPCM_Decoder_Go_Horse decoder;

	sc_in<bool> clk;
	sc_in<bool> reset;

	//sc_in<bool> req_enc;
	//sc_out<bool> rdy_enc;
	sc_in<bool> req_dec;
	sc_out<bool> rdy_dec;
	sc_out<sc_int<16> > pcm_out;
	sc_in<sc_uint<4> > adpcm_in;
	//sc_in<sc_int<16> > pcm_in;
	//sc_out<sc_uint<4> > adpcm_out;

	SC_CTOR(Top_ADPCM) :
		//enconder("ADPCM_Encoder"),
		decoder("ADPCM_Decoder") {

		/*enconder.clk_in(clk);
		enconder.rst_in(reset);
		enconder.op_req_in(req_enc);
		enconder.op_rdy_out(rdy_enc);
		enconder.pcm_in(pcm_in);
		enconder.adpcm_out(adpcm_out);*/

		decoder.clk_in(clk);
		decoder.rst_in(reset);
		decoder.op_req_in(req_dec);
		decoder.op_rdy_out(rdy_dec);
		decoder.pcm_out(pcm_out);
		decoder.adpcm_in(adpcm_in);
	}
};

