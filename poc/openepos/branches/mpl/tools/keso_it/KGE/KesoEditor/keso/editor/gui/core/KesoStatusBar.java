package keso.editor.gui.core;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class KesoStatusBar extends Composite {
	Label statustext;
	
	public KesoStatusBar(Composite parent, int style) {
		super(parent, style);

		this.setLayout(new FillLayout());
		this.statustext = new Label(this, SWT.NONE);
		//this.setText("Hallo");
	}
	
	public void setText(String text) {
		this.statustext.setText(" " + text);
	}
	
	public String getText() {
		return this.statustext.getText();
	}

}
