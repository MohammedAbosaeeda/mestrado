package kesocopter.dev.control;

import keso.core.Memory;
import keso.driver.tricore.tc1796b.*;
import keso.driver.tricore.tc1796b.modules.*;
import keso.driver.tricore.tc1796b.modules.gpta.*;
import keso.driver.tricore.tc1796b.modules.adc.*;

import kesocopter.dev.engine.*;

public final class Poti {
	private final Memory adcMap;
	private final byte channel;

	public Poti(Memory adcMap, byte channel /* 0 - 31 */) {
		int grpsmask = (channel & 0x10)==0 ? 0 : ADC.MASK_GRPS_IMG1;
		channel &= 0xf;

		this.adcMap = adcMap;	
		this.channel = channel;

		// configure AD channel
		ADC.set(adcMap, ADC.OFS_CHCON0 + (channel * ADC.NXT_CHCON),
				ADC.MASK_AREF0|ADC.MASK_RES10bit|grpsmask);
		// activate timer trigger source for the channel
		SourceTimer.activateTrigger(adcMap, channel);
	}

	public short level() {
		short current = (short)((adcMap.get32(
						ADC.OFS_CHSTAT0+ADC.NXT_CHSTAT*channel) & 0xfff)>>>2);
		if(current>1000)
			return (short) 1000;
		return current;
	}
}
