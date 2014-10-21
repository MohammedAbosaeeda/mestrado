/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi.fau.de for more info.

  (c)**/

package keso.compiler.imcode; 

import keso.classfile.*;
import keso.classfile.constantpool.*; 
import keso.classfile.datatypes.*; 

import keso.compiler.*;
import keso.compiler.backend.*;

import keso.util.Debug; 

import java.io.PrintStream;
import java.util.Hashtable;
import java.util.Enumeration;

public class IMBasicBlock extends IMNode {

	private final static boolean verbose_label = true;

	boolean done = false;
	private boolean warn_once = false;
	boolean copied = false;
	private boolean no_follow = false;

	private int dfnum = 0;

	private IMBasicBlockList pred = new IMBasicBlockList();
	private IMBasicBlockList succ = new IMBasicBlockList();

	private IMNode[] enterStack;
	private IMNode[] leaveStack;
	private IMNode   bc_list;

	private IMExceptionHandler exphandler;
	private boolean  subroutine;

	private Hashtable reads=null;
	private Hashtable writes=null;

	protected IMBasicBlock(IMMethod new_method, IMBasicBlock orig) {
		super(orig.src_method,0,-1,orig.src_bcPosition);
		method = new_method;
		no_follow = orig.no_follow;
	}

	public IMBasicBlock(IMMethod method, int bcpos) {
		super(method,0,-1,bcpos);
		subroutine = false;
	}

	public void readSlot(IMSlot var) {
		if (var==null) return;
		if (reads==null) reads=new Hashtable();
		reads.put(var,var);
	}

	public void writeSlot(IMSlot var) {
		if (var==null) return;
		if (writes==null) writes=new Hashtable();
		writes.put(var,var);
	}

	public boolean usedSlot(IMSlot var) {
		if (method.isAnalysed()==false) return true;
		if (reads==null) return false;
		if (reads.get(var)!=null) return true;
		return false;
	}

	public void setDFNum(int dfnum) {
		this.dfnum=dfnum;
	}

	public int getDFNum() {
		return dfnum;
	}

	/**
	 * Insert a list of IMNode objects. The list must start with
	 * a IMListHead object which is skipped.
	 */
	public void insertBCBefor(IMNode pre_list) throws CompileException {
		if (pre_list==null || pre_list.next()==null) return;

		if (bc_list.next()==null) {
			bc_list = pre_list;
			return;
		}

		IMNode node = pre_list;
		while (node.next()!=null) node=node.next();
		node.append(bc_list.next());
		bc_list = pre_list;
	}

	/**
	 * Append a IMNode objects at the end of the basic block.
	 */
	public void appendBC(IMNode node) throws CompileException {
		IMNode prev = bc_list;
		while (prev!=null && prev.next()!=null) prev = prev.next();
		prev = prev.append(node);
	}

	/**
	 * Append a IMNode objects at the end of the basic block.
	 */
	public void appendBeforBranch(IMNode node) throws CompileException {
		IMNode prev = bc_list;
		while (prev!=null && prev.next()!=null && !prev.next().isBranch()) prev = prev.next();
		if (prev!=null) node.append(prev.next());
		prev = prev.append(node);
	}

	/**
	 * Append a IMNode at the top of a basic block.
	 */
	public void placePhi(IMNode node) throws CompileException {
		if (bc_list==null) {
			bc_list = new IMListHead();
		} else {
			node.append(bc_list.next());
		}
		bc_list.append(node);
	}

	public void addBC(IMNode node) throws CompileException {
		if (bc_list==null) bc_list = new IMListHead();
		bc_list.append(node);
	}

