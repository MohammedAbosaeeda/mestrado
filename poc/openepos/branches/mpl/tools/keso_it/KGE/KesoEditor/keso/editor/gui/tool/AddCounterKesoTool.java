package keso.editor.gui.tool;

import keso.editor.data.*;
import keso.editor.property.propertymanager.KesoPropertyManager;

public class AddCounterKesoTool extends AddKesoTool {

	public AddCounterKesoTool() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void generateChildData() {
		this.childdata = new CounterKesoData("counter");
		
		KesoPropertyManager.fillUpData(this.childdata);
	}

}
