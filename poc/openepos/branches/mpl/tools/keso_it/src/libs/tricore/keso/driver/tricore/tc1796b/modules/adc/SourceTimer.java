package keso.driver.tricore.tc1796b.modules.adc;

import keso.core.*;

/**
 * Tricore Analog-to-Digital Converters.
 *
 * @author mike
 */
public final class SourceTimer {
	public static final int OFS_TCON = 0x114;
	public static final int OFS_TTC = 0x8c;
	public static final int MASK_TIMERRUN=0x80000000;
	public static final int MASK_TIMERSTOP=0x40000000;
	
	public static void setReloadValue(Memory adcMemMap, int value) {
		adcMemMap.and32(OFS_TCON, 0xC000ffff);
		adcMemMap.or32(OFS_TCON, (value&0x3fff)<<16);
	}

	public static void setArbitrationLockBoundary(Memory adcMemMap, int value) {
		adcMemMap.set16(OFS_TCON, (short) (value&0x3fff)); // should do the job for little endian
	}

	public static void activateTrigger(Memory adcMemMap, byte channel) {
		adcMemMap.or32(OFS_TTC, 1<<channel);
	}
	public static void setTriggerMask(Memory adcMemMap, int mask) {
		adcMemMap.set32(OFS_TTC, mask&0xffff);
	}
}

