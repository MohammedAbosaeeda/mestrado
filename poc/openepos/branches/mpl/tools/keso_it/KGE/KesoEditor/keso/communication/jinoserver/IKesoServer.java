package keso.communication.jinoserver;

import java.rmi.Remote;
import java.rmi.RemoteException;

import keso.communication.client.IKesoClient;


public interface IKesoServer extends Remote {
	public String getName() throws RemoteException;
	public void ping() throws RemoteException;
	public int size() throws RemoteException;
	public boolean register(IKesoClient client) throws RemoteException;
	public void unregister(IKesoClient client) throws RemoteException;
}
