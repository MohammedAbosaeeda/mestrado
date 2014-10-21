package keso.editor.output;

import keso.editor.data.IKesoData;

/**
 * @author  Wilhelm Haas
 */
public abstract class KesoOutput implements IKesoOutput {

	IKesoData data;
	String filename;
	
	public KesoOutput() {
	
	}
	
	public KesoOutput(String filename) {
		this.setFilename(filename);
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

	/**
	 * @param filename  the filename to set
	 * @uml.property  name="filename"
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * @return  the filename
	 * @uml.property  name="filename"
	 */
	public String getFilename() {
		return this.filename;
	}

	abstract public void output();

}
