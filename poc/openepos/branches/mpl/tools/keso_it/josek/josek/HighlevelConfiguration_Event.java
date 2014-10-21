package josek;

public class HighlevelConfiguration_Event {
	private String name;
	private int eventMask;
	
	HighlevelConfiguration_Event(String name, int eventMask) {
		this.name = name;
		this.eventMask = eventMask;
	}

	public String getName() {
		return name;
	}
	
	public int getEventMask() {
		return eventMask;
	}

	public String toString() {
		return "Event '" + name + "': " + eventMask;
	}
};
