/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.compiler.kni;

import keso.compiler.*;
import keso.compiler.imcode.*;
import keso.compiler.backend.*;

public class Memory extends Weavelet {

	private IMClass clazz;
	private Coder coder;
	private IMMethod callee, caller;
	private IMNode[] args;
	private IMNode obj;

	private StringBuffer fn;
	private String op, ptrType;
	private int asize;

	public Memory(BuilderOptions opts, ClassStore repository) {
		super(opts, repository);
		joinPoints = new String[] { "keso/core/Memory.*","keso/core/Memory.<CLASS>" };
	}

	public void require(int domainID, String className, String methodName) { 
		repository.requireClass(domainID, "keso/core/Memory");
	}

	/**
	 * Use this method to add fields to the affected class.
	 * See the Task weavelet on an example on how to use this. The added fields are
	 * for internal use only, they are not visible to the application.
	 *
	 * The fields will be appended to the regular object fields, no matter if they
	 * are reference fields or not. The class store will contain the size
	 * including the added fields, roff will not be affected since the fields are
	 * put behind the object header.
	 * This especially implicates that reference fields added with this method
	 * will _not_ be scanned by the garbage collector. Therefore you should not
	 * keep references to mortal objects in added fields, as they may be garbage
	 * collected in case they are not visible by the application anymore.
	 * 
	 * @param clazz The intermediate representation of the affected class
	 * @param coder Coder object that can be used to generate code for the class 
	 * @param raw_fields raw access  for adding C code into the object layout.
	 *
	 */
	public boolean addFields(IMClass clazz, Coder coder, StringBuffer raw_fields) throws CompileException {

		raw_fields.append("\t");
		raw_fields.append(opts.targetAddrType());
		raw_fields.append(" _addr;\n\t");
		raw_fields.append(opts.targetAddrType());
		raw_fields.append(" _size;\n");

		return true;
	}

	private void getOperator() {
		op = fn.toString();
		int len=3;
		if (op.startsWith("get")) {
			op = "";
		} else if (op.startsWith("set")) {
			op = "=";
		} else if (op.startsWith("and")) {
			op = "&=";
		} else if (op.startsWith("or")) {
			op = "|="; len=2;
		} else if (op.startsWith("xor")) {
			op = "^=";
		} else {
			op=null;
			return;
		}
		fn.delete(0, len);
	}

	private void getAccSize() {
		int len=2;
		asize=-1;
		String size = fn.toString();

		if(size.startsWith("8")) {
			len=1; asize=1; ptrType="jchar";
		} else if(size.startsWith("16")) {
			asize=2; ptrType = "jshort";
		} else if(size.startsWith("32")) {
			asize=4; ptrType = "jint";
		} else return;

		fn.delete(0, len);
	}

	public boolean affectMethod(IMClass clazz, IMMethod method, Coder coder) throws CompileException {

		this.coder = coder;

		if (method.getMethodNameAndType().equals("getPart(II)Lkeso/core/Memory;")) {
			getPart();
			return true;
		}

		return false;
	}


	public boolean affectInvokeVirtual(IMInvoke node, IMMethod caller,
			IMMethod callee, IMNode obj, IMNode args[], Coder coder)
		throws CompileException {

			this.coder = coder;
			this.caller = caller;
			this.callee = callee;
			this.args = args;
			this.obj = obj;

			if (callee.getMethodNameAndType().equals("getSize()I")) {
				getSize();
				return true;    
			}

			fn = new StringBuffer(callee.getMethodNameAndType());


			getOperator();
			getAccSize();

			if(op==null || asize==-1)
				return false;

			coder.add_befor("/* MEMORY ACCESS */\n");
			coder.chk_range(obj, args[0], asize, caller, node.getBCPosition());
			accessMemlocation();
			if(!op.equals("")) {
				coder.add(op);
				args[1].translate(coder);
			}

			return true;
		}


	private void getSize() throws CompileException {
		opts.verbose("####Memory.getSize()");

		IMClass memory_class = repository.getClass("keso/core/Memory");
		coder.add_class(memory_class); 
		coder.add_getField(memory_class, obj, "size");
	}

	private void getPart() throws CompileException {
		opts.verbose("####Memory.getPart()");

		IMClass memory_class = repository.getClass("keso/core/Memory");
		coder.add_class(memory_class);

		// check memory parameter
		coder.addln("if (obj0 == NULL) return NULL;");

		// check offset
		coder.add("if (0 > i1 || i1 >= ");
		coder.add_getField_fast(memory_class, "obj0", "size");
		coder.addln(") return NULL;");

		// check size
		coder.add("if (0 > i2 || i2 > (");
		coder.add_getField_fast(memory_class, "obj0", "size");
		coder.addln(" - i1)) return NULL;");


		coder.add("object_t* newMem = ");
		coder.add_allocObject(memory_class);
		coder.addln(";");
		coder.add_getField(memory_class, "newMem", "addr");
		coder.add(" = (");
		coder.add_getField_fast(memory_class, "obj0", "addr");
		coder.addln(" + i1);");

		// omit 2nd null reference check
		coder.add_getField_fast(memory_class, "newMem", "size");
		coder.addln(" = i2;");

		coder.addln("return newMem;");
	}


	private void accessMemlocation() throws CompileException {
		IMClass mem_class = callee.getIMClass();

		coder.add("((");
		coder.add("volatile ");
		coder.add(ptrType);
		coder.add(" *) ");
		if (obj instanceof IMAMemConstant) {
			IMAMemConstant const_mem = (IMAMemConstant)obj; 
			coder.add("0x");
			coder.add(Integer.toHexString(const_mem.getAddr()));
		} else {
			coder.add_getField(mem_class, obj, "addr");
		}
		coder.add(")[");

		if (args[0] instanceof IMIConstant) {
			coder.add(((IMIConstant)args[0]).getIntValue() / asize);
		} else {
			args[0].translate(coder);
			coder.add("/");
			coder.add(asize);
		}
		coder.add("]");
	}

	public boolean ignoreMethodBody(IMClass clazz, IMMethod method)
		throws CompileException {
			return true;
		}
}
