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
import keso.compiler.config.Attribut;

import java.util.Hashtable;
import java.util.Enumeration;

/**
 * This class manages the system-wide configured heaps.
 */
public final class IMGlobalHeap extends IMModul {

	/**
	 * Maps heaptypes to one created instance of that heaptype.
	 */
	private Hashtable impls = new Hashtable();

	public IMGlobalHeap(BuilderOptions opts) {
		super("global_heap","global_heap",opts);
	}

	/**
	 * Indicates if the used heap types require the use of writebarriers.
	 */
	private boolean needWrBr=false;

	/**
	 * Indicates if the used heap types require stack scan.
	 */
	private boolean needStackScan=false;

	/**
	 * Indicates if the used heap types require the use of allocation colors.
	 */
	private boolean needObjColor=false;

	/**
	 * Indicates if the allocation of an object can block.
	 */
	private boolean allocBlocks = false;

	/**
	 * Indicates if tasks could be defered by the GC.
	 */
	private boolean deferTasks = false;

	/**
	 * Indicates if we need object size.
	 */
	private boolean needObjSize = false;

	/**
	 * Creates a new heap of the given heap definition.
	 *
	 * @param dom     The domain the newly created Heap will be assigned to
	 * @param heapdef The heap's definition from the configuration file.
	 * @return The newly created Heap
	 */
	public IMHeap createHeap(IMDomain dom, ComplexAttribute heapdef) 
			throws CompileException {
		int size=0;
		IMHeap heap=null;

		if (heapdef!=null) {
			/* more different types */
			size = heap_size(heapdef);
			opts.vverbose("#### "+heapdef.value+" (size: "+size+")");
			
			if (heapdef.value.equals("IdleRoundRobin")) {
				heap = new IMHeapIdleRoundRobin(dom, opts, size, heapdef);
			} else if (heapdef.value.equals("CoffeeBreak")) {
				heap = new IMHeapCoffeeBreak(dom, opts, size, heapdef);
			} else if (heapdef.value.equals("RestrictedDomainScope")) {
				heap = new IMHeapRestrictedDomainScope(dom, opts, size, heapdef);
			}
		}

		if (heap==null) {
			/* default heap implementation very restrict but also small */
			/* dynamic objects are only seen by the creating task (restricted task scope)*/
			/* the objects are collected on task termination */
			opts.warn("## no heap definition found! (use default rds)");
			size = 64*1024;
			heap = new IMHeapRestrictedDomainScope(dom, opts, size, heapdef);
		} else {
			impls.put(heap.name,heap);
		}


		return heap;
	}

	public void emit_type_desc (Coder coder) throws CompileException {
		Enumeration heapImpls=impls.elements();
		coder.header_add("\tunion {\n");
		while(heapImpls.hasMoreElements()) {
			IMHeap thisHeap = (IMHeap) heapImpls.nextElement();
			coder.header_add("\t\tstruct {\n");
			coder.header_add(thisHeap.emit_declareFields("\t\t\t"));
			coder.header_add("\t\t} ");
			coder.header_add(thisHeap.name);
			coder.header_add(";\n");
		}
		coder.header_add("\t} heap;\n");
	}

	public void emit_header(Coder coder) throws CompileException {
		for (Enumeration heapImpls=impls.elements();heapImpls.hasMoreElements();) {
			IMHeap thisHeap = (IMHeap) heapImpls.nextElement();
			coder.header_add("#include \"");
			coder.header_add(thisHeap.getAlias());
			coder.header_add(".h\"\n");
		}
	}

	/**
	 * Translate function for the case in which only one heap implementation is
	 * being used.
	 * KESO_ALLOC will use always the same allocator function and no indirection
	 * via the domain_desc structure will be necessary.
	 *
	 * @param coder The Coder to which the code will be generated.
	 */
	private void translate_singular(Coder coder, IMDomain[] doms) throws CompileException {
		IMHeap heap_impl = doms[0].getHeap();
		String single_heap = heap_impl.getAlias();

		coder.header_add("#include \"");
		coder.header_add(single_heap);
		coder.header_add(".h\"\n");

		coder.header_add("\n#define KESO_ALLOC(_cls_,_size_,_roff_) ");
		coder.header_add("keso_");
		coder.header_add(single_heap);
		coder.header_add("_alloc(_cls_,_size_,_roff_)\n"); 
	}

	/**
	 * Translate function for the case in which more than one heap implementation is
	 * being used.
	 * KESO_ALLOC will use lookup the allocator via the domain_desc entry of the
	 * current domain.
	 *
	 * @param coder The Coder to which the code will be generated.
	 */
	private void translate_non_singular(Coder coder) throws CompileException {
		coder.header_add("\n#define KESO_ALLOC(_cls_,_size_,_roff_) ");
		coder.header_add(IMDomain.emit_getDomainDesc(opts));
		coder.header_add(".allocator(_cls_,_size_,_roff_)\n");
	}

