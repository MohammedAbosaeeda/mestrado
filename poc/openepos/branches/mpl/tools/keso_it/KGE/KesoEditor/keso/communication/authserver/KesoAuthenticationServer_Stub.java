// Stub class generated by rmic, do not edit.
// Contents subject to change without notice.

package keso.communication.authserver;

public final class KesoAuthenticationServer_Stub
    extends java.rmi.server.RemoteStub
    implements keso.communication.authserver.IKesoAuthenticationServer, java.rmi.Remote
{
    private static final long serialVersionUID = 2;
    
    private static java.lang.reflect.Method $method_authenticateClient_0;
    private static java.lang.reflect.Method $method_getPriorityOfClient_1;
    
    static {
	try {
	    $method_authenticateClient_0 = keso.communication.authserver.IKesoAuthenticationServer.class.getMethod("authenticateClient", new java.lang.Class[] {java.lang.String.class, java.lang.String.class});
	    $method_getPriorityOfClient_1 = keso.communication.authserver.IKesoAuthenticationServer.class.getMethod("getPriorityOfClient", new java.lang.Class[] {java.lang.String.class});
	} catch (java.lang.NoSuchMethodException e) {
	    throw new java.lang.NoSuchMethodError(
		"stub class initialization failed");
	}
    }
    
    // constructors
    public KesoAuthenticationServer_Stub(java.rmi.server.RemoteRef ref) {
	super(ref);
    }
    
    // methods from remote interfaces
    
    // implementation of authenticateClient(String, String)
    public boolean authenticateClient(java.lang.String $param_String_1, java.lang.String $param_String_2)
	throws java.rmi.RemoteException
    {
	try {
	    Object $result = ref.invoke(this, $method_authenticateClient_0, new java.lang.Object[] {$param_String_1, $param_String_2}, -3298227146357323421L);
	    return ((java.lang.Boolean) $result).booleanValue();
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of getPriorityOfClient(String)
    public int getPriorityOfClient(java.lang.String $param_String_1)
	throws java.rmi.RemoteException
    {
	try {
	    Object $result = ref.invoke(this, $method_getPriorityOfClient_1, new java.lang.Object[] {$param_String_1}, -6485596330259047843L);
	    return ((java.lang.Integer) $result).intValue();
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
}