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

public class ResourceDefinition extends Set {
	/* used as index into keso_resource_index to
	 * reference the entry associated to an invalid
	 * domain lookup (null)
	 *
	 * Stable after config file has been parsed completly.
	 */
	public static byte INVALID_RESOURCE=0;
	
	public ResourceDefinition(Set dom, String name, int line) {
		super(dom, name, line);
		INVALID_RESOURCE++;
	}

	public String toString() {
		if(parent instanceof DomainDefinition)
			return ident + " (domain: " + parent + ")";
		return ident;
	}

	/**
	 * Generate a RESOURCE block in OIL syntax out of this RESOURCE Definition.
	 *
	 * Known and supported attributes
	 * OIL standard:
	 *   RESOURCEPROPERTY		(STANDARD, LINKED, INTERNAL)
	 *   
	 *   If ResourceProperty==LINKED, it has a subattribute
	 *   LINKED_RESOURCE		(RESOURCETYPE)
	 */
	public void toOIL(StringBuffer oil, String prefix) throws CompileException {
		Attribut property = getAttribute("ResourceProperty");
		
		if (property == null)
			throw new CompileException("ResourceProperty of Resource "+
					getIdentifier()+" not defined.");

		oil.append(prefix+"RESOURCE " + getIdentifier() + " {\n");
		oil.append(prefix+"\t"+property.ident.toUpperCase());
		oil.append(" = ");
		oil.append(property.valueString().toUpperCase());

		if (property.valueString().compareToIgnoreCase("Linked")==0) {
			Attribut linkedRes = getAttribute("LinkedResource");
			if(linkedRes==null)
				throw new CompileException("LinkedResource attribute of LINKED Resource "+
						getIdentifier()+" not defined.");
			
			oil.append(" {\n" + prefix + "\t\t");
			oil.append(linkedRes.ident.toUpperCase());
			oil.append(" = ");
			
			ResourceDefinition lres = ((DomainDefinition) parent).getResource(linkedRes.valueString());
			
			if (lres==null)
				throw new CompileException("Unknown resource " + linkedRes.valueString()+" linked to "+getIdentifier());
			
			oil.append(lres.getIdentifier());
			oil.append(";\n"+prefix+"\t};\n");
		} else oil.append(";\n");

		oil.append(prefix+"};\n\n");
	}

	protected String typeID() { return "r_"; }

	protected void finalizeCfg(FinalizeResult res) {
		// just require Resource class
		res.requireMethod("keso/core/Resource", null);
	}
}
