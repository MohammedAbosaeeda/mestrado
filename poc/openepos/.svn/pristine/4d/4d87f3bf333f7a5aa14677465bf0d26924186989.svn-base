/*
 * uart.cpp
 *
 *  Created on: 30/03/2010
 *      Author: tiago
 */

#include <string>
#include <iostream>
#include "timer.h"
#include <components/src/system/types.h>

void timer::read(unsigned int *data, unsigned int address, int size) {

	*data = time;

	//std::cout << "##INFO: timer::read(data=" << (void*)(*data) << " address=" << (void*)address << ") |"
	//		  <<" time=" << time
	//		  <<" int_time=" << int_time
	//		  <<"\n";

	Global::delay_cycles<0>(this, 1);
}

void timer::write(unsigned int data, unsigned int address, int size) {


    increment = data;
    int_time = (data != 0) ? (time + data) : 0;

	//std::cout << "##INFO: timer::write(data=" << (void*)data << " address=" << (void*)address << ") |"
	//			  <<" time=" << time
	//			  <<" int_time=" << int_time
	//			  <<"\n";

	Global::delay_cycles<0>(this, 1);

}

unsigned int timer::get_start_address() const{
	return start_address;
}

unsigned int timer::get_end_address() const{
	return end_address;
}

void timer::timer_proc() {
	time = 0;
	int_time = 0;
	irq = false;

	while(rst.read() == true) wait(rst.negedge_event());

	Global::delay_cycles<0>(this, 1);
	while(true){

		time.write(time.read() + 1);


		if((int_time != 0) && (int_time == time)){
			int_time = time + increment;
			irq = true;
			//std::cout << "##INFO: timer::timer_proc(irq=true) |"
			//				  <<" time=" << time
			//				  <<" int_time=" << int_time
			//				  <<"\n";
		}
		else{
			irq = false;
		}

		Global::delay_cycles<0>(this, 1);
	}
}




