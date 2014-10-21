/**(c)

  Copyright (C) 2007 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.compiler.imcode; 

import keso.compiler.*;
import keso.compiler.kni.*;
import keso.compiler.backend.Coder;
import keso.compiler.config.*;

import keso.classfile.datatypes.BCBasicDatatype;

public class IMMethodNotFound extends IMSystemMethod {

	private String sig;

	public IMMethodNotFound(IMClass clazz, String sig) throws CompileException {
		super(clazz);
		this.sig = sig;
	}

	public String getMethodName() {
		return "unknown";
	}

	public String getArgString() throws CompileException {
		return "...";
	}

	public String getReturnType() {
		int pos = sig.indexOf(")");
		int return_type = BCBasicDatatype.toBasicDatatype(sig.charAt(pos+1)); 
		return BuilderOptions.typeToString(return_type);
	}

	public String getMethodNameAndType() {
		return "unknown: "+sig;
	}

	public void analyseCallGraph(IMCallGraphVisitor visitor) throws CompileException {
		return;
	}

	public void translate(Coder coder) throws CompileException {
		opts.warn("method not found! "+this.getClass().getName()+"."+sig);
	}
}
