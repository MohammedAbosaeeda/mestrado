%translate=(
'IMPhi' => '
		coder.add(toReadableString());
',
'IMAMTConstant' => '
		coder.add_allocGlobalConstObject(memory_type_class, alias, this);
',
'IMAMemConstant' => '
		coder.add_allocGlobalConstObject(memory_class, alias, this);
',
'IMCall' => '
		coder.add("ret_addr = ");
		coder.add(index);
		coder.add("; goto ");
		coder.add(targets[0].toLabel());
',
'IMPopReturnAddr' => '
		coder.add("((object_t*)ret_addr)");
',
'IMReturnSubroutine' => '
		coder.add("switch ((jshort)");
		coder.add(var.toString());
		coder.add(") {\n");
		IntegerHashtable ret_index = method.getReturnTargets();
		int keys[] = ret_index.sortedKeys();
		for (int i=0;i<keys.length;i++) {
			IMBasicBlock retbb = (IMBasicBlock)ret_index.get(keys[i]);
			coder.add("\t\tcase ");
			coder.add(keys[i]);
			coder.add(": goto ");
			coder.add(retbb.toLabel());
			coder.add(";\n");
		}
		coder.add("\t}");
',
'IMAConstant' => '
		if (value instanceof StringCPEntry) {
			ClassStore repository = method.getClassStore();
			coder.add(repository.allocString(coder, this, value.getDescription()));
		} else {
			coder.add(value.getDescription());
		}	
',
'off_IMAConstant' => '
		if (value instanceof StringCPEntry) {
			IMClass clazz = method.getIMClass("java/lang/String");
			coder.add_class(clazz.getAlias());

			IMClass cclazz = method.getIMClass("[C");
			coder.add_class(cclazz.getAlias());

			String static_string  = method.getAlias()+"_str"+getBCPosition();
			String static_chararr = method.getAlias()+"_arr"+getBCPosition();
			String static_struct  = method.getAlias()+"_s"+getBCPosition();

			String str = value.getDescription();

			coder.local_add("struct ");
			coder.local_add(static_struct);
			coder.local_add(" {\nARRAY_HEADER\njchar data[");
			coder.local_add(str.length()+1);
			coder.local_add("];\n};\n");

			//coder.local_add(opts.declareConst());
			coder.local_add("static struct ");
			coder.local_add(static_struct);
			coder.local_add(" ");
			coder.local_add(static_chararr);
			coder.local_add(" HEAP_SECTION = {\n.class_id = CHAR_ARRAY_ID, .gcinfo=1,\n");
			coder.local_add(".size = ");
			coder.local_add(str.length());
			coder.local_add(",\n.data = {");
			for (int i=0;i<str.length();i++) { 
				coder.local_add((int)str.charAt(i));
				coder.local_add(",");
			}
			coder.local_add("0}\n};\n");

	 		coder.add_allocConstObject(clazz, static_string);
	 		coder.add_init_field(clazz,"value","(object_t*)&"+static_chararr);
	 		coder.add_init_end();
		} else {
			coder.add(value.getDescription());
		}	
',
'IMAReadArray' => '
		IMClass clazz = method.getIMClass("[Ljava/lang/Object;");
		coder.add_class(clazz.getAlias());
		coder.chk_array(this,aOpr,iOpr,method,bcPosition);
		coder.chk_ref(aOpr,method,bcPosition);
		coder.add("((object_array_t*)");
		aOpr.translate(coder);
		coder.add(")->data[");
		iOpr.translate(coder);
		coder.add(\']\');
',
'IMReadLocalVariable' => '
		if (var==null) {
			opts.warn("local variable is null in "+method.getAlias());
			coder.add(0);
		} else {
			coder.add(var.toString());
		}
',
'IMAStoreArray' => '
		IMClass clazz = method.getIMClass("[Ljava/lang/Object;");
		coder.add_class(clazz.getAlias());
		coder.chk_array(this,aOpr,iOpr,method,bcPosition);
		coder.chk_ref(aOpr,method,bcPosition);

		if (opts.getGlobalHeap().needWriteBarriers() && !coder.omitWriteBarrier(BCBasicDatatype.REFERENCE)) {
			coder.add("KESO_WRBR(");
			coder.add("((object_array_t*)");
			aOpr.translate(coder);
			coder.add(")->data[");
			iOpr.translate(coder);
			coder.add("],");
			rvalue.translate(coder);
			coder.add(")");
		} else {
			coder.add("((object_array_t*)");
			aOpr.translate(coder);
			coder.add(")->data[");
			iOpr.translate(coder);
			coder.add("]=");
			rvalue.translate(coder);
		}
',
'IMCStoreArray' => '
		IMClass clazz = method.getIMClass("[C");
		coder.add_class(clazz.getAlias());
		coder.chk_array(this,aOpr,iOpr,method,bcPosition);
		coder.chk_ref(aOpr,method,bcPosition);
		coder.add("((char_array_t*)");
		aOpr.translate(coder);
		coder.add(")->data[");
		iOpr.translate(coder);
		coder.add("]=");
		rvalue.translate(coder);
',
'IMAStoreLocalVariable' => '
		if (operant instanceof IMPhi) coder.add("/* ");
		coder.add(var.toString());
		coder.add(" = ");
		operant.translate(coder);

		if (operant instanceof IMConstant) {
			var.propagateConstant(basicBlock, operant.nodeToConstant());
		} else {
			var.propagateConstant(basicBlock, null);
		}

		if (operant.isChecked(coder)) {
			if (opts.hasVerbose()) coder.addln_befor("/* "+var+" = !null */");
			var.setChecked(coder.getCurrentBasicBlock());
		} else {
			if (opts.hasVerbose()) coder.addln_befor("/* "+var+" = ?null? */");
			var.setChecked(null);
		}
		if (operant instanceof IMPhi) coder.add(" */");
',
'IMStoreLocalVariable' => '
		if (operant instanceof IMPhi) coder.add("/* ");
		coder.add(var.toString());
		coder.add(" = ");
		operant.translate(coder);
		if (operant instanceof IMPhi) coder.add(" */");
',
'IMArrayLength' => '
		coder.chk_ref(array,method,bcPosition);
		coder.add("((array_t*)");
		array.translate(coder);
		coder.add(")->size");
',
'IMBReadArray' => '
		IMClass clazz = method.getIMClass("[B");
		coder.add_class(clazz.getAlias());
		coder.chk_array(this,aOpr,iOpr,method,bcPosition);
		coder.chk_ref(aOpr,method,bcPosition);
		coder.add("((byte_array_t*)");
		aOpr.translate(coder);
		coder.add(")->data[");
		iOpr.translate(coder);
		coder.add(\']\');
',
'IMBStoreArray' => '
		IMClass clazz = method.getIMClass("[B");
		coder.add_class(clazz.getAlias());
		coder.chk_array(this,aOpr,iOpr,method,bcPosition);
		coder.chk_ref(aOpr,method,bcPosition);
		coder.add("((byte_array_t*)");
		aOpr.translate(coder);
		coder.add(")->data[");
		iOpr.translate(coder);
		coder.add("]=");
		rvalue.translate(coder);
',
'IMCReadArray' => '
		IMClass clazz = method.getIMClass("[C");
		coder.add_class(clazz.getAlias());
		coder.chk_array(this,aOpr,iOpr,method,bcPosition);
		coder.add("((char_array_t*)");
		aOpr.translate(coder);
		coder.add(")->data[");
		iOpr.translate(coder);
		coder.add(\']\');
',
'IMCastD2F' => '
		coder.add("((");
		coder.add(BuilderOptions.FLOAT_T);
		coder.add(\')\');
		rOpr.translate(coder);
		coder.add(\')\');
',
'IMCastD2I' => '
		//coder.global_add_fkt("math/keso_math_double.c");
		coder.add("((");
		coder.add(BuilderOptions.INT_T);
		coder.add(\')\');
		//coder.add("keso_castD2I(");
		rOpr.translate(coder);
		coder.add(\')\');
',
'IMCastD2L' => '
		//coder.global_add_fkt("math/keso_math_double.c");
		coder.add("((");
		coder.add(BuilderOptions.LONG_T);
		coder.add(\')\');
		//coder.add("keso_castD2L(");
		rOpr.translate(coder);
		coder.add(\')\');
',
'IMCastF2D' => '
		coder.add("((");
		coder.add(BuilderOptions.DOUBLE_T);
		coder.add(\')\');
		rOpr.translate(coder);
		coder.add(\')\');
',
'IMCastF2I' => '
		coder.add("((");
		coder.add(BuilderOptions.INT_T);
		coder.add(\')\');
		rOpr.translate(coder);
		coder.add(\')\');
',
'IMCastF2L' => '
		coder.add("((");
		coder.add(BuilderOptions.LONG_T);
		coder.add(\')\');
		//coder.add("keso_castF2L(");
		rOpr.translate(coder);
		coder.add(\')\');
',
'IMCastI2B' => '
		coder.add("((");
		coder.add(BuilderOptions.BYTE_T);
		coder.add(\')\');
		rOpr.translate(coder);
		coder.add(\')\');
',
'IMCastI2C' => '
		coder.add("((");
		coder.add(BuilderOptions.CHAR_T);
		coder.add(\')\');
		rOpr.translate(coder);
		coder.add(\')\');
',
'IMCastI2D' => '
		coder.add("((");
		coder.add(BuilderOptions.DOUBLE_T);
		coder.add(\')\');
		rOpr.translate(coder);
		coder.add(\')\');
',
'IMCastI2F' => '
		coder.add("((");
		coder.add(BuilderOptions.FLOAT_T);
		coder.add(\')\');
		rOpr.translate(coder);
		coder.add(\')\');
',
'IMCastI2L' => '
		coder.add("((");
		coder.add(BuilderOptions.LONG_T);
		coder.add(\')\');
		rOpr.translate(coder);
		coder.add(\')\');
',
'IMCastI2S' => '
		coder.add("((");
		coder.add(BuilderOptions.SHORT_T);
		coder.add(\')\');
		rOpr.translate(coder);
		coder.add(\')\');
',
'IMCastL2D' => '
		coder.add("((");
		coder.add(BuilderOptions.DOUBLE_T);
		coder.add(\')\');
		rOpr.translate(coder);
		coder.add(\')\');
',
'IMCastL2F' => '
		coder.add("((");
		coder.add(BuilderOptions.FLOAT_T);
		coder.add(\')\');
		rOpr.translate(coder);
		coder.add(\')\');
',
'IMCastL2I' => '
		coder.add("((");
		coder.add(BuilderOptions.INT_T);
		coder.add(\')\');
		rOpr.translate(coder);
		coder.add(\')\');
',
'IMCaughtException' => '
		coder.add("KESO_PENDING_EXCEPTION; KESO_PENDING_EXCEPTION=(object_t*)0");
',
'IMCheckCast' => '
		IMClass clazz = method.getIMClass(cpEntry.getClassName());
		coder.add_class(clazz.getAlias());

		if (clazz.isInterface()) {
			coder.add("CHK_IFACE_ID");
			coder.add(clazz.getClassTypeID());
			coder.add(\'(\');
			rOpr.translate(coder);
			coder.add(\')\');
		} else {
			ClassTypeInfo type = clazz.getClassTypeInfo();
			if (rOpr.isChecked(coder) && !type.hasSubTypes()) {
				coder.add("(keso_checkcast_fast(");
				coder.add(clazz.getClassID());
				coder.add(\',\');
			} else {
				coder.add("(keso_checkcast(");
				coder.add(clazz.getClassID());
				coder.add(\',\');
				if (opts.hasOption("inline_checkcast")) {
					coder.add(clazz.getAlias().toUpperCase());
					coder.add("_RANGE,");
				}
			} 
			coder.add("(object_t*)");
			rOpr.translate(coder);
			coder.add("))");
		}
',
'IMAdd' => '
		coder.add(\'(\');
		lOpr.translate(coder);
		coder.add(\'+\');
		rOpr.translate(coder);
		coder.add(\')\');
',
'IMDConstant' => '
    		// toString() adds ".0" correctly
    		// NaN and Infinity should be NAN and INFINITY for C => toUpperCase()
		coder.global_add_fkt("math/keso_math_double.c");
		coder.add(\'(\');
    		coder.add(Double.toString(value).toUpperCase());
		coder.add(\')\');
',
'IMDiv' => '
		// TODO: div zero test
		coder.add(\'(\');
		lOpr.translate(coder);
		coder.add(\'/\');
		rOpr.translate(coder);
		coder.add(\')\');
',
'IMDGCompare' => '
		coder.global_add_fkt("math/keso_math_double.c");
		coder.add("keso_dcmpg(");
		lOpr.translate(coder);
		coder.add(\',\');
		rOpr.translate(coder);
		coder.add(\')\');
',
'IMDLCompare' => '
		coder.global_add_fkt("math/keso_math_double.c");
		coder.add("keso_dcmpl(");
		lOpr.translate(coder);
		coder.add(\',\');
		rOpr.translate(coder);
		coder.add(\')\');
',
'IMMul' => '
		coder.add(\'(\');
		lOpr.translate(coder);
		coder.add(\'*\');
		rOpr.translate(coder);
		coder.add(\')\');
',
'IMNeg' => '
		coder.add(\'(\');
		rOpr.translate(coder);
		coder.add("* -1)");
',
'IMDReadArray' => '
		IMClass clazz = method.getIMClass("[D");
		coder.add_class(clazz.getAlias());
		coder.chk_array(this,aOpr,iOpr,method,bcPosition);
		coder.chk_ref(aOpr,method,bcPosition);
		coder.add("((double_array_t*)");
		aOpr.translate(coder);
		coder.add(")->data[");
		iOpr.translate(coder);
		coder.add(\']\');
',
'IMDRem' => '
		opts.warn("fmod not impl.");
		coder.add("keso_fmod(");
		lOpr.translate(coder);
		coder.add(\',\');
		rOpr.translate(coder);
		coder.add(\')\');
',
'IMDReturn' => '
		coder.add("return ");
		rvalue.translate(coder);
',
'IMDStoreArray' => '
		IMClass clazz = method.getIMClass("[D");
		coder.add_class(clazz.getAlias());
		coder.chk_array(this,aOpr,iOpr,method,bcPosition);
		coder.chk_ref(aOpr,method,bcPosition);
		coder.add("((double_array_t*)");
		aOpr.translate(coder);
		coder.add(")->data[");
		iOpr.translate(coder);
		coder.add("]=");
		rvalue.translate(coder);
',
'IMSub' => '
		coder.add(\'(\');
		lOpr.translate(coder);
		coder.add(\'-\');
		rOpr.translate(coder);
		coder.add(\')\');
',
'IMEQConditionalBranch' => '
		coder.add("if (");
		lOpr.translate(coder);
		coder.add("==");
		rOpr.translate(coder);
		coder.add(") goto ");
		coder.add(targets[1].toLabel());
',
'IMFConstant' => '
    		// toString() adds ".0" correctly
    		// NaN and Infinity should be NAN and INFINITY for C => toUpperCase()
		coder.add(\'(\');
    		coder.add(Float.toString(value).toUpperCase());
		coder.add(\')\');
',
'IMFGCompare' => '
/*
   Notes The fcmpg and fcmpl instructions differ only in their treatment of a comparison
   involving NaN. NaN is unordered, so any float comparison fails if either or both
   of its operands are NaN. With both fcmpg and fcmpl available, any float comparison
   may be compiled to push the same result onto the operand stack whether the comparison
   fails on non-NaN values or fails because it encountered a NaN. For more information,
   see Section 7.5, "More Control Examples."
 */
		opts.warn("FCMPG may not be conform to java specification!");
		coder.global_add_fkt("math/keso_math_fcmpg.c");
		coder.add("keso_fcmpg(");
		lOpr.translate(coder);
		coder.add(\',\');
		rOpr.translate(coder);
		coder.add(\')\');
',
'IMFLCompare' => '
/*
   Notes The fcmpg and fcmpl instructions differ only in their treatment of a comparison
   involving NaN. NaN is unordered, so any float comparison fails if either or both
   of its operands are NaN. With both fcmpg and fcmpl available, any float comparison
   may be compiled to push the same result onto the operand stack whether the comparison
   fails on non-NaN values or fails because it encountered a NaN. For more information,
   see Section 7.5, "More Control Examples."
 */
		opts.warn("FCMPL may not be conform to java specification!");
		coder.global_add_fkt("math/keso_math_fcmpl.c");
		coder.add("keso_fcmpl(");
		lOpr.translate(coder);
		coder.add(\',\');
		rOpr.translate(coder);
		coder.add(\')\');
',
'IMFReadArray' => '
		IMClass clazz = method.getIMClass("[F");
		coder.add_class(clazz.getAlias());
		coder.chk_array(this,aOpr,iOpr,method,bcPosition);
		coder.chk_ref(aOpr,method,bcPosition);
		coder.add("((float_array_t*)");
		aOpr.translate(coder);
		coder.add(")->data[");
		iOpr.translate(coder);
		coder.add(\']\');
',
'IMFRem' => '
		opts.warn("fmodf not impl.");
		coder.add("keso_fmodf(");
		lOpr.translate(coder);
		coder.add(\',\');
		rOpr.translate(coder);
		coder.add(\')\');
',
'IMFReturn' => '
		coder.add("return ");
		rvalue.translate(coder);
',
'IMFStoreArray' => '
		IMClass clazz = method.getIMClass("[F");
		coder.add_class(clazz.getAlias());
		coder.chk_array(this,aOpr,iOpr,method,bcPosition);
		coder.chk_ref(aOpr,method,bcPosition);
		coder.add("((float_array_t*)");
		aOpr.translate(coder);
		coder.add(")->data[");
		iOpr.translate(coder);
		coder.add("]=");
		rvalue.translate(coder);
',
'IMGEConditionalBranch' => '
		coder.add("if (");
		lOpr.translate(coder);
		coder.add(">=");
		rOpr.translate(coder);
		coder.add(") goto ");
		coder.add(targets[1].toLabel());
',
'IMGTConditionalBranch' => '
		coder.add("if (");
		lOpr.translate(coder);
		coder.add(\'>\');
		rOpr.translate(coder);
		coder.add(") goto ");
		coder.add(targets[1].toLabel());
',
'IMGetField' => '
		IMClass clazz = method.getIMClass(cpEntry.getClassName());
		coder.add_class(clazz.getAlias());
		coder.chk_ref(rOpr,method,bcPosition);
		if (datatype==BCBasicDatatype.REFERENCE) {
			String fieldType = cpEntry.getMemberTypeDesc();
			IMClass fieldClass = method.getIMClass(fieldType.substring(1,fieldType.length()-1));
			if (fieldClass!=null && fieldClass.isMemoryType()) {
				ClassStore repository = method.getClassStore();
				repository.registerGlobalTranslationCallback("$mt_escape$", this);
				opts.warn("MT field escape! (not tested yet)");
				coder.add("keso_mt_escape(");
				coder.add(fieldClass.getClassID());
				coder.add(",");
				coder.add("(volatile void*)&(");
				coder.add(clazz.getAlias().toUpperCase());
				coder.add("_MT(");
				rOpr.translate(coder);
				coder.add(")->");
				coder.add(clazz.emitField(cpEntry.getMemberName()));
				coder.add("))");
				return;
			}
		} 
		coder.add_getField(clazz, rOpr, cpEntry.getMemberName());
',
'IMGetStatic' => '
		IMClass clazz = method.getIMClass(cpEntry.getClassName());
		coder.add_class(clazz.getAlias());
		coder.add_getStatic(imfield, datatype); 
',
'IMGoto' => '
		IMBasicBlock target = basicBlock.getSucc().at(0);
		if (target==null) {
			opts.critical("ignore blind goto in "+method.getAlias());
			coder.add("/* goto null */");
			return;
		}
		if (hasShortcut()) {
			if (opts.hasDbgSymbols()) {
				coder.add("/* goto ");
				coder.add(target);
				coder.add(" */");
			}
			return;
		} 

		coder.add("goto ");
		coder.add(target);
',
'IMBitAnd' => '
		coder.add(\'(\');
		lOpr.translate(coder);
		coder.add(\'&\');
		rOpr.translate(coder);
		coder.add(\')\');
',
'IMBitOr' => '
		coder.add(\'(\');
		lOpr.translate(coder);
		coder.add(\'|\');
		rOpr.translate(coder);
		coder.add(\')\');
',
'IMBitXor' => '
		coder.add(\'(\');
		lOpr.translate(coder);
		coder.add(\'^\');
		rOpr.translate(coder);
		coder.add(\')\');
',
'IMIConstant' => '
		if (opts.is8BitController()) {
			coder.add(\'(\');
			coder.add(value);
			coder.add(\')\');
		} else {
			coder.add("0x");
			coder.add(Integer.toHexString(value));
		}
',
'IMIReadArray' => '
		IMClass clazz = method.getIMClass("[I");
		coder.add_class(clazz.getAlias());
		coder.chk_array(this,aOpr,iOpr,method,bcPosition);
		coder.chk_ref(aOpr,method,bcPosition);
		coder.add("((int_array_t*)");
		aOpr.translate(coder);
		coder.add(")->data[");
		iOpr.translate(coder);
		coder.add(\']\');
',
'IMIRem' => '
		// TODO: div zero test
		coder.add(\'(\');
		lOpr.translate(coder);
		coder.add(\'%\');
		rOpr.translate(coder);
		coder.add(\')\');
',
'IMAReturn' => '
		coder.add("return ");
		rvalue.translate(coder);
',
'IMEpilog' => '
		if (opts.hasOption("exceptions")) {
			IMExceptionHandler[] catch_list = method.getExceptionHandler();
			if (catch_list!=null) {
				int[] ex_pos = method.getLocalExceptionCalls();
				coder.add_befor("\tgoto ");
				coder.add_befor(method.getAlias());
				coder.add_befor("_eh_end;\n");
				coder.add_befor("\t{\n");
				coder.add_befor("\t\tshort range;\n");
				if (ex_pos!=null) {
					for (int i=0;i<ex_pos.length;i++) {
						coder.add_befor(method.getLocalExceptionLabel(ex_pos[i]));
						coder.add_befor(":\n\t\trange=");
						coder.add_befor(ex_pos[i]);
						coder.add_befor(";\n\t\tgoto ");
						coder.add_befor(method.getAlias());
						coder.add_befor("_eh_start;\n");
					}
				}
				coder.add_befor(method.getAlias());
				coder.add_befor("_eh_start:\n");
				for (int i=0;i<catch_list.length;i++) {
					IMClass etype = catch_list[i].getIMClass();
					IMBasicBlock handler = catch_list[i].getHandler();
					coder.add_befor("\t\tif ((");
					coder.add_befor("range==");
					coder.add_befor(catch_list[i].getRangeID());
					coder.add_befor(")&&keso_instanceof(");
					coder.add_befor(etype.getClassID());
					coder.add_befor(",KESO_PENDING_EXCEPTION))\n\t\t\tgoto ");
					coder.add_befor(handler.toLabel());
					coder.add_befor(";\n");
				}
				coder.add_befor("\t}\n");
				coder.add_befor(method.getAlias());
				coder.add_befor("_eh_end:\n");
			}
		}
		if (method.getBasicReturnType()==BCBasicDatatype.VOID) {
			coder.add("return");
		} else if (method.getBasicReturnType()==BCBasicDatatype.REFERENCE) {
			coder.add("return (object_t*)");
			coder.add(var.toString());
		} else {
			coder.add("return ");
			coder.add(var.toString());
		}
',
'IMIReturn' => '
		coder.add("return ");
		rvalue.translate(coder);
',
'IMIShiftLeft' => '
		coder.add(\'(\');
		if (lOpr.getDatatype()!=BCBasicDatatype.INT) {
			coder.add("((");
			coder.add(BuilderOptions.INT_T);
			coder.add(")");
			lOpr.translate(coder);
			coder.add(")");
		} else {
			lOpr.translate(coder);
		}
		coder.add("<<");
		if (rOpr instanceof IMIConstant) {
			coder.add(((IMIConstant)rOpr).getValue() & 0x1f);
		} else {
			coder.add(\'(\');
			rOpr.translate(coder);
			coder.add(" & 0x1f)"); // Don\'t forget to mask to only 5 bits!
		}
		coder.add(\')\');
',
'IMIShiftRight2' => '

		if (opts.is8BitController() && lOpr.isByte()) {
			// TODO: This is experimental code.
			// better use IMBShiftRight2 etc. in the future.
			coder.add("((jint)((jubyte)");
			lOpr.translate(coder);
			coder.add(">>");
			if (rOpr instanceof IMIConstant) {
				coder.add(((IMIConstant)rOpr).getValue() & 0x1f);
			} else {
				coder.add(\'(\');
				rOpr.translate(coder);
				coder.add(" & 0x1f)"); // Don\'t forget to mask to only 5 bits!
			}
			coder.add("))");
		} else {
			// By casting to unsigned long we make sure a possible sign is ignored
			// unsigned shift right (unter linux mit dem gcc getestet!)
			coder.add("((jint)((juint)");
			lOpr.translate(coder);
			coder.add(">>");
			if (rOpr instanceof IMIConstant) {
				coder.add(((IMIConstant)rOpr).getValue() & 0x1f);
			} else {
				coder.add(\'(\');
				rOpr.translate(coder);
				coder.add(" & 0x1f)"); // Don\'t forget to mask to only 5 bits!
			}
			coder.add("))");
		}
',
'IMIShiftRight' => '
		// TODO Problem, wenn auf Plattform Vorzeichen nicht beachtet!!!
		coder.add(\'(\');
		lOpr.translate(coder);
		coder.add(">>");
		if (rOpr instanceof IMIConstant) {
			coder.add(((IMIConstant)rOpr).getValue() & 0x1f);
		} else {
			coder.add(\'(\');
			rOpr.translate(coder);
			coder.add(" & 0x1f)"); // Don\'t forget to mask to only 5 bits!
		}
		coder.add(\')\');
',
'IMIStoreArray' => '
		IMClass clazz = method.getIMClass("[I");
		coder.add_class(clazz.getAlias());
		coder.chk_array(this,aOpr,iOpr,method,bcPosition);
		coder.chk_ref(aOpr,method,bcPosition);
		coder.add("((int_array_t*)");
		aOpr.translate(coder);
		coder.add(")->data[");
		iOpr.translate(coder);
		coder.add("]=");
		rvalue.translate(coder);
',
'IMInstanceOf' => '
		IMClass clazz = method.getIMClass(cpEntry.getClassName());
		coder.add_class(clazz.getAlias());

		if (clazz.isInterface()) {
			coder.add("INSTANCEOF_IFACE_ID");
			coder.add(clazz.getClassTypeID());
			coder.add(\'(\');
			rOpr.translate(coder);
			coder.add(\')\');
		} else {
			ClassTypeInfo type = clazz.getClassTypeInfo();
			if (rOpr.isChecked(coder) && !type.hasSubTypes()) {
				coder.add("keso_instanceof_fast(");
				coder.add(clazz.getClassID());
				coder.add(\',\');
			} else {
				coder.add("keso_instanceof(");
				coder.add(clazz.getClassID());
				coder.add(\',\');
				if (opts.hasOption("inline_checkcast")) {
					coder.add(clazz.getAlias().toUpperCase());
					coder.add("_RANGE,");
				}
			} 
			coder.add("(object_t*)");
			rOpr.translate(coder);
			coder.add(\')\');
		}
',
'IMInvokeInterface' => '
		IMMethod callee = method.findMethod(cpEntry);	

		if (opts.hasOption("_X_dbg_astack")) showEscape(coder);

		if (!joinPoints.affectInvokeInterface(this,method,callee,obj,args,coder)) { 
			IMClass clazz = method.getIMClass(cpEntry.getClassName());
			coder.add_class(clazz.getAlias());

			coder.chk_ref(obj,method,bcPosition);
			coder.vcall(frame, callee, obj, args);
			method.emitTestException(coder, callee, bcPosition);
		}
',
'IMInvokeSpecial' => '
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
',
'IMInvokeStatic' => '
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
',
'IMInvokeVirtual' => '
		IMMethod callee = method.findMethod(cpEntry);	

		if (opts.hasOption("_X_dbg_astack")) showEscape(coder);

		if (!joinPoints.affectInvokeVirtual(this,method,callee,obj,args,coder)) {
			IMClass clazz = method.getIMClass(cpEntry.getClassName());
			coder.add_class(clazz.getAlias());

			coder.chk_ref(obj,method,bcPosition);
			coder.vcall(frame, callee, obj, args);
			method.emitTestException(coder, callee, bcPosition);
		}
',
'IMLCompare' => '
		//coder.add("keso_lcmp(");
		coder.add("(");
		lOpr.translate(coder);
		//coder.add(\',\');
		coder.add(\'-\');
		rOpr.translate(coder);
		coder.add(\')\');

',
'IMLConstant' => '
		coder.add(\'(\');
    		coder.add(Long.toString(value));
		coder.add("LL");
		coder.add(\')\');
',
'IMLEConditionalBranch' => '
		coder.add("if (");
		lOpr.translate(coder);
		coder.add("<=");
		rOpr.translate(coder);
		coder.add(") goto ");
		coder.add(targets[1].toLabel());
',
'IMLReadArray' => '
		IMClass clazz = method.getIMClass("[J");
		coder.add_class(clazz.getAlias());
		coder.chk_array(this,aOpr,iOpr,method,bcPosition);
		coder.chk_ref(aOpr,method,bcPosition);
		coder.add("((long_array_t*)");
		aOpr.translate(coder);
		coder.add(")->data[");
		iOpr.translate(coder);
		coder.add(\']\');
',
'IMLRem' => '
		// TODO: div zero test
		coder.add(\'(\');
		lOpr.translate(coder);
		coder.add(\'%\');
		rOpr.translate(coder);
		coder.add(\')\');
',
'IMLReturn' => '
		coder.add("return ");
		rvalue.translate(coder);
',
'IMLShiftLeft' => '
		coder.add(\'(\');
		lOpr.translate(coder);
		coder.add("<<(");
		rOpr.translate(coder);
		coder.add(" & 0x3f))"); // Don\'t forget to mask to only 6 bits!
',
'IMLShiftRight2' => '
      		// By casting to unsigned long we make sure a possible sign is ignored
		coder.add("((jlong)((julong)");
		lOpr.translate(coder);
		coder.add(">>(");
		rOpr.translate(coder);
		coder.add(" & 0x3f)))"); // Don\'t forget to mask to only 6 bits!
',
'IMLShiftRight' => '
		// TODO Problem, wenn auf Plattform Vorzeichen nicht beachtet!!!
		coder.add(\'(\');
		lOpr.translate(coder);
		coder.add(">>(");
		rOpr.translate(coder);
		coder.add(" & 0x3f))"); // Don\'t forget to mask to only 6 bits!
',
'IMLStoreArray' => '
		IMClass clazz = method.getIMClass("[J");
		coder.add_class(clazz.getAlias());
		coder.chk_array(this,aOpr,iOpr,method,bcPosition);
		coder.chk_ref(aOpr,method,bcPosition);
		coder.add("((long_array_t*)");
		aOpr.translate(coder);
		coder.add(")->data[");
		iOpr.translate(coder);
		coder.add("]=");
		rvalue.translate(coder);
',
'IMLTConditionalBranch' => '
		coder.add("if (");
		lOpr.translate(coder);
		coder.add(\'<\');
		rOpr.translate(coder);
		coder.add(") goto ");
		coder.add(targets[1].toLabel());
',
'IMLookupSwitch' => '
		coder.add("switch (");
   		operant.translate(coder);
		coder.addln(") {");

		for (int i=1;i<targets.length;i++) {
			coder.add("\tcase ");
			coder.add(keys[i-1]);
			coder.add(": goto ");
			coder.add(targets[i].toLabel());
			coder.addln(\';\');
		}
		coder.add("\tdefault: goto ");
		coder.add(targets[0].toLabel());
		coder.addln(\';\');

		coder.add(\'}\');
',
'IMMonitor' => '
		if (bytecode==0xc2) {
			coder.add("KESO_LOCK(");
		} else {
			coder.add("KESO_UNLOCK(");
		}
		operant.translate(coder);
		coder.add(")");
',
'IMNEConditionalBranch' => '
		coder.add("if (");
		lOpr.translate(coder);
		coder.add("!=");
		rOpr.translate(coder);
		coder.add(") goto ");
		coder.add(targets[1].toLabel());
',
'IMNewArray' => '
		coder.add_class(aclass.getAlias());

		if (!method.getMethodName().equals("<clinit>") &&
			 opts.getGlobalHeap().allocationCanBlock()) 
					method.getMethodFrame().codeEOLL(coder);

		if (why_escape!=null) { 
			coder.add_befor("\t/* escaping: ");
			coder.add_befor(why_escape.toReadableString());
			coder.add_befor(" (array) */\n");
		} 
		if (does_not_escape) {
			coder.add_befor("\t/* no escaping: ");
			coder.add_befor(toReadableString());
			coder.add_befor(" (array) */\n");
		}
		coder.add("keso_alloc_");
		coder.add(aclass.getAlias());
		coder.add("(");
		size.translate(coder);	
		coder.add(\')\');
',
'IMNew' => '
		IMClass clazz = method.getIMClass(cpEntry.getClassName());
		coder.add_class(clazz.getAlias());

		if (method.getMethodName().equals("<clinit>")) {
			coder.add_allocStaticObject(clazz, method.getIdentifier()+"_"+getBCPosition());
		} else {
			if (does_not_escape) {
				if (!opts.getGlobalHeap().needGCInfo() && clazz.getObjRefFieldCount()==0) {
					coder.add_allocStackObjectInlined(clazz);
				} else {
					ClassStore repository = method.getClassStore();
					repository.registerGlobalTranslationCallback("$keso_alloc_stack$", this);
					coder.add_allocStackObject(clazz);
				}
			} else {
				if (why_escape!=null) { 
					coder.add_befor("\t/* escaping: ");
					coder.add_befor(why_escape.toReadableString());
					coder.add_befor(" */\n");
				}
				if (opts.getGlobalHeap().allocationCanBlock()) 
					method.getMethodFrame().codeEOLL(coder);
				coder.add_allocObject(clazz);
			}
		}
',
'IMNewMultiArray' => '
		IMClass clazz = method.getIMClass(cpEntry.getClassName());
		coder.add_class(clazz.getAlias());
		coder.add_class(aclass.getAlias());

		if (!method.getMethodName().equals("<clinit>") &&
			 opts.getGlobalHeap().allocationCanBlock()) 
				method.getMethodFrame().codeEOLL(coder);

		if (why_escape!=null) { 
			coder.add_befor("\t/* escaping: ");
			coder.add_befor(why_escape.toReadableString());
			coder.add_befor(" (array) */\n");
		} 
		if (does_not_escape) {
			coder.add_befor("\t/* no escaping: ");
			coder.add_befor(toReadableString());
			coder.add_befor(" (array) */\n");
		}

		coder.add("keso_allocMultiArray(");
		coder.add(clazz.getClassID());
		coder.add(\',\');
		int len = oprs.length;
		coder.add(len);
		for (int i=1;i<=len;i++) {
			coder.add(\',\');
			oprs[len-i].translate(coder);
		}

		coder.add(\')\');
',
'IMNewObjArray' => '
		IMClass clazz = method.getIMClass(cpEntry.getClassName());
		if (clazz==null) throw new CompileException("Class not found! "+cpEntry.getClassName());

		coder.add_class(clazz.getAlias());
		coder.add_class(aclass.getAlias());

		if (!method.getMethodName().equals("<clinit>") &&
			 opts.getGlobalHeap().allocationCanBlock()) 
				method.getMethodFrame().codeEOLL(coder);

		if (why_escape!=null) { 
			coder.add_befor("\t/* escaping: ");
			coder.add_befor(why_escape.toReadableString());
			coder.add_befor(" (array) */\n");
		} 
		if (does_not_escape) {
			coder.add_befor("\t/* no escaping: ");
			coder.add_befor(toReadableString());
			coder.add_befor(" (array) */\n");
		}

		coder.add("keso_alloc_object_array(");
		coder.add(clazz.getClassID());
		coder.add(\',\');
		size.translate(coder);	
		coder.add(\')\');
',
'IMNop' => ' return; ',
'IMNullConstant' => '
		coder.add("((object_t*)0)");
',
'IMPutField' => '
		IMClass clazz = method.getIMClass(cpEntry.getClassName());
		coder.add_class(clazz.getAlias());
		coder.chk_ref(obj,method,bcPosition);
		if (datatype==BCBasicDatatype.REFERENCE) {
			String fieldType = cpEntry.getMemberTypeDesc();
			if (fieldType.charAt(0)==\'L\') {
				IMClass fieldClass = method.getIMClass(fieldType.substring(1,fieldType.length()-1));
				if (fieldClass==null) 
					opts.warn("class not found: "+fieldType);
				if (fieldClass!=null && fieldClass.isMemoryType()) {
					opts.warn("memory type store ignored!");
					coder.add_comment("memory type store ignored!");
					obj.translate(coder);
					return;
				}
			}
		}
		if (initial_write) {
			coder.add_befor("/* no write barrier needed becase "+obj.toReadableString()+"==null */");
			coder.add_putField_NoWrBr(clazz, obj, cpEntry.getMemberName(), datatype, rvalue); 
		} else {
			coder.add_putField(clazz, obj, cpEntry.getMemberName(), datatype, rvalue); 
		}
',
'IMPutStatic' => '
		IMClass clazz = method.getIMClass(cpEntry.getClassName());
		coder.add_class(clazz.getAlias());
		coder.add_putStatic(imfield,datatype,rvalue);
',
'IMSReadArray' => '
		IMClass clazz = method.getIMClass("[S");
		coder.add_class(clazz.getAlias());
		coder.chk_array(this,aOpr,iOpr,method,bcPosition);
		coder.chk_ref(aOpr,method,bcPosition);
		coder.add("((short_array_t*)");
		aOpr.translate(coder);
		coder.add(")->data[");
		iOpr.translate(coder);
		coder.add(\']\');
',
'IMSStoreArray' => '
		IMClass clazz = method.getIMClass("[S");
		coder.add_class(clazz.getAlias());
		coder.chk_array(this,aOpr,iOpr,method,bcPosition);
		coder.chk_ref(aOpr,method,bcPosition);
		coder.add("((short_array_t*)");
		aOpr.translate(coder);
		coder.add(")->data[");
		iOpr.translate(coder);
		coder.add("]=");
		rvalue.translate(coder);
',
'IMTableSwitch' => '
		coder.add("switch (");
   		operant.translate(coder);
		coder.addln(") {");

		for (int i=1;i<targets.length;i++) {
			coder.add("\tcase ");
			coder.add(low+(i-1));
			coder.add(": goto ");
			coder.add(targets[i].toLabel());
			coder.addln(\';\');
		}
		coder.add("\tdefault: goto ");
		coder.add(targets[0].toLabel());
		coder.addln(\';\');

		coder.add(\'}\');
',
'IMThrow' => '
		if (opts.hasOption("exceptions")) {
			String label;
			coder.add("KESO_PENDING_EXCEPTION = ");
			exception.translate(coder);
			coder.add("; ");
			if ((label=method.lookupExceptionHandler(null, bcPosition))!=null) {
				coder.add("goto ");
				coder.add(label);
			} else {
				switch (method.getBasicReturnType()) {
					case BCBasicDatatype.VOID:
						coder.add("return");
						break;
					default:
						coder.add("return 0");
				}
			}
		} else {
			coder.add("while (1) { __asm__ __volatile__ (\"nop\"); }");
		}
',
'IMVReturn' => '
		coder.add("return");
',
);
