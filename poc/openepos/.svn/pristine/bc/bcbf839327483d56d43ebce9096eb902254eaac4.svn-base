/**(c)

  Copyright (C) 2005 Christian Wawersich

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.driver.tricore.tc1796b;

/**
 * Various hardware addresses and offsets of the TC1796b.
 * Note that an address range starts at the _BASE address and is _SIZE long, so
 * the last value is stored at _BASE + _SIZE - 1. This makes it easy to create
 * a Memory object that covers the whole range since the constructor needs a base
 * address and the size (not the last valid offset).
 */

public class HWPorts {

	public final static int P0_BASE = 0xf0000c00;
	public final static int P1_BASE	= 0xf0000d00;
	public final static int P5_BASE = 0xF0001100;
	public final static int Pn_SIZE = 0x45;

	public final static int ASC0_BASE = 0xF0000A00;
	public final static int ASC1_BASE = 0xF0000B00;
	public final static int ASCn_SIZE = 0x100;

	public final static int WDTCON0_BASE = 0xF0000020; /* Watchdog Timer 0 */
	public final static int WDTCON1_BASE = 0xF0000024; /* Watchdog Timer 1 */
	public final static int WDTCONn_SIZE = 0x5;
	
	public final static int PLL_CLC_BASE = 0xF0000040; /* PLL Clock Controll Register */
	public final static int PLL_CLC_SIZE = 0x5;

}
