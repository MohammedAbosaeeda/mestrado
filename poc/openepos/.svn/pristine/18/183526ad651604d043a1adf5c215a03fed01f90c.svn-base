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

public class ComplexBoolAttribute extends ComplexAttribute {
	public final boolean setting;
	
	public ComplexBoolAttribute(Set parent, String name, String setting, int line) throws ParseException {
		super(parent, name, setting, line);
		if ( setting.compareToIgnoreCase("true") == 0) this.setting = true;
		else if ( setting.compareToIgnoreCase("false") == 0) this.setting = false;
		else throw new ParseException("Setting must be either true or false");
	}

	/**
	 * Return the information from this cattr in a
	 * format suitable for inclusion within an OIL file.
	 *
	 * When the state of this cattr is false, then subattributes
	 * will never be included.
	 * If the state and incSubAttrs are both true, then Set.toOIL()
	 * will be used to generate the output for the subattributes.
	 */
	public void toOIL(StringBuffer oil, boolean incSubAttrs, String[] supportedSubAttrs, String prefix) 
			throws CompileException {
		super.toOIL(oil,(setting&&incSubAttrs), supportedSubAttrs, prefix);
	}
}
