package keso.editor.gui.shape;

import java.awt.Rectangle;
import java.util.Iterator;
import java.util.Vector;

public class KesoTextShapeContainer {

	Vector textshapes = new Vector();
	
	public KesoTextShapeContainer() {
	
	}
	
	public void add(KesoTextShape textshape) {
		boolean finish = false;
		boolean found = false;
		while(!finish) {
			Rectangle newrect = textshape.getLocalBounds();
			finish = true;
			for (Iterator i = this.textshapes.iterator(); i.hasNext(); ) {
				KesoTextShape oldtextshape = (KesoTextShape) i.next();
				if (oldtextshape.equalLine(textshape)) {
					if (oldtextshape.equal(textshape)) {
						textshape.setLocalPosition(oldtextshape.getLocalPosition());
						found = true;
						break;
					} else {
						if (oldtextshape.intersects(textshape)) {
							Rectangle oldrect = oldtextshape.getLocalBounds();
							textshape.move(0, oldrect.y + oldrect.height - newrect.y);
							finish = false;
							break;
						}
					}
				}
			}
		}
		
		if (!found) {
			this.textshapes.add(textshape);
		}
	}
}
