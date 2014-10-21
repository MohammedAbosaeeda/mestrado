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

public class IMCall extends IMBranch {


	/* copy constuctor */
	protected IMCall() { }

	private int index;

	public IMCall(IMMethod method, int bc, int bcpos, IMBasicBlock label, IMBasicBlock ret_label) {
		super(method,bc,-1,bcpos);
		targets = new IMBasicBlock[1];
		targets[0]=label;
		index = method.registerReturnAddr(ret_label);
	}


	final public IMNode copy(IMCopyVisitor visitor) throws CompileException {

		IMCall nnode = new IMCall();
		nnode.copyNodeValues(visitor, this);


		nnode.targets = new IMBasicBlock[targets.length];
		for (int i=0;i<targets.length;i++) nnode.targets[i] = visitor.updateBlock(targets[i]);


		if (visitor==null) return nnode;
		return visitor.visit(nnode,this);
	}

	final public void visitNodes(IMVisitor visitor) throws CompileException {
		if (visitor.skipNode(this)) return;

		visitor.visit(this);


		//for (int i=0;i<targets.length;i++) targets[i].visitNodes(visitor);


	}

	public IMNode processStack(VirtualOperantenStack stack, IMNode extra_ops) throws CompileException {
		stack.push(method.createPopReturnAddr(bcPosition));
      		return this;
	}

	public String toReadableString() {
		return "gosub "+targets[0].toLabel();	    
	}

	public String dumpBC() {return "Call";}

	public void translate(Coder coder) throws CompileException {
		coder.add("ret_addr = ");
		coder.add(index);
		coder.add("; goto ");
		coder.add(targets[0].toLabel());
	}

}
