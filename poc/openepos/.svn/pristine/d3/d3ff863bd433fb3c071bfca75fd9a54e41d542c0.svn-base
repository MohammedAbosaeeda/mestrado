package keso.driver.tricore.tc1796b.modules.adc;

import keso.core.*;

/**
 * Tricore Analog-to-Digital Converters.
 *
 * @author mike
 */

public final class ADC {
	public static final int ADC0_BASE = 0xf0100400;
	public static final int ADC1_BASE = 0xf0100600;
	
	public static final Memory adc0 =  MemoryService.allocStaticDeviceMemory(ADC0_BASE, 2*256);
	public static final Memory adc1 =  MemoryService.allocStaticDeviceMemory(ADC1_BASE, 2*256);
	
	public static final int NXT_CHCON = 4;
	public static final int OFS_CHCON0 = 0x10;
	public static final int OFS_CHCON1 = 0x14;
	public static final int OFS_CHCON2 = 0x18;
	public static final int OFS_CHCON3 = 0x1c;
	public static final int OFS_CHCON4 = 0x20;
	public static final int OFS_CHCON5 = 0x24;
	public static final int OFS_CHCON6 = 0x28;
	public static final int OFS_CHCON7 = 0x2c;
	public static final int OFS_CHCON8 = 0x30;
	public static final int OFS_CHCON9 = 0x34;
	public static final int OFS_CHCON10 = 0x38;
	public static final int OFS_CHCON11 = 0x3c;
	public static final int OFS_CHCON12 = 0x40;
	public static final int OFS_CHCON13 = 0x44;
	public static final int OFS_CHCON14 = 0x48;
	public static final int OFS_CHCON15 = 0x4c;
	
	public static final int OFS_AP =  0x84;
	public static final int OFS_SAL = 0x88;
	public static final int OFS_TTC = 0x8c;
	public static final int OFS_EXTC = 0x90;
	public static final int OFS_SCON = 0x98;
	public static final int OFS_LCCON0 = 0x100;
	public static final int OFS_LCCON1 = 0x104;
	public static final int OFS_LCCON2 = 0x108;
	public static final int OFS_LCCON3 = 0x10c;
	
	public static final int OFS_TCON = 0x114;
	public static final int OFS_CHIN = 0x118;
	public static final int OFS_QR = 0x11c;
	public static final int OFS_CON = 0x120;
	public static final int OFS_SCN = 0x124;
	public static final int OFS_REQ0 = 0x128;
	public static final int NXT_CHSTAT = 4;
	public static final int OFS_CHSTAT0 = 0x130;
	public static final int OFS_CHSTAT1 = 0x134;
	public static final int OFS_CHSTAT2 = 0x138;
	public static final int OFS_CHSTAT3 = 0x13c;
	public static final int OFS_CHSTAT4 = 0x140;
	public static final int OFS_CHSTAT5 = 0x144;
	public static final int OFS_CHSTAT6 = 0x148;
	public static final int OFS_CHSTAT7 = 0x14c;
	public static final int OFS_CHSTAT8 = 0x150;
	public static final int OFS_CHSTAT9 = 0x154;
	public static final int OFS_CHSTAT10= 0x158;
	public static final int OFS_CHSTAT11= 0x15c;
	public static final int OFS_CHSTAT12= 0x160;
	public static final int OFS_CHSTAT13= 0x164;
	public static final int OFS_CHSTAT14= 0x168;
	public static final int OFS_CHSTAT15= 0x16c;
	public static final int OFS_QUEUE0 = 0x170;

	public static final int OFS_TSTAT = 0x1b0;
	public static final int OFS_STAT = 0x1b4;
	public static final int OFS_TCRP = 0x1b8;



	


	/**
	 * Modul aktivieren
	 * Configure Input Lines
	 * Timer konfigurieren
	 *
	 * Timer als Inputquelle
	 *
	 * CHCONn.GRPS
	 */

	/**
	 * Configure Request Sources.
	 */
	public static final int MASK_AREF0 = 0x0000;
	public static final int MASK_AREF1 = 0x0100;
	public static final int MASK_AREF2 = 0x0200;
	public static final int MASK_AREF3 = 0x0300;
	public static final int MASK_RES8bit =  0x0800; 
	public static final int MASK_RES10bit = 0x0000; 
	public static final int MASK_RES12bit = 0x0400;
	public static final int MASK_GRPS_IMG0=0x0;
	public static final int MASK_GRPS_IMG1=0x4000;

	public static final void set(Memory adcMemMap, int regOffset, int setMask) {
		adcMemMap.or32(regOffset, setMask);
	}
	public static final void unset(Memory adcMemMap, int regOffset, int unsetMask) {
		adcMemMap.and32(regOffset, ~unsetMask);
	}
}

