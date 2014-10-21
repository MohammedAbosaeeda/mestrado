package keso.editor.data.datamanager;

import java.util.Hashtable;

public class KesoCanHave {
	Hashtable canhaves = new Hashtable();
	
	public void add(String identifier) {
		this.getCanHaves().put(identifier, identifier);
	}
	
	public boolean contains(String identifier) {
		if (this.getCanHaves().get(identifier) != null) {
			return true;
		} else {
			return false;
		}	
	}
	
	public Hashtable getCanHaves() {
		return this.canhaves;
	}

}
