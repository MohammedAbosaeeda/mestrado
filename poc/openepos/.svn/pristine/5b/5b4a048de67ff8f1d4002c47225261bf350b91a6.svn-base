/*
 * fir2.h
 *
 *  Created on: Jun 17, 2011
 *      Author: tiago
 */

#ifndef FIR2_DEBUGGED_COMP_H_
#define FIR2_DEBUGGED_COMP_H_

#include <systemc.h>
#include "../common/cplxopsphasor.sc.h"
#include "../common/fir_coefs.h"


template<unsigned int N_BITS_INT, unsigned int N_BITS_CORE>
class FIR_Debugged_Comp_Complex_Interface {
public:
	sc_in<sc_int<N_BITS_INT> > sample_in_real;
	sc_in<sc_int<N_BITS_INT> > sample_in_imag;

	sc_out<sc_int<N_BITS_INT> > sample_out_real;
	sc_out<sc_int<N_BITS_INT> > sample_out_imag;

	virtual void read_input(complex<sc_int<N_BITS_CORE> > &sample){
		sample.set_real(sample_in_real.read().range(N_BITS_CORE-1,0));
		sample.set_imag(sample_in_imag.read().range(N_BITS_CORE-1,0));
	}

	virtual void write_output(complex<sc_int<N_BITS_CORE> > &sample){
		sc_int<N_BITS_INT> aux_real;
		sc_int<N_BITS_INT> aux_img;
		for (unsigned int i = 0; i < N_BITS_CORE; ++i) {
			aux_real[i] = sample.get_real()[i];
			aux_img[i] = sample.get_imag()[i];
		}
		for (unsigned int i = N_BITS_CORE; i < N_BITS_INT; ++i) {
			aux_real[i] = sample.get_real()[N_BITS_CORE-1];
			aux_img[i] = sample.get_imag()[N_BITS_CORE-1];
		}
		sample_out_real = aux_real;
		sample_out_imag = aux_img;
	}
};

template<unsigned int N_BITS_INT, unsigned int N_BITS_CORE>
class FIR_Debugged_Comp_Real_Interface {
public:
	sc_in<sc_int<N_BITS_INT> > sample_in;

	sc_out<sc_int<N_BITS_INT> > sample_out;

	virtual void read_input(sc_int<N_BITS_CORE> &sample){
		sample = sample_in.read().range(N_BITS_CORE-1,0);
	}

	virtual void write_output(sc_int<N_BITS_CORE> &sample){
		sc_int<N_BITS_INT> aux;
		for (unsigned int i = 0; i < N_BITS_CORE; ++i) {
			aux[i] = sample[i];
		}
		for (unsigned int i = N_BITS_CORE; i < N_BITS_INT; ++i) {
			aux[i] = sample[N_BITS_CORE-1];
		}
		sample_out = aux;
	}
};

