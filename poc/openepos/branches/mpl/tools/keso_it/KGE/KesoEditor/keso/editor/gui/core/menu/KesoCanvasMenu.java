package keso.editor.gui.core.menu;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import keso.editor.KGE;
import keso.editor.data.IKesoData;
import keso.editor.data.KesoData;
import keso.editor.gui.KesoGuiProperties;
import keso.editor.gui.core.KesoImageManager;
import keso.editor.gui.core.KesoMainWindow;
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

/**
 * @author  Wilhelm Haas
 */
public class KesoCanvasMenu implements SelectionListener, PropertyChangeListener {
	KesoMainWindow window;
	
	MenuItem alwaysontopitem;
	MenuItem sendbackward;
	MenuItem bringforward;
	MenuItem sendtoback;
	MenuItem bringtofront;
	
	MenuItem textatconnectionline;
	MenuItem arrowatconnectionline;
	MenuItem highlightconnections;
	
	
	public KesoCanvasMenu(KesoMainWindow window) {
		this.window = window;
		this.window.addPropertyChangeListener(this);
		
		Menu menu;
		MenuItem menuitem;
		menuitem = new MenuItem(this.window.getMenuBar(), SWT.CASCADE);
		menuitem.setText("&Canvas");
		
		menu = new Menu(window.getShell(), SWT.DROP_DOWN);
		menuitem.setMenu(menu);
		
		
		
		
		
		this.alwaysontopitem = new MenuItem(menu, SWT.CHECK);
		this.alwaysontopitem.setText("Always on top");
		this.alwaysontopitem.addSelectionListener(this);
		
		new MenuItem(menu, SWT.SEPARATOR);
		
		this.bringtofront = new MenuItem(menu, SWT.PUSH);
		this.bringtofront.setText("Bring to Front");
		this.bringtofront.setImage(KesoImageManager.getInstance().getImage("bringtofront"));
		this.bringtofront.addSelectionListener(this);
		
		this.sendtoback = new MenuItem(menu, SWT.PUSH);
		this.sendtoback.setText("Send to Back");
		this.sendtoback.setImage(KesoImageManager.getInstance().getImage("sendtoback"));
		this.sendtoback.addSelectionListener(this);
		
		this.bringforward = new MenuItem(menu, SWT.PUSH);
		this.bringforward.setText("Bring Forward");
		this.bringforward.setImage(KesoImageManager.getInstance().getImage("bringforward"));
		this.bringforward.addSelectionListener(this);
		
		this.sendbackward = new MenuItem(menu, SWT.PUSH);
		this.sendbackward.setText("Send Backward");
		this.sendbackward.setImage(KesoImageManager.getInstance().getImage("sendbackward"));
		this.sendbackward.addSelectionListener(this);
		
		new MenuItem(menu, SWT.SEPARATOR);
		
		this.textatconnectionline = new MenuItem(menu, SWT.CHECK);
		this.textatconnectionline.setText("Show Text");
		this.textatconnectionline.addSelectionListener(this);
		//this.textatconnectionline.setSelection(true);
		
		this.arrowatconnectionline = new MenuItem(menu, SWT.CHECK);
		this.arrowatconnectionline.setText("Show Arrows");
		this.arrowatconnectionline.addSelectionListener(this);
		//this.arrowatconnectionline.setSelection(true);
		
		this.highlightconnections = new MenuItem(menu, SWT.CHECK);
		this.highlightconnections.setText("Highlight Connections");
		this.highlightconnections.addSelectionListener(this);
		//this.highlightconnections.setSelection(true);
		
		KesoGuiProperties properties = KesoGuiProperties.getInstance();
		String tmp = properties.getProperty("canvasmenu.alwaysontop.checked");
		if (tmp != null) {
			this.alwaysontopitem.setSelection(tmp.toLowerCase().equals("true"));
		} else {
			this.alwaysontopitem.setSelection(true);
		}
		tmp = properties.getProperty("canvasmenu.textatconnectionline.checked");
		if (tmp != null) {
			this.textatconnectionline.setSelection(tmp.toLowerCase().equals("true"));
		} else {
			this.textatconnectionline.setSelection(true);
		}
		tmp = properties.getProperty("canvasmenu.arrowatconnectionline.checked");
		if (tmp != null) {
			this.arrowatconnectionline.setSelection(tmp.toLowerCase().equals("true"));
		} else {
			this.arrowatconnectionline.setSelection(true);
		}
		tmp = properties.getProperty("canvasmenu.highlightconnections.checked");
		if (tmp != null) {
			this.highlightconnections.setSelection(tmp.toLowerCase().equals("true"));
		} else {
			this.highlightconnections.setSelection(true);
		}
		
	}

