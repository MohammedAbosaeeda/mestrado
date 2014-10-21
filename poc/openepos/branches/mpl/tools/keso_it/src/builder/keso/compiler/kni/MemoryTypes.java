/**(c)

  Copyright (C) 2005 Christian Wawersich 

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

public class MemoryTypes extends Weavelet {

	public MemoryTypes(BuilderOptions opts, ClassStore repository) {
		super(opts, repository);
		joinPoints = new String[] {
			"keso/core/MT_U8.*",
			"keso/core/MT_U16.*",
			"keso/core/MT_U32.*",
			"keso/core/MT_U32RO.*",
			"keso/core/MT_SPACE32.*",
			"keso/core/MT_U8.<CLASS>",
			"keso/core/MT_U16.<CLASS>",
			"keso/core/MT_U32.<CLASS>",
			"keso/core/MT_U32RO.<CLASS>",
			"keso/core/MT_SPACE32.<CLASS>",
		};
	}

	public int checkFieldAttribut(String classname, String fieldname, int attr) {
		if (attr==ATTR_DO_NOT_OMIT) {
			if (fieldname.equals("self")) return TRUE;
			return FALSE;
		}
		return NOTDEF;
	}

	public boolean ignoreMethodBody(IMClass clazz, IMMethod method)
		throws CompileException {
		return true;
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
		if (method.termed("equals")) {
			coder.addln("return (jint)(obj0==obj1);");
			return true;
		}

		/*
		   String toString()
		   Returns a string representation of the object.
		   */

		return false;
	}

	public boolean affectInvokeVirtual(IMInvoke node, IMMethod caller, IMMethod callee,
			IMNode obj, IMNode args[], Coder coder) throws CompileException {

		if (callee.termed("set")) {
			if (obj.translate_MT_Ref(coder)) {
				coder.add('=');
				args[0].translate(coder);
				return true;
			} 
			return false;
		}

		if (callee.termed("and")) {
			if (obj.translate_MT_Ref(coder)) {
				coder.add("&=");
				args[0].translate(coder);
				return true;
			} 
			return false;
		}

		if (callee.termed("or")) {
			if (obj.translate_MT_Ref(coder)) {
				coder.add("|=");
				args[0].translate(coder);
				return true;
			} 
			return false;
		}

		if (callee.termed("xor")) {
			if (obj.translate_MT_Ref(coder)) {
				coder.add("^=");
				args[0].translate(coder);
				return true;
			} 
			return false;
		}

		if (callee.termed("setBit")) {
			if (obj.translate_MT_Ref(coder)) {
				coder.add(" |= (1 << ");
				args[0].translate(coder);
				coder.add(")");
				return true;
			} 
			return false;
		}

		if (callee.termed("clearBit")) {
			if (obj.translate_MT_Ref(coder)) {
				coder.add(" &= ~(1 << ");
				args[0].translate(coder);
				coder.add(")");
				return true;
			} 
			return false;
		}

		if (callee.termed("isBitSet")) {
			coder.add("((");
			if (obj.translate_MT_Ref(coder)) {
				coder.add(" & (1 <<");
				args[0].translate(coder);
				coder.add(")) != 0)");
				return true;
			}
			return false;
		}

		if (callee.termed("isBitClear")) {
			coder.add("((");
			if (obj.translate_MT_Ref(coder)) {
				coder.add(" & (1 <<");
				args[0].translate(coder);
				coder.add(")) == 0)");
				return true;
			}
			return false;
		}	

		if (callee.termed("get")) {
			if (obj.translate_MT_Ref(coder)) return true; 
		}

		return false;
	}
}
