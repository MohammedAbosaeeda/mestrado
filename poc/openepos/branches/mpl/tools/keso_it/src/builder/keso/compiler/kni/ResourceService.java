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
import keso.compiler.config.ResourceDefinition;
import keso.compiler.config.DomainDefinition;

import java.util.Vector;

public class ResourceService extends KesoIndexedResource {

	public ResourceService(BuilderOptions opts, ClassStore repository) {
		super(opts, repository);
		joinPoints = new String[] { "keso/core/ResourceService.*" };
	}

	public void require(int domainID, String className, String methodName) {
		repository.requireClass(domainID, "keso/core/Resource");
	}

	public boolean affectMethod(IMClass clazz, IMMethod method, Coder coder) throws CompileException {
		this.coder = coder;
		this.clazz = clazz;
		this.callee = method;

		if (method.termed("getResource(Lkeso/core/Resource;)I")) {
			retIgetResRef("GetResource", "calls OSEK GetResource", "keso/core/Resource", "resource_id");
		} else if (method.termed("releaseResource(Lkeso/core/Resource;)I")) {
			retIgetResRef("ReleaseResource", "calls OSEK ReleaseResource", "keso/core/Resource", "resource_id");
		} else return false;

		return true;
	}

	public boolean affectInvokeStatic(IMInvoke node, IMMethod caller, IMMethod callee,
			IMNode args[], Coder coder) throws CompileException {
		this.node   = node;
		this.caller = caller;
		this.callee = callee;
		this.args   = args;
		this.coder  = coder;

		if(callee.termed("getResourceByName(Ljava/lang/String;)Lkeso/core/Resource;")) {
			getResourceByName();
		} else if(callee.termed("getScheduler()I")) {
			coder.add("GetResource(RES_SCHEDULER)");
		} else if(callee.termed("releaseScheduler()I")) {
			coder.add("ReleaseResource(RES_SCHEDULER)");
		} else return false;

		return true;
	}
	
	private void getResourceByName() throws CompileException {
		// generate an array of Vectors with resources of each domain
		Vector domains = opts.getSysDef().getDomains();
		Vector[] localResources = new Vector[domains.size()];
		
		for(int i=0; i<domains.size(); i++) {
			DomainDefinition dom = (DomainDefinition) domains.elementAt(i);
			localResources[i] = dom.getResources();
		}

		if(opts.getSysDef().getPDomain()==null)
			getIndexedResourceByName(localResources, null, "resource", "INVALID_RESOURCE");
		else
			getIndexedResourceByName(localResources,
					opts.getSysDef().getPDomain().getResources(),
					"resource", "INVALID_RESOURCE");
	}

	public boolean ignoreMethodBody(IMClass clazz, IMMethod method) throws CompileException {
		return true;
	}
}
