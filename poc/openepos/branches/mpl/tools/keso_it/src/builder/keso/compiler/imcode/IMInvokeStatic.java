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

public class IMInvokeStatic extends IMInvoke {


	/* copy constuctor */
	protected IMInvokeStatic() { }

	protected MethodRefCPEntry cpEntry;

	public IMInvokeStatic(IMMethod method, int bc, int bcpos, MethodRefCPEntry methodRef) {
		super(method,bc,bcpos,methodRef);
		int domainID=0;
		this.cpEntry=methodRef;
		method.requireMethod(domainID, cpEntry);
	}


	final public IMNode copy(IMCopyVisitor visitor) throws CompileException {

		IMInvokeStatic nnode = new IMInvokeStatic();
		nnode.copyNodeValues(visitor, this);

		nnode.typeDesc = typeDesc;
		nnode.joinPoints = joinPoints;
		nnode.cpEntry=cpEntry;
		nnode.args = new IMNode[args.length];
		for (int i=0;i<args.length;i++) nnode.args[i] = args[i].copy(visitor);

		if (visitor==null) return nnode;
		return visitor.visit(nnode,this);
	}

	final public void visitNodes(IMVisitor visitor) throws CompileException {
		if (visitor.skipNode(this)) return;

		visitor.visit(this);

		for (int i=0;i<args.length;i++) args[i].visitNodes(visitor);

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
		IMDataFlow argsInfo[] = new IMDataFlow[args.length];

		for (int i=0;i<args.length;i++) {
			if (args[i]==null) continue;
			argsInfo[i] = IMDataFlow.createDataFlowObj(args[i], method.getCurrentBasicBlock());
		}

		opts.vverbose("++ "+callee.getAlias()+" called from "+method.getAlias());
		method.call(callee.getClassName(), callee.getMethodNameAndType());
		callee.calledFrom(method, argsInfo);
	}

	public boolean doEscape(IMNode node, boolean isArgument) throws CompileException {
		if (opts.hasOption("_X_ssa_less_astack")) return true;
		IMMethod callee = method.findMethod(cpEntry);	
		for (int i=0;i<args.length;i++) {
			if (node==args[i] && callee.doArgumentEscape(i)) return true;
		}
		return false;
	}

	public void showEscape(Coder coder) throws CompileException {
		IMMethod callee = method.findMethod(cpEntry);	
		StringBuffer buf = new StringBuffer();
		buf.append("	/* ");
		buf.append(callee.getClassName());
		buf.append(".");
		buf.append(callee.getMethodName());
		buf.append("(");
		for (int i=0;i<args.length;i++) {
			if (i>0) buf.append(",");
			if (args[i].isReference() && callee.doArgumentEscape(i))
				 buf.append(" esc> ");
			buf.append(args[i].toReadableString());
		}
		buf.append(") */\n");
		coder.add_befor(buf.toString());
	}

	final public IMNode whyEscape(IMNode node, boolean isArgument) throws CompileException {
		return this;
	}

	public void escapeAnalyses(int mode) throws CompileException {
		if (mode!=IMEscapeVisitor.PREPARE) return;
		for (int i=0;i<args.length;i++) {
			if (args[i]==null) continue;
			args[i].setEscapePath(this);
		}
	}

	public IMBasicBlock inlineMethodCalls(IMBasicBlock current, IMNode prev) throws CompileException {
		IMMethod callee = method.findMethod(cpEntry);	

		if (!callee.isInlineCandidate()) return null;

		return current.insertCode(prev, this, callee, null, args);
	}

	public IMNode processStack(VirtualOperantenStack stack, IMNode extra_ops) throws CompileException {
		int[] argTypes = typeDesc.getBasicArgumentTypes();

		args = new IMNode[argTypes.length];
		for (int i=(argTypes.length-1);i>=0;i--) {
			args[i] = stack.pop();
		}

		if (datatype==BCBasicDatatype.VOID) return this;
	    	stack.push(this);

	    	return null;
	}

	public String toReadableString() {
		String retString = cpEntry.getMemberName();
		return retString += super.toReadableString();
	}

	public String dumpBC() {return "InvokeStatic "+cpEntry.getMemberName();}

	public int costs() throws CompileException {
		int costs = 50;
		for (int i=0;i<args.length;i++) costs+=args[i].costs();
		return costs;
	}

	public void translate(Coder coder) throws CompileException {
		IMMethodFrame frame = method.getMethodFrame();

		IMMethod callee = method.findMethod(cpEntry);	

		if (opts.hasOption("_X_dbg_astack")) showEscape(coder);

		if (!joinPoints.affectInvokeStatic(this,method,callee,args,coder)) {
			//IMClass clazz = method.getIMClass(cpEntry.getClassName());
			//coder.add_class(clazz.getAlias());
			coder.add_class(callee.getIMClass().getAlias());

			coder.add(callee.getIdentifier());
			callee.emitArguments(coder, frame, null, args);
			method.emitTestException(coder, callee, bcPosition);
		}
	}

}
