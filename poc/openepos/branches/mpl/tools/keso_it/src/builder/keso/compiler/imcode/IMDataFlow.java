/**(c)

  Copyright (C) 2005-2006 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.compiler.imcode; 

import keso.classfile.datatypes.BCBasicDatatype;
import keso.compiler.CompileException;
import keso.compiler.BuilderOptions;

public class IMDataFlow {

	public final static IMDataFlowAny ANY = new IMDataFlowAny();
	public final BuilderOptions opts;

	public IMDataFlow(BuilderOptions opts) {
		this.opts = opts;
	}

	public boolean isValid() {
		return false;
	}

	public boolean isAny() {
		return false;
	}

	public boolean isSet() {
		return false;
	}

	public boolean isConstant() {
		return false;
	}

	public IMDataFlow addConstValue(IMDataFlow df) {
		return null;
	}

	public String toReadableString() {
		return "???";
	}

	public IMDataFlow copy() throws CompileException {
		// TODO: copy?
		return this;
	}

	public IMDataFlow fold(IMDataFlow flow) throws CompileException {
		throw new CompileException ("not implemented!");
	}

	public IMConstant getValue() throws CompileException {
		throw new CompileException ("no constant value "+this);
	}

	public IMConstant getConstValue(IMMethod method, int bcpos) throws CompileException {
		throw new CompileException ("no constant value "+this);
	}

	public static IMDataFlow createValidDataFlowObj(IMNode node, IMBasicBlock currentBB) throws CompileException {
		if (node.getDatatype()!=BCBasicDatatype.REFERENCE) throw new CompileException();
		if (node.isConstant()) 
			return new IMDataFlowConstObj(node.nodeToConstant());
		if (node.isChecked(currentBB)) 
			return new IMDataFlowValidObj(node.getOptions());
		if (!node.getOptions().hasOption("no_valid_this_ptr"))
			return new IMDataFlowValidObj(node.getOptions());
		return new IMDataFlowInvalidObj(node.getOptions());
	}

	public static IMDataFlow createDataFlowObj(IMNode node, IMBasicBlock currentBB) throws CompileException {

		switch (node.getDatatype()) {
			case BCBasicDatatype.REFERENCE:
				if (node.isConstant()) 
					return new IMDataFlowConstObj(node.nodeToConstant());
				if (node.isChecked(currentBB)) 
					return new IMDataFlowValidObj(node.getOptions());
				return new IMDataFlowInvalidObj(node.getOptions());
			case BCBasicDatatype.BOOLEAN:
			case BCBasicDatatype.INT:
			case BCBasicDatatype.SHORT:
			case BCBasicDatatype.BYTE:
			case BCBasicDatatype.CHAR:
				if (node.isConstant()) 
					return new IMDataFlowConstValue(node.nodeToConstant());
				break;
		}

		// return ANY;
		return new IMDataFlowAny(node,currentBB);
	}
}
