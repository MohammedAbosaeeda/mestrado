package keso.editor.property;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Vector;
import keso.editor.data.IKesoData;
import keso.editor.property.complexproperty.IKesoComplexProperty;

/**
 * @author  Wilhelm Haas
 */
public abstract class KesoProperty implements IKesoProperty {
	
	private IKesoData owner;
	private String name = "";
	private String value = "";
	private IKesoComplexProperty parent;
	
	protected PropertyChangeSupport change = new PropertyChangeSupport(this);
	
	public KesoProperty(String name) {
		this.setParent(null);
		this.setName(name);
		this.setUnset();
	}
	
	public KesoProperty(String name, String value) {
		this.setParent(null);
		this.setName(name);
		this.setValue(value);
	}
	
	public KesoProperty(IKesoComplexProperty parent, String name, String value) {
		this.setParent(parent);
		this.setName(name);
		this.setValue(value);
	}
	
	public KesoProperty(IKesoComplexProperty parent, String name) {
		this.setParent(parent);
		this.setName(name);
		this.setUnset();
	}
	
	/**
	 * @param parent  the parent to set
	 * @uml.property  name="parent"
	 */
	public void setParent(IKesoComplexProperty parent) {
		this.parent = parent;
	}
	
	/**
	 * @return  the parent
	 * @uml.property  name="parent"
	 */
	public IKesoComplexProperty getParent() {
		return this.parent;
	}
	
	/**
	 * @param value  the value to set
	 * @uml.property  name="value"
	 */
	public void setValue(String value) {
		if (this.value == null || ((value != null) && !this.value.equals(value))) {
			String oldvalue = this.value;
			this.value = value;
			change.firePropertyChange("value changed", oldvalue, value);
		}
	}
	
	/**
	 * @return  the value
	 * @uml.property  name="value"
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * @param name  the name to set
	 * @uml.property  name="name"
	 */
	public void setName(String name) {
		if (!this.name.equals(name)) {
			String oldname = this.name;
			this.name = name;
			change.firePropertyChange("name changed", oldname, name);
		}
	}

	/**
	 * @return  the name
	 * @uml.property  name="name"
	 */
	public String getName() {
		return this.name;
	}

	public void setUnset() {
		this.setValue(IKesoProperty.UNSET);
	}

	public boolean isUnset() {
		return (this.getValue() == IKesoProperty.UNSET);
	}

	/**
	 * @param owner  the owner to set
	 * @uml.property  name="owner"
	 */
	public void setOwner(IKesoData dataparent) {
		this.owner = dataparent;
	}

	/**
	 * @return  the owner
	 * @uml.property  name="owner"
	 */
	public IKesoData getOwner() {
		return this.owner;
	}

	public abstract String getIdentifier();
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.change.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.change.removePropertyChangeListener(listener);
	}
	
	public boolean getBoolean() {
		return this.getValue().toLowerCase().equals("true");
	}
	
	public String getPropertyPath() {
		if (this.getParent() == null) {
			if (this.getOwner() == null) {
				return this.getName();
			} else {
				return this.getOwner().getIdentifier() + "(" + this.getOwner().getName() + ")";
			}
		} else {
			return this.getParent().getPropertyPath() + "." + this.getName();
		}
	}
	
}
