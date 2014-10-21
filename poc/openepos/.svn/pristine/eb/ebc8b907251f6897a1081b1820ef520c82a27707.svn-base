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

public class IMFLCompare extends IMCompare {


	/* copy constuctor */
	protected IMFLCompare() { }

	public IMFLCompare(IMMethod method, int bc, int bcpos) {
		super(method,bc,BCBasicDatatype.FLOAT,bcpos);
	}


	final public IMNode copy(IMCopyVisitor visitor) throws CompileException {

		IMFLCompare nnode = new IMFLCompare();
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
		return "fcmpl("+lOpr.toReadableString()+","+rOpr.toReadableString()+")";
	}

	public String dumpBC() {return "FLCompare";}

	public int costs() throws CompileException {
		return lOpr.costs() + rOpr.costs() + 5;
	}

	public void translate(Coder coder) throws CompileException {
/*
   Notes The fcmpg and fcmpl instructions differ only in their treatment of a comparison
   involving NaN. NaN is unordered, so any float comparison fails if either or both
   of its operands are NaN. With both fcmpg and fcmpl available, any float comparison
   may be compiled to push the same result onto the operand stack whether the comparison
   fails on non-NaN values or fails because it encountered a NaN. For more information,
   see Section 7.5, "More Control Examples."
 */
		opts.warn("FCMPL may not be conform to java specification!");
		coder.global_add_fkt("math/keso_math_fcmpl.c");
		coder.add("keso_fcmpl(");
		lOpr.translate(coder);
		coder.add(',');
		rOpr.translate(coder);
		coder.add(')');
	}

}
