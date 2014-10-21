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

public class IMConstant extends IMNode {


	/* copy constuctor */
	protected IMConstant() { }

	public IMConstant(IMMethod method, int bc, int type, int bcpos) {
		super(method,bc,type,bcpos);
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


	public ConstantPoolEntry getCPEntry() { throw new Error(); }
	public boolean hasSideEffect() { return false; }

	public IMNode processStack(VirtualOperantenStack stack, IMNode extra_ops) throws CompileException {
		stack.push(this);
		return null;
	}

}
