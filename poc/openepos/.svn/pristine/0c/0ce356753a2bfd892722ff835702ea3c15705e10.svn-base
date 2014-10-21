/**(c)

  Copyright (C) 2005 Christian Wawersich

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.leds;

import keso.core.Task;
import keso.core.TaskService;

import keso.core.MemoryService;
import keso.core.Memory;

import keso.driver.avr.atmega128.ATMega128;

public class LEDTask extends Task {
	/* for whatever reason, clearing the PA? Bit does
	 * actually lit the connected LED and the other
	 * way round... */
	static {
		ATMega128.registers.DDRA.setBit(ATMega128.DDA0);
		ATMega128.registers.DDRA.setBit(ATMega128.DDA2);
		ATMega128.registers.PORTA.setBit(ATMega128.PA0);
		ATMega128.registers.PORTA.setBit(ATMega128.PA2);
	}

	public static int pause() {
		for(short i=0; i<30000; i++);
		return 0;
	}

	public static void stackOverflow() {
		ATMega128.registers.PORTA.clearBit(ATMega128.PA2);
		while(true) { }
	}

	public static boolean setLED(boolean state) {
		if(state)
			ATMega128.registers.PORTA.clearBit(ATMega128.PA0);
		else
			ATMega128.registers.PORTA.setBit(ATMega128.PA0);
		/* the compiler seems to be smart enough to replace
		 * recursion with iteration if possible; the cludge
		 * below enforces recursion so we do eventually get
		 * a stack overflow...
		 */
		boolean nstate = setLED(!state);
		return state ^ nstate;
	}

	public void launch() {
		setLED(true);
	}
}
