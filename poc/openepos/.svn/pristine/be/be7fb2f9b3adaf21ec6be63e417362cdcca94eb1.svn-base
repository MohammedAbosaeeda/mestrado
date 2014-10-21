package keso.editor.filter.operation;

import java.util.Iterator;
import java.util.Vector;

import keso.editor.data.IKesoData;

public class GetParentOfTypeKesoFilterOperation {

	public static IKesoData parse(IKesoData data, String parentIdentifier) {
		while (data != null && !data.getIdentifier().equals(parentIdentifier)) {
			data = data.getParent();
		}
		return data;
	}
	
	public static Vector execute(Vector input, String parentIdentifier) {
		Vector output = new Vector();
		for (Iterator i = input.iterator(); i.hasNext(); ) {
			IKesoData child = (IKesoData) i.next();
			IKesoData parent = GetParentOfTypeKesoFilterOperation.parse(child, parentIdentifier);
			if (parent != null) {
				output.add(parent);
			}
		}
		return output;
	}
}
