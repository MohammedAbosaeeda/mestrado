package kesocopter.dev.sensors;

import keso.core.Memory;

import keso.driver.tricore.tc1796b.*;
import keso.driver.tricore.tc1796b.modules.*;
import keso.driver.tricore.tc1796b.modules.gpta.*;
import keso.driver.tricore.tc1796b.modules.adc.*;

import kesocopter.dev.engine.*;

public final class Accelerometer {
	private final Memory adcMap;
	private final short setpoint, mindev, maxdev;
	private final byte channel;

	public Accelerometer(Memory adcMap, byte channel /* 0 - 31 */, short setpoint, short min, short max) {
		int grpsmask = (channel & 0x10)==0 ? 0 : ADC.MASK_GRPS_IMG1;
		channel &= 0xf;

		this.adcMap = adcMap;	
		this.setpoint = setpoint;
		mindev = (short) (min - setpoint);
		maxdev = (short) (max - setpoint);
		this.channel = channel;

		// configure AD channel
		ADC.set(adcMap, ADC.OFS_CHCON0 + (channel * ADC.NXT_CHCON),
				ADC.MASK_AREF0|ADC.MASK_RES10bit|grpsmask);
		// activate timer trigger source for the channel
		SourceTimer.activateTrigger(adcMap, channel);
	}

	public short min=1024, max=0,last;
	public long total=0L;
	public short cnt =0;

	public short getVal() {
		short current = (short)((adcMap.get32(
						ADC.OFS_CHSTAT0+ADC.NXT_CHSTAT*channel) & 0xfff)>>>2);
		last=current;
		total += current;
		cnt++;
		if(current > max) max = current;
		if(current < min) min = current;
		return current;
	}

	public short deviation() {
		short current = (short)((adcMap.get32(
						ADC.OFS_CHSTAT0+ADC.NXT_CHSTAT*channel) & 0xfff)>>>2);
		short deviation = (short) (current - setpoint);

		if(deviation < 0) {
			if(deviation < mindev) return (short) -100;
			return (short) (-(deviation * 100)/mindev);
		}
		if(deviation > maxdev) return (short) 100;
		return (short) ((100*deviation)/maxdev);
	}
}
