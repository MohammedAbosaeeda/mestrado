package keso.editor.gui.canvas;

import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Vector;
import javax.swing.event.EventListenerList;
import keso.editor.data.IKesoData;
import keso.editor.gui.KesoGuiProperties;
import keso.editor.gui.canvas.drawing.KesoDrawingManager;
import keso.editor.gui.core.KesoImageManager;
import keso.editor.gui.core.KesoStatusBar;
import keso.editor.gui.graphics.IKesoGraphics;
import keso.editor.gui.graphics.KesoColor;
import keso.editor.gui.graphics.SwtKesoGraphics;
import keso.editor.gui.shape.KesoShape;
import keso.editor.gui.shape.design.stylemanager.ScreenKesoShapeStyleManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;

/**
 * @author  Wilhelm Haas
 */
public class KesoCanvas implements SelectionListener, KeyListener, PropertyChangeListener, ControlListener, PaintListener, MouseTrackListener, MouseListener, MouseMoveListener {
	public static int countPaints = 0;
	private static Point buttonsize = new Point(11, 11);
	
	Composite parent;
	ScrolledComposite scrolledcomposite;
	Canvas canvas;
	int style;
	
	KesoShape rootshape;
	
	KesoShape selection;
	KesoShape mouseovershape;
	
	EventListenerList mouselistener = new EventListenerList();
	EventListenerList mousemovelistener = new EventListenerList();
	EventListenerList mousetracklistener = new EventListenerList();
	EventListenerList selectionlistener = new EventListenerList();
	EventListenerList keylistener = new EventListenerList();
	
	private boolean shouldredraw = false;
	boolean firststart = true;
	
	boolean ismousebuttonpressed;
	Point lastmouseaction = new Point(0, 0);
	boolean selectionLocked = false;
	
	PropertyChangeSupport change = new PropertyChangeSupport(this);

	public double scale = 1.0;
	public int scaleInt = 100;
	
	private KesoStatusBar statusbar;

	private boolean selectionalwaysontop = KesoGuiProperties.getInstance().getProperty("canvasmenu.alwaysontop.checked", "true").equals("true");

	private Object window;
	
	private boolean showTextAtConnectionLine = KesoGuiProperties.getInstance().getProperty("canvasmenu.textatconnectionline.checked", "true").equals("true");
	private boolean showArrowAtConnetionLine = KesoGuiProperties.getInstance().getProperty("canvasmenu.arrowatconnectionline.checked", "true").equals("true");
	private boolean highlightconnections = KesoGuiProperties.getInstance().getProperty("canvasmenu.highlightconnections.checked", "true").equals("true");

	private boolean mousebuttonpressed;

	private int stepscale = 20;
	
	private boolean stopdefault = false;
	
	public KesoCanvas(Composite parent, int style) {
		this.parent = parent;
		this.style = style;
		/*
		CTabFolder folder = new CTabFolder(this.parent, SWT.BORDER);
		CTabItem citem = new CTabItem(folder, SWT.NONE);
		folder.setSelection(citem);
		citem.setText("Diagram of Configuration");
		citem.setImage(KesoImageManager.getInstance().getImage("canvas"));
		*/
		
		this.scrolledcomposite = new ScrolledComposite(parent, this.style | SWT.H_SCROLL | SWT.V_SCROLL);
		this.scrolledcomposite.setBackground(new Color(Display.getCurrent(), 230, 230, 230));
		this.scrolledcomposite.addControlListener(this);
		this.scrolledcomposite.getHorizontalBar().addSelectionListener(this);
		this.scrolledcomposite.getVerticalBar().addSelectionListener(this);
		
		//citem.setControl(this.scrolledcomposite);
		
		
		this.canvas = new Canvas(this.scrolledcomposite, SWT.DOUBLE_BUFFERED);
		this.canvas.setBackground(new Color(Display.getCurrent(), 230, 230, 230));
		this.canvas.addPaintListener(this);
		this.canvas.addMouseListener(this);
		this.canvas.addMouseMoveListener(this);
		this.canvas.addMouseTrackListener(this);
		this.canvas.addKeyListener(this);
		
		FontData fontdata = this.canvas.getFont().getFontData()[0];
		this.canvas.setFont(new org.eclipse.swt.graphics.Font(this.canvas.getDisplay(), "Dialog", 8, fontdata.getStyle()));
		
		this.scrolledcomposite.setContent(this.canvas);
		//this.canvas.setBounds(0, 0, 1, 1);
	}
	
