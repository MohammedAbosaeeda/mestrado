/*
 * noc_to_catapult.h
 *
 *  Created on: Jan 25, 2012
 *      Author: tiago
 */

#ifndef NOC_TO_CATAPULT_H_
#define NOC_TO_CATAPULT_H_

#include <systemc.h>
#include <iostream>

#include <components/src/system/types_hw.h>

#include <components/src/system/resource_table.h>
using Unified::PHY_Table;


///////////////////////////////

template<
    unsigned int X,
    unsigned int Y,
    unsigned int LOCAL_ADDR,
    unsigned int SIZE_DATA,
    unsigned int SIZE_X,
    unsigned int SIZE_Y
>
SC_MODULE(rtsnoc_to_achannel_auto){

    enum{
        BUS_SIZE = SIZE_DATA+(2*SIZE_X)+(2*SIZE_Y)+6,
    };

    sc_in<bool>     clk;
    sc_in<bool>     rst;

    sc_out<sc_biguint<BUS_SIZE> > din;
    sc_in<sc_biguint<BUS_SIZE> > dout;
    sc_out<bool> wr;
    sc_out<bool> rd;
    sc_in<bool> wait;
    sc_in<bool> nd;

    sc_out< sc_lv<SIZE_DATA> > rx_ch_z;
    sc_out< sc_logic > rx_ch_vz;
    sc_in< sc_logic > rx_ch_lz;

    sc_in< sc_lv<SIZE_DATA> > tx_ch_z;
    sc_out< sc_logic > tx_ch_vz;
    sc_in< sc_logic > tx_ch_lz;

    sc_in<unsigned long int> tsc;

public:
    SC_CTOR(rtsnoc_to_achannel_auto){
        SC_CTHREAD(rx_process, clk.pos());
        reset_signal_is(rst, true);
        SC_CTHREAD(tx_process, clk.pos());
        reset_signal_is(rst, true);

        last_rx_call_X = 0;
        last_rx_call_Y = 0;
        last_rx_call_local = 0;

        last_tx_call_X = 0;
        last_tx_call_Y = 0;
        last_tx_call_local = 0;
    }

private:
    void rx_process();
    void tx_process();

    typedef struct{
        unsigned int   msg_type;
        unsigned int   instance_id;
        unsigned int   type_id;
    } Msg_Header_Parsed;

    template<typename DATA_TYPE>
    void parse_header(Msg_Header_Parsed &header, DATA_TYPE &data){
        header.msg_type = data(39,32).to_uint();
        header.instance_id = data(47,40).to_uint();
        header.type_id = data(55,48).to_uint();
    }

    unsigned int last_rx_call_X;
    unsigned int last_rx_call_Y;
    unsigned int last_rx_call_local;

    unsigned int last_tx_call_X;
    unsigned int last_tx_call_Y;
    unsigned int last_tx_call_local;
};

template<
    unsigned int X,
    unsigned int Y,
    unsigned int LOCAL_ADDR,
    unsigned int SIZE_DATA,
    unsigned int SIZE_X,
    unsigned int SIZE_Y
