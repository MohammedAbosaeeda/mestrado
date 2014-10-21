package test;

import keso.core.Task;
import keso.core.Events;
import keso.core.EventService;

public class TestPrintOneNumber extends Task {

	public void launch() {
		int runs = 0;
		
		/* also works on here*/
		//EventService.waitEvent(Events.Wakeup);
		//EventService.clearEvent(Events.Wakeup);		
		DebugOut.print("Print just one time: ");
		DebugOut.println(runs);
		EventService.waitEvent(Events.Wakeup);
		EventService.clearEvent(Events.Wakeup);		
	}
}