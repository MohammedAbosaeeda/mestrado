/**(c)

  Copyright (C) 2005-2007 Christian Wawersich 

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

import keso.util.*; 

final public class IMSlotPref extends IMSlot {

	public IMSlotPref(IMMethodFrame frame) {
		super(frame,"pref");
	}

	public void setAlias(IMNode node) {
		throw new RuntimeException("pref can't have alias");
	}

	public void setChecked(IMBasicBlock checked) throws CompileException {
		throw new CompileException("pref can't mark as checked");
	}

	public boolean isChecked(IMBasicBlock current) {
		return false;
	}

	/*
	 * simple constant propagation.
	 */
	public void propagateConstant(IMBasicBlock current, IMConstant const_node) {
		throw new RuntimeException("pref");
	}

	public IMConstant varToConstant(IMBasicBlock current) {
		throw new RuntimeException("pref");
	}

	public void setIsStackVar(int alias) {
		throw new RuntimeException("pref");
	}

	public boolean isStackVariable(int stack_pos) {
		throw new RuntimeException("pref");
	}

	public String definition() {
		return "object_t** pref";
	}

	public String toString() { return "pref"; }
}
