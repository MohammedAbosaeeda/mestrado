package keso.driver.tricore.tc1796b.modules.gpta;

import keso.core.*;

/**
 * Tricore Global Timers.
 *
 * @author mike
 */

public final class GT {
	public static final int OFS_GTCTR0 = 0xE0;
	public static final int OFS_GTREV0 = 0xE0 + 4;
	public static final int OFS_GTTIM0 = 0xE0 + 8;

	public static final int OFS_nextTimer = 16; 

	/**
	 * Select the input clock from the 8 lines on the clock bus.
	 */
	public static void selectClock(Memory gptaMemMap, int gtsel, byte buslineIndex) {
		int offset = OFS_GTCTR0 + gtsel*OFS_nextTimer;
		buslineIndex &= 0x7;
		gptaMemMap.and32(offset, 0x8f);
		gptaMemMap.or32(offset, buslineIndex<<4); 
	}

	public static void enableInterruptRequests(Memory gptaMemMap, int gtsel) {
		int offset = OFS_GTCTR0 + gtsel*OFS_nextTimer;
		gptaMemMap.or32(offset, 0x80);
	}
	public static void disableInterruptRequests(Memory gptaMemMap, int gtsel) {
		int offset = OFS_GTCTR0 + gtsel*OFS_nextTimer;
		gptaMemMap.and32(offset, 0x7f);
	}

	public static void setReloadValue(Memory gptaMemMap, int gtsel, int value) {
		int offset = OFS_GTREV0 + gtsel*OFS_nextTimer;
		gptaMemMap.set32(offset, value&0x00ffffff);
	}

	public static int getReloadValue(Memory gptaMemMap, int gtsel) {
		int offset = OFS_GTREV0 + gtsel*OFS_nextTimer;
		return gptaMemMap.get32(offset);
	}
	public static void setValue(Memory gptaMemMap, int gtsel, int value) {
		int offset = OFS_GTTIM0 + gtsel*OFS_nextTimer;
		gptaMemMap.set32(offset, value&0x00ffffff);
	}
	public static int getValue(Memory gptaMemMap, int gtsel) {
		int offset = OFS_GTTIM0 + gtsel*OFS_nextTimer;
		return gptaMemMap.get32(offset);
	}

}
