package keso.editor.gui.core;

import java.util.Enumeration;
import java.util.Hashtable;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;

/**
 * @author  Wilhelm Haas
 */
public class KesoImageManager {

	private static KesoImageManager instance = new KesoImageManager();
	
	private Hashtable images = new Hashtable();
	
	private KesoImageManager() {
		this.init();
	}
	
	private void init() {
		String path = "../../icons/";
		
		this.addImage("new", path + "new.gif");
		this.addImage("compile", path + "compile.gif");
		this.addImage("open", path + "open.gif");
		this.addImage("save", path + "save_2.gif");
		this.addImage("saveas", path + "saveas.gif");
		this.addImage("export", path + "export.gif");
		this.addImage("print", path + "print_2.gif");
		this.addImage("propertygrid", path + "propertygrid.gif");
		this.addImage("canvas", path + "graph.gif");
		this.addImage("selectiontool", path + "selection.gif");
		this.addImage("nodetool", path + "nodetool.gif");
		this.addImage("networktool", path + "networktool.gif");
		this.addImage("domaintool", path + "domaintool.gif");
		this.addImage("publicdomaintool", path + "publicdomain.gif");
		this.addImage("tasktool", path + "tasktool.gif");
		this.addImage("servicetool", path + "servicetool.gif");
		this.addImage("importtool", path + "importtool.gif");
		this.addImage("alarmtool", path + "alarmtool.gif");
		this.addImage("resourcetool", path + "resourcetool.gif");
		this.addImage("appmodetool", path + "appmodetool.gif");
		this.addImage("eventtool", path + "eventtool.gif");
		this.addImage("isrtool", path + "isrtool.gif");
		this.addImage("countertool", path + "clocktool.gif");
		this.addImage("ostool", path + "ostool.gif");
		this.addImage("error", path + "error.gif");
		this.addImage("consolewindow", path + "console_2.gif");
		this.addImage("datatree", path + "datatree_2.gif");
		this.addImage("propertydescription", path + "propertydescription.gif");
		this.addImage("plus", path + "plus_5.bmp");
		this.addImage("minus", path + "minus_5.bmp");
		this.addImage("bringforward", path + "bringforward.gif");
		this.addImage("sendbackward", path + "sendbackward.gif");
		this.addImage("bringtofront", path + "bringtofront.gif");
		this.addImage("sendtoback", path + "sendtoback.gif");
		
		this.addImage("addproperty", path + "bigplus.gif");
		this.addImage("deleteproperty", path + "bigminus.gif");
		this.addImage("renameproperty", path + "rename.gif");
		this.addImage("configtext", path + "configtext.gif");
		this.addImage("autostretch", path + "stretch.bmp");
		this.addImage("nostretch", path + "no_stretch.bmp");
		this.addImage("zoomin", path + "zoomin_2.gif");
		this.addImage("zoomout", path + "zoomout_2.gif");
		this.addImage("onetoone", path + "onetoone.gif");
		this.addImage("onetotwo", path + "onetotwo.gif");
		
		this.addImage("cursor_zoomin", path + "cursor_zoomin_transparent.gif");
		this.addImage("cursor_zoomout", path + "cursor_zoomout_transparent.gif");
		
		this.addImage("directory", path + "open.gif");
		this.addImage("file", path + "file.gif");
		
		this.addImage("logo", path + "logo.png");
	}
	
	public void dispose() {
		for (Enumeration e = this.images.elements(); e.hasMoreElements(); ) {
			Image img = (Image) e.nextElement();
			if (!img.isDisposed()) {
				img.dispose();
			}
		}
	}
	
	/**
	 * @return  the instance
	 * @uml.property  name="instance"
	 */
	public static KesoImageManager getInstance() {
		return KesoImageManager.instance;
	}
	
	public void addImage(String name, String path) {
		ImageData image = new ImageData(getClass().getResourceAsStream(path));
		this.images.put(name, new Image(Display.getCurrent(), image, image.getTransparencyMask()));
	}
	
	public Image getImage(String name) {
		return (Image) this.images.get(name);
	}

}
