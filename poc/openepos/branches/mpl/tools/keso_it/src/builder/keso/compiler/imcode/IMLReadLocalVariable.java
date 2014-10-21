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

public class IMLReadLocalVariable extends IMReadLocalVariable {


	/* copy constuctor */
	protected IMLReadLocalVariable() { }

	public IMLReadLocalVariable(IMMethod method, int bc, int bcpos, int var) {
		this(method,bc,bcpos,
			method.getMethodFrame().getLocalVariable(BCBasicDatatype.LONG,var));
	}

	public IMLReadLocalVariable(IMMethod method, int bc, int bcpos, IMSlot var) {
		super(method,bc,BCBasicDatatype.LONG,bcpos, var);
	}

	public IMLReadLocalVariable(IMMethod method, int bc, int type, int bcpos, IMSlot var) {
		super(method,bc,type,bcpos, var);
	}

	final public IMNode copy(IMCopyVisitor visitor) throws CompileException {

		IMLReadLocalVariable nnode = new IMLReadLocalVariable();
		nnode.copyNodeValues(visitor, this);

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



		return this;
	}

	public int costs() throws CompileException {
		return 3;
	}

}
