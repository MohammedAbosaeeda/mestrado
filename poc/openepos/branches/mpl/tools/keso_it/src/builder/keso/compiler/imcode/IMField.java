/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.compiler.imcode; 

import keso.classfile.*;
import keso.classfile.constantpool.*; 
import keso.classfile.datatypes.*; 

import keso.compiler.*;
import keso.compiler.backend.*;
import keso.util.DecoratedNames;

import java.util.Vector;
import java.util.Hashtable;
import java.util.Enumeration;

/**
 * Internal representation of a object field.
 */
public class IMField {

	protected IMClass	  fieldClass;
	protected BuilderOptions  opts;
	protected int             datatype;
	protected int		  static_index = -1;
	protected FieldData       field; 
	protected String	  fieldName;
	protected String	  aliasName;
	protected String	  macroName;
	protected String	  typeString=null;

	public IMField(BuilderOptions opts, IMClass field_class, FieldData source) {
		this.opts    = opts;
		this.field   = source;
		this.fieldClass=field_class;
		this.datatype = BasicTypeDescriptor.toBasicDatatype(field.getType().charAt(0));
		this.fieldName = field.getName();
		this.aliasName = DecoratedNames.createFieldAlias(opts, field_class, field.getName());

		StringBuffer key = new StringBuffer("S");
		key.append(field_class.getAlias());
		key.append('_');
		key.append(aliasName);
		macroName = key.toString().toUpperCase();

		if (datatype==BCBasicDatatype.REFERENCE) {
			int len = field.getType().length();
			typeString = field.getType().substring(1,len-1); 
		}
	}

	public boolean hasStaticIndex() {
		return static_index!=-1;
	}

	public void setStaticIndex(int value) {
		static_index=value;
	}

	public int getDatatype() {
		return datatype;
	}

	public boolean isReference() { return datatype==BCBasicDatatype.REFERENCE; }
	public boolean isStatic() { return field.isStatic(); }
	public boolean isFinal() { return field.isFinal(); }
	public boolean isPrivate() { return field.isPrivate(); }
	public boolean isVolatile() { return field.isVolatile(); }
	public boolean isTransient() { return field.isTransient(); }

	public String getPrivateFieldName() {
		if (isPrivate()) {
			return aliasName;
		} else {
			return fieldName;
		}
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getAlias() {
		return aliasName;
	}

	public String getMacroName() {
		return macroName; 
	}

	public String getRefClassName() {
		return typeString; 
	}

	public void header_emit_mt(Coder coder) throws CompileException {
		if (typeString.equals("keso/core/MT")) return;

		coder.header_add("\t");
		if (typeString.equals("keso/core/MT_U32")) {
			coder.header_add("volatile unsigned long");
		} else if (typeString.equals("keso/core/MT_SPACE32")) {
			coder.header_add("volatile unsigned long");
		} else if (typeString.equals("keso/core/MT_U32RO")) {
			coder.header_add("volatile unsigned long");
		} else if (typeString.equals("keso/core/MT_U16")) {
			coder.header_add("volatile unsigned short");
		} else if (typeString.equals("keso/core/MT_U8")) {
			coder.header_add("volatile unsigned char");
		} else {
			throw new CompileException("Unkown memory type! "+typeString);
		}
		coder.header_add(" ");
		coder.header_add(aliasName);
		coder.header_add(";\n");
	}

	public void emit_macro(Coder coder) throws CompileException {
		coder.add(macroName);
	}

	public void global_emit_macro(Coder coder) throws CompileException {
		if (!isStatic()) throw new CompileException("is not a static field");
		coder.global_header_add("#define ");
		coder.global_header_add(macroName);
		if (datatype==BCBasicDatatype.REFERENCE && !opts.hasOption("no_static_gc")) {
			coder.global_header_add(" static_ref");
			if (!opts.isSingleDomainSystem()) 
				coder.global_header_add("[KESO_CURRENT_DOMAIN]"); 
			coder.global_header_add("[");
			coder.global_header_add(static_index);
			coder.global_header_add("]");
		} else {
			coder.global_header_add(" ");
			coder.global_header_add(fieldClass.getClassStaticsString());
			if (!opts.isSingleDomainSystem())
				coder.global_header_add("[KESO_CURRENT_DOMAIN]"); 
			coder.global_header_add(".");
			coder.global_header_add(aliasName);
		}
		coder.global_header_add("\n");
	}

	public void header_emit(Coder coder) throws CompileException {
		coder.header_add("\t");
		if (isVolatile())
			coder.header_add("volatile ");
		coder.header_add(BuilderOptions.typeToString(datatype));
		coder.header_add(" ");
		coder.header_add(aliasName);
		coder.header_add(";\n");
	}

	public void emit(StringBuffer buffer) {
		buffer.append("\t");
		if (isVolatile())
			buffer.append("volatile ");
		buffer.append(BuilderOptions.typeToString(datatype));
		buffer.append(" ");
		buffer.append(aliasName);
		buffer.append(";\n");
	}

	public void emit_field(StringBuffer buffer) throws CompileException {
		buffer.append(aliasName);
	}
}
