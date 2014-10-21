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

public class IMGetField extends IMNode {

	protected IMNode rOpr;

	/* copy constuctor */
	protected IMGetField() { }

	protected FieldRefCPEntry cpEntry;

	public IMGetField(IMMethod method, int bc, int bcpos, FieldRefCPEntry field) {
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


		IMGetField nnode = new IMGetField();
		nnode.copyNodeValues(visitor, this);

		nnode.cpEntry=cpEntry;

		nnode.rOpr=rOpr.copy(visitor);

		if (visitor==null) return nnode;
		return visitor.visit(nnode,this);
	}

	final public void visitNodes(IMVisitor visitor) throws CompileException {
		if (visitor.skipNode(this)) return;


		visitor.visit(this);


		rOpr.visitNodes(visitor);

	}

	final public IMNode constant_folding() throws CompileException {
		ClassStore repository = method.getClassStore();

		if (repository.omitField(cpEntry.getClassName(), cpEntry.getMemberTypeDesc(), cpEntry.getMemberName())) {
			opts.verbose("++ omit get field "+cpEntry.getClassName()+" "+cpEntry.getMemberName());
			switch (datatype) {
				case BCBasicDatatype.BOOLEAN:
				case BCBasicDatatype.BYTE:
				case BCBasicDatatype.CHAR:
				case BCBasicDatatype.SHORT:
				case BCBasicDatatype.INT:
					return method.createIMIConstant(0,bcPosition);
				case BCBasicDatatype.LONG:
					return method.createIMLConstant(0,bcPosition);
				case BCBasicDatatype.FLOAT:
					return method.createIMFConstant(0,bcPosition);
				case BCBasicDatatype.DOUBLE:
					return method.createIMDConstant(0,bcPosition);
				case BCBasicDatatype.REFERENCE:
					return this;
					//return method.createIMNullConstant(bcPosition);
				default:
					throw new CompileException("unkown datatype constant");
			}
		} else {
			rOpr=rOpr.constant_folding();
		}


		return this;
	}

	final public IMNode ssa_optimize() throws CompileException {
		IMBasicBlock basicBlock = method.getCurrentBasicBlock();
		if (basicBlock==null) throw new CompileException("no bb in "+method.getAlias());

		ClassStore repository = method.getClassStore();

		if (repository.omitField(cpEntry.getClassName(), cpEntry.getMemberTypeDesc(), cpEntry.getMemberName())) {
			opts.vverbose("++ omit get field");
			switch (datatype) {
				case BCBasicDatatype.BOOLEAN:
				case BCBasicDatatype.BYTE:
				case BCBasicDatatype.CHAR:
				case BCBasicDatatype.SHORT:
				case BCBasicDatatype.INT:
					return method.createIMIConstant(0,bcPosition);
				case BCBasicDatatype.LONG:
					return method.createIMLConstant(0,bcPosition);
				case BCBasicDatatype.FLOAT:
					return method.createIMFConstant(0,bcPosition);
				case BCBasicDatatype.DOUBLE:
					return method.createIMDConstant(0,bcPosition);
				case BCBasicDatatype.REFERENCE:
					return method.createIMNullConstant(bcPosition);
				default:
					throw new CompileException("unkown datatype constant");
			}
		} else {
			rOpr=rOpr.ssa_optimize();
		}

		if (opts.hasOption("ssa_alias_prop")) {
			IMSlot var = method.lookupAlias(basicBlock, this);
			if (var!=null) {
				IMNode rd_var = method.readLocal(getBCPosition(), var);
				opts.verbose("++ forward commen get field "+toReadableString());
				var.use(basicBlock, rd_var);
				return rd_var;
			}
		}


		return this;
	}


	/**
	 * Nodes who only read but not change the global state
	 * are pure.
	 */
	public boolean isPureNode() { return true; }


	
	public void escapeAnalyses(int mode) throws CompileException {
		rOpr.setEscapePath(IMNode.NONE);	
	}

	public void analyseCall() throws CompileException {
		ClassStore repository = method.getClassStore();
		repository.registerIMGetField(cpEntry.getClassName(), cpEntry.getMemberName());
	}

