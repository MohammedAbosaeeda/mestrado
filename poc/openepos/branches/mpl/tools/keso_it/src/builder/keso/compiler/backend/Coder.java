/**(c)
 *
 * Copyright (C) 2005 Christian Wawersich
 *
 * This file is part of the KESO Operating System.
 *
 * It is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * Please contact wawi@cs.fau.de for more info.
 *
 * (c)**/

package keso.compiler.backend;

import keso.classfile.*;
import keso.classfile.constantpool.*;
import keso.classfile.datatypes.*;

import keso.compiler.*;
import keso.compiler.config.*;
import keso.compiler.imcode.*;

import keso.util.Debug;
import keso.util.Bitmap;

import java.io.*;

import java.util.Vector;
import java.util.Hashtable;
import java.util.Enumeration;


public abstract class Coder {

	protected IMModul modul;
	protected IMMethod currentMethod;
	protected BuilderOptions opts;

	protected String code_alias;
	protected StringBuffer mbody;
	protected StringBuffer mbodyOuter;
	protected StringBuffer mbodyFrame;
	protected StringBuffer local;
	protected StringBuffer line;

	protected StringBuffer cheader;
	protected StringBuffer chline;

	/**
	 * Includes for the currently processed function module.
	 */
	protected Hashtable includes;

	protected String indent;

	protected Vector rules;
	protected StringBuffer global;
	protected StringBuffer global_header;

	/**
	 * Includes for global.c
	 */
	protected Hashtable global_includes;

	protected Vector class_constructors = new Vector();
	protected Vector portalConstructors = new Vector();

	/**
	 * Includes for global.h
	 */
	protected Hashtable global_header_includes;

	protected MethodTableFactory mdispatch;
	protected ClassStore repository;

	protected IMBasicBlock currentBB;

	protected InterfaceTypeMatrix ifmatrix;

	private Hashtable allready_added_fkt = new Hashtable();

	public InterfaceTypeMatrix getIFMatrix() {
		return ifmatrix;
	}

	public void setCurrentBasicBlock(IMBasicBlock bb) {
		currentBB = bb;
	}

	public IMBasicBlock getCurrentBasicBlock() {
		return currentBB;
	}

	public ClassStore getClassStore() {
		return repository;
	}

	/**
	 * Add an int to the globals of the C-Target
	 */
	public void global_add(int str) throws CompileException {
		global.append(str);
	}

	/**
	 * Add a line to the globals of the C-Target
	 */
	public void global_add(String str) throws CompileException {
		global.append(str);
	}

    public void global_add(Object str) throws CompileException {
		global.append(str);
	}

    public void global_addln(String str) throws CompileException {
        global.append(str);
        global.append("\n");
    }
    
    public void global_addln(int str) throws CompileException {
        global.append(str);
        global.append("\n");
    }

    public void global_addln(Object str) throws CompileException {
        global.append(str);
        global.append("\n");
    }

    public void global_addln() throws CompileException {
        global.append("\n");
    }

	/**
	 * Add an int to the globals of the C-Target
	 */
	public void global_header_add_asm(String asm) throws CompileException {
		global_header.append("__asm__ __volatile__ (");
		global_header.append(asm);
		global_header.append(");");
	}

	/**
	 * Add an int to the globals of the C-Target
	 */
	public void global_header_add(int str) throws CompileException {
		global_header.append(str);
	}

	/**
	 * Add a line to the globals of the C-Target
	 */
	public void global_header_add(String str) throws CompileException {
		global_header.append(str);
	}

	public void beginClass(IMModul modul) throws CompileException {
		this.modul  = modul;
		this.opts   = modul.getOptions();

		this.local   = new StringBuffer();
		this.chline  = new StringBuffer();

		beginHeader(modul);
	}

	public void beginHeader(IMModul modul) throws CompileException {
		this.modul  = modul;

		this.cheader = new StringBuffer();

		header_add("/* Class: ");
		header_add(modul.getClassName());
		header_addln(" */\n");
		header_add("#ifndef __");
		header_add(modul.getAlias());
		header_addln("_H__");
		header_add("#define __");
		header_add(modul.getAlias());
		header_addln("_H__ 1");
		header_addln("#include <keso_types.h>");
	}

	public void addInvokeMakro(IMMethod method) throws CompileException {
		MethodTable table = method.getMethodTable();
		if (table==null) return;
		table.emit_invoke_makro(method, this);
	}

	public void beginClassMethod(IMModul modul) throws CompileException {
		mbody = new StringBuffer();
		line  = new StringBuffer();
		includes = new Hashtable();
		code_alias = modul.getAlias();

		indent = "";

		/*
		add_rule(new MakeRule(opts,modul.getAlias()+".o",
				modul.getAlias()+".c", modul.getAlias()+".h"));
		*/
	}

	public void beginMethod(IMMethod method) throws CompileException {
		currentMethod = method;
		code_alias = method.getAlias();
		mbody = new StringBuffer();
		mbodyOuter = new StringBuffer();
		line  = new StringBuffer();
		includes = new Hashtable();

		indent = "";

		StringBuffer prototype = new StringBuffer();

		prototype.append(method.getReturnType());
		prototype.append(" ");
		prototype.append(method.getIdentifier());
		prototype.append(method.getArgString());

		if (opts.hasDbgSymbols()) {
			local_add("/* ");
			local_add(method.getClassName());
			local_add(".");
			local_add(method.getMethodNameAndType());
			if (method.isPure())
				local_add(" PURE");
			if (method.isConstant())
				local_add(" CONST");
			local_add(" */\n");
		}

		mbodyOuter.append(prototype);
		addln(" {");

		indent = "\t";

		header_add("/* ");
		header_add(method.getMethodNameAndType());
		header_addln(" */");
		header_add(prototype);
		if (opts.useGCCExtentions()) {
			if (method.isConstant()) {
				header_add(" __attribute__ ((const)) ");
			} else if (method.isPure()) {
				header_add(" __attribute__ ((pure)) ");
			}
		}
		header_addln(";");

	}

	public void add_rule(MakeRule rule) throws CompileException {
		rules.add(rule);
	}

	public void add_class(IMModul modul) throws CompileException {
		add_class(modul.getAlias());
	}
	public void add_class(String classname) throws CompileException {
		includes.put(classname,classname);
	}

	public void global_add_class(IMModul modul) throws CompileException {
		global_add_class(modul.getAlias());
	}
	public void global_add_class(String classname) throws CompileException {
		global_includes.put(classname,classname);
	}

	public void global_header_add_class(IMModul modul) throws CompileException {
		global_header_add_class(modul.getAlias());
	}
	public void global_header_add_class(String classname) throws CompileException {
		global_header_includes.put(classname,classname);
	}

	/**
	 * Create C-code for static a memory allocation.
	 *
	 * coder.add("object_t* memobj = ");
	 * coder.add_allocStaticMem(1024);
	 * coder.addln(";");
	 */
	private int identifierCounter=0;
	public void add_allocStaticMem(int size) throws CompileException {
		IMClass memory_class = repository.getClass("keso/core/Memory");
		add_class(memory_class);
		local_add("static jbyte staticMemArea");
		local_add(identifierCounter);
		local_add("[");
		local_add(size);
		local_add("];\n");

		add_allocConstObject(memory_class, "staticMem"+identifierCounter);
		add_init_field(memory_class, "addr", "("+opts.targetAddrType()+")staticMemArea"+identifierCounter);
		add_init_field(memory_class, "size", java.lang.Integer.toString(size));
		add_init_end();

		identifierCounter++;
	}

	/**
	 * Create C-code for constant object. The Object must be real constant
	 * not only the object reference. The memory is allocated statically at
	 * compile time.
	 *
	 * After calling add_allocConstObject the init process of the object must
	 * be finished. See example:
	 *
	 * IMClass clazz = method.getIMClass("java/lang/String");
	 * coder.add_class(clazz.getAlias());
	 *
	 * coder.add("new_string =");
	 *
	 * coder.add_allocConstObject(clazz, "my_empty_string");
	 * coder.add_init_field("value","(object_t*)0");
	 * coder.add_init_end();
	 *
	 * coder.addln(";");
	 */
	public void add_allocConstObject(IMClass clazz, String static_id) throws CompileException {
		if (opts.hasOption("inline_task") || opts.hasOption("inline_isr") || opts.hasOption("inline_clinit")) {
			add_allocConstObject(true, clazz, static_id);
		} else {
			add_allocConstObject(false, clazz, static_id);
		}
	}

