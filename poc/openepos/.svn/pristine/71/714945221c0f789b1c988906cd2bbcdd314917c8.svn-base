/**(c)

  Copyright (C) 2006 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package test;

import keso.core.*;

public class TickerServiceTask extends Task implements TickerService, Service {

	final static boolean verbose = false;

	public void roundtrip() { 
		if (verbose) DebugOut.println("roundtrip called");
		return;
	}

	public void sleep() {
		if (verbose) DebugOut.println("sleep called ");
		EventService.waitEvent(Events.Wakeup);
		EventService.clearEvent(Events.Wakeup);
	}

	public int add(int a, int b) { 
		if (verbose) {
			DebugOut.print("add called");
			DebugOut.print(" a + b = ");
			DebugOut.print(a+b);
			DebugOut.println();
		}
		return a+b;
	}

	public String sendPacket(String name, Memory data, char[] addr, int offset, int len) {
		if (verbose) DebugOut.println("send called"+name);
		return name;
	}

	public void launch() {
		DebugOut.println("Server task called");
		TaskService.terminate();
	}
}
