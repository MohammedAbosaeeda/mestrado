package keso.editor.gui.core.menu;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import keso.editor.KGE;
import keso.editor.data.IKesoData;
import keso.editor.data.KesoData;
import keso.editor.gui.core.KesoImageManager;
import keso.editor.gui.core.KesoMainWindow;
import keso.editor.gui.tool.AddAlarmKesoTool;
import keso.editor.gui.tool.AddAppmodeKesoTool;
import keso.editor.gui.tool.AddCounterKesoTool;
import keso.editor.gui.tool.AddDomainKesoTool;
import keso.editor.gui.tool.AddEventKesoTool;
import keso.editor.gui.tool.AddImportKesoTool;
import keso.editor.gui.tool.AddIsrKesoTool;
import keso.editor.gui.tool.AddNetworkKesoTool;
import keso.editor.gui.tool.AddNodeKesoTool;
import keso.editor.gui.tool.AddOsekOsKesoTool;
import keso.editor.gui.tool.AddPublicDomainKesoTool;
import keso.editor.gui.tool.AddResourceKesoTool;
import keso.editor.gui.tool.AddServiceKesoTool;
import keso.editor.gui.tool.AddTaskKesoTool;
import keso.editor.gui.tool.IKesoTool;
import keso.editor.gui.tool.SelectionKesoTool;
import keso.editor.gui.tool.ZoomInKesoTool;
import keso.editor.gui.tool.ZoomOutKesoTool;
import keso.editor.property.propertymanager.KesoPropertyManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ToolItem;

/**
 * @author  Wilhelm Haas
 */
public class KesoToolsMenu implements SelectionListener, PropertyChangeListener {
	KesoMainWindow window;
	
