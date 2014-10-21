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

public class OSDefinition extends Set {
	private ComplexBoolAttribute[] hooks; 
	
	/**
	 * Contains descriptons of the Hooks in the hooks array.
	 * The contained String is the name of the associated C-function,
	 * the uppercased String is the name of the OIL setting.
	 */
	private static final String[] hookdescs = {"StartupHook", "ErrorHook",
		"ShutdownHook","PreTaskHook","PostTaskHook",
		"PreIsrHook","PostIsrHook"};

	/**
	 * Contains informations about the hooks might get.
	 * I know this looks pervasive, so here a short description.
	 * On the first level of the array, there is an entry for
	 * each hook (corresponding to hookdescs[]).
	 * If a hook does not get parameters, this is simply the
	 * null reference.
	 * For hooks that do get Parameters, the second level of
	 * the array holds arrays with the paramter descriptions.
	 * The parameter descriptions are a two element array on the
	 * third level that contain the name of the parameters type
	 * at index 0 and the freely choseable name of the parameter
	 * at index 1.
	 *
	 * TODO
	 * as with the TaskIDs, ProOSEk assigns a unique number to
	 * each ISR routine of type IsrType. This number is passed
	 * to the ISR Hooks as parameter isrName. We need to make
	 * some mechanism to make the OSEk numbers available in
	 * Java
	 */
	private static final String[][][] hookparams = { null,
		{ {"StatusType", "status"} }, { {"StatusType", "status"} },
		null, null, { {"IsrType", "isrName"} }, { {"IsrType", "isrName"} } };

	public OSDefinition(SystemDefinition system, String name, int line) {
		super(system, name, line);
		hooks = new ComplexBoolAttribute[hookdescs.length];
	}

	public String toString() { return "os_"+ident; }

	public void setComplex(ComplexAttribute cattr) throws ParseException {
		if(cattr instanceof ComplexBoolAttribute) {
			for(int i=0; i<hookdescs.length;i++) {
				if (0==cattr.ident.compareToIgnoreCase(hookdescs[i])) {
					if (hooks[i]==null) hooks[i]=(ComplexBoolAttribute) cattr;
					else throw new ParseException("Multiple definition of hook routine "+ cattr.ident);
					return;
				}
			}
		}
		throw new ParseException("OSDefinition does not support the complex attribute: "+ cattr.ident);
	}
	
	public ComplexBoolAttribute getHook(String hookdesc) throws CompileException { 
		for(int i=0; i<hookdescs.length;i++) {
			if (0==hookdesc.compareToIgnoreCase(hookdescs[i])) {
				return hooks[i];
			}
		}
		throw new CompileException("OSDefinition does not know any hook called "+ hookdesc);
	}

	public Object[] getAllHooks() {
		Object[] allHooks = { hooks, hookdescs, hookparams };
		return allHooks;
	}
	
	public void addAttribute(String ident, Attribut value) {
		super.addAttribute(ident, value);
	}
	
	/**
	 * Return the information from this OS Object as an OS block in OIL
	 * format.
	 *
	 * Known and supported attributes are
	 * OIL standard:
	 *   STATUS			(STANDARD, EXTENDED)
	 *   HOOK Routines		(boolean)
	 *     STARTUPHOOK
	 *     ERRORHOOK
	 *      USEGETSERVICEID
	 *      USEPARAMETERACCESS
	 *     SHUTDOWNHOOK
	 *     PRETASKHOOK
	 *     POSTTASKHOOK
	 *   USERESSCHEDULER		(boolean)
	 *
	 * ProOSEK extensions:
	 *   CC
	 *   SCHEDULE
	 *   MICROCONTROLLER
	 *   USERMAIN
	 *   STACKCHECK
	 *   TRACEBUFFER
	 *   TRICORE_RT_CLOCK
	 *   TRICORE_NUM_CSA
	 *   EXTRA_RUNTIME_CHECKS
	 *   SERVICETRACE
	 *   USELASTERROR
	 *   PREISRHOOK
	 *   POSTISRHOOK
	 */
	public void toOIL(StringBuffer oil, String prefix) throws CompileException {
		String[] supportedAttributes = { "STATUS", "USEGETSERVICEID", "USEPARAMETERACCESS", "USERESSCHEDULER",
		  // ProOSEK addition
		  "CC", "SCHEDULE", "MICROCONTROLLER", "USERMAIN", "STACKCHECK", "TRACEBUFFER", "TRICORE_RT_CLOCK",
		  "TRICORE_NUM_CSA", "EXTRA_RUNTIME_CHECKS", "SERVICETRACE", "USELASTERROR"
		};
		
		oil.append(prefix + "OS " + ident + " {\n");
		
		// standard attribute
		super.toOIL(oil, supportedAttributes, prefix+"\t");	
		// hook
		// Startuphook is always enabled, since we use it to
		// call the task constructor
		oil.append(prefix+"\t"+"STARTUPHOOK = TRUE;\n");

		// same with PreTaskHook, used to init task and domain id
		oil.append(prefix+"\t"+"PRETASKHOOK = TRUE;\n");
		
		for(int i=1;i<hooks.length;i++) {
			// Skip specially handled hooks here
			if(hookdescs[i].compareTo("PreTaskHook")==0) continue;
			
			oil.append(prefix+"\t"+hookdescs[i].toUpperCase()+" = "+
						((hooks[i]!=null&&hooks[i].setting)?"TRUE":"FALSE")+
						";\n");
		}
		
		oil.append(prefix);
		oil.append("};\n\n");
	}

	protected void finalizeCfg(FinalizeResult res) throws ParseException {
		// make Hook methods required
		for(int i=0; i<hooks.length; i++) {
			if(hooks[i]==null || !hooks[i].setting) continue;
			
			res.requireMethod(hooks[i].getAttribute("HookClass").valueString(),
					hooks[i].getAttribute("HookMethod").valueString());
		}
	}
}
