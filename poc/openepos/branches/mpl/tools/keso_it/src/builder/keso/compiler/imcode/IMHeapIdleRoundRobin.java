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
import keso.compiler.config.DomainDefinition;
import keso.compiler.config.NativeResource;
import keso.compiler.config.NativeTask;
import keso.compiler.config.TaskDefinition;
import keso.compiler.config.ResourceDefinition;
import keso.compiler.config.SystemDefinition;
import keso.compiler.config.StringAttr;

import java.util.Hashtable;
import java.util.Vector;
import java.io.PrintStream;

/**
 * Heap divided into slots of a configurable size with a simple garbage
 * collector shared among all instances of this Heap implementation that
 * cleans memory of participating domains in a round robin manner when the
 * system is idle.
 * <p>
 * The Garbage collector uses a bitmap that contains one bit for each slot.
 * The bitmap will be shared for all domains and must, therefore the domain with
 * the most slots determines its size.
 * <p>
 * The Garbage collector runs with the lowest priority in the system and
 * therefore only when all the other tasks are in suspended or waiting state.
 * The GC therefore only needs to scan static fields and immortal objects. If
 * there are waiting tasks, their stack also needs to be scanned which can be
 * achieved with use of the linked object list.
 * <p>
 * Free Memory is managed with a linked list, contained directly in the free
 * memory slots (slotsize must at least be 4 bytes, which is the current size
 * of the object header). The linked list is regenerated from the bitmap
 * during each GC cycle in order to merge free slots.
 * The listdata will be saved in a list_el structure at the beginning of each
 * free block, containing the size of the block in slots and the offset in
 * slots to the next free block.
 */
public class IMHeapIdleRoundRobin extends IMHeap {

	/* Resources used to synchronize stack scanning */
	private static Hashtable gcresources = new Hashtable();
	private static int createdResources = 0;

	/* Definition of the GC Task */
	private static NativeTask gctask;

	/**
	 * Contains the domain_ids of all domains that
	 * make use of this Heaptype.
	 */
	private static Vector domainids  = new Vector();
	private static Vector domainresources = new Vector();

	/**
	 * Flag to indicate if one-time translation parts like the implementations
	 * functions have already been emitted or not.
	 */
	private static SystemDefinition once = null;

