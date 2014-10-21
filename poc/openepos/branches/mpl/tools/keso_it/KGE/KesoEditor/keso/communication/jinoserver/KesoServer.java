package keso.communication.jinoserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import keso.communication.authserver.IKesoAuthenticationServer;
import keso.communication.client.IKesoClient;
import keso.communication.common.IRemoteFile;
import keso.communication.common.RemoteFile;
import keso.communication.common.RemoteFileTransfer;

import zip.Zipper;


public class KesoServer extends UnicastRemoteObject implements IKesoServer, Runnable {
	private static String name;
	private static int port = Registry.REGISTRY_PORT;
	
	private static String authenticationserveruri;
	private static IKesoAuthenticationServer authenticationserver;
	
	private static String workbench;
	private static String pathtojino;
	
	private static String blacklistfile;
	private static String wishlistfile;
	
	private static Hashtable blacklist = new Hashtable();
	private static Hashtable wishlist = new Hashtable();
	
	private static Vector clients = new Vector();
	
	private static KesoServer instance;
	
	private static BufferedReader stderr;
	private static BufferedReader stdout;
	
	private static Thread stdoutthread;
	private static Thread stderrthread;
	
	private static boolean interrupted = false;
	
	private static boolean allowallfiles = false;
	
	public static String [] envs;
	
	private Object mutex = new Object();
	
	protected KesoServer() throws RemoteException {
		// TODO Auto-generated constructor stub
	}
	
	synchronized public int size() throws  RemoteException {
		cleanClientList();
		return clients.size();
	}

