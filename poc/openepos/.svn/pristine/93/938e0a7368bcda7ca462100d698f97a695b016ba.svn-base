/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.compiler.config;

import java.util.Vector;
import java.util.Enumeration;

public class ArrayAttr extends Attribut {

	private Vector value = new Vector();
	
	public ArrayAttr(Set parent, String ident, int line) {
		super(parent, ident, line);

	}

	public void add(String v) {
		value.addElement(v);
	}

	public int valueInt() { return value.size(); }

	public double valueDouble() { return (double)valueInt(); }

	public String valueString() { return value.toString(); }

	public Vector valueArray() {
		return value;
	}

	public int[] values() { 
		Enumeration elms = value.elements();
		int i=0;
		int arr[] = new int[value.size()];
		while (elms.hasMoreElements()) {
			arr[i++]=Integer.parseInt((String)elms.nextElement());
		}
		return arr;
	}
}