	public void add_allocConstObject(boolean global, IMClass clazz, String static_id) throws CompileException {
		//local_add(opts.declareConst());
		if (global) {
			header_add("#include \"");
			header_add(clazz.getAlias());
			header_add(".h\"\n");
			header_add("extern ");
			header_add(clazz.getClassTypeString());
			header_add(" ");
			header_add(static_id);
			header_add(";\n");
		} else {
			local_add("static ");
		}
		local_add(clazz.getClassTypeString());
		local_add(" HEAP_SECTION ");
		local_add(static_id);
		local_add("={\n");
		local_add(".class_id=");
		local_add(clazz.getClassID());
		local_add(", ");
		if (opts.getGlobalHeap().needGCInfo()) 
			local_add(".gcinfo=1,\n");
		add(add_accessStaticObject(clazz, static_id));
	}

	public void add_init_field(IMClass clazz, String fieldName, String value) throws CompileException {
		local_add(".");
		local_add(clazz.emitField(fieldName));
		local_add("=");
		local_add(value);
		local_add(",\n");
	}

	public void add_init_field(IMClass clazz, String fieldName, int value) throws CompileException {
		local_add(".");
		local_add(clazz.emitField(fieldName));
		local_add("=");
		local_add(value);
		local_add(",\n");
	}

	public void add_init_field_hex(IMClass clazz, String fieldName, int value) throws CompileException {
		local_add(".");
		local_add(clazz.emitField(fieldName));
		local_add("=0x");
		local_add(Integer.toHexString(value));
		local_add(",\n");
	}

	public void add_init_end() throws CompileException {
		local_add("};\n");
	}

	public void add_allocGlobalConstObject(IMClass clazz, String alias, IMNode callback) throws CompileException {
		add_class(clazz);
		repository.registerGlobalTranslationCallback(alias, callback);
		add(add_accessStaticObject(clazz, alias));
	}

	/**
	 * Create C-code for constant object. The Object must be real constant
	 * not only the object reference. The memory is allocated statically at
	 * compile time.
	 */
	public void global_add_allocConstObject(IMClass clazz, String static_id) throws CompileException {
		global_header_add("#include \"");
		global_header_add(clazz.getAlias());
		global_header_add(".h\"\n");
		global_header_add("extern ");
		global_header_add(clazz.getClassTypeString());
		global_header_add(" HEAP_SECTION ");
		global_header_add(static_id);
		global_header_add(";\n");

		global_add(clazz.getClassTypeString());
		global_add(" HEAP_SECTION ");
		global_add(static_id);
		global_add("={\n");
		global_add(".class_id=");
		global_add(clazz.getClassID());
		global_add(", ");
		if (opts.getGlobalHeap().needGCInfo()) 
			global_add(".gcinfo=1,\n");
	}

	public void global_add_init_field_hex(String prefix, String field, int value) throws CompileException {
		global_add(".");
		global_add(prefix);
		global_add(field);
		global_add("=0x");
		global_add(Integer.toHexString(value));
		global_add(",\n");
	}

	public void global_add_init_field(String prefix, String field, String value) throws CompileException {
		global_add(".");
		global_add(prefix);
		global_add(field);
		global_add("=");
		global_add(value);
		global_add(",\n");
	}

	public void global_add_init_end() throws CompileException {
		global_add("};\n");
	}

	public void endOfLocals() throws CompileException {
		if (mbodyFrame==null) {
			mbodyFrame = mbody;
			mbody = new StringBuffer();
		}
	} 

	private int stack_obj_counter = 0;

	public void add_allocStackObjectInlined(IMClass clazz) throws CompileException {
		mbodyFrame.append(indent);
		mbodyFrame.append(clazz.getClassTypeString());
		mbodyFrame.append(" stack_obj");
		mbodyFrame.append(stack_obj_counter);
		mbodyFrame.append(";\n");

		StringBuffer init_stack = new StringBuffer();
		init_stack.append("stack_obj");
		init_stack.append(stack_obj_counter);
		init_stack.append("={.class_id=");
		init_stack.append(clazz.getClassID());
		if (opts.getGlobalHeap().needGCInfo()) 
			init_stack.append(",.gcinfo=1");
		init_stack.append("};");
		addln_befor(init_stack);

		add("((object_t*)&stack_obj");
		add(stack_obj_counter++);
		add(")");
	}

	public void add_allocStackObject(IMClass clazz) throws CompileException {
		mbodyFrame.append(indent);
		mbodyFrame.append(clazz.getClassTypeString());
		mbodyFrame.append(" stack_obj");
		mbodyFrame.append(stack_obj_counter);
		mbodyFrame.append(";\n");
		add("keso_alloc_stack((object_t*)&stack_obj");
		add(stack_obj_counter++);
		add(",");
		add(clazz.getClassID());
		if (!opts.hasOption("no_inline_alloc")) {
			add(',');
			add(clazz.emitObjectSize());
			add(',');
			add(clazz.getObjRefFieldCount());
		}
		add(")");
	}

	/**
	 * Create C-code to alloc a static object. The object must be initialized
	 * by calling the constructor!
	 */
	public void add_allocStaticObject(IMClass clazz, String static_id) throws CompileException {
		local_add("static ");
		local_add(clazz.getClassTypeString());
		local_add(" HEAP_SECTION ");
		local_add(static_id);
		local_add("={\n");
		local_add(".class_id=");
		local_add(clazz.getClassID());
		local_add(", ");
		if (opts.getGlobalHeap().needGCInfo()) 
			local_add(".gcinfo=1\n");
		local_add("};\n");
		add(add_accessStaticObject(clazz, static_id));
	}

	/**
	 * Generates a codestring that resembles a reference to a statically allocated object.
	 * This method takes care that the offset to the OBJECT_HEADER is properly
	 * added to the address if applicable.
	 * This method should always be used to access statically allocated objects,
	 * including system resources like Tasks, Resources and Alarms.
	 *
	 * @param  clazz   The IMClass the accessed object belongs to. This has alway
	 *				 to be the most special class of the object, ie may not be any
	 *				 of its superclasses, so that the offset may be calculated
	 *				 correctly. For Task objects, e.g., it has always to be the
	 *				 Task's mainClass and not the generic Task class.
	 * @param  objName The name of the accessed object in the C-Code.
	 * @return String that can be used to access the object.
	 */
	public String add_accessStaticObject(IMClass clazz, String objName) throws CompileException {
		StringBuffer result = new StringBuffer();
		result.append("((object_t*) ");
		if(clazz.getObjRefFieldCount()>0)
			result.append("((object_t**) ");
		result.append("&");
		result.append(objName);
		if(clazz.getObjRefFieldCount()>0) {
			result.append("+");
			result.append(clazz.getObjRefFieldCount());
			result.append(")");
		}
		result.append(")");
		return result.toString();
	}

	/**
	 * Create C-code to alloc a object. It must be initialized
	 * by calling the constructor!
	 */
	public void add_allocObject(IMClass clazz) throws CompileException {
		if (!opts.hasOption("no_inline_alloc")) {
			add("KESO_ALLOC(");
			add(clazz.getClassID());
			add(',');
			add(clazz.emitObjectSize());
			add(',');
			add(clazz.getObjRefFieldCount());
			add(')');
		} else {
			add("keso_allocObject(");
			add(clazz.getClassID());
			add(')');
		}
	}

	/**
	 * Create C Code to access an object field.
	 *
	 * This is unsafe! The object reference is not checked. Use chk_ref()
	 * to do so.
	 */
	public String getField(IMClass clazz, String obj, String fieldName) throws CompileException {

		StringBuffer field = new StringBuffer("(");
		field.append(clazz.getAlias().toUpperCase());
		field.append("_OBJ(");
		field.append(obj);
		field.append(")->");
		field.append(clazz.emitField(fieldName));
		field.append(')');

		return field.toString();
	}

