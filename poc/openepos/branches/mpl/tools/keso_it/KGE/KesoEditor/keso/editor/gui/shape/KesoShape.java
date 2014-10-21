package keso.editor.gui.shape;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Iterator;
import java.util.Vector;
import keso.editor.KGE;
import keso.editor.data.IKesoData;
import keso.editor.gui.canvas.KesoCanvas;
import keso.editor.gui.canvas.drawing.KesoDrawingManager;
import keso.editor.gui.graphics.IKesoGraphics;
import keso.editor.gui.shape.design.IKesoShapeDesign;
import keso.editor.gui.shape.design.KesoShapeDesign;
import keso.editor.gui.shape.design.KesoShapeDesignManager;
import keso.editor.gui.shape.design.stylemanager.IKesoShapeStyleManager;
import keso.editor.property.IntegerKesoProperty;
import keso.editor.property.StringKesoProperty;
import keso.editor.property.complexproperty.IKesoComplexProperty;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;


/**
 * @author  Wilhelm Haas
 */
public class KesoShape implements PropertyChangeListener {
	IKesoShapeDesign design = new KesoShapeDesign(this);
	
	KesoShape parent;
	IKesoData data; 
	KesoCanvas canvas;
	
	Vector children = new Vector();
	
	IntegerKesoProperty x;
	IntegerKesoProperty y;
	IntegerKesoProperty width;
	IntegerKesoProperty height;
	StringKesoProperty resizeablehorizontal;
	StringKesoProperty resizeablevertical;
	StringKesoProperty strechable;
	StringKesoProperty editorversion;
	StringKesoProperty allowinformationhiding;
	StringKesoProperty informationhidden;
	
	Insets padding = new Insets(16, 2, 2, 2);
	
	Dimension mindimension = new Dimension(0, 0);
	
	PropertyChangeSupport change = new PropertyChangeSupport(this);
	
	public KesoShape(KesoCanvas canvas, IKesoData data) {
		this.setCanvas(canvas);
		this.setData(data);
	}

	public KesoShape(KesoShape parent, IKesoData data) {
		this.setParent(parent);
		this.setData(data);
	}
	
	public Rectangle getBounds() {
		return new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}
	
	public Rectangle getAbsoluteBounds() {
		return new Rectangle(this.getAbsoluteX(), this.getAbsoluteY(), this.getWidth(), this.getHeight());
	}
	
	/**
	 * @param parent  the parent to set
	 * @uml.property  name="parent"
	 */
	public void setParent(KesoShape parent) {
		if (this.parent != null) {
			this.parent.remove(this);
			this.setCanvas(null);
		}
		this.parent = parent;
		if (this.parent != null) {
			this.parent.add(this);
			this.setCanvas(this.parent.getCanvas());
		}
	}
	
	/**
	 * @return  the parent
	 * @uml.property  name="parent"
	 */
	public KesoShape getParent() {
		return this.parent;
	}
	
	/**
	 * @param data  the data to set
	 * @uml.property  name="data"
	 */
	public void setData(IKesoData data) {
		if (this.data != null) {
			this.removeAll();
			this.data.removePropertyChangeListener(this);
		}
		this.data = data;
		if (this.data != null) {
			this.setRenderer(KesoShapeDesignManager.generateRenderere(this));
			this.data.addPropertyChangeListener(this);
			this.x = (IntegerKesoProperty) this.data.getPropertyContainer().getFirstPropertyByName("KGEData_x");
			this.y = (IntegerKesoProperty) this.data.getPropertyContainer().getFirstPropertyByName("KGEData_y");
			this.width = (IntegerKesoProperty) this.data.getPropertyContainer().getFirstPropertyByName("KGEData_width");
			this.height = (IntegerKesoProperty) this.data.getPropertyContainer().getFirstPropertyByName("KGEData_height");
			this.resizeablehorizontal = (StringKesoProperty) this.data.getPropertyContainer().getFirstPropertyByName("KGEData_resizeable_horizontal");
			this.resizeablevertical = (StringKesoProperty) this.data.getPropertyContainer().getFirstPropertyByName("KGEData_resizeable_vertical");	
			this.strechable = (StringKesoProperty) this.data.getPropertyContainer().getFirstPropertyByName("KGEData_strechable");
			this.editorversion = (StringKesoProperty) this.data.getPropertyContainer().getFirstPropertyByName("KGEData_version");
			this.allowinformationhiding = (StringKesoProperty) this.data.getPropertyContainer().getFirstPropertyByName("KGEData_allowhiding");
			this.informationhidden = (StringKesoProperty) this.data.getPropertyContainer().getFirstPropertyByName("KGEData_hidden");
			
			this.allowinformationhiding.addPropertyChangeListener(this);
			this.informationhidden.addPropertyChangeListener(this);

			this.fill();
			//this.initCoordinatesAutomatically();
		}
	}	
	
