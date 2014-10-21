
/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.compiler.kni;

import keso.compiler.imcode.*;
import keso.compiler.*;
import keso.compiler.config.*;
import keso.compiler.backend.Coder;

public class Tricore extends Weavelet {

	private static final boolean inline_endinit = true;

	public Tricore(BuilderOptions opts, ClassStore repository) {
		super(opts, repository);
		joinPoints = new String[] {
			"keso/driver/tricore/tc1796b/TricorePWR.<CLASS>",
			"keso/driver/tricore/tc1796b/TricorePWR.*",
		};
	}

	/**
	 *
	 *
	 * NOTE:
	 *     opts.getTarget() is not valid in this method!
	 */
	public boolean ignoreMethodBody(IMClass clazz, IMMethod method) throws CompileException {
		if (method.termed("unlockSystemRegisters")) {
			return true;
		} else if (method.termed("lockSystemRegisters")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean addFields(IMClass clazz, Coder coder, StringBuffer raw_fields) throws CompileException {
		coder.global_header_add("extern int endinit_status;\n");
		coder.global_add("int endinit_status;\n");
		return false;
	}

	public boolean affectMethod(IMClass clazz, IMMethod method, Coder coder) throws CompileException {
		if (method.termed("unlockSystemRegisters")) {
			coder.addln("\tTRICORE_clearEndinit(endinit_status);");
			return true;

		} else if (method.termed("lockSystemRegisters")) {
			coder.addln("\tTRICORE_setEndinit(endinit_status);");
			return true;

		} else {
			return false;
		}
	}


	public boolean affectInvokeStatic(IMInvoke node, IMMethod caller, IMMethod callee,
			IMNode args[], Coder coder) throws CompileException {
		if (inline_endinit) {
			if (callee.termed("unlockSystemRegisters")) {
				coder.add("TRICORE_clearEndinit(endinit_status)");
				return true;

			} else if (callee.termed("lockSystemRegisters")) {
				coder.add("TRICORE_setEndinit(endinit_status)");
				return true;
			}
		}
		return false;
	}
}
