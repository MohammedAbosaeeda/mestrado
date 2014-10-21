package test;

import keso.core.Task;
import keso.core.TaskService;

public class TestStaticField extends Task {
	private static Object obj;

	public void launch() {
		int c=20000;
		int step=0;

		// Allocated here because writing local
		// variables does not use a writebarrier
		Object x = new StringBuffer(5);
		Object y = new StringBuffer(10);
		Object z = new StringBuffer(15);

		while(c-->0) {
			step++;
			switch(step) {
				case 1:
					obj=x;
					break;
				case 2:
					obj=y;
					break;
				default:
					obj=z;
					step=0;
			}
		}
	}
}
