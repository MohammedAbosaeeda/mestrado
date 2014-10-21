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

public class IMFConstant extends IMConstant {


	/* copy constuctor */
	protected IMFConstant() { }

	protected float value;

	public IMFConstant(IMMethod method, int bc, int bcpos, float value) {
		super(method,bc,BCBasicDatatype.FLOAT,bcpos);
		this.value=value;
	}

	public float getValue() {
		return value;
	}

	public float getFloatValue() {
		return value;
	}

	public void setFloatValue(float value) {
		this.value=value;
	}

	public boolean equalValue(IMConstant node) {
		return this.value==node.getFloatValue();
	}

	public boolean equalValue(float value) {
		return this.value==value;
	}


	final public IMNode copy(IMCopyVisitor visitor) throws CompileException {

		IMFConstant nnode = new IMFConstant();
		nnode.copyNodeValues(visitor, this);

		nnode.value=value;

		if (visitor==null) return nnode;
		return visitor.visit(nnode,this);
	}

	final public void visitNodes(IMVisitor visitor) throws CompileException {
		if (visitor.skipNode(this)) return;

		visitor.visit(this);


	}

	public String toReadableString() {
		return Float.toString(value);
	}

	public String dumpBC() {return "FConstant";}

	public int costs() throws CompileException {
		return 3;
	}

	public void translate(Coder coder) throws CompileException {
    		// toString() adds ".0" correctly
    		// NaN and Infinity should be NAN and INFINITY for C => toUpperCase()
		coder.add('(');
    		coder.add(Float.toString(value).toUpperCase());
		coder.add(')');
	}

}
