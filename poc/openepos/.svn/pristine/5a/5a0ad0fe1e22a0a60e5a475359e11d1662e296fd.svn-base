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

public class CounterDefinition extends Set {
	public CounterDefinition(Set parent, String name, int line) {
		super(parent, name, line);
	}

	public String toString() { return "Counter:"+ ident; }

	/**
	 * Generate a COUNTER block in OIL syntax out of this COUNTER Definition.
	 *
	 * Known and supported attributes
	 * OIL standard:
	 *   MINCYCLE        (UINT32)
	 *   TICKSPERBASE    (UINT32)
	 *   MAXALLOWEDVALUE (UINT32)
	 *
	 * ProOSEK extensions:
	 *   TRICORE_TIMER    (GPTU{0,1}_T{0,1}, STM_T{0,1}, USERCOUNTER)
	 *   TRICORE_IRQLEVEL (UINT32)
	 *   TIME_IN_NS       (UINT32)
	 */
	public void toOIL(StringBuffer oil, String prefix) throws CompileException {
		String[] supportedAttributes = { "MINCYCLE", "TICKSPERBASE",
			"MAXALLOWEDVALUE", "TRICORE_TIMER", "TRICORE_IRQLEVEL",
			"TIME_IN_NS" };
		
		oil.append(prefix+"COUNTER " + getIdentifier() + " {\n");
		super.toOIL(oil, supportedAttributes, prefix+"\t");
		oil.append(prefix+"};\n\n");
	}

	protected String typeID() { return "c_"; }
}
