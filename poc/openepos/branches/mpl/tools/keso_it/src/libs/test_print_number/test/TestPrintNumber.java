package test;

import keso.core.Task;
import keso.core.Events;
import keso.core.EventService;

public class TestPrintNumber extends Task {

	public void launch() {
		int runs = 0;
				
		while(true) {
			DebugOut.print("c: ");

			DebugOut.println(runs);

			EventService.waitEvent(Events.Wakeup);
			EventService.clearEvent(Events.Wakeup);		
		}
		
	}

	protected void finalize() {
	}
}