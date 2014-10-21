package keso.editor.gui.tool;

import java.beans.PropertyChangeListener;
import keso.editor.gui.canvas.KesoCanvas;
import keso.editor.gui.datatree.KesoDataTree;
import keso.editor.gui.toolmanager.KesoToolManager;

/**
 * @author  Wilhelm Haas
 */
public interface IKesoTool extends PropertyChangeListener {
	public void start();
	/**
	 * @param datatree
	 * @uml.property  name="dataTree"
	 */
	public void setDataTree(KesoDataTree datatree);
	/**
	 * @param canvas
	 * @uml.property  name="canvas"
	 */
	public void setCanvas(KesoCanvas canvas);
	
	public KesoDataTree getDataTree();
	public KesoCanvas getCanvas();
	
	/**
	 * @param manager
	 * @uml.property  name="manager"
	 */
	public void setManager(KesoToolManager manager);
	public KesoToolManager getManager();
	
	public void stop();
}
