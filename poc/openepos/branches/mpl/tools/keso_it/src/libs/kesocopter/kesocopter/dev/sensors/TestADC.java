package kesocopter.dev.sensors;

import keso.core.Task;
import keso.core.TaskService;
import test.DebugOut;

import keso.driver.tricore.tc1796b.*;
import keso.driver.tricore.tc1796b.modules.*;
import keso.driver.tricore.tc1796b.modules.gpta.*;
import keso.driver.tricore.tc1796b.modules.adc.*;

import kesocopter.dev.engine.*;

public final class TestADC extends Task {
	static {
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

		// Setup Channel ADC.In0
		ADC.set(ADC.adc0, ADC.OFS_CHCON0, ADC.MASK_AREF0|ADC.MASK_RES10bit);
		ADC.set(ADC.adc0, ADC.OFS_CHCON1, ADC.MASK_AREF0|ADC.MASK_RES10bit);

		// Setup Timer as ADC trigger source
		SourceTimer.setTriggerMask(ADC.adc0, 3); // trigger conversion on In0, In1
		SourceTimer.setReloadValue(ADC.adc0, 14999); // every 100ms @ fTimer=50kHz

		// Setup ADC Arbiter
		ADC.adc0.or32(Arbiter.OFS_AP, Arbiter.MASK_TP);

		// enable timer after powerup is finished
		ADC.adc0.or32(Arbiter.OFS_SCON, Arbiter.MASK_TRS);

	};

	private HWEngine e1,e2;
	
	public TestADC() {
		e1 = new HWEngine(GPTA.gpta0, (byte)0);
		e2 = new HWEngine(GPTA.gpta0, (byte)3);
		
		IOLS.initclear(GPTA.gpta0);
		IOLS.connectLTCInput(IOLS.LIMG_MASK_CLOCK, (byte)0, (byte)0, (byte)0);
		IOLS.connectOutput(IOLS.MASK_LTCG0, (byte)2, IOLS.IOG0, (byte)0); // P2.8
		IOLS.connectLTCInput(IOLS.LIMG_MASK_CLOCK, (byte)0, (byte)0, (byte)3);
		IOLS.connectOutput(IOLS.MASK_LTCG0, (byte)5, IOLS.IOG0, (byte)1); // P2.9
		IOLS.program();
		
		GPIO.config((byte)2,(byte)8,GPIO.GPIO_OUTPUT_ALT1_PUSHPULL);
		GPIO.config((byte)2,(byte)9,GPIO.GPIO_OUTPUT_ALT1_PUSHPULL);
	}

	public void launch() {
		while(true) {
		int t1 = (ADC.adc0.get32(ADC.OFS_CHSTAT0) & 0xfff)>>>2;
		int t2 = (ADC.adc0.get32(ADC.OFS_CHSTAT1) & 0xfff)>>>2;

	
		e1.setThrustLevel((short) t1);
		e2.setThrustLevel((short) t2);
		}
	}
}
