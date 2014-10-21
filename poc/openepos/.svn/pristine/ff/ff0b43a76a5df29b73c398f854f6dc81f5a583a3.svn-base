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

public class AlarmDefinition extends Set {
	/* used as index into keso_alarm_index to
	 * reference the entry associated to an invalid
	 * domain lookup (null)
	 *
	 * Stable after config file has been parsed completly.
	 */
	public static byte INVALID_ALARM=0;
	
	private AlarmActionAttribute action;
	private ComplexBoolAttribute autoStart;

	public AlarmDefinition(Set dom, String name, int line) {
		super(dom, name, line);
		INVALID_ALARM++;
	}
	
	public String toString() {
		if(parent instanceof DomainDefinition)
			return ident + " (domain: " + parent + ")";
		return ident;
	}

	public ComplexBoolAttribute getAutoStart() {
		return this.autoStart;
	}
	
	public void setComplex(ComplexAttribute cattr) throws ParseException {
		if((cattr instanceof ComplexBoolAttribute) && (cattr.ident.compareToIgnoreCase("Autostart")==0)) {
			if (autoStart!=null) throw new ParseException("Multiple definition of Autostart in Alarm " + getIdentifier());
			autoStart = (ComplexBoolAttribute) cattr;
		} else if (cattr instanceof AlarmActionAttribute && cattr.ident.compareToIgnoreCase("Action")==0) {
			if (action!=null) throw new ParseException("Multiple definition of Action in Alarm " + getIdentifier());
			action = (AlarmActionAttribute) cattr;
		} else throw new ParseException("Unknown ComplexAttribute " + cattr.ident + " in Alarm " + getIdentifier());
	}
	
	/**
	 * Generate a ALARM block in OIL syntax out of this ALARM Definition.
	 *
	 * Known and supported attributes
	 * OIL standard:
	 *   COUNTER    (Reference to a defined counter)
	 *   AUTOSTART  (ComplexBoolAttribute)
	 *     - ALARMTIME (time when alarm shall expire first)
	 *     - CYCLETIME (cycletime of a cyclic alarm)
	 *     - APPMODE   (Multiref to Appmodes where autostart applies)
	 *   ACTION     (ComplexAttribute{ALARMCALLBACK, SETEVENT, ACTIVATETASK})
	 *     - TASK   (Reference to a task)
	 *     - EVENT  (Reference to an event)
	 *     - ALARMCALLBACKCLASS (class with callback method)
	 *     - ALARMCALLBACKMETHOD (name+signature of callback method)
	 */
	public void toOIL(StringBuffer oil, String prefix) throws CompileException {
		String[] sAttrAutoStart = {"ALARMTIME", "CYCLETIME", "APPMODE"};

		Attribut counter = getAttribute("UseCounter");
		if (counter == null)
			throw new CompileException("Alarm "+getIdentifier()+" does not have an associated counter.");

		SystemDefinition  sdef;
		CounterDefinition cdef;

		if (parent instanceof DomainDefinition)
			sdef = (SystemDefinition) parent.parent;
		else sdef = (SystemDefinition) parent;

		cdef = sdef.getCounter(counter.valueString());
		if (cdef == null)
			throw new CompileException("Alarm "+getIdentifier()+"'s associated counter "+counter.valueString()+" is now known to the system.");
		
		oil.append(prefix+"ALARM " + getIdentifier() + " {\n");
		oil.append(prefix+"\t");
		oil.append("COUNTER = ");
		oil.append(cdef.getIdentifier());
		oil.append(";\n");
		
		// Autostart
		autoStart.toOIL(oil, true, sAttrAutoStart, prefix+"\t"); 
		
		// Action
		action.toOIL(oil, prefix+"\t");
				
		oil.append(prefix+"};\n\n");
	}

	public ComplexAttribute getAction() { return action; }

	protected String typeID() { return "a_"; }

	protected void finalizeCfg(FinalizeResult res) throws ParseException {
		res.requireMethod("keso/core/Alarm", null);

		// if callback action require callback method, too
		if(action.value.compareToIgnoreCase("ALARMCALLBACK")==0) {
			Attribut classAttr = action.getAttribute("AlarmCallbackClass");
			Attribut methodAttr = action.getAttribute("AlarmCallbackMethod");
			
			if(methodAttr==null || classAttr==null)
				throw new ParseException("Alarm " + getIdentifier() + " needs AlarmCallbackClass and AlarmCallbackMethod specified");

			res.requireMethod(classAttr.valueString(), methodAttr.valueString());
		}
	}
}
