/**(c)

  Copyright (C) 2005 Christian Wawersich

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.leds;

import keso.core.Task;
import keso.core.TaskService;

import keso.core.MemoryService;
import keso.core.Memory;

import keso.driver.tricore.tc1796b.HWPorts;

public class LEDTask extends Task {
	public void launch() {

		Memory port0 = MemoryService.allocStaticDeviceMemory(HWPorts.P0_BASE,HWPorts.Pn_SIZE);

		while (1) {
			for (int i=1;i<HWPorts.OUT__bit17;i++) {
				port0.set32(HWPorts.Pn_OUT_ADDR32,i);
				for (int j=1;j<1000;j++) {}
			}
			port0.set32(HWPorts.Pn_OUT_ADDR32,0);
			for (int j=1;j<1000;j++) {}
		}

		TaskService.terminate();
	}
}
