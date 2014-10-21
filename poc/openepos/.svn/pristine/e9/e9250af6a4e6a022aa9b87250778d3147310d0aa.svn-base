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

final public class IMPhiUpdateVisitor extends IMBasicVisitor {

	private IMMethod method;
	private BuilderOptions opts;
	private Hashtable stacks;
	private IMBasicBlock bb;
	private IMBasicBlock pb;
	private int pb_nr;

	public IMPhiUpdateVisitor(IMMethod method, IMBasicBlock bb, IMBasicBlock pb, Hashtable stacks) throws CompileException {
		this.method = method;
		this.opts=method.getOptions();
		this.stacks = stacks;
		this.bb = bb;
		this.pb = pb;

		pb_nr=-1;
		IMBasicBlockList list = bb.getPred();
		for (int i=0;i<list.length();i++) {
			if (list.at(i)==pb) {
				pb_nr = i;
				break;
			}
		}

		if (pb_nr==-1) throw new CompileException("unknown pred. basic block "+pb+" of "+bb);
	}

	public boolean skipNode(IMNode node) {
		if (node instanceof IMPhi) return false;
		if (node instanceof IMStoreLocalVariable) return false;
		return true; 
	}

	public void visit(IMNode node) throws CompileException {
		if (node instanceof IMPhi) {
			IMPhi phi = (IMPhi)node;
			//opts.verbose("update o "+phi.toReadableString()+" "+pb_nr+":"+phi.getSourceVar(pb_nr));
			IMNode old = phi.getSource(pb_nr);

			IMSlot ovar = old.getIMSlot();
			if (ovar==null) {
				opts.warn("bad update "+phi.toReadableString()+" "+pb_nr+":"+phi.getSourceVar(pb_nr));
			}
			Stack stack = (Stack)stacks.get(ovar);
			if (stack==null) {
				opts.warn("bad update "+ovar+" "+phi.toReadableString()+" "+pb_nr+":"+phi.getSourceVar(pb_nr));
			}
			IMSlot slot = (IMSlot)stack.top();
			// slot may be null if the variable is not defined!
			phi.setSourceVar(pb_nr, slot);

			//if (old!=null) slot.delete_use(old);
			if (slot!=null) slot.use(bb, phi.getSource(pb_nr));
			//if (slot!=null) slot.use(bb, phi);
			//opts.verbose("update n "+phi.toReadableString());
		}
	}
}
