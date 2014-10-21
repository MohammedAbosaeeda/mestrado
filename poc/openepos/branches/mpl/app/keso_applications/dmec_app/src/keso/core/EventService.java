/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.core;

public final class EventService {

	/**
	 * Sets one or more events specified in <event> for the extended task
	 * specified by <taskID>.
	 *
	 * May be called from any task and ISR cat.2
	 */
	public static int setEvent(Task taskID, int evMask) { return 0; }
	
	/**
	 * Events of the calling extended task are cleared according to <event>.
	 */
	public static int clearEvent(int evMask) { return 0; }

	/**
	 * Query the events of a task specified by <taskID>.
	 * The result is returned as eventMask.
	 *
	 * May be called from any task and ISR cat.2
	 *
	 * TODO this does not return the statustype. Throw an OSEKException
	 * in case of an error. We should do this generally for all our syscalls
	 * and make an OSEKException class that has the StatusType encapsulated.
	 */
	public static int getEvent(Task taskID) { return 0; }

	/**
	 * Wait until at least one of the events specified by <event> are set
	 * for the calling extended Task.
	 */
	public static int waitEvent(int evMask) { return 0; }
}
