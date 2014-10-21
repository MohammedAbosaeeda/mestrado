/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.compiler.config;

import keso.compiler.*;
import keso.compiler.config.parser.ParseException;
import keso.util.Debug;
import keso.compiler.config.*;
import keso.compiler.imcode.*;


import java.util.Vector;
import java.util.Iterator;

final public class NetworkDefinition extends Set {
    
    public int identifier;
    private Vector services;
    private final static String[] knownAttributes = new String[] {
        "Protocol",
        "DriverInterface"
    };
    
    private Vector initMethods;
    private Vector initParams;
    
    
    public NetworkDefinition(Set parent, String name, int line) throws ParseException {
        super(parent, name, line);
        services = new Vector();
        initMethods = new Vector();
        initParams = new Vector();
    }

    public String getNetworkName() {
        return ident;
    }

    public void addService(ServiceDefinition def) {
        services.add(def);
    }

    public Vector getServices() {
        return services;
    }

    public void checkClasses(ClassStore repository) throws CompileException {
           
        IMClass driverIface = repository.getClass(getDriverInterfaceName());
        IMClass protocol = repository.getClass(getProtocolName());
        IMClass packetStream = repository.getClass("keso/core/io/PacketStream");
        
        if (driverIface == null) {
            throw new CompileException("The DriverInterface " + getDriverInterfaceName() + " specified for network " + this + " was not found.");
        }

        // check required bean methods for this interface
        Vector attrs = getDriverAttributes();
                
        for (int i = 0; i < attrs.size(); ++i) {
            String id = ((String) attrs.elementAt(i));
            String methodName = "set" + id + "(I)V";
            
            //TODO: Check other int types
	    if (driverIface.getMethod(methodName) == null) {
		    System.err.println("The method " + methodName + " was not found in interface " + getDriverInterfaceName());
	    } else {
		    initMethods.add(methodName);
		    initParams.add(new Integer(getAttribute(id).valueInt()));
	    }
            
        }
        
        if (packetStream == null) {
            throw new CompileException("The interface PacketStream was not found.");
        }

        if (protocol == null) {
            throw new CompileException("The Protocol " + getProtocolName() + " specified for network " + this + " was not found.");
        }

        if (!protocol.implementsInterface(packetStream)) {
            throw new CompileException("The Protocol " + getProtocolName() + " specified for network " + this + " doesn't implement PacketStream.");
        }
      
    }
    
    public String getProtocolName() {
        Attribut attr = getAttribute("Protocol");
        if (attr == null) {
            return null;
        }
        return attr.valueString();
    }

    //TODO access to driver attributes
    public String getDriverInterfaceName() {
        Attribut attr = getAttribute("DriverInterface");
        if (attr == null) {
            return null;
        }
        return attr.valueString();
    }
    
    public Vector getDriverAttributes() {
        Vector result = new Vector();
        String[] ids = getAllIdentifiers();
        for (int i = 0; i < ids.length; ++i) {
            boolean found = false;

            for (int j = 0; j < knownAttributes.length; ++j) {

                if (knownAttributes[j].equals(ids[i])) {
                    found = true;
                    break;
                }
            }
            
            if (!found) {
               result.add(ids[i]);
            }
        }
        return result;
    }
    
    public Vector getDriverInitMethods() {
        return initMethods;
    }

    public Vector getDriverInitParameters() {
        return initParams;
    }

    
}
