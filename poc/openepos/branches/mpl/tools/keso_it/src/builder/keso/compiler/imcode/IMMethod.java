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
import keso.compiler.kni.*;
import keso.compiler.backend.Coder;

import keso.util.*; 

import java.util.Enumeration;
import java.util.Hashtable;

import java.io.IOException;
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.io.File;

import java.util.Vector;

final class JumpStack {

	private IMBasicBlock[] stack;
	private int pos;

	public JumpStack() {
		stack = new IMBasicBlock[60];
		pos = 0;
	}

	public void push(IMBasicBlockList bl) {
		for (int i=0;i<bl.length();i++) push(bl.at(i));	
	}

	public void push(IMBasicBlock node) {
		stack[pos++] = node;
		if (pos==stack.length) {
			IMBasicBlock[] new_stack = new IMBasicBlock[stack.length+10];
			for (int i=0;i<stack.length;i++) new_stack[i] = stack[i];
			stack = new_stack;
			new_stack=null;
		}
	}

	public IMBasicBlock pop() {
		if (pos<=0) return null; 
		return stack[--pos];
	}
}

final class MethodEntry {

	String className;
	String nameAndType;
	private int hash_value;

	MethodEntry(String c, String n) {
		className=c;
		nameAndType=n;
		hash_value = className.hashCode() + nameAndType.hashCode();
	}

	public int hashCode() {
		return hash_value;
	}
	
	public boolean equals(java.lang.Object obj) {
		if (!(obj instanceof MethodEntry)) return false;
		MethodEntry e = (MethodEntry) obj;
		if (!e.className.equals(className)) return false;
		if (e.nameAndType.equals(nameAndType)) return true;
		return false;
	}
}

final class AliasEntry {

	IMBasicBlock bb;
	IMSlot var;
	IMNode value;

	public AliasEntry(IMBasicBlock bb, IMSlot var, IMNode value) {
		this.bb=bb;
		this.var=var;
		this.value=value;
	}
}

public class IMMethod {

	final static private boolean dbg_process_vstack = false;

	private static final int[] aType = new int[] {
		-1,-1,-1,-1,
		BCBasicDatatype.BOOLEAN, BCBasicDatatype.CHAR,
		BCBasicDatatype.FLOAT, BCBasicDatatype.DOUBLE,
		BCBasicDatatype.BYTE, BCBasicDatatype.SHORT,
		BCBasicDatatype.INT, BCBasicDatatype.LONG }; 

	private static final String[] rType = new String[] {
		"","","","",
		"int", "char",
		"float", "double",
		"char", "short",
		"long", "long long" }; 

	private boolean stack_processed = false;
	protected String 	alias;
	protected IMClass	clazz;
	protected MethodData	source;
	private   StringBuffer  methodBody;
	private   Hashtable	includes;
	protected IMMethodFrame   frame;
	protected BuilderOptions  opts;
	protected ConstantPool    cPool;
	protected MethodTable	mtable;
	protected Hashtable	calledMethods;
	protected Vector	called_from;

	private IMBasicBlock	 currentBB;
	private IMBasicBlock     label_list;
	private IntegerHashtable label_index;
	private IMExceptionHandler[]   catch_list;
	private IntegerHashtable 	 local_exception_calls;
	private IMBasicBlock	 endBlock;
	private IMSlot		 return_slot;
	private int 		 addr_slot;

	private DominatorTree    domtree;

	protected boolean isInit;
	protected boolean analysed=false;
	protected boolean	isPure=true;
	protected boolean	isConst=true;
	protected boolean hasNoCode;
	private boolean analyseCalleesDone = false; 
	private boolean allready_inlined = false;

	protected  IMMethod(IMClass clazz) throws CompileException {
		this.clazz  = clazz;
		this.opts   = clazz.getOptions();
		this.cPool  = clazz.getConstantPool();
	}
    
	/**
	  IMMethod representiert eine Methode und all ihre Daten in der Zwischendarstellung. Die
	  Klasse der Methode und die Daten aus der Kalssendatei werden als Parameter bei der
	  Objekt-Erzeugung uebergeben. Enthaelt die Methode ausfuehrbaren Bytecode so wird dieser
	  gelesen und in eine Zwischendarstellung umgewandelt.
	 */
	public IMMethod(IMClass clazz, MethodData source) throws CompileException {
		this(clazz);

		this.source = source;

		isInit = (source.getMethodName().equals("<init>") 
				|| source.getMethodName().equals("<clinit>"));

		if (source.getCode()!=null) {
			hasNoCode=false;
			this.frame  = new IMMethodFrame(this,
					source.getInitialVariableTypes(),
					source.getNumLocalVariables());
			parseBC(source);
		} else {
			hasNoCode=true;
			if (source.isAbstract() || source.isNative()) {
				this.frame  = new IMMethodFrame(this,
						source.getInitialVariableTypes(),
						0);
			}

			if (source.isNative()) {
				isPure = false;
				isConst = false;
			}
		}
	}

	/**
	 * Translate the byte code position into a line number.
	 */
	public int getLineNumber(int bcpos) {
		int line = -1;

		LineAttributeData linedata[] = source.getLineNumberTable();
		if (linedata==null) return -1;

		for (int i=0;i<linedata.length;i++) {
			if (bcpos<linedata[i].startBytecodepos) return line;
			line = linedata[i].lineNumber;
		}

		return line;
	}

	/**
	 * Register a class as used. 
	 */
	public void requireClass(int domainID,String className) {
		clazz.requireClass(domainID,className);
	}

	public void call(String className, String methodName) {
		if (calledMethods==null) calledMethods=new Hashtable();
		MethodEntry e = new MethodEntry(className, methodName);
		if (calledMethods.get(e)==null)
			calledMethods.put(e,e);
	}

	/**
	 * Register a method as used. 
	 */
	public void requireMethod(int domainID, String className, String methodName) {
		if (opts.pruneClassTree()) clazz.requireMethod(domainID, className, methodName);
		call(className, methodName);
	}

	/**
	 * Register a method as used. 
	 */
	final public void requireMethod(int domainID, MethodRefCPEntry cpEntry) {
		requireMethod(domainID, cpEntry.getClassName(), cpEntry.getMemberName()+cpEntry.getMemberTypeDesc());
	}

	/**
	 * Register a method as used. 
	 */
	final public void requireMethod(int domainID, InterfaceMethodRefCPEntry cpEntry) {
		requireMethod(domainID, cpEntry.getClassName(), cpEntry.getMemberName()+cpEntry.getMemberTypeDesc());
	}

	public DominatorTree getDomTree() throws CompileException {
		if (domtree==null) computeDomTree(); 
		return domtree;
	}

	public void setMethodTable(MethodTable table) {
		table.tableIsUsedBy(this);
		mtable = table;
	}

	public MethodTable getMethodTable() {
		return mtable;
	}

	public boolean isAnalysed() {
		return analysed;
	}

	public boolean isInit() {
		return isInit;
	}

	final public boolean isClassInit() {
		return getMethodNameAndType().equals("<clinit>()V");
	}

	/**
	 * The GCC do better work when it knows more about the function, 
	 * therefore we will analyse if a method is _pure_ and use the
	 * GCC function attributes for annotation.
	 *
	 * = gcc info pages ===============================================
	 * Many functions have no effects except the return value and their
	 * return value depends only on the parameters and/or global
	 * variables. Such a function can be subject to common subexpression
	 * elimination and loop optimization just as an arithmetic operator
	 * would be. These functions should be declared with the attribute
	 * `pure'. For example,
	 *
	 * int square (int) __attribute__ ((pure));
	 *
	 * says that the hypothetical function `square' is safe to call fewer
	 * times than the program says.
	 *
	 * Some of common examples of pure functions are `strlen' or `memcmp'.
	 * Interesting non-pure functions are functions with infinite loops
	 * or those depending on volatile memory or other system resource,
	 * that may change between two consecutive calls (such as `feof' in a
	 * multithreading environment).
	 * ================================================================
	 */
	public boolean isPure() throws CompileException {
		if (opts.hasOption("_X_gcc_all_pure")) return true;
		if (opts.hasOption("gcc_no_pure")) return false;
		if (source.isNative()) return false;
		if (Weavelet.FALSE==getJoinPoints().checkAttribut(this, Weavelet.ATTR_PURE)) return false; 
		if (isPure && !analyseCalleesDone) analyseCalledMethods();
		return isPure;
	}

	/**
	 * The GCC do better work when it knows more about the function,
	 * therefore we will analyse if a method is const and use the
	 * GCC function attributes for annotation.
	 *
	 * = gcc info pages ===============================================
	 * Many functions do not examine any values except their arguments,
	 * and have no effects except the return value. Basically this is
	 * just slightly more strict class than the `pure' attribute above,
	 * since function is not allowed to read global memory.
	 *
	 * Note that a function that has pointer arguments and examines the
	 * data pointed to must _not_ be declared `const'. Likewise, a
	 * function that calls a non-`const' function usually must not be
	 * `const'. It does not make sense for a `const' function to return
	 * `void'.
	 * ================================================================
	 */
	public boolean isConstant() throws CompileException {
		if (opts.hasOption("_X_gcc_all_const")) return true;
		if (opts.hasOption("gcc_no_const")) return false;
		if (!isPure()) return false;
		if (Weavelet.FALSE==getJoinPoints().checkAttribut(this, Weavelet.ATTR_CONST)) return false; 
		if (isConst && !analyseCalleesDone) analyseCalledMethods();
		return isConst;
	}

	public boolean hasCode() {
		if (opts.hasOption("omit_unused_methods") && called_from==null) {
			return false;
		}
		return !hasNoCode;
	}

	public boolean unused() {
		if (called_from==null) return true;
		return false;
	}

	/**
	 * Analyse if a task can block by calling the method represented the
	 * IMMethod object.
	 */
	private String canBlockIn = null;
	private final static String none_blocking = "#none_blocking";
	private final static String blocks_in_new = "#new_operation";
	public boolean canBlock() throws CompileException {

		if (canBlockIn==none_blocking) return false;
		if (canBlockIn!=null) return true;

		if (opts.hasOption("_X_all_CanBlock")) return true;
		if (Weavelet.TRUE==getJoinPoints().checkAttribut(this, Weavelet.ATTR_BLOCK)) {
			canBlockIn="self";
			return true;
		}

		if (!analyseCalleesDone) analyseCalledMethods();
		if (canBlockIn!=null && canBlockIn!=none_blocking) return true;
		canBlockIn = none_blocking;
		return false;
	}

	/**
	 * mark Method as blocking in object allocation
	 */
	public void blockInNew() { canBlockIn = blocks_in_new; }

	/**
	 * BuilderOptions
	 */
	public BuilderOptions getOptions() {
		return opts;
	}

	public IMMethodFrame getMethodFrame() {
		return frame;
	}

	public int getSizeOfReturnValue() throws CompileException {
		int rsize = opts.getTypeSize(opts.typeToString(getBasicReturnType()));
		return rsize;
	}

	public int getSizeOfArguments() throws CompileException {
		IMSlot[] argSlots = frame.getMethodArguments();
		int total = 0;

		for (int i = 1; i < argSlots.length; ++i) {
			total += opts.getTypeSize(argSlots[i].cType());
		}

		return total;
	}

	/**
	 * getArg(type,nr) liefert ein IMNode-Object fuer den 
	 * Zurgriff auf die Parameter der Methode.
	 *
	 * @parameter type
	 * 	(BCBasicDatatype.REFERENCE, ...)
	 * @parameter nr
	 * 	Bei virtuellen Methoden ist 0 der this-Pointer, alle
	 * 	weitern Parameter folgen.
	 */
	public IMNode getArg(int type, int nr) throws CompileException {
		return frame.readArgument(type, nr);
	}

	/**
	 * getIMClass() liefert die definierende Klasse der Methode.
	 */
	public IMClass getIMClass() {
		return clazz;
	}

	public void storeMethodBody(StringBuffer body, Hashtable includes) {
		methodBody = body;
		this.includes = includes;
	}

	public StringBuffer getMethodBody() {
		return methodBody;
	}

	public StringBuffer getMethodBodyPretty() {
		StringBuffer body = methodBody;
		int pos=-1;
		while ((pos=body.indexOf("return;"))>0) {
			body.delete(pos,pos+7);
		}
		return methodBody;
	}

	// HACK: inlining for dummies
	public Hashtable getMethodIncludes() {
		return includes;
	}

	/**
	 * Get the global join points. 
	 * This is just delegated to the class. 
	 * @see IMClass.getJoinPoints()
	 */
	public JoinPointChecker getJoinPoints() {
		return clazz.getJoinPoints();
	}