template<typename TYPE, class COEF_INIT, unsigned int N_TAPS, class INTERFACE>
class FIR_Debugged_Comp_Core :
		public sc_module,
		public INTERFACE {
public:

	static const int OPERATION_SIZE = 1;
	static const int PARAM_RETURN_SIZE = 8;
	static const int PRECISION = 8;
	static const int STATE_SIZE = 2;

	sc_in<bool> clk_in;
	sc_in<bool> rst_in;

	sc_out<bool> rdy_out;
	sc_in<bool> req_in;

	sc_out<bool> trigger_out;
	sc_out<sc_uint<64> > data_out;

	sc_signal<bool> rdy;

	sc_signal<sc_uint<OPERATION_SIZE> > operation;
	sc_signal<sc_uint<PARAM_RETURN_SIZE> > param_return;
	sc_signal<sc_uint<STATE_SIZE> > state;
	sc_signal<bool> start_stop;
	sc_signal<sc_uint<PRECISION> > profiled_counter;

	TYPE acc;

	TYPE shift[N_TAPS];
	TYPE coefs[N_TAPS];

	SC_HAS_PROCESS(FIR_Debugged_Comp_Core);
	FIR_Debugged_Comp_Core(sc_module_name nm)
		:sc_module(nm),
		 INTERFACE()
	{
		SC_CTHREAD(bahavior, clk_in.pos());
		reset_signal_is(rst_in, true);

		SC_METHOD(set_rdy_out);
		sensitive << req_in << rdy;

		SC_METHOD(set_debug_data);
		sensitive << start_stop
				  << operation
				  << param_return
				  << profiled_counter
				  << state;

		SC_METHOD(count);
		sensitive << clk_in.pos();
	}

	void count(){
			if(start_stop){
				profiled_counter = 0;
			}
			else
				profiled_counter = profiled_counter.read() + 1;
		}

		void set_debug_data(){
			data_out = (start_stop.read(),
						operation.read(),
						param_return.read(),
						profiled_counter.read(),
						state.read());
		}

		void debug_enter(){
			trigger_out = 1;
			state = 0;
			operation = 0;
			TYPE sample;
			INTERFACE::read_input(sample);
			param_return = sample.to_uint();
			start_stop = 1;
		}

		void debug_leave(){
			trigger_out = 1;
			state = 0;
			operation = 0;
			param_return = acc.to_uint();
			start_stop = 0;
		}

		void debug_go_down(){
			trigger_out = 0;
			state = 0;
			operation = 0;
			param_return = 0;
			start_stop = 0;
		}

	void set_rdy_out(){
		rdy_out = !req_in.read() && rdy.read();
	}

	void bahavior(){
		reset_bahavior();
		wait();
		while(true){
			main_bahavior();
			wait();
		}
	}

	virtual void reset_bahavior(){
		rdy = false;
		acc = TYPE(0);
		for (unsigned int i = 0; i < N_TAPS; ++i) {
			shift[i] = TYPE(0);
			coefs[i] = TYPE(COEF_INIT::COEFS[i]);
		}
		INTERFACE::write_output(acc);

		operation = 0;
		param_return = 0;
		state = 0;
		start_stop = 0;
	}

	void main_bahavior(){
		if(req_in.read()){
			debug_enter();
			rdy = false;

			TYPE sample;
			INTERFACE::read_input(sample);

			for (int i = N_TAPS-1; i >= 1; --i) {
				acc = acc + (shift[i-1] * coefs[i]);
				shift[i] = shift[i-1];
				wait();
			}
			acc = acc + (sample * coefs[0]);
			shift[0] = sample;

			INTERFACE::write_output(acc);
			debug_leave();
		}
		else{
			acc = TYPE(0);
			INTERFACE::write_output(acc);
			debug_go_down();
		}
		rdy = true;
	}



	//Gambi wait
	//TODO commented for synthesis with agility (uncomment for a working model)
	//void wait(){
//		sc_module::wait();
	//}
};

template<unsigned int N_BITS_INT, unsigned int N_BITS_CORE, unsigned int N_TAPS, class COEF_INIT, bool COMPLEX>
class FIR_Debugged_Comp_Aux;

template<unsigned int N_BITS_INT, unsigned int N_BITS_CORE, unsigned int N_TAPS, class COEF_INIT>
class FIR_Debugged_Comp_Aux<N_BITS_INT, N_BITS_CORE, N_TAPS, COEF_INIT, true> :
	public FIR_Debugged_Comp_Core<complex<sc_int<N_BITS_CORE> >,
					  COEF_INIT,
					  N_TAPS,
					  FIR_Debugged_Comp_Complex_Interface<N_BITS_INT, N_BITS_CORE> > {
public:
	typedef FIR_Debugged_Comp_Core<complex<sc_int<N_BITS_CORE> >,
	  COEF_INIT,
	  N_TAPS,
	  FIR_Debugged_Comp_Complex_Interface<N_BITS_INT, N_BITS_CORE> > Base;

	FIR_Debugged_Comp_Aux(sc_module_name nm) :Base(nm) { }
};

template<unsigned int N_BITS_INT, unsigned int N_BITS_CORE, unsigned int N_TAPS, class COEF_INIT>
class FIR_Debugged_Comp_Aux<N_BITS_INT, N_BITS_CORE, N_TAPS, COEF_INIT, false> :
	public FIR_Debugged_Comp_Core<sc_int<N_BITS_CORE>,
					  COEF_INIT,
					  N_TAPS,
					  FIR_Debugged_Comp_Real_Interface<N_BITS_INT, N_BITS_CORE> > {
public:
	typedef FIR_Debugged_Comp_Core<sc_int<N_BITS_CORE>,
	  COEF_INIT,
	  N_TAPS,
	  FIR_Debugged_Comp_Real_Interface<N_BITS_INT, N_BITS_CORE> > Base;

	FIR_Debugged_Comp_Aux(sc_module_name nm) :Base(nm) { }
};

template<unsigned int N_BITS, unsigned int N_TAPS, class COEF_INIT, bool COMPLEX>
class FIR_Debugged_Comp : public FIR_Debugged_Comp_Aux<N_BITS,N_BITS/2,N_TAPS,COEF_INIT,COMPLEX> {
public:
	FIR_Debugged_Comp(sc_module_name nm) :FIR_Debugged_Comp_Aux<N_BITS,N_BITS/2,N_TAPS,COEF_INIT,COMPLEX>(nm) { }
};


#endif /* FIR2_H_ */
