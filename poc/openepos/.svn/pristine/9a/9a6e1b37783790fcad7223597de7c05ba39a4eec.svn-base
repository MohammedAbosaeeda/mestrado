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

import java.util.Vector;

public final class NativeResource extends ResourceDefinition {
	public NativeResource(SystemDefinition sys, String name) {
		super(sys, name, -1);
	}

	public void toOIL(StringBuffer oil, String prefix) throws CompileException {
		oil.append(prefix);
		oil.append("RESOURCE ");
		oil.append(getIdentifier());
		oil.append(" {\n");

		oil.append(prefix);
		oil.append("\tRESOURCEPROPERTY = STANDARD;\n");
		oil.append(prefix);
		oil.append("};\n");
	}

	public String getIdentifier() { return ident; }
}
