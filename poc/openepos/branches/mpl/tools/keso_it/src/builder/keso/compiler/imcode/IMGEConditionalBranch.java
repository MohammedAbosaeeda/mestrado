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

public class IMGEConditionalBranch extends IMConditionalBranch {


	/* copy constuctor */
	protected IMGEConditionalBranch() { }

	public IMGEConditionalBranch(IMMethod method, int bc, int bcpos, IMBasicBlock label, IMBasicBlock next) {
		super(method,bc,bcpos,label,next);
	}
	


	final public IMNode copy(IMCopyVisitor visitor) throws CompileException {

		IMGEConditionalBranch nnode = new IMGEConditionalBranch();
		nnode.copyNodeValues(visitor, this);


		nnode.targets = new IMBasicBlock[targets.length];
		for (int i=0;i<targets.length;i++) nnode.targets[i] = visitor.updateBlock(targets[i]);

		nnode.rOpr = rOpr.copy(visitor);
		nnode.lOpr = lOpr.copy(visitor);

		if (visitor==null) return nnode;
		return visitor.visit(nnode,this);
	}

	final public void visitNodes(IMVisitor visitor) throws CompileException {
		if (visitor.skipNode(this)) return;

		visitor.visit(this);


		//for (int i=0;i<targets.length;i++) targets[i].visitNodes(visitor);

		rOpr.visitNodes(visitor);
		lOpr.visitNodes(visitor);

	}

	final public IMNode constant_folding() throws CompileException {
		rOpr = rOpr.constant_folding();
		lOpr = lOpr.constant_folding();
		if (lOpr.isConstant() && rOpr.isConstant()) {
			opts.verbose("++ todo fold conditional branch");
		}


		return this;
	}

	final public IMNode ssa_optimize() throws CompileException {
		IMBasicBlock basicBlock = method.getCurrentBasicBlock();
		if (basicBlock==null) throw new CompileException("no bb in "+method.getAlias());

		rOpr = rOpr.ssa_optimize();
		lOpr = lOpr.ssa_optimize();
		if (lOpr.isConstant() && rOpr.isConstant()) {
			opts.verbose("++ todo fold conditional branch");
				if (opts.hasOption("ssa_fold_cbranch")) {
				int lvalue = lOpr.nodeToConstant().getIntValue();
				int rvalue = rOpr.nodeToConstant().getIntValue();
				IMBasicBlockList succ = basicBlock.getSucc(); 
				if (lvalue>=rvalue) {
					opts.verbose("++ todo fold "+lvalue+">="+rvalue+" is true");
					IMNode go = method.createGoto(succ.at(1), bcPosition);
					basicBlock.unlinkSucc(0);
					return go;
				} else {
					opts.verbose("++ todo fold "+lvalue+">="+rvalue+" is false");
					IMNode go = method.createGoto(succ.at(0), bcPosition);
					basicBlock.unlinkSucc(1);
					return go;
				}
			}
		}
		boolean use_cmp_fkt = opts.hasOption("use_cmp_fkt");
		if (!use_cmp_fkt && rOpr.isConstant() && lOpr instanceof IMCompare) {
			if (rOpr instanceof IMIConstant && rOpr.nodeToConstant().getIntValue()==0) {
				IMCompare cmp = (IMCompare)lOpr;
				IMNode clOpr = cmp.getLeftNode(); 
				IMNode crOpr = cmp.getRightNode(); 

				if (clOpr.getDatatype() == BCBasicDatatype.DOUBLE) {
					IMDSub sub = new IMDSub(method, -1, bcPosition);
					sub.setLeftNode(clOpr);
					sub.setRightNode(crOpr);
					lOpr = sub;
				}

				if (clOpr.getDatatype() == BCBasicDatatype.FLOAT) {
					IMFSub sub = new IMFSub(method, -1, bcPosition);
					sub.setLeftNode(clOpr);
					sub.setRightNode(crOpr);
					lOpr = sub;
				}
			}
		}


		return this;
	}

	public String toReadableString() {
		return "if ("+lOpr.toReadableString()+">="+rOpr.toReadableString()+") goto "+targets[1].toLabel();
	}

	public String dumpBC() {return "GEConditionalBranch";}

	public int costs() throws CompileException {
		return lOpr.costs() + rOpr.costs() + 10;

	}

	public void translate(Coder coder) throws CompileException {
		coder.add("if (");
		lOpr.translate(coder);
		coder.add(">=");
		rOpr.translate(coder);
		coder.add(") goto ");
		coder.add(targets[1].toLabel());
	}

}
