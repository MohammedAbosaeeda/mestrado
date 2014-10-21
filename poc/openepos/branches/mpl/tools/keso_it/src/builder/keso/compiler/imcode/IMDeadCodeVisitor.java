/**(c)

  Copyright (C) 2006 Christian Wawersich 

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

final public class IMDeadCodeVisitor extends IMBasicVisitor {

	private BuilderOptions opts;
	private IMMethod method;

	public IMDeadCodeVisitor(IMMethod method) {
		this.opts = method.getOptions();
		this.method=method;
	}

	public void visit(IMNode node) throws CompileException {
		if (node instanceof IMReadLocalVariable) {
			IMSlot var = node.getIMSlot();
			if (var==null) return;
			try {
				var.delete_use(node);
			} catch (RuntimeException ex) {
				opts.warn(node.lineInfo()+": Can't delete use of "+node.toReadableString());
				throw ex;
			}
			if (var.hasNoUses()) method.doAnotherSSARun();
		}
	}
}
