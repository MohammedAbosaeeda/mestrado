package keso.editor.gui.core.menu;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Vector;

import keso.editor.KGE;
import keso.editor.data.IKesoData;
import keso.editor.data.KesoData;
import keso.editor.gui.KesoGuiProperties;
import keso.editor.gui.core.KesoImageManager;
import keso.editor.gui.core.KesoMainWindow;
import keso.editor.gui.core.KesoPropertyDialog;
import keso.editor.property.propertymanager.KesoPropertyManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MenuListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

/**
 * @author  Wilhelm Haas
 */
public class KesoFileMenu implements SelectionListener, PropertyChangeListener, MenuListener {
	KesoMainWindow window;
	
	MenuItem newitem;
	MenuItem openitem;
	MenuItem openrecent;
	Menu menuOpenRecent;
	MenuItem closeitem;
	MenuItem saveitem;
	MenuItem saveasitem;
	MenuItem printitem;
	MenuItem printpreviewitem;
	MenuItem exportitem;
	MenuItem propertiesitem;
	MenuItem exititem;
	MenuItem switchworkbench;
	
	
	public KesoFileMenu(KesoMainWindow window) {
		this.window = window;
		this.window.addPropertyChangeListener(this);
		
		Menu menu;
		MenuItem menuitem;
		menuitem = new MenuItem(this.window.getMenuBar(), SWT.CASCADE);
		menuitem.setText("&File");
		
		menu = new Menu(window.getShell(), SWT.DROP_DOWN);
		menuitem.setMenu(menu);
		
		this.newitem = new MenuItem(menu, SWT.PUSH);
		this.newitem.setText("&New\tCTRL+N");
		this.newitem.setAccelerator(SWT.CTRL | 'N');
		this.newitem.setImage(KesoImageManager.getInstance().getImage("new"));
		this.newitem.addSelectionListener(this);
		
		this.openitem = new MenuItem(menu, SWT.PUSH);
		this.openitem.setText("&Open...\tCTRL+O");
		this.openitem.setAccelerator(SWT.CTRL | 'O');
		this.openitem.setImage(KesoImageManager.getInstance().getImage("open"));
		this.openitem.addSelectionListener(this);
		
		new MenuItem(menu, SWT.SEPARATOR);
		
		this.openrecent = new MenuItem(menu, SWT.CASCADE);
		this.openrecent.setText("Open Recent...");
		this.openrecent.addSelectionListener(this);
		
		this.menuOpenRecent = new Menu(window.getShell(), SWT.DROP_DOWN);
		this.menuOpenRecent.addMenuListener(this);
		this.openrecent.setMenu(this.menuOpenRecent);
		
		
		new MenuItem(menu, SWT.SEPARATOR);
		
		this.closeitem = new MenuItem(menu, SWT.PUSH);
		this.closeitem.setText("&Close\tCTRL+W");
		this.closeitem.setAccelerator(SWT.CTRL | 'W');
		this.closeitem.addSelectionListener(this);
		
		new MenuItem(menu, SWT.SEPARATOR);
		
		this.saveitem = new MenuItem(menu, SWT.PUSH);
		this.saveitem.setText("&Save\tCTRL+S");
		this.saveitem.setAccelerator(SWT.CTRL | 'S');
		this.saveitem.setImage(KesoImageManager.getInstance().getImage("save"));
		this.saveitem.addSelectionListener(this);
		
		this.saveasitem = new MenuItem(menu, SWT.PUSH);
		this.saveasitem.setText("&Save As...");
		this.saveasitem.setImage(KesoImageManager.getInstance().getImage("saveas"));
		this.saveasitem.addSelectionListener(this);
		
		
		new MenuItem(menu, SWT.SEPARATOR);
		
		this.exportitem = new MenuItem(menu, SWT.PUSH);
		this.exportitem.setText("Export...");
		this.exportitem.setImage(KesoImageManager.getInstance().getImage("export"));
		this.exportitem.addSelectionListener(this);
		
		new MenuItem(menu, SWT.SEPARATOR);
		
		/*
		this.printpreviewitem = new MenuItem(menu, SWT.PUSH);
		this.printpreviewitem.setText("Print Preview");
		this.printpreviewitem.addSelectionListener(this);
		*/
		
		this.printitem = new MenuItem(menu, SWT.PUSH);
		this.printitem.setText("&Print...\tCTRL+P");
		this.printitem.setAccelerator(SWT.CTRL | 'P');
		this.printitem.setImage(KesoImageManager.getInstance().getImage("print"));
		this.printitem.addSelectionListener(this);
		
		new MenuItem(menu, SWT.SEPARATOR);
		
		this.switchworkbench = new MenuItem(menu, SWT.PUSH);
		this.switchworkbench.setText("Switch Workbench...");
		this.switchworkbench.addSelectionListener(this);
		
		new MenuItem(menu, SWT.SEPARATOR);
		
		this.propertiesitem = new MenuItem(menu, SWT.PUSH);
		this.propertiesitem.setText("Properties");
		this.propertiesitem.setImage(KesoImageManager.getInstance().getImage("propertygrid"));
		this.propertiesitem.addSelectionListener(this);
		
		new MenuItem(menu, SWT.SEPARATOR);
		
		this.exititem = new MenuItem(menu, SWT.PUSH);
		this.exititem.setText("Exit");
		this.exititem.addSelectionListener(this);
		
		
		
		
	}


