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
import keso.compiler.config.NativeTask;
import keso.compiler.config.TaskDefinition;
import keso.compiler.config.SystemDefinition;
import keso.compiler.config.StringAttr;

import java.util.Hashtable;
import java.util.Vector;
import java.io.PrintStream;

/**
 * The coffee break GC.
 *
 * Heap is divided into slots of a configurable size with a simple garbage
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
public class IMHeapCoffeeBreak extends IMHeap {
	/* Definition of the GC Task */
	private static NativeTask gctask;

	/**
	 * Contains the domain_ids of all domains that
	 * make use of this Heaptype.
	 */
	private static Vector domainids  = new Vector();

	/**
	 * Flag to indicate if one-time translation parts like the implementations
	 * functions have already been emitted or not.
	 */
	private static SystemDefinition once = null;

	public IMHeapCoffeeBreak(IMDomain dom, BuilderOptions opts, int size, ComplexAttribute heapdef) 
			throws CompileException {
		super("coffee", "coffee", size,opts,dom,heapdef);
		
		enhanceSysDef();

		IMGlobalHeap gheap = opts.getGlobalHeap();
		gheap.enableStackScan();
		gheap.enableObjColor();

		if (gctmode==ENFORCEONDEMAND) 
			gheap.enableBlockingAllocation();

		if (gctmode==DEFERTASKSONDEMAND) 
			gheap.enableDeferTasks();


		// pointer to the beginning of the heap
		addDescriptorField("coffee_listel_t*", "heap_top", "(coffee_listel_t*)"+heap_ident, false);
		// pointer to the list of free blocks
		addDescriptorField("coffee_listel_t*", "freemem", "(coffee_listel_t*) "+heap_ident, false);
		// size of one slot in bytes
		addDescriptorField("unsigned char", "slotSize", new Integer(slotSize).toString(), false);
		// value of the colorbit of this domain
		addDescriptorField("unsigned char", "colorbit", "1", false);
		if (slotCnt<(2^8)) {
			// number of slots
			addDescriptorField("unsigned char", "numslots", new Integer(slotCnt).toString(), false);
			// free slots on the heap
			addDescriptorField("unsigned char", "freeslots", Integer.toString(slotCnt), false);
			// slots allocated since last scan
			addDescriptorField("unsigned char", "sasls", "0", false);
		} else if (slotCnt<(2^16)) {
			// number of slots
			addDescriptorField("unsigned short", "numslots", new Integer(slotCnt).toString(), false);
			// free slots on the heap
			addDescriptorField("unsigned short", "freeslots", Integer.toString(slotCnt), false);
			// slots allocated since last scan
			addDescriptorField("unsigned short", "sasls", "0", false);
		} else {
			// number of slots
			addDescriptorField("unsigned long", "numslots", new Integer(slotCnt).toString(), false);
			// free slots on the heap
			addDescriptorField("unsigned long", "freeslots", Integer.toString(slotCnt), false);
			// slots allocated since last scan
			addDescriptorField("unsigned long", "sasls", "0", false);
		}

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

			// define the maximum number of slots
			coder.local_add("#define KESO_COFFEE_MAXSLOTS ");
			coder.local_add(maxslots);
			coder.local_add("\n");
			coder.local_add("static unsigned int bitmap[(KESO_COFFEE_MAXSLOTS+7)/(sizeof(unsigned int)*8)];\n");
			// generate module with this heap's functions
			coder.local_add("#include \"");
			coder.local_add(alias);
			coder.local_add(".h\"\n\n");
			addDomainArray(coder, domainids);

			coder.local_add("#define COFFEE_GC_LOCK() DisableAllInterrupts()\n");
			coder.local_add("#define COFFEE_GC_UNLOCK() EnableAllInterrupts()\n");

			if (opts.hasOption("gc_log_unix")) {
				coder.add_fkt("heap/log-unix.c");
			} else if (opts.hasOption("gc_log_i4trace")) {
				coder.add_fkt("heap/log-i4trace.c");
			} else {
				coder.add_fkt("heap/log-none.c");
			}

			coder.add_fkt("heap/coffee-break-alloc.c");

			coder.add_fkt("heap/coffee-break-gc.c");
			if (opts.hasOption("gc_dbg_bitmap")) 
				coder.add_fkt("heap/bitmap_dbg.c");
			coder.endClassMethod();
			coder.endHeader();
		}
	}

	private void addDomainArray(Coder coder, Vector domains) throws CompileException {
		coder.local_add("#define MANAGEDDOMAINS ");
		coder.local_add(domains.size());
		coder.local_add("\n");

		coder.local_add("#define DOMAINID() (domains[curdom])\n");

		coder.local_add("static unsigned char domains[");
		coder.local_add(domains.size());
		coder.local_add("]");
		coder.local_add(" = { ");
		for(int i=0; i<domains.size(); i++) {
			if(i>0) coder.local_add(", ");
			coder.local_add( ((Integer) domains.elementAt(i)).toString() );
		}
		coder.local_add(" };\n");
	}

	public void initHeap(PrintStream out) throws CompileException {
		out.println("\t((coffee_listel_t*) " + heap_ident + ")->size = " + (slotCnt) + ";");
		out.println("\t((coffee_listel_t*) " + heap_ident + ")->next = (coffee_listel_t*)0;");
	}

	private void enhanceSysDef() {
		// create GC Task
		if(gctask!=null) return;
		
		SystemDefinition sys  = opts.getSysDef();
		gctask = new NativeTask(sys, "keso_coffee_gc");
		gctask.setSchedule(true);
		gctask.setStackSize(200);
		sys.addSysTask(gctask);
	}
}
