package keso.editor.gui.tool;

import java.beans.PropertyChangeEvent;

import keso.editor.data.IKesoData;
import keso.editor.gui.core.KesoImageManager;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Cursor;

public class ZoomInKesoTool extends KesoTool implements MouseListener {

	Cursor cursor = null;
	IKesoData selection;
	
	public ZoomInKesoTool() {
		// TODO Auto-generated constructor stub
	}
	
	public void start() {
		if (this.getCanvas().getSelection() != null) {
			this.selection = this.getCanvas().getSelection().getData();
		}
		this.getCanvas().addMouseListener(this);
		this.getCanvas().setStopDefault(true);
		
		super.start();
		
		cursor =  new Cursor(this.getCanvas().getDisplay(),
			KesoImageManager.getInstance().getImage("cursor_zoomin").getImageData(), 6, 6);
		
		this.getCanvas().setCursor(cursor);
	}
	
	public void stop() {
		this.getCanvas().removeMouseListener(this);
		this.getCanvas().setStopDefault(false);
		
		super.stop();
		this.getCanvas().setCursor(this.getCanvas().getDisplay().getSystemCursor(SWT.CURSOR_ARROW));
		cursor.dispose();
		cursor = null;
	}

	public void propertyChange(PropertyChangeEvent e) {
		
	}

	public void mouseDoubleClick(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseDown(MouseEvent e) {
	}

	public void mouseUp(MouseEvent e) {
		this.getCanvas().zoomIn();
		this.getCanvas().setStatusBarText("(x: " + e.x + " y: " + e.y + ") - Zoom: " + (int) (this.getCanvas().scale * 100) + " %");
	}
}