>
void rtsnoc_to_achannel_auto<X,Y,LOCAL_ADDR,SIZE_DATA,SIZE_X,SIZE_Y>::tx_process(){
    tx_ch_vz = sc_logic_0;
    wr = false;
    din = 0;
    sc_module::wait();

    while(true){

        while(tx_ch_lz.read() == sc_logic_0) sc_module::wait();
        tx_ch_vz = sc_logic_1;
        sc_lv<SIZE_DATA> rx_data = tx_ch_z.read();
        sc_module::wait();
        tx_ch_vz = sc_logic_0;

        Msg_Header_Parsed header; parse_header(header, rx_data);

//#ifdef debug_catapult_nodes
//        std::cout << "##INFO NODE (" << X << "," << Y << "," << LOCAL_ADDR << ") - TX: Received " << rx_data.to_string(SC_HEX) << "\n";
//        std::cout << "\t\t msg_type=" << header.msg_type << ", iid=" << header.instance_id << ", tid=" << header.type_id << "\n";
//#endif


        sc_biguint<SIZE_DATA>   tx_data = rx_data;
        sc_biguint<3>           tx_local_orig = LOCAL_ADDR;
        sc_biguint<SIZE_Y>      tx_Y_orig = Y;
        sc_biguint<SIZE_X>      tx_X_orig = X;
        sc_biguint<3>           tx_local_dst;
        sc_biguint<SIZE_Y>      tx_Y_dst;
        sc_biguint<SIZE_X>      tx_X_dst;


        if(header.msg_type==System::MSG_TYPE_RESP){
            //FIXME RESP initiation msgs are not really needed
            std::cout << "##WARNING NODE (" << X << "," << Y << "," << LOCAL_ADDR << ") - Ignoring msg of type MSG_TYPE_RESP\n";
            sc_module::wait();
            continue;
        }
        else if(header.msg_type==System::MSG_TYPE_RESP_DATA){
            tx_local_dst = last_rx_call_local;
            tx_Y_dst = last_rx_call_Y;
            tx_X_dst = last_rx_call_X;
        }
        else if((header.msg_type==System::MSG_TYPE_CALL) || (header.msg_type==System::MSG_TYPE_CALL_DATA)){
            int idx = PHY_Table::type2IDX(header.type_id, header.instance_id);
            if(idx >= 0){
                tx_local_dst = PHY_Table::LOCAL[idx];
                tx_Y_dst = PHY_Table::Y[idx];
                tx_X_dst = PHY_Table::X[idx];

                last_tx_call_local = PHY_Table::LOCAL[idx];
                last_tx_call_Y = PHY_Table::Y[idx];
                last_tx_call_X = PHY_Table::X[idx];
            }
            else{
                std::cout << "##WARNING NODE (" << X << "," << Y << "," << LOCAL_ADDR << ") - Target TX addr not found\n";
                sc_module::wait();
                continue;
            }
        }
        else{
            std::cout << "##WARNING NODE (" << X << "," << Y << "," << LOCAL_ADDR << ") - Wrong msg - Protocol error\n";
            sc_module::wait();
            continue;
        }

        while(wait.read()) sc_module::wait();
        din = (tx_X_orig,
                tx_Y_orig,
                tx_local_orig,
                tx_X_dst,
                tx_Y_dst,
                tx_local_dst,
                tx_data);
        wr = true;

        sc_module::wait();

        wr = false;

//#ifdef debug_catapult_nodes
//        std::cout << "##INFO NODE (" << X << "," << Y << "," << LOCAL_ADDR << ") - TX: " << tx_data.to_string(SC_HEX) << " forwarded to " << "(" << tx_X_dst << "," << tx_Y_dst << "," << tx_local_dst << ")\n";
//#endif

#ifdef debug_catapult_nodes
        std::cout << "##INFO NODE (" << X << "," << Y << "," << LOCAL_ADDR << ") - TX: " << tx_data.to_string(SC_HEX) << " to " << "(" << tx_X_dst << "," << tx_Y_dst << "," << tx_local_dst << ")\n";
        std::cout << "\t\t msg_type=" << header.msg_type << ", iid=" << header.instance_id << ", tid=" << header.type_id << "\n";
        std::cout << "\t\t TSC=" << tsc.read() << "\n";
#endif

        sc_module::wait();
    }
}

template<
    unsigned int X,
    unsigned int Y,
    unsigned int LOCAL_ADDR,
    unsigned int SIZE_DATA,
    unsigned int SIZE_X,
    unsigned int SIZE_Y
