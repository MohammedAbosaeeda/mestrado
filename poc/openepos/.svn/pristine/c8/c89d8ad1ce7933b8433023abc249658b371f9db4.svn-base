package keso.editor.gui.tool;

import keso.editor.data.*;
import keso.editor.property.propertymanager.KesoPropertyManager;

public class AddDomainKesoTool extends AddKesoTool {

	public AddDomainKesoTool() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void generateChildData() {
		DomainKesoData domain = new DomainKesoData("domain");
		TaskKesoData task = new TaskKesoData("task");
		
		this.childdata = domain;
		domain.addChild(task);
		
		KesoPropertyManager.fillUpData(this.childdata);
	}

}
