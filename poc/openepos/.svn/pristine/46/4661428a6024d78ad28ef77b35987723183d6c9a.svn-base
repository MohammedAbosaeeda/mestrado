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

public class IMInvokeSpecial extends IMInvoke {


	/* copy constuctor */
	protected IMInvokeSpecial() { }

	protected MethodRefCPEntry cpEntry;

	public IMInvokeSpecial(IMMethod method, int bc, int bcpos, MethodRefCPEntry methodRef) {
		super(method,bc,bcpos,methodRef);
		int domainID=0;
		this.cpEntry=methodRef;
		method.requireMethod(domainID, cpEntry);
	}


	final public IMNode copy(IMCopyVisitor visitor) throws CompileException {


		IMInvokeSpecial nnode = new IMInvokeSpecial();
		nnode.copyNodeValues(visitor, this);

		nnode.typeDesc = typeDesc;
		nnode.joinPoints = joinPoints;
		nnode.cpEntry=cpEntry;
		nnode.args = new IMNode[args.length];
		for (int i=0;i<args.length;i++) nnode.args[i] = args[i].copy(visitor);

		nnode.obj = obj.copy(visitor);

		if (visitor==null) return nnode;
		return visitor.visit(nnode,this);
	}

	final public void visitNodes(IMVisitor visitor) throws CompileException {
		if (visitor.skipNode(this)) return;


		visitor.visit(this);

		for (int i=0;i<args.length;i++) args[i].visitNodes(visitor);

		obj.visitNodes(visitor);

	}

	final public IMNode constant_folding() throws CompileException {
		if (obj!=null) obj = obj.constant_folding();

		for (int i=0;i<args.length;i++) {
		    if (args[i]!=null) args[i] = args[i].constant_folding();
		}

		IMNode replace = method.getJoinPoints().affectIMInvoke(this, method, method.findMethod(cpEntry), obj, args); 
		if (replace!=null && replace!=this) return replace;


		return this;
	}

	final public IMNode ssa_optimize() throws CompileException {
		if (obj!=null) obj = obj.ssa_optimize();

		for (int i=0;i<args.length;i++) {
		    if (args[i]!=null) args[i] = args[i].ssa_optimize();
		}

		IMNode replace = method.getJoinPoints().affectIMInvoke(this, method, method.findMethod(cpEntry), obj, args); 
		if (replace!=null && replace!=this) return replace;


		return this;
	}



	public boolean isChecked(IMBasicBlock bb) throws CompileException {
		IMMethod callee = null;
		try { callee = method.findMethod(cpEntry); } catch (Exception ex) { return false; }
		return joinPoints.checkAttribut(callee,Weavelet.ATTR_NOTNULL)==Weavelet.TRUE;
	}


	public void analyseCall() throws CompileException {
		IMMethod callee = method.findMethod(cpEntry);	
		MethodTable table = callee.getMethodTable();
		IMDataFlow argsInfo[] = new IMDataFlow[args.length+1];

		argsInfo[0] = IMDataFlow.createValidDataFlowObj(obj, method.getCurrentBasicBlock());
		for (int i=0;i<args.length;i++) {
			if (args[i]==null) continue;
			argsInfo[i+1] = IMDataFlow.createDataFlowObj(args[i], method.getCurrentBasicBlock());
		}

		if (table!=null) {
			Enumeration candidates = table.getCandidates();
			while (candidates.hasMoreElements()) {
				IMMethod m = (IMMethod)candidates.nextElement();
				opts.vverbose("++ "+m.getAlias()+" called from "+method.getAlias());
				method.call(m.getClassName(), m.getMethodNameAndType());
				m.calledFrom(method, argsInfo);
			}
		} else {
			opts.vverbose("++ "+callee.getAlias()+" called from "+method.getAlias());
			method.call(callee.getClassName(), callee.getMethodNameAndType());
			callee.calledFrom(method, argsInfo);
		}
	}

