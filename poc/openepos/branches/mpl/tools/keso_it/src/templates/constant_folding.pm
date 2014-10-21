
$constant_fold_binop = "
		rOpr = rOpr.constant_folding();
		lOpr = lOpr.constant_folding();

		if (lOpr.isConstant() && rOpr.isConstant()) {
			opts.verbose(\"++ folding c\%BIN_OP\%c \"+toReadableString());
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

$constant_fold_method = "
		if (obj!=null) obj = obj.constant_folding();

		for (int i=0;i<args.length;i++) {
		    if (args[i]!=null) args[i] = args[i].constant_folding();
		}

		IMNode replace = method.getJoinPoints().affectIMInvoke(this, method, method.findMethod(cpEntry), obj, args); 
		if (replace!=null && replace!=this) return replace;
";

$constant_fold_marray = "
		for (int i=0;i<oprs.length;i++) {
		    if (oprs[i]!=null) oprs[i]=oprs[i].constant_folding();
		}
";

$constant_fold_conditional = "
\t\trOpr = rOpr.constant_folding();
\t\tlOpr = lOpr.constant_folding();
\t\tif (lOpr.isConstant() && rOpr.isConstant()) {
\t\t\topts.verbose(\"++ todo fold conditional branch\");
\t\t}
";
$constant_fold_tswitch = "
\t\toperant = operant.constant_folding();
\t\tif (operant.isConstant()) {
\t\t\topts.verbose(\"++ todo fold table switch\");
\t\t}
";
$constant_fold_lswitch = "
\t\toperant = operant.constant_folding();
\t\tif (operant.isConstant()) {
\t\t\topts.verbose(\"++ todo fold lookup switch\");
\t\t}
";
$constant_fold_unop = "
\t\trOpr = rOpr.constant_folding();
\t\tif (rOpr.isConstant()) {
\t\t\topts.verbose(\"++ todo fold unop\"+this.toReadableString());
\t\t}
";
$constant_fold_rarray = "
\t\taOpr = aOpr.constant_folding();
\t\tiOpr = iOpr.constant_folding();
";
$constant_fold_sarray = "
$constant_fold_rarray
\t\trvalue = rvalue.constant_folding();
";

$constant_fold_var_opr = "
\t\toperant = operant.constant_folding();
";

%constant_fold = (
    'IMArrayLength' => "
\t\tarray = array.constant_folding();
",
    'IMCastD2F' => $constant_fold_unop,
    'IMCastD2I' => $constant_fold_unop,
    'IMCastD2L' => $constant_fold_unop,
    'IMCastF2D' => $constant_fold_unop,
    'IMCastF2I' => $constant_fold_unop,
    'IMCastF2L' => $constant_fold_unop,
    'IMCastI2B' => $constant_fold_unop,
    'IMCastI2C' => $constant_fold_unop,
    'IMCastI2D' => $constant_fold_unop,
    'IMCastI2F' => $constant_fold_unop,
    'IMCastI2L' => $constant_fold_unop,
    'IMCastI2S' => $constant_fold_unop,
    'IMCastL2D' => $constant_fold_unop,
    'IMCastL2F' => $constant_fold_unop,
    'IMCastL2I' => $constant_fold_unop,
    'IMCheckCast' => "
    		rOpr=rOpr.constant_folding();

		if (rOpr instanceof IMNullConstant) {
			opts.verbose(\"++ fold checkcast NULL\");
			return rOpr;
		}

		IMClass clazz = method.getIMClass(cpEntry.getClassName());
		if (rOpr.isTypeOf(clazz)) {
			opts.verbose(\"++ fold checkcast (\"+cpEntry.getClassName()+\")\");
			return rOpr;
		}
", 
#    'IMDGCompare' => $constant_fold_binop,
#    'IMDLCompare' => $constant_fold_binop,
#    'IMFGCompare' => $constant_fold_binop,
#    'IMFLCompare' => $constant_fold_binop,
#    'IMLCompare' => $constant_fold_binop,
    'IMEQConditionalBranch' => $constant_fold_conditional,
    'IMGEConditionalBranch' => $constant_fold_conditional,
    'IMGTConditionalBranch' => $constant_fold_conditional,
    'IMLEConditionalBranch' => $constant_fold_conditional,
    'IMLTConditionalBranch' => $constant_fold_conditional,
    'IMNEConditionalBranch' => $constant_fold_conditional,
    'IMGetField' => "
		ClassStore repository = method.getClassStore();

		if (repository.omitField(cpEntry.getClassName(), cpEntry.getMemberTypeDesc(), cpEntry.getMemberName())) {
			opts.verbose(\"++ omit get field \"+cpEntry.getClassName()+\" \"+cpEntry.getMemberName());
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
					return this;
					//return method.createIMNullConstant(bcPosition);
				default:
					throw new CompileException(\"unkown datatype constant\");
			}
		} else {
			rOpr=rOpr.constant_folding();
		}
",
    'IMGetStatic' => "
		ClassStore repository = method.getClassStore();

		IMNode obj = repository.forwardStaticField(method, cpEntry.getClassName(), cpEntry.getMemberName());
		if (obj!=null) {
			obj = obj.constant_folding();
			if (obj.isConstObject()) {
				opts.verbose(\"++ forward get field \"+obj.toReadableString());
				return obj;
			}
		}

		if (repository.omitStaticField(cpEntry.getClassName(), cpEntry.getMemberTypeDesc(), cpEntry.getMemberName())) {
			opts.verbose(\"++ omit get static \"+cpEntry.getClassName()+\" \"+cpEntry.getMemberName());
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

",
    'IMInstanceOf' => '
		rOpr = rOpr.constant_folding();

		if (rOpr instanceof IMNullConstant) {
			opts.verbose("+ fold instanceof => 0");
			return method.createIMIConstant(0, rOpr.getBCPosition());
		}

		IMClass clazz = method.getIMClass(cpEntry.getClassName());
		if (rOpr.isTypeOf(clazz)) {
			opts.verbose("+ fold instanceof => 1");
			return method.createIMIConstant(1, rOpr.getBCPosition());
		}
',
    'IMInvokeInterface' => $constant_fold_method,
    'IMInvokeSpecial' => $constant_fold_method,
    'IMInvokeStatic' => $constant_fold_method,
    'IMInvokeVirtual' => $constant_fold_method,
    'IMLookupSwitch' => $constant_fold_lswitch,
    'IMMonitor' => "
",
    'IMNew' => $constant_fold_class, 
    'IMNewArray' => $constant_fold_array,
    'IMNewMultiArray' => $constant_fold_marray,
    'IMNewObjArray' => $constant_fold_obj_array,
    'IMPutField' => "
		ClassStore repository = method.getClassStore();

		rvalue = rvalue.constant_folding();

		if (repository.omitField(cpEntry.getClassName(), cpEntry.getMemberTypeDesc(), cpEntry.getMemberName())) {
			opts.verbose(\"++ omit put field\");
			if (!rvalue.hasSideEffect()) return null;
			return rvalue;
		}

		obj = obj.constant_folding(); 
",
    'IMPutStatic' => "
		ClassStore repository = method.getClassStore();

		rvalue = rvalue.constant_folding();

		if (repository.omitStaticField(cpEntry.getClassName(), cpEntry.getMemberTypeDesc(), cpEntry.getMemberName())) {
			opts.verbose(\"++ omit put static\");
			if (!rvalue.hasSideEffect()) return null;
			return rvalue;
		} 
",
    'IMReturnSubroutine' => $constant_fold_var, 
    'IMTableSwitch' => $constant_fold_tswitch,
    'IMThrow' => "
\t\tif (exception!=null) exception = exception.constant_folding();
",
    'IMAConstant' => $constant_fold_const_value,
    'IMAReadArray' => $constant_fold_rarray,
    'IMAReadLocalVariable' => $constant_fold_var,
    'IMAStoreArray' => $constant_fold_sarray,
    'IMAStoreLocalVariable' => $constant_fold_var_opr,
    'IMBReadArray' => $constant_fold_rarray,
    'IMBStoreArray' => $constant_fold_sarray,
    'IMCReadArray' => $constant_fold_rarray,
    'IMCStoreArray' => $constant_fold_sarray,
    'IMDAdd' => $constant_fold_binop,
    'IMDConstant' => $constant_fold_const_value,
    'IMDDiv' => $constant_fold_binop,
    'IMDMul' => $constant_fold_binop,
    'IMDNeg' => $constant_fold_unop,
    'IMDReadArray' => $constant_fold_rarray,
    'IMDReadLocalVariable' => $constant_fold_var,
    'IMDRem' => $constant_fold_binop,
    'IMDReturn' => $constant_fold_return,
    'IMEpilog' => $constant_fold_imnode,
    'IMDStoreArray' => $constant_fold_sarray,
    'IMDStoreLocalVariable' => $constant_fold_var_opr,
    'IMDSub' => $constant_fold_binop,
    'IMFAdd' => $constant_fold_binop,
    'IMFConstant' => $constant_fold_const_value,
    'IMFDiv' => $constant_fold_binop,
    'IMFMul' => $constant_fold_binop,
    'IMFNeg' => $constant_fold_unop,
    'IMFReadArray' => $constant_fold_rarray,
    'IMFReadLocalVariable' => $constant_fold_var,
    'IMFRem' => $constant_fold_binop,
    'IMFReturn' => $constant_fold_return,
    'IMFStoreArray' => $constant_fold_sarray,
    'IMFStoreLocalVariable' => $constant_fold_var_opr,
    'IMFSub' => $constant_fold_binop,
    'IMIAdd' => $constant_fold_binop,
    'IMIBitAnd' => $constant_fold_binop,
    'IMIBitOr' => $constant_fold_binop,
    'IMIBitXor' => $constant_fold_binop,
    'IMIConstant' => $constant_fold_const_value,
    'IMIDiv' => $constant_fold_binop,
    'IMIMul' => $constant_fold_binop,
    'IMINeg' => $constant_fold_unop,
    'IMIReadArray' => $constant_fold_rarray,
    'IMIReadLocalVariable' => $constant_fold_var,
    'IMIRem' => $constant_fold_binop,
    'IMIReturn' => $constant_fold_return,
    'IMAReturn' => $constant_fold_return,
    'IMIShiftLeft' => $constant_fold_binop,
    'IMIShiftRight' => $constant_fold_binop,
    'IMIShiftRight2' => $constant_fold_binop,
    'IMIStoreArray' => $constant_fold_sarray,
    'IMIStoreLocalVariable' => $constant_fold_var_opr,
    'IMISub' => $constant_fold_binop,
    'IMLAdd' => $constant_fold_binop,
    'IMLBitAnd' => $constant_fold_binop,
    'IMLBitOr' => $constant_fold_binop,
    'IMLBitXor' => $constant_fold_binop,
    'IMLConstant' => $constant_fold_const_value,
    'IMLDiv' => $constant_fold_binop,
    'IMLMul' => $constant_fold_binop,
    'IMLNeg' => $constant_fold_unop,
    'IMLReadArray' => $constant_fold_rarray,
    'IMLReadLocalVariable' => $constant_fold_var,
    'IMLRem' => $constant_fold_binop,
    'IMLReturn' => $constant_fold_return,
    'IMLShiftLeft' => $constant_fold_binop,
    'IMLShiftRight' => $constant_fold_binop,
    'IMLShiftRight2' => $constant_fold_binop,
    'IMLStoreArray' => $constant_fold_sarray,
    'IMLStoreLocalVariable' => $constant_fold_var_opr,
    'IMLSub' => $constant_fold_binop,
    'IMSReadArray' => $constant_fold_rarray,
    'IMSStoreArray' => $constant_fold_sarray,
    'IMVReturn' => $constant_fold_imnode,
);
