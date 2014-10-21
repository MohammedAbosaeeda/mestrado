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

import java.util.*;
import keso.util.IntegerHashtable;

final public class MethodTable {

	final private static Integer ZERO  = new Integer(0);
	final private static Integer JOKER = new Integer(-1);

	private IMClass	top_class;
	private IMMethod top_method;
	private BuilderOptions opts;
	private IntegerHashtable candidates;
	private IntegerHashtable entries;

	private int top_classid;
	private int mt_offset;
	private int table_size = -1;

	/* for index_table only */
	private Vector index_seq;
	private int addr_offset;

	public MethodTable(IMClass tcls) {
		this.top_class=tcls;
		this.top_classid=tcls.getClassTypeID();
		opts=tcls.getOptions();
		candidates = new IntegerHashtable();
		entries = new IntegerHashtable();
	}

	public void joinTables(MethodTable old) throws CompileException {
		//old.entries=null;
		Enumeration ee = old.candidates.elements();
		while (ee.hasMoreElements()) {
			IMMethod method = (IMMethod)ee.nextElement();
			addMethod(method.getIMClass().getClassTypeID(), method);
			method.setMethodTable(this);
		}
		//old.candidates=null;
		//top_class=null;
	}

	/**
	 * add a method to the interface table
	 */
	public void addMethod(int class_id, IMMethod method) throws CompileException {
		if (method==null) throw new CompileException();
		IMClass clazz = method.getIMClass();
		if (class_id<top_classid) {
			top_method=method;
			top_class=clazz;
			top_classid = class_id;
		}
		if (top_method==null) { top_method=method; }
		candidates.put(clazz.getClassTypeID(), method);
		entries.put(class_id, method);
	}

	public void tableIsUsedBy(IMMethod method) {
		//if (top_method==null) top_method=method;
	}

	public Enumeration getCandidates() {
		return candidates.elements();
	}

	public boolean hasDispatchTable() {
		if (opts.hasOption("vt_full")) return true;
		return (candidates.size()>1);
	}

	public void emit_invoke_makro(IMMethod current_method, Coder coder) throws CompileException {
		if (!hasDispatchTable()) return;
		IMMethod method = top_method;

		if (top_method!=current_method) {
			coder.header_add("#include \"");
			coder.header_add(top_method.getIMClass().getAlias());
			coder.header_addln(".h\"");
			coder.header_add("/* import ");
			coder.header_add(method.getInvokeAlias());
			coder.header_addln(" */");
			coder.header_add("#define ");
			coder.header_add(current_method.getInvokeAlias());
			coder.header_add(" ");
			coder.header_addln(top_method.getInvokeAlias());
		} else {
			coder.header_add("#define ");
			coder.header_add(method.getInvokeAlias());
			if (opts.hasOption("vt_switch")) {
				coder.header_add("(_self_) ");
				coder.header_add(method.getIdentifier());
				coder.header_addln("_switch");
			} else if (opts.hasOption("vt_comp") && isCompressed()) {
				coder.header_add("(_self_) ((");
				coder.header_add(method.getReturnType());
				coder.header_add(" (*)");
				coder.header_add(method.getArgString());
				if (is16BitIndex()) {
					coder.header_add(")addr16_table[");
				} else {
					coder.header_add(")addr_table[");
				}
				coder.header_add(addr_offset);
				if (is16BitIndex()) {
					coder.header_add("+index16_table[");
				} else {
					coder.header_add("+index_table[");
				}
				coder.header_add(mt_offset);
				coder.header_addln("+(_self_)->class_id]])");
			} else {
				coder.header_add("(_self_) ((");
				coder.header_add(method.getReturnType());
				coder.header_add(" (*)");
				coder.header_add(method.getArgString());
				coder.header_add(")dispatch_table[");
				coder.header_add(mt_offset);
				coder.header_addln("+(_self_)->class_id])");
			}
		}
	}

	public int tableSize() {
		if (table_size<0) {
			int[] keys = entries.sortedKeys();
			if (keys.length==0) {
				table_size=0;
			} else {
				table_size=keys[keys.length-1]-top_classid+1;
			}
		}
		return table_size;
	}

