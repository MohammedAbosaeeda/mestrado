package test;

import keso.core.Task;

class ClassA {

	ClassA prev;
	String value;

	ClassA(String value) {
		prev=null;
		this.value = value;
	}

	ClassA(ClassA o, String value) {
		prev=o;
		this.value = value;
	}

	ClassA append(ClassA o) {
		value = value + o.value;
		return this;
	}	

	public String toString() {
		return value;
	}
}

class ClassB {
	int primitiv_type;
	ClassB(int p) {
		primitiv_type = p;
	}

	int get() {
		return primitiv_type;
	}
}

public class EscapeTest extends Task {

	void two_obj_loop() {
		ClassA a,b=null;
		for (int i=0;i<4;i++) {
			a = new ClassA(Integer.toString(i));
			if (b!=null) DebugOut.println(a+"!="+b); 
			b = a;
		}
	}

	void obj_loop() {
		ClassA a,b;
		for (int i=0;i<4;i++) {
			a = new ClassA(Integer.toString(i));
			b = a;
			if (b!=null) DebugOut.println(a+"!="+b); 
		}
	}

	void simple_class() {
		ClassB b = new ClassB(42);
		int i = b.get();
		DebugOut.println(i);
	}	

	public void launch() {

		ClassA esc = new ClassA("esc");
		ClassA mayesc= new ClassA("may");
		ClassA noesc = new ClassA(mayesc,"no");

		DebugOut.println(esc);

		two_obj_loop();
		obj_loop();
	}
}
