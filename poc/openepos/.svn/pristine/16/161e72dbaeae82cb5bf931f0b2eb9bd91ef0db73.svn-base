package keso.communication.authserver;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

public class KesoAuthenticationServer extends UnicastRemoteObject implements IKesoAuthenticationServer {
	
	private static String name;
	private static int port = Registry.REGISTRY_PORT;
	public static String accountfile = ".kesoauthetication";
	private static KesoAuthenticationServer instance;
	
	
	protected KesoAuthenticationServer() throws RemoteException {
		super();
	}
	
	private static void parseArguments(String [] args) {
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-name")) {
				name = args[++i];
				continue;
			}
			
			if (args[i].equals("-port")) {
				try {
					port = Integer.parseInt(args[i + 1]);
				} catch(Exception e) {
					
				}
				i++;
				continue;
			}
			
			if (args[i].equals("-f")) {
				accountfile = args[++i];
				continue;
			}
		}
		
		if (name == null) {
			System.err.println("Server needs a name!");
			System.exit(-1);
		}
	}

	public static void main(String[] args) {
		Registry registry = null;
		try {
			parseArguments(args);
			
			Properties properties = new Properties();
			try {
				properties.load(new FileInputStream(accountfile));
			} catch(Exception e) {
				properties.store(new FileOutputStream(accountfile), "");
			}
			
			
			try {
				registry = LocateRegistry.getRegistry(port);
				registry.list();
			} catch(Exception e) {
				registry = null;
			}
			if (registry == null) {
				registry = LocateRegistry.createRegistry(port);
			}
			
			instance = new KesoAuthenticationServer();
			registry.rebind(name, instance);
			
			KesoAuthenticationMenu.menu();
			
			registry.unbind(name);
			System.exit(0);	
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public boolean authenticateClient(String clientname, String clientpwd) throws RemoteException {
		return this.authenticate("client", clientname, clientpwd);
	}

	public int getPriorityOfClient(String clientname) throws RemoteException {
		Properties properties = new Properties();
		int priority = Integer.MAX_VALUE;
	
		try {
			properties.load(new FileInputStream(accountfile));
			String prt = properties.getProperty("client.priority." + clientname, Integer.toString(Integer.MAX_VALUE));		
			priority = Integer.parseInt(prt);
		} catch(Exception e) {
			
		}
		return priority;
	}
	
	private boolean authenticate(String identifier, String username, String password) {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(accountfile));
			String pwd = properties.getProperty(identifier + ".name." + username);
			if (pwd != null) {
				
				
				if (pwd.equals(password)) {
					return true;
				}
			}	
		} catch (Exception e) {
			
		}
		return false;
	}

}
