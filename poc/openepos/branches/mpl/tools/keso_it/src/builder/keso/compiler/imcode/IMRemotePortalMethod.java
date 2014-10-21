/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.compiler.imcode; 

import keso.classfile.*;
import keso.classfile.constantpool.*; 
import keso.classfile.datatypes.*; 

import keso.compiler.*;
import keso.compiler.kni.*;
import keso.compiler.backend.Coder;
import keso.compiler.config.*;

import keso.util.*; 

import java.util.Enumeration;

import java.io.IOException;
import java.io.PrintStream;
import java.io.FileOutputStream;

import java.util.Vector;

/**
 * This class implements the remote portal call to a service. 
 */
final public class IMRemotePortalMethod extends IMSystemMethod {
    
    private ImportDefinition importDefinition;
    private ClassStore repository;

    public IMRemotePortalMethod(IMPortalClass clazz, MethodData source,
            ClassStore src_repository, ImportDefinition importDef) throws CompileException {
	    //super(clazz, source);
	    super(clazz);
	    this.source = source;
	    hasNoCode = false;
	    this.frame  = new IMMethodFrame(this, source.getInitialVariableTypes(), 0);
	    importDefinition = importDef;
	    repository = src_repository;
    }

    public boolean canBlock() throws CompileException {
	    IMClass protocolClass = repository.getClass(importDefinition.getNetwork().getProtocolName());
	    IMMethod writeMethod = protocolClass.getMethod("write(ISI)Z");
	    IMMethod readMethod = protocolClass.getMethod("read(I)Lkeso/core/Memory;");
	    if (writeMethod.canBlock() || readMethod.canBlock()) return true;
	    return false;
    }

    public void analyseCallGraph(IMCallGraphVisitor visitor) throws CompileException {
	    //opts.warn("blind analyseCallGraph() method in "+this.getClass().getName());
	    
	    IMClass protocolClass = repository.getClass(importDefinition.getNetwork().getProtocolName());

	    IMMethod writeMethod = protocolClass.getMethod("write(ISI)Z");
	    IMMethod readMethod = protocolClass.getMethod("read(I)Lkeso/core/Memory;");

	    writeMethod.calledFrom(this, null);
	    readMethod.calledFrom(this, null);

	    writeMethod.analyseCallGraph(visitor);
	    readMethod.analyseCallGraph(visitor);
    }
    
    final public void translate(Coder coder) throws CompileException {

	    String[] argNames = frame.getArgNames();
	    IMSlot[] argSlots = frame.getMethodArguments();
	    boolean hasArguments = argSlots.length > 1;
	    boolean llrefs = opts.hasLinkedListOfLocalReferences(); 

	    IMPortalClass thisClass = (IMPortalClass) clazz;
	    IMClass memoryClass = repository.getClass("keso/core/Memory");
	    IMClass protocolClass = repository.getClass(importDefinition.getNetwork().getProtocolName());

	    coder.addInvokeMakro(this);
	    coder.beginMethod(this); 
	    coder.add_class(thisClass);
	    coder.add_class(memoryClass);        
	    coder.add_class(protocolClass);

	    frame.translate(coder);

	    coder.addln();

	    coder.add("/* This method has caller id 0x");
	    coder.add(Integer.toHexString(importDefinition.getCallIdentifier(getMethodNameAndType())));
	    coder.add(" and return id 0x");
	    coder.add(Integer.toHexString(importDefinition.getReturnIdentifier(getMethodNameAndType())));
	    coder.addln(" */");

	    coder.addln();

	    coder.addln("jshort data_size = 0;");

	    if (hasArguments) {

		    coder.add("jbyte *buffer = (jbyte *) ");
		    coder.add_getField_fast(memoryClass, thisClass.getTransmitBufferObjectName(), "addr");
		    coder.addln(";");

		    for (int i = 1; i < argNames.length; ++i) {
			    if (importDefinition.needDataConversion()) {
				    coder.add("keso_memrevcpy");
			    } else {
				    coder.add("keso_memcpy");
			    }
			    coder.add("(buffer, (jbyte *) &");
			    coder.add(argNames[i]);
			    coder.add(", sizeof(");
			    coder.add(argSlots[i].cType());
			    coder.addln("));");

			    coder.add("buffer += ");
			    coder.add("sizeof(");
			    coder.add(argSlots[i].cType());
			    coder.addln(");");

			    coder.add("data_size += ");
			    coder.add("sizeof(");
			    coder.add(argSlots[i].cType());
			    coder.addln(");");
		    }

		    coder.addln();
	    }

	    IMMethod writeMethod = protocolClass.getMethod("write(ISI)Z");
	    IMMethod readMethod = protocolClass.getMethod("read(I)Lkeso/core/Memory;");

	    // transfer arguments
	    coder.add("if (!");
	    coder.add(writeMethod.getAlias());
	    coder.add("(");

	    if (llrefs && writeMethod.canBlock()) {
		    coder.add("(object_t **) 0, "); //FIXME
	    }

	    coder.add(coder.add_accessStaticObject(protocolClass, thisClass.getProtocolObjectName()));
	    coder.add(", 0x");
	    coder.add(Integer.toHexString(importDefinition.getCallIdentifier(getMethodNameAndType())));
	    //coder.add(", ");
	    //coder.add(coder.add_accessStaticObject(memoryClass, thisClass.getTransmitBufferObjectName()));
	    coder.add(", data_size, ");
	    coder.add(importDefinition.getWriteTimeout());
	    coder.addln(")) {");
	    coder.addln("/* FIXME: timeout occured */");
	    coder.addln("}");


	    coder.addln();


	    // read return value
	    coder.add("object_t *receiveBuffer = (object_t *) "); // FIXME
	    coder.add(readMethod.getAlias());
	    coder.add("(");

	    if (llrefs && readMethod.canBlock()) {
		    coder.add("(object_t **) 0, "); //FIXME
	    }

	    coder.add(coder.add_accessStaticObject(protocolClass, thisClass.getProtocolObjectName()));
	    coder.add(", ");
	    coder.add(importDefinition.getReadTimeout());
	    coder.addln(");");

	    coder.addln();

	    // FIXME check for timeout
	    coder.addln("if (receiveBuffer == NULL) {");
	    coder.addln("/* FIXME: timeout occured */");
	    coder.addln("}");

	    coder.addln();

	    //TODO check length and ID

	    if (hasReturnValue()) {

		    coder.add(opts.typeToString(getBasicReturnType()));
		    coder.addln(" return_value;");

		    if (importDefinition.needDataConversion()) {
			    coder.add("keso_memrevcpy");
		    } else {
			    coder.add("keso_memcpy");
		    }

		    coder.add("((jbyte *) &return_value, (jbyte *) ");
		    coder.add_getField_fast(memoryClass, "receiveBuffer", "addr");
		    coder.add(", sizeof(");
		    coder.add(opts.typeToString(getBasicReturnType()));
		    coder.addln("));");

		    coder.addln("return return_value;");
	    }


	    coder.endMethod();
    }
}
