$copyNode_imnode = "
\t\t\%CLASS\% nnode = new \%CLASS\%();
\t\tnnode.copyNodeValues(visitor, this);
";

$copyNode_const_value = "
$copyNode_imnode
\t\tnnode.value=value;
";

$copyNode_var = "
$copyNode_imnode
\t\tif (visitor==null) {
\t\t\tnnode.var=var;
\t\t\treturn nnode;
\t\t}
\t\tif (var!=null) nnode.var=visitor.adjustSlot(var);
";

$copyNode_binop = "
$copyNode_imnode
\t\tnnode.rOpr = rOpr.copy(visitor);
\t\tnnode.lOpr = lOpr.copy(visitor);
";

$copyNode_opr = "
\t\tnnode.operant = operant.copy(visitor);
";

$copyNode_var_opr = "
$copyNode_imnode
$copyNode_opr
\t\tif (visitor==null) {
\t\t\tnnode.var=var;
\t\t\treturn nnode;
\t\t}
\t\tnnode.var=visitor.adjustSlot(var);
";

$copyNode_field = "
$copyNode_imnode
\t\tnnode.cpEntry=cpEntry;
";

$copyNode_return = "
$copyNode_imnode
\t\tnnode.rvalue=rvalue.copy(visitor);
";
$copyNode_method_super = "
$copyNode_imnode
		nnode.typeDesc = typeDesc;
		nnode.joinPoints = joinPoints;
		nnode.cpEntry=cpEntry;
		nnode.args = new IMNode[args.length];
		for (int i=0;i<args.length;i++) nnode.args[i] = args[i].copy(visitor);
";

$copyNode_method = "
$copyNode_method_super
		nnode.obj = obj.copy(visitor);
";


$copyNode_class = "
$copyNode_imnode
		nnode.cpEntry=cpEntry;
";

$copyNode_obj_array = "
$copyNode_imnode
		nnode.cpEntry=cpEntry;
		nnode.aclass=aclass;
		nnode.size=size.copy(visitor);
";

$copyNode_array = "
$copyNode_imnode
		nnode.aclass=aclass;
		nnode.size=size.copy(visitor);
";

$copyNode_marray = "
$copyNode_imnode
		nnode.cpEntry=cpEntry;
		nnode.dim=dim;
		nnode.aclass=aclass;
		nnode.oprs = new IMNode[oprs.length];
		for (int i=0;i<oprs.length;i++) nnode.oprs[i] = oprs[i].copy(visitor);
";

$copyNode_targets = "
\t\tnnode.targets = new IMBasicBlock[targets.length];
\t\tfor (int i=0;i<targets.length;i++) nnode.targets[i] = visitor.updateBlock(targets[i]);
";
$copyNode_conditional = "
$copyNode_imnode
$copyNode_targets
\t\tnnode.rOpr = rOpr.copy(visitor);
\t\tnnode.lOpr = lOpr.copy(visitor);
";
$copyNode_tswitch = "
$copyNode_imnode
$copyNode_opr
$copyNode_targets
\t\tnnode.high=high;
\t\tnnode.low=low;
";
$copyNode_lswitch = "
$copyNode_imnode
$copyNode_opr
$copyNode_targets
\t\tnnode.keys = new int[keys.length];
\t\tSystem.arraycopy(nnode.keys,0,keys,0,keys.length);
";
$copyNode_unop = "
$copyNode_imnode
\t\tnnode.rOpr = rOpr.copy(visitor);
";
$copyNode_unop_class = "
$copyNode_imnode
\t\tnnode.cpEntry=cpEntry;
\t\tnnode.rOpr = rOpr.copy(visitor);
";
$copyNode_rarray = "
$copyNode_imnode
\t\tnnode.aOpr = aOpr.copy(visitor);
\t\tnnode.iOpr = iOpr.copy(visitor);
";
$copyNode_sarray = "
$copyNode_rarray
\t\tnnode.rvalue = rvalue.copy(visitor);
";

