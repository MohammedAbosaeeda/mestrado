/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi.fau.de for more info.

  (c)**/

package keso.util; 

import java.util.Hashtable;

import keso.compiler.BuilderOptions;
import keso.compiler.ClassTypeInfo;
import keso.compiler.imcode.IMMethod;
import keso.compiler.imcode.IMClass;

import keso.classfile.*;
import keso.classfile.constantpool.*; 
import keso.classfile.datatypes.*; 

final public class DecoratedNames {

	private static boolean short_class_id = true;

	//private static int class_counter=0;
	private static int method_counter=0;
	//private static int field_counter=0;
	private static Hashtable class_index = new Hashtable();
	private static Hashtable field_index = new Hashtable();

	public static void reset() {	
		 method_counter=0;
		 //field_counter=0;
		 class_index = new Hashtable();
		 field_index = new Hashtable();
	}

	public final static String createFieldAlias(BuilderOptions opts, IMClass clazz, String fieldname) { 

		String alias = (String)field_index.get(clazz.getAlias()+fieldname);
		if (alias!=null) return alias; 

		//field_counter++;
		StringBuffer nalias = new StringBuffer();
		if (clazz.isInterface()) { nalias.append('i'); } else { nalias.append('c'); }
		nalias.append(clazz.getClassTypeID());
		nalias.append("f");
		nalias.append(clazz.uniqFieldID());
		if (opts.hasDbgSymbols()) {
			nalias.append("_");
			nalias.append(toEscString(fieldname));
		}
		alias=nalias.toString();

		field_index.put(clazz.getAlias()+fieldname, alias); 
		return alias;
	}

	public final static String createClassAlias(BuilderOptions opts, String classname, ClassData clazz,
							ClassTypeInfo type) {

		if (type==null) throw new Error("called with no ClassTypeInfo!");

		//int i = class_counter++;
		int i = type.getClassTypeID();
		StringBuffer ret = new StringBuffer();

		if (clazz == null) {
			ret.append('x');
		} else {
			if (clazz.isInterface()) { ret.append('i'); } else { ret.append('c'); }
		}
		ret.append(i);
		if (opts.hasDbgSymbols()) {
			ret.append('_');
			if (short_class_id) {
				ret.append(toEscString(baseName(classname)));
			} else {
				ret.append(toEscString(classname));
			}
		}

		return ret.toString();
	}

	public final static String createMethodAlias(BuilderOptions opts, IMMethod method) {
		String class_alias = method.getIMClass().getAlias();

		if (opts.fastMethodAlias()) {
			method_counter++;
		} else {
			Integer counter = (Integer)class_index.get(class_alias); 
			if (counter==null) { counter = new Integer(1); }
			else { counter = new Integer(counter.intValue()+1); }
			class_index.put(class_alias, counter);
			method_counter = counter.intValue();
		}

		if (opts.hasDbgSymbols()) 
			return class_alias+"_m"+method_counter+"_"+toEscString(method.getMethodName());

		return class_alias+"_m"+method_counter;
	}

	public final static String baseName(String str) {
		StringBuffer nstr = new StringBuffer(str);
		int j=0;
		for (int i=0;i<str.length();i++) {
			char c = str.charAt(i);
			if (c=='/' || c=='\\') {
				j=0;
				continue;
			}
			nstr.setCharAt(j,c);
			j++;
		}
		nstr.setLength(j);
		return nstr.toString();
	} 

	public final static String toEscString(String str) {
		StringBuffer nstr = new StringBuffer(str);
		for (int i=0;i<nstr.length();i++) {
			char c = nstr.charAt(i);
			if (c<'0' || (c>'9' && c<'A') || (c>'Z' && c<'a') || c>'z') {
				nstr.setCharAt(i,'_');
			}
			/*
			switch (nstr.charAt(i)) {
				case '<':
				case '>':
				case '/':
				case '\\':
				case '[':
				case ']':
				case '$':
				case ';':
				case ',':
				case '.':
					nstr.setCharAt(i,'_');
			}
			*/
		}
		return nstr.toString();
	} 

	public final static String toEscTexString(String str) {
		StringBuffer nstr = new StringBuffer(str);
		for (int i=0;i<nstr.length();i++) {
			switch (nstr.charAt(i)) {
//				case '<':
//				case '>':
//				case '/':
//				case '[':
//				case ']':
//				case '$':
//				case ';':
//					nstr.setCharAt(i,'_');
				case '\\':
					nstr.setCharAt(i++,'\\');
					nstr.insert(i,"textbackslash ");
					break;
				case '{':
					nstr.setCharAt(i++,'\\');
					nstr.insert(i,"textbraceleft ");
					break;
				case '}':
					nstr.setCharAt(i++,'\\');
					nstr.insert(i,"textbraceright ");
					break;
				case '_':
					nstr.setCharAt(i++,'\\');
					nstr.insert(i,"textunderscore ");
					break;
				case '\"':
					nstr.setCharAt(i++,'\\');
					nstr.insert(i,"textquotedblleft ");
					break;
			}
		}
		return nstr.toString();
	} 
}
