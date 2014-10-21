$costs_epilog = "\n\t\treturn 0;";
$costs_const_value = "\n\t\treturn 3;";
$costs_dconst_value = "\n\t\treturn 5;";
$costs_var = "\n\t\treturn 3;";
$costs_binop = "\n\t\treturn lOpr.costs() + rOpr.costs() + 5;";
$costs_var_opr = "\n\t\treturn operant.costs() + 2;";
$costs_field = "\n\t\treturn 10;";
$costs_class = "\n\t\treturn 20;";
$costs_method_super = "
		int costs = 50;
		for (int i=0;i<args.length;i++) costs+=args[i].costs();
";
$costs_method = "
$costs_method_super
		costs+=obj.costs();
		return costs;
";
$costs_marray = "
		int costs = 50;
		for (int i=0;i<oprs.length;i++) costs+=oprs[i].costs();
		return costs;
";
$costs_conditional = "
\t\treturn lOpr.costs() + rOpr.costs() + 10;
";
$costs_tswitch = "
\t\treturn (50 * targets.length);
";
$costs_lswitch = "
\t\treturn (20 * targets.length);
";
$costs_unop = "
\t\treturn rOpr.costs() + 5;
";
$costs_unop_class = "
\t\treturn rOpr.costs() + 50;
";
$costs_rarray = "
\t\treturn aOpr.costs() + iOpr.costs() + 5;
";
$costs_sarray = "
\t\treturn aOpr.costs() + iOpr.costs() + rvalue.costs() + 10;
";
$costs_opr = "
\t\treturn operant.costs() + 10;
";

