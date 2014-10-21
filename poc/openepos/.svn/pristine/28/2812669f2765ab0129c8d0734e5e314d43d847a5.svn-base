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

public class SystemServiceWeavelet extends Weavelet {

	public SystemServiceWeavelet(BuilderOptions opts, ClassStore repository) {
		super(opts, repository);
		joinPoints = new String[] { "keso/core/SystemService.*" };
	}

	/**
	 *
	 * NOTE:
	 *     opts.getTarget() is not valid in this method!
	 */
	public boolean ignoreMethodBody(IMClass clazz, IMMethod method) throws CompileException {
		opts.vverbose("## inline "+method.getMethodName()+" ignore");
		if (method.termed("getSystemIdentifier")) return true; 	
		if (method.termed("getIntegerConstant")) return true; 
        return false;
	}

	public IMNode affectIMInvoke(IMInvoke self, IMMethod method, IMMethod callee,
			IMNode obj, IMNode args[]) throws CompileException {

		if (opts.hasOption("no_im_weavelet")) return self;

		if (callee.termed("getSystemIdentifier")) {
			return method.createIMIConstant(opts.getCurrentSystemID(), self.getBCPosition());
		}

		return self;
	}

	public boolean affectInvokeStatic(IMInvoke node, IMMethod caller, IMMethod callee,
			IMNode args[], Coder coder) throws CompileException {

		SystemDefinition sysDef = opts.getSysDef();

		if (callee.termed("getSystemIdentifier")) {
			coder.add(sysDef.getSystemID());
			return true;
		}

		if (callee.termed("getIntegerConstant")) {
			if (!args[0].isConstant()) {
				throw new CompileException("System constant name must be a constant string");
			}

			String cName = ((IMAConstant) args[0]).getString();
			Attribut attr = sysDef.getAttribute(cName);

			if (attr == null) {
				opts.critical("The system constant \"" + cName + "\" is not defined in " + sysDef);
				coder.add(0);
				return true;
			}
			coder.add(attr.valueInt());
			return true;
		}

		return false;
	}
}
