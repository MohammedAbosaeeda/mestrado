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
import java.util.Enumeration;

final public class IMRenameVisitor extends IMBasicVisitor {

	private IMMethod method;
	private IMMethodFrame frame;
	private BuilderOptions opts;
	private Hashtable stacks;
	private IMBasicBlock bb;
	private IMBasicBlock pb;
	private int pb_nr;

	public IMRenameVisitor(IMMethod method, IMBasicBlock bb, Hashtable stacks) throws CompileException {
		this.method = method;
		this.frame = method.getMethodFrame();
		this.opts = method.getOptions();
		this.stacks = stacks;
		this.bb = bb;
	}

	public boolean skipNode(IMNode node) {
		if (node instanceof IMPhi) return true;
		return false; 
	}

	private void dumpStacks() {
		Enumeration e = stacks.keys();
		while (e.hasMoreElements()) {
			IMSlot var = (IMSlot)e.nextElement();
			System.err.print(var+" => ");
			System.err.println(stacks.get(var));
		}
	}

	public void visit(IMNode node) throws CompileException {

		if (node instanceof IMPhi) {
			throw new CompileException("unexpected node type");
		}

		if (node instanceof IMReadLocalVariable || node instanceof IMReturnSubroutine || node instanceof IMEpilog) {
			IMSlot var = node.getIMSlot();
			if (var!=null) {
				Stack stack = (Stack)stacks.get(var);
				if (stack==null) {
					opts.warn("no definition for "+var+" in "+method.getAlias()+" bb "+bb.getDFNum());
					//dumpStacks();
					node.setIMSlot(null);
				} else {
					IMSlot slot = (IMSlot)stack.top();
					if (slot!=null) {
						slot.use(bb, node);
						node.setIMSlot(slot);
					} else {
						opts.warn("not definited "+var+" in bb"+bb.getDFNum());
					}
				}
			}
		}

		if (node instanceof IMStoreLocalVariable) {
			IMSlot ovar = node.getIMSlot();
			Stack stack = (Stack)stacks.get(ovar);

			IMSlot nvar = frame.createNewVariableSSA(ovar);
			node.setIMSlot(nvar);
			nvar.define(bb, node);
			stack.push(nvar);
		}

	}
}
