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

import java.util.Hashtable;

final public class IMInlineVisitor implements IMCopyVisitor {

	private IMMethod host_method;
	private IMMethodFrame host_frame;
	private IMNode   self;
	private IMNode[] args;
	private IMBasicBlock next_bb;
	private IMNode	 call;
	private IMNode	 current;
	private int bcpos;

	private BuilderOptions opts;

	private Hashtable allready_seen = new Hashtable(); 

	public IMInlineVisitor(IMMethod host_method, IMNode self, IMNode[] args, int bcpos,
			IMBasicBlock next_bb, IMNode current, IMNode call) {
		this.host_method = host_method;
		this.opts = host_method.getOptions();
		this.host_frame  = host_method.getMethodFrame();
		this.self = self;
		this.args = args;
		this.bcpos = bcpos;
		this.call = call;
		this.current = current;
		this.next_bb = next_bb;

		if (next_bb==null) throw new Error();
	}

	public IMNode visit(IMNode nnode, IMNode onode) throws CompileException {
		if (nnode instanceof IMEpilog) {
			if (call==null) return null;

			IMSlot ret_slot = onode.getMethod().getReturnValue();
			if (ret_slot==null) return null;
			IMReadLocalVariable ret_value = host_method.readLocal(call.getBCPosition(),adjustSlot(ret_slot)); 
			IMReplaceVisitor repl = new IMReplaceVisitor(host_method,call,ret_value);

			nnode = current.copy(repl);
		}
		nnode.method = host_method;
		nnode.bcPosition = bcpos;
		return nnode;
	}

	public IMSlot adjustSlot(IMSlot foreigenSlot) throws CompileException {
		return host_frame.adjustSlot(foreigenSlot);
	}

	public IMBasicBlock updateBlock(IMBasicBlock label) throws CompileException {
		if (label==null) return null;

		IMBasicBlock nl = (IMBasicBlock)allready_seen.get(label);
		if (nl==null) {
			nl = label.createStub(host_method, this);
			nl.copied = true;
			nl.method = host_method;
			nl.bcPosition = bcpos;
			allready_seen.put(nl, nl);
			allready_seen.put(label, nl);
			label.updateStub(nl,this);
		}
		
		return nl;
	}
}

