package keso.editor.gui.core;

import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import keso.editor.KGE;
import keso.editor.configurationloader.KesoConfigurationLoader;
import keso.editor.data.IKesoData;
import keso.editor.data.KesoData;
import keso.editor.data.WorldKesoData;
import keso.editor.data.datamanager.KesoDataManager;
import keso.editor.gui.KesoGuiProperties;
import keso.editor.gui.canvas.KesoCanvas;
import keso.editor.gui.canvas.drawing.KesoDrawingManager;
import keso.editor.gui.compilation.KesoCompilationSettingsDialog;
import keso.editor.gui.core.menu.KesoCanvasMenu;
import keso.editor.gui.core.menu.KesoFileMenu;
import keso.editor.gui.core.menu.KesoHelpMenu;
import keso.editor.gui.core.menu.KesoToolsMenu;
import keso.editor.gui.core.toolbar.KesoStandardToolBar;
import keso.editor.gui.core.toolbar.KesoToolsToolBar;
import keso.editor.gui.datatree.KesoDataTree;
import keso.editor.gui.exceptionlist.KesoExceptionList;
import keso.editor.gui.graphics.EpsKesoGraphics;
import keso.editor.gui.graphics.SwtKesoGraphics;
import keso.editor.gui.propertygrid.KesoPropertyGrid;
import keso.editor.gui.shape.design.stylemanager.BmpExportKesoShapeStyleManager;
import keso.editor.gui.shape.design.stylemanager.EpsExportKesoShapeStyleManager;
import keso.editor.gui.shape.design.stylemanager.IKesoShapeStyleManager;
import keso.editor.gui.shape.design.stylemanager.JpegExportKesoShapeStyleManager;
import keso.editor.gui.shape.design.stylemanager.PrinterKesoShapeStyleManager;
import keso.editor.gui.text.KesoConfigurationText;
import keso.editor.gui.tool.*;
import keso.editor.gui.toolmanager.KesoToolManager;
import keso.editor.output.ConfigurationKesoOutput;
import keso.editor.property.propertymanager.KesoPropertyManager;
import net.sf.epsgraphics.ColorMode;
import net.sf.epsgraphics.EpsGraphics;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

/**
 * @author  Wilhelm Haas
 */
