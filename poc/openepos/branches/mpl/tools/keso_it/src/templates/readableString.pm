%readable=(
'IMAConstant' => '
		if (value instanceof StringCPEntry) {
			return "\""+value.getDescription()+"\"";
		} else {
			return value.getDescription();
		}	
',
'IMAMTConstant' => '
		return "MT";
',
'IMAMemConstant' => '
		return "<0x"+Integer.toHexString(addr)+":"+size+">";
',
'IMPhi' => '
		IMBasicBlockList pred = bb.getPred();
		StringBuffer buf = new StringBuffer("Phi(");
		if (slots!=null) {
			for (int i=0;i<slots.length;i++) {
				if (i>0) buf.append(",");
				if (slots[i]!=null) {
					buf.append(slots[i].toReadableString());
				}
			}
		}
		buf.append(") ");
		return buf.toString();
',
'IMAdd' => '
		return "("+lOpr.toReadableString()+"+"+rOpr.toReadableString()+")";
',
'IMReadLocalVariable' => '
		if (var==null) return "undef";
		return var.toString(); 
',
'IMArrayLength' => '
		return array.toReadableString()+".length";
',
'IMStoreLocalVariable' => '
		if (operant==null) return var.toString()+" = ??? ";
		return var.toString()+" = "+operant.toReadableString();
',
'IMBitAnd' => '
		return "("+lOpr.toReadableString()+" & "+rOpr.toReadableString()+")";
',
'IMBitOr' => '
		return "("+lOpr.toReadableString()+" | "+rOpr.toReadableString()+")";
',
'IMBitXor' => '
		return "("+lOpr.toReadableString()+" ^ "+rOpr.toReadableString()+")";
',
'IMCall' => '
		return "gosub "+targets[0].toLabel();	    
',
'IMCastD2F' => '
		return "(double2float)"+rOpr.toReadableString();	
',
'IMCastD2I' => '
		return "(double2int)"+rOpr.toReadableString();
',
'IMCastD2L' => '
		return "(double2long)"+rOpr.toReadableString();
',
'IMCastF2D' => '
		return "(float2double)"+rOpr.toReadableString();
',
'IMCastF2I' => '
		return "(float2int)"+rOpr.toReadableString();
',
'IMCastF2L' => '
		return "(float2long)"+rOpr.toReadableString();
',
'IMCastI2B' => '
		return "(int2byte)"+rOpr.toReadableString();
',
'IMCastI2C' => '
		return "(int2char)"+rOpr.toReadableString(); 
',
'IMCastI2D' => '
		return "(int2double)"+rOpr.toReadableString();
',
'IMCastI2F' => '
		return "(int2float)"+rOpr.toReadableString();
',
'IMCastI2L' => '
		return "(int2long)"+rOpr.toReadableString();
',
'IMCastI2S' => '
		return "(int2short)"+rOpr.toReadableString();
',
'IMCastL2D' => '
		return "(long2double)"+rOpr.toReadableString();
',
'IMCastL2F' => '
		return "(long2float)"+rOpr.toReadableString();
',
'IMCastL2I' => '
		return "(long2int)"+rOpr.toReadableString();
',
'IMCaughtException' => '
		return "KESO_THR_CONTEXT->pending_exception";
',
'IMCheckCast' => '
		return "("+cpEntry.getClassName()+")("+rOpr.toReadableString()+")";
',
'IMCompare' => '
		return "compare("+lOpr.toReadableString()+","+rOpr.toReadableString()+")";
',
'IMDConstant' => '
		return Double.toString(value); 
',
'IMDGCompare' => '
		return "dcmpg("+lOpr.toReadableString()+","+rOpr.toReadableString()+")";
',
'IMDiv' => '
		return "("+lOpr.toReadableString()+"/"+rOpr.toReadableString()+")";
',
'IMDLCompare' => '
		return "dcmpl("+lOpr.toReadableString()+","+rOpr.toReadableString()+")";
',
'IMDRem' => '
		return "("+lOpr.toReadableString()+"%"+rOpr.toReadableString()+")";
',
'IMDSub' => '
		return "("+lOpr.toReadableString()+"-"+rOpr.toReadableString()+")";
',
'IMEQConditionalBranch' => '
		return "if ("+lOpr.toReadableString()+"=="+rOpr.toReadableString()+") goto "+targets[1].toLabel();
',
'IMFConstant' => '
		return Float.toString(value);
',
'IMFGCompare' => '
		return "fcmpg("+lOpr.toReadableString()+","+rOpr.toReadableString()+")";
',
'IMFLCompare' => '
		return "fcmpl("+lOpr.toReadableString()+","+rOpr.toReadableString()+")";
',
'IMFRem' => '
		return "("+lOpr.toReadableString()+"%"+rOpr.toReadableString()+")";
',
'IMGEConditionalBranch' => '
		return "if ("+lOpr.toReadableString()+">="+rOpr.toReadableString()+") goto "+targets[1].toLabel();
',
'IMGetField' => '
		return rOpr.toReadableString()+"."+cpEntry.getMemberName();
',
'IMGetStatic' => '
		return cpEntry.getClassName()+"."+cpEntry.getMemberName();
',
'IMGoto' => '
		if (hasShortcut()) return "/* goto "+targets[0].toLabel()+" */";
		return "goto "+targets[0].toLabel();	    
',
'IMGTConditionalBranch' => '
		return "if ("+lOpr.toReadableString()+">"+rOpr.toReadableString()+") goto "+targets[1].toLabel();
',
'IMIConstant' => '
		return Integer.toString(value); 
',
'IMInstanceOf' => '
		return "("+rOpr.toReadableString()+" instanceof "+cpEntry.getClassName()+")";
',
'IMInvoke' => '
		String retString = "(";
		int i=0;
		while (i<args.length)  {
			if (args[i]!=null) retString += args[i].toReadableString();
			else retString += "null";
			i++;
			if (i<args.length) retString+=",";
		}
		return retString+")";
',
'IMInvokeInterface' => '
		String retString = "";
		if (obj!=null) retString = obj.toReadableString()+".";
		retString += cpEntry.getMemberName();
		retString += super.toReadableString();
		return retString;
',
'IMInvokeInterface' => '
		String retString = "";
		if (obj!=null) retString = obj.toReadableString()+".";
		retString += cpEntry.getMemberName();
		retString += super.toReadableString();
		return retString;
',
'IMInvokeSpecial' => '
		String retString = "<stack>";
		if (obj!=null) retString = obj.toReadableString()+".";
		retString += cpEntry.getMemberName();
		return retString += super.toReadableString();
',
'IMInvokeStatic' => '
		String retString = cpEntry.getMemberName();
		return retString += super.toReadableString();
',
'IMInvokeVirtual' => '
		String retString = obj.toReadableString()+".";
		retString += cpEntry.getMemberName();
		return retString += super.toReadableString();
',
'IMLCompare' => '
		return "compare("+lOpr.toReadableString()+","+rOpr.toReadableString()+")";
',
'IMLConstant' => '
		return Long.toString(value); 
',
'IMLEConditionalBranch' => '
		if (rOpr==null) return "if ("+lOpr.toReadableString()+"<=0) goto "+targets[1].toLabel();
		return "if ("+lOpr.toReadableString()+"<="+rOpr.toReadableString()+") goto "+targets[1].toLabel();
',
'IMLookupSwitch' => '
		String output = "lswitch ("+operant.toReadableString()+") {\n";
		for (int i=1;i<targets.length;i++) {
			output +=  "\t"+Integer.toString(keys[i-1])+":"+
				targets[i].toLabel() + "\n";
		}       
		output += "\t\t::"+targets[0].toLabel() + "\n";
		return output+"\t}";
',
'IMLRem' => '
		return "("+lOpr.toReadableString()+"%"+rOpr.toReadableString()+")";
',
'IMLTConditionalBranch' => '
		String rStr = ( rOpr == null ? "???" : rOpr.toReadableString());
		String tStr = ( targets==null || targets[1] == null ? "???" : targets[1].toLabel());
		if (rOpr==null) return "if ("+lOpr.toReadableString()+"<0) goto "+tStr;
		return "if ("+lOpr.toReadableString()+"<"+rStr+") goto "+tStr;
',
'IMMonitor' => '
		if (bytecode==BC.MONITORENTER) {
			return operant.toReadableString()+".enter()";
		}
		return operant.toReadableString()+".leave()";
',
'IMMul' => '
		return "("+lOpr.toReadableString()+"*"+rOpr.toReadableString()+")";
',
'IMMultiOperant' => '
		String retString = "(";
		int i=0;
		while (i<args.length)  {
			if (args[i]!=null) retString += args[i].toReadableString();
			else retString += "null";
			i++;
			if (i<args.length) retString+=",";
		}
		return retString+")";
',
'IMNEConditionalBranch' => '
		return "if ("+lOpr.toReadableString()+"!="+rOpr.toReadableString()+") goto "+targets[1].toLabel();
',
'IMNeg' => '
		return "-"+rOpr.toReadableString();
',
'IMNewArray' => '
		return "keso_allocArray("+BCBasicDatatype.toString(datatype)+","+size.toReadableString()+")";
',
'IMNew' => '
		return "keso_allocObject("+cpEntry.getClassName()+")";
',
'IMNewMultiArray' => '
		String retValue =  "keso_allocMultiArray("+cpEntry.getClassName();
		for (int i=0;i<oprs.length;i++) {
			retValue += "," + oprs[i].toReadableString();
		}
		return retValue+")";
',
'IMNewObjArray' => '
	return "keso_allocObjArray("+cpEntry.getClassName()+","+size.toReadableString()+")";
',
'IMNullConstant' => ' return "NULL"; ',
'IMPopReturnAddr' => '
	return "return_addr";
',
'IMPutField' => '
		return obj.toReadableString()+"."+cpEntry.getMemberName()+" = " + rvalue.toReadableString();
',
'IMPutStatic' => '
		return cpEntry.getMemberName()+" = "+rvalue.toReadableString();
',
'IMReadArray' => '
		return aOpr.toReadableString()+"["+iOpr.toReadableString()+"]";
',
'IMRem' => '
		return "("+lOpr.toReadableString()+"%"+rOpr.toReadableString()+")";
',
'IMReturn' => '
		if (rvalue==null) return "return ???";
		return "return "+rvalue.toReadableString();	    
',
'IMEpilog' => '
		if (method.getBasicReturnType()==BCBasicDatatype.VOID) {
			return "return";	    
		} else {
			if (var==null) return "return ???";
			return "return "+var.toString();	    
		}
',
'IMReturnSubroutine' => '
		return "ret "+var.toString();	    
',
'IMShiftLeft' => '
		return "("+lOpr.toReadableString()+" << "+rOpr.toReadableString()+")";
',
'IMShiftRight2' => '
		return "("+lOpr.toReadableString()+" >>> "+rOpr.toReadableString()+")";
',
'IMShiftRight' => '
		return "("+lOpr.toReadableString()+" >> "+rOpr.toReadableString()+")";
',
'IMStoreArray' => '
		return aOpr.toReadableString()+"[" +iOpr.toReadableString()+"] = " +rvalue.toReadableString();
',
'IMSub' => '
		return "("+lOpr.toReadableString()+"-"+rOpr.toReadableString()+")";
',
'IMTableSwitch' => '
		StringBuffer output = new StringBuffer("tswitch (");
		output.append(operant.toReadableString());
		output.append(") {\n");

		for (int i=1;i<targets.length;i++) {
			output.append("\tcase ");
			output.append(low+i);
			output.append(": goto ");
			output.append(targets[i].toLabel());
			output.append(";\n");
		}
		output.append("\tdefault: goto ");
		output.append(targets[0].toLabel());
		output.append(";\n}\n");

		return output.toString();
',
'IMThrow' => '
		if (exception==null) return "throw <error null>";
		return "throw "+exception.toReadableString();
',
'IMVReturn' => '
		return "return";
',
);
