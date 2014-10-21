/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.compiler.imcode; 

import keso.compiler.*;

public interface IMCopyVisitor {
	public IMNode visit(IMNode nnode, IMNode onode) throws CompileException; 
	public IMSlot adjustSlot(IMSlot foreigenSlot) throws CompileException;
	public IMBasicBlock updateBlock(IMBasicBlock label) throws CompileException; 
}
