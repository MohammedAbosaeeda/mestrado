package keso.editor.filter.operation;

import java.util.Iterator;
import java.util.Vector;

import keso.editor.data.IKesoData;

public class GetNextParentKesoFilterOperation {	
	public static Vector execute(Vector input) {
		Vector output = new Vector();
		for (Iterator i = input.iterator(); i.hasNext(); ) {
			IKesoData child = (IKesoData) i.next();
			IKesoData parent = child.getParent();
			if (parent != null) {
				output.add(parent);
			}
		}
		return output;
	}
}