	MenuItem selectionitem;
	MenuItem zoominitem;
	MenuItem zoomoutitem;
	MenuItem nodeitem;
	MenuItem networkitem;
	MenuItem taskitem;
	MenuItem domainitem;
	MenuItem publicdomainitem;
	MenuItem importitem;
	MenuItem serviceitem;
	MenuItem resourceitem;
	MenuItem appmodeitem;
	MenuItem eventitem;
	MenuItem interruptitem;
	MenuItem counteritem;
	MenuItem alarmitem;
	MenuItem osekositem;
	
	
	public KesoToolsMenu(KesoMainWindow window) {
		this.window = window;
		this.window.addPropertyChangeListener(this);
		this.window.getToolManager().addPropertyChangeListener(this);
		
		Menu menu;
		MenuItem menuitem;
		menuitem = new MenuItem(this.window.getMenuBar(), SWT.CASCADE);
		menuitem.setText("&Tools");
		
		menu = new Menu(window.getShell(), SWT.DROP_DOWN);
		menuitem.setMenu(menu);
		
		
		
		this.selectionitem = new MenuItem(menu, SWT.RADIO);
		this.selectionitem.setText("Selection\tCTRL + SHIFT + A");
		this.selectionitem.setAccelerator(SWT.CTRL | SWT.SHIFT | 'A');
		this.selectionitem.setImage(KesoImageManager.getInstance().getImage("selectiontool"));
		this.selectionitem.addSelectionListener(this);
		
		new MenuItem(menu, SWT.SEPARATOR);
		
		this.zoominitem = new MenuItem(menu, SWT.RADIO);
		this.zoominitem.setText("Zoom in \tCTRL + SHIFT + '+'");
		this.zoominitem.setAccelerator(SWT.CTRL | SWT.SHIFT | '+');
		this.zoominitem.setImage(KesoImageManager.getInstance().getImage("zoomin"));
		this.zoominitem.addSelectionListener(this);
		
		this.zoomoutitem = new MenuItem(menu, SWT.RADIO);
		this.zoomoutitem.setText("Zoom out\tCTRL + SHIFT + '-'");
		this.zoomoutitem.setAccelerator(SWT.CTRL | SWT.SHIFT | '-');
		this.zoomoutitem.setImage(KesoImageManager.getInstance().getImage("zoomout"));
		this.zoomoutitem.addSelectionListener(this);
		
		new MenuItem(menu, SWT.SEPARATOR);
		
		
		this.nodeitem = new MenuItem(menu, SWT.RADIO);
		this.nodeitem.setText("Node\tCTRL + SHIFT + Q");
		this.nodeitem.setAccelerator(SWT.CTRL | SWT.SHIFT | 'Q');
		this.nodeitem.setImage(KesoImageManager.getInstance().getImage("nodetool"));
		this.nodeitem.addSelectionListener(this);
		
		this.networkitem = new MenuItem(menu, SWT.RADIO);
		this.networkitem.setText("Network\tCTRL + SHIFT + W");
		this.networkitem.setAccelerator(SWT.CTRL | SWT.SHIFT | 'W');
		this.networkitem.setImage(KesoImageManager.getInstance().getImage("networktool"));
		this.networkitem.addSelectionListener(this);
		
		this.taskitem = new MenuItem(menu, SWT.RADIO);
		this.taskitem.setText("Task\tCTRL + SHIFT + S");
		this.taskitem.setAccelerator(SWT.CTRL | SWT.SHIFT | 'S');
		this.taskitem.setImage(KesoImageManager.getInstance().getImage("tasktool"));
		this.taskitem.addSelectionListener(this);
		
		this.domainitem = new MenuItem(menu, SWT.RADIO);
		this.domainitem.setText("Domain\tCTRL + SHIFT + Y");
		this.domainitem.setAccelerator(SWT.CTRL | SWT.SHIFT | 'Y');
		this.domainitem.setImage(KesoImageManager.getInstance().getImage("domaintool"));
		this.domainitem.addSelectionListener(this);
		
		this.publicdomainitem = new MenuItem(menu, SWT.RADIO);
		this.publicdomainitem.setText("Public Domain\tCTRL + SHIFT + X");
		this.publicdomainitem.setAccelerator(SWT.CTRL | SWT.SHIFT | 'X');
		this.publicdomainitem.setImage(KesoImageManager.getInstance().getImage("publicdomaintool"));
		this.publicdomainitem.addSelectionListener(this);
		
		this.importitem = new MenuItem(menu, SWT.RADIO);
		this.importitem.setText("Import\tCTRL + SHIFT + E");
		this.importitem.setAccelerator(SWT.CTRL | SWT.SHIFT | 'E');
		this.importitem.setImage(KesoImageManager.getInstance().getImage("importtool"));
		this.importitem.addSelectionListener(this);
		
		this.serviceitem = new MenuItem(menu, SWT.RADIO);
		this.serviceitem.setText("Service\tCTRL + SHIFT + D");
		this.serviceitem.setAccelerator(SWT.CTRL | SWT.SHIFT | 'D');
		this.serviceitem.setImage(KesoImageManager.getInstance().getImage("servicetool"));
		this.serviceitem.addSelectionListener(this);
		
		this.resourceitem = new MenuItem(menu, SWT.RADIO);
		this.resourceitem.setText("Resource\tCTRL + SHIFT + C");
		this.resourceitem.setAccelerator(SWT.CTRL | SWT.SHIFT | 'C');
		this.resourceitem.setImage(KesoImageManager.getInstance().getImage("resourcetool"));
		this.resourceitem.addSelectionListener(this);
		
		this.appmodeitem = new MenuItem(menu, SWT.RADIO);
		this.appmodeitem.setText("Application Mode\tCTRL + SHIFT + R");
		this.appmodeitem.setAccelerator(SWT.CTRL | SWT.SHIFT | 'R');
		this.appmodeitem.setImage(KesoImageManager.getInstance().getImage("appmodetool"));
		this.appmodeitem.addSelectionListener(this);
		
		this.eventitem = new MenuItem(menu, SWT.RADIO);
		this.eventitem.setText("Event\tCTRL + SHIFT + F");
		this.eventitem.setAccelerator(SWT.CTRL | SWT.SHIFT | 'F');
		this.eventitem.setImage(KesoImageManager.getInstance().getImage("eventtool"));
		this.eventitem.addSelectionListener(this);
		
		this.interruptitem = new MenuItem(menu, SWT.RADIO);
		this.interruptitem.setText("Interrupt service routine\tCTRL + SHIFT + V");
		this.interruptitem.setAccelerator(SWT.CTRL | SWT.SHIFT | 'V');
		this.interruptitem.setImage(KesoImageManager.getInstance().getImage("isrtool"));
		this.interruptitem.addSelectionListener(this);
		
		this.counteritem = new MenuItem(menu, SWT.RADIO);
		this.counteritem.setText("Counter\tCTRL + SHIFT + T");
		this.counteritem.setAccelerator(SWT.CTRL | SWT.SHIFT | 'T');
		this.counteritem.setImage(KesoImageManager.getInstance().getImage("countertool"));
		this.counteritem.addSelectionListener(this);
		
		this.alarmitem = new MenuItem(menu, SWT.RADIO);
		this.alarmitem.setText("Alarm\tCTRL + SHIFT + G");
		this.alarmitem.setAccelerator(SWT.CTRL | SWT.SHIFT | 'G');
		this.alarmitem.setImage(KesoImageManager.getInstance().getImage("alarmtool"));
		this.alarmitem.addSelectionListener(this);
		
		this.osekositem = new MenuItem(menu, SWT.RADIO);
		this.osekositem.setText("Osek OS Configuration\tCTRL + SHIFT + B");
		this.osekositem.setAccelerator(SWT.CTRL | SWT.SHIFT | 'B');
		this.osekositem.setImage(KesoImageManager.getInstance().getImage("ostool"));
		this.osekositem.addSelectionListener(this);
	}

	
	private void deselectAll() {
		this.selectionitem.setSelection(false);
		this.zoominitem.setSelection(false);
		this.zoomoutitem.setSelection(false);
		this.nodeitem.setSelection(false);
		this.networkitem.setSelection(false);
		this.taskitem.setSelection(false);
		this.domainitem.setSelection(false);
		this.publicdomainitem.setSelection(false);
		this.importitem.setSelection(false);
		this.serviceitem.setSelection(false);
		this.resourceitem.setSelection(false);
		this.appmodeitem.setSelection(false);
		this.eventitem.setSelection(false);
		this.interruptitem.setSelection(false);
		this.counteritem.setSelection(false);
		this.alarmitem.setSelection(false);
		this.osekositem.setSelection(false);
	}

