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

final public class IMObjectArrayClass extends IMArrayClass {

	private boolean require_multi_alloc = false;
	private IMClass elem_class;

	public IMObjectArrayClass(BuilderOptions opts, ClassStore src_repository, String classname,
			String alias, String element_type, boolean isFinal) {
		super(opts,src_repository,classname,alias,element_type,isFinal);
	}

	public IMObjectArrayClass(BuilderOptions opts, ClassStore src_repository, String classname,
			IMClass elem_class, String element_type, boolean isFinal) {
		super(opts,src_repository,classname,"",element_type,isFinal);
		this.elem_class=elem_class;
		require_alloc=true;
	}

	public void requireMultiAlloc() {
		require_alloc=true;
		require_multi_alloc=true;
	}

	public IMClass cloneClass(ClassStore src_repository) {
		if (elem_class!=null) {
			return new IMObjectArrayClass(opts,src_repository,name,elem_class,element_type,isFinal);
		} 
		return new IMObjectArrayClass(opts,src_repository,name,alias,element_type,isFinal);
	}

	public void global_header(Coder coder) throws CompileException {
	}

	public String getAlias() {
		if (alias.equals("") && elem_class!=null) alias=elem_class.getAlias()+"_array";
		return alias;
	}

	public void translate(Coder coder) throws CompileException {
		opts.verbose("###### create "+getClassName());

		getAlias();

		coder.beginHeader(this);

		coder.header_add("#include <keso_types.h>\n");
		coder.header_add("#include <keso_args.h>\n");

		coder.header_add("\n#define ");
		coder.header_add(alias.toUpperCase());
		coder.header_add("_ID ((class_id_t)");
		coder.header_add(type_info.getClassTypeID());
		coder.header_add(")\n");

		coder.header_add("typedef struct {\n");
		coder.header_add("ARRAY_HEADER\n\t");
		coder.header_add("class_id_t type;\n\t");
		coder.header_add(element_type);
		coder.header_add(" data[1];\n");
		coder.header_add("} ");
		coder.header_add(alias);
		coder.header_add("_t;\n");

		if (require_alloc || require_multi_alloc) coder.beginClassMethod(this);

		if (!alias.equals("object_array")) {
			coder.add_class("object_array");
		}

		/* keso_alloc_object_array */
		if (require_alloc) {
			coder.header_add("\nobject_t	*keso_alloc_");
			coder.header_add(alias);
			coder.header_add("(class_id_t class_id, array_size_t size);\n");

			coder.local_add("#include \"");
			coder.local_add(alias);
			coder.local_add(".h\"\n\n");

			coder.local_add("\nobject_t	*keso_alloc_");
			coder.local_add(alias);
			coder.local_add("(class_id_t class_id, array_size_t size) {\n");
			coder.local_add("	object_array_t* array;\n");
			coder.local_add("	array = (object_array_t*)KESO_ALLOC(");
			coder.local_add(alias.toUpperCase());
			coder.local_add("_ID,sizeof(object_array_t)+(size*");
			coder.local_add(emitObjectSize());
			coder.local_add("),0);\n");
			coder.local_add("	array->type=class_id;\n");
			coder.local_add("	array->size=size;\n");
			coder.local_add("	return (object_t*)array;\n");
			coder.local_add("}\n");
		}

		/* keso_allocMultiArray */

		if (require_multi_alloc) {
			coder.header_add("object_t 	*keso_allocMultiArray(class_id_t class_id, array_size_t dim, ... );\n");

			coder.local_add("\nobject_t 	*keso_allocMultiArray(class_id_t class_id, array_size_t dim, ... ) {\n");
			coder.local_add("	va_list arg;\n");
			coder.local_add("	int i;\n");
			coder.local_add("	object_array_t* array;\n\n");
			coder.local_add("	va_start(arg,dim);\n\n");
			coder.local_add("	array = (object_array_t*)keso_alloc_object_array(OBJECT_ARRAY_ID, dim);\n\n");
			coder.local_add("	for (i=0;i<dim;i++) {\n");
			coder.local_add("		array_size_t size = va_arg(arg,array_size_t);\n");
			coder.local_add("		array->data[i] = (object_t*)keso_alloc_object_array(class_id, size);\n");
			coder.local_add("	}\n\n");
			coder.local_add("	return (object_t*)array;\n");
			coder.local_add("}\n");
		}

		if (require_alloc || require_multi_alloc) coder.endClassMethod();

		coder.endHeader();
	}

	public void dumpBC() throws IOException { return ; }

	public void writeCFG() throws IOException { return; }

	public void collectMethodNames(Hashtable method_to_class, Hashtable used_methods) { return; } 

	protected void parseMethodData() { return; }
}
