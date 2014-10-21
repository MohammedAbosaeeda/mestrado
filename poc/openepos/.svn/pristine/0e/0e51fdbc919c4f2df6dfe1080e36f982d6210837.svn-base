/*
 * Scheduler.h
 *
 *  Created on: Nov 23, 2010
 *      Author: tiago
 */

#ifndef DTMF_DETECTOR_ADAPTED_H_
#define DTMF_DETECTOR_ADAPTED_H_

#include <systemc.h>
#include "dtmf_detector.h"
#include "../../aspect/debugged.h"
#include "../../aspect/compressed2.h"

class DTMF_Detector_Adapted : public DTMF_Detector,
							 public Scenario_Debugged<DTMF_Detector_Adapted, 1, 8, 8>,
							 public Scenario_Compressed2<DTMF_Detector_Adapted>{
public:
	typedef Scenario_Debugged<DTMF_Detector_Adapted, 1, 8, 8> Debugged;
	typedef Scenario_Compressed2<DTMF_Detector_Adapted> Compressed;
	typedef DTMF_Detector_Adapted Base;

	DTMF_Detector_Adapted(sc_module_name nm)
		:Base(nm),
		 Debugged(),
		 Compressed(){
		enjambre_set_signals(); //TODO remove this
	}

	void reset_behavior(){
		Compressed::scenario_reset();
		Debugged::scenario_reset();
		Base::reset_behavior();
	}

	void do_dtmf(int sample, char &result) {
		int aux;
		Compressed::scenario_expand(sample, aux);
		Debugged::scenario_enter(aux, 0);
		Base::do_dtmf(aux, result);
		Debugged::scenario_leave(0, result);
	}

	//TODO remove this
	SC_HAS_PROCESS(Scheduler_HL_Adapted);
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


#endif /* DTMF_DETECTOR_ADAPTED_H_ */


