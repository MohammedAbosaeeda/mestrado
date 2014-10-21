package keso.editor.gui.core.toolbar;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;
import java.util.Vector;

import keso.editor.gui.core.KesoImageManager;
import keso.editor.gui.core.KesoMainWindow;
import keso.editor.gui.tool.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

/**
 * @author  Wilhelm Haas
 */
public class KesoToolsToolBar implements SelectionListener, PropertyChangeListener, ModifyListener{

	private KesoMainWindow window;
	
	
	Vector zoomvalues = new Vector();

	ToolItem selectionitem;
	ToolItem zoomin;
	ToolItem zoomout;
	ToolItem resetzoom;
	ToolItem onetotwo;
	ToolItem nodeitem;
	ToolItem networkitem;
	ToolItem taskitem;
	ToolItem domainitem;
	ToolItem publicdomainitem;
	ToolItem importitem;
	ToolItem serviceitem;
	ToolItem resourceitem;
	ToolItem appmodeitem;
	ToolItem eventitem;
	ToolItem interruptitem;
	ToolItem counteritem;
	ToolItem alarmitem;
	ToolItem osekositem;
	
	Combo zoom;
	
	public KesoToolsToolBar(KesoMainWindow window) {
		this.window = window;
		
		this.window.addPropertyChangeListener(this);
		this.window.getToolManager().addPropertyChangeListener(this);
		
		ToolBar toolbar = new ToolBar(this.window.getToolBar(), SWT.FLAT | SWT.HORIZONTAL);
		ToolItem item;
		toolbar.setFocus();
		
		this.zoomvalues.add(new Integer(60));
		this.zoomvalues.add(new Integer(80));
		this.zoomvalues.add(new Integer(100));
		this.zoomvalues.add(new Integer(120));
		this.zoomvalues.add(new Integer(150));
		this.zoomvalues.add(new Integer(200));
		this.zoomvalues.add(new Integer(250));
		this.zoomvalues.add(new Integer(300));
		
		selectionitem = new ToolItem(toolbar, SWT.RADIO);
		selectionitem.setImage(KesoImageManager.getInstance().getImage("selectiontool"));
		selectionitem.setToolTipText("Selection Tool");
		selectionitem.addSelectionListener(this);
		
		new ToolItem(toolbar, SWT.SEPARATOR);
		
		zoomin = new ToolItem(toolbar, SWT.RADIO);
		zoomin.setImage(KesoImageManager.getInstance().getImage("zoomin"));
		zoomin.setToolTipText("Zoom In");
		zoomin.addSelectionListener(this);
		
		zoomout = new ToolItem(toolbar, SWT.RADIO);
		zoomout.setImage(KesoImageManager.getInstance().getImage("zoomout"));
		zoomout.setToolTipText("Zoom Out");
		zoomout.addSelectionListener(this);
		
		ToolItem seperator = new ToolItem(toolbar, SWT.SEPARATOR);
		
		zoom = new Combo(toolbar, SWT.NONE);
		for (Iterator i = zoomvalues.iterator(); i.hasNext(); ) {
			zoom.add(((Integer) i.next()).toString() + " %");
		}
		zoom.pack();
		zoom.addSelectionListener(this);
		zoom.setText("100 %");
		zoom.addModifyListener(this);
		
		seperator.setWidth(zoom.getSize().x);
	    seperator.setControl(zoom);
		
	    /*
		
		resetzoom = new ToolItem(toolbar, SWT.PUSH);
		resetzoom.setImage(KesoImageManager.getInstance().getImage("onetoone"));
		resetzoom.setToolTipText("Reset Zoom");
		resetzoom.addSelectionListener(this);
		
		onetotwo = new ToolItem(toolbar, SWT.PUSH);
		onetotwo.setImage(KesoImageManager.getInstance().getImage("onetotwo"));
		onetotwo.setToolTipText("Zoom 150 %");
		onetotwo.addSelectionListener(this);
		
		*/
		
		new ToolItem(toolbar, SWT.SEPARATOR);
		
		nodeitem = new ToolItem(toolbar, SWT.RADIO);
		nodeitem.setImage(KesoImageManager.getInstance().getImage("nodetool"));
		nodeitem.setToolTipText("Create new node");
		nodeitem.addSelectionListener(this);
		
		networkitem = new ToolItem(toolbar, SWT.RADIO);
		networkitem.setImage(KesoImageManager.getInstance().getImage("networktool"));
		networkitem.setToolTipText("Create new network");
		networkitem.addSelectionListener(this);
		
		
		domainitem = new ToolItem(toolbar, SWT.RADIO);
		domainitem.setImage(KesoImageManager.getInstance().getImage("domaintool"));
		domainitem.setToolTipText("Create new domain");
		domainitem.addSelectionListener(this);
		
		publicdomainitem = new ToolItem(toolbar, SWT.RADIO);
		publicdomainitem.setImage(KesoImageManager.getInstance().getImage("publicdomaintool"));
		publicdomainitem.setToolTipText("Create new public domain");
		publicdomainitem.addSelectionListener(this);
		
		
		taskitem = new ToolItem(toolbar, SWT.RADIO);
		taskitem.setImage(KesoImageManager.getInstance().getImage("tasktool"));
		taskitem.setToolTipText("Create new task");
		taskitem.addSelectionListener(this);
		
		serviceitem = new ToolItem(toolbar, SWT.RADIO);
		serviceitem.setImage(KesoImageManager.getInstance().getImage("servicetool"));
		serviceitem.setToolTipText("Create new service");
		serviceitem.addSelectionListener(this);
		
		
		
		importitem = new ToolItem(toolbar, SWT.RADIO);
		importitem.setImage(KesoImageManager.getInstance().getImage("importtool"));
		importitem.setToolTipText("Create new import");
		importitem.addSelectionListener(this);
		
		
		alarmitem = new ToolItem(toolbar, SWT.RADIO);
		alarmitem.setImage(KesoImageManager.getInstance().getImage("alarmtool"));
		alarmitem.setToolTipText("Create new alarm");
		alarmitem.addSelectionListener(this);
		
		resourceitem = new ToolItem(toolbar, SWT.RADIO);
		resourceitem.setImage(KesoImageManager.getInstance().getImage("resourcetool"));
		resourceitem.setToolTipText("Create new resource");
		resourceitem.addSelectionListener(this);
		
		appmodeitem = new ToolItem(toolbar, SWT.RADIO);
		appmodeitem.setImage(KesoImageManager.getInstance().getImage("appmodetool"));
		appmodeitem.setToolTipText("Create new application mode");
		appmodeitem.addSelectionListener(this);
		
		eventitem = new ToolItem(toolbar, SWT.RADIO);
		eventitem.setImage(KesoImageManager.getInstance().getImage("eventtool"));
		eventitem.setToolTipText("Create new event");
		eventitem.addSelectionListener(this);
		
		interruptitem = new ToolItem(toolbar, SWT.RADIO);
		interruptitem.setImage(KesoImageManager.getInstance().getImage("isrtool"));
		interruptitem.setToolTipText("Create new interrupt service routine");
		interruptitem.addSelectionListener(this);
		
		
		counteritem = new ToolItem(toolbar, SWT.RADIO);
		counteritem.setImage(KesoImageManager.getInstance().getImage("countertool"));
		counteritem.setToolTipText("Create new counter");
		counteritem.addSelectionListener(this);
		
		
		osekositem = new ToolItem(toolbar, SWT.RADIO);
		osekositem.setImage(KesoImageManager.getInstance().getImage("ostool"));
		osekositem.setToolTipText("Create new osek os configuration");
		osekositem.addSelectionListener(this);
		
		CoolItem coolitem = new CoolItem(this.window.getToolBar(), SWT.NONE);
		coolitem.setControl(toolbar);
		
		Point size = toolbar.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		size = coolitem.computeSize(size.x, size.y);
		coolitem.setSize(size);
		coolitem.setMinimumSize(size);
	}

