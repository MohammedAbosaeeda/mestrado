/*
 * dtmf_detector.h
 *
 *  Created on: Jul 4, 2011
 *      Author: tiago
 */

#ifndef DTMF_DETECTOR_H_
#define DTMF_DETECTOR_H_

#define SC_INCLUDE_FX
#include <systemc.h>

SC_MODULE(DTMF_Detector){
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

	sc_in<bool> clk_in;
	sc_in<bool> rst_in;

	sc_in<bool> req_in;
	sc_out<bool> rdy_out;

	sc_in<int> sample_in;
	sc_out<sc_uint<8> > dtmf_out;

private:
    static const int SINES[8];
    static const int COSINES[8];
    static const unsigned char RESPONSE_LOOKUP[MAX_UCHAR + 1];
    static const char button_names[16];

private:
	int samples[FRAMESIZE];
	bool tone_responses[TONES];

public:
	SC_CTOR(DTMF_Detector) {
		SC_CTHREAD(behavior, clk_in.pos());
		reset_signal_is(rst_in, true);
	}


	virtual void reset_behavior(){
		//TODO commented for synthesis with agility (uncomment for a working model)
		/*for (unsigned int i = 0; i < FRAMESIZE; ++i)
			samples[i] = 0;
		for (unsigned int i = 0; i < TONES; ++i)
			tone_responses[i] = 0;*/
	}

	void main_behavior(){
		if(req_in.read()){
			rdy_out = false;
			char result;
			do_dtmf(sample_in.read(), result);
			dtmf_out = result;
		}
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

#endif /* DTMF_DETECTOR_H_ */