	public void setHiddeInformation(boolean hidde) {
		if (hidde) {
			this.informationhidden.setValue("true");
		} else {
			this.informationhidden.setValue("false");
		}
		
		this.initMinDimension();
		//this.updateMinDimension();
		this.redraw();
	}
	
	public boolean amIHidden() {
		if (this.parent != null) {
			return this.parent.isInformationHidden();
		} else {
			return false;
		}
	}
	
	public boolean isInformationHidden() {
		boolean hidden = this.informationhidden.getBoolean();
		if (this.parent != null) {
			hidden = hidden || this.parent.isInformationHidden();
		}
		return hidden;
	}
	
	public void setAllowInformationHiding(boolean allow) {
		if (allow) {
			this.allowinformationhiding.setValue("true");
		} else {
			this.allowinformationhiding.setValue("false");
		}
	}
	
	public boolean isInformationHidingAllowed() {
		return this.allowinformationhiding.getBoolean();
	}
	
	/**
	 * @return  the padding
	 * @uml.property  name="padding"
	 */
	public Insets getPadding() {
		return this.padding;
	}
	
	public Rectangle getChildArea() {
		return new Rectangle(this.padding.left, this.padding.top,
				this.getWidth() - this.padding.left - this.padding.right,
				this.getHeight() - this.padding.top - this.padding.bottom);
	}
	
	public void fill() {
		for (Iterator i = this.data.getChildren().iterator(); i.hasNext(); ) {
			IKesoData child = (IKesoData) i.next();
			new KesoShape(this, child);
		}
	}
	
	public KesoShape getChild(int index) {
		return (KesoShape) this.children.get(index);
	}
	
	public boolean remove(KesoShape child) {
		if (this.children.remove(child)) {
			child.removePropertyChangeListener(this);
			return true;
		}else {
			return false;
		}
	}
	
	
	public void removeAll() {
		this.children.removeAllElements();
	}
	
	/**
	 * @return  the children
	 * @uml.property  name="children"
	 */
	public Vector getChildren() {
		return this.children;
	}
	
	public void add(KesoShape child) {
		child.addPropertyChangeListener(this);
		this.children.add(child);
	}
	
	public void add(int index, KesoShape child) {
		child.addPropertyChangeListener(this);
		this.children.add(index, child);
	}
	
	public boolean isResizeable() {
		return !this.isInformationHidden() && (this.isHorizontalResizeable() ||
				this.isVerticalResizeable());
	}
	
	public boolean isHorizontalResizeable() {
		return this.resizeablehorizontal.getBoolean();
	}
	
	public boolean isVerticalResizeable() {
		return this.resizeablevertical.getBoolean();
	}
	
	public int getAbsoluteX() {
		int x = this.getX();
		KesoShape parent = this.parent;
		while (parent != null) {
			x += parent.getX() + parent.getChildArea().x;
			parent = parent.getParent();
		}
		return x;
	}
	
	public int getAbsoluteY() {		
		int y = this.getY();
		KesoShape parent = this.parent;
		while (parent != null) {
			y += parent.getY() + parent.getChildArea().y;
			parent = parent.getParent();
		}
		return y;
	}
	
	/**
	 * @return  the x
	 * @uml.property  name="x"
	 */
	public int getX() {
		if (this.amIHidden()) {
			return 0;
		} else {
			return this.x.getInteger();
		}
	}
	
	/**
	 * @return  the y
	 * @uml.property  name="y"
	 */
	public int getY() {
		if (this.amIHidden()) {
			return 0;
		} else {
			return this.y.getInteger();
		}
	}
	
	/**
	 * @return  the width
	 * @uml.property  name="width"
	 */
	public int getWidth() {
		if (this.amIHidden()) {
			return 0;
		} else {
			int width = this.width.getInteger();
			int minwidth = this.getMinWidth();
		
			if (width < minwidth || this.isInformationHidden()) {
				return minwidth;
			} else {
				return width;
			}
		}
	}
	
	/**
	 * @return  the height
	 * @uml.property  name="height"
	 */
	public int getHeight() {
		if (this.amIHidden()) {
			return 0;
		} else {
			int height = this.height.getInteger();
			int minheight = this.getMinHeight();
			
			if (height < minheight || this.isInformationHidden()) {
				return minheight;
			} else {
				return height;
			}
		}
	}
	
	public Point translatePoint(int x, int y) {
		return new Point(x - this.getAbsoluteX(), y - this.getAbsoluteY());
	}
	
	public void setBasicX(int x) {
		x = (x < 0) ? 0 : x;
		this.x.setValue(x);
		this.change.firePropertyChange("shape resized", null, null);
	}
	
