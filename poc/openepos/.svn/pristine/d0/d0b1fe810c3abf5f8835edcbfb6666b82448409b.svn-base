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

public class IMStartBlock extends IMBasicBlock {

	public IMStartBlock(IMMethod method, int bcpos) {
		super(method,bcpos);
	}

	public String toLabel() { return src_method.getAlias()+"_S"+Integer.toString(src_bcPosition); }
}
