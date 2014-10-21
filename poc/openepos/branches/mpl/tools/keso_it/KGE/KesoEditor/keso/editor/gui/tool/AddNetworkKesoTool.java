package keso.editor.gui.tool;

import keso.editor.data.*;
import keso.editor.property.propertymanager.KesoPropertyManager;

public class AddNetworkKesoTool extends AddKesoTool {

	public AddNetworkKesoTool() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void generateChildData() {
		this.childdata = new NetworkKesoData("network");
		
		KesoPropertyManager.fillUpData(this.childdata);
	}

}
