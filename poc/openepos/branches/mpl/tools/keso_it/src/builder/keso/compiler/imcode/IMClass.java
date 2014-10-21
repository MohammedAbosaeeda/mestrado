/**(c)

  Copyright (C) 2006 Christian Wawersich 

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

import keso.compiler.CompileException;
import keso.compiler.BuilderOptions;
import keso.compiler.ClassStore;
import keso.compiler.ClassTypeInfo;
import keso.compiler.backend.Coder;
import keso.compiler.kni.JoinPointChecker;

import keso.util.Debug; 
import keso.util.Bitmap; 
import keso.util.DecoratedNames;

import java.io.IOException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.LineNumberReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.File;

import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Vector;

public class IMClass extends IMModul {

	public final static IMClass[] NONE = new IMClass[0];

	private int field_counter=1;

	final protected ClassStore repository;
	final protected ClassData source;
	protected int interfaceType=0;
	protected boolean isFinal;

	protected JoinPointChecker joinPoints;
	protected IMClass super_class;
	protected IMClass[] sub_classes;
	protected IMClass[] impl_classes;
	protected IMClass[] ifaces;
	protected ClassTypeInfo type_info;
	protected int interfaceID = -1;

	protected IMMethod[] methods;
	protected Hashtable methods_index;

	protected int objRefFieldCount;

	protected Hashtable fieldsIndex   = null;
	protected Vector fieldsStaticRef  = null;
	protected Vector fieldsStaticPrim = null;
	protected Vector fieldsRef        = null;
	protected Vector fieldsMT         = null;
	protected Vector fieldsPrim       = null;

	/**
	 * constructor used to create an new class descriptor from a class file.
	 */
	public IMClass(BuilderOptions opts, ClassStore src_repository, ClassData clazz) {
		super(clazz.getClassName(),null,opts);
		this.joinPoints = opts.getJoinPoints();
		this.repository = src_repository;
		this.source     = clazz;
		this.isFinal    = clazz.isFinal();
		this.objRefFieldCount=-1;
	}

	public IMClass(BuilderOptions opts, ClassStore src_repository, String classname, String alias, boolean isFinal) {
		super(classname,alias,opts);
		this.joinPoints = opts.getJoinPoints();
		this.repository = src_repository;
		this.source     = null;
		this.isFinal    = isFinal;
		this.objRefFieldCount=-1;
	}

	public int getObjRefFieldCount() {

		if(objRefFieldCount>=0) return objRefFieldCount;

		if(!name.equals("java/lang/Object")) {
			if (fieldsRef==null) parseFields();
			IMClass sclass = getSuperClass();
			objRefFieldCount = sclass.getObjRefFieldCount() + fieldsRef.size();
		} else { // We have reached the top of the hierarchy
			this.objRefFieldCount = 0;
		}

		return objRefFieldCount;
	}

	/**
	 * copy class constructor. Used by the prune class tree algo. 
	 */
	public IMClass cloneClass(ClassStore src_repository) {
		if (source==null) { return new IMClass(opts,src_repository,name,alias,isFinal); } 
		return new IMClass(opts, src_repository, source);
	}

	public String getAlias() {
		if (alias==null) {
			if (type_info==null) {
				opts.verbose("no type_info yet for "+name);
			}
			alias = DecoratedNames.createClassAlias(opts,name,source,type_info);
		}
		return alias;
	}

	final public String getClassName() {
		return name;
	}

	final public ClassStore getClassStore() {
		return repository;
	}

	/**
	 * Inform the class that it is required and should look
	 * for it is needs.
	 */
	public void require(int domainID) {
		if (getSuperClassName()!=null)
			repository.requireClass(domainID,getSuperClassName());

		String[] if_names = getInterfaceNames();
		if (if_names!=null) 
			for (int i=0;i<if_names.length;i++)
				repository.requireClass(domainID,if_names[i]);
	}

	/**
	 * Mark a class in the repository as required. This is used for class 
	 * elimination at the beginning.
	 */
	final public void requireClass(int domainID, String className) {
		repository.requireClass(domainID, className);
	}

	/**
	 * Mark a method in the repository as required. This is used for method 
	 * elimination at the beginning.
	 */
	final public void requireMethod(int domainID, String className, String methodName) {
		repository.requireMethod(domainID, className, methodName);
	}

	/**
	 *
	 * overwritten in IMPortalClass
	 */
	public ConstantPool getConstantPool() {
		return source.getConstantPool();
	}

	final public BuilderOptions getOptions() {
		return opts;
	}

	final public JoinPointChecker getJoinPoints() {
		return joinPoints;
	}

	final public boolean hasSubClasses() {
		if (sub_classes!=null) return true;
		sub_classes = repository.getSubClasses(name);
		return sub_classes!=null; 
	}

	final public IMClass[] getSubClasses() {
		if (!hasSubClasses()) return null;
		return sub_classes; 
	} 

	final public IMClass getSuperClass() {
		if (super_class!=null) return super_class;
		String s_name = getSuperClassName();
		if (s_name==null) return null;
		super_class = repository.getClass(s_name);
		return super_class;
	}

	public String getSuperClassName() {
		if (source==null) return null;
		return source.getSuperClassName();
	}

	/*
    public void addInterfaceToPortalProxy(IMClass iface, IMClass service_class) {
		throw new Error(" type mishmash "+name);
	}
    */

	/**
	 * Test if this class is sub type of @iface.
	 */
	final private boolean isSubInterface(IMClass iface) {
		IMClass parent = getSuperClass();

		if (parent==null) return false;

		if (parent.equals(iface)) return true;

		if (parent.implementsInterface(iface)) return true;
		
		return false;
	}

	final protected void resolveInterfaces() {
		String[] ifs = getInterfaceNames();
		if (ifs==null) {
			ifaces=NONE;
		} else {
			ifaces = new IMClass[ifs.length];
			for (int i=0;i<ifaces.length;i++) {
				IMClass iclass = repository.getClass(ifs[i]);
				if (iclass==null) throw new Error(ifs[i]+" not found!");
				ifaces[i] = iclass;
			} 
		}
	}

	/**
	 * Test if this class is implementing this interface.
	 */
	final public boolean implementsInterface(IMClass iface) {

		if (isSubInterface(iface)) return true;

		if (ifaces==null) resolveInterfaces();

		for (int i=0;i<ifaces.length;i++) { 
			if (ifaces[i].typeOf(iface)) return true;
		}

		return false;
	}

	/**
	 * Test if this class is sub class of the class clazz.
	 */
	final public boolean isSubClassOf(IMClass clazz) {
		IMClass parent = getSuperClass();

		if (clazz.equals(repository.getRootClass())) return true;

		while (parent!=null) {
			if (parent.equals(clazz)) return true;
			parent = parent.getSuperClass();
		}

		return false;
	}
	
	/**
	 * Test if this class is sub class of the class clazz.
	 */
	final public boolean isSubClassOf(String className) {
		IMClass parent = getSuperClass();

		if (className.equals("java/lang/Object")) return true;

		while (parent!=null) {
			if (parent.name.equals(className)) return true;
			parent = parent.getSuperClass();
		}

		return false;
	}

	/**
	 * Test if the current class is type of the class <clazz>.
	 */
	final public boolean typeOf(IMClass clazz) {

		if (equals(clazz)) return true;

		if (clazz.isInterface()) {
			if (implementsInterface(clazz)) return true;
		} 

		if (isSubClassOf(clazz)) return true;

		return false;
	}

	final public boolean isMemoryType() {
		IMClass clazz = findClass("keso/core/MT");
		if (clazz==null) return false;
		return typeOf(clazz);
	}

	final public IMClass findClass(String className) {
		IMClass cls = repository.getClass(className);
		return cls; 
	}

	public String getSourceFile() throws CompileException {
		return name.substring(0,name.lastIndexOf('/')+1)+source.getSourceFileAttribute();
	}

	private String[] src_cache;
	public String readSourceLine(int line) throws CompileException {
		try {
			if (src_cache==null) {
				src_cache = new String[512];
				String src_file = getSourceFile();
				File sfile = new File(opts.getOutputPath()+"/src/"+src_file);
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(
							new FileInputStream(sfile)));
				String src_line=null;
				int pos = 0;
				while ((src_line=reader.readLine())!=null) {
					if ((pos+1)==src_cache.length) {
						String[] osrc = src_cache;
						src_cache = new String[src_cache.length+32];
						for (int i=0;i<osrc.length;i++) src_cache[i]=osrc[i];
					}
					src_cache[pos++]=src_line;
				}
				reader.close();
			}
		} catch (IOException ex) {
			opts.vwarn("ignore IOException\n");
			opts.verbose(ex);
		}

		if (line<src_cache.length) return src_cache[line-1];
		return "";
	}

	/**
	 * don`t use this method. use getMethod().
	 */
	public boolean hasSourceMethod(String nameAndType) {
		MethodData[] method_src = getMethodData();
		for (int i=0;i<method_src.length;i++) {
			if (method_src[i].getMethodNameAndType().equals(nameAndType)) return true;
		}
		return false;
	}

	public IMMethod getMethod(String nameAndType) {
		if (methods_index==null) parseMethodData();
		IMMethod m = (IMMethod)methods_index.get(nameAndType);

		IMClass sclass = null;
		if (m==null && ((sclass=getSuperClass())!=null)) {
			m=sclass.getMethod(nameAndType);
			if (m!=null && m.isPrivate()) m=null;
		}

		return m; 
	}

	public IMMethod[] getMethods() {
		if (methods_index==null) parseMethodData();
		return methods;
	}

	final public void setClassTypeInfo(ClassTypeInfo type_info) throws CompileException {	
		this.type_info = type_info;
		if (!isFinal) isFinal = type_info.isFinal();
	}

	final public ClassTypeInfo getClassTypeInfo() { return type_info; }

	final public int getInterfaceType() { return interfaceType; }	

	public boolean isInterface() { return source.isInterface(); }

	public boolean isAbstract() { return source.isAbstract(); }

	public boolean isFinal() {
		if (isFinal) return true;
		if (!hasSubClasses()) return true;
		return false;
	}

	/**
	 * is overwritten in IMPortalClass
	 */
	public String[] getInterfaceNames() {
		if (source==null) return null;
		String[] ifs = source.getInterfaceNames();
		if (ifs==null || ifs.length==0) return null;
		return ifs;
	}

	public IMClass[] getInterfaces() throws CompileException {
		String[] _ifaces = getInterfaceNames();

		Vector all = new Vector();
		for (int i=0;_ifaces!=null && i<_ifaces.length;i++) { 
			IMClass iface = repository.getClass(_ifaces[i]);
			all.addElement(iface);
			iface.collectInterfaces(all);
		}

		if (getSuperClass()!=null) {
			IMClass super_ifaces[] = getSuperClass().getInterfaces();
			for (int i=0;super_ifaces!=null && i<super_ifaces.length;i++)
				all.addElement(super_ifaces[i]);
		}

		if (all.size()==0) return null;
		IMClass if_all[] = new IMClass[all.size()];
		for (int i=0;i<all.size();i++) if_all[i]=(IMClass)all.elementAt(i);

		return if_all;
	}

	private void collectInterfaces(Vector all) throws CompileException {
		if (!isInterface()) throw new CompileException();

		String sclass = source.getSuperClassName();
		IMClass siface = repository.getClass(sclass);
		if (siface.isInterface()) {
			all.addElement(siface);
			siface.collectInterfaces(all);
		}

		String[] _ifaces = source.getInterfaceNames();
		if (_ifaces==null || _ifaces.length==0) return;

		for (int i=0;i<_ifaces.length;i++) { 
			IMClass iface = repository.getClass(_ifaces[i]);
			all.addElement(iface);
			iface.collectInterfaces(all);
		}
	}

	final public void setInterfaceID(int id) {
		interfaceID=id;
	}

	final public int getInterfaceID() throws CompileException {
		if (!isInterface()) throw new CompileException();
		return interfaceID;
	}

	final public int getClassTypeID() {
		return type_info.getClassTypeID();
	}

	final public int uniqFieldID() {
		return field_counter++;
	}

	/**
	 * Returns the name for the class ID.
	 *
	 * The ID is defined in the class header file and stored in
	 * every object header. 
	 */
	final public String getClassID() throws CompileException {
		if (type_info==null) throw new CompileException("no type info for "+name);
		return alias.toUpperCase()+"_ID";
	}

	final public String getClassStaticTypeString() {
		return alias+"_s";
	}

	final public String getClassStaticTypeDefine() {
		return getClassStaticTypeString().toUpperCase();
	}

	final public String getClassTypeString() {
		return alias+"_t";
	}

	public String emitObjectSize() {
		return "sizeof("+alias+"_t)";
	}

	final public String getClassTypeDefine() {
		return getClassTypeString().toUpperCase();
	}

	final public String getClassStaticsString() {
		return alias+"_statics";
	}

	/**
	 * Is this class is a interface all classes implementing this
	 * interface are retruned
	 */
	final public IMClass[] getImplementation() {
		if (impl_classes!=null) return impl_classes;
		if (!isInterface()) return null;
		impl_classes = repository.findImplements(name);	
		return impl_classes; 
	}

	public void global_header(Coder coder) throws CompileException {
		coder.global_add("#include \"");
		coder.global_add(getAlias());
		coder.global_add(".h\"\n");

		if (isInterface()) {
			/* CHK_IFACE_-Makro -> class header */
		} else {
			IMClass[] _ifaces = getInterfaces();
			if (_ifaces!=null) {
				Bitmap bitmap = new Bitmap(repository.getMaxInterfaceID()+1);  

				for (int i=0;i<_ifaces.length;i++) {
					bitmap.mark(_ifaces[i].getInterfaceID());
				}

				interfaceType = coder.getIFMatrix().registerInterfaceTypeInfo(bitmap);
			}
		}
	}

	public void inlineMethodCalls() throws CompileException {
		if (methods==null) parseMethodData();
		opts.vverbose("###### inline method calls "+name);
		for (int i=0;i<methods.length;i++) { 
			opts.vverbose("### "+methods[i].getMethodName()+" "+methods[i].getAlias());
			methods[i].inlineMethodCalls();
		}
	}

	public boolean performConstantFolding() throws CompileException {
		if (methods==null) parseMethodData();
		opts.verbose("###### constant folding "+name);

		opts.setCurrentClass(getClassName(),getClassTypeID(),"ssa");

		boolean run = false;
		for (int i=0;i<methods.length;i++) { 
			if (opts.hasOption("v_method")) {
				opts.verbose("### "+methods[i].getMethodName()+" "+methods[i].getAlias());
			}
			IMMethod m = methods[i];
			try {
				if (m.constant_folding()) run=true;
			} catch (Exception ex) {
				System.err.println(ex+" in: ");
				System.err.println(m.getMethodNameAndType()+" "+m.getAlias());
				ex.printStackTrace();
			}
		}

		opts.setCurrentClass("xxx",0,null);

		return run;
	}

	public void resetCallGraph() throws CompileException {
		opts.vverbose("#### reset call graph "+getAlias());
		for (int i=0;i<methods.length;i++) { methods[i].resetCallerAnalyse(); }
	}

	public void analyseCallGraph() throws CompileException {
		opts.vverbose("#### analyse call graph "+getAlias());
		if (methods==null) parseMethodData();

		IMCallGraphVisitor visitor = new IMCallGraphVisitor(opts);
		for (int i=0;i<methods.length;i++) { 
			IMMethod m = methods[i];
			opts.vverbose("### "+m.getMethodNameAndType()+" "+m.getAlias());
			if (m.getMethodNameAndType().equals("<clinit>()V")) {
				m.calledFrom(null,null);
			}
			m.analyseCallGraph(visitor);
		}
	}

	public void omitUnusedMethods() throws CompileException {
		opts.vverbose("#### omit unused methods "+getAlias());

		IMMethod[] used = new IMMethod[methods.length];
		int mc = 0;
		for (int i=0;i<methods.length;i++) {
			if  (methods[i].unused()) continue;
			used[i] = methods[i];
			mc++;
		}

		methods = new IMMethod[mc];
		methods_index = new Hashtable();
		mc=0;
		for (int i=0;i<used.length;i++) {
			IMMethod method = used[i];
			if (method==null) continue;
			methods[mc++] = method;
			methods_index.put(method.getMethodNameAndType(), method);
		}

		return;
	}

	public IMField getField(FieldRefCPEntry cpEntry) throws CompileException {
		return getField(cpEntry.getClassName(), cpEntry.getMemberName());
	}

	public IMField getField(String field_cls, String field_name) throws CompileException {

		IMClass field_class = this;

		if (!field_cls.equals(name)) {
			field_class = findClass(field_cls);
			if (field_class==null) throw new CompileException("Class "+field_cls+" not found!");
		}

		IMField field = field_class.getField(field_name);
		if (field==null) {
			throw new CompileException("Class "+field_cls+"."+field_name+" not found!");
		}


		return field;
	}

	private IMField getField(String fname) throws CompileException {
		if (fieldsStaticRef==null) parseFields();
		IMField f = (IMField)fieldsIndex.get(fname);

		IMClass sclass = null;
		if (f==null && ((sclass=getSuperClass())!=null)) {
			f=sclass.getField(fname);
			if (f!=null && f.isPrivate()) f=null;
		}

		IMClass[] _ifaces = getInterfaces();
		if (f==null && _ifaces!=null) {
			for (int i=0;i<_ifaces.length;i++) {
				f=_ifaces[i].getField(fname);
				if (f!=null) break;
			}
		}

		return f;
	}

	public String emitField(String fieldname) throws CompileException {
		IMField ofield = getField(fieldname);
		if (ofield==null) {
			IMClass sclass = getSuperClass();
			if (fieldname.equals("base")&&fieldsMT.size()>0) {
				// TODO: HACK: move this code into the Weavelet!
				return "base";
			}
			if(name.equals("java/lang/Object") || sclass==null) {
				if (opts.hasVerbose()) opts.warn("unknown field "+fieldname+" ");
				return "_"+fieldname; 
			}
			return sclass.emitField(fieldname);
		}
		return ofield.getAlias();
	}

	private void parseFields() { 
		FieldData[] fields = source.getFields();
		fieldsStaticPrim  = new Vector();
		if (opts.hasOption("no_static_gc")) {
			fieldsStaticRef = fieldsStaticPrim;
		} else {
			fieldsStaticRef = new Vector();
		}
		fieldsRef         = new Vector(); 
		fieldsMT          = new Vector(); 
		fieldsPrim        = new Vector();
		fieldsIndex	  = new Hashtable();

		for (int i=0;i<fields.length;i++) {
			FieldData field = fields[i];
			IMField nfield = new IMField(opts, this, field);
			//fieldsIndex.put(nfield.getPrivateFieldName(), nfield);
			
			fieldsIndex.put(nfield.getFieldName(), nfield);

			if (nfield.isReference()) {
				IMClass refClass = findClass(nfield.getRefClassName());
				if (refClass!=null && refClass.isMemoryType()) {
					// TODO: test class type!!
					fieldsMT.add(nfield);
					continue;
				}
				if (nfield.isStatic()) {
					fieldsStaticRef.add(nfield);
					continue;
				}
				fieldsRef.add(nfield);
			} else {
				if (nfield.isStatic()) {
					if (nfield.isFinal() && !opts.hasOption("dont_rm_final_fields")) continue;
					fieldsStaticPrim.add(nfield);
					continue;
				}
				fieldsPrim.add(nfield);
			}
		}
	}

	public boolean hasBaseField() {
		if (fieldsMT.size()>0) return true;
		if (name.equals("java/lang/Object")) return false;
		return getSuperClass().hasBaseField();
	}

	public void translate(Coder coder) throws CompileException {

		if (methods==null) parseMethodData();

		opts.verbose("###### compile "+name);

		coder.beginClass(this);

		coder.header_add("\n#define ");
		coder.header_add(alias.toUpperCase());
		coder.header_add("_ID ((class_id_t)");
		coder.header_add(type_info.getClassTypeID());
		coder.header_add(")\n");

		coder.header_add("#define ");
		coder.header_add(alias.toUpperCase());
		coder.header_add("_OBJ(_obj_) ((");
		coder.header_add(getClassTypeString());
		if (getObjRefFieldCount()!=0) {
			coder.header_add("*)((object_t**)(_obj_)-");
			coder.header_add(getObjRefFieldCount());
		} else {
			coder.header_add("*)(_obj_");
		}
		coder.header_add("))\n");

		if (isInterface()) coder.getIFMatrix().emitCheckInterfaceMakro(this, type_info); 

		coder.header_add("\n/* class methods prototypes */\n");
		for (int i=0;i<methods.length;i++) {
			IMMethod m = methods[i];
			try {
				opts.verbose("#### "+m.getMethodName()+" "+m.getAlias());
				m.translate(coder);
				if (m.getMethodNameAndType().equals("<clinit>()V")) {
				       	coder.registerClassInit(m);
			       	}
			} catch (RuntimeException ex) {
				System.err.println(ex+" in: ");
				System.err.println(m.getMethodNameAndType()+" "+m.getAlias());
				throw ex;
			}
		}

		if (source!=null) {
			if (fieldsStaticRef==null) parseFields();

			if (fieldsStaticPrim.size()>0) {
				coder.header_add("\n/* static class data */\n");
				coder.header_add("typedef struct {\n");

				/*
				 * All non-reference statics are put in this structure.
				 * For the references, a global class-spanning array will
				 * be generated which will make it easy for the GC to run
				 * over all static references.
				 */
				Enumeration fields = fieldsStaticPrim.elements();
				while (fields.hasMoreElements()) {
					IMField imfield = (IMField)fields.nextElement();
					imfield.header_emit(coder);
				}

				coder.header_add("} ");
				coder.header_add(getClassStaticTypeString());
				coder.header_add(";\n\n");

				IMDomain[] doms = opts.getDomains(); 

				coder.header_add("extern ");
				coder.header_add(getClassStaticTypeString());
				coder.header_add(" ");
				coder.header_add(getClassStaticsString());

				coder.global_add(getClassStaticTypeString());
				coder.global_add(" ");
				coder.global_add(getClassStaticsString());

				if (opts.isSingleDomainSystem()) {
					coder.header_addln(";\n");
					coder.global_add(";\n\n");
				} else {
					coder.header_addln("[];\n");
					coder.global_add("[");
					coder.global_add(doms.length);
					coder.global_add("];\n");
				}
			}

			coder.header_add("\n/* object data */\n");

			coder.header_add("typedef struct {\n");
			coder.header_add(createObjectFields(coder, new StringBuffer()));
			coder.header_add("} ");
			coder.header_add(getClassTypeString());
			coder.header_add(";\n");

			if (fieldsMT.size()>0) {
				coder.header_add("\n/* struct for memory types */\n");

				coder.header_add("#define ");
				coder.header_add(alias.toUpperCase());
				coder.header_add("_MT(_obj_) ((");
				coder.header_add(alias);
				coder.header_add("_mt*)(");
				coder.header_add(alias.toUpperCase());
				coder.header_add("_OBJ(_obj_)->base))\n");

				coder.header_add("typedef struct {\n");

				Enumeration fields = fieldsMT.elements();
				while (fields.hasMoreElements()) {
					IMField imfield = (IMField)fields.nextElement();
					imfield.header_emit_mt(coder);
				}

				coder.header_add("} ");
				coder.header_add(alias);
				coder.header_add("_mt;\n");
			}
		} else {
			coder.header_add("/* object data */\n");

			coder.header_add("typedef struct {\n");
			coder.header_add("OBJECT_HEADER\n");
			coder.header_add("} ");
			coder.header_add(getClassTypeString());
			coder.header_add(";\n");

		}

		coder.endClass();
	}

	protected StringBuffer createObjectFields(Coder coder, StringBuffer buffer) throws CompileException {

		if (fieldsRef==null) parseFields();

		// print reference fields
		Enumeration fields;

		buffer.append("/* "+getAlias()+" */\n");
		fields = fieldsRef.elements();
		while (fields.hasMoreElements()) {
			IMField imfield = (IMField)fields.nextElement();
			imfield.emit(buffer);
		}

		if (!name.equals("java/lang/Object")) {
			IMClass sclass = getSuperClass();
			sclass.createObjectFields(coder, buffer);
			if (fieldsMT.size()>0 && !sclass.hasBaseField()) {
				buffer.append("\tvoid* base;\n");
			}
		} else { // We have reached the top of the hierarchy
			buffer.append("OBJECT_HEADER\n");
		}


		// print non-reference fields
		fields = fieldsPrim.elements();
		while (fields.hasMoreElements()) {
			IMField imfield = (IMField)fields.nextElement();
			imfield.emit(buffer);
		}
	 	
		joinPoints.addFields(this, coder, buffer);

		buffer.append("/* "+getAlias()+" */\n");

		return buffer;
	}

	public void dumpBC() throws IOException {
		if (methods==null) parseMethodData();
		for (int i=0;i<methods.length;i++) { methods[i].dumpBC(); }
	}

	public void writeUMLDiagram() throws IOException {
		opts.verbose("###### write Classes as TeX "+name);
		if (methods==null) parseMethodData();

		String esc_classname = DecoratedNames.toEscString(name);

		String filename = opts.getOutputPath()+"/UML_"+esc_classname+".tex";
		PrintStream out = new PrintStream(new FileOutputStream(filename));

		out.println("\\documentclass[a4paper,14pt]{book}");

		out.println("\\usepackage{geometry}");
		out.println("\\usepackage{tabularx}");
		out.println("\\begin{document}");

		out.print("%% UML Diagram ");
		out.println(name);
		out.println("\\begin{tabular}{|l|}");	
		out.println("\\hline");
		String cname = DecoratedNames.baseName(name);
		out.print(DecoratedNames.toEscTexString(cname));
		out.println(" \\\\");
		out.println("\\hline");
		FieldData[] fields = source.getFields();
		if (fields.length==0) {
			out.println(" \\\\");
		} else {
			for (int i=0;i<fields.length;i++) {
				FieldData field = fields[i];
				out.print(field.getType().charAt(0));
				out.print(" ");
				out.print(DecoratedNames.toEscTexString(field.getName()));
				out.println(" \\\\");
			}
		}
		out.println("\\hline");
		for (int i=0;i<methods.length;i++) { 
			//opts.vverbose("#### "+methods[i].getMethodName()+" "+methods[i].getAlias());
			methods[i].writeUMLDiagram(out);
		}
		out.println("\\hline");
		out.println("\\end{tabular}");
		out.println("\\end{document}");

		out.flush();

		out.close();
	}

	public void writeCFG() throws IOException, CompileException {
		opts.verbose("###### write CFG "+name);
		if (methods==null) parseMethodData();
		for (int i=0;i<methods.length;i++) { 
			opts.vverbose("#### "+methods[i].getMethodName()+" "+methods[i].getAlias());
			methods[i].writeCFG();
		}
	}

	public void load() throws IOException, CompileException {
		if (methods==null) parseMethodData();
	}

	public void writeDomTree() throws IOException, CompileException {
		if (methods==null) parseMethodData();
		opts.verbose("###### write dominator trees "+name);
		for (int i=0;i<methods.length;i++) { 
			opts.vverbose("#### "+methods[i].getMethodName()+" "+methods[i].getAlias());
			methods[i].writeDomTree();
		}
	}

	private Hashtable pre_parsed_methods = null;

	protected void parseMethodData() {
		try {
			if (pre_parsed_methods!=null) {
				methods = new IMMethod[pre_parsed_methods.size()];
				methods_index = new Hashtable();

				Enumeration e = pre_parsed_methods.elements();
				for (int i=0;e.hasMoreElements();i++) {
					IMMethod method = (IMMethod)e.nextElement();
					methods[i] = method;
					methods_index.put(method.getMethodNameAndType(), method);
				}
			} else {
				opts.setCurrentClass(name,0,"parse");
				opts.vverbose("###### parse "+name);

				MethodData[] method_src = getMethodData();
				methods = new IMMethod[method_src.length];
				methods_index = new Hashtable();

				for (int i=0;i<method_src.length;i++) {
					opts.vverbose("#### "+method_src[i].getMethodNameAndType());
					methods[i] = createIMMethod(method_src[i]);
					methods_index.put(method_src[i].getMethodNameAndType(),methods[i]);
				}
				opts.setCurrentClass("xxx",0);
			}
		} catch (CompileException ex) {
			ex.printStackTrace();
			throw new Error("CompileException");
		}

	}

	/**
	 * This method collect all method names and register the class in
	 * method_to_class as implementing class.
	 */
	public void collectMethodNames(int domainID, Hashtable method_to_class) throws CompileException {

		// register all methods of this class as possible existing implementations
		MethodData[] method_src = getMethodData();
		if (method_src.length==0) return;

		for (int i=0;i<method_src.length;i++) {
			String req_method = method_src[i].getMethodNameAndType();

			// register class for all it's methods
			Vector classes = (Vector)method_to_class.get(req_method);
			if (classes==null) {
				classes = new Vector();
				method_to_class.put(req_method,classes);
			}

			classes.add(this);
		}

		// pre parse all allready requierd methods
		if (pre_parsed_methods==null) pre_parsed_methods = new Hashtable(); 
		for (int i=0;i<method_src.length;i++) {
			String req_method = method_src[i].getMethodNameAndType();
			if (repository.isUsedMethod(domainID, this, req_method)) {
				preParseMethodData(method_src[i]);
			}
		}
	}

	private void preParseMethodData(MethodData method_src) throws CompileException {
		String req_method = method_src.getMethodNameAndType();
		if (pre_parsed_methods.get(req_method)==null) {
			opts.vverbose("## pre-parse "+name+"."+req_method);
			pre_parsed_methods.put(req_method, createIMMethod(method_src));
		}
	}

	public void preParseRequiredMethodData(String methodName) {
		if (pre_parsed_methods.get(name)!=null) return; 

		MethodData[] method_src = getMethodData();
		for (int i=0;i<method_src.length;i++) {
			if (!method_src[i].getMethodNameAndType().equals(methodName)) continue;
			try {
				preParseMethodData(method_src[i]);
			} catch (CompileException ex) {
				ex.printStackTrace();
				throw new Error("CompileException");
			}
			return;
		}
	}

	public boolean equals(Object clazz) {
		if (this==clazz) return true;

		if (clazz instanceof IMClass) {
			if (((IMClass)clazz).getClassName().equals(name)) return true;
		}

		return false;
	}

	public int hashCode() {
		return name.hashCode();
	}

	/**
	 * This helper is overwritten in IMPortalClass. IMPortalClass has
	 * no java sources. It is created on the fly, like arrays. 
	 */
	public MethodData[] getMethodData() {
		return source.getMethodData();
	}

	/**
	 * This helper is overwritten in IMPortalClass. IMPortalClass has
	 * no java sources. It is created on the fly, like arrays. 
	 */
	protected IMMethod createIMMethod(MethodData source) throws CompileException {
		return new IMMethod(this,source);
	}

	public void dumpClass() {

		//System.err.println("Store     : "+repository);
		System.err.println("Class     : "+name);
		System.err.println("Alias     : "+getAlias());
		System.err.println("ClassData : "+source);
		System.err.println("iface Type: "+interfaceType);
		System.err.println("final     : "+isFinal);
		System.err.println("SuperClass: "+super_class);
		System.err.println("SubClasses: ");

		if (sub_classes!=null) {
			for (int i=0;i<sub_classes.length;i++) {
				System.err.println(sub_classes[i]);
			}
		} else {
			System.err.println("\tnone");
		}

		System.err.println("Impl.     : ");
		if (impl_classes!=null) {
			for (int i=0;i<impl_classes.length;i++) {
				System.err.println(impl_classes[i]);
			}
		} else {
			System.err.println("\tnone");
		}

		System.err.println("Interface: ");
		if (ifaces!=null) {
			for (int i=0;i<ifaces.length;i++) {
				System.err.println(ifaces[i]);
			}
		} else {
			System.err.println("\tnone");
		}

		//System.err.println( ClassTypeInfo type_info);
		System.err.println("InterfaceID: "+interfaceID);

		System.err.println("Methods: ");
		MethodData[] method_src = getMethodData();
		for (int i=0;i<methods.length;i++) {
			System.err.print("\t"+methods[i]);
			if (i<method_src.length) System.err.println(" src:"+method_src[i].getMethodNameAndType());
			System.err.println();
			//methods[i].dumpRawBC();
			//methods[i].dumpRawBC(method_src[i]);
		}

		//System.err.println( Hashtable methods_index);

		System.err.println("obj refs   : "+objRefFieldCount);

		/*
		System.err.println( Hashtable fieldsIndex   );
		System.err.println( Vector fieldsStaticRef  );
		System.err.println( Vector fieldsStaticPrim );
		System.err.println( Vector fieldsRef        );
		System.err.println( Vector fieldsMT         );
		System.err.println( Vector fieldsPrim       );
		*/
	}

	public String toString() {
		return (isInterface() ? "interface " : "class ") +name;
	}
}
