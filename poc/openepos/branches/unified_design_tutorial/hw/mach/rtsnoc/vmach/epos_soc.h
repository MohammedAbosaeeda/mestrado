/*
 * vistual_platform.h
 *
 *  Created on: Jan 13, 2012
 *      Author: tiago
 */

#ifndef VIRTUAL_PLATFORM_H_
#define VIRTUAL_PLATFORM_H_

#include <systemc.h>
#include <mach/common/rtsnoc_router/rtsnoc_router.h>
#include "proc_io_node.h"
#include <components/hls/add/rtl.h>
#include <components/hls/mult/rtl.h>


SC_MODULE(epos_soc) {

	//Ports
	sc_in<bool> rst;

	sc_in<sc_uint<32> > gpio_in;
	sc_out<sc_uint<32> > gpio_out;
	sc_in<bool>	ext_int[8];


	//Constants
	enum{
		NET_SIZE_X		= 1,
		NET_SIZE_Y		= 1,
		NET_SIZE_X_LOG2 = 1, // it should be "integer(ceil(log2(real(NET_SIZE_X))))" when NET_SIZE_X >= 2
		NET_SIZE_Y_LOG2	= 1, // it should be "integer(ceil(log2(real(NET_SIZE_Y))))" when NET_SIZE_Y >= 2
		ROUTER_X		= 0,
		ROUTER_Y		= 0,
		NET_DATA_WIDTH	= 56,
		ROUTER_N_PORTS	= 8,
		NET_BUS_SIZE 	= NET_DATA_WIDTH+(2*NET_SIZE_X_LOG2)+(2*NET_SIZE_Y_LOG2)+6
	};

	enum{
	    IID_SIZE = 8
	};

	typedef rtsnoc_router_fast<ROUTER_X,ROUTER_Y,NET_DATA_WIDTH,NET_SIZE_X_LOG2,NET_SIZE_Y_LOG2> rtsnoc_router_t;

	enum{
		//NoC node addressess
	    NODE_PROC_IO_ADDR  = Unified::NOC_SW_NODE,

	};

	typedef proc_io_node<ROUTER_X,ROUTER_Y,
				NODE_PROC_IO_ADDR,
				NET_DATA_WIDTH,NET_SIZE_X_LOG2,NET_SIZE_Y_LOG2> proc_io_node_t;

	typedef add_node_fast<ROUTER_X,ROUTER_Y, Unified::Resource_Table<Unified::Add,0>::HW_LOCAL,
					NET_DATA_WIDTH,NET_SIZE_X_LOG2,NET_SIZE_Y_LOG2,
					IID_SIZE, 1> add_node_t0;

	typedef mult_node_fast<ROUTER_X,ROUTER_Y, Unified::Resource_Table<Unified::Mult,0>::HW_LOCAL,
					 NET_DATA_WIDTH,NET_SIZE_X_LOG2,NET_SIZE_Y_LOG2,
					 IID_SIZE, 2> mult_node_t;

	//Modules

	rtsnoc_router_t		router;

	proc_io_node_t		node_proc_io;

	add_node_t0			node_add_0;
	mult_node_t			node_mult;

	//Internal signals
	sc_fifo<sc_biguint<NET_BUS_SIZE> > noc_din[ROUTER_N_PORTS];
	sc_fifo<sc_biguint<NET_BUS_SIZE> > noc_dout[ROUTER_N_PORTS];

	sc_signal<unsigned int>	iid_add_0[Unified::Traits<Unified::Add>::n_ids];
	sc_signal<unsigned int>	iid_mult_0[Unified::Traits<Unified::Mult>::n_ids];

	SC_CTOR(epos_soc)
		:router("rtsnoc_router")
		 , node_proc_io("noc_proc_io")
		 , node_add_0("noc_add_0")
		 , node_mult("noc_mult")
	{

		for (int i = 0; i < ROUTER_N_PORTS; ++i) {
			router.din[i](noc_din[i]);
			router.dout[i](noc_dout[i]);
		}
		node_proc_io.rst(rst);
		node_proc_io.gpio_in(gpio_in);
		node_proc_io.gpio_out(gpio_out);
		for (int i = 0; i < 8; ++i) node_proc_io.ext_int[i](ext_int[i]);
		node_proc_io.noc_din(noc_din[NODE_PROC_IO_ADDR]);
		node_proc_io.noc_dout(noc_dout[NODE_PROC_IO_ADDR]);

		noc_connect<Unified::Add, add_node_t0>(node_add_0, iid_add_0);

		noc_connect<Unified::Mult, mult_node_t>(node_mult, iid_mult_0);

	}

	template<class Unif_T, class T>
	void noc_connect(T &component, sc_signal<unsigned int> *iid){
	    int CALL_PORT = Unified::Resource_Table<Unif_T,0>::HW_LOCAL;

		component.rst(rst);
		component.din[T::CALL_PORT](noc_din[CALL_PORT]);
		component.dout[T::CALL_PORT](noc_dout[CALL_PORT]);

		for (unsigned int i = 0; i < Unified::Traits<Unif_T>::n_ids; ++i){ component.iid[i](iid[i]); iid[i] = Unified::Resource_Table<Unif_T,0>::IID[i]; }

		component.tsc(node_proc_io.get_tsc_sig());
	}

	bool load_bootloader(const char* file_name){ return node_proc_io.load_bootloader(file_name); }

	unsigned long int get_tsc(){ return node_proc_io.get_tsc();}

};




#endif /* VISTUAL_PLATFORM_H_ */
