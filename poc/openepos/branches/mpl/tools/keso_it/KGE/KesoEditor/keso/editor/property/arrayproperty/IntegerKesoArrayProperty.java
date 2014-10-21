package keso.editor.property.arrayproperty;

import keso.editor.KGE;
import keso.editor.property.IKesoProperty;
import keso.editor.property.complexproperty.IKesoComplexProperty;

public class IntegerKesoArrayProperty extends KesoArrayProperty {

	public IntegerKesoArrayProperty(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public IntegerKesoArrayProperty(String name, String value) {
		super(name, value);
		// TODO Auto-generated constructor stub
	}

	public IntegerKesoArrayProperty(IKesoComplexProperty parent, String name,
			String value) {
		super(parent, name, value);
		// TODO Auto-generated constructor stub
	}

	public IntegerKesoArrayProperty(IKesoComplexProperty parent, String name) {
		super(parent, name);
		// TODO Auto-generated constructor stub
	}
	
	public void addItem(int value) {
		super.addItem((new Integer(value)).toString());
	}

	public String getIdentifier() {
		return KGE.PROPERTY_INTEGERARRAY;
	}

}
