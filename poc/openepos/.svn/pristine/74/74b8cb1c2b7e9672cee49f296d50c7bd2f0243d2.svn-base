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

public class IMCheckCast extends IMUnaryNode {


	/* copy constuctor */
	protected IMCheckCast() { }

	protected ClassCPEntry cpEntry;

	public IMCheckCast(IMMethod method, int bc, int bcpos, ClassCPEntry clazz) {
		super(method,bc,BCBasicDatatype.REFERENCE,bcpos);
		int domainID=0;
		this.cpEntry=clazz;
		String className = cpEntry.getClassName();
		method.requireClass(domainID, className);
	}


	final public IMNode copy(IMCopyVisitor visitor) throws CompileException {

		IMCheckCast nnode = new IMCheckCast();
		nnode.copyNodeValues(visitor, this);

		nnode.cpEntry=cpEntry;
		nnode.rOpr = rOpr.copy(visitor);

		if (visitor==null) return nnode;
		return visitor.visit(nnode,this);
	}

	final public void visitNodes(IMVisitor visitor) throws CompileException {
		if (visitor.skipNode(this)) return;

		visitor.visit(this);

		rOpr.visitNodes(visitor);

	}

	final public IMNode constant_folding() throws CompileException {
    		rOpr=rOpr.constant_folding();

		if (rOpr instanceof IMNullConstant) {
			opts.verbose("++ fold checkcast NULL");
			return rOpr;
		}

		IMClass clazz = method.getIMClass(cpEntry.getClassName());
		if (rOpr.isTypeOf(clazz)) {
			opts.verbose("++ fold checkcast ("+cpEntry.getClassName()+")");
			return rOpr;
		}


		return this;
	}

	final public IMNode ssa_optimize() throws CompileException {
    		rOpr=rOpr.ssa_optimize();

		if (rOpr instanceof IMNullConstant) {
			return rOpr;
		}

		IMClass clazz = method.getIMClass(cpEntry.getClassName());
		if (rOpr.isTypeOf(clazz)) {
			opts.vverbose("++ fold checkcast ");
			return rOpr;
		}


		return this;
	}


	public void setEscapePath(IMNode node) throws CompileException {
		rOpr.setEscapePath(node);
	}

	public IMNode getEscapePath() throws CompileException {
		return rOpr.getEscapePath();
	}

	public void analyseCall() throws CompileException {
		ClassStore rep = method.getClassStore();
		IMClass clazz = method.getIMClass(cpEntry.getClassName());
		opts.vverbose("# need check cast "+clazz.getAlias());
		rep.needCheckCast(clazz);
	}

	public boolean isChecked(IMBasicBlock bb) throws CompileException {
		return rOpr.isChecked(bb);
	}

	public IMBasicBlock inlineMethodCalls(IMBasicBlock current, IMNode prev) throws CompileException {
		if (!opts.doInlineRValues()) return null;
		return rOpr.inlineMethodCalls(current, prev);
	}
	public IMNode processStack(VirtualOperantenStack stack, IMNode extra_ops) throws CompileException {
		method.isConst=false;
		method.isPure=false;
		rOpr = stack.pop();
		assertIsReference(rOpr);
		stack.push(this);
		return null;
	}

	public String toReadableString() {
		return "("+cpEntry.getClassName()+")("+rOpr.toReadableString()+")";
	}

	public String dumpBC() {return "CheckCast";}

	public int costs() throws CompileException {
		return rOpr.costs() + 50;

	}

	public void translate(Coder coder) throws CompileException {
		IMClass clazz = method.getIMClass(cpEntry.getClassName());
		coder.add_class(clazz.getAlias());

		if (clazz.isInterface()) {
			coder.add("CHK_IFACE_ID");
			coder.add(clazz.getClassTypeID());
			coder.add('(');
			rOpr.translate(coder);
			coder.add(')');
		} else {
			ClassTypeInfo type = clazz.getClassTypeInfo();
			if (rOpr.isChecked(coder) && !type.hasSubTypes()) {
				coder.add("(keso_checkcast_fast(");
				coder.add(clazz.getClassID());
				coder.add(',');
			} else {
				coder.add("(keso_checkcast(");
				coder.add(clazz.getClassID());
				coder.add(',');
				if (opts.hasOption("inline_checkcast")) {
					coder.add(clazz.getAlias().toUpperCase());
					coder.add("_RANGE,");
				}
			} 
			coder.add("(object_t*)");
			rOpr.translate(coder);
			coder.add("))");
		}
	}

}