	public Control getWidget() {
		return this.scrolledcomposite;
	}
	
	public void setStopDefault(boolean stopdefault) {
		this.stopdefault = stopdefault;
		this.redraw();
	}
	
	public Display getDisplay() {
		return this.canvas.getDisplay();
	}
	
	public void setData(IKesoData data) {
		this.setSelection((KesoShape) null);
		if (this.rootshape != null) {
			this.rootshape.removePropertyChangeListener(this);
		}
		
		this.rootshape = null;
		if (data != null) {
			this.rootshape = new KesoShape(this, data);
		}
		
		if (this.rootshape != null) {
			this.rootshape.addPropertyChangeListener(this);
		}
		this.redraw();
	}
	
	public void setParentWindow(Object window) {
		this.window = window;
	}
	
	public Object getParentWindow() {
		return this.window;
	}
	
	public void redraw() {
		this.shouldredraw = true;
	}
	
	public void raiseSelection() {
		KesoShape shape = this.getSelection();
		while (shape != null) {
			shape.bringToFront();
			shape = shape.getParent();
		}
	}
	
	/**
	 * @param selectionLocked  the selectionLocked to set
	 * @uml.property  name="selectionLocked"
	 */
	public void setSelectionLocked(boolean locked) {
		this.selectionLocked = locked;
	}
	
	/**
	 * @return  the selectionLocked
	 * @uml.property  name="selectionLocked"
	 */
	public boolean isSelectionLocked() {
		return this.selectionLocked;
	}
	
	public void setCursor(Cursor cursor) {
		this.canvas.setCursor(cursor);
	}
	
	public void paint() {
		if (this.shouldredraw) {
			this.shouldredraw = false;
			this.canvas.redraw();
		}
	}
	
	public KesoShape getRootShape() {
		return this.rootshape;
	}
	
	public void initCoordinatesAutomatically() {
		if (this.getRootShape() != null &&
				this.getRootShape().getChild(0).getVersion().equals("V0")) {
			this.getRootShape().initCoordinatesAutomatically();
			this.redraw();
		}
	}
	
	public void setSelectionAlwaysOnTop(boolean selectionalwaysontop) {
		this.selectionalwaysontop = selectionalwaysontop;
		if (this.getSelection() != null) {
			this.getSelection().bringToFront();
		}
	}

	public boolean isSelectionAlwaysOnTop() {
		return this.selectionalwaysontop;
	}
	
	/**
	 * @param selection  the selection to set
	 * @uml.property  name="selection"
	 */
	public void setSelection(KesoShape shape) {
		if (this.selection != shape) {
			
			if (shape == null && this.rootshape != null) {
				shape = this.rootshape;
			}
			
			this.selection = shape;
			this.mouseovershape = shape;
			
			if (this.selection != null && this.isSelectionAlwaysOnTop()) {				
				this.raiseSelection();
			}
			

			for (int i = 0; i < 2; i++) {
				KesoShape tmp_shape = shape;
				
				if (tmp_shape != null && tmp_shape.getParent() != null) {
					tmp_shape = tmp_shape.getParent();
				}
				
				while (tmp_shape != null) {
					tmp_shape.setHiddeInformation(false);
					tmp_shape = tmp_shape.getParent();
				}
			}
			
			
			Event event = new Event();
			event.widget = this.canvas;
			event.data = this.selection;
			this.fireWidgedSelected(new SelectionEvent(event));
			if (this.selection == null) {
				this.change.firePropertyChange("selection changed", null, null);
			} else {
				this.change.firePropertyChange("selection changed", null, this.selection.getData());
			}
			
			this.redraw();
		}
	}
	
	public void bringSelectionToFront() {
		if (this.getSelection() != null) {
			this.getSelection().bringToFront();
		}
	}
	
	public void sendSelectionToBack() {
		if (this.getSelection() != null) {
			this.getSelection().sendToBack();
		}
	}
	
	public void bringSelectionForward() {
		if (this.getSelection() != null) {
			this.getSelection().bringForward();
		}
	}
	
