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

public class IMNewMultiArray extends IMNode {

	protected IMNode[] oprs; 

	/* copy constuctor */
	protected IMNewMultiArray() { }

	protected ClassCPEntry cpEntry;
	protected IMArrayClass aclass;
	protected int dim;

	public IMNewMultiArray(IMMethod method, int bc, int bcpos, ClassCPEntry clazz, int dim) {
		super(method,bc,BCBasicDatatype.REFERENCE,bcpos);
		int domainID=0;
		this.cpEntry=clazz;
		this.dim=dim;
		method.requireClass(domainID, cpEntry.getClassName());
		method.requireClass(domainID, "[Ljava/lang/Object;");
		aclass=(IMArrayClass)method.getIMClass("[Ljava/lang/Object;");
		//method.requireClass(domainID, aclass.getClassName());
		aclass.requireMultiAlloc();
	}


	final public IMNode copy(IMCopyVisitor visitor) throws CompileException {

		IMNewMultiArray nnode = new IMNewMultiArray();
		nnode.copyNodeValues(visitor, this);

		nnode.cpEntry=cpEntry;
		nnode.dim=dim;
		nnode.aclass=aclass;
		nnode.oprs = new IMNode[oprs.length];
		for (int i=0;i<oprs.length;i++) nnode.oprs[i] = oprs[i].copy(visitor);

		if (visitor==null) return nnode;
		return visitor.visit(nnode,this);
	}

	final public void visitNodes(IMVisitor visitor) throws CompileException {
		if (visitor.skipNode(this)) return;

		visitor.visit(this);

		for (int i=0;i<oprs.length;i++) oprs[i].visitNodes(visitor);

	}

	final public IMNode constant_folding() throws CompileException {
		for (int i=0;i<oprs.length;i++) {
		    if (oprs[i]!=null) oprs[i]=oprs[i].constant_folding();
		}


		return this;
	}

	final public IMNode ssa_optimize() throws CompileException {
		for (int i=0;i<oprs.length;i++) {
		    if (oprs[i]!=null) oprs[i]=oprs[i].ssa_optimize();
		}


		return this;
	}


	public boolean isChecked(IMBasicBlock bb) throws CompileException { return true; }

	public void analyseCall() throws CompileException {
		method.isPure = false;
		method.isConst = false;
		if (!method.getMethodName().equals("<clinit>") && 
				opts.getGlobalHeap().allocationCanBlock()) method.blockInNew();
	}

	private boolean does_not_escape = false;
	private IMNode escape_path = null;
	private IMNode why_escape = null;
	public void escapeAnalyses(int mode) throws CompileException {
		if (mode!=IMEscapeVisitor.PREPARE) {
			if (doEscape(this, false)) {
				why_escape=whyEscape(this, false);
				if (why_escape instanceof IMEpilog && !doEscape(this, true)) {
					opts.verbose(this.lineInfo()+" escaping object thru "+why_escape.toReadableString()+" only!");
				}
				does_not_escape = false;
			} else {
				does_not_escape = true;
			}
		}
	}

	public boolean doEscape(IMNode node, boolean isArgument) throws CompileException {
		if (escape_path==null) {
			opts.warn(this.lineInfo()+": new "+toReadableString()+" with unknown escape path!");
			return false;
		}
		return escape_path.doEscape(node, isArgument);
	}

	final public IMNode whyEscape(IMNode node, boolean isArgument) throws CompileException {
		if (escape_path==null) {
			return null;
		}
		return escape_path.whyEscape(this, isArgument);
	}

	public void setEscapePath(IMNode node) throws CompileException {
		escape_path = node;
	}

	public IMNode getEscapePath() throws CompileException {
		return escape_path;
	}
	public IMNode processStack(VirtualOperantenStack stack, IMNode extra_ops) throws CompileException {
		method.isConst=false;
		method.isPure=false;
		oprs = new IMNode[dim];
		for (int i=0;i<dim;i++) {
			oprs[i]=stack.pop();
			assertIsIntValue(oprs[i]);
		}
		stack.store(bcPosition,extra_ops);
		stack.push(this);
		return null;
	}

	public String toReadableString() {
		String retValue =  "keso_allocMultiArray("+cpEntry.getClassName();
		for (int i=0;i<oprs.length;i++) {
			retValue += "," + oprs[i].toReadableString();
		}
		return retValue+")";
	}

	public String dumpBC() {return "NewMultiArray";}

	public int costs() throws CompileException {
		int costs = 50;
		for (int i=0;i<oprs.length;i++) costs+=oprs[i].costs();
		return costs;

	}

	public void translate(Coder coder) throws CompileException {
		IMClass clazz = method.getIMClass(cpEntry.getClassName());
		coder.add_class(clazz.getAlias());
		coder.add_class(aclass.getAlias());

		if (!method.getMethodName().equals("<clinit>") &&
			 opts.getGlobalHeap().allocationCanBlock()) 
				method.getMethodFrame().codeEOLL(coder);

		if (why_escape!=null) { 
			coder.add_befor("\t/* escaping: ");
			coder.add_befor(why_escape.toReadableString());
			coder.add_befor(" (array) */\n");
		} 
		if (does_not_escape) {
			coder.add_befor("\t/* no escaping: ");
			coder.add_befor(toReadableString());
			coder.add_befor(" (array) */\n");
		}

		coder.add("keso_allocMultiArray(");
		coder.add(clazz.getClassID());
		coder.add(',');
		int len = oprs.length;
		coder.add(len);
		for (int i=1;i<=len;i++) {
			coder.add(',');
			oprs[len-i].translate(coder);
		}

		coder.add(')');
	}

}
