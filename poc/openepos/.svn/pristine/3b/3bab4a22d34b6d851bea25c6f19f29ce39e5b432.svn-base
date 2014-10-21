package keso.communication.client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

import keso.communication.common.IRemoteFile;


public interface IKesoClient extends Remote {
	public IRemoteFile getData() throws RemoteException;
	public void setResult(IRemoteFile file) throws RemoteException;
	public void setPosition(int position) throws RemoteException;
	public void send(String text) throws RemoteException;
	public void finish(boolean success) throws RemoteException;
	public String getName() throws RemoteException;
	public String getPassword() throws RemoteException;
	public int size() throws RemoteException;
	public void ping() throws RemoteException;
	public Vector getParameters() throws RemoteException;
	public boolean wantsAllFiles() throws RemoteException;
}
