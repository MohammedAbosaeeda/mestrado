package test;

import keso.core.Task;
import keso.core.TaskService;

public class TestObjArray extends Task {
	public void launch() {
		Object[] obj;
		int c=20000;
		int step=0;

		// Allocated here because writing local
		// variables does not use a writebarrier
		Object x = new StringBuffer(5);
		Object y = new StringBuffer(10);
		Object z = new StringBuffer(15);
		obj = new Object[3];

		while(c-->0) {
			step++;
			switch(step) {
				case 1:
					obj[0]=x;
					break;
				case 2:
					obj[1]=y;
					break;
				default:
					obj[2]=z;
					step=0;
			}
		}
	}
}
