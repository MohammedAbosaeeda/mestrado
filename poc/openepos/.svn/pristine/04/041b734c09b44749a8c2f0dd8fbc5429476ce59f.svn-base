package keso.editor.gui.core.toolbar;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import keso.editor.gui.core.KesoImageManager;
import keso.editor.gui.core.KesoMainWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

/**
 * @author  Wilhelm Haas
 */
public class KesoStandardToolBar implements SelectionListener, Listener, PropertyChangeListener {

	private KesoMainWindow window;

	ToolItem newitem;
	ToolItem openitem;
	ToolItem saveitem;
	ToolItem printitem;
	ToolItem exportitem;
	
	ToolItem compileitem;
	
	public KesoStandardToolBar(KesoMainWindow window) {
		this.window = window;
		this.window.addPropertyChangeListener(this);
		
		ToolBar toolbar = new ToolBar(this.window.getToolBar(), SWT.FLAT | SWT.HORIZONTAL);
		
		this.newitem = new ToolItem(toolbar, SWT.PUSH);
		this.newitem.setImage(KesoImageManager.getInstance().getImage("new"));
		this.newitem.setToolTipText("Start new configuration");
		this.newitem.addSelectionListener(this);
		this.newitem.addListener(SWT.MouseEnter, this);
		this.newitem.addListener(SWT.MouseExit, this);

		this.openitem = new ToolItem(toolbar, SWT.PUSH);
		this.openitem.setImage(KesoImageManager.getInstance().getImage("open"));
		this.openitem.setToolTipText("Open a configuration");
		this.openitem.addSelectionListener(this);
		this.openitem.addListener(SWT.MouseEnter, this);
		this.openitem.addListener(SWT.MouseExit, this);
		
		this.saveitem = new ToolItem(toolbar, SWT.PUSH);
		this.saveitem.setImage(KesoImageManager.getInstance().getImage("save"));
		this.saveitem.setToolTipText("Save current configuration");
		this.saveitem.addSelectionListener(this);
		this.saveitem.addListener(SWT.MouseEnter, this);
		this.saveitem.addListener(SWT.MouseExit, this);
		
		this.exportitem = new ToolItem(toolbar, SWT.PUSH);
		this.exportitem.setImage(KesoImageManager.getInstance().getImage("export"));
		this.exportitem.setToolTipText("Export configuration");
		this.exportitem.addSelectionListener(this);
		this.exportitem.addListener(SWT.MouseEnter, this);
		this.exportitem.addListener(SWT.MouseExit, this);
		
		this.printitem = new ToolItem(toolbar, SWT.PUSH);
		this.printitem.setImage(KesoImageManager.getInstance().getImage("print"));
		this.printitem.setToolTipText("Print out configuration");
		this.printitem.addSelectionListener(this);
		this.printitem.addListener(SWT.MouseEnter, this);
		this.printitem.addListener(SWT.MouseExit, this);
		
		new ToolItem(toolbar, SWT.SEPARATOR);
		
		this.compileitem = new ToolItem(toolbar, SWT.PUSH);
		this.compileitem.setImage(KesoImageManager.getInstance().getImage("compile"));
		this.compileitem.setToolTipText("Compile Configuration");
		this.compileitem.addSelectionListener(this);
		this.compileitem.addListener(SWT.MouseEnter, this);
		this.compileitem.addListener(SWT.MouseExit, this);
		
		
		CoolItem coolitem = new CoolItem(this.window.getToolBar(), SWT.NULL);
		coolitem.setControl(toolbar);
		
		Point size = toolbar.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		size = coolitem.computeSize(size.x, size.y);
		coolitem.setSize(size);
		coolitem.setMinimumSize(size);
	}

	public void mouseEnter(ToolItem button) {
		this.window.getStatusBar().setText(button.getToolTipText());
	}

	public void mouseExit(ToolItem button) {
		this.window.getStatusBar().setText("");
	}

	public void handleEvent(Event event) {
		System.err.println("event: " + event);
		ToolItem button = (ToolItem) event.widget;
		if (event.type == SWT.MouseEnter) {
			this.mouseEnter(button);
		} else if (event.type == SWT.MouseExit) {
			this.mouseExit(button);
		}
	}

	public void widgetSelected(SelectionEvent e) {
		ToolItem button = (ToolItem) e.widget;
		if (button == this.newitem) {
			this.window.newConfiguration();
		} else if (button == this.openitem) {
			this.window.openFile();
		} else if (button == this.saveitem) {
			this.window.save();
		} else if (button == this.printitem) {
			this.window.print();
		} else if (button == this.exportitem) {
			this.window.export();
		} else if (button == this.compileitem) {
			this.window.compile();
		}
	}

	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void propertyChange(PropertyChangeEvent e) {
		if (this.window.getData() == null) {
			this.saveitem.setEnabled(false);
			this.exportitem.setEnabled(false);
			this.printitem.setEnabled(false);
			this.compileitem.setEnabled(false);
		} else {
			this.exportitem.setEnabled(true);
			this.printitem.setEnabled(true);
			if (this.window.getExceptionList().size() == 0) {
				this.compileitem.setEnabled(true);
			} else {
				this.compileitem.setEnabled(false);
			}
			
			if (this.window.isDataChanged() && this.window.getExceptionList().size() == 0) {
				this.saveitem.setEnabled(true);
			} else {
				this.saveitem.setEnabled(false);
			}
		}
		
		if (System.getProperty("os.name").toLowerCase().indexOf("windows") == -1) {
			this.printitem.setEnabled(false);
		}
	}

}
