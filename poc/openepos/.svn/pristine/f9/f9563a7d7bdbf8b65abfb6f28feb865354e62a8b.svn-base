package kesocopter.dev;

import keso.core.Task;
import keso.core.TaskService;
import test.DebugOut;

import keso.driver.tricore.tc1796b.*;
import keso.driver.tricore.tc1796b.modules.*;
import keso.driver.tricore.tc1796b.modules.gpta.*;
import keso.driver.tricore.tc1796b.modules.adc.*;

/**
 * This class performs general setup work to
 * activate and initialize the used modules.
 */
public final class Setup {
	public static void setup() {
		keso.driver.tricore.tc1796b.TricoreSTM.setRMC(1);
		keso.driver.tricore.tc1796b.TricorePWR.setCPUFrequency();

		// enable GPTA clock
		ModControl.enableModule(GPTA.gpta0);
		
		// select fGPTA as 1.66.. MHz (each 0.6us)
		ModControl.setModuleClockDiv(GPTA.gpta0, 45);

		// enable GPTA0 clock
		GPTA.enableGPTAClock(GPTA.G0EN);

		// setup CDU
		GPTA.setupClockDistributionUnit(GPTA.gpta0, (byte) 7,
				(byte) 0, (byte) 12, (byte) 13, (byte) 1 /*CLK7*/, (byte) 7);

		// enable ADC Module
		ModControl.enableModule(ADC.adc0);
		ModControl.setModuleClockDiv(ADC.adc0, 25); // 3 MHz

		// Setup Timer as ADC trigger source
		SourceTimer.setReloadValue(ADC.adc0, 14999); // every 100ms @ fTimer=50kHz

		// Setup ADC Arbiter
		ADC.adc0.or32(Arbiter.OFS_AP, Arbiter.MASK_TP);

		// enable timer after powerup is finished
		ADC.adc0.or32(Arbiter.OFS_SCON, Arbiter.MASK_TRS);
	}
}