	public IMBasicBlockList processBasicBlock(VirtualOperantenStack stack) throws CompileException {
		done = true;

		stack.init(enterStack);

		if (exphandler!=null) stack.push(new IMCaughtException(method, exphandler, bcPosition));

		IMNode new_list = new IMListHead();
		IMNode new_list_prev = null;
		IMNode new_list_end = new_list;

		IMNode prev = bc_list;
		IMListHead extra_ops = new IMListHead();
		while (prev!=null && prev.next() != null) {
			IMNode curr = prev.next();

			if (opts.vvverbose()) opts.vvverbose("# VS "+method.getMethodName()+":"+curr.getBCPosition()+" "+curr.dumpBC());
			IMNode n = curr.processStack(stack, extra_ops);

			new_list_end = new_list_end.append_list(extra_ops);
			extra_ops.clear_link();

			if (n!=null) {
				new_list_prev = new_list_end;
				curr = new_list_end = new_list_end.append_list(n);
			}

			if (curr instanceof IMReturnSubroutine) {
				subroutine = true;
				succ.add(((IMBranch)curr).getUniqTargets());
				curr.clear_link();
				break;
			}

			if (curr.isBranch()) {
				succ.add(((IMBranch)curr).getUniqTargets());
				curr.clear_link();
				if (curr instanceof IMLookupSwitch || curr instanceof IMTableSwitch) {
					no_follow=true;
				}
				opts.vvverbose("# branch (goto, if, switch)");
				break;
			}

			if (curr instanceof IMThrow) {
				curr.clear_link();
				opts.vvverbose("# end of basic block (throw)");
				new_list_prev = new_list_end;
				break;	
			}

			if (curr.isEndOfBasicBlock()) {
				succ.add((IMBasicBlock)next());
				//curr.clear_link();
				opts.vvverbose("# end of basic block ("+curr.dumpBC()+")");
				new_list_end.clear_link();
				new_list_prev = new_list_end;
				break;	
			}

			prev = curr;
		}

		bc_list = new_list;

		if (new_list_prev==null) new_list_prev = new_list_end;
		leaveStack = stack.leave(new_list_prev);
		for (int i=0;i<succ.length();i++) {
			IMBasicBlock s = succ.at(i);
			s.addPred(this);
			if (s.done) continue;
			s.setInitStack(leaveStack);
		}
		
		if (opts.vvverbose()) opts.vvverbose("# END succ "+succ+" leave "+dumpArray(leaveStack));
		return succ;
	}

	public boolean isBasicBlock() { return true; }

	final public IMBasicBlockList getSucc() { return succ; }

	final public IMBasicBlockList getPred() { return pred; }

	final public void addPred(IMBasicBlock pred) { this.pred.add(pred); }

	final public IMBasicBlock removeDeadBasicBlocks(IMBasicBlock pre) throws CompileException {

		if (pre==null) return this;
		if (pred.length()==0) {
			if (succ.length()!=0) 
				opts.warn("call graph mix up!");
			pre.next_node = next_node;

			IMDeadCodeVisitor dv = new IMDeadCodeVisitor(method);
			IMNode prev = bc_list;
			while (prev!=null && prev.next() != null) {
				prev = prev.next();
				prev.visitNodes(dv);
			}

			bc_list = null;
			return pre;
		}

		return this;
	}

	final public void unlinkPred(IMBasicBlock bb) {
		pred.remove(bb);
		// TODO: update Phi-functions, remove vars
		if (pred.length()==0) {
			opts.todo("release basic block!");
			for (int i=0;i<succ.length();i++) unlinkSucc(i);
		}
	}

	final public void unlinkSucc(int nr) {
		IMBasicBlock nb = succ.at(nr);
		nb.unlinkPred(this);
		succ.remove(nb);
	}

	final public void setInitStack(IMNode[] stack) {
		enterStack = stack;
	}

	final public IMNode[] getLeaveStack() {
		return leaveStack;
	}

	public void setExceptionHandler(IMExceptionHandler exphandler) {
		this.exphandler = exphandler;
		if (exphandler.getClassName()!=null) {
			method.requireClass(0, exphandler.getClassName());
		}
	}

	public boolean isExceptionHandler() {
		return exphandler!=null;
	}

	public boolean isSubroutine() {
		return subroutine;
	}

	public boolean isFollowup(IMBasicBlock bb) {
		return (!no_follow && bb==succ.at(0) && next()==bb);
	}

	public boolean empty() {
		return (bc_list==null || bc_list.next()==null); 
	}

	public String toNodeName() {
		return toLabel();
	}

	public String toLabel() { 

		String ret = "unknown";
		if (src_method!=null) ret=src_method.getAlias();

		if (copied) ret="cc"+hashCode()+"_"+ret;

		return ret+"_B"+Integer.toString(src_bcPosition);
	}