	/**
	 * Add C Code to access an object field.
	 *
	 * This is unsafe! The object reference is not checked. Use chk_ref()
	 * to do so.
	 */
	public void add_getField(IMClass clazz, IMNode obj, String fieldName) throws CompileException {
		add('(');
		add(clazz.getAlias().toUpperCase());
		add("_OBJ(");
		obj.translate(this);
		add(")->");
		add(clazz.emitField(fieldName));
		add(')');
	}

	final public void add_putField(IMClass clazz, IMNode obj, String fieldName, int datatype, IMNode value) throws CompileException {
		if (opts.getGlobalHeap().needWriteBarriers() && !omitWriteBarrier(datatype)) {
			add("KESO_WRBR(");
			add_getField(clazz, obj, fieldName);
			add(",");
			value.translate(this);
			add(")");
		} else {
			add_getField(clazz, obj, fieldName);
			add(" = ");
			if (datatype==BCBasicDatatype.REFERENCE) add("(object_t*)");
			value.translate(this);
		}
	}

	final public void add_putField_NoWrBr(IMClass clazz, IMNode obj, String fieldName, int datatype, IMNode value) throws CompileException {
		add_getField(clazz, obj, fieldName);
		add(" = ");
		if (datatype==BCBasicDatatype.REFERENCE) add("(object_t*)");
		value.translate(this);
	}

	public void add_portal_enter(IMClass task_clazz, int target_domain_id) throws CompileException {
		if (opts.isSingleDomainSystem()) return;
        
        addln("{");

		addln("unsigned int task_id;");

		add("keso_stack_t stack = { .domain_id = ");
		add(target_domain_id);
		if (opts.hasLinkedListOfLocalReferences()) add(", .llrefs=((void*)0)");
		addln(", .next=NULL };\n");

		addln("/* task switch */");

		/* get task id */
		add("task_id = KESO_CURRENT_TASK->");
		add(task_clazz.emitField("task_id"));
		addln(";");

		/* switch stack */
		addln("stack.next = keso_stack_index[task_id];");
		addln("keso_stack_index[task_id] = &stack;");

		/* switch domain */
		add("KESO_CURRENT_TASK->");
		add(task_clazz.emitField("e_domain_id"));
		//addln(" = stack.domain_id;");
		add(" = ");
		add(target_domain_id);
		addln(";");
		//addln("keso_current_domain_id = stack.domain_id;\n");
		add("keso_current_domain_id = ");
		add(target_domain_id);
		addln(";\n");
	}

	public void add_portal_leave(IMClass task_clazz) throws CompileException {
        if (opts.isSingleDomainSystem()) return;
        
		addln("/* task switch back */");

		/* switch stack */
		addln("keso_stack_index[task_id] = stack.next;");

		/* switch domain */
		addln("keso_current_domain_id = keso_stack_index[task_id]->domain_id;");
		add("KESO_CURRENT_TASK->");
		add(task_clazz.emitField("e_domain_id"));
		addln(" = keso_current_domain_id;\n");

		addln("}");
	}

	public void add_choose_domain() throws CompileException {
		if (!opts.isSingleDomainSystem()) { add("[KESO_CURRENT_DOMAIN]"); }
	}

	public void add_getStatic(IMField field, int datatype) throws CompileException {
		if (datatype==BCBasicDatatype.REFERENCE) {
			add("((object_t*)");
			field.emit_macro(this);
			add(")");
		} else {
			field.emit_macro(this);
		}
	}

	public void add_putStatic(IMField field, int datatype, IMNode value) throws CompileException {
		if (opts.getGlobalHeap().needWriteBarriers() && !omitWriteBarrier(datatype)) {
			add("KESO_WRBR(");
			field.emit_macro(this);
			add(",");
			value.translate(this);
			add(")");
		} else {
			field.emit_macro(this);
			add(" = ");
			if (datatype==BCBasicDatatype.REFERENCE) add("(object_t*)");
			value.translate(this);
		}
	}

	/**
	 * Add C Code to access an object field.
	 */
	public void add_getField(IMClass clazz, String obj, String fieldName) throws CompileException {
		chk_ref(obj);
		add(getField(clazz, obj, fieldName));
	}

	/**
	 * Add C Code to access an object field.
	 *
	 * This is unsafe! The object reference _must_ always be valid.
	 */
	public void add_getField_fast(IMClass clazz, String obj, String fieldName) throws CompileException {
		if (opts.hasDebugCore()) chk_ref(obj);
		add(getField(clazz, obj, fieldName));
	}

	public void add(char str) throws CompileException {
		line.append(str);
	}

	/**
	 * Add a whole function to the Coder.
	 * The contents of the specified file are appended as-i
	 * to output.
	 *
	 * @param filename File to read the code from.
	 */
	public void add_fkt(String filename) throws CompileException {
		int target=-1;
		try {
			java.io.BufferedReader fktfile = new java.io.BufferedReader(new java.io.FileReader(opts.getCorePath()+"/c-templates/"+filename));
			while(true) {
				String srcline = fktfile.readLine();
				if(srcline==null) break;
				if(srcline.equals("/*KESO--CFILE--KESO*/")) {
					target=1;
					continue;
				} else if(srcline.equals("/*KESO--HEADER--KESO*/")) {
					target=0;
					continue;
				}
				switch(target) {
					case 0:
						header_add(srcline);
						header_add("\n");
						break;
					case 1:
						local_add(srcline);
						local_add("\n");
						break;
					default:
						throw new CompileException("File " + filename + " contains line within unknown section: " + srcline);
				}
			}
			fktfile.close();
		} catch (Exception e) {
			throw new CompileException(e.getMessage());
		}
	}

	/**
	 * Add a whole function to the Coder.
	 * The contents of the specified file are appended as-i
	 * to output.
	 *
	 * @param filename File to read the code from.
	 */
	public void global_add_fkt(String filename) throws CompileException {
		int target=-1;

		if (allready_added_fkt.get(filename) !=null) return;
		allready_added_fkt.put(filename,filename);

		try {
			java.io.BufferedReader fktfile = new java.io.BufferedReader(
					new java.io.FileReader(opts.getCorePath()+"/c-templates/"+filename));
			global_header_add("\n/* BEGIN ");
			global_header_add(filename);
			global_header_add(" */\n");
			while(true) {
				String srcline = fktfile.readLine();
				if(srcline==null) break;
				if(srcline.equals("/*KESO--CFILE--KESO*/")) {
					target=1;
					continue;
				} else if(srcline.equals("/*KESO--HEADER--KESO*/")) {
					target=0;
					continue;
				}
				switch(target) {
					case 0:
						global_header_add(srcline);
						global_header_add("\n");
						break;
					case 1:
						global_add(srcline);
						global_add("\n");
						break;
					default:
						throw new CompileException("File " + filename + " contains line within unknown section: " + srcline);
				}
			}
			fktfile.close();

			global_header_add("\n/* END ");
			global_header_add(filename);
			global_header_add(" */\n");
		} catch (Exception e) {
			throw new CompileException(e.getMessage());
		}
	}