	/**
	 * Get the global ClassStore.
	 * This is just delegated to the class. 
	 * @see IMClass.getClassStore()
	 */
	public ClassStore getClassStore() {
		return clazz.getClassStore();
	}

	/**
	 * Get the IMClass object of a given class.
	 * This is just delegated to the current class store. 
	 * @see ClassStore.getClass(String)
	 */
	public IMClass getIMClass(String classname) {
		return clazz.findClass(classname);
	}

	/**
	 * Get the class name of the defining class.
	 */
	public String getClassName() {
		return clazz.getClassName();
	}

	/**
	 * Lookup the IMMethod object by a constant pool entry.
	 */
	final public IMMethod findMethod(MethodRefCPEntry cpEntry) throws CompileException {
		return findMethod(cpEntry.getClassName(), cpEntry.getMemberName()+cpEntry.getMemberTypeDesc());
	}

	/**
	 * Lookup the IMMethod object by a constant pool entry.
	 */
	final public IMMethod findMethod(InterfaceMethodRefCPEntry cpEntry) throws CompileException {
		return findMethod(cpEntry.getClassName(), cpEntry.getMemberName()+cpEntry.getMemberTypeDesc());
	}

	/**
	 * Lookup the IMMethod object by the symbolic name and type. 
	 */
	final public IMMethod findMethod(String classname, String nameAndType) throws CompileException {

		IMClass cls = clazz.findClass(classname);
		if (cls==null) throw new CompileException("Class not found "+classname);

		if (nameAndType==null) throw new CompileException("Undefined method name.");

		for (IMClass scls=cls;scls!=null;scls=scls.getSuperClass()) {
			IMMethod m = scls.getMethod(nameAndType);
			if (m!=null) return m;

			IMClass[] _ifaces = cls.getInterfaces();
			for (int i=0;_ifaces!=null && i<_ifaces.length;i++) {
				m = _ifaces[i].getMethod(nameAndType);
				if (m!=null) return m;
			}
		}

		throw error(0,"Method "+classname+"." +nameAndType+" not found!"); 
	}

	/**
	 * Is true if the method is not void.
	 */
	public boolean hasReturnValue() {
		return (getBasicReturnType()!=BCBasicDatatype.VOID);
	}

	/**
	 * Get the BCBasicDatatype of the return value.
	 */
	public int getBasicReturnType() {
	    return source.getBasicReturnType();
	}

	/**
	 * Get the C type of the return value.
	 */
	public String getReturnType() {
		return BuilderOptions.typeToString(source.getBasicReturnType());
	}

	/**
	 * Get the hole C like argument list.
	 */
	public String getArgString() throws CompileException {
		return frame.getArgString();
	}

	public String getArgReCallString() throws CompileException {
		return frame.getArgReCallString();
	}

	/**
	 * Get the alias name of the method, which is used in the C source.
	 */
	public String getAlias() {
		if (alias==null) alias=DecoratedNames.createMethodAlias(opts,this);
		return alias;
	}

	public MethodData getSource() {
		return source;
	}
    
	/**
	 * return the C code identifer.
	 */
	public String getIdentifier() {
		return getAlias();
	}

	/**
	 * return the C code identifer for a virtual method invocation.
	 */
	public String getInvokeAlias() {
		return "INVOKE_"+getIdentifier().toUpperCase();
	}

	public void setAddrSlot(int value) {
		addr_slot = value;
	}

	public int getAddrSlot() {
		return addr_slot;
	}

	public String getMethodName() {
		return source.getMethodName();
	}

	public String getMethodNameAndType() {
		return source.getMethodNameAndType();
	}

	final public boolean termed(String symbol) {
		String term = getMethodNameAndType();
		if (term.equals(symbol)) return true;
		term = getMethodName();
		if (term.equals(symbol)) return true;
		return false;
	}

	public boolean isConstructor() {
		// TODO: remove duplicated method (see isInit)
		return getMethodName().equals("<init>");
	}

	public boolean isStatic() { return source.isStatic(); }

	public boolean isVirtual() {
		return (!source.isStatic() && !isFinal() && !source.isPrivate());
	}

	public boolean isPrivate() { return source.isPrivate(); }

