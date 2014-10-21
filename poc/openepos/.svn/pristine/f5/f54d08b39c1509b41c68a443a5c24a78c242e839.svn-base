package test;

import keso.core.Task;

public class ThreadTest extends Task {
	
	public static final int iterations = 5;
	
	public void launch() {
		DebugOut.print("Thread test\n");
		
		DebugOut.print("I'm the first thread of the first task created in the system.\n");
		DebugOut.print("I'll now create two threads and then wait for them to finish ...\n");
		
		Thread a = new PrinterThread('a', iterations);
		Thread b = new PrinterThread('b', iterations);
		
		a.start();
		b.start();
		
		a.join();
		b.join();
				
		DebugOut.print("Thread A and B exited");		
		DebugOut.print("I'm also done, bye!\n");
		
	}
	
}


