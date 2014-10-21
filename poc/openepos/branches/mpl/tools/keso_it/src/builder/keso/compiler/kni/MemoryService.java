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

public class MemoryService extends Weavelet {

	private IMClass clazz;
	private Coder coder;
	private IMMethod callee;

	static private int identifierCounter=0;

	public MemoryService(BuilderOptions opts, ClassStore repository) {
		super(opts, repository);
		joinPoints = new String[] { "keso/core/MemoryService.*" };
	}

	public void require(int domainID, String className, String methodName) { 
		repository.requireClass(domainID, "keso/core/Memory");
		if (methodName.equals("allocMemory(II)Lkeso/core/Memory;")) repository.requireSharedMemory();
	}

	public int checkAttribut(IMMethod method, int attr) 
	{
		if (attr==ATTR_NOTNULL) {
			if (method.termed("allocStaticDeviceMemory(II)Lkeso/core/Memory;"))
				return TRUE;
			if (method.termed("allocStaticMemoryHandle()Lkeso/core/Memory;")) 
				return TRUE;
			if (method.termed("mapStaticDeviceMemory(ILjava/lang/String;)Lkeso/core/MemoryMappedObject;"))
				return TRUE;
			if (method.termed("allocStaticMemory(I)Lkeso/core/Memory;"))
				return TRUE; 
		}

		return FALSE;
	}

	public IMNode affectIMInvoke(IMInvoke self, IMMethod method, IMMethod callee,
			IMNode obj, IMNode args[]) throws CompileException {

		if (callee.termed("allocStaticDeviceMemory(II)Lkeso/core/Memory;")) { 

			if (!(args[0] instanceof IMIConstant) || !(args[1] instanceof IMIConstant))
				throw new CompileException("Parameters to mapStaticDevMem must be constant");

			IMIConstant addr = (IMIConstant)args[0];
			IMIConstant size = (IMIConstant)args[1];

			return method.createMemConstant(self, addr.getValue(), size.getValue());
		}

		if (callee.termed("mapStaticDeviceMemory(ILjava/lang/String;)Lkeso/core/MemoryMappedObject;")) {

			if (!(args[0] instanceof IMIConstant) || !(args[1] instanceof IMAConstant))
				throw new CompileException("Parameters to mapStaticDevMem must be constant");

			String type = ((IMAConstant)args[1]).getString();
			IMClass memory_type_class = repository.getClass(type);

			if (memory_type_class==null) {
				opts.warn("Class \""+type+"\" not found! ("+method.getIMClass().getSourceFile()+")");
				return method.createIMNullConstant(self.getBCPosition());
			}

			IMIConstant addr = (IMIConstant)args[0];

			return method.createMTConstant(self, memory_type_class, addr.getValue());
		}

		return self;
	}

	public boolean affectMethod(IMClass clazz, IMMethod method, Coder coder) throws CompileException {
		this.clazz = clazz;
		this.coder = coder;
		this.callee = method;

		if (method.termed("allocDynamicDeviceMemory(II)Lkeso/core/Memory;")) {
			allocDynDevMem();
		} else if (method.termed("allocMemory(II)Lkeso/core/Memory;")) {
			allocDynMem();
		} else if (callee.termed("adjustMemory")) {
			adjustMem();
		} else if (callee.termed("releaseMemory(Lkeso/core/Memory;)V")) {
			releaseMem();
		} else if (callee.termed("copy(Lkeso/core/Memory;ILkeso/core/Memory;II)V")) {
			copyMem();
		} else return false;
		return true;
	}
	
	public boolean affectInvokeStatic(IMInvoke node, IMMethod caller, IMMethod callee,
			IMNode args[], Coder coder) throws CompileException
	{
		this.callee = callee;
		this.coder = coder;

		if (callee.termed("allocStaticDeviceMemory(II)Lkeso/core/Memory;")) {
			allocStaticDevMem(args);
		} else if (callee.termed("mapStaticDeviceMemory(ILjava/lang/String;)Lkeso/core/MemoryMappedObject;")) {
			mapStaticDevMem(args);
		} else if (callee.termed("allocStaticMemoryHandle()Lkeso/core/Memory;")) {
			coder.add_allocStaticMem(0);
		} else if (callee.termed("allocStaticMemory(I)Lkeso/core/Memory;")) {
			int size = assert_iconst(args[0],"Parameter to allocStaticMem").getValue();
			if (!opts.is8BitController()) {
				// align by 4 bytes
				size = (size+3)&~3;
			}
			coder.add_allocStaticMem(size);
		} else return false;

		return true;
	}
     
	private void allocStaticDevMem(IMNode[] args) throws CompileException {
		IMClass memory_class = repository.getClass("keso/core/Memory");
		if (!(args[0] instanceof IMIConstant) || !(args[1] instanceof IMIConstant))
			throw new CompileException("Parameters to allocStaticDevMem must be constant");

		IMIConstant addr = (IMIConstant)args[0];
		IMIConstant size = (IMIConstant)args[1];
		coder.add_class(memory_class);

		coder.add_allocConstObject(memory_class, "staticDevMem"+(identifierCounter++));
		coder.add_init_field_hex(memory_class,"addr", addr.getValue());
		coder.add_init_field(memory_class,"size", size.toReadableString());
		coder.add_init_end();
	}