	public boolean isFinal() {
		if (source.isFinal() || clazz.isFinal()) return true;
		if (clazz.hasSubClasses()) {
			IMClass[] subs = clazz.getSubClasses();
			for (int i=0;i<subs.length;i++) {
				if (subs[i].getMethod(getMethodNameAndType())!=null) {
					if (isPrivate()) {
						opts.warn("private method "+getMethodNameAndType()+
								" is overwritten in "+subs[i].getClassName());
						return true;
					}
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public int getNumberOfBasicBlocks() {
		int number=0;
		for (IMBasicBlock bb=label_list;bb!=null;bb=(IMBasicBlock)bb.next()) {
			number++;
		}
		return number;
	}

	public String dumpLabelIndex() {
		return label_index.toString();
	}

	protected IMBasicBlock getBasicBlock(int ip, int offset) throws CompileException {
		int bcpos = ip + offset;

		IMBasicBlock label = (IMBasicBlock)label_index.get(bcpos);
		if (label!=null) return label;

		label = createBBlock(bcpos);
		label_index.put(bcpos,label);
		addBasicBlock(label);

		return label;
	}

	private void addBasicBlock(IMBasicBlock bb) {
		int bcpos = bb.getBCPosition();

		if (label_list==null) {
			label_list=bb;
			return;
		}

		if (label_list.getBCPosition() > bcpos) {
			bb.next_node = label_list;	
			label_list = bb;
			return;
		}

		IMBasicBlock c;
		for (c=label_list;c.next_node!=null&&c.next_node.getBCPosition()<bcpos;c=(IMBasicBlock)c.next_node);

		bb.next_node = c.next_node;
		c.next_node = bb;
	    
		return;
	}

	public IMBasicBlock createBBlock(int bcpos) {
		return new IMBasicBlock(this,bcpos);
	}

	public IMAMTConstant createMTConstant(IMNode self, IMClass type, int addr) {
		return new IMAMTConstant(this, -1, self.getBCPosition(), type, addr);
	}

	public IMAMemConstant createMemConstant(IMNode self, int addr, int size) {
		return new IMAMemConstant(this, -1, self.getBCPosition(), addr, size);
	}

	public IMStoreLocalVariable createStoreStackVariable(int stackpos, IMNode expr, int bcpos)
		throws CompileException { 

		int type = expr.getDatatype();
		IMSlot vslot = frame.getStackVariable(type, stackpos);
		vslot.setAlias(expr);
		IMStoreLocalVariable nvar = storeLocal(bcpos, vslot);
		nvar.setOperant(expr);
		nvar.clear_link();

		return nvar;
	}

	public IMStoreLocalVariable createStoreVariable(IMSlot vslot, IMNode expr, int bcpos) throws CompileException { 
		int type = vslot.getType();
		IMStoreLocalVariable nvar = storeLocal(bcpos, vslot);
		nvar.setOperant(expr);
		nvar.clear_link();
		return nvar;
	}

	private IMStoreLocalVariable storeLocal(int bcpos, IMSlot vslot) throws CompileException {
		int type = vslot.getType();
		IMStoreLocalVariable nvar;
		switch (type) {
			case BCBasicDatatype.BOOLEAN: 
			case BCBasicDatatype.BYTE: 
			case BCBasicDatatype.CHAR: 
			case BCBasicDatatype.SHORT: 
				nvar=new IMIStoreLocalVariable(this,54,type,bcpos,vslot);
				break;
			case BCBasicDatatype.INT: 
				nvar=new IMIStoreLocalVariable(this,54,bcpos,vslot);
				break;
			case BCBasicDatatype.LONG: 
				nvar=new IMLStoreLocalVariable(this,55,bcpos,vslot);
				break;
			case BCBasicDatatype.FLOAT: 
				nvar=new IMFStoreLocalVariable(this,56,bcpos,vslot);
				break;
			case BCBasicDatatype.DOUBLE: 
				nvar=new IMDStoreLocalVariable(this,57,bcpos,vslot);
				break;
			case BCBasicDatatype.REFERENCE: 
				nvar=new IMAStoreLocalVariable(this,58,bcpos,vslot);
				break;
			default:
				throw error(bcpos,"unknown type: "+type);
		}
		return nvar;
	}

	public IMReadLocalVariable readLocal(int bcpos, IMSlot vslot) throws CompileException {
		if (vslot==null) throw new CompileException("unknown slot");
		int type = vslot.getType();
		IMReadLocalVariable nvar;
		switch (type) {
			case BCBasicDatatype.BOOLEAN: 
			case BCBasicDatatype.BYTE: 
			case BCBasicDatatype.CHAR: 
			case BCBasicDatatype.SHORT: 
			case BCBasicDatatype.INT: 
				nvar=new IMIReadLocalVariable(this,21,bcpos,vslot);
				break;
			case BCBasicDatatype.LONG: 
				nvar=new IMLReadLocalVariable(this,22,bcpos,vslot);
				break;
			case BCBasicDatatype.FLOAT: 
				nvar=new IMFReadLocalVariable(this,23,bcpos,vslot);
				break;
			case BCBasicDatatype.DOUBLE: 
				nvar=new IMDReadLocalVariable(this,24,bcpos,vslot);
				break;
			case BCBasicDatatype.REFERENCE: 
				nvar=new IMAReadLocalVariable(this,25,bcpos,vslot);
				break;
			default:
				throw error(bcpos,"unknown type: "+type);
		}
		return nvar;
	}

	public IMPopReturnAddr createPopReturnAddr(int bcpos) {
		return new IMPopReturnAddr(this,-1,-1,bcpos);
	}

	public IMConstant createIMIConstant(int value, int bcpos) {
		return new IMIConstant(this,19,bcpos,value);
	}

	public IMConstant createIMLConstant(long value, int bcpos) {
		return new IMLConstant(this,20,bcpos,value);
	}

	public IMConstant createIMFConstant(float value, int bcpos) {
		return new IMFConstant(this,19,bcpos,value);
	}

	public IMConstant createIMDConstant(double value, int bcpos) {
		return new IMDConstant(this,20,bcpos,value);
	}

	public IMConstant createIMAConstant(ConstantPoolEntry cpEntry, int bcpos) {
		return new IMAConstant(this,19,bcpos,cpEntry);
	}

	public IMConstant createIMNullConstant(int bcpos) {
		return new IMNullConstant(this,19,bcpos);
	}

	private IMConstant readConstantPool(int bc,int bcpos,int cindex) {
		ConstantPoolEntry cpEntry = cPool.constantEntryAt(cindex);
		if (cpEntry instanceof NumericCPEntry) {
			try {
				BCBasicDatatype cpValue  = ((NumericCPEntry)cpEntry).value();
				switch (cpValue.type()) {
					case BCBasicDatatype.INT:
						return createIMIConstant(((BCInteger)cpValue).value(),bcpos);
					case BCBasicDatatype.LONG:
						return createIMLConstant(((BCLong)cpValue).value(),bcpos);
					case BCBasicDatatype.FLOAT:
						return createIMFConstant(((BCFloat)cpValue).value(),bcpos);
					case BCBasicDatatype.DOUBLE:
						return createIMDConstant(((BCDouble)cpValue).value(),bcpos);
					default:
						throw new CompileException("unkown datatype constant");
				}
			} catch (Exception ex) {
				System.err.println("!! "+ex);
				System.exit(-1);
			}
		}
		return createIMAConstant(cpEntry,bcpos);
	}

	public IMNode createGoto(IMBasicBlock label, int bcpos) {
		return new IMGoto(this, 167, bcpos, label);
	}

	/**
	 * read bc stream and create list of im-objects
	 */
	private void processBCStream(BytecodeInputStream bcStream, IMNode bc_list, Vector jsr_calls) throws CompileException {
		IMBasicBlock label,des;
		boolean wide_prefix = false;
		while (bcStream.hasMore()) {
			int ip,bc,vi,offset;

			ip = bcStream.getCurrentPosition();
			bc = bcStream.readUnsignedByte();

			switch (bc) {
				case 1: // (0x01) aconst_null
					bc_list=bc_list.append(new IMNullConstant(this,bc,ip));
					break;
				case 2: // (0x02) iconst_m1
					bc_list=bc_list.append(new IMIConstant(this,bc,ip,-1));
					break;
				case 3: // (0x03) iconst_0
					bc_list=bc_list.append(new IMIConstant(this,bc,ip,0));
					break;
				case 4: // (0x04) iconst_1
					bc_list=bc_list.append(new IMIConstant(this,bc,ip,1));
					break;
				case 5: // (0x05) iconst_2
					bc_list=bc_list.append(new IMIConstant(this,bc,ip,2));
					break;
				case 6: // (0x06) iconst_3
					bc_list=bc_list.append(new IMIConstant(this,bc,ip,3));
					break;
				case 7: // (0x07) iconst_4
					bc_list=bc_list.append(new IMIConstant(this,bc,ip,4));
					break;
				case 8: // (0x08) iconst_5
					bc_list=bc_list.append(new IMIConstant(this,bc,ip,5));
					break;
				case 9: // (0x09) lconst_0
					bc_list=bc_list.append(new IMLConstant(this,bc,ip,0));
					break;
				case 10: // (0x0a) lconst_1
					bc_list=bc_list.append(new IMLConstant(this,bc,ip,1));
					break;
				case 11: // (0x0b) fconst_0
					bc_list=bc_list.append(new IMFConstant(this,bc,ip,0));
					break;
				case 12: // (0x0c) fconst_1
					bc_list=bc_list.append(new IMFConstant(this,bc,ip,1));
					break;
				case 13: // (0x0d) fconst_2
					bc_list=bc_list.append(new IMFConstant(this,bc,ip,2));
					break;
				case 14: // (0x0e) dconst_0
					bc_list=bc_list.append(new IMDConstant(this,bc,ip,0));
					break;
				case 15: // (0x0f) dconst_1
					bc_list=bc_list.append(new IMDConstant(this,bc,ip,1));
					break;
				case 16: // (0x10) bipush
					bc_list=bc_list.append(new IMIConstant(this,bc,ip,bcStream.readByte()));
					break;
				case 17: // (0x11) sipush
					bc_list=bc_list.append(new IMIConstant(this,bc,ip,bcStream.readShort()));
					break;
				case 18: // (0x12) ldc
					bc_list=bc_list.append(readConstantPool(bc,ip,bcStream.readUnsignedByte()));
					break;
				case 19: // (0x13) ldc_w  // push 32 bit value
				case 20: // (0x14) ldc2_w // push 64 bit value long or double
					bc_list=bc_list.append(readConstantPool(bc,ip,bcStream.readUnsignedShort()));
					break;
				case 21: // (0x15) iload
					if (wide_prefix) vi=bcStream.readUnsignedShort(); 
					else vi=bcStream.readUnsignedByte();
					bc_list=bc_list.append(new IMIReadLocalVariable(this,bc,ip,vi));
					break;
				case 22: // (0x16) lload
					if (wide_prefix) vi=bcStream.readUnsignedShort(); 
					else vi=bcStream.readUnsignedByte();
					bc_list=bc_list.append(new IMLReadLocalVariable(this,bc,ip,vi));
					break;
				case 23: // (0x17) fload
					if (wide_prefix) vi=bcStream.readUnsignedShort(); 
					else vi=bcStream.readUnsignedByte();
					bc_list=bc_list.append(new IMFReadLocalVariable(this,bc,ip,vi));
					break;
				case 24: // (0x18) dload
					if (wide_prefix) vi=bcStream.readUnsignedShort(); 
					else vi=bcStream.readUnsignedByte();
					bc_list=bc_list.append(new IMDReadLocalVariable(this,bc,ip,vi));
					break;
				case 25: // (0x19) aload
					if (wide_prefix) vi=bcStream.readUnsignedShort(); 
					else vi=bcStream.readUnsignedByte();
					bc_list=bc_list.append(new IMAReadLocalVariable(this,bc,ip,vi));
					break;
				case 26: // (0x1a) iload_0
					//testLocalVar(0);
					bc_list=bc_list.append(new IMIReadLocalVariable(this,bc,ip,0));
					break;
				case 27: // (0x1b) iload_1
					bc_list=bc_list.append(new IMIReadLocalVariable(this,bc,ip,1));
					break;
				case 28: // (0x1c) iload_2
					bc_list=bc_list.append(new IMIReadLocalVariable(this,bc,ip,2));
					break;
				case 29: // (0x1d) iload_3
					bc_list=bc_list.append(new IMIReadLocalVariable(this,bc,ip,3));
					break;
				case 30: // (0x1e) lload_0
					bc_list=bc_list.append(new IMLReadLocalVariable(this,bc,ip,0));
					break;
				case 31: // (0x1f) lload_1
					bc_list=bc_list.append(new IMLReadLocalVariable(this,bc,ip,1));
					break;
				case 32: // (0x20) lload_2
					bc_list=bc_list.append(new IMLReadLocalVariable(this,bc,ip,2));
					break;
				case 33: // (0x21) lload_3
					bc_list=bc_list.append(new IMLReadLocalVariable(this,bc,ip,3));
					break;
				case 34: // (0x22) fload_0
					bc_list=bc_list.append(new IMFReadLocalVariable(this,bc,ip,0));
					break;
				case 35: // (0x23) fload_1
					bc_list=bc_list.append(new IMFReadLocalVariable(this,bc,ip,1));
					break;
				case 36: // (0x24) fload_2
					bc_list=bc_list.append(new IMFReadLocalVariable(this,bc,ip,2));
					break;
				case 37: // (0x25) fload_3
					bc_list=bc_list.append(new IMFReadLocalVariable(this,bc,ip,3));
					break;
				case 38: // (0x26) dload_0
					bc_list=bc_list.append(new IMDReadLocalVariable(this,bc,ip,0));
					break;
				case 39: // (0x27) dload_1
					bc_list=bc_list.append(new IMDReadLocalVariable(this,bc,ip,1));
					break;
				case 40: // (0x28) dload_2
					bc_list=bc_list.append(new IMDReadLocalVariable(this,bc,ip,2));
					break;
				case 41: // (0x29) dload_3
					bc_list=bc_list.append(new IMDReadLocalVariable(this,bc,ip,3));
					break;
				case 42: // (0x2a) aload_0
					bc_list=bc_list.append(new IMAReadLocalVariable(this,bc,ip,0));
					break;
				case 43: // (0x2b) aload_1
					bc_list=bc_list.append(new IMAReadLocalVariable(this,bc,ip,1));
					break;
				case 44: // (0x2c) aload_2
					bc_list=bc_list.append(new IMAReadLocalVariable(this,bc,ip,2));
					break;
				case 45: // (0x2d) aload_3
					bc_list=bc_list.append(new IMAReadLocalVariable(this,bc,ip,3));
					break;
				case 46: // (0x2e) iaload
					bc_list=bc_list.append(new IMIReadArray(this,bc,ip));
					break;
				case 47: // (0x2f) laload
					bc_list=bc_list.append(new IMLReadArray(this,bc,ip));
					break;
				case 48: // (0x30) faload
					bc_list=bc_list.append(new IMFReadArray(this,bc,ip));
					break;
				case 49: // (0x31) daload
					bc_list=bc_list.append(new IMDReadArray(this,bc,ip));
					break;
				case 50: // (0x32) aaload
					bc_list=bc_list.append(new IMAReadArray(this,bc,ip));
					break;
				case 51: // (0x33) baload
					bc_list=bc_list.append(new IMBReadArray(this,bc,ip));
					break;
				case 52: // (0x34) caload
					bc_list=bc_list.append(new IMCReadArray(this,bc,ip));
					break;
				case 53: // (0x35) saload
					bc_list=bc_list.append(new IMSReadArray(this,bc,ip));
					break;
				case 54: // (0x36) istore
					if (wide_prefix)  vi = bcStream.readUnsignedShort();
					else vi = bcStream.readUnsignedByte();
					bc_list=bc_list.append(new IMIStoreLocalVariable(this,bc,ip,vi));
					break;
				case 55: // (0x37) lstore
					if (wide_prefix)  vi = bcStream.readUnsignedShort();
					else vi = bcStream.readUnsignedByte();
					bc_list=bc_list.append(new IMLStoreLocalVariable(this,bc,ip,vi));
					break;
				case 56: // (0x38) fstore
					if (wide_prefix)  vi = bcStream.readUnsignedShort();
					else vi = bcStream.readUnsignedByte();
					bc_list=bc_list.append(new IMFStoreLocalVariable(this,bc,ip,vi));
					break;
				case 57: // (0x39) dstore
					if (wide_prefix)  vi = bcStream.readUnsignedShort();
					else vi = bcStream.readUnsignedByte();
					bc_list=bc_list.append(new IMDStoreLocalVariable(this,bc,ip,vi));
					break;
				case 58: // (0x3a) astore
					if (wide_prefix)  vi = bcStream.readUnsignedShort();
					else vi = bcStream.readUnsignedByte();
					bc_list=bc_list.append(new IMAStoreLocalVariable(this,bc,ip,vi));
					break;
				case 59: // (0x3b) istore_0
					bc_list=bc_list.append(new IMIStoreLocalVariable(this,bc,ip,0));
					break;
				case 60: // (0x3c) istore_1
					bc_list=bc_list.append(new IMIStoreLocalVariable(this,bc,ip,1));
					break;
				case 61: // (0x3d) istore_2
					bc_list=bc_list.append(new IMIStoreLocalVariable(this,bc,ip,2));
					break;
				case 62: // (0x3e) istore_3
					bc_list=bc_list.append(new IMIStoreLocalVariable(this,bc,ip,3));
					break;
				case 63: // (0x3f) lstore_0
					bc_list=bc_list.append(new IMLStoreLocalVariable(this,bc,ip,0));
					break;
				case 64: // (0x40) lstore_1
					bc_list=bc_list.append(new IMLStoreLocalVariable(this,bc,ip,1));
					break;
				case 65: // (0x41) lstore_2
					bc_list=bc_list.append(new IMLStoreLocalVariable(this,bc,ip,2));
					break;
				case 66: // (0x42) lstore_3
					bc_list=bc_list.append(new IMLStoreLocalVariable(this,bc,ip,3));
					break;
				case 67: // (0x43) fstore_0
					bc_list=bc_list.append(new IMFStoreLocalVariable(this,bc,ip,0));
					break;
				case 68: // (0x44) fstore_1
					bc_list=bc_list.append(new IMFStoreLocalVariable(this,bc,ip,1));
					break;
				case 69: // (0x45) fstore_2
					bc_list=bc_list.append(new IMFStoreLocalVariable(this,bc,ip,2));
					break;
				case 70: // (0x46) fstore_3
					bc_list=bc_list.append(new IMFStoreLocalVariable(this,bc,ip,3));
					break;
				case 71: // (0x47) dstore_0
					bc_list=bc_list.append(new IMDStoreLocalVariable(this,bc,ip,0));
					break;
				case 72: // (0x48) dstore_1
					bc_list=bc_list.append(new IMDStoreLocalVariable(this,bc,ip,1));
					break;
				case 73: // (0x49) dstore_2
					bc_list=bc_list.append(new IMDStoreLocalVariable(this,bc,ip,2));
					break;
				case 74: // (0x4a) dstore_3
					bc_list=bc_list.append(new IMDStoreLocalVariable(this,bc,ip,3));
					break;
				case 75: // (0x4b) astore_0
					bc_list=bc_list.append(new IMAStoreLocalVariable(this,bc,ip,0));
					break;
				case 76: // (0x4c) astore_1
					bc_list=bc_list.append(new IMAStoreLocalVariable(this,bc,ip,1));
					break;
				case 77: // (0x4d) astore_2
					bc_list=bc_list.append(new IMAStoreLocalVariable(this,bc,ip,2));
					break;
				case 78: // (0x4e) astore_3
					bc_list=bc_list.append(new IMAStoreLocalVariable(this,bc,ip,3));
					break;
				case 79: // (0x4f) iastore
					bc_list=bc_list.append(new IMIStoreArray(this,bc,ip));
					break;
				case 80: // (0x50) lastore
					bc_list=bc_list.append(new IMLStoreArray(this,bc,ip));
					break;
				case 81: // (0x51) fastore
					bc_list=bc_list.append(new IMFStoreArray(this,bc,ip));
					break;
				case 82: // (0x52) dastore
					bc_list=bc_list.append(new IMDStoreArray(this,bc,ip));
					break;
				case 83: // (0x53) aastore
					bc_list=bc_list.append(new IMAStoreArray(this,bc,ip));
					break;
				case 84: // (0x54) bastore
					bc_list=bc_list.append(new IMBStoreArray(this,bc,ip));
					break;
				case 85: // (0x55) castore
					bc_list=bc_list.append(new IMCStoreArray(this,bc,ip));
					break;
				case 86: // (0x56) sastore
					bc_list=bc_list.append(new IMSStoreArray(this,bc,ip));
					break;
				case 87: // (0x57) pop
					bc_list=bc_list.append(new IMStackOperationPOP(this,bc,ip));
					break;
				case 88: // (0x58) pop2
					bc_list=bc_list.append(new IMStackOperationPOP2(this,bc,ip));
					break;
				case 89: // (0x59) dup
					bc_list=bc_list.append(new IMStackOperationDUP(this,bc,ip));
					break;
				case 90: // (0x5a) dup_x1
					bc_list=bc_list.append(new IMStackOperationDUP_X1(this,bc,ip));
					break;
				case 91: // (0x5b) dup_x2
					bc_list=bc_list.append(new IMStackOperationDUP_X2(this,bc,ip));
					break;
				case 92: // (0x5c) dup2
					bc_list=bc_list.append(new IMStackOperationDUP2(this,bc,ip));
					break;
				case 93: // (0x5d) dup2_x1
					bc_list=bc_list.append(new IMStackOperationDUP2_X1(this,bc,ip));
					break;
				case 94: // (0x5e) dup2_x2
					bc_list=bc_list.append(new IMStackOperationDUP2_X2(this,bc,ip));
					break;
				case 95: // (0x5f) swap
					bc_list=bc_list.append(new IMStackOperationSWAP(this,bc,ip));
					break;
				case 96: // (0x60) iadd
					bc_list=bc_list.append(new IMIAdd(this,bc,ip));
					break;
				case 97: // (0x61) ladd
					bc_list=bc_list.append(new IMLAdd(this,bc,ip));
					break;
				case 98: // (0x62) fadd
					bc_list=bc_list.append(new IMFAdd(this,bc,ip));
					break;
				case 99: // (0x63) dadd
					bc_list=bc_list.append(new IMDAdd(this,bc,ip));
					break;
				case 100: // (0x64) isub
					bc_list=bc_list.append(new IMISub(this,bc,ip));
					break;
				case 101: // (0x65) lsub
					bc_list=bc_list.append(new IMLSub(this,bc,ip));
					break;
				case 102: // (0x66) fsub
					bc_list=bc_list.append(new IMFSub(this,bc,ip));
					break;
				case 103: // (0x67) dsub
					bc_list=bc_list.append(new IMDSub(this,bc,ip));
					break;
				case 104: // (0x68) imul
					bc_list=bc_list.append(new IMIMul(this,bc,ip));
					break;
				case 105: // (0x69) lmul
					bc_list=bc_list.append(new IMLMul(this,bc,ip));
					break;
				case 106: // (0x6a) fmul
					bc_list=bc_list.append(new IMFMul(this,bc,ip));
					break;
				case 107: // (0x6b) dmul
					bc_list=bc_list.append(new IMDMul(this,bc,ip));
					break;
				case 108: // (0x6c) idiv
					bc_list=bc_list.append(new IMIDiv(this,bc,ip));
					break;
				case 109: // (0x6d) ldiv
					bc_list=bc_list.append(new IMLDiv(this,bc,ip));
					break;
				case 110: // (0x6e) fdiv
					bc_list=bc_list.append(new IMFDiv(this,bc,ip));
					break;
				case 111: // (0x6f) ddiv
					bc_list=bc_list.append(new IMDDiv(this,bc,ip));
					break;
				case 112: // (0x70) irem
					bc_list=bc_list.append(new IMIRem(this,bc,ip));
					break;
				case 113: // (0x71) lrem
					bc_list=bc_list.append(new IMLRem(this,bc,ip));
					break;
				case 114: // (0x72) frem
					bc_list=bc_list.append(new IMFRem(this,bc,ip));
					break;
				case 115: // (0x73) drem
					bc_list=bc_list.append(new IMDRem(this,bc,ip));
					break;
				case 116: // (0x74) ineg
					bc_list=bc_list.append(new IMINeg(this,bc,ip));
					break;
				case 117: // (0x75) lneg
					bc_list=bc_list.append(new IMLNeg(this,bc,ip));
					break;
				case 118: // (0x76) fneg
					bc_list=bc_list.append(new IMFNeg(this,bc,ip));
					break;
				case 119: // (0x77) dneg
					bc_list=bc_list.append(new IMDNeg(this,bc,ip));
					break;
				case 120: // (0x78) ishl
					bc_list=bc_list.append(new IMIShiftLeft(this,bc,ip));
					break;
				case 121: // (0x79) lshl
					bc_list=bc_list.append(new IMLShiftLeft(this,bc,ip));
					break;
				case 122: // (0x7a) ishr
					bc_list=bc_list.append(new IMIShiftRight(this,bc,ip));
					break;
				case 123: // (0x7b) lshr
					bc_list=bc_list.append(new IMLShiftRight(this,bc,ip));
					break;
				case 124: // (0x7c) iushr
					bc_list=bc_list.append(new IMIShiftRight2(this,bc,ip));
					break;
				case 125: // (0x7d) lushr
					bc_list=bc_list.append(new IMLShiftRight2(this,bc,ip));
					break;
				case 126: // (0x7e) iand
					bc_list=bc_list.append(new IMIBitAnd(this,bc,ip));
					break;
				case 127: // (0x7f) land
					bc_list=bc_list.append(new IMLBitAnd(this,bc,ip));
					break;
				case 128: // (0x80) ior
					bc_list=bc_list.append(new IMIBitOr(this,bc,ip));
					break;
				case 129: // (0x81) lor
					bc_list=bc_list.append(new IMLBitOr(this,bc,ip));
					break;
				case 130: // (0x82) ixor
					bc_list=bc_list.append(new IMIBitXor(this,bc,ip));
					break;
				case 131: // (0x83) lxor
					bc_list=bc_list.append(new IMLBitXor(this,bc,ip));
					break;
				case 132: // (0x84) iinc
					int value;
					if (wide_prefix) {
						vi = bcStream.readUnsignedShort();
						value = bcStream.readUnsignedShort();
					} else {
						vi = bcStream.readUnsignedByte();
						value = bcStream.readByte();
					}
					bc_list=bc_list.append(new IMIReadLocalVariable(this,21,ip,vi));
					bc_list=bc_list.append(createIMIConstant(value,ip));
					bc_list=bc_list.append(new IMIAdd(this,bc,ip));
					bc_list=bc_list.append(new IMIStoreLocalVariable(this,54,ip,vi));
					break;
				case 133: // (0x85) i2l
					bc_list=bc_list.append(new IMCastI2L(this,bc,ip));
					break;
				case 134: // (0x86) i2f
					bc_list=bc_list.append(new IMCastI2F(this,bc,ip));
					break;
				case 135: // (0x87) i2d
					bc_list=bc_list.append(new IMCastI2D(this,bc,ip));
					break;
				case 136: // (0x88) l2i
					bc_list=bc_list.append(new IMCastL2I(this,bc,ip));
					break;
				case 137: // (0x89) l2f
					bc_list=bc_list.append(new IMCastL2F(this,bc,ip));
					break;
				case 138: // (0x8a) l2d
					bc_list=bc_list.append(new IMCastL2D(this,bc,ip));
					break;
				case 139: // (0x8b) f2i
					bc_list=bc_list.append(new IMCastF2I(this,bc,ip));
					break;
				case 140: // (0x8c) f2l
					bc_list=bc_list.append(new IMCastF2L(this,bc,ip));
					break;
				case 141: // (0x8d) f2d
					bc_list=bc_list.append(new IMCastF2D(this,bc,ip));
					break;
				case 142: // (0x8e) d2i
					bc_list=bc_list.append(new IMCastD2I(this,bc,ip));
					break;
				case 143: // (0x8f) d2l
					bc_list=bc_list.append(new IMCastD2L(this,bc,ip));
					break;
				case 144: // (0x90) d2f
					bc_list=bc_list.append(new IMCastD2F(this,bc,ip));
					break;
				case 145: // (0x91) i2b
					bc_list=bc_list.append(new IMCastI2B(this,bc,ip));
					break;
				case 146: // (0x92) i2c
					bc_list=bc_list.append(new IMCastI2C(this,bc,ip));
					break;
				case 147: // (0x93) i2s
					bc_list=bc_list.append(new IMCastI2S(this,bc,ip));
					break;
				case 148: // (0x94) lcmp
					bc_list=bc_list.append(new IMLCompare(this,bc,ip));
					break;
					/*
					   Notes The fcmpg and fcmpl instructions differ only in their treatment of a comparison
					   involving NaN. NaN is unordered, so any float comparison fails if either or both
					   of its operands are NaN. With both fcmpg and fcmpl available, any float comparison
					   may be compiled to push the same result onto the operand stack whether the comparison
					   fails on non-NaN values or fails because it encountered a NaN. For more information,
					   see Section 7.5, "More Control Examples."
					 */
				case 149: // (0x95) fcmpl
					bc_list=bc_list.append(new IMFLCompare(this,bc,ip));
					break;
				case 150: // (0x96) fcmpg
					bc_list=bc_list.append(new IMFGCompare(this,bc,ip));
					break;
				case 151: // (0x97) dcmpl
					bc_list=bc_list.append(new IMDLCompare(this,bc,ip));
					break;
				case 152: // (0x98) dcmpg
					bc_list=bc_list.append(new IMDGCompare(this,bc,ip));
					break;
				case 153: // (0x99) ifeq
					label=getBasicBlock(ip,bcStream.readShort());
					bc_list=bc_list.append(new IMEQConditionalBranch(this,bc,ip,label,getBasicBlock(ip,3)));
					break;
				case 154: // (0x9a) ifne
					label=getBasicBlock(ip,bcStream.readShort());
					bc_list=bc_list.append(new IMNEConditionalBranch(this,bc,ip,label,getBasicBlock(ip,3)));
					break;
				case 155: // (0x9b) iflt
					label=getBasicBlock(ip,bcStream.readShort());
					bc_list=bc_list.append(new IMLTConditionalBranch(this,bc,ip,label,getBasicBlock(ip,3)));
					break;
				case 156: // (0x9c) ifge
					label=getBasicBlock(ip,bcStream.readShort());
					bc_list=bc_list.append(new IMGEConditionalBranch(this,bc,ip,label,getBasicBlock(ip,3)));
					break;
				case 157: // (0x9d) ifgt
					label=getBasicBlock(ip,bcStream.readShort());
					bc_list=bc_list.append(new IMGTConditionalBranch(this,bc,ip,label,getBasicBlock(ip,3)));
					break;
				case 158: // (0x9e) ifle
					label=getBasicBlock(ip,bcStream.readShort());
					bc_list=bc_list.append(new IMLEConditionalBranch(this,bc,ip,label,getBasicBlock(ip,3)));
					break;
				case 159: // (0x9f) if_icmpeq
					label=getBasicBlock(ip,bcStream.readShort());
					bc_list=bc_list.append(new IMEQConditionalBranch(this,bc,ip,label,getBasicBlock(ip,3)));
					break;
				case 160: // (0xa0) if_icmpne
					label=getBasicBlock(ip,bcStream.readShort());
					bc_list=bc_list.append(new IMNEConditionalBranch(this,bc,ip,label,getBasicBlock(ip,3)));
					break;
				case 161: // (0xa1) if_icmplt
					label=getBasicBlock(ip,bcStream.readShort());
					bc_list=bc_list.append(new IMLTConditionalBranch(this,bc,ip,label,getBasicBlock(ip,3)));
					break;
				case 162: // (0xa2) if_icmpge
					label=getBasicBlock(ip,bcStream.readShort());
					bc_list=bc_list.append(new IMGEConditionalBranch(this,bc,ip,label,getBasicBlock(ip,3)));
					break;
				case 163: // (0xa3) if_icmpgt
					label=getBasicBlock(ip,bcStream.readShort());
					bc_list=bc_list.append(new IMGTConditionalBranch(this,bc,ip,label,getBasicBlock(ip,3)));
					break;
				case 164: // (0xa4) if_icmple
					label=getBasicBlock(ip,bcStream.readShort());
					bc_list=bc_list.append(new IMLEConditionalBranch(this,bc,ip,label,getBasicBlock(ip,3)));
					break;
				case 165: // (0xa5) if_acmpeq
					label=getBasicBlock(ip,bcStream.readShort());
					bc_list=bc_list.append(new IMEQConditionalBranch(this,bc,ip,label,getBasicBlock(ip,3)));
					break;
				case 166: // (0xa6) if_acmpne
					label=getBasicBlock(ip,bcStream.readShort());
					bc_list=bc_list.append(new IMNEConditionalBranch(this,bc,ip,label,getBasicBlock(ip,3)));
					break;
				case 167: // (0xa7) goto
					label=getBasicBlock(ip,bcStream.readShort());
					bc_list=bc_list.append(new IMGoto(this,bc,ip,label));
					break;
				case 168: // (0xa8) jsr
					label=getBasicBlock(ip,bcStream.readShort());
					bc_list=bc_list.append(new IMCall(this,bc,ip,label,getBasicBlock(ip,3)));
					jsr_calls.add(bc_list);
					break;
				case 169: // (0xa9) ret
					if (wide_prefix) vi=bcStream.readUnsignedShort();
					else vi=bcStream.readUnsignedByte();
					bc_list=bc_list.append(new IMReturnSubroutine(this,bc,ip,vi));
					break;
				case 170: // (0xaa) tableswitch
					// skip padding zeroes
					while (bcStream.getCurrentPosition() % 4 != 0) bcStream.readByte();
					offset = bcStream.readInt();
					int low    = bcStream.readInt();
					int high   = bcStream.readInt();
					int tsize = high-low;
					IMBasicBlock jmptable[] = new IMBasicBlock[tsize+2];
					jmptable[0] = getBasicBlock(ip,offset); 
					for (int i=1;i<tsize+2;i++) jmptable[i]=getBasicBlock(ip,bcStream.readInt());
					bc_list=bc_list.append(new IMTableSwitch(this,bc,ip,low,high,jmptable));
					break;
				case 171: // (0xab) lookupswitch
					// skip padding zeroes
					while (bcStream.getCurrentPosition() % 4 != 0) bcStream.readByte();
					offset = bcStream.readInt();
					int len    = bcStream.readInt();
					int values[] = new int[len];
					IMBasicBlock addrs[] = new IMBasicBlock[len+1];
					addrs[0] = getBasicBlock(ip,offset);
					for (int i=0;i<len;i++) {
						values[i] = bcStream.readInt();
						addrs[i+1] = getBasicBlock(ip,bcStream.readInt());
					}
					bc_list=bc_list.append(new IMLookupSwitch(this,bc,ip,values,addrs));
					break;
				case 172: // (0xac) ireturn
					bc_list=bc_list.append(storeLocal(ip, getReturnValue()));
					bc_list=bc_list.append(new IMGoto(this,167,ip,getEndBlock()));
					break;
				case 173: // (0xad) lreturn
					bc_list=bc_list.append(storeLocal(ip,getReturnValue()));
					bc_list=bc_list.append(new IMGoto(this,167,ip,getEndBlock()));
					break;
				case 174: // (0xae) freturn
					bc_list=bc_list.append(storeLocal(ip,getReturnValue()));
					bc_list=bc_list.append(new IMGoto(this,167,ip,getEndBlock()));
					break;
				case 175: // (0xaf) dreturn
					bc_list=bc_list.append(storeLocal(ip,getReturnValue()));
					bc_list=bc_list.append(new IMGoto(this,167,ip,getEndBlock()));
					break;
				case 176: // (0xb0) areturn
					bc_list=bc_list.append(storeLocal(ip,getReturnValue()));
					bc_list=bc_list.append(new IMGoto(this,167,ip,getEndBlock()));
					break;
				case 177: // (0xb1) return
					bc_list=bc_list.append(new IMGoto(this,167,ip,getEndBlock()));
					break;
				case 178: // (0xb2) getstatic
					{
						FieldRefCPEntry cpEntry=cPool.fieldRefEntryAt(bcStream.readUnsignedShort());
						bc_list=bc_list.append(new IMGetStatic(this,bc,ip,cpEntry));
						break;
					} 
				case 179: // (0xb3) putstatic
					{
						FieldRefCPEntry cpEntry=cPool.fieldRefEntryAt(bcStream.readUnsignedShort());
						bc_list=bc_list.append(new IMPutStatic(this,bc,ip,cpEntry));
						break;
					}
				case 180: // (0xb4) getfield
					{
						FieldRefCPEntry cpEntry=cPool.fieldRefEntryAt(bcStream.readUnsignedShort());
						bc_list=bc_list.append(new IMGetField(this,bc,ip,cpEntry));
						break;
					}
				case 181: // (0xb5) putfield
					{
						FieldRefCPEntry cpEntry=cPool.fieldRefEntryAt(bcStream.readUnsignedShort());
						bc_list=bc_list.append(new IMPutField(this,bc,ip,cpEntry));
						break;
					}
				case 182: // (0xb6) invokevirtual
					{
						MethodRefCPEntry cpEntry=cPool.methodRefEntryAt(bcStream.readUnsignedShort());
						bc_list=bc_list.append(new IMInvokeVirtual(this,bc,ip,cpEntry));
						break;
					}
				case 183: // (0xb7) invokespecial
					{
						MethodRefCPEntry cpEntry=cPool.methodRefEntryAt(bcStream.readUnsignedShort());
						bc_list=bc_list.append(new IMInvokeSpecial(this,bc,ip,cpEntry));
						break;
					}
				case 184: // (0xb8) invokestatic
					{
						MethodRefCPEntry cpEntry
							= cPool.methodRefEntryAt(bcStream.readUnsignedShort());
						bc_list=bc_list.append(new IMInvokeStatic(this,bc,ip,cpEntry));
						break;
					}
				case 185: // (0xb9) invokeinterface
					{
						InterfaceMethodRefCPEntry cpEntry
							= cPool.InterfaceMethodRefEntryAt(bcStream.readUnsignedShort());
						bcStream.readByte(); // read useless arg length
						bcStream.readByte(); // read useless padding zero
						bc_list=bc_list.append(new IMInvokeInterface(this,bc,ip,cpEntry));
						break;
					}
				case 186: // (0xba) xxxunusedxxx
					break;
				case 187: // (0xbb) new
					{
						ClassCPEntry cpEntry 
							= cPool.classEntryAt(bcStream.readUnsignedShort());
						bc_list=bc_list.append(new IMNew(this,bc,ip,cpEntry));
						break;
					}
				case 188: // (0xbc) newarray
					{
						int type = bcStream.readByte();
						bc_list=bc_list.append(new IMNewArray(this,bc,ip,aType[type]));
						break;
					}
				case 189: // (0xbd) anewarray
					{
						ClassCPEntry cpEntry 
							= cPool.classEntryAt(bcStream.readUnsignedShort());
						bc_list=bc_list.append(new IMNewObjArray(this,bc,ip,cpEntry));
						break;
					}
				case 190: // (0xbe) arraylength
					bc_list=bc_list.append(new IMArrayLength(this,bc,ip));
					break;
				case 191: // (0xbf) athrow
					bc_list=bc_list.append(new IMThrow(this,bc,ip));
					break;
				case 192: // (0xc0) checkcast
					{
						ClassCPEntry cpEntry 
							= cPool.classEntryAt(bcStream.readUnsignedShort());
						bc_list=bc_list.append(new IMCheckCast(this,bc,ip,cpEntry));
						break;
					}
				case 193: // (0xc1) instanceof
					{
						ClassCPEntry cpEntry 
							= cPool.classEntryAt(bcStream.readUnsignedShort());
						bc_list=bc_list.append(new IMInstanceOf(this,bc,ip,cpEntry));
						break;
					}
				case 194: // (0xc2) monitorenter
					bc_list=bc_list.append(new IMMonitor(this,bc,ip));
					break;
				case 195: // (0xc3) monitorexit
					bc_list=bc_list.append(new IMMonitor(this,bc,ip));
					break;
				case 196: // (0xc4) wide
					wide_prefix = true;
					continue;
				case 197: // (0xc5) multianewarray
					{
						ClassCPEntry cpEntry 
							= cPool.classEntryAt(bcStream.readUnsignedShort());
						int dim = bcStream.readUnsignedByte();
						bc_list=bc_list.append(new IMNewMultiArray(this,bc,ip,cpEntry,dim));
					}
					break;
				case 198: // (0xc6) ifnull
					label=getBasicBlock(ip,bcStream.readShort());
					bc_list=bc_list.append(new IMEQConditionalBranch(this,bc,ip,label,getBasicBlock(ip,3)));
					break;
				case 199: // (0xc7) ifnonnull
					label=getBasicBlock(ip,bcStream.readShort());
					bc_list=bc_list.append(new IMNEConditionalBranch(this,bc,ip,label,getBasicBlock(ip,3)));
					break;
				case 200: // (0xc8) goto_w
					label=getBasicBlock(ip,bcStream.readInt());
					bc_list=bc_list.append(new IMGoto(this,bc,ip,label));
					break;
				case 201: // (0xc9) jsr_w
					label=getBasicBlock(ip,bcStream.readInt());
					bc_list=bc_list.append(new IMCall(this,bc,ip,label,getBasicBlock(ip,5)));
					jsr_calls.add(bc_list);
					break;
				case 202: // (0xca) breakpoint
					/* _quick opcodes: */
				case 203: // (0xcb) ldc_quick
				case 204: // (0xcc) ldc_w_quick
				case 205: // (0xcd) ldc2_w_quick
				case 206: // (0xce) getfield_quick
				case 207: // (0xcf) putfield_quick
				case 208: // (0xd0) getfield2_quick
				case 209: // (0xd1) putfield2_quick
				case 210: // (0xd2) getstatic_quick
				case 211: // (0xd3) putstatic_quick
				case 212: // (0xd4) getstatic2_quick
				case 213: // (0xd5) putstatic2_quick
				case 214: // (0xd6) invokevirtual_quick
				case 215: // (0xd7) invokenonvirtual_quick
				case 216: // (0xd8) invokesuper_quick
				case 217: // (0xd9) invokestatic_quick
				case 218: // (0xda) invokeinterface_quick
				case 219: // (0xdb) invokevirtualobject_quick
				case 221: // (0xdd) new_quick
				case 222: // (0xde) anewarray_quick
				case 223: // (0xdf) multianewarray_quick
				case 224: // (0xe0) checkcast_quick
				case 225: // (0xe1) instanceof_quick
				case 226: // (0xe2) invokevirtual_quick_w
				case 227: // (0xe3) getfield_quick_w
				case 228: // (0xe4) putfield_quick_w
					/* Reserved opcodes */
				case 254: // (0xfe) impdep1
				case 255: // (0xff) impdep2
					throw new CompileException ("not implemeted byte code! ("+bc+")"); 
			}

			wide_prefix = false;
		}
	}

	final public IMSlot getReturnValue() {
		return return_slot;
	}

	final public IMBasicBlock getEndBlock() {
		return endBlock;
	}

	final public void dumpRawBC() {
		dumpRawBC(source);
	}

	final public void dumpRawBC(MethodData src) {
		BytecodeInputStream bcStream = new BytecodeInputStream(src.getBytecode());
		int i=0;
		while (bcStream.hasMore()) {
			if (i==0) System.err.println();
			int bc = bcStream.readUnsignedByte();
			System.err.print("0x");
			if (bc<16) System.err.print("0");
			System.err.print(Integer.toHexString(bc));
			System.err.print(" ");
			if (bc<10) System.err.print(" ");
			if (bc<100) System.err.print(" ");
			System.err.print(bc);
			System.err.print(":");
			i++;
			if (i>=16) i=0;
		}
		System.err.println();
	}

	protected void parseBC(MethodData source) throws CompileException {

		label_index = new IntegerHashtable();
		label_list  = null;

		BytecodeInputStream bcStream = new BytecodeInputStream(source.getBytecode()); 

		// ==========================================
		// 1. PASS
		// ==========================================
		// in the first pass we will translate the 
		// bytecode into imcode objects, and create
		// the basic blocks 
		// ==========================================

		IMBasicBlock   basicBlock = getBasicBlock(bcStream.getCurrentPosition(),0);
		endBlock = getBasicBlock(bcStream.getCurrentPosition(), bcStream.getNumBytes()+1);
		if (getBasicReturnType()==BCBasicDatatype.VOID) {
			endBlock.addBC(new IMEpilog(this, bcStream.getNumBytes()+1));
		} else {
			return_slot = frame.createNewVariable(getBasicReturnType());
			endBlock.addBC(new IMEpilog(this, bcStream.getNumBytes()+1));
		}
		endBlock.done=true;

		ExceptionHandlerData[] handler = source.getExceptionHandlers();
		catch_list = null;
		if (handler!=null && handler.length>0) {
			catch_list = new IMExceptionHandler[handler.length];
			for (int i=0;i<handler.length;i++) {
				getBasicBlock(handler[i].getStartBCIndex(),0);
				getBasicBlock(handler[i].getEndBCIndex(),0);
				catch_list[i] = new IMExceptionHandler(this, handler[i]);
				catch_list[i].setHandler(getBasicBlock(handler[i].getHandlerBCIndex(),0));
			}
		} 

		IMNode bc_list = new IMListHead();
		Vector jsr_calls = new Vector();
		processBCStream(bcStream, bc_list, jsr_calls);


		// ===============================================
		// 2. Pass
		// ===============================================
		// merge basic block list and byte code list
		// ===============================================

		IMBasicBlock bb = label_list;
		int bbpos = bb.getBCPosition();
		while (bc_list!=null && bc_list.next()!=null) {
			IMNode curr = bc_list.next();

			if (curr.getBCPosition()>=bbpos) { 
				bb.addBC(curr);

				bc_list.setEndOfBasicBlock();
				bb=(IMBasicBlock)bb.next();
				bbpos = bb.getBCPosition();
				// seek end of basic block
				while (curr.next()!=null && curr.next().getBCPosition()<bbpos) { curr = curr.next(); };
			}

			bc_list = curr;
		}
		if (bc_list!=null) bc_list.setEndOfBasicBlock();
		bc_list = null;

		if (opts.hasDumpBC()) {
			try { dumpBC(); } catch (IOException ex) { throw new RuntimeException(); }
		}

		// ===============================================
		// 3. Pass
		// ===============================================
		// now we will process all basic blocks in a deap
		// first order of execution 
		// ===============================================

		VirtualOperantenStack oprStack = new VirtualOperantenStack(this,source.getNumStackSlots());
		JumpStack stack = new JumpStack();

		if (opts.hasOption("exceptions")) {
			for (int i=0;catch_list!=null && i<catch_list.length;i++) {
				IMBasicBlock exp_bb = catch_list[i].getHandler();
				exp_bb.setExceptionHandler(catch_list[i]);
				stack.push(exp_bb);
			}
		}

		if (dbg_process_vstack) 
			opts.setCurrentClass(getClassName(),0,"vstack");

		stack.push(label_list);
		while ((bb=stack.pop())!=null) {
			if (bb.done) continue;
			currentBB = bb;	
			stack.push(bb.processBasicBlock(oprStack));
		}

		if (dbg_process_vstack) 
			opts.setCurrentClass("xxx",0,null);

		currentBB = null;
		stack_processed = true;

		/* Remove dead code which is not processed yet because it was not 
		 * reached. This could happen if we have disabled the exception
		 * handler or if the java compiler creates brain dead code. */
		if (!opts.hasOption("no_bb_cleanup")) {
			IMBasicBlock pb=(IMBasicBlock)label_list;
			while (pb!=null&&pb.next()!=null) {
				IMBasicBlock cb = (IMBasicBlock)pb.next();
				if (!cb.done) { 
					IMNode nb=cb;
					while (nb.next()!=null && !(nb.next() instanceof IMBasicBlock)) nb = nb.next();
					pb.append(nb.next());
				} else {
					pb=cb;
				}
			}
		}
	}

	final public IMCallerEntry getFoldedCallerInfo() throws CompileException {
		return IMCallerEntry.getFoldedCallerEntry(called_from);
	}

	public void transformIntoSSA() throws CompileException {

		if (domtree==null) computeDomTree();

		/*
		 * Dominance frontiers capture the precise places at which we need phi functions:
		 * if the node A defines a certain variable, then that definition and that definition
		 * alone will reach every node A dominates. Only when we leave these nodes and enter
		 * the dominance frontier must we account for other flows bringing in other definitions
		 * of the same variable. Moreover, no other phi functions are needed in the control flow
		 * graph to deal with A's definitions, and we can do with no less.
		 * (Wikipedia)
		 */

		if (opts.getJoinPoints().ignoreMethodBody(clazz,this)) return; 

		IntegerHashtable A_orig = new IntegerHashtable();
		Hashtable defsites = new Hashtable(); 
		IntegerHashtable childsOf = new IntegerHashtable();

		IMDefVisitor visitor=new IMDefVisitor(this, defsites, A_orig);
		for (IMBasicBlock bb=label_list;bb!=null;bb=(IMBasicBlock)bb.next()) {
			currentBB = bb;
			bb.visitNodes(visitor);
			IMBasicBlock dbb = domtree.getDominatorOf(bb);
			if (dbb!=null) {
				int n = dbb.getDFNum();
				Vector childs = (Vector)childsOf.get(n);
				if (childs==null) {
					childs=new Vector();
					childsOf.put(n, childs);
				}
				childs.add(bb);
			}
			currentBB = null;
		}	

		/* first place phi-functions */
		placePhiFunctions(defsites, A_orig);

		/* now rename variables */

		Hashtable stacks = new Hashtable();
		Enumeration all_vars = defsites.keys();
		while (all_vars.hasMoreElements()) {
			IMSlot var = (IMSlot)all_vars.nextElement();
			Stack stack = new Stack(16);
			// undef. stack.push();
			var.clear_def_use_chain();
			stacks.put(var, stack);
		}

		/* define all method arguments */
		IMSlot args[] = frame.getMethodArguments();
		for (int i=0;i<args.length;i++) {
			IMSlot avar = args[i];
			Stack stack = new Stack(16);
			avar.clear_def_use_chain();
			avar.define(label_list, null);
			stack.push(avar);
			stacks.put(avar, stack);
		}

		renameVars(1, childsOf, stacks);

		/* DONE!!! */
		/* now we can do all the optimization stuff */
	}

	private void renameVars(int dfnum, IntegerHashtable childsOf,  Hashtable stacks) throws CompileException {
		IMBasicBlock bb = domtree.getIMBasicBlock(dfnum);

		Enumeration es = stacks.elements();
		while (es.hasMoreElements()) { ((Stack)es.nextElement()).mark(); }

		IMRenameVisitor visitor = new IMRenameVisitor(this, bb, stacks);
		bb.visitNodes(visitor);

		IMBasicBlockList succ = bb.getSucc();
		for (int i=0;i<succ.length();i++) {
			IMBasicBlock sb = succ.at(i);
			IMPhiUpdateVisitor uvisitor = new IMPhiUpdateVisitor(this, sb, bb, stacks);
			sb.visitNodes(uvisitor);
		}

		Vector childs = (Vector)childsOf.get(dfnum);
		if (childs!=null) {
			Enumeration ce = childs.elements();
			while (ce.hasMoreElements()) {
				IMBasicBlock nb = (IMBasicBlock)ce.nextElement();
				renameVars(nb.getDFNum(), childsOf, stacks);
			}
		}

		Enumeration es2 = stacks.elements();
		while (es2.hasMoreElements()) { ((Stack)es2.nextElement()).reset(); }
	}

	/**
	 * defsites{var}{n} hash of hashes with all definition sites of variable var
	 * A_orig{n}{var}   hash of hashes with all variables defined in node n 
	 */
	private void placePhiFunctions(Hashtable defsites, IntegerHashtable A_orig) throws CompileException {
		Hashtable A_phi = new Hashtable();
		Enumeration all_vars = defsites.keys();

		// for each variable var do
		while (all_vars.hasMoreElements()) {
			IMSlot var = (IMSlot)all_vars.nextElement();
			IntegerHashtable defs = (IntegerHashtable)defsites.get(var);
			while (!defs.isEmpty()) {
				// worklist = definition sites of var
				int[] worklist = defs.keys();
				for (int i=0; i<worklist.length;i++) {
					int n = worklist[i];
					defs.remove(n);

					int df[] = domtree.getDominaceFrontierIDs(n);
					for (int j=0;j<df.length;j++) {
						int y = df[j];

						IntegerHashtable Avar_phi = (IntegerHashtable)A_phi.get(var);
						if (Avar_phi==null) {
							Avar_phi = new IntegerHashtable();
							A_phi.put(var, Avar_phi);
						}

						if (Avar_phi.get(y)==null) {
							IMBasicBlock df_b = domtree.getIMBasicBlock(y);



							/* insert phi function in basic block y (domainace frontier) */
							IMPhi phi = new IMPhi(this, df_b, var, var.getType());
							IMStoreLocalVariable store = createStoreVariable(var, phi, -1);
							df_b.placePhi(store);
							Avar_phi.put(y, df_b);

							/* add changed basic block to the work list */
							Hashtable A_orig_y=null;
							if ((A_orig_y=(Hashtable)A_orig.get(y))==null || A_orig_y.get(var)==null) {
								defs.put(y, df_b);
							}
						}
					}
				}
			}
		}
	}

	private void transformBackFromSSA() throws CompileException {
		for (IMBasicBlock bb=label_list;bb!=null;bb=(IMBasicBlock)bb.next()) {
			currentBB = bb;
			IMRemoveSSAVisitor visitor = new IMRemoveSSAVisitor(this,bb);
			bb.visitNodes(visitor);
		}	
		currentBB = null;
	}

	private IntegerHashtable ret_index = null;
	final public int registerReturnAddr(IMBasicBlock retbb) {
		if (ret_index==null) ret_index = new IntegerHashtable();
		int index = retbb.getBCPosition();
		ret_index.put(index, retbb);
		return index;
	}

	final public IntegerHashtable getReturnTargets() {
		return ret_index;
	}

	private Hashtable var_alias=null;
	public void registerAlias(IMBasicBlock bb, IMSlot var, IMNode value) throws CompileException {
		if (value.isVolatile()) return;
		if (var_alias==null) var_alias = new Hashtable();

		AliasEntry alias=null;
		if ((alias=(AliasEntry)var_alias.get(value.toMemAlias()))==null) {
			alias = new AliasEntry(bb,var,value);
			var_alias.put(value.toMemAlias(), alias);
			return;
		}

		if (alias.bb!=bb) {
			alias = new AliasEntry(bb,var,value);
			var_alias.put(value.toMemAlias(), alias);
		}
	}

	public void removeAlias(IMBasicBlock bb, IMNode value) throws CompileException {
		if (var_alias==null) return;
		var_alias.remove(value.toMemAlias());
	}

	public IMSlot lookupAlias(IMBasicBlock bb, IMNode value) throws CompileException {
		if (var_alias==null) return null;

		AliasEntry alias = (AliasEntry)var_alias.get(value.toMemAlias()); 

		if (alias==null) return null;

		if (alias.value==value) return null;

		if (alias.bb!=bb) {
			if (opts.hasOption("ssa_alias_less")) {
				IMBasicBlockList pb = bb.getPred();
				if (pb.length()==1 && pb.at(0)==alias.bb) {
					alias.bb = bb;
					return alias.var; 
				}
			} else {
				DominatorTree domtree = getDomTree();
				if (domtree.dom(alias.bb,bb)) return alias.var; 
			}
			return null;
		}

		return alias.var;
	}


	/**
	 * Scan all basic blocks and perform constant folding if possible.
	 */

	private boolean another_ssa_run;
	private boolean global_ssa_run =true;
	private boolean[] arg_escape;

	public void doAnotherSSARun() {
		another_ssa_run = true;
	}

	private int arg_cycle_l1 = -1;
	private int arg_cycle_l2 = -1;
	public boolean doArgumentEscape(int arg) throws CompileException {

		IMSlot[] args = frame.getOriginalArguments();
		if (args[arg].getType()!=BCBasicDatatype.REFERENCE) 
			opts.warn(getAlias()+" args["+arg+"]=>"+args[arg]+" is not a reference!");

		if (opts.hasOption("_X_ssa_less_astack")) return true;

		/* cycles are evil! */
		if (arg_cycle_l1!=-1) {
			/* if it is the same argument we won't escape for now! */
			if (arg_cycle_l1==arg) return false;

			if (arg_cycle_l2!=-1) {
				if (arg_cycle_l2==arg) return false;
			} else {
				arg_cycle_l2=arg;
				boolean arg_tmp = args[arg].doEscape(true);
				arg_cycle_l2=-1;
				return arg_tmp;	
			}

			opts.warn("Can`t resolve cycle in escape analysis!");
			return true;
		}

		if (arg_escape==null) {
			/* no code no escape ;-) */
			if (hasNoCode) return false;

			/* may be we have some hints */
			if (Weavelet.TRUE==opts.getJoinPoints().checkAttribut(this,Weavelet.ATTR_NOESCAPE)) return false; 

			/* we are pessimistic if we cannot analyse the method body */ 
			if (opts.getJoinPoints().ignoreMethodBody(clazz,this)) return true; 
			
			/* do the ssa optimization and escape analysis for the method.
			 * the result is cached in arg_escape[...] */
			arg_cycle_l1 = arg;
			constant_folding();
			arg_cycle_l1 = -1;

			if (arg_escape==null) {
				opts.warn("Can`t do escape analysis on "+getClassName()+"."+getMethodName());
				return true;
			}
		}

		return arg_escape[arg];
	}

	private void doEscapeAnalyses() throws CompileException {
		/* now we do the escape analyses */
		IMVisitor v1 = new IMEscapeVisitor(opts,IMEscapeVisitor.PREPARE);
		for (IMBasicBlock bb=label_list;bb!=null;bb=(IMBasicBlock)bb.next()) {
			currentBB = bb;
			bb.visitNodes(v1);
		}	
		IMVisitor v2 = new IMEscapeVisitor(opts,IMEscapeVisitor.ANALYSE);
		for (IMBasicBlock bb=label_list;bb!=null;bb=(IMBasicBlock)bb.next()) {
			currentBB = bb;
			bb.visitNodes(v2);
		}	
		currentBB = null;

		/*
		 * Analyse if method arguments escape and save the
		 * result in arg_escape[...]
		 */
		IMSlot args[] = frame.getOriginalArguments();
		arg_escape = new boolean[args.length];
		for (int i=0;i<args.length;i++) arg_escape[i] = true; 
		if (!opts.hasOption("_X_ssa_less_astack")) {
			for (int i=0;i<args.length;i++) {
				opts.vverbose("## test escape "+getAlias()+" "+args[i]);
				if (args[i].getType()==BCBasicDatatype.REFERENCE) {
					arg_escape[i] = args[i].doEscape(true);
				}
			}
		}
	}

	public boolean constant_folding() throws CompileException  {

		if (hasNoCode) return false;

		try {
			if (opts.getJoinPoints().ignoreMethodBody(clazz,this)) return false; 

			opts.vverbose("### "+getAlias());
			IMNode pre_block = frame.constant_parameter_propagation();
			label_list.insertBCBefor(pre_block);

			if (opts.hasOption("ssa") && global_ssa_run) {
				global_ssa_run = false;

				transformIntoSSA();

				/*
				 * optimizations we will do now are
				 *
				 * + copy and constant propagation
				 * + constant folding
				 * - constant conditions (in the future)
				 * o alias propagation (-X:ssa_alias_prop)
				 * + delete unreachable code
				 * o escape analysis and stack allocation (-X:ssa_astack)
				 *
				 */
				if (!opts.hasOption("ssa_dbg")) {
					IMUpdateUsesVisitor update_visitor = new IMUpdateUsesVisitor(this);
					another_ssa_run = true;
					// repeat this loop until no changes occur
					while (another_ssa_run) {
						another_ssa_run = false;

						// optimize
						for (IMBasicBlock bb=label_list;bb!=null;) {
							currentBB=bb;
							bb=(IMBasicBlock)bb.ssa_optimize();
						}

						// the uses are buggy
						update_visitor.begin();
						for (IMBasicBlock bb=label_list;bb!=null;bb=(IMBasicBlock)bb.next()) {
							currentBB = bb;
							bb.visitNodes(update_visitor);
						}
						update_visitor.end();

						// collect dead code
						IMBasicBlock pb=null;
						for (IMBasicBlock bb=label_list;bb!=null;bb=(IMBasicBlock)pb.next()) {
							currentBB=bb;
							pb=(IMBasicBlock)bb.removeDeadBasicBlocks(pb);
						}
						currentBB=null;
					}

				}

				if (opts.hasOption("ssa_astack")) doEscapeAnalyses();

				transformBackFromSSA();
				frame.opt_vars();

				return true;
			} else {
				/* minimal constant folding if no ssa optimization is active or if we have allready
				 * done the ssa optimization. */
				for (IMBasicBlock bb=label_list;bb!=null;) {
					currentBB=bb;
					bb=(IMBasicBlock)bb.constant_folding();
				}
				currentBB=null;
			}

		} catch (Error ex) {
			System.err.println("======================================================");
			System.err.println("Error in "+clazz.getSourceFile());
			System.err.println(getMethodNameAndType());
			if (currentBB!=null)  System.err.println(currentBB.toLabel());
			try { writeCFG(); System.err.println("wrote "+getAlias()+".dot"); } catch (Exception iex) {};
			System.err.println("======================================================");
			throw ex;
		} catch (CompileException ex) {
			System.err.println("======================================================");
			System.err.println("CompileException in "+clazz.getSourceFile());
			System.err.println(getMethodNameAndType());
			if (currentBB!=null)  System.err.println(currentBB.toLabel());
			try { writeCFG(); System.err.println("wrote "+getAlias()+".dot"); } catch (Exception iex) {};
			System.err.println("======================================================");
			throw ex;
		}

		return false;
	}
	
	public void analyseCallGraph(IMCallGraphVisitor visitor) throws CompileException {

		if (hasNoCode) return;

		if (opts.getJoinPoints().ignoreMethodBody(clazz,this)) return; 

		try {
			for (IMBasicBlock bb=label_list;bb!=null;bb=(IMBasicBlock)bb.next()) {
				currentBB = bb;
				bb.visitNodes(visitor);
				currentBB = null;
			}	

			analysed=true;
		} catch (CompileException ex) {
			System.err.println("======================================================");
			System.err.println("CompileException in "+clazz.getSourceFile());
			System.err.println(getMethodNameAndType());
			if (currentBB!=null)  System.err.println(currentBB.toLabel());
			System.err.println("======================================================");
			throw ex;
		} catch (RuntimeException ex) {
			System.err.println("======================================================");
			System.err.println("RuntimeException in "+clazz.getSourceFile());
			System.err.println(getMethodNameAndType());
			if (currentBB!=null)  System.err.println(currentBB.toLabel());
			System.err.println("======================================================");
			throw ex;
		}
	}


	/**
	 * Scan all basic blocks and inline method calls if it makes
	 * sense.
	 */
	final public void inlineMethodCalls() throws CompileException {

		opts.vverbose("&& inlineMethodCalls in "+getAlias());

		if (allready_inlined) return;
		allready_inlined = true;

		if (hasNoCode) return;

		if (opts.getJoinPoints().ignoreMethodBody(clazz,this)) return; 
	
		for (IMBasicBlock bb=label_list;bb!=null;) {
			currentBB=bb;
			bb=bb.inlineMethodCalls();
		}
		
		if (!opts.hasOption("nomelt")) {
			IMBasicBlock bb=label_list;
			while (bb!=null) {
				currentBB=bb;
				bb = bb.melt();
			}
		}

		currentBB=null;
	}

	final public int getCosts() throws CompileException {
		int costs = 0;
		for (IMBasicBlock bb=label_list;bb!=null;bb=(IMBasicBlock)bb.next()) {
			currentBB = bb;
			costs += bb.costs();
		}
		currentBB = null;
		return costs;
	}

	/**
	 * calculate the method costs and decide if the method is a
	 * candidate for inlining.
	 */
	private int inline_loop_level = 0;
	public boolean isInlineCandidate() throws CompileException {

		opts.vverbose("## inline ? "+getClassName()+"."+getMethodNameAndType());

		if (source.isNative() || label_list==null) {
			return false;
		}

		// ignore methods with exception handlers
		ExceptionHandlerData[] handler = source.getExceptionHandlers();
		if (handler!=null && handler.length>0) { 
			opts.vverbose("# has exception handler");
			return false;
		}

		// preserve join points
		if (opts.getJoinPoints().ignoreMethodBody(clazz,this)) {
			opts.vverbose("# is joint point ");
			return false;
		}

		// first try to inline all methods
		if (inline_loop_level>0) {
			opts.vverbose("# inline loop "+getAlias());
			return false;
		}
		inline_loop_level++;
		if (!allready_inlined) inlineMethodCalls(); 
		inline_loop_level--;

		// calculate inline costs
		int max_costs = opts.maxInlineCosts();
		if (called_from!=null && called_from.size()==1) {
			// single caller sides are prefered
			max_costs = opts.maxInlineCostsSingle();
		}
		int costs = getCosts();
		if (costs>max_costs) {
			opts.vverbose("# "+getAlias()+" inline costs to high "+costs+">"+max_costs);
			return false;
		}

		opts.vverbose("# "+getAlias()+" is candidate");
		return true;
	}

	/** 
	 * Make a copy of the imcode and insert it into the host method.
	 *
	 * host_method  the new method to place the code
	 * current 	current line to place the code
	 * call		the invokation object it self
	 * self		this-pointer of the method call
	 * args		arguments of the method call
	 *
	 * prev_bb	the prev. basic block of the host method 
	 * next_bb 	the next basic block of the host method 
	 */
	public boolean insertCode(IMMethod host_method,
			IMNode current, IMNode call, IMNode self, IMNode[] args,
			IMBasicBlock prev_bb, IMBasicBlock next_bb) throws CompileException {

		opts.vverbose("### "+host_method.getAlias());
		opts.vverbose("## "+call.lineInfo()+" inline "+call.toReadableString());

		int bcpos = call.getBCPosition();

		next_bb.getPred().remove(prev_bb);

		//  create a copy of all basic blocks
		IMInlineVisitor visitor; 
		if (current != call) 
			visitor = new IMInlineVisitor(host_method, self, args, bcpos, next_bb, current, call);
		else 
			visitor = new IMInlineVisitor(host_method, self, args, bcpos, next_bb, null, null);

		IMMethodFrame host_frame = host_method.getMethodFrame();

		host_frame.beginInlineForeigenFrame();

		IMBasicBlock code = (IMBasicBlock)label_list.copy(visitor);

		/* seek end of code */
		IMBasicBlock cpos = code;
		while (cpos!=null && cpos.next()!=null) {
			if (code.empty()) {
				cpos.getPred().remove(code);
				code=cpos; /* skip emtpy basic blocks */
			}
			cpos=(IMBasicBlock)cpos.next();
		}

		if (code.empty()) {
			opts.vverbose("= empty ==");
		} else {
			cpos.link(next_bb);
			opts.vverbose("= insert ==");
			opts.vverbose(code.toReadableString());
			opts.vverbose("--------");
			IMBasicBlockList cpred = code.getPred(); 
			opts.vverbose("-- insert code "+getAlias()+" with pred "+cpred);
			prev_bb.link(code);

			int off=0;

			// store the arguments in new local variables 
			// this pointer
			if (self!=null) {
				IMSlot obj0 = frame.getLocalVariable(self.getDatatype(),0);
				if (host_frame.hasUpdate(obj0)) {
					IMStoreLocalVariable store_var = host_method.createStoreVariable(host_frame.adjustSlot(obj0), self, bcpos);
					prev_bb.appendBC(store_var);
				}
				off++;
			}

			// args
			for (int i=0;i<args.length;i++) {
				IMSlot arg = frame.getLocalVariable(args[i].getDatatype(),i+off);
				if (host_frame.hasUpdate(arg)) {
					IMStoreLocalVariable store_var_arg = host_method.createStoreVariable(host_frame.adjustSlot(arg), args[i], bcpos);
					prev_bb.appendBC(store_var_arg);
				}
			}

			host_frame.endInlineForeigenFrame();

		}

		opts.vverbose("= copy ==");
		for (IMBasicBlock pos = prev_bb;pos!=null && pos!=next_bb.next();pos=(IMBasicBlock)pos.next()) {
			pos.method=host_method;
			opts.vverbose(pos.getMethod());
			opts.vverbose(pos.toReadableString());
		}
		opts.vverbose("--------");

		return true; 
	}

	public IMBasicBlock getCurrentBasicBlock() {
		return currentBB;
	}

	public void emitArguments(Coder coder, IMMethodFrame caller_frame, IMNode self, IMNode[] args) throws CompileException {
		frame.emitArguments(coder, caller_frame, self, args);
	}

	public IMExceptionHandler[] getExceptionHandler() {
		return catch_list;
	}

	public void emitTestException(Coder coder, IMMethod callee, int bcpos) throws CompileException {
		if (opts.hasOption("exceptions")) {
			String label;
			coder.addln(";");
			coder.add("if (KESO_PENDING_EXCEPTION) ");
			if ((label=lookupExceptionHandler(null, bcpos))!=null) {
				coder.add("goto ");
				coder.add(label);
			} else {
				switch (getBasicReturnType()) {
					case BCBasicDatatype.VOID:
						coder.add("return");
						break;
					default:
						coder.add("return 0");
				}
			}
		}
	}

	public String lookupExceptionHandler(IMClass type, int bcpos) throws CompileException {
		String ret=null;
		int range = 0;

		if (catch_list==null) return null;

		for (int i=0;i<catch_list.length;i++) {
			IMExceptionHandler exp_handler = catch_list[i];
			if (exp_handler.inRegion(bcpos)) {
				ret = getAlias()+"_eh_"+exp_handler.getStartPos()+"_"+exp_handler.getEndPos();
				range = exp_handler.getRangeID();
				break;
			} 
			// TODO: local short cuts 
		}

		if (ret==null) return null;

		if (local_exception_calls==null) {
			local_exception_calls = new IntegerHashtable();
		}

		if (local_exception_calls.get(range)==null) {
			local_exception_calls.put(range, ret);
		}

		return ret;
	}

	public String getLocalExceptionLabel(int range_id) {
		return (String)local_exception_calls.get(range_id);
	}

	public int[] getLocalExceptionCalls() throws CompileException {
		if (local_exception_calls==null) return null;
		return local_exception_calls.sortedKeys();
	}

	/**
	 * This method translate the imcode to the backend language. 
	 *
	 * The method is overwritten in IMPortalMethod.
	 */
	public void translate(Coder coder) throws CompileException {

		try {

			if (source!=null) coder.addInvokeMakro(this);

			if (hasNoCode) {
				coder.header_add("/* abstract method ");
				coder.header_add(getAlias());
				coder.header_addln(" */");
				coder.header_add("#define ");
				coder.header_add(getAlias());
				coder.header_add(" ((");
				coder.header_add(getReturnType());
				coder.header_add(" (*)");
				coder.header_add(getArgString());
				coder.header_addln(")keso_throw_method_not_implemented(\"\",0))");
				return;
			}

			if (!stack_processed) {
				throw new CompileException("not ready!");
			}

			if (called_from!=null) {
				IMCallerEntry.emitCallerInfo(coder, this, called_from);
			} else {
				coder.local_add("/*\n * call analyse 0 ");
				coder.local_add(getAlias());
				coder.local_add("\n */\n");
				if (opts.hasOption("omit_unused_methods")) {
					opts.verbose("# omit "+getAlias());
					return;
				}
			}

			coder.beginMethod(this);

			coder.add_class(clazz.getAlias());

			if (ret_index!=null) {
				coder.addln("jshort ret_addr;");
			}

			int curr_line = -1;
			if (!opts.getJoinPoints().affectMethod(clazz, this, coder)) {
				frame.translate(coder);
				for (IMBasicBlock bb=label_list;bb!=null;bb=(IMBasicBlock)bb.next()) {
					currentBB = bb;
					curr_line=bb.translate(coder, curr_line);
				}	
				currentBB = null;
			}

			coder.endMethod();

			if (!hasReturnValue()) {
				if (isPure() || isConstant()) {
					opts.vwarn("constant or pure method '"+getAlias()+"' is void!");
				}
			}
		} catch (CompileException ex) {
			System.err.println("======================================================");
			System.err.println("CompileException in "+clazz.getSourceFile());
			System.err.println(getMethodNameAndType());
			if (currentBB!=null)  System.err.println(currentBB.toLabel());
			System.err.println("======================================================");
			throw ex;
		} catch (RuntimeException ex) {
			System.err.println("======================================================");
			System.err.println("RuntimeException in "+clazz.getSourceFile());
			System.err.println(getMethodNameAndType());
			if (currentBB!=null)  System.err.println(currentBB.toLabel());
			System.err.println("======================================================");
			throw ex;
		}
	}

	final private void analyseCalledMethod(IMMethod m) throws CompileException {
		if (!m.isPure()) {
			isPure=false;
			isConst=false;
		} else if (!m.isConstant()) {
			isConst=false;
		}
	}

	protected boolean analyseRunning = false;
	final private void analyseCalledMethods() throws CompileException {
		if (analyseCalleesDone) return;

		if (calledMethods!=null) {
			Enumeration cmethods = calledMethods.elements();
			//opts.vverbose("## analyse called methods "+getAlias()+" ("+calledMethods.size()+")");
			while (cmethods.hasMoreElements()) {
				MethodEntry e = (MethodEntry)cmethods.nextElement();
				IMMethod m = findMethod(e.className, e.nameAndType);
				if (m==null)
					throw new CompileException("Can't find method "+e.className+" "+e.nameAndType);

				if (m.analyseRunning) continue;
				m.analyseRunning = true;

				// protect recursion
				//e.nameAndType = null; done by cached_m now!

				if (m.canBlock()) { canBlockIn = m.getAlias(); }

				MethodTable table = m.getMethodTable();
				if (table==null) {
					analyseCalledMethod(m);
				} else {
					Enumeration candidates = table.getCandidates();
					while (candidates.hasMoreElements()) {
						IMMethod candidate = (IMMethod)candidates.nextElement(); 
						analyseCalledMethod(candidate);
					}
				}
			}
		}

		analyseCalleesDone = true;
		if (canBlockIn!=null) return;
		
		if (mtable==null) {
			if (!isConstructor() && isVirtual())
				opts.warn(getClassName()+" "+getMethodName()+" has no mtable");
		} else {
			Enumeration candidates = mtable.getCandidates();
			while (candidates.hasMoreElements()) {
				IMMethod candidate = (IMMethod)candidates.nextElement(); 
				if (candidate==null || candidate==this) continue;
				if (canBlockIn==null && candidate.canBlock()) {
					canBlockIn = candidate.getAlias();
					break;
				}
			}
		}
	}

	final public void calledFrom(IMMethod from, IMDataFlow[] argsInfo) {
		if (called_from==null) called_from = new Vector(); 
		called_from.add(new IMCallerEntry(from, argsInfo));
	}

	final public void resetCallerAnalyse() {
		called_from = null; 
		calledMethods = null;
	}

	final public void analyseFieldAccess() throws CompileException {
		if (hasNoCode) return;

		IMVisitor visitor = new IMFieldVisitor(this);
		for (IMBasicBlock bb=label_list;bb!=null;bb=(IMBasicBlock)bb.next()) {
			currentBB = bb;
			bb.visitNodes(visitor);
		}	
		currentBB = null;
	}

	final public void dumpBC() throws IOException {
		if (hasNoCode) return;

		String filename = opts.getOutputPath()+"/"+getAlias()+".dump";
		PrintStream out = new PrintStream(new FileOutputStream(filename));

		out.println("class ");
		out.println(getClassName());
		out.println();
		BytecodeInputStream bcStream = new BytecodeInputStream(source.getBytecode());
		while (bcStream.hasMore()) {
			int bc = bcStream.readUnsignedByte();
			out.print(" 0x");
			if (bc<16) out.print("0");
			out.print(Integer.toHexString(bc));
		}
		out.println("\n");

		out.println(getMethodNameAndType()+" {");	

		for (IMBasicBlock bb=label_list;bb!=null;bb=(IMBasicBlock)bb.next()) {
			currentBB = bb;
			bb.dumpBC(out);
		}	
		currentBB = null;

		out.println("}\n");

		out.flush();

		out.close();
	}

	/**
	 * compute the dominator tree and the dominace frontier of 
	 * the method. 
	 */
	final private void computeDomTree() throws CompileException {
		domtree = new DominatorTree(this, label_list);

		/* compute the dominator tree */
		domtree.computeDominators();

		/* now compute dominance frontiers */
		if (opts.hasOption("dom_use_aternative_algo")) {
			domtree.computeDominanceFrontier2();
		} else {
			domtree.computeDominanceFrontier();
		}
	}


	public void writeDomTree() throws IOException, CompileException {
		if (hasNoCode) return;

		if (domtree==null) computeDomTree();

		String filename = opts.getOutputPath()+"/dom_"+getAlias()+".dot";
		PrintStream out = new PrintStream(new FileOutputStream(filename));

		out.println("digraph Flowgraph {\n");	
		out.println("\tsize=\"7,10\"\n\tpage=\"8.25,11.75\"");
		out.println("\tnode [shape=box hight=.5 width=.5 fontsize=10];\n");

		if (label_list!=null) 
			for (IMBasicBlock bb=(IMBasicBlock)label_list.next();bb!=null;bb=(IMBasicBlock)bb.next()) {
				bb.writeDomEdges(out, domtree.getDominatorOf(bb));
			}	

		for (IMBasicBlock bb=label_list;bb!=null;bb=(IMBasicBlock)bb.next()) {
			currentBB = bb;
			bb.writeDomBoxes(this, out);
		}	
		currentBB = null;

		out.println("}\n");

		out.flush();

		out.close();
	}

	final public void writeUMLDiagram(PrintStream out) throws IOException {
		String mname = getMethodName();
		if (mname.equals("<clinit>")) return;

		if (mname.equals("<init>")) {
			String cname = DecoratedNames.baseName(getClassName());
			out.print(DecoratedNames.toEscTexString(cname));
		} else {
			BasicTypeDescriptor type = source.getReturnType();
			out.print(DecoratedNames.toEscTexString(type.getJavaLanguageType()));
			out.print(' ');
			out.print(DecoratedNames.toEscTexString(mname));
		}
		out.print('(');
		BasicTypeDescriptor[] param = source.getParameterTypes(); 
		for (int i=0;i<param.length;i++) {
			if (i>0) out.print(',');
			out.print(DecoratedNames.toEscTexString(param[i].getJavaLanguageType()));
		}
		out.print(')');
		out.println(" \\\\");
	}

	final public void writeCFG() throws IOException, CompileException {
		if (hasNoCode) return;

		String filename = opts.getOutputPath()+"/"+getAlias()+".dot";
		File odir = new File(opts.getOutputPath());
		if (!odir.exists()) { odir.mkdirs(); }
		PrintStream out = new PrintStream(new FileOutputStream(filename));

		out.println("digraph Flowgraph {\n");	
		out.println("\tsize=\"7,10\"\n\tpage=\"8.25,11.75\"");
		out.println("\tnode [shape=box hight=.5 width=.5 fontsize=10];\n");

		for (IMBasicBlock bb=label_list;bb!=null;bb=(IMBasicBlock)bb.next()) {
			bb.writeCFGEdges(out);
		}	

		for (IMBasicBlock bb=label_list;bb!=null;bb=(IMBasicBlock)bb.next()) {
			currentBB = bb;
			IMBasicBlockList pred = bb.getPred();
			if (bb==label_list || pred.length()>0) 
				bb.writeCFGBoxes(out);
		}
		currentBB = null;


		out.print("\n");

		out.println("}\ndigraph Flowgraph {\n");	
		out.println("\tnode [shape=box];\n");
		String prev = null;
		int i=0;
		Enumeration slots = frame.allSlots();
		while (slots.hasMoreElements()) {
			IMSlot slot = (IMSlot)slots.nextElement();
			Enumeration uses = slot.getUses();
			if (uses!=null) { 
				if (prev!=null) {
					out.print("\t\t");
					out.print(prev);
					out.print("->");
					prev = slot.toString();
					prev=prev.replace('[','_');
					prev=prev.replace(']','_');
					out.print(prev);
					out.print(";\n");
				} else {
					prev = slot.toString();
					prev=prev.replace('[','_');
					prev=prev.replace(']','_');
				}
				out.print("\t\t");
				out.print(prev);
				out.print(" [label=\"");
				out.print("\\l");
				out.print(slot.toString());
				out.print("\\ldef:\\l");
				IMNode def = slot.getDefinedStatement();
				if (def!=null) {
					if (def instanceof IMStoreLocalVariable) {
						def = ((IMStoreLocalVariable)def).getOperant();
					}
					out.print(def.toReadableString());
				}
				out.print("\\luse:");
				if (true) {
					out.print(" ");
					out.print(slot.numberOfUses());
				} else {
					while (uses.hasMoreElements()) {
						IMNode use = (IMNode)uses.nextElement();
						out.print("\\l");
						out.print(slot.getUseBB(use).toLabel());	
						//out.print("\\l");
						//out.print(use.toReadableString());
					}
				}
				out.print("\" fontsize=6 ");
				out.println("]");
				i++;
				if (i==8) {
					prev = null;
					i=0;
				}
			}
		}

		out.println("}\n");

		out.flush();

		out.close();
	}

	final protected CompileException error() {
		return error(-1,"");
	}

	final protected CompileException error(int bcpos, String text) {
		return new CompileException(lineInfo(bcpos)+": "+text);
	}

	final protected String lineInfo(int bcpos) {
		int line = getLineNumber(bcpos);
		StringBuffer infomsg = new StringBuffer();
		if (false) {
			infomsg.append(getClassName());
			infomsg.append(".");
			infomsg.append(getMethodName());
		} else {
			infomsg.append(getAlias());
		}
		if (line<0) {
			infomsg.append(" (bc ");
			infomsg.append(bcpos);
		} else {
			infomsg.append(" (line ");
			infomsg.append(line);
		}
		infomsg.append(")");
		return infomsg.toString();
	}

	public String toString() {
		return getAlias();
	}

	public int hashCode() {
		if (alias==null) alias=DecoratedNames.createMethodAlias(opts,this);
		return alias.hashCode();
	}

	public boolean equals(java.lang.Object o) {
		if (o==this) return true;
		if (o instanceof IMMethod) {
			if (alias==null) alias=DecoratedNames.createMethodAlias(opts,this);
			return alias.equals(((IMMethod)o).getAlias());
		} 
		return false;
	}
}
