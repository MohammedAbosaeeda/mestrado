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

import keso.util.Stack;
import java.util.Hashtable;
import java.util.Enumeration;

final public class IMRemoveSSAVisitor extends IMBasicVisitor {

	private IMMethod method;
	private BuilderOptions opts;
	private IMBasicBlock bb;

	private boolean rm_phi = false;

	public IMRemoveSSAVisitor(IMMethod method, IMBasicBlock bb) throws CompileException {
		this.method = method;
		this.opts = method.getOptions();
		this.bb = bb;
		this.rm_phi = !opts.hasOption("ssa_no_rm_phi");
	}

	public void visit(IMNode node) throws CompileException {
		if (node instanceof IMStoreLocalVariable) {
			IMStoreLocalVariable store = (IMStoreLocalVariable)node;
			IMNode operant = store.getOperant();
			if (operant instanceof IMPhi) {
				IMPhi phi = (IMPhi)operant;
				removePhi(store, phi);
			}
		}
	}

	private void removePhi(IMStoreLocalVariable store, IMPhi phi) throws CompileException {
		opts.vverbose("remove "+phi.toReadableString());
		IMBasicBlockList list=bb.getPred();
		IMSlot dst_slot = store.getIMSlot();

		for (int i=0;i<list.length();i++) {
			IMBasicBlock pb = list.at(i);
			IMNode src = phi.getSource(i);
			IMSlot src_slot = src.getIMSlot();
			
			if (src_slot==dst_slot) {
				opts.warn("ssa: ignore "+dst_slot.toString()+"="+src.toReadableString()+" ("+method.getAlias()+")");
				continue;
			}

			if (src_slot==null) {
				IMNode nstore = method.createStoreVariable(dst_slot, src, phi.getBCPosition());
				pb.appendBeforBranch(nstore);
				continue;
			}

			IMStoreLocalVariable def = (IMStoreLocalVariable)src_slot.getDefinedStatement();
			IMBasicBlock defIn = src_slot.getDefinedIn();

			boolean phi_def = false;
			if (def !=null && def.getOperant() instanceof IMPhi) phi_def = true;
			boolean rename_vars = rm_phi && !phi_def;

			if (def==null) {
				/* this may happen with arguments */
				rename_vars=false;
			}

			// Rename only if variable is defined in the prev. basic block
			// and if all uses of var. in the prev. or in the current basic block.
			if (rename_vars && defIn==pb) {
				Enumeration uses = src_slot.getUses();
				while (uses.hasMoreElements()) {
					IMNode use = (IMNode)uses.nextElement();
					IMBasicBlock ub = src_slot.getUseBB(use);
					if (ub==bb) continue;
					if (ub!=pb) {
						opts.vverbose("no rename "+src.toReadableString()+" -> "+dst_slot);
						rename_vars = false;
						break;
					}

				}
			} else {
				rename_vars = false;
			}

			if (rename_vars) {
				//
				// l1:                                  l1:
				//    o1 = 1;                              o2 = 1;
				//    ...                                  ...
				//
				// l2:                      =====>      l2:
				//    o2 = phi(o1, ... );                  o2=phi(o2, ... );
				//

				opts.vverbose("rename "+src.toReadableString()+" -> "+dst_slot);
				def.setIMSlot(dst_slot);
				dst_slot.use(defIn, def);

				Enumeration uses = src_slot.getUses();
				while (uses.hasMoreElements()) {
					IMNode use = (IMNode)uses.nextElement();
					use.setIMSlot(dst_slot);
					dst_slot.use(src_slot.getUseBB(use), use);
					//src_slot.delete_use(use);
				}

				//src_slot.delete_use(src);
				src_slot.clear_def_use_chain();
			} else { 
				//
				// l1:                                  l1:
				//    ...                                  ...
				//                                         o2 = o1;
				// l2:                      =====>      l2:
				//    o2 = phi(o1, ... );                  o2=phi(o2, ... );
				//
				
				IMNode nstore = method.createStoreVariable(dst_slot, src, phi.getBCPosition());
				pb.appendBeforBranch(nstore);
				src_slot.use(pb, nstore);
			}
		}
	}
}
