package test;

import keso.core.Task;
import keso.core.TaskService;
import keso.core.Events;
import keso.core.EventService;

class BClass extends HelloWorld {
	static String o2;
}

public class HelloWorld extends Task {

	static String o1;
	static int i1;
	static BClass o3;

	public void launch() {
		try {
			DebugOut.println("Hello World! Will now wait for ev1");

			EventService.clearEvent(Events.ev1);
			EventService.waitEvent(Events.ev1);

			DebugOut.println("ev1 has been set. Proceeding forward...");
		} catch (Exception ex) {
			DebugOut.println("Exception caught");
		}

		TaskService.terminate();
	}
}
