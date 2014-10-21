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

final public class IMCallGraphVisitor extends IMBasicVisitor {

	private BuilderOptions opts;

	public IMCallGraphVisitor(BuilderOptions opts) {
		this.opts = opts;
	}

	public void visit(IMNode node) throws CompileException {
		try {
			node.analyseCall();
		} catch (StackOverflowError ex) {
			ex.printStackTrace();
		}
	}
}
