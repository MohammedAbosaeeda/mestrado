package keso.editor.gui.compilation.parameter;

import java.util.Vector;

public class KesoParameter {
	private String description;
	private String type;
	private String name;
	private String separator;
	private String defaultvalue;
	private Vector possiblevalues;
	
	public KesoParameter(String description, String type, String name, String separator, Vector entries, String defaultvalue) {
		this.description = description;
		this.type = type;
		this.name = name;
		this.separator = separator;
		this.possiblevalues = entries;
		this.defaultvalue = defaultvalue;
	}
	
	public boolean hasDescription() {
		return this.description != null;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getType() {
		return this.type;
	}
	
	public String getName() {
		return this.name;
	}
	
	public boolean hasPossibleValues() {
		return (this.possiblevalues != null && this.possiblevalues.size() > 0);
	}
	
	public Vector getPossibleValues() {
		return this.possiblevalues;
	}
	
	public boolean hasDefaultValue() {
		return this.defaultvalue != null;
	}
	
	public String getDefaultValue() {
		return this.defaultvalue;
	}
	
	public boolean hasSeparator() {
		return this.separator.length() > 0;
	}
	
	public String getSeparator() {
		return this.separator;
	}
}
