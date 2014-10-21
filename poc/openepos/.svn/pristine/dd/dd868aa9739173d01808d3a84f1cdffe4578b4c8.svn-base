/*
 * dtmf_detector.cc
 *
 *  Created on: Aug 24, 2011
 *      Author: tiago
 */

#include "dtmf_detector.h"

const int DTMF_Detector::SINES[8] = {0,0,0,0,0,0,0,0};
const int DTMF_Detector::COSINES[8] = {0,0,0,0,0,0,0,0};

// sparse array, indexed with 8 bits indices, provides a 0(1) lookup to the valid tone combinations
const unsigned char DTMF_Detector::RESPONSE_LOOKUP[MAX_UCHAR + 1] = {
		INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,
		INVAL,   13,   15,INVAL,    0,INVAL,INVAL,INVAL,   14,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,
		INVAL,   12,    9,INVAL,    8,INVAL,INVAL,INVAL,    7,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,
		INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,
		INVAL,   11,    6,INVAL,    5,INVAL,INVAL,INVAL,    4,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,
		INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,
		INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,
		INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,
		INVAL,   10,    3,INVAL,    2,INVAL,INVAL,INVAL,    1,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,
		INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,
		INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,
		INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,
		INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,
		INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,
		INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,
		INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL,INVAL
};

const char DTMF_Detector::button_names[16] = {
		'0', '1', '2', '3', '4', '5', '6', '7',
		'8', '9', 'A', 'B', 'C', 'D', '*', '#',
};

void DTMF_Detector::goertzel(int &tone_index, bool &result) {
	int cosine = COSINES[tone_index];
	int sine = SINES[tone_index];
	int coeff = 2 * cosine;

	int real = 0.0;
	int imag = 0.0;
	int real_square = 0.0;
	int imag_square = 0.0;

	int q0 = 0.0, q1 = 0.0, q2 = 0.0;


	//GOERTZEL_ACC:
	for(int i = 0; i < FRAMESIZE; ++i) {
		q0 = (coeff * q1) - (q2 + samples[i]);
		q2 = q1;
		q1 = q0;
		wait();
	}

	real = q1 - q2 * cosine;
	wait();

	imag = q2 * sine;
	wait();

	real_square = real*real;
	wait();
	imag_square = imag*imag;
	wait();

	result = ((real_square+imag_square) > THRESHOLD);
}

void DTMF_Detector::analyze_responses(unsigned char &result) {
	unsigned char index = 0;

	//ANALYZE_SHIFT:
	for(int i = 0; i < 8; ++i){
		index |= (tone_responses[i] << i);
		wait();
	}

	result = RESPONSE_LOOKUP[index];
}

void DTMF_Detector::do_dtmf(int sample, char &result){

	//SAMPLE_SHIFT:
	for (int i = FRAMESIZE-1; i >= 0; --i) {
		samples[i] = (i == 0) ? sample : samples[i-1];
	}
	wait();

	//GOERTZEL:
	for(int i = 0; i < TONES; ++i){
		goertzel(i, tone_responses[i]);
		wait();
	}

	unsigned char detected_signal = INVAL;
	analyze_responses(detected_signal);
	wait();

	// convert from [0, 15] to [1,2,3,..,A,B,..,F] using a lookup table
	// and write the char to UART
	// 'I' for invalid signal
	if(detected_signal == INVAL) {
		result = 'I';
	} else {
		result = button_names[detected_signal];
	}
}
