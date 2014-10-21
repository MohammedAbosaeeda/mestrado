package keso.editor.filter.operation;

import java.util.Iterator;
import java.util.Vector;

import keso.editor.data.IKesoData;

public class DeleteRedundantNamesKesoFilterOperator {

	public static Vector execute(Vector input) {
		Vector output = new Vector();
		Vector names = new Vector();
		
		for (Iterator i = input.iterator(); i.hasNext(); ) {
			IKesoData data = (IKesoData) i.next();
			if (!names.contains(data.getName())) {
				output.add(data);
				names.add(data.getName());
			}
		}
		return output;
	}
}