>
void rtsnoc_to_achannel_auto<X,Y,LOCAL_ADDR,SIZE_DATA,SIZE_X,SIZE_Y>::rx_process(){
    rd = false;
    rx_ch_z = 0;
    rx_ch_vz = sc_logic_0;
    sc_module::wait();

    while(true){

        while(!nd.read()) sc_module::wait();

        sc_biguint<SIZE_DATA>   rx_data;
        sc_biguint<3>           rx_local_dst;
        sc_biguint<SIZE_Y>      rx_Y_dst;
        sc_biguint<SIZE_X>      rx_X_dst;
        sc_biguint<3>           rx_local_orig;
        sc_biguint<SIZE_Y>      rx_Y_orig;
        sc_biguint<SIZE_X>      rx_X_orig;
        (rx_X_orig,
         rx_Y_orig,
         rx_local_orig,
         rx_X_dst,
         rx_Y_dst,
         rx_local_dst,
         rx_data) = dout.read();

        Msg_Header_Parsed header; parse_header(header, rx_data);

#ifdef debug_catapult_nodes
        std::cout << "##INFO NODE (" << X << "," << Y << "," << LOCAL_ADDR << ") - RX: " << rx_data.to_string(SC_HEX) << " from " << "(" << rx_X_orig << "," << rx_Y_orig << "," << rx_local_orig << ")\n";
        std::cout << "\t\t msg_type=" << header.msg_type << ", iid=" << header.instance_id << ", tid=" << header.type_id << "\n";
        std::cout << "\t\t TSC=" << tsc.read() << "\n";
#endif

        sc_lv<SIZE_DATA> tx_data = rx_data;

        rd = true;
        sc_module::wait();
        rd = false;

       if(header.msg_type==System::MSG_TYPE_RESP){
            //FIXME RESP initiation msgs are not really needed
            std::cout << "##WARNING NODE (" << X << "," << Y << "," << LOCAL_ADDR << ") - Ignoring msg of type MSG_TYPE_RESP\n";
            sc_module::wait();
            continue;
       }
       else if(header.msg_type==System::MSG_TYPE_RESP_DATA){
            bool addr_match = (rx_X_orig.to_uint() == last_tx_call_X) &&
                              (rx_Y_orig.to_uint() == last_tx_call_Y) &&
                              (rx_local_orig.to_uint() == last_tx_call_local);
            if(!addr_match){
                std::cout << "##WARNING NODE (" << X << "," << Y << "," << LOCAL_ADDR << ") - RX resp addrs does not match TX call addr\n";
                sc_module::wait();
                continue;
            }
        }
        else if((header.msg_type==System::MSG_TYPE_CALL) || (header.msg_type==System::MSG_TYPE_CALL_DATA)){
            last_rx_call_X = rx_X_orig.to_uint();
            last_rx_call_Y = rx_Y_orig.to_uint();
            last_rx_call_local = rx_local_orig.to_uint();
        }
        else{
            std::cout << "##WARNING NODE (" << X << "," << Y << "," << LOCAL_ADDR << ") - Wrong msg - Protocol error\n";
            sc_module::wait();
            continue;
        }

        while(rx_ch_lz.read() == sc_logic_0) sc_module::wait();
        rx_ch_z = tx_data;
        rx_ch_vz = sc_logic_1;
        sc_module::wait();
        rx_ch_vz = sc_logic_0;

//#ifdef debug_catapult_nodes
//        std::cout << "##INFO NODE (" << X << "," << Y << "," << LOCAL_ADDR << ") - RX: " << tx_data.to_string(SC_HEX) << " forwarded\n";
//#endif

        sc_module::wait();
    }
}

////////////////////////////////

template<
	unsigned int X,
	unsigned int Y,
	unsigned int LOCAL_ADDR,
	unsigned int SIZE_DATA,
	unsigned int SIZE_X,
	unsigned int SIZE_Y
>
SC_MODULE(rtsnoc_to_achannel){

	enum{
		BUS_SIZE = SIZE_DATA+(2*SIZE_X)+(2*SIZE_Y)+6,
	};

	sc_in<bool>		clk;
	sc_in<bool>		rst;

	sc_out<sc_biguint<BUS_SIZE> > din;
	sc_in<sc_biguint<BUS_SIZE> > dout;
	sc_out<bool> wr;
	sc_out<bool> rd;
	sc_in<bool> wait;
	sc_in<bool> nd;

	sc_out< sc_lv<SIZE_DATA> > rx_ch_z;
	sc_out< sc_logic > rx_ch_vz;
	sc_in< sc_logic > rx_ch_lz;

	sc_in< sc_lv<SIZE_DATA> > tx_ch_z;
	sc_out< sc_logic > tx_ch_vz;
	sc_in< sc_logic > tx_ch_lz;

protected:
	SC_CTOR(rtsnoc_to_achannel){
		SC_CTHREAD(rx_process, clk.pos());
		reset_signal_is(rst, true);
		SC_CTHREAD(tx_process, clk.pos());
		reset_signal_is(rst, true);
	}

private:
	void rx_process();
	void tx_process();
	virtual unsigned int get_dest_x() = 0;
	virtual unsigned int get_dest_y() = 0;
	virtual unsigned int get_dest_local() = 0;
	virtual void set_dest_x(unsigned int) = 0;
	virtual void set_dest_y(unsigned int) = 0;
	virtual void set_dest_local(unsigned int) = 0;

};

template<
	unsigned int X,
	unsigned int Y,
	unsigned int LOCAL_ADDR,
	unsigned int SIZE_DATA,
	unsigned int SIZE_X,
	unsigned int SIZE_Y
