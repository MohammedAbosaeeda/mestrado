/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.compiler.kni;

import keso.compiler.*;
import keso.compiler.imcode.*;
import keso.compiler.backend.*;

public class OSService extends Weavelet {

	public OSService(BuilderOptions opts, ClassStore repository) {
		super(opts, repository);
		joinPoints = new String[] { "keso/core/OSService.*" };
	}

	public void require(int domainID, String className, String methodName) {
	}

	public boolean affectInvokeStatic(IMInvoke node, IMMethod caller, IMMethod callee,
			IMNode args[], Coder coder) throws CompileException
	{
		if (callee.termed("getActiveApplicationMode()I")) {
			coder.add("GetActiveApplicationMode()");
		} else if (callee.termed("startOS(I)V")) {
			coder.add("StartOS(");
			args[0].translate(coder);
			coder.add(")");
		} else if (callee.termed("shutdownOS()V")) {
			coder.add("ShutdownOS()");
		} else return false;

		return true;
	}

	public boolean ignoreMethodBody(IMClass clazz, IMMethod method) throws CompileException {
		return true;
	}
}
