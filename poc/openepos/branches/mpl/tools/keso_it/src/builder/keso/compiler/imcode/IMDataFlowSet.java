/**(c)

  Copyright (C) 2005-2006 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.compiler.imcode; 

import java.util.Hashtable;
import java.util.Enumeration;

import keso.compiler.CompileException;
import keso.util.IntegerHashtable;
import keso.classfile.datatypes.BCBasicDatatype;

public class IMDataFlowSet extends IMDataFlow {

	int datatype = -1;
	Hashtable dataflows = new Hashtable();

	public IMDataFlowSet(IMDataFlow df, IMDataFlow df2) {
		super(df.opts);
		addConstValue(df);
		addConstValue(df2);
		if (df instanceof IMDataFlowConstValue) {
			try {
			datatype = ((IMDataFlowConstValue)df).getValue().getDatatype();
			} catch (CompileException ex) {
			}
		}
	}

	public IMDataFlow fold(IMDataFlow flow) throws CompileException {
		if (flow.isAny()) return flow;
		return addConstValue(flow);
	}

	public boolean isSet() {
		return true;
	}

	public IMDataFlow addConstValue(IMDataFlow df) {
		if (df.isSet()) {
			Enumeration elements = ((IMDataFlowSet)df).elements();
			while (elements.hasMoreElements()) {
				IMDataFlow dfi = (IMDataFlow)elements.nextElement();
				dataflows.put(dfi, dfi);
			}
			return this;
		}
		dataflows.put(df,df);
		return this;
	}

	public Enumeration elements() {
		return dataflows.elements();
	}

	public String toReadableString() {
		if (datatype==BCBasicDatatype.INT) {
			StringBuffer str = new StringBuffer("[");
			try {
				IntegerHashtable ht = new IntegerHashtable();
				Enumeration values = dataflows.elements();
				while (values.hasMoreElements()) {
					IMDataFlow df = (IMDataFlowConstValue)values.nextElement();
					int value = df.getValue().getIntValue();
					ht.put(value, df);

				}

				int ks[] = ht.sortedKeys();
				if (ks.length<4) {
					for (int i=0;i<ht.size();i++) {
						IMDataFlow df = (IMDataFlow)ht.get(ks[i]);
						if (i>0) str.append(",");
						str.append(df.toReadableString());
					}
				} else {
					str.append(ks[0]);
					str.append("-");
					str.append(ks[ks.length-1]);
				}
			} catch (CompileException ex) {
			}
			return str.append("]").toString();
		} else {
			Enumeration values = dataflows.elements();
			StringBuffer str = new StringBuffer("[");
			while (values.hasMoreElements()) {
				IMDataFlow df = (IMDataFlow)values.nextElement();
				str.append(df.toReadableString());
				if (values.hasMoreElements()) str.append(",");
			}
			return str.append("]").toString();
		}
	}
}
