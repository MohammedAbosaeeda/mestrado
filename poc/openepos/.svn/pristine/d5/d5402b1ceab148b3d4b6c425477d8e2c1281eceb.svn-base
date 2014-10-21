/*
 * Main.cc
 *
 *  Created on: Nov 15, 2010
 *      Author: tiago
 */


#define MAX_THREADS 3
#define DWIDTH 32

#include <systemc.h>
#include "../../scheduler/scheduler_rtl/scheduler.h"
#include "../../scheduler/scheduler_hl/scheduler_hl.h"
#include "../../scheduler/testbench/testbench.h"
#include "../../scheduler/scheduler_rtl/debugged.h"
#include "../../scheduler/scheduler_rtl/adapted_scheduler.h"
#include "../../scheduler/scheduler_rtl/scheduler_adapted.h"
#include "../../scheduler/scheduler_rtl/scheduler_debuged.h"
#include "../../scheduler/scheduler_hl/scheduler_hl_adapted.h"
#include "../../scheduler/scheduler_hl/scheduler_hl_debugged.h"
#include <iostream>
#include <string>


SC_MODULE(Toplevel_Simulation_SystemC) {
	Scheduler<MAX_THREADS, DWIDTH> scheduler;
	//Scheduler_Debuged<MAX_THREADS, DWIDTH> scheduler;

	Testbench<MAX_THREADS, DWIDTH> testbench;

	sc_time clk_period;
	sc_clock clk;

	sc_signal<bool> rst;
	sc_signal<sc_bv<4> > sched_command;
	sc_signal<sc_bv<16> > sched_priority;
	sc_signal<sc_bv<DWIDTH> > sched_paremeter;
	sc_signal<sc_bv<DWIDTH> > sched_return;
	sc_signal<sc_bv<6> > sched_status;
	sc_signal<sc_bv<1> > sched_interrupt;
	sc_signal<sc_uint<64> > sched_debug_data;
	sc_signal<bool> sched_debug_trig;

	SC_CTOR(Toplevel_Simulation_SystemC) :
		scheduler("Scheduler"),
		testbench("Testbench"),
		clk_period(30, SC_NS), clk("clk", clk_period) {

		scheduler.clk_in(clk);
		scheduler.rst_in(rst);
		scheduler.command_in(sched_command);
		scheduler.priority_in(sched_priority);
		scheduler.paremeter_in(sched_paremeter);
		scheduler.return_out(sched_return);
		scheduler.status_out(sched_status);
		scheduler.interrupt_out(sched_interrupt);
		//scheduler.trigger_out(sched_debug_trig);
		//scheduler.data_out(sched_debug_data);

		testbench.clk_in(clk);
		testbench.rst_in(rst);
		testbench.rst_out(rst);
		testbench.command_out(sched_command);
		testbench.paremeter_out(sched_paremeter);
		testbench.priority_out(sched_priority);
		testbench.return_in(sched_return);
		testbench.status_in(sched_status);
		testbench.interrupt_in(sched_interrupt);
		testbench.debug_trig_in(sched_debug_trig);
		testbench.debug_data_in(sched_debug_data);
	}
};

SC_MODULE(Toplevel_Simulation_SystemC_HL) {

	Scheduler_HL<MAX_THREADS, DWIDTH> scheduler;

	Testbench<MAX_THREADS, DWIDTH> testbench;

	sc_time clk_period;
	sc_clock clk;

	sc_signal<bool> rst;
	sc_signal<sc_bv<4> > sched_command;
	sc_signal<sc_bv<16> > sched_priority;
	sc_signal<sc_bv<DWIDTH> > sched_paremeter;
	sc_signal<sc_bv<DWIDTH> > sched_return;
	sc_signal<sc_bv<6> > sched_status;
	sc_signal<sc_bv<1> > sched_interrupt;
	sc_signal<sc_uint<64> > sched_debug_data;
	sc_signal<bool> sched_debug_trig;

	SC_CTOR(Toplevel_Simulation_SystemC_HL) :
		scheduler("Scheduler"),
		testbench("Testbench"),
		clk_period(30, SC_NS), clk("clk", clk_period) {

		scheduler.clk_in(clk);
		scheduler.rst_in(rst);
		scheduler.command_in(sched_command);
		scheduler.priority_in(sched_priority);
		scheduler.paremeter_in(sched_paremeter);
		scheduler.return_out(sched_return);
		scheduler.status_out(sched_status);
		scheduler.interrupt_out(sched_interrupt);
		//scheduler.trigger_out(sched_debug_trig);
		//scheduler.data_out(sched_debug_data);

		testbench.clk_in(clk);
		testbench.rst_in(rst);
		testbench.rst_out(rst);
		testbench.command_out(sched_command);
		testbench.paremeter_out(sched_paremeter);
		testbench.priority_out(sched_priority);
		testbench.return_in(sched_return);
		testbench.status_in(sched_status);
		testbench.interrupt_in(sched_interrupt);
		testbench.debug_trig_in(sched_debug_trig);
		testbench.debug_data_in(sched_debug_data);
	}
};

