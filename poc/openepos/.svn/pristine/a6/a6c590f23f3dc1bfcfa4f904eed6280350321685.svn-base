package keso.compiler;

import keso.compiler.config.*;
import keso.compiler.imcode.*;
import java.util.*;

public final class ServiceManager {
    public static final ServiceManager instance = new ServiceManager();

    private Hashtable networks;
    private Hashtable services;
    private Vector imports;

    private int methodIDWidth;
    private int importIDWidth;
    private int serviceIDWidth;
    private int networkIDWidth;

    private final static int INVALID_ID = -1;

    private ServiceManager() {
        networks = new Hashtable();
        services = new Hashtable();
        imports = new Vector();
    }
    
    
    public void addNetwork(NetworkDefinition def) {
        def.identifier = networks.size();
        networks.put(def.ident, def);
    }


    public void addServices(Vector srv) {
        if (srv != null) {
            for (int i = 0; i < srv.size(); ++i) {
                addService((ServiceDefinition) srv.elementAt(i));
            }
        }
    }
    

    public void addService(ServiceDefinition def) {
        def.identifier = services.size();
        services.put(def.ident, def);
    }


    public void addImports(Vector imp) {
        if (imp != null) {
            for (int i = 0; i < imp.size(); ++i) {
                addImport((ImportDefinition) imp.elementAt(i));
            }
        }
    }

    
    public void addImport(ImportDefinition def) {
        imports.add(def);
    }

    
    public NetworkDefinition lookupNetwork(String networkName) {
        return (NetworkDefinition) networks.get(networkName);
    }


    public ServiceDefinition lookupService(String serviceName) {
        return (ServiceDefinition) services.get(serviceName);
    }

    
    public Vector getServicesOnCurrentSystem() {
        Vector result = new Vector();

        for (Enumeration srvs = services.elements(); srvs.hasMoreElements();) { 
            ServiceDefinition srv = (ServiceDefinition) srvs.nextElement(); 
            
            if (srv.getSystem() == BuilderOptions.getOpts().getSysDef()) {
                result.add(srv);
            }
        }

        return result;
    }


    public Vector getImportsOnCurrentSystem() {
        Vector result = new Vector();
        
        for (int i = 0; i < imports.size(); ++i) {
            ImportDefinition imp = (ImportDefinition) imports.elementAt(i);
            
            if (imp.getSystem() == BuilderOptions.getOpts().getSysDef()) {
                result.add(imp);
            }
        }

        return result;
    }

    
    private int getNetworkID(NetworkDefinition netDef) {
        return netDef.identifier;
    }


    private int getServiceID(ServiceDefinition srvDef) {
       int i = 0;
        
       for (Enumeration srvs = services.elements(); srvs.hasMoreElements();) { 
            ServiceDefinition srv = (ServiceDefinition) srvs.nextElement(); 
             
            if (srv == srvDef) {
                return i;
            }
        }

        return INVALID_ID;
    }

    
    private int getImportID(ImportDefinition impDef) {
        
        for (int i = 0; i < impDef.getService().getImports().size(); ++i) {
            if (impDef == (ImportDefinition) impDef.getService().getImports().elementAt(i)) {
                return i;
            }
        }

        return INVALID_ID;    
    }
    

    public int getMethodID(ServiceDefinition srvDef, String nameAndType) {
        
        for (int i = 0; i < srvDef.getMethods().length; ++i) {
            if (srvDef.getMethods()[i].getMethodNameAndType().equals(nameAndType)) {
                return i + 1;
            }
        }

        return INVALID_ID;
    }

    
    public String emitGetMethodID(String variable) {
        return "(" + variable + " & 0x" + Integer.toHexString(getOnes(methodIDWidth)) + ")";
    }

    
    public String emitGetReturnID(String variable) {
        return "(" + variable + " | 0x" + Integer.toHexString((1 << (methodIDWidth + importIDWidth))) + ")";
    }
    
    
    /**
     *  IDs are generate from the following scheme:
     *
     *  NETWORK_ID | SERVICE_ID | DIRECTION_BIT (0 = call, 1 = return) | IMPORT_ID | METHOD_ID
     *
     */
    private int getID(NetworkDefinition net, ServiceDefinition srv, boolean call, ImportDefinition imp, String methodNameAndType) {
        int result = getNetworkID(net);
        
        result <<= serviceIDWidth;
        result |= getServiceID(srv);
        
        result <<= 1;   // direction bit 
        if (!call) {
            result |= 1;
        }
        
        result <<= importIDWidth;
        if (imp != null) {
            result |= getImportID(imp);
        }
        
        result <<= methodIDWidth;
        if (methodNameAndType != null) {
            result |= getMethodID(srv, methodNameAndType);
        }
        
        return result;
    }

    
    private int getOnes(int count) {
        int ret = 0;

        while  (0 < count--) {
            ret <<= 1;
            ret |= 1;
        }

        return ret;
    }
    
 
   
    /**
     * Get the id for calling a service method from an import
     */
    public int getCallIdentifier(ImportDefinition imp, String methodNameAndType) {
        return getID(imp.getNetwork(), imp.getService(), true, imp, methodNameAndType);
    }

    
    /**
     * Get the id of the return packet when calling a service method from an import
     */
    public int getReturnIdentifier(ImportDefinition imp, String methodNameAndType) {
        return getID(imp.getNetwork(), imp.getService(), false, imp, methodNameAndType);
    } 


