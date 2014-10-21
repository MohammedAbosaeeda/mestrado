/**(c)

  Copyright (C) 2006 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.compiler.config;

import keso.compiler.*;
import keso.compiler.imcode.*;
import java.util.Vector;
import keso.classfile.*;

public class ServiceDefinition extends Set {

    public int identifier;
    
    private Vector networks; 
    private Vector imports;
    private MethodData[] methods;
    
	public ServiceDefinition(DomainDefinition dom, String name, int line) {
		super(dom, name, line);
		parent.registerFinalize(this);
        imports = new Vector();
        networks = new Vector();
	}

    public void addImport(ImportDefinition def) {
        def.identifier = imports.size();
        imports.add(def);
    }

    public void lookupMethods(ClassStore repository) throws CompileException {
        String serviceIfaceName = getServiceInterfaceName();
         
        if (serviceIfaceName == null) {
            throw new CompileException("No ServiceInterface defined for " + this);
        }
            
        IMClass serviceIface = repository.getClass(serviceIfaceName); 
            
        if (serviceIface == null) {
            throw new CompileException("The ServiceInterface " + serviceIfaceName + " specified for service " + this + " was not found.");
        } 
            
        methods = serviceIface.getMethodData();
    }

    public void checkClasses(ClassStore repository) throws CompileException {
        String serviceIfaceName = getServiceInterfaceName();
         
        if (serviceIfaceName == null) {
            throw new CompileException("No ServiceInterface defined for " + this);
        }

        String serviceClassName = getServiceClassName();

        if (serviceClassName == null) {
            throw new CompileException("No ServiceClass defined for " + this);
        }
            
        IMClass serviceIface = repository.getClass(serviceIfaceName); 
        IMClass portalIface = repository.getClass("keso/core/Portal");   
        IMClass serviceClass = repository.getClass(serviceClassName);
        
        if (serviceIface == null) {
            throw new CompileException("The ServiceInterface " + serviceIfaceName + " specified for service " + this + " was not found.");
        }

        if (serviceClass == null) {
            throw new CompileException("The ServiceClass " + serviceClassName + " specified for service " + this + " was not found.");
        }

        if (!serviceIface.implementsInterface(portalIface)) {
            throw new CompileException("The ServiceInterface " + serviceIfaceName + " specified for service " + this + " doesn't implement the Portal interface.");
        }

        if (!serviceClass.implementsInterface(serviceIface)) {
            throw new CompileException("The ServiceClass " + serviceClassName + " specified for service " + this + " doesn't implement the specified ServiceInterface.");
        }

        
        if (networks.size() > 0) {
            if (getDrivers() == null) {
                throw new CompileException("No drivers specified for service " + this + " with specified networks.");
            } else if (networks.size() != getDrivers().length) {
                throw new CompileException("The amout of drivers specified for service " + this + " doesn't match the amount of specified networks.");
            }
        }

        
        // check network drivers
        for (int i = 0; i < networks.size(); ++i) {
            NetworkDefinition netDef = (NetworkDefinition) networks.elementAt(i);
            
            IMClass driverIface = repository.getClass(netDef.getDriverInterfaceName());
            IMClass driverClass = repository.getClass(getDrivers()[i]);
            
            if (driverClass == null) {
                throw new CompileException("The Driver " + getDrivers()[i] + " specified for service " + this + " was not found.");
            }

            
            if (!driverClass.implementsInterface(driverIface)) {
                throw new CompileException("The Driver " + getDrivers()[i] + " specified for service " + this + " doesn't implement the specified DriverInterface.");
            }

        }
  
    }
    
    public void addNetwork(NetworkDefinition def) {
        networks.add(def);
    }

    public Vector getImports() {
        return imports;
    }

    public MethodData[] getMethods() {
        return methods;
    }

    public Vector getNetworks() {
        return networks;
    }    
   
	public String getServiceClassName() {
	    Attribut attr = getAttribute("ServiceClass");
        if (attr == null) {
            return null;
        }
        return attr.valueString();
	}

    public int getWriteTimeout() {
        Attribut attr = getAttribute("WriteTimeout");
        if (attr == null) {
            return 0;   // NO_TIMEOUT
        }
        return attr.valueInt();
    }

    public int getAllocTimeout() {
        Attribut attr = getAttribute("AllocTimeout");
        if (attr == null) {
            return -1;  // BLOCK
        }
        return attr.valueInt();
    }
    
    public IMClass getServiceClass(ClassStore repository) {
        return repository.getClass(getServiceClassName());
    }
    

	public String getServiceInterfaceName() {
		Attribut attr = getAttribute("ServiceInterface");
        if (attr == null) {
            return null;
        }
        return attr.valueString();
	}

    
    public IMClass getServiceInterface(ClassStore repository) {
        return repository.getClass(getServiceInterfaceName());
    }
    

    public boolean hasNetworkAccess() {
        return getNetworks().size() > 0;
    }
    
    /**
     * Get the class names of the device drivers used to communicate with the networks.
     */ 
    public String[] getDrivers() {
        Attribut attr = getAttribute("Drivers");
        if (attr == null) {
            return null;
        }
        return attr.valueString().split(":");
    }
    

    /**
     * Get the names of the networks in which this service will be available
     */
    public String[] getAccess() {
        Attribut attr = getAttribute("Access");
        if (attr == null) {
            return null;
        }
        return attr.valueString().split(":");
    }
    
    
    public boolean hasLittleEndianMemory() {
        return BuilderOptions.hasLittleEndianMemory(getSystem().getTarget());
    }
    
	public String getServiceName() {
        return ident;
    }
    
    public DomainDefinition getDomain() {
		return (DomainDefinition) parent; 
	}

    public SystemDefinition getSystem() {
        return (SystemDefinition) parent.parent;
    }

	protected void finalizeCfg(FinalizeResult res) throws keso.compiler.config.parser.ParseException {
		// TODO: not called
		//System.err.println("finalizeCfg ServiceDef."+getAttribute("ServiceClass").valueString());
		//res.requireMethod(getAttribute("ServiceClass").valueString(), "launch()V");
		//res.requireMethod(getAttribute("ServiceClass").valueString(), "<init>()V");
	}

	public String toString() { 
        return getServiceName() + " (domain: " + getDomain() + ")";
    }
}
