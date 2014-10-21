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

public class IMNullConstant extends IMConstant {


	/* copy constuctor */
	protected IMNullConstant() { }

	public IMNullConstant(IMMethod method, int bc, int bcpos) {
		super(method,bc,BCBasicDatatype.REFERENCE,bcpos);
	}


	final public IMNode copy(IMCopyVisitor visitor) throws CompileException {
		IMNullConstant nnode = new IMNullConstant();
		nnode.copyNodeValues(visitor, this);

		if (visitor==null) return nnode;
		return visitor.visit(nnode,this);
	}

	final public void visitNodes(IMVisitor visitor) throws CompileException {
		if (visitor.skipNode(this)) return;
		visitor.visit(this);

	}


	public int getIntValue() { return 0; }

	public String toReadableString() { return "NULL"; 	}

	public String dumpBC() {return "NullConstant";}

	public int costs() throws CompileException {
		return 3;
	}

	public void translate(Coder coder) throws CompileException {
		coder.add("((object_t*)0)");
	}

}