	final public String toReadableString() {

		StringBuffer s = new StringBuffer();

		s.append(toLabel());
		s.append(":\n"); 

		IMNode prev = bc_list;
		while (prev!=null && prev.next() != null) {
			IMNode curr = prev.next();
			try { 
				s.append("\t");
				s.append(curr.toReadableString());
				s.append(";\n");
			} catch (NullPointerException ex) {
				s.append("\t<NullPointerException> "+curr.getBCPosition()+": "+curr.dumpBC());
			}
			prev = curr;
		}

		return s.toString();
	}

	public void visitNodes(IMVisitor visitor) throws CompileException {
		if (!done) {
			if (!warn_once) opts.warn(method.lineInfo(getBCPosition())+": basic block not processed yet! - ignore"); 
			warn_once = true;
			return;
		}
		IMNode prev = bc_list;

		while (prev!=null && prev.next() != null) {
			try {
				prev = prev.next();
				prev.visitNodes(visitor);
			} catch (RuntimeException ex) {
				printError(ex, prev);
				throw ex;
			} catch (CompileException ex) {
				printError(ex, prev);
				throw ex;
			}
		}
	}

	public final void removeNode(IMNode remove) throws CompileException {
		IMNode prev = bc_list;
		while (prev!=null && prev.next() != null) {
			if (prev.next() == remove) {
				prev.append(remove.next());
				return;
			}
			prev = prev.next();
		}
	}

	public IMNode copy(IMCopyVisitor visitor) throws CompileException {
		return visitor.updateBlock(this);
	}

	final public IMBasicBlock createStub(IMMethod host_method, IMCopyVisitor visitor) throws CompileException {
		IMBasicBlock nbb = new IMBasicBlock(host_method, this);
		nbb.subroutine = subroutine;
		nbb.no_follow = no_follow;
		return nbb;
	}

	final public IMNode updateStub(IMBasicBlock nbb, IMCopyVisitor visitor) throws CompileException {
		nbb.copyNodeValues(visitor, this);
		nbb.done = done;
		// FIXME: exception handler not supported yet.
		if (exphandler!=null) throw new Error();	
		nbb.exphandler = null;
		nbb.subroutine = subroutine;
		nbb.pred = pred.copy(visitor);
		nbb.succ = succ.copy(visitor);
		nbb.bc_list = copyBCList(visitor);
		if (next_node!=null) {
			nbb.next_node = ((IMBasicBlock)next_node).copy(visitor);
		}
		nbb.no_follow = no_follow;
		return nbb;
	}

	final public IMNode copyBCList(IMCopyVisitor visitor) throws CompileException {
		IMNode prev = bc_list;

		if (bc_list==null) return null;

		if (!(prev instanceof IMListHead)) throw new CompileException("not a list "+bc_list);

		IMNode new_list = new IMListHead();
		IMNode tail=new_list;
		while (prev!=null && prev.next() != null) {
			IMNode curr = prev.next();
			tail = tail.append(curr.copy(visitor));
			prev = curr;
		}

		return new_list;
	}

	/**
	 * insert code into this basic block from the callee.
	 *
	 * prev_pos: ...
	 * current : INVOKE callee <=> body callee
	 * tail	   : ...
	 *       
	 */
	final public IMBasicBlock insertCode(IMNode prev_pos, IMNode call, IMMethod callee, IMNode self, IMNode args[]) throws CompileException {
		IMNode current = prev_pos.next();

		// split basic block at the prev_pos statement
		IMBasicBlock tail = split(prev_pos);
		callee.insertCode(method, current, call, self, args, this, tail);

		return tail;
	}

	final public void link_nofollow(IMBasicBlock next_bb) throws CompileException {
		if (succ.length()>1) throw new CompileException();
		succ = new IMBasicBlockList(next_bb);
		next_bb.pred = new IMBasicBlockList(this);
	}

	/**
	 * link the given basic block (next_bb) at (this) basic block.
	 */
	final public void link(IMBasicBlock next_bb) throws CompileException {
		if (succ.length()>1) throw new CompileException();
		succ = new IMBasicBlockList(next_bb);
		if (next_bb.pred.length()>0) { 
			next_bb.addPred(this);
		} else {
			next_bb.pred = new IMBasicBlockList(this);
		}
		next_node = next_bb;
	}

