/**(c)

  Copyright (C) 2006 Christian Wawersich 

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

import keso.compiler.CompileException;
import keso.compiler.BuilderOptions;
import keso.compiler.ClassStore;
import keso.compiler.ClassTypeInfo;
import keso.compiler.backend.Coder;
import keso.compiler.kni.JoinPointChecker;
import keso.compiler.config.*;
import keso.compiler.imcode.*;

import keso.util.Debug; 
import keso.util.DecoratedNames;

import java.io.IOException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Vector;

class ClassInitMethodData extends MethodData {
	public String getMethodName() { return "<clinit>"; }
	public String getMethodType() { return "()V"; }
	public String getMethodNameAndType() { return "<clinit>()V"; }
	public int getBasicReturnType() { return BCBasicDatatype.VOID; } 
	public BasicTypeDescriptor getReturnType() { return null; }
}

public class IMPortalClass extends IMClass {

	private ImportDefinition impDef;
	private String[]	 ifaceNames;

	private String              protocolClassName = null; 
	private MethodData[] impl = null;

	public IMPortalClass(BuilderOptions opts, ClassStore repository, ImportDefinition impDef) {
		super(opts, repository, createClassName(impDef), null, true);

		this.impDef = impDef;
		this.ifaceNames = new String[] {impDef.getService().getServiceInterfaceName()};

		if (!impDef.isLocal()) {
			this.protocolClassName = impDef.getNetwork().getProtocolName();           
		}
	}

	private static String createClassName(ImportDefinition impDef) {
		return "keso/core/" + impDef.getServiceName() + "Proxy";
	}

	/**
	 * copy class constructor. Used by the prune class tree algo. 
	 */
	public IMClass cloneClass(ClassStore new_repository) {
		IMPortalClass nclass = new IMPortalClass(opts, new_repository, impDef);
		return nclass;
	}

	/**
	 * This callback informs the class that it is required.
	 *
	 * This method is a callback from the ClassStore!
	 */
	public void require(int domainID) {
		// require super class and interfaces first
		super.require(domainID);

		if (!impDef.isLocal()) {

			if (domainID!=0) {
				if (domainID!=impDef.getDomain().domainid)
					throw new Error("domain mix up");
			}

			repository.requireClass(domainID, protocolClassName);

			IMClass packetStream = repository.getClass("keso/core/io/PacketStream");
			MethodData[] methods = packetStream.getMethodData();

			// constructor
			String cName = "<init>(L" + impDef.getNetwork().getDriverInterfaceName() + ";IIIBB)V";
			repository.requireMethod(domainID, protocolClassName, cName);

			for (int i = 0; i < methods.length; ++i) {
				// require all methods found in the interface for the implementation
				repository.requireMethod(domainID, protocolClassName , methods[i].getMethodNameAndType());
			}

			String driverClassName = impDef.getDriver();
			repository.requireClass(domainID, driverClassName);
			IMClass driverIface = repository.getClass(impDef.getNetwork().getDriverInterfaceName());
			methods = driverIface.getMethodData();

			// constructor
			repository.requireMethod(domainID, driverClassName , "<init>()V");

			for (int i = 0; i < methods.length; ++i) {
				// require all methods found in the interface for the implementation
				repository.requireMethod(domainID, driverClassName , methods[i].getMethodNameAndType());
			}
		}
	}

	public boolean isInterface() {
		return false;
	}

	public String getSuperClassName() {
		return "java/lang/Object";
	}

	public int getObjRefFieldCount() {
		return 0;
	}

	/**
	 * overrides getInterfaceNames() in IMClass
	 */
	public String[] getInterfaceNames() {
		return ifaceNames;
	}

	public String getSourceFile() throws CompileException {
		return impDef.getService().getServiceClassName() + ".java";
	}

	public ConstantPool getConstantPool() {
		return null;
	}         

	public MethodData[] getMethodData() {

		if (impl==null) {
			if (impDef.isLocal() || !opts.hasOption("new_portal_init")) {
				impl=impDef.getService().getMethods();
			} else {
				MethodData[] d = impDef.getService().getMethods();
				impl = new MethodData[d.length+1];
				impl[0] = new ClassInitMethodData();
				for (int i=0;i<d.length;i++) { impl[i+1] = d[i]; }
			}
		}

		return impl;
	}

	protected IMMethod createIMMethod(MethodData source) throws CompileException {
		if (impDef.isLocal()) {
			return new IMLocalPortalMethod(this, source, repository, impDef);
		} else {
			if (source.getMethodName().equals("<clinit>")) {
				return new IMPortalConstructor(this, repository, impDef);
			}
			return new IMRemotePortalMethod(this, source, repository, impDef);
		}
	}

	public String getReceiveBufferSize() throws CompileException {
		return IMPortalService.getSizeOfReturnValues(getMethodData());
		/*
		MethodData[] meths = getMethodData();
		int max_size = 0;

		if (meths==null) return "0";

		for (int i=0;i<meths.length;i++) {
			IMMethod meth = getMethod(meths[i].getMethodNameAndType());
			if (meth==null) continue;

			max_size = Math.max(max_size, meth.getSizeOfReturnValue());
		}

		return Integer.toString(max_size);        
		*/
	}

	public String getTransmitBufferSize() throws CompileException {

		MethodData[] meths = getMethodData();
		int max_size = 0;

		if (meths==null) return "0";

		for (int i=0;i<meths.length;i++) {
			IMMethod meth = getMethod(meths[i].getMethodNameAndType());
			if (meth==null) continue;

			max_size = Math.max(max_size, meth.getSizeOfArguments());
		}

		return Integer.toString(max_size);        
	}
 
	/*
	private StringBuffer appendSize(MethodData[] meths, int start) throws CompileException {

		String sizeOfArguments = null;

		if (meths[start] == null) {
			sizeOfArguments = "0";
		} else {
			IMMethod meth = this.getMethod(meths[start].getMethodNameAndType());

			if (meth == null) {
				sizeOfArguments = "0";
			} else {
				sizeOfArguments = IMPortalService.getSizeOfArguments(meth);
			}
		}

		if (start >= meths.length - 1) {

			return new StringBuffer(sizeOfArguments);

		} else {

			StringBuffer str = new StringBuffer("(");
			str.append(sizeOfArguments);
			str.append(" > ");
			str.append(appendSize(meths, start + 1));
			str.append(" ? ");
			str.append(sizeOfArguments);
			str.append(" : ");
			str.append(appendSize(meths, start + 1));
			str.append(")");

			return str;
		}
	}
	*/


	public String getDriverObjectName() {
		return DecoratedNames.toEscString(impDef.getServiceName()) + "_" + DecoratedNames.toEscString(impDef.getNetwork().getNetworkName()) + "_driver_obj";
	}

	public String getTransmitBufferObjectName() {
		return DecoratedNames.toEscString(impDef.getServiceName()) + "_" + DecoratedNames.toEscString(impDef.getNetwork().getNetworkName()) + "_transmit_buffer_obj";
	}

	public String getProtocolObjectName() {
		return DecoratedNames.toEscString(impDef.getServiceName()) + "_" + DecoratedNames.toEscString(impDef.getNetwork().getNetworkName()) + "_protocol_obj";
	}

	public String getProxyObjectName() {
		return DecoratedNames.toEscString(impDef.getServiceName()) + "_proxy_obj";
	}    

	public void translate(Coder coder) throws CompileException {

		if (methods==null) parseMethodData();

		// create proxy object
		coder.global_add_allocConstObject(this, getProxyObjectName());
		coder.global_add_init_end();        
		coder.global_addln("\n");

		if (!opts.hasOption("new_portal_init")) {
			if (!impDef.isLocal()) {
				new IMPortalClassInit(this, repository, impDef).translate(coder);
			}
		}

		coder.global_header_add("#define ");
		coder.global_header_add(IMPortalService.getLookupMacro(impDef.getService().getServiceName()));
		coder.global_header_add("(object_t*) &");
		coder.global_header_add(getProxyObjectName());
		coder.global_header_add("\n"); 

		super.translate(coder);
	}
}
