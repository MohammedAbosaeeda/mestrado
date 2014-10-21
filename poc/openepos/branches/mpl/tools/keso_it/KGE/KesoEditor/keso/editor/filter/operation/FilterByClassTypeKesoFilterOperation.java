package keso.editor.filter.operation;

import java.util.Iterator;
import java.util.Vector;

import keso.editor.data.IKesoData;

public class FilterByClassTypeKesoFilterOperation {

	public static Vector execute(Vector input, Vector classtypes) {
		Vector output = new Vector();
		for (Iterator i = input.iterator(); i.hasNext(); ) {
			IKesoData data = (IKesoData) i.next();
			if (classtypes.contains(data.getIdentifier())) {
				output.add(data);
			}
		}
		return output;
	}

}
