package keso.editor.gui.compilation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.Vector;

import keso.communication.client.IKesoClient;
import keso.communication.common.IRemoteFile;
import keso.communication.common.KesoMD5;
import keso.communication.common.RemoteFile;
import keso.communication.common.RemoteFileTransfer;
import keso.communication.jinoserver.IKesoServer;
import keso.editor.gui.KesoGuiProperties;
import keso.editor.gui.core.KesoMainWindow;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

import zip.Zipper;



public class KesoCompilation extends UnicastRemoteObject implements IKesoClient, ShellListener, SelectionListener {
	
	private static final String TEXT_POSITION = "Position in the queue";
	private static final String TEXT_CONNECTED = "Connected to";
	private static final String TEXT_TIMEOUT = "Timeout";
	private static final String TEXT_TRY_TO_CONNECT = "Try to connect to";
	
	private Registry registry;
	
	
	private static final int MAX_TIMEOUT = 30;
	int timeout = MAX_TIMEOUT;
	
	Shell shell;
	Shell parentshell;
	KesoMainWindow window;
	int steps;
	Display display;
	
	boolean interrupted = false;
	
	
	ProgressBar progressbar;
	StyledText console;
	Button cancel;
	private String rcfilename;
	
	Thread stdoutthread;
	Thread stderrthread;
	
	Vector parameters;
	private Vector servers;
	private boolean finished;
	private Thread timeoutthread;
	private Label connectionlbl;
	private Label positionlbl;
	private Label timeoutlbl;
	private Object mutex = new Object();
	private int position = -1;
	private String serverurl = null;
	private boolean successful;
	
	Process process;
	private boolean trytoconnect;
	
	public KesoCompilation() throws RemoteException {
		
	}
	
	public  KesoCompilation(Shell parentshell, KesoMainWindow window, String filename, Vector parameters, Vector servers, int steps) throws RemoteException {	
		this.parentshell = parentshell;
		this.window = window;
		this.steps = steps;
		this.rcfilename = filename;
		this.parameters = parameters;
		this.servers = servers;
		
		this.shell = new Shell(parentshell, SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);
		this.shell.addShellListener(this);
		this.shell.setText("Compilation");
		this.display = this.shell.getDisplay();
		
		
		GridLayout grid = new GridLayout();
		grid.numColumns = 1;
		
		shell.setLayout(grid);
		
		CTabFolder tabfolder = new CTabFolder(shell, SWT.BORDER);
		tabfolder.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		CTabItem tabitem = new CTabItem(tabfolder, SWT.NONE);
		tabitem.setText("Information");
		
		
		Composite comp = new Composite(tabfolder, SWT.NONE);
		comp.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		grid = new GridLayout();
		grid.numColumns = 1;
		
		comp.setLayout(grid);
		
		tabitem.setControl(comp);
		
		

		this.connectionlbl = new Label(comp, SWT.NONE);
		this.connectionlbl.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		this.connectionlbl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		this.connectionlbl.setText(TEXT_CONNECTED + ": localhost");
		
		this.positionlbl = new Label(comp, SWT.NONE);
		this.positionlbl.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		this.positionlbl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		this.positionlbl.setText(TEXT_POSITION + ": ---");
		
		this.timeoutlbl = new Label(comp, SWT.NONE);
		this.timeoutlbl.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		this.timeoutlbl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		this.timeoutlbl.setText(TEXT_TIMEOUT + ": ---");

		
		Label tmplbl = new Label(comp, SWT.NONE);
		tmplbl.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		tmplbl.setText("Progress:");
		tmplbl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		
		this.progressbar = new ProgressBar(comp, SWT.NONE);
		this.progressbar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		//this.progressbar.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		
		
		
		tabfolder = new CTabFolder(shell, SWT.BORDER);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 200;
		gd.widthHint = 400;
		
		tabfolder.setLayoutData(gd);
		
		tabitem = new CTabItem(tabfolder, SWT.NONE);
		tabitem.setText("Output");
		
		
		comp = new Composite(tabfolder, SWT.NONE);
		comp.setLayout(new FillLayout());
		comp.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		
		tabitem.setControl(comp);
			
		tabfolder.setSelection(0);
		
		
		this.console = new StyledText(comp, SWT.WRAP | SWT.V_SCROLL);
		this.console.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		
		
		comp = new Composite(this.shell, SWT.NONE);
		comp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		grid = new GridLayout();
		grid.numColumns = 3;
		grid.marginBottom = 0;
		grid.marginHeight = 0;
		grid.marginLeft = 0;
		grid.marginRight = 0;
		grid.marginTop = 0;
		grid.marginWidth = 0;
		comp.setLayout(grid);
		
		Composite fakecomp = new Composite(comp, SWT.NONE);
		gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 1;
		fakecomp.setLayoutData(gd);
		
		this.cancel = new Button(comp, SWT.PUSH);
		this.cancel.setLayoutData(new GridData());
		this.cancel.setText("Cancel");
		this.cancel.addSelectionListener(this);
		
		this.shell.pack();
		
		Rectangle clientarea = this.parentshell.getBounds();
		int mx = (clientarea.x + clientarea.x + clientarea.width) / 2 - (this.shell.getSize().x / 2);
		int my = (clientarea.y + clientarea.y + clientarea.height) / 2 - (this.shell.getSize().y / 2);
		
		this.shell.setLocation(mx, my);
		
		this.window.getConsole().setText("");
		
		if (this.steps == 1) {
			this.setProgressInterval(0, 4);
		} else {
			this.setProgressInterval(0, 12);
		}
	}
	
