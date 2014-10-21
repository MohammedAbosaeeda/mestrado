/**(c)

  Copyright (C) 2007 Christian Wawersich 

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
 * This class implements the class constructor of a remote service. 
 */
final public class IMPortalServiceInit implements IMPortalInit {

    private ServiceDefinition serviceDefinition;
    private IMPortalService service;

    public IMPortalServiceInit(IMPortalService srv, ServiceDefinition srvDef) throws CompileException {
        service = srv; 
        serviceDefinition = srvDef;
    }

    final public void translate(Coder coder) throws CompileException {

	    ClassStore repository = coder.getClassStore();

        String name = DecoratedNames.toEscString(serviceDefinition.getServiceName()) + "_service_init";
        coder.registerPortalInit(name);
        
        coder.global_header_add("__inline__ void ");
        coder.global_header_add(name);
        coder.global_header_add("(void);\n");

        // create service object
        IMClass serviceClass = serviceDefinition.getServiceClass(repository);
        coder.global_add_allocConstObject(serviceClass, service.getServiceObjectName());
        coder.global_add_init_end();        
        coder.global_addln("\n");


        coder.global_add("void ");
        coder.global_add(name);
        coder.global_addln("() {");

        // init service object
        IMMethod constructor = serviceClass.getMethod("<init>()V");
        coder.global_add("\t");
        coder.global_add(constructor.getAlias());
        coder.global_add("(");
        coder.global_add(coder.add_accessStaticObject(serviceClass, service.getServiceObjectName()));
        coder.global_addln(");");
        coder.global_addln("}");
       
    }
}
