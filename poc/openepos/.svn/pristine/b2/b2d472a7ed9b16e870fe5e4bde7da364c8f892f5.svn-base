package keso.editor.gui.propertygrid.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;

/**
 * @author  Wilhelm Haas
 */
public class KesoPropertySelectionDialog implements SelectionListener {
	Shell parentshell;
	Shell shell;
	
	Table table;
	
	Button okbutton;
	Button cancelbutton;
	private String[] selection;
	
	boolean result = false;
	boolean multi = true;
	
	
	public KesoPropertySelectionDialog(Shell parentshell, boolean multi) {
		this.parentshell = parentshell;
		this.shell = new Shell(this.parentshell, SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);
		this.shell.setText("Select...");
		this.shell.setSize(300, 300);
		this.multi = multi;

		/*
		Display display = this.shell.getDisplay();
		
		Rectangle clientarea = display.getClientArea();
		int mx = (clientarea.x + clientarea.width) / 2 - (this.shell.getSize().x / 2);
		int my = (clientarea.y + clientarea.height) / 2 - (this.shell.getSize().y / 2);
		
		this.shell.setLocation(mx, my);
		*/
		
		Rectangle clientarea = this.parentshell.getBounds();
		int mx = (clientarea.x + clientarea.x + clientarea.width) / 2 - (this.shell.getSize().x / 2);
		int my = (clientarea.y + clientarea.y + clientarea.height) / 2 - (this.shell.getSize().y / 2);
		
		this.shell.setLocation(mx, my);
		
		GridLayout grid = new GridLayout();
		grid.numColumns = 1;
		this.shell.setLayout(grid);
		CTabFolder folder = new CTabFolder(this.shell, SWT.BORDER);
		folder.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		CTabItem item = new CTabItem(folder, SWT.NONE);
		item.setText("Select");
		this.table = new Table(folder, SWT.CHECK | SWT.FULL_SELECTION);
		this.table.addSelectionListener(this);
		item.setControl(table);
		
		Composite comp = new Composite(this.shell, SWT.NONE);
		comp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		GridLayout glayout = new GridLayout();
		glayout.numColumns = 2;
		glayout.makeColumnsEqualWidth = true;
		glayout.marginBottom = 0;
		glayout.marginHeight = 0;
		glayout.marginLeft = 0;
		glayout.marginRight = 0;
		glayout.marginTop = 0;
		glayout.marginWidth = 0;
		comp.setLayout(glayout);
		
		
		this.okbutton = new Button(comp, SWT.NONE);
		this.okbutton.setText("OK");
		this.okbutton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		this.okbutton.addSelectionListener(this);
		
		
		this.cancelbutton = new Button(comp, SWT.NONE);
		this.cancelbutton.setText("Cancel");
		this.cancelbutton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		this.cancelbutton.addSelectionListener(this);
	}
	
	public boolean open() {
		this.shell.open();
		while(!this.shell.isDisposed()) {
			if (!this.shell.getDisplay().readAndDispatch()) {
				this.shell.getDisplay().sleep();
			}
		}
		return this.result;
	}

	public void setPossibleValues(String [] possible) {
		this.table.removeAll();
		for (int i = 0; i < possible.length; i++) {
			TableItem item = new TableItem(this.table, SWT.NONE, this.getIndex(possible[i]));
			item.setText(possible[i]);
		}
	}
	
	private int getIndex(String str) {
		for (int i = 0; i < this.table.getItemCount(); i++) {
			TableItem item = this.table.getItem(i);
			if (item.getText().compareToIgnoreCase(str) > 0) {
				return i;
			}
		} 
		return this.table.getItemCount();
	}
	
	/**
	 * @param selection  the selection to set
	 * @uml.property  name="selection"
	 */
	public void setSelection(String [] selection) {
		for (int i = 0; i < selection.length; i++) {
			for (int j = 0; j < this.table.getItemCount(); j++) {
				if (this.table.getItem(j).getText().equals(selection[i])) {
					this.table.getItem(j).setChecked(true);
				}
			}
		}
		this.updateSelection();
	}
	
	/**
	 * @return  the selection
	 * @uml.property  name="selection"
	 */
	public String [] getSelection() {
		if (!this.shell.isDisposed()) {
			this.updateSelection();
		}
		return this.selection;
	}
	
	private void updateSelection() {
		int numchecked = 0;
		for (int i = 0; i < this.table.getItemCount(); i++) {
			if (this.table.getItem(i).getChecked()) {
				numchecked++;
			}
		}
		this.selection = new String[numchecked];
		numchecked = 0;
		for (int i = 0; i < this.table.getItemCount(); i++) {
			if (this.table.getItem(i).getChecked()) {
				this.selection[numchecked++] = this.table.getItem(i).getText();
			}
		}
	}
	
	public void widgetSelected(SelectionEvent e) {
		if (e.widget == this.okbutton) {
			this.result = true;
			this.shell.dispose();
		} else if (e.widget == this.cancelbutton) {
			this.result = false;
			this.shell.dispose();
			
		}  else if (e.item instanceof TableItem) {
			if (!this.multi) {
				TableItem item = (TableItem) e.item;
				if (item.getChecked()) {
					for (int i = 0; i < this.table.getItemCount(); i++) {
						this.table.getItem(i).setChecked(false);
					}
					item.setChecked(true);
				}
			}
			this.updateSelection();
		} else if (e.widget == this.table) {
			this.updateSelection();
		}
	}

	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
