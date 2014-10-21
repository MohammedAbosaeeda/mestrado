package keso.editor.filter.operation;

import java.util.Vector;

public class UniteKesoFilterOperation {

	public static Vector execute(Vector first, Vector second) {
		Vector output = new Vector();
		output.addAll(first);
		output.addAll(second);
		return output;
	}
	
}
