package keso.editor.gui.tool;

import java.beans.PropertyChangeEvent;

import keso.editor.gui.core.KesoImageManager;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Cursor;

public class ZoomOutKesoTool extends KesoTool implements MouseListener {

	Cursor cursor = null;
	
	public ZoomOutKesoTool() {
		// TODO Auto-generated constructor stub
	}
	
	public void start() {
		this.getCanvas().setStopDefault(true);
		this.getCanvas().addMouseListener(this);
		super.start();
		
		cursor =  new Cursor(this.getCanvas().getDisplay(),
			KesoImageManager.getInstance().getImage("cursor_zoomout").getImageData(), 6, 6);
		
		this.getCanvas().setCursor(cursor);
	}
	
	public void stop() {
		this.getCanvas().setStopDefault(false);
		this.getCanvas().removeMouseListener(this);
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
		// TODO Auto-generated method stub
		
	}

	public void mouseUp(MouseEvent e) {
		this.getCanvas().zoomOut();
		this.getCanvas().setStatusBarText("(x: " + e.x + " y: " + e.y + ") - Zoom: " + (int) (this.getCanvas().scale * 100) + " %");
	}

}
