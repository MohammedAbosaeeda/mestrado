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

public class IMBStoreArray extends IMStoreArray {


	/* copy constuctor */
	protected IMBStoreArray() { }

	public IMBStoreArray(IMMethod method, int bc, int bcpos) {
		super(method,bc,BCBasicDatatype.BYTE,bcpos);
	}


	final public IMNode copy(IMCopyVisitor visitor) throws CompileException {


		IMBStoreArray nnode = new IMBStoreArray();
		nnode.copyNodeValues(visitor, this);

		nnode.aOpr = aOpr.copy(visitor);
		nnode.iOpr = iOpr.copy(visitor);

		nnode.rvalue = rvalue.copy(visitor);

		if (visitor==null) return nnode;
		return visitor.visit(nnode,this);
	}

	final public void visitNodes(IMVisitor visitor) throws CompileException {
		if (visitor.skipNode(this)) return;


		visitor.visit(this);

		aOpr.visitNodes(visitor);
		iOpr.visitNodes(visitor);

		rvalue.visitNodes(visitor);

	}

	final public IMNode constant_folding() throws CompileException {

		aOpr = aOpr.constant_folding();
		iOpr = iOpr.constant_folding();

		rvalue = rvalue.constant_folding();


		return this;
	}

	final public IMNode ssa_optimize() throws CompileException {

		aOpr = aOpr.ssa_optimize();
		iOpr = iOpr.ssa_optimize();

		rvalue = rvalue.ssa_optimize();


		return this;
	}

	public int costs() throws CompileException {
		return aOpr.costs() + iOpr.costs() + rvalue.costs() + 10;

	}

	public void translate(Coder coder) throws CompileException {
		IMClass clazz = method.getIMClass("[B");
		coder.add_class(clazz.getAlias());
		coder.chk_array(this,aOpr,iOpr,method,bcPosition);
		coder.chk_ref(aOpr,method,bcPosition);
		coder.add("((byte_array_t*)");
		aOpr.translate(coder);
		coder.add(")->data[");
		iOpr.translate(coder);
		coder.add("]=");
		rvalue.translate(coder);
	}

}
