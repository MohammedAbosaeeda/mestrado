package josek;

public class HighlevelConfiguration_Resource {
	private String name;
	private int ceilingPriority;
	
	HighlevelConfiguration_Resource(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getCeilingPriority() {
		return ceilingPriority;
	}

	public void setUsedByPriority(int priority) {
		if (priority > ceilingPriority) ceilingPriority = priority;
	}

	public String toString() {
		String s = "Resource '" + name + "': " + ceilingPriority;
		return s;
	}
};
