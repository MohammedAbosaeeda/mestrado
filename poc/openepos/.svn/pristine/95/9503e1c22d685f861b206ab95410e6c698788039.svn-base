package test;

import keso.core.Task;

class A {
	final int cvalue = 42;

	public int foo(int i) {
		return i+cvalue;
	}
}

interface I {
	public long bar(long i, long j);
}

class B1 extends A {
	public int foo(int i) {
		return i-cvalue;
	}
}

class B2 extends A {
	public int foo(int i) {
		return 0;
	}
}

class B3 extends A {
	public int foo() {
		return 0;
	}
}

class B4 extends A {
}

class C extends B1 implements I {
	public long bar(long i, long j) {
		return foo((int)(i+j-cvalue));
	}	
}

class D extends A implements I {

	public int foo(int i) {
		return i+7;
	}

	public long bar(long i, long j) {
		return foo((int)(i+j+cvalue));
	}	
}

public class VTWorld extends Task {

	long submethod(I ic, long l) {
		return ic.bar(1L,l);
	}

	void submethod(A a) {
		int v = a.foo(8);
	}

	public void launch() {

		C c = new C();
		D d = new D();
		A a = d;

		int v = a.foo(7); 
		long l = submethod(c,41L);

		B1 b1 = new B1();
		B2 b2 = new B2();
		B3 b3 = new B3();
		B4 b4 = new B4();

		submethod(d);

		DebugOut.println("===============");
		DebugOut.print(v);
		DebugOut.print(" ");
		DebugOut.print((int)l);
		DebugOut.println(" (14 -42)");
		DebugOut.println("===============");
	}
}