	public void sendSelectionBackward() {
		if (this.getSelection() != null) {
			this.getSelection().sendBackward();
		}
	}
	
	
	/**
	 * @return  the selection
	 * @uml.property  name="selection"
	 */
	public KesoShape getSelection() {
		return this.selection;
	}
	
	public void setSelection(IKesoData data) {
		this.setSelection(this.find(data));
	}
	
	public void getGraphic(KesoDrawingManager drawingmanager) {
		if (this.getRootShape() != null) {
			this.getRootShape().setBasicX(0);
			this.getRootShape().setBasicY(0);
			
			drawingmanager.getGraphics().setTextAntialias(true);
			drawingmanager.getGraphics().setAntialias(true);
			this.getRootShape().paintShape(drawingmanager, drawingmanager.start_offsetx, drawingmanager.start_offsety);
		}
	}
	
	/*
	public void getEpsImage(EpsGraphics eps) {
		if (this.getRootShape() != null) {
			this.getRootShape().setBasicX(0);
			this.getRootShape().setBasicY(0);
			
			Font font = new Font("Tahoma", 0, 11);
			eps.setFont(font);
			EpsKesoGraphics gc = new EpsKesoGraphics(eps);
			
			gc.setClipping(0, 0, this.getRootShape().getWidth() + 5, this.getRootShape().getHeight() + 5);
			gc.setAntialias(true);
			
			this.getRootShape().paintShape(gc, 0, 0);
		}
	}
	*/
	
	public void paintControl(PaintEvent e) {		
		if (this.getRootShape() != null) {			
			IKesoGraphics gc = new SwtKesoGraphics(e.gc);
			KesoDrawingManager drawingmanager = new KesoDrawingManager(gc, ScreenKesoShapeStyleManager.getInstance());
			drawingmanager.shapescale = scale;
			drawingmanager.fontscale = scale;
			
			drawingmanager.start_offsetx = 0;
			drawingmanager.start_offsety = 0;
			this.getGraphic(drawingmanager);
			
			this.drawFocus(gc);
			this.drawResizeButtons(gc);
			this.drawButtons(gc, this.mouseovershape);
		}
	}
	
	
	private void drawButtons(IKesoGraphics gc, KesoShape shape) {
		if (shape != null && !this.stopdefault) {
			int x = -1;
			int y = -1;
			KesoShape informationshape = shape;
			
			while (informationshape != null && informationshape.isInformationHidingAllowed() == false) {
				informationshape = informationshape.getParent();	
			}
			
			if (informationshape != null) {
				Rectangle bounds = informationshape.getAbsoluteBounds();
				bounds.x = (int) Math.ceil(bounds.x * this.scale);
				bounds.y = (int) Math.ceil(bounds.y * this.scale);
				bounds.width = (int) Math.ceil(bounds.width * this.scale);
				bounds.height = (int) Math.ceil(bounds.height * this.scale);
				
				/*
				Image img;
				if (informationshape.isInformationHidden()) {
					img = KesoImageManager.getInstance().getImage("plus");
				} else {
					img = KesoImageManager.getInstance().getImage("minus");
				}
				
				*/
				
				int width = 11;
				int height = 11;
				x = bounds.x + bounds.width - width - 3;
				y = bounds.y + 3;
				
				if (informationshape.isInformationHidden()) {
					this.drawInformationHidingButton(gc, true, x, y, width, height);
				} else {
					this.drawInformationHidingButton(gc, false, x, y, width, height);
				}
			}
			
			KesoShape stretchshape = shape;
			while (stretchshape != null && (stretchshape.isHorizontalResizeable() == false ||
					stretchshape.isVerticalResizeable() == false)) {
				stretchshape = stretchshape.getParent();
			}
			
			if (stretchshape != null) {
				
				/*
				Image img = null;
				if (stretchshape.isStrechable()) {
					img = KesoImageManager.getInstance().getImage("nostretch");
				} else {
					img = KesoImageManager.getInstance().getImage("autostretch");
				}
				*/
				int width = 11;
				int height = 11;
				
				if (stretchshape != informationshape) {
					Rectangle bounds = stretchshape.getAbsoluteBounds();
					bounds.x = (int) Math.ceil(bounds.x * this.scale);
					bounds.y = (int) Math.ceil(bounds.y * this.scale);
					bounds.width = (int) Math.ceil(bounds.width * this.scale);
					bounds.height = (int) Math.ceil(bounds.height * this.scale);
					
					x = bounds.x  + bounds.width - 3;
					y = bounds.y + 3;
				} else {
					x -= 1;
				}
				
				x -= width;
				
				this.drawStretchingButton(gc, !stretchshape.isStrechable(), x, y, width, height);
				//gc.drawImage(img, x, y);
			}
		}
	}	
	
