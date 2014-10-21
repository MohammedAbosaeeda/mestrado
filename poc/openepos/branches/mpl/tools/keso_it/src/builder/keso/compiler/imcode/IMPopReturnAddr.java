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

public class IMPopReturnAddr extends IMNode {


	/* copy constuctor */
	protected IMPopReturnAddr() { }

	public IMPopReturnAddr(IMMethod method, int bc, int type, int bcpos) {
		super(method,bc,type,bcpos);
	}

	final public IMNode copy(IMCopyVisitor visitor) throws CompileException {
		IMPopReturnAddr nnode = new IMPopReturnAddr();
		nnode.copyNodeValues(visitor, this);

		if (visitor==null) return nnode;
		return visitor.visit(nnode,this);
	}

	final public void visitNodes(IMVisitor visitor) throws CompileException {
		if (visitor.skipNode(this)) return;/* we do nothing here */
	}

	public IMNode processStack(VirtualOperantenStack stack, IMNode extra_ops) throws CompileException {
		stack.push(this);
		return null;
	}

	public String toReadableString() {
	return "return_addr";
	}

	public String dumpBC() {return "PopReturnAddr";}

	public void translate(Coder coder) throws CompileException {
		coder.add("((object_t*)ret_addr)");
	}

}