	public boolean is16BitIndex() {
		int nc = candidates.size();
		if (nc>254) return true;
		return false;
	}

	public boolean isCompressed() {
		if (opts.hasOption("vt_comp_all")) return true;

		/* more then 254 candidates therefor we need 
		 * two bytes per index entry. The compression will only work
		 * if we need more then two bytes for the address!
		 */
		int nc = candidates.size();
		if (nc>254 && opts.targetAddrSize()<4) return false;

		/* prefer small tables! */
		int ts = tableSize();
		if (opts.hasOption("vt_comp_lt6") && ts < 6) {
			if (top_class.isAbstract()) return true;
		}

		int iesize = (nc>254?2:1);
		int saving = (ts-nc) * opts.targetAddrSize();
		int isize = (iesize*ts)+1;

		return (saving > isize);
	}

	public boolean emit_call(Coder coder, IMNode self) throws CompileException {
		if (top_method==null) {
			if (candidates.size()==0 && top_class.isInterface()) {
				coder.add("keso_throw_method_not_implemented(\"");
				coder.add(self.getMethod().getAlias());
				coder.add("\",");
				coder.add(self.getBCPosition());
				coder.add(")");
				return false;
			}
			if (candidates.size()==0 && top_class.isAbstract()) {
				coder.add("keso_throw_method_not_implemented(\"");
				coder.add(self.getMethod().getAlias());
				coder.add("\",");
				coder.add(self.getBCPosition());
				coder.add(")");
				return false;
			}
			/* never reached */
			Enumeration e = candidates.elements();
			while (e.hasMoreElements()) {
				IMMethod m = (IMMethod)e.nextElement();
				System.err.println(m.getAlias());
			}
			throw new CompileException("top_method == null");
		}
		if (!hasDispatchTable()) {
			if (top_class!=null) coder.add_class(top_class.getAlias());
			coder.add(top_method.getIdentifier());
			return true;
		} else {
			if (top_class!=null) coder.add_class(top_class.getAlias());
			coder.add(top_method.getInvokeAlias());
			coder.add('(');
			self.translate(coder);
			coder.add(')');
		}
		return true;
	}

	public IMMethod getFirstCandidate() throws CompileException {
		return top_method;
	}
	
	public void emit_switch(Coder coder) throws CompileException {
		if (!hasDispatchTable()) return;

		IMMethod m = top_method;
		int top_id = top_class.getClassTypeID();
		boolean has_return_value = m.hasReturnValue();

		if (m!=null && m.getMethodNameAndType().equals("finalize()V")) {
			coder.global_header_add("#define KESO_NEED_FINALIZE 1\n");
			coder.global_header_add("#define KESO_INVOKE_FINALIZE(_self_) ");
			coder.global_header_add(m.getIdentifier());
			coder.global_header_add("_switch\n");
		}

		coder.global_header_add(m.getReturnType());
		coder.global_header_add(" ");
		coder.global_header_add(m.getIdentifier());
		coder.global_header_add("_switch");
		coder.global_header_add(m.getArgString());
		coder.global_header_add(";\n");

		coder.global_add(m.getReturnType());
		coder.global_add(" ");
		coder.global_add(m.getIdentifier());
		coder.global_add("_switch");
		coder.global_add(m.getArgString());
		coder.global_add(" {\n");
		coder.global_add("\tswitch (obj0->class_id) {\n");

		int[] keys = entries.sortedKeys();
		for (int i=0;i<keys.length;i++) {
			IMMethod method = (IMMethod)entries.get(keys[i]);
			coder.global_add("\t\tcase ");
			coder.global_add(keys[i]);
			coder.global_add(":\n\t\t\t");
			if (has_return_value) coder.global_add("return ");
			coder.global_add(method.getIdentifier());
			coder.global_add(method.getArgReCallString());
			coder.global_add(";\n");
			if (!has_return_value) coder.global_add("\t\t\treturn;\n");
		}
		coder.global_add("\t}\n");
		//coder.global_add("\tthrow_error(\"\");\n");
		coder.global_add("}\n\n");
	}

