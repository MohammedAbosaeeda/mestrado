package keso.editor.gui.canvas.drawing;

import keso.editor.gui.graphics.IKesoGraphics;
import keso.editor.gui.shape.KesoTextShapeContainer;
import keso.editor.gui.shape.design.stylemanager.IKesoShapeStyleManager;

/**
 * @author  Wilhelm Haas
 */
public class KesoDrawingManager {

	IKesoGraphics graphics;
	IKesoShapeStyleManager stylemanager;
	KesoTextShapeContainer textshapes = new KesoTextShapeContainer();
	KesoLineContainer lines = new KesoLineContainer();
	KesoLineContainer speciallines = new KesoLineContainer();
	
	public double shapescale = 1;
	public double fontscale = 1;
	
	public int start_offsetx = 0;
	public int start_offsety = 0;
	
	public KesoDrawingManager(IKesoGraphics graphics, IKesoShapeStyleManager stylemanager) {
		this.setGraphics(graphics);
		this.setStyleManager(stylemanager);
	}
	
	/**
	 * @param graphics  the graphics to set
	 * @uml.property  name="graphics"
	 */
	public void setGraphics(IKesoGraphics graphics) {
		this.graphics = graphics;
	}
	
	/**
	 * @return  the graphics
	 * @uml.property  name="graphics"
	 */
	public IKesoGraphics getGraphics() {
		return this.graphics;
	}
	
	public IKesoShapeStyleManager getStyleManager() {
		return this.stylemanager;
	}
	
	public KesoTextShapeContainer getTextShapeContainer() {
		return this.textshapes;
	}
	
	public KesoLineContainer getLineContainer() {
		return this.lines;
	}
	
	public KesoLineContainer getSpecialLineContainer() {
		return this.speciallines;
	}
	
	public void setStyleManager(IKesoShapeStyleManager stylemanager) {
		this.stylemanager = stylemanager;
	}
	
	public void setTextShapeContainer(KesoTextShapeContainer textshapes) {
		this.textshapes = textshapes;
	}
	
	public void setLineContainer(KesoLineContainer linecontainer) {
		this.lines = linecontainer;
	}
	
	public void setSpecialLineContainer(KesoLineContainer speciallines) {
		this.speciallines = speciallines;
	}

}
