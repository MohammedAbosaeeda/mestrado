package keso.driver.tricore.tc1796b.modules.gpta;

import keso.core.*;

/**
 * Tricore General Purpose Timer Arrays.
 *
 * @author mike
 */

public final class GPTA {
	public static final int GPTA0_BASE = 0xf0001800;
	public static final int GPTA1_BASE = 0xf0002000;
	public static final int LTCA2_BASE = 0xf0002800;
	
	public static final Memory gpta0 =  MemoryService.allocStaticDeviceMemory(GPTA0_BASE, 8*256);
	public static final Memory gpta1 =  MemoryService.allocStaticDeviceMemory(GPTA1_BASE, 8*256);
	public static final Memory lcta2 =  MemoryService.allocStaticDeviceMemory(LTCA2_BASE, 8*256);

	public static final Memory GPTA0_EDCTR = MemoryService.allocStaticDeviceMemory(0xf0001c00, 4);
	public static final int OFS_CKBCTR = 0xd8;

	public static final int GT00RUN=1;
	public static final int GT01RUN=2;
	public static final int GT10RUN=4;
	public static final int GT11RUN=8;
	public static final int G0EN=0x100;
	public static final int G1EN=0x200;
	public static final int L2EN=0x400;

	public static void enableGPTAClock(int clock) {
		GPTA0_EDCTR.or32(0, clock);
	}
	public static void disableGPTAClock(int clock) {
		GPTA0_EDCTR.and32(0, ~clock);
	}

	public static void setupClockDistributionUnit(Memory gptaMemMap, byte dfa2,
			byte dfa3, byte dfa4, byte dfa6, byte dfa7, byte dfaltc) {
		int mask = dfa2 | (dfa4<<4) | (dfa6<<8) | (dfa7<<12) | (dfa3<<16) | (dfaltc<<18);
		gptaMemMap.set32(OFS_CKBCTR, mask&0x1fffff);
	}
}

