/**(c)

  Copyright (C) 2005 Christian Wawersich

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/
package keso.driver.tricore.tc1796b;
import keso.core.*;

/**
 * This class allows the configuration of the
 * STM system timers of the Tricore TC1796
 * board.
 *
 * TODO port to use MemoryMapped types
 */
public final class TricoreSTM {
	public static final int STM_BASE = 0xf0000200;
	public static final int STM_SIZE = 252;

	public static final int STM_OFS_MODHDR_CLC=0;
	/* reserved */
	public static final int STM_OFS_MODHDR_ID =8;
	/* reserved */
	public static final int STM_OFS_TIM0      =16;
	public static final int STM_OFS_TIM1      =20;
	public static final int STM_OFS_TIM2      =24;
	public static final int STM_OFS_TIM3      =28;
	public static final int STM_OFS_TIM4      =32;
	public static final int STM_OFS_TIM5      =36;
	public static final int STM_OFS_TIM6      =40;
	public static final int STM_OFS_CAP       =44;
	public static final int STM_OFS_CMP       =48; /* 2 Regs */
	public static final int STM_OFS_CMCON     =56; /* 2 16bit Regs */
	public static final int STM_OFS_ICR       =60;
	public static final int STM_OFS_ISRR      =64;
	/* 45 32-bit reserved */
	public static final int STM_OFS_SRC1      =244;
	public static final int STM_OFS_SRC0      =248;


	private static final Memory stm =
		MemoryService.allocStaticDeviceMemory(STM_BASE, STM_SIZE);

	/**
	 * Configure Module Frequency of the STM timer module.
	 * This function furthermore fully activates the timer and
	 * disables any suspend modes.
	 *
	 * @param rmc Clock Divider in run mode. The system clock
	 *   is divided by the rmc to generate the clock for the
	 *   STM timer. The special value 0 disables the clock
	 *   signal for the STM timer. Range 0..7
	 */
	public static final void setRMC(int rmc) {
		if (rmc<0 || rmc>7) return;

		TricorePWR.unlockSystemRegisters();
		stm.set32(STM_OFS_MODHDR_CLC, rmc<<8);
		TricorePWR.lockSystemRegisters();
	}
}
