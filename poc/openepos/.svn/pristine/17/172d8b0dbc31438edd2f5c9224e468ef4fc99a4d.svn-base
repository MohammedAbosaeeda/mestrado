package josek;
import java.util.Vector;
import java.util.Collections;

public class Priorities {
	Vector priorityList;
	boolean finalized;

	public Priorities() {
		priorityList = new Vector();
		finalized = false;
	}

	public void registerPriority(int level) {
		if (finalized) throw new RuntimeException("Priorities have already been finalized.");
		Integer i = new Integer(level);
		if (!priorityList.contains(i)) {
			priorityList.addElement(i);
		}
	}

	public void finalizePriorities() {
		finalized = true;
		java.util.Collections.sort(priorityList);
	}

	public int internalToExternal(int level) {
		if (!finalized) throw new RuntimeException("Priorities are not finalized yet.");
		return ((Integer)priorityList.get(level - 1)).intValue();
	}

	public int externalToInternal(int level) {
		if (!finalized) throw new RuntimeException("Priorities are not finalized yet.");
		Integer i = new Integer(level);
		return priorityList.lastIndexOf(i) + 1;
	}
	
	public int size() {
		if (!finalized) throw new RuntimeException("Priorities are not finalized yet.");
		return priorityList.size();
	}

	public void dumpPriorities() {
		if (!finalized) throw new RuntimeException("Priorities are not finalized yet.");
		for (int i = 0; i < priorityList.size(); i++) {
			System.out.println("Priority " + (i + 1) + " -> " + (Integer)priorityList.get(i));
		}
	}
}
