package josek;

public class AlarmEvent_ActivateTask extends AlarmEvent {
	private String taskName;
	
	AlarmEvent_ActivateTask(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskName() {
		return taskName;
	}

	public void generateCode() {
		SSingleton.clear();
		SSingleton.add("ActivateTask(");
		SSingleton.add(taskName);
		SSingleton.add(");");
	}

	public String toString() {
		return "Activate task " + taskName;
	}
}
