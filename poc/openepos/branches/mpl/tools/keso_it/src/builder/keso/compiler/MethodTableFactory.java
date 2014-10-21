/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.compiler;

import keso.compiler.backend.Coder;
import keso.compiler.imcode.IMClass;
import keso.compiler.imcode.IMMethod;
import keso.compiler.imcode.IMMethodNotFound;
import keso.compiler.imcode.MethodTable;

import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Vector;

final public class MethodTableFactory 
{
	final private static boolean debug = false;

	final private ClassStore repository;
	final private BuilderOptions opts;
	final private Hashtable mtables;

	public MethodTableFactory(BuilderOptions opts, ClassStore repository) {
		this.opts    = opts;
		this.repository = repository;
		this.mtables = new Hashtable();
	}
	
	public void computeTables() throws CompileException {

		IMClass root = repository.getRootClass();
		opts.verbose("##### compute java.lang.Object method table");
		computeCurrentMethodTable(root);

		opts.verbose("##### compute interface tables");
		IMClass[] subs = root.getSubClasses();
		for (int i=0;subs!=null && i<subs.length;i++) {
			IMClass cclass = subs[i];
			if (!cclass.isInterface()) continue;
			computeInterfaceTable(cclass);
		}

		opts.verbose("##### compute method tables");
		for (int i=0;subs!=null && i<subs.length;i++) {
			IMClass cclass = subs[i];
			if (cclass.isInterface()) continue;
			opts.vverbose(cclass.getClassName());
			computeMethodTable(cclass);
		}
	}

	private void computeCurrentMethodTable(IMClass current) throws CompileException {
		IMMethod[] methods = current.getMethods();
		for (int i=0;methods!=null && i<methods.length;i++) {
			IMMethod m = methods[i];
			if (m.isStatic() || m.isInit()) continue;
			if (m.getMethodTable()!=null) continue;
			String sig = m.getMethodNameAndType();
			MethodTable table = new MethodTable(current);
			mtables.put(current.getClassName()+sig, table);
			buildMethodTable(table, current, m, sig);
		}
	}

	private void computeInterfaceTable(IMClass iface) throws CompileException {

		opts.verbose("#### compute interface "+iface.getClassName());

		if (iface.getInterfaceID()==-1) {
			if (!opts.hasOption("iface_no_omit_unused")) {
				if (repository.typeInUse(iface)) {
					int ifid = repository.getNewInterfaceID();
					if (ifid==0) ifid = repository.getNewInterfaceID();
					iface.setInterfaceID(ifid);
					opts.vverbose("#? interface "+iface.getClassName()+" id="+ifid);
				} else {
					opts.vverbose("#? interface "+iface.getClassName()+" id=0 <omit>");
					iface.setInterfaceID(0);
				}
			} else {
				int ifid = repository.getNewInterfaceID();
				iface.setInterfaceID(ifid);
				opts.vverbose("## interface "+iface.getClassName()+" id="+ifid);
			}
		}

		IMMethod[] methods = iface.getMethods();
		if (methods==null) return;

		IMClass[] impl_classes = repository.findImplements(iface.getClassName());
		if (impl_classes==null || impl_classes.length==0) return;

		for (int i=0;methods!=null && i<methods.length;i++) {
			IMMethod m = methods[i];
			if (m.isClassInit()) continue;
			if (m.getMethodTable()!=null) continue;
			String sig = m.getMethodNameAndType();
			opts.verbose("### compute interface method "+sig);

			MethodTable table = new MethodTable(impl_classes[0]);
			
			m.setMethodTable(table);
			mtables.put(iface+sig, table);
			for (int j=0;j<impl_classes.length;j++) {
				IMClass iclass = impl_classes[j];
				IMMethod imethod = iclass.getMethod(sig);
				if (imethod==null) {
					if (iclass.isInterface()) continue; 
					if (iclass.isAbstract()) continue; 
					if (debug) opts.warn(iclass.getClassName()+"."+sig+" not found!");
					//addToMethodTable(table, iclass, new IMMethodNotFound(iclass, sig), sig);
					throw new CompileException(iclass.getClassName()+"."+sig+" not found!");
				} else {
					addToMethodTable(table, iclass, imethod, sig); 
				}
			}
		}

		IMClass[] subs = iface.getSubClasses();
		for (int i=0;subs!=null && i<subs.length;i++) {
			computeInterfaceTable(subs[i]);
		}
	}

	private void computeMethodTable(IMClass current) throws CompileException {
		IMMethod[] methods = current.getMethods();
		for (int i=0;methods!=null && i<methods.length;i++) {
			IMMethod m = methods[i];
			if (m.isStatic() || m.isInit()) continue;	
			if (m.getMethodTable()!=null) continue;
			String sig = m.getMethodNameAndType();
			MethodTable table = new MethodTable(current);
			mtables.put(current.getClassName()+sig, table);
			buildMethodTable(table, current, m, sig);
		}
		IMClass[] subs = current.getSubClasses();
		for (int i=0;subs!=null && i<subs.length;i++) {
			computeMethodTable(subs[i]);
		}
	}

	private void addToMethodTable(MethodTable table, IMClass current, IMMethod method, String sig) throws CompileException {

		/* computation of interface method table EHT */

		MethodTable current_table = method.getMethodTable();
		if (current_table!=null) {
			if (current_table == table) return;
			/*
			System.err.println("old table: "+current_table);
			System.err.println("new table: "+table);
			*/

			table.joinTables(current_table);

			return;
			//throw new CompileException("fixme: multiply method tables!");
		}

		table.addMethod(current.getClassTypeID(), method);
		method.setMethodTable(table);
		IMClass[] subs = current.getSubClasses();
		for (int i=0;subs!=null && i<subs.length;i++) {
			IMMethod next = subs[i].getMethod(sig);
			if (next==null) next = method;
			addToMethodTable(table,subs[i],next,sig);
		}
	} 

