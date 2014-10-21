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

import java.util.Hashtable;

final public class DebugOutWeavelet extends Weavelet {

	private boolean caller_side = false;

	public DebugOutWeavelet(BuilderOptions opts, ClassStore repository) {
		super(opts, repository); 
		caller_side = opts.hasOption("dbgout_caller");
		joinPoints = new String[] {
			"test/DebugOut.raw_print(Ljava/lang/StringBuffer;)V",
		};
	}

	/**
	 * This protect a method body from analayses and translation process.
	 */
	public boolean ignoreMethodBody(IMClass clazz, IMMethod method) throws CompileException
	{
		if (method.termed("raw_print(Ljava/lang/StringBuffer;)V")) return true; 
		return false;
	}

	public void require(int domainID,String className, String methodName) {
		if (methodName.equals("raw_print(Ljava/lang/StringBuffer;)V")) {
			repository.registerIMGetField("java/lang/StringBuffer","data");
			repository.registerIMGetField("java/lang/StringBuffer","length");
		}
	}

	public int checkAttribut(IMMethod method, int attr) throws CompileException {
		if (attr==ATTR_NOESCAPE) {
			/* The argument won't escape the method frame */
			if (method.termed("raw_print(Ljava/lang/StringBuffer;)V")) return TRUE; 
		}
		return FALSE;
	}

	/**
	 * This affect the method body. 
	 */
	public boolean affectMethod(IMClass clazz, IMMethod method, Coder coder) throws CompileException {
		if (caller_side) return false; 

		IMClass array_class = method.getIMClass("[C");
		if (method.termed("raw_print(Ljava/lang/StringBuffer;)V")) { 

			IMClass string_class = method.getIMClass("java/lang/StringBuffer");

			coder.add_class(array_class.getAlias());
			coder.add_class(string_class.getAlias());

			coder.local_add("#include <keso_support.h>\n");

			coder.add(array_class.getClassTypeString());
			coder.add("* char_array = (");
			coder.add(array_class.getClassTypeString());
			coder.add("*)");
			coder.add_getField(string_class, "obj0", "data");
			coder.addln(";");

			coder.add("write(1, char_array->data, ");
			coder.add_getField_fast(string_class, "obj0", "length");
			coder.addln(");");

			coder.addln("return;");

			return true;
		}

		return false;
	}

	public boolean affectInvokeStatic(IMInvoke node, IMMethod caller, IMMethod callee,
			IMNode args[], Coder coder) throws CompileException {
		if (!caller_side) return false; 

		IMClass array_class = caller.getIMClass("[C");

		if (callee.termed("raw_print(Ljava/lang/StringBuffer;)V")) { 

			IMClass string_class = callee.getIMClass("java/lang/StringBuffer");

			coder.add_class(array_class.getAlias());
			coder.add_class(string_class.getAlias());

			coder.local_add("#include <keso_support.h>\n");
			coder.addln("{");
			coder.addln("jint len;");
			coder.add(string_class.getClassTypeString());
			coder.addln("* arr;");
			coder.add("arr = ");
			coder.add_getField(string_class, args[0], "data");
			coder.addln(";");
			coder.add("len = ");
			coder.add_getField(string_class, args[0], "length");
			coder.addln(";");

			coder.addln("write(1, arr->data, len);");

			coder.addln("}");
			
			return true;
		} 

		return false;
	}
}