	public void setBasicY(int y) {
		y = (y < 0) ? 0 : y;
		this.y.setValue(y);
		this.change.firePropertyChange("shape resized", null, null);
	}
	
	public void setLocation(int x, int y) {
		this.setBasicX(x);
		this.setBasicY(y);
		this.redraw();
	}
	
	public void move(int dx, int dy) {
		this.setLocation(this.getX() + dx, this.getY() + dy);
	}
	
	public void setX(int x) {
		this.setBasicX(x);
		this.redraw();
	}
	
	public void setY(int y) {
		this.setBasicY(y);
		this.redraw();
	}
	
	public void setWidth(int width) {
		this.setBasicWidth(width);
		this.redraw();
	}
	
	public void setHeight(int height) {
		this.setBasicHeight(height);
		this.redraw();
	}
	
	public void redraw() {
		if (this.getCanvas() != null) {
			this.getCanvas().redraw();
		}
	}
	
	public void setBasicWidth(int width) {
		width = (width < 0) ? 0 : width;
		this.width.setValue(width);
		this.change.firePropertyChange("shape resized", null, null);
	}
	
	public void setBasicHeight(int height) {
		height = (height < 0) ? 0 : height;
		this.height.setValue(height);
		this.change.firePropertyChange("shape resized", null, null);
	}
	
	public void resize(int dwidth, int dheight) {
		this.setBasicWidth(this.getWidth() + dwidth);
		this.setBasicHeight(this.getHeight() + dheight);
		this.change.firePropertyChange("shape resized", null, null);
		this.redraw();
	}
	
	/**
	 * @param canvas  the canvas to set
	 * @uml.property  name="canvas"
	 */
	public void setCanvas(KesoCanvas canvas) {
		this.canvas = canvas;
	}
	
	/**
	 * @return  the canvas
	 * @uml.property  name="canvas"
	 */
	public KesoCanvas getCanvas() {
		return this.canvas;
	}
	
	/**
	 * @return  the data
	 * @uml.property  name="data"
	 */
	public IKesoData getData() {
		return this.data;
	}
	
	public void setRenderer(IKesoShapeDesign renderer) {
		this.design = renderer;
	}
	
	/**
	 * @return  the design
	 * @uml.property  name="design"
	 */
	public IKesoShapeDesign getDesign() {
		return this.design;
	}
	
	public int getMinWidth() {
		return this.mindimension.width;
	}
	
	public int getMinHeight() {
		return this.mindimension.height;
	}
	
	
	public void setSize(int width, int height) {
		this.setBasicWidth(width);
		this.setBasicHeight(height);
		this.change.firePropertyChange("shape resized", null, null);
		this.redraw();
	}
	
	public void initMinDimension() {
		for (Iterator i = this.children.iterator(); i.hasNext(); ) {
			KesoShape shape = (KesoShape) i.next();
			shape.initMinDimension();
		}
		this.updateMinDimension();
	}
	
	public void updateMinDimension() {
		int minwidth = 0;
		int minheight = 0;
		boolean equal = true;
		
		for (Iterator i = this.children.iterator(); i.hasNext(); ) {
			KesoShape shape = (KesoShape) i.next();
			minwidth = Math.max(minwidth, shape.getX() + shape.getWidth());
			minheight = Math.max(minheight, shape.getY() + shape.getHeight());
		}
		
		minwidth += this.padding.left + this.padding.right; /*childarea.width; */
		minheight += this.padding.top + this.padding.bottom;
	
		if (this.getDesign() != null) {
			if (minwidth < this.getDesign().getMinWidth()) {
				minwidth = this.getDesign().getMinWidth();
			}
			if (minheight < this.getDesign().getMinHeight()) {
				minheight = this.getDesign().getMinHeight();
			}
		}
		
		if (this.mindimension.width != minwidth || this.mindimension.height != minheight) {
			equal = false;
		}
		this.mindimension.width = minwidth;
		this.mindimension.height = minheight;
		if (!equal) {
			change.firePropertyChange("shape resized", null, null);
		}
	}
	
	
	public void paintShape(KesoDrawingManager drawingmanager, int offsetx, int offsety) {
		
		IKesoGraphics gc = drawingmanager.getGraphics();
		
		Rectangle clippingarea = gc.getClipping();
		offsetx += this.getX();
		offsety += this.getY();
		gc.setClipping(offsetx, offsety, this.getWidth(), this.getHeight());
		Rectangle childarea = this.getChildArea();
		if (this.getDesign() != null) {
			this.getDesign().draw(drawingmanager, offsetx, offsety);
		}
		offsetx += childarea.x;
		offsety += childarea.y;
		gc.setClipping(offsetx, offsety, childarea.width, childarea.height);
		for (Iterator i = this.children.iterator(); i.hasNext(); ) {
			KesoShape shape = (KesoShape) i.next();
			shape.paintShape(drawingmanager, offsetx, offsety);
		}
		gc.setClipping(clippingarea);
	}
	
