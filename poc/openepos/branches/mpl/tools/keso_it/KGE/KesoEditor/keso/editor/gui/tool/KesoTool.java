package keso.editor.gui.tool;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import keso.editor.gui.canvas.KesoCanvas;
import keso.editor.gui.datatree.KesoDataTree;
import keso.editor.gui.toolmanager.KesoToolManager;

/**
 * @author  Wilhelm Haas
 */
public abstract class KesoTool implements IKesoTool, PropertyChangeListener {
	KesoDataTree datatree;
	KesoCanvas canvas;
	private KesoToolManager manager;
	
	public KesoTool() {
	}

	public void start() {
		this.datatree.addPropertyChangeListener(this);
	}

	public void setDataTree(KesoDataTree datatree) {
		this.datatree = datatree;
	}

	/**
	 * @param canvas  the canvas to set
	 * @uml.property  name="canvas"
	 */
	public void setCanvas(KesoCanvas canvas) {
		this.canvas = canvas;
	}
	
	public void stop() {
		this.datatree.removePropertyChangeListener(this);
	}

	
	public abstract void propertyChange(PropertyChangeEvent e);

	public KesoDataTree getDataTree() {
		return this.datatree;
	}
	
	/**
	 * @return  the canvas
	 * @uml.property  name="canvas"
	 */
	public KesoCanvas getCanvas() {
		return this.canvas;
	}
	
	/**
	 * @param manager  the manager to set
	 * @uml.property  name="manager"
	 */
	public void setManager(KesoToolManager manager) {
		this.manager = manager;
	}
	
	/**
	 * @return  the manager
	 * @uml.property  name="manager"
	 */
	public KesoToolManager getManager() {
		return this.manager;
	}
}
