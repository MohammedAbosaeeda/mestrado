/**(c)

  Copyright (C) 2006 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.compiler.imcode;

import keso.compiler.*;
import keso.compiler.imcode.*;
import keso.compiler.backend.*;
import keso.compiler.config.*;

import keso.classfile.MethodData;
import keso.classfile.datatypes.*;
import keso.util.DecoratedNames;

import java.util.Hashtable;
import java.util.Vector;
import java.util.Enumeration;


/**
 * This class handle the auto generated proxy classes for portals and services.
 */

final public class IMPortalService {

	private ServiceDefinition serviceDefinition;
	private BuilderOptions opts;
	private ClassStore repository;
	private IMPortalServiceInit init;

	public IMPortalService(BuilderOptions opts, ClassStore repository,
			ServiceDefinition srvDef) throws CompileException {

		this.serviceDefinition = srvDef;
		this.opts = opts;
		this.repository = repository;

		init = new IMPortalServiceInit(this, serviceDefinition);
	}

	/**
	 * require() this method is used to register all needed classes.
	 */
	public void require(ClassStore new_repository) throws CompileException {
		//new_repository.requireClass("keso/core/Portal");

		/* HACK ahead */
		int domainid = serviceDefinition.getDomain().domainid;

		String serviceClassName = serviceDefinition.getServiceClassName();
		String serviceInterfaceName = serviceDefinition.getServiceInterfaceName();
		new_repository.requireClass(domainid, "keso/core/Portal");
		new_repository.requireClass(domainid, serviceInterfaceName); 
		new_repository.requireClass(domainid, serviceClassName);

		// require service methods
		// constructor
		new_repository.requireMethod(domainid, serviceClassName , "<init>()V");
		MethodData[] methods = repository.getClass(serviceInterfaceName).getMethodData();

		for (int i = 0; i < methods.length; ++i) {
			// require all methods found in the interface for the implementation
			new_repository.requireMethod(domainid, serviceClassName , methods[i].getMethodNameAndType());
		}

		if (serviceDefinition.hasNetworkAccess()) {

			Vector nets = serviceDefinition.getNetworks();
			for (int j = 0; j < nets.size(); ++j) {

				String driverIfaceName = ((NetworkDefinition) serviceDefinition.getNetworks().elementAt(j)).getDriverInterfaceName();

				String protocolClassName = ((NetworkDefinition) nets.elementAt(j)).getProtocolName();           
				new_repository.requireClass(domainid, protocolClassName);
				IMClass packetStream = repository.getClass("keso/core/io/PacketStream");
				methods = packetStream.getMethodData();

				// constructor
				new_repository.requireMethod(domainid, protocolClassName , "<init>(L" + driverIfaceName + ";IIIBB)V");

				for (int i = 0; i < methods.length; ++i) {
					// require all methods found in the interface for the implementation
					new_repository.requireMethod(domainid, protocolClassName , methods[i].getMethodNameAndType());
				}

				String driverClassName = serviceDefinition.getDrivers()[j];
				new_repository.requireClass(domainid, driverClassName);

				IMClass driverIface = repository.getClass(driverIfaceName);
				methods = driverIface.getMethodData();

				// constructor
				opts.verbose(driverClassName+"<init>()V");
				new_repository.requireMethod(domainid, driverClassName , "<init>()V");

				for (int i = 0; i < methods.length; ++i) {
					// require all methods found in the interface for the implementation
					new_repository.requireMethod(domainid, driverClassName , methods[i].getMethodNameAndType());
				}

			}

		}
		this.repository = new_repository;
	}

	/*
	public static String getSizeOfArguments(IMMethod meth) throws CompileException {
		StringBuffer str = new StringBuffer("0");
		IMMethodFrame frame = meth.getMethodFrame();
		IMSlot[] argSlots = frame.getMethodArguments();

		for (int i = 1; i < argSlots.length; ++i) {
			str.append("+sizeof(");
			str.append(argSlots[i].cType());
			str.append(")");
		}

		return str.toString();
	}
	*/

