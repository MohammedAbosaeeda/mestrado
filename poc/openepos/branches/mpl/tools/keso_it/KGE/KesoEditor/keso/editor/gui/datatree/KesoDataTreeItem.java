package keso.editor.gui.datatree;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;
import keso.editor.KGE;
import keso.editor.data.IKesoData;
import keso.editor.data.datamanager.KesoDataManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.TreeItem;

/**
 * @author  Wilhelm Haas
 */
public class KesoDataTreeItem implements PropertyChangeListener, DisposeListener {
	TreeItem item;
	IKesoData data;
	
	public KesoDataTreeItem(TreeItem item, IKesoData data) {
		this.item = item;
		this.data = data;
		this.data.addPropertyChangeListener(this);
		this.item.setData(this);
		this.item.addDisposeListener(this);
		this.item.setText(this.getDataRepresentation());
		/*
		if (this.data.getIdentifier().equals(KesoConstant.ISR)) {
			ImageData image = new ImageData(getClass().getResourceAsStream("../../icons/isr.gif"));
			this.item.setImage(new Image(item.getDisplay(), image, image.getTransparencyMask()));
		}
		*/
	}
	
	
	/**
	 * @param data  the data to set
	 * @uml.property  name="data"
	 */
	public void setData(IKesoData data) {
		this.data = data;
	}
	
	/**
	 * @return  the data
	 * @uml.property  name="data"
	 */
	public IKesoData getData() {
		return this.data;
	}

	public void propertyChange(PropertyChangeEvent e) {
		if (e.getPropertyName().equals("name changed")) {
			this.item.setText(this.getDataRepresentation());
		} else if (e.getPropertyName().equals("add child")){
			this.addData((IKesoData) e.getNewValue());
		} else if (e.getPropertyName().equals("remove child")) {
			for (int i = 0; i < this.item.getItemCount(); i++) {
				if (((KesoDataTreeItem) this.item.getItem(i).getData()).getData() == e.getOldValue()) {
					this.item.getItem(i).dispose();
					break;
				}
			}
		}
	}
	
	private int getIndex(IKesoData data) {
		String nodeText = this.getDataRepresentation(data);
		for (int i = 0; i < this.item.getItemCount(); i++) {
			if (this.item.getItem(i).getText().compareTo(nodeText) > 0) {
				return i;
			}
		}
		return this.item.getItemCount();
	}
	
	public String getDataRepresentation() {
		return this.getDataRepresentation(this.data);
	}
	
	public String getDataRepresentation(IKesoData data) {
		return data.getIdentifier() + ": " + data.getName();
	}
	
	
	
	public void fill() {
		this.item.removeAll();
		for (Iterator i = this.data.getChildren().iterator(); i.hasNext(); ) {
			IKesoData child = (IKesoData) i.next();
			this.addData(child);
		}
	}
	
	public void addData(IKesoData data) {
		TreeItem item = new TreeItem(this.item, this.item.getStyle(), this.getIndex(data));
		KesoDataTreeItem treeitem = new KesoDataTreeItem(item, data);
		treeitem.fill();
	}

	public void widgetDisposed(DisposeEvent e) {
		this.data.removePropertyChangeListener(this);
		try {
			this.finalize();
		} catch(Throwable thr) {
			
		}
	}

}
