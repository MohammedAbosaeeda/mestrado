/**(c)
 *
 * Copyright (C) 2007 Christian Wawersich
 *
 * This file is part of the KESO Operating System.
 *
 * It is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * Please contact wawi@cs.fau.de for more info.
 *
 * (c)**/

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
 * This class implements the class constructor of a remote class. 
 */
final public class IMPortalClassInit implements IMPortalInit {
    
    private ImportDefinition importDefinition;
    private ClassStore repository;
    private IMPortalClass thisClass;

    public IMPortalClassInit(IMPortalClass clazz, ClassStore src_repository, ImportDefinition importDef) throws CompileException {
		 
        thisClass = clazz;
        importDefinition = importDef;
        repository = src_repository;

    }
        
    final public void translate(Coder coder) throws CompileException {
        
        String name = DecoratedNames.toEscString(importDefinition.getServiceName()) + "_proxy_init";
        coder.registerPortalInit(name);

        // create protocol object
        IMClass protocolClass = repository.getClass(importDefinition.getNetwork().getProtocolName());
        coder.global_add_allocConstObject(protocolClass, thisClass.getProtocolObjectName());
        coder.global_add_init_end();        
        coder.global_addln("\n");

        // create driver object
        IMClass driverClass = repository.getClass(importDefinition.getDriver());          
        coder.global_add_allocConstObject(driverClass, thisClass.getDriverObjectName()); 
        coder.global_add_init_end();
        coder.global_addln("\n");
        
        // transmit buffer
        coder.global_header_add("extern object_t *");
        coder.global_header_add(thisClass.getTransmitBufferObjectName());
        coder.global_header_add(";\n");

        coder.global_add("object_t *");
        coder.global_add(thisClass.getTransmitBufferObjectName());
        coder.global_addln(";\n");

        
        coder.global_header_add("void ");
        coder.global_header_add(name);
        coder.global_header_add("(void);\n");    
       
        coder.global_add("void ");
        coder.global_add(name);
        coder.global_addln("() {\n"); 
        
        
        // call the default constructor
        IMMethod constructor = driverClass.getMethod("<init>()V");
        coder.global_add("\t");
        coder.global_add(constructor.getAlias());
        coder.global_add("(");
        coder.global_add(coder.add_accessStaticObject(driverClass, thisClass.getDriverObjectName()));
        coder.global_addln(");");
        
        // call all specified bean methods
        Vector methods = importDefinition.getNetwork().getDriverInitMethods();
        Vector params = importDefinition.getNetwork().getDriverInitParameters();
        
        for (int i = 0; i < methods.size(); ++i) {
            IMMethod meth = driverClass.getMethod((String) methods.elementAt(i));
            coder.global_add("\t");
            coder.global_add(meth.getAlias());
            coder.global_add("(");
            coder.global_add(coder.add_accessStaticObject(driverClass, thisClass.getDriverObjectName()));
            coder.global_add(", ");
            coder.global_add(params.elementAt(i));
            coder.global_addln(");");
        }
        
        String cName = "<init>(L" + importDefinition.getNetwork().getDriverInterfaceName() + ";IIIBB)V";
        constructor = protocolClass.getMethod(cName);
        
        coder.global_add("\t");
        coder.global_add(constructor.getAlias());
        coder.global_add("(");
        coder.global_add(coder.add_accessStaticObject(protocolClass, thisClass.getProtocolObjectName()));
        coder.global_add(", ");
        coder.global_add(coder.add_accessStaticObject(driverClass, thisClass.getDriverObjectName()));
        // rxId
        coder.global_add(", 0x");
        coder.global_add(Integer.toHexString(ServiceManager.instance.getImportIdentifier(importDefinition)));
        // rxMask
        coder.global_add(", 0x");
        coder.global_add(Integer.toHexString(ServiceManager.instance.getImportMask()));
        // bufferMask
        coder.global_add(", 0x00000000");
        // idBits
        coder.global_add(", ");
        coder.global_add(ServiceManager.instance.getIDWidth());
        // numRxBuffers
        coder.global_addln(", 1);");
        
        
        // allocate a buffer for sending and receiving        
        IMMethod allocReceiveBuffer = protocolClass.getMethod("allocReceiveBuffer(ISI)Z");
        IMMethod allocTransmitBuffer = protocolClass.getMethod("allocTransmitBuffer(SI)Lkeso/core/Memory;");
        
        coder.global_add("\tif (!");
        coder.global_add(allocReceiveBuffer.getAlias());
        coder.global_add("(");
        coder.global_add(coder.add_accessStaticObject(protocolClass, thisClass.getProtocolObjectName()));
        coder.global_add(", 0x");
        coder.global_add(Integer.toHexString(ServiceManager.instance.getImportIdentifier(importDefinition)));
        coder.global_add(", ");
        coder.global_add(thisClass.getReceiveBufferSize());
        coder.global_add(", ");
        coder.global_add(importDefinition.getAllocTimeout());
        coder.global_addln(")) {");
        coder.global_addln("\t\t/* Allocation of the rx buffer failed */");
        coder.global_addln("\t}");
        
        IMClass memoryClass = repository.getClass("keso/core/Memory");
        
        coder.global_add("\t");
        coder.global_add(thisClass.getTransmitBufferObjectName());
        coder.global_add(" = (object_t *) ");
        coder.global_add(allocTransmitBuffer.getAlias());
        coder.global_add("(");
        coder.global_add(coder.add_accessStaticObject(protocolClass, thisClass.getProtocolObjectName()));
        coder.global_add(", ");
        coder.global_add(thisClass.getTransmitBufferSize());
        coder.global_add(", ");
        coder.global_add(importDefinition.getAllocTimeout());
        coder.global_addln(");");
        coder.global_addln("}\n");
        
    }
}