	public void widgetSelected(SelectionEvent e) {

		if (e.widget instanceof Combo) {
			if (e.widget == this.zoom) {
				int value = 100;
				try {
					String strzoom = this.zoom.getText().trim();
					if (strzoom.length() != 0) {
						if (strzoom.charAt(strzoom.length() - 1) == '%') {
							strzoom = strzoom.substring(0, strzoom.length() - 1);
						}
					}
					
					value = (new Integer(strzoom.trim())).intValue();
				} catch(Exception exc) {
					value = 100;
				}

				if (value >= 60) {
					if (value != this.window.getCanvas().scaleInt) {
						this.window.getCanvas().setZoom(value);
					}
				}
			}
		} else if (e.widget instanceof ToolItem) {
			ToolItem button = (ToolItem) e.widget;
			
			/*
			if (button == this.resetzoom) {
				this.window.getCanvas().resetZoom();
			} else if (button == this.onetotwo) {
				this.window.getCanvas().setZoom(200);
			}
			*/
		
			if (button.getSelection()) {
				if (button == this.selectionitem) {
					this.window.getToolManager().setTool(new SelectionKesoTool());
				} else if (button == this.zoomin) {
					this.window.getToolManager().setTool(new ZoomInKesoTool());
				} else if (button == this.zoomout) {
					this.window.getToolManager().setTool(new ZoomOutKesoTool());
				} else if (button == this.nodeitem) {
					this.window.getToolManager().setTool(new AddNodeKesoTool());
				} else if (button == this.networkitem) {
					this.window.getToolManager().setTool(new AddNetworkKesoTool());
				} else if (button == this.taskitem) {
					this.window.getToolManager().setTool(new AddTaskKesoTool());
				} else if (button == this.domainitem) {
					this.window.getToolManager().setTool(new AddDomainKesoTool());
				} else if (button == this.publicdomainitem) {
					this.window.getToolManager().setTool(new AddPublicDomainKesoTool());
				} else if (button == this.importitem) {
					this.window.getToolManager().setTool(new AddImportKesoTool());
				} else if (button == this.serviceitem) {
					this.window.getToolManager().setTool(new AddServiceKesoTool());
				} else if (button == this.resourceitem) {
					this.window.getToolManager().setTool(new AddResourceKesoTool());
				} else if (button == this.appmodeitem) {
					this.window.getToolManager().setTool(new AddAppmodeKesoTool());
				} else if (button == this.eventitem) {
					this.window.getToolManager().setTool(new AddEventKesoTool());
				} else if (button == this.interruptitem) {
					this.window.getToolManager().setTool(new AddIsrKesoTool());
				} else if (button == this.counteritem) {
					this.window.getToolManager().setTool(new AddCounterKesoTool());
				} else if (button == this.alarmitem) {
					this.window.getToolManager().setTool(new AddAlarmKesoTool());
				} else if (button == this.osekositem) {
					this.window.getToolManager().setTool(new AddOsekOsKesoTool());
				}
			}
		}
	}

	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	private void reset() {
		this.selectionitem.setSelection(true);
		this.zoomin.setSelection(false);
		this.zoomout.setSelection(false);
		//this.resetzoom.setSelection(false);
		//this.onetotwo.setSelection(false);
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
	
	private void deselectAll() {
		this.selectionitem.setSelection(false);
		this.zoomin.setSelection(false);
		this.zoomout.setSelection(false);
		//this.resetzoom.setSelection(false);
		//this.onetotwo.setSelection(false);
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
				if (!this.zoomin.getSelection()) {
					this.zoomin.setSelection(true);
				}
			} else if (tool instanceof ZoomOutKesoTool) {
				if (!this.zoomout.getSelection()) {
					this.zoomout.setSelection(true);
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
				this.zoomin.setEnabled(false);
				this.zoomout.setEnabled(false);
				this.zoom.setEnabled(false);
				//this.onetotwo.setEnabled(false);
				//this.resetzoom.setEnabled(false);
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
				this.window.getToolManager().setTool(new SelectionKesoTool());
				this.selectionitem.setEnabled(true);
				this.zoomin.setEnabled(true);
				this.zoomout.setEnabled(true);
				this.zoom.setEnabled(true);
				//this.resetzoom.setEnabled(true);
				//this.onetotwo.setEnabled(true);
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
		} else if (e.getPropertyName().equals("zoom changed")) {
			this.zoom.setText(Integer.toString(this.window.getCanvas().scaleInt) + " %");
		}
	}

	public void modifyText(ModifyEvent e) {
		if (e.widget instanceof Combo) {
			if (e.widget == this.zoom) {
				int value = 100;
				try {
					String strzoom = this.zoom.getText().trim();
					if (strzoom.length() != 0) {
						if (strzoom.charAt(strzoom.length() - 1) == '%') {
							strzoom = strzoom.substring(0, strzoom.length() - 1);
						}
					}
					
					value = (new Integer(strzoom.trim())).intValue();
				} catch(Exception exc) {
					value = 100;
				}

				if (value >= 60) {
					if (value != this.window.getCanvas().scaleInt) {
						this.window.getCanvas().setZoom(value);
					}
				}
			}
		}
	}

}
