/*
 * led_debug.h
 *
 *  Created on: Mar 17, 2009
 *      Author: tiago
 */

#include <cpu.h>
#include <machine.h>

#ifndef LED_DEBUG_H_
#define LED_DEBUG_H_

using namespace System;

//#ifndef TSG_DEBUG_LED
#define TSG_DEBUG_LED

class TSG_led_debug {

private:

	typedef IO_Map<Machine> IO;


public:
	TSG_led_debug(){

	}

	enum LED{						 //GYR
		IDLE			= 0, //000
		OK				= 1, //001
		SENDING_CMD_AT	= 2, //010
		SENDING_CMD_TXT_AND_SMS_FORMAT	= 3, //011
		SMS_RECEIVED	= 4, //100
		PREPARING_MSG	= 5, //101
		SENDING_SMS		= 6, //110
		ERROR			= 7, //111

	};

	inline static void debug(LED led){
		#ifdef TSG_DEBUG_LED
		CPU::out8(IO::PORTB, (CPU::in8(IO::PORTB) & 0x1F) | (led << 5));
		#endif
	}

};


#endif /* LED_DEBUG_H_ */