	/**
	 * Add a whole function to the Coder.
	 * The contents of the specified file are appended as-i
	 * to output.
	 *
	 * @param filename File to read the code from.
	 */
	public void global_add_fkt_extern(String xalias, String filename) throws CompileException {
		int target=-1;

		StringBuffer fkt_header = new StringBuffer();
		StringBuffer fkt_body = new StringBuffer();

		try {
			java.io.BufferedReader fktfile = new java.io.BufferedReader(new java.io.FileReader(opts.getCorePath()+"/c-templates/"+filename));
			/*
			global_header_add("#include \"");
			global_header_add(xalias);
			global_header_add(".h\"\n");
			*/

			while(true) {
				String srcline = fktfile.readLine();
				if(srcline==null) break;
				if(srcline.equals("/*KESO--CFILE--KESO*/")) {
					target=1;
					continue;
				} else if(srcline.equals("/*KESO--HEADER--KESO*/")) {
					target=0;
					continue;
				}
				switch(target) {
					case 0:
						fkt_header.append(srcline);
						fkt_header.append("\n");
						break;
					case 1:
						fkt_body.append(srcline);
						fkt_body.append("\n");
						break;
					default:
						throw new CompileException("File " + filename + " contains line within unknown section: " + srcline);
				}
			}
			fktfile.close();
			fktfile=null;

			String cfilename = opts.getOutputPath()+"/"+xalias+".c";
			String hfilename = opts.getOutputPath()+"/"+xalias+".h";
			PrintStream out = new PrintStream(new FileOutputStream(hfilename));

			out.println("/* THIS FILE IS AUTO GENERATED BY KESO! DON'T EDIT */\n");
			out.println("#include <keso_support.h>");
			out.println("#include <keso_types.h>");
			out.println("\n");
			out.print("#ifndef ");
			out.println(xalias.toUpperCase());
			out.print("#define ");
			out.println(xalias.toUpperCase());
			out.println(fkt_header.toString());
			out.println("\n#endif\n");
			out.close();

			out = new PrintStream(new FileOutputStream(cfilename));

			out.println("/* THIS FILE IS AUTO GENERATED BY KESO! DON'T EDIT */\n");
			out.println("#include <keso_support.h>");
			out.println("#include <keso_types.h>");
			out.println("#include \"global.h\"");
			out.println("#include \"domains.h\"");
			out.print("#include \"");
			out.print(xalias);
			out.println(".h\"\n");
			out.println("\n");
			out.println(fkt_body.toString());
			out.close();
			add_rule(new MakeRule(opts,xalias+".o", cfilename, hfilename));

		} catch (Exception e) {
			throw new CompileException(e.getMessage());
		}
	}

	public void add(int str) throws CompileException {
		line.append(str);
	}

	public void add(Object str) throws CompileException {
		line.append(str.toString());
	}

	public void add(StringBuffer str) throws CompileException {
		line.append(str);
	}

	public void add_hex(int value) throws CompileException {
		line.append("0x");
		line.append(Integer.toHexString(value));
	}

	public void add(String str) throws CompileException {
		line.append(str);
	}

	public void add_comment(String msg) throws CompileException {
		if (!opts.hasDbgSymbols()) return;
		line.append("/* ");
		line.append(msg);
		line.append(" */");
		addln();
	}

	public void add_asm(String asm) throws CompileException {
		line.append("__asm__ __volatile__ (\"");
		line.append(asm);
		line.append("\")");
	}

	public void addln(char str) throws CompileException {
		line.append(str); addln();
	}

	public void addln(StringBuffer str) throws CompileException {
		line.append(str); addln();
	}

	public void addln(String str) throws CompileException {
		line.append(str); addln();
	}

	public void chk_ref(String obj) throws CompileException {
		mbody.append(indent);
		mbody.append("KESO_CHECK_NULLPOINTER(");
		mbody.append(obj);

		if (opts.useExceptionStrings()) {
			mbody.append(",\"");
			mbody.append(code_alias);
			mbody.append("\",");
		} else {
			mbody.append(",(char*) 0,");
		}

		mbody.append(-1);
		mbody.append(");\n");
	}

	public void chk_ref(IMNode obj, IMMethod method, int bcPosition) throws CompileException {

		if (obj==null) {
			try { method.dumpBC(); } catch (IOException ex) { throw new RuntimeException(); }
			throw new CompileException("obj == null "+method.getMethodName()+" -> "+method.getAlias());
		}

		if (obj instanceof IMNullConstant) {
			opts.warn("static null pointer in "+method.getMethodName());
			mbody.append(indent);
			mbody.append("KESO_CHECK_NULLPOINTER((object_t*)0, (char*)0, ");
			mbody.append(bcPosition);
			mbody.append(");\n");
			return;
		} 

		if (obj instanceof IMAConstant) {
			if (opts.hasVerbose()) opts.warn("check reference: const object used omit check");
			return;
		}

		if (opts.hasDebugCore() || !obj.isChecked(this)) {
			if (!(obj instanceof IMAReadLocalVariable || obj.isFinalStatic())) {
				/*
				 * processStack is calling the store method befor every method
				 * invocation. It is not possible to get something other then 
				 * a local variable!
				 */
				throw new CompileException("obj is not a variable! "+method.getMethodName()+" "+obj.toReadableString());
			}
			mbody.append(indent);
			if (obj.isChecked(this)) {
				mbody.append("KESO_ASSERT_NULLPOINTER(");
			} else {
				mbody.append("KESO_CHECK_NULLPOINTER(");
				obj.setChecked(this);
			}
			mbody.append(obj.emit());
			mbody.append(",\"");
			mbody.append(method.getMethodName());
			mbody.append("\",");
			mbody.append(bcPosition);
			mbody.append(");\n");
		} else {
			if (!(obj instanceof IMAReadLocalVariable || obj.isFinalStatic())) {
				opts.warn("obj is not a variable! "+method.getMethodName()+" "+obj.toReadableString());
			}
		}
	}

	public void chk_array(IMNode self, IMNode obj, IMNode index, IMMethod imethod, int bcPosition) throws CompileException {
		try {

			mbody.append(indent);

			if (obj instanceof IMNullConstant) {
				opts.warn("array reference is null! "+imethod.getMethodName());
				mbody.append("/* Sorry, but this array reference is allways null! */");
				mbody.append(indent);
				mbody.append("KESO_CHECK_NULLPOINTER((object_t*)0,\"");
				mbody.append(imethod.getMethodName());
				mbody.append("\",");
				mbody.append(bcPosition);
				mbody.append(");\n");
				return;
			}

			if (!(obj instanceof IMAReadLocalVariable))
				throw new CompileException("obj is not a variable! "
						+imethod.getMethodName()+" "+obj.toReadableString()+" "+obj);

			if (obj.getArrayLength()!=-1) {
				mbody.append("KESO_CHK_CBOUNDS(");
				mbody.append(obj.getArrayLength());
			} else {
				repository.registerGlobalTranslationCallback("$chk_array$", self);
				if (obj.isChecked(this)) {
					mbody.append("KESO_CHECK_ARRAY(");
				} else {
					mbody.append("KESO_CHECK_ARR_REF(");
					obj.setChecked(this);
				}
				mbody.append(((IMAReadLocalVariable)obj).getIMSlot().toString());
			}
			mbody.append(",");
			mbody.append(index.toReadableString());

			if (opts.useExceptionStrings()) {
				mbody.append(",\"");
				mbody.append(imethod.getMethodName());
				mbody.append("\",");
			} else {
				mbody.append(", (char*) 0,");
			}

			mbody.append(bcPosition);
			mbody.append(");\n");

		} catch (ClassCastException ex) {
			System.err.println("obj = "+obj.toReadableString());
			System.err.println("index = "+index.toReadableString());
			throw ex;
		}
	}

	/**
	 * Checks the offset to a (direct) Memory access via the keso/core/Memory
	 * object.
	 *
	 * @param obj The Memory object to which the access is made
	 * @param index The offset of the access
	 * @param accesssize Size in bytes of the access
	 * @param imethod calling method
	 * @param bcPosition bytecode position
	 */
	public void chk_range(IMNode obj, IMNode index, int accesssize,
			IMMethod imethod, int bcPosition) throws CompileException {

		IMClass mem_class = repository.getClass("keso/core/Memory");
		add_class(mem_class);

		if (!(obj instanceof IMAReadLocalVariable) && !obj.isConstant())
			throw new CompileException("obj is not a variable! "
					+imethod.getMethodName()+" "+obj.toReadableString()+" "+obj);

		// null reference check
		if (!obj.isConstant()) chk_ref(obj, imethod, bcPosition);

		if (index instanceof IMIConstant) {
			int off = ((IMIConstant)index).getValue();
			if(off<0) throw new CompileException("negative memory offset "
					+imethod.getMethodName()+" "+index.toReadableString()+" "+index);

			if (obj.isConstant()) {
				IMConstant const_node = obj.nodeToConstant();
				opts.vverbose("perform range check "+const_node.toReadableString()+" ["+off+"]");
				if ((accesssize-1+off)>((IMAMemConstant)const_node).getSize())
					throw new CompileException("access out of range");
			} else {
				mbody.append(indent);
				mbody.append("KESO_CHK_BOUNDS(");
				mbody.append(mem_class.getClassTypeString());
				mbody.append(',');
				mbody.append(((IMAReadLocalVariable)obj).getIMSlot().toString());
				mbody.append(',');
				mbody.append(accesssize-1+off);
				mbody.append(',');
				if (opts.useExceptionStrings()) {
					mbody.append('"');
					mbody.append(imethod.getMethodName());
					mbody.append("\",");
				} else {
					mbody.append("(char *) 0,");
				}
				mbody.append(bcPosition);
				mbody.append(");\n");
			}

		} else {

			mbody.append(indent);
			if (obj instanceof IMAMemConstant) {
				mbody.append("KESO_CHK_CBOUNDS(");
				mbody.append(((IMAMemConstant)obj).getSize());
			} else {
				mbody.append("KESO_CHK_BOUNDS(");
				mbody.append(mem_class.getClassTypeString());
				mbody.append(',');
				mbody.append(((IMAReadLocalVariable)obj).getIMSlot().toString());
			}
			mbody.append(',');
			mbody.append(accesssize-1);
			mbody.append("+");
			mbody.append(((IMIReadLocalVariable)index).getIMSlot().toString());
			mbody.append(',');
			if (opts.useExceptionStrings()) {
				mbody.append('"');
				mbody.append(imethod.getMethodName());
				mbody.append("\",");
			} else {
				mbody.append("(char *) 0,");
			}
			mbody.append(bcPosition);
			mbody.append(");\n");
		}
	}

