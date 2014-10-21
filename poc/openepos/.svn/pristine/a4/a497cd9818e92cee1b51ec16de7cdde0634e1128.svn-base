package keso.editor.gui.shape.design.style;

import java.util.Hashtable;
import keso.editor.gui.graphics.KesoColor;
import keso.editor.gui.graphics.KesoFont;

/**
 * @author  Wilhelm Haas
 */
public class KesoShapeStyle implements IKesoShapeStyle {

	KesoColor standardColor = new KesoColor(255, 255, 255);
	
	Hashtable colors = new Hashtable();
	Hashtable fonts = new Hashtable();
	
	boolean shadow;
	
	public KesoShapeStyle() {
		this.setColor("COLOR_FOREGROUND", new KesoColor(0, 0, 0));
		this.setColor("COLOR_BACKGROUND", new KesoColor(255, 255, 255));
		this.setColor("COLOR_SHADOW", new KesoColor(100, 100, 100));
		this.setColor("COLOR_HIGHLIGHT", new KesoColor(0, 255, 0));
		this.setColor("COLOR_TEXT", new KesoColor(0, 0, 0));
		this.setColor("COLOR_HIGHLIGHTED_CONNECTION", new KesoColor(0, 255, 0));
		this.setColor("COLOR_CONNECTION", new KesoColor(0, 0, 0));
		this.setShadow(false);
	}

	public void setColor(String name, KesoColor color) {
		this.colors.put(name, color);
	}

	public void removeColor(String name) {
		this.colors.remove(name);
	}

	public KesoColor getColor(String name) {
		KesoColor result = (KesoColor) this.colors.get(name);
		if (result == null) {
			return this.standardColor;
		} else {
			return result;
		}
	}

	/**
	 * @param shadow  the shadow to set
	 * @uml.property  name="shadow"
	 */
	public void setShadow(boolean shadow) {
		this.shadow = shadow;
	}

	public boolean hasShadow() {
		return this.shadow;
	}

	public void setFont(String name, KesoFont font) {
		this.fonts.put(name, font);
	}

	public void removeFont(String name) {
		this.fonts.remove(name);
	}

	public KesoFont getFont(String name) {
		return (KesoFont) this.fonts.get(name);
	}

	public Hashtable getColors() {
		return this.colors;
	}

	public Hashtable getFonts() {
		return this.fonts;
	}

}
