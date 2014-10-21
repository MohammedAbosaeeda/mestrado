/**(c)

  Copyright (C) 2005-2007 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi.fau.de for more info.

  (c)**/

package keso.compiler; 


import keso.compiler.config.*;
import keso.compiler.imcode.*;
import keso.classfile.ClassData;

import keso.compiler.imcode.IMNode;
import keso.compiler.imcode.IMDomain;
import keso.compiler.imcode.IMClass;
import keso.compiler.imcode.IMMethod;
import keso.compiler.imcode.IMArrayClass;
import keso.compiler.imcode.IMObjectArrayClass;
import keso.compiler.imcode.IMPortalService;

import keso.compiler.kni.JoinPointChecker;
import keso.compiler.kni.Weavelet;
import keso.compiler.config.ComplexBoolAttribute;
import keso.compiler.backend.Coder;

import java.io.RandomAccessFile;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;

import java.util.Hashtable;
import java.util.Vector;
import java.util.Enumeration;
import java.util.Set;
import java.util.Iterator;

import keso.util.Debug; 
import keso.util.IntegerHashtable; 

import keso.compiler.config.FinalizeResult;

final class FieldAccessEntry {

	private int hCode;
	protected String class_name;
	protected String field_name;
	protected boolean read = false;
	protected boolean write = false;
	protected IMNode forward = null;

	FieldAccessEntry(String c, String f) {
		class_name=c;
		field_name=f;
		hCode=c.hashCode()+f.hashCode();
	}

	public void mark_read() { read = true; }
	public void mark_write() { write = true; }

	public boolean omit() { return !( read && write ); }

	public void forward(IMNode fwd) { forward=fwd; }

	public IMNode forwardObj() throws CompileException {
		return forward;
	}

	public String toString() {
		StringBuffer msg = new StringBuffer(class_name);
		msg.append(" ");
		msg.append(field_name);
		msg.append(" ");
		if (read) { msg.append("r");} else { msg.append("-"); }
		if (write) { msg.append("w ");} else { msg.append("- "); }
		if (forward!=null) msg.append(forward.toReadableString());
		return msg.toString(); 
	}

	public boolean equals(Object obj) {
		if (obj == null) return false;

		if (!(obj instanceof FieldAccessEntry)) return false;

		FieldAccessEntry entry = (FieldAccessEntry)obj;

		if (!entry.class_name.equals(this.class_name)) return false;

		return entry.field_name.equals(this.field_name);
	}

	public int hashCode() { return hCode; }
}

/**
 * This class contains all source classes.
 */
final public class ClassStore {

	private BuilderOptions opts = null;
	private JoinPointChecker jps = null;
	private Hashtable classes = new Hashtable();
	private Hashtable sub_classes = new Hashtable();
	private Hashtable impl_ifaces = new Hashtable();
	private IMClass   rootClass;

	private Hashtable statics_to_index = new Hashtable();
	private Hashtable statics_to_access = new Hashtable();
	private Hashtable fields_to_access = new Hashtable();
	private Hashtable translation_callback = new Hashtable();
	private IntegerHashtable class_type_info;

	private int statics_id = 0;

	//private static int uniqInterfaceID=0;
	//There is only one ClassStore!
	private int uniqInterfaceID=0;

	private int sharedMemorySize = 0;

	private ClassStore current_src=null;

	private Hashtable need_check=null;
	private boolean need_class_checkcast = false;
	private boolean need_iface_checkcast = false;
	private boolean need_class_instanceof = false;
	private boolean need_iface_instanceof = false;

	private IMPortalService[] systemServices = null;
	private IMPortalClass[] portalProxies = null;

	/**
	 * Creates an empty ClassStore
	 */
	private ClassStore(BuilderOptions opts, ClassStore source) throws CompileException {
		this.opts=opts;
		this.jps=opts.getJoinPoints();
		this.current_src = source;
		opts.registerWeavelets(this);
	}

	/**
	 * Create the ClassStore and populate with standard Arrayclasses
	 * and the classes discovered in the sourcepath
	 */
	public ClassStore(BuilderOptions opts, String[] sourcePaths) throws IOException, CompileException {
		this.opts=opts;
		this.jps=opts.getJoinPoints();

		// Add standard Arrayclasses
		addPredefinedClass(new IMObjectArrayClass(opts,this,"[Ljava/lang/Object;","object_array","object_t*",true));
		addPredefinedClass(new IMArrayClass(opts,this,"[Z","boolean_array",BuilderOptions.BOOLEAN_T,true));
		addPredefinedClass(new IMArrayClass(opts,this,"[B","byte_array",BuilderOptions.BYTE_T,true));
		addPredefinedClass(new IMArrayClass(opts,this,"[C","char_array",BuilderOptions.CHAR_T,true));
		addPredefinedClass(new IMArrayClass(opts,this,"[S","short_array",BuilderOptions.SHORT_T,true));
		addPredefinedClass(new IMArrayClass(opts,this,"[I","int_array",BuilderOptions.INT_T,true));
		addPredefinedClass(new IMArrayClass(opts,this,"[J","long_array",BuilderOptions.LONG_T,true));
		addPredefinedClass(new IMArrayClass(opts,this,"[F","float_array",BuilderOptions.FLOAT_T,true));
		addPredefinedClass(new IMArrayClass(opts,this,"[D","double_array",BuilderOptions.DOUBLE_T,true));

		// Source Path parameter contains multiple paths/classfiles seperated by ':'
		// Add all Classfiles discoverable within the sourcepath to the repository
		for (int n=0; n<sourcePaths.length;n++) {
			opts.verbose("source path: "+sourcePaths[n]);
			addClassFiles(sourcePaths[n]);
		}

		registerSystemServices(this);
		createPortalClasses();
	}

	public BuilderOptions getOptions() {
		return opts;
	}

	public void resetCallGraph() {
		need_check = null;
		need_iface_instanceof = false;
		need_class_instanceof = false;
		need_iface_checkcast = false;
		need_class_checkcast = false;
	}

	public void needInstanceOf(IMClass clazz) {
		if (need_check==null) need_check = new Hashtable();
		need_check.put(clazz,clazz);
		if (clazz.isInterface()) {
			need_iface_instanceof = true;
		} else {
			need_class_instanceof = true;
		}
	}

	public void needCheckCast(IMClass clazz) {
		if (need_check==null) need_check = new Hashtable();
		need_check.put(clazz,clazz);
		if (clazz.isInterface()) {
			need_iface_checkcast = true;
		} else {
			need_class_checkcast = true;
		}
	}

	/**
	 * is true if the type is used for checkcast or instanceof
	 */
	public boolean typeInUse(IMClass clazz) {
		if (need_check==null) return false;
		if (need_check.get(clazz)!=null) return true;
		return false;
	}

	private class StringEntry {
		String alias;
		String emit;
		String value;
	}

	private static int str_counter=0;
	private Hashtable string_index = new Hashtable();
	private IntegerHashtable struct_index = new IntegerHashtable();
	private IMClass str_class = null;
	public String allocString(Coder coder, IMNode node, String str) throws CompileException {

		StringEntry e = (StringEntry)string_index.get(str);
		if (e==null) {
			if (str_class==null) str_class=getClass("java/lang/String");
			e = new StringEntry();
			e.alias = "str"+str_counter++;
			//e.emit = "((object_t*)&"+e.alias+")";
			e.emit = coder.add_accessStaticObject(str_class, e.alias);
			e.value = str;
			string_index.put(str,e);
			struct_index.put(str.length(), e);
		}

		return e.emit;
	}

	private void emit_global_obj_header(Coder coder, IMClass clazz) throws CompileException {
		if (opts.hasOption("c++")) {
			if (opts.getGlobalHeap().needGCInfo()) {
				coder.global_add("1,");
			} else {
				coder.global_add("0,");
			}
			coder.global_add("0,");
			coder.global_add(clazz.getClassID());
		} else {
			coder.global_add(".class_id=");
			coder.global_add(clazz.getClassID());
			if (opts.getGlobalHeap().needGCInfo())
				coder.global_add(", .gcinfo=1");
		}
	}