	public void scall(IMMethod method, String vname,  IMNode[] args) throws CompileException {
		add(method.getAlias());
		add('(');
		add(vname);
		if (args.length>0) {
			add(',');
			add_args(args);
		}
		add(')');
		return;
	}

	public void add_args(IMNode[] args) throws CompileException {
		for (int i=0;i<args.length;i++)  {
			if (i>0) add(',');
			args[i].translate(this);
		}
	}

	public void vcall(IMMethodFrame frame, IMMethod callee, IMNode self, IMNode[] args) throws CompileException {

		if (self.hasSideEffect()) throw new CompileException("not a pure note! "
				+code_alias+" "+self.toReadableString()+" "+self.dumpBC());

		MethodTable table = callee.getMethodTable();
		if (table==null) {
			opts.vwarn("interface method "
					+callee.getClassName()+"."+callee.getMethodName()+" not impl.!");
			add("keso_throw_method_not_implemented(\"");
			add(code_alias);
			add("\",");
			add(self.getBCPosition());
			add(")");
			return;
		}

		if (table.emit_call(this,self)) {
			callee.emitArguments(this, frame, self, args);
		}
	}

	/**
	 * Generate code to execute a root controlflow.
	 * A root control flow means that the executed function is the first using a
	 * fresh stack. If the linked list of local references mode is enabled,
	 * exec_task will pass the initial pref to the function.
	 *
	 * @param buffer      StringBuffer that the code will be written to.
	 * @param method      The method that shall be executed.
	 * @param pref        The initial pref Attribute, or null if not applicable.
	 * @param parameters  Any parameters that method might expect, or null if
	 *                    none. parameters.toString() must return a valid list of
	 *                    parameters in C-Syntax.
	 * @return buffer with the code appended.
	 */
	public StringBuffer exec_task(StringBuffer buffer, IMMethod method, String pref, Object parameters)
		throws CompileException {
		buffer.append("\t");
		buffer.append(method.getIdentifier());
		buffer.append("(");

		if (opts.hasLinkedListOfLocalReferences() && method.canBlock()) {
			if(pref==null)
				throw new CompileException(method.getAlias() + " is not allow to use block (pref set to null)");
			else {
				buffer.append("(object_t**)&(");
				buffer.append(pref);
				if (parameters!=null) buffer.append("),"); else buffer.append(")");
			}
		}

		if (parameters!=null) buffer.append(parameters.toString());
		buffer.append(");\n");

		if (opts.hasLinkedListOfLocalReferences() && pref!=null) {
			buffer.append("\t");
			buffer.append(pref);
			buffer.append("=NULL;\n");
		}

		if (opts.hasOption("exceptions")) {
			buffer.append("\tif (KESO_PENDING_EXCEPTION) KESO_CALL_ERROR_HOOK();\n");
		}

		return buffer;
	}

	final public void add_befor(int value) throws CompileException {
		mbody.append(value);
	}

	final public void add_befor(StringBuffer str) throws CompileException {
		mbody.append(str);
	}

	final public void add_befor(String str) throws CompileException {
		mbody.append(str);
	}

	final public void addln_befor(StringBuffer str) throws CompileException {
		if (str.length()>0 && str.charAt(0)!='#') mbody.append(indent);
		mbody.append(str);
		mbody.append("\n");
	}

	final public void addln_befor(String str) throws CompileException {
		if (str.length()>0 && str.charAt(0)!='#') mbody.append(indent);
		mbody.append(str);
		mbody.append("\n");
	}

	final public void addln() throws CompileException {
		if (line.length()>0 && line.charAt(0)!='#') mbody.append(indent);
		mbody.append(line);
		mbody.append("\n");
		line.setLength(0);
	}

	final public void header_add(int str) throws CompileException {
		chline.append(str);
	}

	final public void header_add(StringBuffer str) throws CompileException {
		chline.append(str);
	}

	final public void header_add(String str) throws CompileException {
		chline.append(str);
	}

	final public void header_addln(StringBuffer str) throws CompileException {
		cheader.append(chline);
		cheader.append(str);
		cheader.append("\n");
		chline.setLength(0);
	}

	final public void header_addln(String str) throws CompileException {
		cheader.append(chline);
		cheader.append(str);
		cheader.append("\n");
		chline.setLength(0);
	}

	final public void local_add(int str) throws CompileException {
		local.append(str);
	}

	final public void local_add(String str) throws CompileException {
		local.append(str);
	}

	public void endMethod() throws CompileException {
		indent = "";
		addln("}");
		if (mbodyFrame!=null) {
			mbodyFrame.append(mbody);
		} else {
			mbodyFrame = mbody;
		}	
		mbodyOuter.append(mbodyFrame);
		writeCodeFile(mbodyOuter);
		currentMethod.storeMethodBody(mbodyFrame, includes);
		mbody = null;
		mbodyFrame = null;
		mbodyOuter = null;
		currentMethod = null;
	}

	public void endClassMethod() throws CompileException {
		indent = "";
		writeCodeFile(mbody);
	}

	public void endHeader() throws CompileException {
		flushCodeFile();
		header_addln("\n#endif");
		writeHeaderFile(cheader);
		cheader=null;
	}
	public void endClass() throws CompileException {
		endHeader();
	}

	public void close() throws CompileException {
		writeGlobalFiles();
	}

