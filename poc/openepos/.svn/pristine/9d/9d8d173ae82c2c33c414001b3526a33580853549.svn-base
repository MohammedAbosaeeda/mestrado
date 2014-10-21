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
import keso.compiler.backend.Coder;

import keso.util.*; 

import java.util.Enumeration;

final public class IMMethodFrame {

	private IMMethod method;
	private BuilderOptions opts;

	private int new_count;
	private int[] arg_types;

	private IntegerHashtable stack_index = new IntegerHashtable();	
	private IntegerHashtable all = new IntegerHashtable();

	private IntegerHashtable foreigenSlots = null;
	private IntegerHashtable referenceSlots = null;
	private int referenceCount=0;

	private IMCallerEntry callerInfo = null;

	private final static int[] NOARGS = new int[] {};

	private final static String volatile_cast = "(object_t*)";

	public IMMethodFrame(IMMethod method, int start_counter) {
		this(method,NOARGS,start_counter);
	}

	public IMMethodFrame(IMMethod method, int[] arg_types, int start_counter) {
		this.method=method;
		this.opts=method.getOptions();
		this.arg_types=arg_types;
		this.new_count=start_counter;
		int locals=0;
		for (int i=0;i<arg_types.length;i++) {
			getLocalVariable(arg_types[i],locals);
			locals+=BCBasicDatatype.sizeInWords(arg_types[i]);
		}
	}

	public int argRange() {
		int locals=0;
		for (int i=0;i<arg_types.length;i++) {
			locals+=BCBasicDatatype.sizeInWords(arg_types[i]);
		}
		return locals;
	}

	public int registerSlot(int alias) {
		if (referenceSlots==null) referenceSlots = new IntegerHashtable();
		Integer slot=(Integer)referenceSlots.get(alias);
		if (slot==null) {
			slot = new Integer(referenceCount++);
			referenceSlots.put(alias,slot);
		}
		return slot.intValue();
	}

	public IMMethod getMethod() {
		return method;
	}

	public BuilderOptions getOptions() {
		return opts;
	}

	public IMSlot getLocalVariable(int type, int src_alias) {

		int locals=0;
		for (int i=0;i<arg_types.length;i++) {
			locals+=BCBasicDatatype.sizeInWords(arg_types[i]);
		}

		int typed_alias = src_alias;
		if (src_alias>=locals) typed_alias += (type<<16); 

		IMSlot ret = (IMSlot)all.get(typed_alias);

		if (ret==null) {
			ret = new IMSlot(this, type,src_alias);
			all.put(typed_alias,ret);
		}

		return ret;
	}

	public IMSlot getStackVariable(int type, int stack_alias) {
		int real_alias = (type<<16) + stack_alias;
		IMSlot ret = (IMSlot)stack_index.get(real_alias);
		if (ret==null) {
			ret = createNewVariable(type);
			ret.setIsStackVar(stack_alias);
			stack_index.put(real_alias,ret);
		}
		return ret;
	}

	public IMSlot createNewVariableSSA(IMSlot old) {
		IMSlot new_slot =  createNewVariable(old.getType());
		new_slot.setName(old.incName());
		return new_slot;
	}

	public IMSlot createNewVariable(int type) {
		int alias = new_count++;
		IMSlot ret = new IMSlot(this, type, alias);
		all.put((type<<16)+alias,ret);
		return ret;
	}

	public IMNode readArgument(int type, int nr) throws CompileException {
		IMSlot[] args = getMethodArguments();
		if (args[nr].getType()!=type) 
			throw new CompileException("wrong argument type!");
		return method.readLocal(0, args[nr]);
	}

