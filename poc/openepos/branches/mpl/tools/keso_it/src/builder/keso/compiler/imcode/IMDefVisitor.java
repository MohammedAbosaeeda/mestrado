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
import keso.util.IntegerHashtable;

final public class IMDefVisitor extends IMBasicVisitor {

	private IMMethod method;
	private Hashtable defsites;
	private IntegerHashtable bb_vars;

	public IMDefVisitor(IMMethod method, Hashtable defsites, IntegerHashtable bb_vars) throws CompileException {
		this.method = method;
		this.defsites = defsites;
		this.bb_vars = bb_vars;
	}

	public void visit(IMNode node) throws CompileException {
		if (node instanceof IMStoreLocalVariable) {
			IMBasicBlock bb = method.getCurrentBasicBlock();
			IMSlot cvar = node.getIMSlot();
			int n = bb.getDFNum();

			IntegerHashtable def_var;
			if ((def_var=(IntegerHashtable)defsites.get(cvar))==null) {
				def_var = new IntegerHashtable();
				defsites.put(cvar, def_var);
			}
			def_var.put(n,bb);

			Hashtable vars;
			if ((vars=(Hashtable)bb_vars.get(n))==null) {
				vars = new Hashtable();
				bb_vars.put(n, vars);
			}
			vars.put(cvar,cvar);
		}
	}
}
