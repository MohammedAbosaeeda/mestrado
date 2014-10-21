package keso.editor.filter.manager;

import java.util.Hashtable;
import keso.editor.KGE;
import keso.editor.filter.KesoFilter;
import keso.editor.filter.manager.reader.KesoFilterReader;

/**
 * @author  Wilhelm Haas
 */
public abstract class KesoFilterManager {

	private static Hashtable filters = new Hashtable();
	
	/**
	 * @return  the filters
	 * @uml.property  name="filters"
	 */
	public static Hashtable getFilters() {
		return KesoFilterManager.filters;
	}
	
	public static void add(String name, KesoFilter filter) {
		if (!KesoFilterManager.getFilters().containsKey(name)) {
			KesoFilterManager.getFilters().put(name, filter);
		}
	}
	
	public static KesoFilter get(String name) {
		if (KesoFilterManager.getFilters().containsKey(name)) {
			return (KesoFilter) KesoFilterManager.getFilters().get(name);
		} else {
			return new KesoFilter();
		}
	}
	
	public static void init() {
		try {
			KesoFilterReader.parseFile(KGE.FILTER_CONFIGURATION_FILE_1);
		} catch(Exception e) {
			//e.printStackTrace();
			try {
				KesoFilterReader.parseFile(KGE.FILTER_CONFIGURATION_FILE_2);
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
}
