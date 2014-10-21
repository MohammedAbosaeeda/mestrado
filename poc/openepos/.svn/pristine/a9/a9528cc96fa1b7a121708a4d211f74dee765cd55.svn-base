/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.compiler.imcode; 

import keso.compiler.*;
import java.util.Hashtable;
import java.util.Enumeration;

/**
 * The elements of this class represent descriptor fields of the domain_desc
 * table.
 */
final class DescriptorField {
	String name;
	String type;
	String value;

	/**
	 * Indicates if this DescriptorField's type is a function pointer.
	 * In this case, the name of the field is included in the type
	 * and does not need to be printed seperately.
	 */
	boolean fnPointer;

	DescriptorField(String type, String name, String value, boolean fnPointer) {
		this.type = type;
		this.name = name;
		this.value = value;
		this.fnPointer = fnPointer;
	}

	String emit_declare() {
		if(fnPointer) return type+";\n";
		return type+" "+name+";\n";
	}

	String emit_define() {
		if (value==null) return "";
		return "."+name+"="+value+",\n";
	}

	final public int hashCode() {
		return name.hashCode();
	}

	final public boolean equals(Object obj) {
		if (obj instanceof DescriptorField) return name.equals(((DescriptorField)obj).name);
		return false;
	}
}

/**
 * Abstract superclass for all IMModul childs that contain fields of the
 * domain descriptor (or maybe other descriptors in the future).
 */
public abstract class IMDescriptorContainer extends IMModul {
	/**
	 * Contains the descriptor fields.
	 */
	private Hashtable descFields = new Hashtable();

	protected IMDescriptorContainer(String name, String alias, BuilderOptions opts) {
		super(name,alias,opts);
	}

	/**
	 * Add a new descriptor field. 
	 *
	 * @param type C-Type of the field.
	 * @param name Name of the field.
	 */
	public void addDescriptorField(String type, String name, boolean fnPointer)  {
		addDescriptorField(type,name,null, fnPointer);
	}

	/**
	 * Add a new descriptor field and assign an initial value.
	 *
	 * @param type  C-Type of the field.
	 * @param name  Name of the field.
	 * @param value Initial value of the field.
	 */
	public void addDescriptorField(String type, String name, String value, boolean fnPointer)  {
		descFields.put(name,new DescriptorField(type, name, value, fnPointer));
	}

	/**
	 * Change the value of a descriptor field.
	 *
	 * @param name  Name of the field.
	 * @param value New value of the field.
	 *
	 * @throws CompileException A field with the specified name does not exist.
	 */
	public void setFieldValue(String name, String value)  throws CompileException {
		DescriptorField f=null;
		if ((f=(DescriptorField)descFields.get(name))==null) throw new CompileException("unkown descriptor field");
		f.value = value;
	}

	/**
	 * Generate a String with the specified prefix (indentation) that
	 * contains the declaration of all fields in this container.
	 *
	 * @param  prefix String that will be prefixed to each line, primarily for indentation.
	 * @return String containing the declarations, one per line, with prefixed
	 *         with the specified prefix.
	 */
	public String emit_declareFields(String prefix) {
		StringBuffer declaration = new StringBuffer();
		Enumeration fields = descFields.elements();
		while(fields.hasMoreElements()) {
			DescriptorField f = (DescriptorField) fields.nextElement();
			declaration.append(prefix);
			declaration.append(f.emit_declare());
		}
		return declaration.toString();
	}

	/**
	 * Generate a String with the specified prefix (indentation) that
	 * contains the definition of all fields in this container.
	 *
	 * @param  prefix String that will be prefixed to each line, primarily for indentation.
	 * @return String containing the definitions, one per line, with prefixed
	 *         with the specified prefix.
	 */
	public String emit_defineFields(String prefix) {
		StringBuffer definition = new StringBuffer();
		Enumeration fields = descFields.elements();
		while(fields.hasMoreElements()) {
			DescriptorField f = (DescriptorField) fields.nextElement();
			definition.append(prefix);
			definition.append(f.emit_define());
		}
		return definition.toString();
	}
}