	public int emit_addr_table(Coder coder, int offset) throws CompileException {
		int nc = candidates.size();
		if (!hasDispatchTable()) return offset;

		this.addr_offset = offset;

		IMMethod m = top_method;	
		coder.global_add("/* ");
		coder.global_add(m.getMethodNameAndType());
		coder.global_add(" candidates ");
		coder.global_add(nc);
		coder.global_add(" size ");
		int ts = tableSize();
		coder.global_add(ts);
		coder.global_add(" save ");
		int addr_size = opts.targetAddrSize();
		coder.global_add((ts*addr_size)-(nc*addr_size+ts));
		coder.global_add(" */\n");
		int[] keys = candidates.sortedKeys();
		for (int i=0;i<keys.length;i++) {
			IMMethod method = (IMMethod)candidates.get(keys[i]);

			if (method.hasCode()) {
				coder.global_add("/* ");
				coder.global_add(offset);
				coder.global_add(" */ \t((code_t)");
				coder.global_add(method.getIdentifier());
				coder.global_add("),\n");
				method.setAddrSlot(offset-this.addr_offset);
				offset++;
			} 
		}

		return offset;
	}

	private void createIndexSeq() throws CompileException {
		index_seq = new Vector();

		int[] keys = entries.sortedKeys();
		int top_id = top_class.getClassTypeID();
		for (int i=0;i<keys.length;i++) {
			IMMethod method = (IMMethod)entries.get(keys[i]);
			int ccid = keys[i];

			while (top_id!=ccid) {
				/* we have a hole in the method table if the top_id
				 * is not equal the current id. This may happen because
				 * of a interface. */
				index_seq.add(JOKER);
				top_id++;
			}

			if (method.hasCode()) {
				/* we must match the same index number here! */
				index_seq.add(new Integer(method.getAddrSlot()));
			} else {
				/* the value are not of interest! */
				index_seq.add(JOKER);
			}
			top_id++;
		}
	}

	private boolean matchSeqFussy(int pos, Vector t, Vector p) {
		for (int i=0;i<p.size();i++) {
			if (pos+i>=t.size()) return true;
			int ti = ((Integer)t.elementAt(pos+i)).intValue();
			if (ti<0) continue;
			int pi = ((Integer)p.elementAt(i)).intValue();
			if (pi<0) continue;
			if (ti!=pi) return false;
		}
		return true;
	}

	private void patchSeq(int pos, Vector t, Vector p) {
		for (int i=0;i<p.size();i++) {
			if (pos+i>=t.size()) {
				t.add(p.elementAt(i));
			} else {
				Integer pi = (Integer)p.elementAt(i);
				if (pi==JOKER) continue;
				t.setElementAt(pi, pos+i);
			}
		}
	}

	private Vector includeSeq(Vector global, Vector seq) throws CompileException {

		for (int i=0;i<global.size();i++) {
			if (matchSeqFussy(i, global, seq)) {
				patchSeq(i, global, seq);
				return global;
			}
		}

		for (int j=0;j<seq.size();j++) {
			global.add(seq.elementAt(j));
		}

		return global;
	}

	private Vector includeSeqBest(Vector global, Vector seq) throws CompileException {

		for (int i=0;i<global.size();i++) {
			if (matchSeqFussy(i, global, seq)) {
				patchSeq(i, global, seq);
				return global;
			}
		}

		for (int j=0;j<seq.size();j++) {
			global.add(seq.elementAt(j));
		}

		return global;
	}


	public Vector updateIndexSeq(Vector current) throws CompileException {
		/* nothing todo */
		if (!hasDispatchTable()) return current;

		if (index_seq==null) createIndexSeq();

		if (current==null) return index_seq;

		if (opts.hasOption("vt_comp_fast")) {
			for (int j=0;j<index_seq.size();j++) {
				current.add(index_seq.elementAt(j));
			}
			return current;
		}

		if (index_seq.size()>current.size()) {
			current=includeSeq(index_seq, current);
			index_seq=null;
			return current;
		}

		if (opts.hasOption("vt_comp_best")) {
			return includeSeqBest(current, index_seq);
		}

		return includeSeq(current, index_seq);
	}

