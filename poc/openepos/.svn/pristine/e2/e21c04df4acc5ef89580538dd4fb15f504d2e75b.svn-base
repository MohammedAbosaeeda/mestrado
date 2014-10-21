/**(c)

  Copyright (C) 2005-2006 Christian Wawersich 

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
import keso.compiler.backend.*;
import keso.compiler.kni.*;

import keso.util.Debug; 
import keso.util.DecoratedNames; 
import keso.util.IntegerHashtable; 

import java.util.Vector;
import java.util.Enumeration;
import java.util.Hashtable;


/* AUTO GENERATED FILE DON'T EDIT */

public class IMStackOperationPOP2 extends IMNode {


	/* copy constuctor */
	protected IMStackOperationPOP2() { }

	public IMStackOperationPOP2(IMMethod method, int bc, int bcpos) {
		super(method,bc,BCBasicDatatype.UNKNOWN_TYPE,bcpos);
	}


	public IMNode processStack(VirtualOperantenStack stack, IMNode extra_ops) throws CompileException {
		IMNode opr,opr2;

		opr=stack.pop();
		if (isCategory2(opr)) return null;

		opr2=stack.pop();
		if (isCategory2(opr2)) throw new CompileException();

		IMNode ret=null;

		if (opr instanceof IMInvoke) ret=opr;
		if (opr2 instanceof IMInvoke) {
			 if (ret!=null) { ret.append(opr2); } else { ret=opr2; }
		}
		
		return ret;
	}

}
