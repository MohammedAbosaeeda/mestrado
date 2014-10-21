package keso.editor.gui.canvas.drawing;

import java.util.Iterator;
import java.util.Vector;

import keso.editor.gui.shape.KesoLine;

public class KesoLineContainer {

	Vector lines = new Vector();
	
	public KesoLineContainer() {
		
	}
	
	public void add(KesoLine line) {
		lines.add(line);
	}
	
	public KesoLine find(KesoLine line) {
		for (Iterator i = this.lines.iterator(); i.hasNext(); ) {
			KesoLine l = (KesoLine) i.next();
			if (l.equals(line)) {
				return l;
			}
		}
		return null;
	}

}
