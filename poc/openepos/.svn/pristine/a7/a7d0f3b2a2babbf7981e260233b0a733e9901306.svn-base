/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi.fau.de for more info.

  (c)**/

package keso.compiler.imcode; 

import keso.classfile.*;
import keso.classfile.constantpool.*; 
import keso.classfile.datatypes.*; 

import keso.compiler.*;

import keso.util.Debug; 

public class IMUnaryNode extends IMNode {

	protected IMNode rOpr;

	protected IMUnaryNode() {
	}

	public IMUnaryNode(IMMethod method, int bc, int type, int bcpos) {
		super(method,bc,type,bcpos);
	}

	public boolean hasConstant() { return rOpr.isConstant(); }

	public boolean hasSideEffect() { return rOpr.hasSideEffect(); }

	/*
	public void visitNodes(IMVisitor visitor) throws CompileException {
		visitor.visit(this);
		rOpr.visit(visitor);
	}
	*/

	final public IMNode getRightNode() { return rOpr; }

	final public void setRightNode(IMNode opr) { rOpr = opr; }

	public IMNode processStack(VirtualOperantenStack stack, IMNode extra_ops) throws CompileException {
		if (opts.hasOption("exceptions")) 
			stack.store3(bcPosition, extra_ops, 1);

		rOpr = stack.pop();
		stack.push(this);
		return null;
	}
}
