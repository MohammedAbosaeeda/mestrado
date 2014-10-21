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

public class TickerConsumer extends Task {

	final static int count = 20;
	final static Memory packet = MemoryService.allocStaticMemory(1024);
	final static char[] char_array = new char[10];

	public void launch() {

		TickerService srv = (TickerService)PortalService.lookup("TickerService");

		while (true) {

			DebugOut.println("roundtrip");	
			for (int i=0;i<count;i++) { srv.roundtrip(); }

			int c = 0;
			DebugOut.println("add");	
			for (int i=0;i<count;i++) { c=srv.add(c,1); }

			DebugOut.println("sleep");	
			for (int i=0;i<count;i++) { srv.sleep(); }

			DebugOut.println("send memory");
			for (int i=0;i<count;i++) { srv.sendPacket("hello",packet,char_array,0,64); }
		}
	}
}
