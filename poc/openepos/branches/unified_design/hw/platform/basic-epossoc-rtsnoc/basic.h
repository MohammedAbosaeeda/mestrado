
#ifndef basic_H_
#define basic_H_


#include <systemc.h>
#include <mach/common/rtsnoc_router/rtsnoc_router.h>
#include <mach/epossoc/rtsnoc/cpu_io_node.h>
#include <mach/common/rtsnoc_router/rtsnoc_echo.h>


SC_MODULE(Basic) {

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
		NET_BUS_SIZE 	= NET_DATA_WIDTH+(2*NET_SIZE_X_LOG2)+(2*NET_SIZE_Y_LOG2)+6,
		RMI_MSG_SIZE    = 80,
	};

	enum{
	    IID_SIZE = 8
	};

	typedef rtsnoc_router<ROUTER_X,ROUTER_Y,NET_DATA_WIDTH,NET_SIZE_X_LOG2,NET_SIZE_Y_LOG2> rtsnoc_router_t;

	enum{
		//NoC node addressess
	    NODE_PROC_IO_ADDR_GENERIC  = Implementation::NOC_SW_NODE_GENERIC,
	    NODE_PROC_IO_ADDR_MANAGER  = Implementation::NOC_SW_NODE_MANAGER,
	    NODE_ECHO_P0        = rtsnoc_router_t::ROUTER_WW,
	    NODE_ECHO_P1        = rtsnoc_router_t::ROUTER_SS
	};

	typedef cpu_io_node<ROUTER_X,ROUTER_Y,
				NODE_PROC_IO_ADDR_GENERIC,
				NODE_PROC_IO_ADDR_MANAGER,
				NET_DATA_WIDTH,NET_SIZE_X_LOG2,NET_SIZE_Y_LOG2> cpu_io_node_t;


	typedef rtsnoc_echo_fast<ROUTER_X,ROUTER_Y,
				NODE_ECHO_P0,
				NODE_ECHO_P1,
				NET_DATA_WIDTH,NET_SIZE_X_LOG2,NET_SIZE_Y_LOG2> rtsnoc_echo_t;
	//Modules

	rtsnoc_router_t		router;

	proc_io_node_t		node_proc_io;

	rtsnoc_echo_t		node_echo;

	//Internal signals
	sc_fifo<sc_biguint<NET_BUS_SIZE> > noc_din[ROUTER_N_PORTS];
	sc_fifo<sc_biguint<NET_BUS_SIZE> > noc_dout[ROUTER_N_PORTS];

	SC_CTOR(Basic)
		:router("rtsnoc_router")
		 , node_proc_io("noc_proc_io")
		 , node_echo("noc_echo")
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

		node_echo.rst(rst);
		node_echo.din[0](noc_din[NODE_ECHO_P0]);
		node_echo.dout[0](noc_dout[NODE_ECHO_P0]);
		node_echo.din[1](noc_din[NODE_ECHO_P1]);
		node_echo.dout[1](noc_dout[NODE_ECHO_P1]);
	}

	unsigned long int get_tsc(){ return node_proc_io.get_tsc();}

};




#endif /* VISTUAL_PLATFORM_H_ */
