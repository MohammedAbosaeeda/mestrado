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

public class IMStoreLocalVariable extends IMNode {

	protected IMNode operant;

	/* copy constuctor */
	protected IMStoreLocalVariable() { }

	protected IMSlot var;

	public IMStoreLocalVariable(IMMethod method, int bc, int type, int bcpos, IMSlot var) {
		super(method,bc,type,bcpos);
		if (var==null) throw new NullPointerException();
		this.var=var;
	}

	public IMSlot getIMSlot() {
		return var;
	}

	public void setIMSlot(IMSlot slot) {
		//if (slot==null) throw new NullPointerException();
		var = slot;	
	}



	public void analyseCall() throws CompileException {
		IMBasicBlock basicBlock = method.getCurrentBasicBlock();
 		basicBlock.writeSlot(var);
	}

	public void setOperant(IMNode opr) {
		this.operant = opr;
	}

	public IMNode getOperant() {
		return operant;
	}

	public IMReadLocalVariable getReadOperation() throws CompileException {
		throw new CompileException();
	} 

	public void setChecked(IMBasicBlock bb) throws CompileException {
		var.setChecked(bb);
	}

	public IMBasicBlock inlineMethodCalls(IMBasicBlock current, IMNode prev) throws CompileException {
		if (!opts.doInlineRValues()) return null;
		return operant.inlineMethodCalls(current, prev);
	}
	public IMNode processStack(VirtualOperantenStack stack, IMNode extra_ops) throws CompileException {
		operant = stack.pop();
		stack.store2(bcPosition, extra_ops, var);
		return this;
	}

	public String toReadableString() {
		if (operant==null) return var.toString()+" = ??? ";
		return var.toString()+" = "+operant.toReadableString();
	}

	public String dumpBC() {return "store "+var;}

	public void translate(Coder coder) throws CompileException {
		if (operant instanceof IMPhi) coder.add("/* ");
		coder.add(var.toString());
		coder.add(" = ");
		operant.translate(coder);
		if (operant instanceof IMPhi) coder.add(" */");
	}

}
