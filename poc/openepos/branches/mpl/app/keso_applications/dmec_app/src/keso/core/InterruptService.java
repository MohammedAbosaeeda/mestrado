/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.core;

public final class InterruptService {

	/**
	 * This service disables all interrupts for which the hardware supports
	 * disabling. The state before is saved for the enableAll() call.
	 *
	 * The service may be called from an ISR category 1 and category 2 and 
	 * from the task level, but not from hook routines. This service is 
	 * intended to start a critical section of the code. This section shall 
	 * be finished by calling the EnableAllInterrupts service.
	 * No API service calls are allowed within this critical section. The
	 * implementation should adapt this service to the target hardware 
	 * providing a minimum overhead. Usually, this service disables recognition
	 * of interrupts by the central processing unit. Note that this service
	 * does not support nesting. If nesting is needed for critical sections
	 * e.g. for libraries suspendOS/resumeOS or suspendAll/resumeAll should be used.
	 *
	 * Status:
	 *     Standard: none 
	 *     Extended: none 
	 */
	public static void disableAll() {};

	/**
	 * This service restores the state saved by DisableAllInterrupts.
	 *
	 * The service may be called from an ISR category 1 and category 2
	 * and from the task level, but not from hook routines. This service 
	 * is a counterpart of disableAll service, which has to be called
	 * before, and its aim is the completion of the critical section
	 * of code. No API service calls are allowed The implementation should 
	 * adapt this service to the target hardware providing a minimum overhead.
	 * Usually, this service enables recognition of interrupts by the
	 * central processing unit.
	 *
	 * Status:
	 *     Standard: none 
	 *     Extended: none 
	 */
	public static void enableAll() {};

	/**
	 * This service saves the recognition status of all interrupts and disables
	 * all interrupts for which the hardware supports disabling.
	 *
	 * The service may be called from an ISR category 1 and category 2, from 
	 * alarm-callbacks and from the task level, but not from all hook routines.
	 * This service is intended to protect a critical section of code from
	 * interruptions of any kind. This section shall be finished by calling the
	 * resumeAll service.
	 * No API service calls beside suspendAll/resumeAll pairs and suspendOS/resumeOS
	 * pairs are allowed within this critical section. 
	 *
	 * The implementation should adapt this service to the target hardware providing
	 * a minimum overhead.
	 * 
	 * Status:
	 *     Standard: none 
	 *     Extended: none 
	 */
	public static void suspendAll() {};

	/**
	 * This service restores the recognition status of all interrupts saved by
	 * the suspendAll service.
	 *
	 * The service may be called from an ISR category 1 and category 2, from
	 * alarm-callbacks and from the task level, but not from all hook routines.
	 * This service is the counterpart of suspendAll service, which has
	 * to have been called before, and its aim is the completion of the critical 
	 * section of code. No API service calls beside suspendAll/resumeAll pairs
	 * and suspendOS/resumeOS pairs are allowed within this critical section.
	 *
	 * Status:
	 *     Standard: none 
	 *     Extended: none 
	 */
	public static void resumeAll() {};

	/**
	 * This service saves the recognition status of interrupts of category 2
	 * and disables the recognition of these interrupts.
	 *
	 * The service may be called from an ISR and from the task level, This
	 * service is intended to protect a critical section of code. This section
	 * shall be finished by calling the resumeOS service. No API
	 * service calls beside suspendAll/resumeAll pairs and suspendOS/resumeOS
	 * pairs are allowed within this critical section.
	 *
	 * The implementation should adapt this service to the target hardware 
	 * providing a minimum overhead. It is intended only to disable interrupts
	 * of category 2. However, if this is not possible in an efficient way
	 * more interrupts may be disabled.
	 *
	 * Status:
	 *     Standard: none 
	 *     Extended: none 
	 */
	public static void suspendOS() {};

	/**
	 * This service restores the recognition status of interrupts saved by the 
	 * suspendOS service.
	 *
	 * The service may be called from an ISR category 1 and category 2 and from
	 * the task level, but not from hook routines. This service is the counterpart
	 * of suspendOS service, which has to have been called before, and its aim
	 * is the completion of the critical section of code. No API service calls
	 * beside suspendAll/resumeAll pairs and suspendOS/resumeOS pairs are allowed
	 * within this critical section. The implementation should adapt this service
	 * to the target hardware providing a minimum overhead. suspendOS/resumeOS
	 * can be nested. In case of nesting pairs of the calls suspendOS and resumeOS
	 * the interrupt recognition status saved by the first call of suspendOS
	 * is restored by the last call of the resumeOS service.
	 *
	 * Status:
	 *     Standard: none 
	 *     Extended: none 
	 */
	public static void resumeOS() {};
	
	public static int getIRQLevel(String isrName) { return -1; };
}