	public void widgetSelected(SelectionEvent e) {
		MenuItem button = (MenuItem) e.widget;
		if (button == this.alwaysontopitem) {
			this.window.getCanvas().setSelectionAlwaysOnTop(this.alwaysontopitem.getSelection());
			KesoGuiProperties.getInstance().setProperty("canvasmenu.alwaysontop.checked", Boolean.toString(this.alwaysontopitem.getSelection()));
		} else if (button == this.bringforward) {
			this.window.getCanvas().bringSelectionForward();
		} else if (button == this.sendbackward) {
			this.window.getCanvas().sendSelectionBackward();
		} else if (button == this.bringtofront) {
			this.window.getCanvas().bringSelectionToFront();
		} else if (button == this.sendtoback) {
			this.window.getCanvas().sendSelectionToBack();
		} else if (button == this.textatconnectionline) {
			this.window.getCanvas().setTextAtConnectionLine(this.textatconnectionline.getSelection());
			KesoGuiProperties.getInstance().setProperty("canvasmenu.textatconnectionline.checked", Boolean.toString(this.textatconnectionline.getSelection()));
		} else if (button == this.arrowatconnectionline) {
			this.window.getCanvas().setArrowAtConnectionLine(this.arrowatconnectionline.getSelection());
			KesoGuiProperties.getInstance().setProperty("canvasmenu.arrowatconnectionline.checked", Boolean.toString(this.arrowatconnectionline.getSelection()));
		} else if (button == this.highlightconnections) {
			this.window.getCanvas().setHighlightConnections(this.highlightconnections.getSelection());
			KesoGuiProperties.getInstance().setProperty("canvasmenu.highlightconnections.checked", Boolean.toString(this.highlightconnections.getSelection()));
		}
	}


	public void widgetDefaultSelected(SelectionEvent e) {
		
	}


	public void propertyChange(PropertyChangeEvent e) {
		if (e.getPropertyName().equals("global data changed")) {
			if (this.window.getData() == null) {
				this.alwaysontopitem.setEnabled(false);
				this.bringforward.setEnabled(false);
				this.bringtofront.setEnabled(false);
				this.sendbackward.setEnabled(false);
				this.sendtoback.setEnabled(false);
				this.textatconnectionline.setEnabled(false);
				this.arrowatconnectionline.setEnabled(false);
				this.highlightconnections.setEnabled(false);
			} else {
				this.alwaysontopitem.setEnabled(true);
				this.bringforward.setEnabled(true);
				this.bringtofront.setEnabled(true);
				this.sendbackward.setEnabled(true);
				this.sendtoback.setEnabled(true);
				this.textatconnectionline.setEnabled(true);
				this.arrowatconnectionline.setEnabled(true);
				this.highlightconnections.setEnabled(true);
			}
		}
		
		if (this.window.getCanvas().getSelection() != null) {
			this.bringforward.setEnabled(true);
			this.bringtofront.setEnabled(true);
			this.sendbackward.setEnabled(true);
			this.sendtoback.setEnabled(true);
		} else {
			this.bringforward.setEnabled(false);
			this.bringtofront.setEnabled(false);
			this.sendbackward.setEnabled(false);
			this.sendtoback.setEnabled(false);
		}
	}

}
