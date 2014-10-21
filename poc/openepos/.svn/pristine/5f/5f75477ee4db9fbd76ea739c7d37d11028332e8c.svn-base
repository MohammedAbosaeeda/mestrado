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
import keso.compiler.imcode.IMHeap;
import java.util.Vector;

public class DomainDefinition extends Set
	implements ResourceContainer, AlarmContainer, ISRContainer {
	/* value that will be assigned to INVALID_DOMAIN.
	 * This will be used as domain entry for global
	 * resources. The value is useable after the
	 * configuration file was completly parsed.
	 */
	public static byte INVALID_DOMAIN=0;

	protected Vector tasks;
	protected Vector isrs;
	protected Vector resources;
	protected Vector alarms;
	public final byte domainid;
	protected ComplexAttribute heapdef;
	private   Vector services;
	private Vector imports; 
	
    public DomainDefinition(SystemDefinition system, String name, int line) {
		super(system, name, line);
		tasks = new Vector();
		resources = new Vector();
		alarms = new Vector();
		isrs = new Vector();
		domainid = INVALID_DOMAIN++;
        services = new Vector();
        imports = new Vector();
	}

	final public void registerService(String name, ServiceDefinition srv) {
		services.add(srv);
	}

    final public void registerImport(String name, ImportDefinition imp) {
		imports.add(imp);
	}

	final public Vector getImports() {
		return imports;
	}

	public void registerFinalize(Set set) { parent.registerFinalize(this); }

	final public Vector getServices() {
		return services;
	}

	public void addTask(TaskDefinition task) {
		BuilderOptions.getOpts().verbose("task: "+task);
		tasks.add(task);
	}

	public TaskDefinition getTask(String taskName) {
		for(int i=0; i<tasks.size(); i++) {
			TaskDefinition task = (TaskDefinition) tasks.elementAt(i);
			if(task.ident.compareTo(taskName) == 0) return task;
		}
		return null;
	}

	public void addISR(ISRDefinition isr) {
		BuilderOptions.getOpts().verbose("ISR: "+isr);
		isrs.add(isr);
	}

	public Vector getISRs() { return isrs; }

	public ISRDefinition getISR(String isrName) {
		for(int i=0; i<isrs.size(); i++) {
			ISRDefinition isr = (ISRDefinition) isrs.elementAt(i);
			if(isr.ident.compareTo(isrName) == 0) return isr;
		}
		return null;
	}

	public void addResource(ResourceDefinition res) {
		BuilderOptions.getOpts().verbose("Resource: "+res);
		resources.add(res);
	}

	public Vector getTasks() { return tasks; }
	public Vector getResources() { return resources; }

	public ResourceDefinition getResource(String resName) {
		return (ResourceDefinition) findAttributeInVector(resources, resName);
	}

	public void addAlarm(AlarmDefinition adef) {
		BuilderOptions.getOpts().verbose("global alarm: "+adef);
		alarms.add(adef);
	}

	public Vector getAlarms() {	return alarms; }

	public AlarmDefinition getAlarm(String aName) {
		return (AlarmDefinition) findAttributeInVector(alarms, aName);
	}

	public String toString() { return ident; }

	/**
	 * Return the information from this domain and its subobjects in a
	 * format suitable for inclusion within an OIL file.
	 */
	public void toOIL(StringBuffer oil, String prefix) throws CompileException {

		for(int i=0;i<tasks.size();i++)
			((TaskDefinition)tasks.elementAt(i)).toOIL(oil, prefix);

		for(int i=0;i<resources.size();i++) {
			ResourceDefinition res = (ResourceDefinition)resources.elementAt(i);
			res.toOIL(oil, prefix);
		}

		for(int i=0;i<alarms.size();i++)
			((AlarmDefinition) alarms.elementAt(i)).toOIL(oil, prefix);

		for(int i=0;i<isrs.size();i++)
			((ISRDefinition) isrs.elementAt(i)).toOIL(oil, prefix);

	}

	public boolean needGCRunEvent() {
	
		ComplexAttribute heap = getHeap();
		if (heap==null) return false;

		Attribut gcmode = ((Set)heap).getAttribute("GCTMode");
		if (gcmode==null) return false; 

		String value = gcmode.valueString().toUpperCase();
		if (value!=null && value.equals("ENFORCEONDEMAND")) return true; 
		if (value!=null && value.equals("DEFERTASKSONDEMAND")) return true; 

		return false;
	}

	protected void finalizeCfg(FinalizeResult res) throws keso.compiler.config.parser.ParseException {
		finalizeVector(tasks, res);
		finalizeVector(resources, res);
		finalizeVector(alarms, res);
		finalizeVector(isrs, res);
	}

	protected String typeID() { return "d_"; }

	public void setComplex(ComplexAttribute cattr) throws ParseException {
		if(cattr.ident.compareToIgnoreCase("Heap")!=0)
			throw new ParseException("Domain supports only the complex attribute \"heap\"");
		heapdef = cattr;
	}

	public ComplexAttribute getHeap() { return heapdef; }
}
