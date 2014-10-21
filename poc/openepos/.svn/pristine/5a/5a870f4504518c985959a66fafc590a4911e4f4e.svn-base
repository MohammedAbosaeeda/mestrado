/*
 * dtmf_detector.h
 *
 *  Created on: Jul 4, 2011
 *      Author: tiago
 */

#ifndef DTMF_DETECTOR_HARD_H_
#define DTMF_DETECTOR_HARD_H_

#define SC_INCLUDE_FX
#include <systemc.h>
#include "../../adpcm/adpcm_rtl/adpcm_decoder.h"

SC_MODULE(DTMF_Detector_Hardcodded){
public:
	enum{
		THRESHOLD = 42, // bogus, define it better later
		TONES = 8,
		FRAMESIZE = 700,
		SRATE = 32000,

		MAX_UCHAR = 255,
		MAX_SHORT = 32768,
		INVAL  = 255,

		SINCOS_SIZE = 32,
		SINCOS_FP = 28,

		SAMPLE_SIZE = 32,
		SAMPLE_FP = 28
	};

	static const int OPERATION_SIZE = 1;
	static const int PARAM_RETURN_SIZE = 8;
	static const int PRECISION = 8;
	static const int STATE_SIZE = 2;

	sc_in<bool> clk_in;
	sc_in<bool> rst_in;

	sc_in<bool> req_in;
	sc_out<bool> rdy_out;

	sc_in<int> sample_in;
	sc_out<sc_uint<8> > dtmf_out;

	sc_out<bool> trigger_out;
	sc_out<sc_uint<64> > data_out;

private:
    static const int SINES[8];
    static const int COSINES[8];
    static const unsigned char RESPONSE_LOOKUP[MAX_UCHAR + 1];
    static const char button_names[16];

private:
	Aspect_ADPCM_Decoder adpcm_decoder;
	sc_signal<sc_int<16> > adpcm_dec_pcm_out;
	sc_signal<sc_uint<4> > adpcm_dec_adpcm_in;
	sc_signal<bool> adpcm_dec_op_rdy;
	sc_signal<bool> adpcm_dec_op_req;

    int samples[FRAMESIZE];
	bool tone_responses[TONES];

	sc_signal<sc_uint<OPERATION_SIZE> > operation;
	sc_signal<sc_uint<PARAM_RETURN_SIZE> > param_return;
	sc_signal<sc_uint<STATE_SIZE> > state;
	sc_signal<bool> start_stop;
	sc_signal<sc_uint<PRECISION> > profiled_counter;

public:
	SC_CTOR(DTMF_Detector_Hardcodded)
		:adpcm_decoder("Aspect_ADPCM_Decoder")
	{
		SC_CTHREAD(behavior, clk_in.pos());
		reset_signal_is(rst_in, true);

		SC_METHOD(set_debug_data);
		sensitive << start_stop
				  << operation
				  << param_return
				  << profiled_counter
				  << state;

		SC_METHOD(count);
		sensitive << clk_in.pos();

		adpcm_decoder.clk_in(clk_in);
		adpcm_decoder.rst_in(rst_in);
		adpcm_decoder.op_req_in(adpcm_dec_op_req);
		adpcm_decoder.op_rdy_out(adpcm_dec_op_rdy);
		adpcm_decoder.adpcm_in(adpcm_dec_adpcm_in);
		adpcm_decoder.pcm_out(adpcm_dec_pcm_out);
	}


	void count(){
				if(start_stop){
					profiled_counter = 0;
				}
				else
					profiled_counter = profiled_counter.read() + 1;
			}

			void set_debug_data(){
				data_out = (start_stop.read(),
							operation.read(),
							param_return.read(),
							profiled_counter.read(),
							state.read());
			}

			void debug_enter(){
				trigger_out = 1;
				state = 0;
				operation = 0;
				param_return = sample_in.read();
				start_stop = 1;
			}

			void debug_leave(){
				trigger_out = 1;
				state = 0;
				operation = 0;
				param_return = dtmf_out.read();
				start_stop = 0;
			}

			void debug_go_down(){
				trigger_out = 0;
				state = 0;
				operation = 0;
				param_return = 0;
				start_stop = 0;
			}


	virtual void reset_behavior(){
		//TODO commented for synthesis with agility (uncomment for a working model)
		/*for (unsigned int i = 0; i < FRAMESIZE; ++i)
			samples[i] = 0;
		for (unsigned int i = 0; i < TONES; ++i)
			tone_responses[i] = 0;

		operation = 0;
				param_return = 0;
				state = 0;
				start_stop = 0;
				*/
	}

	void main_behavior(){
		if(req_in.read()){
			rdy_out = false;

			debug_enter();

			adpcm_dec_op_req = true;
			adpcm_dec_adpcm_in = sample_in.read();
			wait();
			adpcm_dec_op_req = false;
			while(!adpcm_dec_op_rdy.read()) wait();

			char result;
			do_dtmf(adpcm_dec_pcm_out.read(), result);
			dtmf_out = result;


			debug_leave();
			wait();
		}
		debug_go_down();
		rdy_out = true;
	}

	void behavior(){
		reset_behavior();
		wait();
		while(true){
			main_behavior();
			wait();
		}
	}

private:
    void goertzel(int &tone_index, bool &result);
    void analyze_responses(unsigned char &result);
    virtual void do_dtmf(int sample, char &result);
};

#endif /* DTMF_DETECTOR_HARD_H_ */