	public void widgetSelected(SelectionEvent e) {
		MenuItem item = (MenuItem) e.widget;
		if (item.getSelection()) {
			if (item == this.selectionitem) {
				this.window.getToolManager().setTool(new SelectionKesoTool());
			} else if (item == this.zoominitem) {
				this.window.getToolManager().setTool(new ZoomInKesoTool());
			} else if (item == this.zoomoutitem) {
				this.window.getToolManager().setTool(new ZoomOutKesoTool());
			} else if (item == this.nodeitem) {
				this.window.getToolManager().setTool(new AddNodeKesoTool());
			} else if (item == this.networkitem) {
				this.window.getToolManager().setTool(new AddNetworkKesoTool());
			} else if (item == this.taskitem) {
				this.window.getToolManager().setTool(new AddTaskKesoTool());
			} else if (item == this.domainitem) {
				this.window.getToolManager().setTool(new AddDomainKesoTool());
			} else if (item == this.publicdomainitem) {
				this.window.getToolManager().setTool(new AddPublicDomainKesoTool());
			} else if (item == this.importitem) {
				this.window.getToolManager().setTool(new AddImportKesoTool());
			} else if (item == this.serviceitem) {
				this.window.getToolManager().setTool(new AddServiceKesoTool());
			} else if (item == this.resourceitem) {
				this.window.getToolManager().setTool(new AddResourceKesoTool());
			} else if (item == this.appmodeitem) {
				this.window.getToolManager().setTool(new AddAppmodeKesoTool());
			} else if (item == this.eventitem) {
				this.window.getToolManager().setTool(new AddEventKesoTool());
			} else if (item == this.interruptitem) {
				this.window.getToolManager().setTool(new AddIsrKesoTool());
			} else if (item == this.counteritem) {
				this.window.getToolManager().setTool(new AddCounterKesoTool());
			} else if (item == this.alarmitem) {
				this.window.getToolManager().setTool(new AddAlarmKesoTool());
			} else if (item == this.osekositem) {
				this.window.getToolManager().setTool(new AddOsekOsKesoTool());
			}
		}
	}


	public void widgetDefaultSelected(SelectionEvent e) {
		
	}

	private void reset() {
		this.selectionitem.setSelection(true);
		this.zoominitem.setSelection(false);
		this.zoomoutitem.setSelection(false);
		this.nodeitem.setSelection(false);
		this.networkitem.setSelection(false);
		this.taskitem.setSelection(false);
		this.domainitem.setSelection(false);
		this.publicdomainitem.setSelection(false);
		this.importitem.setSelection(false);
		this.serviceitem.setSelection(false);
		this.resourceitem.setSelection(false);
		this.appmodeitem.setSelection(false);
		this.eventitem.setSelection(false);
		this.interruptitem.setSelection(false);
		this.counteritem.setSelection(false);
		this.alarmitem.setSelection(false);
		this.osekositem.setSelection(false);
	}
	

