package keso.editor.gui.shape.design.stylemanager;

import java.util.Enumeration;
import java.util.Hashtable;
import keso.editor.KGE;
import keso.editor.gui.KesoGuiProperties;
import keso.editor.gui.graphics.KesoColor;
import keso.editor.gui.graphics.KesoFont;
import keso.editor.gui.shape.design.style.*;

/**
 * @author  Wilhelm Haas
 */
public class KesoShapeStyleManager implements IKesoShapeStyleManager {

	IKesoShapeStyle defaultStyle = new KesoShapeStyle();
	
	Hashtable styles = new Hashtable();
	
	public KesoShapeStyleManager() {
		super();
		this.reset();
	}
	
	protected void setStyle(String identifier, IKesoShapeStyle style) {
		this.styles.put(identifier, style);
	}

	public IKesoShapeStyle getStyle(String identifier) {
		IKesoShapeStyle style = (IKesoShapeStyle) this.styles.get(identifier);
		if (style == null) {
			return this.defaultStyle;
		} else {
			return style;
		}
	}

	/**
	 * @return  the styles
	 * @uml.property  name="styles"
	 */
	public Hashtable getStyles() {
		return this.styles;
	}
	
	public void loadState(String identifier) {
		KesoGuiProperties properties = KesoGuiProperties.getInstance();
		String path = "";
		for (Enumeration e = this.styles.keys(); e.hasMoreElements(); ) {
			String key = (String) e.nextElement();
			IKesoShapeStyle style = this.getStyle(key);
			Hashtable colors = style.getColors();
			Hashtable fonts = style.getFonts();
			path = identifier + "." + key + ".";
			for (Enumeration c = colors.keys(); c.hasMoreElements(); ) {
				String color_key = (String) c.nextElement();
				
				try {
					int red;
					int green;
					int blue;
					
					String tmp = properties.getProperty(path + "color." + color_key + ".red");
					if (tmp != null) {
						red = Integer.parseInt(tmp); 
					} else {
						continue;
					}
					tmp = properties.getProperty(path + "color." + color_key + ".green");
					if (tmp != null) {
						green = Integer.parseInt(tmp); 
					} else {
						continue;
					}
					tmp = properties.getProperty(path + "color." + color_key + ".blue");
					if (tmp != null) {
						blue = Integer.parseInt(tmp); 
					}
					 else {
							continue;
						}
					style.setColor(color_key, new KesoColor(red, green, blue));
				} catch (Exception exc) {
					
				}
				
			}
			
			for (Enumeration f = fonts.keys(); f.hasMoreElements(); ) {
				String font_key = (String) f.nextElement();
				
				try {
					String name;
					int size;
					int type;
					
					String tmp = properties.getProperty(path + "font." + font_key + ".name");
					if (tmp != null) {
						name = tmp;
					} else {
						continue;
					}
					tmp = properties.getProperty(path + "font." + font_key + ".size");
					if (tmp != null) {
						size = Integer.parseInt(tmp); 
					} else {
						continue;
					}
					tmp = properties.getProperty(path + "font." + font_key + ".type");
					if (tmp != null) {
						type = Integer.parseInt(tmp); 
					}
					 else {
						continue;
					}
					style.setFont(font_key, new KesoFont(name, size, type));
				} catch (Exception exc) {
					
				}
			}
			
		}
		
	}
	
	public void saveState(String identifier) {
		KesoGuiProperties properties = KesoGuiProperties.getInstance();
		String path = "";
		for (Enumeration e = this.styles.keys(); e.hasMoreElements(); ) {
			String key = (String) e.nextElement();
			IKesoShapeStyle style = this.getStyle(key);
			Hashtable colors = style.getColors();
			Hashtable fonts = style.getFonts();
			path = identifier + "." + key + ".";
			for (Enumeration c = colors.keys(); c.hasMoreElements(); ) {
				String color_key = (String) c.nextElement();
				KesoColor color = style.getColor(color_key);
				properties.setProperty(path + "color." + color_key + ".red", Integer.toString(color.red));
				properties.setProperty(path + "color." + color_key + ".green", Integer.toString(color.green));
				properties.setProperty(path + "color." + color_key + ".blue", Integer.toString(color.blue));
			}
			
			for (Enumeration f = fonts.keys(); f.hasMoreElements(); ) {
				String font_key = (String) f.nextElement();
				KesoFont font = (KesoFont) style.getFont(font_key);
				properties.setProperty(path + "font." + font_key + ".name", font.getFontname());
				properties.setProperty(path + "font." + font_key + ".size", Integer.toString(font.getSize()));
				properties.setProperty(path + "font." + font_key + ".type", Integer.toString(font.getType()));
			}
			
		}
		
	}

	public void init() {
		// TODO Auto-generated method stub
		
	}

	public void save() {
		// TODO Auto-generated method stub
		
	}

	public void reset() {
		this.setStyle(KGE.WORLD, new WorldKesoShapeStyle());
		this.setStyle(KGE.NODE, new NodeKesoShapeStyle());
		this.setStyle(KGE.DOMAIN, new DomainKesoShapeStyle());
		this.setStyle(KGE.PUBLICDOMAIN, new DomainKesoShapeStyle());
		this.setStyle(KGE.ALARM, new KesoShapeStyle());
		this.setStyle(KGE.APPMODE, new KesoShapeStyle());
		this.setStyle(KGE.COUNTER, new KesoShapeStyle());
		this.setStyle(KGE.EVENT, new KesoShapeStyle());
		this.setStyle(KGE.IMPORT, new KesoShapeStyle());
		this.setStyle(KGE.ISR, new KesoShapeStyle());
		this.setStyle(KGE.NETWORK, new KesoShapeStyle());
		this.setStyle(KGE.OSEKOS, new KesoShapeStyle());
		this.setStyle(KGE.RESOURCE, new KesoShapeStyle());
		this.setStyle(KGE.SERVICE, new KesoShapeStyle());
		this.setStyle(KGE.TASK, new KesoShapeStyle());
	}

}
