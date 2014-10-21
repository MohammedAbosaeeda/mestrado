package keso.editor.property.propertymanager;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import keso.editor.KGE;
import keso.editor.data.IKesoData;
import keso.editor.filter.KesoFilter;
import keso.editor.filter.manager.KesoFilterManager;
import keso.editor.property.IKesoProperty;
import keso.editor.property.UnquotedStringKesoProperty;
import keso.editor.property.complexproperty.IKesoComplexProperty;
import keso.editor.property.complexproperty.UnquotedStringKesoComplexProperty;
import keso.editor.property.exceptioncontainer.KesoPropertyException;

/**
 * @author  Wilhelm Haas
 */
public class KesoPropertyManagerEntry {
	public static final int INFINIT = -1;
	private String name;
	private Vector entries = new Vector();
	private int max = KesoPropertyManagerEntry.INFINIT;
	private int min = 0;
	
	private boolean notnull = false;
	private boolean mustexist = false;
	private boolean unique = false;
	private boolean globaly = false;
	
	private boolean isdefault = false;
	private Vector possiblevalues;
	private String filter;
	private String type;
	private String description;
	private String defaultValue;
	
	
	public KesoPropertyManagerEntry(String name) {
		this.setName(name);
	}
	
	public void setPropertyType(String type) {
		this.type = type;
	}
	
	public String getPropertyType() {
		return this.type;
	}
	
	/**
	 * @param name  the name to set
	 * @uml.property  name="name"
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return  the name
	 * @uml.property  name="name"
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * @return  the entries
	 * @uml.property  name="entries"
	 */
	public Vector getEntries() {
		return this.entries;
	}
	
	/**
	 * @param description  the description to set
	 * @uml.property  name="description"
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * @return  the description
	 * @uml.property  name="description"
	 */
	public String getDescription() {
		return this.description;
	}
	
	public boolean hasDescription() {
		return (this.getDescription() != null);
	}
	
	public void addEntry(KesoPropertyManagerEntry entry) {
		this.getEntries().add(entry);
	}
	
	public KesoPropertyManagerEntry deleteEntry(int index) {
		return (KesoPropertyManagerEntry) this.getEntries().remove(index);
	}
	
	public boolean deleteEntry(KesoPropertyManagerEntry entry) {
		return this.getEntries().remove(entry);
	}
	
	public KesoPropertyManagerEntry getEntry(String name) {
		
		for (Iterator i = this.getEntries().iterator(); i.hasNext(); ) {
			KesoPropertyManagerEntry entry = (KesoPropertyManagerEntry) i.next();
			if (entry.getName().equals(name)) {
				return entry;
			}
		}
		
		return null;
	}
	
	/**
	 * @return  the max
	 * @uml.property  name="max"
	 */
	public int getMax() {
		return this.max;
	}
	
	/**
	 * @param max  the max to set
	 * @uml.property  name="max"
	 */
	public void setMax(int max) {
		this.max = max;
	}
	
	/**
	 * @return  the min
	 * @uml.property  name="min"
	 */
	public int getMin() {
		return this.min;
	}
	
	/**
	 * @param min  the min to set
	 * @uml.property  name="min"
	 */
	public void setMin(int min) {
		this.min = min;
	}
	
	public boolean isDefault() {
		return isdefault;
	}
	
	public void setDefault(boolean value) {
		this.isdefault = value;
	}
	
	public void setPossibleValues(Vector values) {
		this.possiblevalues = values;
	}
	
	public Vector getPossibleValues(IKesoProperty property) {
		if (property == null) {
			return new Vector();
		}
		if (this.hasFilter()) {
			KesoFilter filter = KesoFilterManager.get(this.getFilter());
			if (filter == null) {
				return this.possiblevalues;
			} else {
				return filter.filter(property.getOwner());
			}
		} else {
			return this.possiblevalues;
		}
	}
	
	/**
	 * @param filter  the filter to set
	 * @uml.property  name="filter"
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}
	
	/**
	 * @return  the filter
	 * @uml.property  name="filter"
	 */
	public String getFilter() {
		return this.filter;
	}
	
	public boolean hasFilter() {
		return (this.getFilter() != null);
	}
	
	/**
	 * @param defaultValue  the defaultValue to set
	 * @uml.property  name="defaultValue"
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	/**
	 * @return  the defaultValue
	 * @uml.property  name="defaultValue"
	 */
	public String getDefaultValue() {
		return this.defaultValue;
	}
	
