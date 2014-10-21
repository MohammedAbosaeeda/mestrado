package keso.editor.data;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Vector;
import keso.editor.property.IKesoProperty;
import keso.editor.property.StringKesoProperty;
import keso.editor.property.complexproperty.*;

/**
 * @author  Wilhelm Haas
 */
public class KesoData implements IKesoData, PropertyChangeListener {

	private StringKesoProperty name;
	private String identifier;
	private IKesoData parent;
	private Vector children = new Vector();
	
	private IKesoComplexProperty propertyContainer = new StringKesoComplexProperty("");
	
	private PropertyChangeSupport change = new PropertyChangeSupport(this);
	
	public IKesoData getChild(int index) {
		return (IKesoData) this.getChildren().get(index);
	}
	
	public KesoData(String identifier, String name) {
		this.name = new StringKesoProperty("Name", "");
		this.name.addPropertyChangeListener(this);
		this.addProperty(this.name);
		this.setIdentifier(identifier);
		this.setName(name);
		this.getPropertyContainer().setOwner(this);
	}
	
	/**
	 * @return  the propertyContainer
	 * @uml.property  name="propertyContainer"
	 */
	public IKesoComplexProperty getPropertyContainer() {
		return this.propertyContainer;
	}
	
	/**
	 * @param propertyContainer  the propertyContainer to set
	 * @uml.property  name="propertyContainer"
	 */
	public void setPropertyContainer(IKesoComplexProperty rootProperty) {
		this.propertyContainer = rootProperty;
		this.getPropertyContainer().setOwner(this);
	}
	
	/**
	 * @param identifier  the identifier to set
	 * @uml.property  name="identifier"
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
		this.getPropertyContainer().setName(identifier);
	}

	/**
	 * @return  the identifier
	 * @uml.property  name="identifier"
	 */
	public String getIdentifier() {
		return this.identifier;
	}

	public void setName(String name) {
		this.name.setValue(name);
	}

	/**
	 * @return  the name
	 * @uml.property  name="name"
	 */
	public String getName() {
		return this.name.getValue();
	}

	public void addChild(IKesoData child) {
		this.getChildren().add(child);
		child.setParent(this);
		this.change.firePropertyChange("add child", null, child);
	}

	public IKesoData removeChild(int index) {
		IKesoData child = (IKesoData) this.getChildren().remove(index);
		this.change.firePropertyChange("remove child", child, null);
		return child;
	}

	public boolean removeChild(IKesoData child) {
		if (this.getChildren().remove(child)) {
			this.change.firePropertyChange("remove child", child, null);
			return true;
		} else {
			return false;
		}
	}

	public int sizeOfChildren() {
		return this.getChildren().size();
	}

	/**
	 * @return  the children
	 * @uml.property  name="children"
	 */
	public Vector getChildren() {
		return this.children;
	}

	/**
	 * @param parent  the parent to set
	 * @uml.property  name="parent"
	 */
	public void setParent(IKesoData parent) {
		if (this.getParent() != null) {
			this.getParent().removeChild(this);
		}
		this.parent = parent;
	}

	/**
	 * @return  the parent
	 * @uml.property  name="parent"
	 */
	public IKesoData getParent() {
		return this.parent;
	}

	public void addProperty(IKesoProperty property) {
		if (this.name == property || !property.getName().equals("Name")) {
			this.getPropertyContainer().add(property);
		}
	}
	
	public String toString() {
		return this.getName();
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.change.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.change.removePropertyChangeListener(listener);
	}

	public void propertyChange(PropertyChangeEvent e) {
		this.change.firePropertyChange("name changed", null, this.getName());
	}

	public void changeChildPosition(int index, IKesoData child) {
		if (this.getChildren().remove(child)) {
			this.getChildren().add(index, child);
		}
	}

	public int indexOf(KesoData data) {
		return this.getChildren().indexOf(data);
	}
	
	public void moveDown() {
		if (this.getParent() != null) {
			this.getParent().changeChildPosition(0, this);
		}
	}

	public void moveUp() {
		if (this.getParent() != null) {
			this.getParent().changeChildPosition(this.getParent().getChildren().size() - 1, this);
		}
	}
	
	public void moveOneStepDown() {
		if (this.getParent() != null) {
			int index = this.getParent().indexOf(this) - 1;
			this.getParent().changeChildPosition(((index < 0)? 0 : index), this);
		}
	}
	
	public void moveOneStepUp() {
		if (this.getParent() != null) {
			int index = this.getParent().indexOf(this) + 1;
			this.getParent().changeChildPosition(((index > this.sizeOfChildren())? this.sizeOfChildren() : index), this);
		}
	}

}
