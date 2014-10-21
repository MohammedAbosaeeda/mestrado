package keso.editor.gui.tool;

import keso.editor.data.*;
import keso.editor.property.propertymanager.KesoPropertyManager;

public class AddNodeKesoTool extends AddKesoTool {

	public AddNodeKesoTool() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void generateChildData() {
		this.childdata = new SystemKesoData("node");
		DomainKesoData domain = new DomainKesoData("domain");
		TaskKesoData task = new TaskKesoData("task");
		
		domain.addChild(task);
		this.childdata.addChild(domain);
		
		KesoPropertyManager.fillUpData(this.childdata);
	}

}