	private void emit_Strings(Coder coder) throws CompileException {

		int[] keys = struct_index.sortedKeys();
		if (keys.length==0) return; 

		boolean fld_noname = opts.hasOption("c++");

		coder.global_add("/* global strings */\n");

		IMClass str_clazz = getClass("java/lang/String");
		coder.global_header_add("#include \"");
		coder.global_header_add(str_clazz.getAlias());
		coder.global_header_add(".h\"\n");

		IMClass ca_clazz = getClass("[C");
		coder.global_header_add("#include \"");
		coder.global_header_add(ca_clazz.getAlias());
		coder.global_header_add(".h\"\n");

		for (int i=0;i<keys.length;i++) {
			String static_struct  = "str"+keys[i]+"_s";
			coder.global_add("struct ");
			coder.global_add(static_struct);
			coder.global_add(" {\nARRAY_HEADER\njchar data[");
			coder.global_add(keys[i]+1);
			coder.global_add("];\n};\n");
		}

		Enumeration en = string_index.elements();
		while (en.hasMoreElements()) {
			StringEntry e = (StringEntry)en.nextElement();
			String static_string  = e.alias;
			String static_chararr = e.alias+"_arr";
			String static_struct  = "str"+e.value.length()+"_s";
			String str = e.value;

			coder.global_header_add("extern ");
			coder.global_header_add(str_clazz.getClassTypeString());
			coder.global_header_add(" ");
			coder.global_header_add(static_string);
			coder.global_header_add(";\n");

			coder.global_add("static struct ");
			coder.global_add(static_struct);
			coder.global_add(" ");
			coder.global_add(static_chararr);
			coder.global_add(" HEAP_SECTION ={\n");
			emit_global_obj_header(coder,ca_clazz);
			coder.global_add(",");
			if (!fld_noname) 
				coder.global_add(".size=");
			coder.global_add(str.length());
			coder.global_add(",");
			if (!fld_noname) 
				coder.global_add("\n.data=");
			coder.global_add("{");
			for (int i=0;i<str.length();i++) { 
				char c = str.charAt(i);
				if (c>255) {
					coder.global_add("'?'");
				} else {
					coder.global_add((int)c);
				}
				coder.global_add(",");
			}
			coder.global_add("0}\n};\n");

			coder.global_add(str_clazz.getClassTypeString());
			coder.global_add(" HEAP_SECTION ");
			coder.global_add(e.alias);
			coder.global_add("={\n");
			if (!fld_noname) {
				coder.global_add(".");
				coder.global_add(str_clazz.emitField("value"));
				coder.global_add("=");
			}
			coder.global_add("(object_t*)&");
			coder.global_add(static_chararr);
			coder.global_add(",");
			emit_global_obj_header(coder,str_clazz);
			coder.global_add("\n};\n");
		}
	}

	public void registerStaticRefField(IMField sfield) {
		if (statics_to_index.get(sfield)==null) {
			if (sfield.isReference()) sfield.setStaticIndex(statics_id++);
			statics_to_index.put(sfield,sfield);
		}
	}

	public void registerGlobalTranslationCallback(String key, IMNode callback) {
		translation_callback.put(key, callback);
	}

	public void registerIMGetStatic(String classname, String fieldname) {
		FieldAccessEntry oentry;
		FieldAccessEntry entry = new FieldAccessEntry(classname, fieldname);

		if ((oentry=(FieldAccessEntry)statics_to_access.get(entry))==null) {
			oentry=entry;
			statics_to_access.put(entry, entry);
		}

		oentry.mark_read();
		opts.vverbose("registerIMGetStatic "+oentry.toString());
	}

	public void registerIMPutStatic(String classname, String fieldname, IMNode forward) {
		FieldAccessEntry oentry;
		FieldAccessEntry entry = new FieldAccessEntry(classname, fieldname);

		if ((oentry=(FieldAccessEntry)statics_to_access.get(entry))==null) {
			oentry=entry;
			statics_to_access.put(entry, entry);
		}

		oentry.mark_write();
		oentry.forward(forward);
		opts.vverbose("registerIMPutStatic "+oentry.toString());
	}

	public void registerIMGetField(String classname, String fieldname) {
		FieldAccessEntry oentry=null;
		FieldAccessEntry entry = new FieldAccessEntry(classname, fieldname);

		if ((oentry=(FieldAccessEntry)fields_to_access.get(entry))==null) {
			oentry=entry;
			fields_to_access.put(entry, entry);
		}

		oentry.mark_read();
		opts.vverbose("registerIMGetField "+oentry.toString());
	}

	public void registerIMPutField(String classname, String fieldname) {
		FieldAccessEntry oentry=null;
		FieldAccessEntry entry = new FieldAccessEntry(classname, fieldname);

		if ((oentry=(FieldAccessEntry)fields_to_access.get(entry))==null) {
			oentry=entry;
			fields_to_access.put(entry, entry);
		}

		oentry.mark_write();
		opts.vverbose("registerIMPutField "+oentry.toString());
	}

	public boolean omitField(String classname, String typedesc, String fieldname) {
		if (!opts.hasOption("omit_fields")) return false;

		if (jps.checkFieldAttribut(classname,fieldname,Weavelet.ATTR_DO_NOT_OMIT)==Weavelet.TRUE)
			return false;

		FieldAccessEntry entry = new FieldAccessEntry(classname, fieldname);
		FieldAccessEntry fld = (FieldAccessEntry)fields_to_access.get(entry);

		if (typedesc.charAt(0)=='L') {
			String type = typedesc.substring(1,typedesc.length()-1);
			if (getClass(type).isMemoryType()) {
				/* all memory types are protected */
				//opts.verbose(fld+" memory type!");
				return false;
			}
		}

		if (fld==null) {
			opts.warn(entry+" unused!");
			return true;
		}

		//opts.verbose(entry);
		return fld.omit();
	}

	public boolean omitStaticField(String classname, String typedesc, String fieldname) {
		if (!opts.hasOption("omit_fields")) return false;
		
		if (jps.checkFieldAttribut(classname,fieldname,Weavelet.ATTR_DO_NOT_OMIT)==Weavelet.TRUE)
			return false;

		FieldAccessEntry entry = new FieldAccessEntry(classname, fieldname);
		FieldAccessEntry fld = (FieldAccessEntry)statics_to_access.get(entry);

		if (typedesc.charAt(0)=='L') {
			String type = typedesc.substring(1,typedesc.length()-1);
			if (getClass(type).isMemoryType()) {
				/* all memory types are protected */
				//opts.verbose(fld+" memory type!");
				return false;
			}
		}

		if (fld==null) {
			opts.warn(fld+" unused!");
			return true;
		}

		opts.verbose("++ omit "+fld);
		return fld.omit();
	}

	public IMNode forwardStaticField(IMMethod dstMethod, String classname, String fieldname) throws CompileException {
		if (opts.hasOption("no_forward_fields")) return null;
		FieldAccessEntry entry = new FieldAccessEntry(classname, fieldname);
		FieldAccessEntry fld = (FieldAccessEntry)statics_to_access.get(entry);
		if (fld==null) return null; 
		if (fld.forwardObj()==null) return null;
		return fld.forwardObj().copy(new IMReplaceVisitor(dstMethod, null, null));
	}

	public int getMaxInterfaceID() {
		if (opts.hasOption("iface_dbg128") && uniqInterfaceID<129) return 128;
		if (uniqInterfaceID==0) return 0;
		return uniqInterfaceID-1;
	}

	public int getNewInterfaceID() {
		if (classes==null) throw new Error();
		return uniqInterfaceID++; 
	}

	public IMClass getRootClass() {
		if (rootClass==null) rootClass = getClass("java/lang/Object");
		return rootClass;
	}

	public IMClass getClass(String classname) {
		return (IMClass)classes.get(classname);
	}

	public boolean hasSubClasses(String classname) {
		if (sub_classes.get(classname)==null) return false;
		return true;
	}

	public IMClass[] getSubClasses(String classname) {

		Hashtable subs = (Hashtable)sub_classes.get(classname);
		if (subs==null) return null;

		IMClass[] all = new IMClass[subs.size()];

		if (false) {
			Enumeration e = subs.elements();
			for (int i=0;e.hasMoreElements();i++) { all[i] = (IMClass)e.nextElement(); }
		} else {
			/* all Classes first */
			int p=0;
			Enumeration e = subs.elements();
			while (e.hasMoreElements()) {
				IMClass cls = (IMClass)e.nextElement();
				if (cls.isInterface()) continue;
				all[p++]=cls;
			}

			/* interfaces */
			e = subs.elements();
			while (e.hasMoreElements()) {
				IMClass cls = (IMClass)e.nextElement();
				if (!cls.isInterface()) continue;
				all[p++]=cls;
			}
		}

		return all;
	}

