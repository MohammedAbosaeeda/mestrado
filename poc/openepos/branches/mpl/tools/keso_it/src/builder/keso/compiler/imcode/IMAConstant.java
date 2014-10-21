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

public class IMAConstant extends IMConstant {


	/* copy constuctor */
	protected IMAConstant() { }

	protected ConstantPoolEntry value;

	public IMAConstant(IMMethod method, int bc, int bcpos, ConstantPoolEntry value) {
		super(method,bc,BCBasicDatatype.REFERENCE,bcpos);
		int domainID=0;
		this.value=value;
		if (value instanceof StringCPEntry) {
			method.requireClass(domainID, "java/lang/String");
			method.requireClass(domainID, "[C");
		}
	}

	public ConstantPoolEntry getValue() {
		return value;
	}


	final public IMNode copy(IMCopyVisitor visitor) throws CompileException {

		IMAConstant nnode = new IMAConstant();
		nnode.copyNodeValues(visitor, this);

		nnode.value=value;

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

	public String getString() { return ((StringCPEntry)value).getDescription(); }


	public ConstantPoolEntry getCPEntry() { return value; }

 	public boolean hasSideEffect() { return false; }

 	public boolean isConstObject() throws CompileException { return true; }

	public boolean isChecked(IMBasicBlock bb) throws CompileException { return true; }


	public void analyseCall() throws CompileException {
		if (value instanceof StringCPEntry) {
			ClassStore repository = method.getClassStore();
			repository.registerIMPutField("java/lang/String","value");
		}
	}
	public String toReadableString() {
		if (value instanceof StringCPEntry) {
			return "\""+value.getDescription()+"\"";
		} else {
			return value.getDescription();
		}	
	}

	public String dumpBC() {return "AConstant";}

	public int costs() throws CompileException {
		return 3;
	}

	public void translate(Coder coder) throws CompileException {
		if (value instanceof StringCPEntry) {
			ClassStore repository = method.getClassStore();
			coder.add(repository.allocString(coder, this, value.getDescription()));
		} else {
			coder.add(value.getDescription());
		}	
	}

}
