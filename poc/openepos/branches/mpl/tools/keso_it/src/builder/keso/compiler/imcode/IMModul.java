/**(c)

  Copyright (C) 2005,2006,2007 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.compiler.imcode; 

import keso.compiler.*;

public abstract class IMModul {

	final protected String name;
	final protected BuilderOptions opts;

	protected String alias;

	public IMModul(String name, String alias, BuilderOptions opts) {
		this.name = name;
		this.alias = alias;
		this.opts = opts;
	}

	public String getAlias() {
		return alias;
	}

	public String getClassName() {
		return name;
	}

	public BuilderOptions getOptions() {
		return opts;
	}
}