	public IMHeapIdleRoundRobin(IMDomain dom, BuilderOptions opts, int size, ComplexAttribute heapdef) 
			throws CompileException {
		super("irr", "irr", size,opts,dom,heapdef);

		ResourceDefinition gcresource = enhanceSysDef();

		IMGlobalHeap gheap = opts.getGlobalHeap();
		gheap.enableWriteBarriers();
		gheap.enableStackScan();

		// pointer to the beginning of the heap
		addDescriptorField("irr_listel_t*", "heap_top", "(irr_listel_t*)"+heap_ident, false);
		// pointer to the list of free blocks
		addDescriptorField("volatile irr_listel_t*", "freemem", "(irr_listel_t*) "+heap_ident, false);
		// heapsize in bytes
		addDescriptorField("unsigned int", "heap_size", String.valueOf(heap_size), false);
		// size of one slot in bytes
		addDescriptorField("unsigned char", "slotSize", new Integer(slotSize).toString(), false);
		// value of the colorbit of this domain
		addDescriptorField("unsigned char", "colorbit", "2", false);
		if (slotCnt<(2^16)) {
			// free slots on the heap
			addDescriptorField("unsigned short", "freeslots", Integer.toString(slotCnt), false);
			// slots allocated since last scan
			addDescriptorField("unsigned short", "sasls", "0", false);
		} else {
			// free slots on the heap
			addDescriptorField("unsigned long", "freeslots", Integer.toString(slotCnt), false);
			// slots allocated since last scan
			addDescriptorField("unsigned long", "sasls", "0", false);
		}

		// add Resource to tasks that use waitevent
		for(int i=0; i<dom.getDef().getTasks().size(); i++) {
			TaskDefinition task = (TaskDefinition) dom.getDef().getTasks().elementAt(i);
			if(task.getEvents().size()>0) task.addResource(gcresource);
		}

		domainresources.add( ((gcresource==null)?"KESO_IRR_NOGCRESOURCE":gcresource.getIdentifier()));
		domainids.add(new Integer(dom.getDomainID()));
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
			coder.beginClassMethod(this);

			// define a value for domains that need no gc resource
			coder.local_add("#define KESO_IRR_NOGCRESOURCE ~0\n");

			// define the maximum number of slots
			coder.local_add("#define KESO_IRR_MAXSLOTS ");
			coder.local_add(maxslots);

			coder.local_add("\n");
			coder.local_add("static unsigned int bitmap[(KESO_IRR_MAXSLOTS+7)/(sizeof(unsigned int)*8)];\n");

			// IRRGC Task modes
			switch (gctmode) {
				case WORKAHOLIC:
					gctask.setAutoStart(true);
					break;
				case ANY:
					gctmode=0;
					break;
			}
			coder.local_add("#define KESO_IRRGCT_MODE_");
			coder.local_add(modes[gctmode]);
			coder.local_add(" 1\n");

			// make listel type known
			//coder.global_header_add_class(this);

			// generate module with this heap's functions
			coder.local_add("#include \"");
			coder.local_add(alias);
			coder.local_add(".h\"\n\n");
			addDomainArray(coder, domainids, domainresources);
			coder.add_fkt("heap/irr-alloc.c");
			coder.add_fkt("heap/irr-gc.c");
			//coder.add_fkt("heap/gc-common.c");
			coder.endClassMethod();
			coder.endHeader();
		}
	}

	private void addDomainArray(Coder coder, Vector domains, Vector resources) throws CompileException {
		coder.local_add("#define MANAGEDDOMAINS ");
		coder.local_add(domains.size());
		coder.local_add("\n");

		coder.local_add("#define DOMAINID() (domains[curdom]");
		if(resources!=null && createdResources>1) coder.local_add("[0]");
		coder.local_add(")\n");

		if(resources!=null) {
			if(createdResources>1) {
				coder.local_add("#define GCRESOURCE() (domains[curdom][1])\n");
			} else {
				coder.local_add("#define GCRESOURCE() ");
				coder.local_add((String)resources.elementAt(0));
				coder.local_add("\n");
			}
		}

		coder.local_add("static unsigned char domains[");
		coder.local_add(domains.size());
		coder.local_add("]");
		if(resources!=null && createdResources>1) coder.local_add("[2]");
		coder.local_add(" = { ");
		for(int i=0; i<domains.size(); i++) {
			if(i>0) coder.local_add(", ");
			if(resources!=null && createdResources>1) coder.local_add("{ ");
			coder.local_add( ((Integer) domains.elementAt(i)).toString() );
			if(resources!=null && createdResources>1) {
				coder.local_add(", ");
				coder.local_add((String)resources.elementAt(i));
				coder.local_add("}");
			}
		}
		coder.local_add(" };\n");
	}

	public void initHeap(PrintStream out) throws CompileException {
		out.println("\t((irr_listel_t*) " + heap_ident + ")->size = " + (heap_size/slotSize) + ";");
		out.println("\t((irr_listel_t*) " + heap_ident + ")->next = (irr_listel_t*) -1;");
		out.println("\t((irr_listel_t*) " + heap_ident + ")->alloccolor = 0;");
		out.println("\t((irr_listel_t*) " + heap_ident + ")->mode = 0;");
	}

	private ResourceDefinition enhanceSysDef() {
		NativeResource gcresource;
		String groupname;
		boolean createResource=false;
		SystemDefinition sys  = opts.getSysDef();

		// create GC Task
		if(gctask==null) {
			gctask = new NativeTask(sys, "keso_irr_gc");
			gctask.setSchedule(true);
			gctask.setStackSize(200);
			sys.addSysTask(gctask);
		}

		// Check if this domain needs a gc resource
		Vector tasks = domain.getDef().getTasks();
		for(int i=0; i<tasks.size(); i++) {
			TaskDefinition task = (TaskDefinition) tasks.elementAt(i);
			if(task.getEvents().size()>0) { createResource=true; break; }
		}
		if(createResource==false) return null;

		try {
			groupname = heapdef.getAttribute("Group").valueString();
		} catch (NullPointerException e) {
			groupname="Default";
		}
		gcresource = (NativeResource)gcresources.get(groupname);
		if(gcresource != null) return gcresource;


		// create GC Resource
		gcresource = new NativeResource(sys, "IRRHEAP_"+groupname);
		createdResources++;
		gcresources.put(groupname, gcresource);

		sys.addSysResource(gcresource);
		gctask.addResource(gcresource);
		return gcresource;
	}
}
