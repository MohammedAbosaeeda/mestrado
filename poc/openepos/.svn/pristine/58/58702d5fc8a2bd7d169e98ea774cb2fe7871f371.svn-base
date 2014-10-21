/**(c)

  Copyright (C) 2005-2007 Christian Wawersich 

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
 * This class implements the portal call to a service. 
 */
final public class IMPortalConstructor extends IMSystemMethod {

	private ImportDefinition impDefinition;
	private ClassStore repository;
	private IMPortalClass thisClass;

	public IMPortalConstructor(IMPortalClass clazz, ClassStore src_repository, ImportDefinition impDef) throws CompileException {
		super(clazz);
		hasNoCode = false;
		this.frame  = new IMMethodFrame(this, 0);
		thisClass = clazz;
		impDefinition = impDef;
		repository = src_repository;
	}

	public void analyseCallGraph(IMCallGraphVisitor visitor) throws CompileException {
		//opts.warn("blind analyseCallGraph() method in "+this.getClass().getName());

		IMClass protocolClass = repository.getClass(impDefinition.getNetwork().getProtocolName());
		IMClass driverClass = repository.getClass(impDefinition.getDriver());          

		IMMethod constructor = driverClass.getMethod("<init>()V");
		constructor.calledFrom(this, null);
		constructor.analyseCallGraph(visitor);

		// call all specified bean methods
		Vector methods = impDefinition.getNetwork().getDriverInitMethods();
		//Vector params = impDefinition.getNetwork().getDriverInitParameters();
		for (int i = 0; i < methods.size(); ++i) {
			IMMethod meth = driverClass.getMethod((String) methods.elementAt(i));
			meth.calledFrom(this, null);
			meth.analyseCallGraph(visitor);
		}

		String cName = "<init>(L" + impDefinition.getNetwork().getDriverInterfaceName() + ";IIIBB)V";
		constructor = protocolClass.getMethod(cName);
		constructor.calledFrom(this, null);
		constructor.analyseCallGraph(visitor);

		// allocate a buffer for sending and receiving        
		IMMethod allocReceiveBuffer = protocolClass.getMethod("allocReceiveBuffer(ISI)Z");
		allocReceiveBuffer.calledFrom(this, null);
		allocReceiveBuffer.analyseCallGraph(visitor);

		IMMethod allocTransmitBuffer = protocolClass.getMethod("allocTransmitBuffer(SI)Lkeso/core/Memory;");
		allocTransmitBuffer.calledFrom(this, null);
		allocTransmitBuffer.analyseCallGraph(visitor);
	}

	public String getMethodName() {
		return "<clinit>";
	}

	public String getMethodNameAndType() {
		return "<clinit>V()";
	}

	public boolean isConstructor() {
		return true;
	}

	public boolean isStatic() {
		return true;
	}

	public boolean isFinal() {
		return true;
	}

	public boolean canBlock() throws CompileException {
		// FIXME: Im Startup-Hook duerfen wir uns nicht blockieren.
		// Daher brauchen wir auch keine llrefs! Im Moment tun wir
		// so ob wir immer blockieren.
		return true;
	}

	public String getReturnType() {
		return "void";
	}

