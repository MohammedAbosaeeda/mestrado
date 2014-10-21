package kesocopter.dev.engine;

import keso.core.Task;
import keso.core.TaskService;
import test.DebugOut;

import keso.driver.tricore.tc1796b.*;
import keso.driver.tricore.tc1796b.modules.*;
import keso.driver.tricore.tc1796b.modules.gpta.*;

public final class TestHWEngine extends Task {
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
	};

	public TestHWEngine() {
		int cpufreq, sysfreq;
		e1 = new HWEngine(GPTA.gpta0, (byte)0);
		e2 = new HWEngine(e1, (byte)3);
		
		IOLS.initclear(GPTA.gpta0);
		IOLS.connectLTCInput(IOLS.LIMG_MASK_CLOCK, (byte)0, (byte)0, (byte)0);
		IOLS.connectOutput(IOLS.MASK_LTCG0, (byte)2, IOLS.IOG0, (byte)0); // P2.8
		IOLS.connectOutput(IOLS.MASK_LTCG0, (byte)2, IOLS.IOG0, (byte)1); // P2.9
		IOLS.program();
		
		GPIO.config((byte)2,(byte)8,GPIO.GPIO_OUTPUT_ALT1_PUSHPULL);
		GPIO.config((byte)2,(byte)9,GPIO.GPIO_OUTPUT_ALT1_PUSHPULL);
		
		cpufreq = keso.driver.tricore.tc1796b.TricorePWR.getCPUFrequency();
		sysfreq = keso.driver.tricore.tc1796b.TricorePWR.getSystemFrequency();
		DebugOut.println("CPU @ " + cpufreq/1000000 + " MHz");
		DebugOut.println("System @ " + sysfreq/1000000 + " MHz");
	}

	private HWEngine e1,e2;
	private int tlevel=0;
	private int initthreshold=0;
	private boolean init=true, tup=true;

	public void launch() {
		if(init) {
			if(initthreshold++ < 20) return;
		//	if(tlevel==0)
		//		tlevel=200;
		//	else {
		//		tlevel=0;
				init=false;
		//	}
		} else {
			if(tup) tlevel++;
			else tlevel--;
			
			if(tlevel==0 || tlevel==1000)
				tup = !tup;
		}
		if( 0 == (tlevel % 50))
			DebugOut.println("Setting Thrustlevel to " + tlevel/10 + "%");
		e1.setThrustLevel((short)tlevel);
		e2.setThrustLevel((short)tlevel);
	}
}
