package keso.editor.gui.compilation;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URI;
import java.util.Vector;

import keso.editor.KGE;
import keso.editor.gui.KesoGuiProperties;
import keso.editor.gui.core.KesoMainWindow;

import org.eclipse.swt.SWT;
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
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

import zip.Zipper;

public class KesoCompilationDialog implements   SelectionListener, ShellListener {

	boolean interrupted;
	String filename;
	KesoMainWindow window;
	
	BufferedReader stdout;
	BufferedReader err;
	
	ProgressBar progressbar;
	
	Display display;
	private Shell parentshell;
	Shell shell;
	protected Button cancel;
	private boolean manualinterrupted;
	StyledText console;
	
	
	int steps;
	private Thread stdoutthread;
	private Thread errthread;
	
	public KesoCompilationDialog(Shell parentshell, KesoMainWindow window, String filename, int steps) {
		
		this.parentshell = parentshell;
		this.filename = filename;
		this.window = window;
		
		this.steps = steps;
		
		this.shell = new Shell(parentshell, SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);
		this.shell.addShellListener(this);
		this.shell.setText("Compilation");
		this.display = this.shell.getDisplay();
	
		GridLayout gridlayout = new GridLayout();
		gridlayout.numColumns = 1;
		
		this.shell.setLayout(gridlayout);
		
		Group group = new Group(this.shell, SWT.NONE);
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		group.setLayout(new FillLayout());
		group.setText("Progress");
		
		this.progressbar = new ProgressBar(group, SWT.NONE);
		this.progressbar.setMinimum(0);
		this.progressbar.setMaximum(4);
		
		this.console = new StyledText(this.shell, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 200;
		gd.widthHint = 400;
		this.console.setLayoutData(gd);
		
		Composite comp = new Composite(this.shell, SWT.NONE);
		comp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		gridlayout = new GridLayout();
		gridlayout.numColumns = 3;
		gridlayout.marginBottom = 0;
		gridlayout.marginHeight = 0;
		gridlayout.marginLeft = 0;
		gridlayout.marginRight = 0;
		gridlayout.marginTop = 0;
		gridlayout.marginWidth = 0;
		comp.setLayout(gridlayout);
		
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
		
		this.window.getConsole().setText("Hello World!");
		
		if (steps == 1) {
			this.progressbar.setMaximum(6);
		} else {
			this.progressbar.setMaximum(9);
		}
	}
	
	
	public void open() {
		this.shell.open();
		
		(new Thread() {
			public void run() {
				KesoCompilationDialog.this.compile();
			}
		}).start();
		
		
		while(!this.shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
	}
	
	
	protected void setInterrupted(boolean interrupted) {
		this.interrupted = interrupted;
	}
	protected void increaseProgress() {
		display.syncExec(new Runnable() {
			public void run() {
				if (progressbar != null && !progressbar.isDisposed()) {
					progressbar.setSelection(progressbar.getSelection() + 1);
				}
			}
		});
	}
	
	public boolean isInterrupted() {
		return this.interrupted;
	}
	
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
	}
	
	public boolean getManualInterrupted() {
		return this.manualinterrupted;
	}

	public void widgetSelected(SelectionEvent e) {
		if (e.widget == this.cancel) {
			this.manualinterrupted = true;
			this.interrupted = true;
			this.shell.dispose();
		}
	}


	public void shellActivated(ShellEvent e) {
		// TODO Auto-generated method stub
		
	}


	public void shellClosed(ShellEvent e) {
		this.interrupted = true;
		this.manualinterrupted = true;
		this.shell.dispose();
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
	
	public void writeHint(String hint) {
		final String h = hint;
		display.syncExec(new Runnable() {
			public void run() {
				write(h, display.getSystemColor(SWT.COLOR_GRAY));
			}
		});
	}
	
	public void startFlushing(final BufferedReader stdout, final BufferedReader err) {
		this.stdoutthread = new Thread() {
			public void run() {
				String text;
				try {
					while ((text = stdout.readLine()) != null) {
						final String tcpy = text;
						display.syncExec(new Runnable() {
							public void run() {
								write(tcpy, null);
							}
						});
					}
				} catch(Exception e) {
					
				}
				stdoutthread = null;
			}
		};
		
		this.errthread = new Thread() {
			public void run() {
				String text;
				try {
					while ((text = err.readLine()) != null) {
						final String tcpy = text;
						display.syncExec(new Runnable() {
							public void run() {
								write(tcpy, display.getSystemColor(SWT.COLOR_RED));
							}
						});
					}
				} catch(Exception e) {
					
				}
				errthread = null;
			}
		};
		
		this.stdoutthread.start();
		this.errthread.start();
	}
	
	public void waitForOutput() {
		while (this.stdoutthread != null || this.errthread != null) {
			try {
				Thread.sleep(1000);
			} catch(Exception e) {
				
			}
		}
	}
	
	private int firstStep() throws IOException {
		int exitvalue = -1;
		this.writeHint("Generate Classes");
		
		String [] start = new String[] {"java", "keso.compiler.config.GenerateKesoClasses", 
			KesoGuiProperties.getInstance().getProperty("workbench.directory"), 
			this.filename,
			"templates", 
			"libs", 
			"buildstamps"
		};
		
		this.increaseProgress();
		
		Process process = Runtime.getRuntime().exec(start, new String[0], new File(KesoGuiProperties.getInstance().getProperty("jino.path")));
		this.startFlushing(new BufferedReader(new InputStreamReader(process.getInputStream())), 
				new BufferedReader(new InputStreamReader(process.getErrorStream())));
		this.waitForOutput();
		
		this.increaseProgress();
		
		exitvalue = process.exitValue();
		try {
			process.destroy();
		} catch(Exception e) {
			
		}
		
		this.increaseProgress();
		return exitvalue;
	}
	
	private int second() throws IOException {
		int exitvalue = -1;
		this.writeHint("Compile Java Code");
		this.writeHint("Please, be patient!");
		
		Vector start = new Vector();
		start.add("java");
		start.add("-classpath"); 
		
		if (KesoGuiProperties.getInstance().getProperty("jdk.path") != null
				&& KesoGuiProperties.getInstance().getProperty("jdk.path").trim().length() > 0) {
				start.add(
						KesoGuiProperties.getInstance().getProperty("jdk.path") + File.separator + "lib" + File.separator + "tools.jar" + 
						File.pathSeparator + 
						KesoGuiProperties.getInstance().getProperty("jino.path")
				);
		} else {
			start.add(KesoGuiProperties.getInstance().getProperty("jino.path"));
		}
		
		start.add("keso.compiler.Main");
		start.add("-bootmodules"); start.add("core");
		start.add("-output"); start.add(KesoGuiProperties.getInstance().getProperty("workbench.directory") + File.separator + "tmp");
		start.add("-def"); start.add(this.filename);
		start.add("-precompile");
		start.add("-X:no_automake");
		
		String [] parameters = KesoGuiProperties.getInstance().getProperty("server.parameter.string").split(" ");
		for (int i = 0; i < parameters.length; i++) {
			start.add(parameters[i].trim());
		}
		
		this.increaseProgress();
		
		Process process = Runtime.getRuntime().exec((String []) start.toArray(new String[0]), new String[0], new File(KesoGuiProperties.getInstance().getProperty("workbench.directory"))); 
		this.startFlushing(new BufferedReader(new InputStreamReader(process.getInputStream())), 
				new BufferedReader(new InputStreamReader(process.getErrorStream())));
		this.waitForOutput();
		
		this.increaseProgress();
		
		exitvalue = process.exitValue();
		
		try {
			process.destroy();
		} catch(Exception e) {
			
		}
		
		this.increaseProgress();
		
		return exitvalue;
	}
	
	public int third() throws IOException {
		int exitvalue = -1;
		String workbench = KesoGuiProperties.getInstance().getProperty("workbench.directory");
		
		this.writeHint("Remote Compilation");
		this.writeHint("Please, be patient!");
		
		
		Vector start = new Vector();
		start.add("java");
		start.add("-classpath"); start.add(KesoGuiProperties.getInstance().getProperty("communicator.path"));
		start.add("keso.communication.client.KesoClient");
		start.add("-server"); start.add(KesoGuiProperties.getInstance().getProperty("server.name"));
		start.add("-port"); start.add(KesoGuiProperties.getInstance().getProperty("server.port"));
		start.add("-source"); start.add(workbench + File.separator + "tmp" + File.separator + "source.zip");
		start.add("-target"); start.add(workbench + File.separator + "tmp");
		start.add("-username"); start.add(KesoGuiProperties.getInstance().getProperty("server.username"));
		start.add("-password"); start.add(KesoGuiProperties.getInstance().getProperty("server.password"));
		start.add("-parameter"); start.add(KesoGuiProperties.getInstance().getProperty("server.parameter.string"));
		
		if (KesoGuiProperties.getInstance().getProperty("server.getallfiles").equals("true")) {
			start.add("-all");
		}
		
		String systemroot = System.getProperty("SystemRoot");
		String [] arguments;
		if( systemroot != null) {
			arguments = new String [] {"SystemRoot=" + systemroot};
		} else {
			arguments = new String[0];
		}
		
		this.increaseProgress();
		
		Process process = Runtime.getRuntime().exec((String []) start.toArray(new String[0]), arguments, new File(KesoGuiProperties.getInstance().getProperty("communicator.path"))); 
		this.startFlushing(new BufferedReader(new InputStreamReader(process.getInputStream())), 
				new BufferedReader(new InputStreamReader(process.getErrorStream())));
		this.waitForOutput();
		
		this.increaseProgress();
		
		exitvalue = process.exitValue();
		
		try {
			process.destroy();
		} catch(Exception e) {
			
		}
		
		this.increaseProgress();
		
		return exitvalue;
		
	}
	
	
	public void compile() {
		
		try {
			int exitvalue = 0;
			if (!this.isInterrupted() && !this.getManualInterrupted()) {
				exitvalue = this.firstStep();
			}
			
			if (!this.isInterrupted() && !this.getManualInterrupted() && exitvalue == 0) {
				exitvalue = this.second();
			}
			
			if (exitvalue == 0 && this.steps == 2) {
				String workbench = KesoGuiProperties.getInstance().getProperty("workbench.directory");
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
						workbench + File.separator + this.filename
				};
				zipper.zip(args);
				
				(new File(workbench + File.separator + "tmp" + File.separator + "classes.zip")).delete();
				
				try {
					exitvalue = -1;
					exitvalue = this.third();
				} catch (Exception e) {
					
				}
			}

			writeHint("END");
			display.syncExec(new Runnable() {
				public void run() {
					if (!cancel.isDisposed()) {
						cancel.setText("Close");
					}
				}
			});
			
		} catch(Exception e) {
		}
	}
}
