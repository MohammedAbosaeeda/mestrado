/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.compiler.config;

public class IntegerAttr extends Attribut {
	private int value;
	
	public IntegerAttr(Set parent, String ident, String value, int line) {
		super(parent, ident, line);
		this.value = Integer.parseInt(value);
	}

	public int valueInt() {
		return value;
	}

	public double valueDouble() {
		return (double)value;
	}

	public String valueString() {
		return (new Integer(value)).toString();
	}
}
