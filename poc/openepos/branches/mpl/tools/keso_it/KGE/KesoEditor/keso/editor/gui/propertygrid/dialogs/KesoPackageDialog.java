package keso.editor.gui.propertygrid.dialogs;

import java.io.File;
import java.util.Iterator;
import java.util.Vector;

import keso.editor.gui.KesoGuiProperties;
import keso.editor.gui.core.KesoImageManager;
import keso.editor.gui.core.KesoMainWindow;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class KesoPackageDialog implements SelectionListener, Listener {
	Shell shell;
	Shell parentshell;
	Display display;
	
	String [] modules;
	
	Tree tree;
	
	boolean result = false;
	
	String [] selections;
	
	Vector checkeditems = new Vector();
	boolean multi = false;
	
	public KesoPackageDialog(Shell parentshell, String [] modules, boolean m) {
		this.parentshell = parentshell;
		this.display = this.parentshell.getDisplay();
		this.modules = modules;
		this.multi = m;
		
		this.shell = new Shell(parentshell, SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM | SWT.RESIZE);
		this.shell.setSize(400, 400);

		
		Rectangle clientarea = this.parentshell.getBounds();
		int mx = (clientarea.x + clientarea.x + clientarea.width) / 2 - (this.shell.getSize().x / 2);
		int my = (clientarea.y + clientarea.y + clientarea.height) / 2 - (this.shell.getSize().y / 2);
		
		this.shell.setLocation(mx, my);
		
		this.shell.setText("Explorer");
		
		GridLayout glayout = new GridLayout();
		glayout.numColumns = 1;
		
		this.shell.setLayout(glayout);
		
		CTabFolder folder = new CTabFolder(this.shell, SWT.BORDER);
		folder.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		CTabItem folderitem = new CTabItem(folder, SWT.NONE);
		this.tree = new Tree(folder, SWT.NONE | SWT.CHECK);
		this.tree.addSelectionListener(this);
		this.tree.addListener(SWT.Selection, this);
		folderitem.setControl(this.tree);
		folderitem.setText("Explorer");
		
		
		Composite comp = new Composite(this.shell, SWT.NONE);
		comp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		glayout = new GridLayout();
		glayout.numColumns = 3;
		glayout.marginBottom = 0;
		glayout.marginHeight = 0;
		glayout.marginLeft = 0;
		glayout.marginRight = 0;
		glayout.marginTop = 0;
		glayout.marginWidth = 0;
		comp.setLayout(glayout);
		
		Composite fakecomp = new Composite(comp, SWT.NONE);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 1;
		fakecomp.setLayoutData(gd);
				
		Button button = new Button(comp, SWT.NONE);
		GridData gdata = new GridData();
		//gdata.widthHint = 100;
		button.setLayoutData(gdata);
		button.setText("Select");
		button.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
			}

			public void widgetSelected(SelectionEvent e) {
				result = true;
				
				Vector allpaths = new Vector();
				for (Iterator j = checkeditems.iterator(); j.hasNext(); ) {
					TreeItem item = (TreeItem) j.next();
					if (!((File) item.getData()).isDirectory()) {
						String path = "";
						String [] ext = (item.getText()).split("\\.");
						
						for (int i = 0; i < ext.length; i++) {
							if (i == 0) {
								path += ext[i];
							} else if (i == ext.length - 1) {
								
							} else {
								path += "." + ext[i];
							}
						}
						while (item.getParentItem() != null) {
							path = item.getParentItem().getText() + "/" + path;
							item = item.getParentItem();
						}

						allpaths.add(path);
					}
				}
				if (allpaths.size() == 0) {
					selections = new String[0];
				} else {
					selections = (String []) allpaths.toArray(new String[0]);
				}
				
				result = true;
				shell.dispose();
			}
		});
		
		button = new Button(comp, SWT.NONE);
		button.setLayoutData(new GridData());
		button.setText("Cancel");
		button.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
			}

			public void widgetSelected(SelectionEvent e) {
				selections = null;
				result = false;
				shell.dispose();
			}
			
		});	
		
		this.fillTree();
	}
	
	public String [] getSelections() {
		return this.selections;
	}
	
	private void fillTree() {
		this.tree.removeAll();
		String workbench = KesoGuiProperties.getInstance().getProperty("workbench.directory") + "/libs";
		File workb = new File(workbench);
		if (workb.exists() && workb.isDirectory()) {
			String [] list = this.modules;
			
			for (int i = 0; i < list.length; i++) {
				if (list[i].length() != 0 && list[i].charAt(0) != '.') {
					this.parseDirectory(null, workbench + "/" + list[i]);
				}
			}
		}
	}
	
	public void setSelection(String [] spaths) {
		for (int z = 0; z < spaths.length; z++) {
			String [] parts = spaths[z].split("/");
			TreeItem parent = null;
			TreeItem item = null;
			for (int i = 0; i < parts.length; i++) {
				parts[i] = parts[i].trim();
				String filename = parts[i];
				
				parent = item;
				
				if (i == parts.length - 1) {
					String [] extensions = parts[i].split("\\.");
					if (!extensions[extensions.length - 1].trim().toLowerCase().equals("java")) {
						filename = parts[i] + ".java";
					}
				}
				item = this.find(parent, filename);
				
				if (item == null) {
					break;
				}
			}
			if (item != null) {
				this.tree.setSelection(item);
				item.setChecked(true);
				this.checkeditems.add(item);
			}
		}
	}
	
	private void parseDirectory(TreeItem parentitem, String directory) {
		File dir = new File(directory);
		if (dir.exists() && dir.isDirectory()) {
			String [] list = dir.list();
			for (int i = 0; i < list.length; i++) {
				boolean parse = true;
				if (list[i].length() != 0 && list[i].charAt(0) != '.') {
					String path = directory + "/" + list[i];
					File file = new File(path);
					TreeItem item = null;
					if (file.isDirectory()) {
						item = this.find(parentitem, list[i]);
					} else {
						String [] extensions = list[i].split("\\.");
						if (!extensions[extensions.length - 1].trim().toLowerCase().equals("java")) {
							parse = false;
						}
					}
					if (parse) {
						if (item == null) {
							if (parentitem == null) {
								item = new TreeItem(this.tree, 
										SWT.NONE, 
										this.getIndex(null, list[i], file.isDirectory()));
							} else {
								item = new TreeItem(parentitem, 
										SWT.NONE, 
										this.getIndex(parentitem, list[i], file.isDirectory()));
							}
							if (file.isDirectory()) {
								item.setImage(KesoImageManager.getInstance().getImage("directory"));
							} else {
								item.setImage(KesoImageManager.getInstance().getImage("file"));
							}
							item.setText(list[i]);
							item.setData(file);
						}
						if (file.isDirectory()) {
							this.parseDirectory(item, directory + "/" + list[i]);
						}
					}
				}
			}
		}
	}
	
	private int getIndex(TreeItem item, String node, boolean isdirectory) {
		if (item == null) {
			for (int i = 0; i < this.tree.getItemCount(); i++) {
				TreeItem it = this.tree.getItem(i);
				if (isdirectory) {
					if (((File) it.getData()).isDirectory()) {
						if (it.getText().compareToIgnoreCase(node) > 0) {
							return i;
						}
					} else {
						return i;
					}
				} else {
					if (((File) it.getData()).isDirectory()) {
						continue;
					} else {
						if (it.getText().compareToIgnoreCase(node) > 0) {
							return i;
						}
					}
				}
			}
			return this.tree.getItemCount();
		} else {
			for (int i = 0; i < item.getItemCount(); i++) {
				TreeItem it = item.getItem(i);
				if (isdirectory) {
					if (((File) it.getData()).isDirectory()) {
						if (it.getText().compareToIgnoreCase(node) > 0) {
							return i;
						}
					} else {
						return i;
					}
				} else {
					if (((File) it.getData()).isDirectory()) {
						continue;
					} else {
						if (it.getText().compareToIgnoreCase(node) > 0) {
							return i;
						}
					}
				}
			}
			return item.getItemCount();
		}
	}
	
	private TreeItem find(TreeItem item, String node) {
		if (item == null) {
			TreeItem [] items = this.tree.getItems();
			for (int i = 0; i < items.length; i++) {
				if (items[i].getText().equals(node)) {
					return items[i];
				}
			}
		} else {
			TreeItem [] items = item.getItems();
			for (int i = 0; i < items.length; i++) {
				if (items[i].getText().equals(node)) {
					return items[i];
				}
			}
		}
		return null;
	}
	
	
	
	public boolean open() {
		this.shell.open();
		while(!this.shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return this.result;
	}

	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void widgetSelected(SelectionEvent e) {
		
	}

	public void handleEvent(Event event) {
		if (event.detail == SWT.CHECK) {
			TreeItem item = (TreeItem) event.item;
			if (((File) item.getData()).isDirectory()) {
				item.setChecked(false);
			} else {
				if (item.getChecked()) {
					if (!this.multi) {
						TreeItem [] items = (TreeItem []) this.checkeditems.toArray(new TreeItem[0]);
						if (items != null && items.length != 0) {
							for (int i = 0; i < items.length; i++) {
								items[i].setChecked(false);
								this.checkeditems.remove(items[i]);
							}
						}	
					}
					this.checkeditems.add(item);
				} else {
					this.checkeditems.remove(item);
				}
			}
		}
	}
}
