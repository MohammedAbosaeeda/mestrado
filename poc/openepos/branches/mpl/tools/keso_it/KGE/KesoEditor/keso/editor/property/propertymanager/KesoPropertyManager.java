package keso.editor.property.propertymanager;

import java.beans.PropertyChangeSupport;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import keso.editor.KGE;
import keso.editor.data.IKesoData;
import keso.editor.filter.manager.KesoFilterManager;
import keso.editor.filter.manager.reader.KesoFilterReader;
import keso.editor.property.*;
import keso.editor.property.arrayproperty.IntegerKesoArrayProperty;
import keso.editor.property.complexproperty.*;
import keso.editor.property.propertymanager.reader.KesoPropertyManagerReader;

/**
 * @author  Wilhelm Haas
 */
public abstract class KesoPropertyManager {
	private static Vector booleanOutput = new Vector();
	
	public static final KesoPropertyManagerEntry root = new KesoPropertyManagerEntry("root");
	
	public static Vector getPossibleValues(IKesoProperty property) {
		if (	property instanceof BooleanKesoProperty ||
				property instanceof BooleanKesoComplexProperty) {
			if (KesoPropertyManager.booleanOutput.size() == 0) {
				KesoPropertyManager.booleanOutput.add("false");
				KesoPropertyManager.booleanOutput.add("true");
			}
			return KesoPropertyManager.booleanOutput;
		}
		
		KesoPropertyManagerEntry entry = KesoPropertyManager.getEntryOfProperty(property);
		
		if (entry == null) {
			return new Vector();
		}
		
		return entry.getPossibleValues(property);
	}
	
	public static void addEntry(KesoPropertyManagerEntry entry) {
		KesoPropertyManager.root.addEntry(entry);
	}
	
	public static KesoPropertyManagerEntry getEntryOfProperty(IKesoProperty property) {
		Vector stack = new Vector();
		IKesoProperty parent = property;
		while(parent != null) {
			stack.add(0, parent.getName());
			parent = parent.getParent();
		}
		KesoPropertyManagerEntry entry = KesoPropertyManager.root;
		while (stack.size() != 0 && entry != null) {
			entry = entry.getEntry((String) stack.remove(0));
		}
		return entry;
	}
	
	public static boolean acceptsNewChild(IKesoComplexProperty parent, String name) {
		KesoPropertyManagerEntry entry = KesoPropertyManager.getEntryOfProperty(parent);
		if (entry == null) {
			return true;
		}
		KesoPropertyManagerEntry subentry = entry.getEntry(name);
		if (subentry != null) {
			return subentry.acceptsNewChild(parent, name);
		}
		return true;
	}
	
	public static boolean acceptsRemovalOfChild(IKesoComplexProperty parent, String name) {
		KesoPropertyManagerEntry entry = KesoPropertyManager.getEntryOfProperty(parent);
		if (entry == null) {
			return true;
		}
		KesoPropertyManagerEntry subentry = entry.getEntry(name);
		if (subentry != null) {
			return subentry.acceptsRemovalOfChild(parent, name);
		}
		return true;
	}
	
	public static IKesoProperty generateProperty(IKesoComplexProperty parent, String name, String wishedType) {
		KesoPropertyManagerEntry entry = KesoPropertyManager.getEntryOfProperty(parent);
		if (entry == null) {
			return null;
		}
		KesoPropertyManagerEntry subentry = entry.getEntry(name);
		
		if (subentry != null) {
			wishedType = subentry.getPropertyType();
		}
		
		IKesoProperty property;
		if (wishedType.equals("Integer")) {
			property = new IntegerKesoProperty(name);
		} else if (wishedType.equals("IntegerArray")) {
			property = new IntegerKesoArrayProperty(name);
		} else if (wishedType.equals("ComplexBoolean")) {
			property = new BooleanKesoComplexProperty(name);
		} else if (wishedType.equals("ComplexUnquotedString")) {
			property = new UnquotedStringKesoComplexProperty(name);
		} else {
			property = new StringKesoProperty(name);
		}
		
		if (subentry != null && subentry.hasDefaultValue()) {
			property.setValue(subentry.getDefaultValue());
		}
		
		return property;
	}
	
	public static void fillUpData(IKesoData data) {
		KesoPropertyManagerEntry entry = KesoPropertyManager.root.getEntry(data.getIdentifier());
		if (entry != null) {
			fillUpProperty(entry, data.getPropertyContainer());
		}
		for (Iterator i = data.getChildren().iterator(); i.hasNext(); ) {
			fillUpData((IKesoData) i.next());
		}
	}
	
	public static void fillUpProperty(IKesoComplexProperty property) {
		KesoPropertyManagerEntry entry = KesoPropertyManager.getEntryOfProperty(property);
		if (entry != null) {
			fillUpProperty(entry, property);
		}
	}
	
	private static void fillUpProperty(KesoPropertyManagerEntry propertyentry, IKesoComplexProperty parentproperty) {
		for (Enumeration e = propertyentry.getEntries().elements(); e.hasMoreElements(); ) {
			KesoPropertyManagerEntry entry = (KesoPropertyManagerEntry) e.nextElement();
			if (entry.isDefault()) {
				Vector properties = parentproperty.getPropertiesByName(entry.getName());
				if (properties.size() == 0) {
					IKesoProperty property = KesoPropertyManager.generateProperty(parentproperty, entry.getName(), entry.getPropertyType());
					parentproperty.add(property);
					if (property instanceof IKesoComplexProperty) {
						fillUpProperty(entry, (IKesoComplexProperty) property);
					}
				} else {
					for (Iterator i = properties.iterator(); i.hasNext(); ) {
						IKesoProperty property = (IKesoProperty) i.next();
						if (property instanceof IKesoComplexProperty) {
							fillUpProperty(entry, (IKesoComplexProperty) property);
						}
					}
				}
			}
		}
	}
	
	public static Vector check(IKesoData data) {
		return KesoPropertyManager.root.getEntry(data.getIdentifier()).check(data.getPropertyContainer());
	}
	
	public static Vector checkAll(IKesoData parent) {
		Vector exceptions = new Vector();
		exceptions.addAll(KesoPropertyManager.root.getEntry(parent.getIdentifier()).check(parent.getPropertyContainer()));
		for (Iterator i = parent.getChildren().iterator(); i.hasNext(); ) {
			IKesoData data = (IKesoData) i.next();
			exceptions.addAll(KesoPropertyManager.checkAll(data));
		}
		return exceptions;
	}
	
	
	
	public static void init() {
		try {
			KesoPropertyManagerReader.parseFile(KGE.PROPERTY_CONFIGURATION_FILE_1);
		} catch(Exception e) {
			//e.printStackTrace();
			try {
				KesoPropertyManagerReader.parseFile(KGE.PROPERTY_CONFIGURATION_FILE_2);
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public static String getDescription(IKesoProperty property) {
		KesoPropertyManagerEntry entry = KesoPropertyManager.getEntryOfProperty(property);
		if (entry == null) {
			return null;
		} else {
			return entry.getDescription();
		}
	}
	
	
}
