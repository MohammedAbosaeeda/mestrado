/**(c)

  Copyright (C) 2007 Christian Wawersich 

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

public class SystemTimeWeavelet extends Weavelet {

	public SystemTimeWeavelet(BuilderOptions opts, ClassStore repository) {
		super(opts, repository);
		joinPoints = new String[] { "java/lang/System.currentTimeMillis()J" };
	}

	public void require(int domainID, String className, String methodName) {

	}

	public boolean affectMethod(IMClass clazz, IMMethod method, Coder coder) throws CompileException {
		if (opts.isTarget(BuilderOptions.LINUX_JOSEK_TARGET)) {
			coder.local_add("#include <time.h>\n");
			coder.addln("return (jlong)time(0);");
			return true;
		}
		return false;	
	}

	public boolean ignoreMethodBody(IMClass clazz, IMMethod method) throws CompileException {
		return true;
	}
}
