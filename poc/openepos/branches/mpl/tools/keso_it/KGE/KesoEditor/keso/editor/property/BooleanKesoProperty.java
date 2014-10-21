package keso.editor.property;

import keso.editor.KGE;
import keso.editor.property.complexproperty.IKesoComplexProperty;

public class BooleanKesoProperty extends KesoProperty {

	public BooleanKesoProperty(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public BooleanKesoProperty(String name, boolean value) {
		super(name, (new Boolean(value)).toString());
		// TODO Auto-generated constructor stub
	}

	public BooleanKesoProperty(IKesoComplexProperty parent, String name, boolean value) {
		super(parent, name, (new Boolean(value)).toString());
		// TODO Auto-generated constructor stub
	}

	public BooleanKesoProperty(IKesoComplexProperty parent, String name) {
		super(parent, name);
		// TODO Auto-generated constructor stub
	}
	
	public void setValue(String value) {
		super.setValue(value.toLowerCase().equals("true")? "true" : "false");
	}
	
	public boolean getBoolean() {
		return this.getValue().toLowerCase().equals("true");
	}

	public String getIdentifier() {
		return KGE.PROPERTY_BOOLEAN;
	}

}
