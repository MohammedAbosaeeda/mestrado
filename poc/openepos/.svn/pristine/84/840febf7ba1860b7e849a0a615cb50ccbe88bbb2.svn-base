/**(c)

  Copyright (C) 2005-2006 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.compiler.imcode; 

import keso.compiler.CompileException;
import keso.compiler.BuilderOptions;
import keso.classfile.datatypes.BCBasicDatatype;

final public class IMDataFlowConstValue extends IMDataFlow {

	private IMConstant node;
	private String value_str;

	public IMDataFlowConstValue(IMConstant node) {
		super(node.getOptions());
		this.node = node;
	}

	public IMDataFlow fold(IMDataFlow flow) throws CompileException {
		IMDataFlow dfset;
		if (flow instanceof IMDataFlowConstValue) {
			if (node.equalValue(flow.getValue())) return this;
		}
		if ((dfset=flow.addConstValue(this))!=null) return dfset;
		return IMDataFlow.ANY;
	}

	/**
	 * create a new set of constant values.
	 */
	public IMDataFlow addConstValue(IMDataFlow df) {
		return new IMDataFlowSet(this, df);
	}

	public boolean isConstant() {
		return true;
	}

	public IMConstant getValue() throws CompileException {
		return node;
	}

	public IMConstant getConstValue(IMMethod method, int bcpos) throws CompileException {
		switch (node.getDatatype()) {
			case BCBasicDatatype.BOOLEAN:
			case BCBasicDatatype.BYTE:
			case BCBasicDatatype.CHAR:
			case BCBasicDatatype.SHORT:
			case BCBasicDatatype.INT:
				return method.createIMIConstant(node.getIntValue(),bcpos);
			case BCBasicDatatype.LONG:
				return method.createIMLConstant(node.getLongValue(),bcpos);
			case BCBasicDatatype.FLOAT:
				return method.createIMFConstant(node.getFloatValue(),bcpos);
			case BCBasicDatatype.DOUBLE:
				return method.createIMDConstant(node.getDoubleValue(),bcpos);
			default:
				throw new CompileException("unkown datatype constant: "+BCBasicDatatype.toString(node.getDatatype()));
		}
	}

	public boolean isValid() {
		return true;
	}

	public int hashCode() {
		if (value_str==null) value_str = node.toReadableString(); 
		return value_str.hashCode();
	}

	public boolean equals(Object obj) {
		if (value_str==null) value_str = node.toReadableString(); 
		return value_str.equals(obj);
	}

	public String toReadableString() {
		if (value_str==null) value_str = node.toReadableString();
		return node.toReadableString();
	}
}
