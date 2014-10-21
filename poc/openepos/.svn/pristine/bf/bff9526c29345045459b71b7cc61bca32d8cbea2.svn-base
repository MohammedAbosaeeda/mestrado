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

public class IMGetStatic extends IMNode {


	/* copy constuctor */
	protected IMGetStatic() { }

	protected FieldRefCPEntry cpEntry;
	protected IMField imfield=null;

	public IMGetStatic(IMMethod method, int bc, int bcpos, FieldRefCPEntry field) {
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


		IMGetStatic nnode = new IMGetStatic();
		nnode.copyNodeValues(visitor, this);

		nnode.cpEntry=cpEntry;

		nnode.imfield = imfield;	

		if (visitor==null) return nnode;
		return visitor.visit(nnode,this);
	}

	final public void visitNodes(IMVisitor visitor) throws CompileException {
		if (visitor.skipNode(this)) return;

		visitor.visit(this);


	}

	final public IMNode constant_folding() throws CompileException {
		ClassStore repository = method.getClassStore();

		IMNode obj = repository.forwardStaticField(method, cpEntry.getClassName(), cpEntry.getMemberName());
		if (obj!=null) {
			obj = obj.constant_folding();
			if (obj.isConstObject()) {
				opts.verbose("++ forward get field "+obj.toReadableString());
				return obj;
			}
		}

		if (repository.omitStaticField(cpEntry.getClassName(), cpEntry.getMemberTypeDesc(), cpEntry.getMemberName())) {
			opts.verbose("++ omit get static "+cpEntry.getClassName()+" "+cpEntry.getMemberName());
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
		}



		return this;
	}

	final public IMNode ssa_optimize() throws CompileException {
		IMBasicBlock basicBlock = method.getCurrentBasicBlock();
		if (basicBlock==null) throw new CompileException("no bb in "+method.getAlias());

		ClassStore repository = method.getClassStore();

		if (repository.omitStaticField(cpEntry.getClassName(), cpEntry.getMemberTypeDesc(), cpEntry.getMemberName())) {
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
		}

		IMNode obj = repository.forwardStaticField(method, cpEntry.getClassName(), cpEntry.getMemberName());
		if (obj!=null) {
			obj = obj.ssa_optimize();
			if (obj.isConstObject()) {
				opts.vverbose("++ forward get field "+obj.toReadableString());
				return obj;
			}
		}

		if (opts.hasOption("ssa_alias_prop") && datatype==BCBasicDatatype.REFERENCE) {
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

	public boolean hasSideEffect() { return false; }

 
	/**
	 * Nodes who only read but not change the global state
	 * are pure.
	 */
	public boolean isPureNode() { return true; }


	public boolean isFinalStatic() throws CompileException {
		IMClass field_class = method.getIMClass(cpEntry.getClassName());
		IMField field = field_class.getField(cpEntry);
		return field.isFinal();
	}

	public String emit() throws CompileException {
		return imfield.getMacroName();
	}

	public boolean isVolatile() throws CompileException {
		IMClass clazz = method.getIMClass(cpEntry.getClassName());
		IMField field = clazz.getField(cpEntry);
		return field.isVolatile();
	}

	public void analyseCall() throws CompileException {
		ClassStore repository = method.getClassStore();
		repository.registerIMGetStatic(cpEntry.getClassName(), cpEntry.getMemberName());

		IMClass field_class = repository.getClass(cpEntry.getClassName());
		imfield = field_class.getField(cpEntry);
		if (imfield==null) {
			opts.warn("Field "+cpEntry.getMemberName()+" in class "+field_class.getClassName()+" not found!");
			return;
		} 
		repository.registerStaticRefField(imfield);
	}
	public IMNode processStack(VirtualOperantenStack stack, IMNode extra_ops) throws CompileException {
		method.isConst=false;
		stack.push(this);
		return null;
	}

	public String toReadableString() {
		return cpEntry.getClassName()+"."+cpEntry.getMemberName();
	}

	public String dumpBC() {return "GetStatic "+cpEntry.getMemberName();}

	public int costs() throws CompileException {
    		if (opts.isSingleDomainSystem()) return 5;
		return 15;
	}

	public void translate(Coder coder) throws CompileException {
		IMClass clazz = method.getIMClass(cpEntry.getClassName());
		coder.add_class(clazz.getAlias());
		coder.add_getStatic(imfield, datatype); 
	}

}
