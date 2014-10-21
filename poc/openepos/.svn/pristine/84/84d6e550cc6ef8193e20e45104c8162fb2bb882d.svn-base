package keso.editor.property.exceptioncontainer;

import keso.editor.data.IKesoData;
import keso.editor.property.IKesoProperty;

/**
 * @author  Wilhelm Haas
 */
public class KesoPropertyException {
	public static final int NO_EXCEPTION = 0;
	public static final int EXCEPTION_IS_UNSET = 1;
	public static final int EXCEPTION_DOESNT_EXIST = 2;
	public static final int EXCEPTION_NOT_UNIQUE_LOCAL = 3;
	public static final int EXCEPTION_NOT_UNIQUE_GLOBAL = 4;
	public static final int EXCEPTION_TO_LESS = 5;
	public static final int EXCEPTION_TO_MANY = 6;
	public static final int EXCEPTION_INVALID_VALUE = 7;
	public static final int EXCEPTION_NOT_UNIQUE = 8;
	public static final int EXCEPTION_MODULE_DOES_NOT_EXIST = 9;
	public static final int EXCEPTION_INVALID_FILE = 10;
	
	IKesoProperty property;
	private int exception;
	private int min;
	private int max;
	private int count;
	private String additionalString;
	
	public KesoPropertyException(IKesoProperty property) {
		this.setProperty(property);
	}

	/**
	 * @param property  the property to set
	 * @uml.property  name="property"
	 */
	public void setProperty(IKesoProperty property) {
		this.property = property;
	}
	
	/**
	 * @return  the property
	 * @uml.property  name="property"
	 */
	public IKesoProperty getProperty() {
		return this.property;
	}
	
	public IKesoData getPropertyOwner() {
		return this.getProperty().getOwner();
	}
	
	public void setExceptionNo(int excno) {
		this.exception = excno;
	}
	
	public int getExceptionNo() {
		return this.exception;
	}
	
	/**
	 * @param min  the min to set
	 * @uml.property  name="min"
	 */
	public void setMin(int min) {
		this.min = min;
	}
	
	/**
	 * @param max  the max to set
	 * @uml.property  name="max"
	 */
	public void setMax(int max) {
		this.max = max;
	}
	
	/**
	 * @param count  the count to set
	 * @uml.property  name="count"
	 */
	public void setCount(int count) {
		this.count = count;
	}
	
	/**
	 * @return  the min
	 * @uml.property  name="min"
	 */
	public int getMin() {
		return this.min;
	}
	
	/**
	 * @return  the max
	 * @uml.property  name="max"
	 */
	public int getMax() {
		return this.max;
	}
	
	/**
	 * @return  the count
	 * @uml.property  name="count"
	 */
	public int getCount() {
		return this.count;
	}
	
	/**
	 * @param additionalString  the additionalString to set
	 * @uml.property  name="additionalString"
	 */
	public void setAdditionalString(String str) {
		this.additionalString = str;
	}
	
	/**
	 * @return  the additionalString
	 * @uml.property  name="additionalString"
	 */
	public String getAdditionalString() {
		if (this.additionalString == null) {
			return "";
		} else {
			return this.additionalString;
		}
	}
	
	public String toString() {
		String path = this.getProperty().getPropertyPath();
		switch (this.getExceptionNo()) {
			case EXCEPTION_INVALID_FILE:
				return path + " contains an invalid path to '" + this.getAdditionalString() + "'";
			case EXCEPTION_MODULE_DOES_NOT_EXIST: 
				return path + ": '" + this.getAdditionalString() + "' does not exist";
			case EXCEPTION_IS_UNSET:
				return path + " is unset";
			case EXCEPTION_NOT_UNIQUE_GLOBAL:
				return path + " must be globaly unique";
			case EXCEPTION_NOT_UNIQUE_LOCAL:
				return path + " must be localy unique";
			case EXCEPTION_NOT_UNIQUE:
				if (this.getAdditionalString() != null) {
					return path + " must be unique ( => " + this.getAdditionalString() + ")";
				} else {
					return path + " must be unique";
				}
			case EXCEPTION_INVALID_VALUE:
				return path + " has an invalid value";
			case EXCEPTION_TO_LESS:
				return path + " must have at least " + this.getMin() + " of property '"  + this.getAdditionalString();
			case EXCEPTION_TO_MANY:
				return path + " must not have more than " + this.getMax() + " of property '"  + this.getAdditionalString();
			case NO_EXCEPTION:
			default:
				return "";
		}
	}
}
