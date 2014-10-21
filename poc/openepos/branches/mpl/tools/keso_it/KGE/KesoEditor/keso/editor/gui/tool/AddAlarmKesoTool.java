package keso.editor.gui.tool;

import keso.editor.data.*;
import keso.editor.property.propertymanager.KesoPropertyManager;

public class AddAlarmKesoTool extends AddKesoTool {

	public AddAlarmKesoTool() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void generateChildData() {
		this.childdata = new AlarmKesoData("alarm");
		
		KesoPropertyManager.fillUpData(this.childdata);
	}

}
