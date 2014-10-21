package keso.editor.property.complexproperty;

import java.util.Iterator;
import java.util.Vector;
import keso.editor.data.IKesoData;
import keso.editor.property.IKesoProperty;
import keso.editor.property.KesoProperty;
import keso.editor.property.propertymanager.KesoPropertyManager;

/**
 * @author  Wilhelm Haas
 */
public abstract class KesoComplexProperty extends KesoProperty implements
		IKesoComplexProperty {

	private Vector properties = new Vector();
	
	public KesoComplexProperty(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public KesoComplexProperty(String name, String value) {
		super(name, value);
		// TODO Auto-generated constructor stub
	}

	public KesoComplexProperty(IKesoComplexProperty parent, String name,
			String value) {
		super(parent, name, value);
		// TODO Auto-generated constructor stub
	}

	public KesoComplexProperty(IKesoComplexProperty parent, String name) {
		super(parent, name);
		// TODO Auto-generated constructor stub
	}

	
	
	public void add(IKesoProperty property) {
		
	//	if (this.accept(property.getName())) {
			if (property.getParent() != null) {
				property.getParent().remove(property);
			}
			this.getProperties().add(property);
			property.setOwner(this.getOwner());
			property.setParent(this);
			this.change.firePropertyChange("add property", null, property);
	//	}
	}

	public boolean remove(IKesoProperty property) {
		if (this.getProperties().remove(property)) {
			property.setParent(null);
			property.setOwner(null);
			this.change.firePropertyChange("remove property", property, null);
			return true;
		} else {
			return false;
		}
	}

	public IKesoProperty remove(int index) {
		IKesoProperty property =  (IKesoProperty) this.getProperties().remove(index);
		if (property != null) {
			property.setParent(null);
			property.setOwner(null);
			this.change.firePropertyChange("remove property", property, null);
			return property;
		} else {
			return null;
		}
	}
	
	public void setOwner(IKesoData owner) {
		super.setOwner(owner);
		for (Iterator i = this.getProperties().iterator(); i.hasNext(); ) {
			((IKesoProperty) i.next()).setOwner(owner);
		}
	}

	public IKesoProperty getProperty(int index) {
		return (IKesoProperty) this.getProperties().get(index);
	}

	/**
	 * @return  the properties
	 * @uml.property  name="properties"
	 */
	public Vector getProperties() {
		return this.properties;
	}

	public int size() {
		return this.getProperties().size();
	}
/*
	public boolean accept(String name) {
		return KesoPropertyManager.accepts(this, name);
	}
*/
	public Vector getPropertiesByName(String name) {
		Vector properties = new Vector();
		for (Iterator i = this.getProperties().iterator(); i.hasNext(); ) {
			IKesoProperty property = (IKesoProperty) i.next();
			if (property.getName().equals(name)) {
				properties.add(property);
			}
		}
		return properties;
	}
	
	public IKesoProperty getFirstPropertyByName(String name) {
		Vector found = this.getPropertiesByName(name);
		if (found.size() == 0) {
			return null; 
		} else {
			return (IKesoProperty) found.get(0);
		}
	}

}
