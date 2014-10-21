/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.compiler.config;

import keso.compiler.config.parser.ParseException;
import keso.compiler.CompileException;

import java.util.Vector;

public class Set extends Attribut {

	final private static int InitialSize = 64;

	class Entry {
		Entry(int hash, String ident, Attribut value) {
			this.id=ident;
			this.hash=hash;
			this.value=value;
		}
		int	 hash;
		String	 id;
		Attribut value;
		Entry    next;
		Entry	list;
	}

	private Entry[] table;
	private Entry[] values;
	private int count;
	private int resize;

	public Set(String ident, int line) { this(null, ident, line); }

	public Set(Set parent, String ident, int line) {
		super(parent,ident,line);
		table  = new Entry[InitialSize];
		values = new Entry[InitialSize];

		// resize ~= 75% of table.length
		resize = (table.length >> 1) + (table.length >> 2); 
	}

	public int valueInt() { throw new Error(); } 
	public double valueDouble() { throw new Error(); } 
	public String valueString() { throw new Error(); }

	public void addAttribute(String ident, Attribut value) {

		int hash = ident.toUpperCase().hashCode(); 

		Entry ne = new Entry(hash, ident, value);
		values[count] = ne;
		count++;
		if (count==values.length) incStoreCapacity();

		put(ident, hash, ne);
	} 

	public Attribut getAttribute(String ident) {
		Entry e = get(ident,ident.toUpperCase().hashCode());
		if (e==null) return null;
		return e.value;
	}

	public Attribut[] getAllAttributes() {
		Attribut[] ret = new Attribut[count];
		for (int i=0;i<count;i++) ret[i] = values[i].value;
		return ret;	
	}

    public String[] getAllIdentifiers() {
        String[] ret = new String[count];
        
        for (int i = 0; i < count; ++i) {
            ret[i] = values[i].id;
        }
        
        return ret;
    }

	public void setComplex(ComplexAttribute cattr) throws ParseException {
		addAttribute(cattr.toString(), cattr);
		//throw new ParseException("No complex attributes supported by this Set");
	}
	
	/**
	 * General OIL conversion method that will return all subattributes
	 * of this Set in OIL format.
	 * Specializations may overload this method as appropriate but still
	 * use it to dump their subattributes.
	 * The parameter supportedAttributes contains the name of all known
	 * Attributes for this Set. Only those attributes will be dumped.
	 * If supportedAttributes is the null reference, all attributes will
	 * be dumped.
	 * The parameter prefix will be prepended to every generated line
	 * of output, thus allowing to give hints for correct identation.
	 */
	public void toOIL(StringBuffer oil, String[] supportedAttributes, String prefix) throws CompileException {
		Attribut[] attrs = getAllAttributes();
	
		for (int i=0; i<attrs.length; i++) {
			if (supportedAttributes==null) {
				attrs[i].toOIL(oil, prefix);
				continue;
			}
			for(int j=0; j<supportedAttributes.length; j++) {
				if(supportedAttributes[j].compareToIgnoreCase(attrs[i].ident)==0) {
					attrs[i].toOIL(oil, prefix);
					break;
				}
			}
		}
	}
	
	/**
	 * Find an Attribut by its identifier in a Vector of attributes.
	 *
	 * @param A vector with Attribut only elements that will be searched.
	 * @param The identifier of the Attribute that shall be looked up.
	 * @return null if the Attribut was not found, the Attribut if it was found
	 */
	public Attribut findAttributeInVector(Vector attributes, String ident) {
		for(int i=0; i<attributes.size(); i++) {
			Attribut a = (Attribut) attributes.elementAt(i);
			if(a.ident.compareTo(ident) == 0) return a;
		}
		return null;
	}

	/**
	 * finalizeCfg will be called after the config file has entirely been read.
	 * Any subclass may overload it to do work that can only be done with
	 * knowledge of the complete config.
	 *
	 * This is the standard finalizeCfg function that just does not do anything
	 */
	protected void finalizeCfg(FinalizeResult res) throws keso.compiler.config.parser.ParseException {}

	final protected void finalizeVector(Vector sets, FinalizeResult res) throws keso.compiler.config.parser.ParseException {
		for(int i=0; i<sets.size(); i++)
			((Set) sets.elementAt(i)).finalizeCfg(res);
	}
	
	private Entry get(String id, int hash) {
		if (table==null) return null;
		int l = table.length;
		int i = (hash & 0x7fffffff) % l;
		Entry e=table[i];
		while (e!=null && e.hash!=hash && !e.id.equals(id)) e=e.next;
		if (e==null) return null;
		return e;
	}

	private void put(String id, int hash, Entry ne) {

		if (count>resize) incIndexCapacity();

		int i = (hash & 0x7fffffff) % table.length;

		Entry e=table[i];
		while (e!=null) {
			if (e.hash==hash && e.id.equals(id)) break;
			e=e.next;
		}
		if (e!=null) {
			ne.next = e.next;
			e.next = null;
			ne.list = e;
		}

		e = table[i];
		ne.next = e;
		table[i] = ne;
	}

	private void incStoreCapacity() {
		Entry[] ov = values;
		int size = values.length << 1;
		values = new Entry[size];
		System.arraycopy(ov,0,values,0,ov.length);
	}

	private void incIndexCapacity() {
		Entry[] ot = table;
		int size = table.length << 1;
		resize = (table.length >> 1) + (table.length >> 2); 
		table = new Entry[size];
		for (int i=0; i<ot.length; i++) {
			Entry e = ot[i];
			while (e!=null) { put(e.id, e.hash, e); e=e.next; } 
		}
	}
	
	protected void multiRefToOIL(StringBuffer oil, String prefix, String attrName, String value) {
		oil.append(prefix);
		oil.append(attrName.toUpperCase());
		oil.append(" = ");
		oil.append(value);
		oil.append(";\n");
	}


    public String toString() {
        return ident;
    }
}