	public void propertyChange(PropertyChangeEvent e) {
		if (e.getPropertyName().equals("tool changed")) {
			IKesoTool tool = this.window.getToolManager().getTool();
			this.deselectAll();
			if (tool instanceof SelectionKesoTool) {
				if (!this.selectionitem.getSelection()) {
					this.selectionitem.setSelection(true);
				}
			} else if (tool instanceof ZoomInKesoTool) {
				if (!this.zoominitem.getSelection()) {
					this.zoominitem.setSelection(true);
				}
			} else if (tool instanceof ZoomOutKesoTool) {
				if (!this.zoomoutitem.getSelection()) {
					this.zoomoutitem.setSelection(true);
				}
			} else if (tool instanceof AddNodeKesoTool) {
				if (!this.nodeitem.getSelection()) {
					this.nodeitem.setSelection(true);
				}
			} else if (tool instanceof AddNetworkKesoTool) {
				if (!this.networkitem.getSelection()) {
					this.networkitem.setSelection(true);
				}
			} else if (tool instanceof AddTaskKesoTool) {
				if (!this.taskitem.getSelection()) {
					this.taskitem.setSelection(true);
				}
			} else if (tool instanceof AddDomainKesoTool) {
				if (!this.domainitem.getSelection()) {
					this.domainitem.setSelection(true);
				}
			} else if (tool instanceof AddPublicDomainKesoTool) {
				if (!this.publicdomainitem.getSelection()) {
					this.publicdomainitem.setSelection(true);
				}
			} else if (tool instanceof AddServiceKesoTool) {
				if (!this.serviceitem.getSelection()) {
					this.serviceitem.setSelection(true);
				}
			} else if (tool instanceof AddImportKesoTool) {
				if (!this.importitem.getSelection()) {
					this.importitem.setSelection(true);
				}
			} else if (tool instanceof AddCounterKesoTool) {
				if (!this.counteritem.getSelection()) {
					this.counteritem.setSelection(true);
				}
			} else if (tool instanceof AddAlarmKesoTool) {
				if (!this.alarmitem.getSelection()) {
					this.alarmitem.setSelection(true);
				}
			} else if (tool instanceof AddEventKesoTool) {
				if (!this.eventitem.getSelection()) {
					this.eventitem.setSelection(true);
				}
			} else if (tool instanceof AddIsrKesoTool) {
				if (!this.interruptitem.getSelection()) {
					this.interruptitem.setSelection(true);
				}
			} else if (tool instanceof AddOsekOsKesoTool) {
				if (!this.osekositem.getSelection()) {
					this.osekositem.setSelection(true);
				}
			} else if (tool instanceof AddAppmodeKesoTool) {
				if (!this.appmodeitem.getSelection()) {
					this.appmodeitem.setSelection(true);
				}
			} else if (tool instanceof AddResourceKesoTool) {
				if (!this.resourceitem.getSelection()) {
					this.resourceitem.setSelection(true);
				}
			}
		} else if (e.getPropertyName().equals("global data changed")) {
			if (this.window.getData() == null) {
				this.deselectAll();
				this.selectionitem.setEnabled(false);
				this.zoominitem.setEnabled(false);
				this.zoomoutitem.setEnabled(false);
				this.nodeitem.setEnabled(false);
				this.networkitem.setEnabled(false);
				this.taskitem.setEnabled(false);
				this.domainitem.setEnabled(false);
				this.publicdomainitem.setEnabled(false);
				this.importitem.setEnabled(false);
				this.serviceitem.setEnabled(false);
				this.resourceitem.setEnabled(false);
				this.appmodeitem.setEnabled(false);
				this.eventitem.setEnabled(false);
				this.interruptitem.setEnabled(false);
				this.counteritem.setEnabled(false);
				this.alarmitem.setEnabled(false);
				this.osekositem.setEnabled(false);
			} else {
				this.reset();
				this.selectionitem.setEnabled(true);
				this.zoominitem.setEnabled(true);
				this.zoomoutitem.setEnabled(true);
				this.nodeitem.setEnabled(true);
				this.networkitem.setEnabled(true);
				this.taskitem.setEnabled(true);
				this.domainitem.setEnabled(true);
				this.publicdomainitem.setEnabled(true);
				this.importitem.setEnabled(true);
				this.serviceitem.setEnabled(true);
				this.resourceitem.setEnabled(true);
				this.appmodeitem.setEnabled(true);
				this.eventitem.setEnabled(true);
				this.interruptitem.setEnabled(true);
				this.counteritem.setEnabled(true);
				this.alarmitem.setEnabled(true);
				this.osekositem.setEnabled(true);
			}
		}
		
	}

}
