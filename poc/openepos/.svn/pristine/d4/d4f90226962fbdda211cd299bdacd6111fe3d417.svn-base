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

public class IMAStoreLocalVariable extends IMStoreLocalVariable {


	/* copy constuctor */
	protected IMAStoreLocalVariable() { }

	public IMAStoreLocalVariable(IMMethod method, int bc, int bcpos, int var) {
		this(method,bc,bcpos,
			method.getMethodFrame().getLocalVariable(BCBasicDatatype.REFERENCE,var));
	}

	public IMAStoreLocalVariable(IMMethod method, int bc, int bcpos, IMSlot var) {
		super(method,bc,BCBasicDatatype.REFERENCE,bcpos, var);
	}

	public IMAStoreLocalVariable(IMMethod method, int bc, int type, int bcpos, IMSlot var) {
		super(method,bc,type,bcpos, var);
	}

	final public IMNode copy(IMCopyVisitor visitor) throws CompileException {

		IMAStoreLocalVariable nnode = new IMAStoreLocalVariable();
		nnode.copyNodeValues(visitor, this);


		nnode.operant = operant.copy(visitor);

		if (visitor==null) {
			nnode.var=var;
			return nnode;
		}
		nnode.var=visitor.adjustSlot(var);

		if (visitor==null) return nnode;
		return visitor.visit(nnode,this);
	}

	final public void visitNodes(IMVisitor visitor) throws CompileException {
		if (visitor.skipNode(this)) return;
		operant.visitNodes(visitor); 

		visitor.visit(this);


	}

	final public IMNode constant_folding() throws CompileException {
		operant = operant.constant_folding();


		return this;
	}

	final public IMNode ssa_optimize() throws CompileException {
		IMBasicBlock basicBlock = method.getCurrentBasicBlock();
		if (basicBlock==null) throw new CompileException("no bb in "+method.getAlias());

		operant = operant.ssa_optimize();
		if (var.hasNoUses()) {
			if (!operant.isPureNode()) {
				opts.vverbose("++ no store "+toReadableString());
				return operant;
			}
			opts.vverbose("++ remove "+toReadableString());
			IMDeadCodeVisitor visitor = new IMDeadCodeVisitor(method);
			operant.visitNodes(visitor);
			return null;
		}

		if (opts.hasOption("ssa_alias_prop")) {
			if (datatype==BCBasicDatatype.REFERENCE) {
				if (operant instanceof IMGetField || operant instanceof IMGetStatic) {
					method.registerAlias(basicBlock, var, operant);
				}
			}

			if (opts.hasOption("_X_ssa_alias_prop_all")) {
				if (operant instanceof IMGetField || operant instanceof IMGetStatic) {
					method.registerAlias(basicBlock, var, operant);
				}
			}
		}


		return this;
	}



	public boolean doEscape(IMNode node, boolean isArgument) throws CompileException {
		return var.doEscape(isArgument);
	}

	final public IMNode whyEscape(IMNode node, boolean isArgument) throws CompileException {
		return var.whyEscape(isArgument);	
	}

	public void escapeAnalyses(int mode) throws CompileException {
		if (mode!=IMEscapeVisitor.PREPARE) return;
		operant.setEscapePath(this);
	}

	public IMReadLocalVariable getReadOperation() throws CompileException {
		return new IMAReadLocalVariable(method,21,bcPosition,var);
	}

	public int costs() throws CompileException {
		return operant.costs() + 2;
	}

	public void translate(Coder coder) throws CompileException {
		IMBasicBlock basicBlock = method.getCurrentBasicBlock();

		if (operant instanceof IMPhi) coder.add("/* ");
		coder.add(var.toString());
		coder.add(" = ");
		operant.translate(coder);

		if (operant instanceof IMConstant) {
			var.propagateConstant(basicBlock, operant.nodeToConstant());
		} else {
			var.propagateConstant(basicBlock, null);
		}

		if (operant.isChecked(coder)) {
			if (opts.hasVerbose()) coder.addln_befor("/* "+var+" = !null */");
			var.setChecked(coder.getCurrentBasicBlock());
		} else {
			if (opts.hasVerbose()) coder.addln_befor("/* "+var+" = ?null? */");
			var.setChecked(null);
		}
		if (operant instanceof IMPhi) coder.add(" */");
	}

}