	public boolean doEscape(IMNode node, boolean isArgument) throws CompileException {
		if (opts.hasOption("_X_ssa_less_astack")) return true;
		IMMethod callee = method.findMethod(cpEntry);	
		MethodTable table = callee.getMethodTable();
		if (table==null) {
			if (obj==node && callee.doArgumentEscape(0)) return true; 
			for (int i=0;i<args.length;i++) {
				if (node==args[i] && callee.doArgumentEscape(i+1)) {
					return true;
				}
			}
			return false;
		} else {
			Enumeration candidates = table.getCandidates();
			while (candidates.hasMoreElements()) {
				IMMethod m = (IMMethod)candidates.nextElement();
				if (obj==node && m.doArgumentEscape(0)) return true; 
				for (int i=0;i<args.length;i++) {
					if (node==args[i] && m.doArgumentEscape(i+1)) {
						return true;
					}
				}
			}
			return false;
		}
	}

	public void showEscape(Coder coder) throws CompileException {
		IMMethod callee = method.findMethod(cpEntry);	
		MethodTable table = callee.getMethodTable();
		StringBuffer buf = new StringBuffer();
		if (table==null) {
			buf.append("	/* ");
			buf.append(callee.getClassName());
			buf.append(".");
			buf.append(callee.getMethodName());
			buf.append("(");
			if (callee.doArgumentEscape(0)) buf.append(" esc> ");
			buf.append(obj.toReadableString());
			for (int i=0;i<args.length;i++) {
				buf.append(",");
				if (args[i].isReference() && callee.doArgumentEscape(i+1))
					buf.append(" esc> ");
				buf.append(args[i].toReadableString());
			}
			buf.append(") */\n");
		} else {
			Enumeration candidates = table.getCandidates();
			while (candidates.hasMoreElements()) {
				IMMethod m = (IMMethod)candidates.nextElement();
				buf.append("	/* ");
				buf.append(m.getClassName());
				buf.append(".");
				buf.append(m.getMethodName());
				buf.append("(");
				if (m.doArgumentEscape(0)) buf.append(" esc> ");
				buf.append(obj.toReadableString());
				for (int i=0;i<args.length;i++) {
					buf.append(",");
					if (args[i].isReference() && m.doArgumentEscape(i+1))
						buf.append(" esc> ");
					buf.append(args[i].toReadableString());
				}
				buf.append(") */\n");
			}
		}
		coder.add_befor(buf.toString());
	}

	final public IMNode whyEscape(IMNode node, boolean isArgument) throws CompileException {
		if (doEscape(node,isArgument)) return this;
		return null;
	}

	public void escapeAnalyses(int mode) throws CompileException {
		if (mode!=IMEscapeVisitor.PREPARE) return;
		obj.setEscapePath(this);
		for (int i=0;i<args.length;i++) {
			if (args[i]==null) continue;
			args[i].setEscapePath(this);
		}
	}

	public IMBasicBlock inlineMethodCalls(IMBasicBlock current, IMNode prev) throws CompileException {
		IMMethod callee = method.findMethod(cpEntry);	

		if (!callee.isInlineCandidate()) return null;

		return current.insertCode(prev, this, callee, obj, args);
	}
	public String toReadableString() {
		String retString = "<stack>";
		if (obj!=null) retString = obj.toReadableString()+".";
		retString += cpEntry.getMemberName();
		return retString += super.toReadableString();
	}

	public String dumpBC() {return "InvokeSpecial "+cpEntry.getMemberName();}

	public int costs() throws CompileException {

		int costs = 50;
		for (int i=0;i<args.length;i++) costs+=args[i].costs();

		costs+=obj.costs();
		return costs;

	}

	public void translate(Coder coder) throws CompileException {
		IMMethodFrame frame = method.getMethodFrame();

		IMMethod callee = method.findMethod(cpEntry);	

		if (opts.hasOption("_X_dbg_astack")) showEscape(coder);

		if (!joinPoints.affectInvokeSpecial(this,method,callee,obj,args,coder)) {
			IMClass clazz = method.getIMClass(cpEntry.getClassName());
			coder.add_class(clazz.getAlias());

			coder.chk_ref(obj,method,bcPosition);
			coder.add(callee.getIdentifier());
			callee.emitArguments(coder, frame, obj, args);
			method.emitTestException(coder, callee, bcPosition);
		}
	}

}
