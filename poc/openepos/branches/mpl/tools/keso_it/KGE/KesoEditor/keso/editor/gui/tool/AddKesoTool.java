package keso.editor.gui.tool;

import java.beans.PropertyChangeEvent;
import keso.editor.data.IKesoData;
import keso.editor.data.datamanager.KesoDataManager;
import keso.editor.gui.core.KesoMainWindow;
import keso.editor.gui.datatree.KesoDataTreeItem;
import keso.editor.gui.shape.KesoShape;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TreeItem;

/**
 * @author  Wilhelm Haas
 */
public abstract class AddKesoTool extends KesoTool implements MouseListener, SelectionListener, MouseMoveListener {

	protected IKesoData childdata;
	TreeItem mouseoveritem;
	
	
	public AddKesoTool() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void start() {
		super.start();
		this.generateChildData();
		this.getDataTree().getTree().addMouseMoveListener(this);
		this.getDataTree().getTree().addSelectionListener(this);
		
		this.getCanvas().addMouseListener(this);
		this.getCanvas().addMouseMoveListener(this);
		this.getCanvas().setSelectionLocked(true);
		
		this.getCanvas().setStopDefault(true);
	}
	
	public void stop() {
		this.getDataTree().getTree().removeMouseMoveListener(this);
		this.getDataTree().getTree().removeSelectionListener(this);
		this.getCanvas().removeMouseListener(this);
		this.getCanvas().removeMouseMoveListener(this);
		this.getCanvas().setSelectionLocked(false);
		this.getCanvas().setStopDefault(false);
	}

	public void propertyChange(PropertyChangeEvent e) {
		// TODO Auto-generated method stub

	}

	public void widgetSelected(SelectionEvent e) {
		if (this.mouseoveritem != null && e.item != null) {
			TreeItem item = (TreeItem) e.item;
			IKesoData parentdata = ((KesoDataTreeItem) item.getData()).getData();
			if (KesoDataManager.acceptsNewChild(parentdata, this.childdata)) {
				parentdata.addChild(this.childdata);
				this.getDataTree().setSelection(this.childdata);
				this.getCanvas().setSelection(childdata);
				this.getCanvas().getSelection().updateMinDimension();
				((KesoMainWindow) this.getCanvas().getParentWindow()).checkForExceptions();
				if (this.getManager() != null) {
					this.getManager().setTool(null);
				}
			}
		}
	}

	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	abstract protected void generateChildData();

	public void mouseMove(MouseEvent e) {
		if (e.widget == this.getDataTree().getTree()) {
			TreeItem item = this.getDataTree().getTree().getItem(new Point(e.x, e.y));
			this.mouseoveritem = item;
			if (item != null) {
				IKesoData parentdata = ((KesoDataTreeItem) item.getData()).getData();
				if (KesoDataManager.acceptsNewChild(parentdata, childdata)) {
					this.getDataTree().getTree().setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_HAND));
				} else {
					this.getDataTree().getTree().setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_NO));
				}
			} else {
				this.getDataTree().getTree().setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
			}
		} else {
			KesoShape parent = this.getCanvas().getShapeAtPosition(e.x, e.y);
			if (parent != null) {
				if (KesoDataManager.acceptsNewChild(parent.getData(), childdata)) {
					this.getCanvas().setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
				} else {
					this.getCanvas().setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_NO));
				}
			}
		}
	}

	public void mouseDoubleClick(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseDown(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseUp(MouseEvent e) {
		KesoShape parent = this.getCanvas().getShapeAtPosition(e.x, e.y);
		if (parent != null) {
			if (KesoDataManager.acceptsNewChild(parent.getData(), childdata)) {
				parent.getData().addChild(childdata);
				
				
				this.getCanvas().setSelection(childdata);
				this.getDataTree().setSelection(childdata);
				this.getCanvas().getSelection().updateMinDimension();
				KesoShape selection = this.getCanvas().getSelection();
				if (selection != null) {
					Point translation = parent.translatePoint(e.x, e.y);
					selection.setX(translation.x - parent.getPadding().left);
					selection.setY(translation.y - parent.getPadding().top);
				}
				((KesoMainWindow) this.getCanvas().getParentWindow()).setDataChanged(true);
				((KesoMainWindow) this.getCanvas().getParentWindow()).checkForExceptions();
				if (this.getManager() != null) {
					this.getManager().setTool(null);
				}
			} else {
				
			}
		}
	}

}
