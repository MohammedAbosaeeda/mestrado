package josek;

public class AlarmEvent_Callback extends AlarmEvent {
	private String funcName;
	
	AlarmEvent_Callback(String funcName) {
		this.funcName = funcName;
	}

	public void generateCode() {
		SSingleton.clear();
		SSingleton.add("JOSEK_CALLBACK_");
		SSingleton.add(funcName);
		SSingleton.add("();");
	}

	public String getFuncName() {
		return funcName;
	}

	public String toString() {
		return "Callback to function " + funcName;
	}
}
