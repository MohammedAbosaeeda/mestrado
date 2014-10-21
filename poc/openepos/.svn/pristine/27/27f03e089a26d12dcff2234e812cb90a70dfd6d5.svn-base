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

public class IMPhi extends IMNode {


	/* copy constuctor */
	protected IMPhi() { }
 
   	protected IMNode slots[];
	protected IMSlot orig_slot;
	protected IMBasicBlock bb;

	public IMPhi(IMMethod method, IMBasicBlock bb, IMSlot orig_slot, int type) throws CompileException {
		super(method, -1, type, -1);
		this.orig_slot = orig_slot;
		this.bb = bb;

		IMBasicBlockList bl = bb.getPred();

		slots = new IMNode[bl.length()];
		for (int i=0;i<slots.length;i++) slots[i]=method.readLocal(-1,orig_slot);
	}

	public void setSource(int pos, IMNode slot) throws CompileException {
		slots[pos]=slot;
	}

	public IMNode getSource(int pos) throws CompileException {
		return slots[pos];
	}

	public void setSourceVar(int pos, IMSlot slot) throws CompileException {
		slots[pos].setIMSlot(slot);
	}

	public IMSlot getSourceVar(int pos) throws CompileException {
		return slots[pos].getIMSlot();
	}


	final public void visitNodes(IMVisitor visitor) throws CompileException {
		if (visitor.skipNode(this)) return;
		visitor.visit(this);
		for (int i=0;i<slots.length;i++) slots[i].visitNodes(visitor);
	}

	final public IMNode ssa_optimize() throws CompileException {
    		IMConstant all_const=null;
		boolean all_const_b = true;
		for (int i=0;i<slots.length;i++) {
			IMNode node = slots[i];
			if (node==null) {
				opts.warn("undefined slot "+i+" "+toReadableString());
				all_const_b = false;
				break;
			}

			if (node instanceof IMReadLocalVariable && node.getIMSlot()==null) {
				if (!opts.hasOption("ssa_no_omit_undef")) {
					opts.vverbose("%% omit "+toReadableString()+"(slot "+i+" undefined!)");
					for (int j=0;j<slots.length;j++) {
						IMSlot var = slots[j].getIMSlot();
						if (var!=null) var.delete_use(slots[j]);
					}
					switch (getDatatype()) {
						case BCBasicDatatype.BOOLEAN:
						case BCBasicDatatype.BYTE:
						case BCBasicDatatype.CHAR:
						case BCBasicDatatype.SHORT:
						case BCBasicDatatype.INT:
							return method.createIMIConstant(0,getBCPosition());
						case BCBasicDatatype.LONG:
							return method.createIMLConstant(0L,getBCPosition());
						case BCBasicDatatype.FLOAT:
							return method.createIMFConstant(0.0f,getBCPosition());
						case BCBasicDatatype.DOUBLE:
							return method.createIMDConstant(0.0,getBCPosition());
						case BCBasicDatatype.REFERENCE:
							return method.createIMNullConstant(getBCPosition());
						default:
							opts.warn("Unknown datatype for constant value. "+getDatatype());
							opts.warn(toReadableString());
							throw new CompileException("Unknown datatype for constant value.");
					}

				} 
				all_const_b = false;
				break;
			}

			slots[i] = slots[i].ssa_optimize();
			if (slots[i].isConstant()) {
				IMConstant cnode = slots[i].nodeToConstant();
				if (all_const==null) {
					all_const = cnode;
				} else if (!cnode.equals(all_const)) {
					all_const_b = false;
				}
			} else {
				all_const_b = false;
			}
		}

		if (all_const_b) {
			opts.vverbose("++ phi is constant "+toReadableString()+" "+all_const.toReadableString());
			return all_const;
		}


		return this;
	}

	public boolean hasSideEffect() { return false; }

 
	/**
	 * Nodes who only read but not change the global state
	 * are pure.
	 */
	public boolean isPureNode() { return true; }



	public void escapeAnalyses(int mode) throws CompileException {
		if (mode!=IMEscapeVisitor.PREPARE) return;
		for (int i=0;i<slots.length;i++) slots[i].setEscapePath(this);
	}

	private IMNode escape_path = null;
	private int escape_state = 0;
	public boolean doEscape(IMNode node, boolean isArgument) throws CompileException {

		/* cycle */
		if (escape_state==1) return true;
		/* dominate */
		if (escape_state==2) return true;
		/* do escape */
		if (escape_state==3) return true;
		/* no escape */
		if (escape_state==4) return false;

		IMBasicBlock phi_bb = escape_path.getIMSlot().getDefinedIn();
		IMSlot var_in = node.getIMSlot();
		if (var_in!=null) {
			DominatorTree domtree = method.getDomTree();
			IMBasicBlock in_bb = var_in.getDefinedIn();
			if (domtree.dom(phi_bb,in_bb)) {
				opts.warn(escape_path.toReadableString()+" dominate the definition of "+node.toReadableString());
				escape_state=2;
				return true;
			}
		} else {
			opts.warn(method.getAlias()+": phi-fkt no slot "+node.toReadableString());
		}

		if (escape_path==null) {
			opts.warn(this.lineInfo()+": phi-fkt "+toReadableString()+" with unknown escape path!");
			return false;
		}

		escape_state = 1;
		//boolean esc =  escape_path.doEscape(this, isArgument);
		boolean esc =  escape_path.doEscape(node, isArgument);
		escape_state = ( esc ? 3 : 4 );
		return esc;
	}

	final public IMNode whyEscape(IMNode node, boolean isArgument) throws CompileException {

		if (doEscape(node, isArgument) && escape_state<4) {
			return escape_path;
		}

		return escape_path.whyEscape(this, isArgument);
	}

	public void setEscapePath(IMNode node) throws CompileException {
		escape_path = node;
	}

	public IMNode getEscapePath() throws CompileException {
		return escape_path;
	}

	public String toReadableString() {
		IMBasicBlockList pred = bb.getPred();
		StringBuffer buf = new StringBuffer("Phi(");
		if (slots!=null) {
			for (int i=0;i<slots.length;i++) {
				if (i>0) buf.append(",");
				if (slots[i]!=null) {
					buf.append(slots[i].toReadableString());
				}
			}
		}
		buf.append(") ");
		return buf.toString();
	}

	public int costs() throws CompileException {
		return 1;
	}

	public void translate(Coder coder) throws CompileException {
		coder.add(toReadableString());
	}

}
