package test;

import keso.core.Task;
import keso.core.TaskService;

public class Task3 extends Task {
	public void launch() {
		DebugOut.println("Task3 running!\n");
		if (TaskService.getTaskID() == this)
			DebugOut.println("getTaskID ok!\n");
		else DebugOut.println("getTaskID erronous!\n");

		if( TaskService.getTaskState(this) == Task.RUNNING )
			DebugOut.println("getTaskState verified that I am indeed running\n");
		else DebugOut.println("getTaskState does not work\n");
		TaskService.terminate();
	}
}
