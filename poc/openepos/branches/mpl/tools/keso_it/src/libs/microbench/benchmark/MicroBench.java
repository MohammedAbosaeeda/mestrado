/**(c)

  Copyright (C) 2005-2007 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi.fau.de for more info.

  (c)**/

package benchmark;

import keso.core.Task;
import keso.core.TaskService;
import keso.core.Events;
import keso.core.EventService;
import keso.core.InterruptService;
import test.DebugOut;

class VoidTask1 extends Task {
	static long n;
	public void launch() {
		n++;	TaskService.chain(this);
//		n++;	TaskService.activate(this); TaskService.terminate();
//		while (true) { TaskService.schedule(); n++; }
	}
}

class VoidTask2 extends Task {

	public Task t1;

	public void launch() {
		switch (MicroBench.action) {
			case MicroBench.TERM:
				TaskService.terminate();
				break;
			case MicroBench.CHAIN:
				TaskService.chain(t1);
				break;
			default:
				/* we do nothing */
		}
	}
}

public class MicroBench extends Task {

	private final boolean call_schedule = true;
	private final static int runs = 1000;

	final static int TERM = 0;
	final static int CHAIN = 1;

	static int action = TERM;

	private VoidTask1 t1;
	private VoidTask2 t2;


	private void init() {
		t1 = (VoidTask1)TaskService.getTaskByName("vtask1");
//		t2 = (VoidTask2)TaskService.getTaskByName("vtask2");
//		t2.t1 = t1;
	}


	private void measurement_task1_activation() {
		for (int r=0;r<runs;r++) {
			TaskService.activate(t1);
			TaskService.schedule();
		}
	}

	private void measurement_task2_activation() {
		for (int r=0;r<runs;r++) {
			TaskService.activate(t2);
			TaskService.schedule();
		}
	}

	private void measurement_task2_l2_term() {
		action = TERM;
		for (int r=0;r<runs;r++) {
			TaskService.activate(t2);
			TaskService.schedule();
		}
	}

	private void measurement_task2_l2_chain() {
		action = CHAIN;
		for (int r=0;r<runs;r++) {
			TaskService.activate(t2);
			TaskService.schedule();
		}
	}

	private void measurement_task_getState() {
		int vt;
		Timer.startPoint("GetTaskState");
		for (int r=0;r<runs;r++) {
			vt = TaskService.getTaskState(t1);
		}
		Timer.endPoint("GetTaskState");
	}

	private void measurement_task_getID() {
		Task vt;
		for (int r=0;r<runs;r++) {
			vt = TaskService.getTaskID();
		}
	}

	private void measurement_task_getName() {
		Task vt;
		for (int r=0;r<runs;r++) {
			vt = TaskService.getTaskByName("vtask1");
		}
	}

	public void launch() {
		init();
		TaskService.chain(t1);
	}

	private static void startup() {
		keso.driver.tricore.tc1796b.TricorePWR.setCPUFrequency();
		keso.driver.tricore.tc1796b.TricoreSTM.setRMC(1);
	}
}
