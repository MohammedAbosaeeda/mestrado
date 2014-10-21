/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.core;

public final class OSService {
	/**
	 * Returns the current application mode. May be used to write mode dependant
	 * code.
	 */
	public static int getActiveApplicationMode() { return 0; }

	/**
	 * The user may use this call to start the OS in a specific application mode.
	 * 
	 * May only be called from outside the OS.
	 */
	public static void startOS(int appMode) {}

	/**
	 * This service may be called to abort the whole system (emergency off).
	 *
	 * If a ShutdownHook is specified, it is always called with <error> as
	 * parameter before the system is shut off.
	 */
	public static void shutdownOS(int error) {}
}
