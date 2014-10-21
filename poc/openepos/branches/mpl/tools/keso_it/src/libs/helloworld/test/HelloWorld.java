package test;

import keso.core.Task;
import keso.core.TaskService;
import keso.posix.*;

public class HelloWorld extends Task {
	boolean a=true;

	static String str = new String("Hello clinit");

	public HelloWorld() { DebugOut.println("HelloWorld constructor\n"); }

	public int sub_method() {
		int i=0;
		try {
			i++;
		} catch (NullPointerException ex) {
			i=0;
			DebugOut.println("exception caught");
		}
		return i;
	}

	static int fifo = 0;
	static char buf[] = new char[256];

	public static void ioisr() {
		DebugOut.println("IO ISR");
		PosixIO.read(fifo, buf, buf.length);
		DebugOut.println(new String(buf));
	}

	public void launch() {

		int fd = PosixIO.open("test.txt", PosixIO.WRITE|PosixIO.READ|PosixIO.TRUNC|PosixIO.CREAT);

		String str = "Hello World\n";
		PosixIO.write(fd,str.toCharArray(), str.length());

		PosixIO.close(fd);

		fifo = PosixIO.open("fifo", PosixIO.WRITE|PosixIO.READ);

		if (fifo>=0) {
			DebugOut.println("activate ISR");
			PosixIO.activateISR(fifo, 0, PosixIO.EVENT_RD);
		}

		DebugOut.println(keso.core.Config.getString("ddom1.ttask1.HelloString","Hello World!"));
		DebugOut.println("You successfully compiled and ran KESO. Goodbye...\n");

		TaskService.terminate();
	}
}