	final public void updatePred(IMBasicBlock old_bb, IMBasicBlock new_bb) {
		pred.update(old_bb, new_bb);
	}

	final public void updateSucc(IMBasicBlock old_bb, IMBasicBlock new_bb) {
		succ.update(old_bb, new_bb);
	}

	final public IMBasicBlock split(IMNode prev_pos) throws CompileException {

		// FIXME: exception handler not supported yet.
		if (exphandler!=null) throw new Error();	

		IMBasicBlock tail = new IMBasicBlock(method,this);

		tail.copied=true;

		tail.done = done;
		tail.subroutine = subroutine;

		// predictor
		tail.pred = new IMBasicBlockList(this);
	
		// succesor
		for (int i=0;i<succ.length();i++) { succ.at(i).updatePred(this,tail); }
		tail.succ = succ;
		succ = new IMBasicBlockList(tail);
		// update next_node
		tail.next_node = next_node;
		next_node = tail;

		// bc list
		IMNode curr = prev_pos.next();
		prev_pos.clear_link();
		opts.vverbose("split at "+curr.lineInfo()+" "+curr.toReadableString());

		if (curr.next()==null) {
			tail.bc_list = null;
		} else {
			tail.bc_list = new IMListHead();
			tail.bc_list.append(curr.next());
			tail.src_bcPosition = curr.next().getBCPosition();
		}

		tail.no_follow = no_follow;

		return tail;
	}

	/**
	 * melt basic blocks together if we have only one successor
	 * and if we are the only predecessor the successor.
	 */
	final public IMBasicBlock melt() throws CompileException {

		if (succ.length() == 1 && succ.at(0)==next_node) {
			IMBasicBlock nb = succ.at(0);
			if (nb==method.getEndBlock()) return nb;
			if (nb.pred.length()==1 && nb.pred.at(0)==this) {
				succ = nb.succ;
				//opts.verbose("melt "+this+" "+nb+" "+succ.length());
				for (int i=0;i<succ.length();i++) {
					//opts.verbose("update "+succ.at(i)+" "+succ.at(i).pred);
					succ.at(i).updatePred(nb,this);
					//opts.verbose("       "+succ.at(i)+" "+succ.at(i).pred);
				}
				next_node = nb.next_node;

				IMNode node = bc_list;
				while (node !=null && node.next()!=null) {
					if (node.next() instanceof IMGoto) {
						break;
					}
					node=node.next();
				}
				node.append_list(nb.bc_list);
				no_follow = nb.no_follow;

				return this;
			}
		}

		return (IMBasicBlock)next_node;
	}

	final public IMNode constant_folding() throws CompileException {

		if (!done) {
			if (!warn_once) opts.warn(method.lineInfo(getBCPosition())+": basic block not processed yet! - ignore"); 
			warn_once = true;
			return next();
		}

		if (bc_list==null || bc_list.next()==null) return next();

		IMNode prev = bc_list;
		for (IMNode curr=prev.next();curr!=null;curr=curr.next()) {
			IMNode updatenode = curr.constant_folding();
			if (updatenode==null) {
				// remove current node
				prev.append(curr.next());
				curr=prev;
			} else if (updatenode!=curr) {
				// replace current node with a new node or a list 
				// of new nodes.
				prev=prev.append_list(updatenode);
				prev.append(curr.next());
				curr=prev;
			}
			prev = curr;
		}

		return next();
	}

	final public IMNode ssa_optimize() throws CompileException {

		if (!done) {
			if (!warn_once) opts.warn(method.lineInfo(getBCPosition())+": basic block not processed yet! - ignore"); 
			warn_once = true;
			return next();
		}

		if (bc_list==null || bc_list.next()==null) return (IMBasicBlock)next();

		IMNode prev = bc_list;
		for (IMNode curr=prev.next();curr!=null;curr=curr.next()) {
			try {
				IMNode updatenode = curr.ssa_optimize();
				if (updatenode==null) {
					// remove current node
					prev.append(curr.next());
					curr=prev;
				} else if (updatenode!=curr) {
					// replace current node with a new node or a list 
					// of new nodes.
					prev=prev.append_list(updatenode);
					prev.append(curr.next());
					curr=prev;
				}
				prev = curr;
			} catch (RuntimeException ex) {
				printError(ex, curr);
				throw ex;
			} catch (CompileException ex) {
				printError(ex, curr);
				throw ex;
			}
		}

		return next();
	}

