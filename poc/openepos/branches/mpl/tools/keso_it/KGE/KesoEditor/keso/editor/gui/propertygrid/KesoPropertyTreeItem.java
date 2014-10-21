package keso.editor.gui.propertygrid;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;
import keso.editor.property.IKesoProperty;
import keso.editor.property.complexproperty.IKesoComplexProperty;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.TreeItem;

/**
 * @author  Wilhelm Haas
 */
public class KesoPropertyTreeItem implements PropertyChangeListener, DisposeListener{
	IKesoProperty property;
	TreeItem item;
	
	public KesoPropertyTreeItem(TreeItem item, IKesoProperty property) {
		this.property = property;
		this.property.addPropertyChangeListener(this);
		
		this.item = item;
		this.item.addDisposeListener(this);
		this.item.setData(this);
		
		this.item.setText(0, this.property.getName());
		this.item.setText(1, this.property.getValue());
	}

	public void fill() {
		if (this.property instanceof IKesoComplexProperty) {
			IKesoComplexProperty complexproperty = (IKesoComplexProperty) property;
			for (Iterator i = complexproperty.getProperties().iterator(); i.hasNext(); ) {
				IKesoProperty property = (IKesoProperty) i.next();
				add(property);
			}
		}
	}
	
	/**
	 * @return  the property
	 * @uml.property  name="property"
	 */
	public IKesoProperty getProperty() {
		return this.property;
	}
	
	private void add(IKesoProperty property) {
		TreeItem item = new TreeItem(this.item, this.item.getStyle(), this.getIndex(property));
		KesoPropertyTreeItem treeitem = new KesoPropertyTreeItem(item, property);
		treeitem.fill();
	}
	
	public int getIndex(IKesoProperty property) {
		for (int i = 0; i < this.item.getItemCount(); i++) {
			TreeItem item = this.item.getItem(i);
			if (item.getText(0).compareToIgnoreCase(property.getName()) > 0) {
				return i;
			}
		} 
		return this.item.getItemCount();
	}
	
	public void widgetDisposed(DisposeEvent e) {
		this.property.removePropertyChangeListener(this);	
		try {
			this.finalize();
		} catch (Throwable thr) {
			
		}
	}

	public void propertyChange(PropertyChangeEvent e) {
		if (e.getPropertyName().equals("name changed")) {
			this.item.setText(0, this.property.getName());
		} else if (e.getPropertyName().equals("value changed")) {
			this.item.setText(1, this.property.getValue());
		} else if (e.getPropertyName().equals("add property")) {
			this.add((IKesoProperty) e.getNewValue());
		} else if (e.getPropertyName().equals("remove property")) {
			IKesoProperty property = (IKesoProperty) e.getOldValue();
			for (int i = 0; i < this.item.getItemCount(); i++) {
				KesoPropertyTreeItem child = (KesoPropertyTreeItem) this.item.getItem(i).getData();
				if (child.getProperty() == property) {
					this.item.getItem(i).dispose();
					break;
				}
			}
		}
	}

}
