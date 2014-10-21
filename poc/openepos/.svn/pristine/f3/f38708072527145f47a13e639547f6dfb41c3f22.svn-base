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

public class IMThrow extends IMNode {

	protected IMNode exception;

	/* copy constuctor */
	protected IMThrow() { }

	public IMThrow(IMMethod method, int bc, int bcpos) {
		super(method,bc,BCBasicDatatype.UNKNOWN_TYPE,bcpos);
	}


	final public IMNode copy(IMCopyVisitor visitor) throws CompileException {

		IMThrow nnode = new IMThrow();
		nnode.copyNodeValues(visitor, this);

		if (exception!=null) nnode.exception = exception.copy(visitor);

		if (visitor==null) return nnode;
		return visitor.visit(nnode,this);
	}

	final public void visitNodes(IMVisitor visitor) throws CompileException {
		if (visitor.skipNode(this)) return;

		visitor.visit(this);

		if (exception!=null) exception.visitNodes(visitor);

	}

	final public IMNode constant_folding() throws CompileException {
		if (exception!=null) exception = exception.constant_folding();


		return this;
	}

	final public IMNode ssa_optimize() throws CompileException {
		if (exception!=null) exception = exception.ssa_optimize();


		return this;
	}


	final public void escapeAnalyses(int mode) throws CompileException {
		if (mode!=IMEscapeVisitor.PREPARE) return;
		exception.setEscapePath(this);
	}

	final public boolean doEscape(IMNode node, boolean isArgument) throws CompileException {
		return true;
	}

	final public IMNode whyEscape(IMNode node, boolean isArgument) throws CompileException {
		return this;
	}
	public IMNode processStack(VirtualOperantenStack stack, IMNode extra_ops) throws CompileException {
		method.isConst=false;
		method.isPure=false;
		exception = stack.pop();	
		stack.clear();
		return this;
	}

	public String toReadableString() {
		if (exception==null) return "throw <error null>";
		return "throw "+exception.toReadableString();
	}

	public String dumpBC() {return "Throw";}

	public int costs() throws CompileException {
		return exception.costs() + 50;
	}

	public void translate(Coder coder) throws CompileException {
		if (opts.hasOption("exceptions")) {
			String label;
			coder.add("KESO_PENDING_EXCEPTION = ");
			exception.translate(coder);
			coder.add("; ");
			if ((label=method.lookupExceptionHandler(null, bcPosition))!=null) {
				coder.add("goto ");
				coder.add(label);
			} else {
				switch (method.getBasicReturnType()) {
					case BCBasicDatatype.VOID:
						coder.add("return");
						break;
					default:
						coder.add("return 0");
				}
			}
		} else {
			coder.add("while (1) { __asm__ __volatile__ (\"nop\"); }");
		}
	}

}
