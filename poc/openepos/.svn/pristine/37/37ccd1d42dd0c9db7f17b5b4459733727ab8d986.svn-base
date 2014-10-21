/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.compiler.kni;

import java.util.Vector;
import keso.compiler.*;
import keso.compiler.imcode.*;
import keso.compiler.backend.*;
import keso.compiler.config.*;

import java.util.Hashtable;

public class InterruptService extends Weavelet {

	private IMInvoke node;
	private IMMethod caller;
	private IMMethod callee;
	private IMNode args[];
	private Coder coder;
	
	public InterruptService(BuilderOptions opts, ClassStore repository) {
		super(opts, repository);
		joinPoints = new String[] { "keso/core/InterruptService.*" };
	}

	public void require(int domainID, String className, String methodName) {
	}

	// modifies a method, calls to the method are not modified
	public boolean affectMethod(IMClass clazz, IMMethod method, Coder coder) throws CompileException {
		return false;
	}

	// modifies a call to a static method
	public boolean affectInvokeStatic(IMInvoke node, IMMethod caller, IMMethod callee,
			IMNode args[], Coder coder) throws CompileException {
		this.node   = node;
		this.caller = caller;
		this.callee = callee;
		this.args   = args;
		this.coder  = coder;

        if (callee.termed("disableAll()V")) {
			coder.add("DisableAllInterrupts()");
		} else if (callee.termed("enableAll()V")) {
			coder.add("EnableAllInterrupts()");
		} else if (callee.termed("suspendAll()V")) {
			coder.add("SuspendAllInterrupts()");
		} else if (callee.termed("resumeAll()V")) {
			coder.add("ResumeAllInterrupts()");
		} else if (callee.termed("suspendOS()V")) {
			coder.add("SuspendOSInterrupts()");
		} else if (callee.termed("resumeOS()V")) {
			coder.add("ResumeOSInterrupts()");
		} else if (callee.termed("getIRQLevel(Ljava/lang/String;)I")) {
			if (!(args[0] instanceof IMAConstant))
				throw new CompileException("getIRQLevel(): first argument must be constant!");

			String isrIdentifier = ((IMAConstant)args[0]).getString();
			Vector ddefs=opts.getSysDef().getDomains();
			String isrMakroName = null;

			for (int j=0; j<ddefs.size(); j++) {
				DomainDefinition ddef = (DomainDefinition) ddefs.elementAt(j);
				ISRDefinition isr = ddef.getISR(isrIdentifier);
				if (isr != null) {
						isrMakroName = "RUNPRIO_ISR_" + isr.getIdentifier();
						break;
				}
			}
			if (isrMakroName == null)
				throw new CompileException("getIRQLevel(): no ISR found for identifier '"
							   	+ isrIdentifier + "'");

			coder.add(isrMakroName);
		} else {
            return false;
        }
       
        return true;
		
    }

        

    
	public boolean ignoreMethodBody(IMClass clazz, IMMethod method) throws CompileException {
		return true;
	}

}