	private void buildMethodTable(MethodTable table, IMClass current, IMMethod method, String sig) throws CompileException {

		/* computation of method table HT */

		table.addMethod(current.getClassTypeID(), method);
		if (method.getMethodTable()==null) method.setMethodTable(table);

		IMClass[] subs = current.getSubClasses();
		for (int i=0;subs!=null && i<subs.length;i++) {
			IMMethod next = subs[i].getMethod(sig);
			if (next==null) next = method;
			buildMethodTable(table,subs[i],next,sig);
		}
	} 

	public void emit_index_table(Coder coder, Vector ctables, String version) throws CompileException {
		coder.global_header_add("extern ");
		coder.global_header_add(opts.declareConst());
		coder.global_header_add("code_t addr");
		coder.global_header_add(version);
		coder.global_header_add("_table[];\n");

		//coder.global_add("/* compressed dispatch_table */\n");
		coder.global_add(opts.declareConst());
		coder.global_add("code_t addr");
		coder.global_add(version);
		coder.global_add("_table[] = {\n");
		Enumeration ae = ctables.elements();
		int addr_table_off = 0;
		for (int i=0;ae.hasMoreElements();i++) {
			MethodTable table = (MethodTable)ae.nextElement();
			addr_table_off = table.emit_addr_table(coder, addr_table_off);
		}
		coder.global_add("};\n");

		/* compute global index table */
		Vector index_table = null;
		Enumeration pre_e = ctables.elements();
		while (pre_e.hasMoreElements()) {
			MethodTable table = (MethodTable)pre_e.nextElement();
			index_table = table.updateIndexSeq(index_table);
		}

		if (index_table!=null) {
			int n = 0;
			pre_e = ctables.elements();
			while (pre_e.hasMoreElements()) {
				MethodTable table = (MethodTable)pre_e.nextElement();
				table.updateOffset(index_table);
				if (table.hasDispatchTable()) n++;
			}

			int bits = (index_table.size()*8) / n;

			coder.global_header_add("extern ");
			coder.global_header_add(opts.declareConst());
			if (version.equals("16")) {
				coder.global_header_add("unsigned short index");
			} else {
				coder.global_header_add("unsigned char index");
			}
			coder.global_header_add(version);
			coder.global_header_add("_table[];\n");

			coder.global_add("/* index_table size:"+index_table.size());
			coder.global_add(" ctables:"+n+" bits/table:"+bits);
			coder.global_add(" */\n");
			coder.global_add(opts.declareConst());
			if (version.equals("16")) {
				coder.global_add("unsigned short index");
			} else {
				coder.global_add("unsigned char index");
			}
			coder.global_add(version);
			coder.global_add("_table[] = {");
			Enumeration ndx_e = index_table.elements();
			for (int i=0; ndx_e.hasMoreElements(); i++) {
				if ((i%4)==0) coder.global_add("\n/* "+i+" */ \t");
				int ndx = ((Integer)ndx_e.nextElement()).intValue();
				if (ndx>=0 && ndx<10) coder.global_add(" ");
				coder.global_add(ndx);
				coder.global_add(",");

			}
			coder.global_add("\n};\n");
		}
	}

	public void translate(Coder coder) throws CompileException {

		if (opts.hasOption("vt_switch")) {
			Enumeration e = mtables.elements();
			for (int i=0;e.hasMoreElements();i++) {
				MethodTable table = (MethodTable)e.nextElement();
				table.emit_switch(coder);
			}
		} else if (opts.hasOption("vt_comp")) {
			coder.global_header_add("extern ");
			coder.global_header_add(opts.declareConst());
			coder.global_header_add("code_t dispatch_table[];\n");

			coder.global_add(opts.declareConst());
			coder.global_add("code_t dispatch_table[] = {\n");
			Enumeration e = mtables.elements();
			Vector ctables = new Vector();
			Vector ctables16 = new Vector();
			int offset = 0;
			for (int i=0;e.hasMoreElements();i++) {
				MethodTable table = (MethodTable)e.nextElement();
				if (table.isCompressed()) {
					if (table.is16BitIndex()) {
						ctables16.add(table);
					} else {
						ctables.add(table);
					}
					continue;
				}
				offset = table.emit_table(coder, offset);
			}
			coder.global_add("};\n");

			if (ctables.size()>0) emit_index_table(coder, ctables, "");
			if (ctables16.size()>0) emit_index_table(coder, ctables16, "16");

		} else {
			coder.global_header_add("extern ");
			coder.global_header_add(opts.declareConst());
			coder.global_header_add("code_t dispatch_table[];\n");

			coder.global_add(opts.declareConst());
			coder.global_add("code_t dispatch_table[] = {\n");
			Enumeration e = mtables.elements();
			int offset = 0;
			for (int i=0;e.hasMoreElements();i++) {
				MethodTable table = (MethodTable)e.nextElement();
				offset = table.emit_table(coder, offset);
			}
			coder.global_add("};\n");
		}

		coder.global_header_add("#ifndef KESO_NEED_FINALIZE\n");
		coder.global_header_add("#define KESO_INVOKE_FINALIZE(_self_)(_self_)\n");
		coder.global_header_add("#endif\n");
	}
}
