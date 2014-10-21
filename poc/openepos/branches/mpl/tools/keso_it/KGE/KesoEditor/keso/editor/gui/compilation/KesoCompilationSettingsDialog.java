package keso.editor.gui.compilation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Iterator;
import java.util.Vector;

import keso.editor.gui.KesoGuiProperties;
import keso.editor.gui.compilation.parameter.KesoParameter;
import keso.editor.gui.compilation.parameter.KesoParameterList;
import keso.editor.gui.core.KesoMainWindow;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class KesoCompilationSettingsDialog implements SelectionListener {
	Shell shell;
	Shell parentshell;
	Display display;
	KesoMainWindow window;
	
	Button localbtn;
	Button remotebtn;
	Button cancelbtn;
	Button compilebtn;
	
	Group userdatagrp;
	Group serverdatagrp;
	Group communicatorgrp;
	
	Text jinopath;
	Text communicatorpath;
	Text jdkpath;
	Text serverurl;
	Text port;
	Text username;
	Text password;

	String filename = "";
	
	
	Button prune;
	Button verbose;
	Button veryverbose;
	Button inline;
	Button dump;
	Button debug;
	Button cfg;
	Button pedantic;
	Button tricore;
	Button avr;
	Button h8300;
	Button linux;
	Button arm;
	
	Button getallfiles;
	
	Group parametergroup;
	Table parametertable;
	
	CTabFolder tabfolder;
	
	private Label serverlb;
	private StringBuffer parameterstring;
	
	boolean validserver = false;
	
	TableEditor editor;
	private Text descriptiontext;
	private Table servertable;
	private Vector servers;
	private Label invalidlbl;
	
	public KesoCompilationSettingsDialog(KesoMainWindow window) {
		this.parentshell = window.getShell();
		this.display = this.parentshell.getDisplay();
		this.window = window;
		
		this.shell = new Shell(parentshell, SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);
		this.shell.setText("Compilation");
		
		
		GridLayout gridlayout = new GridLayout();
		gridlayout.numColumns = 1;
		
		this.shell.setLayout(gridlayout);
		
		tabfolder = new CTabFolder(this.shell, SWT.BORDER);
		tabfolder.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		CTabItem tabitem = new CTabItem(tabfolder, SWT.NONE);
		
		tabitem.setText("Compile Settings");
		
		Composite comp = new Composite(tabfolder, SWT.NONE);
		comp.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		gridlayout = new GridLayout();
		gridlayout.numColumns = 1;
		
		comp.setLayout(gridlayout);
		
		tabitem.setControl(comp);
		
		this.localbtn = new Button(comp, SWT.RADIO);
		this.localbtn.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		this.localbtn.setLayoutData(new GridData());
		this.localbtn.setText("Compile Java (Local)");
		this.localbtn.addSelectionListener(this);
		this.localbtn.setSelection(true);
		
		
		this.remotebtn = new Button(comp, SWT.RADIO);
		this.remotebtn.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		this.remotebtn.setText("Compile Java and Generate Images (Remote)");
		GridData gd = new GridData();
		this.remotebtn.setLayoutData(gd);
		this.remotebtn.addSelectionListener(this);
		
		
		tabfolder = new CTabFolder(this.shell, SWT.BORDER);
		gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 200;
		tabfolder.setLayoutData(gd);
		tabitem = new CTabItem(tabfolder, SWT.NONE);
		tabitem.setText("General");
		
		tabfolder.setSelection(tabitem);
		
		comp = new Composite(tabfolder, SWT.NONE);
		comp.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		gridlayout = new GridLayout();
		gridlayout.numColumns = 2;
		comp.setLayout(gridlayout);
		
		tabitem.setControl(comp);
		
		Composite tmpcomp = new Composite(comp, SWT.NONE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		tmpcomp.setLayoutData(gd);
		tmpcomp.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		gridlayout = new GridLayout();
		gridlayout.numColumns = 3;
		gridlayout.marginBottom = 0;
		gridlayout.marginHeight = 0;
		gridlayout.marginLeft = 0;
		gridlayout.marginRight = 0;
		gridlayout.marginTop = 0;
		gridlayout.marginWidth = 0;
		tmpcomp.setLayout(gridlayout);
		
		this.serverlb = new Label(tmpcomp, SWT.NONE);
		this.serverlb.setText("Server URL:");
		this.serverlb.setLayoutData(new GridData());
		this.serverlb.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		
		this.serverurl = new Text(tmpcomp, SWT.BORDER);
		this.serverurl.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		this.serverurl.setLayoutData(new GridData());
		gd = new GridData(GridData.FILL_HORIZONTAL);
		this.serverurl.setLayoutData(gd);
		
		
		Button btn = new Button(tmpcomp, SWT.FLAT);
		btn.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		btn.setLayoutData(new GridData());
		btn.setText("Check ...");
		btn.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void widgetSelected(SelectionEvent e) {
				downloadLists();
			}
			
		});
		
		
		Label usernamelb = new Label(comp, SWT.NONE);
		usernamelb.setText("Username:");
		usernamelb.setLayoutData(new GridData());
		usernamelb.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		
		this.username = new Text(comp, SWT.BORDER);
		this.username.setLayoutData(new GridData());
		this.username.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		this.username.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		
		Label passwordlb = new Label(comp, SWT.NONE);
		passwordlb.setText("Password:");
		passwordlb.setLayoutData(new GridData());
		passwordlb.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		
		this.password = new Text(comp, SWT.BORDER | SWT.PASSWORD);
		this.password.setLayoutData(new GridData());
		this.password.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		this.password.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		
		this.getallfiles = new Button(comp, SWT.CHECK);
		gd = new GridData();
		gd.horizontalSpan = 2;
		this.getallfiles.setLayoutData(gd);
		this.getallfiles.setText("Get all files!");
		this.getallfiles.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		
		
		
		
		
		
		tabitem = new CTabItem(tabfolder, SWT.NONE);
		tabitem.setText("Paths");
		
		
		comp = new Composite(tabfolder, SWT.NONE);
		comp.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		gridlayout = new GridLayout();
		gridlayout.numColumns = 3;
		
		comp.setLayout(gridlayout);
		
		tabitem.setControl(comp);

		Label javapthlbl = new Label(comp, SWT.NONE);
		javapthlbl.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		javapthlbl.setLayoutData(new GridData());
		javapthlbl.setText("Path of JDK:");
		
		this.jdkpath = new Text(comp, SWT.BORDER | SWT.READ_ONLY);
		this.jdkpath.setLayoutData(new GridData());
		this.jdkpath.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		this.jdkpath.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		btn = new Button(comp, SWT.FLAT);
		btn.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		btn.setLayoutData(new GridData());
		btn.setText("...");
		btn.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dialog = new DirectoryDialog(shell, SWT.NONE);
				dialog.setText("Path of JDK");
				dialog.setText("Path of JDK");
				dialog.setFilterPath(jdkpath.getText());
				String path = dialog.open();
				
				if (path != null) {
					jdkpath.setText(path);
					KesoGuiProperties.getInstance().setProperty("jdk.path", path);
				} 
			}
			
		});
		
		
		Label jinopathlbl = new Label(comp, SWT.NONE);
		jinopathlbl.setLayoutData(new GridData());
		jinopathlbl.setText("Path of Jino:");
		jinopathlbl.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		
		this.jinopath = new Text(comp, SWT.BORDER | SWT.READ_ONLY);
		this.jinopath.setLayoutData(new GridData());
		this.jinopath.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		this.jinopath.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		
		
		
		btn = new Button(comp, SWT.FLAT);
		btn.setLayoutData(new GridData());
		btn.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		btn.setText("...");
		btn.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dialog = new DirectoryDialog(shell, SWT.NONE);
				dialog.setText("Path of Jino");
				dialog.setFilterPath(jinopath.getText());
				String path = dialog.open();
				
				if (path != null) {
					jinopath.setText(path);
					KesoGuiProperties.getInstance().setProperty("jino.path", path);
				} 
			}
			
		});
		