%costs = (
    'IMArrayLength' => "\n\t\treturn 5;",
    'IMCastD2F' => $costs_unop,
    'IMCastD2I' => $costs_unop,
    'IMCastD2L' => $costs_unop,
    'IMCastF2D' => $costs_unop,
    'IMCastF2I' => $costs_unop,
    'IMCastF2L' => $costs_unop,
    'IMCastI2B' => $costs_unop,
    'IMCastI2C' => $costs_unop,
    'IMCastI2D' => $costs_unop,
    'IMCastI2F' => $costs_unop,
    'IMCastI2L' => $costs_unop,
    'IMCastI2S' => $costs_unop,
    'IMCastL2D' => $costs_unop,
    'IMCastL2F' => $costs_unop,
    'IMCastL2I' => $costs_unop,
    'IMCaughtException' => "\n\t\treturn 100;",
    'IMCheckCast' => $costs_unop_class, 
    'IMDGCompare' => $costs_binop,
    'IMDLCompare' => $costs_binop,
    'IMFGCompare' => $costs_binop,
    'IMFLCompare' => $costs_binop,
    'IMLCompare' => $costs_binop,
    'IMEQConditionalBranch' => $costs_conditional,
    'IMGEConditionalBranch' => $costs_conditional,
    'IMGTConditionalBranch' => $costs_conditional,
    'IMLEConditionalBranch' => $costs_conditional,
    'IMLTConditionalBranch' => $costs_conditional,
    'IMNEConditionalBranch' => $costs_conditional,
    'IMGetField' => "\n\t\treturn rOpr.costs() + 10;",
    'IMGetStatic' => "
    		if (opts.isSingleDomainSystem()) return 5;
		return 15;",
    'IMInstanceOf' => $costs_unop_class, 
    'IMInvokeInterface' => $costs_method,
    'IMInvokeSpecial' => $costs_method,
    'IMInvokeStatic' => "$costs_method_super\t\treturn costs;",
    'IMInvokeVirtual' => $costs_method,
    'IMLookupSwitch' => $costs_lswitch,
    'IMMonitor' => $costs_opr,
    'IMNew' => $costs_class, 
    'IMNewArray' => "\n\t\treturn 50;",
    'IMNewMultiArray' => $costs_marray,
    'IMNewObjArray' => "\n\t\treturn 50;",
    'IMPutField' => "\n\t\treturn obj.costs() + 10;",
    'IMPutStatic' => "
\t\tif (opts.isSingleDomainSystem()) return rvalue.costs() + 10;
\t\treturn rvalue.costs() + 15;",
   # 'IMReturnSubroutine' => $costs_var, 
    'IMGoto' => "\n\t\tif (hasShortcut()) return 0;\n\t\treturn 2;", 
    'IMTableSwitch' => $costs_tswitch,
    'IMThrow' => "\n\t\treturn exception.costs() + 50;",
    'IMAConstant' => $costs_const_value,
    'IMAMemConstant' => $costs_const_value,
    'IMAMTConstant' => $costs_const_value,
    'IMAReadArray' => $costs_rarray,
    'IMAReadLocalVariable' => $costs_var,
    'IMAStoreArray' => $costs_sarray,
    'IMAStoreLocalVariable' => $costs_var_opr,
    'IMBReadArray' => $costs_rarray,
    'IMBStoreArray' => $costs_sarray,
    'IMCReadArray' => $costs_rarray,
    'IMCStoreArray' => $costs_sarray,
    'IMDAdd' => $costs_binop,
    'IMDConstant' => $costs_dconst_value,
    'IMDDiv' => $costs_binop,
    'IMDMul' => $costs_binop,
    'IMDNeg' => $costs_unop,
    'IMDReadArray' => $costs_rarray,
    'IMDReadLocalVariable' => $costs_var,
    'IMDRem' => $costs_binop,
    'IMEpilog' => $costs_epilog,
    'IMDStoreArray' => $costs_sarray,
    'IMDStoreLocalVariable' => $costs_var_opr,
    'IMDSub' => $costs_binop,
    'IMFAdd' => $costs_binop,
    'IMFConstant' => $costs_const_value,
    'IMFDiv' => $costs_binop,
    'IMFMul' => $costs_binop,
    'IMFNeg' => $costs_unop,
    'IMFReadArray' => $costs_rarray,
    'IMFReadLocalVariable' => $costs_var,
    'IMFRem' => $costs_binop,
    'IMFStoreArray' => $costs_sarray,
    'IMFStoreLocalVariable' => $costs_var_opr,
    'IMFSub' => $costs_binop,
    'IMIAdd' => $costs_binop,
    'IMIBitAnd' => $costs_binop,
    'IMIBitOr' => $costs_binop,
    'IMIBitXor' => $costs_binop,
    'IMIConstant' => $costs_const_value,
    'IMIDiv' => $costs_binop,
    'IMIMul' => $costs_binop,
    'IMINeg' => $costs_unop,
    'IMIReadArray' => $costs_rarray,
    'IMIReadLocalVariable' => $costs_var,
    'IMIRem' => $costs_binop,
    'IMIShiftLeft' => $costs_binop,
    'IMIShiftRight' => $costs_binop,
    'IMIShiftRight2' => $costs_binop,
    'IMIStoreArray' => $costs_sarray,
    'IMIStoreLocalVariable' => $costs_var_opr,
    'IMISub' => $costs_binop,
    'IMLAdd' => $costs_binop,
    'IMLBitAnd' => $costs_binop,
    'IMLBitOr' => $costs_binop,
    'IMLBitXor' => $costs_binop,
    'IMLConstant' => $costs_dconst_value,
    'IMLDiv' => $costs_binop,
    'IMLMul' => $costs_binop,
    'IMLNeg' => $costs_unop,
    'IMLReadArray' => $costs_rarray,
    'IMLReadLocalVariable' => $costs_var,
    'IMLRem' => $costs_binop,
    'IMLShiftLeft' => $costs_binop,
    'IMLShiftRight' => $costs_binop,
    'IMLShiftRight2' => $costs_binop,
    'IMLStoreArray' => $costs_sarray,
    'IMLStoreLocalVariable' => $costs_var_opr,
    'IMLSub' => $costs_binop,
    'IMNullConstant' => $costs_const_value,
    'IMSReadArray' => $costs_rarray,
    'IMSStoreArray' => $costs_sarray,
    'IMPhi' => "\n\t\treturn 1;",
);