	public void widgetSelected(SelectionEvent e) {
		MenuItem item = (MenuItem) e.widget;
		if (item == this.newitem) {
			this.window.newConfiguration();
		} else if (item == this.openitem) {
			this.window.openFile();
		} else if (item == this.saveitem) {
			this.window.save();
		} else if (item == this.saveasitem) {
			this.window.saveAs();
		} else if (item == this.exportitem) {
			this.window.export();
		} else if (item == this.closeitem) {
			this.window.close();
		} else if (item == this.printitem) {
			this.window.print();
			
		}/* else if (item == this.printpreviewitem) {
			
		}*/ else if (item == this.propertiesitem) {
			this.window.showProperties();
		} else if (item == this.exititem) {
			this.window.exit();
		} else if (item == this.switchworkbench) {
			this.window.setWorkbench(false);
		} else {
			
		}
	}


	public void widgetDefaultSelected(SelectionEvent e) {
		
	}


	public void propertyChange(PropertyChangeEvent e) {
		
		if (this.window.getData() == null) {
			this.saveitem.setEnabled(false);
			this.saveasitem.setEnabled(false);
			this.closeitem.setEnabled(false);
			this.saveitem.setEnabled(false);
			this.exportitem.setEnabled(false);
			//this.printpreviewitem.setEnabled(false);
			this.printitem.setEnabled(false);
		} else {
			this.closeitem.setEnabled(true);
			this.exportitem.setEnabled(true);
			//this.printpreviewitem.setEnabled(true);
			this.printitem.setEnabled(true);
			this.saveasitem.setEnabled(true);
		
			if (this.window.isDataChanged()) {
				if (this.window.getExceptionList().size() != 0) {
					this.saveitem.setEnabled(false);
					this.saveasitem.setEnabled(false);
				} else {
					this.saveitem.setEnabled(true);
				}
			} else {
				if (this.window.getExceptionList().size() != 0) {
					this.saveasitem.setEnabled(false);
				} else {
					this.saveasitem.setEnabled(true);
				}
				this.saveitem.setEnabled(false);
			}
		}
		
		if (System.getProperty("os.name").toLowerCase().indexOf("windows") == -1) {
			this.printitem.setEnabled(false);
		}
	}


	public void menuHidden(MenuEvent e) {
	}


	public void menuShown(MenuEvent e) {
		if (e.widget == this.menuOpenRecent) {
			if (e.widget == this.menuOpenRecent) {
				MenuItem [] items = this.menuOpenRecent.getItems();
				for (int i = 0; i < items.length; i++) {
					if (!items[i].isDisposed()) {
						items[i].dispose();
					}
				}
			}
			
			Menu recent = (Menu) e.widget;
			int i = 1;
			while (true) {
				String filename = KesoGuiProperties.getInstance().getProperty("lastopenfile." + (i++));
				if (filename != null) {
					MenuItem item = new MenuItem(recent, SWT.PUSH);
					item.setData(filename);
					item.setText((i - 1) + ". " + filename);
					item.addSelectionListener(new SelectionListener() {

						public void widgetDefaultSelected(SelectionEvent e) {
							// TODO Auto-generated method stub
							
						}

						public void widgetSelected(SelectionEvent e) {
							MenuItem item = (MenuItem) e.widget;
							String filename = (String) item.getData();
							if (filename != null) {

								if (window.canProceed() && !window.openFile(filename)) {
									Vector lastopen = new Vector();
									KesoGuiProperties properties = KesoGuiProperties.getInstance();
									for (int i = 1; i <= 4; i++) {
										String tmp = properties.getProperty("lastopenfile." + i);
										if (tmp != null && !tmp.equals(filename)) {
											lastopen.add(tmp);
										}
									}
	
									for (int i = 0; i < lastopen.size(); i++) {
										properties.setProperty("lastopenfile." + (i + 1), (String) lastopen.get(i));
									}
								}
							}
						}
						
					});
				} else {
					break;
				}
			}
		}
	}

}
