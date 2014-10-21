package keso.editor.gui.shape.design.stylemanager;

import java.util.Hashtable;

import keso.editor.gui.shape.design.style.IKesoShapeStyle;

public interface IKesoShapeStyleManager {
	public IKesoShapeStyle getStyle(String identifier);
	public Hashtable getStyles();
	
	public void init();
	public void save();
	public void reset();
}
