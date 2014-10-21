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

final public class IMDataFlowValidObj extends IMDataFlow {

	public IMDataFlowValidObj(BuilderOptions opts) {
		super(opts);
	}

	public IMDataFlow fold(IMDataFlow flow) throws CompileException {
		if (flow.isValid()) return this;
		return flow;
	}

	public boolean isValid() {
		return true;
	}

	public String toReadableString() {
		return "valid";
	}
}
