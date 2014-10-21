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
import keso.compiler.backend.*;
import keso.compiler.config.ComplexAttribute;
import keso.compiler.config.SystemDefinition;

import java.util.Hashtable;

public class IMHeapRestrictedDomainScope extends IMHeap {

	/**
	 * Flag to indicate if one-time translation parts like the implementations
	 * functions have already been emitted or not.
	 */
	private static SystemDefinition once = null;
	private int size;

	public IMHeapRestrictedDomainScope(IMDomain dom, BuilderOptions opts, int size, ComplexAttribute heapdef) throws CompileException {
		super("rds","rds",size,opts,dom,heapdef);

		this.size=size;
		if (!opts.isSingleDomainSystem()) {
			addDescriptorField("unsigned int", "heap_size", new Integer(size).toString(), false);
			addDescriptorField("char*", "heap_top", heap_ident, false);
		}
		addDescriptorField("char*", "new_ptr", heap_ident, false);
	}

	public void translate(Coder coder) throws CompileException {
		opts.verbose("#### create heap "+alias);

		super.translate(coder);

		/* Write functions for this heap (allocator, etc.)
		 * only done once per implementation
		 */
		SystemDefinition sysDef = opts.getSysDef();
		if (once != sysDef) {
			once = sysDef;

			coder.beginHeader(this);
			coder.header_add("object_t* keso_");
			coder.header_add(alias);
			coder.header_add("_alloc(class_id_t class_id, obj_size_t size, obj_size_t roff);\n");

			coder.beginClassMethod(this);

			coder.local_add("#include \"");
			coder.local_add(alias);
			coder.local_add(".h\"\n\n");

			if (!opts.isAVR() && opts.targetAddrSizeShift()>0) {
				int tmp = opts.targetAddrSize()-1;
				coder.local_add("#define KESO_ALIGN_OBJ(_size_) ((_size_+");
				coder.local_add(tmp);
				coder.local_add(")&~");
				coder.local_add(tmp);
				coder.local_add(")");
			} else {
				coder.local_add("#define KESO_ALIGN_OBJ(_size_) (_size_)\n");
			}

			coder.local_add("\nobject_t* keso_");
			coder.local_add(alias);
			coder.local_add("_alloc(class_id_t class_id, obj_size_t size, obj_size_t roff) {\n");
			coder.local_add("\tchar* mem;\n\tint i;\n\tobject_t* obj;\n\n");
			if (opts.isSingleDomainSystem()) {
				String dom = IMDomain.emit_getDomainDesc(opts);
				coder.local_add("\tmem=");
				coder.local_add(dom);
				coder.local_add(".heap.rds.new_ptr;\n\t");
				coder.local_add(dom);
				coder.local_add(".heap.rds.new_ptr += KESO_ALIGN_OBJ(size);\n");
				coder.local_add("\tif (");
				coder.local_add(dom);
				coder.local_add(".heap.rds.new_ptr>");
				coder.local_add("(");
				coder.local_add(heap_ident);
				coder.local_add("+");
				coder.local_add(size);
				coder.local_add("))\n");
			} else {
				coder.local_add("\tdomain_t* domain;\n\n");
				coder.local_add("\tdomain = &");
				coder.local_add(IMDomain.emit_getDomainDesc(opts));
				coder.local_add(";\n");
				coder.local_add("\tmem = domain->heap.rds.new_ptr;\n");
				coder.local_add("\tdomain->heap.rds.new_ptr += KESO_ALIGN_OBJ(size);\n");
				coder.local_add("\tif (domain->heap.rds.new_ptr>");
				coder.local_add("(domain->heap.rds.heap_top + domain->heap.rds.heap_size))\n");
			}
			coder.local_add("\t\tKESO_THROW_ERROR(\"out of memory\");\n");
			if (opts.is8BitController()) {
				coder.local_add("\tfor (i=0;i<size;i++) mem[i] = 0;\n");
			} else {
				coder.local_add("\ti=KESO_ALIGN_OBJ(size)/sizeof(int);\n");
				coder.local_add("\twhile(i-->0) ((int *)mem)[i] = 0;\n");
			}
			coder.local_add("\tobj = (object_t*) ((object_t**)mem + roff);\n");
			coder.local_add("\tobj->class_id = class_id;\n");
			coder.local_add("\n\treturn obj;\n");
			coder.local_add("}\n");

			coder.endClassMethod();

			coder.endHeader();
		}
	}
}
