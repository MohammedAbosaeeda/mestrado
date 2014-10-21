package keso.editor.filter.operation;

import java.util.Iterator;
import java.util.Vector;

import keso.editor.data.IKesoData;

public class GetAllChildrenKesoFilterOperation {

	public static Vector parse(IKesoData data) {
		Vector output = new Vector();
		for (Iterator i = data.getChildren().iterator(); i.hasNext(); ) {
			IKesoData child = (IKesoData) i.next();
			output.addAll(GetAllKesoFilterOperation.parse(child));
		}
		return output;
	}
	
	public static Vector execute(Vector input) {
		Vector output = new Vector();
		for (Iterator i = input.iterator(); i.hasNext(); ) {
			IKesoData child = (IKesoData) i.next();
			output.addAll(GetAllChildrenKesoFilterOperation.parse(child));
		}
		return output;
	}

}
