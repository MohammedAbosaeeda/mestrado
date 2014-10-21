/*
 * Main.cc
 *
 *  Created on: Nov 15, 2010
 *      Author: tiago
 */

#include <systemc.h>
#include "../../aspect/aspect.h"
#include "../../scheduler/scheduler_rtl/scheduler.h"
#include "../../scheduler/scheduler_rtl/adapted_scheduler.h"
#include "../../scheduler/scheduler_rtl/scheduler_adapted.h"
#include "../../scheduler/scheduler_rtl/scheduler_debuged.h"
#include "../../scheduler/scheduler_rtl/debugged.h"
#include "../../scheduler/scheduler_hl/scheduler_hl.h"
#include "../../scheduler/scheduler_hl/scheduler_hl_debugged.h"
#include "../../scheduler/scheduler_hl/scheduler_hl_adapted_gohorse.h"
#include "../../scheduler/scheduler_hl/priority_linked_list.h"

#define MAX_THREADS 3
#define DWIDTH 32

int ag_main() {
	//Priority_Linked_List<4,3,32> list("List");
	//Scheduler_Adapted<MAX_THREADS, DWIDTH> scheduler("Scheduler");
	//Scheduler_Debuged<MAX_THREADS, DWIDTH> scheduler("Scheduler");

	//Profiled<16,8> profiled("Profiled");
	//Traced<16,16> traced("Traced");
	//Watched<16> watched("Watched");


	Scheduler_HL<MAX_THREADS, DWIDTH> scheduler("Scheduler");
	//Scheduler_HL_Debugged<MAX_THREADS, DWIDTH> scheduler("Scheduler");
	//Scheduler_HL_Adapted_Go_Horse<MAX_THREADS, DWIDTH> scheduler("Scheduler");

	//Aspect_Traced<4, 32> db_scenario_traced("Traced");
	//Aspect_Profiled_Go_Horse<4,8> db_scenario_profiled("Profiled");

	return 0;
}