>
void rtsnoc_to_achannel<X,Y,LOCAL_ADDR,SIZE_DATA,SIZE_X,SIZE_Y>::tx_process(){
	tx_ch_vz = sc_logic_0;
	wr = false;
	din = 0;
	sc_module::wait();

	while(true){

		while(tx_ch_lz.read() == sc_logic_0) sc_module::wait();
		tx_ch_vz = sc_logic_1;
		sc_lv<SIZE_DATA> rx_data = tx_ch_z.read();
		sc_module::wait();
		tx_ch_vz = sc_logic_0;

#ifdef debug_catapult_nodes
		std::cout << "##INFO NODE (" << X << "," << Y << "," << LOCAL_ADDR << ") - TX: Received " << rx_data.to_string(SC_HEX) << "\n";
#endif

		sc_biguint<SIZE_DATA>	tx_data = rx_data;
		sc_biguint<3> 			tx_local_dst = get_dest_local();
		sc_biguint<SIZE_Y>		tx_Y_dst = get_dest_y();
		sc_biguint<SIZE_X>		tx_X_dst = get_dest_x();
		sc_biguint<3>			tx_local_orig = LOCAL_ADDR;
		sc_biguint<SIZE_Y>		tx_Y_orig = Y;
		sc_biguint<SIZE_X>		tx_X_orig = X;

		while(wait.read()) sc_module::wait();
		din = (tx_X_orig,
				tx_Y_orig,
				tx_local_orig,
				tx_X_dst,
				tx_Y_dst,
				tx_local_dst,
				tx_data);
		wr = true;

		sc_module::wait();

		wr = false;

#ifdef debug_catapult_nodes
		std::cout << "##INFO NODE (" << X << "," << Y << "," << LOCAL_ADDR << ") - TX: " << tx_data.to_string(SC_HEX) << " forwarded to " << "(" << tx_X_dst << "," << tx_Y_dst << "," << tx_local_dst << ")\n";
#endif

		sc_module::wait();
	}
}

template<
	unsigned int X,
	unsigned int Y,
	unsigned int LOCAL_ADDR,
	unsigned int SIZE_DATA,
	unsigned int SIZE_X,
	unsigned int SIZE_Y
>
void rtsnoc_to_achannel<X,Y,LOCAL_ADDR,SIZE_DATA,SIZE_X,SIZE_Y>::rx_process(){
	rd = false;
	rx_ch_z = 0;
	rx_ch_vz = sc_logic_0;
	sc_module::wait();

	while(true){

		while(!nd.read()) sc_module::wait();

		sc_biguint<SIZE_DATA>	rx_data;
		sc_biguint<3> 			rx_local_dst;
		sc_biguint<SIZE_Y>		rx_Y_dst;
		sc_biguint<SIZE_X>		rx_X_dst;
		sc_biguint<3>			rx_local_orig;
		sc_biguint<SIZE_Y>		rx_Y_orig;
		sc_biguint<SIZE_X>		rx_X_orig;
		(rx_X_orig,
		 rx_Y_orig,
		 rx_local_orig,
		 rx_X_dst,
		 rx_Y_dst,
		 rx_local_dst,
		 rx_data) = dout.read();

		set_dest_local(rx_local_orig.to_uint());
		set_dest_x(rx_X_orig.to_uint());
		set_dest_y(rx_Y_orig.to_uint());

#ifdef debug_catapult_nodes
		std::cout << "##INFO NODE (" << X << "," << Y << "," << LOCAL_ADDR << ") - RX: Received " << rx_data.to_string(SC_HEX) << " from " << "(" << rx_X_orig << "," << rx_Y_orig << "," << rx_local_orig << ")\n";
#endif

		sc_lv<SIZE_DATA> tx_data = rx_data;

		rd = true;
		sc_module::wait();
		rd = false;

		while(rx_ch_lz.read() == sc_logic_0) sc_module::wait();
		rx_ch_z = tx_data;
		rx_ch_vz = sc_logic_1;
		sc_module::wait();
		rx_ch_vz = sc_logic_0;

#ifdef debug_catapult_nodes
		std::cout << "##INFO NODE (" << X << "," << Y << "," << LOCAL_ADDR << ") - RX: " << tx_data.to_string(SC_HEX) << " forwarded\n";
#endif

		sc_module::wait();
	}
}

template<
	unsigned int X,
	unsigned int Y,
	unsigned int LOCAL_ADDR,
	unsigned int DEST_X,
	unsigned int DEST_Y,
	unsigned int DEST_LOCAL_ADDR,
	unsigned int SIZE_DATA,
	unsigned int SIZE_X,
	unsigned int SIZE_Y
>
class rtsnoc_to_achannel_static_dest: public rtsnoc_to_achannel<X,Y,LOCAL_ADDR,SIZE_DATA,SIZE_X,SIZE_Y>{
public:
	typedef rtsnoc_to_achannel<X,Y,LOCAL_ADDR,SIZE_DATA,SIZE_X,SIZE_Y> Base;

