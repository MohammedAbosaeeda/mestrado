/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.compiler.config;

import keso.compiler.*;

import java.lang.Integer;
import java.util.Vector;

public class EventDefinition extends Set {
	/** Mask of this event. A power of 2 */
	private int mask;

	/** Contains TaskDefinitions of all owner Tasks.
	 * Used to find a mask
	 */
	private Vector ownerTasks;

	private boolean systemEvent = false;
	
	public EventDefinition(Set parent, String name, int line) {
		super(parent, name, line);
		ownerTasks = new Vector();
	}

	public EventDefinition(Set parent, String name) {
		super(parent, name, -1);
		systemEvent=true;
		ownerTasks = new Vector();
	}

	public void addAttribute(String ident, Attribut value) {
		super.addAttribute(ident, value);
		
		// AUTO masks need to be calculated in the finalizer when
		// all owning tasks are known
		if(ident.compareToIgnoreCase("Mask")!=0) return;
		String maskstr = value.valueString();
		if(maskstr.compareToIgnoreCase("AUTO")==0) return;
		
		try {
			if (maskstr.startsWith("0x")) {
				mask = Integer.parseInt(maskstr.substring(2), 16);
			} else {
				mask = Integer.parseInt(maskstr);
			}
		} catch (java.lang.NumberFormatException e) {
			mask = 0;
			BuilderOptions.getOpts().verbose("Event " + getIdentifier() + "'s mask cannot be parsed. AUTO assigned.");
			return;
		}

		if (bitCount(mask)!=1) {
			BuilderOptions.getOpts().verbose("Event " + getIdentifier() + "'s mask is not a power of 2. AUTO assigned.");
			mask = 0;
		}
	}

	/**
	 * Determine the number of bits set in an int.
	 *
	 * @param i is the Integer whose bits will be checked
	 * @return the number of bits set in i
	 */
	private static int bitCount(int i) {
		int cnt=0;

		do {
			cnt += i&1;
			i >>>= 1;
		} while (i!=0);

		return cnt;
	}

	public String toString() { return "Event:"+ ident; }

	/**
	 * Generate a EVENT block in OIL syntax out of this EVENT Definition.
	 *
	 * May be used only if already finalized
	 *
	 * Known and supported attributes
	 * OIL standard:
	 *   MASK		(UINT64)
	 */
	public void toOIL(StringBuffer oil, String prefix) throws CompileException {
	
		if (mask==0) throw new CompileException("Mask not set for Event "+getIdentifier()+". Finalize first!");
		
		oil.append(prefix+"EVENT " + getIdentifier() + " {\n");
		oil.append(prefix+"\tMASK = 0x");
		oil.append(Integer.toHexString(mask));
		oil.append(";\n");
		oil.append(prefix+"};\n\n");
	}

	public String getIdentifier() {
		if (systemEvent) return ident;
		return super.getIdentifier();
	}

	protected void finalizeCfg(FinalizeResult res) throws keso.compiler.config.parser.ParseException {
		// find out mask, all owning tasks must have registered themselves
		if (mask == 0) {
			int combinedMask=0;
			for(int i=0; i<ownerTasks.size(); i++) {
				TaskDefinition task = (TaskDefinition) ownerTasks.elementAt(i);
				combinedMask |= task.getEvBits();
			}

			// now find the least significant cleared bit
			for(int i=0;i<32;i++) {
				mask = 1<<i;
				if (0== (combinedMask & mask)) break;
			}
		}

		// mark mask's bit used at all owner tasks
		for(int i=0; i<ownerTasks.size(); i++) {
			TaskDefinition task = (TaskDefinition) ownerTasks.elementAt(i);
			task.registerEvMask(mask);
		}
	}

	public int getMask() throws CompileException {
		if(mask==0) throw new CompileException("getMask(): finalize first");
		return mask;
	}

	protected String typeID() { return "e_"; }

	protected void registerTask(TaskDefinition task) {
		ownerTasks.add(task);
	}
}
