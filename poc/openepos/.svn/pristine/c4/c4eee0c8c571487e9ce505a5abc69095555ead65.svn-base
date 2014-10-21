/**(c)

  Copyright (C) 2007 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.compiler.kni;

import keso.compiler.*;
import keso.compiler.imcode.*;
import keso.compiler.backend.*;

public class Object extends Weavelet {

	public Object(BuilderOptions opts, ClassStore repository) {
		super(opts, repository);
		joinPoints = new String[] { "java/lang/Object.*" };
	}

	public void require(int domainID, String className, String methodName) {
		if (methodName.equals("getClass()Ljava/lang/Class;")) {
			repository.requireClass("java/lang/Class");
		}
	}

	public int checkAttribut(IMMethod method, int attr) throws CompileException 
	{
		if (attr==ATTR_NOESCAPE) {
			if (method.termed("hashCode()I")) return TRUE; 
			if (method.termed("equals(Ljava/lang/Object;)Z")) return TRUE; 
		}

		if (attr==ATTR_CONST || attr==ATTR_PURE) {
			if (method.termed("hashCode()I")) return TRUE; 
			if (method.termed("equals(Ljava/lang/Object;)Z")) return TRUE; 
		}

		if (attr==ATTR_NOTNULL) {
			/* TODO: change this after implementing the method */
			if (method.termed("getClass()Ljava/lang/Class;")) return FALSE;
		}

		return FALSE;
	}

	public boolean affectMethod(IMClass clazz, IMMethod method, Coder coder) throws CompileException
	{
		/* Creates and returns a copy of this object. */
		if (method.termed("clone()Ljava/lang/Object;")) {
		}

		/* Called by the garbage collector on an object when garbage collection
		 * determines that there are no more references to the object. */
		if (method.termed("finalize()V")) {
			coder.addln("return;");
			return true;
		}

		/* Returns the runtime class of an object. */
		if (method.termed("getClass()Ljava/lang/Class;")) {
			opts.warn("getClass() not implemeted yet.");
			coder.addln("return NULL;");
			return true;
		}

		/* Wakes up a single thread that is waiting on this object's monitor. */
		if (method.termed("notify()V")) {
		}

		/* Wakes up all threads that are waiting on this object's monitor. */
		if (method.termed("notifyAll()V")) {
		} 

		/* Causes current thread to wait until another thread invokes the 
		 * notify() method or the notifyAll() method for this object. */
		if (method.termed("wait()V")) { }

		/* Causes current thread to wait until either another thread invokes the 
		 * notify() method or the notifyAll() method for this object, or a 
		 * specified amount of time has elapsed. */
		if (method.termed("wait(J)V")) {}

		/*
		   Causes current thread to wait until another thread invokes the 
		   notify() method or the notifyAll() method for this object, or
		   some other thread interrupts the current thread, or a certain 
		   amount of real time has elapsed.
		   */
		if (method.termed("wait(JI)V")) {}

		/* Returns a hash code value for the object. */
		if (method.termed("hashCode()I")) {
			coder.addln("/* we use the object address as default hash code */");
			coder.addln("return (jint)obj0;");
			return true;
		}

		/* Indicates whether some other object is "equal to" this one. */
		if (method.termed("equals(Ljava/lang/Object;)Z")) { 
			coder.addln("return (jint)(obj0==obj1);");
			return true;
		}

		return false;
	}

	public boolean ignoreMethodBody(IMClass clazz, IMMethod method) throws CompileException {
		if(method.termed("toString()Ljava/lang/String;"))
			return false;
		if(method.termed("<init>"))
			return false;
		return true;
	}
}
