package kesocopter.dev.engine;

import keso.driver.tricore.tc1796b.modules.gpta.*;
import keso.core.*;

public class HWEngine {
	/**
	 * Thrustlevel of the engine, in steps of 0.1%.
	 */
	private short thrustlevel;
	
	/**
	 * HWEngine requires 3 LTCs for generating the
	 * signal, this is the index of the first one.
	 */
	private final byte dc_ltc;

	/**
	 * Memorymap of the GPTA/LCTA unit used.
	 */
	private final Memory ltcabase;

	private static final int PERIOD_LEN=36666;
	private static final int DUTY_CYCLE_MIN=1999;
	private static final int DUTY_CYCLE_INIT=1666;

	/**
	 * Make sure to select an 1.66 MHz input clock for the LTCA.
	 * The engine is controlled by a square wave signal with
	 * a frequency of 45.45 Hz (22ms period length). The duty
	 * cycle of this signal has a minimum of 1.2ms and a maximum
	 * of 1.8ms. 0.1% engine performance correspond to
	 * 0.6us additional duty cycle to the base of 1.2ms.
	 */
	public HWEngine(Memory ltcabaseMemMap, byte baseltc) {
		ltcabase=ltcabaseMemMap;
		thrustlevel=0; // start disabled
		this.dc_ltc = (byte) (baseltc+2);

		// Setup first LTC in Reset Timer mode
		LTC.setDataReg(ltcabase, baseltc, 0);
		LTC.setMode(ltcabase, baseltc, LTC.MODE_RESET_TIMER);
		LTC.set(ltcabase, baseltc, LTC.BIT_ILM); // level triggered
		LTC.setOCMMode(ltcabase, baseltc, LTC.OCM_MODE_HOLD | LTC.OCM_MODE_INTEVENT);

		// Setup 2nd LTC in Compare mode
		LTC.setDataReg(ltcabase, baseltc+1, PERIOD_LEN);
		LTC.setMode(ltcabase, baseltc+1, LTC.MODE_COMPARE);
		LTC.setOCMMode(ltcabase, baseltc+1, LTC.OCM_MODE_SET | LTC.OCM_MODE_INTEVENT);
		LTC.set(ltcabase, baseltc+1, LTC.BIT_SOL);
		LTC.set(ltcabase, baseltc+1, LTC.BIT_OIA);

		// Setup 3rd LTC in Compare mode
		LTC.setDataReg(ltcabase, dc_ltc, DUTY_CYCLE_INIT); // init
		LTC.setMode(ltcabase, dc_ltc, LTC.MODE_COMPARE);
		LTC.setOCMMode(ltcabase, dc_ltc, LTC.OCM_MODE_RESET | LTC.OCM_MODE_INTEVENT_OR_ADJACENT_LTC);
		LTC.set(ltcabase, dc_ltc, LTC.BIT_SOL);
	}

	public HWEngine(HWEngine ref, byte dc_ltc) {
		ltcabase=ref.ltcabase;
		thrustlevel=0; // start disabled
		this.dc_ltc = dc_ltc;

		// Setup 3rd LTC in Compare mode
		LTC.setDataReg(ltcabase, dc_ltc, DUTY_CYCLE_INIT); // init
		LTC.setMode(ltcabase, dc_ltc, LTC.MODE_COMPARE);
		LTC.setOCMMode(ltcabase, dc_ltc, LTC.OCM_MODE_RESET | LTC.OCM_MODE_INTEVENT_OR_ADJACENT_LTC);
		LTC.set(ltcabase, dc_ltc, LTC.BIT_SOL);
	}

	public void setThrustLevel(short percent) {
		if(percent>=0 && percent<=1000)
			thrustlevel=percent;

		LTC.setDataReg(ltcabase, dc_ltc, DUTY_CYCLE_MIN + thrustlevel);
	}
}

