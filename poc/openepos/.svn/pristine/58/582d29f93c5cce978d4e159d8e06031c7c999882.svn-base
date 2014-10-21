package keso.editor.data;

import java.beans.PropertyChangeListener;
import java.util.Vector;
import keso.editor.property.IKesoProperty;
import keso.editor.property.complexproperty.*;

/**
 * @author  Wilhelm Haas
 */
public interface IKesoData {
	/**
	 * @param identifier
	 * @uml.property  name="identifier"
	 */
	public void setIdentifier(String identifier);
	/**
	 * @return
	 * @uml.property  name="identifier"
	 */
	public String getIdentifier();
	/**
	 * @param name
	 * @uml.property  name="name"
	 */
	public void setName(String name);
	/**
	 * @return
	 * @uml.property  name="name"
	 */
	public String getName();
	public void addChild(IKesoData child);
	public void changeChildPosition(int index, IKesoData child);
	public int indexOf(KesoData data);
	public void moveDown();
	public void moveUp();
	public void moveOneStepDown();
	public void moveOneStepUp();
	public void addProperty(IKesoProperty property);
	public IKesoData removeChild(int index);
	public boolean removeChild(IKesoData child);
	public int sizeOfChildren();
	public Vector getChildren();
	public IKesoData getChild(int index);
	/**
	 * @param parent
	 * @uml.property  name="parent"
	 */
	public void setParent(IKesoData parent);
	public IKesoData getParent();
	
	public IKesoComplexProperty getPropertyContainer();
	/**
	 * @param propertyContainer
	 * @uml.property  name="propertyContainer"
	 */
	public void setPropertyContainer(IKesoComplexProperty propertyContainer);
	
	public void addPropertyChangeListener(PropertyChangeListener listener);
	public void removePropertyChangeListener(PropertyChangeListener listener);
}
