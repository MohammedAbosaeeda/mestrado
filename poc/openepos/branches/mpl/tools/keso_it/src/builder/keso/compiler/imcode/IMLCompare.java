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

public class IMLCompare extends IMCompare {


	/* copy constuctor */
	protected IMLCompare() { }

	public IMLCompare(IMMethod method, int bc, int bcpos) {
		super(method,bc,BCBasicDatatype.INT,bcpos);
	}


	final public IMNode copy(IMCopyVisitor visitor) throws CompileException {

		IMLCompare nnode = new IMLCompare();
		nnode.copyNodeValues(visitor, this);

		nnode.rOpr = rOpr.copy(visitor);
		nnode.lOpr = lOpr.copy(visitor);

		if (visitor==null) return nnode;
		return visitor.visit(nnode,this);
	}

	final public void visitNodes(IMVisitor visitor) throws CompileException {
		if (visitor.skipNode(this)) return;

		visitor.visit(this);

		rOpr.visitNodes(visitor);
		lOpr.visitNodes(visitor);

	}

	public String toReadableString() {
		return "compare("+lOpr.toReadableString()+","+rOpr.toReadableString()+")";
	}

	public String dumpBC() {return "LCompare";}

	public int costs() throws CompileException {
		return lOpr.costs() + rOpr.costs() + 5;
	}

	public void translate(Coder coder) throws CompileException {
		//coder.add("keso_lcmp(");
		coder.add("(");
		lOpr.translate(coder);
		//coder.add(',');
		coder.add('-');
		rOpr.translate(coder);
		coder.add(')');

	}

}
