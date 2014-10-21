/*
 * plasma.h
 *
 *  Created on: 28/02/2010
 *      Author: tiago
 */

#ifndef MIPS32_H_
#define MIPS32_H_

#include <systemc.h>
#include <arch/common/cpu.h>
#include <arch/common/mem_ctrl_if.h>
#include "fetch.h"
#include "decoder.h"
#include "exec.h"
#include "mem.h"
#include "wb.h"
#include <platform/common/global.h>

class mips32 : public cpu {

public:

    SC_HAS_PROCESS(mips32);

    mips32(sc_module_name nm) : cpu(nm){

		r = new int[32];
		pc_data = new pc_info;
		pc_new_data = new pc_new_info;
		dec_data = new dec_info;
		cpu_status = new cpu_info;
		mem_data = new mem_info;
		wb_data = new wb_info;

		fet = new Fetch("fet", pc_data, pc_new_data, cpu_status);
		dec = new Decoder("dec", r, pc_data, dec_data);
		exe = new Exec("exe", pc_data, pc_new_data, dec_data, cpu_status, mem_data, wb_data);
		m = new Mem("m", pc_data, dec_data, cpu_status, mem_data, wb_data);
		w = new WB("w", r, pc_data, dec_data, cpu_status, wb_data);

		fet->mem_ctrl(bus);

		dec->exec = exe;

		exe->irq(irq);

		m->mem_ctrl(bus);

		SC_THREAD(run_plasma);
	}

	~mips32() {
		delete[] r;
		delete pc_data;
		delete pc_new_data;
		delete dec_data;
		delete cpu_status;
		delete mem_data;
		delete wb_data;

		delete fet;
		delete dec;
		delete exe;
		delete m;
		delete w;
	}

#ifdef dump_signals_vcd_internal
public:
#else
private:
#endif

	Fetch		*fet;
	Decoder		*dec;
	Exec		*exe;
	Mem			*m;
	WB			*w;

	int			*r;
	pc_info     *pc_data;
	pc_new_info *pc_new_data;
	dec_info    *dec_data;
	cpu_info    *cpu_status;
	mem_info    *mem_data;
	wb_info     *wb_data;

private:
	void run_plasma(){
	    while(rst.read() == true) wait(rst.negedge_event());

	    while(true){
	        fet->do_fetch();
	        dec->do_decode();

	        int delay = exe->do_exec();
	        Global::delay_cycles<0>(this, delay);

	        m->do_run();
	        w->do_wb();
	    }

	}

};


#endif /* PLASMA_H_ */
