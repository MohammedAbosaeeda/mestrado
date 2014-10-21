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
 * This class contains ProOSEK services (these are not defined in the OSEK spec)
 * to deal with counters.
 * 
 * The services of this class are especially useful to advance user managed
 * counters that do not have a hardware timer attached.
 */

public final class CounterService {
	/**
	 * Advance <counter> by one from task level.
	 */
	public static int advanceCounter(int counterID) { return 0; }

	/**
	 * Advance <counter> by one from an ISR.
	 *
	 * The user must ensure that calls to iAdvanceCounter do not overlap.
	 */
	public static int iAdvanceCounter(int counterID) { return 0; }
}
