package keso.editor.data.datamanager;

import java.util.Hashtable;
import java.util.Vector;
import keso.editor.KGE;
import keso.editor.data.IKesoData;
import keso.editor.data.datamanager.reader.KesoDataManagerReader;

/**
 * @author  Wilhelm Haas
 */
public abstract class KesoDataManager {
	private static Hashtable canhaves = new Hashtable();
	private static Hashtable restrictions = new Hashtable();
	
	public static void addCanHave(String identifier, KesoCanHave canhave) {
		KesoDataManager.getCanHaves().put(identifier, canhave);
	}
	
	public static void init() {
		try {
			KesoDataManagerReader.parseFile(KGE.DATA_CONFIGURATION_FILE_1);
		} catch(Exception e) {
			//e.printStackTrace();
			try {
				KesoDataManagerReader.parseFile(KGE.DATA_CONFIGURATION_FILE_2);
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public static void addRestriction(String identifier, IKesoDataRestriction restriction) {
		KesoDataManager.getRestrictions().put(identifier, restriction);
	}

	public static Hashtable getCanHaves() {
		return KesoDataManager.canhaves;
	}
	
	/**
	 * @return  the restrictions
	 * @uml.property  name="restrictions"
	 */
	public static Hashtable getRestrictions() {
		return KesoDataManager.restrictions;
	}
	
	public static KesoCanHave getCanHave(String identifier) {
		return (KesoCanHave) KesoDataManager.getCanHaves().get(identifier);
	}
	
	public static boolean acceptsNewChild(IKesoData data, IKesoData child) {
		KesoCanHave canhave = KesoDataManager.getCanHave(data.getIdentifier());
		if (canhave != null && canhave.contains(child.getIdentifier())) {
			IKesoDataRestriction restriction =  KesoDataManager.getRestriction(data.getIdentifier());
			if (restriction != null) {
				Vector children = data.getChildren();
				KesoDataCounter counter = new KesoDataCounter();
				counter.add(children);
				counter.add(child);
				return restriction.accepts(counter, IKesoDataRestriction.ADD_CHILD);
			}
			return true;
		}
		return false;
	}
	
	public static boolean acceptsRemovalOfChild(IKesoData data, IKesoData child) {
		KesoCanHave canhave = KesoDataManager.getCanHave(data.getIdentifier());
		if (canhave != null && canhave.contains(child.getIdentifier())) {
			IKesoDataRestriction restriction =  KesoDataManager.getRestriction(data.getIdentifier());
			if (restriction != null) {
				Vector children = data.getChildren();
				KesoDataCounter counter = new KesoDataCounter();
				counter.add(children);
				counter.remove(child);
				return restriction.accepts(counter, IKesoDataRestriction.REMOVE_CHILD);
			}
			return true;
		}
		return false;
	}

	public static boolean evaluate(IKesoData data) {
		IKesoDataRestriction restriction =  KesoDataManager.getRestriction(data.getIdentifier());
		if (restriction != null) {
			Vector children = data.getChildren();
			KesoDataCounter counter = new KesoDataCounter();
			counter.add(children);
			return restriction.accepts(counter, IKesoDataRestriction.EVALUATE);
		}
		return true;
	}
	
	private static IKesoDataRestriction getRestriction(String identifier) {
		return (IKesoDataRestriction) KesoDataManager.getRestrictions().get(identifier);
	}
}
