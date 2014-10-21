package josek;

public class HighlevelConfiguration_ISR {
	private String name;
	private int category;
	private int priority;
	private int stackSize;
	private int signal;
	private int irqLevel;
	
	HighlevelConfiguration_ISR(String name, int category, int priority, int stackSize, int signal, int irqLevel) {
		this.name = name;
		this.category = category;
		this.priority = priority;
		this.stackSize = stackSize;
		this.signal = signal;
		this.irqLevel = irqLevel;
	}

	public String getName() {
		return name;
	}

	public int getSignal() {
		return signal;
	}

	public int getLevel() {
		return irqLevel;
	}

	public String toString() {
		return "ISR '" + name + "' delivered on signal " + signal;
	}
};

