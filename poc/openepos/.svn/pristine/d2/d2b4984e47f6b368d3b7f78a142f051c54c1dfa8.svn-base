package test;

import keso.core.Task;
import keso.core.TaskService;
import keso.core.Events;
import keso.core.EventService;
import keso.core.InterruptService;

class QueueElement {
	QueueElement(int value) {
		this.value = value;
		next = null;
	}

	protected void finalize() {
		if((value % 20)==0) {
			DebugOut.print("Freed QEL ");
			DebugOut.print(value);
			DebugOut.println();
		}
	}

	QueueElement next;
	int value;
}

public class HelloWorld extends Task {

	static final int qlen = 4;

	int runs;

	public HelloWorld() { 
		runs=0;
	}

	public void launch() {

		//InterruptService.disableAll();

		QueueElement qel, head;
		
		head = new QueueElement(runs++);

		while(true) {
			DebugOut.print("Queue: ");

			if(runs>qlen) head = head.next;
			qel = head;

			while(true) {
				DebugOut.print(qel.value + ", ");
				if(qel.next == null) break;
				qel = qel.next;
			}
			qel.next = new QueueElement(runs++);
			DebugOut.println(qel.next.value);

			//InterruptService.enableAll();
			EventService.waitEvent(Events.Wakeup);
			EventService.clearEvent(Events.Wakeup);
			//InterruptService.disableAll();
		}
	}

	protected void finalize() {

	}
}
