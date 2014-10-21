package keso.editor.property.arrayproperty;

import java.util.Iterator;
import java.util.Vector;
import keso.editor.property.IKesoProperty;
import keso.editor.property.KesoProperty;
import keso.editor.property.complexproperty.IKesoComplexProperty;

/**
 * @author  Wilhelm Haas
 */
public abstract class KesoArrayProperty extends KesoProperty implements
		IKesoArrayProperty {

	private Vector values = new Vector();
	
	public KesoArrayProperty(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public KesoArrayProperty(String name, String value) {
		super(name, value);
		// TODO Auto-generated constructor stub
	}

	public KesoArrayProperty(IKesoComplexProperty parent, String name,
			String value) {
		super(parent, name, value);
		// TODO Auto-generated constructor stub
	}

	public KesoArrayProperty(IKesoComplexProperty parent, String name) {
		super(parent, name);
		// TODO Auto-generated constructor stub
	}
	
	public void setValue(String value) {
		value = value.trim();
		if (value.length() != 0) {
			if (value.charAt(0) == '{') {
				value = value.substring(1);
			} 
			if (value.charAt(value.length() - 1) == '}') {
				value = value.substring(0, value.length() - 1);
			}
			this.setValues(this.split(value));
			super.setValue(this.getValue());
		} else {
			super.setValue("");
		}
	}
	
	private Vector split(String value) {
		Vector vv = new Vector();
		String [] values = value.split(",");
		for (int i = 0; i < values.length; i++) {
			values[i] = values[i].trim();
			vv.add(values[i]);
		}
		return vv;
	}
	
	public void addItem(String value) {
		this.getValues().add(value);
		this.setValue(this.getValue());
	}
	
	/**
	 * @param values  the values to set
	 * @uml.property  name="values"
	 */
	public void setValues(Vector values) {
		this.values = values;
	}
	
	public String getValue() {
		boolean first = true;
		StringBuffer sb = new StringBuffer();
		for(Iterator i = this.getValues().iterator(); i.hasNext(); ) {
			if (first) {
				first = false;
			} else {
				sb.append(", ");
			}
			sb.append((String) i.next());
		}
		return sb.toString();
	}
	
	/**
	 * @return  the values
	 * @uml.property  name="values"
	 */
	public Vector getValues() {
		return this.values;
	}

	public String get(int index) {
		return (String) this.getValues().get(index);
	}

	public String remove(int index) {
		return (String) this.getValues().remove(index);
	}

	public int size() {
		return this.getValues().size();
	}

}
