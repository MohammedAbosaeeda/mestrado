package keso.editor.gui.core;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

public class KesoAboutWindow implements PaintListener, SelectionListener {
	Shell shell;
	Shell parent;
	Display display;
	Canvas canvas;
	Image logo;
	Button close;
	
	public KesoAboutWindow(Shell parent) {
		shell = new Shell(parent);
		shell.setText("About KESO Graphical Editor");
		this.parent = parent;
		this.display = parent.getDisplay();
		GridLayout glayout = new GridLayout();
		glayout.numColumns = 1;
		glayout.marginBottom = 0;
		glayout.marginTop = 0;
		glayout.marginRight = 0;
		glayout.marginHeight = 2;
		glayout.marginWidth = 0;
		glayout.marginLeft = 0;
		glayout.verticalSpacing = 2;
		glayout.horizontalSpacing = 0;
		shell.setLayout(glayout);
		canvas = new Canvas(shell, SWT.NONE);
		GridData gd = new GridData();
		logo = KesoImageManager.getInstance().getImage("logo");
		gd.heightHint = logo.getImageData().height;
		gd.widthHint = logo.getImageData().width;
		canvas.setLayoutData(gd);
		canvas.addPaintListener(this);
		
		Composite comp = new Composite(this.shell, SWT.NONE);
		comp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		glayout = new GridLayout();
		glayout.numColumns = 3;
		glayout.marginBottom = 0;
		glayout.marginHeight = 0;
		glayout.marginLeft = 0;
		glayout.marginRight = 0;
		glayout.marginTop = 0;
		glayout.marginWidth = 0;
		comp.setLayout(glayout);
		
		Composite fakecomp = new Composite(comp, SWT.NONE);
		gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 1;
		fakecomp.setLayoutData(gd);
		
		this.close = new Button(comp, SWT.PUSH);
		this.close.setLayoutData(new GridData());
		this.close.setText("Close");
		this.close.addSelectionListener(this);
		
		shell.pack();
		
		Rectangle clientarea = display.getClientArea();
		int mx = (clientarea.x + clientarea.x + clientarea.width) / 2 - (this.shell.getSize().x / 2);
		int my = (clientarea.y + clientarea.y + clientarea.height) / 2 - (this.shell.getSize().y / 2);
		
		this.shell.setLocation(mx, my);
		
		shell.open();
		while(!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	public void paintControl(PaintEvent e) {
		GC gc = e.gc;
		gc.drawImage(logo, 0, 0);
	}

	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void widgetSelected(SelectionEvent e) {
		if (this.close == e.widget) {
			this.shell.dispose();
		}
	}
}
