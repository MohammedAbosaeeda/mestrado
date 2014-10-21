/**
 *	This class contains functions common to all Tricore
 *	modules, namely operations on the Clock Control
 *	Registers (CLC) and the Fractional Divider
 *	Registers (FDR). These allow to enable/disable
 *	single modules and adjust the clock of the module.
 *
 *	@author mike
 */
package keso.driver.tricore.tc1796b.modules;

import keso.core.*;
import keso.driver.tricore.tc1796b.TricorePWR;

public final class ModControl {
	/*
	 * WARNING: In the Tricore 1796 manual, I found that
	 * these offsets are the same from the base address
	 * for all modules. The manual does, however, never
	 * state this explicitly, so it might be wrong.
	 */
	public static final int OFS_CLC=0;
	public static final int OFS_FDR=0xC;

	/**
	 * Enables the module
	 */
	public static final void enableModule(Memory moduleMemMap) {
		TricorePWR.unlockSystemRegisters();
		moduleMemMap.and32(OFS_CLC, ~1); 
		TricorePWR.lockSystemRegisters();
	}

	/**
	 * Disable the module
	 */
	public static final void disableModule(Memory moduleMemMap) {
		TricorePWR.unlockSystemRegisters();
		moduleMemMap.or32(OFS_CLC, 1); 
		TricorePWR.lockSystemRegisters();
	}

	/**
	 * Checks if the module is enabled
	 */
	public static final boolean isEnabled(Memory moduleMemMap) {
		return ( (moduleMemMap.get32(OFS_CLC)&2) == 0 );
	}

	/**
	 * Setup the FDR for 1/n division of the system frequency to
	 * generate the module frequency.
	 *
	 * @param moduleMemMap Memory map of the module
	 * @param div The divisor in the range of [1; 1024].
	 */
	public static final void setModuleClockDiv(Memory moduleMemMap, int div) {
		if(div<1 || div>1024) return;

		TricorePWR.unlockSystemRegisters();

		moduleMemMap.and32(OFS_FDR, 0x3c00);

		// Set FDR.STEP for div and FDR.DM to Normal Divider mode,
		// and enable the unit
		moduleMemMap.or32(OFS_FDR, (1024-div)|0x4000);

		// Enable the FDR
		moduleMemMap.and32(OFS_FDR, 0x7fffcfff);

		TricorePWR.lockSystemRegisters();
	}

	/**
	 * Setup the FDR for n/1024 division of the system frequency to
	 * generate the module frequency.
	 *
	 * @param moduleMemMap Memory map of the module
	 * @param div The divisor in the range of [0; 1023].
	 */
	public static final void setModuleClockDiv1024(Memory moduleMemMap, int div) {
		if(div<0 || div>1023) return;

		TricorePWR.unlockSystemRegisters();
		moduleMemMap.and32(OFS_FDR, 0x3c00);

		// Set FDR.STEP for div and FDR.DM to Normal Divider mode,
		// and enable the unit
		moduleMemMap.or32(OFS_FDR, div|0x8000);

		// Enable the FDR
		moduleMemMap.and32(OFS_FDR, 0x7fffcfff);

		TricorePWR.lockSystemRegisters();
	}
}
