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

public class IMStoreArray extends IMNode {

	protected IMNode rvalue;
	protected IMNode iOpr;
	protected IMNode aOpr;

	/* copy constuctor */
	protected IMStoreArray() { }

	public IMStoreArray(IMMethod method, int bc, int type, int bcpos) {
		super(method,bc,type,bcpos);
	}


	public void translate_global(Coder coder) throws CompileException {
		coder.global_header_add("void 	keso_check_array(object_t* arr, jint index, const char* msg, jint bcPos);\n");
		coder.global_add("void keso_check_array(object_t* arr, jint index, const char* msg, jint bcPos) {\n");
		coder.global_add("\tKESO_CHECK_NULLPOINTER(arr, msg, bcPos);\n");
		coder.global_add("\tif (unlikely(((array_t*)arr)->size<=(array_size_t)index))");
		coder.global_add(" keso_throw_index_out_of_bounds(msg, bcPos);\n");
		coder.global_add("}\n");
	}

	public boolean doEscape(IMNode node, boolean isArgument) throws CompileException {
		return true;
	}

	final public IMNode whyEscape(IMNode node, boolean isArgument) throws CompileException {
		return this;
	}

	public void escapeAnalyses(int mode) throws CompileException {
		if (mode!=IMEscapeVisitor.PREPARE) return;
		aOpr.setEscapePath(IMNode.NONE);	
		rvalue.setEscapePath(this);	
	}

	public IMBasicBlock inlineMethodCalls(IMBasicBlock current, IMNode prev) throws CompileException {
		if (!opts.doInlineRValues()) return null;
		return rvalue.inlineMethodCalls(current, prev);
	}
	public IMNode processStack(VirtualOperantenStack stack, IMNode extra_ops) throws CompileException {
		method.isPure=false;
		method.isConst=false;
		stack.store(bcPosition, extra_ops);

		rvalue = stack.pop();
		iOpr   = stack.pop();
		assertIsIntValue(iOpr);
		aOpr   = stack.pop();
		assertIsReference(aOpr);
		return this;
	}

	public String toReadableString() {
		return aOpr.toReadableString()+"[" +iOpr.toReadableString()+"] = " +rvalue.toReadableString();
	}

	public String dumpBC() {return "StoreArray";}

}
