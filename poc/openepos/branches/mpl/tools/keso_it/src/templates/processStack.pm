%stack=(
'IMArrayLength-vars' => 'protected IMNode array;',
'IMArrayLength' => '
		stack.store(bcPosition, extra_ops);

		array = stack.pop();
		assertIsReference(array);
		stack.push(this);
		return null;
',
'IMCall' => '
		stack.push(method.createPopReturnAddr(bcPosition));
      		return this;
',
'IMCaughtException' => '
		stack.push(this);
		return null;
    ',
'IMCheckCast' => '
		method.isConst=false;
		method.isPure=false;
		rOpr = stack.pop();
		assertIsReference(rOpr);
		stack.push(this);
		return null;
',
'IMConstant' => '
		stack.push(this);
		return null;
',
'IMGetField-vars' => 'protected IMNode rOpr;',
'IMGetField' => '
		method.isConst=false;
		stack.store(bcPosition, extra_ops);
		rOpr = stack.pop();
		assertIsReference(rOpr);
		stack.push(this);
      		return null;
',
'IMGetStatic' => '
		method.isConst=false;
		stack.push(this);
		return null;
',
'IMGoto' => '
		return this;
',
'IMInstanceOf-vars' => 'protected IMNode rOpr;',
'IMInstanceOf' => '
		rOpr = stack.pop();
		assertIsReference(rOpr);
		stack.push(this);
		return null;
',
'IMInvoke-vars' => 'protected IMNode[] args;
	protected IMNode obj;',
'IMInvoke' => '
		int[] argTypes = typeDesc.getBasicArgumentTypes();

		stack.store(bcPosition, extra_ops);

		args = new IMNode[argTypes.length];
		for (int i=(argTypes.length-1);i>=0;i--) {
			args[i] = stack.pop();
		}

		obj = stack.pop();

		if (datatype==BCBasicDatatype.VOID) return this;
	    	stack.push(this);

	    	return null;
',
'IMInvokeStatic' => '
		int[] argTypes = typeDesc.getBasicArgumentTypes();

		args = new IMNode[argTypes.length];
		for (int i=(argTypes.length-1);i>=0;i--) {
			args[i] = stack.pop();
		}

		if (datatype==BCBasicDatatype.VOID) return this;
	    	stack.push(this);

	    	return null;
',
'IMLookupSwitch-vars' => 'protected IMNode operant;',
'IMLookupSwitch' => '
		operant = stack.pop();
		return this;
    ',
'IMMonitor-vars' => 'protected IMNode operant;',
'IMMonitor' => '
		method.isConst=false;
		method.isPure=false;
		operant = stack.pop();
		assertIsReference(operant);
		stack.store(bcPosition, extra_ops);

		return this;
',
'IMNewArray-vars' => 'protected IMNode size;',
'IMNewArray' => '
		method.isConst=false;
		method.isPure=false;
		size = stack.pop();
		assertIsIntValue(size);
		stack.store(bcPosition,extra_ops);
		stack.push(this);
		return null;
',
'IMNew' => '
		method.isConst=false;
		method.isPure=false;
		stack.store(bcPosition,extra_ops);
		stack.push(this);
		return null;
    ',
'IMNewMultiArray-vars' => 'protected IMNode[] oprs; ',
'IMNewMultiArray' => '
		method.isConst=false;
		method.isPure=false;
		oprs = new IMNode[dim];
		for (int i=0;i<dim;i++) {
			oprs[i]=stack.pop();
			assertIsIntValue(oprs[i]);
		}
		stack.store(bcPosition,extra_ops);
		stack.push(this);
		return null;
',
'IMNewObjArray-vars' => 'protected IMNode size;',
'IMNewObjArray' => '
		method.isConst=false;
		method.isPure=false;
		size = stack.pop();
		assertIsIntValue(size);
		stack.store(bcPosition,extra_ops);
		stack.push(this);
		return null;
',
'IMNop' => '
		return this;
',
'IMPopReturnAddr' => '
		stack.push(this);
		return null;
',
'IMPutField-vars' => 'protected IMNode rvalue;
	protected IMNode obj;',
'IMPutField' => '
		method.isConst=false;
		method.isPure=false;
		stack.store(bcPosition,extra_ops);
		rvalue = stack.pop();
		obj    = stack.pop();
		assertIsReference(obj);
		return this;
',
'IMPutStatic-vars' => 'protected IMNode rvalue;',
'IMPutStatic' => '
		method.isConst=false;
		method.isPure=false;
		rvalue = stack.pop();
		stack.store(bcPosition,extra_ops);
		return this;
',
'IMReadArray-vars' => 'protected IMNode iOpr;
	protected IMNode aOpr;',
'IMReadArray' => '
		method.isConst=false;
		stack.store(bcPosition, extra_ops);

		iOpr = stack.pop();
		assertIsIntValue(iOpr);
		aOpr = stack.pop();
		assertIsReference(aOpr);
		stack.push(this);
		return null;
',
'IMReadLocalVariable' => '
		stack.push(this);
		return null;
',
'IMReturn-vars' => 'protected IMNode rvalue;',
'IMReturn' => ' 
		rvalue = stack.pop();
		return this;
',
'IMReturnSubroutine' => '
		IntegerHashtable ret_index = method.getReturnTargets();
		int keys[] = ret_index.sortedKeys();
		if (targets==null) {
			targets = new IMBasicBlock[keys.length];
			for (int i=0;i<keys.length;i++) {
				targets[i]=(IMBasicBlock)ret_index.get(keys[i]);
			}
		} else {
			if (keys.length != targets.length) {
				opts.warn("incomplete jump targets in return subroutine!");
			}
		}
		return this;
',
'IMStackOperationDUP2' => '
		IMNode opr1,opr2;

		stack.store(bcPosition,extra_ops);
		opr1 = stack.pop();

		if (opr1.getDatatype()==-1) 
			throw new CompileException("wrong datatype on stack");

		if (isCategory2(opr1)) {
			stack.push(opr1);
			stack.push(opr1.copy(null));
			return null;
		} 

		opr2 = stack.pop();
		stack.push(opr2);
		stack.push(opr1);
		stack.push(opr2.copy(null));
		stack.push(opr1.copy(null));

		return null;
',
'IMStackOperationDUP2_X1' => '
		IMNode opr1,opr2,opr3;

		stack.store(bcPosition,extra_ops);

		opr1 = stack.pop();
		opr2 = stack.pop();
		if (isCategory2(opr1)) {
			stack.push(opr1);
			stack.push(opr2);
			stack.push(opr1.copy(null));
		} else {
			opr3 = stack.pop();
			stack.push(opr2);
			stack.push(opr1);
			stack.push(opr3);
			stack.push(opr2.copy(null));
			stack.push(opr1.copy(null));
		}

		return null;
',
'IMStackOperationDUP2_X2' => '
		IMNode opr1,opr2,opr3,opr4;

		stack.store(bcPosition,extra_ops);
		opr1 = stack.pop();
		opr2 = stack.pop();
	    	if (isCategory2(opr1)) {
			if (isCategory2(opr2)) {
				stack.push(opr1);
			} else {
				opr3 = stack.pop();
				stack.push(opr1);
				stack.push(opr3);
			}
			stack.push(opr2);
			stack.push(opr1.copy(null));
	    	} else {
			opr3 = stack.pop();
			if (isCategory2(opr3)) {
				stack.push(opr2);
				stack.push(opr1);
				stack.push(opr3);
			} else {
				opr4 = stack.pop();
				stack.push(opr2);
				stack.push(opr1);
				stack.push(opr4);
				stack.push(opr3);
			}
			stack.push(opr2.copy(null));
			stack.push(opr1.copy(null));
		}

		return null;
',
'IMStackOperationDUP' => '
		IMNode opr;

		stack.store(bcPosition,extra_ops);
		opr = stack.pop();
		stack.push(opr);
		stack.push(opr.copy(null));

		return null; 
',
'IMStackOperationDUP_X1' => '
		IMNode opr1,opr2;

		stack.store(bcPosition,extra_ops);
		opr1 = stack.pop();
		opr2 = stack.pop();
		stack.push(opr1);
		stack.push(opr2);
		stack.push(opr1.copy(null));

		return null;
',
'IMStackOperationDUP_X2' => '
		IMNode opr1,opr2,opr3;

	    	stack.store(bcPosition,extra_ops);
		opr1 = stack.pop();
		opr2 = stack.pop();
		if (isCategory2(opr2)) {
			stack.push(opr1);
			stack.push(opr2);
			stack.push(opr1.copy(null));
			return null;
	    	}
		opr3 = stack.pop();
		stack.push(opr1);
		stack.push(opr3);
		stack.push(opr2);
		stack.push(opr1.copy(null));

		return null;
',
'IMStackOperationPOP2' => '
		IMNode opr,opr2;

		opr=stack.pop();
		if (isCategory2(opr)) return null;

		opr2=stack.pop();
		if (isCategory2(opr2)) throw new CompileException();

		IMNode ret=null;

		if (opr instanceof IMInvoke) ret=opr;
		if (opr2 instanceof IMInvoke) {
			 if (ret!=null) { ret.append(opr2); } else { ret=opr2; }
		}
		
		return ret;
',
'IMStackOperationPOP' => '
		IMNode opr;

		opr=stack.pop();
		if (opr instanceof IMInvoke) extra_ops = extra_ops.append(opr);

		return null;
',
'IMStackOperationSWAP' => '
	        IMNode opr1,opr2;

		stack.store(bcPosition,extra_ops);
		opr1 = stack.pop();
		opr2 = stack.pop();
		stack.push(opr1);
		stack.push(opr2);

		return null;
',
'IMStoreArray-vars' => 'protected IMNode rvalue;
	protected IMNode iOpr;
	protected IMNode aOpr;',
'IMStoreArray' => '
		method.isPure=false;
		method.isConst=false;
		stack.store(bcPosition, extra_ops);

		rvalue = stack.pop();
		iOpr   = stack.pop();
		assertIsIntValue(iOpr);
		aOpr   = stack.pop();
		assertIsReference(aOpr);
		return this;
',

'IMStoreLocalVariable-vars' => 'protected IMNode operant;',
'IMStoreLocalVariable' => '
		operant = stack.pop();
		stack.store2(bcPosition, extra_ops, var);
		return this;
',
'IMTableSwitch-vars' => 'protected IMNode operant;',
'IMTableSwitch' => '
		operant=stack.pop();
		return this;
',
'IMThrow-vars' => 'protected IMNode exception;',
'IMThrow' => '
		method.isConst=false;
		method.isPure=false;
		exception = stack.pop();	
		stack.clear();
		return this;
',
'IMVReturn' => ' 
		return this;
',
);
