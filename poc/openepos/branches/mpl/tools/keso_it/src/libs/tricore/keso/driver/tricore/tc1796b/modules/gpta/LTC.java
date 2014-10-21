package keso.driver.tricore.tc1796b.modules.gpta;

import keso.core.*;

/**
 * Tricore GPTA Local Timer Cells.
 *
 * @author mike
 */
public final class LTC {
	private static final int OFS_LTCCTR00 = 0x200;
	private static final int OFS_LTCXR00 = 0x200+4;
	private static final int OFS_nextCell = 8; 

	public static final int MODE_CAPTURE=0;
	public static final int MODE_COMPARE=1;
	public static final int MODE_FREE_RUNNING_TIMER=2;
	public static final int MODE_RESET_TIMER=3;

	public static final int OCM_MODE_HOLD=0;
	public static final int OCM_MODE_TOGGLED=1;
	public static final int OCM_MODE_RESET=2;
	public static final int OCM_MODE_SET=3;
	public static final int OCM_MODE_INTEVENT=0;
	public static final int OCM_MODE_INTEVENT_OR_ADJACENT_LTC=4;

	public static final int BIT_OSM=4;
	public static final int BIT_REN=8;
	public static final int BIT_RED=0x10;
	public static final int BIT_PEN=0x10;
	public static final int BIT_SOL=0x10;
	public static final int BIT_FED=0x20;
	public static final int BIT_AIL=0x20;
	public static final int BIT_SOH=0x20;
	public static final int BIT_SLO=0x40;
	public static final int BIT_BYP=0x40;
	public static final int BIT_CUDCLR=0x80;
	public static final int BIT_EOA=0x80;
	public static final int BIT_ILM=0x100;
	public static final int BIT_CUD=0x200;
	public static final int BIT_SLL=0x200;
	public static final int BIT_CEN=0x400;
	public static final int BIT_OIA=0x4000;
	public static final int BIT_OUT=0x8000;

	public static void setDataReg(Memory ltcaMemMap, int ltcsel, int value) {
		int offset = OFS_LTCXR00 + ltcsel*OFS_nextCell;
		ltcaMemMap.set32(offset, value & 0xffff);
	}

	public static void setMode(Memory ltcaMemMap, int ltcsel, int ltcmode) {
		int offset = OFS_LTCCTR00 + ltcsel*OFS_nextCell;
		
		ltcaMemMap.and32(offset, ~3);
		ltcaMemMap.or32(offset, ltcmode);
	}

	public static void setOCMMode(Memory ltcaMemMap, int ltcsel, int ocmmode) {
		int offset = OFS_LTCCTR00 + ltcsel*OFS_nextCell;
		
		ltcaMemMap.and32(offset, ~0x3800);
		ltcaMemMap.or32(offset, ocmmode<<11);
	}

	public static void set(Memory ltcaMemMap, int ltcsel, int setting) {
		int offset = OFS_LTCCTR00 + ltcsel*OFS_nextCell;
		ltcaMemMap.or32(offset, setting);
	}
	public static void unset(Memory ltcaMemMap, int ltcsel, int setting) {
		int offset = OFS_LTCCTR00 + ltcsel*OFS_nextCell;
		ltcaMemMap.and32(offset, ~setting);
	}
	public static boolean get(Memory ltcaMemMap, int ltcsel, int setting) {
		int offset = OFS_LTCCTR00 + ltcsel*OFS_nextCell;
		return (ltcaMemMap.get32(offset) & setting)!=0;
	}
}

