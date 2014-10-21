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

public class IMInvoke extends IMNode {

	protected IMNode[] args;
	protected IMNode obj;

	/* copy constuctor */
	protected IMInvoke() { }

	protected MethodTypeDescriptor	typeDesc;
	protected JoinPointChecker 	joinPoints;

	public IMInvoke(IMMethod method, int bc, int bcpos, ClassMemberCPEntry cpEntry) {
		super(method,bc,-1,bcpos);
		this.joinPoints= method.getJoinPoints();
		this.typeDesc  = new MethodTypeDescriptor(cpEntry.getMemberTypeDesc());
		this.datatype  = typeDesc.getBasicReturnType();
	}



	public void analyseCall() throws CompileException {
		opts.warn(this+" has no anaylse call method!");
	}

	public IMNode processStack(VirtualOperantenStack stack, IMNode extra_ops) throws CompileException {
		int[] argTypes = typeDesc.getBasicArgumentTypes();

		stack.store(bcPosition, extra_ops);

		args = new IMNode[argTypes.length];
		for (int i=(argTypes.length-1);i>=0;i--) {
			args[i] = stack.pop();
		}

		obj = stack.pop();

		if (datatype==BCBasicDatatype.VOID) return this;
	    	stack.push(this);

	    	return null;
	}

	public String toReadableString() {
		String retString = "(";
		int i=0;
		while (i<args.length)  {
			if (args[i]!=null) retString += args[i].toReadableString();
			else retString += "null";
			i++;
			if (i<args.length) retString+=",";
		}
		return retString+")";
	}

}