	public static String getSizeOfReturnValues(MethodData[] meths) throws CompileException {
		int max_size = 0;
		if (meths==null) return "0";
		for (int i=0;i<meths.length;i++) {
			String ctype = BuilderOptions.typeToString(meths[i].getBasicReturnType());
			int r_size = BuilderOptions.getTypeSize(ctype);
			max_size = Math.max(max_size, r_size);
		}
		return Integer.toString(max_size);        
	}
/*
	public static String getSizeOfReturnValues(MethodData[] meths) throws CompileException {
		return appendSize(meths, 0).toString();
	}

	private static StringBuffer appendSize(MethodData[] meths, int start) {

        boolean hasReturnValue = false;
		String returnType = null;
        
        if (meths[start] != null) {
		hasReturnValue = meths[start].getBasicReturnType() != BCBasicDatatype.VOID;
		returnType = BuilderOptions.getOpts().typeToString(meths[start].getBasicReturnType());
        }

		if (start >= meths.length - 1) {

			if (hasReturnValue) {
				StringBuffer str = new StringBuffer("sizeof(");
				str.append(returnType);
				str.append(")");
				return str;
			} else {
				return new StringBuffer("0");
			}

		} else {

			if (hasReturnValue) {
				StringBuffer str = new StringBuffer("(sizeof(");
				str.append(returnType);
				str.append(") > ");
				str.append(appendSize(meths, start + 1));
				str.append(" ? sizeof(");
				str.append(returnType);
				str.append(") : ");
				str.append(appendSize(meths, start + 1));
				str.append(")");

				return str;
			} else {
				return appendSize(meths, start + 1);
			}
		}
	}
	*/

	private String serviceAlias=null;
	public String getServiceAlias() {
		if (serviceAlias==null)
			serviceAlias = DecoratedNames.toEscString(serviceDefinition.getServiceName());
		return serviceAlias;
	}

	public String getDriverObjectName(NetworkDefinition net) {
		StringBuffer str = new StringBuffer(getServiceAlias());
		str.append("_");
		str.append(DecoratedNames.toEscString(net.getNetworkName()));
		str.append("_driver_obj");
		return str.toString();
	}

	public String getTransmitBufferObjectName(NetworkDefinition net) {
		return getServiceAlias() + "_" + DecoratedNames.toEscString(net.getNetworkName()) + "_transmit_buffer_obj";
	}

	public String getProtocolObjectName(NetworkDefinition net) {
		return getServiceAlias() + "_" + DecoratedNames.toEscString(net.getNetworkName()) + "_protocol_obj";
	}

	public static String getPacketHandlerFunction(ServiceDefinition srv, NetworkDefinition net) throws CompileException {
		return "keso_" + getAlias(srv.getServiceName()) + "_" + DecoratedNames.toEscString(net.getNetworkName()) + "_handler";
	}

	public String getServiceObjectName() throws CompileException {
		return "keso_" + getAlias(serviceDefinition.getServiceName()) + "_obj";
	}

	private static String getAlias(String name) {
		return DecoratedNames.toEscString(name);
	}

	/**
	 * Get the C macro to get a service object reference.
	 */
	public static String getGetMacro(String serviceName) throws CompileException {
		ServiceDefinition srv = ServiceManager.instance.lookupService(serviceName);
		if (srv != null) {
			return "GET_" + getAlias(serviceName).toUpperCase() + "()";
		} else {
			throw new CompileException("Lookup of service " + serviceName + " failed.");
		}
	}

	/**
	 * Get the C macro to lookup a service object reference.
	 */
	public static String getLookupMacro(String serviceName) throws CompileException {
		ServiceDefinition srv = ServiceManager.instance.lookupService(serviceName);
		if (srv != null) {
			return "LOOKUP_" + getAlias(serviceName).toUpperCase() + "()";
		} else {
			throw new CompileException("Lookup of service " + serviceName + " failed.");
		}
	}

