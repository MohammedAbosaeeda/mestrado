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

public class IMDAdd extends IMAdd {


	/* copy constuctor */
	protected IMDAdd() { }

	public IMDAdd(IMMethod method, int bc, int bcpos) {
		super(method,bc,BCBasicDatatype.DOUBLE,bcpos);
	}


	final public IMNode copy(IMCopyVisitor visitor) throws CompileException {

		IMDAdd nnode = new IMDAdd();
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

	final public IMNode constant_folding() throws CompileException {
		rOpr = rOpr.constant_folding();
		lOpr = lOpr.constant_folding();

		if (lOpr.isConstant() && rOpr.isConstant()) {
			opts.verbose("++ folding c+c "+toReadableString());
			IMConstant clOpr = lOpr.nodeToConstant();
			IMConstant crOpr = rOpr.nodeToConstant();
			clOpr.setDoubleValue(clOpr.getDoubleValue()+crOpr.getDoubleValue());
			return clOpr;
		}


		if (lOpr.isConstant()) {
			IMNode swap = lOpr;
			lOpr = rOpr;
			rOpr = swap;
		}

		return this;
	}

	final public IMNode ssa_optimize() throws CompileException {
		rOpr = rOpr.ssa_optimize();
		lOpr = lOpr.ssa_optimize();

		if (lOpr.isConstant() && rOpr.isConstant()) {
			opts.vverbose("++ folding c+c "+toReadableString());
			IMConstant clOpr = lOpr.nodeToConstant();
			IMConstant crOpr = rOpr.nodeToConstant();
			clOpr.setDoubleValue(clOpr.getDoubleValue()+crOpr.getDoubleValue());
			return clOpr;
		}


		if (lOpr.isConstant()) {
			IMNode swap = lOpr;
			lOpr = rOpr;
			rOpr = swap;
		}

		return this;
	}

	public int costs() throws CompileException {
		return lOpr.costs() + rOpr.costs() + 5;
	}

}
