package keso.editor.property.complexproperty;

import keso.editor.KGE;
import keso.editor.property.IKesoProperty;

public class UnquotedStringKesoComplexProperty extends
		KesoComplexProperty {

	public UnquotedStringKesoComplexProperty(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public UnquotedStringKesoComplexProperty(String name, String value) {
		super(name, value);
		// TODO Auto-generated constructor stub
	}

	public UnquotedStringKesoComplexProperty(IKesoComplexProperty parent, String name,
			String value) {
		super(parent, name, value);
		// TODO Auto-generated constructor stub
	}

	public UnquotedStringKesoComplexProperty(IKesoComplexProperty parent, String name) {
		super(parent, name);
		// TODO Auto-generated constructor stub
	}

	public String getIdentifier() {
		return KGE.PROPERTY_COMPLEXUNQUOTEDSTRING;
	}

	
	
}
