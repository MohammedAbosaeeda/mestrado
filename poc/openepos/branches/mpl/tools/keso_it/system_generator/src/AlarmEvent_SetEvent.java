package josek;

public class AlarmEvent_SetEvent extends AlarmEvent {
	private String eventName;
	private String taskName;
	
	AlarmEvent_SetEvent(String eventName, String taskName) {
		this.eventName = eventName;
		this.taskName = taskName;
	}

	public String getTaskName() {
		return taskName;
	}

	public String getEventName() {
		return eventName;
	}

	public void generateCode() {
		SSingleton.clear();
		SSingleton.add("SetEvent(");
		SSingleton.add(taskName);
		SSingleton.add(", ");
		SSingleton.add(eventName);
		SSingleton.add(");");
	}

	public String toString() {
		return "Set event " + eventName + " in task " + taskName;
	}
}