	public String[] findImplementsByName(String iface_name) {
		IMClass[] impls=null;

		if (current_src!=null) {
			impls=current_src.findImplements(iface_name);
		} else {
			impls=findImplements(iface_name); 
		}

		if (impls==null) return null;

		String[] ret = new String[impls.length];
		for (int i=0;i<impls.length;i++) ret[i]=impls[i].getClassName();

		return ret;
	}

	public IMClass[] findImplements(String iface_name) {
		Hashtable impl = (Hashtable)impl_ifaces.get(iface_name);
		if (impl==null) return null;

		IMClass[] all = new IMClass[impl.size()];
		Enumeration e = impl.elements();
		for (int i=0;e.hasMoreElements();i++) { all[i] = (IMClass)e.nextElement(); }

		return all;
	}

	public IMClass[] getAllClasses() {
		IMClass[] all = new IMClass[classes.size()];

		Enumeration e = classes.elements();
		for (int i=0;e.hasMoreElements();i++) { all[i] = (IMClass)e.nextElement(); }
		
		return all;
	}

	public String toString() {
		return classes.toString();
	}

	/**
	 * Add all classfiles in the given directory and its subdirs to the repository
	 */
	private void addClassFiles(String path) throws IOException {
		try {
			File dir = new File(path);
			if (dir.isDirectory()) { 
				String dlist[] = dir.list();
				for (int e=0;e<dlist.length;e++) addClassFiles(path+"/"+dlist[e]);
			} else {
				if (path.endsWith(".class")) addClassFile(dir);
			}
		} catch (FileNotFoundException ex) {
			Debug.out.println(ex.toString());
			throw new IOException (ex.toString());
		}
	}


	/**
	 * Read the supplied classfile and add it to the repository.
	 */
	private void addClassFile(File file) throws IOException, FileNotFoundException {
		RandomAccessFile clazz_file = new RandomAccessFile(file, "r");

		ClassData clazz = new ClassData(clazz_file);
		String name = clazz.getClassName();
		IMClass imclass = new IMClass(opts,this,clazz);
		addClass(imclass, clazz.getSuperClassName(), clazz.getInterfaceNames());

		clazz_file.close();
	}

	private void addPredefinedClass(IMClass imclass) {
		addClass(imclass,"java/lang/Object",null);
	}

	public void addClass(IMClass imclass, String superclass, String[] ifaces) {
		String name = imclass.getClassName();

		if (classes.get(name)!=null) {
			try {
				throw new Error();
			} catch (Error err) {
				System.err.println(name+" load a second time!");
				err.printStackTrace();
			}
		}

		classes.put(name, imclass);

		// register subclass
		opts.vverbose("add class: "+name+" -> "+superclass);

		if (superclass==null) return;

		Hashtable subs = (Hashtable)sub_classes.get(superclass);
		if (subs==null) {
			subs = new Hashtable(5);
			sub_classes.put(superclass,subs);
		}

		subs.put(name,imclass);

		if (ifaces==null) return;

		for (int i=0;i<ifaces.length;i++) { 
			Hashtable impl = (Hashtable)impl_ifaces.get(ifaces[i]);
			if (impl==null) {
				impl = new Hashtable(5);
				impl_ifaces.put(ifaces[i],impl);
			}
			impl.put(name,imclass);
		}
	}

	public void requireSharedMemory() {
		if (opts.is8BitController()) {
			sharedMemorySize = 100;
		} else {
			//sharedMemorySize = 64*1024;
			sharedMemorySize = 4*1024*1024;
		}
	}

	private Hashtable used_methods = new Hashtable(); 
	private Hashtable method_to_class = new Hashtable();

	public IMClass requireClass(String className) {
		// TODO: domain support
		return requireClass(0,className);
	}

	/**
	 * Mark a class as required. This is used for class 
	 * elimination at the beginning.
	 *
	 * Die benoetigte Klasse und rekursive auch all Ihre Vorgaenger- und
	 * Interface-Klassen werden in den ClassStore uebernommen.
	 *
	 * Alle Methoden werden registriert und benutzte Methoden 
	 * geladen und untersucht.
	 */
	public IMClass requireClass(int domainID, String className) {

		assert_prune();

		IMClass req_class = getClass(className);

		if (current_src==null && req_class==null) {
			// Falls wir kein Source-Repository haben kann es sein
			// das wir eine Array-Klasse noch automatisch erzeugen muessen.
			if (className.charAt(0)=='[') {
				opts.verbose("## auto class "+className);
				String array_type = className.substring(1);
				IMClass elem_class = requireClass(domainID,array_type);
				addPredefinedClass(new IMObjectArrayClass(opts,this,className,
							elem_class,"object_t*",true));
				return getClass(className);
			}

			// ODER: Der Klassenname ist falsch, dann wollen wir mal nicht 
			// so sein. L...; -> ...
			if (className.charAt(0)=='L' && className.endsWith(";")) {
				className=className.substring(1,className.length()-1);
				req_class=getClass(className);
			}
		}

		try {
			if (req_class==null) {
				opts.verbose("#### require "+className);

				if (current_src==null) throw new Error("class "+className+" not found! (1)"); 


				IMClass src_class = current_src.getClass(className);
				if (src_class==null) throw new Error("class "+className+" not found! (2)"); 

				req_class = src_class.cloneClass(this);
				addClass(req_class, req_class.getSuperClassName(), req_class.getInterfaceNames());

				req_class.require(domainID);

				// Alle aus der Klassendatei bekannten Methoden der Klasse werden registriert
				// und Methoden die in Verwendung sind werden geladen.
				// isUsedMethod -> preParseMethodData
				req_class.collectMethodNames(domainID, method_to_class);
			}
		} catch (CompileException ex) {
			ex.printStackTrace();
			throw new Error("CompileException");
		}


		return req_class;
	}

	/**
	 * Mark a method as required. This is used for method 
	 * elimination at the beginning.
	 *
	 * Die Klasse der angegebenen Methode wird als benoetigt markiert
	 * und damit den moeglichen Typen im ClassStore hinzugefuehgt.
	 * 
	 * Anschliessend werden alle Methoden mit dem angebenen 
	 * Namen und Argumenten als moeglicherweise erreichbar angesehen.
	 *
	 * Der Bytecode all dieser Methoden wird "geladen" und 
	 * nach (erreichbaren) Methodenaufrufen und benoetigten Klassen
	 * untersucht. Wodurch sich der Vorgang rekursive wiederholt.
	 * 
	 * TODO: weiter Verbesserungen
	 *
	 * 	 - Die Domain-Zugehoerigkeit von Klassen beruecksichtigen.
	 * 	 
	 *       - Den Vorgang nach dem Entfernen von Methoden-Aufrufen
	 *	   durch In-Line-Expansion und Konstantenfaltung wiederholen.  
	 */
	public void requireMethod(int domainID, String className, String methodName) {

		// Hier wird die Klasse als erreichbar markiert und falls
		// noetig aus dem Source-Repository kopiert.
		//
		// Alle Methoden der Klasse werden dabei registriert und 
		// bereits erreichbare Methoden werden geladen und untersucht.
		IMClass req_class = requireClass(domainID,className);

		jps.require(domainID, className, methodName);

		// Nur wenn der Methodentyp noch nicht als erreichbar markiert ist
		// muessen wir die Methode explizit als erreichbar markieren.
		if (!isUsedMethod(domainID,req_class,methodName)) {
			// Die Methode als erreichbar markieren. 
			registerUsedMethod(domainID, req_class, methodName);

			// Alle Methoden von diesem Typ werden nun als erreichbar
			// betrachtet und muessen nun auch geladen.
			callbackUsedMethod(domainID, req_class, methodName);
		}
	}