	public void writeInfoFile() throws CompileException {
		try {
			String mainfile = opts.getOutputPath()+"/kesoinfo.c";
			PrintStream out = new PrintStream(new FileOutputStream(mainfile));

			out.println("/* THIS FILE IS AUTO GENERATED BY KESO! DON'T EDIT */\n");

			out.println("#include <stdio.h>");
			out.println("#include <stdlib.h>");
			out.println("#include <string.h>");
			out.println("#define TARGET_LINUX_JOSEK\n");
			out.println("#include <keso_types.h>\n");

			StringBuffer buf=new StringBuffer();
			repository.emitClassStore(buf);
			out.print(buf);

			out.print("\nvoid dump_class_store() {\n");
			out.print("\tint i;\n");
			out.print("\tprintf(\"cls:type    :size:ifs:roff:classname\\n\");\n");
			out.print("\tfor (i=0;i<CLASSSTORE_SIZE;i++) {\n");
			out.print("\t\tint cid = i+1;\n");
			out.print("\t\tclass_t cls = CLASS(cid);\n");
			out.print("\t\tprintf(\"%3d:%3d -%3d:%4d:%3d:%4d:\",cid,cid,cls.type_range,cls.size,cls.ifaces,CLS_ROFF(cid));\n");
			out.print("\t\tprintf(\"%s\\n\",class_names[i]);\n");
			out.print("\t}\n");
			out.print("}\n\n");

			out.print("\nvoid dump_gc_stat() {\n");
			if (opts.hasOption("gc_log_i4trace")) {
				/* work todo */
			} else {
				out.print("\tFILE *log;\n");
				out.print("\tif (NULL==(log=fopen(\"gc.log\",\"r\"))) { perror(\"gc.log\"); exit(1); }\n");
				out.print("\tprintf(\"E: time :total:free:slots:\\n\");\n");
				out.print("\twhile (!feof(log)) {\n");
				out.print("\t\tchar event;\n"); 
				out.print("\t\tunsigned int time, total, free, size, did, tid, pos, cid;\n");
				out.print("\t\tfscanf(log,\"%c:%u:%u:%u:%u:%u:%u:%u:%u\\n\",&event,&time,&total,&free,&size,&did,&tid,&pos,&cid);\n");
				out.print("\t\tprintf(\"%c:%6u:%5u:%4u:%5u:\",event,time,total,free,size);\n");
				out.print("\t\tswitch (event) {\n");
				out.print("\t\tcase 'a':\n");
				out.print("\t\t\tprintf(\"task %u alloc(%s)\\n\",tid,class_names[cid-1]);\n");
				out.print("\t\t\tbreak;\n");
				out.print("\t\tdefault:\n");
				out.print("\t\t\tprintf(\"domain %u\\n\",did);\n");
				out.print("\t\t}\n");
				out.print("\t}\n");
				out.print("\tfclose(log);\n");
			}
			out.print("}\n\n");

			out.print("\nint main(int argc, char* argv[]) {\n");
			out.print("\tif (argc<2) { printf(\"usage: %s [cls|gc]\\n\",argv[0]); exit(1); }\n");
			out.print("\tif (strncmp(argv[1], \"cls\", 3)==0) {\n");
			out.print("\t\tdump_class_store(); exit(0);\n");
			out.print("\t} else if (strncmp(argv[1], \"gc\", 2)==0) {\n");
			out.print("\t\tdump_gc_stat();exit(0);\n");
			out.print("\t}\n");
			out.print("\treturn 1;\n");
			out.print("}\n");

			out.close();

		} catch (IOException ex) {
			throw new CompileException(ex.toString());
		}
	}

	/**
	 * Write the file with the main function. This method depends on the architecture of the target system.
	 */
	public abstract void writeMainFile() throws CompileException;


	 /**
	 * Write the global files needed to build the system. This method depends on the architecture of the target system.
	 */
	protected abstract void writeGlobalFiles() throws CompileException;

	protected void writeOILFile() throws CompileException, IOException {
		writeOILFile(null);
	}

	protected void writeOILFile(String oilHeader) throws CompileException, IOException {
		String oilfile_name = opts.getOutputPath()+"/keso_main.oil";

		StringBuffer buf_oil = new StringBuffer(1000);
		//buf_oil.append("# THIS FILE IS AUTO GENERATED BY KESO! DON'T EDIT\n");
		if (oilHeader!=null) buf_oil.append(oilHeader);
		opts.getSysDef().toOIL(buf_oil,"");
		String oil = buf_oil.toString();

		File oilfile = new File(oilfile_name);

		if (opts.smartFileUpdate(oilfile, oil)) {
			System.err.println("writing OIL file '" + oilfile_name + "'");
			PrintStream out = new PrintStream(new FileOutputStream(oilfile));
			out.print(oil);
			out.close();
		}
	}

	protected void writeRuntimeData(PrintStream out, Vector tasks) throws CompileException {
		IMClass task_class = repository.getClass("keso/core/Task");
		// add an alias to the task class type name for use by c-template
		global_header_add("#define KESO_TASKCLASSTYPE ");
		global_header_add(task_class.getClassTypeString());
		global_header_add("\n");
		global_header_add("#define KESO_TASKCLASSID ");
		global_header_add(task_class.getClassID());
		global_header_add("\n");

		global_header_add("extern keso_stack_t* keso_stack_index[];\n");
		global_add("keso_stack_t* keso_stack_index[KESO_MAX_TASK];\n");

		SystemDefinition sysDef = opts.getSysDef();
		addManagedResource(opts.getSysTasks(), "Task", null, 0);

		/* global task pointer, updated by PreTaskHook */
		global_header_add("#define KESO_CURRENT_TASK keso_curr_task\n");
		global_header_add("#define KESO_SET_CURRENT_TASK(_x_) keso_curr_task=(_x_)\n");

		global_header_add("extern KESO_TASKCLASSTYPE *keso_curr_task;\n");
		global_add("KESO_TASKCLASSTYPE *keso_curr_task;\n");

		/* keso_curr_task_fkt() will return a reference to the running
		 * task with the help of OSEK GetTaskID(). It will be used in
		 * the PreTaskHook to update the keso_curr_task variable.
		 */
		if (!opts.hasOption("no_inline_curr_task_fkt")) {
			global_header_add("#define keso_curr_task_fkt() (keso_task_index[OSEKOStidact])\n");
		} else {
			global_header_add("KESO_TASKCLASSTYPE *keso_curr_task_fkt();\n");
			global_add("\n\n");
			global_add("/* Returns a pointer to the currently running Task\n");
			global_add(" * INVALID_TASK is assumed to be greater by one than\n");
			global_add(" * the highest TaskID. This is ProOSEK specific and\n");
			global_add(" * will likely not work on other OSEK implementations.\n");
			global_add(" * keso_task_index is initialised with NULL, which\n");
			global_add(" * represents the INVALID_TASK Java-Task object.\n");
			global_add(" */\n");
			global_add("KESO_TASKCLASSTYPE *keso_curr_task_fkt() {\n");

			// FIXME unsupported for non PROOSEK system
			global_add("\treturn keso_task_index[0];\n");

			global_add("}\n\n");
		}

		/* RESOURCE MANAGEMENT */
		addManagedResource(opts.getSysResource(), "Resource", "INVALID_RESOURCE", ResourceDefinition.INVALID_RESOURCE);

		/* ALARM MANAGEMENT */
		addManagedResource(opts.getSysAlarms(), "Alarm", "INVALID_ALARM", AlarmDefinition.INVALID_ALARM);
	}

	protected void addManagedResource(Vector resources, String resClass,
			String invalidName, int invalidID) throws CompileException {

		if(resources.size()>0) { // only used if there are user defined resource
			IMClass resource_class = repository.getClass("keso/core/"+resClass);

			global_header_add("\n\n/* "+resClass+" management */\n\n");
			global_add("\n/* "+resClass+" management */\n");

			global_header_add("#include \"");
			global_header_add(resource_class.getAlias());
			global_header_add(".h\"\n");

			// define INVALID_<RESOURCE>, used for invalid resource lookup
			if(invalidName!=null) {
				global_header_add("#define "+ invalidName +" ");
				global_header_add(invalidID);
				global_header_add("\n");
			}

			// create global resource object
			for (int i=0; i<resources.size();i++) {
				Set res = (Set) resources.elementAt(i);
				String res_obj = res.getIdentifier()+"_obj";
				IMClass real_resource_class=null;

				// Special case tasks: we need the real class of each task
				if(res instanceof TaskDefinition)
					real_resource_class = repository.getClass(((TaskDefinition) res).getMainClassName());

				if(real_resource_class==null) real_resource_class = resource_class;
		
				int did = ((DomainDefinition)res.parent).domainid;

				global_add_allocConstObject(real_resource_class, res_obj);	
				if(res.parent instanceof DomainDefinition)
					global_add_init_field_hex("", resource_class.emitField("domain_id"), did);
				else 
					global_add_init_field("", resource_class.emitField("domain_id"), "DOMAIN_ZERO");
				if (res instanceof TaskDefinition)
					global_add_init_field_hex("", resource_class.emitField("e_domain_id"), did);
				global_add_init_field("", resource_class.emitField(resClass.toLowerCase()+"_id"), res.getIdentifier());
				global_add_init_end();
			}

			global_header_add("\n#define KESO_MAX_");
			global_header_add(resClass.toUpperCase());
			global_header_add(" ");
			global_header_add(resources.size()+1);
			global_header_add("\n");

			global_header_add("extern ");
			global_header_add(resource_class.getClassTypeString());
			global_header_add("* keso_");
			global_header_add(resClass.toLowerCase());
			global_header_add("_index[];\n");

			global_add(resource_class.getClassTypeString());
			global_add("* keso_");
			global_add(resClass.toLowerCase());
			global_add("_index[KESO_MAX_");
			global_add(resClass.toUpperCase());
			global_add("];\n");
		}
	}