	public void open() {
		this.shell.open();
		
		(new Thread() {
			public void run() {
				KesoCompilation.this.compile();
			}
		}).start();
		
		
		while(!this.shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
	}
	
	private void compile() {
		int exitvalue = -1;
		try {
			exitvalue = this.first();
			
			if (!interrupted && exitvalue == 0) {
				exitvalue = this.second();
			}
			
			if (steps == 2 && !interrupted && exitvalue == 0) {
				exitvalue = this.third();
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		/*
		if (exitvalue == 0) {
			writeHint("Compile process was finished successful");
		} else {
			writeHint("Compile process failed");
		}
		*/
		if (interrupted) {
			writeHint("Compile process was interrupted");
		} else {
			writeHint("End");
		}
		
		
		display.syncExec(new Runnable() {
			public void run() {
				if (!cancel.isDisposed()) {
					cancel.setText("Close");
				}
			}
		});
	}
	
	private void interrupt() {
		interrupted = true;
	}

	public void shellActivated(ShellEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void shellClosed(ShellEvent e) {
		interrupt();
		shell.dispose();
	}

	public void shellDeactivated(ShellEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void shellDeiconified(ShellEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void shellIconified(ShellEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void write(String text, Color color) {
		StyledText mainconsole = this.window.getConsole();
		if (!this.console.isDisposed()) {
			this.console.append(text + "\n");
			int lines = this.console.getLineCount();
			this.console.setLineBackground(lines - 2, 1, color);
			mainconsole.append(text + "\n");
			lines = mainconsole.getLineCount();
			mainconsole.setLineBackground(lines - 2, 1, color);
			mainconsole.setTopIndex(mainconsole.getLineCount());
			mainconsole.redraw();
			this.console.setTopIndex(this.console.getLineCount());
			this.console.redraw();
		}
	}
	
	public void writeStdout(final String text) {
		display.syncExec(new Runnable() {
			public void run() {
				write(text, null);
			}
		});
	}
	
	public void writeHint(final String text) {
		display.syncExec(new Runnable() {
			public void run() {
				write(text, Display.getCurrent().getSystemColor(SWT.COLOR_GRAY));
			}
		});
	}
	
	public void writeStderr(final String text) {
		display.syncExec(new Runnable() {
			public void run() {
				write(text, Display.getCurrent().getSystemColor(SWT.COLOR_RED));
			}
		});
	}
	
	
	private void flush(final Process process) {
		this.stderrthread = new Thread() {
			public void run() {
				try {
					BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
					String text;
					while ((text = reader.readLine()) != null) {
						writeStderr(text);
					}
					reader.close();
				} catch(Exception e) {
					
				}
				stderrthread = null;
			}
		};
		
		this.stdoutthread = new Thread() {
			public void run() {
				try {
					BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
					String text;
					while ((text = reader.readLine()) != null) {
						writeStdout(text);
					}
					reader.close();
				} catch(Exception e) {
					
				}
				stdoutthread = null;
			}
		};
		
		this.stderrthread.start();
		this.stdoutthread.start();
		
		while(this.stderrthread != null || this.stdoutthread != null) {
			try {
				Thread.sleep(1000);
			} catch(Exception e) {
				
			}
		}
	}
	
	private int first() throws IOException {
		int exitvalue = -1;
		String [] start = new String[] {"java", "keso.compiler.config.GenerateKesoClasses", 
			KesoGuiProperties.getInstance().getProperty("workbench.directory"), 
			this.rcfilename,
			"templates", 
			"libs", 
			"buildstamps"
		};
		
		this.increaseProgress();
		
		process = Runtime.getRuntime().exec(start, new String[0], new File(KesoGuiProperties.getInstance().getProperty("jino.path")));
		this.flush(process);
		
		this.increaseProgress();
		
		try {
			exitvalue = process.exitValue();
			process.destroy();
		} catch(Exception e) {
			
		}
		
		return exitvalue;
	}
	
	private int second() throws IOException {
		int exitvalue = -1;
		
		Vector start = new Vector();
		start.add("java");
		start.add("-classpath"); 
		
		if (KesoGuiProperties.getInstance().getProperty("server.path.jdk") != null
				&& KesoGuiProperties.getInstance().getProperty("server.path.jdk").trim().length() > 0) {
				start.add(
						KesoGuiProperties.getInstance().getProperty("server.path.jdk") + File.separator + "lib" + File.separator + "tools.jar" + 
						File.pathSeparator + 
						KesoGuiProperties.getInstance().getProperty("server.path.jino")
				);
		} else {
			start.add(KesoGuiProperties.getInstance().getProperty("server.path.jino"));
		}
		
		start.add("keso.compiler.Main");
		start.add("-bootmodules"); start.add("core");
		start.add("-output"); start.add(KesoGuiProperties.getInstance().getProperty("workbench.directory") + File.separator + "tmp");
		start.add("-def"); start.add(this.rcfilename);
		start.add("-precompile");
		start.add("-X:no_automake");
		
		this.increaseProgress();
		
		process = Runtime.getRuntime().exec((String []) start.toArray(new String[0]), new String[0], new File(KesoGuiProperties.getInstance().getProperty("workbench.directory"))); 
		this.flush(process);
		
		this.increaseProgress();
		
		try {
			exitvalue = process.exitValue();
			process.destroy();
		} catch(Exception e) {
			
		}
		
		return exitvalue;
	}
	
	private boolean zipContent() {
		try {
			
		
			
			String workbench = KesoGuiProperties.getInstance().getProperty("workbench.directory");
		
			File file = new File(workbench + File.separator + "tmp" + File.separator + "source.zip");
			if (file.exists()) {
				try {
					file.delete();
				} catch(Exception e) {
					
				}
			}
			
			file = new File(workbench + File.separator + "tmp" + File.separator + "result.zip");
			if (file.exists()) {
				try {
					file.delete();
				} catch(Exception e) {
					
				}
			}
			
			Zipper zipper = new Zipper();
			String [] args = new String [] {
					"zip",
					workbench + File.separator + "tmp" + File.separator + "classes.zip",
					workbench + File.separator + "classes",
			};
			zipper.zip(args);
			
			args = new String [] {
					"zip",
					workbench + File.separator + "tmp" + File.separator + "source.zip",
					workbench + File.separator + "tmp" + File.separator + "classes.zip",
					workbench + File.separator + this.rcfilename
			};
			zipper.zip(args);
			
			(new File(workbench + File.separator + "tmp" + File.separator + "classes.zip")).delete();
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private void setProgressInterval(int min, int max) {
		this.progressbar.setMinimum(min);
		this.progressbar.setMaximum(max);
	}
	
	private void increaseProgress() {
		display.syncExec(new Runnable() {
			public void run() {
				progressbar.setSelection(progressbar.getSelection() + 1);
			}
		});
	}
	
	private int third() throws IOException {
		int exitvalue = -1;
		String workbench = KesoGuiProperties.getInstance().getProperty("workbench.directory");
		
		this.increaseProgress();
		
		if (!zipContent()) {
			return exitvalue;
		}
		
		this.increaseProgress();
		
		IKesoServer server = this.chooseServer();
		if (server != null) {
			this.updateDisplay();
			
			this.timeoutthread = new Thread() {
				public void run() {
					while (timeoutthread != null) {
						try {
							timeout--;
							updateDisplay();
							if (timeout == 0) {
								timeoutthread = null;
								KesoCompilation.this.interrupt();
								writeStderr("Time out! Connection closed!");
							}
							Thread.sleep(1000);
						} catch(Exception e) {
							
						}
					}
				}
			};
			
			this.timeoutthread.start();
			
			try {
				if (!server.register(this)) {
					interrupted = true;
					this.writeStderr("Wrong username or password!");
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
				
			this.increaseProgress();
			
			while (!interrupted && !finished) {
				synchronized (mutex) {
					try {
						mutex.wait(1000);
					} catch(Exception e) {
						
					}
				}
			}
		
			this.increaseProgress();
			
			try {
				UnicastRemoteObject.unexportObject(this, true);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			this.increaseProgress();
			
			this.timeoutthread = null;
			
			this.timeout = -1;
			this.position = -1;
			this.serverurl = null;
			this.updateDisplay();
			
			this.increaseProgress();
		
			if (finished) {
				try {
					Zipper zipper = new Zipper();
					zipper.unzip(new String [] {
						"unzip",
						workbench + File.separator + "tmp" + File.separator + "result.zip",
						workbench
					});
				} catch(Exception e) {
					
				}
				
				
				this.increaseProgress();
				
				
				try {
					FileInputStream fin = new FileInputStream(workbench + File.separator + "tmp" + File.separator + "stdout.txt");
					BufferedReader reader = new BufferedReader(new InputStreamReader(fin));
					String text;
					while ((text = reader.readLine()) != null) {
						writeStdout(text);
					}
					reader.close();
					fin.close();
					
					fin = new FileInputStream(workbench + File.separator + "tmp" + File.separator + "stderr.txt");
					reader = new BufferedReader(new InputStreamReader(fin));
					while ((text = reader.readLine()) != null) {
						writeStderr(text);
					}
					reader.close();
					fin.close();
				} catch(Exception e) {
					successful = false;
					e.printStackTrace();
				}
				
				if (successful) {
					
					this.increaseProgress();
					
					exitvalue = 0;
				}
			}
			
		} else {
			writeStderr("Can not connect to server!");
		}
		
		return exitvalue;
	}
	
	private IKesoServer chooseServer() {
		IKesoServer server = null;
		String serverurl = null;
		int minclients = Integer.MAX_VALUE;
		for (Iterator i = this.servers.iterator(); i.hasNext(); ) {
			String address = (String) i.next();
			
			this.serverurl = address;
			this.trytoconnect = true;
			this.updateDisplay();
			try {
				IKesoServer tmpserver = (IKesoServer) Naming.lookup(address);
				
				if (tmpserver == null) {
					serverurl = address;
					server = tmpserver;
					minclients = tmpserver.size();
				} else {
					int tmpmin = tmpserver.size();
					if (tmpmin < minclients) {
						serverurl = address;
						minclients = tmpmin;
						server = tmpserver;
					}
				}
			
			} catch(Exception e) {
				this.serverurl = null;
				e.printStackTrace();
			}	
			this.trytoconnect  = false;
			this.updateDisplay();
		}
		
		this.serverurl = serverurl;
		
		return server;
	}
	

	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void widgetSelected(SelectionEvent e) {
		interrupt();
		try {
			process.destroy();
		} catch(Exception ex) {
			
		}
		shell.dispose();
	}

	public void finish(boolean success) throws RemoteException {
		this.finished = true;
		this.successful = success;
		ping();
	}

	public IRemoteFile getData() throws RemoteException {
		ping();
		return new RemoteFile(KesoGuiProperties.getInstance().getProperty("workbench.directory") + File.separator + "tmp" + File.separator + "source.zip");
	}

	public String getName() throws RemoteException {
		ping();
		return KesoGuiProperties.getInstance().getProperty("server.username");
	}

	public Vector getParameters() throws RemoteException {
		ping();
		return this.parameters;
	}

	public String getPassword() throws RemoteException {
		ping();
		return KesoMD5.getMD5Hash(KesoGuiProperties.getInstance().getProperty("server.password"));
	}

	public void ping() throws RemoteException {
		this.timeout = MAX_TIMEOUT;
		this.updateDisplay();
	}
	
	public void updateDisplay() {
		display.asyncExec(new Runnable() {
			public void run() {
				if (!timeoutlbl.isDisposed()) {
					
					if (position == -1) {
						timeoutlbl.setText(TEXT_TIMEOUT + ": ---");
					} else {
						timeoutlbl.setText(TEXT_TIMEOUT + ": " + timeout + " sec.");
					}
				}
				if (!positionlbl.isDisposed()) {
					if (position == -1) {
						positionlbl.setText(TEXT_POSITION + ": ---");
					} else {
						positionlbl.setText(TEXT_POSITION + ": " + position);
					}
				}
				
				if (!connectionlbl.isDisposed()) {
					if (trytoconnect) {
						connectionlbl.setText(TEXT_TRY_TO_CONNECT + " " + serverurl);
					} else {
						if (serverurl == null) {
							connectionlbl.setText(TEXT_CONNECTED + " localhost");
						} else {
							connectionlbl.setText(TEXT_CONNECTED + " " + serverurl);
						}
					}
				}
			}
		});
	}

	public void send(String text) throws RemoteException {
		this.writeStdout(text);	
		ping();
	}

	public void setPosition(int position) throws RemoteException {
		this.position = position;
		ping();
	}

	public void setResult(IRemoteFile file) throws RemoteException {
		RemoteFileTransfer.transfer(KesoGuiProperties.getInstance().getProperty("workbench.directory") + File.separator + "tmp" + File.separator + "result.zip", file);
		ping();
	}

	public int size() throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean wantsAllFiles() throws RemoteException {
		ping();
		return KesoGuiProperties.getInstance().getProperty("server.getallfiles").equals("true");
	}
	
}