	/**
	 * create additional C code for portal service.
	 */
	public void translate(Coder coder) throws CompileException {

		init.translate(coder);

		coder.global_header_add("#define ");
		coder.global_header_add(getGetMacro(serviceDefinition.getServiceName()));
		coder.global_header_add(" (object_t*) (KESO_CURRENT_DOMAIN == ");
		coder.global_header_add(serviceDefinition.getDomain().domainid);
		coder.global_header_add(" ? &");
		coder.global_header_add(getServiceObjectName());
		coder.global_header_add(" : 0);\n");

		IMClass serviceClass = serviceDefinition.getServiceClass(repository);
		IMClass memoryClass = repository.getClass("keso/core/Memory");
		boolean llrefs = opts.hasLinkedListOfLocalReferences();

		if (serviceDefinition.hasNetworkAccess()) {

			Vector nets = serviceDefinition.getNetworks();
			for (int i = 0; i < nets.size(); ++i) {

				NetworkDefinition net = (NetworkDefinition) nets.elementAt(i);

				// create protocol object
				IMClass protocolClass = repository.getClass(net.getProtocolName());
				coder.global_add_allocConstObject(protocolClass, getProtocolObjectName(net));
				coder.global_add_init_end();        
				coder.global_addln("\n");

				// create driver object
				IMClass driverClass = repository.getClass(serviceDefinition.getDrivers()[i]);          
				coder.global_add_allocConstObject(driverClass, getDriverObjectName(net)); 
				coder.global_add_init_end();
				coder.global_addln("\n");

				// transmit buffer
				coder.global_add("object_t *");
				coder.global_add(getTransmitBufferObjectName(net));
				coder.global_addln(";\n");


				coder.global_header_add("__inline__ void ");
				coder.global_header_add(getPacketHandlerFunction(serviceDefinition, net));
				coder.global_header_add("(void);\n");

				coder.global_add("void ");
				coder.global_add(getPacketHandlerFunction(serviceDefinition, net));
				coder.global_addln("() {");

				// call the default constructor
				IMMethod constructor = driverClass.getMethod("<init>()V");
				coder.global_add("\t");
				coder.global_add(constructor.getAlias());
				coder.global_add("(");
				coder.global_add(coder.add_accessStaticObject(driverClass, getDriverObjectName(net)));
				coder.global_addln(");");
				coder.global_addln();

				// call all specified bean methods
				Vector methods = ((NetworkDefinition) serviceDefinition.getNetworks().elementAt(i)).getDriverInitMethods();
				Vector params = ((NetworkDefinition) serviceDefinition.getNetworks().elementAt(i)).getDriverInitParameters();

				for (int j = 0; j < methods.size(); ++j) {
					IMMethod meth = driverClass.getMethod((String) methods.elementAt(j));
					coder.global_add("\t");
					coder.global_add(meth.getAlias());
					coder.global_add("(");
					coder.global_add(coder.add_accessStaticObject(driverClass, getDriverObjectName(net)));
					coder.global_add(", ");
					coder.global_add(params.elementAt(j));
					coder.global_addln(");");
				}

				coder.global_addln();
				String cName = "<init>(L" + ((NetworkDefinition) serviceDefinition.getNetworks().elementAt(i)).getDriverInterfaceName() + ";IIIBB)V";
				constructor = protocolClass.getMethod(cName);

				coder.global_add("\t");
				coder.global_add(constructor.getAlias());
				coder.global_add("(");
				coder.global_add(coder.add_accessStaticObject(protocolClass, getProtocolObjectName(net)));
				coder.global_add(", ");
				coder.global_add(coder.add_accessStaticObject(driverClass, getDriverObjectName(net)));
				// rxId
                coder.global_add(", 0x");
				coder.global_add(Integer.toHexString(ServiceManager.instance.getServiceIdentifier(serviceDefinition, net)));
                // rxMask
                coder.global_add(", 0x");
				coder.global_add(Integer.toHexString(ServiceManager.instance.getServiceMask()));
			    // bufferMask
                coder.global_add(", 0x");
				coder.global_add(Integer.toHexString(ServiceManager.instance.getServiceBufferMask()));
			    // idBits 	
                coder.global_add(", ");
				coder.global_add(ServiceManager.instance.getIDWidth());
				// numRxBuffers
                coder.global_add(", ");
                coder.global_add(ServiceManager.instance.getServiceBufferCount());
                               
                coder.global_addln(");");
				coder.global_addln();

			
				// allocate buffers for sending and receiving        
				IMMethod allocReceiveBuffer = protocolClass.getMethod("allocReceiveBuffer(ISI)Z");
				IMMethod allocTransmitBuffer = protocolClass.getMethod("allocTransmitBuffer(SI)Lkeso/core/Memory;");


				for (int imp = 0; imp < serviceDefinition.getImports().size(); ++imp) {
					ImportDefinition impDef = (ImportDefinition) serviceDefinition.getImports().elementAt(imp);
					for (int method = 0; method < serviceDefinition.getMethods().length; ++method) {
						String methodName = serviceDefinition.getMethods()[method].getMethodNameAndType();
						IMMethod serviceMethod = serviceClass.getMethod(methodName);
						int id = ServiceManager.instance.getCallIdentifier(impDef, methodName);

						coder.global_add("/* ");
						coder.global_add(impDef);
						coder.global_add(".");
						coder.global_add(methodName);
						coder.global_addln(" */");

						coder.global_add("\tif (!");
						coder.global_add(allocReceiveBuffer.getAlias());
						coder.global_add("(");
						coder.global_add(coder.add_accessStaticObject(protocolClass, getProtocolObjectName(net)));
						coder.global_add(", 0x");
						coder.global_add(Integer.toHexString(id));
						coder.global_add(", ");
						coder.global_add(serviceMethod.getSizeOfArguments());
						coder.global_add(", ");
						coder.global_add(serviceDefinition.getAllocTimeout());
						coder.global_addln(")) {");
						coder.global_addln("\t\t/* Allocation of the receive buffer failed */");
						coder.global_addln("\t\treturn;");
						coder.global_addln("\t}");

					}
				}

				coder.global_add("\t");
				coder.global_add(getTransmitBufferObjectName(net));
				coder.global_add(" = (object_t *) ");
				coder.global_add(allocTransmitBuffer.getAlias());
				coder.global_add("(");
				coder.global_add(coder.add_accessStaticObject(protocolClass, getProtocolObjectName(net)));
				coder.global_add(", ");
				coder.global_add(getSizeOfReturnValues(serviceDefinition.getMethods()));
				coder.global_add(", ");
				coder.global_add(serviceDefinition.getAllocTimeout());
				coder.global_addln(");");
				coder.global_addln(); 

				coder.global_add("\tif (NULL == ");
				coder.global_add(getTransmitBufferObjectName(net));
				coder.global_addln(") {");
				coder.global_addln("\t\t/* Allocation of the transmit buffer failed */");
				coder.global_addln("\t\treturn;");
				coder.global_addln("\t}");
				coder.global_addln();

				// handle packets
				coder.global_addln("\tfor(;;) {");                 
				coder.global_addln();

				IMMethod readMethod = protocolClass.getMethod("read(I)Lkeso/core/Memory;");
				IMMethod getPacketLength = protocolClass.getMethod("getPacketLength()S");
				IMMethod getPacketID = protocolClass.getMethod("getPacketID()I");
				IMMethod releaseReceiveBuffer = protocolClass.getMethod("releaseReceiveBuffer()V");

				coder.global_add("\t\tobject_t *receiveBuffer = (object_t *) "); // FIXME
				coder.global_add(readMethod.getAlias());
				coder.global_add("(");

				if (llrefs && readMethod.canBlock()) {
					opts.critical("FIXME");
					coder.global_add("(object_t **) 0, "); //FIXME
				}

				coder.global_add(coder.add_accessStaticObject(protocolClass, getProtocolObjectName(net)));
				coder.global_addln(", -1);");
				coder.global_addln();

				coder.global_addln("\t\tif (receiveBuffer == NULL) {");
				coder.global_addln("\t\t\t/* error e.g. timeout occured - this must not happen on service side! */");
				coder.global_addln("\t\t\tcontinue;");
				coder.global_addln("\t\t}");

				coder.global_addln();

				coder.global_add("\t\tjshort packet_length = ");
				coder.global_add(getPacketLength.getAlias());
				coder.global_add("(");
				coder.global_add(coder.add_accessStaticObject(protocolClass, getProtocolObjectName(net)));
				coder.global_addln(");");

				coder.global_add("\t\tjint packet_id = ");
				coder.global_add(getPacketID.getAlias());
				coder.global_add("(");
				coder.global_add(coder.add_accessStaticObject(protocolClass, getProtocolObjectName(net)));
				coder.global_addln(");");

				coder.global_add("\t\tjbyte *buffer = (jbyte*)");
				coder.global_add(coder.getField(memoryClass, "receiveBuffer", "addr"));
				coder.global_addln(";");


				// build dispatcher
				coder.global_add("\t\tswitch (");
				coder.global_add(ServiceManager.instance.emitGetMethodID("packet_id"));
				coder.global_addln(") {");

				for (int method = 0; method < serviceDefinition.getMethods().length; ++method) {
					IMMethod currentMethod = serviceClass.getMethod(serviceDefinition.getMethods()[method].getMethodNameAndType());

					if (currentMethod == null) {
						throw new CompileException("Service method not found " + serviceDefinition.getMethods()[method].getMethodNameAndType() + " in class " + serviceClass.getClassName());
					}


					coder.global_add("\t\t\tcase 0x");
					coder.global_add(Integer.toHexString(ServiceManager.instance.getMethodID(serviceDefinition, currentMethod.getMethodNameAndType())));
					coder.global_add(": /* ");
					coder.global_add(serviceClass.getClassName());
					coder.global_add(".");
					coder.global_add(currentMethod.getMethodNameAndType());
					coder.global_addln(" */");
					coder.global_addln("\t\t\t{");

					IMSlot[] argSlots = currentMethod.getMethodFrame().getMethodArguments();
					boolean hasArguments = argSlots.length > 1;                              

					// TODO check length of packet

					for (int param = 1; param < argSlots.length; ++param) {
						coder.global_add("\t\t\t\t");
						coder.global_add(argSlots[param].cType());
						coder.global_add(" param_");
						coder.global_add(param);
						coder.global_addln(";");

						coder.global_add("\t\t\t\tkeso_memcpy((jbyte*) &param_");
						coder.global_add(param);
						coder.global_add(", buffer, sizeof(");
						coder.global_add(argSlots[param].cType());
						coder.global_addln("));");

						coder.global_add("\t\t\t\tbuffer += sizeof(");
						coder.global_add(argSlots[param].cType());
						coder.global_addln(");");

					}

					coder.global_add("\t\t\t\t");
					coder.global_add(releaseReceiveBuffer.getAlias());
					coder.global_add("(");
					coder.global_add(coder.add_accessStaticObject(protocolClass, getProtocolObjectName(net)));
					coder.global_addln(");");

					coder.global_add("\t\t\t\t");

					if (currentMethod.hasReturnValue()) {
						coder.global_add(opts.typeToString(currentMethod.getBasicReturnType()));
						coder.global_addln(" return_value = ");
					}

					coder.global_add(currentMethod.getAlias());
					coder.global_add("(");
					coder.global_add(coder.add_accessStaticObject(serviceClass, getServiceObjectName()));

					for (int param = 1; param < argSlots.length; ++param) {
						coder.global_add(", param_");
						coder.global_add(param);
					}

					coder.global_addln(");"); 

					if (currentMethod.hasReturnValue()) {

						coder.global_add("\t\t\t\tkeso_memcpy((jbyte*) ");
						coder.global_add(coder.getField(memoryClass, getTransmitBufferObjectName(net), "addr"));
						coder.global_add(", (jbyte*) &return_value, sizeof(");
						coder.global_add(opts.typeToString(currentMethod.getBasicReturnType()));
						coder.global_addln("));");

						coder.global_add("\t\t\t\tpacket_length = sizeof(");
						coder.global_add(opts.typeToString(currentMethod.getBasicReturnType()));
						coder.global_addln(");");
					} else {
						coder.global_addln("\t\t\t\tpacket_length = 0;");
					}

					coder.global_add("\t\t\t\tpacket_id = ");
					coder.global_add(ServiceManager.instance.emitGetReturnID("packet_id"));
					coder.global_addln(";");

					coder.global_addln("\t\t\t\tbreak;");
					coder.global_addln("\t\t\t}");

				}


				coder.global_addln("\t\t\tdefault: ");
				coder.global_addln("\t\t\t\t/* This id is unknown - this should never happen */");
				coder.global_addln("\t\t\t\tcontinue;");
				coder.global_addln("\t\t}");

                
				IMMethod writeMethod = protocolClass.getMethod("write(ISI)Z");

				// transfer return value
				coder.global_add("\t\tif (!");
				coder.global_add(writeMethod.getAlias());
				coder.global_add("(");

				if (llrefs && writeMethod.canBlock()) {
					opts.critical("FIXME");
					coder.global_add("(object_t **) 0, "); //FIXME
				}

				coder.global_add(coder.add_accessStaticObject(protocolClass, getProtocolObjectName(net)));
				coder.global_add(", packet_id, packet_length, ");
				coder.global_add(serviceDefinition.getWriteTimeout());
				coder.global_addln(")) {");
				coder.global_addln("\t\t\t/* timeout occured */");
				coder.global_addln("\t\t\tcontinue;");
				coder.global_addln("\t\t}");
				coder.global_addln();

				coder.global_addln("\t}\n");
				coder.global_addln("}\n");
			}

		}
	}

}
