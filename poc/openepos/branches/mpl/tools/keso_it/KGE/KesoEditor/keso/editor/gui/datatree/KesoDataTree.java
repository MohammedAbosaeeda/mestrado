package keso.editor.gui.datatree;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Vector;
import keso.editor.data.*;
import keso.editor.gui.core.KesoImageManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * @author  Wilhelm Haas
 */
public class KesoDataTree implements PropertyChangeListener, ControlListener {
	private Composite parent;
	private int style;
	private Tree tree;
	private IKesoData data;
	private TreeItem worldtreeitem;
	private TreeItem selection;
	
	PropertyChangeSupport change = new PropertyChangeSupport(this);
	
	public KesoDataTree(Composite parent, int style) {
		this.parent = parent;
		this.style = style;
		
		
		CTabFolder folder = new CTabFolder(parent, SWT.BORDER);
		CTabItem item = new CTabItem(folder, SWT.NONE);
		folder.setSelection(item);
		item.setText("Data Tree");
		
		item.setImage(KesoImageManager.getInstance().getImage("datatree"));
		
		this.tree = new Tree(folder, style);
		item.setControl(this.tree);
	}
	
	public void setBounds(int x, int y, int width, int height) {
		this.tree.setBounds(x, y, width, height);
	}
	
	/**
	 * @return  the tree
	 * @uml.property  name="tree"
	 */
	public Tree getTree() {
		return this.tree;
	}
	
	/**
	 * @param data  the data to set
	 * @uml.property  name="data"
	 */
	public void setData(IKesoData world) {
		this.removeAll();
		this.data = world;
		
		if (this.worldtreeitem != null && !this.worldtreeitem.isDisposed()) {
			this.worldtreeitem.dispose();
		}
		
		if (this.data != null) {
			this.worldtreeitem = new TreeItem(this.tree, SWT.NONE);
			KesoDataTreeItem treeitem = new KesoDataTreeItem(this.worldtreeitem, this.data);
			treeitem.fill();
		}
	}
	
	/**
	 * @return  the data
	 * @uml.property  name="data"
	 */
	public IKesoData getData() {
		return this.data;
	}
	
	public void removeAll() {
		this.tree.removeAll();
	}

	public TreeItem find(IKesoData data) {
		if (data != null && this.worldtreeitem != null) {
			Vector items = new Vector();
			items.add(this.worldtreeitem);
			while(items.size() != 0) {
				TreeItem item = (TreeItem) items.remove(0);
				if (((KesoDataTreeItem) item.getData()).getData() == data) {
					return item;
				}
				for (int i = 0; i < item.getItemCount(); i++) {
					items.add(item.getItem(i));
				}
			}
		}
		return null;
	}
	
	public void setSelection(IKesoData data) {
		TreeItem item = this.find(data);
		this.setSelection(item);
	}
	
	/**
	 * @param selection  the selection to set
	 * @uml.property  name="selection"
	 */
	public void setSelection(TreeItem item) {
		if (item != this.selection) {
			this.selection = item;
			/*
			if (this.selection ) {
				this.tree.setSelection(this.worldtreeitem);
			} else {
				this.tree.setSelection(item);
			}
			*/
			if (this.selection != null) {
				this.tree.setSelection(this.selection);
			} else {
				if (this.worldtreeitem != null && !this.worldtreeitem.isDisposed()) {
					this.tree.setSelection(this.worldtreeitem);
				}
			}
		}
	}
	
	public void propertyChange(PropertyChangeEvent e) {
		
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
		this.tree.setBounds(1, 1, this.parent.getSize().x - 2, this.parent.getSize().y - 2);
	}
	
	public void fireDataChanged() {
		this.change.firePropertyChange("data changed", null, null);
	}
}

