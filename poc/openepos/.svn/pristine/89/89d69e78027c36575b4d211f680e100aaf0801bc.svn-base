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

public class IMAReadLocalVariable extends IMReadLocalVariable {


	/* copy constuctor */
	protected IMAReadLocalVariable() { }

	private int alength = -1;


	public IMAReadLocalVariable(IMMethod method, int bc, int bcpos, int var) {
		this(method,bc,bcpos,
			method.getMethodFrame().getLocalVariable(BCBasicDatatype.REFERENCE,var));
	}

	public IMAReadLocalVariable(IMMethod method, int bc, int bcpos, IMSlot var) {
		super(method,bc,BCBasicDatatype.REFERENCE,bcpos, var);
	}

	public IMAReadLocalVariable(IMMethod method, int bc, int type, int bcpos, IMSlot var) {
		super(method,bc,type,bcpos, var);
	}


	public int getArrayLength() throws CompileException {
		return alength;
	}

	final public IMNode copy(IMCopyVisitor visitor) throws CompileException {

		IMAReadLocalVariable nnode = new IMAReadLocalVariable();
		nnode.copyNodeValues(visitor, this);

		nnode.escape_path = escape_path;
		if (visitor==null) {
			nnode.var=var;
			return nnode;
		}
		if (var!=null) nnode.var=visitor.adjustSlot(var);

		if (visitor==null) return nnode;
		return visitor.visit(nnode,this);
	}

	final public void visitNodes(IMVisitor visitor) throws CompileException {
		if (visitor.skipNode(this)) return;

		visitor.visit(this);


	}

	final public IMNode ssa_optimize() throws CompileException {
		IMBasicBlock basicBlock = method.getCurrentBasicBlock();
		if (basicBlock==null) throw new CompileException("no bb in "+method.getAlias());


		if (var==null) return this;

		IMStoreLocalVariable store = (IMStoreLocalVariable)var.getDefinedStatement();
		if (store==null) {
			return this;
		}

		// bad idea!
		// IMNode alias = store.getOperant().ssa_optimize();
		IMNode alias = store.getOperant();

		// constant propagation
		if (alias.isConstant() && !opts.hasOption("ssa_no_const_prop")) {
			opts.vverbose("++ propagate "+alias.toReadableString()+"->"+var);
			var.delete_use(this);
			return alias.nodeToConstant().copy(null);
		}

		// copy propagation
		if (alias instanceof IMReadLocalVariable) {
			IMSlot nvar=((IMReadLocalVariable)alias).getIMSlot();
			opts.vverbose("++ propagate "+nvar+"->"+var);
			var.delete_use(this);

			if (nvar==null) throw new CompileException("undefined value for "+var);
			IMReadLocalVariable nread = method.readLocal(getBCPosition(), nvar);
			nvar.use(basicBlock, nread);

			return nread;
		}

		if (alias.isFinalStatic() && opts.hasOption("ssa_fwd_statics")) {
			// only copy propagate single reads
			if (var.numberOfUses()==1) {
				//alias = alias.copy(null);
				//var.delete_use(this);
				return alias;
			}
		}


		if (!opts.hasOption("ssa_no_array_len") && alength==-1) {
			alength = alias.getArrayLength(); 
			if (alength!=-1) opts.verbose("++ propagate constant array length "+var+"["+alength+"]");
		}


		return this;
	}


	private IMNode escape_path = null;
	public boolean doEscape(IMNode node, boolean isArgument) throws CompileException {
		if (escape_path==null) {
			opts.warn(this.lineInfo()+": read "+toReadableString()+" with unknown escape path!");
			return false;
		}
		return escape_path.doEscape(this, isArgument);
	}

	final public IMNode whyEscape(IMNode node, boolean isArgument) throws CompileException {
		if (escape_path==null) {
			return this;
		}
		return escape_path.whyEscape(this, isArgument);
	}

	public void setEscapePath(IMNode node) throws CompileException {
		if (node == null ) throw new Error("upps");
		escape_path = node;
	}

	public IMNode getEscapePath() throws CompileException {
		return escape_path;
	}

	public String emit() throws CompileException {
		return var.toString();
	}

	public int costs() throws CompileException {
		return 3;
	}

}
