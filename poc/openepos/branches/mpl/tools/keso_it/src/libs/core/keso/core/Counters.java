/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

/**
 * This class contains the OSEK counter constants.
 * These are the return values of GetAlarmBase. Calls
 * to GetAlarmBase can be spared if the name of the
 * underlying counter is known.
 */
package keso.core;

public final class Counters {
	private Counters() {}

	public static final int OSTICKSPERBASE = 0x0;
	public static final int OSMAXALLOWEDVALUE = 0x0;
	public static final int OSMINCYCLE = 0x0;
	public static final int OSTICKDURATION = 0x0;

}
