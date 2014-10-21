package keso.editor.gui.tool;

import keso.editor.data.*;
import keso.editor.property.propertymanager.KesoPropertyManager;

public class AddPublicDomainKesoTool extends AddKesoTool {

	public AddPublicDomainKesoTool() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void generateChildData() {
		PublicDomainKesoData domain = new PublicDomainKesoData("publicdomain");
		ResourceKesoData resource = new ResourceKesoData("resource");
		
		this.childdata = domain;
		domain.addChild(resource);
		
		KesoPropertyManager.fillUpData(this.childdata);
	}

}