    /**
     * Get the ID of a service. This is NETWORK_ID | SERVICE_ID | DIRECTION_BIT = 0.
     * Any packet that matches serviceID == packetID & serviceMask will be handled by the service.
     */
    public int getServiceIdentifier(ServiceDefinition srvDef, NetworkDefinition netDef) {
        return getID(netDef, srvDef, true, null, null);
    }
    
    
    /**
     * Get the mask for the service ID. This will match against the NETWORK_ID | SERVICE_ID | DIRECTION_BIT.
     */
    public int getServiceMask() {
        return getOnes(serviceIDWidth + networkIDWidth + 1) << (importIDWidth + methodIDWidth);
    }

    
     /**
     * Get the mask for the service ID. This will match against the IMPORT_ID | METHOD_ID.
     */
    public int getServiceBufferMask() {
        return getOnes(importIDWidth + methodIDWidth);
    }


    /**
     * Get the maximum number of buffers required on service side. 
     */
    public int getServiceBufferCount() {
        return (int) Math.ceil(Math.pow(2, importIDWidth + methodIDWidth));
    }


    /**
     * Get the ID of an import. This is NETWORK_ID | SERVICE_ID | DIRECTION_BIT = 1 | IMPORT_ID.
     * Any packet that matches importID == packetID & importMask will be accepted.
     */
    public int getImportIdentifier(ImportDefinition impDef) {
        return getID(impDef.getNetwork(), impDef.getService(), false, impDef, null);
    }

    
    /**
     * Get the mask for the import ID. This will match against the NETWORK_ID | SERVICE_ID | DIRECTION_BIT | IMPORT_ID.
     */
    public int getImportMask() {
        return getOnes(serviceIDWidth + networkIDWidth + 1 + importIDWidth) << methodIDWidth;
    }
    

    /**
     * Get the total number of bits used for service ID's
     */
    public int getIDWidth() {
        return 1 + methodIDWidth + importIDWidth + serviceIDWidth + networkIDWidth;
    }
    
    
    public void buildDependencyTree() throws CompileException {
        
        // assign networks
        for (Enumeration srvs = services.elements(); srvs.hasMoreElements();) { 
            ServiceDefinition srv = (ServiceDefinition) srvs.nextElement(); 
            String[] access = srv.getAccess();
            
            if (access != null) {
                for (int i = 0; i < access.length; ++i) {
                    
                    NetworkDefinition net = lookupNetwork(access[i]);
                    if (net != null) {
                        srv.addNetwork(net);
                    } else {
                        throw new CompileException("The network " + access[i] + " specified for service " + srv + " was not found.");
                    }
                }
            }

        }            
        
        // assign imports
        for (int i = 0; i < imports.size(); ++i) {
            
            ImportDefinition imp = (ImportDefinition) imports.elementAt(i);
            ServiceDefinition srv = imp.getService();

            if (srv != null) {
                // check if service is local or remote
                if (srv.getSystem() != imp.getSystem()) {
                    // check network
                    if (!srv.hasNetworkAccess()) {
                        throw new CompileException("No route to service " + srv + ". The service has no network access.");
                    } else if (!imp.hasNetworkAccess()) {
                        throw new CompileException("No route to service " + srv + ". No network access specified in import " + imp + ".");
                    } else if (!srv.getNetworks().contains(imp.getNetwork())) {
                        // no routing at the moment
                        throw new CompileException("No route to service " + srv + ". The service has no access to the network " + imp.getNetwork() + " specified in import " + imp + ".");
                    }
                }
                
                srv.addImport(imp);
            } else {
                throw new CompileException("The service " + imp.getServiceName() + " specified for import " + imp + " was not found.");
            }
        }

        // calc ID width
        networkIDWidth = getBitsForCount(networks.size());
        serviceIDWidth = getBitsForCount(services.size());
    }

    
    public void computeServiceMethods(ClassStore repository) throws CompileException {
        
        int maxMethodsPerService = 0;
        int maxImportsPerService = 0;
       
        // check classes
        for (Enumeration nets = networks.elements(); nets.hasMoreElements();) {
            NetworkDefinition net = (NetworkDefinition) nets.nextElement();
            net.checkClasses(repository);
        }

        for (int i = 0; i < imports.size(); ++i) {
            ImportDefinition imp = (ImportDefinition) imports.elementAt(i);
            imp.checkClasses(repository);
        }
        
        // assign methods 
        for (Enumeration srvs = services.elements(); srvs.hasMoreElements();) { 
            ServiceDefinition srv = (ServiceDefinition) srvs.nextElement(); 
            srv.checkClasses(repository);
            srv.lookupMethods(repository);
                        
            maxMethodsPerService = Math.max(srv.getMethods().length, maxMethodsPerService);
            maxImportsPerService = Math.max(srv.getImports().size(), maxImportsPerService);
        }

        methodIDWidth = getBitsForCount(maxMethodsPerService + 1);
        importIDWidth = getBitsForCount(maxImportsPerService);        
        
        BuilderOptions.getOpts().verbose("Service IDs are " + getIDWidth() + " bits wide.");
        BuilderOptions.getOpts().verbose("Using " + networkIDWidth + " bits for network ID.");
        BuilderOptions.getOpts().verbose("Using " + serviceIDWidth + " bits for service ID.");
        BuilderOptions.getOpts().verbose("Using " + importIDWidth + " bits for import ID.");
        BuilderOptions.getOpts().verbose("Using " + methodIDWidth + " bits for method ID.");
    }

    
    private int getBitsForCount(int count) {
        if (count <= 1) {
            return 0;
        }
        return (int) Math.ceil(Math.log(count) / Math.log(2));
    }



}
