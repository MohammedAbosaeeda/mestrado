/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.compiler.config;

import keso.compiler.*;
import keso.compiler.config.parser.ParseException;

import java.util.Vector;

public final class NativeTask extends TaskDefinition {

	private boolean autoStart=false;
	private int priority=0;
	private boolean preemptable=false;
	private boolean basictask=true;
	private boolean callsched=false;
	private int activation=1;
	private int stacksize=100;
	private String event=null;

	public NativeTask(SystemDefinition sys, String name) {
		super(sys, name, -1);
	}

	public void setAutoStart(boolean aS) { autoStart = aS; }
	public void setPriority(int prio) { priority = prio; }
	public void setStackSize(int size) { stacksize = size; }
	public void setSchedule(boolean preempt) { preemptable = preempt; }
	public void addEvent(String ev) {
		event = ev;
	}

	public void toOIL(StringBuffer oil, String prefix) throws CompileException {
		oil.append(prefix); oil.append("TASK "); oil.append(ident); oil.append(" {\n");
		oil.append(prefix); oil.append("\tAUTOSTART = ");
		if(autoStart) {
			oil.append("TRUE {\n");
			oil.append(prefix); oil.append("\t\tAPPMODE = OSDEFAULTAPPMODE;\n");
			oil.append(prefix); oil.append("\t};\n");
		} else {
			oil.append("FALSE;\n");
		}

		if (event!=null) {
			oil.append(prefix); oil.append("\tEVENT = "); oil.append(event); oil.append(";");
		}

		SystemDefinition sysdef = (SystemDefinition) parent;

		for(int i=0; i<usedResources.size(); i++) {
			Attribut a = (Attribut) usedResources.elementAt(i);
			ResourceDefinition res = sysdef.getSysResource(a.valueString());
			if (res == null)
				throw new CompileException("Task " + getIdentifier() + " references unknown Resource " + a.valueString());

			multiRefToOIL(oil, prefix+"\t", "RESOURCE", res.getIdentifier());
		}

		oil.append(prefix); oil.append("\tPRIORITY = "); oil.append(priority); oil.append(";\n");
		oil.append(prefix); oil.append("\tSCHEDULE = "); oil.append((preemptable?"FULL;\n":"NONE;\n"));
		oil.append(prefix); oil.append("\tTYPE = "); oil.append((basictask?"BASIC;\n":"EXTENDED;\n"));
		oil.append(prefix); oil.append("\tSTACKSIZE = "); oil.append(stacksize); oil.append(";\n");
		oil.append(prefix); oil.append("\tCALLSCHEDULER = "); oil.append((callsched?"YES;\n":"NO;\n"));
		oil.append(prefix); oil.append("\tACTIVATION = "); oil.append(activation); oil.append(";\n");
		oil.append(prefix); oil.append("};\n\n");

	}

	public String getIdentifier() { return ident; }
}
