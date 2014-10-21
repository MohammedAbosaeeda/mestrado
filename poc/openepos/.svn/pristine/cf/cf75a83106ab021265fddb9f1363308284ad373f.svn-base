package kesocopter.control;

import keso.core.Task;
import keso.core.TaskService;
import test.DebugOut;

import keso.driver.tricore.tc1796b.*;
import keso.driver.tricore.tc1796b.modules.*;
import keso.driver.tricore.tc1796b.modules.gpta.*;
import keso.driver.tricore.tc1796b.modules.adc.*;

import kesocopter.dev.engine.*;
import kesocopter.dev.sensors.*;
import kesocopter.dev.control.*;

public final class SimpleBalance extends Task {
	private Axis xAxis;
	private Poti tlevelCtrl;
	private Accelerometer xpsens, xasens;

	static {
		kesocopter.dev.Setup.setup();
	};

	public SimpleBalance() {
		HWEngine e1,e2;
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

		tlevelCtrl = new Poti(ADC.adc0, (byte) 0);
		xpsens = new Accelerometer(ADC.adc0,
				(byte) 3, (short) 524, (short) 506, (short) 544);
		xasens = new Accelerometer(ADC.adc0,
				(byte) 4, (short) 480, (short) 390, (short) 570);
		xAxis = new Axis(xpsens, xasens, e1, e2);
	}


	private int xx=0;
	public void launch() {
		short ctrl = tlevelCtrl.level();
	 	xAxis.setThrustLevel(ctrl);
	
		if(xx++ == 50) {	xx=0;
		DebugOut.println("----------------------");
		DebugOut.println("Current: "+ xpsens.getVal());
		DebugOut.println("Min: "+ xpsens.min);
		DebugOut.println("Max: "+ xpsens.max);
		DebugOut.println("Avg: "+ (int)(xpsens.total/xpsens.cnt));
		}
	}
}
