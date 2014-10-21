package keso.editor.gui.propertygrid;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.Iterator;
import java.util.Vector;

import keso.editor.KGE;
import keso.editor.data.IKesoData;
import keso.editor.data.KesoData;
import keso.editor.filter.manager.KesoFilterManager;
import keso.editor.gui.KesoGuiProperties;
import keso.editor.gui.core.KesoImageManager;
import keso.editor.gui.core.KesoMainWindow;
import keso.editor.gui.propertygrid.dialogs.KesoNewPropertyDialog;
import keso.editor.gui.propertygrid.dialogs.KesoPackageDialog;
import keso.editor.gui.propertygrid.dialogs.KesoPropertySelectionDialog;
import keso.editor.property.IKesoProperty;
import keso.editor.property.KesoProperty;
import keso.editor.property.complexproperty.IKesoComplexProperty;
import keso.editor.property.propertymanager.KesoPropertyManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

/**
 * @author  Wilhelm Haas
 */
public class KesoPropertyGrid implements PropertyChangeListener, SelectionListener, ControlListener {
	Composite parent;
	int style;
	
	Tree tree;
	TreeColumn propertyColumn;
	TreeColumn valueColumn;
	TreeEditor editor;
	SashForm sashform;
	Label description;
	
	IKesoData data;
	
	private KesoPropertyTreeItem selection;
	
	ToolItem newproperty;
	ToolItem deleteproperty;
	ToolItem editname;
	
	private PropertyChangeSupport change = new PropertyChangeSupport(this);
	
