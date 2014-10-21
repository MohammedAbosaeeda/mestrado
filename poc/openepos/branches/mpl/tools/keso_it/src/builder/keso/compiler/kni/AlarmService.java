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

public class AlarmService extends KesoIndexedResource {

	public AlarmService(BuilderOptions opts, ClassStore repository) {
		super(opts, repository);
		joinPoints = new String[] { "keso/core/AlarmService.*" };
	}

	public void require(int domainID, String className, String methodName) {
		repository.requireClass(domainID,"keso/core/Alarm");
		if (methodName.equals("getAlarmBase(Lkeso/core/Alarm;Lkeso/core/AlarmBase;)I"))
			repository.requireClass(domainID,"keso/core/AlarmBase");
	}

	public boolean affectMethod(IMClass clazz, IMMethod method, Coder coder) throws CompileException {
		this.clazz = clazz;
		this.coder = coder;
		this.callee = method;

		if (method.termed("getAlarmBase(Lkeso/core/Alarm;Lkeso/core/AlarmBase;)I")) {
			getAlarmBase();
		} else if (method.termed("getAlarm(Lkeso/core/Alarm;)I")) {
			getAlarm();
		} else if (method.termed("setRelAlarm(Lkeso/core/Alarm;II)I")) {
			retIgetAlarmRefmultInts("SetRelAlarm", 2);
		} else if (method.termed("setAbsAlarm(Lkeso/core/Alarm;II)I")) {
			retIgetAlarmRefmultInts("SetAbsAlarm", 2);
		} else if (method.termed("cancelAlarm(Lkeso/core/Alarm;)I")) {
			retIgetAlarmRefmultInts("CancelAlarm", 0);
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

		if(callee.termed("getAlarmByName(Ljava/lang/String;)Lkeso/core/Alarm;")) {
			getAlarmByName();
		} else return false;

		return true;
	}

	private void getAlarm() throws CompileException {
		IMClass alarm_class = repository.getClass("keso/core/Alarm");
		coder.add_class(alarm_class);
		
		coder.addln("TickType ticks;\n");
		coder.add("GetAlarm(");
		
		coder.add_getField(alarm_class, "obj0", "alarm_id");
		coder.addln(", &ticks);");
		coder.addln("return (jint) ticks;");
	}
	
	/**
	 * Add code to call an OSEK call fnname that gets an alarm ID as its first parameter
	 * and optionally no_params int paramters.
	 * The call may return anything as long as it is compatible with the return
	 * type of the KESO Java Service.
	 */
	private void retIgetAlarmRefmultInts(String fnname, int no_params) throws CompileException {
		IMClass alarm_class = repository.getClass("keso/core/Alarm");
		coder.add_class(alarm_class);

		coder.add("return ");
		coder.add(fnname);
		coder.add("(");
		coder.add_getField(alarm_class, "obj0", "alarm_id");
		
		for(int i=0; i<no_params;i++) {
			coder.add(", i");
			coder.add(i+1);
		}

		coder.addln(");");
	}
	
	private void getAlarmBase() throws CompileException {
		// obj0 is alarmID, obj1 is AlarmBase object
		IMClass alarm_class = repository.getClass("keso/core/Alarm");
		IMClass alarmbase_class = repository.getClass("keso/core/AlarmBase");

		coder.add_class(alarm_class);
		coder.add_class(alarmbase_class);

		coder.addln("AlarmBaseType abase;");
		coder.addln("StatusType retstatus;\n");

		coder.add("retstatus = GetAlarmBase(");
		coder.add_getField(alarm_class, "obj0", "alarm_id");
		coder.addln(", &abase);");
		
		coder.add_getField(alarmbase_class, "obj1", "maxallowedvalue");
		coder.addln(" = abase.maxallowedvalue;");
		// no need to check the reference again
		coder.add_getField_fast(alarmbase_class, "obj1", "ticksperbase");
		coder.addln(" = abase.ticksperbase;");
		coder.add_getField_fast(alarmbase_class, "obj1", "mincycle");
		coder.addln(" = abase.mincycle;");
		
		coder.addln("return retstatus;");
	}
	
	private void getAlarmByName() throws CompileException {
		Vector domains = opts.getSysDef().getDomains();
		Vector[] localResources = new Vector[domains.size()];
		
		for(int i=0; i<domains.size(); i++) {
			DomainDefinition dom = (DomainDefinition) domains.elementAt(i);
			localResources[i] = dom.getAlarms();
		}

		if(opts.getSysDef().getPDomain()==null)
			getIndexedResourceByName(localResources, null, "alarm", "INVALID_ALARM");
		else
			getIndexedResourceByName(localResources,
					opts.getSysDef().getPDomain().getAlarms(),
					"alarm", "INVALID_ALARM");
	}
}
