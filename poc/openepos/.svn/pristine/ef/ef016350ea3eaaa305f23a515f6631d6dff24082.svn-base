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

public class IMMonitor extends IMNode {

	protected IMNode operant;

	/* copy constuctor */
	protected IMMonitor() { }

	public IMMonitor(IMMethod method, int bc, int bcpos) {
		super(method,bc,BCBasicDatatype.UNKNOWN_TYPE,bcpos);
	}


	final public IMNode copy(IMCopyVisitor visitor) throws CompileException {

		IMMonitor nnode = new IMMonitor();
		nnode.copyNodeValues(visitor, this);


		nnode.operant = operant.copy(visitor);


		if (visitor==null) return nnode;
		return visitor.visit(nnode,this);
	}

	final public void visitNodes(IMVisitor visitor) throws CompileException {
		if (visitor.skipNode(this)) return;

		visitor.visit(this);


		operant.visitNodes(visitor);


	}

	final public IMNode constant_folding() throws CompileException {


		return this;
	}

	final public IMNode ssa_optimize() throws CompileException {


		return this;
	}

	public IMNode processStack(VirtualOperantenStack stack, IMNode extra_ops) throws CompileException {
		method.isConst=false;
		method.isPure=false;
		operant = stack.pop();
		assertIsReference(operant);
		stack.store(bcPosition, extra_ops);

		return this;
	}

	public String toReadableString() {
		if (bytecode==BC.MONITORENTER) {
			return operant.toReadableString()+".enter()";
		}
		return operant.toReadableString()+".leave()";
	}

	public String dumpBC() {return "Monitor";}

	public int costs() throws CompileException {
		return operant.costs() + 10;

	}

	public void translate(Coder coder) throws CompileException {
		if (bytecode==0xc2) {
			coder.add("KESO_LOCK(");
		} else {
			coder.add("KESO_UNLOCK(");
		}
		operant.translate(coder);
		coder.add(")");
	}

}
