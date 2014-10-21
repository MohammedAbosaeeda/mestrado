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

public class IMArrayLength extends IMNode {

	protected IMNode array;

	/* copy constuctor */
	protected IMArrayLength() { }

	public IMArrayLength(IMMethod method, int bc, int bcpos) {
		super(method,bc,BCBasicDatatype.INT,bcpos);
	}


	final public IMNode copy(IMCopyVisitor visitor) throws CompileException {

		IMArrayLength nnode = new IMArrayLength();
		nnode.copyNodeValues(visitor, this);

		nnode.array = array.copy(visitor);

		if (visitor==null) return nnode;
		return visitor.visit(nnode,this);
	}

	final public void visitNodes(IMVisitor visitor) throws CompileException {
		if (visitor.skipNode(this)) return;

		visitor.visit(this);

		array.visitNodes(visitor);

	}

	final public IMNode constant_folding() throws CompileException {
		array = array.constant_folding();


		return this;
	}

	final public IMNode ssa_optimize() throws CompileException {
		array = array.ssa_optimize();
		if (!opts.hasOption("ssa_no_array_len")) {
			int alength = array.getArrayLength(); 
			if (alength!=-1) {
				opts.verbose("++ propagate constant array length "+array+"["+alength+"].length()");
				return method.createIMIConstant(alength, getBCPosition());
			}
		}


		return this;
	}


	/**
	 * Nodes who only read but not change the global state
	 * are pure.
	 */
	public boolean isPureNode() { return true; }


	public void escapeAnalyses(int mode) throws CompileException {
		if (mode!=IMEscapeVisitor.PREPARE) return;
		array.setEscapePath(IMNode.NONE);
	}
	public IMNode processStack(VirtualOperantenStack stack, IMNode extra_ops) throws CompileException {
		stack.store(bcPosition, extra_ops);

		array = stack.pop();
		assertIsReference(array);
		stack.push(this);
		return null;
	}

	public String toReadableString() {
		return array.toReadableString()+".length";
	}

	public String dumpBC() {return "ArrayLength";}

	public int costs() throws CompileException {
		return 5;
	}

	public void translate(Coder coder) throws CompileException {
		coder.chk_ref(array,method,bcPosition);
		coder.add("((array_t*)");
		array.translate(coder);
		coder.add(")->size");
	}

}