	/**
	 * Generate code for all created heaps.
	 * The generated code will depend on whether there is more than one Heap
	 * used or not.
	 * <p>
	 * Furthermore, all used heap implementation's translate function will
	 * be called.
	 * @param coder Code will be generated to this Coder.
	 * @param doms  Array with interal representations of all domains
	 * @see #translate_non_singular(Coder coder, IMDomain[] doms)
	 * @see #translate_singular(Coder coder, IMDomain[] doms)
	 * @see IMHeap#translate(Coder coder)
	 */
	public void translate(Coder coder, IMDomain[] doms) throws CompileException {
		opts.verbose("###### create Heaps ");

		// global heap support functions
		if (impls.size()==1) {
			translate_singular(coder, doms);
		} else {
			translate_non_singular(coder);
		}

		if (needObjSize) {
			coder.header_add("obj_size_t keso_objSize(obj_size_t slot_size, object_t *obj);\n");
			coder.local_add("\n/* Determine the size in slots of the object\n");
			coder.local_add(" * pointed to by obj\n");
			coder.local_add(" */\n");
			coder.local_add("obj_size_t keso_objSize(obj_size_t slot_size, object_t *obj) {\n");
			coder.local_add("	obj_size_t size = CLASS(obj->class_id).size;\n\n");
			coder.local_add("	if(keso_isArrayClass(obj->class_id)) {\n");
			coder.local_add("		/* element size * element count */\n");
			coder.local_add("		size *= ((array_t*) obj)->size;\n");
			coder.local_add("		/* plus size of array header */\n");
			coder.local_add("		size += sizeof(array_t);\n");
			coder.local_add("	}\n\n");
			coder.local_add("	size += slot_size-1;\n");
			coder.local_add("	size /= slot_size;\n");
			coder.local_add("	return size;\n");
			coder.local_add("}\n");
		}

		if (needWrBr) {
			coder.header_add("#ifndef KESO_OMIT_WRITEBARRIERS\n");
			coder.header_add("#if KESO_WRBR_INLINE_LEVEL==1\n");
			coder.header_add("#define KESO_WRBR(_field_,_value_) { object_t** ref=&(_field_); ");
			coder.header_add("if(KESO_CURRENT_DOMAIN==irr_gc_active_domain) keso_irr_pushObject(*ref); ");
			coder.header_add("*ref =  _value_; }\n");
			coder.header_add("#elif KESO_WRBR_INLINE_LEVEL==0\n");
			coder.header_add("#define KESO_WRBR(_field_,_value_) keso_irr_writebarrier(&(_field_), _value_)\n");
			coder.header_add("#endif\n");
			coder.header_add("#else\n");
			coder.header_add("#define KESO_WRBR(_field_,_value_) (_field_ =  _value_)\n");
			coder.header_add("#endif\n");
		}

		if (needObjColor) {
			coder.header_add("/* Contains the full gcbyte for an already colored object.\n");
			coder.header_add(" * This allows checking/setting an objects color without\n");
			coder.header_add(" * masking any bits. The initial value does not matter because\n");
			coder.header_add(" * the GC will correctly initialize it every time before enabling\n");
			coder.header_add(" * writebarriers. Currently, this value be always either 1 or 3.\n");
			coder.header_add(" */\n");
			coder.header_add("#define KESO_COLOROBJ(__obj__) ((__obj__)->gcinfo=");
			coder.header_add(IMDomain.emit_getDomainDesc(opts));
			coder.header_add(".gc_color)\n");
			coder.header_add("#define KESO_OBJCOLORED(__obj__) ((__obj__)->gcinfo==");
			coder.header_add(IMDomain.emit_getDomainDesc(opts));
			coder.header_add(".gc_color)\n");
			coder.header_add("#define KESO_SWITCHCOLOR(__dom__) ");
			coder.header_add(IMDomain.emit_getDomainDesc(opts));
			coder.header_add(".gc_color^=2\n");
		}

		// generic keso_allocObject function
		if (opts.hasOption("no_inline_alloc")) {
			coder.header_add("\nobject_t *keso_allocObject(class_id_t class_id);\n");
			coder.local_add("\nobject_t *keso_allocObject(class_id_t class_id) {\n");
			coder.local_add("\treturn KESO_ALLOC(class_id,CLASS(class_id).size,CLS_ROFF(class_id));\n");
			coder.local_add("}\n");
		}
	}

	/**
	 * Query if multiple different heap types are used in the system.
	 *
	 * @return true if multiple different heap types are configured for the domains
	 */
	public boolean multiImpl() {
		return impls.size()>1;
	}

	/**
	 * Get an Enumeration of all used heap implementations
	 */
	public Enumeration usedImpls() {
		return impls.elements();
	}

	public void enableStackScan() {
		needStackScan=true;
		needObjSize=true;
	}

	public boolean needStackScan() {
		return needStackScan;
	}

	public boolean needObjSize() {
		return needObjSize;
	}

	public void enableWriteBarriers() {
		needWrBr=true;
	}

	public void enableObjColor() {
		needObjColor=true;
	}

	public boolean needGCInfo() {
		return (needObjColor || needStackScan);
	}

	public boolean needWriteBarriers() {
		return needWrBr;
	}

	public void enableBlockingAllocation() {
		allocBlocks = true;
	}

	public boolean allocationCanBlock() {
		return allocBlocks;
	}

	public void enableDeferTasks() {
		deferTasks = true;
	}

	public boolean deferTasksOnDemand() {
		return deferTasks;
	}

	private static int heap_size(ComplexAttribute heapdef) {
		Attribut size_attr = heapdef.getAttribute("HeapSize");
		if (size_attr!=null) return size_attr.valueInt();

		size_attr = heapdef.getAttribute("HeapSizeKB");
		if (size_attr!=null) return (size_attr.valueInt() * 1024);

		size_attr = heapdef.getAttribute("HeapSizeMB");
		if (size_attr!=null) return (size_attr.valueInt() * 1024 * 1024);

		return 1024;
	}

}