	SC_HAS_PROCESS(rtsnoc_to_achannel_static_dest);
	rtsnoc_to_achannel_static_dest(sc_module_name nm) :Base(nm){}

private:
	virtual unsigned int get_dest_x(){ return DEST_X;}
	virtual unsigned int get_dest_y(){ return DEST_Y;}
	virtual unsigned int get_dest_local(){ return DEST_LOCAL_ADDR;}
	virtual void set_dest_x(unsigned int a){}
	virtual void set_dest_y(unsigned int b){}
	virtual void set_dest_local(unsigned int c) {}

};

template<
	unsigned int X,
	unsigned int Y,
	unsigned int LOCAL_ADDR,
	unsigned int SIZE_DATA,
	unsigned int SIZE_X,
	unsigned int SIZE_Y
>
class rtsnoc_to_achannel_echo_dest: public rtsnoc_to_achannel<X,Y,LOCAL_ADDR,SIZE_DATA,SIZE_X,SIZE_Y>{
public:
	typedef rtsnoc_to_achannel<X,Y,LOCAL_ADDR,SIZE_DATA,SIZE_X,SIZE_Y> Base;

	SC_HAS_PROCESS(rtsnoc_to_achannel_echo_dest);
	rtsnoc_to_achannel_echo_dest(sc_module_name nm) :Base(nm){
		x = 0;
		y = 0;
		local = 0;
		addr_set = false;
	}

private:
	unsigned int x;
	unsigned int y;
	unsigned int local;
	bool addr_set;
	void check_addr_set(){
		if(!addr_set){
			std::cout << "##ERROR rtsnoc_to_achannel_echo_dest: tx before rx --- dest addr. not set\n";
			sc_stop();
		}
	}

	virtual unsigned int get_dest_x(){ check_addr_set(); return x;}
	virtual unsigned int get_dest_y(){ check_addr_set(); return y;}
	virtual unsigned int get_dest_local(){ check_addr_set(); return local;}
	virtual void set_dest_x(unsigned int val){ addr_set = true; x = val;}
	virtual void set_dest_y(unsigned int val){ addr_set = true; y = val;}
	virtual void set_dest_local(unsigned int val) { addr_set = true; local = val;}

};


template<
	unsigned int X,
	unsigned int Y,
	unsigned int LOCAL_ADDR,
	unsigned int DEST_X,
	unsigned int DEST_Y,
	unsigned int DEST_LOCAL_ADDR,
	unsigned int SIZE_DATA,
	unsigned int SIZE_X,
	unsigned int SIZE_Y
>
SC_MODULE(NOC_Transceiver){

	enum{
		BUS_SIZE = SIZE_DATA+(2*SIZE_X)+(2*SIZE_Y)+6,

		MASK_SIZE_DATA = ~((-1) << SIZE_DATA),
		MASK_SIZE_X = ~((-1) << SIZE_X),
		MASK_SIZE_Y = ~((-1) << SIZE_Y),
		MASK_SIZE_LOCAL = 0x7,

		MASK_WR = 0x1,
		MASK_RD = 0x2,
	};

	sc_in<bool>		clk;
	sc_in<bool>		rst;

	sc_in<sc_biguint<BUS_SIZE> > din;
	sc_out<sc_biguint<BUS_SIZE> > dout;
	sc_in<bool> wr;
	sc_in<bool> rd;
	sc_out<bool> wait;
	sc_out<bool> nd;

	typedef void(*callback_t)(unsigned int&);

	SC_HAS_PROCESS(NOC_Transceiver);
	NOC_Transceiver(sc_module_name nm, callback_t cb = 0)
			:sc_module(nm),
			 call_back(cb){

		SC_CTHREAD(rx_process, clk.pos());
		reset_signal_is(rst, true);
		rx_data = 0;
		rx_req = false;

		SC_CTHREAD(tx_process, clk.pos());
		reset_signal_is(rst, true);
		tx_data = 0;
		tx_req = false;

		if(cb) SC_THREAD(callback_process);
	}

	unsigned int read(){
		if(call_back)
			return 0;
		else
			return private_read();

	}
	void write(unsigned int);

private:
	unsigned int private_read();

	void callback_process();

	void rx_process();

	void tx_process();

	unsigned int tx_data;
	unsigned int rx_data;
	bool rx_req;
	sc_event rx_end;
	bool tx_req;
	sc_event tx_end;

	callback_t	call_back;


};

template<
	unsigned int X,
	unsigned int Y,
	unsigned int LOCAL_ADDR,
	unsigned int DEST_X,
	unsigned int DEST_Y,
	unsigned int DEST_LOCAL_ADDR,
	unsigned int SIZE_DATA,
	unsigned int SIZE_X,
	unsigned int SIZE_Y
