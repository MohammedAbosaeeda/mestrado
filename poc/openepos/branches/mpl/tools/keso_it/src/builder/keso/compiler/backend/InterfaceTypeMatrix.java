/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.compiler.backend; 

import keso.compiler.*;
import keso.compiler.config.*;
import keso.compiler.imcode.*;

import keso.util.Debug; 
import keso.util.Bitmap; 

import java.util.Vector;
import java.util.Hashtable;
import java.util.Enumeration;

/**
 *
 * extra compiler options:
 * -X:iface_force_matrix
 */
public class InterfaceTypeMatrix {

	private Vector iface_types = new Vector();
	private Hashtable iface_index = new Hashtable();

	private Coder coder;
	private BuilderOptions opts;
	private ClassStore repository;

	public InterfaceTypeMatrix(BuilderOptions opts, Coder coder, ClassStore store) {
		this.coder=coder;
		this.opts=opts;
		this.repository=store;
	}

	public int registerInterfaceTypeInfo(Bitmap bitmap) throws CompileException {
		
		int max_ifaces = repository.getMaxInterfaceID();
		if (inlineIFMatrix(opts,max_ifaces)) return bitmap.value(0);

		Integer index = (Integer)iface_index.get(bitmap);
		if (index==null) {
			iface_types.add(bitmap);
			index = new Integer(iface_types.size());
			iface_index.put(bitmap,index);
		}

		return index.intValue();
	}

	public void emitCheckInterfaceMakro(IMClass iface, ClassTypeInfo type_info) throws CompileException {
		int max_ifaces = repository.getMaxInterfaceID();
		int id   = iface.getInterfaceID(); 

		coder.header_add("#define CHK_IFACE_ID");
		coder.header_add(type_info.getClassTypeID());
		coder.header_add("(_obj_) keso_checkiface(_obj_,");
		if (max_ifaces>31) {
			coder.header_add(id);
		} else {
			coder.header_add(1<<id);
		}
		coder.header_add(")\n");

		coder.header_add("#define INSTANCEOF_IFACE_ID");
		coder.header_add(type_info.getClassTypeID());
		coder.header_add("(_obj_) keso_instanceof_iface(_obj_,");
		if (max_ifaces>31) {
			coder.header_add(id);
		} else {
			coder.header_add(1<<id);
		}
		coder.header_add(")\n");
	}

	public void emitInterfaceMatrix() throws CompileException {
		int max_ifaces = repository.getMaxInterfaceID();

		if (!inlineIFMatrix(opts,max_ifaces)) {
			coder.global_add("\n/* the interface type matrix */\n");
			coder.global_add("/* length: ");
			coder.global_add(iface_types.size());
			coder.global_add(" */\n");
			coder.global_add(opts.declareConst());
			coder.global_add("unsigned long iface_type_matrix[] = {\n");
			for (Enumeration e = iface_types.elements();e.hasMoreElements();) {
				Bitmap iftype = (Bitmap)e.nextElement();
				coder.global_add("\t");
				coder.global_add(iftype.emit_flat());
				coder.global_add("\n");
			}
			coder.global_add("};\n");
		}

		boolean iface_store = opts.hasOption("iface_store");

		coder.global_header_add("object_t *keso_checkiface(object_t* obj, unsigned int id);\n");
		coder.global_add("\nobject_t *keso_checkiface(object_t* obj, unsigned int id) {\n");
		coder.global_add("	unsigned int info;\n\n");
		coder.global_add("	if (obj==NULL) return obj;\n\n");
		if (iface_store) {
			coder.global_add("	if ((unsigned int)(obj->class_id-KESO_IFACE_START)>=KESO_IFACE_SIZE)\n");
			coder.global_add("		KESO_THROW_ERROR(\"checkcase\");\n");
			coder.global_add("	info = iface_store[obj->class_id-KESO_IFACE_START];\n");
		} else {
			coder.global_add("	info = CLASS(obj->class_id).ifaces;\n");
		}
		emitTypeCheck(max_ifaces);
		coder.global_add("		KESO_THROW_ERROR(\"checkcase\");\n\n");
		coder.global_add("	return obj;\n}\n");

		coder.global_header_add("jboolean keso_instanceof_iface(object_t* obj, unsigned int id);\n");
		coder.global_add("\njboolean keso_instanceof_iface(object_t* obj, unsigned int id) {\n");
		coder.global_add("	unsigned int info;\n\n");
		coder.global_add("	if (obj==NULL) return 1;\n\n");
		if (iface_store) {
			coder.global_add("	if ((unsigned int)(obj->class_id-KESO_IFACE_START)>=KESO_IFACE_SIZE)\n");
			coder.global_add("		return 0;\n\n");
			coder.global_add("	info = iface_store[obj->class_id-KESO_IFACE_START];\n");
		} else {
			coder.global_add("	info = CLASS(obj->class_id).ifaces;\n");
		}
		emitTypeCheck(max_ifaces);
		coder.global_add("		return 0;\n\n");
		coder.global_add("	return 1;\n}\n");
	}

	private void emitTypeCheck(int max_ifaces) throws CompileException {
		if (inlineIFMatrix(opts,max_ifaces)) {
			coder.global_add("	if (!(info & id))\n");
		} else {
			if (max_ifaces<32) {
				coder.global_add("\tif (info==0 || !(iface_type_matrix[info-1] & id))\n");
			} else {
				opts.warn("Have "+max_ifaces+" interfaces! (>32)");
				coder.global_add("\tif (info==0 || !(iface_type_matrix[");
				coder.global_add("((info-1)*");
				coder.global_add((max_ifaces+31)/32+1);
				coder.global_add(")+(id/32)] & (1<<(id%32))))\n");
			}
		}
	}

	private static boolean inlineIFMatrix(BuilderOptions sopts, int max_ifaces) {
		if (sopts.hasOption("iface_force_matrix")) return false;
		return (max_ifaces<8);
	}
}
