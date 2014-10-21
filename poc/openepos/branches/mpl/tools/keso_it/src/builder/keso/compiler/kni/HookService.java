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

public class HookService extends Weavelet {

	public HookService(BuilderOptions opts, ClassStore repository) {
		super(opts, repository);
		joinPoints = new String[] { "keso/core/HookService.*" };
	}

	// modifies a call to a static method
	public boolean affectInvokeStatic(IMInvoke node, IMMethod caller, IMMethod callee,
			IMNode args[], Coder coder) throws CompileException {
		boolean objvariant=false;
		
		if (callee.termed("OSErrorGetServiceId()I")) {
			coder.add("OSErrorGetServiceId()");
			return true;
		} else if (callee.termed("OSError_int(Ljava/lang/String;Ljava/lang/String;)I")) {
			// fall through
		} else if (callee.termed("OSError_obj(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;")) {
			objvariant=true;
		} else return false;
		
		if (!(args[0] instanceof IMAConstant) || !(args[1] instanceof IMAConstant))
				throw new CompileException("OSError: arguments must be constant!");
		IMAConstant arg_service = (IMAConstant)args[0];
		IMAConstant arg_param = (IMAConstant)args[1];

		OSError(arg_service.getString(), arg_param.getString(), coder, objvariant);
		return true;
	}

	private void OSError(String service, String param, Coder coder, boolean isObj) throws CompileException {
		String oS, oP, resType=null;
		boolean isInt=true;
		if (service.compareTo("activate")==0 && param.compareToIgnoreCase("taskID")==0) {
			oS = "ActivateTask"; oP="TaskID"; isInt=false; resType="task";
		} else if (service.compareTo("cancelAlarm")==0 && param.compareToIgnoreCase("alarmID")==0) {
			oS = "CancelAlarm"; oP="AlarmID"; isInt=false; resType="alarm";
		} else if (service.compareTo("chain")==0 && param.compareToIgnoreCase("taskID")==0) {
			oS = "ChainTask"; oP="TaskID"; isInt=false; resType="task";
		} else if (service.compareTo("clearEvent")==0 && param.compareToIgnoreCase("evMask")==0) {
			oS = "ClearEvent"; oP="Mask";
		} else if (service.compareTo("getAlarmBase")==0 && param.compareToIgnoreCase("alarmID")==0) {
			oS = "GetAlarmBase"; oP="AlarmID"; isInt=false; resType="alarm";
		} else if (service.compareTo("getAlarmBase")==0 && param.compareToIgnoreCase("info")==0) {
			// we don't have the user supplied AlarmBase object anywhere...
			throw new CompileException("OSError is not supported for getAlarmBase info parameter");
		} else if (service.compareTo("getAlarm")==0 && param.compareToIgnoreCase("alarmID")==0) {
			oS = "GetAlarm"; oP="AlarmID"; isInt=false; resType="alarm";
		} else if (service.compareTo("getAlarm")==0 && param.compareToIgnoreCase("tick")==0) {
			throw new CompileException("OSError: the tick parameter to getAlarm does not exist in the KESO interface");
		} else if (service.compareTo("getEvent")==0 && param.compareToIgnoreCase("event")==0) {
			throw new CompileException("OSError: the event parameter to getEvent does not exist in the KESO interface");
		} else if (service.compareTo("getEvent")==0 && param.compareToIgnoreCase("taskID")==0) {
			oS = "GetEvent"; oP = "TaskID"; isInt=false; resType="task";
		} else if (service.compareTo("getResource")==0 && param.compareToIgnoreCase("resource")==0) {
			oS = "GetResource"; oP = "ResID"; isInt=false; resType="resource";
		} else if (service.compareTo("getTaskID")==0 && param.compareToIgnoreCase("taskID")==0) {
			throw new CompileException("OSError: the taskID parameter to getTaskID does not exist in the KESO interface");
		} else if (service.compareTo("getTaskState")==0 && param.compareToIgnoreCase("state")==0) {
			throw new CompileException("OSError: the state parameter to getTaskState does not exist in the KESO interface");
		} else if (service.compareTo("getTaskState")==0 && param.compareToIgnoreCase("taskID")==0) {
			oS = "GetTaskState"; oP="TaskID"; isInt=false; resType="task";
		} else if (service.compareTo("releaseResource")==0 && param.compareToIgnoreCase("resource")==0) {
			oS = "ReleaseResource"; oP="ResID"; isInt=false; resType="resource";
		} else if (service.compareTo("setAbsAlarm")==0 && param.compareToIgnoreCase("alarmID")==0) {
			oS="SetAbsAlarm"; oP="AlarmID"; isInt=false; resType="alarm";
		} else if (service.compareTo("setAbsAlarm")==0 && param.compareToIgnoreCase("cycle")==0) {
			oS="SetAbsAlarm"; oP="cycle";
		} else if (service.compareTo("setAbsAlarm")==0 && param.compareToIgnoreCase("start")==0) {
			oS="SetAbsAlarm"; oP="start";
		} else if (service.compareTo("setEvent")==0 && param.compareToIgnoreCase("taskID")==0) {
			oS = "SetEvent"; oP="TaskID"; isInt=false; resType="task";
		} else if (service.compareTo("setEvent")==0 && param.compareToIgnoreCase("evMask")==0) {
			oS = "SetEvent"; oP="Mask";
		} else if (service.compareTo("setRelAlarm")==0 && param.compareToIgnoreCase("alarmID")==0) {
			oS="SetRelAlarm"; oP="AlarmID"; isInt=false; resType="alarm";
		} else if (service.compareTo("setRelAlarm")==0 && param.compareToIgnoreCase("cycle")==0) {
			oS="SetRelAlarm"; oP="cycle";
		} else if (service.compareTo("setRelAlarm")==0 && param.compareToIgnoreCase("increment")==0) {
			oS="SetRelAlarm"; oP="increment";
		} else if (service.compareTo("waitEvent")==0 && param.compareToIgnoreCase("evMask")==0) {
			oS="WaitEvent"; oP="Mask";
		} else throw new CompileException("Unsupported OSError call OSError_"+service+"_"+param);

		// This should already get caught by the java compiler but will help to
		// detect bugs in the above which was written with my brain turning
		// continously down :-)
		if(isInt == isObj) throw new CompileException("OSError_"+service+"_"+param+" not handled correctly in weavelet");

		if(isObj) {
			coder.add("keso_");
			coder.add(resType);
			coder.add("_index[");
		}
		OSEKOSError(oS, oP, coder);
		if(isObj) {
			coder.add("]");
		}
	}

	private void OSEKOSError(String service, String param, Coder coder) throws CompileException {
		coder.add("OSError_");
		coder.add(service);
		coder.add("_");
		coder.add(param);
		coder.add("()");
	}

	public boolean ignoreMethodBody(IMClass clazz, IMMethod method) throws CompileException {
		return true;
	}

}
