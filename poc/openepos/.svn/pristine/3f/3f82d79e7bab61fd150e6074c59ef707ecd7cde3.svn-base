package keso.editor.gui.core;

import java.util.Enumeration;

import keso.editor.KGE;
import keso.editor.gui.graphics.KesoColor;
import keso.editor.gui.shape.design.style.IKesoShapeStyle;
import keso.editor.gui.shape.design.stylemanager.BmpExportKesoShapeStyleManager;
import keso.editor.gui.shape.design.stylemanager.EpsExportKesoShapeStyleManager;
import keso.editor.gui.shape.design.stylemanager.IKesoShapeStyleManager;
import keso.editor.gui.shape.design.stylemanager.JpegExportKesoShapeStyleManager;
import keso.editor.gui.shape.design.stylemanager.PrinterKesoShapeStyleManager;
import keso.editor.gui.shape.design.stylemanager.ScreenKesoShapeStyleManager;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class KesoPropertyDialog implements SelectionListener {
	Shell shell;
	Shell parentshell;
	Display display;
	
	CTabFolder folder;
	CTabItem styleitem;
	Composite stylecomposite;
	
	Tree styletree;
	
	IKesoShapeStyle shapestyle;
	IKesoShapeStyleManager shapestylemanager;
	
	
	Group shapecolorgrp;
	Group connectioncolorgrp;
	
	Label edgecolorlbl;
	Label backgroundcolorlbl;
	Label textcolorlbl;
	Label connectioncolorlbl;
	Label highlightcolorlbl;
	private Button defbtn;
	
	
	public KesoPropertyDialog(Shell parentshell) {
		this.parentshell = parentshell;
		this.display = this.parentshell.getDisplay();
		this.shell = new Shell(parentshell, SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM | SWT.RESIZE);
		this.shell.setSize(600, 400);

		
		Rectangle clientarea = this.parentshell.getBounds();
		int mx = (clientarea.x + clientarea.x + clientarea.width) / 2 - (this.shell.getSize().x / 2);
		int my = (clientarea.y + clientarea.y + clientarea.height) / 2 - (this.shell.getSize().y / 2);
		
		this.shell.setLocation(mx, my);
		
		GridLayout glayout = new GridLayout();
		glayout.numColumns = 1;
		
		this.shell.setLayout(glayout);
		this.folder = new CTabFolder(this.shell, SWT.BORDER);
		this.folder.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		this.createStyleItem();
		
		
		Composite comp = new Composite(this.shell, SWT.NONE);
		comp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		glayout = new GridLayout();
		glayout.numColumns = 2;
		glayout.marginBottom = 0;
		glayout.marginHeight = 0;
		glayout.marginLeft = 0;
		glayout.marginRight = 0;
		glayout.marginTop = 0;
		glayout.marginWidth = 0;
		comp.setLayout(glayout);
		
		
		Composite fakecomp = new Composite(comp, SWT.NONE);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 1;
		fakecomp.setLayoutData(gd);
				
		Button button = new Button(comp, SWT.NONE);
		gd = new GridData();
		//gd.widthHint = 100;
		//gd.horizontalAlignment = SWT.RIGHT;
		button.setLayoutData(gd);
		button.setText("Close");
		button.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
			}

			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
			
		});
		
		
		//this.shell.pack();
		
		this.shell.open();
		while(!this.shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	public void createStyleItem() {
		this.styleitem = new CTabItem(this.folder, SWT.NONE);
		this.styleitem.setText("Shape Styles");
		Composite comp = new Composite(this.folder, SWT.NONE);
		this.styleitem.setControl(comp);
		comp.setLayout(new FillLayout());
		
		SashForm sash = new SashForm(comp, SWT.HORIZONTAL);
		
		this.styletree = new Tree(sash, SWT.NONE);
		this.styletree.addSelectionListener(this);
		
		this.fillStyleTree();
		
	
		this.stylecomposite = new Composite(sash, SWT.NONE);
		this.stylecomposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		GridLayout glayout = new GridLayout();
		glayout.numColumns = 1;
		this.stylecomposite.setLayout(glayout);
		
		
		this.shapecolorgrp = new Group(this.stylecomposite, SWT.NONE);
		this.shapecolorgrp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		this.shapecolorgrp.setText("Shape Colors");
		
		glayout = new GridLayout();
		glayout.numColumns = 3;
		this.shapecolorgrp.setLayout(glayout);
		
		/*            */
		
		Label label = new Label(this.shapecolorgrp, SWT.NONE);
		label.setLayoutData(new GridData());
		label.setText("Background:");
		
		this.backgroundcolorlbl = new Label(this.shapecolorgrp, SWT.BORDER);
		this.backgroundcolorlbl.setLayoutData(new GridData(GridData.FILL_BOTH));
		this.backgroundcolorlbl.setForeground(new Color(display, 0, 0, 0));
		
		Button btn = new Button(this.shapecolorgrp, SWT.FLAT);
		btn.setLayoutData(new GridData());
		btn.setText("...");
		btn.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void widgetSelected(SelectionEvent e) {
				ColorDialog colordialog = new ColorDialog(shell, SWT.NONE);
				Color color = backgroundcolorlbl.getBackground();
				RGB rgb = color.getRGB();
				color.dispose();
				colordialog.setRGB(rgb);
				rgb = colordialog.open();
				if (rgb != null) {
					color = new Color(display, rgb);
					backgroundcolorlbl.setBackground(color);
					color.dispose();
					shapestyle.setColor("COLOR_BACKGROUND", new KesoColor(rgb.red, rgb.green, rgb.blue));
				}
			}
			
		});
		
		/* ---------------------------------------- */
		label = new Label(this.shapecolorgrp, SWT.NONE);
		label.setLayoutData(new GridData());
		label.setText("Edge:");
		
		this.edgecolorlbl = new Label(this.shapecolorgrp, SWT.BORDER);
		this.edgecolorlbl.setLayoutData(new GridData(GridData.FILL_BOTH));
		this.edgecolorlbl.setForeground(new Color(display, 0, 0, 0));
		
		btn = new Button(this.shapecolorgrp, SWT.FLAT);
		btn.setLayoutData(new GridData());
		btn.setText("...");
		btn.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void widgetSelected(SelectionEvent e) {
				ColorDialog colordialog = new ColorDialog(shell, SWT.NONE);
				Color color = edgecolorlbl.getBackground();
				RGB rgb = color.getRGB();
				color.dispose();
				colordialog.setRGB(rgb);
				rgb = colordialog.open();
				if (rgb != null) {
					color = new Color(display, rgb);
					edgecolorlbl.setBackground(color);
					color.dispose();
					shapestyle.setColor("COLOR_FOREGROUND", new KesoColor(rgb.red, rgb.green, rgb.blue));
				}
			}
			
		});
		
		/* ---------------------------------------- */
		label = new Label(this.shapecolorgrp, SWT.NONE);
		label.setLayoutData(new GridData());
		label.setText("Text:");
		
		this.textcolorlbl = new Label(this.shapecolorgrp, SWT.BORDER);
		this.textcolorlbl.setLayoutData(new GridData(GridData.FILL_BOTH));
		this.textcolorlbl.setForeground(new Color(display, 0, 0, 0));
		
		btn = new Button(this.shapecolorgrp, SWT.FLAT);
		btn.setLayoutData(new GridData());
		btn.setText("...");
		btn.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void widgetSelected(SelectionEvent e) {
				ColorDialog colordialog = new ColorDialog(shell, SWT.NONE);
				Color color = textcolorlbl.getBackground();
				RGB rgb = color.getRGB();
				color.dispose();
				colordialog.setRGB(rgb);
				rgb = colordialog.open();
				if (rgb != null) {
					color = new Color(display, rgb);
					textcolorlbl.setBackground(color);
					color.dispose();
					shapestyle.setColor("COLOR_TEXT", new KesoColor(rgb.red, rgb.green, rgb.blue));
				}
			}
		});
		
		this.connectioncolorgrp = new Group(this.stylecomposite, SWT.NONE);
		this.connectioncolorgrp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		this.connectioncolorgrp.setText("Connection Colors");
		
		glayout = new GridLayout();
		glayout.numColumns = 3;
		this.connectioncolorgrp.setLayout(glayout);
		
		label = new Label(this.connectioncolorgrp, SWT.NONE);
		label.setLayoutData(new GridData());
		label.setText("Connection:");
		
		this.connectioncolorlbl = new Label(this.connectioncolorgrp, SWT.BORDER);
		this.connectioncolorlbl.setLayoutData(new GridData(GridData.FILL_BOTH));
		this.connectioncolorlbl.setForeground(new Color(display, 0, 0, 0));
		
		btn = new Button(this.connectioncolorgrp, SWT.FLAT);
		btn.setLayoutData(new GridData());
		btn.setText("...");
		btn.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void widgetSelected(SelectionEvent e) {
				ColorDialog colordialog = new ColorDialog(shell, SWT.NONE);
				Color color = connectioncolorlbl.getBackground();
				RGB rgb = color.getRGB();
				color.dispose();
				colordialog.setRGB(rgb);
				rgb = colordialog.open();
				if (rgb != null) {
					color = new Color(display, rgb);
					connectioncolorlbl.setBackground(color);
					color.dispose();
					for (Enumeration element = shapestylemanager.getStyles().elements(); element.hasMoreElements();) {
						IKesoShapeStyle style = (IKesoShapeStyle) element.nextElement();
						style.setColor("COLOR_CONNECTION", new KesoColor(rgb.red, rgb.green, rgb.blue));
					}
				}
			}
			
		});
		
		/* ---------------------------------------- */
		label = new Label(this.connectioncolorgrp, SWT.NONE);
		label.setLayoutData(new GridData());
		label.setText("Highlighted Connection:");
		
		this.highlightcolorlbl = new Label(this.connectioncolorgrp, SWT.BORDER);
		this.highlightcolorlbl.setLayoutData(new GridData(GridData.FILL_BOTH));
		this.highlightcolorlbl.setForeground(new Color(display, 0, 0, 0));
		
		btn = new Button(this.connectioncolorgrp, SWT.FLAT);
		btn.setLayoutData(new GridData());
		btn.setText("...");
		btn.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void widgetSelected(SelectionEvent e) {
				ColorDialog colordialog = new ColorDialog(shell, SWT.NONE);
				Color color = highlightcolorlbl.getBackground();
				RGB rgb = color.getRGB();
				color.dispose();
				colordialog.setRGB(rgb);
				rgb = colordialog.open();
				if (rgb != null) {
					color = new Color(display, rgb);
					highlightcolorlbl.setBackground(color);
					color.dispose();
					for (Enumeration element = shapestylemanager.getStyles().elements(); element.hasMoreElements();) {
						IKesoShapeStyle style = (IKesoShapeStyle) element.nextElement();
						style.setColor("COLOR_HIGHLIGHTED_CONNECTION", new KesoColor(rgb.red, rgb.green, rgb.blue));
					}
				}
			}
			
		});
		
		comp = new Composite(this.stylecomposite, SWT.NONE);
		comp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		glayout = new GridLayout();
		glayout.numColumns = 2;
		comp.setLayout(glayout);
		
		Composite fake = new Composite(comp, SWT.NONE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 1;
		fake.setLayoutData(gd);
		
		this.defbtn = new Button(comp, SWT.NONE);
		this.defbtn.setLayoutData(new GridData());
		this.defbtn.setText("Set Default Style");
		//this.defbtn.setEnabled(false);
		this.defbtn.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
				
			}

			public void widgetSelected(SelectionEvent e) {
				if (styletree.getSelectionCount() != 0) {
					TreeItem item = styletree.getSelection()[0];
					if (item.getData() instanceof IKesoShapeStyleManager) {
						shapestyle = null;
						shapestylemanager = (IKesoShapeStyleManager) item.getData();
						shapestylemanager.reset();
					} else if (item.getData() instanceof String) {
						IKesoShapeStyleManager manager = (IKesoShapeStyleManager) item.getParentItem().getData();
						shapestylemanager = null;
						manager.reset();
						shapestyle = manager.getStyle((String) item.getData());
					}
					updateStyle();
				}
			}
			
		});
		
		sash.setWeights(new int[] {2, 4});
		
		
		this.connectioncolorgrp.setEnabled(false);
		Control [] ctr = this.connectioncolorgrp.getChildren();
		for (int i = 0; i < ctr.length; i++) {
			ctr[i].setEnabled(false);
		}
		
		this.shapecolorgrp.setEnabled(false);
		ctr = this.shapecolorgrp.getChildren();
		for (int i = 0; i < ctr.length; i++) {
			ctr[i].setEnabled(false);
		}
	}
	
	public void fillStyles(TreeItem parent) {
		IKesoShapeStyleManager stylemanager = (IKesoShapeStyleManager) parent.getData();
		for (Enumeration e = stylemanager.getStyles().keys(); e.hasMoreElements(); ) {
			String strstyle = (String) e.nextElement();
			//IKesoShapeStyle style = stylemanager.getStyle(strstyle);
			TreeItem item = new TreeItem(parent, SWT.NONE);
			item.setText(strstyle);
			item.setData(strstyle);
		}
	}
	
	public void fillStyleTree() {
		IKesoShapeStyleManager stylemanager;
		
		TreeItem mitem;
		
		this.styletree.removeAll();
		
		stylemanager = BmpExportKesoShapeStyleManager.getInstance();
		mitem = new TreeItem(this.styletree, SWT.NONE);
		mitem.setText("BMP - Export Style");
		mitem.setData(stylemanager);
		this.fillStyles(mitem);
		
		stylemanager = EpsExportKesoShapeStyleManager.getInstance();
		mitem = new TreeItem(this.styletree, SWT.NONE);
		mitem.setText("EPS - Export Style");
		mitem.setData(stylemanager);
		this.fillStyles(mitem);
		
		stylemanager = JpegExportKesoShapeStyleManager.getInstance();
		mitem = new TreeItem(this.styletree, SWT.NONE);
		mitem.setText("JPEG - Export Style");
		mitem.setData(stylemanager);
		this.fillStyles(mitem);
		
		stylemanager = PrinterKesoShapeStyleManager.getInstance();
		mitem = new TreeItem(this.styletree, SWT.NONE);
		mitem.setText("Printer Style");
		mitem.setData(stylemanager);
		this.fillStyles(mitem);
		
		stylemanager = ScreenKesoShapeStyleManager.getInstance();
		mitem = new TreeItem(this.styletree, SWT.NONE);
		mitem.setText("Screen Style");
		mitem.setData(stylemanager);
		this.fillStyles(mitem);
		
	}

	public void updateColors() {
		Color color;
		
	}

	private void fillStyleContent(IKesoShapeStyleManager manager) {
		this.shapestyle = null;
		this.shapestylemanager = manager;
	}

	
	private void updateStyle() {
		if (this.shapestyle != null) {
			KesoColor kesocolor = this.shapestyle.getColor("COLOR_FOREGROUND");
			Color color = new Color(display, kesocolor.red, kesocolor.green, kesocolor.blue);
			this.edgecolorlbl.setBackground(color);
			color.dispose();
			
			kesocolor = this.shapestyle.getColor("COLOR_BACKGROUND");
			color = new Color(display, kesocolor.red, kesocolor.green, kesocolor.blue);
			this.backgroundcolorlbl.setBackground(color);
			color.dispose();
			
			kesocolor = this.shapestyle.getColor("COLOR_TEXT");
			color = new Color(display, kesocolor.red, kesocolor.green, kesocolor.blue);
			this.textcolorlbl.setBackground(color);
			color.dispose();
			
			this.connectioncolorlbl.setBackground(display.getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
			this.highlightcolorlbl.setBackground(display.getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		} else if (this.shapestylemanager != null) {
			KesoColor kesocolor = this.shapestylemanager.getStyle(KGE.WORLD).getColor("COLOR_CONNECTION");
			Color color = new Color(display, kesocolor.red, kesocolor.green, kesocolor.blue);
			this.connectioncolorlbl.setBackground(color);
			color.dispose();
			
			
			kesocolor = this.shapestylemanager.getStyle(KGE.WORLD).getColor("COLOR_HIGHLIGHTED_CONNECTION");
			color = new Color(display, kesocolor.red, kesocolor.green, kesocolor.blue);
			this.highlightcolorlbl.setBackground(color);
			color.dispose();
			
			this.backgroundcolorlbl.setBackground(display.getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
			this.edgecolorlbl.setBackground(display.getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
			this.textcolorlbl.setBackground(display.getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
			
			
		}
	}
	
	public void widgetSelected(SelectionEvent e) {
		if (e.item instanceof TreeItem) {
			TreeItem item = (TreeItem) e.item;
			if (item.getData() instanceof String) {
				IKesoShapeStyleManager manager = (IKesoShapeStyleManager) item.getParentItem().getData();
				this.shapestyle = manager.getStyle((String) item.getData());
				this.shapestylemanager = null;
				
				
				this.updateStyle();
				/*
				KesoColor kesocolor = this.shapestyle.getColor("COLOR_FOREGROUND");
				Color color = new Color(display, kesocolor.red, kesocolor.green, kesocolor.blue);
				this.edgecolorlbl.setBackground(color);
				color.dispose();
				
				kesocolor = this.shapestyle.getColor("COLOR_BACKGROUND");
				color = new Color(display, kesocolor.red, kesocolor.green, kesocolor.blue);
				this.backgroundcolorlbl.setBackground(color);
				color.dispose();
				
				kesocolor = this.shapestyle.getColor("COLOR_TEXT");
				color = new Color(display, kesocolor.red, kesocolor.green, kesocolor.blue);
				this.textcolorlbl.setBackground(color);
				color.dispose();
				
				this.connectioncolorlbl.setBackground(display.getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
				this.highlightcolorlbl.setBackground(display.getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
				*/
				this.connectioncolorgrp.setEnabled(false);
				Control [] ctr = this.connectioncolorgrp.getChildren();
				for (int i = 0; i < ctr.length; i++) {
					ctr[i].setEnabled(false);
				}
				
				this.shapecolorgrp.setEnabled(true);
				ctr = this.shapecolorgrp.getChildren();
				for (int i = 0; i < ctr.length; i++) {
					ctr[i].setEnabled(true);
				}
				
				//this.defbtn.setEnabled(false);
				
				
			} else if (item.getData() instanceof IKesoShapeStyleManager) {
				this.shapestyle = null;
				this.shapestylemanager = (IKesoShapeStyleManager) item.getData();
				
				/*
				KesoColor kesocolor = this.shapestylemanager.getStyle(KGE.WORLD).getColor("COLOR_CONNECTION");
				Color color = new Color(display, kesocolor.red, kesocolor.green, kesocolor.blue);
				this.connectioncolorlbl.setBackground(color);
				color.dispose();
				
				
				kesocolor = this.shapestylemanager.getStyle(KGE.WORLD).getColor("COLOR_HIGHLIGHTED_CONNECTION");
				color = new Color(display, kesocolor.red, kesocolor.green, kesocolor.blue);
				this.highlightcolorlbl.setBackground(color);
				color.dispose();
				
				this.backgroundcolorlbl.setBackground(display.getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
				this.edgecolorlbl.setBackground(display.getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
				this.textcolorlbl.setBackground(display.getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
				*/
				
				this.updateStyle();
				
				this.connectioncolorgrp.setEnabled(true);
				Control [] ctr = this.connectioncolorgrp.getChildren();
				for (int i = 0; i < ctr.length; i++) {
					ctr[i].setEnabled(true);
				}
				
				this.shapecolorgrp.setEnabled(false);
				ctr = this.shapecolorgrp.getChildren();
				for (int i = 0; i < ctr.length; i++) {
					ctr[i].setEnabled(false);
				}
				
				//this.defbtn.setEnabled(true);
			}
		}
	}

	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
