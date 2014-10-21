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

public class IMReadLocalVariable extends IMNode {


	/* copy constuctor */
	protected IMReadLocalVariable() { }

	protected IMSlot var;

	public IMReadLocalVariable(IMMethod method, int bc, int type, int bcpos, IMSlot var) {
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


	public boolean hasSideEffect() { return false; }


	/*
	public boolean translate_MT_Ref(Coder coder) throws CompileException {
		if (datatype!=BCBasicDatatype.REFERENCE) return false;
		IMNode alias = var.getAlias();
		if (alias!=null && alias.translate_MT_Ref(coder)) {
			opts.warn("mt forwarded by alias");
			return true;
		}
		return false;
	}
	*/

	public void analyseCall() throws CompileException {
		IMBasicBlock basicBlock = method.getCurrentBasicBlock();
 		basicBlock.readSlot(var);
	}

	public boolean isStackVariable(int stack_pos) {
		return var.isStackVariable(stack_pos);
	}

	public boolean isChecked(IMBasicBlock bb) throws CompileException {
		if (var==null) return false;
		return var.isChecked(bb);
	}

	public void setChecked(IMBasicBlock bb) throws CompileException {
		var.setChecked(bb);
	}

	public boolean isConstant() {
		if (var==null) return false;
		IMBasicBlock basicBlock = method.getCurrentBasicBlock();
		return var.varToConstant(basicBlock)!=null;
	}

	public IMConstant nodeToConstant() throws CompileException {
		IMBasicBlock basicBlock = method.getCurrentBasicBlock();
		IMConstant const_node = var.varToConstant(basicBlock);
		if (const_node==null) throw new CompileException("not constant!");
		return const_node;
	}
	public IMNode processStack(VirtualOperantenStack stack, IMNode extra_ops) throws CompileException {
		stack.push(this);
		return null;
	}

	public String toReadableString() {
		if (var==null) return "undef";
		return var.toString(); 
	}

	public String dumpBC() {return "load "+var;}

	public void translate(Coder coder) throws CompileException {
		if (var==null) {
			opts.warn("local variable is null in "+method.getAlias());
			coder.add(0);
		} else {
			coder.add(var.toString());
		}
	}

}