	private void mapStaticDevMem(IMNode[] args) throws CompileException {

		String type = assert_string(args[1],"Parameters to mapStaticDevMem");
		IMClass memory_type_class = repository.getClass(type);

		if (memory_type_class==null) 
			throw new CompileException("Class \""+type+"\" not found!");

		IMIConstant addr = assert_iconst(args[0],"Parameters to mapStaticDevMem");

		coder.add_class(memory_type_class);

		coder.add_allocConstObject(memory_type_class, "staticDevObj"+(identifierCounter++));
		coder.add_init_field_hex(memory_type_class, "base", addr.getValue());
		coder.add_init_end();
	}

	private void adjustMem() throws CompileException {
		IMClass memory_class = repository.getClass("keso/core/Memory");
		coder.add_class(memory_class);

		coder.addln("char *src;");
		coder.add("src = ((char*)");
		coder.add_getField(memory_class, "obj0", "addr");
		coder.addln(") + i1;");

		/* check source */
		coder.add("KESO_CHK_BOUNDS(");
		coder.add(memory_class.getClassTypeString());
		coder.add(",obj0,i1+i3-1,");
		if (opts.useExceptionStrings()) {
			coder.add('"');
			coder.add(callee.getAlias());
			coder.add("\",");
		} else {
			coder.add("(char *) 0,");
		}
		coder.addln("-1);");

		coder.add_getField(memory_class, "obj2", "addr");
		coder.addln(" = (jint)src;");
		coder.add_getField_fast(memory_class, "obj2", "size");
		coder.addln(" = i3;");

		coder.addln("return 1;");
	}

	private void releaseMem() throws CompileException {
		IMClass memory_class = repository.getClass("keso/core/Memory");
		coder.add_class(memory_class);
		opts.todo("releaseMemory: add support for dynamic memory!");
		coder.add_getField_fast(memory_class, "obj0", "size");
		coder.addln(" = 0;");
		coder.addln("return;");
	}
	private void copyMem() throws CompileException {
		IMClass memory_class = repository.getClass("keso/core/Memory");
		coder.add_class(memory_class);

		coder.addln("char *src,*dst;");
		coder.add("src = ((char*)");
		coder.add_getField(memory_class, "obj0", "addr");
		coder.addln(") + i1;");
		coder.add("dst = ((char*)");
		coder.add_getField(memory_class, "obj2", "addr");
		coder.addln(") + i3;");

		coder.add("KESO_CHK_BOUNDS(");
		coder.add(memory_class.getClassTypeString());
		coder.add(",obj0,i1+i4-1,");
		if (opts.useExceptionStrings()) {
			coder.add('"');
			coder.add(callee.getAlias());
			coder.add("\",");
		} else {
			coder.add("(char *) 0,");
		}
		coder.addln("-1);");

		coder.add("KESO_CHK_BOUNDS(");
		coder.add(memory_class.getClassTypeString());
		coder.add(",obj2,i3+i4-1,");
		if (opts.useExceptionStrings()) {
			coder.add('"');
			coder.add(callee.getAlias());
			coder.add("\",");
		} else {
			coder.add("(char *) 0,");
		}
		coder.addln("-1);");

		coder.addln("for (;i4>=0;i4--) { *dst=*src; dst++; src++; };");
		coder.addln("return;");
	}

	private void allocDynDevMem() throws CompileException {
		IMClass memory_class = repository.getClass("keso/core/Memory");
		coder.add_class(memory_class);

		coder.add("object_t* newMem = ");
		coder.add_allocObject(memory_class);
		coder.addln(";");
		coder.add_getField(memory_class, "newMem", "addr");
		coder.addln(" = i0;");
		// omit 2nd null reference check
		coder.add_getField_fast(memory_class, "newMem", "size");
		coder.addln(" = i1;");

		coder.addln("return newMem;");
	}

	private void allocDynMem() throws CompileException {
		IMClass memory_class = repository.getClass("keso/core/Memory");
		coder.add_class(memory_class);

		coder.addln("jint addr;");
		coder.addln("object_t* newMem=NULL;");

		coder.addln("if ((addr=(jint)keso_alloc_shared_memory(i0,i1))!=NULL) {");
		coder.add("\tnewMem = ");
		coder.add_allocObject(memory_class);
		coder.addln(";\t");
		coder.add_getField(memory_class, "newMem", "addr");
		coder.addln(" = addr;\t");

		// omit 2nd null reference check
		coder.add_getField_fast(memory_class, "newMem", "size");
		coder.addln(" = ((i0+3)&~3);");
		coder.addln('}');

		coder.addln("return newMem;");
	}

	public boolean ignoreMethodBody(IMClass clazz, IMMethod method)
		throws CompileException {
		return true;
	}
}
