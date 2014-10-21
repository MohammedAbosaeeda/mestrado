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

public class IMReturnSubroutine extends IMBranch {


	/* copy constuctor */
	protected IMReturnSubroutine() { }

	protected IMSlot var;

	public IMReturnSubroutine(IMMethod method, int bc, int bcpos, int var) {
		super(method,bc,BCBasicDatatype.REFERENCE,bcpos);
		this.var = method.getMethodFrame().getLocalVariable(BCBasicDatatype.REFERENCE,var);
	}

	public IMSlot getIMSlot() {
		return var;
	}

	public void setIMSlot(IMSlot slot) {
		var = slot;	
	}

	final public IMNode copy(IMCopyVisitor visitor) throws CompileException {

		IMReturnSubroutine nnode = new IMReturnSubroutine();
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

	public IMNode processStack(VirtualOperantenStack stack, IMNode extra_ops) throws CompileException {
		IntegerHashtable ret_index = method.getReturnTargets();
		int keys[] = ret_index.sortedKeys();
		if (targets==null) {
			targets = new IMBasicBlock[keys.length];
			for (int i=0;i<keys.length;i++) {
				targets[i]=(IMBasicBlock)ret_index.get(keys[i]);
			}
		} else {
			if (keys.length != targets.length) {
				opts.warn("incomplete jump targets in return subroutine!");
			}
		}
		return this;
	}

	public String toReadableString() {
		return "ret "+var.toString();	    
	}

	public String dumpBC() {return "ReturnSubroutine";}

	public void translate(Coder coder) throws CompileException {
		coder.add("switch ((jshort)");
		coder.add(var.toString());
		coder.add(") {\n");
		IntegerHashtable ret_index = method.getReturnTargets();
		int keys[] = ret_index.sortedKeys();
		for (int i=0;i<keys.length;i++) {
			IMBasicBlock retbb = (IMBasicBlock)ret_index.get(keys[i]);
			coder.add("\t\tcase ");
			coder.add(keys[i]);
			coder.add(": goto ");
			coder.add(retbb.toLabel());
			coder.add(";\n");
		}
		coder.add("\t}");
	}

}
