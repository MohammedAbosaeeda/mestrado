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

public class IMCastI2B extends IMCast {


	/* copy constuctor */
	protected IMCastI2B() { }

	public IMCastI2B(IMMethod method, int bc, int bcpos) {
		super(method,bc,BCBasicDatatype.BYTE,bcpos);
	}


	final public IMNode copy(IMCopyVisitor visitor) throws CompileException {

		IMCastI2B nnode = new IMCastI2B();
		nnode.copyNodeValues(visitor, this);

		nnode.rOpr = rOpr.copy(visitor);

		if (visitor==null) return nnode;
		return visitor.visit(nnode,this);
	}

	final public void visitNodes(IMVisitor visitor) throws CompileException {
		if (visitor.skipNode(this)) return;

		visitor.visit(this);

		rOpr.visitNodes(visitor);

	}

	final public IMNode constant_folding() throws CompileException {
		rOpr = rOpr.constant_folding();
		if (rOpr.isConstant()) {
			opts.verbose("++ todo fold unop"+this.toReadableString());
		}


		return this;
	}

	final public IMNode ssa_optimize() throws CompileException {
		rOpr = rOpr.ssa_optimize();
		if (rOpr.isConstant()) {
			opts.verbose("++ todo fold unop "+this.toReadableString());
		}


		return this;
	}

	public String toReadableString() {
		return "(int2byte)"+rOpr.toReadableString();
	}

	public String dumpBC() {return "CastI2B";}

	public int costs() throws CompileException {
		return rOpr.costs() + 5;

	}

	public void translate(Coder coder) throws CompileException {
		coder.add("((");
		coder.add(BuilderOptions.BYTE_T);
		coder.add(')');
		rOpr.translate(coder);
		coder.add(')');
	}

}
