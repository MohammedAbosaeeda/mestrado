package keso.editor.property.arrayproperty;

import java.util.Vector;

import keso.editor.property.IKesoProperty;

public interface IKesoArrayProperty extends IKesoProperty {
	public String get(int index);
	public String remove(int index);
	public int size();
	public Vector getValues();
	public void addItem(String item);
}
