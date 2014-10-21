/**(c)

  Copyright (C) 2005 Christian Wawersich 

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

import keso.util.Debug; 

public class IMListHead extends IMNode {

	public IMListHead() { super(); }

	public IMNode copy(IMBasicBlock currentBasicBlock,IMCopyVisitor visitor) throws CompileException {
		IMListHead nnode = new IMListHead();
		nnode.copyNodeValues(visitor, this);
		return nnode;
	}

	public boolean emptyList() { return next()==null; }

	public String toReadableString() {
		String s = "";

		IMNode prev = this;
		while (prev!=null && prev.next() != null) {
			IMNode curr = prev.next();
			s = s + "\t" + curr.toReadableString() + ";\n";
			prev = curr;
		}

		return s;
	}
}