	void drawInformationHidingButton(IKesoGraphics gc, boolean plus, int x, int y, int width, int height) {
		gc.setLineWidth(1);
		gc.setBackground(new KesoColor(255, 255, 255));
		gc.setForeground(new KesoColor(0, 0, 0));
		
		gc.fillRoundRectangle(x, y, width - 1, height - 1, 4, 4);
		gc.drawRoundRectangle(x, y, width - 1, height - 1, 4, 4);
		
		gc.drawLine(x + 3, y + width / 2, x + width - 4, y + width / 2);
		if (plus) {
			gc.drawLine(x + width / 2, y + 3, x + width / 2, y + height - 4);
		}
	}
	
	void drawStretchingButton(IKesoGraphics gc, boolean autostretch, int x, int y, int width, int height) {
		gc.setLineWidth(1);
		gc.setBackground(new KesoColor(255, 255, 255));
		gc.setForeground(new KesoColor(0, 0, 0));
		
		gc.fillRoundRectangle(x, y, width - 1, height - 1, 4, 4);
		gc.drawRoundRectangle(x, y, width - 1, height - 1, 4, 4);
		
		gc.setBackground(new KesoColor(0, 0, 0));
		if (autostretch) {
			gc.fillRoundRectangle(x + 2, y + 2, width - 4, height - 4, 4, 4);
		} else {
			gc.fillRoundRectangle(x + width / 2 - 1, y + height / 2 - 1, 3, 3, 4, 4);
		}
	}
	
	public void drawFocus(IKesoGraphics gc) {
		/* Draw focus */
		if (this.getSelection() != null) {
			gc.setLineWidth((int) Math.floor(1 * this.scale));
			int x = this.getSelection().getAbsoluteX();
			int y = this.getSelection().getAbsoluteY();
			int width = this.getSelection().getWidth();
			int height = this.getSelection().getHeight();
			
			KesoColor color = gc.getForeground();
			
			/*
			gc.drawFocus(x - this.scrolledcomposite.getHorizontalBar().getSelection(), 
					y - this.scrolledcomposite.getVerticalBar().getSelection(), width, height);
			*/
			gc.setForeground(new KesoColor(255, 0, 0));
			gc.drawRoundRectangle((int) Math.ceil(this.scale * x), 
					(int) Math.ceil(this.scale * y), 
					(int) Math.ceil(this.scale * width), 
					(int) Math.ceil(this.scale * height), 
					(int) Math.ceil(this.scale * 4), 
					(int) Math.ceil(this.scale * 4));
			
			gc.setForeground(color);
		}
	}
	
