package test;

import keso.core.Task;
import keso.core.TaskService;
import keso.core.ResourceService;
import keso.core.Resource;
import keso.core.Events;
import keso.core.EventService;
import keso.core.AlarmService;
import keso.core.Alarm;
import keso.core.AlarmBase;
import keso.core.MemoryService;
import keso.core.Memory;

public class Task2 extends Task {
	private Task t1;
	private Resource myRes, resSched;
	private Alarm a1;
	private AlarmBase abase;
	
	public void launch() {
		myRes = ResourceService.getResourceByName("res1");
		resSched = ResourceService.getResourceByName("RES_SCHEDULER");
	
		t1 = TaskService.getTaskByName("task1");
		
		ResourceService.getResource(myRes);
		ResourceService.getResource(resSched);

		a1 = AlarmService.getAlarmByName("alarm1");
		abase = new AlarmBase();
		AlarmService.getAlarmBase(a1, abase);

		DebugOut.println("Task2 running\n");

		DebugOut.println("Properties of alarm1's base counter\n");
		DebugOut.println("Maxallowed value: " + abase.maxallowedvalue);
		DebugOut.println("\nTicksperBase: " + abase.ticksperbase);
		DebugOut.println("\nMincycle: " + abase.mincycle + "\n");

		ResourceService.releaseResource(resSched);

		if ( TaskService.chain(t1) == TaskService.E_OS_RESOURCE ) {
			DebugOut.println("Oops, still have myRes occupied\n");
		}

		ResourceService.releaseResource(myRes);
		
		if ( (EventService.getEvent(t1) & Events.ev1) == 0)
			EventService.setEvent(t1, Events.ev1);

		TaskService.terminate();
	}
}
