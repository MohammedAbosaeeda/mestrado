
#ifndef epossoc_rsp_H_
#define epossoc_rsp_H_

#include <systemc.h>
#include <mach/common/rtsnoc_router/rtsnoc_router.h>
#include <mach/epossoc/rtsnoc/cpu_io_node.h>
#include <framework/catapult/top_level/add/rtl.h>
//#include <framework/catapult/top_level/rsp_eth/rtl.h>
/*#include <framework/catapult/top_level/rsp_aes/rtl.h>
#include <framework/catapult/top_level/rsp_adpcm/rtl.h>
#include <framework/catapult/top_level/rsp_dtmf/rtl.h>*/



SC_MODULE(RSP) {

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

    typedef Add_Node<ROUTER_X,ROUTER_Y, Implementation::Resource_Table<Implementation::Add,0>::HW_LOCAL,
                     NET_DATA_WIDTH,NET_SIZE_X_LOG2,NET_SIZE_Y_LOG2,
                     RMI_MSG_SIZE,
                     IID_SIZE, Implementation::Traits<Implementation::Add>::n_ids> add_node_t;

    /*typedef RSP_AES_Node_cycle_acc<ROUTER_X,ROUTER_Y, Implementation::Resource_Table<Implementation::RSP_AES,0>::HW_LOCAL,
                     NET_DATA_WIDTH,NET_SIZE_X_LOG2,NET_SIZE_Y_LOG2,
                     RMI_MSG_SIZE,
                     IID_SIZE, Implementation::Traits<Implementation::RSP_AES>::n_ids> rsp_aes_node_t;

    typedef RSP_ADPCM_Node_cycle_acc<ROUTER_X,ROUTER_Y, Implementation::Resource_Table<Implementation::RSP_ADPCM,0>::HW_LOCAL,
                     NET_DATA_WIDTH,NET_SIZE_X_LOG2,NET_SIZE_Y_LOG2,
                     RMI_MSG_SIZE,
                     IID_SIZE, Implementation::Traits<Implementation::RSP_ADPCM>::n_ids> rsp_adpcm_node_t;

    typedef RSP_DTMF_Node_cycle_acc<ROUTER_X,ROUTER_Y, Implementation::Resource_Table<Implementation::RSP_DTMF,0>::HW_LOCAL,
                     NET_DATA_WIDTH,NET_SIZE_X_LOG2,NET_SIZE_Y_LOG2,
                     RMI_MSG_SIZE,
                     IID_SIZE, Implementation::Traits<Implementation::RSP_DTMF>::n_ids> rsp_dtmf_node_t;*/

    //Modules

	rtsnoc_router_t		router;

	cpu_io_node_t		node_proc_io;

	add_node_t     node_add;
	/*rsp_aes_node_t     node_rsp_aes;
	rsp_adpcm_node_t   node_rsp_adpcm;
	rsp_dtmf_node_t   node_rsp_dtmf;*/

	//Internal signals
	sc_fifo<sc_biguint<NET_BUS_SIZE> > noc_din[ROUTER_N_PORTS];
	sc_fifo<sc_biguint<NET_BUS_SIZE> > noc_dout[ROUTER_N_PORTS];

	sc_signal<unsigned int> iid_add[Implementation::Traits<Implementation::Add>::n_ids];
	/*sc_signal<unsigned int> iid_rsp_aes[Implementation::Traits<Implementation::RSP_AES>::n_ids];
	sc_signal<unsigned int> iid_rsp_adpcm[Implementation::Traits<Implementation::RSP_ADPCM>::n_ids];
	sc_signal<unsigned int> iid_rsp_dtmf[Implementation::Traits<Implementation::RSP_DTMF>::n_ids];*/

	SC_CTOR(RSP)
		:router("rtsnoc_router")
		 , node_proc_io("noc_proc_io")
		 , node_add("node_add")
		 /*, node_rsp_aes("node_rsp_aes")
		 , node_rsp_adpcm("node_rsp_adpcm")
		 , node_rsp_dtmf("node_rsp_dtmf")*/
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

        noc_connect<Implementation::Add, add_node_t>(node_add, iid_add);
        /*noc_connect<Implementation::RSP_AES, rsp_aes_node_t>(node_rsp_aes, iid_rsp_aes);
        noc_connect<Implementation::RSP_ADPCM, rsp_adpcm_node_t>(node_rsp_adpcm, iid_rsp_adpcm);
        noc_connect<Implementation::RSP_DTMF, rsp_dtmf_node_t>(node_rsp_dtmf, iid_rsp_dtmf);*/

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
