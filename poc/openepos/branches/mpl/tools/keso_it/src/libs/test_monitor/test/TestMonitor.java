package test;

import keso.core.Task;
import keso.core.TaskService;
import keso.core.Events;
import keso.core.EventService;
import keso.core.InterruptService;

class SomeMonitor {

	private String name;

	public SomeMonitor(String name) {
		this.name = name;
	}

	//public synchronized void doSomething(String threadName) {
	public void doSomething(String threadName) {
		DebugOut.print("Thread: " + threadName + " entering in monitor: " + name);

		while (true) {		
			DebugOut.print("Thread: " + threadName + " doing something in monitor: " + name);

			//EventService.waitEvent(Events.Wakeup);
			//EventService.clearEvent(Events.Wakeup);		
		}
	}

} 

class SomeThread extends Task {
	
	private String name;
	private SomeMonitor monitor;

	public SomeThread(String threadName, SomeMonitor monitor) {
		this.name = threadName;
		this.monitor = monitor;	
	}

	public void launch() {
		DebugOut.print("Thread: " + name + " SomeThread::launch");
		monitor.doSomething(name);
	}

}


public class TestMonitor extends Task {

	public void launch() {
		int runs = 0;

		SomeMonitor xMonitor = new SomeMonitor("X");
		SomeMonitor yMonitor = new SomeMonitor("Y");
		
		SomeThread alpha = new SomeThread("Alpha", xMonitor);
		SomeThread beta = new SomeThread("Beta", yMonitor);
		//SomeThread beta = new SomeThread("Beta", xMonitor);

		alpha.launch();
		beta.launch();		
				
		while(true) {
			DebugOut.print("M: ");

			DebugOut.println(runs);

			EventService.waitEvent(Events.Wakeup);
			EventService.clearEvent(Events.Wakeup);		
		}
		
	}

	protected void finalize() {

	}
}
