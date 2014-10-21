$visitNode_imnode = "
\t\tvisitor.visit(this);
";

$visitNode_const_value = "
$visitNode_imnode
";

$visitNode_var = "
$visitNode_imnode
";

$visitNode_binop = "
$visitNode_imnode
\t\trOpr.visitNodes(visitor);
\t\tlOpr.visitNodes(visitor);
";

$visitNode_var_opr = "
\t\toperant.visitNodes(visitor); 
$visitNode_imnode
";

$visitNode_opr = "
\t\toperant.visitNodes(visitor);
";

$visitNode_field = "
$visitNode_imnode
";

$visitNode_return = "
$visitNode_imnode
\t\trvalue.visitNodes(visitor);
";

$visitNode_method_super = "
$visitNode_imnode
\t\tfor (int i=0;i<args.length;i++) args[i].visitNodes(visitor);
";

$visitNode_method = "
$visitNode_method_super
\t\tobj.visitNodes(visitor);
";


$visitNode_class = "
$visitNode_imnode
";

$visitNode_obj_array = "
$visitNode_imnode
		size.visitNodes(visitor);
";

$visitNode_array = "
$visitNode_imnode
		size.visitNodes(visitor);
";

$visitNode_marray = "
$visitNode_imnode
		for (int i=0;i<oprs.length;i++) oprs[i].visitNodes(visitor);
";

$visitNode_targets = "
\t\t//for (int i=0;i<targets.length;i++) targets[i].visitNodes(visitor);
";
$visitNode_conditional = "
$visitNode_imnode
$visitNode_targets
\t\trOpr.visitNodes(visitor);
\t\tlOpr.visitNodes(visitor);
";
$visitNode_tswitch = "
$visitNode_imnode
$visitNode_targets
$visitNode_opr
";
$visitNode_lswitch = "
$visitNode_imnode
$visitNode_targets
$visitNode_opr
";
$visitNode_unop = "
$visitNode_imnode
\t\trOpr.visitNodes(visitor);
";
$visitNode_unop_class = "
$visitNode_imnode
\t\trOpr.visitNodes(visitor);
";
$visitNode_rarray = "
$visitNode_imnode
\t\taOpr.visitNodes(visitor);
\t\tiOpr.visitNodes(visitor);
";
$visitNode_sarray = "
$visitNode_rarray
\t\trvalue.visitNodes(visitor);
";

