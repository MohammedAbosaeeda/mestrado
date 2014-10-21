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
import keso.compiler.config.*;
import keso.compiler.backend.Coder;
import keso.compiler.BuilderOptions;
import keso.util.IntegerHashtable;

import java.util.Vector;
import java.util.Hashtable;
import java.util.Enumeration;

/**
 * Internal representation of a domain.
 */
final public class IMDomain extends IMDescriptorContainer {

	private static Hashtable all_doms = new Hashtable();

	/**
	 * System-wide unique identifier for this domain.
	 */
	private int domain_id;

	/**
	 * Internal representations of all tasks belonging to this domain.
	 */
	private IMTask[] task_list;

	/**
	 * The Heap of this domain.
	 */
	private IMHeap heap;

	/**
	 * The representation of this domain's definition in
	 * the KESO configuration file.
	 */
	private DomainDefinition myDef;
	
	/**
	 * Initializes a newly created IMDomain instance.
	 *
	 * @param domainDef Representation of the domain's definition from the configuration file.
	 */
	private IMDomain(BuilderOptions opts, DomainDefinition domainDef) throws CompileException {
		super("domains","domains",opts);

		myDef = domainDef;
		domain_id = domainDef.domainid;	

		heap = opts.getGlobalHeap().createHeap(this, domainDef.getHeap());

		Vector tasks = domainDef.getTasks();
		task_list = new IMTask[tasks.size()];
		for (int i=0; i<tasks.size(); i++) {
			TaskDefinition task_def = (TaskDefinition)tasks.elementAt(i);
			if (task_def instanceof NativeTask) continue;
			task_list[i] = new IMTask(task_def);
		}
	}

	/**
	 * Get a String usable to access the domain_desc of the current domain at runtime.
	 *
	 * @return A String suitable to access the domain_desc structure of the currently active domain in C-Code at runtime.
	 */
	public static String emit_getDomainDesc(BuilderOptions opts) throws CompileException {
		if (opts.isSingleDomainSystem()) {
			return "domain_desc";
		} else {
			return "domain_desc[KESO_CURRENT_DOMAIN]";
		}
	}

	/**
	 * Get a String usable to access a field of the domain_desc specified by its name of the current domain at runtime.
	 * This will only work for fields that are not specific to a heap
	 * implementation.
	 *
	 * @param  name Name of the domain_desc field that is to be accessed.
	 * @return A String suitable to access the domain_desc field of the currently active domain in C-Code at runtime.
	 */
	public static String emit_getDomainField(BuilderOptions opts, String name) throws CompileException {
		return "(" + emit_getDomainDesc(opts) +"."+name+")";
	}

	/**
	 * Get the system-wide unique identifier of this domain.
	 *
	 * @return A system-wide unique identifier of this domain.
	 */
	public int getDomainID() { return domain_id; }

	/**
	 * Get the definition of this domain in the configuration file.
	 *
	 * @return Definition of this domain in the configuration file.
	 */
	public DomainDefinition getDef() { return myDef; }

	/**
	 * Get unique String identifier for this domain.
	 *
	 * @return System-wide unique String identifying this domain.
	 */
	public String getIdentifier() { return myDef.getIdentifier(); }

	/**
	 * Get the internal representations of all Tasks belonging to this domain.
	 *
	 * @return Array with the internal representations of all Tasks belonging to this domain.
	 */
	public IMTask[] getTasks() { return task_list; }

	/**
	 * Get the heap of this domain.
	 *
	 * @return Heap of this domain.
	 */
	public IMHeap getHeap() { return heap; }

	/**
	 * Get the internal representation of a Task belonging to this domain
	 * specified by its name.
	 *
	 * @param  taskName Name of the wanted task as specified in the keso configuration.
	 * @return The internal representation of the requested Task, or null if no tasks with the specified name exists in this domain.
	 */
	public IMTask getTask(String taskName) {
		for (int j=0;j<task_list.length;j++) {
			if (task_list[j].getDef().ident.equals(taskName)) return task_list[j];
		}
		return null;
	}

	/**
	 * Generate code that initializes the domain_desc structure of this domain.
	 *
	 * @param coder The Coder that the code will be emitted to.
	 */
	private void translate_desc(Coder coder) throws CompileException {
		String heapprefix = "\t.heap."+heap.name;
		coder.local_add(emit_defineFields("\t"));
		coder.local_add(heap.emit_defineFields(heapprefix));
		coder.add_class(heap);
	}

	/**
	 * Generate supplemental code for this domain, including the heap of the
	 * domain.
	 *
	 * @param coder The Coder that the code will be emitted to.
	 */
	public void translate(Coder coder) throws CompileException {
		// add shared domain fields
		if (opts.getGlobalHeap().multiImpl()) {
			/* all domain_descs contain a field with a pointer to
			 * their heap's allocator function
			 */
			addDescriptorField("object_t* (*allocator) (class_id_t, obj_size_t, obj_size_t)", "allocator", heap.getAllocatorName(), true);
		}
		heap.translate(coder);
	}

