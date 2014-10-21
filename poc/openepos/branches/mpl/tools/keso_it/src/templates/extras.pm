
$ext_purenode = "
	/**
	 * Nodes who only read but not change the global state
	 * are pure.
	 */
	public boolean isPureNode() { return true; }\n\n";
$ext_no_pure = "
	public void analyseCall() throws CompileException {
		method.isPure=false;
		method.isConst=false;
	}
";
$ext_sideeffect = "\tpublic boolean hasSideEffect() { return false; }\n\n";
$ext_const = "\tpublic boolean isConstant() { return true; }\n\n";
$ext_const .= "\tpublic boolean hasConstant() { return true; }\n\n";
$ext_const .= "\tpublic IMConstant nodeToConstant() throws CompileException { return this; }\n\n";
$ext_const .= "\tpublic int getIntValue() { throw new Error(); }\n\n";
$ext_const .= "\tpublic long getLongValue() { return getIntValue(); }\n\n";
$ext_const .= "\tpublic float getFloatValue() { throw new Error(); }\n\n";
$ext_const .= "\tpublic double getDoubleValue() { return getFloatValue(); }\n\n";
$ext_const .= "\tpublic void setIntValue(int value) { throw new Error(); }\n\n";
$ext_const .= "\tpublic void setLongValue(long value) { throw new Error(); }\n\n";
$ext_const .= "\tpublic void setFloatValue(float value) { throw new Error(); }\n\n";
$ext_const .= "\tpublic void setDoubleValue(double value) { throw new Error(); }\n\n";
$ext_const .= "\tpublic boolean equalValue(IMConstant node) { throw new Error(); }\n\n";
$ext_aconst = "$ext_const\tpublic String getString() { return ((StringCPEntry)value).getDescription(); }\n\n
\tpublic ConstantPoolEntry getCPEntry() { return value; }\n\n";
$is_const_obj = "\tpublic boolean isConstObject() throws CompileException { return true; }\n
\tpublic boolean isChecked(IMBasicBlock bb) throws CompileException { return true; }\n
";
$ext_add = "\tpublic boolean isAdd() {return true;}\n\n";
$ext_sub = "\tpublic boolean isSub() {return true;}\n\n";
$ext_mul = "\tpublic boolean isMul() {return true;}\n\n";
$ext_bit = "\tpublic boolean isBitOpr() {return true;}\n\n";
$ext_valid_ref = "\n\tpublic boolean isChecked(IMBasicBlock bb) throws CompileException { return true; }\n";
$ext_new_block = '
	public void analyseCall() throws CompileException {
		method.isPure = false;
		method.isConst = false;
		if (!method.getMethodName().equals("<clinit>") && 
				opts.getGlobalHeap().allocationCanBlock()) method.blockInNew();
	}
';
$ext_valid_ref2 = '
	public boolean isChecked(IMBasicBlock bb) throws CompileException {
		IMMethod callee = null;
		try { callee = method.findMethod(cpEntry); } catch (Exception ex) { return false; }
		return joinPoints.checkAttribut(callee,Weavelet.ATTR_NOTNULL)==Weavelet.TRUE;
	}
