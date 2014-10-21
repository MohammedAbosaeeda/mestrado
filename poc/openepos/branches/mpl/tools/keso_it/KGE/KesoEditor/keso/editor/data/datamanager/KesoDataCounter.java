package keso.editor.data.datamanager;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import keso.editor.data.IKesoData;

/**
 * @author  Wilhelm Haas
 */
public class KesoDataCounter {
	Hashtable counters = new Hashtable();
	
	public void add(Vector data) {
		for (Iterator i = data.iterator(); i.hasNext(); ) {
			this.add((IKesoData) i.next());
		}
	}
	
	public void add(IKesoData data) {
		KesoInteger integer = (KesoInteger) this.getCounters().get(data.getIdentifier());
		if (integer == null) {
			this.getCounters().put(data.getIdentifier(), new KesoInteger(1));
		} else {
			integer.increase();
		}
	}
	
	public void remove(IKesoData data) {
		KesoInteger integer = (KesoInteger) this.getCounters().get(data.getIdentifier());
		if (integer != null) {
			integer.decrease();
		}
	}	
	
	/**
	 * @return  the counters
	 * @uml.property  name="counters"
	 */
	public Hashtable getCounters() {
		return this.counters;
	}
	
	public int getCounter(String identifier) {
		KesoInteger integer = (KesoInteger) this.getCounters().get(identifier);
		if (integer == null) {
			return 0;
		} else {
			return integer.intValue();
		}
	}
}
