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
import keso.classfile.datatypes.BCBasicDatatype;

final public class IMDataFlowAny extends IMDataFlow {

	private IMNode node = null;

	public IMDataFlowAny() {
		super(null);
	}

	public IMDataFlowAny(IMNode node, IMBasicBlock bb) {
		super(node.getOptions());
		this.node = node;
	}

	public IMDataFlow fold(IMDataFlow flow) throws CompileException {
		// einmal Any immer Any.
		return this;
	}

	public boolean isAny() {
		return true;
	}

	public String toReadableString() {
		if (node!=null) {
			return BCBasicDatatype.toString(node.getDatatype());
		}
		//return node.toReadableString();
		return "unknown";
	}
}
