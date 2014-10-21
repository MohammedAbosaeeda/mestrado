/**(c)

  Copyright (C) 2005-2007 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.compiler.imcode; 

import keso.compiler.CompileException;

final public class IMDataFlowConstObj extends IMDataFlow {

	private IMConstant alias;

	public IMDataFlowConstObj(IMConstant alias) {
		super(alias.getOptions());
		this.alias = alias;
	}

	public IMDataFlow fold(IMDataFlow flow) throws CompileException {
		if (flow.isValid()) return new IMDataFlowValidObj(opts);
		return flow;
	}

	public boolean isValid() {
		return true;
	}

	public boolean isConstant() {
		return true; 
	}

	public String toReadableString() {
		return alias.toReadableString();
	}

	public IMConstant getConstValue(IMMethod method, int bcpos) throws CompileException {
		if (alias instanceof IMNullConstant) {
			return method.createIMNullConstant(bcpos);
		}
		return method.createIMAConstant(alias.getCPEntry(), bcpos);
	}
}
