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

/**
 * Differs from ComplexAttribute in that it handles some attributes
 * of the complex Alarm-Action attribute specially when outputting OIL.
 */
public class AlarmActionAttribute extends ComplexAttribute {
	public AlarmActionAttribute(Set parent, String name, String setting, int line) throws ParseException {
		super(parent, name, setting, line);
	}

	/**
	 * Return an OIL representation of this AlarmAction attribute.
	 */
	public void toOIL(StringBuffer oil, String prefix) throws CompileException {
		oil.append(prefix);
		oil.append(ident.toUpperCase());
		oil.append(" = ");
		oil.append(value.toUpperCase());
		
		// Dump the subattributes
		oil.append(" {\n");

		if (value.compareToIgnoreCase("ALARMCALLBACK")==0) {
			// need Alarmcallbackname
			oil.append(prefix+"\tALARMCALLBACKNAME = ");
			oil.append(parent.getIdentifier());
			oil.append("_callback;\n");
		} else if (parent.parent instanceof SystemDefinition)
			throw new CompileException("Global alarm "+parent.getIdentifier()+" may only specify a callback function");
		
		if (value.compareToIgnoreCase("ACTIVATETASK")==0 || 
				value.compareToIgnoreCase("SETEVENT")==0) {
			// need TASK attribute
			Attribut task = getAttribute("UseTask");
			if(task == null) throw new CompileException("Alarm "+parent.getIdentifier()+"'s action needs the UseTask attribute specified.");
			TaskDefinition tdef = ((DomainDefinition) parent.parent).getTask(task.valueString());
			if(tdef == null)
				throw new CompileException("Alarm "+getIdentifier()+"'s UseTask specifies a Task not known in domain "+
						parent.parent.getIdentifier());
			oil.append(prefix);
			oil.append("\tTASK = ");
			oil.append(tdef.getIdentifier());
			oil.append(";\n");
		}

		if (value.compareToIgnoreCase("SETEVENT")==0) {
			// need EVENT attribute
			Attribut ev = getAttribute("UseEvent");
			if (ev == null) throw new CompileException("Alarm "+parent.getIdentifier()+"'s action needs the UseEvent attribute specified.");

			EventDefinition edef = ((SystemDefinition) parent.parent.parent).getEvent(ev.valueString());
			if(edef==null)
				throw new CompileException("Alarm "+getIdentifier()+"'s UseEvent specifies an unknown Event " + ev.valueString());
			oil.append(prefix);
			oil.append("\tEVENT = ");
			oil.append(edef.getIdentifier());
			oil.append(";\n");
		}
		
		oil.append(prefix);
		oil.append("};\n");
	}
}
