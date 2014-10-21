package keso.editor.gui.toolmanager;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import keso.editor.gui.canvas.KesoCanvas;
import keso.editor.gui.datatree.KesoDataTree;
import keso.editor.gui.tool.IKesoTool;
import keso.editor.gui.tool.SelectionKesoTool;

/**
 * @author  Wilhelm Haas
 */
public class KesoToolManager {
	KesoDataTree datatree;
	KesoCanvas canvas;
	IKesoTool currenttool;
	
	PropertyChangeSupport change = new PropertyChangeSupport(this);
	
	public KesoToolManager() {
		
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
	
	public void setTool(IKesoTool tool) {
		if (this.currenttool != null) {
			this.currenttool.stop();
		}
		if (tool == null) {
			tool = new SelectionKesoTool();
		}
		
		this.currenttool = tool;
		this.currenttool.setManager(this);
		//if (this.currenttool != null) {
		
		this.currenttool.setDataTree(this.datatree);
		this.currenttool.setCanvas(this.canvas);
		this.currenttool.start();
		
		//}
		this.change.firePropertyChange("tool changed", null, null);
	}
	
	public IKesoTool getTool() {
		return this.currenttool;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.change.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.change.removePropertyChangeListener(listener);
	}
}
