package keso.editor.gui.tool;

import keso.editor.data.*;
import keso.editor.property.propertymanager.KesoPropertyManager;

public class AddResourceKesoTool extends AddKesoTool {

	public AddResourceKesoTool() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void generateChildData() {
		this.childdata = new ResourceKesoData("resource");
		
		KesoPropertyManager.fillUpData(this.childdata);
	}

}