public class KesoMainWindow implements PropertyChangeListener, DisposeListener, 
	ShellListener, SelectionListener, ControlListener {
	
	public static final int IMG_BMP = 0;
	public static final int IMG_JPEG = 1;
	public static final int IMG_PNG = 2;
	public static final int IMG_TIFF = 3;
	public static final int IMG_GIF = 4;
	public static final int IMG_EPS = 5;
	//public static String workbenchdirectory = ".";
	
	Shell shell;
	Display display;
	
	IKesoData data;
	KesoConfigurationText configurationtext;
	KesoCanvas canvas;
	KesoDataTree datatree;
	KesoPropertyGrid propertygrid;
	KesoToolManager manager;
	KesoExceptionList exceptionlist;
	
	String filename;
	
	KesoData selection;
	
	private Menu menubar;
	private CoolBar coolbar;
	private KesoStatusBar statusbar;
	
	boolean datachanged = false;

	PropertyChangeSupport change = new PropertyChangeSupport(this);
	
	CTabFolder mainfolder;
	CTabItem canvasitem;
	CTabItem textitem;
	
	StyledText console;
	
	CTabFolder lowesttab;
	CTabItem consoleitem;
	
	
	public KesoMainWindow(Display display) {
		this.shell = new Shell(display);
		
		KesoGuiProperties properties = KesoGuiProperties.getInstance();
		
		
		
		int main_window_width = 0;
		int main_window_height = 0;
		
		String tmp = properties.getProperty("mainwindow.width");
		if (tmp != null) {
			main_window_width = Integer.parseInt(tmp);
		}
		tmp = properties.getProperty("mainwindow.height");
		if (tmp != null) {
			main_window_height = Integer.parseInt(tmp);
		}
		
		if (main_window_width > 400 && main_window_height > 300) {
			Rectangle clientarea = display.getClientArea();
			Point position = this.shell.getLocation();
			int right = position.x + main_window_width;
			int bottom = position.y + main_window_height;
			
			if (right > clientarea.x + clientarea.width) {
				main_window_width -= right - (clientarea.x + clientarea.width);
			}
			
			if (bottom > clientarea.y + clientarea.height) {
				main_window_height -= bottom - (clientarea.y + clientarea.height);
			}
			
			this.shell.setSize(main_window_width, main_window_height);
		}
		
		tmp = properties.getProperty("mainwindow.maximized");
		if (tmp != null) {
			this.shell.setMaximized(tmp.toLowerCase().equals("true"));
		}
		
		
		
		this.shell.addDisposeListener(this);
		this.shell.addShellListener(this);
		this.shell.addControlListener(this);
		
		this.setTitle();
		this.display = display;
		
		this.manager = new KesoToolManager();
		
		this.createMenu();
		
		GridLayout gridlayout = new GridLayout();
		gridlayout.numColumns = 1;
		gridlayout.marginBottom = 0;
		gridlayout.marginHeight = 2;
		gridlayout.marginLeft = 0;
		gridlayout.marginRight = 0;
		gridlayout.marginTop = 0;
		gridlayout.marginWidth = 0;
		
		shell.setLayout(gridlayout);
		
		this.createToolBar();
		
		SashForm sash = new SashForm(shell, SWT.HORIZONTAL);	
		sash.setLayoutData(new GridData(GridData.FILL_BOTH));
		sash.setLayout(new FillLayout());
		
		this.datatree = new KesoDataTree(sash, SWT.FLAT);		
		
		/*
		SashForm sash2 = new SashForm(sash, SWT.HORIZONTAL);
		sash2.setLayout(new FillLayout());
		*/
		
		SashForm sash3 = new SashForm(sash, SWT.VERTICAL);
		
		
		this.mainfolder = new CTabFolder(sash3, SWT.BORDER);
		this.mainfolder.addSelectionListener(this);
		this.canvasitem = new CTabItem(this.mainfolder, SWT.NONE);
		this.mainfolder.setSelection(this.canvasitem);
		this.canvasitem.setText("Graphical View");
		this.canvasitem.setImage(KesoImageManager.getInstance().getImage("canvas"));
		this.canvas = new KesoCanvas(this.mainfolder, SWT.FLAT);
		this.canvas.setParentWindow(this);
		this.canvasitem.setControl(this.canvas.getWidget());
		
		
		this.textitem = new CTabItem(this.mainfolder, SWT.NONE);
		this.mainfolder.setSelection(this.textitem);
		this.textitem.setText("Textual View");
		this.textitem.setImage(KesoImageManager.getInstance().getImage("configtext"));
		
		this.configurationtext = new KesoConfigurationText(this.mainfolder, SWT.FLAT);
		this.configurationtext.setParentWindow(this);
		
		this.textitem.setControl(this.configurationtext.getWidget());
		this.mainfolder.setSelection(this.canvasitem);
		
		
		this.lowesttab = new CTabFolder(sash3, SWT.BORDER);
		CTabItem errors = new CTabItem(this.lowesttab, SWT.NONE);
		this.lowesttab.setSelection(errors);
		errors.setText("Errors");
		errors.setImage(KesoImageManager.getInstance().getImage("error"));
		
		Composite comp =  new Composite(this.lowesttab, SWT.NONE);
		errors.setControl(comp);
		comp.setLayout(new FillLayout());
		
		this.exceptionlist = new KesoExceptionList(comp);
		this.exceptionlist.addPropertyChangeListener(this);
		this.addPropertyChangeListener(this.exceptionlist);
		
		this.consoleitem = new CTabItem(this.lowesttab, SWT.NONE);
		this.consoleitem.setText("Console");
		this.consoleitem.setImage(KesoImageManager.getInstance().getImage("consolewindow"));
		
		comp =  new Composite(this.lowesttab, SWT.NONE);
		this.consoleitem.setControl(comp);
		comp.setLayout(new FillLayout());
		
		this.console = new StyledText(comp, SWT.NONE | SWT.H_SCROLL | SWT.V_SCROLL);
		
		
		sash3.setWeights(new int[] {4, 1});
		
		this.propertygrid = new KesoPropertyGrid(sash, SWT.FLAT);
		
		sash.setWeights(new int[] {2, 6, 3});
		
		this.statusbar = new KesoStatusBar(this.shell, SWT.NONE);
		this.statusbar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		this.canvas.setStatusbar(this.statusbar);
		
		this.manager.setCanvas(this.canvas);
		this.manager.setDataTree(this.datatree);
		this.manager.setTool(new SelectionKesoTool());
		
		this.canvas.addPropertyChangeListener(this);
		this.datatree.addPropertyChangeListener(this);
		this.propertygrid.addPropertyChangeListener(this);
		
		this.addPropertyChangeListener(this.canvas);
		this.addPropertyChangeListener(this.datatree);
		this.addPropertyChangeListener(this.exceptionlist);
		
		this.setData(null);
	}
	
	public void open() {
		shell.open();
	}
	
	public void run() {
		String tmp = KesoGuiProperties.getInstance().getProperty("workbench.directory");
		if (tmp == null) {
			this.setWorkbench(true);
		}
		while(!shell.isDisposed()) {
			this.canvas.paint();
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
		KesoImageManager.getInstance().dispose();
	}
	
	public StyledText getConsole() {
		return this.console;
	}
	
	public KesoToolManager getToolManager() {
		return this.manager;
	}
	
	public void setTitle() {
		String title = "Keso Graphical Editor";
		if (this.filename != null) {
			title += " - " + this.filename;
		} else {
			if (this.getData() != null) {
				title += " - <UNDEFINED>";
			}
		}
		this.shell.setText(title);
		
	}
	
	public CoolBar getToolBar() {
		return this.coolbar;
	}
	
	public void createToolBar() {
		this.coolbar = new CoolBar(this.shell, SWT.NULL);
		coolbar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		
		KesoStandardToolBar standard = new KesoStandardToolBar(this);
		KesoToolsToolBar tools = new KesoToolsToolBar(this);
	}
	
	public KesoStatusBar getStatusBar() {
		return this.statusbar;
	}
	
	public void print() {
		PrintDialog dialog = new PrintDialog(this.shell);
		PrinterData printerdata = dialog.open();
		if (printerdata != null) {
			if (this.mainfolder.getSelection() == this.canvasitem) {
				Printer printer = new Printer(printerdata);
				Rectangle clientarea = printer.getClientArea();
				
				org.eclipse.swt.graphics.Point screenDPI = this.getShell().getDisplay().getDPI();
				org.eclipse.swt.graphics.Point printerDPI = printer.getDPI();
				int scaleFactor = printerDPI.x / screenDPI.x;
				
				int image_width = (int) Math.ceil(scaleFactor * (this.getCanvas().getRootShape().getWidth() + 1));
				int image_height = (int) Math.ceil(scaleFactor * (this.getCanvas().getRootShape().getHeight() + 1));
				
				if (printer.startJob("Keso")) {
					int x = 0;
					int y = 0;
					
					while (y < image_height) {
						int tmp_sub_image_width = image_width - x;
						int sub_image_height = Math.min(image_height - y, clientarea.height);
						int sub_image_width = Math.min(tmp_sub_image_width, clientarea.width);
				
						int local_x = 0;
						int local_y = 0;
						int small_image_width = 500;
						int small_image_height = 500;
						
						if (printer.startPage()) {
						
							while (local_y < sub_image_height) {
								int tmp_width = sub_image_width - local_x;
								int width = Math.min(tmp_width, small_image_width);
								int height = Math.min(sub_image_height - local_y, small_image_height);
								
								Image img = new Image(printer, width, height);
								GC gc = new GC(img);
								KesoDrawingManager drawingmanager = new KesoDrawingManager(
										new SwtKesoGraphics(gc),
										PrinterKesoShapeStyleManager.getInstance());
								drawingmanager.shapescale = scaleFactor;
								
								drawingmanager.start_offsetx = -1 * (x + local_x) / scaleFactor;
								drawingmanager.start_offsety = -1 * (y + local_y) / scaleFactor;
								
								this.canvas.getGraphic(drawingmanager);
								
								gc.dispose();
								
								
								GC printergc = new GC(printer);
								printergc.drawImage(img, local_x, local_y);
								printergc.dispose();
								
								img.dispose();
								
								
								if (width == tmp_width) {
									local_x = 0;
									local_y += height;
								} else {
									local_x += width;
								}
							}
							
							if (sub_image_width == tmp_sub_image_width) {
								x = 0;
								y += sub_image_height;
							} else {
								x += sub_image_width;
							}
						}
						printer.endPage();
					}
					printer.endJob();
				}
					
					
					
					
					
					
					
					
					
					
					
					
					
					
				printer.dispose();
				
			} else if (this.mainfolder.getSelection() == this.textitem) {
				Printer printer = new Printer(printerdata);
				this.configurationtext.print(printer);
				printer.dispose();
			}
		}
	}
	
	/**
	 * @return  the canvas
	 * @uml.property  name="canvas"
	 */
	public KesoCanvas getCanvas() {
		return this.canvas;
	}
	
	public Menu getMenuBar() {
		return this.menubar;
	}
	
	public void createMenu() {
		this.menubar = new Menu(this.shell, SWT.BAR);
		this.shell.setMenuBar(this.menubar);
		
		new KesoFileMenu(this);
		new KesoToolsMenu(this);
		new KesoCanvasMenu(this);
		new KesoHelpMenu(this);
		
	}
	
	/**
	 * @param data  the data to set
	 * @uml.property  name="data"
	 */
	public void setData(IKesoData data) {
		this.data = data;
		this.canvas.setData(data);
		this.datatree.setData(data);
		//this.propertygrid.setData(null);
		this.exceptionlist.setData(data);
		if (this.data != null) {
			this.canvas.initCoordinatesAutomatically();
		}
		this.change.firePropertyChange("global data changed", null, null);
		//this.selection = null;
		
		this.configurationtext.setData(data);
		
		this.canvas.setSelection(data);
	}
	
	/**
	 * @return  the data
	 * @uml.property  name="data"
	 */
	public IKesoData getData() {
		return this.data;
	}
	
	/**
	 * @return  the shell
	 * @uml.property  name="shell"
	 */
	public Shell getShell() {
		return this.shell;
	}
	
	public void openFile() {
		
		if (this.canProceed())  {

			boolean finished = false;
			while (!finished) {
				FileDialog filedialog = new FileDialog(this.shell, SWT.OPEN);
				filedialog.setFilterExtensions(new String [] {"*.kcl"});
				filedialog.setFilterNames(new String [] {"Keso Configuration File (*.kcl)"});
				String file = filedialog.open();
				if (file == null) {
					finished = true;
				} else {
					finished = this.openFile(file);
				}
			}
		}
	}
	
	public boolean openFile(String filename) {
		boolean finished = false;
		//if (this.canProceed())  {
			try {
				IKesoData world = KesoConfigurationLoader.loadFile(filename);
				KesoPropertyManager.fillUpData(world);
				this.reset();
				this.setData(world);
				this.canvas.initCoordinatesAutomatically();
				this.canvas.getRootShape().initMinDimension();
				finished = true;
				this.filename = filename;
				this.setTitle();
				
				Vector lastopen = new Vector();
				KesoGuiProperties properties = KesoGuiProperties.getInstance();
				for (int i = 1; i <= 4; i++) {
					String tmp = properties.getProperty("lastopenfile." + i);
					if (tmp != null && !tmp.equals(this.filename)) {
						lastopen.add(tmp);
					}
				}
	
				properties.setProperty("lastopenfile.1", this.filename);
				for (int i = 0; i < 3 && i < lastopen.size(); i++) {
					properties.setProperty("lastopenfile." + (i + 2), (String) lastopen.get(i));
				}
			} catch (Exception exc) {
				exc.printStackTrace();
				
				MessageBox messagebox = new MessageBox(this.shell, 
						SWT.OK | SWT.ICON_ERROR);
				messagebox.setText("Could not open configuration");
				messagebox.setMessage("'" + filename + "' is not a valid Keso configuration!");
				messagebox.open();
			}
		//}
		return finished;
	}
	
	/**
	 * @return  the filename
	 * @uml.property  name="filename"
	 */
	public String getFilename() {
		return this.filename;
	}
	
	public boolean canProceed() {
		if (this.getData() != null) {
			if (this.isDataChanged()) {
				if (this.exceptionlist.size() != 0) {
					MessageBox messagebox = new MessageBox(this.shell, 
							 SWT.NO | SWT.YES | SWT.ICON_QUESTION);
					messagebox.setText("Do you want to proceed?");
					messagebox.setMessage("Your configuration was changed!\n" +
							"You can not save your configuration " +
							"as long as it has exceptions.\n" +
							"Do you want to proceed and lose the changes?");
					int result = messagebox.open();
					if (result == SWT.YES) {
						return true;
					} else {
						return false;
					}
				} else {
					MessageBox messagebox = new MessageBox(this.shell, 
							SWT.YES | SWT.NO | SWT.CANCEL | SWT.ICON_QUESTION);
					messagebox.setText("Keso Graphical Editor");
					String text = "Save changes";
					if (this.getFilename() != null) {
						text += " to '" + this.getFilename() + "'";
					}
					text += "?";
					messagebox.setMessage(text);
					int result = messagebox.open();
					
					if (result == SWT.YES) {
						this.save();
						if (this.isDataChanged()) {
							return false;
						}
						return true;
					} else if (result == SWT.NO) {
						return true;
					} else {
						return false;
					}
				}
			}
		
		}
		return true;
	}

	public void saveAs() {
		if (this.data != null) {
			boolean repeat = true;
			while (repeat) {
				repeat = false;
				FileDialog filedialog = new FileDialog(this.shell, SWT.SAVE);
				filedialog.setFilterExtensions(new String [] {"*.kcl"});
				filedialog.setFilterNames(new String [] {"Keso Configuration File"});
				String filename = filedialog.open();
				if (filename != null) {
					boolean done = false;
					File file = new File(filename);
			        if (file.exists()) {
			        	MessageBox mb = new MessageBox(this.shell, 
								SWT.ICON_WARNING | SWT.YES | SWT.NO);
						
						mb.setMessage("'" + filename + "' already exists. Do you want to replace it?");
						
						if (mb.open() == SWT.YES) {
							  done = true;
						} else {
							 repeat = true;
						}
			        } else {
			        	done = true;
			        }
				
					if (done) {
						this.filename = filename;
						this.proceedSave();
						this.setTitle();
					}
				}
			}
		}
	}
	
	public void save() {
		if (this.data != null) {
			if (this.filename == null) {
				this.saveAs();
			} else {
				this.proceedSave();
			}
		} else {
			this.proceedSave();
		}
	}
	
	public void proceedSave() {
		String [] extension = this.filename.split("\\.");
		
		if (!extension[extension.length - 1].trim().toLowerCase().equals("kcl")) {
			this.filename = this.filename.trim() + ".kcl";
		} 
		
		
		ConfigurationKesoOutput output = new ConfigurationKesoOutput(this.filename);
		output.setData(this.data);
		output.output();	
		this.setDataChanged(false);
	}
	
	public void reset() {
		this.filename = null;
		this.setData(null);
		this.setDataChanged(false);
	}
	
	public void close() {
		if (this.canProceed()) {
			this.reset();
		}
	}
	
	public void export() {
		if (this.data != null) {
			boolean repeat = true;
			while(repeat) {
				repeat = false;
				FileDialog filedialog = new FileDialog(this.shell, SWT.SAVE);
				
				filedialog.setFilterExtensions(new String [] {"*.bmp", "*.jpg; *.jpeg", "*.eps"});
				filedialog.setFilterNames(new String [] {"BMP (*.bmp)", "JPEG (*.jpg; *.jpeg)", "EPS (*.eps)"});
				
				String filename = filedialog.open();
				if (filename != null) {
					filename = filename.trim();
					if (filename.length() == 0) {
						filename = null;
					}
				}
				
				if (filename != null) {
					if (filename != null) {
						boolean done = false;
						File file = new File(filename);
				        if (file.exists()) {
				        	MessageBox mb = new MessageBox(this.shell, 
									SWT.ICON_WARNING | SWT.YES | SWT.NO);
							
							mb.setMessage("'" + filename + "' already exists. Do you want to replace it?");
							
							if (mb.open() == SWT.YES) {
								  done = true;
							} else {
								 repeat = true;
							}
				        } else {
				        	done = true;
				        }
					
						if (done) {
							int type = IMG_BMP;
							
							String [] extension = filename.split("\\.");
	
							if (extension[extension.length - 1].trim().toLowerCase().equals("bmp")) {
								type = IMG_BMP;
							} else if (extension[extension.length - 1].trim().toLowerCase().equals("jpg") ||
								extension[extension.length - 1].trim().toLowerCase().equals("jpeg")) {
								type = IMG_JPEG;
							} else if (extension[extension.length - 1].trim().toLowerCase().equals("eps")){
								type = IMG_EPS;
							} else {
								type = IMG_BMP;
								filename += ".bmp";
							}
							
							if (type != IMG_EPS) {
								Image img = new Image(this.shell.getDisplay(), this.getCanvas().getRootShape().getWidth() + 5, this.getCanvas().getRootShape().getHeight() + 5); 
								GC gc = new GC(img);
								//org.eclipse.swt.graphics.Font font = new org.eclipse.swt.graphics.Font(gc.getDevice(), "Tahoma", 8, SWT.NORMAL);
								//gc.setFont(font);
								
								IKesoShapeStyleManager stylemanager;// = ExportKesoShapeStyleManager.getInstance();
								
								switch(type) {
									case IMG_JPEG:
										stylemanager = JpegExportKesoShapeStyleManager.getInstance();
										break;
									case IMG_BMP:
									default:
										stylemanager = BmpExportKesoShapeStyleManager.getInstance();
								}
								
								//SwtKesoGraphics swtgc = new SwtKesoGraphics(gc);
								//swtgc.setTextAntialias(true);
								this.canvas.getGraphic(new KesoDrawingManager(new SwtKesoGraphics(gc), stylemanager));
								
								
								gc.dispose();
								
								ImageLoader loader = new ImageLoader();
								loader.data = new ImageData[] {img.getImageData()};
								
								switch(type) {
									case IMG_PNG:
										loader.save(filename, SWT.IMAGE_PNG);
										break;
									case IMG_GIF:
										loader.save(filename, SWT.IMAGE_GIF);
										break;
									case IMG_TIFF:
										loader.save(filename, SWT.IMAGE_TIFF);
										break;
									case IMG_JPEG:
										loader.save(filename, SWT.IMAGE_JPEG);
										break;
									default:
										loader.save(filename, SWT.IMAGE_BMP_RLE);
										break;
								}
								img.dispose();
							} else {
								try {
									EpsGraphics eps = new EpsGraphics("Keso Graphical Editor", new FileOutputStream(filename), 
											0, 0, this.getCanvas().getRootShape().getWidth() + 5, this.getCanvas().getRootShape().getHeight() + 5,
											ColorMode.COLOR_RGB);
									eps.setClip(0, 0, this.getCanvas().getRootShape().getWidth() + 5, this.getCanvas().getRootShape().getHeight() + 5);
									this.getCanvas().getGraphic(new KesoDrawingManager(new EpsKesoGraphics(eps), EpsExportKesoShapeStyleManager.getInstance()));
									eps.flush();
									eps.close();
								} catch (Exception e) {
									e.printStackTrace();
									MessageBox messagebox = new MessageBox(this.shell, 
											SWT.OK | SWT.ICON_ERROR);
									messagebox.setText("Could not create EPS");
									messagebox.setMessage("'" + filename + "' could not be created!");
									messagebox.open();
								}
							}
						}
					}
				}
			}
		}
	}

	public void newConfiguration() {
		if (this.canProceed()) {
			IKesoData world = new KesoData(KGE.WORLD, "world");
			IKesoData system = new KesoData(KGE.NODE, "node");
			IKesoData domain = new KesoData(KGE.DOMAIN, "domain");
			IKesoData task = new KesoData(KGE.TASK, "task");
			world.addChild(system);
			system.addChild(domain);
			domain.addChild(task);
			KesoPropertyManager.fillUpData(world);
			this.setData(world);
			
			this.filename = null;
			this.setTitle();
			
			this.setDataChanged(true);
			this.change.firePropertyChange("new configuration", null, null);
		}
	}
	
	public void showProperties() {
		new KesoPropertyDialog(this.shell);
		this.canvas.redraw();
	}
	
	public void exit() {
		if (this.canProceed()) {
			this.getShell().dispose();
		}
	}
	
	public void propertyChange(PropertyChangeEvent e) {
		if (e.getPropertyName().equals("selection changed")) {
			this.canvas.setSelection((IKesoData) e.getNewValue());
			this.datatree.setSelection((IKesoData) e.getNewValue());
			this.propertygrid.setData((IKesoData) e.getNewValue());
			//this.propertygrid.setData((IKesoData) e.getNewValue());
			this.change.firePropertyChange(e);
		} else if (e.getPropertyName().equals("data changed")) {
			this.setDataChanged(true);
			this.change.firePropertyChange("data changed", null, null);
		} else if (e.getPropertyName().equals("property changed")) {
			this.setDataChanged(true);
			this.change.firePropertyChange("property changed", null, null);
		} else if (e.getPropertyName().equals("checked for exceptions")) {
			this.change.firePropertyChange("checked for exceptions", null, null);
		} else if (e.getPropertyName().equals("zoom changed")) {
			this.change.firePropertyChange("zoom changed", null, null);
		}

		if (this.mainfolder.getSelection() == this.textitem) {
			this.configurationtext.updateText();
		}
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.change.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.change.removePropertyChangeListener(listener);
	}
	
	public void setDataChanged(boolean datachanged) {
		this.datachanged = datachanged;
		this.change.firePropertyChange("data changed", null, null);
	}
	
	public boolean isDataChanged() {
		return this.datachanged;
	}

	public void widgetDisposed(DisposeEvent e) {
		KesoImageManager.getInstance().dispose();
	}

	public void checkForExceptions() {
		this.exceptionlist.check();
	}
	
	public KesoExceptionList getExceptionList() {
		return this.exceptionlist;
	}

	public void shellActivated(ShellEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void shellClosed(ShellEvent e) {
		if (this.canProceed()) {
			e.doit = true;
		} else {
			e.doit = false;
		}
	}

	public void shellDeactivated(ShellEvent e) {
		
	}

	public void shellDeiconified(ShellEvent e) {
		
	}

	public void shellIconified(ShellEvent e) {
		
	}

	public void widgetSelected(SelectionEvent e) {
		if (e.widget == this.mainfolder) {
			if (e.item == this.textitem) {
				//this.configurationtext.setData(this.getData());
				this.configurationtext.updateText();
			}
		}
	}

	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void controlMoved(ControlEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void controlResized(ControlEvent e) {
		KesoGuiProperties properties = KesoGuiProperties.getInstance();
		if (e.widget instanceof Shell) {
			Shell shell = (Shell) e.widget;
			if (shell.getMaximized()) {
				properties.setProperty("mainwindow.maximized", "true");
			} else {
				properties.setProperty("mainwindow.maximized", "false");
				properties.setProperty("mainwindow.width", Integer.toString(shell.getSize().x));
				properties.setProperty("mainwindow.height", Integer.toString(shell.getSize().y));
			}
		}
	}
	
	public void setWorkbench(boolean force) {
		DirectoryDialog dialog = new DirectoryDialog(this.shell);
		dialog.setText("Switch Workbench");
		dialog.setMessage("Please choose or create a directory for KGEs workbench:");
		if (KesoGuiProperties.getInstance().getProperty("workbench.directory") != null) {
			dialog.setFilterPath(KesoGuiProperties.getInstance().getProperty("workbench.directory"));
		}
		boolean stop = false;
		while (!stop) {
			String directory = dialog.open();
			if (directory != null) {
				if (this.checkWorkbench(directory)) {
						KesoGuiProperties.getInstance().setProperty("workbench.directory", directory);
						stop = true;
						if (this.getData() != null) {
							this.exceptionlist.check();
						}
				}
			} else {
				if (!force) {
					stop = true;
				}
			}
		}
	}
	
	public boolean checkWorkbench(String directory) {
		File file = new File(directory);
		if (file.exists()) {
			if (file.isDirectory()) {
				return true;
			}
		}
		return false;
	}

	public void compile() {
		if (this.isDataChanged()) {
			this.save();
		}
		
		if (this.isDataChanged()) {
			MessageBox messagebox = new MessageBox(this.shell, SWT.OK);
			messagebox.setText("Save your Configuration!");
			messagebox.setMessage("Before compiling you have to save your Configuration!");
			messagebox.open();
		} else {
			CTabItem tmp = this.lowesttab.getSelection();
			this.lowesttab.setSelection(this.consoleitem);
			new KesoCompilationSettingsDialog(this);
			this.lowesttab.setSelection(tmp);
		}
	}
}
