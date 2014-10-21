package lukas.device;

import keso.core.*;
import keso.driver.tricore.tc1796b.TricorePWR;

public final class PhotoSensor {
	public static final int NUM_SENSORS=8;

	private static byte mask=(byte)0;
	private static byte lastInterrupted;
	private static boolean risingEnabled;
	private static boolean fallingEnabled;
	private static byte errorOnBelow;

	public static final void init() {
		int value;
		for(int i=0; i<=7; i++)
			GPIO.config((byte)7,(byte) i, GPIO.GPIO_INPUT);

		mask = (byte) 0;
		lastInterrupted = noInterrupted();

		TricorePWR.pwr.set32(TricorePWR.PWR_OFS_EICR, 0x100);
		risingEnabled = false;
		disableFallingEdge();
		TricorePWR.pwr.or32(TricorePWR.PWR_OFS_EICR, 0x800);
		TricorePWR.pwr.set32(TricorePWR.PWR_OFS_IGCR, 0);

		TricoreDMA.dma.and32(
				TricoreDMA.DMA_OFS_SYSSRC+TricoreDMA.DMA_SYSSRC_NtoI(2)*4, ~0x1000);
		TricoreDMA.dma.set32(
				TricoreDMA.DMA_OFS_SYSSRC+TricoreDMA.DMA_SYSSRC_NtoI(2)*4, 3);
		TricoreDMA.dma.or32(
				TricoreDMA.DMA_OFS_SYSSRC+TricoreDMA.DMA_SYSSRC_NtoI(2)*4, 0x1000);

		errorOnBelow = (byte) -1;
	}

	public static final void enableRisingEdge() {
		risingEnabled=true;
	}
	public static final void disableRisingEdge() {
		risingEnabled=false;
	}
	public static final void enableFallingEdge() {
		fallingEnabled=true;
		TricorePWR.pwr.or32(TricorePWR.PWR_OFS_EICR, 0x200);
	}

	public static final void disableFallingEdge() {
		TricorePWR.pwr.and32(TricorePWR.PWR_OFS_EICR, ~0x200);
		fallingEnabled=false;
	}

	public static final void disableBothEdges() {
		disableFallingEdge();
		disableRisingEdge();
	}

	public static boolean status(byte sensorno) {
		if ((sensorno < 0) || (sensorno >= NUM_SENSORS))
			return false;

		return !GPIO.read((byte)7, sensorno);
	}

	public static byte noInterrupted() {
		for(int i=0; i<=7; ++i) {
			if(!GPIO.read((byte)7, (byte) i))
				return (byte) i;
		}
		return (byte)-1;
	}

	public static final void maskAll() {
		mask=(byte)0;
	}

	public static final void unmaskAll() {
		mask=(byte)0xff;
	}

	public static final void mask(byte sensorno) {
		if((sensorno >= (byte)0) && (sensorno <= (byte)7))
			mask &= ~(1<<sensorno);
	}

	public static final void maskAllButOne(byte sensorno) {
		mask = (byte) (1<<sensorno);
	}

	public static final void unmask(byte sensorno) {
		if((sensorno >= (byte)0) && (sensorno <= (byte)7))
			mask |= (1<<sensorno);
	}
	
	public static final boolean isMasked(byte sensorno) {
		if((sensorno >= (byte)0) && (sensorno <= (byte)7)) {
			return (mask & (1 << sensorno)) == 0;
		} else return true;
	}

	public static final void errorOnBelow(byte sensorno) {
		errorOnBelow = sensorno;
	}

	public static final void interrupt() {
		/* Determine the interrupted photosensor.
		 * Only rising edges can be detected.
		 * -1 will be returned upon a falling edge
		 */
		byte interrupted = noInterrupted();
		boolean risingEdge = (interrupted != -1);
		boolean doCall = true;

		if(risingEdge) {
			lastInterrupted = interrupted;
			if(!risingEnabled)
				doCall = false;
		} else {
			if((lastInterrupted == -1) || !fallingEnabled)
				doCall = false;
		}
		if(lastInterrupted < errorOnBelow) {
				lukas.Main.errorCallback(risingEdge, lastInterrupted);
			return;
		}
		if(isMasked(lastInterrupted)) doCall = false;

		if(doCall)
			lukas.Main.callback(risingEdge, lastInterrupted);
	}
}
