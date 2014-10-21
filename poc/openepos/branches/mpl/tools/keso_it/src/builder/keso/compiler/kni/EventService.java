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
import keso.compiler.config.DomainDefinition;
import keso.compiler.config.TaskDefinition;

import java.util.Hashtable;
import java.util.Vector;

public class EventService extends Weavelet {

	private Coder coder;

	public EventService(BuilderOptions opts, ClassStore repository) {
		super(opts, repository);
		joinPoints = new String[] { "keso/core/EventService.*" };
	}

	public void require(int domainID, String className, String methodName) {
		repository.requireClass(domainID,"keso/core/Events");
	}

	public int checkAttribut(IMMethod self, int attr) {
		if (attr==Weavelet.ATTR_BLOCK) {
			if (self.termed("waitEvent(I)I")) return TRUE;
		}
		return FALSE;
	}

	public boolean affectMethod(IMClass clazz, IMMethod method, Coder coder) throws CompileException
	{
		this.coder = coder;
		
		if (method.termed("setEvent(Lkeso/core/Task;I)I")) {
			getsetEvent("SetEvent", false);
			return true;
		} else if (method.termed("getEvent(Lkeso/core/Task;)I")) {
			getsetEvent("GetEvent", true);
			return true;
		} else if (method.termed("waitEvent(I)I") && opts.getGlobalHeap().deferTasksOnDemand()) {
			coder.addln("StatusType s;");
			coder.addln("s = WaitEvent(i1);");
			coder.addln("KESO_GC_DEFER_TASK();");
			coder.addln("return s;");
			return true;
		}

		return false;
	}
	
	public boolean affectInvokeStatic(IMInvoke node, IMMethod caller, IMMethod callee,
			IMNode args[], Coder coder) throws CompileException
	{
		if (callee.termed("clearEvent(I)I")) {
			coder.add("ClearEvent(");
			args[0].translate(coder);
			coder.add(")");
		} else if (callee.termed("waitEvent(I)I")) {
			caller.getMethodFrame().codeEOLL(coder);
			coder.add("WaitEvent(");
			args[0].translate(coder);
			coder.add(")");
		} else return false;
		
		// return value true tells the builder that this invoke has been handled
		return true;
	}

	private void getsetEvent(String fnname, boolean get) throws CompileException {
		IMClass task_class = repository.getClass("keso/core/Task");
		coder.add_class(task_class);

		if(get) coder.addln("EventMaskType ev;\n");
		else coder.add("return ");

		coder.add(fnname);
		coder.add("(");
		coder.add_getField(task_class, "obj0", "task_id");
		coder.add(", ");
		
		if(get) coder.add("&ev");
		else coder.add("i1");
		
		coder.addln(");");
		
		if(get) coder.addln("return ev;");
	}

	public boolean ignoreMethodBody(IMClass clazz, IMMethod method) throws CompileException {
		return true;
	}
}
