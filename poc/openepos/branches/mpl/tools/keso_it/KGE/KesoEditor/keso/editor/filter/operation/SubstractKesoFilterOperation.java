package keso.editor.filter.operation;

import java.util.Iterator;
import java.util.Vector;

public class SubstractKesoFilterOperation {

	public static Vector execute(Vector first, Vector second) {
		Vector output = new Vector();
		output.addAll(first);
		for (Iterator i = second.iterator(); i.hasNext(); ) {
			Object data = i.next();
			if (output.contains(data)) {
				output.remove(data);
			}
		}
		return output;
	}

}
