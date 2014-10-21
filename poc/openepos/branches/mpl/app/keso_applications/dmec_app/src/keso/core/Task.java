/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.core;

public abstract class Task implements NonCopyable {

	// These TaskState values are ProOSEK dependant
	public static final int SUSPENDED    = 0;
	public static final int READY        = 1;
	public static final int RUNNING      = 2;
	public static final int WAITING      = 3;

	public static final Task INVALID_TASK = null;

	/* these fields are initialised by the keso runtime system */
	//private byte domain_id;
	/* effective domain id */
	//private byte e_domain_id;
	/* OSEK/VDX task id */
	//private byte task_id;

	protected Task() {  }

	public abstract void launch();

	public void UncaughtExceptionHandler() {
		/* TerminateTask */
	}
}