	protected void writeHeaderFile(StringBuffer str) throws CompileException {
		try {
			String filename = opts.getOutputPath()+"/"+modul.getAlias()+".h";
			PrintStream out = new PrintStream(new FileOutputStream(filename));
			out.println("/* THIS FILE IS AUTO GENERATED BY KESO! DON'T EDIT */\n");
			out.println(str);
			out.close();
		} catch (IOException ex) {
			throw new CompileException(ex.toString());
		}
	}

	protected void writeIncludes(PrintStream out, Hashtable includes) {
		Enumeration inc = includes.elements();
		while (inc.hasMoreElements()) {
			String s = (String)inc.nextElement();
			out.print("#include \"");
			out.print(s);
			out.println(".h\"");
		}
	}

	protected void writeCodeFile(StringBuffer str) throws CompileException {
		try {
			if (!opts.hasOption("single_file")) {
				add_rule(new MakeRule(opts, code_alias, modul.getAlias()+".h"));
				String filename = opts.getOutputPath()+"/"+code_alias+".c";
				PrintStream out = new PrintStream(new FileOutputStream(filename));

				printDebugLine(out, 0);

				out.println("/* THIS FILE IS AUTO GENERATED BY KESO! DON'T EDIT */\n");
				out.println("#include <keso_support.h>");
				out.println("#include <keso_types.h>");
				out.println("#include \"global.h\"");
				out.println("#include \"domains.h\"");

				writeIncludes(out, includes);

				out.println("\n");
				out.println(local.toString());
				local.setLength(0);
				out.println(str);
				out.close();
			} else {
				writeSingleCodeFile(str);
			}
		} catch (IOException ex) {
			throw new CompileException(ex.toString());
		}
	}

	final protected void printDebugLine(PrintStream out, int line) throws CompileException, IOException {
		if (opts.hasOption("dbg_line") && currentMethod!=null) {
			int dbg_line = currentMethod.getLineNumber(line);
			if (dbg_line>0) {
				out.print("#line ");
				out.print(dbg_line-1);
				out.print(" \"src/");
				out.print(currentMethod.getIMClass().getSourceFile());
				out.println("\"");
			}
		}
	} 


	private PrintStream cout = null;
	final protected void writeSingleCodeFile(StringBuffer str) throws CompileException {
		try {
			if (cout==null) {
				String filename = opts.getOutputPath()+"/";
				if (currentMethod!=null) {
					add_rule(new MakeRule(opts, currentMethod.getIMClass().getAlias(), modul.getAlias()+".h"));
					filename += currentMethod.getIMClass().getAlias()+".c";
				} else {
					add_rule(new MakeRule(opts,modul.getAlias()+".o",
								modul.getAlias()+".c", modul.getAlias()+".h"));
					//add_rule(new MakeRule(opts, code_alias, modul.getAlias()+".h"));
					filename += code_alias+".c";
				}
				cout = new PrintStream(new FileOutputStream(filename));

				printDebugLine(cout, 0);

				cout.println("/* THIS FILE IS AUTO GENERATED BY KESO! DON'T EDIT */\n");
				cout.println("#include <keso_support.h>");
				cout.println("#include <keso_types.h>");
				cout.println("#include \"global.h\"");
				cout.println("#include \"domains.h\"");
			} else {
				printDebugLine(cout, 0);
			}


			writeIncludes(cout, includes);

			cout.println("\n");
			cout.println(local.toString());
			local.setLength(0);
			cout.println(str);

		} catch (IOException ex) {
			throw new CompileException(ex.toString());
		}
	}

	final protected void flushCodeFile() throws CompileException {
		if (cout!=null) {
			cout.close();
			cout=null;
		}
	}


	protected void writeMakefile(Vector rules, PrintStream out) throws IOException {
		out.println("# THIS FILE IS AUTO GENERATED BY KESO! DON'T EDIT\n\n");
		out.println(".PHONY: clean all recreate\n");
		out.print("CC=");
		out.println(opts.getCC());
		out.print("STRIP=");
		out.println(opts.getStrip());
		out.print("SIZE=");
		out.println(opts.getObjSize());
		out.print("OBJCPY=");
		out.println(opts.getObjCopy());

		if (opts.getLinkerOptions()!=null) {
			out.print("LDSCR=");
			out.println(opts.getLinkerOptions());
		}

		out.print(opts.getCFlagsVarName() + "=");
		out.print("-I ");
		out.print(opts.getCorePath());
		out.print(" ");
		out.println(opts.getCFlags());
		out.println("");
		out.print(opts.getObjVarName() + " = ");
		out.print("global.o ");

		String obj_files[] = new String[rules.size()];
		for (int i=0;i<rules.size();i++) {
			obj_files[i] = ((MakeRule)rules.elementAt(i)).getTarget();
			out.print(obj_files[i]);
			out.print(" ");
		}


		out.print("\nall: keso_main\n");

		out.print("\nkeso_main: keso_main.o libKESO.a");
		out.print("\n\t@echo LD  keso_main");
		out.print("\n\t@$(CC) $(" + opts.getCFlagsVarName() + ") -o keso_main keso_main.o  -L. -lKESO -L");
		out.print(opts.getCorePath());
		out.print(" -l");
		out.print(opts.getCoreLibName());
		out.print("\n\t@cp keso_main keso_main_g");
		out.print("\n\t@$(STRIP) keso_main");
		out.print("\n\t@$(OBJCPY) -R .comment keso_main");
		out.print("\n\t@echo $(KESORC) : $(JINOFLAGS)");
		out.println("\n\t@$(SIZE) keso_main");

		out.println("clean: ");
		out.println("\t@rm -rf keso_main keso_main_g *.o *.dot\n");

		out.println("recreate: ");
		out.println("\tcd ../.. ; make\n");

		out.print("libKESO.a: global.o $(OBJS_KESO)\n");
		out.print("\t@$(AR) rc libKESO.a  $(OBJS_KESO)\n\n");

		out.print("global.o: global.c");
		out.print("\n\t@echo OBJ global.o");
		//out.print("\n\t@mkexptab.pl -plain");
		out.print("\n\t@$(CC) $(" + opts.getCFlagsVarName() + ") -c -o global.o global.c");
		out.print("\n\n");

		for (int i=0;i<rules.size();i++) {
			MakeRule rule = (MakeRule)rules.elementAt(i);
			rule.writeRule(out);
			out.print("\n");
		}
	}

	protected void writeISRs(PrintStream out) throws CompileException {
		/* REMOVE: this is TriCore only */
	}

	protected void writeHookRoutines(PrintStream out, Vector tasks) throws CompileException {
		/* REMOVE: this is TriCore only */
	}

	/**
	 * Return true if no write barriers are needed for the method
	 * (generally code) that is currently translated.
	 */
	public boolean omitWriteBarrier(int datatype) {

		if (datatype!=BCBasicDatatype.REFERENCE) return true;

		if(currentMethod==null) return false;

		// All class initializers are called in the startup hook, GC is never running
		if(currentMethod.getMethodName().equals("<clinit>")) return true;

		// All Task/Resource/Alarm Constructors are also called in StartupHook
		if(currentMethod.isInit()) {
			if(currentMethod.getIMClass().getClassName().equals("keso/core/Alarm"))
				return true;
			if(currentMethod.getIMClass().getClassName().equals("keso/core/Resource"))
				return true;
			if(currentMethod.getIMClass().isSubClassOf("keso/core/Task"))
				return true;
		}

		return false;
	}


