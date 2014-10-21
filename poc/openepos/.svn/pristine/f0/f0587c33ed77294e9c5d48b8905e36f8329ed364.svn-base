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

public class IMBinaryNode extends IMNode {

	protected IMNode rOpr;
	protected IMNode lOpr;

	public IMBinaryNode() {
	}

	public IMBinaryNode(IMMethod method, int bc, int type, int bcpos) {
		super(method,bc,type,bcpos);
	}

	public int getDatatype() {
		if (datatype==BCBasicDatatype.INT) {
			if (opts.hasOption("stack8") || opts.hasOption("stack16")) {
				int rtype = rOpr.getDatatype();
				int ltype = lOpr.getDatatype();
				if (rtype==ltype) return rtype; 
				if (rtype==BCBasicDatatype.SHORT || rtype==BCBasicDatatype.BYTE) {
					if (ltype==BCBasicDatatype.SHORT || ltype==BCBasicDatatype.BYTE)
						return BCBasicDatatype.SHORT;
				}
			} 
		}
		return datatype;
	}

	public boolean hasConstant() { return lOpr.isConstant() || rOpr.isConstant(); }

	public boolean hasSideEffect() { return lOpr.hasSideEffect() || rOpr.hasSideEffect(); }

	public boolean isPureNode() { return lOpr.isPureNode() && rOpr.isPureNode(); }

	final public IMNode getLeftNode() { return lOpr; }

	final public IMNode getRightNode() { return rOpr; }

	final public void setLeftNode(IMNode opr) { lOpr = opr; }

	final public void setRightNode(IMNode opr) { rOpr = opr; }

	final public void swapNodes() {
		IMNode swap = rOpr;
		rOpr = lOpr;
		lOpr = swap;
	}

	public IMNode processStack(VirtualOperantenStack stack, IMNode extra_ops) throws CompileException {
		if (opts.hasOption("exceptions")) {
			stack.store3(bcPosition, extra_ops, 2);
		}

		rOpr = stack.pop();
		lOpr = stack.pop();
		stack.push(this);

		return null;
	}
}