	final public void translate(Coder coder) throws CompileException {

		String name = getAlias();

		boolean llrefs = opts.hasLinkedListOfLocalReferences();

		// transmit buffer
		coder.header_add("extern object_t *");
		coder.header_add(thisClass.getTransmitBufferObjectName());
		coder.header_add(";\n");

		coder.local_add("object_t *");
		coder.local_add(thisClass.getTransmitBufferObjectName());
		coder.local_add(";\n");

		coder.beginMethod(this);
		coder.add_class(clazz.getAlias());

		coder.addln("object_t* protocol_obj;");
		coder.addln("object_t* driver_obj;");
		coder.addln();

		// create protocol object
		IMClass protocolClass = repository.getClass(impDefinition.getNetwork().getProtocolName());
		coder.add_class(protocolClass.getAlias());
		coder.add("protocol_obj = ");
		coder.add_allocConstObject(true, protocolClass, thisClass.getProtocolObjectName());
		coder.add_init_end();        
		coder.addln(";");

		// create driver object
		IMClass driverClass = repository.getClass(impDefinition.getDriver());
		coder.add_class(driverClass.getAlias());
		coder.add("driver_obj = ");
		coder.add_allocConstObject(true, driverClass, thisClass.getDriverObjectName()); 
		coder.add_init_end();
		coder.addln(";");
		coder.addln();

		// call the default constructor
		IMMethod constructor = driverClass.getMethod("<init>()V");
		coder.add(constructor.getAlias());
		coder.add("(");
		if (llrefs && constructor.canBlock()) {
			coder.add("pref,");
		}
		coder.addln("driver_obj);");
		coder.addln();

		// call all specified bean methods
		coder.addln("/* call specified bean methods */");
		Vector methods = impDefinition.getNetwork().getDriverInitMethods();
		Vector params = impDefinition.getNetwork().getDriverInitParameters();
		for (int i = 0; i < methods.size(); ++i) {
			IMMethod meth = driverClass.getMethod((String) methods.elementAt(i));
			coder.add(meth.getAlias());
			coder.add("(");
			if (llrefs && meth.canBlock()) {
				coder.add("pref,");
			}
			coder.add(coder.add_accessStaticObject(driverClass, thisClass.getDriverObjectName()));
			coder.add(", ");
			coder.add(params.elementAt(i));
			coder.addln(");");
		}
		coder.addln();

		coder.addln("/* call protocol constructor */");
		String cName = "<init>(L" + impDefinition.getNetwork().getDriverInterfaceName() + ";IIIBB)V";
		constructor = protocolClass.getMethod(cName);

		coder.add(constructor.getAlias());
		coder.add("(");
		if (llrefs && constructor.canBlock()) {
			coder.add("pref,");
		}
		coder.add("protocol_obj,driver_obj");
		// rxId
		coder.add(",0x");
		coder.add(Integer.toHexString(ServiceManager.instance.getImportIdentifier(impDefinition)));
		// rxMask
		coder.add(",0x");
		coder.add(Integer.toHexString(ServiceManager.instance.getImportMask()));
		// bufferMask
		coder.add(",0x00000000");
		// idBits
		coder.add(",");
		coder.add(ServiceManager.instance.getIDWidth());
		// numRxBuffers
		coder.addln(",1);");


		// allocate a buffer for sending and receiving        
		coder.addln("/* allocate receive buffer */");
		IMMethod allocReceiveBuffer = protocolClass.getMethod("allocReceiveBuffer(ISI)Z");
		IMMethod allocTransmitBuffer = protocolClass.getMethod("allocTransmitBuffer(SI)Lkeso/core/Memory;");

		coder.add("if (!");
		coder.add(allocReceiveBuffer.getAlias());
		coder.add("(");
		if (llrefs && allocReceiveBuffer.canBlock()) {
			coder.add("pref,");
		}
		coder.add("protocol_obj,0x");
		coder.add(Integer.toHexString(ServiceManager.instance.getImportIdentifier(impDefinition)));
		coder.add(",");
		coder.add(thisClass.getReceiveBufferSize());
		coder.add(",");
		coder.add(impDefinition.getAllocTimeout());
		coder.addln(")) {");
		coder.addln("\t/* Allocation of the rx buffer failed */");
		coder.addln("}");
		coder.addln();

		coder.addln("/* allocate transmit buffer */");
		coder.add(thisClass.getTransmitBufferObjectName());
		coder.add(" = (object_t *) ");
		coder.add(allocTransmitBuffer.getAlias());
		coder.add("(");
		if (llrefs && allocTransmitBuffer.canBlock()) {
			coder.add("pref,");
		}
		coder.add("protocol_obj,");
		coder.add(thisClass.getTransmitBufferSize());
		coder.add(",");
		coder.add(impDefinition.getAllocTimeout());
		coder.addln(");");

		coder.endMethod();
	}
}
