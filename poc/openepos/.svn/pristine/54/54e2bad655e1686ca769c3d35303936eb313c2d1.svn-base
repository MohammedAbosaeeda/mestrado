package keso.editor.filter.operation;

import java.util.Iterator;
import java.util.Vector;

import keso.editor.data.IKesoData;

public class DeleteRedundantNamesInSameClassGroupKesoFilterOperation {

	public static Vector execute(Vector input) {
		Vector classtypes = new Vector();
		Vector names = new Vector();
		Vector output = new Vector();
		for (Iterator i = input.iterator(); i.hasNext(); ) {
			IKesoData data = (IKesoData) i.next();
			if (classtypes.contains(data.getIdentifier())) {
				int index = classtypes.indexOf(data.getIdentifier());
				Vector namelist = (Vector) names.get(index);
				if (!namelist.contains(data.getName())) {
					namelist.add(data.getName());
					output.add(data);
				}
			} else {
				Vector namelist = new Vector();
				namelist.add(data.getName());
				names.add(namelist);
				classtypes.add(data.getIdentifier());
				output.add(data);
			}
		}
		return output;
	}
}
