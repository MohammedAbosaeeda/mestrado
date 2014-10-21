/*
 * main.cpp
 *
 *  Created on: 28/02/2010
 *      Author: tiago
 */
#define SC_USE_STD_STRING
#define SYSTEMC_SIM
#define HIGH_LEVEL_SYNTHESIS

#include <systemc.h>
#include <iostream>
#include <common/debugs.h>
#include "../rsp.h"

SC_MODULE(Virtual_Platform) {

	RSP soc;

	sc_signal<bool> rst;

	sc_signal<sc_uint<32> > gpio_in;
	sc_signal<sc_uint<32> > gpio_out;

	sc_signal<bool> ext_int[8];

	long int real_time_start;

	sc_trace_file *fp;

	SC_CTOR(Virtual_Platform)
		:soc("epos_soc"),
		 rst("rst")
	{
		soc.rst(rst);
		soc.gpio_in(gpio_in);
		soc.gpio_out(gpio_out);
		for (int i = 0; i < 8; ++i) soc.ext_int[i](ext_int[i]);

		SC_THREAD(start_plat);

		SC_THREAD(print_time);

		//SC_METHOD(dump_gpio); sensitive << gpio_out;

		rst = true;
		for (int i = 0; i < 8; ++i) ext_int[i] = false;
		//gpio_in = 0xFFFFFFFF;

		fp = 0;

#ifdef dump_signals_vcd
		dump_signals();
#endif
	}

	void dump_gpio(){
	    std::cout << "## INFO: GPIO_OUT = " << gpio_out.read().to_string(SC_HEX_US) << "\n";
	    std::cout << "##       Simulation time: " << sc_time_stamp().to_seconds()
	                  << "s | Real time: " << (Global::get_real_time_ms() - real_time_start)/1000.0 << "s\n";
	}

	void start_plat();

	void print_time();

#ifdef dump_signals_vcd
	void dump_signals();
#endif

};

#ifdef dump_signals_vcd
void Virtual_Platform::dump_signals(){

	fp=sc_create_vcd_trace_file("virtual_platform");

	sc_trace(fp, rst, "rst");

#ifdef dump_signals_vcd_internal
	sc_trace(fp, soc.node_proc_io.cpu.pc_data->pc_current, "cpu_current_pc");
	sc_trace(fp, soc.node_proc_io.cpu.pc_data->pc_current_on_ds, "cpu_current_on_ds");
	sc_trace(fp, soc.node_proc_io.cpu.pc_data->opcode, "cpu_current_opcode");
	sc_trace(fp, soc.node_proc_io.cpu.pc_data->pc_next, "cpu_next_pc");
	sc_trace(fp, soc.node_proc_io.cpu.cpu_status->epc, "cpu_info_epc");
	sc_trace(fp, soc.node_proc_io.cpu.cpu_status->skip, "cpu_info_skip");
	sc_trace(fp, soc.node_proc_io.cpu.cpu_status->skip_next, "cpu_info_skip_next");
#endif

	sc_trace(fp, soc.node_proc_io.irq, "pic_irq");
	for (int i = 0; i < 32; ++i) {
		std::ostringstream data;
		data << "irq(" << i << ")";
		sc_trace(fp, soc.node_proc_io.m_pic.irq_in[i], data.str());
	}
	sc_trace(fp, soc.node_proc_io.m_pic.mask, "irq_mask");
	sc_trace(fp, soc.node_proc_io.m_pic.pending, "irq_pending");

	sc_trace(fp, soc.node_proc_io.get_tsc_sig(), "tsc");

	sc_trace(fp, soc.node_proc_io.m_noc_proxy.din_s, "proxy_din");
	sc_trace(fp, soc.node_proc_io.m_noc_proxy.dout_s, "proxy_dout");
	sc_trace(fp, soc.node_proc_io.m_noc_proxy.wr, "proxy_wr");
	sc_trace(fp, soc.node_proc_io.m_noc_proxy.rd, "proxy_rd");
	sc_trace(fp, soc.node_proc_io.m_noc_proxy.wait, "proxy_wait");
	sc_trace(fp, soc.node_proc_io.m_noc_proxy.nd, "proxy_nd");

	sc_trace(fp, soc.node_rsp_eth.adapted.clk, "caller_clk");
	sc_trace(fp, soc.node_rsp_eth.din_s, "caller_din");
	sc_trace(fp, soc.node_rsp_eth.dout_s, "caller_dout");
	sc_trace(fp, soc.node_rsp_eth.wr, "caller_wr");
	sc_trace(fp, soc.node_rsp_eth.rd, "caller_rd");
	sc_trace(fp, soc.node_rsp_eth.wait, "caller_wait");
	sc_trace(fp, soc.node_rsp_eth.nd, "caller_nd");
	sc_trace(fp, soc.node_rsp_eth.adapted.rx_ch_rsc_z, "caller_rx_ch_z");
	sc_trace(fp, soc.node_rsp_eth.adapted.rx_ch_rsc_vz, "caller_rx_ch_vz");
	sc_trace(fp, soc.node_rsp_eth.adapted.rx_ch_rsc_lz, "caller_rx_ch_lz");
	sc_trace(fp, soc.node_rsp_eth.adapted.tx_ch_rsc_z, "caller_tx_ch_z");
	sc_trace(fp, soc.node_rsp_eth.adapted.tx_ch_rsc_vz, "caller_tx_ch_vz");
	sc_trace(fp, soc.node_rsp_eth.adapted.tx_ch_rsc_lz, "caller_tx_ch_lz");

}
#endif

void Virtual_Platform::start_plat(){

   rst = true;

   Global::delay_cycles<0>(this, 10);

   rst = false;

   std::cout << "## INFO: System ready\n";
}

void Virtual_Platform::print_time(){
	while(true){
		sc_time wait_time(100,SC_MS);
		sc_module::wait(wait_time);

		std::cout << "## INFO: Simulation time: " << sc_time_stamp().to_seconds()
				  << "s | Real time: " << (Global::get_real_time_ms() - real_time_start)/1000.0 << "s\n";
	}
}


int sc_main (int argc, char *argv[]){

	sc_report_handler::set_actions("/IEEE_Std_1666/deprecated", SC_DO_NOTHING);

	Virtual_Platform plat("virtual_platform");

	plat.real_time_start = Global::get_real_time_ms();

	sc_start();

	if(plat.fp){
	    sc_close_vcd_trace_file(plat.fp);
	}

	return 0;
}
