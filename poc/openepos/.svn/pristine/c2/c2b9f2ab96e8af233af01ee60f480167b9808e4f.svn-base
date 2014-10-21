package josek;
import java.util.Vector;

public class HighlevelConfiguration_Task {
	private String name;
	private boolean autoActivation;
	private int priority;
	private Vector usedResources;
	private Vector usedEvents;
	
	HighlevelConfiguration_Task(String name, boolean autoActivation, int priority) {
		this.name = name;
		this.autoActivation = autoActivation;
		this.priority = priority;
		this.usedResources = new Vector();
		this.usedEvents = new Vector();
		usesResource("RES_SCHEDULER");
	}

	public String getName() {
		return name;
	}

	public boolean getAutoactivation() {
		return autoActivation;
	}

	public int getPriority() {
		return priority;
	}

	public void usesResource(String res) {
		usedResources.add(res);
	}

	public int getUsedResourceCount() {
		return usedResources.size();
	}

	public String getUsedResource(int i) {
		return (String)usedResources.get(i);
	}

	public void usesEvent(String event) {
		usedEvents.add(event);
	}

	public int getUsedEventCount() {
		return usedEvents.size();
	}

	public String getUsedEvent(int i) {
		return (String)usedEvents.get(i);
	}

	public String toString() {
		String s = "Task '" + name + "':\n    Autoactivation: ";
		if (autoActivation) s += "Yes"; else s += "No";
		s += "\n    Priority      : " + priority;
		s += "\n    # of resources: " + getUsedResourceCount();
		s += "\n    # of events   : " + getUsedEventCount();
		return s;
	}
};
