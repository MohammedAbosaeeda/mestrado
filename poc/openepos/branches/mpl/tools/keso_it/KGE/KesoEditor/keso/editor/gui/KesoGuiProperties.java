package keso.editor.gui;

import java.util.Properties;

public class KesoGuiProperties extends Properties {

	private static String filename;
	private static KesoGuiProperties properties = new KesoGuiProperties();
	
	private KesoGuiProperties() {
		// TODO Auto-generated constructor stub
	}

	private KesoGuiProperties(Properties arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	
	public static KesoGuiProperties getInstance() {
		return KesoGuiProperties.properties;
	}
}
