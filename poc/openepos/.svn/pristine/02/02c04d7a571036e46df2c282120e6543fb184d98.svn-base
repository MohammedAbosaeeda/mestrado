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


public class Task extends Weavelet {

	public Task(BuilderOptions opts, ClassStore repository) {
		super(opts, repository);
		joinPoints = new String[] { "keso/core/Task.*","keso/core/Task.<CLASS>" };
	}

	public boolean addFields(IMClass clazz, Coder coder, StringBuffer raw_fields) throws CompileException {
		raw_fields.append("\tjbyte _domain_id;\n");
		raw_fields.append("\tjbyte _e_domain_id;\n");
		raw_fields.append("\tjbyte _task_id;\n");
		return true;
	}

	public boolean affectInvokeSpecial(IMInvoke node, IMMethod caller,
			IMMethod callee, IMNode obj, IMNode args[], Coder coder)
		throws CompileException {
		if (opts.isSingleTaskSystem()) return false;
		if (callee.termed("<init>()V")) {
			IMClass taskClass = repository.getClass("keso/core/Task");
			coder.add_class(taskClass);

			coder.chk_ref(obj, caller, 0);
			coder.add_getField(taskClass, obj, "task_id");
			coder.add(" = INVALID_TASK");
			return true;
		}
		return false;
	}

	public boolean ignoreMethodBody(IMClass clazz, IMMethod method)
		throws CompileException {
		if (opts.isSingleTaskSystem()) return false;
		return false;
	}
}
