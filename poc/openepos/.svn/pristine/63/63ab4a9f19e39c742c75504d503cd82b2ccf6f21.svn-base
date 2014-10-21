/**(c)

  Copyright (C) 2006 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.compiler.config;

import keso.compiler.config.*;
import keso.compiler.imcode.*;

import keso.compiler.*;

public class ImportDefinition extends Set {

    public int identifier;
    
	public ImportDefinition(DomainDefinition dom, String name, int line) {
		super(dom, name, line);
		parent.registerFinalize(this);
	}

	public String getServiceName() {
		return ident;
	}

    public boolean hasNetworkAccess() {     
        return getNetwork() != null;
    }

    public boolean isLocal() {
        return getService().getSystem() == getSystem();
    }

    public boolean needDataConversion() {
        return getSystem().hasLittleEndianMemory() != getService().getSystem().hasLittleEndianMemory();
    }

    public String getDriver() {
        Attribut attr = getAttribute("Driver");
        if (attr == null) {
            return null;
        }
        return attr.valueString();
    }

    public void checkClasses(ClassStore repository) throws CompileException {
        if (!isLocal()) {
            IMClass driverIface = repository.getClass(getNetwork().getDriverInterfaceName());
            IMClass driverClass = repository.getClass(getDriver());

            if (driverClass == null) {
                throw new CompileException("The Driver " + getDriver() + " specified for import " + this + " was not found.");
            }

            if (!driverClass.implementsInterface(driverIface)) {
                throw new CompileException("The Driver " + getDriver() + " specified for import " + this + " doesn't implement the specified DriverInterface.");
            }
        }
    }
    

    public int getCallIdentifier(String methodNameAndType) {
        return ServiceManager.instance.getCallIdentifier(this, methodNameAndType);
    }

    public int getReturnIdentifier(String methodNameAndType) {
        return ServiceManager.instance.getReturnIdentifier(this, methodNameAndType);
    }
    
    public NetworkDefinition getNetwork() {
        return ServiceManager.instance.lookupNetwork(getAccess());
    }

    public int getReadTimeout() {
        Attribut attr = getAttribute("ReadTimeout");
        if (attr == null) {
            return 0;   // NO_TIMEOUT
        }
        return attr.valueInt();
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
	/*
	 * Man kann im Builder nicht auf Klassen zu greifen die
	 * aus der Bibliothek stammen. Das soll auch _nicht_
	 * funktionieren!
	 *
	 * Eine Moeglichkeit waere im ConstantenPool nachschauen.
	 *
	 * IMClass memService = repository.getClass("keso/core/MemoryService");
	 *
	 * Aber das ist auch nicht einfach.
	 */
	//return keso.core.MemoryService.BLOCK;
        if (attr == null) {
            return -1;      // BLOCK
        }
        return attr.valueInt();
    }
    
    public String getAccess() {
        Attribut attr = getAttribute("Access");
        if (attr == null) {
            return null;
        }
        return attr.valueString();
    }

    public ServiceDefinition getService() {
        return ServiceManager.instance.lookupService(getServiceName());
    }
    
	public DomainDefinition getDomain() {
		return (DomainDefinition) parent; 
	}

    public SystemDefinition getSystem() {
        return (SystemDefinition) parent.parent;
    }

	protected void finalizeCfg(FinalizeResult res) throws keso.compiler.config.parser.ParseException {
		System.err.println("finalizeCfg ImportDef. " + ident);
	}

	public String toString() { return ident + " (domain: " + parent + ")"; }
}
