package keso.editor.gui.exceptionlist;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Iterator;
import java.util.Vector;
import keso.editor.data.IKesoData;
import keso.editor.gui.core.KesoImageManager;
import keso.editor.property.exceptioncontainer.KesoPropertyException;
import keso.editor.property.propertymanager.KesoPropertyManager;
import keso.editor.property.propertymanager.KesoPropertyRuleManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;

/**
 * @author  Wilhelm Haas
 */
public class KesoExceptionList implements SelectionListener, PropertyChangeListener, ControlListener {

	Composite parent;
	Table table;
	TableColumn column;
	
	IKesoData data;
	
	PropertyChangeSupport change = new PropertyChangeSupport(this);
	
	public KesoExceptionList(Composite parent) {
		this.setParent(parent);
		
		Composite comp = new Composite(this.getParent(), SWT.NONE);
		
		GridLayout gridlayout = new GridLayout();
		gridlayout.numColumns = 1;
		gridlayout.marginBottom = 0;
		gridlayout.marginHeight = 0;
		gridlayout.marginLeft = 0;
		gridlayout.marginRight = 0;
		gridlayout.marginTop = 0;
		gridlayout.marginWidth = 0;
		
		comp.setLayout(gridlayout);
		
		
		this.table = new Table(comp, SWT.NONE | SWT.DOUBLE_BUFFERED | SWT.FULL_SELECTION);
		this.table.setLayoutData(new GridData(GridData.FILL_BOTH));
		this.table.addSelectionListener(this);
		this.table.setLinesVisible(true);
		this.table.setHeaderVisible(true);
		this.table.addControlListener(this);
		
		this.column = new TableColumn(this.table, SWT.NONE);
		this.column.setText("Exception");
		this.column.setWidth(400);
	}
	
	/**
	 * @param parent  the parent to set
	 * @uml.property  name="parent"
	 */
	public void setParent(Composite parent) {
		this.parent = parent;
	}
	
	/**
	 * @return  the parent
	 * @uml.property  name="parent"
	 */
	public Composite getParent() {
		return this.parent;
	}
	
	/**
	 * @param data  the data to set
	 * @uml.property  name="data"
	 */
	public void setData(IKesoData data) {
		this.data = data;
		this.check();
	}
	
	/**
	 * @return  the data
	 * @uml.property  name="data"
	 */
	public IKesoData getData() {
		return this.data;
	}
	
	public void check() {
		this.table.removeAll();
		if (this.getData() != null) {
			Vector exceptions = KesoPropertyRuleManager.getInstance().check(this.getData());
			
			for (Iterator i = exceptions.iterator(); i.hasNext(); ) {
				KesoPropertyException exception = (KesoPropertyException) i.next();
				TableItem item = new TableItem(this.table, SWT.NONE);
				item.setImage(KesoImageManager.getInstance().getImage("error"));
				item.setText(exception.toString());
				item.setData(exception);
			}
		}
		this.change.firePropertyChange("checked for exceptions", null, null);
	}

	public void propertyChange(PropertyChangeEvent e) {
		if (e.getPropertyName().equals("property changed")) {
			this.check();
		}
	}

	public void widgetSelected(SelectionEvent e) {
		if (e.item != null) {
			TableItem item = (TableItem) e.item;
			IKesoData data = ((KesoPropertyException) item.getData()).getPropertyOwner();
			this.change.firePropertyChange("selection changed", null, data);
		}
	}
	
	public int size() {
		return this.table.getItemCount();
	}

	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.change.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.change.removePropertyChangeListener(listener);
	}

	public void controlMoved(ControlEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void controlResized(ControlEvent e) {
		int width = this.table.getSize().x  - 50;
		this.column.setWidth((width <= 0)? 1 : width);
	}

}
