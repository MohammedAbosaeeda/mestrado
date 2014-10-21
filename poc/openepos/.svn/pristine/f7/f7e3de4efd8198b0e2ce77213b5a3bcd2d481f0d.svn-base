package keso.editor.gui.compilation.parameter;
import java.util.Vector;

import keso.editor.gui.compilation.parameter.reader.KesoParameterReader;


abstract public class KesoParameterList {
	private static Vector parameters = new Vector();
	
	public static void add(String description, String type, String name, String sep, Vector entries, String defvalue) {
		parameters.add(new KesoParameter(description, type, name, sep, entries, defvalue));
	}
	
	public static int size() {
		return parameters.size();
	}
	
	public static Vector getParameters() {
		return parameters;
	}
	
	public static void add(KesoParameter parameter) {
		parameters.add(parameter);
	}
	
	public static void clear() {
		parameters.clear();
	}
	
	public static void read(String parameterstring) throws Exception {
			clear();
			KesoParameterReader.parseFile(parameterstring);
	}
}