%visitNode = (
    'IMArrayLength' => "
$visitNode_imnode
\t\tarray.visitNodes(visitor);
",
     'IMCall' => "
$visitNode_imnode
$visitNode_targets
",
    'IMAMemConstant' => $visitNode_imnode,
    'IMAMTConstant' => $visitNode_imnode, 
    'IMCastD2F' => $visitNode_unop,
    'IMCastD2I' => $visitNode_unop,
    'IMCastD2L' => $visitNode_unop,
    'IMCastF2D' => $visitNode_unop,
    'IMCastF2I' => $visitNode_unop,
    'IMCastF2L' => $visitNode_unop,
    'IMCastI2B' => $visitNode_unop,
    'IMCastI2C' => $visitNode_unop,
    'IMCastI2D' => $visitNode_unop,
    'IMCastI2F' => $visitNode_unop,
    'IMCastI2L' => $visitNode_unop,
    'IMCastI2S' => $visitNode_unop,
    'IMCastL2D' => $visitNode_unop,
    'IMCastL2F' => $visitNode_unop,
    'IMCastL2I' => $visitNode_unop,
    'IMCaughtException' => $visitNode_imnode,
    'IMCheckCast' => $visitNode_unop_class, 
    'IMDGCompare' => $visitNode_binop,
    'IMDLCompare' => $visitNode_binop,
    'IMFGCompare' => $visitNode_binop,
    'IMFLCompare' => $visitNode_binop,
    'IMLCompare' => $visitNode_binop,
    'IMEQConditionalBranch' => $visitNode_conditional,
    'IMGEConditionalBranch' => $visitNode_conditional,
    'IMGTConditionalBranch' => $visitNode_conditional,
    'IMLEConditionalBranch' => $visitNode_conditional,
    'IMLTConditionalBranch' => $visitNode_conditional,
    'IMNEConditionalBranch' => $visitNode_conditional,
    'IMGetField' => "
$visitNode_class
		rOpr.visitNodes(visitor);
",
    'IMGetStatic' => $visitNode_class,
    'IMGoto' => "
$visitNode_imnode
$visitNode_targets
",
    'IMInstanceOf' => $visitNode_unop_class, 
    'IMInvokeInterface' => $visitNode_method,
    'IMInvokeSpecial' => $visitNode_method,
    'IMInvokeStatic' => $visitNode_method_super,
    'IMInvokeVirtual' => $visitNode_method,
    'IMLookupSwitch' => $visitNode_lswitch,
    'IMMonitor' => "
$visitNode_imnode
$visitNode_opr
",
    'IMNew' => $visitNode_class, 
    'IMNewArray' => $visitNode_array,
    'IMNewMultiArray' => $visitNode_marray,
    'IMNewObjArray' => $visitNode_obj_array,
    'IMPutField' => "
$visitNode_class
\t\trvalue.visitNodes(visitor);
\t\tobj.visitNodes(visitor); 
",
    'IMPutStatic' => "
$visitNode_class
\t\trvalue.visitNodes(visitor);
",
    'IMReturnSubroutine' => $visitNode_var, 
    'IMTableSwitch' => $visitNode_tswitch,
    'IMThrow' => "
$visitNode_imnode
		if (exception!=null) exception.visitNodes(visitor);
",
    'IMAConstant' => $visitNode_const_value,
    'IMPhi' => "
\t\tvisitor.visit(this);
\t\tfor (int i=0;i<slots.length;i++) slots[i].visitNodes(visitor);",
    'IMAReadArray' => $visitNode_rarray,
    'IMAReadLocalVariable' => $visitNode_var,
    'IMAStoreArray' => $visitNode_sarray,
    'IMAStoreLocalVariable' => $visitNode_var_opr,
    'IMPopReturnAddr' => '/* we do nothing here */',
    'IMBReadArray' => $visitNode_rarray,
    'IMBStoreArray' => $visitNode_sarray,
    'IMCReadArray' => $visitNode_rarray,
    'IMCStoreArray' => $visitNode_sarray,
    'IMDAdd' => $visitNode_binop,
    'IMDConstant' => $visitNode_const_value,
    'IMDDiv' => $visitNode_binop,
    'IMDMul' => $visitNode_binop,
    'IMDNeg' => $visitNode_unop,
    'IMDReadArray' => $visitNode_rarray,
    'IMDReadLocalVariable' => $visitNode_var,
    'IMDRem' => $visitNode_binop,
    'IMDReturn' => $visitNode_return,
    'IMEpilog' => $visitNode_imnode,
    'IMDStoreArray' => $visitNode_sarray,
    'IMDStoreLocalVariable' => $visitNode_var_opr,
    'IMDSub' => $visitNode_binop,
    'IMFAdd' => $visitNode_binop,
    'IMFConstant' => $visitNode_const_value,
    'IMFDiv' => $visitNode_binop,
    'IMFMul' => $visitNode_binop,
    'IMFNeg' => $visitNode_unop,
    'IMFReadArray' => $visitNode_rarray,
    'IMFReadLocalVariable' => $visitNode_var,
    'IMFRem' => $visitNode_binop,
    'IMFReturn' => $visitNode_return,
    'IMFStoreArray' => $visitNode_sarray,
    'IMFStoreLocalVariable' => $visitNode_var_opr,
    'IMFSub' => $visitNode_binop,
    'IMIAdd' => $visitNode_binop,
    'IMIBitAnd' => $visitNode_binop,
    'IMIBitOr' => $visitNode_binop,
    'IMIBitXor' => $visitNode_binop,
    'IMIConstant' => $visitNode_const_value,
    'IMIDiv' => $visitNode_binop,
    'IMIMul' => $visitNode_binop,
    'IMINeg' => $visitNode_unop,
    'IMIReadArray' => $visitNode_rarray,
    'IMIReadLocalVariable' => $visitNode_var,
    'IMIRem' => $visitNode_binop,
    'IMIReturn' => $visitNode_return,
    'IMAReturn' => $visitNode_return,
    'IMIShiftLeft' => $visitNode_binop,
    'IMIShiftRight' => $visitNode_binop,
    'IMIShiftRight2' => $visitNode_binop,
    'IMIStoreArray' => $visitNode_sarray,
    'IMIStoreLocalVariable' => $visitNode_var_opr,
    'IMISub' => $visitNode_binop,
    'IMLAdd' => $visitNode_binop,
    'IMLBitAnd' => $visitNode_binop,
    'IMLBitOr' => $visitNode_binop,
    'IMLBitXor' => $visitNode_binop,
    'IMLConstant' => $visitNode_const_value,
    'IMLDiv' => $visitNode_binop,
    'IMLMul' => $visitNode_binop,
    'IMLNeg' => $visitNode_unop,
    'IMLReadArray' => $visitNode_rarray,
    'IMLReadLocalVariable' => $visitNode_var,
    'IMLRem' => $visitNode_binop,
    'IMLReturn' => $visitNode_return,
    'IMLShiftLeft' => $visitNode_binop,
    'IMLShiftRight' => $visitNode_binop,
    'IMLShiftRight2' => $visitNode_binop,
    'IMLStoreArray' => $visitNode_sarray,
    'IMLStoreLocalVariable' => $visitNode_var_opr,
    'IMLSub' => $visitNode_binop,
    'IMNullConstant' => $visitNode_imnode,
    'IMSReadArray' => $visitNode_rarray,
    'IMSStoreArray' => $visitNode_sarray,
    'IMVReturn' => $visitNode_imnode,
);
