package keso.communication.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRemoteFile extends Remote {
	public void open() throws RemoteException;
	public byte [] nextBlock() throws RemoteException;
	public void close() throws RemoteException;
	public String getFileName() throws RemoteException;
}
