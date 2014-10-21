package test;

import keso.core.Task;

public class ExceptionTest extends Task {

	synchronized int test(int value) {

		if (value==0) return 0;

		if (value==1) throw new RuntimeException();

		try {
			if (value==2) throw new NullPointerException();
		} catch (NullPointerException ex) {
			return 3;
		}

		return 4;
	}

	public void launch_jsr() {
		try {
			test(0);
			test(1);
			test(2);
		} finally {
			test(4);
		}
	}

	public void launch() {
		try {
			test(0);
			test(1);
			test(2);
			test(3);
		} catch (RuntimeException ex) {
		}
	}
}