>
void NOC_Transceiver<X,Y,LOCAL_ADDR,DEST_X,DEST_Y,DEST_LOCAL_ADDR,SIZE_DATA,SIZE_X,SIZE_Y>::rx_process(){
	wait = false;
	sc_module::wait();

	while(true){

		while(!wr.read()) sc_module::wait();
		rx_data = dout.read().range(SIZE_DATA-1,0);
		wait = true;

		while(!rx_req) sc_module::wait();
		rx_req = false;

		wait = false;
		rx_end.notify();

		sc_module::wait();
	}
}

template<
	unsigned int X,
	unsigned int Y,
	unsigned int LOCAL_ADDR,
	unsigned int DEST_X,
	unsigned int DEST_Y,
	unsigned int DEST_LOCAL_ADDR,
	unsigned int SIZE_DATA,
	unsigned int SIZE_X,
	unsigned int SIZE_Y
>
void NOC_Transceiver<X,Y,LOCAL_ADDR,DEST_X,DEST_Y,DEST_LOCAL_ADDR,SIZE_DATA,SIZE_X,SIZE_Y>::tx_process(){
	nd = false;
	dout = 0;
	sc_module::wait();

	while(true){

		if(tx_req){
			tx_req = false;

			sc_uint<SIZE_DATA>	tx_data_ = tx_data;
			sc_uint<3> 			tx_local_dst = DEST_LOCAL_ADDR;
			sc_uint<SIZE_Y>		tx_Y_dst = DEST_Y;
			sc_uint<SIZE_X>		tx_X_dst = DEST_X;
			sc_uint<3>			tx_local_orig = LOCAL_ADDR;
			sc_uint<SIZE_Y>		tx_Y_orig = Y;
			sc_uint<SIZE_X>		tx_X_orig = X;
			dout = (tx_X_orig,
					tx_Y_orig,
					tx_local_orig,
					tx_X_dst,
					tx_Y_dst,
					tx_local_dst,
					tx_data_);
			nd = true;

			sc_module::wait();
			nd = false;
			while(!rd.read()) sc_module::wait();

			tx_end.notify();

		}
		else{
			nd = false;
		}

		sc_module::wait();
	}
}

template<
	unsigned int X,
	unsigned int Y,
	unsigned int LOCAL_ADDR,
	unsigned int DEST_X,
	unsigned int DEST_Y,
	unsigned int DEST_LOCAL_ADDR,
	unsigned int SIZE_DATA,
	unsigned int SIZE_X,
	unsigned int SIZE_Y
>
void NOC_Transceiver<X,Y,LOCAL_ADDR,DEST_X,DEST_Y,DEST_LOCAL_ADDR,SIZE_DATA,SIZE_X,SIZE_Y>::callback_process(){
	unsigned int data;

	while(true){
		data = private_read();
		(*call_back)(data);
	}
}

template<
	unsigned int X,
	unsigned int Y,
	unsigned int LOCAL_ADDR,
	unsigned int DEST_X,
	unsigned int DEST_Y,
	unsigned int DEST_LOCAL_ADDR,
	unsigned int SIZE_DATA,
	unsigned int SIZE_X,
	unsigned int SIZE_Y
>
unsigned int NOC_Transceiver<X,Y,LOCAL_ADDR,DEST_X,DEST_Y,DEST_LOCAL_ADDR,SIZE_DATA,SIZE_X,SIZE_Y>::private_read(){
	rx_req = true;
	sc_module::wait(rx_end);
	return rx_data;
}

template<
	unsigned int X,
	unsigned int Y,
	unsigned int LOCAL_ADDR,
	unsigned int DEST_X,
	unsigned int DEST_Y,
	unsigned int DEST_LOCAL_ADDR,
	unsigned int SIZE_DATA,
	unsigned int SIZE_X,
	unsigned int SIZE_Y
>
void NOC_Transceiver<X,Y,LOCAL_ADDR,DEST_X,DEST_Y,DEST_LOCAL_ADDR,SIZE_DATA,SIZE_X,SIZE_Y>::write(unsigned int d){
	tx_req = true;
	tx_data = d;
	sc_module::wait(tx_end);
}


template<
	class Top_Level,
	unsigned int X_CALL,
	unsigned int Y_CALL,
	unsigned int LOCAL_ADDR_CALL,
	unsigned int SIZE_DATA,
	unsigned int SIZE_X,
	unsigned int SIZE_Y,
	unsigned int IID_SIZE,
	unsigned int IID_N_IDS