	public KesoPropertyGrid(Composite parent, int style) {
		this.parent = parent;
		this.style = style;
		
		this.sashform = new SashForm(this.parent, SWT.VERTICAL);
		this.sashform.setLayout(new FillLayout());
		
		CTabFolder folder = new CTabFolder(this.sashform, SWT.BORDER);
		CTabItem citem = new CTabItem(folder, SWT.NONE);
		folder.setSelection(citem);
		citem.setText("Properties");

		citem.setImage(KesoImageManager.getInstance().getImage("propertygrid"));
		
		Composite comp = new Composite(folder, SWT.NONE);
		citem.setControl(comp);
		
		GridLayout gridlayout = new GridLayout();
		gridlayout.numColumns = 1;
		gridlayout.marginBottom = 0;
		gridlayout.marginHeight = 0;
		gridlayout.marginLeft = 0;
		gridlayout.marginRight = 0;
		gridlayout.marginTop = 3;
		gridlayout.marginWidth = 0;
		
		comp.setLayout(gridlayout);
	
	 	ToolBar toolbar = new ToolBar(comp, SWT.FLAT);
		toolbar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		this.newproperty = new ToolItem(toolbar, SWT.PUSH);
		this.newproperty.setImage(KesoImageManager.getInstance().getImage("addproperty"));
		this.newproperty.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				new KesoNewPropertyDialog(KesoPropertyGrid.this);
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		this.newproperty.setEnabled(false);
		
		this.deleteproperty = new ToolItem(toolbar, SWT.PUSH);
		this.deleteproperty.setImage(KesoImageManager.getInstance().getImage("deleteproperty"));
		this.deleteproperty.setEnabled(false);
		
		this.deleteproperty.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				if (getSelection() != null) {
					IKesoProperty property = getSelection().getProperty();
					IKesoComplexProperty parent = property.getParent();
					if (KesoPropertyManager.acceptsRemovalOfChild(parent, property.getName())) {
						MessageBox messagebox = new MessageBox(getShell(), 
								SWT.YES | SWT.NO | SWT.ICON_QUESTION);
						messagebox.setText("Delete Property?");
						messagebox.setMessage("Do you really want to delete property '" + 
								property.getName() + "'?");
						if (messagebox.open() == SWT.YES) {
							parent.remove(property);
						}
						//fill();
					} else {
						MessageBox messagebox = new MessageBox(getShell(), 
								SWT.OK | SWT.ICON_ERROR);
						messagebox.setText("Can not delete property");
						messagebox.setMessage(property.getName() + " can not be deleted!");
						messagebox.open();
					}
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		/*
		this.editname = new ToolItem(toolbar, SWT.PUSH);
		this.editname.setImage(KesoImageManager.getInstance().getImage("renameproperty"));
		this.editname.setEnabled(false);
		*/
		
		this.tree = new Tree(comp, this.style | SWT.SINGLE | SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.H_SCROLL);
		this.tree.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		this.tree.setLinesVisible(true);
		this.tree.setHeaderVisible(true);
		this.tree.addSelectionListener(this);
		
		this.propertyColumn = new TreeColumn(this.tree, SWT.NONE);
		this.propertyColumn.setText("Property");
		this.propertyColumn.setWidth(200);
		this.valueColumn = new TreeColumn(this.tree, SWT.NONE);
		this.valueColumn.setText("Value");
		this.valueColumn.setWidth(100);

		this.editor = new TreeEditor(this.tree);
		this.editor.grabHorizontal = true;
		this.editor.grabVertical = true;
		this.editor.minimumWidth = 100;
		
		
		folder = new CTabFolder(this.sashform, SWT.BORDER);
		citem = new CTabItem(folder, SWT.NONE);
		folder.setSelection(citem);
		citem.setText("Property Description");
		
		citem.setImage(KesoImageManager.getInstance().getImage("propertydescription"));
		
		comp = new Composite(folder, SWT.NONE);
		comp.setLayout(new FillLayout());
		citem.setControl(comp);
		
		this.description = new Label(comp, SWT.WRAP);
		this.description.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		this.sashform.setWeights(new int [] {8, 2});
	}
	
	public Shell getShell() {
		return this.tree.getShell();
	}
	
	/**
	 * @return  the selection
	 * @uml.property  name="selection"
	 */
	public KesoPropertyTreeItem getSelection() {
		return this.selection;
	}
	
	/**
	 * @param data  the data to set
	 * @uml.property  name="data"
	 */
	public void setData(IKesoData data) {
		if (this.data != null) {
			this.data.getPropertyContainer().removePropertyChangeListener(this);
		}
		this.data = data;
		this.description.setText("");
		if (this.editor.getEditor() != null) {
			this.editor.getEditor().dispose();
			editor.setEditor(null);
		}
		this.removeAll();
		if (this.data != null) {
			this.data.getPropertyContainer().addPropertyChangeListener(this);
		}
		this.fill();
		
		if (this.getData() != null) {
			this.newproperty.setEnabled(true);
		} else {
			this.newproperty.setEnabled(false);
			this.deleteproperty.setEnabled(false);
			//this.editname.setEnabled(false);
		}
		this.setSelection(null);
	}
	
	public void removeAll() {
		this.tree.removeAll();
	}
	
	public void fill() {
		if (this.editor.getEditor() != null) {
			this.editor.getEditor().dispose();
			editor.setEditor(null);
		}
		this.removeAll();
		if (this.data != null) {
			for (Iterator i = this.data.getPropertyContainer().getProperties().iterator(); i.hasNext(); ) {
				IKesoProperty property = (IKesoProperty) i.next();
				String name = property.getName();
				if (name!=null && name.startsWith("KGEData_")) {
					// ignore KGEData_
				} else {
					add(property);
				}
			}
		}
	}
	
	private void add(IKesoProperty property) {
		TreeItem item = new TreeItem(this.tree, SWT.NONE, this.getIndex(property));
		KesoPropertyTreeItem treeitem = new KesoPropertyTreeItem(item, property);
		treeitem.fill();
	}

	public int getIndex(IKesoProperty property) {
		for (int i = 0; i < this.tree.getItemCount(); i++) {
			TreeItem item = this.tree.getItem(i);
			if (item.getText(0).compareToIgnoreCase(property.getName()) > 0) {
				return i;
			}
		} 
		return this.tree.getItemCount();
	}
	
	public void propertyChange(PropertyChangeEvent e) {
		if (e.getPropertyName().equals("add property")) {
			IKesoProperty property = (IKesoProperty) e.getNewValue();
			this.add(property);
		} else if (e.getPropertyName().equals("remove property")) {
			IKesoProperty property = (IKesoProperty) e.getOldValue();
			
			for (int i = 0; i < this.tree.getItemCount(); i++) {
				KesoPropertyTreeItem child = (KesoPropertyTreeItem) this.tree.getItem(i).getData();
				if (child.getProperty() == property) {
					this.tree.getItem(i).dispose();
					break;
				}
			}
		} else if (e.getPropertyName().equals("global data changed")) {
			this.setData(null);
		}
	}
	
	/**
	 * @return  the data
	 * @uml.property  name="data"
	 */
	public IKesoData getData() {
		return this.data;
	}

	public void widgetSelected(SelectionEvent e) {
		if (this.editor.getEditor() != null) {
			this.editor.getEditor().dispose();
			editor.setEditor(null);
		}
		
		TreeItem item = ((TreeItem) e.item);
		KesoPropertyTreeItem propertyitem = (KesoPropertyTreeItem) item.getData();
		final IKesoProperty property = propertyitem.getProperty();
		
		this.setSelection(propertyitem);
		
		String description = KesoPropertyManager.getDescription(property);
		if (description != null && description.length() != 0) {
			this.description.setText(description);
		} else {
			this.description.setText("");
		}
		
		Vector possibleValues = KesoPropertyManager.getPossibleValues(property);
		
		if (false && property.getName().equals("Modules")) {
			possibleValues = new Vector();
			String workbench = KesoGuiProperties.getInstance().getProperty("workbench.directory") + "/libs/";
			File file = new File(workbench);
			if (file.exists() && file.isDirectory()) {
				String [] list = file.list();
				for (int i = 0; i < list.length; i++) {
					if (list[i].length() != 0 && 
							list[i].charAt(0) == '.') {
						continue;
					}
					String filename = workbench + list[i];
					File p = new File(filename);
					if (p.isDirectory()) {
						possibleValues.add(list[i]);
					}
				}
			}
		}
		
		final Vector fpossibleValues = possibleValues;
		
		if (false && (property.getName().equals("HandlerClass") ||
				property.getName().equals("ServiceClass") ||
				property.getName().equals("ServiceInterface") ||
				property.getName().equals("MainClass") ||
				property.getName().equals("Driver") ||
				property.getName().equals("Drivers"))) {
			
			Composite comp = new Composite(this.tree, SWT.NONE);
			GridLayout glayout = new GridLayout();
			glayout.numColumns = 2;
			glayout.marginBottom = 0;
			glayout.marginHeight = 0;
			glayout.marginLeft = 0;
			glayout.marginRight = 0;
			glayout.marginTop = 0;
			glayout.marginWidth = 0;
			comp.setLayout(glayout);
			comp.setBackground(this.tree.getBackground());
			
			final Text text = new Text(comp, SWT.NONE | SWT.READ_ONLY);
			text.setText(property.getValue());
			text.setBackground(this.tree.getBackground());
			text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			
			
			Button btn = new Button(comp, SWT.NONE);
			btn.setText("...");
			btn.setLayoutData(new GridData(GridData.FILL_VERTICAL));
			btn.setSize(40, 0);
			
		//	if (property.getName().equals("Access")) {
				btn.addSelectionListener(new SelectionListener() {

					public void widgetSelected(SelectionEvent e) {
						
						Vector parents = KesoFilterManager.get("filter_parent_node").filter(property.getOwner());
						if (parents != null && parents.size() != 0) {
							String [] modules = new String[0];
							IKesoData parent = (IKesoData) parents.get(0);
							Vector allmodules = new Vector();
							Vector vmodules = parent.getPropertyContainer().getPropertiesByName("Modules");
							if (vmodules != null && vmodules.size() != 0) {
								for (Iterator i = vmodules.iterator(); i.hasNext(); ) {
									IKesoProperty propmod = (IKesoProperty) i.next();
									String [] mods = propmod.getValue().split(":");
									for (int j = 0; j < mods.length; j++) {
										allmodules.add(mods[j].trim());
									}
								}
								
								modules = (String []) allmodules.toArray(new String[0]);
							}
							KesoPackageDialog dialog;
							if (property.getName().equals("Drivers")) {
								dialog = new KesoPackageDialog(getShell(), modules, true);
								String [] paths = property.getValue().split(":");
								dialog.setSelection(paths);
							} else {
								dialog = new KesoPackageDialog(getShell(), modules, false);
								dialog.setSelection(new String [] {property.getValue()});
							}

							if (dialog.open()) {
								String [] paths = dialog.getSelections();
								String newtext = "";
								for (int i = 0; i <paths.length; i++) {
									if (i == 0) {
										newtext += paths[i];
									} else {
										newtext += ":" + paths[i];
									}
								}
								text.setText(newtext);
								property.setValue(newtext);
								change.firePropertyChange("property changed", null, null);
							}
						}
					}

					public void widgetDefaultSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						
					}
					
				}); 
		
			editor.setEditor(comp, item, 1);
			
			
			
		} else if (possibleValues == null || possibleValues.size() == 0) {
			Text text = new Text(this.tree, SWT.NONE);
			text.setText(item.getText(1));
			text.selectAll();
			text.setFocus();
			text.addDisposeListener(new DisposeListener() {

				public void widgetDisposed(DisposeEvent e) {
					Text text = (Text) e.getSource();
					TreeItem item = (TreeItem) editor.getItem();
					if (!item.isDisposed()) {
						KesoPropertyTreeItem propertyitem = (KesoPropertyTreeItem) item.getData();
						IKesoProperty property = propertyitem.getProperty();
						property.setValue(text.getText());
						change.firePropertyChange("property changed", null, null);
					}
				}
			});
			editor.setEditor(text, item, 1);
		} else {
			if (property.getName().equals("Access")/* ||
					property.getName().equals("Modules") */) {
				Composite comp = new Composite(this.tree, SWT.NONE);
				GridLayout glayout = new GridLayout();
				glayout.numColumns = 2;
				glayout.marginBottom = 0;
				glayout.marginHeight = 0;
				glayout.marginLeft = 0;
				glayout.marginRight = 0;
				glayout.marginTop = 0;
				glayout.marginWidth = 0;
				comp.setLayout(glayout);
				comp.setBackground(this.tree.getBackground());
				
				final Text text = new Text(comp, SWT.NONE | SWT.READ_ONLY);
				text.setText(property.getValue());
				text.setBackground(this.tree.getBackground());
				text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				
				
				Button btn = new Button(comp, SWT.NONE);
				btn.setText("...");
				btn.setLayoutData(new GridData(GridData.FILL_VERTICAL));
				btn.setSize(40, 0);
				
			//	if (property.getName().equals("Access")) {
					btn.addSelectionListener(new SelectionListener() {
	
						public void widgetSelected(SelectionEvent e) {
							String [] possible = new String[fpossibleValues.size()];
							int ptr = 0;
							for (Iterator i = fpossibleValues.iterator(); i.hasNext(); ) {
								possible[ptr++] = i.next().toString();
							}
							String [] selection = property.getValue().split(":");
							KesoPropertySelectionDialog dialog = null;
							if (property.getOwner().getIdentifier().equals(KGE.IMPORT)) {
								dialog = new KesoPropertySelectionDialog(getShell(), false);
							} else {
								dialog = new KesoPropertySelectionDialog(getShell(), true);
							}
							dialog.setPossibleValues(possible);
							dialog.setSelection(selection);
							if (dialog.open()) {
								selection = dialog.getSelection();
								String newtext = "";
								for (int i = 0; i < selection.length; i++) {
									if (i == 0) {
										newtext += selection[i];
										if (property.getOwner().getIdentifier().equals(KGE.IMPORT)) {
											break;
										}
									} else {
										newtext += ":" + selection[i];
									}
								}
								text.setText(newtext);
								property.setValue(newtext);
								change.firePropertyChange("property changed", null, null);
							}
							
						}
	
						public void widgetDefaultSelected(SelectionEvent e) {
							// TODO Auto-generated method stub
							
						}
						
					}); 
			
				editor.setEditor(comp, item, 1);
				
			} else {
				Vector values = KesoPropertyManager.getPossibleValues(property);
				Combo cb = new Combo(this.tree, SWT.NONE | SWT.DROP_DOWN);
				for (Iterator i = values.iterator(); i.hasNext(); ) {
					cb.add(i.next().toString());
				}
				cb.setText(property.getValue());
				cb.setFocus();
				cb.addSelectionListener(new SelectionListener()  {
					public void widgetSelected(SelectionEvent e) {
						Combo cb = (Combo) e.getSource();
						TreeItem item = (TreeItem) editor.getItem();
						KesoPropertyTreeItem propertyitem = (KesoPropertyTreeItem) item.getData();
						IKesoProperty property = propertyitem.getProperty();
						property.setValue(cb.getText());
					}
	
					public void widgetDefaultSelected(SelectionEvent e) {
						widgetSelected(e);	
					}
				});
				
				cb.addDisposeListener(new DisposeListener() {
					public void widgetDisposed(DisposeEvent e) {
						Combo cb = (Combo) e.getSource();
						TreeItem item = (TreeItem) editor.getItem();
						if (!item.isDisposed()) {
							KesoPropertyTreeItem propertyitem = (KesoPropertyTreeItem) item.getData();
							IKesoProperty property = propertyitem.getProperty();
							property.setValue(cb.getText());
							change.firePropertyChange("property changed", null, null);
						}
					}
				});
				editor.setEditor(cb, item, 1);
			}
		}
	}

	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void controlMoved(ControlEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void controlResized(ControlEvent e) {
		this.sashform.setBounds(1, 1, this.parent.getSize().x - 2, this.parent.getSize().y - 2);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.change.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.change.removePropertyChangeListener(listener);
	}

	/**
	 * @param selection  the selection to set
	 * @uml.property  name="selection"
	 */
	public void setSelection(KesoPropertyTreeItem property) {
		this.selection = property;
		
		if (this.selection != null) {
			this.deleteproperty.setEnabled(true);
			//this.editname.setEnabled(true);
		} else {
			this.deleteproperty.setEnabled(false);
			//this.editname.setEnabled(false);		
		}
	}
}
