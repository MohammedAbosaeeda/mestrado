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

public class IMPutStatic extends IMNode {

	protected IMNode rvalue;

	/* copy constuctor */
	protected IMPutStatic() { }

	protected FieldRefCPEntry cpEntry;
	protected IMField imfield=null;

	public IMPutStatic(IMMethod method, int bc, int bcpos, FieldRefCPEntry field) {
		super(method, bc, BCBasicDatatype.toBasicDatatype(field.getMemberTypeDesc()), bcpos);
		int domainID=0;
		this.cpEntry=field;
		method.requireClass(domainID, cpEntry.getClassName());
		String fieldType = cpEntry.getMemberTypeDesc();
		if (fieldType.charAt(0)=='L') {
			method.requireClass(domainID, fieldType.substring(1,fieldType.length()-1));
		}
	}

	final public IMNode copy(IMCopyVisitor visitor) throws CompileException {


		IMPutStatic nnode = new IMPutStatic();
		nnode.copyNodeValues(visitor, this);

		nnode.cpEntry=cpEntry;

		nnode.imfield = imfield;
		nnode.rvalue = rvalue.copy(visitor);

		if (visitor==null) return nnode;
		return visitor.visit(nnode,this);
	}

	final public void visitNodes(IMVisitor visitor) throws CompileException {
		if (visitor.skipNode(this)) return;


		visitor.visit(this);


		rvalue.visitNodes(visitor);

	}

	final public IMNode constant_folding() throws CompileException {
		ClassStore repository = method.getClassStore();

		rvalue = rvalue.constant_folding();

		if (repository.omitStaticField(cpEntry.getClassName(), cpEntry.getMemberTypeDesc(), cpEntry.getMemberName())) {
			opts.verbose("++ omit put static");
			if (!rvalue.hasSideEffect()) return null;
			return rvalue;
		} 


		return this;
	}

	final public IMNode ssa_optimize() throws CompileException {
		IMBasicBlock basicBlock = method.getCurrentBasicBlock();
		if (basicBlock==null) throw new CompileException("no bb in "+method.getAlias());

		ClassStore repository = method.getClassStore();

		rvalue = rvalue.ssa_optimize();

		if (repository.omitStaticField(cpEntry.getClassName(), cpEntry.getMemberTypeDesc(), cpEntry.getMemberName())) {
			opts.vverbose("++ omit put static");
			if (rvalue instanceof IMNew) return null;
			if (rvalue instanceof IMNewArray) return null;
			if (rvalue instanceof IMNewObjArray) return null;
			if (rvalue instanceof IMNewMultiArray) return null;
			if (!rvalue.hasSideEffect()) return null;
			return rvalue;
		} 

		if (opts.hasOption("ssa_alias_prop") && datatype==BCBasicDatatype.REFERENCE) {
			if (rvalue instanceof IMAReadLocalVariable) {
				IMSlot var = ((IMAReadLocalVariable)rvalue).getIMSlot();
				method.registerAlias(basicBlock, var, this);
			} else {
				method.removeAlias(basicBlock, this);
			}
		}


		return this;
	}


	public IMBasicBlock inlineMethodCalls(IMBasicBlock current, IMNode prev) throws CompileException {
		if (!opts.doInlineRValues()) return null;
		return rvalue.inlineMethodCalls(current, prev);
	}

	public boolean doEscape(IMNode node, boolean isArgument) throws CompileException {
		return true;
	}

	final public IMNode whyEscape(IMNode node, boolean isArgument) throws CompileException {
		return this;
	}

	public void escapeAnalyses(int mode) throws CompileException {
		if (mode!=IMEscapeVisitor.PREPARE) return;
		rvalue.setEscapePath(this);
	}

	public void analyseCall() throws CompileException {
		method.isPure  = false;
		method.isConst = false;

		ClassStore repository = method.getClassStore();

		IMField fld = method.getIMClass(cpEntry.getClassName()).getField(cpEntry);

		if (fld==null) throw new CompileException ("field "+
				method.getIMClass().getClassName()+":"+cpEntry.getMemberName()+" not found!");

		if (method.getMethodName().equals("<clinit>") && fld.isFinal()) {
			repository.registerIMPutStatic(cpEntry.getClassName(), cpEntry.getMemberName(), rvalue);
		} else {
			repository.registerIMPutStatic(cpEntry.getClassName(), cpEntry.getMemberName(), null);
		}

		IMClass field_class = repository.getClass(cpEntry.getClassName());
		imfield = field_class.getField(cpEntry);
		repository.registerStaticRefField(imfield);
	}

	public String toMemAlias() {
		return cpEntry.getClassName()+"."+cpEntry.getMemberName();
	}
	public IMNode processStack(VirtualOperantenStack stack, IMNode extra_ops) throws CompileException {
		method.isConst=false;
		method.isPure=false;
		rvalue = stack.pop();
		stack.store(bcPosition,extra_ops);
		return this;
	}

	public String toReadableString() {
		return cpEntry.getMemberName()+" = "+rvalue.toReadableString();
	}

	public String dumpBC() {return "PutStatic";}

	public int costs() throws CompileException {
		if (opts.isSingleDomainSystem()) return rvalue.costs() + 10;
		return rvalue.costs() + 15;
	}

	public void translate(Coder coder) throws CompileException {
		IMClass clazz = method.getIMClass(cpEntry.getClassName());
		coder.add_class(clazz.getAlias());
		coder.add_putStatic(imfield,datatype,rvalue);
	}

}