	static private int findSeq(Vector t, Vector p) {

		/*
		 * TODO: Such-Algortihmus optimieren.
		 *
		 * z.B.
		 * Knuth-Morris-Pratt-Algorithmus 
		 * http://www.iti.fh-flensburg.de/lang/algorithmen/pattern/kmp.htm
		 *
		 * Boyer-Moore-Algorithmus
		 * http://www.iti.fh-flensburg.de/lang/algorithmen/pattern/bm.htm
		 * Wohl eher ungeeignet, da der Symbol-Vorrad seher klein ist.
		 *
		 */

		int n = t.size() - (p.size()-1);
		int m = p.size();
		for (int j=0;j<n;j++) {
			boolean found = true;
			for (int i=0;i<m;i++) {
				int pi = ((Integer)p.elementAt(i)).intValue();
				int ti = ((Integer)t.elementAt(j+i)).intValue();
				if (pi<0) continue;
				if (ti!=pi) {
					//System.err.println("fail j="+j+" pi["+i+"]="+pi+" ti["+(j+i)+"]="+ti);
					found = false;
					break;
				}
			}
			if (found) { 
				//System.err.println("");
				return j;
			}
		}
		System.err.println("global:");
		dump_seq(t);
		dump_seq(p);
		throw new RuntimeException("seq not found!");
	}

	static private void dump_seq(Vector t) {
		Enumeration e = t.elements();
		while (e.hasMoreElements()) {
			Integer i = (Integer)e.nextElement();
			if (i==JOKER) {
				System.err.print("?");
			} else {
				System.err.print(i);
			}
			System.err.print(",");
		}
		System.err.print("\n");
	}

	public void updateOffset(Vector current) throws CompileException {
		/* nothing todo */
		if (!hasDispatchTable()) return;
		if (index_seq==null) createIndexSeq();

		int start_off = findSeq(current, index_seq);
		this.mt_offset = start_off-top_class.getClassTypeID();

		return;
	}

	public int emit_table(Coder coder, int offset) throws CompileException {
		int nc = candidates.size();

		if (!hasDispatchTable()) return offset;

		IMMethod m = top_method;	
		coder.global_add("/* ");
		if (m==null) {
			coder.global_add("null");
		} else {
			coder.global_add(m.getMethodNameAndType());
			coder.global_add(" \t off:");
			coder.global_add(offset);
			coder.global_add(" cls:");
			coder.global_add(top_class.getClassTypeID());
			coder.global_add(" len:");
			int ts = tableSize();
			coder.global_add(ts);
			coder.global_add(" candidates:");
			coder.global_add(nc);
		}
		coder.global_add(" */\n");

		int top_id = top_class.getClassTypeID();
		this.mt_offset = offset - top_id;

		if (m!=null && m.getMethodNameAndType().equals("finalize()V")) {
			coder.global_header_add("#define KESO_NEED_FINALIZE 1\n");
			coder.global_header_add("#define KESO_INVOKE_FINALIZE(_self_) ((");
			coder.global_header_add(m.getReturnType());
			coder.global_header_add(" (*)");
			coder.global_header_add(m.getArgString());
			coder.global_header_add(")dispatch_table[");
			coder.global_header_add(mt_offset);
			coder.global_header_add("+(_self_)->class_id])\n");
		}

		int[] keys = entries.sortedKeys();
		for (int i=0;i<keys.length;i++) {
			IMMethod method = (IMMethod)entries.get(keys[i]);
			int ccid = keys[i];

			while (top_id!=ccid) {
				coder.global_add("/* ");
				coder.global_add(offset);
				coder.global_add(" */ \t((code_t)0), /* hole */\n");
				top_id++;
				offset++;
			}

			coder.global_add("/* ");
			coder.global_add(offset);
			coder.global_add(" */ \t((code_t)");

			if (method.hasCode()) {
				coder.global_add(method.getIdentifier());
				coder.global_add("),\n");
			} else {
				coder.global_add("0),\n");
			}
			top_id++;
			offset++;
		}

		return offset;
	}

	public String toString() {
		StringBuffer msg = new StringBuffer("top ");
		msg.append(top_class);
		msg.append(" id ");
		msg.append(top_classid);
		msg.append(" offset ");
		msg.append(mt_offset);
		msg.append(" method ");
		if (top_method!=null) { msg.append(top_method.getMethodNameAndType()); }
		else { msg.append("null"); }
		return msg.toString();
	}
}
