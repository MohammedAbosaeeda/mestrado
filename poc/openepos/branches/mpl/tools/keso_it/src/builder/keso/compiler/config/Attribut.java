/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.compiler.config;

import keso.compiler.CompileException;

abstract public class Attribut {

	public final Attribut  parent;
	public final String    ident;	
	protected int       line;

	public Attribut(Attribut parent, String ident, int line) {
		this.parent = parent;
		this.ident  = ident;
		this.line   = line;
	}

	public Attribut(Attribut attr) {
		this.parent = attr.parent;
		this.ident  = attr.ident;
		this.line   = attr.line;
	}

	public String getIdentifier() {
		if (parent==null) return ident;
		return parent.getIdentifier()+"_"+typeID()+ident;
	}
	
	protected String typeID() { return ""; }
	
	public int getLine() { return line; }

	public void toOIL(StringBuffer oil, String prefix) throws CompileException {
		oil.append(prefix);
		oil.append(ident.toUpperCase());
		oil.append(" = ");
		oil.append(valueString().toUpperCase());
		oil.append(";\n");
	}

	public void registerFinalize(Set set) { throw new Error("abstract"); }

	abstract public int valueInt(); 
	abstract public double valueDouble(); 
	abstract public String valueString();
}
