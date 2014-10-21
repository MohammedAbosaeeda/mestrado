/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.compiler.config;

import keso.compiler.*;
import keso.compiler.config.parser.ParseException;

public class ComplexAttribute extends Set {
	public final String value;

	public ComplexAttribute(Set parent, String name, String setting, int line) throws ParseException {
		super(parent, name, line);
		this.value = setting;
	}

	public String toString() { return ident; }

	/**
	 * Return the information from this cattr in a
	 * format suitable for inclusion within an OIL file.
	 *
	 * If incSubAttrs is true, then Set.toOIL()
	 * will be used to generate the output for the subattributes.
	 */
	public void toOIL(StringBuffer oil, boolean incSubAttrs, String[] supportedSubAttrs, String prefix) 
			throws CompileException {

		oil.append(prefix);
		oil.append(ident.toUpperCase());
		oil.append(" = ");
		oil.append(value.toUpperCase());
		
		if (!incSubAttrs) {
			oil.append(";\n");
			return;
		}

		// Dump the subattributes
		oil.append(" {\n");
		super.toOIL(oil, supportedSubAttrs, prefix + "\t");
		
		oil.append(prefix + "};\n");
	}
}
