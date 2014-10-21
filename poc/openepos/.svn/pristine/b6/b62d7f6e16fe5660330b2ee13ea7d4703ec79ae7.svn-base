
$ssa_optimize_binop = "
		rOpr = rOpr.ssa_optimize();
		lOpr = lOpr.ssa_optimize();

		if (lOpr.isConstant() && rOpr.isConstant()) {
			opts.vverbose(\"++ folding c\%BIN_OP\%c \"+toReadableString());
			IMConstant clOpr = lOpr.nodeToConstant();
			IMConstant crOpr = rOpr.nodeToConstant();
			clOpr.set\%UJTYPE\%Value(clOpr.get\%UJTYPE\%Value()\%BIN_OP\%crOpr.get\%UJTYPE\%Value());
			return clOpr;
		}
";

$constant_swap = "
		if (lOpr.isConstant()) {
			IMNode swap = lOpr;
			lOpr = rOpr;
			rOpr = swap;
		}
";

%ssa_neutral_elem=(
    'IMIShiftLeft' => '0',
    'IMIShiftRight' => '0',
    'IMIShiftRight2' => '0',
    'IMIBitOr' => '0',
    'IMIBitXor' => '0',
    'IMISub' => '0',
    'IMIAdd' => '0',
    'IMIMul' => '1',
    'IMIDiv' => '1',
    'IMLShiftLeft' => '0L',
    'IMLShiftRight' => '0L',
    'IMLShiftRight2' => '0L',
    'IMLBitOr' => '0L',
    'IMLBitXor' => '0L',
    'IMLSub' => '0L',
    'IMLAdd' => '0L',
    'IMLMul' => '1L',
    'IMLDiv' => '1L',
);

%ssa_clear_elem=(
    'IMIBitAnd' => '0',
    'IMIMul' => '0',
    'IMIRem' => '1',
    'IMLBitAnd' => '0L',
    'IMLMul' => '0L',
);

$ssa_optimize_method = "
		if (obj!=null) obj = obj.ssa_optimize();

		for (int i=0;i<args.length;i++) {
		    if (args[i]!=null) args[i] = args[i].ssa_optimize();
		}

		IMNode replace = method.getJoinPoints().affectIMInvoke(this, method, method.findMethod(cpEntry), obj, args); 
		if (replace!=null && replace!=this) return replace;
";

$ssa_optimize_marray = "
		for (int i=0;i<oprs.length;i++) {
		    if (oprs[i]!=null) oprs[i]=oprs[i].ssa_optimize();
		}
";

%ssa_condition=(
    'IMEQConditionalBranch' => '==',
    'IMGEConditionalBranch' => '>=',
    'IMGTConditionalBranch' => '>',
    'IMLEConditionalBranch' => '<=',
    'IMLTConditionalBranch' => '<',
    'IMNEConditionalBranch' => '!=',
);

$ssa_optimize_conditional = "
\t\trOpr = rOpr.ssa_optimize();
\t\tlOpr = lOpr.ssa_optimize();
\t\tif (lOpr.isConstant() && rOpr.isConstant()) {
\t\t\topts.verbose(\"++ todo fold conditional branch\");
\t\t\t\tif (opts.hasOption(\"ssa_fold_cbranch\")) {
\t\t\t\tint lvalue = lOpr.nodeToConstant().getIntValue();
\t\t\t\tint rvalue = rOpr.nodeToConstant().getIntValue();
\t\t\t\tIMBasicBlockList succ = basicBlock.getSucc(); 
\t\t\t\tif (lvalue\%CONDITION\%rvalue) {
\t\t\t\t\topts.verbose(\"++ todo fold \"+lvalue+\"\%CONDITION\%\"+rvalue+\" is true\");
\t\t\t\t\tIMNode go = method.createGoto(succ.at(1), bcPosition);
\t\t\t\t\tbasicBlock.unlinkSucc(0);
\t\t\t\t\treturn go;
\t\t\t\t} else {
\t\t\t\t\topts.verbose(\"++ todo fold \"+lvalue+\"\%CONDITION\%\"+rvalue+\" is false\");
\t\t\t\t\tIMNode go = method.createGoto(succ.at(0), bcPosition);
\t\t\t\t\tbasicBlock.unlinkSucc(1);
\t\t\t\t\treturn go;
\t\t\t\t}
\t\t\t}
\t\t}
		boolean use_cmp_fkt = opts.hasOption(\"use_cmp_fkt\");
		if (!use_cmp_fkt && rOpr.isConstant() && lOpr instanceof IMCompare) {
			if (rOpr instanceof IMIConstant && rOpr.nodeToConstant().getIntValue()==0) {
				IMCompare cmp = (IMCompare)lOpr;
				IMNode clOpr = cmp.getLeftNode(); 
				IMNode crOpr = cmp.getRightNode(); 

				if (clOpr.getDatatype() == BCBasicDatatype.DOUBLE) {
					IMDSub sub = new IMDSub(method, -1, bcPosition);
					sub.setLeftNode(clOpr);
					sub.setRightNode(crOpr);
					lOpr = sub;
				}

				if (clOpr.getDatatype() == BCBasicDatatype.FLOAT) {
					IMFSub sub = new IMFSub(method, -1, bcPosition);
					sub.setLeftNode(clOpr);
					sub.setRightNode(crOpr);
					lOpr = sub;
				}
			}
		}
";
$ssa_optimize_tswitch = "
\t\toperant = operant.ssa_optimize();
\t\tif (operant.isConstant()) {
\t\t\topts.verbose(\"++ todo fold table switch\");
\t\t}
";
$ssa_optimize_lswitch = "
\t\toperant = operant.ssa_optimize();
\t\tif (operant.isConstant()) {
\t\t\topts.verbose(\"++ todo fold lookup switch\");
\t\t}
";
$ssa_optimize_unop = "
\t\trOpr = rOpr.ssa_optimize();
\t\tif (rOpr.isConstant()) {
\t\t\topts.verbose(\"++ todo fold unop \"+this.toReadableString());
\t\t}
";
$ssa_optimize_rarray = "
\t\taOpr = aOpr.ssa_optimize();
\t\tiOpr = iOpr.ssa_optimize();
";
$ssa_optimize_sarray = "
$ssa_optimize_rarray
\t\trvalue = rvalue.ssa_optimize();
";

$ssa_optimize_var_store = "
		operant = operant.ssa_optimize();
		if (var.hasNoUses()) {
			if (!operant.isPureNode()) {
				opts.vverbose(\"++ no store \"+toReadableString());
				return operant;
			}
			opts.vverbose(\"++ remove \"+toReadableString());
			IMDeadCodeVisitor visitor = new IMDeadCodeVisitor(method);
			operant.visitNodes(visitor);
			return null;
		}

		if (opts.hasOption(\"ssa_alias_prop\")) {
			if (datatype==BCBasicDatatype.REFERENCE) {
				if (operant instanceof IMGetField || operant instanceof IMGetStatic) {
					method.registerAlias(basicBlock, var, operant);
				}
			}

			if (opts.hasOption(\"_X_ssa_alias_prop_all\")) {
				if (operant instanceof IMGetField || operant instanceof IMGetStatic) {
					method.registerAlias(basicBlock, var, operant);
				}
			}
		}
";

$ssa_optimize_var = "
		if (var==null) return this;

		IMStoreLocalVariable store = (IMStoreLocalVariable)var.getDefinedStatement();
		if (store==null) {
			return this;
		}

		// bad idea!
		// IMNode alias = store.getOperant().ssa_optimize();
		IMNode alias = store.getOperant();

		// constant propagation
		if (alias.isConstant() && !opts.hasOption(\"ssa_no_const_prop\")) {
			opts.vverbose(\"++ propagate \"+alias.toReadableString()+\"->\"+var);
			var.delete_use(this);
			return alias.nodeToConstant().copy(null);
		}

		// copy propagation
		if (alias instanceof IMReadLocalVariable) {
			IMSlot nvar=((IMReadLocalVariable)alias).getIMSlot();
			opts.vverbose(\"++ propagate \"+nvar+\"->\"+var);
			var.delete_use(this);

			if (nvar==null) throw new CompileException(\"undefined value for \"+var);
			IMReadLocalVariable nread = method.readLocal(getBCPosition(), nvar);
			nvar.use(basicBlock, nread);

			return nread;
		}

		if (alias.isFinalStatic() && opts.hasOption(\"ssa_fwd_statics\")) {
			// only copy propagate single reads
			if (var.numberOfUses()==1) {
				//alias = alias.copy(null);
				//var.delete_use(this);
				return alias;
			}
		}

";


%ssa_optimize = (
    'IMArrayLength' => "
\t\tarray = array.ssa_optimize();
\t\tif (!opts.hasOption(\"ssa_no_array_len\")) {
\t\t\tint alength = array.getArrayLength(); 
\t\t\tif (alength!=-1) {
\t\t\t\topts.verbose(\"++ propagate constant array length \"+array+\"[\"+alength+\"].length()\");
\t\t\t\treturn method.createIMIConstant(alength, getBCPosition());
\t\t\t}
\t\t}
",
    'IMPhi' => "
    		IMConstant all_const=null;
		boolean all_const_b = true;
		for (int i=0;i<slots.length;i++) {
			IMNode node = slots[i];
			if (node==null) {
				opts.warn(\"undefined slot \"+i+\" \"+toReadableString());
				all_const_b = false;
				break;
			}

			if (node instanceof IMReadLocalVariable && node.getIMSlot()==null) {
				if (!opts.hasOption(\"ssa_no_omit_undef\")) {
					opts.vverbose(\"%% omit \"+toReadableString()+\"(slot \"+i+\" undefined!)\");
					for (int j=0;j<slots.length;j++) {
						IMSlot var = slots[j].getIMSlot();
						if (var!=null) var.delete_use(slots[j]);
					}
					switch (getDatatype()) {
						case BCBasicDatatype.BOOLEAN:
						case BCBasicDatatype.BYTE:
						case BCBasicDatatype.CHAR:
						case BCBasicDatatype.SHORT:
						case BCBasicDatatype.INT:
							return method.createIMIConstant(0,getBCPosition());
						case BCBasicDatatype.LONG:
							return method.createIMLConstant(0L,getBCPosition());
						case BCBasicDatatype.FLOAT:
							return method.createIMFConstant(0.0f,getBCPosition());
						case BCBasicDatatype.DOUBLE:
							return method.createIMDConstant(0.0,getBCPosition());
						case BCBasicDatatype.REFERENCE:
							return method.createIMNullConstant(getBCPosition());
						default:
							opts.warn(\"Unknown datatype for constant value. \"+getDatatype());
							opts.warn(toReadableString());
							throw new CompileException(\"Unknown datatype for constant value.\");
					}

				} 
				all_const_b = false;
				break;
			}

			slots[i] = slots[i].ssa_optimize();
			if (slots[i].isConstant()) {
				IMConstant cnode = slots[i].nodeToConstant();
				if (all_const==null) {
					all_const = cnode;
				} else if (!cnode.equals(all_const)) {
					all_const_b = false;
				}
			} else {
				all_const_b = false;
			}
		}

		if (all_const_b) {
			opts.vverbose(\"++ phi is constant \"+toReadableString()+\" \"+all_const.toReadableString());
			return all_const;
		}
",
    'IMCastD2F' => $ssa_optimize_unop,
    'IMCastD2I' => $ssa_optimize_unop,
    'IMCastD2L' => $ssa_optimize_unop,
    'IMCastF2D' => $ssa_optimize_unop,
    'IMCastF2I' => $ssa_optimize_unop,
    'IMCastF2L' => $ssa_optimize_unop,
    'IMCastI2B' => $ssa_optimize_unop,
    'IMCastI2C' => $ssa_optimize_unop,
    'IMCastI2D' => $ssa_optimize_unop,
    'IMCastI2F' => $ssa_optimize_unop,
    'IMCastI2L' => $ssa_optimize_unop,
    'IMCastI2S' => $ssa_optimize_unop,
    'IMCastL2D' => $ssa_optimize_unop,
    'IMCastL2F' => $ssa_optimize_unop,
    'IMCastL2I' => $ssa_optimize_unop,
    'IMCheckCast' => "
    		rOpr=rOpr.ssa_optimize();

		if (rOpr instanceof IMNullConstant) {
			return rOpr;
		}

		IMClass clazz = method.getIMClass(cpEntry.getClassName());
		if (rOpr.isTypeOf(clazz)) {
			opts.vverbose(\"++ fold checkcast \");
			return rOpr;
		}
", 
#    'IMDGCompare' => $ssa_optimize_binop,
#    'IMDLCompare' => $ssa_optimize_binop,
#    'IMFGCompare' => $ssa_optimize_binop,
#    'IMFLCompare' => $ssa_optimize_binop,
#    'IMLCompare' => $ssa_optimize_binop,
    'IMEQConditionalBranch' => $ssa_optimize_conditional,
    'IMGEConditionalBranch' => $ssa_optimize_conditional,
    'IMGTConditionalBranch' => $ssa_optimize_conditional,
    'IMLEConditionalBranch' => $ssa_optimize_conditional,
    'IMLTConditionalBranch' => $ssa_optimize_conditional,
    'IMNEConditionalBranch' => $ssa_optimize_conditional,
    'IMGetField' => "
		ClassStore repository = method.getClassStore();

		if (repository.omitField(cpEntry.getClassName(), cpEntry.getMemberTypeDesc(), cpEntry.getMemberName())) {
			opts.vverbose(\"++ omit get field\");
			switch (datatype) {
				case BCBasicDatatype.BOOLEAN:
				case BCBasicDatatype.BYTE:
				case BCBasicDatatype.CHAR:
				case BCBasicDatatype.SHORT:
				case BCBasicDatatype.INT:
					return method.createIMIConstant(0,bcPosition);
				case BCBasicDatatype.LONG:
					return method.createIMLConstant(0,bcPosition);
				case BCBasicDatatype.FLOAT:
					return method.createIMFConstant(0,bcPosition);
				case BCBasicDatatype.DOUBLE:
					return method.createIMDConstant(0,bcPosition);
				case BCBasicDatatype.REFERENCE:
					return method.createIMNullConstant(bcPosition);
				default:
					throw new CompileException(\"unkown datatype constant\");
			}
		} else {
			rOpr=rOpr.ssa_optimize();
		}

		if (opts.hasOption(\"ssa_alias_prop\")) {
			IMSlot var = method.lookupAlias(basicBlock, this);
			if (var!=null) {
				IMNode rd_var = method.readLocal(getBCPosition(), var);
				opts.verbose(\"++ forward commen get field \"+toReadableString());
				var.use(basicBlock, rd_var);
				return rd_var;
			}
		}
",
    'IMGetStatic' => "
		ClassStore repository = method.getClassStore();

		if (repository.omitStaticField(cpEntry.getClassName(), cpEntry.getMemberTypeDesc(), cpEntry.getMemberName())) {
			opts.vverbose(\"++ omit get field\");
			switch (datatype) {
				case BCBasicDatatype.BOOLEAN:
				case BCBasicDatatype.BYTE:
				case BCBasicDatatype.CHAR:
				case BCBasicDatatype.SHORT:
				case BCBasicDatatype.INT:
					return method.createIMIConstant(0,bcPosition);
				case BCBasicDatatype.LONG:
					return method.createIMLConstant(0,bcPosition);
				case BCBasicDatatype.FLOAT:
					return method.createIMFConstant(0,bcPosition);
				case BCBasicDatatype.DOUBLE:
					return method.createIMDConstant(0,bcPosition);
				case BCBasicDatatype.REFERENCE:
					return method.createIMNullConstant(bcPosition);
				default:
					throw new CompileException(\"unkown datatype constant\");
			}
		}

		IMNode obj = repository.forwardStaticField(method, cpEntry.getClassName(), cpEntry.getMemberName());
		if (obj!=null) {
			obj = obj.ssa_optimize();
			if (obj.isConstObject()) {
				opts.vverbose(\"++ forward get field \"+obj.toReadableString());
				return obj;
			}
		}

		if (opts.hasOption(\"ssa_alias_prop\") && datatype==BCBasicDatatype.REFERENCE) {
			IMSlot var = method.lookupAlias(basicBlock, this);
			if (var!=null) {
				IMNode rd_var = method.readLocal(getBCPosition(), var);
				opts.verbose(\"++ forward commen get field \"+toReadableString());
				var.use(basicBlock, rd_var);
				return rd_var;
			}
		}
",
    'IMInstanceOf' => '
		rOpr = rOpr.ssa_optimize();

		if (rOpr instanceof IMNullConstant) {
			opts.vverbose("+ fold instanceof => 0");
			return method.createIMIConstant(0, rOpr.getBCPosition());
		}

		IMClass clazz = method.getIMClass(cpEntry.getClassName());
		if (rOpr.isTypeOf(clazz)) {
			opts.vverbose("+ fold instanceof => 1");
			return method.createIMIConstant(1, rOpr.getBCPosition());
		}
',
    'IMInvokeInterface' => $ssa_optimize_method,
    'IMInvokeSpecial' => $ssa_optimize_method,
    'IMInvokeStatic' => $ssa_optimize_method,
    'IMInvokeVirtual' => $ssa_optimize_method,
    'IMLookupSwitch' => $ssa_optimize_lswitch,
    'IMMonitor' => "
",
    'IMNew' => $ssa_optimize_class,
    'IMNewArray' => $ssa_optimize_array,
    'IMNewMultiArray' => $ssa_optimize_marray,
    'IMNewObjArray' => $ssa_optimize_obj_array,
    'IMPutField' => "
		ClassStore repository = method.getClassStore();

		rvalue = rvalue.ssa_optimize();

		if (repository.omitField(cpEntry.getClassName(), cpEntry.getMemberTypeDesc(), cpEntry.getMemberName())) {
			opts.vverbose(\"++ omit put field\");
			if (rvalue instanceof IMNew) return null;
			if (rvalue instanceof IMNewArray) return null;
			if (rvalue instanceof IMNewObjArray) return null;
			if (rvalue instanceof IMNewMultiArray) return null;
			if (!rvalue.hasSideEffect()) return null;
			return rvalue;
		}

		obj = obj.ssa_optimize(); 

		if (opts.hasOption(\"ssa_alias_prop\") && datatype==BCBasicDatatype.REFERENCE) {
			if (rvalue instanceof IMAReadLocalVariable) {
				IMSlot var = ((IMAReadLocalVariable)rvalue).getIMSlot();
				method.registerAlias(basicBlock, var, this);
			} else {
				method.removeAlias(basicBlock, this);
			}
		}
",
    'IMPutStatic' => "
		ClassStore repository = method.getClassStore();

		rvalue = rvalue.ssa_optimize();

		if (repository.omitStaticField(cpEntry.getClassName(), cpEntry.getMemberTypeDesc(), cpEntry.getMemberName())) {
			opts.vverbose(\"++ omit put static\");
			if (rvalue instanceof IMNew) return null;
			if (rvalue instanceof IMNewArray) return null;
			if (rvalue instanceof IMNewObjArray) return null;
			if (rvalue instanceof IMNewMultiArray) return null;
			if (!rvalue.hasSideEffect()) return null;
			return rvalue;
		} 

		if (opts.hasOption(\"ssa_alias_prop\") && datatype==BCBasicDatatype.REFERENCE) {
			if (rvalue instanceof IMAReadLocalVariable) {
				IMSlot var = ((IMAReadLocalVariable)rvalue).getIMSlot();
				method.registerAlias(basicBlock, var, this);
			} else {
				method.removeAlias(basicBlock, this);
			}
		}
",
    'IMReturnSubroutine' => $ssa_optimize_var, 
    'IMEpilog' => '
		if (var==null) return this;
		IMStoreLocalVariable store = (IMStoreLocalVariable)var.getDefinedStatement();
		if (store==null) {
			return this;
		}

		// bad idea!
		// IMNode alias = store.getOperant().ssa_optimize();
		IMNode alias = store.getOperant();

		// copy propagation
		if (alias instanceof IMReadLocalVariable) {
			IMSlot nvar=((IMReadLocalVariable)alias).getIMSlot();
			opts.vverbose("++ propagate "+nvar+"->"+var);
			var.delete_use(this);
			var=nvar;
			nvar.use(basicBlock, this);

			return this;
		}
',
    'IMTableSwitch' => $ssa_optimize_tswitch,
    'IMThrow' => "
\t\tif (exception!=null) exception = exception.ssa_optimize();
",
    'IMAConstant' => $ssa_optimize_const_value,
    'IMAReadArray' => $ssa_optimize_rarray,
    'IMAReadLocalVariable' => "
$ssa_optimize_var
		if (!opts.hasOption(\"ssa_no_array_len\") && alength==-1) {
			alength = alias.getArrayLength(); 
			if (alength!=-1) opts.verbose(\"++ propagate constant array length \"+var+\"[\"+alength+\"]\");
		}
",
    'IMAStoreArray' => $ssa_optimize_sarray,
    'IMAStoreLocalVariable' => $ssa_optimize_var_store,
    'IMBReadArray' => $ssa_optimize_rarray,
    'IMBStoreArray' => $ssa_optimize_sarray,
    'IMCReadArray' => $ssa_optimize_rarray,
    'IMCStoreArray' => $ssa_optimize_sarray,
    'IMDAdd' => $ssa_optimize_binop,
    'IMDConstant' => $ssa_optimize_const_value,
    'IMDDiv' => $ssa_optimize_binop,
    'IMDMul' => $ssa_optimize_binop,
    'IMDNeg' => $ssa_optimize_unop,
    'IMDReadArray' => $ssa_optimize_rarray,
    'IMDReadLocalVariable' => $ssa_optimize_var,
    'IMDRem' => $ssa_optimize_binop,
    'IMDReturn' => $ssa_optimize_return,
    'IMDStoreArray' => $ssa_optimize_sarray,
    'IMDStoreLocalVariable' => $ssa_optimize_var_store,
    'IMDSub' => $ssa_optimize_binop,
    'IMFAdd' => $ssa_optimize_binop,
    'IMFConstant' => $ssa_optimize_const_value,
    'IMFDiv' => $ssa_optimize_binop,
    'IMFMul' => $ssa_optimize_binop,
    'IMFNeg' => $ssa_optimize_unop,
    'IMFReadArray' => $ssa_optimize_rarray,
    'IMFReadLocalVariable' => $ssa_optimize_var,
    'IMFRem' => $ssa_optimize_binop,
    'IMFReturn' => $ssa_optimize_return,
    'IMFStoreArray' => $ssa_optimize_sarray,
    'IMFStoreLocalVariable' => $ssa_optimize_var_store,
    'IMFSub' => $ssa_optimize_binop,
    'IMIAdd' => $ssa_optimize_binop,
    'IMIBitAnd' => $ssa_optimize_binop,
    'IMIBitOr' => $ssa_optimize_binop,
    'IMIBitXor' => $ssa_optimize_binop,
    'IMIConstant' => $ssa_optimize_const_value,
    'IMIDiv' => $ssa_optimize_binop,
    'IMIMul' => $ssa_optimize_binop,
    'IMINeg' => $ssa_optimize_unop,
    'IMIReadArray' => $ssa_optimize_rarray,
    'IMIReadLocalVariable' => $ssa_optimize_var,
    'IMIRem' => $ssa_optimize_binop,
    'IMIReturn' => $ssa_optimize_return,
    'IMAReturn' => $ssa_optimize_return,
    'IMIShiftLeft' => $ssa_optimize_binop,
    'IMIShiftRight' => $ssa_optimize_binop,
    'IMIShiftRight2' => $ssa_optimize_binop,
    'IMIStoreArray' => $ssa_optimize_sarray,
    'IMIStoreLocalVariable' => $ssa_optimize_var_store,
    'IMISub' => $ssa_optimize_binop,
    'IMLAdd' => $ssa_optimize_binop,
    'IMLBitAnd' => $ssa_optimize_binop,
    'IMLBitOr' => $ssa_optimize_binop,
    'IMLBitXor' => $ssa_optimize_binop,
    'IMLConstant' => $ssa_optimize_const_value,
    'IMLDiv' => $ssa_optimize_binop,
    'IMLMul' => $ssa_optimize_binop,
    'IMLNeg' => $ssa_optimize_unop,
    'IMLReadArray' => $ssa_optimize_rarray,
    'IMLReadLocalVariable' => $ssa_optimize_var,
    'IMLRem' => $ssa_optimize_binop,
    'IMLReturn' => $ssa_optimize_return,
    'IMLShiftLeft' => $ssa_optimize_binop,
    'IMLShiftRight' => $ssa_optimize_binop,
    'IMLShiftRight2' => $ssa_optimize_binop,
    'IMLStoreArray' => $ssa_optimize_sarray,
    'IMLStoreLocalVariable' => $ssa_optimize_var_store,
    'IMLSub' => $ssa_optimize_binop,
    'IMSReadArray' => $ssa_optimize_rarray,
    'IMSStoreArray' => $ssa_optimize_sarray,
    'IMVReturn' => $ssa_optimize_imnode,
);
