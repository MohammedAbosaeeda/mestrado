/*
 * Main.cc
 *
 *  Created on: Nov 15, 2010
 *      Author: tiago
 */

#include <systemc.h>
#include "../../fir/fir_hl/fir.h"
#include "../../fir/fir_hl/fir_debugged_comp.h"

#define C_SAMPLE_SIZE 32
#define C_N_TAPS 17
#define DWIDTH 32

int ag_main() {

	//FIR<C_SAMPLE_SIZE, C_N_TAPS, FIR_Coefs<int, C_N_TAPS>, false> fir_real("FIR");

	FIR_Debugged_Comp<C_SAMPLE_SIZE, C_N_TAPS, FIR_Coefs<int, C_N_TAPS>, false> fir_real("FIR");

	return 0;
}

