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

public class IMGoto extends IMBranch {


	/* copy constuctor */
	protected IMGoto() { }

	public IMGoto(IMMethod method, int bc, int bcpos, IMBasicBlock label) {
		super(method,bc,-1,bcpos);
		targets = new IMBasicBlock[1];
		targets[0]=label;
	}


	final public IMNode copy(IMCopyVisitor visitor) throws CompileException {

		IMGoto nnode = new IMGoto();
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


	public boolean hasShortcut() {
		IMBasicBlock basicBlock = method.getCurrentBasicBlock();
		if (basicBlock.getSucc()==null) return false;
		return (basicBlock.next()==basicBlock.getSucc().at(0));
	}
	public IMNode processStack(VirtualOperantenStack stack, IMNode extra_ops) throws CompileException {
		return this;
	}

	public String toReadableString() {
		if (hasShortcut()) return "/* goto "+targets[0].toLabel()+" */";
		return "goto "+targets[0].toLabel();	    
	}

	public String dumpBC() {return "Goto "+targets[0].getBCPosition();}

	public int costs() throws CompileException {
		if (hasShortcut()) return 0;
		return 2;
	}

	public void translate(Coder coder) throws CompileException {
		IMBasicBlock basicBlock = method.getCurrentBasicBlock();

		IMBasicBlock target = basicBlock.getSucc().at(0);
		if (target==null) {
			opts.critical("ignore blind goto in "+method.getAlias());
			coder.add("/* goto null */");
			return;
		}
		if (hasShortcut()) {
			if (opts.hasDbgSymbols()) {
				coder.add("/* goto ");
				coder.add(target);
				coder.add(" */");
			}
			return;
		} 

		coder.add("goto ");
		coder.add(target);
	}

}
