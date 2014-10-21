package keso.editor.property;

import java.beans.PropertyChangeListener;
import java.util.Vector;
import keso.editor.data.IKesoData;
import keso.editor.property.complexproperty.IKesoComplexProperty;

/**
 * @author  Wilhelm Haas
 */
public interface IKesoProperty {
	public static final String UNSET = "";
	
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
	/**
	 * @param parent
	 * @uml.property  name="parent"
	 */
	public void setParent(IKesoComplexProperty parent);
	public IKesoComplexProperty getParent();
	/**
	 * @param value
	 * @uml.property  name="value"
	 */
	public void setValue(String value);
	/**
	 * @return
	 * @uml.property  name="value"
	 */
	public String getValue();
	public void setUnset();
	public boolean isUnset();
	/**
	 * @param dataparent
	 * @uml.property  name="owner"
	 */
	public void setOwner(IKesoData dataparent);
	public IKesoData getOwner();
	
	public String getIdentifier();
	public boolean getBoolean();
	public String getPropertyPath();
	
	public void addPropertyChangeListener(PropertyChangeListener listener);
	public void removePropertyChangeListener(PropertyChangeListener listener);
}
