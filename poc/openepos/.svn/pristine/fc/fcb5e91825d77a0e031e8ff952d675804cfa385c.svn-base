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

public class ISRDefinition extends Set {
	private Vector usedMessages;
	private Vector usedResources;

	public ISRDefinition(Set parent, String name, int line) {
		super(parent, name, line);
		usedMessages = new Vector();
		usedResources = new Vector();
	}

	public String toString() { return "ISR:"+ ident; }

	public void addAttribute(String ident, Attribut value) {
	  if(ident.compareToIgnoreCase("UseResource")==0) {
	    usedResources.add(value);
	  } else if(ident.compareToIgnoreCase("UseMessage")==0) {
	    usedMessages.add(value);
	  } else super.addAttribute(ident,value);
	}

	public int getCategory() {
		Attribut cat = getAttribute("category");
		if (cat == null) return 2;
		return Integer.parseInt(cat.valueString()); 
	}

    public String getHandlerClass() {
        Attribut attr = getAttribute("HandlerClass");
		if (attr==null) return null;
		return attr.valueString();
	}
    
    public String getHandlerMethod() {
        Attribut attr = getAttribute("HandlerMethod");
		if (attr==null) return null;
		return attr.valueString();
	}

    public String getName() {
		return ident;
	}

	public Vector getUsedMessages() {
		return this.usedMessages;
	}

	public Vector getUsedResources() {
		return this.usedResources;
	}


	/**
	 * Generate a ISR block in OIL syntax out of this ISR Definition.
	 *
	 * Known and supported attributes
	 * OIL standard:
	 *   CATEGORY         (UINT32)
	 *   RESOURCE         (Reference to an accessed resource)
	 *   MESSAGE          (Reference to a message accessed by this ISR)
	 *
	 * ProOSEK extensions:
	 *   TRICORE_VECTOR   (specifies the SRR the ISR will be attached to)
	 *   TRICORE_IRQLEVEL (UINT32 greater 0)
	 *   TRICORE_NUM_CSA  (UINT32)
	 *   STACKSIZE        (UINT32)
	 */
	public void toOIL(StringBuffer oil, String prefix) throws CompileException {
		String[] supportedAttributes = { "CATEGORY", "TRICORE_VECTOR", "SIGNAL",
			"TRICORE_IRQLEVEL", "TRICORE_NUM_CSA", "STACKSIZE" };
		
		oil.append(prefix+"ISR " + getIdentifier() + " {\n");
		super.toOIL(oil, supportedAttributes, prefix+"\t");
		
		// Resources
		DomainDefinition dom = (DomainDefinition) parent;
		SystemDefinition sysdef = (SystemDefinition) dom.parent;

		for(int i=0; i<usedResources.size(); i++) {
			Attribut a = (Attribut) usedResources.elementAt(i);
			ResourceDefinition res = dom.getResource(a.valueString());
			if (res == null && sysdef.getPDomain()!=null)
				res = sysdef.getPDomain().getResource(a.valueString());
			if (res == null)
				throw new CompileException("ISR " + getIdentifier() + " references unknown Resource " + a.valueString());

			multiRefToOIL(oil, prefix+"\t", "RESOURCE", res.getIdentifier());
		}
		// TODO messages
		oil.append(prefix+"};\n\n");
	}

	protected void finalizeCfg(FinalizeResult res) throws ParseException {
		Attribut classAttr = getAttribute("HandlerClass");
		Attribut methodAttr = getAttribute("HandlerMethod");
			
			if(methodAttr==null || classAttr==null)
				throw new ParseException("ISR " + getIdentifier() + " needs HandlerClass and HandlerMethod specified");

			res.requireMethod(classAttr.valueString(), methodAttr.valueString());
	}

	protected String typeID() { return "i_"; }
}
