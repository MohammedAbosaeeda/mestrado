package keso.editor.output;

import keso.editor.data.IKesoData;

/**
 * @author  Wilhelm Haas
 */
public interface IKesoOutput {
	/**
	 * @param data
	 * @uml.property  name="data"
	 */
	public void setData(IKesoData data);
	public IKesoData getData();
	/**
	 * @param filename
	 * @uml.property  name="filename"
	 */
	public void setFilename(String filename);
	/**
	 * @return
	 * @uml.property  name="filename"
	 */
	public String getFilename();
	public void output();
}