	public boolean isMemoryType() throws CompileException {
		if (datatype!=BCBasicDatatype.REFERENCE) return false;
		String fieldType = cpEntry.getMemberTypeDesc();
		if (fieldType.charAt(0)=='[') return false;
		IMClass fieldClass = method.getIMClass(fieldType.substring(1,fieldType.length()-1));
		if (fieldClass!=null) return fieldClass.isMemoryType();
		opts.warn("Cannot find field type "+fieldType.substring(1,fieldType.length()-1));
		return false;
	}

	public boolean isVolatile() throws CompileException {
		IMClass clazz = method.getIMClass(cpEntry.getClassName());
		IMField field = clazz.getField(cpEntry);
		return field.isVolatile();
	}

	/**
	 * This method is the right place to add a piece of code to global.c if needed.
	 * The imnode must be registered with 
	 * repository.registerGlobalTranslationCallback("mt_escape", this);
	 */
	public void translate_global(Coder coder) throws CompileException {
		IMClass mtclazz = method.getIMClass("keso/core/MT");
		if (mtclazz!=null) {
			coder.global_header_add("#define KESO_OBJ_TO_MT(_obj_) ");
			coder.global_header_add(mtclazz.getAlias().toUpperCase());
			coder.global_header_add("_OBJ(_obj_)\n");
			coder.global_add_fkt("mt_escape.c");
		}
	}

	public boolean translate_MT_Ref(Coder coder) throws CompileException {
		if (isMemoryType()) {
			IMClass clazz = method.getIMClass(cpEntry.getClassName());
			coder.add_class(clazz);
			if (rOpr instanceof IMAMTConstant) {
				coder.add("((");
				coder.add(clazz.getAlias());
				coder.add("_mt*)");
				coder.add_hex(((IMAMTConstant)rOpr).getAddr());
				coder.add(")->");
				coder.add(clazz.emitField(cpEntry.getMemberName()));
			} else {
				coder.chk_ref(rOpr,method,bcPosition);
				coder.add("(");
				coder.add(clazz.getAlias().toUpperCase());
				coder.add("_MT(");
				rOpr.translate(coder);
				coder.add(")->");
				coder.add(clazz.emitField(cpEntry.getMemberName()));
				coder.add(")");
			}
			return true;
		}
		return false;
	}
	public IMNode processStack(VirtualOperantenStack stack, IMNode extra_ops) throws CompileException {
		method.isConst=false;
		stack.store(bcPosition, extra_ops);
		rOpr = stack.pop();
		assertIsReference(rOpr);
		stack.push(this);
      		return null;
	}

	public String toReadableString() {
		return rOpr.toReadableString()+"."+cpEntry.getMemberName();
	}

	public String dumpBC() {return "GetField "+cpEntry.getMemberName();}

	public int costs() throws CompileException {
		return rOpr.costs() + 10;
	}

	public void translate(Coder coder) throws CompileException {
		IMClass clazz = method.getIMClass(cpEntry.getClassName());
		coder.add_class(clazz.getAlias());
		coder.chk_ref(rOpr,method,bcPosition);
		if (datatype==BCBasicDatatype.REFERENCE) {
			String fieldType = cpEntry.getMemberTypeDesc();
			IMClass fieldClass = method.getIMClass(fieldType.substring(1,fieldType.length()-1));
			if (fieldClass!=null && fieldClass.isMemoryType()) {
				ClassStore repository = method.getClassStore();
				repository.registerGlobalTranslationCallback("$mt_escape$", this);
				opts.warn("MT field escape! (not tested yet)");
				coder.add("keso_mt_escape(");
				coder.add(fieldClass.getClassID());
				coder.add(",");
				coder.add("(volatile void*)&(");
				coder.add(clazz.getAlias().toUpperCase());
				coder.add("_MT(");
				rOpr.translate(coder);
				coder.add(")->");
				coder.add(clazz.emitField(cpEntry.getMemberName()));
				coder.add("))");
				return;
			}
		} 
		coder.add_getField(clazz, rOpr, cpEntry.getMemberName());
	}

}