	private static void readList(String filename, Hashtable container) {
		try {
			FileInputStream fileinput = new FileInputStream(filename);
			BufferedReader reader = new BufferedReader(new InputStreamReader(fileinput));
			String line;
			while ((line = reader.readLine()) != null) {
				container.put(line, line);
			}
			reader.close();
			fileinput.close();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void parseArguments(String [] args) {
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-jino")) {
				pathtojino = args[++i];
				continue;
			}
			
			if (args[i].equals("-workbench")) {
				workbench = args[++i];
				continue;
			}
			
			if (args[i].equals("-blacklist")) {
				blacklistfile = args[++i];
				readList(blacklistfile, blacklist);
				continue;
			}
			
			if (args[i].equals("-wishlist")) {
				wishlistfile = args[++i];
				readList(wishlistfile, wishlist);
				continue;
			}
			
			if (args[i].equals("-aserver")) {
				authenticationserveruri = args[++i];
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
			
			if (args[i].equals("-name")) {
				name = args[++i];
				continue;
			}
			
			if (args[i].equals("-allfiles")) {
				allowallfiles = true;
				continue;
			}
		}
		
		boolean error = false;
		
		if (name == null) {
			error = true;
			System.err.println("Server must have a name!");
		}
		if (workbench == null) {
			error = true;
			System.err.println("Server must have a workbench!");
		}
		if (pathtojino == null) {
			error = true;
			System.err.println("Need path to jino!");
		}
		if (authenticationserveruri == null) {
			error = true;
			System.err.println("Server needs URI of authentication server!");
		}
		
		if (error) {
			System.exit(-1);
		}
		
	}
	
	public static void main(String[] args) {
		Registry registry = null;
		try {
			Vector envcontainer = new Vector();
			KesoServer.parseArguments(args);
			
			for (Enumeration keys = System.getProperties().keys(); keys.hasMoreElements(); ) {
				String key = (String) keys.nextElement();
				if (key.startsWith("keso")) {
					String env = key.substring(("keso").length());
					env += "=\"" + System.getProperty(env, "") + "\"";
				}
			}
			
			envs = (String []) envcontainer.toArray(new String[0]);
			
			try {
				registry = LocateRegistry.getRegistry(port);
				registry.list();
			} catch(Exception e) {
				registry = null;
			}
			if (registry == null) {
				registry = LocateRegistry.createRegistry(port);
			}
			
			instance = new KesoServer();
			registry.rebind(name, instance);
			
			try {
				authenticationserver = (IKesoAuthenticationServer) Naming.lookup(authenticationserveruri);
				if (authenticationserver != null) {
					(new Thread(instance)).start();
				}
				
				String input;
				BufferedReader inputkeyboard = new BufferedReader(new InputStreamReader(System.in));
				System.out.println("Menu:");
				System.out.println("X. Exit");
				System.out.println("");
				System.out.print("Input: ");
				while ((input = inputkeyboard.readLine()) != null) {
					if (input.compareToIgnoreCase("x") == 0) {
						interrupted = true;
						break;
					}
					System.out.println("Menu:");
					System.out.println("X. Exit");
					System.out.println("");
					System.out.print("Input: ");
				}
				
				inputkeyboard.close();
			} catch(Exception e) {
				
			}
			
			registry.unbind(name);
		} catch(Exception e) {
			e.printStackTrace();
		}
		System.exit(0);	
	}

	synchronized public String getName() throws RemoteException{
		return name;
	}

	synchronized public void ping() throws RemoteException{
		
	}

	synchronized public boolean register(IKesoClient client) throws RemoteException{
		cleanClientList();
		try {
			if (authenticationserver.authenticateClient(client.getName(), client.getPassword())) {
				int clientpriority = authenticationserver.getPriorityOfClient(client.getName());
				int position = clients.size();
				for (int i = 0; i < clients.size(); i++) {
					Vector entry = (Vector) clients.get(i);
					Integer priority = (Integer) entry.get(1);
					if (priority.intValue() > clientpriority) {
						position = i;
						break;
					}
				}
				
				Vector newentry = new Vector();
				newentry.add(client);
				newentry.add(new Integer(clientpriority));
				clients.add(position, newentry);
				client.setPosition(position + 1);
				sendPositions();
				return true;
			}
		} catch(Exception e) {
		}
		return false;
	}
	
	private void cleanClientList() {
		Vector deleteclients = new Vector();
		for (Iterator i = clients.iterator(); i.hasNext(); ) {
			Vector entry = (Vector) i.next();
			IKesoClient client = (IKesoClient) entry.get(0);
			try {
				client.ping();
			} catch(Exception e) {
				deleteclients.add(entry);
			}
		}	
		clients.removeAll(deleteclients);
	}
	
	synchronized private void sendPositions() {
		cleanClientList();
		int position = 1;
		for (Iterator i = clients.iterator(); i.hasNext(); ) {
			Vector entry = (Vector) i.next();
			IKesoClient client = (IKesoClient) entry.get(0);
			try {
				client.setPosition(position);
				position++;
			} catch(Exception e) {
				
			}
		}
	}

	
	synchronized private IKesoClient pop(){
		try {
			if (this.size() > 0) {
				Vector entry = (Vector) clients.remove(0);
				IKesoClient client = (IKesoClient) entry.remove(0);
				return client;
			}
		} catch(Exception e) {
			
		}
		return null;
	}
	
	
	private void setupOutputstreams(Process process) {
		stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
		stderr = new BufferedReader(new InputStreamReader(process.getErrorStream()));
		
		stdoutthread = new Thread() {
			public void run() {
				try {
					FileOutputStream fileoutput = new FileOutputStream(workbench + File.separator + "tmp" + File.separator + "stdout.txt");
					PrintStream pout = new PrintStream(fileoutput);
					String text;
					try {
						while ((text = stdout.readLine()) != null) {
							pout.println(text);
						}
					}catch(Exception e) {
							
					}
					pout.flush();
					pout.close();
					fileoutput.close();
					stdoutthread = null;
				} catch(Exception e) {
				
				}
			}
		};
		
		stderrthread = new Thread() {
			public void run() {
				try {
					FileOutputStream fileoutput = new FileOutputStream(workbench + File.separator + "tmp" + File.separator + "stderr.txt");
					PrintStream pout = new PrintStream(fileoutput);
					String text;
					try {
						while ((text = stderr.readLine()) != null) {
							pout.println(text);
						}
					}catch(Exception e) {
							
					}
					pout.flush();
					pout.close();
					fileoutput.close();
					stderrthread = null;
				} catch(Exception e) {
				
				}
			}
		};
		
		stdoutthread.start();
		stderrthread.start();
	}
	
	public void run() {
		
		Thread informationthread = new Thread() {
			public void run() {
				while(!interrupted) {
					try {
						sendPositions();
						Thread.sleep(5000);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		};
		
		informationthread.start();
		
		IKesoClient client;
		while (!interrupted) {
			client = pop();
			if (client == null) {
				synchronized (mutex) {
					try {
						mutex.wait(2000);
					} catch(Exception e) {
						
					}
				}
			} else {
				try {
					System.out.println("process "+client.getName());

					int exitvalue = -1;
					
					File tmpfile = new File(workbench + File.separator + "classes");
					if (tmpfile.exists()) {
						this.deleteDir(tmpfile);
					}
					
					tmpfile = new File(workbench + File.separator + "tmp");
					if (tmpfile.exists()) {
						this.deleteDir(tmpfile);
					}
					tmpfile.mkdirs();
					
					tmpfile = new File(workbench + File.separator + "rc");
					if (tmpfile.exists()) {
						this.deleteDir(tmpfile);
					}
					tmpfile.mkdirs();
					
					IRemoteFile file = client.getData();
					RemoteFileTransfer.transfer(workbench + File.separator + "rc" + File.separator + "tmp.zip", file);
					
					Zipper zipper = null;
					
					try {
						
						zipper = new Zipper();
						zipper.unzip(new String [] {
							"unzip",
							workbench + File.separator + "rc" + File.separator + "tmp.zip",
							workbench + File.separator + "rc"
						});
						
						
						zipper.unzip(new String [] {
							"unzip",
							workbench + File.separator + "rc" + File.separator + "classes.zip",
							workbench
						});
						
					} catch(Exception e) {
						continue;
					}

					Vector parameters = client.getParameters();
					Vector start = new Vector();
					start.add("java");
					start.add("-classpath"); start.add(pathtojino);
					start.add("keso.compiler.Main");
					start.add("-bootmodules"); start.add("core");
					start.add("-output"); start.add(workbench + File.separator + "tmp");
					start.add("-def"); start.add(workbench + File.separator + "rc" + File.separator + "config.kcl");
					start.add("-translate");
					start.add("-kesosrcpath"); start.add(workbench);
					start.addAll(parameters);
					
					try {
						final Process process = Runtime.getRuntime().exec((String []) start.toArray(new String[0]), envs, new File(workbench)); 
						setupOutputstreams(process);
						
						while (stdoutthread != null || stderrthread != null) {
							try {
								Thread.sleep(1000);
							} catch(Exception e) {
								
							}
						}
						
						exitvalue = process.exitValue();
						
						try {
							process.destroy();
						} catch(Exception e) {
							
						}
						
					} catch(Exception e) {
						System.err.println("Exception "+e);
					}
				
					if (client.wantsAllFiles() && allowallfiles) {
						filterBlacklist(new File(workbench + File.separator + "tmp"));
					} else {
						filterWishedList(new File(workbench + File.separator + "tmp"));
					}
					
					boolean waszipped = true;
					try {
						zipper.zip(new String [] {
								"zip",
								workbench + File.separator + "tmp" + File.separator + "result.zip",
								workbench + File.separator + "tmp"
						});
					} catch(Throwable e) {
						waszipped = false;
						client.send("No files to send!");
					}
					
					if (waszipped) {
						try {
							client.setResult(new RemoteFile(workbench + File.separator + "tmp" + File.separator + "result.zip"));	
							client.finish(true);
						} catch(Exception e) {
							client.send("Could not receive files!");
						}
					}	else {
						try{
							client.finish(false);
						} catch(Exception e) {
							
						}	
					}
				
				} catch(Exception e) {
					try {
						client.finish(false);
					} catch(Exception ex) {
						
					}	
				}
			}
		}
	}
	
	private void deleteDir(File fileclasses) {
		if (fileclasses.isDirectory()) {
			File [] files = fileclasses.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].getName().equals(".") ||
						files[i].getName().equals("..")) {
					continue;
				} else {
					this.deleteDir(files[i]);
				}
			}
			//System.err.println(fileclasses.getName());
			fileclasses.delete();
		} else {
			//System.err.println(fileclasses.getName());
			fileclasses.delete();
		}
	}
	
	

	private Vector getAllFiles(File file) {
		Vector all = new Vector();
		if (file.equals(".") || file.equals("..")) {
			
		} else {
			if (file.isDirectory()) {
				File [] list = file.listFiles();
				for (int i = 0; i < list.length; i++) {
					all.addAll(getAllFiles(list[i]));
				}
			} else {
				try {
					all.add(file.getCanonicalFile());
				} catch(Exception e) {
					
				}
			}
		}
		return all;
	}
	
	private void filterWishedList(File file) {
		Vector all = getAllFiles(file);
		for (Iterator i = all.iterator(); i.hasNext(); ) {
			File f = (File) i.next();
			if (wishlist.get(f.getName()) == null) {
				f.delete();
			}
		}
	}

	private void filterBlacklist(File file) {
		Vector all = getAllFiles(file);
		for (Iterator i = all.iterator(); i.hasNext(); ) {
			File f = (File) i.next();
			if (blacklist.get(f.getName()) != null) {
				f.delete();
			}
		}
	}

	synchronized public void unregister(IKesoClient client) throws RemoteException {
		for (Iterator i = this.clients.iterator(); i.hasNext(); ) {
			Vector entry = (Vector) i.next();
			if (entry.get(0) == client) {
				this.clients.remove(entry);
				break;
			}
		}
	}

}
