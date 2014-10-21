/*
 * mesh_bean2_led_master.h
 *
 *  Created on: Dec 3, 2011
 *      Author: mateus
 */

#ifndef __atmega1281_mesh_bean2_led_master_h
#define __atmega1281_mesh_bean2_led_master_h

namespace System {


class MeshBean2_LED_Master
{

public:

	static void turn_on_gr3_led()
	{
		CPU::out8(Machine::IO::PORTB, (1 << 7)); // Green LED.
	}


	static void turn_on_yr2_led()
	{
		CPU::out8(Machine::IO::PORTB, (1 << 6)); // Yellow LED.
	}


	static void turn_on_rr1_led()
	{
		CPU::out8(Machine::IO::PORTB, (1 << 5)); // Red LED.
	}


	static void turn_off_gr3_led()
	{
		CPU::out8(Machine::IO::PORTB, (0 << 7)); // Green LED.
	}


	static void turn_off_yr2_led()
	{
		CPU::out8(Machine::IO::PORTB, (0 << 6)); // Yellow LED.
	}


	static	void turn_off_rr1_led()
	{
		CPU::out8(Machine::IO::PORTB, (0 << 5)); // Red LED.
	}

};


}

#endif
