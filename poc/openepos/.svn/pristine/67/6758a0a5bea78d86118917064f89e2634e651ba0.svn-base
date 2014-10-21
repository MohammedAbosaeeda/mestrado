package keso.communication.common;

import java.io.File;
import java.io.FileInputStream;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class RemoteFile extends UnicastRemoteObject implements IRemoteFile {

	public static final int BLOCKSIZE = 4096;
	
	String path;
	FileInputStream in;
	
	public RemoteFile(String filename) throws RemoteException {
		try {
			this.open(new File(filename).getCanonicalPath());
		} catch(Exception e) {
			e.printStackTrace();
			throw new RemoteException();
		}
	}
	
	public RemoteFile(File file) throws RemoteException {
		try {
			this.open(file.getCanonicalPath());
		} catch(Exception e) {
			e.printStackTrace();
			throw new RemoteException();
		}
	}
	
	public void open(String path) {
		try {
			this.path = path;
			if (this.in != null) {
				this.close();
				this.open();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void close() throws RemoteException {
		try {
			if (this.in != null) {
				this.in.close();
			}
			this.in =  null;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public byte[] nextBlock() throws RemoteException {
		if (this.in !=  null) {
			try {
				byte [] data;
				if (this.in.available() != 0) {
					if (this.in.available() > BLOCKSIZE) {
						data = new byte[BLOCKSIZE];
					} else {
						data = new byte[this.in.available()];
					}
					this.in.read(data, 0, data.length);
					return data;
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public String getFileName() throws RemoteException  {
		try {
			return new File(this.path).getName();
		} catch(Exception e) {
			e.printStackTrace();
			throw new RemoteException();
		}
	}
	
	public void open() throws RemoteException {
		try {
			if (this.in != null) {
				this.close();
			}
			this.in = new FileInputStream(this.path);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
