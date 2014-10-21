package keso.editor.gui.shape.design.stylemanager;

import java.awt.Font;
import java.util.Enumeration;
import keso.editor.gui.graphics.KesoFont;
import keso.editor.gui.shape.design.style.IKesoShapeStyle;

/**
 * @author  Wilhelm Haas
 */
public class EpsExportKesoShapeStyleManager extends KesoShapeStyleManager {

	private static EpsExportKesoShapeStyleManager instance = new EpsExportKesoShapeStyleManager();
	
	private EpsExportKesoShapeStyleManager() {
		super();
		
		this.reset();
	}
	
	/**
	 * @return  the instance
	 * @uml.property  name="instance"
	 */
	public static IKesoShapeStyleManager getInstance() {
		return EpsExportKesoShapeStyleManager.instance;
	}

	public void save() {
		super.saveState("eps");
	}
	
	public void init() {
		super.loadState("eps");
	}
	
	public void reset() {
		super.reset();
		for (Enumeration e = this.styles.elements(); e.hasMoreElements(); ) {
			IKesoShapeStyle style = (IKesoShapeStyle) e.nextElement();
			style.setShadow(false);
			style.setFont("FONT_STANDARD", new KesoFont("Dialog", 10, KesoFont.PLAIN));
			style.setFont("FONT_CONNECTION", new KesoFont("Dialog", 9, KesoFont.PLAIN));
		}
	}
}