	public void drawResizeButtons(IKesoGraphics gc) {
		if (	this.getSelection() != null && 
				this.getSelection().isResizeable() &&
				!this.stopdefault) {
			gc.setLineWidth(1);
			int delta = 3;
			int x = (int) Math.ceil(this.scale * this.getSelection().getAbsoluteX());
			int y = (int) Math.ceil(this.scale * this.getSelection().getAbsoluteY());
			int width = (int) Math.ceil(this.scale * this.getSelection().getWidth());
			int height = (int) Math.ceil(this.scale * this.getSelection().getHeight());
			
			gc.setForeground(new KesoColor(0, 0, 0));
			gc.setBackground(new KesoColor(255, 255, 255));
			
			if (this.getSelection().isHorizontalResizeable() &&
					this.getSelection().isVerticalResizeable()) {
				
				gc.fillRectangle(x - delta, y - delta , 2 * delta , 2 * delta);
				gc.fillRectangle(x - delta + width, y - delta , 2 * delta , 2 * delta);
				
				gc.fillRectangle(x - delta, y - delta + height, 2 * delta , 2 * delta);
				gc.fillRectangle(x - delta + width, y - delta + height, 2 * delta , 2 * delta);
				
				gc.drawRectangle(x - delta, y - delta , 2 * delta , 2 * delta);
				gc.drawRectangle(x - delta + width, y - delta , 2 * delta , 2 * delta);
				gc.drawRectangle(x - delta, y - delta + height, 2 * delta , 2 * delta);
				gc.drawRectangle(x - delta + width, y - delta + height, 2 * delta , 2 * delta);
			}
			
			if (this.getSelection().isVerticalResizeable()) {
				gc.fillRectangle(x + width / 2 - delta, y - delta , 2 * delta , 2 * delta);
				gc.fillRectangle(x +width / 2 - delta, y - delta + height , 2 * delta , 2 * delta);
				
				gc.drawRectangle(x + width / 2 - delta, y - delta , 2 * delta , 2 * delta);
				gc.drawRectangle(x + width / 2 - delta, y - delta + height, 2 * delta , 2 * delta);
			}
			
			if (this.getSelection().isHorizontalResizeable()) {
				gc.fillRectangle(x - delta, y - delta + height / 2, 2 * delta , 2 * delta);
				gc.fillRectangle(x - delta + width, y - delta  + height / 2, 2 * delta , 2 * delta);
				
				gc.drawRectangle(x - delta, y - delta + height / 2, 2 * delta , 2 * delta);
				gc.drawRectangle(x - delta + width, y - delta  + height / 2, 2 * delta , 2 * delta);
			}
		}
	}
	
	public KesoShape find(IKesoData data) {
		if (data != null && this.rootshape != null) {
			Vector shapes = new Vector();
			shapes.add(this.rootshape);
			while(shapes.size() != 0) {
				KesoShape shape = (KesoShape) shapes.remove(0);
				if (shape.getData() == data) {
					return shape;
				}
				shapes.addAll(shape.getChildren());
			}
		}
		return null;
	}
	
	public KesoShape getShapeAtPosition(int x, int y) {
		KesoShape shape = this.getRootShape().getShapeAtPosition(x, y);
		if (shape == null && this.rootshape != null) {
			return this.rootshape;
		} else {
			return shape;
		}
		
	}
	
