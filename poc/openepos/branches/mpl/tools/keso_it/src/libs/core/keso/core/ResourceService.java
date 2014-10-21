/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.core;

public final class ResourceService {

	/**	
	 * This call serves to enter critical sections in the code that are assigned
	 * to the resource referenced by <ResID>. A critical section shall always
	 * be left using ReleaseResource.
	 *
	 * Nested resource occupation is only allowed if the inner critical sections
	 * are completely executed within the surrounding critical section (strictly stacked).
	 * Nested occupation of one and the same resource is also forbidden! It is
	 * recommended that corresponding calls to getResource and releaseResource
	 * appear within the same method or class.
	 *
	 * It is not allowed to use services which are points of rescheduling for non
	 * preemptable tasks (terminateTask,chainTask, schedule and waitEvent) in critical
	 * sections. Additionally, critical sections are to be left before completion of
	 * an interrupt service routine. Generally speaking, critical sections should be short.
	 * The service may be called from an ISR and from task level.
	 *
	 * Status:
	 *     Standard: No error (E_OK) 
	 *     Extended: Resource <ResID> is invalid (E_OS_ID) 
	 *		 Attempt to get a resource which is already occupied by any task or ISR,
	 *	 	 or the statically assigned priority of the calling task or interrupt
	 * 		 routine is higher than the calculated ceiling priority (E_OS_ACCESS)
	 */
	public static int getResource(Resource resource) { return 0; }

	/**	
	 * releaseResource is the counterpart of getResource and serves to leave critical 
	 * sections in the code that are assigned to the resource referenced by <ResID>.
	 *
	 * For information on nesting conditions, see getResource.
	 *
	 * Status:
	 *     Standard: No error (E_OK) 
	 *     Extended: Resource <ResID> is invalid (E_OS_ID) 
	 *		 Attempt to get a resource which is already occupied by any task or ISR,
	 *	 	 or the statically assigned priority of the calling task or interrupt
	 * 		 routine is higher than the calculated ceiling priority (E_OS_ACCESS)
	 */
	public static int releaseResource(Resource resource) { return 0; }

	/*					KESO extensions */

	/**
	 * Get a reference to a Resource object by specifying its name as it was
	 * defined in kesorc.
	 * The special name RES_SCHEDULER may be used to get a reference to the
	 * Scheduler resource, but only if USERESSCHEDULER was set to true in kesorc.
	 *
	 * The String must be a constant as it will be internally resolved at compile
	 * time.
	 *
	 * null will be returned if the Resource cannot be found.
	 *
	 */
	public static Resource getResourceByName(String name) {	return null; }

	/**
	 * Occupy the special Scheduler Resource.
	 * The OSEK scheduler Resource is not occupied using the
	 * regular getResource() service. Instead, this method
	 * is used.
	 */
	public static int getScheduler() { return 0; }

	/**
	 * Release the special Scheduler Resource.
	 * The OSEK scheduler Resource is not released using the
	 * regular releaseResource() service. Instead, this method
	 * is used.
	 */
	public static int releaseScheduler() { return 0; }
}