>
SC_MODULE(Call_Channel_Node){

	Top_Level toplevel;
	rtsnoc_to_achannel_auto<X_CALL,Y_CALL,LOCAL_ADDR_CALL,SIZE_DATA,SIZE_X,SIZE_Y> convert;

	enum{
		BUS_SIZE = rtsnoc_to_achannel_echo_dest<X_CALL,Y_CALL,LOCAL_ADDR_CALL,SIZE_DATA,SIZE_X,SIZE_Y>::BUS_SIZE,
		CALL_PORT = 0,
		REQ_PORT = 0xFF,
	};

	sc_in<bool>		clk;
	sc_in<bool>		rst;

	sc_out<sc_biguint<BUS_SIZE> > din[1];
	sc_in<sc_biguint<BUS_SIZE> > dout[1];
	sc_out<bool> wr[1];
	sc_out<bool> rd[1];
	sc_in<bool> wait[1];
	sc_in<bool> nd[1];
	sc_in<unsigned int> iid[IID_N_IDS];

	sc_in<unsigned long int> tsc;

	sc_signal<sc_logic> rst_logic;

	sc_signal< sc_lv<56> > rx_ch_rsc_z;
	sc_signal< sc_logic > rx_ch_rsc_vz;
	sc_signal< sc_logic > rx_ch_rsc_lz;

	sc_signal< sc_lv<56> > tx_ch_rsc_z;
	sc_signal< sc_logic > tx_ch_rsc_vz;
	sc_signal< sc_logic > tx_ch_rsc_lz;

	sc_signal< sc_lv<IID_N_IDS*IID_SIZE> > iid_rsc;

	SC_CTOR(Call_Channel_Node)
		:toplevel("Top"),
		 convert("Convert"){

		std::cout << "## INFO: Elaborating node " << this->name() << " using Call_Channel_Node<"
				<< "X_CALL= " << X_CALL << ","
				<< "Y_CALL= " << Y_CALL << ","
				<< "LOCAL_ADDR_CALL= " << LOCAL_ADDR_CALL << ","
				<< "SIZE_DATA= " << SIZE_DATA << ","
				<< "SIZE_X= " << SIZE_X << ","
				<< "SIZE_Y= " << SIZE_Y << ","
				<< "BUS_SIZE= " << BUS_SIZE << ","
				<< ">" << std::endl;

		convert.clk(clk);
		convert.rst(rst);
		convert.din(din[CALL_PORT]);
		convert.dout(dout[CALL_PORT]);
		convert.wr(wr[CALL_PORT]);
		convert.rd(rd[CALL_PORT]);
		convert.wait(wait[CALL_PORT]);
		convert.nd(nd[CALL_PORT]);
		convert.rx_ch_z(rx_ch_rsc_z);
		convert.rx_ch_vz(rx_ch_rsc_vz);
		convert.rx_ch_lz(rx_ch_rsc_lz);
		convert.tx_ch_z(tx_ch_rsc_z);
		convert.tx_ch_vz(tx_ch_rsc_vz);
		convert.tx_ch_lz(tx_ch_rsc_lz);
		convert.tsc(tsc);

		toplevel.clk(clk);
		toplevel.rst(rst_logic);
		toplevel.rx_ch_rsc_z(rx_ch_rsc_z);
		toplevel.rx_ch_rsc_vz(rx_ch_rsc_vz);
		toplevel.rx_ch_rsc_lz(rx_ch_rsc_lz);
		toplevel.tx_ch_rsc_z(tx_ch_rsc_z);
		toplevel.tx_ch_rsc_vz(tx_ch_rsc_vz);
		toplevel.tx_ch_rsc_lz(tx_ch_rsc_lz);
		toplevel.iid(iid_rsc);

		SC_METHOD(rst_convert); sensitive << rst;

		SC_METHOD(iid_convert); for (unsigned int i = 0; i < IID_N_IDS; ++i) sensitive << iid[i];
	}

	void rst_convert(){
		rst_logic = rst.read() ? sc_logic_1 : sc_logic_0;
	}

	void iid_convert(){
		sc_lv<IID_N_IDS*IID_SIZE> tmp;
		for (unsigned int i = 0; i < IID_N_IDS; ++i) {
			tmp.range(((i+1)*IID_SIZE)-1,(i*IID_SIZE)) = iid[i];
		}
		iid_rsc.write(tmp);
	}

};

#define DECLARE_NODE(node_name, top_level_ns)											\
template<																				\
	unsigned int X,																\
	unsigned int Y,																\
	unsigned int LOCAL_ADDR,														\
	unsigned int SIZE_DATA,																\
	unsigned int SIZE_X,																\
	unsigned int SIZE_Y,																\
	unsigned int IID_SIZE,																\
	unsigned int IID_N_IDS																\
