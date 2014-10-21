/**(c)

  Copyright (C) 2005 Christian Wawersich 

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

public class IMNode {

	protected final static IMNode NONE = new IMNode();

	protected IMMethod method;
	protected IMMethod src_method;
	protected BuilderOptions opts;

	protected int	   bytecode;
	protected int      datatype;
	protected int 	   bcPosition;
	protected int 	   src_bcPosition;

	protected IMNode	next_node;

	protected IMNode() { 
		this.method     = null;
		this.opts       = null;
		this.bytecode   = -1;
		this.datatype   = -1;
		this.src_method = null;
		this.bcPosition = -1;
		this.src_bcPosition = -1;
	}

	public IMNode(IMMethod method, int bc, int type, int bcpos) {
		this.method     = method;
		this.opts       = method.getOptions();
		this.bytecode   = bc;
		this.datatype   = type;
		this.src_method = method;
		this.bcPosition = bcpos;
		this.src_bcPosition = bcpos;
	}

	final public BuilderOptions getOptions() {
		return opts;
	}

	final public void copyNodeValues(IMCopyVisitor visitor, IMNode old) {
		opts       = old.opts; 
		bytecode   = old.bytecode;
		datatype   = old.datatype;
		src_method     = old.src_method;
		src_bcPosition = old.src_bcPosition;

		// this should be overwritten in visitor.visit()
		method     = old.method;
		bcPosition = old.bcPosition;

	}

	public IMNode copy(IMCopyVisitor visitor) throws CompileException {
		ONI();
		return null;
	}

	public void  visitNodes(IMVisitor visitor) throws CompileException {
		ONI();
	}

	/**
	 * This method is called by the IMCallGraphVisitor.
	 */
	public void analyseCall() throws CompileException {
		return;
	}

	public IMNode constant_folding() throws CompileException {
		return this;
	}

	public IMNode ssa_optimize() throws CompileException {
		return this;
	}

	/**
	 * isConstObject returns true if the object content is not changeable!
	 */
	public boolean isConstObject() throws CompileException {
		return false;
	}
	
	public IMClass getType() {
		return null;
	}

	public void escapeAnalyses(int mode) throws CompileException {
	}

	public void setEscapePath(IMNode node) throws CompileException {
	}

	public IMNode getEscapePath() throws CompileException {
		return null;
	}

	public boolean doEscape(IMNode node, boolean isArgument) throws CompileException {
		return false;
	}

	public IMNode whyEscape(IMNode node, boolean isArgument) throws CompileException {
		return null;
	}

	public boolean isTypeOf(IMClass type) throws CompileException {
		return false;
	}

	public int getArrayLength() throws CompileException {
		return -1;
	}

	public int costs() throws CompileException {
		ONI();
		return 0;
	}

	final public boolean isChecked(Coder coder) throws CompileException {
		return isChecked(coder.getCurrentBasicBlock());
	}

	public boolean isChecked(IMBasicBlock bb) throws CompileException { return false; }

	final public void setChecked(Coder coder) throws CompileException { 
		setChecked(coder.getCurrentBasicBlock());	
	}

	public void setChecked(IMBasicBlock bb) throws CompileException { }

	public boolean emptyList() { return false; }

	public boolean isBasicBlock() { return false; }

	public boolean isConstant()   { return false; }

	public boolean isFinalStatic() throws CompileException { return false; }

	public boolean isBranch()     { return false; }

	public boolean isVariable()   { return false; }

	public boolean isStackVariable(int stack_pos) { return false; }

	public boolean hasConstant()  { return false; }

	public IMConstant nodeToConstant() throws CompileException { throw new CompileException("not constant!"); }

	public boolean hasSideEffect() { return true; }

	/**
	 * nodes who only read but not change the global state
	 * are pure.
	 */
	public boolean isPureNode() {
		if (hasSideEffect()) {
			return false;
		}	
		// a node without side effects is allways pure
		return true;
	}

	public boolean isVolatile() throws CompileException {return false;}

	public boolean isAdd() {return false;}

	public boolean isSub() {return false;}

	public boolean isMul() {return false;}

	public boolean isBitOpr() {return false;}

	final public boolean isDouble() throws CompileException {
		if (datatype==-1) throw new CompileException("wrong or unkown datatype on stack");
		return BCBasicDatatype.isDouble(datatype);
	}

	final public int getBCPosition() { return bcPosition; }

	public int getDatatype() { return datatype; }

	final public boolean isByte() { return datatype==BCBasicDatatype.BYTE; }

	final public boolean isShort() { return datatype==BCBasicDatatype.SHORT; }

	final public boolean isReference() { return datatype==BCBasicDatatype.REFERENCE; }

	final public IMMethod getMethod() { return method; }

	public IMNode getLeftNode() throws CompileException { throw new CompileException("node has no left operand"); }

	public IMNode getRightNode() throws CompileException { throw new CompileException("node has no right operand"); }

	public IMSlot getIMSlot() { return null; }

	public void setIMSlot(IMSlot slot) { return; }

	public boolean checkReference() { return true; }

	public boolean checkArrayRange(int index) { return true; }

	public boolean checkArrayRange(IMOperant index) { return true; }

	final protected String nodeInfo() {
		try {
			return "("+method.getClassName()+":"+method.getMethodName()+" bcpos: "+bcPosition+")";
		} catch (RuntimeException ex) {
			return "("+getClass().getName()+")";
		}
	}

	public IMNode processStack(VirtualOperantenStack stack, IMNode extra_ops) throws CompileException {
		ONI();
		return null;
	}

	/**
	 * @return: basic block after insertion
	 */
	public IMBasicBlock inlineMethodCalls(IMBasicBlock current, IMNode prev) throws CompileException {
		return null;
	}

	public boolean isMemoryType() throws CompileException {
		return false;
	}

	/**
	 * emit() is similar to translate but only needed by chk_ref and
	 * therefore only implemented by IMAReadLocalVariable and IMGetField.
	 */
	public String emit() throws CompileException {
		ONI();
		return null;
	}

	public boolean translate_MT_Ref(Coder coder) throws CompileException {
		return false;
	}

	/**
	 * This method is the right place to add a piece of code to global.c if needed.
	 * The imnode must be registered with 
	 * repository.registerGlobalTranslationCallback(alias, callback);
	 */
	public void translate_global(Coder coder) throws CompileException {
		System.err.println(dumpBC());
		ONI();
		return;
	}

	public void translate(Coder coder) throws CompileException {
		System.err.println(dumpBC());
		ONI();
		return;
	}

	final public boolean isEndOfBasicBlock() {
		return next_node==null;
	}

	final public void setEndOfBasicBlock() {
		next_node = null;
	}

	final public IMNode next() {
		return next_node;
	}

	final public IMNode clear_link() {
		next_node = null;
		return this;
	}

	/**
	 * Append a list of IMNode objects. The list must start with
	 * a IMListHead object which is skipped, otherwise the behavoir
	 * is the same as append(). The end of the appended list
	 * is returned.
	 */
	final public IMNode append_list(IMNode node) {
		if (node==null) return this;

		if (node instanceof IMListHead) {
			if (node.next_node==null) return this;

			this.next_node = node.next_node;
			while (node.next_node!=null) { node=node.next_node; }

			return node;
		}

		this.next_node = node;

		return node;
	}

	/**
	 * Insert a IMNode object.
	 */
	final public IMNode insert(IMNode node) {
		if (node.next_node!=null) throw new Error("node lost");
		node.next_node = this.next_node;
		this.next_node = node;
		return node;
	}

	/**
	 * Append a IMNode object.
	 */
	final public IMNode append(IMNode node) {
		this.next_node = node;
		return node;
	}

	public String dumpBC() {
		return "BC "+bytecode;
	}

	public String lineInfo() {
		if (src_method != null) {
			return src_method.lineInfo(src_bcPosition);
		}
		return "unknown (bc:"+src_bcPosition+")";
	}

	public String toReadableString() {
		return bcPosition+": "+bytecode; 
	}

	public String toMemAlias() {
		return toReadableString();
	}

	/* debugging stuff */

	final protected void assertIsReference(IMNode node) throws CompileException {
		if (node.getDatatype()!=BCBasicDatatype.REFERENCE)	
			throw new CompileException("bcpos:"+Integer.toString(bcPosition)+" wrong datatype on stack!");
	}

	final protected void assertIsIntValue(IMNode node) throws CompileException {
		int t = node.getDatatype();
		if (!(t==BCBasicDatatype.INT||t==BCBasicDatatype.SHORT||t==BCBasicDatatype.BYTE)) 
			throw new CompileException("bcpos:"+Integer.toString(bcPosition)+" wrong datatype on stack!");
	}

	final protected boolean isCategory2(IMNode node) {
		return (node.getDatatype()==BCBasicDatatype.LONG)||(node.getDatatype()==BCBasicDatatype.DOUBLE);
	}

	final protected void assertIsLongOrDouble(IMNode node) throws CompileException {
		int t = node.getDatatype();
		if (!(t==BCBasicDatatype.LONG||t==BCBasicDatatype.DOUBLE)) 
			throw new CompileException("bcpos:"+Integer.toString(bcPosition)+" wrong datatype on stack!");
	}

	final protected void ONI() throws CompileException {
		throw new Error(getClass().getName()+": Operation not implemented! "+nodeInfo());
	}
}
