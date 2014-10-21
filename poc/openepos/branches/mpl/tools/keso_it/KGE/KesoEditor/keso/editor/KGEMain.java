package keso.editor;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import keso.compiler.BuilderOptions;
import keso.editor.KGE;
import keso.editor.configurationloader.KesoConfigurationLoader;
import keso.editor.data.WorldKesoData;
import keso.editor.data.datamanager.KesoDataManager;
import keso.editor.filter.manager.KesoFilterManager;
import keso.editor.gui.KesoGuiProperties;
import keso.editor.gui.canvas.KesoCanvas;
import keso.editor.gui.core.KesoMainWindow;
import keso.editor.gui.core.KesoSplashScreen;
import keso.editor.gui.datatree.KesoDataTree;
import keso.editor.gui.propertygrid.KesoPropertyGrid;
import keso.editor.gui.shape.design.stylemanager.*;
import keso.editor.gui.tool.SelectionKesoTool;
import keso.editor.gui.toolmanager.KesoToolManager;
import keso.editor.property.propertymanager.KesoPropertyManager;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class KGEMain {

	public KGEMain() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String [] argv) {
		Display display = new Display();
		KesoSplashScreen splash = new KesoSplashScreen(display);
		splash.setMax(12);
		splash.open();
		
		FileInputStream filein = null;
		try {
			filein = new FileInputStream(KGE.GUI_CONFIGURATION_FILE);
		} catch (Exception e) {
			try {
				FileOutputStream fileoutput = new FileOutputStream(KGE.GUI_CONFIGURATION_FILE);
				fileoutput.close();
				filein = new FileInputStream(KGE.GUI_CONFIGURATION_FILE); 
			} catch (Exception ex) {
				System.err.println("Could not write '" + KGE.GUI_CONFIGURATION_FILE + "'!");
			}
		}
		
		splash.increase();
		
		if (filein != null) {
			try {
				KesoGuiProperties.getInstance().load(filein);
			} catch (Exception e) {
				
			}
			try {
				filein.close();
			} catch (Exception e) {
				
			}
		}
		
		splash.increase();
		
		/* INTIALIZATION */
		new BuilderOptions(null);
		splash.increase();
		KesoDataManager.init();
		splash.increase();
		KesoPropertyManager.init();
		splash.increase();
		
		try {
			Thread.sleep(1000);
		} catch(Exception e) {
			
		}
		
		KesoFilterManager.init();
		splash.increase();
		ScreenKesoShapeStyleManager.getInstance().init();
		splash.increase();
		BmpExportKesoShapeStyleManager.getInstance().init();
		splash.increase();
		JpegExportKesoShapeStyleManager.getInstance().init();
		splash.increase();
		EpsExportKesoShapeStyleManager.getInstance().init();
		splash.increase();
		PrinterKesoShapeStyleManager.getInstance().init();
		splash.increase();
		KesoMainWindow window = new KesoMainWindow(display);
		splash.increase();
		
		window.open();
		try {
			Thread.sleep(2500);
		} catch(Exception e) {
			
		}
		splash.close();
		
		window.run();
		

		ScreenKesoShapeStyleManager.getInstance().save();
		BmpExportKesoShapeStyleManager.getInstance().save();
		JpegExportKesoShapeStyleManager.getInstance().save();
		EpsExportKesoShapeStyleManager.getInstance().save();
		PrinterKesoShapeStyleManager.getInstance().save();
		
		try {
			FileOutputStream fileoutput = new FileOutputStream(KGE.GUI_CONFIGURATION_FILE);
			KesoGuiProperties.getInstance().store(fileoutput, "Keso Graphical Editor");
			fileoutput.close();
		} catch (Exception e) {
			
		}
		display.dispose();
		
		System.exit(0);
	}

}
