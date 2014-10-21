package keso.editor.gui.core;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class KesoHelpWindow {
	public KesoHelpWindow() {
		Display display = Display.getCurrent();
		Shell shell = new Shell(display);
		shell.setText("KESO Graphical Editor V1.0 Help");
		//Display display = shell.getDisplay();
		shell.setLayout(new FillLayout());
		Browser browser = new Browser(shell, SWT.NONE);
		browser.setUrl("file://" + System.getProperty("user.dir") + "/keso/help/index.html");
		System.err.println(browser.getUrl());
		
		shell.open();
		while(!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
