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
import keso.compiler.kni.*;
import keso.compiler.backend.Coder;
import keso.compiler.config.*;

import keso.util.*; 

import java.util.Enumeration;

import java.io.IOException;
import java.io.PrintStream;
import java.io.FileOutputStream;

import java.util.Vector;

public abstract class IMSystemMethod extends IMMethod {

	public IMSystemMethod(IMClass clazz) throws CompileException {
		super(clazz);
	}

	public void analyseCallGraph(IMCallGraphVisitor visitor) throws CompileException {
		opts.warn("blind analyseCallGraph() method in "+this.getClass().getName());
	}

	public boolean isPure() throws CompileException {
		return false;
	}

	public boolean isConstant() throws CompileException {
		return false;
	}

	final public boolean isInlineCandidate() throws CompileException {
		return false;
	}

	final public int getNumberOfBasicBlocks() {
		return 0;
	}

	final public void writeDomTree() throws IOException, CompileException {
		return;
	}

	final public boolean insertCode(IMMethod host_method,
			IMNode current, IMNode call, IMNode self, IMNode[] args,
			IMBasicBlock prev_bb, IMBasicBlock next_bb) throws CompileException {
		throw new CompileException("try to inline system method!");
	}

	public boolean constant_folding() throws CompileException {
		return false;
	}

	public void translate(Coder coder) throws CompileException {
		opts.warn("blind translate() method in "+this.getClass().getName());
	}
}
