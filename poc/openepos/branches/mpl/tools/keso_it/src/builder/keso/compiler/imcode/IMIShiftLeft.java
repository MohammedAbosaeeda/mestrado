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

public class IMIShiftLeft extends IMShiftLeft {


	/* copy constuctor */
	protected IMIShiftLeft() { }

	public IMIShiftLeft(IMMethod method, int bc, int bcpos) {
		super(method,bc,BCBasicDatatype.INT,bcpos);
	}


	final public IMNode copy(IMCopyVisitor visitor) throws CompileException {

		IMIShiftLeft nnode = new IMIShiftLeft();
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
			opts.verbose("++ folding c<<c "+toReadableString());
			IMConstant clOpr = lOpr.nodeToConstant();
			IMConstant crOpr = rOpr.nodeToConstant();
			clOpr.setIntValue(clOpr.getIntValue()<<crOpr.getIntValue());
			return clOpr;
		}


		return this;
	}

	final public IMNode ssa_optimize() throws CompileException {
		rOpr = rOpr.ssa_optimize();
		lOpr = lOpr.ssa_optimize();

		if (lOpr.isConstant() && rOpr.isConstant()) {
			opts.vverbose("++ folding c<<c "+toReadableString());
			IMConstant clOpr = lOpr.nodeToConstant();
			IMConstant crOpr = rOpr.nodeToConstant();
			clOpr.setIntValue(clOpr.getIntValue()<<crOpr.getIntValue());
			return clOpr;
		}


		if (rOpr.isConstant()) {
			opts.vverbose("++ folding x_c x<<c "+toReadableString());
			int value = rOpr.nodeToConstant().getIntValue();
			if (value==0) return lOpr;
		}

		return this;
	}

	public int costs() throws CompileException {
		return lOpr.costs() + rOpr.costs() + 5;
	}

	public void translate(Coder coder) throws CompileException {
		coder.add('(');
		if (lOpr.getDatatype()!=BCBasicDatatype.INT) {
			coder.add("((");
			coder.add(BuilderOptions.INT_T);
			coder.add(")");
			lOpr.translate(coder);
			coder.add(")");
		} else {
			lOpr.translate(coder);
		}
		coder.add("<<");
		if (rOpr instanceof IMIConstant) {
			coder.add(((IMIConstant)rOpr).getValue() & 0x1f);
		} else {
			coder.add('(');
			rOpr.translate(coder);
			coder.add(" & 0x1f)"); // Don't forget to mask to only 5 bits!
		}
		coder.add(')');
	}

}