SC_MODULE(Toplevel_Simulation_SystemC_HL_Adapted) {

	Scheduler_HL_Adapted<MAX_THREADS, DWIDTH> scheduler;

	Testbench<MAX_THREADS, DWIDTH> testbench;

	sc_time clk_period;
	sc_clock clk;

	sc_signal<bool> rst;
	sc_signal<sc_bv<4> > sched_command;
	sc_signal<sc_bv<16> > sched_priority;
	sc_signal<sc_bv<DWIDTH> > sched_paremeter;
	sc_signal<sc_bv<DWIDTH> > sched_return;
	sc_signal<sc_bv<6> > sched_status;
	sc_signal<sc_bv<1> > sched_interrupt;
	sc_signal<sc_uint<64> > sched_debug_data;
	sc_signal<bool> sched_debug_trig;

	SC_CTOR(Toplevel_Simulation_SystemC_HL_Adapted) :
		scheduler("Scheduler"),
		testbench("Testbench"),
		clk_period(30, SC_NS), clk("clk", clk_period) {

		scheduler.clk_in(clk);
		scheduler.rst_in(rst);
		scheduler.command_in(sched_command);
		scheduler.priority_in(sched_priority);
		scheduler.paremeter_in(sched_paremeter);
		scheduler.return_out(sched_return);
		scheduler.status_out(sched_status);
		scheduler.interrupt_out(sched_interrupt);
		scheduler.trigger_out(sched_debug_trig);
		scheduler.data_out(sched_debug_data);

		testbench.clk_in(clk);
		testbench.rst_in(rst);
		testbench.rst_out(rst);
		testbench.command_out(sched_command);
		testbench.paremeter_out(sched_paremeter);
		testbench.priority_out(sched_priority);
		testbench.return_in(sched_return);
		testbench.status_in(sched_status);
		testbench.interrupt_in(sched_interrupt);
		testbench.debug_trig_in(sched_debug_trig);
		testbench.debug_data_in(sched_debug_data);
	}
};

SC_MODULE(Toplevel_Simulation_SystemC_HL_Hand_Codded) {

	Scheduler_HL_Debugged<MAX_THREADS, DWIDTH> scheduler;

	Testbench<MAX_THREADS, DWIDTH> testbench;

	sc_time clk_period;
	sc_clock clk;

	sc_signal<bool> rst;
	sc_signal<sc_bv<4> > sched_command;
	sc_signal<sc_bv<16> > sched_priority;
	sc_signal<sc_bv<DWIDTH> > sched_paremeter;
	sc_signal<sc_bv<DWIDTH> > sched_return;
	sc_signal<sc_bv<6> > sched_status;
	sc_signal<sc_bv<1> > sched_interrupt;
	sc_signal<sc_uint<64> > sched_debug_data;
	sc_signal<bool> sched_debug_trig;

	SC_CTOR(Toplevel_Simulation_SystemC_HL_Hand_Codded) :
		scheduler("Scheduler"),
		testbench("Testbench"),
		clk_period(30, SC_NS), clk("clk", clk_period) {

		scheduler.clk_in(clk);
		scheduler.rst_in(rst);
		scheduler.command_in(sched_command);
		scheduler.priority_in(sched_priority);
		scheduler.paremeter_in(sched_paremeter);
		scheduler.return_out(sched_return);
		scheduler.status_out(sched_status);
		scheduler.interrupt_out(sched_interrupt);
		scheduler.trigger_out(sched_debug_trig);
		scheduler.data_out(sched_debug_data);

		testbench.clk_in(clk);
		testbench.rst_in(rst);
		testbench.rst_out(rst);
		testbench.command_out(sched_command);
		testbench.paremeter_out(sched_paremeter);
		testbench.priority_out(sched_priority);
		testbench.return_in(sched_return);
		testbench.status_in(sched_status);
		testbench.interrupt_in(sched_interrupt);
		testbench.debug_trig_in(sched_debug_trig);
		testbench.debug_data_in(sched_debug_data);
	}
};

