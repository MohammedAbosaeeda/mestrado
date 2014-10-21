/**(c)

  Copyright (C) 2006 Christian Wawersich 

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
import keso.compiler.backend.Coder;

import keso.util.Debug; 

import java.util.Hashtable;

final public class IMEscapeVisitor extends IMBasicVisitor {

	public static final int PREPARE = 1;
	public static final int ANALYSE = 2;

	private BuilderOptions opts;
	private int mode;

	public IMEscapeVisitor(BuilderOptions opts, int mode) {
		this.opts = opts;
		this.mode = mode;
	}

	public void printStack() {
		try {
			throw new CompileException();
		} catch (CompileException ex) {
			ex.printStackTrace();
		}
	}

	public void visit(IMNode node) throws CompileException {
		try {
			node.escapeAnalyses(mode);
		} catch (Error ex) {
			printStack();
			throw ex;
		}
	}
}
