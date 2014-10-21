/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.compiler.imcode; 

import keso.compiler.config.*;

final public class IMTask {

	private String mainClass;
	private String mainMethod;
	private TaskDefinition myDef;
	
	IMTask(TaskDefinition taskDef) {
		myDef = taskDef;
		
		Attribut attr = taskDef.getAttribute("MainClass");

		if (attr!=null) {
			this.mainClass = attr.valueString();
		} else {
			System.err.println("Error: now MainClass found! "+taskDef);
			throw new Error();
		}

		if (taskDef.getAttribute("MainMethod")!=null)
			this.mainMethod = taskDef.getAttribute("MainMethod").valueString();
		else mainMethod = "launch()V";
	}
	
	public String getMainClass() { return mainClass; }

	public String getMainMethod() { return mainMethod; }

	public TaskDefinition getDef() { return myDef; }
}
