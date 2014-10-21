package keso.editor.property.propertymanager;

import java.io.File;
import java.util.Iterator;
import java.util.Vector;
import keso.editor.KGE;
import keso.editor.data.IKesoData;
import keso.editor.filter.manager.KesoFilterManager;
import keso.editor.gui.KesoGuiProperties;
import keso.editor.gui.core.KesoMainWindow;
import keso.editor.property.IKesoProperty;
import keso.editor.property.KesoProperty;
import keso.editor.property.exceptioncontainer.KesoPropertyException;

/**
 * @author  Wilhelm Haas
 */
public class KesoPropertyRuleManager {

	private static KesoPropertyRuleManager instance = new KesoPropertyRuleManager();
	
	private KesoPropertyRuleManager() {
	}
	
	/**
	 * @return  the instance
	 * @uml.property  name="instance"
	 */
	public static KesoPropertyRuleManager getInstance() {
		return KesoPropertyRuleManager.instance;
	}
	
	public Vector check(IKesoData datatocheck) {
		Vector exceptions = new Vector();
		
		Vector all = KesoFilterManager.get("filter_all").filter(datatocheck);
		for (Iterator i = all.iterator(); i.hasNext(); ) {
			IKesoData data = (IKesoData) i.next();
			if (!data.getName().matches("([a-z]|[A-Z])([a-z]|[A-Z]|[0-9]|_)*")) {
				KesoPropertyException exception = new KesoPropertyException(data.getPropertyContainer().getFirstPropertyByName("Name"));
				exception.setExceptionNo(KesoPropertyException.EXCEPTION_INVALID_VALUE);
				exceptions.add(exception);
			}
			
			if (data.getIdentifier().equals(KGE.SERVICE)) {
				Vector globaly = KesoFilterManager.get("filter_globaly").filter(data);
				for (Iterator j = globaly.iterator(); j.hasNext(); ) {
					IKesoData compdata = (IKesoData) j.next();
					if (data == compdata) {
						continue;
					}
					if (compdata.getIdentifier().equals(KGE.IMPORT)) {
						continue;
					} else {
						if (data.getName().equals(compdata.getName())) {
							KesoPropertyException exception = new KesoPropertyException(data.getPropertyContainer().getFirstPropertyByName("Name"));
							exception.setExceptionNo(KesoPropertyException.EXCEPTION_NOT_UNIQUE);
							exception.setAdditionalString(compdata.getPropertyContainer().getFirstPropertyByName("Name").getPropertyPath());
							exceptions.add(exception);
							
							exception = new KesoPropertyException(compdata.getPropertyContainer().getFirstPropertyByName("Name"));
							exception.setExceptionNo(KesoPropertyException.EXCEPTION_NOT_UNIQUE);
							exception.setAdditionalString(data.getPropertyContainer().getFirstPropertyByName("Name").getPropertyPath());
							exceptions.add(exception);
						}
					}
				}
			} else if (data.getIdentifier().equals(KGE.IMPORT)) {
				
			} else if (data.getIdentifier().equals(KGE.NODE)) {
				/*
				Vector properties = data.getPropertyContainer().getPropertiesByName("Modules");
				for (Iterator j = properties.iterator(); j.hasNext(); ) {
					KesoProperty property = (KesoProperty) j.next();
					String [] modules = property.getValue().trim().split(":");
					for (int k = 0; k < modules.length; k++) {
						String module = modules[k].trim();
						File file = new File(KesoGuiProperties.getInstance().getProperty("workbench.directory") + "/libs/" + module);
						if (file.exists()) {
							if (!file.isDirectory()) {
								KesoPropertyException exception = new KesoPropertyException(property);
								exception.setExceptionNo(KesoPropertyException.EXCEPTION_MODULE_DOES_NOT_EXIST);
								exception.setAdditionalString(module);
								exceptions.add(exception);
							}
						} else {
							KesoPropertyException exception = new KesoPropertyException(property);
							exception.setExceptionNo(KesoPropertyException.EXCEPTION_MODULE_DOES_NOT_EXIST);
							exception.setAdditionalString(module);
							exceptions.add(exception);
						}
					}
				}
				*/
			} else {
				Vector localy = KesoFilterManager.get("filter_localy_advanced").filter(data);
				if (data.getParent() != null) {
					//localy.add(data.getParent());
				}
				for (Iterator j = localy.iterator(); j.hasNext(); ) {
					IKesoData compdata = (IKesoData) j.next();
					if (data == compdata) {
						continue;
					}
					if (data.getName().equals(compdata.getName())) {
						KesoPropertyException exception = new KesoPropertyException(data.getPropertyContainer().getFirstPropertyByName("Name"));
						exception.setExceptionNo(KesoPropertyException.EXCEPTION_NOT_UNIQUE);
						exception.setAdditionalString(compdata.getPropertyContainer().getFirstPropertyByName("Name").getPropertyPath());
						exceptions.add(exception);
					}
				}
			}
			
			/*
			Vector properties = new Vector();
			properties.addAll(data.getPropertyContainer().getPropertiesByName("HandlerClass"));
			properties.addAll(data.getPropertyContainer().getPropertiesByName("ServiceClass"));
			properties.addAll(data.getPropertyContainer().getPropertiesByName("ServiceInterface"));
			properties.addAll(data.getPropertyContainer().getPropertiesByName("MainClass"));
			properties.addAll(data.getPropertyContainer().getPropertiesByName("Driver"));
			properties.addAll(data.getPropertyContainer().getPropertiesByName("Drivers"));
			
			
			String [] modules = this.getModules(data);
			String workbench = KesoGuiProperties.getInstance().getProperty("workbench.directory") + "/libs";
			
			for (Iterator j = properties.iterator(); j.hasNext(); ) {
				IKesoProperty property = (IKesoProperty) j.next();
				if (property.getValue().length() != 0) {
					boolean found = false;
					if (!property.getName().equals("Drivers")) {
						for (int k = 0; k < modules.length && !found; k++) {
							File file = new File(workbench + "/" + modules[k] + "/" + property.getValue() + ".java");
							if (file.exists() && !file.isDirectory()) {
								found = true;
							}
						}
						if (!found) {
							KesoPropertyException exception = new KesoPropertyException(property);
							exception.setExceptionNo(KesoPropertyException.EXCEPTION_INVALID_FILE);
							exception.setAdditionalString(property.getValue());
							exceptions.add(exception);
						}
					} else {
						
						String [] files = property.getValue().split(":");
						for (int m = 0; m < files.length; m++) {
							found = false;
							for (int k = 0; k < modules.length && !found; k++) {
								File file = new File(workbench + "/" + modules[k] + "/" + files[m].trim() + ".java");
								if (file.exists() && !file.isDirectory()) {
									found = true;
								}
							}
							if (!found) {
								KesoPropertyException exception = new KesoPropertyException(property);
								exception.setExceptionNo(KesoPropertyException.EXCEPTION_INVALID_FILE);
								exception.setAdditionalString(files[m]);
								exceptions.add(exception);
							}
						}
						
					}
				}
			}*/
			
		}
		
		exceptions.addAll(KesoPropertyManager.checkAll(datatocheck));
		
		return exceptions;
	}
	
	private String [] getModules(IKesoData data) {
		Vector parents = KesoFilterManager.get("filter_parent_node").filter(data);
		Vector allmodules = new Vector();
		if (parents != null && parents.size() != 0) {
			IKesoData parent = (IKesoData) parents.get(0);
			Vector properties = parent.getPropertyContainer().getPropertiesByName("Modules");
			for (Iterator i = properties.iterator(); i.hasNext(); ) {
				IKesoProperty property = (IKesoProperty) i.next();
				String [] modules = property.getValue().trim().split(":");
				for (int k = 0; k < modules.length; k++) {
					String module = modules[k].trim();
					allmodules.add(module);
				}
			}
		}
		if (allmodules.size() == 0) {
			return new String[0];
		} else {
			return (String []) allmodules.toArray(new String[0]);
		}
	}
}
