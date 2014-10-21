package keso.editor.property;

import keso.editor.property.complexproperty.IKesoComplexProperty;

public class UnquotedStringKesoProperty extends StringKesoProperty {
	
	public UnquotedStringKesoProperty(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public UnquotedStringKesoProperty(String name, String value) {
		super(name, value);
		// TODO Auto-generated constructor stub
	}

	public UnquotedStringKesoProperty(IKesoComplexProperty parent, String name,
			String value) {
		super(parent, name, value);
		// TODO Auto-generated constructor stub
	}

	public UnquotedStringKesoProperty(IKesoComplexProperty parent, String name) {
		super(parent, name);
		// TODO Auto-generated constructor stub
	}
}
