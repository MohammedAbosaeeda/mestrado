/**(c)

  Copyright (C) 2005 Christian Wawersich 

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
import keso.compiler.backend.Coder;

import keso.util.Debug; 

final public class IMReplaceVisitor implements IMCopyVisitor {

	private IMNode from;
	private IMNode to;
	private IMMethod host_method;
	
	public IMReplaceVisitor(IMMethod host_method, IMNode from, IMNode to) {
		this.host_method = host_method;
		this.from = from;
		this.to = to;
	}

	public IMNode visit(IMNode nnode, IMNode onode) throws CompileException {
		nnode.method = host_method;
		if (onode==from) return to;
		return nnode;
	}

	public IMSlot adjustSlot(IMSlot foreigenSlot) throws CompileException {
		return foreigenSlot;
	}

	public IMBasicBlock updateBlock(IMBasicBlock label) throws CompileException {
		return label;
	}
}