	/**
	 * Generate code for system-wide domain management.
	 * The domain_desc type domain_t will also be generated by this method.
	 *
	 * @param coder The Coder that the code will be emitted to.
	 * @param opts  BuilderOptions instance.
	 */
	public static void translateDomains(Coder coder, BuilderOptions opts) throws CompileException {
		IMDomain[] domains = opts.getDomains();

		for (int i=0;i<domains.length;i++) {
			domains[i].translate(coder);
		}

		/* The domain_t will contain common fields that are independant of the
		 * underlying heap implementation and a union of structures that contain
		 * the fields of the specific heap type.
		 */
		coder.beginHeader(domains[0]);

		coder.beginClassMethod(domains[0]);

		coder.header_add("#include \"global.h\"\n");
		opts.getGlobalHeap().emit_header(coder);

		coder.header_add("\ntypedef struct {\n");
		coder.header_add(domains[0].emit_declareFields("\t"));
		/* add field for domain specific heap data */
		opts.getGlobalHeap().emit_type_desc(coder);
		coder.header_add("} domain_t;\n");

		coder.header_add("#define INVALID_DOMAIN ");
		coder.header_add(domains.length);
		coder.header_add("\n");

		if (opts.isSingleDomainSystem()) {
			coder.local_add("/* static references */\n");
			coder.header_add("extern volatile object_t* static_ref[KESO_NUM_STATIC_REFS];\n");
			coder.header_add("#define DOMAINDESC(id) domain_desc\n");
			coder.header_add("#define STATICREF(did) static_ref\n");
			coder.header_add("#define KESO_CURRENT_DOMAIN 0\n");
			coder.local_add("volatile object_t* static_ref[KESO_NUM_STATIC_REFS];\n");

		} else {

			/*
			 * The TriCore registers a0, a1, a8 and a9 are not stored in the CSA
			 * and exist for global use. Therefore we can use a8 as global domain
			 * pointer.
			 *
			 * The domain_id must be updated in PreTaskHook!
			 */
			coder.header_add("#define KESO_CURRENT_DOMAIN keso_current_domain_id\n");
			coder.header_add("#define DOMAINDESC(id) domain_desc[id]\n");
			coder.header_add("#define STATICREF(did) static_ref[did]\n");
			coder.header_add("/* we need a DOMAIN_ZERO */\n");
			coder.header_add("#ifndef DOMAIN_ZERO\n");
			coder.header_add("#define DOMAIN_ZERO INVALID_DOMAIN\n");
			coder.header_add("#endif\n");
			if (opts.hasOption("useGlobalReg")) {
				coder.header_add("register unsigned char keso_current_domain_id");
				coder.header_add(" __asm__(\"a8\");\n");
			} else {
				coder.header_add("extern unsigned char keso_current_domain_id;\n");
				coder.local_add("unsigned char keso_current_domain_id = DOMAIN_ZERO;\n");
			}

			coder.header_add("\n\n#define KESO_NUM_DOMAINS ");
			coder.header_add(domains.length);
			coder.header_add("\n");
			coder.local_add("/* static references */\n");
			coder.header_add("extern volatile object_t* static_ref[KESO_NUM_DOMAINS][KESO_NUM_STATIC_REFS];\n");
			coder.local_add("volatile object_t* static_ref[KESO_NUM_DOMAINS][KESO_NUM_STATIC_REFS];\n");
		}

		// Define and init domain_desc array
		if (opts.isSingleDomainSystem()) {	
			coder.header_add("extern domain_t domain_desc;\n");
			coder.local_add("\ndomain_t domain_desc = {\n");
			domains[0].translate_desc(coder);
			coder.local_add("\n};\n");
		} else {
			coder.header_add("extern domain_t domain_desc[];\n");
			coder.local_add("\ndomain_t domain_desc[KESO_NUM_DOMAINS] = {\n");
			for (int i=0;i<domains.length;i++) {
				coder.local_add("{\n");
				domains[i].translate_desc(coder);
				coder.local_add("\n}");
				if (!((i+1)==domains.length)) coder.local_add(",\n");
			}
			coder.local_add("};\n");
		}

		opts.getGlobalHeap().translate(coder,domains);

		coder.endClassMethod();

		coder.endHeader();

		//coder.global_header_add_class(domains[0]);
	}

	/**
	 * Constructs the internal representations of all domains
	 * using the DomainDefinitions of the system configuration.
	 *
	 * @param  opts BuilderOptions instance.
	 * @return Array with the internal domain representations.
	 */

	public static IMDomain[] createDomains(String sys_name, BuilderOptions opts) throws CompileException {
		IMDomain[] doms=(IMDomain[])all_doms.get(sys_name);
		if (doms==null) {
			Vector domains = opts.getSysDef().getDomains();
			doms = new IMDomain[domains.size()];

			opts.setupGlobalHeap();

			for (int i=0;i<domains.size();i++) {
				doms[i] = new IMDomain(opts, (DomainDefinition) domains.elementAt(i));
			}
			all_doms.put(sys_name,doms);
		}
		return doms;
	}
}
