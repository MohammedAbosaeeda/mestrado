/**(c)

  Copyright (C) 2008 Christian Wawersich 

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
import keso.util.Stack;
import java.util.Hashtable;

final public class IMUpdateUsesVisitor extends IMBasicVisitor {

	private BuilderOptions opts;
	private IMMethod method;
	private int old_uses;
	private int new_uses;

	public IMUpdateUsesVisitor(IMMethod method) {
		this.opts = method.getOptions();
		this.method=method;
	}

	public void begin() throws CompileException {
		old_uses = method.getMethodFrame().resetUses();
		new_uses = 0;
	}

	public void end() throws CompileException {
		if (old_uses!=new_uses) method.doAnotherSSARun();
	}

	public void visit(IMNode node) throws CompileException {
		IMBasicBlock basicBlock = method.getCurrentBasicBlock();
		if (node instanceof IMReadLocalVariable ||
				node instanceof IMEpilog ||
				node instanceof IMReturnSubroutine) { 
			IMSlot var = node.getIMSlot();
			if (var!=null) {
				var.use(basicBlock, node);
				new_uses++;
			}
		}
	}
}