	/**
	 * Call from the StartUpHook creating code.
	 * Adds code to init the index of a manged Resource
	 * and defines the resource ids on targets where thi
	 * is required
	 */
	protected void initManagedResource(Vector resources, String resClass, PrintStream out) throws CompileException {
		if(resources.size()==0) return;

		IMClass resource_class = repository.getClass("keso/core/"+resClass);

		String index_name = "keso_"+resClass.toLowerCase()+"_index";

		out.print("\t/* Initialize ");
		out.print(index_name);
		out.print(" */\n");

		for(int i=0; i<resources.size(); i++) {
			Set res = (Set) resources.elementAt(i);
			IMClass specializedClass = resource_class;

			if ( res instanceof TaskDefinition && !(res instanceof NativeTask))
				specializedClass = repository.getClass(((TaskDefinition) res).getMainClassName());

			out.print("\t");
			out.print(index_name);
			out.print("[");
			out.print(res.getIdentifier());
			out.print("] = (" );
			out.print(resource_class.getClassTypeString());
			out.print("*) ");
			out.print(add_accessStaticObject(specializedClass, res.getIdentifier()+"_obj"));
			out.println(";");

		}

		out.print("\t");
		out.print(index_name);
		out.print("[INVALID_");
		out.print(resClass.toUpperCase());
		out.print("] = NULL;\n\n");
	}

	/**
	 * Call from the StartUpHook creating code.
	 * Adds code to call the constructor of managed
	 * Resources. This is to be called after all
	 * those Resource indices have been initialized
	 * with initManagedResource.
	 */
	protected void callManagedResourceConstructors(Vector resources, String resClass,
			PrintStream outstream) throws CompileException {
		if (resources.size()==0) return;
		
		StringBuffer out = new StringBuffer(500);
		boolean ccalled=false;

		byte lastdomain=-1;

		out.append("\n\t/* Call each "+resClass+"'s constructor */\n");

		for(int i=0; i<resources.size(); i++) {
			IMClass resource_class;
			Set res = (Set) resources.elementAt(i);

			// Special case Tasks: call the constructor of their mainClass
			if(res instanceof TaskDefinition && !(res instanceof NativeTask))
				resource_class = repository.getClass(((TaskDefinition) res).getMainClassName());
			else resource_class = repository.getClass("keso/core/"+resClass);

			IMMethod constructor = resource_class.getMethod("<init>()V");

			if(constructor==null) {
				if(res instanceof TaskDefinition) continue;
				else break;
			}

			/* Set the currrent domain id correctly
			 * We do not need to set current Task because
			 * no system services that need the current task
			 * must be invoked from the StartupHook
			 */
			if (!opts.isSingleDomainSystem()) {
				if ( ((DomainDefinition)res.parent).domainid != lastdomain ) {
					lastdomain = ((DomainDefinition)res.parent).domainid;
					out.append("\tkeso_current_domain_id = ");
					out.append(lastdomain);
					out.append(";\n");
				}
			}

			exec_task(out,constructor,null,"(object_t*) keso_"+resClass.toLowerCase()+"_index[" + res.getIdentifier()+"]");

			if(res instanceof TaskDefinition) {
				/* correctly set the task_id of the Task */
				out.append("\tkeso_");
				out.append(resClass.toLowerCase());
				out.append("_index[");
				out.append(res.getIdentifier());
				out.append("]->");
				out.append(resource_class.emitField("task_id"));
				out.append(" = ");
				out.append(res.getIdentifier());
				out.append(";\n");
			}
			ccalled=true;
		}

		/* No need to reset the current domain id, the PreTaskHook will set
		 * it correctly before running any Task */
		if(ccalled) outstream.println(out.toString());
	}

	public void registerClassInit(IMMethod clinit) {
		if (!class_constructors.contains(clinit)) {
			opts.verbose("register class init for "+clinit.getClassName());
			class_constructors.add(clinit);
		}
	}

    public void registerPortalInit(String cName) {
        portalConstructors.add(cName);
    }


	public void writeHeaderClassConstructors(PrintStream out) throws CompileException {
		if (class_constructors.size()==0) return;
		Enumeration clinits = class_constructors.elements();
		while (clinits.hasMoreElements()) {
			IMMethod m = (IMMethod)clinits.nextElement();
			IMClass clazz = m.getIMClass();
			out.print("#include \"");
			out.print(m.getIMClass().getAlias());
			out.print(".h\"\n");
		}
	}

    
	public void writeCallClassConstructors(PrintStream out) throws CompileException {

		if (class_constructors.size() > 0) {

			boolean llrefs = opts.hasLinkedListOfLocalReferences();
			boolean has_blocking_clinit = false;

			Enumeration clinits = class_constructors.elements();
			if (llrefs) while (clinits.hasMoreElements()) {
				IMMethod m = (IMMethod)clinits.nextElement();
				if (m.canBlock()) has_blocking_clinit = true; 
			}

			// TODO: which Task is executing the constructor? FIXME:
			out.println("\t/* Call class constructors */");
			out.println("\t{");
			if (has_blocking_clinit) {
				out.println("\t\tobject_t* dummy;");
			}
			if (opts.isSingleDomainSystem()) {
				clinits = class_constructors.elements();
				while (clinits.hasMoreElements()) {
					IMMethod m = (IMMethod)clinits.nextElement();

					if (opts.hasOption("inline_clinit")) {
						if (m.getCosts()==0) continue;
						StringBuffer body = m.getMethodBodyPretty();
						out.println(body);
					} else {
						out.print("\t\t");
						out.print(m.getAlias());
						if (llrefs && m.canBlock()) {
						 	out.println("(&dummy);");
						} else {
							out.println("();");
						}
					}
				}
			} else {
				// FIXME: domain.h hack! Do this befor calling writeCallClassConstructors!  
				//out.println("#include \"domains.h\"\n");
				if (opts.is8BitController()) {	
					out.println("\t\tjbyte dom_i;");
				} else {
					out.println("\t\tjint dom_i;");
				}
				out.println("\t\tfor (dom_i=0;dom_i<KESO_NUM_DOMAINS;dom_i++) {");
				out.println("\t\t\tKESO_CURRENT_DOMAIN=dom_i;");
				clinits = class_constructors.elements();
				while (clinits.hasMoreElements()) {
					IMMethod m = (IMMethod)clinits.nextElement();
					out.print("\t\t\t");
					out.print(m.getIdentifier());
					if (m.canBlock()) {
						out.println("(&dummy);");
					} else {
						out.println("();");
					}
				}
				out.println("\t\t}");
				out.println("\t\tKESO_CURRENT_DOMAIN=DOMAIN_ZERO;");
			}
			out.println("\t}");
		}

		// portal class constructors
		out.println("\t/* Call portal class constructors */");
		for (Enumeration constructors = portalConstructors.elements(); constructors.hasMoreElements();) {
			String clinit = (String) constructors.nextElement();
			out.print("\t"); 
			out.print(clinit);
			out.println("();");
		}

	}

	/**
	 * adds a function call to the user defined hook method.
	 */
	protected void addHookCode(PrintStream out, ComplexBoolAttribute hook, String[][] params) throws CompileException {
		/* REMOVE is TriCore only */
	}

	/**
	 * Adds typedefs for the OSEK datatypes.
	 * The data types are compatible with ProOSEK.
	 */
	protected void addOsekTypes(PrintStream glb_out) {
		glb_out.println("typedef unsigned char StatusType;");
		glb_out.println("typedef unsigned char IsrType;");
		glb_out.println("typedef unsigned char TaskType;");
		glb_out.println("typedef unsigned char ResourceType;");
		glb_out.println("typedef unsigned char OSEKOSPrioType;");
		glb_out.println("typedef unsigned char TaskStateType;");
		glb_out.println("typedef unsigned char EventMaskType;");
		glb_out.println("typedef unsigned char CounterType;");
		glb_out.println("typedef unsigned char TickType;");
		glb_out.println("typedef unsigned char AlarmType;");
		glb_out.println("typedef unsigned char AppModeType;");
		glb_out.println("typedef unsigned char OSEKOSTaskActCntType;");
		glb_out.println("");
		glb_out.println("#define DisableAllInterrupts()");
		glb_out.println("#define EnableAllInterrupts()");
		glb_out.println("#define ResumeAllInterrupts()");
		glb_out.println("#define SuspendAllInterrupts()");
		glb_out.println("#define AlarmBaseType int");
		glb_out.println("#ifndef INVALID_TASK");
		glb_out.println("#define INVALID_TASK -1");
		glb_out.println("#endif");
		glb_out.println("");
	}

	protected void writeAlarmCallbacks(PrintStream out, Vector alarmDefs) throws CompileException {
		/* REMOVE is Tricore only */
	}
}
