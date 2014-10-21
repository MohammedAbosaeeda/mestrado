/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.core;

/**
 * Represents the OSEK AlarmBaseType.
 *
 * ProOSEK only used unsigned char for all of those values.
 * The Java byte is not sufficient because of signedness, so we
 * use int here to overcome that problem.
 */
public final class AlarmBase {
	/**
	 * Maximum allowed value before the counter rolls over
	 */
	public int maxallowedvalue;

	/**
	 * Number of ticks required to reach a counter specific unit
	 */
	public int ticksperbase;

	/**
	 * Minimum number of ticks required for a cyclic alarm (extended mode only)
	 */
	public int mincycle;

	public AlarmBase() { }
}

