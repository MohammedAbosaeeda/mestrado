
#ifndef unifiedtests_H_
#define unifiedtests_H_


#include <systemc.h>
#include <mach/common/rtsnoc_router/rtsnoc_router.h>
#include <mach/epossoc/rtsnoc/cpu_io_node.h>
#include <framework/catapult/top_level/dummy_caller/rtl.h>
#include <framework/catapult/top_level/dummy_callee/rtl.h>


SC_MODULE(Unified_Tests) {

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
	    NODE_PROC_IO_ADDR_MANAGER  = Implementation::NOC_SW_NODE_MANAGER
	};

	typedef cpu_io_node<ROUTER_X,ROUTER_Y,
				NODE_PROC_IO_ADDR_GENERIC,
				NODE_PROC_IO_ADDR_MANAGER,
				NET_DATA_WIDTH,NET_SIZE_X_LOG2,NET_SIZE_Y_LOG2> cpu_io_node_t;


    typedef Dummy_Callee_Node<ROUTER_X,ROUTER_Y, Implementation::Resource_Table<Implementation::Dummy_Callee,0>::HW_LOCAL,
                    NET_DATA_WIDTH,NET_SIZE_X_LOG2,NET_SIZE_Y_LOG2,
                    RMI_MSG_SIZE,
                    IID_SIZE, 1> dummy_callee_node_t;

    typedef Dummy_Caller_Node<ROUTER_X,ROUTER_Y, Implementation::Resource_Table<Implementation::Dummy_Caller,0>::HW_LOCAL,
                    NET_DATA_WIDTH,NET_SIZE_X_LOG2,NET_SIZE_Y_LOG2,
                    RMI_MSG_SIZE,
                    IID_SIZE, 2> dummy_caller_node_t;

	//Modules

	rtsnoc_router_t		router;

	cpu_io_node_t		node_proc_io;

    dummy_callee_node_t node_callee_0;
    dummy_caller_node_t node_caller_0;

	//Internal signals
	sc_fifo<sc_biguint<NET_BUS_SIZE> > noc_din[ROUTER_N_PORTS];
	sc_fifo<sc_biguint<NET_BUS_SIZE> > noc_dout[ROUTER_N_PORTS];

	sc_signal<unsigned int> iid_callee_0[Implementation::Traits<Implementation::Dummy_Callee>::n_ids];
	sc_signal<unsigned int> iid_caller_0[Implementation::Traits<Implementation::Dummy_Caller>::n_ids];

	SC_CTOR(Unified_Tests)
		:router("rtsnoc_router")
		 , node_proc_io("noc_proc_io")
         , node_callee_0("node_callee_0")
         , node_caller_0("node_caller_0")
	{

		for (int i = 0; i < ROUTER_N_PORTS; ++i) {
			router.din[i](noc_din[i]);
			router.dout[i](noc_dout[i]);
		}
		node_proc_io.rst(rst);
		node_proc_io.gpio_in(gpio_in);
		node_proc_io.gpio_out(gpio_out);
		for (int i = 0; i < 8; ++i) node_proc_io.ext_int[i](ext_int[i]);

		node_proc_io.noc_din(noc_din[NODE_PROC_IO_ADDR_GENERIC]);
		node_proc_io.noc_dout(noc_dout[NODE_PROC_IO_ADDR_GENERIC]);

		node_proc_io.cmp_manager_din(noc_din[NODE_PROC_IO_ADDR_MANAGER]);
        node_proc_io.cmp_manager_dout(noc_dout[NODE_PROC_IO_ADDR_MANAGER]);

		noc_connect<Implementation::Dummy_Callee, dummy_callee_node_t>(node_callee_0, iid_callee_0);

        noc_connect<Implementation::Dummy_Caller, dummy_caller_node_t>(node_caller_0, iid_caller_0);
	}

	template<class Unif_T, class T>
	void noc_connect(T &component, sc_signal<unsigned int> *iid){
	    int CALL_PORT = Implementation::Resource_Table<Unif_T,0>::HW_LOCAL;

		component.rst(rst);
		component.din(noc_din[CALL_PORT]);
		component.dout(noc_dout[CALL_PORT]);

		for (unsigned int i = 0; i < Implementation::Traits<Unif_T>::n_ids; ++i){ component.iid[i](iid[i]); iid[i] = Implementation::Resource_Table<Unif_T,0>::IID[i]; }

	}

	unsigned long int get_tsc(){ return node_proc_io.get_tsc();}

};




#endif /* VISTUAL_PLATFORM_H_ */
