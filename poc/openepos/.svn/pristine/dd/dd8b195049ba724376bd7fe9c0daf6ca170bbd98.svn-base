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

import java.util.Hashtable;
import java.io.PrintStream;

/**
 * Abstract superclass of all heap implementations.
 */
public abstract class IMHeap extends IMDescriptorContainer {

	/**
	 * Size of this heap.
	 */
	protected final int heap_size;

	/**
	 * System-wide unique identifier of this heap.
	 */
	protected final String heap_ident;

	/**
	 * Domain this heap belongs to.
	 */
	protected final IMDomain domain;

	/**
	 * Definition of this heap from kesorc.
	 */
	protected final ComplexAttribute heapdef;

	/**
	 * Size of a slot on the heap.
	 */
	public final int slotSize;

	/**
	 * Number of slots used by the heap.
	 */
	public final int slotCnt;

	/**
	 * max #slots of any instance created.
	 */
	protected static int maxslots=0;

	/**
	 * Remember the gctmode of previous domains
	 */ 
	public final static int ANY                = -1;
	public final static int LAZY               = 0;
	public final static int WORKAHOLIC         = 1;
	public final static int ENFORCEONDEMAND    = 2;
	public final static int DEFERTASKSONDEMAND = 3;

	protected static final String[] modes = {
		"LAZY",
		"WORKAHOLIC",
		"ENFORCEONDEMAND",
		"DEFERTASKSONDEMAND",
	};

	public static int gctmode = ANY;

	public IMHeap(String name, String alias, int size, BuilderOptions opts, IMDomain domain, ComplexAttribute heapdef) {
		super(name,alias,opts);

		heap_ident = domain.getIdentifier()+"_heap";
		this.domain = domain;
		this.heapdef = heapdef;

		if (heapdef.getAttribute("slotSize")==null) {
			this.slotSize = 8;
		} else {
			int ss = heapdef.getAttribute("slotSize").valueInt();
			this.slotSize = alignSlotSize(ss);
			if (this.slotSize!=ss) opts.warn("Given slot size is invalid! use: "+this.slotSize);
		}

		slotCnt = size/slotSize;
		heap_size = slotCnt * slotSize;
		if (slotCnt>maxslots) maxslots=slotCnt;

		if (heapdef.getAttribute("GCTMode") != null ) {
			String gctmodestring = heapdef.getAttribute("GCTMode").valueString().toUpperCase();

			int gmode=0;
			while (gmode<modes.length) {
				if (modes[gmode].equals(gctmodestring)) break;
				gmode++;
			}

			if (gmode>=modes.length) {
				opts.warn("Unknown GC mode: "+gctmodestring);
			} else {
				if (gctmode<0) {
					gctmode = gmode;
				} else if (gctmode != gmode) {
					if (gctmode<gmode) gctmode = gmode;
					opts.warn("All Domains need the same GC mode! use: "+modes[gctmode]);
				}
			}
		}
	}

	/**
	 * Check if a slot size is valid. 
	 *
	 * slot size must be a power of 2.
	 * minimal slot size is 8.
	 * maximal slot size is 128.
	 */
	private static int alignSlotSize(int ss) {
		for (int mins=8;mins<=128;mins=mins<<1) {
			if (ss<=mins) return mins;
		}
		return 128;
	}

	/**
	 * Get the name of the allocator function belonging to this
	 * heap's type.
	 */
	public String getAllocatorName() { return "keso_"+name+"_alloc"; }

	/**
	 * Common tasks used by many if not all implementations.
	 */
	protected void translate(Coder coder) throws CompileException {
		// create heap's memory
		coder.global_header_add("extern char ");
		coder.global_header_add(heap_ident);
		coder.global_header_add("[");
		coder.global_header_add(heap_size);
		coder.global_header_add("];\n");

		coder.global_add("char ");
		coder.global_add(heap_ident);
		coder.global_add("[");
		coder.global_add(heap_size);
		if (opts.isAVR()) {
			coder.global_add("] HEAP_SECTION ;\n");
		} else {
			coder.global_add("] ALIGN4 HEAP_SECTION ;\n");
		}

		if (gctmode==ANY) gctmode = LAZY;
		coder.global_header_add("#define KESO_GCMODE_");
		coder.global_header_add(modes[gctmode]);
		coder.global_header_add(" 1\n");

		coder.global_header_add("#define KESO_SEND_GCRUN_EVENTS() ");
		if (gctmode==ENFORCEONDEMAND || gctmode==DEFERTASKSONDEMAND) {
			coder.global_header_add("{ int _t; for (_t=0;_t<KESO_MAX_TASK;_t++) ");
			coder.global_header_add("SetEvent(_t, KESO_GCRun); }");
		}
		coder.global_header_add("\n");

		coder.global_header_add("#define KESO_GC_DEFER_TASK() ");
		if (gctmode==DEFERTASKSONDEMAND) {
			if (false) {
				coder.global_header_add("if (DOMAINDESC(KESO_CURRENT_DOMAIN).heap.coffee.freeslots<");
				coder.global_header_add("(DOMAINDESC(KESO_CURRENT_DOMAIN).heap.coffee.numslots>>1)");
				coder.global_header_add(") { WaitEvent(KESO_GCRun); ClearEvent(KESO_GCRun); }");
			} else {
				coder.global_header_add("{ if (DOMAINDESC(KESO_CURRENT_DOMAIN).heap.coffee.freeslots<");
				coder.global_header_add("(DOMAINDESC(KESO_CURRENT_DOMAIN).heap.coffee.numslots>>1)");
				coder.global_header_add(") keso_defer_task(); }");
				coder.global_header_add("\nvoid keso_defer_task();");
				coder.global_add("void keso_defer_task() {\n");
				coder.global_add("\tWaitEvent(KESO_GCRun);ClearEvent(KESO_GCRun);\n");
				coder.global_add("}\n");
			}
		}
		coder.global_header_add("\n");
	} 

	public void initHeap(PrintStream out) throws CompileException {}
}
