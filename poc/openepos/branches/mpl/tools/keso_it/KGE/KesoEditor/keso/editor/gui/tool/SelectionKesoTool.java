package keso.editor.gui.tool;

import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import keso.editor.data.IKesoData;
import keso.editor.data.datamanager.KesoDataManager;
import keso.editor.gui.core.KesoImageManager;
import keso.editor.gui.core.KesoMainWindow;
import keso.editor.gui.datatree.KesoDataTreeItem;
import keso.editor.gui.shape.KesoShape;
import keso.editor.property.complexproperty.IKesoComplexProperty;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TreeItem;

/**
 * @author  Wilhelm Haas
 */
public class SelectionKesoTool extends KesoTool implements KeyListener, SelectionListener, DragSourceListener, 
	DropTargetListener, MouseMoveListener, MouseTrackListener, MouseListener {
	
	public static final int RESIZE_NO = 0;
	public static final int RESIZE_E = 1;
	public static final int RESIZE_SE = 2;
	public static final int RESIZE_S = 3;
	public static final int RESIZE_SW = 4;
	public static final int RESIZE_W = 5;
	public static final int RESIZE_NW = 6;
	public static final int RESIZE_N = 7;
	public static final int RESIZE_NE = 8;
	
	public static final int KEY_NO = 0;
	public static final int KEY_CTRL = 1;
	
	Transfer[] dragtypes = new Transfer[] { TextTransfer.getInstance() };
    int operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK;
    DragSource dragsource;
    DropTarget droptarget;
	TreeItem dragitem;
	IKesoData dragdata;
	
	boolean ismousebuttonpressed = false;
	Point shapemovingpoint = new Point(0, 0);
	Point resizemovingpoint = new Point(0, 0);
	Point clickdifference = new Point(0, 0);
	
	
	IKesoData forceSelection;
	
	private int resizestate;
	
	private int pressedkey = KEY_NO;
	
	public SelectionKesoTool() {
	}

	public void start() {
		super.start();
		
		this.canvas.addSelectionListener(this);
		this.canvas.addMouseMoveListener(this);
		this.canvas.addMouseTrackListener(this);
		this.canvas.addMouseListener(this);
		this.canvas.addKeyListener(this);
		
		this.datatree.getTree().addSelectionListener(this);
		this.datatree.getTree().addKeyListener(this);
		
		this.dragsource = new DragSource(this.datatree.getTree(), this.operations);
		this.dragsource.setTransfer(this.dragtypes);
		this.dragsource.addDragListener(this);
		
		this.droptarget = new DropTarget(this.datatree.getTree(), this.operations);
		this.droptarget.setTransfer(this.dragtypes);
		this.droptarget.addDropListener(this);
		
		this.datatree.getTree().setCursor(this.datatree.getTree().getDisplay().getSystemCursor(SWT.CURSOR_ARROW));
		this.canvas.setCursor(this.datatree.getTree().getDisplay().getSystemCursor(SWT.CURSOR_ARROW));
	}
	
	public void stop() {
		super.stop();
		this.dragsource.dispose();
		this.droptarget.dispose();
		
		this.canvas.removeSelectionListener(this);
		this.canvas.removeMouseMoveListener(this);
		this.canvas.removeMouseTrackListener(this);
		this.canvas.removeMouseListener(this);
		this.canvas.removeKeyListener(this);
		
		this.datatree.getTree().removeSelectionListener(this);
		this.datatree.getTree().removeKeyListener(this);
	}
	
	
	public void propertyChange(PropertyChangeEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void dragStart(DragSourceEvent event) {
		if (this.datatree.getTree().getSelectionCount() == 1) {
			this.dragitem = this.datatree.getTree().getSelection()[0];
			this.dragdata = ((KesoDataTreeItem) this.dragitem.getData()).getData();
			if (this.dragdata.getParent() != null && KesoDataManager.acceptsRemovalOfChild(this.dragdata.getParent(), this.dragdata)) {
				event.doit = true;
			} else {
				this.dragitem = null;
				this.dragdata = null;
				event.doit = false;
			}
		} else {
			event.doit = false;
		}
	}

	public void dragSetData(DragSourceEvent event) {
		event.data = this.dragitem.getText();
	}

	public void dragFinished(DragSourceEvent event) {
		this.dragitem = null;
        this.dragdata = null;
	}

	public void dragEnter(DropTargetEvent event) {
		// TODO Auto-generated method stub
		
	}

	public void dragLeave(DropTargetEvent event) {
		// TODO Auto-generated method stub
		
	}

	public void dragOperationChanged(DropTargetEvent event) {
		// TODO Auto-generated method stub
		
	}

	public void dragOver(DropTargetEvent event) {
		event.feedback = DND.FEEDBACK_EXPAND | DND.FEEDBACK_SCROLL;
        if (event.item != null) {
			TreeItem item = (TreeItem) event.item;
			KesoDataTreeItem treeitem = (KesoDataTreeItem) item.getData();
			if (KesoDataManager.acceptsNewChild(treeitem.getData(), this.dragdata)) {
				event.detail = DND.DROP_MOVE;
				event.feedback |= DND.FEEDBACK_INSERT_AFTER;
			} else {
				event.detail = DND.DROP_NONE;
			}
        } else {
        	event.detail = DND.DROP_NONE;
        }
	}

	public void drop(DropTargetEvent event) {
		if (this.dragdata != null) {
			TreeItem item = (TreeItem) event.item;
			KesoDataTreeItem treeitem = (KesoDataTreeItem) item.getData();
			IKesoData data = treeitem.getData();
			
			/*
			IKesoComplexProperty property = (IKesoComplexProperty) this.dragdata.getPropertyContainer();
			property.getFirstPropertyByName("x").setValue("0");
			property.getFirstPropertyByName("y").setValue("0");
			*/
			
			data.addChild(this.dragdata);
			event.detail = DND.DROP_NONE;
			this.getDataTree().setSelection(this.dragdata);
			this.getCanvas().setSelection(this.dragdata);
			this.forceSelection = this.dragdata;
			this.getDataTree().fireDataChanged();
			this.canvas.getRootShape().initMinDimension();
			this.canvas.redraw();
			((KesoMainWindow) this.getCanvas().getParentWindow()).checkForExceptions();
		}
	}

	public void dropAccept(DropTargetEvent event) {
		// TODO Auto-generated method stub
		
	}

	public void widgetSelected(SelectionEvent e) {
		if (forceSelection != null) {
			this.getCanvas().setSelection(forceSelection);
			this.getDataTree().setSelection(forceSelection);
			forceSelection = null;
			
		} else {
			if (e.data != null) {
				if (e.data instanceof KesoShape) {
					IKesoData data = ((KesoShape) e.data).getData();
					this.datatree.setSelection(data);
				}
			} 
			if (e.item != null) {
				if (e.item instanceof TreeItem) {
					KesoDataTreeItem treeitem = (KesoDataTreeItem) ((TreeItem) e.item).getData();
					IKesoData data = treeitem.getData();
					this.canvas.setSelection(data);
				}
			}
		}
	}

	public void widgetDefaultSelected(SelectionEvent e) {
		
	}

	public void mouseMove(MouseEvent e) {
		if (this.ismousebuttonpressed) {
			if (this.getResizeState() == RESIZE_NO) {
				if (this.ismousebuttonpressed && this.getCanvas().getSelection() != null && 
						this.getCanvas().getSelection() != this.getCanvas().getRootShape() ) {
					this.moveShape(e.x, e.y);
				}
			} else {
				this.resizeShape(e.x, e.y);
			}
		} else {
			this.checkResizeState(e.x, e.y);
		}
	}
	
	public Point moveIntoNewParent(KesoShape parent, KesoShape newparent, int mousex, int mousey) {
		KesoShape selection = this.getCanvas().getSelection();
		this.getCanvas().setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
		
		selection.setParent(newparent);
		
		newparent.getData().addChild(selection.getData());
		
		this.getDataTree().setSelection(selection.getData());
		this.getCanvas().setSelection(selection.getData());
		
		forceSelection = selection.getData();
		
		parent.updateMinDimension();
		
		parent = newparent;
		
		if (this.getCanvas().isSelectionAlwaysOnTop()) {
			KesoShape shape = parent;
			while (shape != null) {
				shape.bringToFront();
				shape = shape.getParent();
			}
		}
		
		Point translation = parent.translatePoint(mousex, mousey);
		
		int x = translation.x - parent.getPadding().left  + this.clickdifference.x;
		int y = translation.y - parent.getPadding().top + this.clickdifference.y;
		
		((KesoMainWindow) this.getCanvas().getParentWindow()).checkForExceptions();
		
		return new Point(x, y);
	}
	
	public void moveShape(int mousex, int mousey) {
		KesoShape selection = this.getCanvas().getSelection();
		KesoShape parent = selection.getParent();
		
		int deltax = mousex - selection.getAbsoluteX();
		int deltay = mousey - selection.getAbsoluteY();
		
		int x = selection.getX() + deltax + this.clickdifference.x;
		int y = selection.getY() + deltay + this.clickdifference.y;
		
		int width = selection.getWidth();
		int height = selection.getHeight();
		
		if (parent != null && (!parent.isStrechable() || !this.isCtrlPressed())) {
			KesoShape newparent = this.getCanvas().getShapeAtPosition(mousex, mousey);
			if (newparent != null && newparent != parent) {
				if (newparent == this.getCanvas().getSelection()) {
					this.getCanvas().setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
				} else {
				
					boolean accepts = true;
					if (parent != null) {
						accepts = KesoDataManager.acceptsRemovalOfChild(parent.getData(), selection.getData());
					}
					accepts = accepts && KesoDataManager.acceptsNewChild(newparent.getData(), selection.getData());
					
					if (accepts) {
						Point p = moveIntoNewParent(parent, newparent, mousex, mousey);
						x = p.x;
						y = p.y;
					} else {
						this.getCanvas().setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_NO));
					}
				}
			} else {
				
			}
		}					
		
		if (parent != null && !parent.isStrechable() && !this.isCtrlPressed()) {
			Rectangle clientarea = parent.getChildArea();
			
			if (x + width > clientarea.width) {
				x = clientarea.width - width;
			}
			
			if (y + height > clientarea.height) {
				y = clientarea.height - height;
			}
		}
		
		selection.setLocation(x, y);
		this.shapemovingpoint.x = mousex;
		this.shapemovingpoint.y = mousey;
		
		this.canvas.fireDataChanged();
	}
	
	public void checkResizeState(int mousex, int mousey) {
		this.setResizeState(RESIZE_NO);
		if (this.getCanvas().getSelection() != null && this.getCanvas().getSelection().isResizeable()) {
			double scale = this.getCanvas().scale;
			KesoShape selection = this.getCanvas().getSelection();
			
			int px = selection.getAbsoluteX();
			int py = selection.getAbsoluteY();
			int pwidth = selection.getWidth();
			int pheight = selection.getHeight();
			
			int delta = (int) Math.ceil(3 / scale);
			
			if (this.getCanvas().getSelection().isHorizontalResizeable() &&
					this.getCanvas().getSelection().isVerticalResizeable()) {
				
				if (insideRectangle(px, py, delta, delta, mousex, mousey)) {
					this.setResizeState(RESIZE_NW);
				} else if (insideRectangle(px + pwidth , py, delta, delta, mousex, mousey)) {
					this.setResizeState(RESIZE_NE);
				} else if (insideRectangle(px + pwidth , py + pheight, delta, delta, mousex, mousey)) {
					this.setResizeState(RESIZE_SE);
				} else if (insideRectangle(px, py + pheight, delta, delta, mousex, mousey)) {
					this.setResizeState(RESIZE_SW);
				}
			}
			
			if (this.getCanvas().getSelection().isHorizontalResizeable()) {
				if (insideRectangle(px + pwidth, py + pheight / 2, delta, delta, mousex, mousey)) {
					this.setResizeState(RESIZE_E);
				} else if (insideRectangle(px, py + pheight / 2, delta, delta, mousex, mousey)) {
					this.setResizeState(RESIZE_W);
				}
			}
			
			if (this.getCanvas().getSelection().isVerticalResizeable()) {
				if (insideRectangle(px + pwidth / 2, py, delta, delta, mousex, mousey)) {
					this.setResizeState(RESIZE_N);
				}  else if (insideRectangle(px + pwidth / 2, py + pheight, delta, delta, mousex, mousey)) {
					this.setResizeState(RESIZE_S);
				}
			}
		}
	}
	
	public void resizeShape(int mousex, int mousey) {
		int deltax = 0;
		int deltay = 0;
		int deltawidth = 0;
		int deltaheight = 0;
		
		switch(this.getResizeState()) {
			case RESIZE_NE:
				{
					int dx = mousex - this.resizemovingpoint.x;
					int dy = mousey - this.resizemovingpoint.y;
					
					deltay = dy;
					deltawidth = dx;
					deltaheight = -1 * dy;
				}
				break;
			case RESIZE_E:
				{
					int dx = mousex - this.resizemovingpoint.x;

					deltawidth = dx;
				}
				break;
			case RESIZE_SE:
				{
					int dx = mousex - this.resizemovingpoint.x;
					int dy = mousey - this.resizemovingpoint.y;
					
					deltawidth = dx;
					deltaheight = dy;
				}
				break;
			case RESIZE_S:
				{
					int dy = mousey - this.resizemovingpoint.y;
					
					deltawidth = 0;
					deltaheight = dy;
				}
				break;
			case RESIZE_SW:
				{
					int dx = mousex - this.resizemovingpoint.x;
					int dy = mousey - this.resizemovingpoint.y;
					
					deltax = dx;
					deltawidth = -1 * dx;
					deltaheight = dy;
				}
				break;	
			case RESIZE_W:
				{
					int dx = mousex - this.resizemovingpoint.x;
					
					deltax = dx;

					deltawidth = -1 * dx;
				}
				break;	
			case RESIZE_NW:
				{
					int dx = mousex - this.resizemovingpoint.x;
					int dy = mousey - this.resizemovingpoint.y;
					
					deltax = dx;
					deltay = dy;
					deltawidth = -1 * dx;
					deltaheight = -1 * dy;
				}
				break;
			case RESIZE_N:
				{
					int dy = mousey - this.resizemovingpoint.y;
					
					deltay = dy;
					deltaheight = -1 * dy;
				}
				break;
				
			default:
				break;
		}
		this.resizemovingpoint.x = mousex;
		this.resizemovingpoint.y = mousey;
		
		KesoShape selection = this.getCanvas().getSelection();
		KesoShape parent = selection.getParent();
		int x = selection.getX() + deltax;
		int y = selection.getY() + deltay;
		int width = selection.getWidth() + deltawidth;
		int height = selection.getHeight() + deltaheight;
		
		
		selection.setLocation(x, y);
		selection.setSize(width, height);
		
		this.canvas.fireDataChanged();
	}
	
	public boolean insideRectangle(int mx, int my, int dx, int dy, int px, int py) {
		int eleft = mx - dx;
		int etop = my - dy;
		int eright = mx + dx;
		int ebottom = my + dy;
		
		return (px >= eleft) && (px <= eright) && (py >= etop) && (py <= ebottom);
	}

	public void mouseEnter(MouseEvent e) {
		
	}

	public void mouseExit(MouseEvent e) {
		
	}
	
	public void mouseHover(MouseEvent e) {
		
	}

	public void mouseDoubleClick(MouseEvent e) {
		
		
	}

	public void mouseDown(MouseEvent e) {
		if (e.button == 1) {
			this.ismousebuttonpressed = true;
			this.shapemovingpoint.x = e.x;
			this.shapemovingpoint.y = e.y;
			
			this.resizemovingpoint.x = e.x;
			this.resizemovingpoint.y = e.y;
			if (this.getCanvas().getSelection() != null) {
				this.clickdifference.x = this.getCanvas().getSelection().getAbsoluteX() - e.x;
				this.clickdifference.y = this.getCanvas().getSelection().getAbsoluteY() - e.y;
			}
		}
		
	}

	public void mouseUp(MouseEvent e) {
		if (e.button == 1) {
			this.ismousebuttonpressed = false;
			/*
			if (this.getCanvas().getSelection() != null) {
				KesoShape selection = this.getCanvas().getSelection();
				if (selection.isInformationHiddingAllowed()) {
					Rectangle bounds = selection.getAbsoluteBounds();
					Image img;
					if (selection.isInformationHidden()) {
						img = KesoImageManager.getInstance().getImage("plus");
					} else {
						img = KesoImageManager.getInstance().getImage("minus");
					}
					int x = bounds.x + bounds.width - img.getImageData().width - 3;
					int y = bounds.y + 3;
					
					
					if (	(e.x >= x) && 
							(e.x <= x + img.getImageData().width) &&
							(e.y >= y) &&
							(e.y <= y + img.getImageData().height)) {
						selection.setHiddeInformation(!selection.isInformationHidden());
						((KesoMainWindow) this.getCanvas().getParentWindow()).setDataChanged(true);
					}
							
				}
			}
			*/
		}
	}
	
	public void setResizeState(int state) {
		if (this.getCanvas().getSelection() != null) {
			if (this.getCanvas().getSelection().isResizeable()) {
				this.resizestate = state;
				switch (state) {
					case RESIZE_E:
						this.setCursor(SWT.CURSOR_SIZEE);
							break;
					case RESIZE_SE:
						this.setCursor(SWT.CURSOR_SIZESE);
							break;
					case RESIZE_S:
						this.setCursor(SWT.CURSOR_SIZES);
							break;
					case RESIZE_SW:
						this.setCursor(SWT.CURSOR_SIZESW);
							break;
					case RESIZE_W:
						this.setCursor(SWT.CURSOR_SIZEW);
							break;
					case RESIZE_NW:
						this.setCursor(SWT.CURSOR_SIZENW);
							break;
					case RESIZE_N:
						this.setCursor(SWT.CURSOR_SIZEN);
							break;
					case RESIZE_NE:
						this.setCursor(SWT.CURSOR_SIZENE);
							break;
					case RESIZE_NO:
					default:
						this.setCursor(SWT.CURSOR_ARROW);
						break;
					
				}
			} else {
				this.resizestate = RESIZE_NO;
				this.setCursor(SWT.CURSOR_ARROW);
			}
			
			if (this.getResizeState() == RESIZE_NO) {
				this.getCanvas().setSelectionLocked(false);
			} else {
				this.getCanvas().setSelectionLocked(true);
			}
		}
	}
	
	public void setCursor(int cursor) {
		this.getCanvas().setCursor(Display.getCurrent().getSystemCursor(cursor));
	}

	
	public int getResizeState() {
		return this.resizestate;
	}

	public void keyPressed(KeyEvent e) {
		this.setPressedKey(e.keyCode);
	}

	public void keyReleased(KeyEvent e) {
		this.setPressedKey(KEY_NO);
		
		if (e.keyCode == SWT.DEL) {
			IKesoData data = null;
			if (e.widget == this.getDataTree().getTree()) {
				if (this.getDataTree().getTree().getSelectionCount() != 0) {
					TreeItem item = this.getDataTree().getTree().getSelection()[0];
					KesoDataTreeItem dataitem = (KesoDataTreeItem) item.getData();
					data = dataitem.getData();
				}
			} else {
				if (this.getCanvas().getSelection() != null) {
					data = this.getCanvas().getSelection().getData();
				}
			}
			if (data != null) {
				boolean showtext = true;
				boolean delete = false;
				if (data.getParent() != null) {
					if (KesoDataManager.acceptsRemovalOfChild(data.getParent(), data)) {
						MessageBox messagebox = new MessageBox(this.getDataTree().getTree().getShell(), 
								SWT.YES | SWT.NO | SWT.ICON_QUESTION);
						messagebox.setText("Delete Item?");
						messagebox.setMessage("Do you realy want to delete item '"  +
								data.getIdentifier() + "(" + data.getName() + ")'?");
						if (messagebox.open() == SWT.YES) {
							delete = true;
						} else {
							showtext = false;
						}
					}
				}
				if (delete) {
					this.getCanvas().setSelection((IKesoData) null);
					this.getDataTree().setSelection((IKesoData) null);
					data.getParent().removeChild(data);
					((KesoMainWindow) this.getCanvas().getParentWindow()).checkForExceptions();
				} else if (showtext) {
					MessageBox messagebox = new MessageBox(this.getDataTree().getTree().getShell(), 
							SWT.OK | SWT.ICON_ERROR);
					messagebox.setText("Can not delete Item!");
					messagebox.setMessage("You can not delete this item!");
					messagebox.open();
				}
			}
		}
	}
	
	public void setPressedKey(int key) {
		this.pressedkey = key;
	}
	
	public int getPressedKey() {
		return this.pressedkey;
	}
	
	public boolean isCtrlPressed() {
		return (this.getPressedKey() == SWT.CTRL);
	}

}
