package keso.editor.data.datamanager;

import java.util.Iterator;
import java.util.Vector;

/**
 * @author  Wilhelm Haas
 */
public class AndKesoDataRestriction implements IKesoDataRestrictionContainer {
	private Vector restrictions = new Vector();

	public void add(IKesoDataRestriction restriction) {
		this.getRestrictions().add(restriction);
	}
	
	/**
	 * @return  the restrictions
	 * @uml.property  name="restrictions"
	 */
	public Vector getRestrictions() {
		return this.restrictions;
	}
	
	public boolean accepts(KesoDataCounter counter, int flag) {
		for (Iterator i = this.getRestrictions().iterator(); i.hasNext(); ) {
			IKesoDataRestriction restriction = (IKesoDataRestriction) i.next();
			if (restriction.accepts(counter, flag) == false) {
				return false;
			}
		}
		return true;
	}

	public void add(int index, IKesoDataRestriction restriction) {
		this.getRestrictions().add(index, restriction);
	}
	
	
	

}
