/*
 * fir2.h
 *
 *  Created on: Jun 17, 2011
 *      Author: tiago
 */

#ifndef FIR_ADAPTED_H_
#define FIR_ADAPTED_H_

#include <systemc.h>
#include "fir.h"
#include "../../aspect/compressed.h"
#include "../../aspect/debugged.h"

//Não teve como fazer essa adaptação sem fazer um wrapper comum
template<unsigned int N_BITS, unsigned int N_TAPS, class COEF_INIT, bool COMPLEX>
class FIR_Adapted: public FIR_Aux<N_BITS,N_BITS/2,N_TAPS,COEF_INIT,COMPLEX>,
				   public Scenario_Compressed<FIR_Adapted<N_BITS,N_TAPS,COEF_INIT,COMPLEX>, sc_int<N_BITS/2>, N_BITS/2, sc_int<N_BITS>, N_BITS>,
				   public Scenario_Debugged<FIR_Adapted<N_BITS,N_TAPS,COEF_INIT,COMPLEX>, 1, 8, 8>{
public:
	typedef Scenario_Compressed<FIR_Adapted<N_BITS,N_TAPS,COEF_INIT,COMPLEX>, sc_int<N_BITS/2>, N_BITS/2, sc_int<N_BITS>, N_BITS> Compressed;
	typedef Scenario_Debugged<FIR_Adapted<N_BITS,N_TAPS,COEF_INIT,COMPLEX>, 1, 8, 8> Debugged;
	typedef FIR_Aux<N_BITS,N_BITS/2,N_TAPS,COEF_INIT,COMPLEX> Base;

	SC_HAS_PROCESS(FIR_Adapted);
	FIR_Adapted(sc_module_name nm)
		:Base(nm),
		 Compressed(),
		 Debugged(){
		enjambre_set_signals(); //TODO remove this
	}

	void reset_bahavior(){
		Compressed::scenario_reset();
		Debugged::scenario_reset();
		Base::reset_bahavior();
	}

	//TODO remove this
	void enjambre_set_signals(){
		SC_METHOD(enjambre_behavior);
		Base::sensitive << Debugged::profiled_trigger
				        << Debugged::profiled_data
				        << Debugged::traced_trigger
				        << Debugged::traced_data;
	}

	//TODO remove this
	void enjambre_behavior(){
		sc_uint<38> tmp(0);
		Debugged::data_out = (tmp, Debugged::traced_data.read(), Debugged::profiled_data);
		Debugged::trigger_out = Debugged::profiled_trigger.read() || Debugged::traced_trigger.read();
	}


};


#endif /* FIR_ADAPTED_H_ */
