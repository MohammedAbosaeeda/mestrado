/**(c)

  Copyright (C) 2005-2006 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.compiler.imcode; 

import keso.classfile.*;
import keso.classfile.constantpool.*; 
import keso.classfile.datatypes.*; 

import keso.compiler.*;
import keso.compiler.backend.*;
import keso.compiler.kni.*;

import keso.util.Debug; 
import keso.util.DecoratedNames; 
import keso.util.IntegerHashtable; 

import java.util.Vector;
import java.util.Enumeration;
import java.util.Hashtable;


/* AUTO GENERATED FILE DON'T EDIT */

public class IMReadArray extends IMNode {

	protected IMNode iOpr;
	protected IMNode aOpr;

	/* copy constuctor */
	protected IMReadArray() { }

	public IMReadArray(IMMethod method, int bc, int type, int bcpos) {
		super(method,bc,type,bcpos);
	}


	/**
	 * Nodes who only read but not change the global state
	 * are pure.
	 */
	public boolean isPureNode() { return true; }

 
	public void translate_global(Coder coder) throws CompileException {
		coder.global_header_add("void 	keso_check_array(object_t* arr, jint index, const char* msg, jint bcPos);\n");
		coder.global_add("void keso_check_array(object_t* arr, jint index, const char* msg, jint bcPos) {\n");
		coder.global_add("\tKESO_CHECK_NULLPOINTER(arr, msg, bcPos);\n");
		coder.global_add("\tif (unlikely(((array_t*)arr)->size<=(array_size_t)index))");
		coder.global_add(" keso_throw_index_out_of_bounds(msg, bcPos);\n");
		coder.global_add("}\n");
	}

	public void escapeAnalyses(int mode) throws CompileException {
		if (mode!=IMEscapeVisitor.PREPARE) return;
		aOpr.setEscapePath(IMNode.NONE);
	}
	public IMNode processStack(VirtualOperantenStack stack, IMNode extra_ops) throws CompileException {
		method.isConst=false;
		stack.store(bcPosition, extra_ops);

		iOpr = stack.pop();
		assertIsIntValue(iOpr);
		aOpr = stack.pop();
		assertIsReference(aOpr);
		stack.push(this);
		return null;
	}

	public String toReadableString() {
		return aOpr.toReadableString()+"["+iOpr.toReadableString()+"]";
	}

	public String dumpBC() {return "ReadArray";}

}
