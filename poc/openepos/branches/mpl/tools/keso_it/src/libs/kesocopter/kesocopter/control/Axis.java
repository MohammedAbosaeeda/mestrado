package kesocopter.control;

import keso.driver.tricore.tc1796b.*;
import keso.driver.tricore.tc1796b.modules.*;
import keso.driver.tricore.tc1796b.modules.gpta.*;
import keso.driver.tricore.tc1796b.modules.adc.*;

import kesocopter.dev.engine.*;
import kesocopter.dev.sensors.*;

public final class Axis {
	private Accelerometer positionSensor, accSensor;
	private HWEngine en, ep;
	private short thrustlevel;

	public Axis(Accelerometer posSensor, Accelerometer accSensor, HWEngine nEngine, HWEngine pEngine) {
		positionSensor=posSensor;
		this.accSensor=accSensor;
		en = nEngine;
		ep = pEngine;
		thrustlevel=0;
		lastvals = new short[16];
	}

	private short[] lastvals;
	private int li=0;
	private int ip=0;

	public void balance() {
		short dev = positionSensor.deviation();
		short diff = accSensor.deviation();
		short prop = (short)(thrustlevel/5 + 1);
		dev/=10;
	
		lastvals[li] = dev;
		if(li < 15) {
			li++; return;
		}
		li=0;
	
		int avg = 0;
		for(int i=0; i<16; i++)
			avg += lastvals[i];
		avg /= 16;

		/////////////
		dev = (short)0;
		dev += (short) ( avg  / prop);
	 	dev += (short) ( ip   / 4 );
		dev += (short) ( diff *6 / prop);
		ip += avg;
		////////////

		HWEngine inc, dec;
		if(dev < 0) {
			dev = (short) Math.abs(dev);
			inc = en;
			dec = ep;
		} else {
			inc = ep;
			dec = en;
		}

		short plevel = (short)(thrustlevel + dev);
		if(plevel > 1000) plevel=1000;
		inc.setThrustLevel(plevel);
		dec.setThrustLevel((short)(plevel-dev));
	}

	public void setThrustLevel(short thrustlevel) {
		if(thrustlevel < 0 || thrustlevel > 1000) return;
		this.thrustlevel = thrustlevel;
		balance();
	}
}
