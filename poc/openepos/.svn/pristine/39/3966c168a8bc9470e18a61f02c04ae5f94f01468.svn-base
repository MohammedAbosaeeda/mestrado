package keso.editor.gui.text;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.LineStyleEvent;
import org.eclipse.swt.custom.LineStyleListener;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class KesoTextStyleListener implements LineStyleListener {

	private static final String NAME_START = "(";	
	private static final String NAME_END = ")";
	private static final String PROPERTY_START = "=";
	private static final String PROPERTY_END_2 = " {";
	private static final String PROPERTY_END_1 = ";";
	
	private static final int NAME_STYLE = 1;
	private static final int PROPERTY_STYLE = 2;
	
	private static final int NAME_FONTSTYLE = SWT.NORMAL;
	private static final Color NAME_COLOR = Display.getCurrent().getSystemColor(SWT.COLOR_GRAY);
	private static final int PROPERTY_FONTSTYLE = SWT.NORMAL;
	private static final Color PROPERTY_COLOR = Display.getCurrent().getSystemColor(SWT.COLOR_BLUE);
	
	
	List commentOffsets = new LinkedList();
	
	 public void refresh(String text) {
		// Clear any stored offsets
		commentOffsets.clear();

		for (int pos = text.indexOf(NAME_START); pos > -1; pos = text.indexOf(
				NAME_START, pos)) {

			int[] offsets = new int[3];
			offsets[0] = pos + 1;
			pos = text.indexOf(NAME_END, pos);
			offsets[1] = pos == -1 ? text.length() - 1 : pos
					+ NAME_END.length() - 1;
			pos = offsets[1];
			offsets[1] -= 1;
			offsets[2] = NAME_STYLE;
			commentOffsets.add(offsets);
		}
		
		for (int pos = text.indexOf(PROPERTY_START); pos > -1; pos = text.indexOf(
				PROPERTY_START, pos)) {
			int[] offsets = new int[3];
			offsets[0] = pos + 1;
			
			int pos_1 = text.indexOf(PROPERTY_END_1, pos + 1);
			int pos_2 = text.indexOf(PROPERTY_END_2, pos + 1);
			pos_1 = (pos_1 == -1)? text.length() - 1 : pos_1 + PROPERTY_END_1.length() - 1;
			pos_2 = (pos_2 == -1)? text.length() - 1 : pos_2 + PROPERTY_END_2.length() - 1;
			offsets[1] = Math.min(pos_1, pos_2);
			pos = offsets[1];
			offsets[1] -= 1;
			offsets[2] = PROPERTY_STYLE;
			commentOffsets.add(offsets);
		}
	}
	
	
	public void lineGetStyle(LineStyleEvent event) {
		List styles = new ArrayList();
		StyleRange st = null;
		int length = event.lineText.length();

		for (int i = 0, n = commentOffsets.size(); i < n; i++) {
			int[] offsets = (int[]) commentOffsets.get(i);

			// If starting offset is past current line--quit
			/*
			if (offsets[0] > event.lineOffset + length)
				break;
*/
			// Check if we're inside a multiline comment
			if (offsets[0] <= event.lineOffset + length
					&& offsets[1] >= event.lineOffset) {
				// Calculate starting offset for StyleRange
				int start = Math.max(offsets[0], event.lineOffset);

				// Calculate length for style range
				int len = Math.min(offsets[1], event.lineOffset + length)
						- start + 1;

				// Add the style range
				
				//styles.add(new StyleRange(start, len, (offsets[2] == NAME_STYLE)? NAME_COLOR : PROPERTY_COLOR, null));
				
				Color color = null;
				int fontstyle = SWT.NORMAL;
				switch(offsets[2]) {
					case NAME_STYLE:
						fontstyle = NAME_FONTSTYLE;
						color = NAME_COLOR;
						break;
					case PROPERTY_STYLE:
						fontstyle = PROPERTY_FONTSTYLE;
						color = PROPERTY_COLOR;
						break;
					default:
						break;
				}
				st = new StyleRange(start, len, color, null);
				st.fontStyle = fontstyle;
			}
		}

		// Copy all the ranges into the event
		//event.styles = (StyleRange[]) styles.toArray(new StyleRange[0]);
		if (st != null) {
			event.styles = new StyleRange[] {st};
		} else {
			event.styles = new StyleRange[0];
		}
	}
	
}
