package test;

import keso.core.Task;
import keso.core.TaskService;

class TaskGamma extends Task {
	
	public void launch() {
		int n = 33;
		DebugOut.print("Hi, I'm a TaskGamma instance!");
		DebugOut.println(n);
	}
}

class TaskAlpha extends Task {
	
	public void launch() {
		int n = 22;
		DebugOut.print("Hi, I'm a TaskAlpha instance!");
		DebugOut.println(n);
	}
}

public class TestSomeTasks extends Task {

	//private TaskBeta tb;

	public void launch() {
		int n = 11;
		DebugOut.print("Hi, I'm a TestSomeTasks instance!");
		DebugOut.println(n);
		/*
		int n = 10;
		DebugOut.print("Hello I am the main task!");
		DebugOut.println(n);
		tb = (TaskBeta) TaskService.getTaskByName("task_beta");
		TaskService.chain(tb);
		DebugOut.print("bye!");
		DebugOut.println(n);
		*/
	}
}