	public void registerUsedMethod(int domainID, IMClass clazz, String methodName) {
		assert_prune();
		if (opts.hasOption("fast_prune")) {
			used_methods.put(methodName, methodName);
		} else {
			Vector types=(Vector)used_methods.get(methodName);
			if (types==null) { 
				types = new Vector();
				used_methods.put(methodName, types);
			}
			types.add(clazz);
		}
	}

	public boolean isUsedMethod(int domainID, IMClass method_clazz, String methodName) {
		if (opts.hasOption("fast_prune")) {
			return used_methods.get(methodName)!=null;
		} else {

			Vector types=(Vector)used_methods.get(methodName);
			if (types==null) return false;

			Enumeration e = types.elements();
			while (e.hasMoreElements()) {
				IMClass used_clazz = (IMClass)e.nextElement();

				/* we have to require the super class or typeOf won't work */
				if (method_clazz.getSuperClassName()!=null)
					requireClass(domainID,method_clazz.getSuperClassName());

				if (!method_clazz.typeOf(used_clazz)) continue;

				return true;
			}

			return false;
		}
	}

	final public void callbackUsedMethod(int domainID, IMClass method_clazz, String methodName) {
		Vector callback_classes = (Vector)method_to_class.get(methodName);
		if (callback_classes==null) {
			opts.warn("method not found "+methodName+" in class " + method_clazz.getClassName() + "!");
			return;
		}

		Enumeration e = callback_classes.elements();
		boolean fast_prune = opts.hasOption("fast_prune");

		for (int i=0;e.hasMoreElements();i++) {
			IMClass clazz = (IMClass)e.nextElement();
			if (fast_prune) {
				clazz.preParseRequiredMethodData(methodName);
			} else {
				if (!clazz.typeOf(method_clazz)) continue;
				clazz.preParseRequiredMethodData(methodName);
			}
		}
	}

	private void registerSystemServices(ClassStore repository) throws CompileException {
		Vector services = ServiceManager.instance.getServicesOnCurrentSystem();
		repository.systemServices = new IMPortalService[services.size()];
		for (int i = 0; i < services.size(); ++i) {
			ServiceDefinition srvDef = (ServiceDefinition) services.elementAt(i);
			opts.verbose("Creating service " + srvDef);
			repository.systemServices[i] = new IMPortalService(opts, repository, srvDef);
		} 
	}
   
	public void createPortalClasses() throws CompileException {
		opts.verbose("Creating portal classes");
		Vector imps = ServiceManager.instance.getImportsOnCurrentSystem();

		portalProxies = new IMPortalClass[imps.size()];
		for (int i = 0; i < imps.size(); ++i) {
			ImportDefinition impDef = (ImportDefinition) imps.elementAt(i);
			opts.verbose("Creating proxy for import " + impDef);
			IMPortalClass proxy = new IMPortalClass(opts, this, impDef);
			portalProxies[i]=proxy;
			addClass(proxy, "java/lang/Object", proxy.getInterfaceNames());
		}     			
	}
 
	/**
	 * Create a new ClassStore which only contains classes needed by methods
	 * which are reachable from the start method. The start methods are the 
	 * main methods of all tasks in all domains. (see requiredMethod and 
	 * requiredClass for details)
	 */
	public ClassStore getPrunedClassStore() throws CompileException {
		opts.verbose("##### prune class tree");
		ClassStore new_store = new ClassStore(opts, this);
		IMDomain[] doms = opts.getDomains();

		// TODO: add Domain support.
		int domainID=0;

		// all class constructors are auto required!
		new_store.registerUsedMethod(0, getRootClass(), "<clinit>()V");

		/* registerSystemServices(new_store); */
		for (int i = 0; i < systemServices.length; ++i) {
			systemServices[i].require(new_store);
		} 
		new_store.systemServices = systemServices;

		/* let the portal proxies servive */
		/* TODO: requireClass() in PortalService-Weavelet */
		for (int i = 0; i < portalProxies.length; ++i) {
			new_store.requireClass(domainID, portalProxies[i].getClassName());
		} 

		Iterator it = opts.getSysDef().getRequiredMethods();
		while (it.hasNext()) {
			FinalizeResult.ProtectedMethod pm = (FinalizeResult.ProtectedMethod) it.next();
			opts.verbose("Require Method: " + pm.className + "." + pm.method);

			if (pm.method==null)
				new_store.requireClass(domainID, pm.className);

			else new_store.requireMethod(domainID, pm.className, pm.method);
		}
        
		new_store.releaseSourceRepository();

		return new_store;
	}
	
	private int array_start;
	private int array_end;

	public void setArrayTypeRange(int start, int end) {
		array_start = start;
		array_end = end;
	}

	/**
	 * emitClassStore is only for debug
	 */
	public void emitClassStore(StringBuffer buf) {
		int keys[] = class_type_info.sortedKeys();

		if (opts.is8BitController() || opts.hasOption("compact_header")) {
			buf.append("typedef unsigned char  class_id_t;\n");
			buf.append("typedef unsigned short array_size_t;\n\n");
		} else {
			buf.append("typedef unsigned short class_id_t;\n");
			buf.append("typedef unsigned long  array_size_t;\n\n");
		}

		buf.append("#define OBJECT_HEADER \\\n");
		if (opts.is8BitController() || opts.hasOption("compact_header")) {
			if (opts.getGlobalHeap().needGCInfo())
				buf.append("	unsigned char gcinfo;\\\n");
		} else {
			buf.append("	unsigned char gcinfo;\\\n");
			buf.append("	unsigned char bitfld1;\\\n");
		}
		buf.append("	class_id_t class_id;\\\n");
		buf.append("\n");

		buf.append("typedef struct {\n");
		buf.append("OBJECT_HEADER\n");
		buf.append("} object_t;\n");
		buf.append("\n");
		buf.append("typedef struct {\n");
		buf.append("	class_id_t     type_range;\n");
		buf.append("	unsigned short size;\n");
		buf.append("	unsigned char ifaces;\n");
		buf.append("	unsigned char roff;\n");
		buf.append("} class_t;\n");

		buf.append("#define ARRAY_HEADER \\\n");
		buf.append("OBJECT_HEADER\\\n");
		buf.append("	array_size_t size;\n");

		int cls_i=0;
		for (;cls_i<keys.length;cls_i++) {
			ClassTypeInfo cinfo = (ClassTypeInfo)class_type_info.get(cls_i+1);
			IMClass imclass = cinfo.getIMClass();
			if (imclass.isInterface()) break;
			buf.append("#include \"");
			buf.append(imclass.getAlias());
			buf.append(".h\"\n");
		}

		buf.append("\nclass_t class_store[] = {\n");
		buf.append(" /* 0 RESERVED */\n");
		cls_i=0;
		for (;cls_i<keys.length;cls_i++) {
			ClassTypeInfo cinfo = (ClassTypeInfo)class_type_info.get(cls_i+1);
			IMClass imclass = cinfo.getIMClass();

			if (imclass.isInterface()) break;

			buf.append(" /* ");
			buf.append(cls_i+1);
			buf.append("\t");
			buf.append(imclass.getClassName());
			buf.append(" */\n");

			if (cinfo==null) {
				buf.append(" {},\n");
				continue;
			} 

			buf.append(" {");
			buf.append(".type_range=");
			buf.append(cinfo.getTypeRange());
			buf.append(", ");

			if (imclass!=null) {
				buf.append(".size=");
				buf.append(imclass.emitObjectSize());

				buf.append(", .ifaces=");
				buf.append((0xff & imclass.getInterfaceType()));

				buf.append(", .roff=");
				buf.append(imclass.getObjRefFieldCount());
			}
			buf.append("},\n");
		}

		buf.append("};\n");
		buf.append("#define CLASSSTORE_SIZE ");
		buf.append(cls_i);
		buf.append("\n");
		buf.append("#define CLASS(_id_) class_store[((_id_)-1)]\n");
		buf.append("#define CLS_ROFF(_cls_) CLASS(_cls_).roff\n");

		buf.append("\nchar *class_names[] = {\n");
		for (int i=0;i<keys.length;i++) {
			ClassTypeInfo cinfo = (ClassTypeInfo)class_type_info.get(i+1);
			IMClass imclass = cinfo.getIMClass();
			if (imclass.isInterface()) break;
			buf.append("\t\"");
			buf.append(imclass.getClassName());
			buf.append("\",\n");
		}

		buf.append("};\n");
	}

