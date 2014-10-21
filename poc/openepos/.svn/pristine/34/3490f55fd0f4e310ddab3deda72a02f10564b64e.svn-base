/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi.fau.de for more info.

  (c)**/

package keso.compiler.imcode; 

import keso.classfile.*;
import keso.classfile.constantpool.*; 
import keso.classfile.datatypes.*; 

import keso.compiler.*;

import keso.util.Debug; 

public class IMBranch extends IMNode {

	protected IMBasicBlock targets[];

	protected IMBranch() {
	}

	public IMBranch(IMMethod method, int bc, int bcpos) {
		super(method,bc,-1,bcpos);
	}

	public IMBranch(IMMethod method, int bc, int type, int bcpos) {
		super(method,bc,type,bcpos);
	}

	final public boolean isBranch() { return true; }

	final public IMBasicBlock[] getTargets() { return targets; }

	public IMBasicBlock[] getUniqTargets() {
		return targets;
	}
}