%copyNode = (
    'IMArrayLength' => "
$copyNode_imnode
\t\tnnode.array = array.copy(visitor);
",
    'IMCall' => "
$copyNode_imnode
$copyNode_targets
",
    'IMCastD2F' => $copyNode_unop,
    'IMCastD2I' => $copyNode_unop,
    'IMCastD2L' => $copyNode_unop,
    'IMCastF2D' => $copyNode_unop,
    'IMCastF2I' => $copyNode_unop,
    'IMCastF2L' => $copyNode_unop,
    'IMCastI2B' => $copyNode_unop,
    'IMCastI2C' => $copyNode_unop,
    'IMCastI2D' => $copyNode_unop,
    'IMCastI2F' => $copyNode_unop,
    'IMCastI2L' => $copyNode_unop,
    'IMCastI2S' => $copyNode_unop,
    'IMCastL2D' => $copyNode_unop,
    'IMCastL2F' => $copyNode_unop,
    'IMCastL2I' => $copyNode_unop,
    'IMCaughtException' => "
$copyNode_imnode
		nnode.exphandler=exphandler;
",
    'IMCheckCast' => $copyNode_unop_class, 
    'IMDGCompare' => $copyNode_binop,
    'IMDLCompare' => $copyNode_binop,
    'IMFGCompare' => $copyNode_binop,
    'IMFLCompare' => $copyNode_binop,
    'IMLCompare' => $copyNode_binop,
    'IMEQConditionalBranch' => $copyNode_conditional,
    'IMGEConditionalBranch' => $copyNode_conditional,
    'IMGTConditionalBranch' => $copyNode_conditional,
    'IMLEConditionalBranch' => $copyNode_conditional,
    'IMLTConditionalBranch' => $copyNode_conditional,
    'IMNEConditionalBranch' => $copyNode_conditional,
    'IMGetField' => "
$copyNode_class
		nnode.rOpr=rOpr.copy(visitor);
",
    'IMGetStatic' => "
$copyNode_class
\t\tnnode.imfield = imfield;	
",
    'IMGoto' => "
$copyNode_imnode
$copyNode_targets
",
    'IMInstanceOf' => $copyNode_unop_class, 
    'IMInvokeInterface' => $copyNode_method,
    'IMInvokeSpecial' => $copyNode_method,
    'IMInvokeStatic' => $copyNode_method_super,
    'IMInvokeVirtual' => $copyNode_method,
    'IMLookupSwitch' => $copyNode_lswitch,
    'IMMonitor' => "
$copyNode_imnode
$copyNode_opr
",
    'IMNew' => $copyNode_class, 
    'IMNewArray' => $copyNode_array,
    'IMNewMultiArray' => $copyNode_marray,
    'IMNewObjArray' => $copyNode_obj_array,
    'IMPutField' => "
$copyNode_class
\t\tnnode.rvalue = rvalue.copy(visitor);
\t\tnnode.obj = obj.copy(visitor); 
",
    'IMPutStatic' => "
$copyNode_class
\t\tnnode.imfield = imfield;
\t\tnnode.rvalue = rvalue.copy(visitor);
",
    'IMReturnSubroutine' => $copyNode_var, 
    'IMTableSwitch' => $copyNode_tswitch,
    'IMThrow' => "
$copyNode_imnode
\t\tif (exception!=null) nnode.exception = exception.copy(visitor);
",
    'IMAConstant' => $copyNode_const_value,
    'IMAMTConstant' => "
$copyNode_imnode
\t\tnnode.memory_type_class = memory_type_class;
\t\tnnode.base = base;
\t\tnnode.alias = alias;
",
    'IMAMemConstant' => "
$copyNode_imnode
\t\tnnode.memory_class = memory_class;
\t\tnnode.addr = addr;
\t\tnnode.size = size;
\t\tnnode.alias = alias;
",
    'IMAReadArray' => $copyNode_rarray,
    'IMAReadLocalVariable' => "
$copyNode_imnode
\t\tnnode.escape_path = escape_path;
\t\tif (visitor==null) {
\t\t\tnnode.var=var;
\t\t\treturn nnode;
\t\t}
\t\tif (var!=null) nnode.var=visitor.adjustSlot(var);
",
    'IMAStoreArray' => $copyNode_sarray,
    'IMAStoreLocalVariable' => $copyNode_var_opr,
    'IMBReadArray' => $copyNode_rarray,
    'IMBStoreArray' => $copyNode_sarray,
    'IMCReadArray' => $copyNode_rarray,
    'IMCStoreArray' => $copyNode_sarray,
    'IMDAdd' => $copyNode_binop,
    'IMDConstant' => $copyNode_const_value,
    'IMDDiv' => $copyNode_binop,
    'IMDMul' => $copyNode_binop,
    'IMDNeg' => $copyNode_unop,
    'IMDReadArray' => $copyNode_rarray,
    'IMDReadLocalVariable' => $copyNode_var,
    'IMDRem' => $copyNode_binop,
    'IMDReturn' => $copyNode_return,
    'IMEpilog' => $copyNode_var,
    'IMDStoreArray' => $copyNode_sarray,
    'IMDStoreLocalVariable' => $copyNode_var_opr,
    'IMDSub' => $copyNode_binop,
    'IMFAdd' => $copyNode_binop,
    'IMFConstant' => $copyNode_const_value,
    'IMFDiv' => $copyNode_binop,
    'IMFMul' => $copyNode_binop,
    'IMFNeg' => $copyNode_unop,
    'IMFReadArray' => $copyNode_rarray,
    'IMFReadLocalVariable' => $copyNode_var,
    'IMFRem' => $copyNode_binop,
    'IMFReturn' => $copyNode_return,
    'IMFStoreArray' => $copyNode_sarray,
    'IMFStoreLocalVariable' => $copyNode_var_opr,
    'IMFSub' => $copyNode_binop,
    'IMIAdd' => $copyNode_binop,
    'IMIBitAnd' => $copyNode_binop,
    'IMIBitOr' => $copyNode_binop,
    'IMIBitXor' => $copyNode_binop,
    'IMIConstant' => $copyNode_const_value,
    'IMPopReturnAddr' => $copyNode_imnode,
    'IMIDiv' => $copyNode_binop,
    'IMIMul' => $copyNode_binop,
    'IMINeg' => $copyNode_unop,
    'IMIReadArray' => $copyNode_rarray,
    'IMIReadLocalVariable' => $copyNode_var,
    'IMIRem' => $copyNode_binop,
    'IMIReturn' => $copyNode_return,
    'IMAReturn' => $copyNode_return,
    'IMIShiftLeft' => $copyNode_binop,
    'IMIShiftRight' => $copyNode_binop,
    'IMIShiftRight2' => $copyNode_binop,
    'IMIStoreArray' => $copyNode_sarray,
    'IMIStoreLocalVariable' => $copyNode_var_opr,
    'IMISub' => $copyNode_binop,
    'IMLAdd' => $copyNode_binop,
    'IMLBitAnd' => $copyNode_binop,
    'IMLBitOr' => $copyNode_binop,
    'IMLBitXor' => $copyNode_binop,
    'IMLConstant' => $copyNode_const_value,
    'IMLDiv' => $copyNode_binop,
    'IMLMul' => $copyNode_binop,
    'IMLNeg' => $copyNode_unop,
    'IMLReadArray' => $copyNode_rarray,
    'IMLReadLocalVariable' => $copyNode_var,
    'IMLRem' => $copyNode_binop,
    'IMLReturn' => $copyNode_return,
    'IMLShiftLeft' => $copyNode_binop,
    'IMLShiftRight' => $copyNode_binop,
    'IMLShiftRight2' => $copyNode_binop,
    'IMLStoreArray' => $copyNode_sarray,
    'IMLStoreLocalVariable' => $copyNode_var_opr,
    'IMLSub' => $copyNode_binop,
    'IMNullConstant' => $copyNode_imnode,
    'IMSReadArray' => $copyNode_rarray,
    'IMSStoreArray' => $copyNode_sarray,
    'IMVReturn' => $copyNode_imnode,
);
