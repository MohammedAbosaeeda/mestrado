/*
 * atmega1281_led_app.cc
 *
 * Sample program for MeshBean2 (ZigBit) ATMega 1281.
 *
 *  Created on: Dec 2, 2011
 *      Author: mateus
 */

#include <machine.h>
#include <time_master.h>
#include <mach/atmega1281/mesh_bean2_led_master.h>

__USING_SYS

int main()
{
	while (true) {
		MeshBean2_LED_Master::turn_on_rr1_led();
		TimeMaster::wait_more(2);
		MeshBean2_LED_Master::turn_off_rr1_led();
		TimeMaster::wait_more(2);
	}
}