	/**
	 * translate all java classes into c
	 */
	public void translate(Coder coder, MethodTableFactory table, IntegerHashtable class_type_info) throws CompileException {

		this.class_type_info = class_type_info;

		IMClass imclasses[] = getAllClasses();

		boolean has_llrefs = opts.hasLinkedListOfLocalReferences();

		coder.global_header_add("/* adjust this value to your needs! */\n");
		coder.global_header_add("#define KESO_TICKS_MICRO 1995\n"); 

		int keys[] = class_type_info.sortedKeys();
		if (opts.is8BitController()) {
			coder.global_header_add("typedef unsigned char class_id_t;\n");
			coder.global_header_add("typedef unsigned char array_size_t;\n");
			coder.global_header_add("/* max. object size is 256 Byte */\n");
			coder.global_header_add("typedef unsigned char obj_size_t;\n\n");
			coder.global_header_add("#define OBJECT_HEADER \\\n");
			if (opts.getGlobalHeap().needGCInfo())
				coder.global_header_add("	unsigned char gcinfo;\\\n");
			coder.global_header_add("	class_id_t class_id;\\\n");

			if (keys.length>254) opts.critical("too many classes for object header!");
		} else if (opts.hasOption("compact_header")) {
			coder.global_header_add("typedef unsigned char  class_id_t;\n");
			coder.global_header_add("typedef unsigned short array_size_t;\n\n");
			coder.global_header_add("/* max. object size is 64 KByte */\n");
			coder.global_header_add("typedef unsigned short obj_size_t;\n\n");
			coder.global_header_add("/* compact object header (254 classes max.) */\n");
			coder.global_header_add("#define OBJECT_HEADER \\\n");
			if (opts.getGlobalHeap().needGCInfo())
				coder.global_header_add("	unsigned char gcinfo;\\\n");
			coder.global_header_add("	class_id_t class_id;\\\n");

			if (keys.length>254) opts.critical("too many classes for compact object header!");
		} else {
			coder.global_header_add("typedef unsigned short class_id_t;\n");
			coder.global_header_add("typedef unsigned long  array_size_t;\n\n");
			coder.global_header_add("/* max. object size is 64 KByte */\n");
			coder.global_header_add("typedef unsigned short obj_size_t;\n\n");

			coder.global_header_add("/* it is important for the IRR GC that\n");
			coder.global_header_add(" * bit 0 of the first word of the object\n");
			coder.global_header_add(" * header is set to 1, in order to distinguish\n");
			coder.global_header_add(" * it from object references. The irr-alloc sets\n");
			coder.global_header_add(" * bit 0 of gcinfo to 1 on object allocation. If\n");
			coder.global_header_add(" * you change the object header, be sure not to break\n");
			coder.global_header_add(" * this. Also the endianess is of importance - gcinfo\n");
			coder.global_header_add(" * must be the least sigificant byte of the header's\n");
			coder.global_header_add(" * first word. The following header is for little\n");
			coder.global_header_add(" * endian architectures (just reverse the element order\n");
			coder.global_header_add(" * for big endian architectures).\n");
			coder.global_header_add(" */\n");
			coder.global_header_add("#define OBJECT_HEADER \\\n");
			if (opts.getGlobalHeap().needGCInfo()) {
				coder.global_header_add("	unsigned char gcinfo;\\\n");
			} else {
				coder.global_header_add("	unsigned char bitfld2;\\\n");
			}
			coder.global_header_add("	unsigned char bitfld1;\\\n");
			coder.global_header_add("	class_id_t class_id;\\\n");
		}
		coder.global_header_add("\n");

		coder.global_header_add("typedef struct {\n");
		coder.global_header_add("OBJECT_HEADER\n");
		coder.global_header_add("} object_t;\n");
		coder.global_header_add("\n");


		boolean need_roff=false;
		IntegerHashtable roff_index = new IntegerHashtable();
		int cls_i=0;
		for (;cls_i<keys.length;cls_i++) {
			ClassTypeInfo cinfo = (ClassTypeInfo)class_type_info.get(cls_i+1);
			IMClass imclass = cinfo.getIMClass();
			if (imclass.isInterface()) break;
			if (imclass.getObjRefFieldCount()!=0) {
				roff_index.put(cls_i, imclass);
			}
		}

		coder.global_header_add("#define CLS_ROFF(_cls_) ");
		if (opts.hasOption("comp_cls_store") && roff_index.size()>1) {
			coder.global_header_add("keso_roff(_cls_)\n");
			coder.global_header_add("obj_size_t keso_roff(class_id_t cls)");
			if (opts.useGCCExtentions())
				coder.global_header_add(" __attribute__ ((const)) ");
			coder.global_header_add(";\n");
			coder.global_add("obj_size_t keso_roff(class_id_t cls) {\n");
			coder.global_add("\tswitch (cls) {\n");
			int k[] = roff_index.keys();
			for (int i=0;i<k.length;i++) {
				IMClass iclass = (IMClass)roff_index.get(k[i]);
				coder.global_add("\t\tcase ");
				coder.global_add(iclass.getClassTypeID());
				coder.global_add(": return ");
				coder.global_add(iclass.getObjRefFieldCount());
				coder.global_add(";\n");
			}
			coder.global_add("\t}\n");
			coder.global_add("\treturn 0;\n");
			coder.global_add("}\n");
		} else {
			if (roff_index.size()>1) { need_roff=true; }
			if (need_roff) {
				coder.global_header_add("CLASS(_cls_).roff\n");
			} else {
				if (roff_index.size()==1) {
					int k[] = roff_index.keys();
					IMClass iclass = (IMClass)roff_index.get(k[0]);
					coder.global_header_add("((_cls_)==");
					coder.global_header_add(iclass.getClassTypeID());
					coder.global_header_add("?");
					coder.global_header_add(iclass.getObjRefFieldCount());
					coder.global_header_add(":0)\n");
				} else {
					coder.global_header_add("(0)\n");
				}
			}
		}

		coder.global_header_add("typedef struct {\n");
		if (!opts.hasOption("inline_checkcast")) {
			coder.global_header_add("	class_id_t     type_range;\n");
		}
		//if (opts.getGlobalHeap().needObjSize() || opts.hasOption("no_inline_alloc")) {
		coder.global_header_add("	obj_size_t     size;\n");
		//}
		boolean iface_store = opts.hasOption("iface_store");
		if ((need_iface_checkcast || need_iface_instanceof) && !iface_store) {
			coder.global_header_add("/* class info extension for interfaces etc. */\n");
			coder.global_header_add("	unsigned char ifaces;\n");
		}
		if (need_roff) {
			coder.global_header_add("/* class info for bidirectional object layout */\n");
			coder.global_header_add("	unsigned char roff;\n");
		}
		coder.global_header_add("} class_t;\n\n");

		coder.global_header_add("#define ARRAY_HEADER \\\n");
		coder.global_header_add("OBJECT_HEADER\\\n");
		coder.global_header_add("	array_size_t size;\n\n");

		coder.global_header_add("typedef struct {\n");
		coder.global_header_add("ARRAY_HEADER\n");
		coder.global_header_add("} array_t;\n");
		coder.global_header_add("#define CLASS(_id_) class_store[((_id_)-1)]\n");
		coder.global_header_add("extern class_t	class_store[];\n");

		coder.global_header_add("/* KESO_DUP_OBJ_MAX is the maximum of recursion depth\n");
		coder.global_header_add(" * for object copy in portal calls */\n");
		coder.global_header_add("#ifndef KESO_DUP_OBJ_MAX\n");
		coder.global_header_add("#define KESO_DUP_OBJ_MAX 10\n");
		coder.global_header_add("#endif\n");

		coder.global_header_add("#define KESO_OBJ_PTR(_roff_, _mem_) (object_t*)((object_t**)(_mem_)+(_roff_))\n");
		coder.global_header_add("#define KESO_TOP_PTR(_roff_, _obj_) (jbyte*)((object_t**)(_obj_)-(_roff_))\n");
		coder.global_header_add("#define KESO_REF_PTR(_roff_, _obj_) (object_t**)((object_t**)(_obj_)-(_roff_))\n");

		if (opts.hasOption("omit_safechk")) {
			coder.global_header_add("#define KESO_OMIT_SAFECHECKS 1\n");
		}

		if (opts.hasOption("debug")) {
			coder.global_header_add("#define DEBUG 1\n");
		} else {
			if (opts.hasOption("production")) coder.global_header_add("#define KESO_PRODUCTION 1\n");
		}

		if (opts.isAVR() || opts.hasOption("no_write")) {
			coder.global_header_add("#define NO_WRITE 1\n");
		}

		if (statics_to_access.size()<20) {
			coder.global_header_add("\n/* static accesses */\n");
			Enumeration static_access = statics_to_access.keys();
			while (static_access.hasMoreElements()) {
				coder.global_header_add("/* ");
				coder.global_header_add(static_access.nextElement().toString());
				coder.global_header_add(" */\n");
			}
		}

		if (fields_to_access.size()<20) {
			coder.global_header_add("\n/* field accesses */\n");
			Enumeration field_access = fields_to_access.keys();
			while (field_access.hasMoreElements()) {
				coder.global_header_add("/* ");
				coder.global_header_add(field_access.nextElement().toString());
				coder.global_header_add(" */\n");
			}
		}

		for (int i=0;i<imclasses.length;i++) { imclasses[i].global_header(coder); }

		if (opts.hasOption("exceptions")) {
			coder.global_header_add("\n#define KESO_PENDING_EXCEPTION keso_pend_exception\n");
			coder.global_header_add("extern volatile object_t* keso_pend_exception;\n");
			coder.global_add("\nvolatile object_t* keso_pend_exception = ((object_t*)0);\n");
			coder.global_header_add("#define KESO_CALL_ERROR_HOOK() { exit(-1); }\n");
		} else {
			if (opts.isLinux()) {
				coder.global_header_add("\n#define KESO_PENDING_EXCEPTION keso_pend_exception\n");
				coder.global_header_add("extern volatile object_t* keso_pend_exception;\n");
				coder.global_add("\nvolatile object_t* keso_pend_exception = ((object_t*)0);\n");
			} else {
				coder.global_header_add("\n#define KESO_PENDING_EXCEPTION ((object_t*)0)\n");
			}
		}

		if (opts.useGCCExtentions()) {
			coder.global_header_add("\n#define likely(x) __builtin_expect((x),1)\n");
			coder.global_header_add("#define unlikely(x) __builtin_expect((x),0)\n");
			coder.global_header_add("#define NORETURN __attribute__((noreturn))\n");
			coder.global_header_add("#define ALIGN4 __attribute__ ((aligned (4)))\n");

			if (opts.isAVR()) {
				// put heap into normal data-section 
				coder.global_header_add("#define HEAP_SECTION __attribute__ ((section(\".data\")))\n");
			} else {
				coder.global_header_add("#define HEAP_SECTION __attribute__ ((section(\".objheap\")))\n"); 
			}

			if (opts.isTriCore()) {
				coder.global_header_add("#define TRICORE_PSPR_SECTION __attribute__ ((section(\".tricore_pspr\")))\n");
			} else {
				coder.global_header_add("/* PSPR section is TriCore architecture only -- ignore. */\n");
				coder.global_header_add("#define TRICORE_PSPR_SECTION\n");
			}

		} else {
			opts.warn("HEAP_SECTION is not defined");
			coder.global_header_add("\n#define likely(x) x\n");
			coder.global_header_add("#define unlikely(x) x\n");
			coder.global_header_add("#define HEAP_SECTION\n"); 
			coder.global_header_add("#define NORETURN\n");
			coder.global_header_add("#define ALIGN4\n");
			coder.global_header_add("#define TRICORE_PSPR_SECTION\n");
			coder.global_header_add("#define TRICORE_DSPR_SECTION\n");
		}

		coder.global_header_add("#define INLINE_ARRAY_CHK 1\n");

		coder.global_add_fkt("null_chk.c");
		coder.global_add_fkt("array_chk.c");
		coder.global_add_fkt("error_chk.c");

		if (sharedMemorySize>0) {
			opts.warn("Dynamic shared memory is not tested!");
			opts.warn("hint: use allocStaticMemory or allocStaticDeviceMemory!");
			coder.global_header_add("\n#define KESO_SHARED_MEM_SIZE ");
			coder.global_header_add(sharedMemorySize);
			coder.global_header_add("\n");

			if (opts.is8BitController()) {
				coder.global_header_add("\n#define KESO_MEM_NO_GC 1");
				coder.global_add_fkt("tiny_shared_memory.c");
			} else {

				/*
				 * TODO: less restrict locks 
				 * This locks may protect long periods of time while
				 * searching in the free list or doing garbage collection.
				 */
				opts.warn("FIXME: Dynamic shared memory uses sub optimal locking!");
				coder.global_header_add("#define LOCK_SHARED_MEMORY() SuspendAllInterrupts()\n");
				coder.global_header_add("#define UNLOCK_SHARED_MEMORY() ResumeAllInterrupts()\n");

				/*	
				 * This locks protect only short sections.
				 */
				coder.global_header_add("#define LOCK_SHARED_MEMORY_GQ() SuspendAllInterrupts()\n");
				coder.global_header_add("#define UNLOCK_SHARED_MEMORY_GQ() ResumeAllInterrupts()\n");

				coder.global_add_fkt("shared_memory.c");
			}
		}

		if (opts.hasOption("enable_pcp_emu")) {
			coder.global_header_add("extern unsigned int keso_pcp_counter;\n");
			coder.global_add("unsigned int keso_pcp_counter=0;\n");
			coder.global_header_add("#define KESO_LOCK(_obj_) do { if (!(keso_pcp_counter++)) GetResource(RES_SCHEDULER); } while (0)\n");
			coder.global_header_add("#define KESO_UNLOCK(_obj_) do { if (!(--keso_pcp_counter)) ReleaseResource(RES_SCHEDULER); } while (0)\n");
		} else {
			coder.global_header_add("#define KESO_LOCK(_obj_)\n");
			coder.global_header_add("#define KESO_UNLOCK(_obj_)\n");
		}

		IMClass obj_array = getClass("[Ljava/lang/Object;");
		int obj_array_id = 0;
		coder.global_header_add("\n#define keso_isObjectArrayClass(_class_id_) (");
		if (obj_array!=null) {
			obj_array_id = obj_array.getClassTypeInfo().getClassTypeID(); 
			coder.global_header_add(obj_array_id);
			coder.global_header_add("==(_class_id_))\n");
			coder.global_header_add("#define KESO_OBJECTARRAYCLASS_AVAILABLE 1\n");
		} else {
			coder.global_header_add("0)\n");
		}

		coder.global_header_add("#define keso_isArrayClass(_class_id_) (");
		if (array_start>1) {
			coder.global_header_add(array_start-1);
			coder.global_header_add("<(_class_id_)&&(_class_id_)<");
			coder.global_header_add(array_end);
			coder.global_header_add(")\n");
			coder.global_header_add("#define KESO_HAS_ARRAYS 1\n");
		} else {
			coder.global_header_add("0)\n");
		}

		IMClass mem_obj = getClass("keso/core/Memory");
		if (mem_obj!=null) {
			coder.global_header_add("\n#define KESO_MEMORYOBJECT_AVAILABLE\n");
			coder.global_header_add("#define KESO_MEMORY_ID ");
			coder.global_header_add(mem_obj.getClassTypeInfo().getClassTypeID());
			coder.global_header_add("\n#define keso_isMemoryClass(_class_id_) (");
			coder.global_header_add(mem_obj.getClassTypeInfo().getClassTypeID());
			coder.global_header_add("==(_class_id_))\n");
			coder.global_header_add("#define KESO_MEMORY_ADDR(_obj_) ");
			coder.global_header_add(coder.getField(mem_obj,"(_obj_)","addr"));
			coder.global_header_add("\n");
			coder.global_header_add("#define KESO_MEMORY_SIZE(_obj_) ");
			coder.global_header_add(coder.getField(mem_obj,"(_obj_)","size"));
			coder.global_header_add("\n");
		} else {
			coder.global_header_add("\n/* not available #define KESO_MEMORYOBJECT_AVAILABLE */\n");
			coder.global_header_add("#define keso_isMemoryClass(_class_id_) (0)\n");
			coder.global_header_add("#define KESO_MEMORY_ADDR(_obj_) (0)\n");
			coder.global_header_add("#define KESO_MEMORY_SIZE(_obj_) (0)\n");
		}

		coder.global_add("\n");

		if (need_iface_checkcast || need_iface_instanceof) {
			coder.getIFMatrix().emitInterfaceMatrix();
		}

		if (need_class_checkcast) {

			if (opts.hasOption("inline_checkcast")) {
				coder.global_header_add("#define keso_checkcast_fast(_class_id_, _obj_)");
				coder.global_header_add("(object_t*)(_class_id_!=(_obj_)->class_id ? KESO_THROW_ERROR(\"checkcast\") : _obj_)\n");

				coder.global_header_add("object_t *keso_checkcast(class_id_t class_id, class_id_t type_range, object_t* obj);\n");
				coder.global_add("object_t *keso_checkcast(class_id_t class_id, class_id_t type_range, object_t* obj) {\n");
				coder.global_add("\tregister class_id_t obj_type;\n");
				coder.global_add("\tif (obj==NULL) return obj;\n\n");
				coder.global_add("\tobj_type=obj->class_id;\n");
				coder.global_add("\tif (obj_type<class_id || obj_type>type_range) {\n");
				coder.global_add("\t\tKESO_THROW_ERROR(\"checkcast\");\n");
				coder.global_add("\t}\n");
				coder.global_add("\treturn obj;\n}\n\n");
			} else {
				coder.global_header_add("object_t *keso_checkcast_fast(class_id_t class_id, object_t* obj);\n");
				coder.global_add("object_t *keso_checkcast_fast(class_id_t class_id, object_t* obj) {\n");
				coder.global_add("\tif (class_id!=obj->class_id) KESO_THROW_ERROR(\"checkcast\");\n");
				coder.global_add("\treturn obj;\n}\n\n");

				coder.global_header_add("object_t *keso_checkcast(class_id_t class_id, object_t* obj);\n");
				coder.global_add("object_t *keso_checkcast(class_id_t class_id, object_t* obj) {\n");
				coder.global_add("\tregister class_id_t obj_type;\n");
				coder.global_add("\tregister class_id_t type_range;\n\n");
				coder.global_add("\tif (obj==NULL) return obj;\n\n");
				coder.global_add("\tobj_type=obj->class_id;\n");
				coder.global_add("\ttype_range = CLASS(class_id).type_range;\n");
				coder.global_add("\tif (obj_type<class_id || obj_type>type_range) {\n");
				coder.global_add("\t\tKESO_THROW_ERROR(\"checkcast\");\n");
				coder.global_add("\t}\n");
				coder.global_add("\treturn obj;\n}\n\n");
			}
		}

		if (need_class_instanceof) {
			coder.global_header_add("#define keso_instanceof_fast(_cid_,_obj_) ((_cid_)==(_obj_)->class_id)\n");
			if (opts.hasOption("inline_checkcast")) {
				coder.global_header_add("jboolean keso_instanceof(class_id_t class_id, class_id_t type_range, object_t* obj);\n");
				coder.global_add("jboolean keso_instanceof(class_id_t class_id, class_id_t type_range, object_t* obj) {\n");
				coder.global_add("	register class_id_t obj_type;\n");
				coder.global_add("	if (obj==NULL) return 0;\n\n");
				coder.global_add("	obj_type=obj->class_id;\n");
				coder.global_add("	if ( obj_type<class_id || obj_type>type_range ) return 0;\n\n");
				coder.global_add("	return 1;\n}\n\n");
			} else {
				coder.global_header_add("jboolean keso_instanceof(class_id_t class_id, object_t* obj);\n");
				coder.global_add("jboolean keso_instanceof(class_id_t class_id, object_t* obj) {\n");
				coder.global_add("	register class_id_t obj_type;\n");
				coder.global_add("	register class_id_t type_range;\n\n");
				coder.global_add("	if (obj==NULL) return 0;\n\n");
				coder.global_add("	obj_type=obj->class_id;\n");
				coder.global_add("	type_range = CLASS(class_id).type_range;\n");
				coder.global_add("	if ( obj_type<class_id || obj_type>type_range ) return 0;\n\n");
				coder.global_add("	return 1;\n}\n\n");
			}
		}

		coder.global_header_add("\ntypedef struct keso_stack_s {\n");
		coder.global_header_add("	int domain_id;\n");
		if (has_llrefs) coder.global_header_add("	object_t** llrefs;\n");
		coder.global_header_add("	struct keso_stack_s* next;\n");
		coder.global_header_add("} keso_stack_t;\n");

		if (has_llrefs) {
			coder.global_header_add("\n#define KESO_EOLL ((object_t*) -1) /* end of linked list */\n");
			/*
			 * Innerhalb einer Methode werden Objektreferenzen in einem Objekt-Array
			 * registriert, so ist es fuer den GC moeglich die lebenden Objekte beim 
			 * Stack-Scann sicher zu finden. Dieser Ansatz ist Architetkur unabhaengig
			 * und *sollte* mit jedem C-Compiler funktionieren. 
			 *
			 * Die Objekt-Arrays der einzelnen Methoden sind wiederrum zu einer
			 * Liste verlinkt. Ein Feld im Array kann folgende Inhalte besitzen. 
			 *
			 * 1. NULL (leer)
			 * 2. Zeiger auf ein Objekt (Objekt-Zeiger)
			 * 3. Zeiger auf das naechste Objekt-Array (Stack-Zeiger)
			 * 4. KESO_EOLL (end of linked list)
			 *
			 * GC muss beim Stack-Scann diese Inhalte untescheiden. Die Inhalte (1) und
			 * (4) sind dabei trivial. Fuer (2) und (3) ist das Makro keso_isStackRef()
			 * zustaendig. Hier gibt es zwei unterschiedliche Implementierungen. Entweder
			 * man kennt einen Speicherbereich in den nur ein Stack-Zeiger verweisen kann
			 * kann und kein Objekt-Zeiger, oder wir setzen das niederwertigste Bit in 
			 * allen Stack-Zeigern und pruefen dieses.
			 */
			if (!opts.useLLRefMarker()) {
				coder.global_header_add("extern object_t keso_heap_start;\n");
				coder.global_header_add("extern object_t keso_heap_end;\n");
				coder.global_header_add("extern object_t keso_stack_start;\n");
				coder.global_header_add("extern object_t keso_stack_end;\n");
				if (!opts.hasOption("debug")) {
					coder.global_header_add("#define keso_isStackRef(_r_) ");
					coder.global_header_add("(&(keso_stack_start)<=(_r_)&&(_r_)<&(keso_stack_end))\n");
				} else {
					coder.global_header_add("int keso_isStackRef(object_t* ref);\n");
					coder.global_add("int keso_isStackRef(object_t* ref) {\n");
					coder.global_add("\tif (&(keso_stack_start)<=ref && ref<&(keso_stack_end)) return 1;\n");
					coder.global_add("\treturn 0;\n");
					coder.global_add("}\n");
				}
				coder.global_header_add("#define keso_unpackStackRef(_ref_) (object_t**)(_ref_)\n");
			} else {
				if (opts.useLLRefMarker2()) {
					opts.warn("keso_isStackRef is using not tested llref marker version 2!");
					coder.global_header_add("#define KESO_FRAME_END_MARK ((object_t*) -2)\n");
					coder.global_header_add("#define keso_isStackRef(_ref_) ");
					coder.global_header_add("((_ref_)[1]==KESO_FRAME_END_MARK)\n");
					coder.global_header_add("#define keso_unpackStackRef(_ref_) (_ref_)\n");
				} else {
					opts.warn("keso_isStackRef is using stack refernce marker version 1.");
					coder.global_header_add("#define keso_isStackRef(_ref_) ");
					coder.global_header_add("((unsigned int)(_ref_)&0x1)\n");
					coder.global_header_add("#define keso_unpackStackRef(_ref_) ");
					coder.global_header_add("(object_t**)((unsigned int)(_ref_)-1)\n");
				}
			}
			coder.global_add_fkt("heap/gc-common-llrefs.c");
		}

		coder.global_header_add("\n/* static fields */\n");
		if (!opts.hasOption("no_static_gc")) {
			coder.global_header_add("#define KESO_NUM_STATIC_REFS ");
			coder.global_header_add(statics_id);
			coder.global_header_add("\n");
			Enumeration es = statics_to_index.elements();
			while (es.hasMoreElements()) {
				((IMField)es.nextElement()).global_emit_macro(coder);
			}
			coder.global_header_add("\n");
		} else {
			coder.global_header_add("#define KESO_NUM_STATIC_REFS 0\n");
			Enumeration es = statics_to_index.elements();
			while (es.hasMoreElements()) {
				((IMField)es.nextElement()).global_emit_macro(coder);
			}
			coder.global_header_add("\n");
		}

		coder.global_add("\n");

		// add required c functions

		boolean memCpy = false;
		boolean memRevCpy = false;
		boolean objCpy = false;

		Vector imports = ServiceManager.instance.getImportsOnCurrentSystem();
		for (int idx = 0; idx < imports.size(); ++idx) {
			ImportDefinition impDef = (ImportDefinition) imports.elementAt(idx);

			if (impDef.isLocal()) {
				if (!opts.isSingleDomainSystem()) {
					if (!memCpy) {
						coder.global_add_fkt("mem_copy.c");
						memCpy = true;
					}

					if (!objCpy) {
						coder.global_add_fkt("object_copy.c");
						objCpy = true;
					}
				}
			} else {
				if (!impDef.needDataConversion() && !memCpy) {
					coder.global_add_fkt("mem_copy.c");
					memCpy = true;
				}

				if (impDef.needDataConversion() && !memRevCpy) {
					coder.global_add_fkt("mem_revcopy.c");
					memRevCpy = true;
				}
			}
		}

		Vector services = ServiceManager.instance.getServicesOnCurrentSystem();
		for (int idx = 0; idx < services.size(); ++idx) {
			ServiceDefinition srvDef = (ServiceDefinition) services.elementAt(idx);

			if (!memCpy && srvDef.hasNetworkAccess()) {
				coder.global_add_fkt("mem_copy.c");
				memCpy = true;
			}
		}

		// translate services
		for (int srv = 0; srv < systemServices.length; ++srv) {     
			systemServices[srv].translate(coder);
		}

		table.translate(coder);	

		coder.global_add("\n");

		boolean fld_name = !opts.hasOption("c++");
		if ((need_iface_checkcast || need_iface_instanceof) && iface_store) {
			coder.global_header_add("extern unsigned char iface_store[];\n");
			coder.global_add("/* seperated interface type information. */\n");
			coder.global_add("unsigned char iface_store[] = {\n");
			cls_i=0;
			int if_start = -1;
			int if_end = -1;
			int if_skip = 0;
			for (;cls_i<keys.length;cls_i++) {
				ClassTypeInfo cinfo = (ClassTypeInfo)class_type_info.get(cls_i+1);
				IMClass imclass = cinfo.getIMClass();

				if (imclass.isInterface()) break;

				int if_type = imclass.getInterfaceType();
				if (if_start<0) {
					if (if_type==0) continue;
					if_start = cls_i+1;
				} else {
					if (if_type==0) {
						if_skip += 1;
						continue;
					}
				}

				while (if_skip>0) {
					coder.global_add("0,\n");
					if_skip--;
				}

				coder.global_add(" /* ");
				coder.global_add(cls_i+1);
				if (imclass!=null && opts.hasDbgSymbols()) {
					coder.global_add("\t");
					coder.global_add(imclass.getClassName());
				}
				coder.global_add(" */\n");

				coder.global_add((0xff & if_type));
				coder.global_add(",\n");
				if_end = cls_i+1;
			}
			coder.global_add("};\n\n");
			if (if_start>0) {
				coder.global_header_add("#define KESO_IFACE_START ");
				coder.global_header_add(if_start);
				coder.global_header_add("\n");
				coder.global_header_add("#define KESO_IFACE_SIZE ");
				coder.global_header_add((if_end-if_start)+1);
				coder.global_header_add("\n");
			} else {
				coder.global_header_add("#define KESO_IFACE_START 0\n");
				coder.global_header_add("#define KESO_IFACE_SIZE 0\n");
			}
		}

		coder.global_add("/* use the CLASS(id) macro to access the class store! */\n");
		coder.global_add("/* use the CLS_ROFF(id) macro to access the reference offset! */\n");
		coder.global_add("class_t class_store[] = {\n");

		coder.global_add(" /* 0 RESERVED */\n");
		cls_i=0;
		for (;cls_i<keys.length;cls_i++) {
			ClassTypeInfo cinfo = (ClassTypeInfo)class_type_info.get(cls_i+1);
			IMClass imclass = cinfo.getIMClass();

			if (imclass.isInterface()) break;

			coder.global_add(" /* ");
			coder.global_add(cls_i+1);
			if (imclass!=null && opts.hasDbgSymbols()) {
				coder.global_add("\t");
				coder.global_add(imclass.getClassName());
			}
			coder.global_add(" */\n");

			if (cinfo==null) {
				coder.global_add(" {},\n");
				continue;
			} 

			coder.global_add(" {");

			if (!opts.hasOption("inline_checkcast")) {
				if (fld_name)
					coder.global_add(".type_range=");
				coder.global_add(cinfo.getTypeRange());
				coder.global_add(", ");
			} else {
				coder.global_header_add("#define ");
				coder.global_header_add(imclass.getAlias().toUpperCase());
				coder.global_header_add("_RANGE ");
				coder.global_header_add(cinfo.getTypeRange());
				coder.global_header_add("\n");
			}

			if (imclass!=null) {

				//if (opts.getGlobalHeap().needObjSize() || opts.hasOption("no_inline_alloc")) {
				if (fld_name)
					coder.global_add(".size=");
				coder.global_add(imclass.emitObjectSize());
				//}

				if (!iface_store && (need_iface_checkcast || need_iface_instanceof)) {
					coder.global_add(", ");
					if (fld_name)
						coder.global_add(".ifaces=");
					coder.global_add((0xff & imclass.getInterfaceType()));
				}

				if (need_roff) {
					coder.global_add(", ");
					if (fld_name)
						coder.global_add(".roff=");
					coder.global_add(imclass.getObjRefFieldCount());
				}
			}
			coder.global_add("},\n");
		}

		coder.global_add("};\n");
		coder.global_header_add("#define KESO_CLASSSTORE_SIZE ");
		coder.global_header_add(cls_i-1);
		coder.global_header_add("\n");

		/* ASSERTCLASSID */
		coder.global_header_add("#define ASSERTCLASSID(_id_) KESO_ASSERT(0<(_id_) && (_id_)<");
		coder.global_header_add(cls_i);
		coder.global_header_add(")\n");

		if (opts.hasOption("debug")) {
			coder.global_header_add("#define ASSERTOBJ(_obj_) {");
			coder.global_header_add("if (_obj_!=NULL) ASSERTCLASSID((_obj_)->class_id); }\n");
		} else {
			coder.global_header_add("#define ASSERTOBJ(_obj_)\n");
		}

		for (int i=0;i<imclasses.length;i++) {
			IMClass imclass = imclasses[i];
			imclass.translate(coder);
		}

		emit_Strings(coder);

		Enumeration callback = translation_callback.elements();
		if (callback.hasMoreElements()) {
			coder.global_header_add("\n/* translation callbacks */\n");
			coder.global_add("\n/* translation callbacks */\n");
			while (callback.hasMoreElements()) {
				IMNode node = (IMNode)callback.nextElement();
				node.translate_global(coder);
			}
		}

	}

	private void releaseSourceRepository() {
		current_src=null;
	}

	public void assert_prune() {
		if (current_src!=null) return;
		if (opts.pruneClassTree()) {
			opts.warn("require class or method before or after prune.");
			if (opts.hasOption("dbg_prune")) throw new Error();
		}
	}

	public void resetFieldAccess() {
		statics_to_access = new Hashtable();
		fields_to_access = new Hashtable();
	}

	public void destroy() {
		classes = null;
		sub_classes = null; 
		impl_ifaces = null;
		rootClass = null;
		statics_to_index = null;
		statics_to_access = null;
		fields_to_access = null;
	}
}
