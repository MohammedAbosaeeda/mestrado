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

public class IMPutField extends IMNode {

	protected IMNode rvalue;
	protected IMNode obj;

	/* copy constuctor */
	protected IMPutField() { }

	protected FieldRefCPEntry cpEntry;

	public IMPutField(IMMethod method, int bc, int bcpos, FieldRefCPEntry field) {
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


		IMPutField nnode = new IMPutField();
		nnode.copyNodeValues(visitor, this);

		nnode.cpEntry=cpEntry;

		nnode.rvalue = rvalue.copy(visitor);
		nnode.obj = obj.copy(visitor); 

		if (visitor==null) return nnode;
		return visitor.visit(nnode,this);
	}

	final public void visitNodes(IMVisitor visitor) throws CompileException {
		if (visitor.skipNode(this)) return;


		visitor.visit(this);


		rvalue.visitNodes(visitor);
		obj.visitNodes(visitor); 

	}

	final public IMNode constant_folding() throws CompileException {
		ClassStore repository = method.getClassStore();

		rvalue = rvalue.constant_folding();

		if (repository.omitField(cpEntry.getClassName(), cpEntry.getMemberTypeDesc(), cpEntry.getMemberName())) {
			opts.verbose("++ omit put field");
			if (!rvalue.hasSideEffect()) return null;
			return rvalue;
		}

		obj = obj.constant_folding(); 


		return this;
	}

	final public IMNode ssa_optimize() throws CompileException {
		IMBasicBlock basicBlock = method.getCurrentBasicBlock();
		if (basicBlock==null) throw new CompileException("no bb in "+method.getAlias());

		ClassStore repository = method.getClassStore();

		rvalue = rvalue.ssa_optimize();

		if (repository.omitField(cpEntry.getClassName(), cpEntry.getMemberTypeDesc(), cpEntry.getMemberName())) {
			opts.vverbose("++ omit put field");
			if (rvalue instanceof IMNew) return null;
			if (rvalue instanceof IMNewArray) return null;
			if (rvalue instanceof IMNewObjArray) return null;
			if (rvalue instanceof IMNewMultiArray) return null;
			if (!rvalue.hasSideEffect()) return null;
			return rvalue;
		}

		obj = obj.ssa_optimize(); 

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


	private boolean initial_write = false;

	public IMBasicBlock inlineMethodCalls(IMBasicBlock current, IMNode prev) throws CompileException {
		if (!opts.doInlineRValues()) return null;
		return rvalue.inlineMethodCalls(current, prev);
	}

	public boolean doEscape(IMNode node, boolean isArgument) throws CompileException {
		return true;
		/*
		 This is more complex! 

		if (opts.hasOption("ssa_astack_less")) return true;
		if (obj.getIMSlot().doEscape(false)) return true;
		return false;
		*/
	}

	final public IMNode whyEscape(IMNode node, boolean isArgument) throws CompileException {
		if (!doEscape(this,false)) return null;
		return this;
	}

	public void analyseCall() throws CompileException {
		ClassStore repository = method.getClassStore();
		repository.registerIMPutField(cpEntry.getClassName(), cpEntry.getMemberName());
	}

	public void escapeAnalyses(int mode) throws CompileException {
		if (mode!=IMEscapeVisitor.PREPARE) return;
		obj.setEscapePath(IMNode.NONE);
		rvalue.setEscapePath(this);	
	}

	public String toMemAlias() {
		return obj.toReadableString()+"."+cpEntry.getMemberName();
	}
	public IMNode processStack(VirtualOperantenStack stack, IMNode extra_ops) throws CompileException {
		method.isConst=false;
		method.isPure=false;
		stack.store(bcPosition,extra_ops);
		rvalue = stack.pop();
		obj    = stack.pop();
		assertIsReference(obj);
		return this;
	}

	public String toReadableString() {
		return obj.toReadableString()+"."+cpEntry.getMemberName()+" = " + rvalue.toReadableString();
	}

	public String dumpBC() {return "PutField";}

	public int costs() throws CompileException {
		return obj.costs() + 10;
	}

	public void translate(Coder coder) throws CompileException {
		IMClass clazz = method.getIMClass(cpEntry.getClassName());
		coder.add_class(clazz.getAlias());
		coder.chk_ref(obj,method,bcPosition);
		if (datatype==BCBasicDatatype.REFERENCE) {
			String fieldType = cpEntry.getMemberTypeDesc();
			if (fieldType.charAt(0)=='L') {
				IMClass fieldClass = method.getIMClass(fieldType.substring(1,fieldType.length()-1));
				if (fieldClass==null) 
					opts.warn("class not found: "+fieldType);
				if (fieldClass!=null && fieldClass.isMemoryType()) {
					opts.warn("memory type store ignored!");
					coder.add_comment("memory type store ignored!");
					obj.translate(coder);
					return;
				}
			}
		}
		if (initial_write) {
			coder.add_befor("/* no write barrier needed becase "+obj.toReadableString()+"==null */");
			coder.add_putField_NoWrBr(clazz, obj, cpEntry.getMemberName(), datatype, rvalue); 
		} else {
			coder.add_putField(clazz, obj, cpEntry.getMemberName(), datatype, rvalue); 
		}
	}

}
