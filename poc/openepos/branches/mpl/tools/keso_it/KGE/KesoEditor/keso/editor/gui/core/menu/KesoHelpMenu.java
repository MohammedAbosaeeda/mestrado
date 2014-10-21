package keso.editor.gui.core.menu;

import java.io.File;

import keso.editor.KGE;
import keso.editor.data.IKesoData;
import keso.editor.data.KesoData;
import keso.editor.gui.core.KesoAboutWindow;
import keso.editor.gui.core.KesoHelpWindow;
import keso.editor.gui.core.KesoMainWindow;
import keso.editor.property.propertymanager.KesoPropertyManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

/**
 * @author  Wilhelm Haas
 */
public class KesoHelpMenu {
	KesoMainWindow window;
	
	public KesoHelpMenu(KesoMainWindow window) {
		this.window = window;
		
		Menu menu;
		MenuItem menuitem;
		menuitem = new MenuItem(this.window.getMenuBar(), SWT.CASCADE);
		menuitem.setText("&Help");
		
		menu = new Menu(this.window.getShell(), SWT.DROP_DOWN);
		menuitem.setMenu(menu);
		
		menuitem = new MenuItem(menu, SWT.PUSH);
		menuitem.setText("Help Contents");
		
		menuitem.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void widgetSelected(SelectionEvent e) {
				Display display = Display.getCurrent();
				display.asyncExec(new Runnable() {
					public void run() {
						//new KesoHelpWindow();
						org.eclipse.swt.program.Program.launch("file://" + System.getProperty("user.dir") + File.separator + "keso" + File.separator + "help" + File.separator + "index.html");
					}
				});
			}
			
		});
		
		menuitem = new MenuItem(menu, SWT.SEPARATOR);
		
		menuitem = new MenuItem(menu, SWT.PUSH);
		menuitem.setText("About Keso Graphical Editor");
		menuitem.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void widgetSelected(SelectionEvent e) {
				new KesoAboutWindow(KesoHelpMenu.this.window.getShell());
			}
			
		});
	}

}
