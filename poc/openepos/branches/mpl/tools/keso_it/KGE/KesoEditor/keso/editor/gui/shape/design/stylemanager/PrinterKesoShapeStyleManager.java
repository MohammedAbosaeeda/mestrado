package keso.editor.gui.shape.design.stylemanager;

import java.awt.Font;
import java.util.Enumeration;
import keso.editor.gui.graphics.KesoFont;
import keso.editor.gui.shape.design.style.IKesoShapeStyle;

/**
 * @author  Wilhelm Haas
 */
public class PrinterKesoShapeStyleManager extends KesoShapeStyleManager {

	private static PrinterKesoShapeStyleManager instance = new PrinterKesoShapeStyleManager();
	
	private PrinterKesoShapeStyleManager() {
		super();
		this.reset();
	}
	
	/**
	 * @return  the instance
	 * @uml.property  name="instance"
	 */
	public static IKesoShapeStyleManager getInstance() {
		return PrinterKesoShapeStyleManager.instance;
	}
	
	public void save() {
		super.saveState("printer");
	}
	
	public void init() {
		super.loadState("printer");
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
