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

public class IMAMTConstant extends IMConstant {


	/* copy constuctor */
	protected IMAMTConstant() { }

	private IMClass memory_type_class;
	private int base;
	private String alias;

	public IMAMTConstant(IMMethod method, int bc, int bcpos, IMClass type, int base) {
		super(method,bc,BCBasicDatatype.REFERENCE,bcpos);
		this.memory_type_class = type;
		this.base = base;
		IMClass clazz = method.getIMClass();
		this.alias = clazz.getAlias()+"_mt_0x"+Integer.toHexString(base);
	}

	public int getAddr() {
		return base;
	}

	public IMClass getType() {
		return memory_type_class;
	}

	public boolean isTypeOf(IMClass type) {
		return memory_type_class.typeOf(type);
	}

	final public IMNode copy(IMCopyVisitor visitor) throws CompileException {

		IMAMTConstant nnode = new IMAMTConstant();
		nnode.copyNodeValues(visitor, this);

		nnode.memory_type_class = memory_type_class;
		nnode.base = base;
		nnode.alias = alias;

		if (visitor==null) return nnode;
		return visitor.visit(nnode,this);
	}

	final public void visitNodes(IMVisitor visitor) throws CompileException {
		if (visitor.skipNode(this)) return;
		visitor.visit(this);

	}

	public boolean isConstant() { return true; }

	public boolean hasConstant() { return true; }

	public IMConstant nodeToConstant() throws CompileException { return this; }

	public int getIntValue() { throw new Error(); }

	public long getLongValue() { return getIntValue(); }

	public float getFloatValue() { throw new Error(); }

	public double getDoubleValue() { return getFloatValue(); }

	public void setIntValue(int value) { throw new Error(); }

	public void setLongValue(long value) { throw new Error(); }

	public void setFloatValue(float value) { throw new Error(); }

	public void setDoubleValue(double value) { throw new Error(); }

	public boolean equalValue(IMConstant node) { throw new Error(); }

 	public boolean hasSideEffect() { return false; }

 	public boolean isConstObject() throws CompileException { return true; }

	public boolean isChecked(IMBasicBlock bb) throws CompileException { return true; }


	public void translate_global(Coder coder) throws CompileException {
		coder.global_add_allocConstObject(memory_type_class, alias);
		coder.global_add_init_field_hex("", "base", base);
		coder.global_add_init_end();
	}
	public String toReadableString() {
		return "MT";
	}

	public int costs() throws CompileException {
		return 3;
	}

	public void translate(Coder coder) throws CompileException {
		coder.add_allocGlobalConstObject(memory_type_class, alias, this);
	}

}
