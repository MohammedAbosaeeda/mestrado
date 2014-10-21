package keso.driver.tricore.tc1796b.modules.gpta;

import keso.core.*;

/**
 * Tricore GPTA Global Timer Cells.
 *
 * @author mike
 */
public final class GTC {
	public static final int OFS_GTCCTR00 = 0x100;
	public static final int OFS_GTCXR00 = 0x100+4;

	public static final int OFS_nextCell = 8; 

	public static void setMode(Memory gptaMemMap, int gtcsel, boolean captureMode, int sourceGT) {
		int offset = OFS_GTCCTR00 + gtcsel*OFS_nextCell;
		
		sourceGT &= 1;
		if(!captureMode) sourceGT |=2;

		gptaMemMap.and32(offset,0xfc);
		gptaMemMap.or32(offset, sourceGT);
	}

	public static void enableOneShot(Memory gptaMemMap, int gtcsel) {
		int offset = OFS_GTCCTR00 + gtcsel*OFS_nextCell;
		gptaMemMap.or32(offset, 4);
	}

	public static void disableOneShot(Memory gptaMemMap, int gtcsel) {
		int offset = OFS_GTCCTR00 + gtcsel*OFS_nextCell;
		gptaMemMap.and32(offset, 0xfb);
	}

	public static void enableInterruptRequests(Memory gptaMemMap, int gtcsel) {
		int offset = OFS_GTCCTR00 + gtcsel*OFS_nextCell;
		gptaMemMap.or32(offset, 8);
	}

	public static void disableInterruptRequests(Memory gptaMemMap, int gtcsel) {
		int offset = OFS_GTCCTR00 + gtcsel*OFS_nextCell;
		gptaMemMap.and32(offset, 0xf7);
	}

	public static void enableCaptureRisingEdge(Memory gptaMemMap, int gtcsel) {
		int offset = OFS_GTCCTR00 + gtcsel*OFS_nextCell;
		gptaMemMap.or32(offset, 0x10);
	}

	public static void disableCaptureRisingEdge(Memory gptaMemMap, int gtcsel) {
		int offset = OFS_GTCCTR00 + gtcsel*OFS_nextCell;
		gptaMemMap.and32(offset, 0xef);
	}

	public static void enableCaptureFallingEdge(Memory gptaMemMap, int gtcsel) {
		int offset = OFS_GTCCTR00 + gtcsel*OFS_nextCell;
		gptaMemMap.or32(offset, 0x20);
	}

	public static void disableCaptureFallingEdge(Memory gptaMemMap, int gtcsel) {
		int offset = OFS_GTCCTR00 + gtcsel*OFS_nextCell;
		gptaMemMap.and32(offset, 0xdf);
	}

	public static void compareGreaterEqual(Memory gptaMemMap, int gtcsel, boolean compge) {
		int offset = OFS_GTCCTR00 + gtcsel*OFS_nextCell;

		if(compge)
			gptaMemMap.or32(offset, 0x10);
		else
			gptaMemMap.and32(offset, 0xef);
	}

	public static void captureAfterCompare(Memory gptaMemMap, int gtcsel, boolean capAfterComp) {
		int offset = OFS_GTCCTR00 + gtcsel*OFS_nextCell;
		if(capAfterComp)
			gptaMemMap.or32(offset, 0x20);
		else
			gptaMemMap.and32(offset, 0xdf);
	}

	public static void captureAlternateTimer(Memory gptaMemMap, int gtcsel, boolean capAltTimer) {
		int offset = OFS_GTCCTR00 + gtcsel*OFS_nextCell;
		if(capAltTimer)
			gptaMemMap.or32(offset, 0x40);
		else
			gptaMemMap.and32(offset, 0xbf);
	}

	public static void setBypass(Memory gptaMemMap, int gtcsel, boolean bypassEnabled) {
		int offset = OFS_GTCCTR00 + gtcsel*OFS_nextCell;
		if(bypassEnabled)
			gptaMemMap.or32(offset, 0x80);
		else
			gptaMemMap.and32(offset, 0x7f);
	}

	public static void setEnableOnAction(Memory gptaMemMap, int gtcsel, boolean eoa) {
		int offset = OFS_GTCCTR00 + gtcsel*OFS_nextCell + 1;
		if(eoa)
			gptaMemMap.or32(offset, 1);
		else
			gptaMemMap.and32(offset, 0xfe);
	}
}

