package keso.editor.gui.shape.design.stylemanager;

import java.awt.Font;
import java.util.Enumeration;
import keso.editor.gui.graphics.KesoFont;
import keso.editor.gui.shape.design.style.IKesoShapeStyle;

/**
 * @author  Wilhelm Haas
 */
public class BmpExportKesoShapeStyleManager extends KesoShapeStyleManager {

	private static BmpExportKesoShapeStyleManager instance = new BmpExportKesoShapeStyleManager();
	
	private BmpExportKesoShapeStyleManager() {
		super();
		this.reset();
	}
	
	/**
	 * @return  the instance
	 * @uml.property  name="instance"
	 */
	public static IKesoShapeStyleManager getInstance() {
		return BmpExportKesoShapeStyleManager.instance;
	}
	
	public void save() {
		super.saveState("bmp");
	}

	public void init() {
		super.loadState("bmp");
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
