/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.compiler;

import keso.compiler.imcode.*;

import keso.classfile.datatypes.*;
import keso.classfile.constantpool.*;

final public class VirtualOperantenStack {

	private IMNode[] opr_stack;
	private int ptr;
	private IMMethod method;
	private BuilderOptions opts;

	public VirtualOperantenStack(IMMethod method) {
		this(method, 10);
	}

	public VirtualOperantenStack(IMMethod method, int size) {
		this.method = method;
		this.opts   = method.getOptions();
		opr_stack   = new IMNode[size+1];
		ptr=0;
	}

	/**
	 * push a operant on top of the stack.
	 */
	public void push(IMNode opr) throws CompileException {
		if (ptr>=opr_stack.length) extend(10);
		if (opr==null) throw new CompileException("store null pointer"); 
		opr_stack[ptr++]=opr;
	}

	/**
	 * fetch the top operant form stack.
	 */
	public IMNode pop() throws CompileException {
		if (ptr==0) throw new CompileException("operanten stack underrun"); 
		return opr_stack[--ptr].clear_link();
	}  

	/**
	 * clear the operant stack.
	 */
	public void clear() { ptr=0; }

	/**
	 * write the content of the current operanten stack to local variables
	 * needed because of possible side effects.
	 *
	 * exluded objects are constants, read variables and read return address 
	 */
	public void store(int bcPosition, IMNode extra_ops) throws CompileException {

		for (int i=0;i<ptr;i++) {
			IMNode curr = opr_stack[i];
			if (curr instanceof IMConstant) continue;
			if (curr instanceof IMReadLocalVariable) continue; 
			if (curr instanceof IMPopReturnAddr) continue;
			if (curr.isMemoryType()) continue;
			if (curr.isStackVariable(i)) continue; 

			IMStoreLocalVariable store_var = method.createStoreStackVariable(i,curr,bcPosition);
			opr_stack[i]=store_var.getReadOperation();

			extra_ops = extra_ops.append(store_var);
		}
	}

	/**
	 * write the content of the current operanten stack to local variables
	 * needed because of possible side effects.
	 *
	 * exluded objects are constants and stack variables it self 
	 */
	public void store2(int bcPosition, IMNode extra_ops, IMSlot var) throws CompileException {

		for (int i=0;i<ptr;i++) {
			IMNode curr = opr_stack[i];
			if (curr instanceof IMConstant) continue;
			// DON'T DO THIS! THIS WILL BREAK THE EXECUTION ORDER!
			// if (curr instanceof IMReadLocalVariable && curr.getIMSlot().equals(var)) continue; 
			if (curr.isStackVariable(i)) continue; 

			IMStoreLocalVariable store_var = method.createStoreStackVariable(i,curr,bcPosition);
			opr_stack[i]=store_var.getReadOperation();

			extra_ops = extra_ops.append(store_var);
		}
	}

	/**
	 * write the content of the current operanten stack to local variables
	 * needed because of possible side effects.
	 *
	 * exluded objects are constants, read variables, read return address,
	 * getfield, getstatic
	 *
	 * The store is only executed if one of the upper stack members are 
	 * an methode invocation. 
	 */
	public void store3(int bcPosition, IMNode extra_ops, int deep) throws CompileException {

		if (deep>0) {
			boolean flag = true;
			for (int i=0;i<deep && i<ptr;i++) {
				if (opr_stack[ptr-(i+1)] instanceof IMInvoke) flag=false;
			}
			if (flag) return;
		}

		for (int i=0;i<ptr;i++) {
			IMNode curr = opr_stack[i];
			if (curr instanceof IMConstant) continue;
			if (curr instanceof IMReadLocalVariable) continue; 
			if (curr instanceof IMPopReturnAddr) continue;
			if (curr instanceof IMGetStatic) continue;
			if (curr instanceof IMGetField) continue;
			if (curr.isMemoryType()) continue;
			if (curr.isStackVariable(i)) continue; 

			IMStoreLocalVariable store_var = method.createStoreStackVariable(i,curr,bcPosition);
			opr_stack[i]=store_var.getReadOperation();

			extra_ops = extra_ops.append(store_var);
		}
	}

	public void init(IMNode[] stack) throws CompileException {
		ptr=0;
		if (stack==null) return;
		for (int i=0;i<stack.length;i++) if (stack[i]!=null) push(stack[i].copy(null));
	}

	public IMNode[] leave(IMNode extra_ops) throws CompileException {
		if (ptr==0) return null;	
		IMNode[] leaveStack = new IMNode[ptr];
		for (int i=0;i<ptr;i++) {
			IMNode curr = opr_stack[i];

			if (curr.isStackVariable(i)) {
				leaveStack[i]=curr;
				continue; 
			}

			if (curr instanceof IMPopReturnAddr) {
				leaveStack[i] = curr;
			} else {
				IMStoreLocalVariable store_var = method.createStoreStackVariable(i,curr,-1);
				leaveStack[i] = store_var.getReadOperation();
				extra_ops = extra_ops.insert(store_var);
			}
		}
		ptr=0;
		return leaveStack;
	}

	private void extend(int value) {
		if (opts.hasVerbose()) opts.warn("virtual operant stack overrun! ("+opr_stack.length+" exceeded)");
		IMNode[] new_stack = new IMNode[opr_stack.length+value];
		System.arraycopy(opr_stack,0,new_stack,0,opr_stack.length);
		opr_stack =  new_stack;
		new_stack = null;
	}

	public String stackToString() {
		String ret = "";

		for (int i=0;i<ptr;i++) {
			ret += " " + opr_stack[i].toReadableString();
		}

		return ret;
	}

	public String toString() {
		return stackToString();
	}
}
