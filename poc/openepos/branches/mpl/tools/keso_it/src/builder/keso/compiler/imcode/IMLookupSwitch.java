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

public class IMLookupSwitch extends IMBranch {

	protected IMNode operant;

	/* copy constuctor */
	protected IMLookupSwitch() { }

	protected int[] keys;

	public IMLookupSwitch(IMMethod method, int bc, int bcpos, int[] keys, IMBasicBlock[] table) {
		super(method,bc,BCBasicDatatype.UNKNOWN_TYPE,bcpos);
		this.keys=keys;
		this.targets=table;
	}


	final public IMNode copy(IMCopyVisitor visitor) throws CompileException {

		IMLookupSwitch nnode = new IMLookupSwitch();
		nnode.copyNodeValues(visitor, this);


		nnode.operant = operant.copy(visitor);


		nnode.targets = new IMBasicBlock[targets.length];
		for (int i=0;i<targets.length;i++) nnode.targets[i] = visitor.updateBlock(targets[i]);

		nnode.keys = new int[keys.length];
		System.arraycopy(nnode.keys,0,keys,0,keys.length);

		if (visitor==null) return nnode;
		return visitor.visit(nnode,this);
	}

	final public void visitNodes(IMVisitor visitor) throws CompileException {
		if (visitor.skipNode(this)) return;

		visitor.visit(this);


		//for (int i=0;i<targets.length;i++) targets[i].visitNodes(visitor);


		operant.visitNodes(visitor);


	}

	final public IMNode constant_folding() throws CompileException {
		operant = operant.constant_folding();
		if (operant.isConstant()) {
			opts.verbose("++ todo fold lookup switch");
		}


		return this;
	}

	final public IMNode ssa_optimize() throws CompileException {
		operant = operant.ssa_optimize();
		if (operant.isConstant()) {
			opts.verbose("++ todo fold lookup switch");
		}


		return this;
	}


	private IMBasicBlock[] uniqTargets=null;
	public IMBasicBlock[] getUniqTargets() {
		if (uniqTargets==null) {
			//Hashtable hash = new Hashtable();
			IntegerHashtable hash = new IntegerHashtable();
			for (int i=0;i<targets.length;i++) {
				//hash.put(targets[i].toLabel(),targets[i]);
				hash.put(targets[i].getBCPosition(),targets[i]);
			}
			Enumeration all = hash.elements();
			uniqTargets=new IMBasicBlock[hash.size()];
			for (int i=0;all.hasMoreElements();i++) {
				uniqTargets[i]=(IMBasicBlock)all.nextElement();
			}
		}
		return uniqTargets;
	}

	final public void escapeAnalyses(int mode) throws CompileException {
		if (mode!=IMEscapeVisitor.PREPARE) return;
		operant.setEscapePath(IMNode.NONE);
	}
	public IMNode processStack(VirtualOperantenStack stack, IMNode extra_ops) throws CompileException {
		operant = stack.pop();
		return this;
    	}

	public String toReadableString() {
		String output = "lswitch ("+operant.toReadableString()+") {\n";
		for (int i=1;i<targets.length;i++) {
			output +=  "\t"+Integer.toString(keys[i-1])+":"+
				targets[i].toLabel() + "\n";
		}       
		output += "\t\t::"+targets[0].toLabel() + "\n";
		return output+"\t}";
	}

	public String dumpBC() {return "LookupSwitch";}

	public int costs() throws CompileException {
		return (20 * targets.length);

	}

	public void translate(Coder coder) throws CompileException {
		coder.add("switch (");
   		operant.translate(coder);
		coder.addln(") {");

		for (int i=1;i<targets.length;i++) {
			coder.add("\tcase ");
			coder.add(keys[i-1]);
			coder.add(": goto ");
			coder.add(targets[i].toLabel());
			coder.addln(';');
		}
		coder.add("\tdefault: goto ");
		coder.add(targets[0].toLabel());
		coder.addln(';');

		coder.add('}');
	}

}
