/**(c)

  Copyright (C) 2005-2007 Christian Wawersich 

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

import keso.util.*; 
import java.util.Hashtable;
import java.util.Enumeration;

public class IMSlot {

	private int stack_alias=-1;
	private int type;
	private int src_alias;
	private IMBasicBlock checked;
	private IMBasicBlock const_bb;
	private IMConstant const_node;
	private String alias;
	//private boolean llrefs=false;

	private IMNode alias_expr = null;
	private IMDataFlow dataflowinfo = null;

	private IMNode defined = null;
	private IMBasicBlock defined_bb = null;
	private Hashtable uses = null;
	private int ssa_count=0;

	BuilderOptions opts;
	IMMethodFrame frame;

	public IMSlot(IMMethodFrame frame, String alias) {
		this.frame = frame;
		this.opts = frame.getOptions();
		this.src_alias = -1;
		this.type=BCBasicDatatype.VOID;
		this.alias=alias;
		//this.llrefs = opts.hasLinkedListOfLocalReferences();
	}

	public IMSlot(IMMethodFrame frame, int type, int alias) {
		this.frame = frame;
		this.opts = frame.getOptions();
		this.src_alias = alias;
		this.type=type;
		//this.llrefs = opts.hasLinkedListOfLocalReferences();

		switch (type) {
			case BCBasicDatatype.INT:
				this.alias = "i";
				break;
			case BCBasicDatatype.LONG: 
				this.alias = "l";
				break;
			case BCBasicDatatype.FLOAT: 
				this.alias = "f";
				break;
			case BCBasicDatatype.DOUBLE:
				this.alias = "d";
				break;
			case BCBasicDatatype.REFERENCE: 
				this.alias = "obj";
				break;
			case BCBasicDatatype.BYTE: 
				this.alias = "b";
				break;
			case BCBasicDatatype.CHAR: 
				this.alias = "c";
				break;
			case BCBasicDatatype.SHORT: 
				this.alias = "s";
				break;
			case BCBasicDatatype.BOOLEAN:
				this.alias = "flag";
				break;
			case BCBasicDatatype.RETURN_ADDRESS: 
				this.alias = "addr";
				break;
			default:
				this.alias = "unknown";
		}
		this.alias += alias;
	}

	public void register_ref() {
		//if (!llrefs) return;
		if (!opts.hasLinkedListOfLocalReferences()) return;
		if (type!=BCBasicDatatype.REFERENCE) return;
		if (src_alias>=0 && src_alias<frame.argRange()) return; 
		this.alias = "obj["+frame.registerSlot(src_alias)+"]";
	}

	public String incName() {
		int pos = alias.indexOf("[");

		StringBuffer nname = new StringBuffer();
		if (pos>0) {
			nname.append(alias.substring(0,pos));
			nname.append("_");
			nname.append(ssa_count);
			nname.append(alias.substring(pos,alias.length()));
		} else {
			nname.append(alias);
			nname.append("_");
			nname.append(ssa_count);
		}

		ssa_count++;

		return nname.toString();
	}

	public void setName(String nalias) {
		alias=nalias;
	}

	public void setDataFlowInfo(IMDataFlow df) {
		dataflowinfo = df;
	}

	public IMDataFlow getDataFlowInfo() {
		return dataflowinfo;
	}

	public void setAlias(IMNode node) {
		alias_expr = node;
	}

	public IMNode getAlias() {
		return alias_expr;
	}

	public int getType() {
		return type;
	}

	// define-use-chain ============================================================
	
	public void clear_def_use_chain() {
		defined=null;
		defined_bb=null;
		uses=null;
	}

	public boolean define(IMBasicBlock bb, IMNode statement) throws CompileException {
		if (defined!=null) {
			opts.warn("statement "+statement.toReadableString()+" in "+bb.toString());
			opts.warn("allready defined "+defined.toReadableString()+" in "+defined_bb.toString());
			//throw new CompileException("multi defined statement! ");
			return false;
		}
		defined=statement;
		defined_bb=bb;

		return true;
	}

	public IMNode getDefinedStatement() {
		return defined;
	}

	public IMBasicBlock getDefinedIn() {
		return defined_bb;
	}

	public Enumeration getUses() {
		if (uses==null) return null;
		return uses.keys();
	}

	/**
	 * Die lokale Variable verlässt den Stack, wenn eine Verwendung
	 * zum Verlassen führt.
	 */
	public boolean doEscape(boolean isArgument) throws CompileException {
		Enumeration eu = getUses();
		if (eu==null) {
			opts.vwarn(frame.getMethod().getAlias()+": doEscape slot "+alias+" with out uses.");
			return false;
		}
		while (eu.hasMoreElements()) {
			IMNode nu = (IMNode)eu.nextElement();
			if (nu.doEscape(nu, isArgument)) return true;
		}
		return false;
	}

	public IMNode whyEscape(boolean isArgument) throws CompileException {
		Enumeration eu = getUses();
		if (eu==null) return null; 
		while (eu.hasMoreElements()) {
			IMNode nu = (IMNode)eu.nextElement();
			IMNode roe = nu.whyEscape(nu, isArgument);
			if (roe!=null) return roe;
		}
		opts.warn(frame.getMethod().getAlias()+": slot "+alias+" without escape hint.");
		return null;
	}

	public int numberOfUses() {
		if (uses==null) return 0;
		return uses.size();
	}

	public IMBasicBlock getUseBB(IMNode statement) {
		if (uses==null) return null;
		return (IMBasicBlock)uses.get(statement);
	}

	public void use(IMBasicBlock bb, IMNode statement) {
		if (uses==null) uses = new Hashtable();
		uses.put(statement, bb);
	}

	public void delete_use(IMNode statement) {
		if (uses==null) opts.warn("var has no uses! "+statement.toReadableString()
				+" in "+frame.getMethod().getAlias());
		uses.remove(statement);
		if (uses.isEmpty()) {
			frame.getMethod().doAnotherSSARun();
			uses=null;
		}
	}

	public void reset_uses() {
		uses=null;
	}

	public boolean hasNoUses() {
		return uses==null;
	}

	// define-use-chain ============================================================
	
	public boolean sameFrame(IMMethodFrame frame) {
		return this.frame==frame;
	}

	public int getVarNumber() {
		return src_alias;
	}	

	public void setChecked(IMBasicBlock checked) throws CompileException {
		this.checked = checked;
	}

	public boolean isChecked(IMBasicBlock current)  {
		// uses is not null if ssa is true.
		if (uses!=null) {
			try {
				if (opts.hasOption("ssa_buggy_safechk")) {
					// FIXME: Bug: not all uses are checks!
					DominatorTree domtree = frame.getMethod().getDomTree();
					Enumeration bbe = uses.elements();
					while (bbe.hasMoreElements()) {
						IMBasicBlock ub = (IMBasicBlock)bbe.nextElement();
						if (domtree.dom(ub,current)) return true;
					}
				}
				if (defined!=null && defined instanceof IMAStoreLocalVariable) {
					IMAStoreLocalVariable store = (IMAStoreLocalVariable)defined;
					if (store.getOperant().isChecked(defined_bb)) return true;
				}
				if (dataflowinfo!=null && dataflowinfo.isValid()) {
					if (checked==null) checked = current;
					return true;
				} 
				if (checked==null) return false;
				if (checked==current) return true;
				DominatorTree domtree = frame.getMethod().getDomTree();
				if (domtree.dom(checked, current)) return true;
				return false;
			} catch (CompileException ex) {
				throw new RuntimeException();
			}
		}
		if (checked==null) return false;
		return (checked==current);
	}

	/*
	 * simple constant propagation.
	 */
	public void propagateConstant(IMBasicBlock current, IMConstant const_node) {
		this.const_node = const_node;
		this.const_bb = current;
	}

	public IMConstant varToConstant(IMBasicBlock current) {
		if (current==const_bb) return const_node;
		return null;
	}

	public void setIsStackVar(int alias) { stack_alias = alias; }

	public boolean isStackVariable(int stack_pos) { return (stack_alias == stack_pos); }

	public String definition() {
		return BuilderOptions.typeToString(type)+" "+this.alias;
	}

	public String cType() {
		return BuilderOptions.typeToString(type);
	}

	public String toString() { return alias; }
}