	public void propertyChange(PropertyChangeEvent e) {
		if (e.getPropertyName().equals("name changed")) {
			this.updateMinDimension();
			this.redraw();
		} else if (e.getPropertyName().equals("shape resized")) {
			this.updateMinDimension();
		} else if (e.getPropertyName().equals("add child")) {
			IKesoData data = (IKesoData) e.getNewValue();
			KesoShape shape = this.getCanvas().find(data);
			if (shape == null) {
				shape = new KesoShape(this, data);
				shape.initCoordinatesAutomatically();
			}
			this.redraw();
		} else if (e.getPropertyName().equals("remove child")) {
			IKesoData data = (IKesoData) e.getOldValue();
			for (Iterator i = this.children.iterator(); i.hasNext(); ) {
				KesoShape child = (KesoShape) i.next();
				if (child.getData() == data) {
					this.remove(child);
					break;
				}
			}
			this.redraw();
		} else if (e.getPropertyName().equals("value changed")) {
			this.initMinDimension();
		}
	}

	public void initCoordinatesAutomatically() {
		Vector children = new Vector();
		int x = 0;
		int y = 0;
		for (Iterator i = this.children.iterator(); i.hasNext(); ) {
			KesoShape child = (KesoShape) i.next();
			child.updateMinDimension();
			child.initCoordinatesAutomatically();
			Rectangle bounds = child.getBounds();
			boolean found = false;
			while (!found) {
				found = true;
				for (Iterator j = children.iterator(); j.hasNext(); ) {
					Rectangle childrect = (Rectangle) j.next();
					if (childrect.intersects(bounds)) {
						
						x = childrect.x + childrect.width;
						if (x > 300) {
							x = 0;
							y = childrect.y + childrect.height;
						}
						bounds.x = x;
						bounds.y = y;
						found = false;
						break;
					}
				}
			}
			bounds.x += (bounds.x == 0) ? 0 : 4;
			bounds.y += (bounds.y == 0) ? 0 : 4;
			child.setBasicX(bounds.x);
			child.setBasicY(bounds.y);
			children.add(bounds);
		}
		this.updateMinDimension();
		this.setVersion(KGE.KESOEDITOR_VERSION);
	}

	
	public boolean contains(int x, int y) {
		return this.getBounds().contains(x, y);
	}
	
	public KesoShape getShapeAtPosition(int x, int y) {
		KesoShape result = null;
		if (this.contains(x, y)) {
			result = this;
			x -= this.getX() + this.padding.left;
			y -= this.getY() + this.padding.top;
			for (Iterator i = this.children.iterator(); i.hasNext(); ) {
				KesoShape tmp_result = ((KesoShape) i.next()).getShapeAtPosition(x, y);
				result = tmp_result != null ? tmp_result : result;
			}
		}
		return result;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.change.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.change.removePropertyChangeListener(listener);
	}
	
	public void bringToFront() {
		if (this.parent != null) {
			this.parent.remove(this);
			this.parent.add(this);
			this.getData().moveUp();
			this.redraw();
		}
	}
	
	public void bringForward() {
		if (this.parent != null) {
			int index = this.parent.indexOf(this) + 1;
			this.parent.remove(this);
			this.parent.add(((index > this.parent.size())? this.parent.size() : index), this);
			this.getData().moveOneStepUp();
			this.redraw();
		}
	}
	
	public void sendBackward() {
		if (this.parent != null) {
			int index = this.parent.indexOf(this) - 1;
			this.parent.remove(this);
			this.parent.add(((index < 0)? 0 : index), this);
			this.getData().moveOneStepDown();
			this.redraw();
		}
	}
	
	public void sendToBack() {
		if (this.parent != null) {
			this.parent.remove(this);
			this.parent.add(0, this);
			this.getData().moveDown();
			this.redraw();
		}
	}
	
	public int size() {
		return this.children.size();
	}
	
	public int indexOf(KesoShape child) {
		return this.children.indexOf(child);
	}

	public void setStrechable(boolean stretchable) {
		if (stretchable) {
			//this.setSize(this.getWidth(), this.getHeight());
			this.strechable.setValue("true");
		}	else {
			this.strechable.setValue("false");	
		}
	}
	
	public boolean isStrechable() {
		return this.strechable.getBoolean();
	}	
	
	public String getVersion() {
		return this.editorversion.getValue();
	}
	
	public void setVersion(String version) {
		this.editorversion.setValue(version);
	}
}