	final public IMBasicBlock inlineMethodCalls() throws CompileException {

		IMNode curr=null;
		try {
			if (bc_list==null || bc_list.next()==null) return (IMBasicBlock)next();

			IMNode prev = bc_list;
			for (curr=prev.next();curr!=null;curr=curr.next()) {
				IMBasicBlock tail=curr.inlineMethodCalls(this, prev);
				if (tail!=null) {
					return tail;
				}
				prev = curr;
			}

		} catch (RuntimeException ex) {
			printError(ex, curr);
			throw ex;
		} catch (CompileException ex) {
			printError(ex, curr);
			throw ex;
		}

		return (IMBasicBlock)next();
	}

	final public int costs() throws CompileException {
		int costs = 0;
		IMNode prev = bc_list;

		while (prev!=null && prev.next() != null) {
			prev = prev.next();
			costs += prev.costs();
		}

		return costs;
	}

	final private void emit_source_line(String pre, StringBuffer buffer, int line) throws CompileException {
		if (line<0) return;
		try {
			IMClass clazz = method.getIMClass();
			StringBuffer src = new StringBuffer();
			src.append(clazz.readSourceLine(line));
			int pos=0;
			while ((pos=src.indexOf("*/"))>0) src.setCharAt(pos,'o');
			while ((pos=src.indexOf("/*"))>0) src.setCharAt(pos+1,'o');
			while ((pos=src.indexOf("\t"))>0) src.setCharAt(pos,' ');
			buffer.append(pre);
			buffer.append(" ");
			buffer.append(line);
			buffer.append(": ");
			buffer.append(src);
			buffer.append("\n");
		} catch (Exception ex) {
			opts.vwarn(ex+" "+method.getMethodNameAndType()+" line: "+line);
		}
	}

	final public int emit_source(Coder coder, int pos) throws CompileException {
		IMNode curr=null;
		int curr_line = pos;
		try {
			boolean has_info=false;
			StringBuffer dbg=new StringBuffer();
			dbg.append("\n/*==============================================================/\n");
			IMNode prev = bc_list;
			while (prev!=null && prev.next() != null) {
				curr = prev.next();

				int line = method.getLineNumber(curr.getBCPosition());
				if (line>curr_line) {
					if (curr_line!=-1) {
						for (int i=3;i>0;i--) 
							if (line>(curr_line+i)) emit_source_line(":",dbg, line-i);
					}
					emit_source_line("|",dbg, line);
					curr_line=line;
					has_info=true;
				}
				prev = curr;
			}
			dbg.append("/==============================================================*/");
			if (has_info) coder.addln(dbg);
		} catch (RuntimeException ex) {
			printError(ex, curr);
			throw ex;
		} catch (CompileException ex) {
			printError(ex, curr);
			throw ex;
		}

		return curr_line;
	}