>																						\
class node_name: public Call_Channel_Node<top_level_ns::top_level,X,Y,LOCAL_ADDR,SIZE_DATA,SIZE_X,SIZE_Y,IID_SIZE,IID_N_IDS> {					\
public:																					\
	typedef Call_Channel_Node<top_level_ns::top_level,X,Y,LOCAL_ADDR,SIZE_DATA,SIZE_X,SIZE_Y,IID_SIZE,IID_N_IDS> Base;							\
																						\
	SC_HAS_PROCESS(node_name);															\
	node_name(sc_module_name nm) :Base(nm){}											\
}

template<
    unsigned int X,
    unsigned int Y,
    unsigned int LOCAL_ADDR,
    unsigned int SIZE_DATA,
    unsigned int SIZE_X,
    unsigned int SIZE_Y,
    unsigned int IID_N_IDS,
    class Adapted
>
class adapter_fast : public sc_module{
public:
    enum{
        BUS_SIZE = SIZE_DATA+(2*SIZE_X)+(2*SIZE_Y)+6,
        CALL_PORT = Adapted::CALL_PORT,
        REQ_PORT = Adapted::REQ_PORT
    };
    sc_in<bool>     rst;
    sc_in<unsigned int> iid[IID_N_IDS];
    sc_fifo_out<sc_biguint<BUS_SIZE> > din[1];
    sc_fifo_in<sc_biguint<BUS_SIZE> > dout[1];
    sc_in<unsigned long int> tsc;

public:
    rtsnoc_router_fast_wrapper<X, Y, LOCAL_ADDR, SIZE_DATA, SIZE_X, SIZE_Y> wrapper;
    Adapted adapted;

    sc_signal<sc_biguint<BUS_SIZE> > din_s;
    sc_signal<sc_biguint<BUS_SIZE> > dout_s;
    sc_signal<bool> wr;
    sc_signal<bool> rd;
    sc_signal<bool> wait;
    sc_signal<bool> nd;

public:
    SC_HAS_PROCESS(adapter_fast);
    adapter_fast(sc_module_name nm) :sc_module(nm),
        wrapper("fast_wrapper"),
        adapted("adapted")

    {
        wrapper.rst(rst);
        wrapper.din(din_s);
        wrapper.dout(dout_s);
        wrapper.wr(wr);
        wrapper.rd(rd);
        wrapper.wait(wait);
        wrapper.nd(nd);
        wrapper.fifo_din(dout[CALL_PORT]);
        wrapper.fifo_dout(din[CALL_PORT]);

        adapted.rst(rst);
        for (unsigned int i = 0; i < IID_N_IDS; ++i) {
            adapted.iid[i](iid[i]);
        }
        adapted.clk(wrapper.clk);
        adapted.din[CALL_PORT](din_s);
        adapted.dout[CALL_PORT](dout_s);
        adapted.wr[CALL_PORT](wr);
        adapted.rd[CALL_PORT](rd);
        adapted.wait[CALL_PORT](wait);
        adapted.nd[CALL_PORT](nd);
        adapted.tsc(tsc);
    }


};

#define DECLARE_NODE_FAST(node_name)                                           \
template<                                                                               \
    unsigned int X,                                                             \
    unsigned int Y,                                                             \
    unsigned int LOCAL_ADDR,                                                        \
    unsigned int SIZE_DATA,                                                             \
    unsigned int SIZE_X,                                                                \
    unsigned int SIZE_Y,                                                                \
    unsigned int IID_SIZE,                                                              \
    unsigned int IID_N_IDS                                                              \
>                                                                                       \
class node_name##_fast: public adapter_fast<X,Y,LOCAL_ADDR,SIZE_DATA,SIZE_X,SIZE_Y,IID_N_IDS, \
                                                node_name<X,Y,LOCAL_ADDR,SIZE_DATA,SIZE_X,SIZE_Y,IID_SIZE,IID_N_IDS> > {                  \
public:                                                                                 \
    typedef node_name<X,Y,LOCAL_ADDR,SIZE_DATA,SIZE_X,SIZE_Y,IID_SIZE,IID_N_IDS> Adapted;                          \
    typedef adapter_fast<X,Y,LOCAL_ADDR,SIZE_DATA,SIZE_X,SIZE_Y, IID_N_IDS, Adapted> Base;                          \
\
    SC_HAS_PROCESS(node_name##_fast);                                                          \
    node_name##_fast(sc_module_name nm) :Base(nm){}                                            \
}

//

#endif /* NOC_TO_CATAPULT_H_ */
