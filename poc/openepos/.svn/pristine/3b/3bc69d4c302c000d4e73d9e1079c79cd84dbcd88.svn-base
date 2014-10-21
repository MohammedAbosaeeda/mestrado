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

public class AppmodeDefinition extends Set {
	public AppmodeDefinition(Set parent, String name, int line) {
		super(parent, name, line);
	}

	public String toString() { return "Appmode:"+ ident; }

	/**
	 * Generate an APPMODE block in OIL syntax out of this APPMODE Definition.
	 *
	 * Known and supported attributes
	 * OIL standard:
	 *   (none)
	 */
	public void toOIL(StringBuffer oil, String prefix) throws CompileException {
		oil.append(prefix+"APPMODE " + ident + ";\n\n");
	}

	protected String typeID() { return "appmode_"; }
}
