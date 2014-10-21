package keso.editor.filter.operation;

import java.util.Iterator;
import java.util.Vector;
import keso.editor.data.IKesoData;

public class DeleteRedundantObjectsKesoFilterOperation {

	public static Vector execute(Vector input) {
		Vector output = new Vector();
		for (Iterator i = input.iterator(); i.hasNext(); ) {
			IKesoData data = (IKesoData) i.next();
			if (!output.contains(data)) {
				output.add(data);
			}
		}
		return output;
	}

}
