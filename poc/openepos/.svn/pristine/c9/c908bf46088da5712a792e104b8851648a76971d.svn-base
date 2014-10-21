package keso.editor.property.complexproperty;

import keso.editor.KGE;
import keso.editor.property.IKesoProperty;

public class BooleanKesoComplexProperty extends KesoComplexProperty {

	public BooleanKesoComplexProperty(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public BooleanKesoComplexProperty(String name, boolean value) {
		super(name, (new Boolean(value)).toString());
		// TODO Auto-generated constructor stub
	}

	public BooleanKesoComplexProperty(IKesoComplexProperty parent, String name,
			boolean value) {
		super(parent, name, (new Boolean(value)).toString());
		// TODO Auto-generated constructor stub
	}

	public BooleanKesoComplexProperty(IKesoComplexProperty parent, String name) {
		super(parent, name);
		// TODO Auto-generated constructor stub
	}
	
	public boolean getBoolean() {
		return this.getValue().toLowerCase().equals("true");
	}
	
	public void setValue(String value) {
		super.setValue(value.toLowerCase().equals("true")? "true" : "false");	
	}

	public String getIdentifier() {
		return KGE.PROPERTY_COMPLEXBOOLEAN;
	}

}