	public boolean hasDefaultValue() {
		return (this.getDefaultValue() != null);
	}
	
	public void setNotNull() {
		this.notnull = true;
	}
	
	public void setMustExist() {
		this.mustexist = true;
	}
	
	public void setLocalyUnique() {
		this.unique = true;
		this.globaly = false;
	}
	
	public void setGlobalyUnique() {
		this.unique = true;
		this.globaly = true;
	}
	
	public boolean isNotNull() {
		return this.notnull;
	}
	
	public boolean isMustExist() {
		return this.mustexist;
	}
	
	public boolean isLocalyUnique() {
		return this.unique && (this.globaly == false);
	}
	
	public boolean isGlobalyUnique() {
		return this.unique && this.globaly;
	}
	
	public boolean acceptsNewChild(IKesoComplexProperty parentproperty, String name) {
		if (this.getMax() == KesoPropertyManagerEntry.INFINIT) {
			return true;
		} else {
			Vector children = parentproperty.getOwner().getPropertyContainer().getPropertiesByName(name);
			if (children.size() >= this.getMax()) {
				return false;
			} else {
				return true;
			}
		}
	}
	
	public boolean acceptsRemovalOfChild(IKesoComplexProperty parentproperty, String name) {
		if (this.getMin() <= 0) {
			return true;
		} else {
			Vector children = parentproperty.getOwner().getPropertyContainer().getPropertiesByName(name);
			
			if (children.size() - 1 < this.getMin()) {
				return false;
			} else {
				return true;
			}
		}
	}
	
	public Vector check(IKesoComplexProperty parentproperty) {
		Vector exceptions = new Vector();
		
		for (Iterator i = this.getEntries().iterator(); i.hasNext(); ) {
			KesoPropertyManagerEntry entry = (KesoPropertyManagerEntry) i.next();
			Vector properties = parentproperty.getPropertiesByName(entry.getName());
			
			if (properties.size() < entry.getMin()) {
				KesoPropertyException exception = new KesoPropertyException(parentproperty);
				exception.setExceptionNo(KesoPropertyException.EXCEPTION_TO_LESS);
				exception.setMin(entry.getMin());
				exception.setAdditionalString(entry.getName());
				exceptions.add(exception);
			} else if (entry.getMax() != KesoPropertyManagerEntry.INFINIT && properties.size() > entry.getMax()) {
				KesoPropertyException exception = new KesoPropertyException(parentproperty);
				exception.setExceptionNo(KesoPropertyException.EXCEPTION_TO_MANY);
				exception.setMax(entry.getMax());
				exception.setAdditionalString(entry.getName());
				exceptions.add(exception);
			}
			
			for (Iterator j = properties.iterator(); j.hasNext(); ) {
				IKesoProperty property  = (IKesoProperty) j.next();
				
				if (property instanceof UnquotedStringKesoComplexProperty
						|| property instanceof UnquotedStringKesoProperty) {
					if (!property.getValue().matches("([a-z]|[A-Z])([a-z]|[A-Z]|[0-9]|_)*")) {
						KesoPropertyException exception = new KesoPropertyException(property);
						exception.setExceptionNo(KesoPropertyException.EXCEPTION_INVALID_VALUE);
						exceptions.add(exception);
					}
				}
				
				
				if (entry.isNotNull() && property.isUnset()) {
					KesoPropertyException exception = new KesoPropertyException(property);
					exception.setExceptionNo(KesoPropertyException.EXCEPTION_IS_UNSET);
					exceptions.add(exception);
				}
				if (entry.isMustExist()) {
					Vector possiblevalues = entry.getPossibleValues(property);
					boolean found = false;
					for (Iterator k = possiblevalues.iterator(); k.hasNext(); ) {
						IKesoData data = (IKesoData) k.next();
						if (data.getName().equals(property.getValue())) {
							found = true;
							break;
						}
					}
					if (!found) {
						KesoPropertyException exception = new KesoPropertyException(property);
						exception.setExceptionNo(KesoPropertyException.EXCEPTION_INVALID_VALUE);
						exceptions.add(exception);
					}
				}
				if (property instanceof IKesoComplexProperty) {
					exceptions.addAll(entry.check((IKesoComplexProperty) property));
				}
			}
			
		}
		
		return exceptions;
	}

}
