/**(c)

  Copyright (C) 2005-2006 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.compiler.imcode; 

import keso.compiler.*;
import keso.compiler.backend.Coder;

import java.util.Vector;
import java.util.Enumeration;

final public class IMCallerEntry {

	IMMethod from;
	IMDataFlow[] argsInfo;

	IMCallerEntry(IMMethod from, IMDataFlow[] argsInfo) {
		this.from = from;
		this.argsInfo = argsInfo;
	}

	String getAlias() {
		if (from!=null) return from.getAlias();
		return "<kore>";
	}

	IMDataFlow elementAt(int i) {
		if (argsInfo==null) return null;
		return argsInfo[i];
	}

	int length() { return argsInfo.length; }

	IMConstant getValue(int i) throws CompileException {
		return argsInfo[i].getValue();
	}

	static IMCallerEntry getFoldedCallerEntry(Vector entries) throws CompileException {
		if (entries==null) return null;
		return new IMCallerEntry(null, getFoldedArgs(entries));
	}

	static IMDataFlow[] getFoldedArgs(Vector entries) throws CompileException {
		Enumeration called = entries.elements();
		IMDataFlow[] nentry=null;
		while (called.hasMoreElements()) {
			IMCallerEntry m = (IMCallerEntry)called.nextElement();
			IMDataFlow[] mf = m.argsInfo;
			if (mf==null) return null;
			if (nentry==null) {
				nentry=new IMDataFlow[mf.length]; 
				for (int i=0;i<mf.length;i++) {
					nentry[i]=mf[i].copy();
				}
			} else {
				for (int i=0;i<mf.length;i++) {
					nentry[i] = nentry[i].fold(mf[i].copy());
				}
			}
		}
		return nentry;
	}

	static void emitCallerInfo(Coder coder, IMMethod method, Vector called_from) throws CompileException {
		coder.local_add("/*\n * call analyse ");
		coder.local_add(called_from.size());
		coder.local_add(" ");
		coder.local_add(method.getAlias());
		coder.local_add("\n");
		Enumeration called = called_from.elements();
		while (called.hasMoreElements()) {
			IMCallerEntry m = (IMCallerEntry)called.nextElement();
			coder.local_add(" *\n * ");
			coder.local_add(m.getAlias());
			coder.local_add("\n * ");
			coder.local_add(m.showArgs());
			coder.local_add("\n");
		}
		IMCallerEntry m = IMCallerEntry.getFoldedCallerEntry(called_from);
		coder.local_add(" *\n * total ");
		coder.local_add(m.showArgs());
		coder.local_add("\n");
		if (method.getOptions().hasOption("show_costs")) {
			coder.local_add(" *\n * costs: ");
			coder.local_add(method.getCosts());
			coder.local_add("\n");
		}
		coder.local_add(" */\n\n");

	}

	String showArgs() {
		if (argsInfo==null) return "()";
		StringBuffer nb = new StringBuffer();
		nb.append("(");
		for (int i=0;i<argsInfo.length;i++)  {
			if (i>0) nb.append(",");
			nb.append(argsInfo[i].toReadableString());
		}
		nb.append(")");
		return nb.toString();
	}
}
