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
import keso.util.Debug;

import java.util.Vector;
import java.util.Iterator;

public class SystemDefinition extends Set {
	private static int SYSTEM_ID = 0;
	private final int systemID;

	private PublicDomain pdomain;
	private Vector domains;

	// system tasks and resources
	// (garbage collector, etc., not available through name service)
	private Vector sysTasks;
	private Vector sysResources;

	private Vector appmodes;
	private Vector counters;
	private Vector events;
	private Vector finalizeCallback;

	private OSDefinition os;
	private ComplexAttribute stack;

	FinalizeResult finalizeResult;
	
	public SystemDefinition(Set parent, String name, int line) throws ParseException {
		super(parent, name, line);
		systemID = SYSTEM_ID++;
		domains = new Vector();
		appmodes = new Vector();
		events = new Vector();
		counters = new Vector();
		sysTasks = new Vector();
		sysResources = new Vector();
		finalizeCallback = new Vector();
	}

	public int getSystemID() {
		return systemID;
	}

    public boolean hasLittleEndianMemory() {
        return BuilderOptions.hasLittleEndianMemory(getTarget());
    }

	public int getTarget() {
		Attribut attr = getAttribute("Target");
		if (attr == null) {
			return BuilderOptions.DEFAULT_TARGET;
		}
		return BuilderOptions.getTargetByName(attr.valueString());
	} 
   
	public String getProcessorType() {
		Attribut main = getAttribute("ProcessorType");
		if (main==null) return null;
		return main.valueString();
	}

    
    
	public void addDomain(DomainDefinition dom) {
		BuilderOptions.getOpts().verbose("domain: "+dom);
		domains.addElement(dom);
	}

	private boolean multpdoms=false;
	public void addPDomain(PublicDomain dom) throws ParseException {
		if(multpdoms)
			throw new ParseException("Multiple public domains defined!");
		multpdoms=true;
		addDomain(dom);
		pdomain = dom;
	}

	public PublicDomain getPDomain() {
		return pdomain;
	}

	public Vector getDomains() {
		return domains;
	}

	public DomainDefinition getDomain(String dName) {
		return (DomainDefinition) findAttributeInVector(domains, dName);
	}

	public void addAppmode(AppmodeDefinition amode) {
		BuilderOptions.getOpts().verbose("Appmode: "+amode);
		appmodes.add(amode);
	}

	public Vector getAppmodes() { return appmodes; }

	public AppmodeDefinition getAppmode(String amName) {
		return (AppmodeDefinition) findAttributeInVector(appmodes, amName);
	}

	public void addCounter(CounterDefinition cdef) {
		BuilderOptions.getOpts().verbose("counter: "+cdef);
		counters.add(cdef);
	}

	public Vector getCounters() {	return counters; }
	
	public CounterDefinition getCounter(String cName) {
		return (CounterDefinition) findAttributeInVector(counters, cName);
	}

	public void addEvent(EventDefinition ev) {
		BuilderOptions.getOpts().verbose("Event: "+ev);
		events.add(ev);
	}

	public Vector getEvents() { return events; }

	public EventDefinition getEvent(String evName) {
		return (EventDefinition) findAttributeInVector(events, evName);
	}

	public void setOS(OSDefinition os) throws ParseException {
		if(this.os != null)
			throw new ParseException("Duplicate definition of OsekOS encountered (kesorc:)"+os.getLine()+")");
		this.os = os;
	}

	public void registerFinalize(Set set) {
		finalizeCallback.add(set);
	}

	public void finalizeCfg() throws keso.compiler.config.parser.ParseException {
		finalizeResult = new FinalizeResult();

		if (os!=null) {
			os.finalizeCfg(finalizeResult);
		}
		finalizeVector(appmodes, finalizeResult);

		//finalizeVector(domains, finalizeResult);
		boolean gcevent = false;
		for(int i=0; i<domains.size(); i++) {
			DomainDefinition dom = (DomainDefinition)domains.elementAt(i);
			if (dom.needGCRunEvent()) {
				if (!gcevent) {
					events.add(new EventDefinition(this, "KESO_GCRun"));
					gcevent=true;
				}

			}
			dom.finalizeCfg(finalizeResult);
		}

		finalizeVector(counters, finalizeResult);
		finalizeVector(finalizeCallback, finalizeResult);


		// all tasks need to have finalized before finalizing Events
		finalizeVector(events, finalizeResult);

		// mark stack overflow handler as required if present
		if(stack!=null && stack.getAttribute("StackChecks").valueString().compareToIgnoreCase("true")==0) {
			try {
				finalizeResult.requireMethod(stack.getAttribute("HandlerClass").valueString(),
						stack.getAttribute("HandlerMethod").valueString());
			} catch (NullPointerException e) {
				throw new ParseException("Stackcheck needs HandlerClass and HandlerMethod specified");
			}
		}
	}

	public Iterator getRequiredMethods() {
		return finalizeResult.requiredMethods.iterator();
	}

	public OSDefinition getOSDef() { return os; }

	/**
	 * Generates an OIL file out of the SystemDefinition.
	 * The information of the subobjects will be included,
	 * therefore the returned String contains a whole OIL file.
	 */
	public void toOIL(StringBuffer oil, String prefix) throws CompileException {
		oil.append(prefix+"\nCPU " + ident + " {\n");

		// OS block
		if (os == null) throw new CompileException("OsekOS Block not defined in kesorc");
		os.toOIL(oil, prefix+"\t");

		// Domains
		if (domains.size()==0) throw new CompileException("At least one domain must be defined in kesorc");
		// ISRs belong to domains, so they are also handled here
		for (int i=0; i<domains.size(); i++) 
			((DomainDefinition) domains.elementAt(i)).toOIL(oil, prefix+"\t");

		// appmodes
		for (int i=0; i<appmodes.size(); i++)
			((AppmodeDefinition) appmodes.elementAt(i)).toOIL(oil, prefix+"\t");

		// events
		for(int i=0; i<events.size(); i++)
			((EventDefinition) events.elementAt(i)).toOIL(oil, prefix+"\t");

		// counters
		for(int i=0; i<counters.size(); i++)
			((CounterDefinition) counters.elementAt(i)).toOIL(oil, prefix+"\t");

		// System Tasks
		for(int i=0; i<sysTasks.size(); i++)
			 ((TaskDefinition) sysTasks.elementAt(i)).toOIL(oil, prefix+"\t");

		// System Resources
		for(int i=0; i<sysResources.size(); i++)
			((ResourceDefinition) sysResources.elementAt(i)).toOIL(oil, prefix+"\t");

		//oil.append("\tEVENT KESO_GCRun { MASK = 0x1; };\n");

		oil.append(prefix+"};\n");
	}

	/**
	 * Add a system Task.
	 */
	public void addSysTask(NativeTask t) {
		sysTasks.add(t);
	}

	/**
	 * Get a system Task.
	 */
	public NativeTask getSysTask(String id) {
		return (NativeTask) findAttributeInVector(sysTasks, id);
	}

	/**
	 * Add a system Resource.
	 */
	public void addSysResource(NativeResource r) {
		sysResources.add(r);
	}

	/**
	 * Get a system Resource.
	 */
	public NativeResource getSysResource(String id) {
		return (NativeResource) findAttributeInVector(sysResources, id);
	}

	public void setComplex(ComplexAttribute cattr) throws ParseException {
		if(cattr.ident.compareToIgnoreCase("Stack")!=0)
			throw new ParseException("System only supports the complex attribute \"Stack\"");
		stack = cattr;
	}
	public ComplexAttribute getStack() { return stack; }
}
