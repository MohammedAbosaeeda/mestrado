/**(c)

  Copyright (C) 2006 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.compiler.imcode; 

import keso.classfile.*;
import keso.classfile.constantpool.*; 
import keso.classfile.datatypes.*; 

import keso.compiler.*;
import keso.compiler.backend.*;

import java.util.Vector;
import java.util.Hashtable;
import java.util.Enumeration;

final public class IMExceptionHandler {

	private IMMethod method;
	private ExceptionHandlerData expData;
	private IMBasicBlock handler;
	private IMClass type;

	public IMExceptionHandler(IMMethod method, ExceptionHandlerData source) {
		this.method = method;
		this.expData=source;
	}

	public void setHandler(IMBasicBlock handler) {
		this.handler=handler;
	}

	public IMBasicBlock getHandler() {
		return this.handler;
	}

	public int getStartPos() {
		return expData.getStartBCIndex();
	}

	public int getEndPos() {
		return expData.getEndBCIndex();
	}

	public int getRangeID() {
		return (expData.getStartBCIndex() << 16) + expData.getEndBCIndex();
	}

	public boolean inRegion(int position) {
		return (expData.getStartBCIndex()<=position && position<expData.getEndBCIndex());
	}

	public String getClassName() {
		return expData.getCatchTypeCPEntry();
	}

	public IMClass getIMClass() {
		if (type==null) type=method.getIMClass(expData.getCatchTypeCPEntry());
		return type; 
	}
}
