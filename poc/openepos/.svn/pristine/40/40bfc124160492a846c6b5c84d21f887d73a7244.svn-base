package keso.editor.gui.shape.design.stylemanager;

import java.awt.Font;
import java.util.Enumeration;
import keso.editor.gui.graphics.KesoFont;
import keso.editor.gui.shape.design.style.IKesoShapeStyle;

/**
 * @author  Wilhelm Haas
 */
public class ScreenKesoShapeStyleManager extends KesoShapeStyleManager {

	private static ScreenKesoShapeStyleManager instance = new ScreenKesoShapeStyleManager();
	
	private ScreenKesoShapeStyleManager() {
		super();
		this.reset();
	}
	
	/**
	 * @return  the instance
	 * @uml.property  name="instance"
	 */
	public static IKesoShapeStyleManager getInstance() {
		return ScreenKesoShapeStyleManager.instance;
	}
	
	public void save() {
		super.saveState("screen");
	}
	
	public void init() {
		super.loadState("screen");
	}
	
	public void reset() {
		super.reset();
		for (Enumeration e = this.styles.elements(); e.hasMoreElements(); ) {
			IKesoShapeStyle style = (IKesoShapeStyle) e.nextElement();
			style.setFont("FONT_STANDARD", new KesoFont("Dialog", 8, KesoFont.PLAIN));
			style.setFont("FONT_CONNECTION", new KesoFont("Dialog", 9, KesoFont.BOLD));
		}
	}

}