	final public int translate(Coder coder, int curr_line) throws CompileException {

		if (!done) {
			if (!warn_once) opts.warn(method.lineInfo(getBCPosition())+": basic block not processed yet! - ignore"); 
			warn_once = true;
			return curr_line;
		}

		if (pred.length()>0 || isExceptionHandler()) {
			if (pred.length()==1 && pred.at(0).isFollowup(this)) {
				if (opts.hasDbgSymbols()) {
					coder.add("\n/* ");
					coder.add(toLabel());
					coder.add(": ");
					if (verbose_label) {
						coder.add(" Pred: ");
						coder.add(pred);
						coder.add(" No: ");
						coder.add(dfnum);
						coder.add((done?" done":" NOT done"));
					}
					coder.addln(" */");
				}
			} else {
				coder.add("\n");
				coder.add(toLabel());
				if (verbose_label) {
					coder.add(": /* Pred: ");
					coder.add(pred);
					coder.add(" No: ");
					coder.add(dfnum);
					coder.add((done?" done":" NOT done"));
					coder.addln(" */");
				} else {
					coder.addln(":");
				}
			}
		} else {
			coder.addln();
		}

		if (opts.hasOption("bb_glb_label")) {
			coder.add("__asm__(\".global ");
			coder.add(toLabel());
			coder.addln("\");");
			coder.add("__asm__(\"");
			coder.add(toLabel());
			coder.addln(":\");");
		}

		if (opts.hasOption("dbg_src")) curr_line=emit_source(coder,curr_line); 

		if (bc_list==null || bc_list.next()==null) return curr_line;

		coder.setCurrentBasicBlock(this);

		opts.vvverbose("# BB "+toReadableString());

		IMNode curr=null;
		try {
			IMNode prev = bc_list;
			while (prev!=null && prev.next() != null) {
				curr = prev.next();

				if (opts.hasOption("dbg_line")) {
					int line = method.getLineNumber(curr.getBCPosition());
					if (line>=0 && line!=curr_line) {
						IMClass clazz = method.getIMClass();
						coder.add("#line ");
						coder.add(line);
						coder.add(" \"src/");
						coder.add(clazz.getSourceFile());
						coder.addln("\"");
						curr_line=line;
					}
				}
				if (false && opts.hasVerbose()) {
					if (!(curr instanceof IMGoto))
						coder.add_befor("/* "+curr.toReadableString()+" */");
				}
				curr.translate(coder);
				coder.addln(";");

				prev = curr;
			}
		} catch (RuntimeException ex) {
			printError(ex, curr);
			throw ex;
		} catch (CompileException ex) {
			printError(ex, curr);
			throw ex;
		}

		return curr_line;
	}

	final private void printError(Throwable ex, IMNode curr) {
		System.err.println("========================================================");
		System.err.println(ex+" in: ");
		System.err.print(method.getClassName());
		System.err.print(".java  ");
		System.err.print(method.getMethodNameAndType());
		if (curr!=null) {
			int line = method.getLineNumber(curr.getBCPosition());
			if (line>=0) System.err.println(" (line: "+line+")");
			else System.err.println(" (bc: "+curr.getBCPosition()+")");
		}
		System.err.print(method.getAlias());
		System.err.println(".c");
		System.err.println("========================================================");
	}

	final public void dumpBC(PrintStream out) {
		out.print(toLabel());
		out.print(": ");
		out.println(done);
		IMNode prev = bc_list;
		while (prev!=null && prev.next() != null) {
			IMNode curr = prev.next();
			out.println("\t"+curr.getBCPosition()+":\t"+curr.dumpBC());
			prev = curr;
		}
	}

	final public void writeCFGEdges(PrintStream out) {
		for (int i=0; i<succ.length();i++) {
			IMBasicBlock nb = succ.at(i);
			out.print("\t");
			out.print(toNodeName());
			out.print("->");
			out.print(nb.toNodeName());
			out.print(" [label=\" ");
			out.print(i);
			out.print("\" style=bold]");
			out.println(";");
		}
		if (opts.hasOption("cfg_pred")) {
			for (int i=0; i<pred.length();i++) {
				IMBasicBlock nb = pred.at(i);
				out.print("\t");
				out.print(toNodeName());
				out.print("->");
				out.print(nb.toNodeName());
				out.print(" [label=\" ");
				out.print(i);
				out.print("\" style=dotted]");
				out.println(";");
			}
		}
		if (opts.hasOption("cfg_order")) {
			IMBasicBlock nb = (IMBasicBlock)next();
			if (nb!=null) {
				out.print("\t");
				out.print(toNodeName());
				out.print("->");
				out.print(nb.toNodeName());
				out.print(" [style=dashed]");
				out.println(";");
			}
		}
		out.println();
	}

