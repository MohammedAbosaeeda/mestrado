/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.compiler.config;

public class StringAttr extends Attribut {

	private String value;
	
	public StringAttr(Set parent, String ident, String value, int line) {
		super(parent, ident, line);
		this.value = value;
	}

	public int valueInt() {
		return Integer.parseInt(value);
	}

	public double valueDouble() {
		return Double.parseDouble(value);
	}

	public String valueString() {
		return value;
	}
}
