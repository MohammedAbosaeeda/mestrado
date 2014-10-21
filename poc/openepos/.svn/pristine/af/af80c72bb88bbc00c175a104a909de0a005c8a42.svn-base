package keso.editor.gui.propertygrid.dialogs;

import keso.editor.KGE;
import keso.editor.gui.propertygrid.KesoPropertyGrid;
import keso.editor.property.IKesoProperty;
import keso.editor.property.complexproperty.IKesoComplexProperty;
import keso.editor.property.complexproperty.KesoComplexProperty;
import keso.editor.property.propertymanager.KesoPropertyManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * @author  Wilhelm Haas
 */
public class KesoNewPropertyDialog implements SelectionListener {

	KesoPropertyGrid propertygrid;
	Shell shell;
	
	Text name;
	Button integerButton;
	Button stringButton;
	Button integerArrayButton;
	
	Button okbutton;
	Button cancelbutton;
	
	public KesoNewPropertyDialog(KesoPropertyGrid propertygrid) {
		this.propertygrid = propertygrid;
		this.shell = new Shell(this.propertygrid.getShell(), SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);
		this.shell.setText("Add New Property");
		
		//this.shell.setSize(335, 137);
		
		//Display display = this.shell.getDisplay();
		//Rectangle displayrect = display.getClientArea();
		
		
		GridLayout gridlayout = new GridLayout();
		gridlayout.numColumns = 1;
		this.shell.setLayout(gridlayout);
		
		Composite comp = new Composite(this.shell, SWT.NONE);
		comp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		gridlayout = new GridLayout();
		gridlayout.numColumns = 2;
		comp.setLayout(gridlayout);
		
		Label label = new Label(comp, SWT.NONE);
		label.setBounds(11, 10, 39, 13);
		label.setText("Name:");
		label.setLayoutData(new GridData());
		
		this.name = new Text(comp, SWT.BORDER);
		this.name.setBounds(56, 7, 256, 19);
		this.name.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		
		Group propertygrp = new Group(this.shell, SWT.NONE);
		propertygrp.setLayoutData(new GridData(GridData.FILL_BOTH));
		propertygrp.setText("Property Type");
		
		gridlayout = new GridLayout();
		gridlayout.numColumns = 3;
		propertygrp.setLayout(gridlayout);
		
		this.integerButton = new Button(propertygrp, SWT.RADIO);
		this.integerButton.setText("Integer");
		this.integerButton.setSelection(true);
		this.integerButton.setLayoutData(new GridData());
		this.stringButton = new Button(propertygrp, SWT.RADIO);
		this.stringButton.setText("String");
		
		this.stringButton.setLayoutData(new GridData());
		this.integerArrayButton = new Button(propertygrp, SWT.RADIO);
		this.integerArrayButton.setText("Integer Array");
		this.integerArrayButton.setLayoutData(new GridData());
		
		
		
		comp = new Composite(this.shell, SWT.NONE);
		comp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		gridlayout = new GridLayout();
		gridlayout.numColumns = 3;
		comp.setLayout(gridlayout);
		
		Composite tmp = new Composite(comp, SWT.NONE);
		GridData tmpgriddata = new GridData(GridData.FILL_HORIZONTAL);
		tmpgriddata.heightHint = 1;
		tmp.setLayoutData(tmpgriddata);
		this.okbutton = new Button(comp, SWT.NONE);
		this.okbutton.setLayoutData(new GridData());
		this.okbutton.setBounds(155, 82, 76, 23);
		this.okbutton.setText("Add");
		this.okbutton.addSelectionListener(this);
		
		this.cancelbutton = new Button(comp, SWT.NONE);
		this.cancelbutton.setLayoutData(new GridData());
		this.cancelbutton.setBounds(239, 82, 76, 23);
		this.cancelbutton.setText("Cancel");
		this.cancelbutton.addSelectionListener(this);
		
		this.shell.pack();
		
		Rectangle clientarea = this.propertygrid.getShell().getBounds();
		int mx = (clientarea.x + clientarea.x + clientarea.width) / 2 - (this.shell.getSize().x / 2);
		int my = (clientarea.y + clientarea.y + clientarea.height) / 2 - (this.shell.getSize().y / 2);
		
		this.shell.setLocation(mx, my);
		
		this.shell.open();
		while(!this.shell.isDisposed()) {
			if (!this.shell.getDisplay().readAndDispatch()) {
				this.shell.getDisplay().sleep();
			}
		}
	}
	
	private void createPropertyType() {
		
	}

	public void widgetSelected(SelectionEvent e) {
		Button button = (Button) e.widget;
		if (button == this.okbutton) {
			if (this.name.getText().trim().length() == 0) {
				MessageBox messagebox = new MessageBox(this.shell, 
						SWT.OK | SWT.ICON_ERROR);
				messagebox.setText("Please type in Name!");
				messagebox.setMessage("Please type in Name!");
				messagebox.open();
				this.name.setFocus();
			} else {
				IKesoProperty parent;
				if (this.propertygrid.getSelection() == null) {
					parent = this.propertygrid.getData().getPropertyContainer();
				} else {
					parent = this.propertygrid.getSelection().getProperty();
				}
				if (!(parent instanceof IKesoComplexProperty)) {
					parent = parent.getParent();
				}
				String type = KGE.PROPERTY_STRING;
				if (this.integerButton.getSelection()) {
					type = KGE.PROPERTY_INTEGER;
				} else if (this.integerArrayButton.getSelection()) {
					type = KGE.PROPERTY_INTEGERARRAY;
				}
				
				if (KesoPropertyManager.acceptsNewChild((IKesoComplexProperty) parent, this.name.getText())) {
					IKesoProperty property = KesoPropertyManager.generateProperty((IKesoComplexProperty) parent, this.name.getText(), type);
					((IKesoComplexProperty) parent).add(property);	
					//this.propertygrid.fill();
					this.shell.dispose();
				} else {
					MessageBox messagebox = new MessageBox(this.shell, 
							SWT.OK | SWT.ICON_ERROR);
					messagebox.setText("Can not add property");
					messagebox.setMessage(this.name.getText() + " can not be added here!");
					messagebox.open();
				}
			}
			
		} else if (button == this.cancelbutton) {
			this.shell.dispose();
		}
	}

	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