	/**
	 * parameter optimization.
	 */
	public IMNode constant_parameter_propagation() throws CompileException {

		if (!opts.hasOption("const_arg_prop")) return null;

		callerInfo = method.getFoldedCallerInfo();
		if (callerInfo==null) return null;

		IMListHead list = new IMListHead();

		int t=0;
		for (int i=0;i<arg_types.length;i++) {
			int type = arg_types[i];
			IMSlot slot = (IMSlot)all.get(t);
			if (slot==null) slot = getLocalVariable(type,t);
			IMDataFlow df = callerInfo.elementAt(i);
			slot.setDataFlowInfo(df);
			if (df!=null && df.isConstant()) {
				try {
					IMNode constant = df.getConstValue(method,0);
					IMNode store = method.createStoreVariable(slot, constant, 0);
					opts.verbose("++ propagate method argument "+store.toReadableString());
					list.insert(store);	
				} catch (CompileException ex) {
					System.err.println(method.getMethodNameAndType()+": "+df.getClass().getName());
					throw ex;
				}
			}
			t=t+BCBasicDatatype.sizeInWords(type);
		}

		arg_slots_cache=null;
		return list;
	}

	public IMSlot[] getOriginalArguments() throws CompileException {
		IMSlot[] jargs = new IMSlot[arg_types.length];
		int t=0;
		for (int i=0;i<arg_types.length;i++) {
			int type = arg_types[i];
			IMSlot slot = (IMSlot)all.get(t);
			if (slot==null) slot = getLocalVariable(type,t);
			jargs[i]=slot;
			t=t+BCBasicDatatype.sizeInWords(type);
		}
		return jargs;
	} 

	private IMSlot[] arg_slots_cache = null;
	public IMSlot[] getMethodArguments() throws CompileException {

		/* allways remeber caches are evil! */
		if (arg_slots_cache!=null && !opts.hasOption("_X_no_arg_cache")) return arg_slots_cache;

		IMSlot[] arg_slots = null;
		boolean llrefs = (opts.hasLinkedListOfLocalReferences() && method.canBlock()); 
		arg_slots = new IMSlot[arg_types.length+(llrefs?1:0)];

		int l=0;
		if (llrefs) {
			arg_slots[l]=new IMSlotPref(this);
			l++;
		}

		int t=0;
		for (int i=0;i<arg_types.length;i++) {
			int type = arg_types[i];
			IMSlot slot = (IMSlot)all.get(t);
			if (slot==null) slot = getLocalVariable(type,t);
			if (callerInfo!=null) {
				IMDataFlow df = slot.getDataFlowInfo();
				if (df==null || !df.isConstant()) {
					arg_slots[l]=slot;
					l++;
				}
			} else {
				arg_slots[l]=slot;
				l++;
			}
			t=t+BCBasicDatatype.sizeInWords(type);
		}

		if (l<arg_slots.length) {
			IMSlot[] old = arg_slots;
			arg_slots = new IMSlot[l];
			for (int i=0;i<l;i++) { arg_slots[i] = old[i]; }
		}

		arg_slots_cache = arg_slots;
		return arg_slots;
	}

	/**
	 * arguments for method definition
	 */
	public String getArgString() throws CompileException {
		StringBuffer args = new StringBuffer();

		IMSlot[] arguments = getMethodArguments();

		args.append('(');

		if (arguments.length == 0) {    // void
			args.append("void");
		} else {
			for (int i=0;i<arguments.length;i++) {
				IMSlot slot = arguments[i];
				if (i>0) args.append(',');
				args.append(slot.definition());
			}
		}
		args.append(')');
		return args.toString();
	}

	public String getArgReCallString() throws CompileException {
		StringBuffer args = new StringBuffer();

		IMSlot[] arguments = getMethodArguments();

		args.append('(');

		for (int i=0;i<arguments.length;i++) {
			IMSlot slot = arguments[i];
			if (i>0) args.append(',');
			args.append(slot.toString());
		}

		args.append(')');
		return args.toString();
	}

	public String[] getArgNames() throws CompileException {
		IMSlot[] arguments = getMethodArguments();
		String[] args = new String[arguments.length];
		for (int i=0;i<arguments.length;i++) { args[i]=arguments[i].toString(); }
		return args;
	}

	public int numberOfRefs() {
		return referenceSlots.size();
	}

	public int[] getArgTypes() throws CompileException {
		IMSlot[] arguments = getMethodArguments();
		int[] types = new int[arguments.length];
		for (int i=0;i<arguments.length;i++) { types[i] = arguments[i].getType(); } 
		return types;
	}

