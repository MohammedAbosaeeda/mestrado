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

public class IMCaughtException extends IMNode {


	/* copy constuctor */
	protected IMCaughtException() { }

	private IMExceptionHandler exphandler;
	
	public IMCaughtException(IMMethod method, IMExceptionHandler exphandler, int bcpos) {
		super(method,-1,BCBasicDatatype.REFERENCE,bcpos);
		this.exphandler=exphandler;
	} 

	final public IMNode copy(IMCopyVisitor visitor) throws CompileException {

		IMCaughtException nnode = new IMCaughtException();
		nnode.copyNodeValues(visitor, this);

		nnode.exphandler=exphandler;

		if (visitor==null) return nnode;
		return visitor.visit(nnode,this);
	}

	final public void visitNodes(IMVisitor visitor) throws CompileException {
		if (visitor.skipNode(this)) return;
		visitor.visit(this);

	}

	public IMNode processStack(VirtualOperantenStack stack, IMNode extra_ops) throws CompileException {
		stack.push(this);
		return null;
    	}

	public String toReadableString() {
		return "KESO_THR_CONTEXT->pending_exception";
	}

	public String dumpBC() {return "CaughtException";}

	public int costs() throws CompileException {
		return 100;
	}

	public void translate(Coder coder) throws CompileException {
		coder.add("KESO_PENDING_EXCEPTION; KESO_PENDING_EXCEPTION=(object_t*)0");
	}

}
