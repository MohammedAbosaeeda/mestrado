package keso.editor.gui.core;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

public class KesoSplashScreen implements PaintListener {
	Canvas canvas;
	Display display;
	Image logo;
	ProgressBar progressbar;
	Shell shell;
	
	public KesoSplashScreen(Display display) {
		shell = new Shell(display, SWT.ON_TOP);
		this.display = display;
		GridLayout glayout = new GridLayout();
		glayout.numColumns = 1;
		glayout.marginBottom = 0;
		glayout.marginTop = 0;
		glayout.marginRight = 0;
		glayout.marginHeight = 0;
		glayout.marginWidth = 0;
		glayout.marginLeft = 0;
		glayout.verticalSpacing = 0;
		glayout.horizontalSpacing = 0;
		shell.setLayout(glayout);
		canvas = new Canvas(shell, SWT.NONE);
		GridData gd = new GridData();
		logo = KesoImageManager.getInstance().getImage("logo");
		gd.heightHint = logo.getImageData().height;
		gd.widthHint = logo.getImageData().width;
		canvas.setLayoutData(gd);
		canvas.addPaintListener(this);
		progressbar = new ProgressBar(shell, SWT.NONE);
		progressbar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		progressbar.setSelection(0);
		
		shell.pack();
		
		Rectangle clientarea = display.getClientArea();
		int mx = (clientarea.x + clientarea.x + clientarea.width) / 2 - (this.shell.getSize().x / 2);
		int my = (clientarea.y + clientarea.y + clientarea.height) / 2 - (this.shell.getSize().y / 2);
		
		this.shell.setLocation(mx, my);
	}
	
	public void open() {
		//display.asyncExec(new Runnable() {
			//public void run() {
				shell.open();
				/*while(!shell.isDisposed()) {
					if (!display.readAndDispatch()) {
						display.sleep();
					}
				}
			}
		});*/
	}
	
	public void close() {
		shell.dispose();
	}
	
	public void setMax(final int max) {
		display.syncExec(new Runnable() {
			public void run() {
				progressbar.setMaximum(max);
				progressbar.setMinimum(0);
			}
		});
	}
	
	public void increase() {
		display.syncExec(new Runnable() {
			public void run() {
				progressbar.setSelection(progressbar.getSelection() + 1);
			}
		});
	}
	
	public void paintControl(PaintEvent e) {
		GC gc = e.gc;
		gc.drawImage(logo, 0, 0);
	}
}