/*
		Label communictatorlbl = new Label(comp, SWT.NONE);
		communictatorlbl.setLayoutData(new GridData());
		communictatorlbl.setText("Path of Communicator:");
		communictatorlbl.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		
		
		this.communicatorpath = new Text(comp, SWT.BORDER | SWT.READ_ONLY);
		this.communicatorpath.setLayoutData(new GridData());
		this.communicatorpath.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		this.communicatorpath.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		if (KesoGuiProperties.getInstance().getProperty("communicator.path") != null) {
			this.communicatorpath.setText(KesoGuiProperties.getInstance().getProperty("communicator.path"));
		}
		
		btn = new Button(comp, SWT.FLAT);
		btn.setLayoutData(new GridData());
		btn.setText("...");
		btn.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		btn.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dialog = new DirectoryDialog(shell, SWT.NONE);
				dialog.setText("Path of Communicator");
				dialog.setFilterPath(communicatorpath.getText());
				String path = dialog.open();
				
				if (path != null) {
					communicatorpath.setText(path);
					KesoGuiProperties.getInstance().setProperty("communicator.path", path);
				} 
			}
			
		});
		*/
		
		
		
		tabitem = new CTabItem(tabfolder, SWT.NONE);
		tabitem.setText("Parameters");
		
		
		comp = new Composite(tabfolder, SWT.NONE);
		comp.setLayout(new FillLayout());
		
		tabitem.setControl(comp);
		
		SashForm sash = new SashForm(comp, SWT.VERTICAL);
		comp = new Composite(sash, SWT.NONE);
		comp.setLayout(new FillLayout());
		

		this.parametertable = new Table(comp, SWT.FULL_SELECTION);
		this.parametertable.addSelectionListener(this);
		
		this.parametertable.setLinesVisible(true);
		this.parametertable.setHeaderVisible(true);
		
		this.editor = new TableEditor(this.parametertable);
		this.editor.grabHorizontal = true;
		this.editor.grabVertical = true;
		this.editor.minimumWidth = 100;
		
		TableColumn column = new TableColumn(this.parametertable, SWT.NONE);
		column.setText("Parameter");
		column.setWidth(100);
		
		column = new TableColumn(this.parametertable, SWT.NONE);
		column.setText("Value");
		column.setWidth(150);
		
		column = new TableColumn(this.parametertable, SWT.NONE);
		column.setText("Separator");
		column.setWidth(80);
		
		
		comp = new Composite(sash, SWT.NONE);
		comp.setLayout(new FillLayout());
		
		this.descriptiontext = new Text(comp, SWT.NONE | SWT.WRAP | SWT.V_SCROLL);
		this.descriptiontext.setText("Description:");
		
		
		sash.setWeights(new int [] {5, 1});
		
		
		tabitem = new CTabItem(tabfolder, SWT.NONE);
		tabitem.setText("Server List");
		
		
		comp = new Composite(tabfolder, SWT.NONE);
		comp.setLayout(new FillLayout());
		
		tabitem.setControl(comp);
		
		this.servertable = new Table(comp, SWT.FULL_SELECTION);
		this.servertable.setLinesVisible(true);
		this.servertable.setHeaderVisible(true);
		
		column = new TableColumn(this.servertable, SWT.NONE);
		column.setText("Server");
		column.setWidth(300);
		
		
		
		
		
		

		comp = new Composite(this.shell, SWT.NONE);
		comp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		gridlayout = new GridLayout();
		gridlayout.numColumns = 5;
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
		
		
		this.invalidlbl = new Label(comp, SWT.NONE);
		this.invalidlbl.setLayoutData(new GridData());
		this.invalidlbl.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
		this.invalidlbl.setText("invalid url");
		
		
		btn = new Button(comp, SWT.PUSH);
		btn.setLayoutData(new GridData());
		btn.setText("Apply");
		btn.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void widgetSelected(SelectionEvent e) {
				store();
			}
		});
		
		this.compilebtn = new Button(comp, SWT.PUSH);
		this.compilebtn.setLayoutData(new GridData());
		this.compilebtn.setText("Compile");
		this.compilebtn.addSelectionListener(this);
		
		this.cancelbtn = new Button(comp, SWT.PUSH);
		this.cancelbtn.setLayoutData(new GridData());
		this.cancelbtn.setText("Cancel");
		this.cancelbtn.addSelectionListener(this);
		
		
		//this.updateEnabledStat();
		
		this.copyFile();
		
		//this.setButtonSelection();
		
		this.shell.pack();
		
		this.shell.setSize(380, this.shell.getSize().y);
	

		Rectangle clientarea = this.parentshell.getBounds();
		int mx = (clientarea.x + clientarea.x + clientarea.width) / 2 - (this.shell.getSize().x / 2);
		int my = (clientarea.y + clientarea.y + clientarea.height) / 2 - (this.shell.getSize().y / 2);
		
		this.shell.setLocation(mx, my);
		
		this.restore();
		
		
		if (this.serverurl.getText().trim().length() > 0) {
			this.downloadLists();
		}
		
		this.shell.open();
		while(!this.shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	private void downloadLists() {
		try {
			URL serverurl = new URL(this.serverurl.getText() + "/kesoparameterlist.txt");
			BufferedReader reader = new BufferedReader(new InputStreamReader(serverurl.openStream()));
			this.parameterstring = new StringBuffer();
			String text;
			while ((text = reader.readLine()) != null) {
				this.parameterstring.append(text + "\n");
			}
			
			KesoParameterList.read(this.parameterstring.toString());
			
			serverurl = new URL(this.serverurl.getText() + "/kesoserverlist.txt");
			reader = new BufferedReader(new InputStreamReader(serverurl.openStream()));
			this.servers = new Vector();
			while ((text = reader.readLine()) != null) {
				if (text.trim().length() > 0) {
					this.servers.add(text);
				}
			}
			
			this.validserver = true;
			
		} catch(Exception e) {
			this.servers = null;
			this.validserver = false;
			this.parameterstring = null;
			KesoParameterList.clear();
			
		}

		if (this.remotebtn.getSelection()) {
			this.invalidlbl.setVisible(!this.validserver);
		} else {
			this.invalidlbl.setVisible(false);
		}
		
		updateTables();
		//updateEnabledStat();
	}
	
	private void updateTables() {
		removeEditor();
		
		this.parametertable.removeAll();
		this.servertable.removeAll();
		
		Vector v = KesoParameterList.getParameters();
		
		for (Iterator i = v.iterator(); i.hasNext(); ) {
			KesoParameter parameter = (KesoParameter) i.next();
			TableItem item = new TableItem(this.parametertable, SWT.NONE);
			item.setData(parameter);
			
			String chr = "";
			if (parameter.getSeparator().length() > 0) {
				chr = Integer.toString((int) (parameter.getSeparator().toCharArray()[0]));
			}
			
			String value = KesoGuiProperties.getInstance().getProperty("server.parameter." + parameter.getName() + chr, null);
			if (value != null && parameter.hasPossibleValues()) {
				if (!parameter.getPossibleValues().contains(value)) {
					value = null;
				}
			}
			
			if (value == null) {
				if (parameter.hasDefaultValue()) {
					if (parameter.getType().equals("boolean")) {
						value = parameter.getDefaultValue().trim().toLowerCase().equals("true") ? "true" : "false";
					} else {
						value = parameter.getDefaultValue();
					}
				} else {
					if (parameter.getType().equals("boolean")) {
						value = "false";
					} else {
						if (parameter.getType().equals("string") && parameter.hasPossibleValues()) {
							value = "<UNSET>";
						}  else {
							value = "";
						}
					}
				}
				
			}
			
			item.setText(new String [] {parameter.getName(), value, parameter.getSeparator()});
		}
		
		this.servertable.removeAll();
		
		if (this.servers != null) {
			for (Iterator i = this.servers.iterator(); i.hasNext(); ) {
				TableItem item = new TableItem(this.servertable, SWT.NONE);
				item.setData((String) i.next());
				item.setText((String) item.getData());
			}
		}
	}
	
	private void setButtonSelection() {
		if (KesoGuiProperties.getInstance().getProperty("server.getallfiles") != null && 
				KesoGuiProperties.getInstance().getProperty("server.getallfiles").equals("true")) {
			this.getallfiles.setSelection(true);
		} else {
			this.getallfiles.setSelection(false);
		}
		
		
	}

	private void copyFile() {
		String rcfilename = (new File(this.window.getFilename())).getName();
		File  file = new File(KesoGuiProperties.getInstance().getProperty("workbench.directory") + File.separator + "rc" + File.separator + "tmp");
		if (!file.exists()) {
			file.mkdirs();
		}
		
		byte [] data = new byte[4096];
		int read;
		try {
			FileInputStream in = new FileInputStream(this.window.getFilename());
			FileOutputStream out = new FileOutputStream(file.getAbsolutePath() + File.separator + "config.kcl");
			
			while((read = in.read(data)) >= 0) {
				out.write(data, 0, read);
			}
			
			in.close();
			out.close();
		
			this.filename = "rc" + File.separator + "tmp" + File.separator + "config.kcl";
		} catch(Exception e) {
			
		}
		
	}

	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private void removeEditor() {
		if (this.editor.getEditor() != null) {
			this.editor.getEditor().dispose();
			editor.setEditor(null);
		}
	}

	public void widgetSelected(SelectionEvent e) {
		if (e.widget == this.parametertable) {
			if (this.parametertable.getSelectionCount() == 1) {
				removeEditor();
				
				final TableItem item = this.parametertable.getSelection()[0];
				KesoParameter parameter = (KesoParameter) item.getData();
				
				this.descriptiontext.setText("Description:");
				if (parameter.hasDescription()) {
					this.descriptiontext.append("\n" + parameter.getDescription());
				}
				
				if (parameter.getType().equals("boolean") || parameter.hasPossibleValues()) {
					Vector possiblevalues;
					if (parameter.getType().equals("boolean")) {
						possiblevalues = new Vector();
						possiblevalues.add("false");
						possiblevalues.add("true");
					} else {
						possiblevalues = new Vector();
						possiblevalues.add("<UNSET>");
						possiblevalues.addAll(parameter.getPossibleValues());
					}
					
					final Combo combo = new Combo(this.parametertable, SWT.READ_ONLY);
					for (Iterator i = possiblevalues.iterator(); i.hasNext(); ) {
						String value = (String) i.next();
						combo.add(value);
					}
					combo.setText(item.getText(1));
					combo.setFocus();
					combo.addSelectionListener(new SelectionListener() {
						public void widgetDefaultSelected(SelectionEvent e) {
							
						}

						public void widgetSelected(SelectionEvent e) {
							item.setText(1, combo.getText());
						}
					});
					
					combo.addFocusListener(new FocusListener() {

						public void focusGained(FocusEvent e) {
							// TODO Auto-generated method stub
							
						}

						public void focusLost(FocusEvent e) {
							removeEditor();
						}
						
					});
					
					editor.setEditor(combo, item, 1);
					
				} else {
					final Text text = new Text(this.parametertable, SWT.NONE);
					text.setText(item.getText(1));
					text.selectAll();
					text.setFocus();
					text.addModifyListener(new ModifyListener() {

						public void modifyText(ModifyEvent e) {
							item.setText(1, text.getText());
						}
						
					});
					
					text.addFocusListener(new FocusListener() {

						public void focusGained(FocusEvent e) {
							// TODO Auto-generated method stub
							
						}

						public void focusLost(FocusEvent e) {
							removeEditor();
						}
						
					});
					
					editor.setEditor(text, item, 1);
				}
				
			}
		} else if (e.widget == this.localbtn) {
			this.invalidlbl.setVisible(false);
		} else if (e.widget == this.remotebtn) {
			if (!this.validserver) {
				this.invalidlbl.setVisible(true);
			}
		} else if (e.widget == this.compilebtn) {
			File f = new File(this.jinopath.getText());
			if (f.exists()) {
				if (this.localbtn.getSelection()) {
					try {
						this.store();
						KesoCompilation dialog = new KesoCompilation(this.shell, this.window, this.filename, null, null, 1);
						dialog.open();
					} catch(Exception ex) {
						ex.printStackTrace();
					}
					
				} else if (this.remotebtn.getSelection()) {
					if (this.serverurl.getText().trim().length() > 0 && this.validserver) {
						if (this.servers.size() > 0) {
							if (this.username.getText().trim().length() > 0) {
								if (this.password.getText().trim().length() > 0) {
									Vector parameters = this.generateParameters();
									try {
										this.store();
										KesoCompilation dialog = new KesoCompilation(this.shell, this.window, this.filename, parameters, this.servers, 2);
										dialog.open();
									} catch(Exception ex) {
									}
								} else {
									MessageBox message = new MessageBox(this.shell, SWT.OK | SWT.ICON_ERROR);
									message.setText("Error");
									message.setMessage("Please, type in your Password!");
									message.open();
									this.tabfolder.setSelection(0);
									this.password.setFocus();
								}
							} else {
								MessageBox message = new MessageBox(this.shell, SWT.OK | SWT.ICON_ERROR);
								message.setText("Error");
								message.setMessage("Please, type in your Username!");
								message.open();
								this.tabfolder.setSelection(0);
								this.username.setFocus();
							}
						} else {
							MessageBox message = new MessageBox(this.shell, SWT.OK | SWT.ICON_WARNING);
							message.setText("Warning");
							message.setMessage("Can not compile! There are no active Server at the moment.");
							message.open();
						}
					} else {
						MessageBox message = new MessageBox(this.shell, SWT.OK | SWT.ICON_ERROR);
						message.setText("Error");
						message.setMessage("Please, type in a valid Server URL and check it!");
						message.open();
						this.tabfolder.setSelection(0);
						this.serverurl.setFocus();
					}
				}
			} else {
				MessageBox message = new MessageBox(this.shell, SWT.OK | SWT.ICON_ERROR);
				message.setText("Error");
				message.setMessage("JINO Path is incorrect!");
				message.open();
				this.tabfolder.setSelection(1);
				this.jinopath.setFocus();
			}
			
			
		} else if (e.widget == this.cancelbtn) {
			this.shell.dispose();
		}
	}
	
	private Vector generateParameters() {
		Vector output = new Vector();
		TableItem [] items = this.parametertable.getItems();
		for (int i = 0; i < items.length; i++) {
			KesoParameter parameter = (KesoParameter) items[i].getData();
			if (parameter.getType().equals("boolean")) {
				if (items[i].getText(1).equals("true")) {
					output.add("-" + parameter.getName());
				}
			} else {
				if (items[i].getText(1).trim().length() != 0 && !items[i].getText(1).trim().equals("<UNSET>")) {
					if (parameter.getSeparator().equals(" ")) {
						output.add("-" + parameter.getName());
						output.add(items[i].getText(1));
					} else {
						output.add("-" + parameter.getName() + parameter.getSeparator() + items[i].getText(1));
					}
				}
			}
		}
		return output;
	}
	
	private void restore() {
		KesoGuiProperties properties = KesoGuiProperties.getInstance();
		
		/*
		if (properties.getProperty("server.locale", "false").equals("true")) {
			this.onlylocalebtn.setSelection(true);
			this.remotebtn.setSelection(false);
		} else {
			this.onlylocalebtn.setSelection(false);
			this.remotebtn.setSelection(true);
		}
		*/
	
		if (properties.getProperty("server.local", "false").equals("true")) {
			this.localbtn.setSelection(true);
			this.remotebtn.setSelection(false);
		} else {
			this.localbtn.setSelection(false);
			this.remotebtn.setSelection(true);
		}
		
		this.serverurl.setText(properties.getProperty("server.url", ""));
		this.username.setText(properties.getProperty("server.username", ""));
		this.password.setText(properties.getProperty("server.password", ""));
		this.jinopath.setText(properties.getProperty("server.path.jino", ""));
		this.jdkpath.setText(properties.getProperty("server.path.jdk", ""));
		//this.communicatorpath.setText(properties.getProperty("server.path.communicator", ""));
		
		this.getallfiles.setSelection(properties.getProperty("server.getallfiles", "false").equals("true"));
		updateTables();
	}
	
	private void store() {
		KesoGuiProperties properties = KesoGuiProperties.getInstance();
	
		properties.setProperty("server.local", Boolean.toString(this.localbtn.getSelection()).toLowerCase());
		
		properties.setProperty("server.url", this.serverurl.getText());
		properties.setProperty("server.username", this.username.getText());
		properties.setProperty("server.password", this.password.getText());
		properties.setProperty("server.path.jino", this.jinopath.getText());
		properties.setProperty("server.path.jdk", this.jdkpath.getText());
		//properties.setProperty("server.path.communicator", this.communicatorpath.getText());
		properties.setProperty("server.getallfiles", Boolean.toString(this.getallfiles.getSelection()).toLowerCase());
		
		TableItem [] items = this.parametertable.getItems();
		
		for (int i = 0; i < items.length; i++) {
			KesoParameter parameter = (KesoParameter) items[i].getData();
			String chr = "";
			if (parameter.getSeparator().length() > 0) {
				chr = Integer.toString((int) (parameter.getSeparator().toCharArray()[0]));
			}
			
			properties.setProperty("server.parameter." + parameter.getName() + chr, items[i].getText(1));
		}
		
		
	}
	
}