#ifdef MTI_SYSTEMC
#include "../modelsim/priority_scheduler_sc.h"

SC_MODULE(Toplevel_Simulation_VHDL) {
	PriorityScheduler_sc scheduler;

	Testbench<MAX_THREADS, DWIDTH> testbench;

	sc_time clk_period;
	sc_clock clk;

	sc_signal<bool> rst;
	sc_signal<sc_bv<4> > sched_command;
	sc_signal<sc_bv<16> > sched_priority;
	sc_signal<sc_bv<DWIDTH> > sched_paremeter;
	sc_signal<sc_bv<DWIDTH> > sched_return;
	sc_signal<sc_bv<6> > sched_status;
	sc_signal<sc_bv<1> > sched_interrupt;

	sc_signal<sc_logic> p_clk;
	sc_signal<sc_logic> p_reset;
	sc_signal<sc_lv<4> > p_command;
	sc_signal<sc_lv<16> > p_priority;
	sc_signal<sc_lv<32> > p_parameter;
	sc_signal<sc_lv<32> > p_return;
	sc_signal<sc_lv<6> > p_status;
	sc_signal<sc_logic> p_interrupt;

	sc_signal<sc_uint<64> > debug_data;
	sc_signal<bool> debug_trig;

	SC_CTOR(Toplevel_Simulation_VHDL) :
		scheduler("Scheduler", "PriorityScheduler_sc"),
		testbench("Testbench"),
		clk_period(30, SC_NS), clk("clk", clk_period) {

		testbench.clk_in(clk);
		testbench.rst_in(rst);
		testbench.rst_out(rst);
		testbench.command_out(sched_command);
		testbench.paremeter_out(sched_paremeter);
		testbench.priority_out(sched_priority);
		testbench.return_in(sched_return);
		testbench.status_in(sched_status);
		testbench.interrupt_in(sched_interrupt);
		testbench.debug_data_in(debug_data);
		testbench.debug_trig_in(debug_trig);

		scheduler.p_clk(p_clk);
		scheduler.p_reset(p_reset);
		scheduler.p_command(p_command);
		scheduler.p_priority(p_priority);
		scheduler.p_parameter(p_parameter);
		scheduler.p_return(p_return);
		scheduler.p_status(p_status);
		scheduler.p_interrupt(p_interrupt);

		SC_METHOD(convert_signals);
		sensitive << clk << rst << sched_command << sched_paremeter
				  << sched_priority << p_return << p_status
				  << p_interrupt;

	}

	void convert_signals(){
		p_clk.write(bool_to_logic(clk.read()));
		p_reset.write(bool_to_logic(rst.read()));
		p_command.write(sched_command.read());
		p_priority.write(sched_priority.read());
		p_parameter.write(sched_paremeter.read());
		sched_return.write(lv_to_bv(p_return.read()));
		sched_status.write(lv_to_bv(p_status.read()));
		sched_interrupt.write(logic_to_bv1(p_interrupt.read()));
	}

	sc_logic bool_to_logic(bool val){ return val ? sc_logic_1 : sc_logic_0; }

	sc_bv<1> logic_to_bv1(sc_logic val) {
		sc_bv<1> aux;
		if (val == sc_logic_1) aux = 1; else aux = 0;
		return aux;
	}

	template<int size>
	sc_bv<size> lv_to_bv(sc_lv<size> val) {
		sc_bv<size> aux;
		for (int i = 0; i < size; ++i) {
			if (val[i] == sc_logic_1) aux[i] = 1; else aux[i] = 0;
		}
		return aux;
	}

};
#endif

#ifdef MTI_SYSTEMC
#ifdef SYNTHESIS

