/**(c)

  Copyright (C) 2006 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package test;

import keso.core.Portal;
import keso.core.Memory;

public interface TickerService extends Portal {
	public void roundtrip();
	public int  add(int a, int b);
	public void sleep();
	public String sendPacket(String name, Memory data, char[] addr, int offset, int len);
}
