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

public class IMConditionalBranch extends IMBranch {

	protected IMNode rOpr;
	protected IMNode lOpr;

	public IMConditionalBranch() {
	}

	public IMConditionalBranch(IMMethod method, int bc, int bcpos, IMBasicBlock label, IMBasicBlock next) {
		super(method,bc,-1,bcpos);
		targets = new IMBasicBlock[2];
		targets[0]=next;
		targets[1]=label;
	}

	public void escapeAnalyses(int mode) throws CompileException {
		if (mode!=IMEscapeVisitor.PREPARE) return;
		if (lOpr!=null) lOpr.setEscapePath(IMNode.NONE);
		if (rOpr!=null) rOpr.setEscapePath(IMNode.NONE);
	}

	public IMNode processStack(VirtualOperantenStack stack, IMNode extra_ops) throws CompileException {
		if (opts.hasOption("exceptions"))
			stack.store3(bcPosition, extra_ops, 2);

		if (bytecode<159) {
			rOpr = method.createIMIConstant(0,bcPosition);
		} else {
			if (bytecode==198 || bytecode==199) {
				rOpr = method.createIMNullConstant(bcPosition);
			} else {
				rOpr = stack.pop();
			}
		}
		lOpr = stack.pop();
		return this;
	}

	public IMNode getLeftNode() {
		return lOpr;
	}

	public IMNode getRightNode() {
		return rOpr;
	}
}
