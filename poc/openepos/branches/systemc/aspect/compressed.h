/*
 * compressed.h
 *
 *  Created on: Jun 27, 2011
 *      Author: tiago
 */

#ifndef COMPRESSED_H_
#define COMPRESSED_H_

#include "aspect.h"

#include "../dynamic_range/dynamic_range_compression.h"

#include "../adpcm/adpcm_rtl/adpcm_encoder.h"
#include "../adpcm/adpcm_rtl/adpcm_decoder.h"


template<class Super, class COMP_TYPE, unsigned int COMP_BITS, class EXP_TYPE, unsigned int EXP_BITS>
class Scenario_Compressed {
public:
	Aspect_Dynamic_Range_Compression<COMP_TYPE,COMP_BITS,EXP_TYPE,EXP_BITS> dynamic_range_comp;

	sc_signal<bool> dynamic_range_comp_op_rdy;
	sc_signal<bool> dynamic_range_comp_op_req;
	sc_signal<bool> dynamic_range_comp_compress;
	sc_signal<EXP_TYPE> dynamic_range_comp_sample_in;
	sc_signal<EXP_TYPE> dynamic_range_comp_sample_out;

	Scenario_Compressed()
		:dynamic_range_comp("Dynamic_Range_Compression")
	{
		dynamic_range_comp.clk_in(static_cast<Super*>(this)->clk_in);
		dynamic_range_comp.rst_in(static_cast<Super*>(this)->rst_in);
		dynamic_range_comp.op_req_in(dynamic_range_comp_op_req);
		dynamic_range_comp.op_rdy_out(dynamic_range_comp_op_rdy);
		dynamic_range_comp.compress_in(dynamic_range_comp_compress);
		dynamic_range_comp.sample_in(dynamic_range_comp_sample_in);
		dynamic_range_comp.sample_out(dynamic_range_comp_sample_out);
	}

	void scenario_reset(){
		dynamic_range_comp_op_req = false;
		dynamic_range_comp_compress = false;
		dynamic_range_comp_sample_in = 0;
	}

	void wait_operation(){
		while(!dynamic_range_comp_op_rdy.read()) static_cast<Super*>(this)->wait();
	}

	void start_operation(){
		dynamic_range_comp_op_req = true;
		static_cast<Super*>(this)->wait();
		dynamic_range_comp_op_req = false;
	}

	void scenario_compress(const EXP_TYPE &input, COMP_TYPE &output){

		wait_operation();

		dynamic_range_comp_sample_in = input;
		dynamic_range_comp_compress = true;
		start_operation();

		wait_operation();
		output = dynamic_range_comp_sample_out.read().range(COMP_BITS-1, 0);

	}

	void scenario_expand(const COMP_TYPE &input, EXP_TYPE &output){

		wait_operation();

		sc_bv<EXP_BITS-COMP_BITS> aux(0);
		dynamic_range_comp_sample_in = (aux, input);
		dynamic_range_comp_compress = false;
		start_operation();

		wait_operation();
		output = dynamic_range_comp_sample_out.read();
	}

};


#endif /* COMPRESSED_H_ */
