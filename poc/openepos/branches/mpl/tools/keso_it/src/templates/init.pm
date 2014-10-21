$init_imnode = "
	public \%CLASS\%(IMMethod method, int bc, int type, int bcpos) {
		super(method,bc,type,bcpos);\%DOMID\%
	}
";

$init_super = "\t\tsuper(method,bc,\%BCTYPE\%,bcpos);\%DOMID\%";

$init_default = "
	public \%CLASS\%(IMMethod method, int bc, int bcpos) {
$init_super
	}

";

$init_const_value = "
	protected \%JTYPE\% value;

	public \%CLASS\%(IMMethod method, int bc, int bcpos, \%JTYPE\% value) {
$init_super
		this.value=value;
	}

	public \%JTYPE\% getValue() {
		return value;
	}

	public \%JTYPE\% get\%UJTYPE\%Value() {
		return value;
	}

	public void set\%UJTYPE\%Value(\%JTYPE\% value) {
		this.value=value;
	}

	public boolean equalValue(IMConstant node) {
		return this.value==node.get\%UJTYPE\%Value();
	}

	public boolean equalValue(\%JTYPE\% value) {
		return this.value==value;
	}

";

$init_aconst = "
	protected ConstantPoolEntry value;

	public \%CLASS\%(IMMethod method, int bc, int bcpos, ConstantPoolEntry value) {
$init_super
		this.value=value;
		if (value instanceof StringCPEntry) {
			method.requireClass(domainID, \"java/lang/String\");
			method.requireClass(domainID, \"[C\");
		}
	}

	public ConstantPoolEntry getValue() {
		return value;
	}

";

$init_var_super = "
	protected IMSlot var;

	public \%CLASS\%(IMMethod method, int bc, int type, int bcpos, IMSlot var) {
		super(method,bc,type,bcpos);
		if (var==null) throw new NullPointerException();
		this.var=var;
	}

	public IMSlot getIMSlot() {
		return var;
	}

	public void setIMSlot(IMSlot slot) {
		//if (slot==null) throw new NullPointerException();
		var = slot;	
	}
";

$init_var = "
	public \%CLASS\%(IMMethod method, int bc, int bcpos, int var) {
		this(method,bc,bcpos,
			method.getMethodFrame().getLocalVariable(\%BCTYPE\%,var));
	}

	public \%CLASS\%(IMMethod method, int bc, int bcpos, IMSlot var) {
		super(method,bc,\%BCTYPE\%,bcpos, var);
	}

	public \%CLASS\%(IMMethod method, int bc, int type, int bcpos, IMSlot var) {
		super(method,bc,type,bcpos, var);
	}
";
$init_var_ref = "
	private int alength = -1;

$init_var

	public int getArrayLength() throws CompileException {
		return alength;
	}
";
$init_field = "
	protected FieldRefCPEntry cpEntry;

	public \%CLASS\%(IMMethod method, int bc, int bcpos, FieldRefCPEntry field) {
		super(method, bc, BCBasicDatatype.toBasicDatatype(field.getMemberTypeDesc()), bcpos);\%DOMID\%
		this.cpEntry=field;
		method.requireClass(domainID, cpEntry.getClassName());
		String fieldType = cpEntry.getMemberTypeDesc();
		if (fieldType.charAt(0)=='L') {
			method.requireClass(domainID, fieldType.substring(1,fieldType.length()-1));
		}
	}

";
$init_static_field = "
	protected FieldRefCPEntry cpEntry;
	protected IMField imfield=null;

	public \%CLASS\%(IMMethod method, int bc, int bcpos, FieldRefCPEntry field) {
		super(method, bc, BCBasicDatatype.toBasicDatatype(field.getMemberTypeDesc()), bcpos);\%DOMID\%
		this.cpEntry=field;
		method.requireClass(domainID, cpEntry.getClassName());
		String fieldType = cpEntry.getMemberTypeDesc();
		if (fieldType.charAt(0)=='L') {
			method.requireClass(domainID, fieldType.substring(1,fieldType.length()-1));
		}
	}
";
$init_method_super = "
	protected MethodTypeDescriptor	typeDesc;
	protected JoinPointChecker 	joinPoints;

	public \%CLASS\%(IMMethod method, int bc, int bcpos, ClassMemberCPEntry cpEntry) {
		super(method,bc,-1,bcpos);
		this.joinPoints= method.getJoinPoints();
		this.typeDesc  = new MethodTypeDescriptor(cpEntry.getMemberTypeDesc());
		this.datatype  = typeDesc.getBasicReturnType();
	}

";

$init_method = "
	protected MethodRefCPEntry cpEntry;

	public \%CLASS\%(IMMethod method, int bc, int bcpos, MethodRefCPEntry methodRef) {
		super(method,bc,bcpos,methodRef);\%DOMID\%
		this.cpEntry=methodRef;
		method.requireMethod(domainID, cpEntry);
	}

";

$init_method_interface = "
	protected InterfaceMethodRefCPEntry cpEntry;

	public \%CLASS\%(IMMethod method, int bc, int bcpos, InterfaceMethodRefCPEntry methodRef) {
		super(method,bc,bcpos,methodRef);\%DOMID\%
		this.cpEntry=methodRef;
		method.requireMethod(domainID, cpEntry);
	}

";

$init_class = "
	protected ClassCPEntry cpEntry;

	public \%CLASS\%(IMMethod method, int bc, int bcpos, ClassCPEntry clazz) {
$init_super
		this.cpEntry=clazz;
		String className = cpEntry.getClassName();
		method.requireClass(domainID, className);
	}

";

$init_new = "
	protected ClassCPEntry cpEntry;

	public \%CLASS\%(IMMethod method, int bc, int bcpos, ClassCPEntry clazz) {
$init_super
		this.cpEntry=clazz;
		String className = cpEntry.getClassName();
		method.requireClass(domainID, className);
	}

";

$init_marray = "
	protected ClassCPEntry cpEntry;
	protected IMArrayClass aclass;
	protected int dim;

	public \%CLASS\%(IMMethod method, int bc, int bcpos, ClassCPEntry clazz, int dim) {
$init_super
		this.cpEntry=clazz;
		this.dim=dim;
		method.requireClass(domainID, cpEntry.getClassName());
		method.requireClass(domainID, \"[Ljava/lang/Object;\");
		aclass=(IMArrayClass)method.getIMClass(\"[Ljava/lang/Object;\");
		//method.requireClass(domainID, aclass.getClassName());
		aclass.requireMultiAlloc();
	}

";

$init_obj_array = "
	protected ClassCPEntry cpEntry;
	protected IMArrayClass aclass;

	public \%CLASS\%(IMMethod method, int bc, int bcpos, ClassCPEntry clazz) {
$init_super
		this.cpEntry=clazz;
		method.requireClass(domainID, cpEntry.getClassName());
		method.requireClass(domainID, \"[Ljava/lang/Object;\");
		aclass=(IMArrayClass)method.getIMClass(\"[Ljava/lang/Object;\");
		//method.requireClass(domainID, aclass.getClassName());
		aclass.requireAlloc();
	}

	public int getArrayLength() throws CompileException {
		if (size.isConstant()) return ((IMConstant)size).getIntValue(); 
		return -1;
	}
";

$init_array = "
	protected IMArrayClass aclass;

	public \%CLASS\%(IMMethod method, int bc, int bcpos, int type) {
$init_super
		String className=\"unknown\";
		switch (type) {
			case BCBasicDatatype.INT: className=\"[I\"; break;
			case BCBasicDatatype.LONG: className=\"[J\"; break;
			case BCBasicDatatype.FLOAT: className=\"[F\"; break; 
			case BCBasicDatatype.DOUBLE: className=\"[D\"; break; 
			case BCBasicDatatype.BYTE: className=\"[B\"; break; 
			case BCBasicDatatype.CHAR: className=\"[C\"; break; 
			case BCBasicDatatype.SHORT: className=\"[S\"; break; 
			case BCBasicDatatype.BOOLEAN: className=\"[Z\"; break; 
			default:
				throw new Error(\"unkown type!\");
		}
		method.requireClass(domainID, className);
		aclass=(IMArrayClass)method.getIMClass(className);
		aclass.requireAlloc();
	}

	public int getArrayLength() throws CompileException {
		if (size.isConstant()) return ((IMConstant)size).getIntValue(); 
		return -1;
	}
";


$init_label = "
	public \%CLASS\%(IMMethod method, int bc, int bcpos, IMBasicBlock label) {
		super(method,bc,-1,bcpos);
		targets = new IMBasicBlock[1];
		targets[0]=label;
	}

";

$init_call = "
	private int index;

	public \%CLASS\%(IMMethod method, int bc, int bcpos, IMBasicBlock label, IMBasicBlock ret_label) {
		super(method,bc,-1,bcpos);
		targets = new IMBasicBlock[1];
		targets[0]=label;
		index = method.registerReturnAddr(ret_label);
	}

";

$init_conditional = "
	public \%CLASS\%(IMMethod method, int bc, int bcpos, IMBasicBlock label, IMBasicBlock next) {
		super(method,bc,bcpos,label,next);
	}
	

";
$init_const_null = "
	public \%CLASS\%(IMMethod method, int bc, int bcpos) {
$init_super
	}

";
$init_tswitch = "
	protected int high;
	protected int low;

	public \%CLASS\%(IMMethod method, int bc, int bcpos, int low, int high, IMBasicBlock[] table) {
$init_super
		this.low=low;
		this.high=high;
		this.targets=table;
	}

";
$init_lswitch = "
	protected int[] keys;

	public \%CLASS\%(IMMethod method, int bc, int bcpos, int[] keys, IMBasicBlock[] table) {
$init_super
		this.keys=keys;
		this.targets=table;
	}

";

%init = (
   'IMPhi' => " 
   	protected IMNode slots[];
	protected IMSlot orig_slot;
	protected IMBasicBlock bb;

	public \%CLASS\%(IMMethod method, IMBasicBlock bb, IMSlot orig_slot, int type) throws CompileException {
		super(method, -1, type, -1);\%DOMID\%
		this.orig_slot = orig_slot;
		this.bb = bb;

		IMBasicBlockList bl = bb.getPred();

		slots = new IMNode[bl.length()];
		for (int i=0;i<slots.length;i++) slots[i]=method.readLocal(-1,orig_slot);
	}

	public void setSource(int pos, IMNode slot) throws CompileException {
		slots[pos]=slot;
	}

	public IMNode getSource(int pos) throws CompileException {
		return slots[pos];
	}

	public void setSourceVar(int pos, IMSlot slot) throws CompileException {
		slots[pos].setIMSlot(slot);
	}

	public IMSlot getSourceVar(int pos) throws CompileException {
		return slots[pos].getIMSlot();
	}

",
    'IMReadLocalVariable' => $init_var_super,
    'IMStoreLocalVariable' => $init_var_super,
    'IMArrayLength' => $init_default,
    'IMCall' => $init_call,
    'IMStackOperationPOP' => $init_default,
    'IMStackOperationPOP2' => $init_default,
    'IMStackOperationDUP' => $init_default,
    'IMStackOperationDUP_X1' => $init_default,
    'IMStackOperationDUP_X2' => $init_default,
    'IMStackOperationDUP2' => $init_default,
    'IMStackOperationDUP2_X1' => $init_default,
    'IMStackOperationDUP2_X2' => $init_default,
    'IMStackOperationSWAP' => $init_default,
#    'IMCast' => 'IMUnaryNode',
    'IMCastD2F' => $init_default,
    'IMCastD2I' => $init_default,
    'IMCastD2L' => $init_default,
    'IMCastF2D' => $init_default,
    'IMCastF2I' => $init_default,
    'IMCastF2L' => $init_default,
    'IMCastI2B' => $init_default,
    'IMCastI2C' => $init_default,
    'IMCastI2D' => $init_default,
    'IMCastI2F' => $init_default,
    'IMCastI2L' => $init_default,
    'IMCastI2S' => $init_default,
    'IMCastL2D' => $init_default,
    'IMCastL2F' => $init_default,
    'IMCastL2I' => $init_default,
    'IMCaughtException' => '
	private IMExceptionHandler exphandler;
	
	public %CLASS%(IMMethod method, IMExceptionHandler exphandler, int bcpos) {
		super(method,-1,BCBasicDatatype.REFERENCE,bcpos);
		this.exphandler=exphandler;
	} 
',
    'IMCheckCast' => $init_class,
    'IMDGCompare' => $init_default,
    'IMDLCompare' => $init_default,
    'IMFGCompare' => $init_default,
    'IMFLCompare' => $init_default,
    'IMLCompare' => $init_default,
#    'IMAdd' => 'IMBinaryNode',
#    'IMDiv' => 'IMBinaryNode',
#    'IMCompare' => 'IMBinaryNode',
#    'IMMul' => 'IMBinaryNode',
#    'IMNeg' => 'IMUnaryNode',
#    'IMRem' => 'IMBinaryNode',
#    'IMReturn' => 'IMNode',
    'IMEpilog' => '
    	private IMSlot var;

	public %CLASS%(IMMethod method, int bcpos) {
		super(method, -1, BCBasicDatatype.VOID, bcpos);
		var = method.getReturnValue();
	} 

	public IMSlot getIMSlot() {
		return var;
	}

	public void setIMSlot(IMSlot slot) {
		var = slot;	
	}
    ',
#    'IMSub' => 'IMBinaryNode',
#    'IMBitAnd' => 'IMBinaryNode',
#    'IMBitOr' => 'IMBinaryNode',
#    'IMBitXor' => 'IMBinaryNode',
#    'IMShiftLeft' => 'IMBinaryNode',
#    'IMShiftRight' => 'IMBinaryNode',
#    'IMShiftRight2' => 'IMBinaryNode',
    'IMEQConditionalBranch' => $init_conditional,
    'IMGEConditionalBranch' => $init_conditional,
    'IMGTConditionalBranch' => $init_conditional,
    'IMLEConditionalBranch' => $init_conditional,
    'IMLTConditionalBranch' => $init_conditional,
    'IMNEConditionalBranch' => $init_conditional,
    'IMGetField' => $init_field,
    'IMGetStatic' => $init_static_field,
    'IMGoto' => $init_label,
    'IMInstanceOf' => $init_class,
    'IMInvoke' => $init_method_super,
    'IMInvokeInterface' => $init_method_interface,
    'IMInvokeSpecial' => $init_method,
    'IMInvokeStatic' => $init_method,
    'IMInvokeVirtual' => $init_method,
    'IMLookupSwitch' => $init_lswitch,
    'IMMonitor' => $init_default,
    'IMNew' => $init_new, 
    'IMNewArray' => $init_array,
    'IMNewMultiArray' => $init_marray,
    'IMNewObjArray' => $init_obj_array,
    'IMPutField' => $init_field,
    'IMPutStatic' => $init_static_field,
    'IMReturnSubroutine' => "
	protected IMSlot var;

	public \%CLASS\%(IMMethod method, int bc, int bcpos, int var) {
		super(method,bc,\%BCTYPE\%,bcpos);
		this.var = method.getMethodFrame().getLocalVariable(\%BCTYPE\%,var);
	}

	public IMSlot getIMSlot() {
		return var;
	}

	public void setIMSlot(IMSlot slot) {
		var = slot;	
	}
",
    'IMTableSwitch' => $init_tswitch,
    'IMThrow' => $init_default,
    'IMAConstant' => $init_aconst,
    'IMAMTConstant' => "
	private IMClass memory_type_class;
	private int base;
	private String alias;

	public \%CLASS\%(IMMethod method, int bc, int bcpos, IMClass type, int base) {
$init_super
		this.memory_type_class = type;
		this.base = base;
		IMClass clazz = method.getIMClass();
		this.alias = clazz.getAlias()+\"_mt_0x\"+Integer.toHexString(base);
	}

	public int getAddr() {
		return base;
	}

	public IMClass getType() {
		return memory_type_class;
	}

	public boolean isTypeOf(IMClass type) {
		return memory_type_class.typeOf(type);
	}
",
    'IMAMemConstant' => "
	private IMClass memory_class;
	private int addr;
	private int size;
	private String alias;

	public \%CLASS\%(IMMethod method, int bc, int bcpos, int addr, int size) {
$init_super
		this.addr = addr;
		this.size = size;
		this.memory_class = method.getIMClass(\"keso/core/Memory\");
		IMClass clazz = method.getIMClass();
		this.alias = clazz.getAlias()+\"_mem_0x\"+Integer.toHexString(addr);
	}

	public int getAddr() {
		return addr;
	}

	public int getSize() {
		return size;
	}

	public IMClass getType() {
		return memory_class;
	}

	public boolean isTypeOf(IMClass type) {
		return memory_class.typeOf(type);
	}
",
    'IMAReadArray' => $init_default,
    'IMAReadLocalVariable' => $init_var_ref,
    'IMAStoreArray' => $init_default,
    'IMAStoreLocalVariable' => $init_var,
    'IMBReadArray' => $init_default,
    'IMBStoreArray' => $init_default,
    'IMCReadArray' => $init_default,
    'IMCStoreArray' => $init_default,
    'IMDAdd' => $init_default,
    'IMDConstant' => $init_const_value,
    'IMDDiv' => $init_default,
    'IMDMul' => $init_default,
    'IMDNeg' => $init_default,
    'IMDReadArray' => $init_default,
    'IMDReadLocalVariable' => $init_var,
    'IMDRem' => $init_default,
    'IMDReturn' => $init_default,
    'IMDStoreArray' => $init_default,
    'IMDStoreLocalVariable' => $init_var,
    'IMDSub' => $init_default,
    'IMFAdd' => $init_default,
    'IMFConstant' => $init_const_value,
    'IMFDiv' => $init_default,
    'IMFMul' => $init_default,
    'IMFNeg' => $init_default,
    'IMFReadArray' => $init_default,
    'IMFReadLocalVariable' => $init_var,
    'IMFRem' => $init_default,
    'IMFReturn' => $init_default,
    'IMFStoreArray' => $init_default,
    'IMFStoreLocalVariable' => $init_var,
    'IMFSub' => $init_default,
    'IMIAdd' => $init_default,
    'IMIBitAnd' => $init_default,
    'IMIBitOr' => $init_default,
    'IMIBitXor' => $init_default,
    'IMIConstant' => $init_const_value,
    'IMIDiv' => $init_default,
    'IMIMul' => $init_default,
    'IMINeg' => $init_default,
    'IMIReadArray' => $init_default,
    'IMIReadLocalVariable' => $init_var,
    'IMIRem' => $init_default,
    'IMIReturn' => $init_default,
    'IMAReturn' => $init_default,
    'IMIShiftLeft' => $init_default,
    'IMIShiftRight' => $init_default,
    'IMIShiftRight2' => $init_default,
    'IMIStoreArray' => $init_default,
    'IMIStoreLocalVariable' => $init_var,
    'IMISub' => $init_default,
    'IMLAdd' => $init_default,
    'IMLBitAnd' => $init_default,
    'IMLBitOr' => $init_default,
    'IMLBitXor' => $init_default,
    'IMLConstant' => $init_const_value,
    'IMLDiv' => $init_default,
    'IMLMul' => $init_default,
    'IMLNeg' => $init_default,
    'IMLReadArray' => $init_default,
    'IMLReadLocalVariable' => $init_var,
    'IMLRem' => $init_default,
    'IMLReturn' => $init_default,
    'IMLShiftLeft' => $init_default,
    'IMLShiftRight' => $init_default,
    'IMLShiftRight2' => $init_default,
    'IMLStoreArray' => $init_default,
    'IMLStoreLocalVariable' => $init_var,
    'IMLSub' => $init_default,
    'IMNullConstant' => $init_const_null,
    'IMSReadArray' => $init_default,
    'IMSStoreArray' => $init_default,
    'IMVReturn' => $init_default,
);
