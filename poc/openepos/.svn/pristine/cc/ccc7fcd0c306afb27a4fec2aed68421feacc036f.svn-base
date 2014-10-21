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

import keso.compiler.CompileException;
import keso.compiler.BuilderOptions;
import keso.compiler.ClassStore;
import keso.compiler.ClassTypeInfo;
import keso.compiler.backend.Coder;
import keso.compiler.kni.JoinPointChecker;

import keso.util.Debug; 
import keso.util.DecoratedNames;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

public class IMArrayClass extends IMClass {

	protected String element_type;
	protected boolean require_alloc = false;

	public IMArrayClass(BuilderOptions opts, ClassStore src_repository, String classname,
			String alias, String element_type, boolean isFinal) {
		super(opts,src_repository,classname,alias,isFinal);
		this.element_type=element_type;
	}

	public int getObjRefFieldCount() {
		return 0;
	}

	public void requireAlloc() {
		require_alloc=true;
	}

	public void requireMultiAlloc() {
		throw new Error();
	}

	public IMClass cloneClass(ClassStore src_repository) {
		return new IMArrayClass(opts,src_repository,name,alias,element_type,isFinal);
	}

	public boolean isInterface() {
		return false;
	}

	public IMClass[] getInterfaces() {
		return null;
	}

	public boolean hasSourceMethod(String nameAndType) {
		return false;
	}

	public IMMethod getMethod(String nameAndType) {
		return null;
	}

	public IMMethod[] getMethods() {
		return null;
	}

	public String getSuperClassName() {
		return "java/lang/Object";
	}

	public String emitObjectSize() {
		return "sizeof("+element_type+")";
	}

	public void global_header(Coder coder) throws CompileException {
		/*
		coder.global_add("#include \"");
		coder.global_add(getAlias());
		coder.global_add(".h\"\n");
		*/
	}

	public void load() throws IOException, CompileException {
		return;
	}

	public void resetCallGraph() throws CompileException {
		return;
	}

	public void analyseCallGraph() throws CompileException {
		return;
	}

	public void omitUnusedMethods() throws CompileException {
		return;
	}

	public void inlineMethodCalls() throws CompileException {
		return;
	}

	public boolean performConstantFolding() throws CompileException {
		return false;
	}

	public void translate(Coder coder) throws CompileException {
		opts.verbose("###### create "+getClassName());

		coder.beginHeader(this);

		coder.header_add("#include <keso_types.h>\n");
		coder.header_add("\n#define ");
		coder.header_add(alias.toUpperCase());
		coder.header_add("_ID ((class_id_t)");
		coder.header_add(type_info.getClassTypeID());
		coder.header_add(")\n");

		coder.header_add("typedef struct {\n");
		coder.header_add("ARRAY_HEADER\n\t");
		coder.header_add(element_type);
		coder.header_add(" data[1];\n");
		coder.header_add("} ");
		coder.header_add(alias);
		coder.header_add("_t;\n");

		if (require_alloc) {
			coder.header_add("\nobject_t	*keso_alloc_");
			coder.header_add(alias);
			coder.header_add("(array_size_t size);\n");

			coder.beginClassMethod(this);

			coder.local_add("#include \"");
			coder.local_add(alias);
			coder.local_add(".h\"\n\n");

			coder.local_add("object_t	*keso_alloc_");
			coder.local_add(alias);
			coder.local_add("(array_size_t size) {\n");
			coder.local_add("\tarray_t* array;\n");
			coder.local_add("\tarray=(array_t*)KESO_ALLOC(");
			coder.local_add(alias.toUpperCase());
			coder.local_add("_ID,");
			coder.local_add("sizeof(array_t)+(size*");
			coder.local_add(emitObjectSize());
			coder.local_add("),0);\n");
			coder.local_add("\tarray->size=size;\n");
			coder.local_add("\treturn (object_t*)array;\n");
			coder.local_add("}");

			coder.endClassMethod();
		}

		coder.endHeader();
	}

	public void dumpBC() throws IOException { return ; }

	public void writeUMLDiagram() throws IOException { return; }

	public void writeCFG() throws IOException { return; }

	public void writeDomTree() throws IOException { return; }

	public void collectMethodNames(int DomainID, Hashtable method_to_class) { return; } 

	protected void parseMethodData() { return; }
}
