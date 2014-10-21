/**(c)

  Copyright (C) 2005-2007 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.compiler.config;

import keso.compiler.*;
import keso.compiler.config.parser.ParseException;
import keso.util.Debug;

import java.util.Vector;
import java.util.Iterator;

final public class WorldDefinition extends Set {

	private Attribut[] attr_lst = null;
	private int pos = 0;


	public WorldDefinition(String name, int line) throws ParseException {
		super(name, line);
	}


	public void addSystem(SystemDefinition system) {
		BuilderOptions.getOpts().verbose("system: "+system);
		addAttribute(ident,system);
	}

	public void addNetwork(NetworkDefinition net) {
		BuilderOptions.getOpts().verbose("Network: " + net);
		addAttribute(ident, net);
	}

	public void rewindSystemDef() {
		pos = 0;
	}

	public SystemDefinition nextSystemDef() {

		if (attr_lst == null) {
			attr_lst = getAllAttributes();
		}

		while (pos < attr_lst.length) {
			if (attr_lst[pos] instanceof SystemDefinition) {             
				return (SystemDefinition)attr_lst[pos++];
			}

			++pos;
		} 

		return null;
	}
}
