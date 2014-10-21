package keso.editor.property;

import keso.editor.KGE;
import keso.editor.property.complexproperty.IKesoComplexProperty;

public class IntegerKesoProperty extends KesoProperty {

	public IntegerKesoProperty(String name) {
		super(name);
	}

	public IntegerKesoProperty(String name, int value) {
		super(name, (new Integer(value)).toString());
	}

	public IntegerKesoProperty(IKesoComplexProperty parent, String name, int value) {
		super(parent, name, (new Integer(value)).toString());
	}

	public IntegerKesoProperty(IKesoComplexProperty parent, String name) {
		super(parent, name);
	}
	
	public void setValue(String value) {
		try {
			super.setValue((new Integer(Integer.parseInt(value))).toString());
		} catch(Exception e) {
			super.setValue("0");
		}
	}
	
	public void setValue(int value) {
		super.setValue((new Integer(value)).toString());
	}
	
	public int getInteger() {
		try {
			return Integer.parseInt(this.getValue());
		} catch(Exception e) {
			
		}
		return 0;
	}

	public String getIdentifier() {
		return KGE.PROPERTY_INTEGER;
	}

}
