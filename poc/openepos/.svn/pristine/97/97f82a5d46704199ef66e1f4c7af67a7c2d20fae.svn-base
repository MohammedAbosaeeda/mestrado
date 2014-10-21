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

public class IMIConstant extends IMConstant {


	/* copy constuctor */
	protected IMIConstant() { }

	protected int value;

	public IMIConstant(IMMethod method, int bc, int bcpos, int value) {
		super(method,bc,BCBasicDatatype.INT,bcpos);
		this.value=value;
	}

	public int getValue() {
		return value;
	}

	public int getIntValue() {
		return value;
	}

	public void setIntValue(int value) {
		this.value=value;
	}

	public boolean equalValue(IMConstant node) {
		return this.value==node.getIntValue();
	}

	public boolean equalValue(int value) {
		return this.value==value;
	}


	final public IMNode copy(IMCopyVisitor visitor) throws CompileException {

		IMIConstant nnode = new IMIConstant();
		nnode.copyNodeValues(visitor, this);

		nnode.value=value;

		if (visitor==null) return nnode;
		return visitor.visit(nnode,this);
	}

	final public void visitNodes(IMVisitor visitor) throws CompileException {
		if (visitor.skipNode(this)) return;

		visitor.visit(this);


	}


	public int getDatatype() {
		if (opts.hasOption("stack8") || opts.hasOption("stack16")) {
			if (value>-127 && 127>value) return BCBasicDatatype.BYTE;
			if (value>-32000 && 32000>value) return BCBasicDatatype.SHORT;
		}
		return datatype;
	}
	public String toReadableString() {
		return Integer.toString(value); 
	}

	public String dumpBC() {return "IConstant";}

	public int costs() throws CompileException {
		return 3;
	}

	public void translate(Coder coder) throws CompileException {
		if (opts.is8BitController()) {
			coder.add('(');
			coder.add(value);
			coder.add(')');
		} else {
			coder.add("0x");
			coder.add(Integer.toHexString(value));
		}
	}

}
