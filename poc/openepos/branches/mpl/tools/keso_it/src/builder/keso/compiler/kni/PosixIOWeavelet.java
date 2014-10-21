/**(c)

  Copyright (C) 2007 Christian Wawersich 

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
import keso.classfile.datatypes.*;

public class PosixIOWeavelet extends Weavelet {

	public PosixIOWeavelet(BuilderOptions opts, ClassStore repository) {
		super(opts, repository);
		joinPoints = new String[] {
			"keso/posix/PosixIO.activateISR(III)I",
			"keso/posix/PosixIO.open(Ljava/lang/String;I)I",
			"keso/posix/PosixIO.write(I[CI)I",
			"keso/posix/PosixIO.read(I[CI)I",
			"keso/posix/PosixIO.close(I)I",
			"keso/posix/PosixIO.ioctl(II[C)I",
		};
	}

	public void require(int domainID, String className, String methodName) {
		repository.requireClass(domainID, "[C");
		repository.requireClass(domainID, "java/lang/String");
		repository.registerIMGetField("java/lang/String","data");
		repository.registerIMGetField("[C","data");
	}

	public boolean affectMethod(IMClass clazz, IMMethod method, Coder coder) throws CompileException {
		IMClass array_class = method.getIMClass("[C");
		if (method.termed("open")) { 

			IMClass string_class = method.getIMClass("java/lang/String");

			coder.add_class(array_class.getAlias());
			coder.add_class(string_class.getAlias());

			//coder.local_add("#include <unistd.h>\n");
			coder.local_add("#include <sys/types.h>\n");
			coder.local_add("#include <sys/stat.h>\n");
			coder.local_add("#include <fcntl.h>\n");

			coder.add(array_class.getClassTypeString());
			coder.add("* char_array = (");
			coder.add(array_class.getClassTypeString());
			coder.add("*)");
			coder.add_getField(string_class, "obj0", "value");
			coder.addln(";");

			coder.addln("return open(char_array->data, i1, S_IRUSR|S_IWUSR|S_IRGRP|S_IROTH);");

			return true;
		} else if (method.termed("activateISR")) { 
			coder.addln("return SetupIOISR(i0, i1, i2);");
			return true;
		} else if (method.termed("write")) { 
			coder.add_class(array_class.getAlias());

			coder.chk_ref("obj1");
			coder.add("return write(i0, (void*)(((");
			coder.add(array_class.getClassTypeString());
			coder.addln("*)obj1)->data), i2);");

			return true;
		} else if (method.termed("read")) { 
			coder.add_class(array_class.getAlias());

			coder.chk_ref("obj1");
			coder.add("return read(i0, (void*)(((");
			coder.add(array_class.getClassTypeString());
			coder.addln("*)obj1)->data), i2);");

			return true;
		} else if (method.termed("close")) { 
			coder.add("return close(i0);");
			return true;
		} else if (method.termed("ioctl")) { 
			coder.add_class(array_class.getAlias());

			coder.add("return ioctl(i0, i1, (void*)(((");
			coder.add(array_class.getClassTypeString());
			coder.addln("*)obj2)->data));");

			return true;
		}

		return false;
	}

	public boolean affectInvokeStatic(IMInvoke node, IMMethod caller, IMMethod callee,
			IMNode args[], Coder coder) throws CompileException
	{
		if (callee.termed("open")) { 

			if (!args[1].isConstant()) {
				opts.critical("'how' argument must be constant!");
			}

			coder.local_add("#include <sys/types.h>\n");
			coder.local_add("#include <sys/stat.h>\n");
			coder.local_add("#include <fcntl.h>\n");

			coder.add_class(callee.getIMClass());

			coder.add(callee.getAlias());
			coder.add("(");
			args[0].translate(coder);
			coder.add(",");
			int how = ((IMIConstant)args[1]).getIntValue();
			coder.add("0");
			if ((how&1) != 0) coder.add("|O_WRONLY");
			if ((how&2) != 0) coder.add("|O_RDONLY");
			if ((how&4) != 0) coder.add("|O_CREAT");
			if ((how&8) != 0) coder.add("|O_TRUNC");
			if ((how&16) != 0) coder.add("|O_APPEND");
			if ((how&32) != 0) coder.add("|O_NONBLOCK");
			if ((how&64) != 0) coder.add("|O_EXCL");
			coder.add(")");

			return true;
		} else if (callee.termed("activateISR")) { 
			coder.add("SetupIOISR(");
			args[0].translate(coder);
			coder.add(",");
			args[1].translate(coder);
			coder.add(",");
			if (args[2].isConstant()) {
				int how = ((IMIConstant)args[2]).getIntValue();
				coder.add("0");
				if ((how&1) != 0) coder.add("|POLL_READ");
				if ((how&2) != 0) coder.add("|POLL_WRITE");
				if ((how&4) != 0) coder.add("|POLL_ERROR");
				if ((how&8) != 0) coder.add("|POLL_HANGUP");
			} else {
				args[2].translate(coder);
			}
			coder.add(")");
			return true;
		} 

		return false;
	}

	public boolean ignoreMethodBody(IMClass clazz, IMMethod method) throws CompileException {
		return true;
	}
}