	public void beginInlineForeigenFrame() {
		foreigenSlots = new IntegerHashtable();
	}

	public boolean hasUpdate(IMSlot foreigenSlot) {
		return foreigenSlots.get(foreigenSlot.getVarNumber())!=null;
	}

	public IMSlot adjustSlot(IMSlot foreigenSlot) throws CompileException {

		if (foreigenSlot.sameFrame(this)) return foreigenSlot;

		IMSlot nslot = (IMSlot)foreigenSlots.get(foreigenSlot.getVarNumber());
		if (nslot==null) {
			nslot = createNewVariable(foreigenSlot.getType());
			foreigenSlots.put(foreigenSlot.getVarNumber(),nslot);
		}

		return nslot;
	}

	public void endInlineForeigenFrame() {
		foreigenSlots=null;
	}

	public int referenceSlotSize() {
		if (referenceSlots==null) return 0;
		return referenceSlots.size();
	}

	/**
	 * emit the arguments for caller side.
	 */
	public void emitArguments(Coder coder, IMMethodFrame caller_frame, IMNode self, IMNode[] args) throws CompileException {
		int keys[] = all.sortedKeys();
		boolean sep=false;
		coder.add('(');

		/* add precursor link */
		if (method.canBlock() && opts.hasLinkedListOfLocalReferences()) {
			int ref_size = caller_frame.referenceSlotSize();
			if (ref_size==0) {
				coder.add("pref"); 
			} else {
				coder.add("(object_t**)&obj[");
				coder.add(ref_size);
				coder.add("]");
			}
			sep=true;
		}

		int j=0;
		if (self!=null) {
			if (sep) coder.add(',');
			coder.add(volatile_cast);
			self.translate(coder);
			j++;
			sep=true;
		}

		for (int i=0;i<args.length;i++) {

			IMDataFlow df =null;
			if (j<keys.length) {
				IMSlot slot=(IMSlot)all.get(keys[j]);
				df = slot.getDataFlowInfo();
				//coder.add("/*");
				//coder.add(j);
				//coder.add(":");
				//coder.add(keys[j]);
				//coder.add(":");
				//if (df!=null) coder.add((df.isConstant()?"const":""));
				//else coder.add("null");
				//coder.add(" */");
				j+=BCBasicDatatype.sizeInWords(slot.getType());
			}
			//IMSlot slot = (IMSlot)all.get(j);
			//IMDataFlow df = slot.getDataFlowInfo();
			if (df!=null && df.isConstant()) {
				/* skip */
			} else {
				if (sep) coder.add(',');
				if (args[i].getDatatype()==BCBasicDatatype.REFERENCE)
					coder.add(volatile_cast);
				args[i].translate(coder);
				sep=true;
			}
			//j++;
		}
		coder.add(')');
	}

	public void codeEOLL(Coder coder) throws CompileException {
		if (opts.hasLinkedListOfLocalReferences()) {
			if (!method.canBlock())
				throw new CompileException("Unexpected block operation! (try workaround -X:_X_all_CanBlock and report the bug)");
			if (referenceSlots==null) {
				coder.add_befor("\t*pref=KESO_EOLL; /* we have no local references. */\n");
			} else {
				coder.add_befor("\tobj["+referenceSlots.size()+"]=KESO_EOLL;\n");
			}
		}
	}

	public int resetUses() throws CompileException {
		int sum =0;
		Enumeration slots = all.elements();
		while (slots.hasMoreElements()) {
			IMSlot s = (IMSlot)slots.nextElement();
			sum = sum + s.numberOfUses();
			s.reset_uses();
		}
		return sum;
	}