';
$ext_check_arr = "
	public void translate_global(Coder coder) throws CompileException {
		coder.global_header_add(\"void 	keso_check_array(object_t* arr, jint index, const char* msg, jint bcPos);\\n\");
		coder.global_add(\"void keso_check_array(object_t* arr, jint index, const char* msg, jint bcPos) {\\n\");
		coder.global_add(\"\\tKESO_CHECK_NULLPOINTER(arr, msg, bcPos);\\n\");
		coder.global_add(\"\\tif (unlikely(((array_t*)arr)->size<=(array_size_t)index))\");
		coder.global_add(\" keso_throw_index_out_of_bounds(msg, bcPos);\\n\");
		coder.global_add(\"}\\n\");
	}
";
$ext_field_inline = "
	public IMBasicBlock inlineMethodCalls(IMBasicBlock current, IMNode prev) throws CompileException {
		if (!opts.doInlineRValues()) return null;
		return rvalue.inlineMethodCalls(current, prev);
	}
";
$ext_new_escape = "
	private boolean does_not_escape = false;
	private IMNode escape_path = null;
	private IMNode why_escape = null;
	public void escapeAnalyses(int mode) throws CompileException {
		if (mode!=IMEscapeVisitor.PREPARE) {
			if (doEscape(this, false)) {
				why_escape=whyEscape(this, false);
				if (why_escape instanceof IMEpilog && !doEscape(this, true)) {
					opts.verbose(this.lineInfo()+\" escaping object thru \"+why_escape.toReadableString()+\" only!\");
				}
				does_not_escape = false;
			} else {
				does_not_escape = true;
			}
		}
	}

	public boolean doEscape(IMNode node, boolean isArgument) throws CompileException {
		if (escape_path==null) {
			opts.warn(this.lineInfo()+\": new \"+toReadableString()+\" with unknown escape path!\");
			return false;
		}
		return escape_path.doEscape(node, isArgument);
	}

	final public IMNode whyEscape(IMNode node, boolean isArgument) throws CompileException {
		if (escape_path==null) {
			return null;
		}
		return escape_path.whyEscape(this, isArgument);
	}

	public void setEscapePath(IMNode node) throws CompileException {
		escape_path = node;
	}

	public IMNode getEscapePath() throws CompileException {
		return escape_path;
	}
";
$ext_new_escape_arr = $ext_new_escape;
$ext_invoke_virtual = "
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
				opts.vverbose(\"++ \"+m.getAlias()+\" called from \"+method.getAlias());
				method.call(m.getClassName(), m.getMethodNameAndType());
				m.calledFrom(method, argsInfo);
			}
		} else {
			opts.vverbose(\"++ \"+callee.getAlias()+\" called from \"+method.getAlias());
			method.call(callee.getClassName(), callee.getMethodNameAndType());
			callee.calledFrom(method, argsInfo);
		}
	}

	public boolean doEscape(IMNode node, boolean isArgument) throws CompileException {
		if (opts.hasOption(\"_X_ssa_less_astack\")) return true;
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
			buf.append(\"\t/* \");
			buf.append(callee.getClassName());
			buf.append(\".\");
			buf.append(callee.getMethodName());
			buf.append(\"(\");
			if (callee.doArgumentEscape(0)) buf.append(\" esc> \");
			buf.append(obj.toReadableString());
			for (int i=0;i<args.length;i++) {
				buf.append(\",\");
				if (args[i].isReference() && callee.doArgumentEscape(i+1))
					buf.append(\" esc> \");
				buf.append(args[i].toReadableString());
			}
			buf.append(\") */\\n\");
		} else {
			Enumeration candidates = table.getCandidates();
			while (candidates.hasMoreElements()) {
				IMMethod m = (IMMethod)candidates.nextElement();
				buf.append(\"\t/* \");
				buf.append(m.getClassName());
				buf.append(\".\");
				buf.append(m.getMethodName());
				buf.append(\"(\");
				if (m.doArgumentEscape(0)) buf.append(\" esc> \");
				buf.append(obj.toReadableString());
				for (int i=0;i<args.length;i++) {
					buf.append(\",\");
					if (args[i].isReference() && m.doArgumentEscape(i+1))
						buf.append(\" esc> \");
					buf.append(args[i].toReadableString());
				}
				buf.append(\") */\\n\");
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
";

$ext_invoke_static = "
	public void analyseCall() throws CompileException {
		IMMethod callee = method.findMethod(cpEntry);	
		MethodTable table = callee.getMethodTable();
		IMDataFlow argsInfo[] = new IMDataFlow[args.length];

		for (int i=0;i<args.length;i++) {
			if (args[i]==null) continue;
			argsInfo[i] = IMDataFlow.createDataFlowObj(args[i], method.getCurrentBasicBlock());
		}

		opts.vverbose(\"++ \"+callee.getAlias()+\" called from \"+method.getAlias());
		method.call(callee.getClassName(), callee.getMethodNameAndType());
		callee.calledFrom(method, argsInfo);
	}

	public boolean doEscape(IMNode node, boolean isArgument) throws CompileException {
		if (opts.hasOption(\"_X_ssa_less_astack\")) return true;
		IMMethod callee = method.findMethod(cpEntry);	
		for (int i=0;i<args.length;i++) {
			if (node==args[i] && callee.doArgumentEscape(i)) return true;
		}
		return false;
	}

	public void showEscape(Coder coder) throws CompileException {
		IMMethod callee = method.findMethod(cpEntry);	
		StringBuffer buf = new StringBuffer();
		buf.append(\"\t/* \");
		buf.append(callee.getClassName());
		buf.append(\".\");
		buf.append(callee.getMethodName());
		buf.append(\"(\");
		for (int i=0;i<args.length;i++) {
			if (i>0) buf.append(\",\");
			if (args[i].isReference() && callee.doArgumentEscape(i))
				 buf.append(\" esc> \");
			buf.append(args[i].toReadableString());
		}
		buf.append(\") */\\n\");
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
";

$ext_uniq_targets = '
	private IMBasicBlock[] uniqTargets=null;
	public IMBasicBlock[] getUniqTargets() {
		if (uniqTargets==null) {
			//Hashtable hash = new Hashtable();
			IntegerHashtable hash = new IntegerHashtable();
			for (int i=0;i<targets.length;i++) {
				//hash.put(targets[i].toLabel(),targets[i]);
				hash.put(targets[i].getBCPosition(),targets[i]);
			}
			Enumeration all = hash.elements();
			uniqTargets=new IMBasicBlock[hash.size()];
			for (int i=0;all.hasMoreElements();i++) {
				uniqTargets[i]=(IMBasicBlock)all.nextElement();
			}
		}
		return uniqTargets;
	}
';

%extra = (

	'IMConstant' 	=> "$ext_const
	public ConstantPoolEntry getCPEntry() { throw new Error(); }
$ext_sideeffect",
	'IMIConstant' 	=> "
	public int getDatatype() {
		if (opts.hasOption(\"stack8\") || opts.hasOption(\"stack16\")) {
			if (value>-127 && 127>value) return BCBasicDatatype.BYTE;
			if (value>-32000 && 32000>value) return BCBasicDatatype.SHORT;
		}
		return datatype;
	}
",
	'IMAConstant' 	=> "$ext_aconst $ext_sideeffect $is_const_obj
	public void analyseCall() throws CompileException {
		if (value instanceof StringCPEntry) {
			ClassStore repository = method.getClassStore();
			repository.registerIMPutField(\"java/lang/String\",\"value\");
		}
	}
",
	'IMNullConstant'	=> "
\tpublic int getIntValue() { return 0; }\n\n",
	'IMAMemConstant' 	=> "$ext_const $ext_sideeffect $is_const_obj
	public void translate_global(Coder coder) throws CompileException {
		coder.global_add_allocConstObject(memory_class, alias);
		coder.global_add_init_field_hex(\"_\",\"addr\", addr);
		coder.global_add_init_field_hex(\"_\",\"size\", size);
		coder.global_add_init_end();
	}
",
	'IMAMTConstant' 	=> "$ext_const $ext_sideeffect $is_const_obj
	public void translate_global(Coder coder) throws CompileException {
		coder.global_add_allocConstObject(memory_type_class, alias);
		coder.global_add_init_field_hex(\"\", \"base\", base);
		coder.global_add_init_end();
	}
",
	'IMAdd' 	=> "$ext_add $ext_sideeffect",
	'IMSub' 	=> "$ext_sub $ext_sideeffect",
	'IMMul' 	=> "$ext_mul $ext_sideeffect",
	'IMDiv' 	=> "$ext_sideeffect",
	'IMBitAnd' 	=> "$ext_bit $ext_sideeffect",
	'IMBitOr' 	=> "$ext_bit $ext_sideeffect",
	'IMBitXor' 	=> "$ext_bit $ext_sideeffect",
	'IMShiftLeft' 	=> "$ext_sideeffect",
	'IMShiftRight' 	=> "$ext_sideeffect",
	'IMShiftRight2' => "$ext_sideeffect",
	'IMMul' 	=> "$ext_sideeffect",
	'IMNeg' 	=> "$ext_sideeffect",
	'IMRem' 	=> "$ext_sideeffect",
	'IMInstanceOf' => '
	public void analyseCall() throws CompileException {
		ClassStore rep = method.getClassStore();
		IMClass clazz = method.getIMClass(cpEntry.getClassName());
		opts.vverbose("# need InstanceOf "+clazz.getAlias());
		rep.needInstanceOf(clazz);
	}

	final public void escapeAnalyses(int mode) throws CompileException {
		if (mode!=IMEscapeVisitor.PREPARE) return;
		rOpr.setEscapePath(IMNode.NONE);
	}

',
	'IMEpilog' => '
	public boolean doEscape(IMNode node, boolean isArgument) throws CompileException {
		return !isArgument;
	}

	final public IMNode whyEscape(IMNode node, boolean isArgument) throws CompileException {
		if (!isArgument) return this;
		return null;
	}

	public void analyseCall() throws CompileException {
		ClassStore rep = method.getClassStore();
		if (opts.hasOption("exceptions")) {
			IMExceptionHandler[] catch_list = method.getExceptionHandler();
			if (catch_list==null) return;
			for (int i=0;i<catch_list.length;i++) {
				rep.needInstanceOf(catch_list[i].getIMClass());
			}
		}
	}

',
	'IMInvoke' => "
	public void analyseCall() throws CompileException {
		opts.warn(this+\" has no anaylse call method!\");
	}

",
	'IMInvokeSpecial' => "
$ext_valid_ref2
$ext_invoke_virtual
	public IMBasicBlock inlineMethodCalls(IMBasicBlock current, IMNode prev) throws CompileException {
		IMMethod callee = method.findMethod(cpEntry);	

		if (!callee.isInlineCandidate()) return null;

		return current.insertCode(prev, this, callee, obj, args);
	}
",
	'IMInvokeVirtual' => "
$ext_valid_ref2
$ext_invoke_virtual
	public IMBasicBlock inlineMethodCalls(IMBasicBlock current, IMNode prev) throws CompileException {
		IMMethod callee = method.findMethod(cpEntry);

		if (!callee.isFinal()) return null;

		if (!callee.isInlineCandidate()) return null;

		return current.insertCode(prev, this, callee, obj, args);
	}
",
	'IMInvokeStatic' => "
$ext_valid_ref2
$ext_invoke_static
	public IMBasicBlock inlineMethodCalls(IMBasicBlock current, IMNode prev) throws CompileException {
		IMMethod callee = method.findMethod(cpEntry);	

		if (!callee.isInlineCandidate()) return null;

		return current.insertCode(prev, this, callee, null, args);
	}

",
	'IMInvokeInterface' => "
$ext_valid_ref2
$ext_invoke_virtual
	public IMBasicBlock inlineMethodCalls(IMBasicBlock current, IMNode prev) throws CompileException {
		IMMethod callee = method.findMethod(cpEntry);

		MethodTable table = callee.getMethodTable();
		if (table==null) return null; 

		if (table.hasDispatchTable()) return null;
		callee = table.getFirstCandidate();

		if (callee==null) return null;
		if (!callee.isFinal()) return null;

		if (!callee.isInlineCandidate()) return null;
		return current.insertCode(prev, this, callee, obj, args);
	}
",
	'IMGoto'	=> "
	public boolean hasShortcut() {
		IMBasicBlock basicBlock = method.getCurrentBasicBlock();
		if (basicBlock.getSucc()==null) return false;
		return (basicBlock.next()==basicBlock.getSucc().at(0));
	}
",
	'IMLookupSwitch' => "$ext_uniq_targets
	final public void escapeAnalyses(int mode) throws CompileException {
		if (mode!=IMEscapeVisitor.PREPARE) return;
		operant.setEscapePath(IMNode.NONE);
	}
", 
	'IMTableSwitch' => "$ext_uniq_targets
	final public void escapeAnalyses(int mode) throws CompileException {
		if (mode!=IMEscapeVisitor.PREPARE) return;
		operant.setEscapePath(IMNode.NONE);
	}
",
	'IMThrow' => "
	final public void escapeAnalyses(int mode) throws CompileException {
		if (mode!=IMEscapeVisitor.PREPARE) return;
		exception.setEscapePath(this);
	}

	final public boolean doEscape(IMNode node, boolean isArgument) throws CompileException {
		return true;
	}

	final public IMNode whyEscape(IMNode node, boolean isArgument) throws CompileException {
		return this;
	}
",
	'IMArrayLength' => "$ext_purenode
	public void escapeAnalyses(int mode) throws CompileException {
		if (mode!=IMEscapeVisitor.PREPARE) return;
		array.setEscapePath(IMNode.NONE);
	}
",
	'IMReadArray' => "$ext_purenode $ext_check_arr
	public void escapeAnalyses(int mode) throws CompileException {
		if (mode!=IMEscapeVisitor.PREPARE) return;
		aOpr.setEscapePath(IMNode.NONE);
	}
",
	'IMNew' => "
$ext_new_block$ext_valid_ref$ext_valide_ref$ext_new_escape
	public void translate_global(Coder coder) throws CompileException {
		if (opts.hasOption(\"no_inline_alloc\")) {
			coder.global_header_add(\"object_t* keso_alloc_stack(object_t* obj, class_id_t cid);\\n\");
			coder.global_add(\"object_t* keso_alloc_stack(object_t* obj, class_id_t cid) {\\n\");
			coder.global_add(\"\tobj_size_t size,roff;\\n\");
			coder.global_add(\"\tsize = CLASS(cid).size;\\n\");
			coder.global_add(\"\troff = CLS_ROFF(cid);\\n\");
		} else {
			coder.global_header_add(\"object_t* keso_alloc_stack(object_t* obj, class_id_t cid, obj_size_t size, obj_size_t roff);\\n\");
			coder.global_add(\"object_t* keso_alloc_stack(object_t* obj, class_id_t cid, obj_size_t size, obj_size_t roff) {\\n\");
		} 
		coder.global_add(\"\twhile (roff-->0) { ((object_t**)obj)[0]=(object_t*)0; obj = (object_t*) (((object_t**)obj)+1); }\\n\");
		coder.global_add(\"\tobj->class_id = cid;\\n\");
		if (opts.getGlobalHeap().needGCInfo()) 
			coder.global_add(\"\tobj->gcinfo = 1;\\n\");
		coder.global_add(\"\treturn (object_t*)obj;\\n\");
		coder.global_add(\"}\\n\");
	}
",
	'IMNewArray' => "
$ext_valid_ref$ext_new_block$ext_new_escape_arr
",
	'IMNewMultiArray' => "$ext_valid_ref$ext_new_block$ext_new_escape_arr",
	'IMNewObjArray' => "$ext_valid_ref$ext_new_block$ext_new_escape_arr",
	'IMGetField' => "$ext_purenode
	
	public void escapeAnalyses(int mode) throws CompileException {
		rOpr.setEscapePath(IMNode.NONE);	
	}

	public void analyseCall() throws CompileException {
		ClassStore repository = method.getClassStore();
		repository.register\%CLASS\%(cpEntry.getClassName(), cpEntry.getMemberName());
	}

	public boolean isMemoryType() throws CompileException {
		if (datatype!=BCBasicDatatype.REFERENCE) return false;
		String fieldType = cpEntry.getMemberTypeDesc();
		if (fieldType.charAt(0)=='[') return false;
		IMClass fieldClass = method.getIMClass(fieldType.substring(1,fieldType.length()-1));
		if (fieldClass!=null) return fieldClass.isMemoryType();
		opts.warn(\"Cannot find field type \"+fieldType.substring(1,fieldType.length()-1));
		return false;
	}

	public boolean isVolatile() throws CompileException {
		IMClass clazz = method.getIMClass(cpEntry.getClassName());
		IMField field = clazz.getField(cpEntry);
		return field.isVolatile();
	}

	/**
	 * This method is the right place to add a piece of code to global.c if needed.
	 * The imnode must be registered with 
	 * repository.registerGlobalTranslationCallback(\"mt_escape\", this);
	 */
	public void translate_global(Coder coder) throws CompileException {
		IMClass mtclazz = method.getIMClass(\"keso/core/MT\");
		if (mtclazz!=null) {
			coder.global_header_add(\"#define KESO_OBJ_TO_MT(_obj_) \");
			coder.global_header_add(mtclazz.getAlias().toUpperCase());
			coder.global_header_add(\"_OBJ(_obj_)\\n\");
			coder.global_add_fkt(\"mt_escape.c\");
		}
	}

	public boolean translate_MT_Ref(Coder coder) throws CompileException {
		if (isMemoryType()) {
			IMClass clazz = method.getIMClass(cpEntry.getClassName());
			coder.add_class(clazz);
			if (rOpr instanceof IMAMTConstant) {
				coder.add(\"((\");
				coder.add(clazz.getAlias());
				coder.add(\"_mt*)\");
				coder.add_hex(((IMAMTConstant)rOpr).getAddr());
				coder.add(\")->\");
				coder.add(clazz.emitField(cpEntry.getMemberName()));
			} else {
				coder.chk_ref(rOpr,method,bcPosition);
				coder.add(\"(\");
				coder.add(clazz.getAlias().toUpperCase());
				coder.add(\"_MT(\");
				rOpr.translate(coder);
				coder.add(\")->\");
				coder.add(clazz.emitField(cpEntry.getMemberName()));
				coder.add(\")\");
			}
			return true;
		}
		return false;
	}
",
	'IMGetStatic' => "$ext_sideeffect $ext_purenode
	public boolean isFinalStatic() throws CompileException {
		IMClass field_class = method.getIMClass(cpEntry.getClassName());
		IMField field = field_class.getField(cpEntry);
		return field.isFinal();
	}

	public String emit() throws CompileException {
		return imfield.getMacroName();
	}

	public boolean isVolatile() throws CompileException {
		IMClass clazz = method.getIMClass(cpEntry.getClassName());
		IMField field = clazz.getField(cpEntry);
		return field.isVolatile();
	}

	public void analyseCall() throws CompileException {
		ClassStore repository = method.getClassStore();
		repository.registerIMGetStatic(cpEntry.getClassName(), cpEntry.getMemberName());

		IMClass field_class = repository.getClass(cpEntry.getClassName());
		imfield = field_class.getField(cpEntry);
		if (imfield==null) {
			opts.warn(\"Field \"+cpEntry.getMemberName()+\" in class \"+field_class.getClassName()+\" not found!\");
			return;
		} 
		repository.registerStaticRefField(imfield);
	}
",
	'IMReadLocalVariable' => "
$ext_sideeffect
	/*
	public boolean translate_MT_Ref(Coder coder) throws CompileException {
		if (datatype!=BCBasicDatatype.REFERENCE) return false;
		IMNode alias = var.getAlias();
		if (alias!=null && alias.translate_MT_Ref(coder)) {
			opts.warn(\"mt forwarded by alias\");
			return true;
		}
		return false;
	}
	*/

	public void analyseCall() throws CompileException {
		IMBasicBlock basicBlock = method.getCurrentBasicBlock();
 		basicBlock.readSlot(var);
	}

	public boolean isStackVariable(int stack_pos) {
		return var.isStackVariable(stack_pos);
	}

	public boolean isChecked(IMBasicBlock bb) throws CompileException {
		if (var==null) return false;
		return var.isChecked(bb);
	}

	public void setChecked(IMBasicBlock bb) throws CompileException {
		var.setChecked(bb);
	}

	public boolean isConstant() {
		if (var==null) return false;
		IMBasicBlock basicBlock = method.getCurrentBasicBlock();
		return var.varToConstant(basicBlock)!=null;
	}

	public IMConstant nodeToConstant() throws CompileException {
		IMBasicBlock basicBlock = method.getCurrentBasicBlock();
		IMConstant const_node = var.varToConstant(basicBlock);
		if (const_node==null) throw new CompileException(\"not constant!\");
		return const_node;
	}
",
	'IMStoreLocalVariable' => '

	public void analyseCall() throws CompileException {
		IMBasicBlock basicBlock = method.getCurrentBasicBlock();
 		basicBlock.writeSlot(var);
	}

	public void setOperant(IMNode opr) {
		this.operant = opr;
	}

	public IMNode getOperant() {
		return operant;
	}

	public IMReadLocalVariable getReadOperation() throws CompileException {
		throw new CompileException();
	} 

	public void setChecked(IMBasicBlock bb) throws CompileException {
		var.setChecked(bb);
	}

	public IMBasicBlock inlineMethodCalls(IMBasicBlock current, IMNode prev) throws CompileException {
		if (!opts.doInlineRValues()) return null;
		return operant.inlineMethodCalls(current, prev);
	}
',
	'IMCheckCast' => '
	public void setEscapePath(IMNode node) throws CompileException {
		rOpr.setEscapePath(node);
	}

	public IMNode getEscapePath() throws CompileException {
		return rOpr.getEscapePath();
	}

	public void analyseCall() throws CompileException {
		ClassStore rep = method.getClassStore();
		IMClass clazz = method.getIMClass(cpEntry.getClassName());
		opts.vverbose("# need check cast "+clazz.getAlias());
		rep.needCheckCast(clazz);
	}

	public boolean isChecked(IMBasicBlock bb) throws CompileException {
		return rOpr.isChecked(bb);
	}

	public IMBasicBlock inlineMethodCalls(IMBasicBlock current, IMNode prev) throws CompileException {
		if (!opts.doInlineRValues()) return null;
		return rOpr.inlineMethodCalls(current, prev);
	}
',
	'IMStoreArray' => $ext_check_arr.'
	public boolean doEscape(IMNode node, boolean isArgument) throws CompileException {
		return true;
	}

	final public IMNode whyEscape(IMNode node, boolean isArgument) throws CompileException {
		return this;
	}

	public void escapeAnalyses(int mode) throws CompileException {
		if (mode!=IMEscapeVisitor.PREPARE) return;
		aOpr.setEscapePath(IMNode.NONE);	
		rvalue.setEscapePath(this);	
	}

	public IMBasicBlock inlineMethodCalls(IMBasicBlock current, IMNode prev) throws CompileException {
		if (!opts.doInlineRValues()) return null;
		return rvalue.inlineMethodCalls(current, prev);
	}
',
	'IMPhi' => "$ext_sideeffect $ext_purenode

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
				opts.warn(escape_path.toReadableString()+\" dominate the definition of \"+node.toReadableString());
				escape_state=2;
				return true;
			}
		} else {
			opts.warn(method.getAlias()+\": phi-fkt no slot \"+node.toReadableString());
		}

		if (escape_path==null) {
			opts.warn(this.lineInfo()+\": phi-fkt \"+toReadableString()+\" with unknown escape path!\");
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

",
	'IMPutField' => "
	private boolean initial_write = false;
$ext_field_inline
	public boolean doEscape(IMNode node, boolean isArgument) throws CompileException {
		return true;
		/*
		 This is more complex! 

		if (opts.hasOption(\"ssa_astack_less\")) return true;
		if (obj.getIMSlot().doEscape(false)) return true;
		return false;
		*/
	}

	final public IMNode whyEscape(IMNode node, boolean isArgument) throws CompileException {
		if (!doEscape(this,false)) return null;
		return this;
	}

	public void analyseCall() throws CompileException {
		ClassStore repository = method.getClassStore();
		repository.register\%CLASS\%(cpEntry.getClassName(), cpEntry.getMemberName());
	}

	public void escapeAnalyses(int mode) throws CompileException {
		if (mode!=IMEscapeVisitor.PREPARE) return;
		obj.setEscapePath(IMNode.NONE);
		rvalue.setEscapePath(this);	
	}

	public String toMemAlias() {
		return obj.toReadableString()+\".\"+cpEntry.getMemberName();
	}
",
	'IMPutStatic' => "$ext_field_inline
	public boolean doEscape(IMNode node, boolean isArgument) throws CompileException {
		return true;
	}

	final public IMNode whyEscape(IMNode node, boolean isArgument) throws CompileException {
		return this;
	}

	public void escapeAnalyses(int mode) throws CompileException {
		if (mode!=IMEscapeVisitor.PREPARE) return;
		rvalue.setEscapePath(this);
	}

	public void analyseCall() throws CompileException {
		method.isPure  = false;
		method.isConst = false;

		ClassStore repository = method.getClassStore();

		IMField fld = method.getIMClass(cpEntry.getClassName()).getField(cpEntry);

		if (fld==null) throw new CompileException (\"field \"+
				method.getIMClass().getClassName()+\":\"+cpEntry.getMemberName()+\" not found!\");

		if (method.getMethodName().equals(\"<clinit>\") && fld.isFinal()) {
			repository.registerIMPutStatic(cpEntry.getClassName(), cpEntry.getMemberName(), rvalue);
		} else {
			repository.registerIMPutStatic(cpEntry.getClassName(), cpEntry.getMemberName(), null);
		}

		IMClass field_class = repository.getClass(cpEntry.getClassName());
		imfield = field_class.getField(cpEntry);
		repository.registerStaticRefField(imfield);
	}

	public String toMemAlias() {
		return cpEntry.getClassName()+\".\"+cpEntry.getMemberName();
	}
",
	'IMIStoreLocalVariable' => '
	public IMReadLocalVariable getReadOperation() throws CompileException {
		return new IMIReadLocalVariable(method,21,bcPosition,var);
	}

',
	'IMLStoreLocalVariable' => '
	public IMReadLocalVariable getReadOperation() throws CompileException {
		return new IMLReadLocalVariable(method,21,bcPosition,var);
	}

',
	'IMFStoreLocalVariable' => '
	public IMReadLocalVariable getReadOperation() throws CompileException {
		return new IMFReadLocalVariable(method,21,bcPosition,var);
	}

',
	'IMDStoreLocalVariable' => '
	public IMReadLocalVariable getReadOperation() throws CompileException {
		return new IMDReadLocalVariable(method,21,bcPosition,var);
	}

',
	'IMAStoreLocalVariable' => '

	public boolean doEscape(IMNode node, boolean isArgument) throws CompileException {
		return var.doEscape(isArgument);
	}

	final public IMNode whyEscape(IMNode node, boolean isArgument) throws CompileException {
		return var.whyEscape(isArgument);	
	}

	public void escapeAnalyses(int mode) throws CompileException {
		if (mode!=IMEscapeVisitor.PREPARE) return;
		operant.setEscapePath(this);
	}

	public IMReadLocalVariable getReadOperation() throws CompileException {
		return new IMAReadLocalVariable(method,21,bcPosition,var);
	}

',
	'IMAReadLocalVariable' => '
	private IMNode escape_path = null;
	public boolean doEscape(IMNode node, boolean isArgument) throws CompileException {
		if (escape_path==null) {
			opts.warn(this.lineInfo()+": read "+toReadableString()+" with unknown escape path!");
			return false;
		}
		return escape_path.doEscape(this, isArgument);
	}

	final public IMNode whyEscape(IMNode node, boolean isArgument) throws CompileException {
		if (escape_path==null) {
			return this;
		}
		return escape_path.whyEscape(this, isArgument);
	}

	public void setEscapePath(IMNode node) throws CompileException {
		if (node == null ) throw new Error("upps");
		escape_path = node;
	}

	public IMNode getEscapePath() throws CompileException {
		return escape_path;
	}

	public String emit() throws CompileException {
		return var.toString();
	}

',
); # end %extra