	public void controlMoved(ControlEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void controlResized(ControlEvent e) {
		//this.scrolledcomposite.setBounds(1, 1, this.parent.getSize().x - 2, this.parent.getSize().y - 2);
	}
	
	public GC getGC() {
		if (this.canvas != null) {
			return new GC(this.canvas);
		} else {
			return null;
		}
	}
	
	
	public void mouseDoubleClick(MouseEvent e) {
		e.x /= this.scale;
		e.y /= this.scale;
		this.fireMouseDoubleClick(e);
	}
	
	public void mouseDown(MouseEvent e) {
		e.x /= this.scale;
		e.y /= this.scale;
		this.mousebuttonpressed = true;
		if (!this.isSelectionLocked() && !this.stopdefault) {
			this.setSelection(this.getShapeAtPosition(e.x, e.y));
		}
		this.fireMouseDown(e);
	}
	
	public void mouseUp(MouseEvent e) {
		int mousex = e.x; // (int) Math.ceil(e.x * this.scale);
		int mousey = e.y; // (int) Math.ceil(e.y * this.scale);
		
		e.x /= this.scale;
		e.y /= this.scale;
		
		this.mousebuttonpressed = false;
		if (e.button == 1 && this.mouseovershape != null && !this.stopdefault) {
						
			int x = -1;
			int y = -1;
			if (this.mouseovershape.isInformationHidingAllowed()) {
				Rectangle bounds = this.mouseovershape.getAbsoluteBounds();
				
				bounds.x = (int) Math.ceil(this.scale * bounds.x);
				bounds.y = (int) Math.ceil(this.scale * bounds.y);
				bounds.width = (int) Math.ceil(this.scale * bounds.width);
				bounds.height = (int) Math.ceil(this.scale * bounds.height);
				
				/*
				Image img;
				if (this.mouseovershape.isInformationHidden()) {
					img = KesoImageManager.getInstance().getImage("plus");
				} else {
					img = KesoImageManager.getInstance().getImage("minus");
				}
				*/
				
				x = bounds.x + bounds.width - KesoCanvas.buttonsize.x - 3;
				y = bounds.y + 3;
				
				if (	(mousex >= x) && 
						(mousex <= x + KesoCanvas.buttonsize.x - 1) &&
						(mousey >= y) &&
						(mousey <= y + KesoCanvas.buttonsize.y - 1)) {
					this.mouseovershape.setHiddeInformation(!this.mouseovershape.isInformationHidden());
					this.change.firePropertyChange("data changed", null, null);
					int tmpx = e.x;
					int tmpy = e.y;
					
					e.x = mousex;
					e.y = mousey;
					this.mouseMove(e);
					e.x = tmpx;
					e.y = tmpy;
					
					this.redraw();
				}
			}
		
			
			if (this.mouseovershape.isHorizontalResizeable() && 
					this.mouseovershape.isVerticalResizeable()) {
				/*
				Image img;
				if (this.mouseovershape.isStrechable()) {
					img = KesoImageManager.getInstance().getImage("nostretch");
				} else {
					img = KesoImageManager.getInstance().getImage("autostretch");
				}
				*/
				
				if (x == -1 || y == -1) {
					Rectangle bounds = this.mouseovershape.getAbsoluteBounds();
					bounds.x = (int) Math.ceil(this.scale * bounds.x);
					bounds.y = (int) Math.ceil(this.scale * bounds.y);
					bounds.width = (int) Math.ceil(this.scale * bounds.width);
					bounds.height = (int) Math.ceil(this.scale * bounds.height);
					
					x = bounds.x + bounds.width - 3;
					y = bounds.y + 3;
				} else {
					x -= 1;
				}
				
				x -= KesoCanvas.buttonsize.x;
				
				if (	(mousex >= x) && 
						(mousex <= x + KesoCanvas.buttonsize.x - 1) &&
						(mousey >= y) &&
						(mousey <= y + KesoCanvas.buttonsize.y - 1)) {
					this.mouseovershape.setStrechable(!this.mouseovershape.isStrechable());
					this.change.firePropertyChange("data changed", null, null);
					
					this.redraw();
				}
				
			}
			
		}
		
		this.fireMouseUp(e);
	}
	
	public void mouseMove(MouseEvent e) {
		int mousex = e.x;
		int mousey = e.y;
		
		e.x /= this.scale;
		e.y /= this.scale;
		
		if (this.mousebuttonpressed == false) {
			if (this.rootshape != null) {
				KesoShape shape = this.getShapeAtPosition(e.x, e.y);
				if (shape != this.mouseovershape) {
					this.mouseovershape = shape;
					//System.err.println("redraw");
					this.redraw(); 
				}
			}
		}
		
		boolean showtooltip = false;
		
		String text = null;
		
		if (this.mouseovershape != null && !this.stopdefault) {
			int x = -1;
			int y = -1;
			if (this.mouseovershape.isInformationHidingAllowed()) {
				Rectangle bounds = this.mouseovershape.getAbsoluteBounds();
				bounds.x = (int) Math.ceil(this.scale * bounds.x);
				bounds.y = (int) Math.ceil(this.scale * bounds.y);
				bounds.width = (int) Math.ceil(this.scale * bounds.width);
				bounds.height = (int) Math.ceil(this.scale * bounds.height);
				
				boolean hidde = false;
				
				if (this.mouseovershape.isInformationHidden()) {
					hidde = false;
				} else {
					hidde = true;
				}
				
				x = bounds.x + bounds.width - KesoCanvas.buttonsize.x - 3;
				y = bounds.y + 3;
				
				
				if (	(mousex >= x) && 
						(mousex <= x + KesoCanvas.buttonsize.x - 1) &&
						(mousey >= y) &&
						(mousey <= y + KesoCanvas.buttonsize.y - 1)) {
					if (hidde) {
						text = "Hide Informations";
					} else {
						text = "Show Informations";
					}
				}
			}
			
			if (this.mouseovershape.isHorizontalResizeable() && 
					this.mouseovershape.isVerticalResizeable()) {

				boolean autostretch = false;
				if (this.mouseovershape.isStrechable()) {
					autostretch = false;
				} else {
					autostretch = true;
				}
				
				if (x == -1 || y == -1) {
					Rectangle bounds = this.mouseovershape.getAbsoluteBounds();
					bounds.x = (int) Math.ceil(this.scale * bounds.x);
					bounds.y = (int) Math.ceil(this.scale * bounds.y);
					bounds.width = (int) Math.ceil(this.scale * bounds.width);
					bounds.height = (int) Math.ceil(this.scale * bounds.height);
					x = bounds.x + bounds.width - 3;
					y = bounds.y + 3;
				} else {
					x -= 1;
				}
				
				x -= KesoCanvas.buttonsize.x;
				
				if (	(mousex >= x) && 
						(mousex <= x + KesoCanvas.buttonsize.x - 1) &&
						(mousey >= y) &&
						(mousey <= y + KesoCanvas.buttonsize.y - 1)) {
					if (autostretch) {
						text = "Activate Stretching";
					} else {
						text = "Deactivate stretching";
					}
				}
				
			}
		}
		
		if (text != null) {
			this.setStatusBarText(text);
		} else {
			this.setStatusBarText("(x: " + e.x + " y: " + e.y + ") - Zoom: " + (int) (this.scale * 100) + " %");
		}
		
		this.fireMouseMove(e);
	}
	
	public void setStatusBarText(String text) {
		if (this.statusbar != null && text != null) {
			this.statusbar.setText(text);
		}
	}
	
	public KesoShape getMouseOverShape() {
		return this.mouseovershape;
	}
	
	public void mouseEnter(MouseEvent e) {
		this.fireMouseEnter(e);
	}

	public void mouseExit(MouseEvent e) {
		if (this.statusbar != null) {
			this.statusbar.setText("");
		}
		this.fireMouseExit(e);
	}

	public void mouseHover(MouseEvent e) {
		this.fireMouseHover(e);
	}
	
	public void addMouseListener(MouseListener listener) {
		this.mouselistener.add(MouseListener.class, listener);
	}
	
	public void addMouseMoveListener(MouseMoveListener listener) {
		this.mousemovelistener.add(MouseMoveListener.class, listener);
	}
	
	public void addMouseTrackListener(MouseTrackListener listener) {
		this.mousetracklistener.add(MouseTrackListener.class, listener);
	}

	public void addSelectionListener(SelectionListener listener) {
		this.selectionlistener.add(SelectionListener.class, listener);
	}
	
	public void addKeyListener(KeyListener listener) {
		this.keylistener.add(KeyListener.class, listener);
	}
	
	public void removeMouseListener(MouseListener listener) {
		this.mouselistener.remove(MouseListener.class, listener);
	}
	
	public void removeMouseMoveListener(MouseMoveListener listener) {
		this.mousemovelistener.remove(MouseMoveListener.class, listener);
	}
	
	public void removeMouseTrackListener(MouseTrackListener listener) {
		this.mousetracklistener.remove(MouseTrackListener.class, listener);
	}

	public void removeSelectionListener(SelectionListener listener) {
		this.selectionlistener.remove(SelectionListener.class, listener);
	}
	
	public void removeKeyListener(KeyListener listener) {
		this.keylistener.remove(KeyListener.class, listener);
	}
	
	public void fireWidgedSelected(SelectionEvent e) {
		Object[] listeners = this.selectionlistener.getListenerList();
		for (int i = 1; i < listeners.length; i += 2) {
			((SelectionListener) listeners[i]).widgetSelected(e);
		}
	}
	
	public void fireMouseDoubleClick(MouseEvent e) {
		Object[] listeners = this.mouselistener.getListenerList();
		for (int i = 1; i < listeners.length; i += 2) {
			((MouseListener) listeners[i]).mouseDoubleClick(e);
		}
	}
	
	public void fireMouseDown(MouseEvent e) {
		Object[] listeners = this.mouselistener.getListenerList();
		for (int i = 1; i < listeners.length; i += 2) {
			((MouseListener) listeners[i]).mouseDown(e);
		}
	}
	
	public void fireMouseUp(MouseEvent e) {
		Object[] listeners = this.mouselistener.getListenerList();
		for (int i = 1; i < listeners.length; i += 2) {
			((MouseListener) listeners[i]).mouseUp(e);
		}
	}
	
	public void fireMouseMove(MouseEvent e) {
		Object[] listeners = this.mousemovelistener.getListenerList();
		for (int i = 1; i < listeners.length; i += 2) {
			((MouseMoveListener) listeners[i]).mouseMove(e);
		}
	}
	
	public void fireMouseEnter(MouseEvent e) {
		Object[] listeners = this.mousetracklistener.getListenerList();
		for (int i = 1; i < listeners.length; i += 2) {
			((MouseTrackListener) listeners[i]).mouseEnter(e);
		}
	}
	
	public void fireMouseExit(MouseEvent e) {
		Object[] listeners = this.mousetracklistener.getListenerList();
		for (int i = 1; i < listeners.length; i += 2) {
			((MouseTrackListener) listeners[i]).mouseExit(e);
		}
	}
	
	public void fireMouseHover(MouseEvent e) {
		Object[] listeners = this.mousetracklistener.getListenerList();
		for (int i = 1; i < listeners.length; i += 2) {
			((MouseTrackListener) listeners[i]).mouseHover(e);
		}
	}
	
	public void fireKeyPressed(KeyEvent e) {
		Object[] listeners = this.keylistener.getListenerList();
		for (int i = 1; i < listeners.length; i += 2) {
			((KeyListener) listeners[i]).keyPressed(e);
		}
	}
	
	public void fireKeyReleased(KeyEvent e) {
		Object[] listeners = this.keylistener.getListenerList();
		for (int i = 1; i < listeners.length; i += 2) {
			((KeyListener) listeners[i]).keyReleased(e);
		}
	}

	public void propertyChange(PropertyChangeEvent e) {
		if (e.getPropertyName().equals("shape resized")) {
			int width = this.rootshape.getWidth() + 5;
			int height = this.rootshape.getHeight() + 5;
			this.canvas.setSize((int) Math.ceil(width * scale), (int) Math.ceil(height * scale));
		} else if (e.getPropertyName().equals("property changed")) {
			this.redraw();
		} else if (e.getPropertyName().equals("new configuration")) {
			this.selection = null;
			this.mouseovershape = null;
			this.redraw();
		}
	}

	public void keyPressed(KeyEvent e) {
		this.fireKeyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		this.fireKeyReleased(e);
	}

	public void widgetSelected(SelectionEvent e) {
		this.redraw();
	}

	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @param statusbar  the statusbar to set
	 * @uml.property  name="statusbar"
	 */
	public void setStatusbar(KesoStatusBar bar) {
		this.statusbar = bar;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.change.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.change.removePropertyChangeListener(listener);
	}
	
	public void fireDataChanged() {
		this.change.firePropertyChange("data changed", null, null);
	}
	
	public void setTextAtConnectionLine(boolean istext) {
		this.showTextAtConnectionLine = istext;
		this.redraw();
	}
	
	public boolean isTextAtConnectionLine() {
		return this.showTextAtConnectionLine;
	}
	
	public void setArrowAtConnectionLine(boolean arrow) {
		this.showArrowAtConnetionLine = arrow;
		this.redraw();
	}
	
	public boolean isArrowAtConnectionLine() {
		return this.showArrowAtConnetionLine;
	}
	
	public void setHighlightConnections(boolean highlight) {
		this.highlightconnections = highlight;
		this.redraw();
	}
	
	public boolean isHighlightConnections() {
		return this.highlightconnections;
	}

	public void zoomIn() {
		this.scaleInt += this.stepscale;
		this.setZoom(this.scaleInt);
	}
	
	public void zoomOut() {
		this.scaleInt -= this.stepscale;
		this.setZoom(this.scaleInt);
	}
	
	public void setZoom(int scale) {
		this.scaleInt = scale;
		if (this.scaleInt < 60) {
			this.scaleInt = 60;
		}
		this.scale = (double) this.scaleInt / 100.0;
		if (this.scrolledcomposite.getHorizontalBar() != null) {
			this.scrolledcomposite.getHorizontalBar().setIncrement((int) Math.floor(2 * this.scale));
			this.scrolledcomposite.getVerticalBar().setIncrement((int) Math.floor(2 * this.scale));
			//System.err.println(this.scrolledcomposite.getHorizontalBar().getIncrement());
		}
		
		this.redraw();
		
		this.change.firePropertyChange("zoom changed", null, null);
	}

	public void resetZoom() {
		this.setZoom(100);
	}
}