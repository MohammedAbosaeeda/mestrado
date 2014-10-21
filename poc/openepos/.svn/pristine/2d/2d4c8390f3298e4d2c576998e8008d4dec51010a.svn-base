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

/**
 * This class represents the Definition of the special public domain.
 * All global resources will have their domainid set to this domain.
 *
 * Some of the HookRoutines cannot be assigned a Task and therefore run in
 * the public domain.
 *  - StartUpHook, except constructors for local resources
 *  - ShutdownHook (TODO, set domain upon entering the ShutdownHook)
 *  - ErrorHook, but only if the error was caused in the public domain
 *  - global ISRs (TODO) and their Pre/PostIsrHooks
 *  - global alarm-callback
 */
public final class PublicDomain extends DomainDefinition {
	public PublicDomain(SystemDefinition system, int line) throws ParseException {
		super(system, "PublicDomain", line);
	}

	public String getIdentifier() { return "PublicDomain"; }
}