#include "../modelsim/scheduler.h"
SC_MODULE(Toplevel_Simulation_Synthesis) {
	Scheduler scheduler;

	Testbench<MAX_THREADS, DWIDTH> testbench;

	sc_time clk_period;
	sc_clock clk;

	sc_signal<bool> rst;
	sc_signal<sc_bv<4> > sched_command;
	sc_signal<sc_bv<16> > sched_priority;
	sc_signal<sc_bv<DWIDTH> > sched_paremeter;
	sc_signal<sc_bv<DWIDTH> > sched_return;
	sc_signal<sc_bv<6> > sched_status;
	sc_signal<sc_bv<1> > sched_interrupt;

	sc_signal<sc_logic> p_clk;
	sc_signal<sc_logic> p_reset;

	SC_CTOR(Toplevel_Simulation_Synthesis) :
		scheduler("Scheduler", "Scheduler"),
		testbench("Testbench"),
		clk_period(30, SC_NS), clk("clk", clk_period) {

		testbench.clk_in(clk);
		testbench.rst_in(rst);
		testbench.rst_out(rst);
		testbench.command_out(sched_command);
		testbench.paremeter_out(sched_paremeter);
		testbench.priority_out(sched_priority);
		testbench.return_in(sched_return);
		testbench.status_in(sched_status);
		testbench.interrupt_in(sched_interrupt);

		scheduler.clk_in(p_clk);
		scheduler.rst_in(p_reset);
		scheduler.command_in(sched_command);
		scheduler.priority_in(sched_priority);
		scheduler.paremeter_in(sched_paremeter);
		scheduler.return_out(sched_return);
		scheduler.status_out(sched_status);
		scheduler.interrupt_out(sched_interrupt);

		SC_METHOD(convert_signals);
		sensitive << clk << rst;

	}

	void convert_signals(){
		p_clk.write(bool_to_logic(clk.read()));
		p_reset.write(bool_to_logic(rst.read()));
	}

	sc_logic bool_to_logic(bool val){ return val ? sc_logic_1 : sc_logic_0; }

	sc_bv<1> logic_to_bv1(sc_logic val) {
		sc_bv<1> aux;
		if (val == sc_logic_1) aux = 1; else aux = 0;
		return aux;
	}

	template<int size>
	sc_bv<size> lv_to_bv(sc_lv<size> val) {
		sc_bv<size> aux;
		for (int i = 0; i < size; ++i) {
			if (val[i] == sc_logic_1) aux[i] = 1; else aux[i] = 0;
		}
		return aux;
	}

};
#endif
#endif


#ifdef MTI_SYSTEMC

SC_MODULE_EXPORT(Toplevel_Simulation_SystemC);
SC_MODULE_EXPORT(Toplevel_Simulation_SystemC_HL);
SC_MODULE_EXPORT(Toplevel_Simulation_SystemC_HL_Adapted);
SC_MODULE_EXPORT(Toplevel_Simulation_VHDL);
#ifdef SYNTHESIS
SC_MODULE_EXPORT(Toplevel_Simulation_Synthesis);
#endif

#else

template<class T>
void sc_trace_array(sc_trace_file *tf, sc_signal<T> *v, const std::string& name, int len) {
	char stbuf[20];
	for (int i = 0; i < len; i++) {
		sprintf(stbuf, "(%d)", i);
		sc_trace(tf, v[i], name + stbuf);
	}
}


int sc_main (int argc, char *argv[]){

	//Toplevel_Simulation_SystemC simulation("Simulation");
	//Toplevel_Simulation_SystemC_HL simulation("Simulation");
	Toplevel_Simulation_SystemC_HL_Hand_Codded simulation("Simulation");

	sc_trace_file *trcf = sc_create_vcd_trace_file("trace");
	if (trcf) {
		sc_trace(trcf, simulation.clk, "clk");
		sc_trace(trcf, simulation.rst, "rst");
		sc_trace(trcf, simulation.sched_command, "cmd");
		sc_trace(trcf, simulation.sched_paremeter, "parameter");
		sc_trace(trcf, simulation.sched_priority, "priority");
		sc_trace(trcf, simulation.sched_return, "return");
		sc_trace(trcf, simulation.sched_status, "status");
		sc_trace(trcf, simulation.sched_interrupt, "interrupt");
		trcf->set_time_unit(-15);
	} else {
		std::cout << "Deu pau no trace" << std::endl;
	}

	sc_start();

	if (trcf)
		sc_close_vcd_trace_file(trcf);

	return 0;
}

#endif

