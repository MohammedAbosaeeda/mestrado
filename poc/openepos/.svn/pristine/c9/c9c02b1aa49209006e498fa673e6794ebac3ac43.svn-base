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

public class IMLConstant extends IMConstant {


	/* copy constuctor */
	protected IMLConstant() { }

	protected long value;

	public IMLConstant(IMMethod method, int bc, int bcpos, long value) {
		super(method,bc,BCBasicDatatype.LONG,bcpos);
		this.value=value;
	}

	public long getValue() {
		return value;
	}

	public long getLongValue() {
		return value;
	}

	public void setLongValue(long value) {
		this.value=value;
	}

	public boolean equalValue(IMConstant node) {
		return this.value==node.getLongValue();
	}

	public boolean equalValue(long value) {
		return this.value==value;
	}


	final public IMNode copy(IMCopyVisitor visitor) throws CompileException {

		IMLConstant nnode = new IMLConstant();
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
		return Long.toString(value); 
	}

	public String dumpBC() {return "LConstant";}

	public int costs() throws CompileException {
		return 5;
	}

	public void translate(Coder coder) throws CompileException {
		coder.add('(');
    		coder.add(Long.toString(value));
		coder.add("LL");
		coder.add(')');
	}

}
