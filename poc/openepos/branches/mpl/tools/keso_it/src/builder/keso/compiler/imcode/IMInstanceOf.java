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

public class IMInstanceOf extends IMNode {

	protected IMNode rOpr;

	/* copy constuctor */
	protected IMInstanceOf() { }

	protected ClassCPEntry cpEntry;

	public IMInstanceOf(IMMethod method, int bc, int bcpos, ClassCPEntry clazz) {
		super(method,bc,BCBasicDatatype.BOOLEAN,bcpos);
		int domainID=0;
		this.cpEntry=clazz;
		String className = cpEntry.getClassName();
		method.requireClass(domainID, className);
	}


	final public IMNode copy(IMCopyVisitor visitor) throws CompileException {

		IMInstanceOf nnode = new IMInstanceOf();
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
		rOpr = rOpr.constant_folding();

		if (rOpr instanceof IMNullConstant) {
			opts.verbose("+ fold instanceof => 0");
			return method.createIMIConstant(0, rOpr.getBCPosition());
		}

		IMClass clazz = method.getIMClass(cpEntry.getClassName());
		if (rOpr.isTypeOf(clazz)) {
			opts.verbose("+ fold instanceof => 1");
			return method.createIMIConstant(1, rOpr.getBCPosition());
		}


		return this;
	}

	final public IMNode ssa_optimize() throws CompileException {
		rOpr = rOpr.ssa_optimize();

		if (rOpr instanceof IMNullConstant) {
			opts.vverbose("+ fold instanceof => 0");
			return method.createIMIConstant(0, rOpr.getBCPosition());
		}

		IMClass clazz = method.getIMClass(cpEntry.getClassName());
		if (rOpr.isTypeOf(clazz)) {
			opts.vverbose("+ fold instanceof => 1");
			return method.createIMIConstant(1, rOpr.getBCPosition());
		}


		return this;
	}


	public void analyseCall() throws CompileException {
		ClassStore rep = method.getClassStore();
		IMClass clazz = method.getIMClass(cpEntry.getClassName());
		opts.vverbose("# need InstanceOf "+clazz.getAlias());
		rep.needInstanceOf(clazz);
	}

	final public void escapeAnalyses(int mode) throws CompileException {
		if (mode!=IMEscapeVisitor.PREPARE) return;
		rOpr.setEscapePath(IMNode.NONE);
	}

	public IMNode processStack(VirtualOperantenStack stack, IMNode extra_ops) throws CompileException {
		rOpr = stack.pop();
		assertIsReference(rOpr);
		stack.push(this);
		return null;
	}

	public String toReadableString() {
		return "("+rOpr.toReadableString()+" instanceof "+cpEntry.getClassName()+")";
	}

	public String dumpBC() {return "InstanceOf";}

	public int costs() throws CompileException {
		return rOpr.costs() + 50;

	}

	public void translate(Coder coder) throws CompileException {
		IMClass clazz = method.getIMClass(cpEntry.getClassName());
		coder.add_class(clazz.getAlias());

		if (clazz.isInterface()) {
			coder.add("INSTANCEOF_IFACE_ID");
			coder.add(clazz.getClassTypeID());
			coder.add('(');
			rOpr.translate(coder);
			coder.add(')');
		} else {
			ClassTypeInfo type = clazz.getClassTypeInfo();
			if (rOpr.isChecked(coder) && !type.hasSubTypes()) {
				coder.add("keso_instanceof_fast(");
				coder.add(clazz.getClassID());
				coder.add(',');
			} else {
				coder.add("keso_instanceof(");
				coder.add(clazz.getClassID());
				coder.add(',');
				if (opts.hasOption("inline_checkcast")) {
					coder.add(clazz.getAlias().toUpperCase());
					coder.add("_RANGE,");
				}
			} 
			coder.add("(object_t*)");
			rOpr.translate(coder);
			coder.add(')');
		}
	}

}