	private boolean ssa = false;
	public void opt_vars() { ssa = true; }
	private IntegerHashtable finalize_local_vars() throws CompileException {
		boolean llrefs = opts.hasLinkedListOfLocalReferences() && method.canBlock();
		int keys[] = all.sortedKeys();

		IntegerHashtable nall=null;
		if (ssa) nall= new IntegerHashtable();

		for (int i=0;i<keys.length;i++) {
			IMSlot slot=(IMSlot)all.get(keys[i]);
			if (llrefs && slot.getType()==BCBasicDatatype.REFERENCE) {
				if (ssa) {
					if (!slot.hasNoUses() || i<arg_types.length) {
						slot.register_ref();
					}
				} else {
					slot.register_ref();
				}
			}
			if (ssa) {
				if (!slot.hasNoUses() || i<arg_types.length) {
					nall.put(keys[i], slot);
				}
			}

		}

		if (ssa) all = nall;

		return all;
	}

	public void translate(Coder coder) throws CompileException {
		boolean llrefs = opts.hasLinkedListOfLocalReferences() && method.canBlock();

		int keys[] = finalize_local_vars().sortedKeys();

		int locals=0;
		for (int i=0;i<keys.length;i++) {
			IMSlot slot=(IMSlot)all.get(keys[i]);
			IMDataFlow df = slot.getDataFlowInfo();
			locals+=BCBasicDatatype.sizeInWords(slot.getType());
		}

		int args=0;
		for (int i=0;i<arg_types.length;i++) {
			IMSlot slot=(IMSlot)all.get(keys[i]);
			IMDataFlow df = slot.getDataFlowInfo();
			if (df!=null && df.isConstant()) {
				coder.add(slot.definition());
				coder.addln(";");
			}
			args+=BCBasicDatatype.sizeInWords(arg_types[i]);
		}

		if (args<locals) {
			for (int i=0;i<keys.length;i++) {
				IMSlot slot=(IMSlot)all.get(keys[i]);
				if (slot.getVarNumber()<args) continue;
				if (llrefs && slot.getType()==BCBasicDatatype.REFERENCE) continue; 
				if (opts.hasOption("_X_dbg_uses")) {
					coder.addln();
					Enumeration us = slot.getUses();
					while (us.hasMoreElements()) {
						IMNode un = (IMNode)us.nextElement();
						coder.add("/* ");
						coder.add(un.getMethod().getMethodName());
						coder.add(" ");
						coder.add(un.lineInfo());
						coder.add(":");
						coder.add(un.dumpBC());
						coder.add(',');
						coder.addln(" */");
					}
				}
				coder.add(slot.definition());
				coder.addln(";");
			}

			if (llrefs && referenceSlots!=null) {
				int frame_size = referenceSlots.size();
				/* extra space for end frame list marker */
				if (method.canBlock()) frame_size++; 
				/* extra space for end frame marker */
				if (opts.useLLRefMarker2()) frame_size++;
				coder.add("volatile ");
				coder.add("object_t* obj[");
				coder.add(frame_size);
				coder.addln("];");
				if (opts.useLLRefMarker2()) {
					coder.add("obj["); 
					coder.add(frame_size-1);
					coder.addln("]=KESO_STACK_REF_MARK;");
				}
				if (method.canBlock()) {
					if (referenceSlots.size()>2) {
						coder.addln("/* initial new frame (big) */");
						coder.add("{int c; for (c=0;c<");
						coder.add(referenceSlots.size());
						coder.addln(";c++) obj[c]=(object_t*)0;}");
					} else {
						coder.addln("/* initial new frame (small) */");
						if (referenceSlots.size()>1) 
							coder.addln("obj[1]=0;");
						coder.addln("obj[0]=0;");
					}
					/*
					 * It is adequate to terminate the list just before
					 * we block. (see blocking Systemcalls for details)
					 *
					 coder.add("obj[");
					 coder.add(referenceSlots.size());
					 coder.addln("]=KESO_EOLL;");
					 */
					if (opts.useLLRefMarker() && !opts.useLLRefMarker2()) {
						coder.addln("*pref=(object_t*)((int)obj|0x1); /* register new frame */");
					} else {
						coder.addln("*pref=(object_t*)obj; /* register new frame */");
					}
				}
			}
		}

		/* inform the coder of the end of local definition */
		coder.endOfLocals();	
	} 

	public Enumeration allSlots() {
		return all.elements();
	}
}
