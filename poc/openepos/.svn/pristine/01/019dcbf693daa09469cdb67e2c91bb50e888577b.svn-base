package keso.communication.authserver;



import java.rmi.Remote;
import java.rmi.RemoteException;


public interface IKesoAuthenticationServer extends Remote {
	public boolean authenticateClient(String clientname, String clientpwd) throws RemoteException;
	public int getPriorityOfClient(String clientname) throws RemoteException;
	
}