	final public void writeCFGBoxes(PrintStream out) throws CompileException {
		out.print("\t");
		out.print(toNodeName());

		if (opts.hasOption("cfg_small")) {
			out.print(" [label=\"");
			out.print(toLabel());
			out.print("\" fontsize=8 ");
			if (!copied) out.print(" style=rounded ");
			if (pred.length()==0 || succ.length()==0) out.print("shape=diamond"); 
			out.println("]");
		} else if (opts.hasOption("cfg_var")) {
			out.print(" [label=\" No.");
			out.print(dfnum);
			out.print("\\l ");

			int[] df = method.getDomTree().getDominaceFrontierIDs(this);
			if (df!=null) {
				out.print(" DF:");
				for (int i=0;i<df.length;i++) {
					out.print(" ");
					out.print(df[i]);
				}
				out.print("\\l ");
			}

			if (reads!=null) {
				Enumeration venum = reads.elements();
				out.print(" r: ");
				boolean sep = false;
				while (venum.hasMoreElements()) {
					IMSlot slot = (IMSlot)venum.nextElement();
					if (sep) out.print(",");
					out.print(slot);
					sep=true;
				}
				out.print("\\l");
			}
			if (writes!=null) {
				Enumeration venum = writes.elements();
				out.print(" w: ");
				boolean sep = false;
				while (venum.hasMoreElements()) {
					IMSlot slot = (IMSlot)venum.nextElement();
					if (sep) out.print(",");
					out.print(slot);
					sep=true;
				}
				out.print("\\l");
			}

			out.print("\" fontsize=8 ");
			if (!copied) out.print("style=rounded ");
			if (pred.length()==0 || succ.length()==0) out.print("shape=diamond"); 
			out.println("]");
		} else {
			writePseudoCode(out);
		}
	}

	final public void writeDomEdges(PrintStream out, IMBasicBlock dom) {
		if (dom==null) return;
		out.print("\t");
		out.print(dom.toLabel());
		out.print("->");
		out.print(toLabel());
		out.println(";");
	}

	final public void writeDomBoxes(IMMethod cmethod, PrintStream out) throws CompileException {
		out.print("\t");
		out.print(toLabel());

		//writePseudoCode(out);
		out.print(" [label=\"");
		out.print(toLabel());
		out.print("\\l ");
		out.print(dfnum);
		if (method!=cmethod) {
			opts.critical("basic block mix up: "+toReadableString()+"\n"+method.getAlias()+"!="+cmethod.getAlias());
		}
		int[] df = method.getDomTree().getDominaceFrontierIDs(this);
		if (df!=null) {
			out.print(" DF:");
			for (int i=0;i<df.length;i++) {
				out.print(" ");
				out.print(df[i]);
			}
		}
		out.println("\" fontsize=8]");
	}

	final public String toString() {
		return toLabel();
	}

	final private void writePseudoCode(PrintStream out) {
		boolean str=false;
		out.print(" [label=\" No.");
		out.print(dfnum);
		out.print("\\l\\N:\\l ");

	
		if (!opts.hasOption("cfg_no_code")) {	
			IMNode prev = bc_list;
			while (prev!=null && prev.next() != null) {
				IMNode curr = prev.next();
				StringBuffer s = new StringBuffer(curr.toReadableString());
				for (int i=0;i<s.length();i++) {
					switch (s.charAt(i)) {
						case '"':
							s.setCharAt(i,'\'');
							str=(str?false:true);
							break;
						case '\t':
							s.setCharAt(i,' ');
							s.insert(i,"    ");
							break;
						case '\n':
							s.setCharAt(i,'.');
							break;
					}
				}
				out.print(s.toString());
				out.print("\\l ");
				prev = curr;
			}
		}

		out.print("\" fontsize=8 ");
		if (!copied) {
			out.print("style=rounded ");
			if (method!=src_method) out.print(" color=red ");
		} else {
			if (method==src_method) out.print(" color=red ");
		}
		if (pred.length()==0 || succ.length()==0) out.print("shape=diamond"); 
		out.println("]");
	}

	private String dumpArray(IMNode[] arr) {
		if (arr==null) return "null";

		if (arr.length<1) return " - ";

		StringBuffer ret = new StringBuffer((arr[0]==null?" --- ":arr[0].toReadableString()));
		for (int i=1;i<arr.length;i++) { ret.append(','); ret.append((arr[i]==null?" --- ":arr[i].toReadableString())); }

		return ret.toString();
	}
}
