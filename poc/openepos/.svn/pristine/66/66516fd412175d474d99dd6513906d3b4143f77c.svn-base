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

public class TaskDefinition extends Set {
	private ComplexBoolAttribute autoStart;
	protected Vector usedResources;
	protected Vector usedEvents;
	protected Vector usedMessages;

	private int evBitsUsed;
	
	public TaskDefinition(Set dom, String name, int line) {
		super(dom, name, line);
		usedResources = new Vector();
		usedEvents = new Vector();
		usedMessages = new Vector();
		evBitsUsed=0;
	}


    // TODO Maybe tasks could be used to automaticaly handle packets
    // without any user code.
    public String getServiceName() {
       	Attribut attr = getAttribute("ServiceName");
		if (attr == null) return null;
		return attr.valueString();
    }


	public ComplexBoolAttribute getAutoStart() {
		return this.autoStart;
	}
	
    public String getNetworkName() {
       	Attribut attr = getAttribute("NetworkName");
		if (attr == null) return null;
		return attr.valueString();
    }

    public boolean isServiceHandlerTask() {
        return getServiceName() != null && getNetworkName() != null;
    }

	public String getMainMethodName() {
		Attribut main = getAttribute("MainMethod");
		if (main==null) return "launch()V";
		return main.valueString();
	}

	public String getMainClassName() {
		Attribut main = getAttribute("MainClass");
		if (main==null) return "Main";
		return main.valueString();
	}

	public void setComplex(ComplexAttribute cattr) throws ParseException {
		if ( (!(cattr instanceof ComplexBoolAttribute)) ||
				(cattr.ident.compareToIgnoreCase("autostart")!=0))
			throw new ParseException("TaskDefinition does not support the ComplexAttribute + " + cattr.ident);
		autoStart = (ComplexBoolAttribute) cattr;
	}

	public void addAttribute(String ident, Attribut value) {
	  if(ident.compareToIgnoreCase("UseResource")==0) {
	    usedResources.add(value);
	  } else if(ident.compareToIgnoreCase("UseMessage")==0) {
	    usedMessages.add(value);
	  } else if(ident.compareToIgnoreCase("UseEvent")==0) {
	    usedEvents.add(value);
	  } else super.addAttribute(ident,value);
	}

	public Vector getResources()	{return usedResources;}
	public Vector getEvents()	{return usedEvents;   }
	public Vector getMessages()	{return usedMessages; }

	public void addResource(ResourceDefinition res) {
		addAttribute("UseResource", new StringAttr(this, "UseResource", res.ident,0));
	}

	public String toString() { return ident + " (domain: " + parent + ")"; }

	public int getEvBits() { return evBitsUsed; }

	// called by an Event to register its mask as used so other Events can chose
	// another one
	public void registerEvMask(int evMask) { evBitsUsed |= evMask; }

	/**
	 * Generate a TASK block in OIL syntax out of this TaskDefinition.
	 *
	 * Known and supported attribute
	 * OIL standard:
	 *   PRIORITY			(UINT32)
	 *   SCHEDULE			(NON, FULL)
	 *   ACTIVATION			(UINT32)
	 *   AUTOSTART			(boolean, if true has APPMODE subattrs)
	 *   RESOURCE			(multiple references to accessed Resources)
	 *   EVENT			(multiple references to used events)
	 *   MESSAGE			(multiple references to used messages)
	 * ProOSEK extensions:
	 *   TYPE			(BASIC, EXTENDED)
	 *   STACKSIZE			(UINT32)
	 *   CALLSCHEDULER		(boolean)
	 */
	public void toOIL(StringBuffer oil, String prefix) throws CompileException {
		String[] supportedAttributes = {
			"PRIORITY", "SCHEDULE", "ACTIVATION",
			"TYPE", "STACKSIZE", "CALLSCHEDULER"
		};
		String[] supportedAttributesAS = {"APPMODE"};

		oil.append(prefix+"TASK " + getIdentifier() + " {\n");

		// autostart attribute handled specially
		if (autoStart==null) {
			System.err.print("warning: no autostart attribute in task \"");
			System.err.print(getIdentifier());
			System.err.print("\"! start task at OSDefaultAppMode.\n");
			oil.append("\t\tAUTOSTART = TRUE {\n\t\t\tAPPMODE = OSDEFAULTAPPMODE;\n\t\t};\n");
		} else {
			autoStart.toOIL(oil, true, supportedAttributesAS, prefix+"\t");
		}

		// Resources, Events and Messages are multiple reference
		DomainDefinition dom = (DomainDefinition) parent;
		SystemDefinition sysdef = (SystemDefinition) dom.parent;

		for(int i=0; i<usedResources.size(); i++) {
			Attribut a = (Attribut) usedResources.elementAt(i);
			ResourceDefinition res = dom.getResource(a.valueString());
			if (res == null && sysdef.getPDomain()!=null)
				res = sysdef.getPDomain().getResource(a.valueString());
			if (res == null) res = sysdef.getSysResource(a.valueString());
			if (res == null)
				throw new CompileException("Task " + getIdentifier() + " references unknown Resource " + a.valueString());

			multiRefToOIL(oil, prefix+"\t", "RESOURCE", res.getIdentifier());
		}

		for(int i=0; i<usedEvents.size(); i++) {
			Attribut a = (Attribut) usedEvents.elementAt(i);
			EventDefinition ev = sysdef.getEvent(a.valueString());
			
			if(ev == null)
				throw new CompileException("Task " + getIdentifier() + " references unknown Event " + a.valueString());
			
			multiRefToOIL(oil, prefix+"\t", "EVENT", ev.getIdentifier());
		}

		// TODO message

		// emit standard attribute
		super.toOIL(oil, supportedAttributes, prefix+"\t");
		oil.append(prefix+"};\n\n");
	}

	protected void finalizeCfg(FinalizeResult res) throws keso.compiler.config.parser.ParseException {
		SystemDefinition sysdef = (SystemDefinition) parent.parent;
		
		// register at all used event
		for(int i=0; i<usedEvents.size(); i++) {
			EventDefinition ev = sysdef.getEvent( ((Attribut)usedEvents.elementAt(i)).valueString() );
			if (ev==null) throw new keso.compiler.config.parser.ParseException("Unknown Event " + 
					((Attribut)usedEvents.elementAt(i)).valueString()	+ " referenced from Task " + getIdentifier());
			ev.registerTask(this);
		}

		// mark launch method as required
		Attribut mmethod = getAttribute("MainMethod");
		if(mmethod==null) res.requireMethod(getAttribute("MainClass").valueString(), "launch()V");
		else res.requireMethod(getAttribute("MainClass").valueString(), mmethod.valueString());

		res.requireMethod(getAttribute("MainClass").valueString(), "<init>()V");
	}
	
	protected String typeID() { return "t_"; }
}
