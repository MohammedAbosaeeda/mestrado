/*
 * adapted_scheduler.h
 *
 *  Created on: Jan 19, 2011
 *      Author: tiago
 */

#ifndef ADAPTED_SCHEDULER_H_
#define ADAPTED_SCHEDULER_H_

#include <systemc.h>
#include "scheduler.h"
#include "scheduler_adapted.h"
#include "debugged.h"

//Agility's GAMBI adapter
//typedef Scheduler_Adapted<MAX_THREADS, DWIDTH> AdaptedScheduler;

//Original adapter that could no be synthesized by Agility
/*
template<unsigned int C_MAX_THREADS, unsigned int C_DWIDTH>
class AdaptedScheduler:
	public Scheduler<C_MAX_THREADS, C_DWIDTH>,
	public Debugged<4, C_DWIDTH, 8, 8>
{
private:
	sc_signal<sc_uint<4> > command;

public:
	AdaptedScheduler(sc_module_name nm)
		:Scheduler<C_MAX_THREADS, C_DWIDTH>(nm),
		 Debugged<4, C_DWIDTH, 8, 8>(){

		//Debugged<4, C_DWIDTH, 8, 8>::clk_rst_bind.clk_in.bind(Scheduler<C_MAX_THREADS, C_DWIDTH>::clk_in);
		//Debugged<4, C_DWIDTH, 8, 8>::clk_rst_bind.rst_in.bind(Scheduler<C_MAX_THREADS, C_DWIDTH>::rst_in);
		this->bind_clk_rst(this->clk_in, this->rst_in);
	}

	void main_process_behavior(){
		if((this->s_state.read() == this->idle) && (this->command_in.read() != 0)) {
			//std::cout << "Entering cmd " << Scheduler<C_MAX_THREADS, C_DWIDTH>::command_in.read().to_uint() << "\n";
			command = this->command_in.read();
			this->enter(this->command_in.read(), this->paremeter_in.read());
		}
		else
			this->go_down();

		Scheduler<C_MAX_THREADS, C_DWIDTH>::main_process_behavior();

		if((this->s_state.read() == this->exit_ok)) {
			//std::cout << "Leaving cmd" << command.read().to_uint() << "\n";
			this->leave(command.read(), this->return_out.read());
		}
		else
			this->go_down();

	}

};
*/

#endif /* ADAPTED_SCHEDULER_H_ */
