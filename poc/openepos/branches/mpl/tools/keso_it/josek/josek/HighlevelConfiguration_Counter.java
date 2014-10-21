package josek;
import java.util.Vector;

public class HighlevelConfiguration_Counter {
	private String name;
	private int cntrMaxValue;
	private int minCycle;
	private int ticksPerBase;
	private int timeInNanoSeconds;
	private Vector baseForAlarms;
	
	HighlevelConfiguration_Counter(String name, int cntrMaxValue, int minCycle, int ticksPerBase, int timeInNanoSeconds) {
		this.name = name;
		this.cntrMaxValue = cntrMaxValue;
		this.minCycle = minCycle;
		this.ticksPerBase = ticksPerBase;
		this.timeInNanoSeconds = timeInNanoSeconds;
		this.baseForAlarms = new Vector();
	}

	public String getName() {
		return name;
	}

	public int getMaxValue() {
		return cntrMaxValue;
	}

	public int getTicksPerBase() {
		return ticksPerBase;
	}

	public int getTimeInNanoSeconds() {
		return timeInNanoSeconds;
	}

	public void setMultiplicator(int m) {
		ticksPerBase *= m;
	}

	public void registerAlarm(HighlevelConfiguration_Alarm a) {
		baseForAlarms.add(a);
	}

	public int getAlarmCount() {
		return baseForAlarms.size();
	}

	public HighlevelConfiguration_Alarm getAlarm(int i) {
		return (HighlevelConfiguration_Alarm)baseForAlarms.get(i);
	}

	public String toString() {
		return "Counter '" + name + "': Max = " + cntrMaxValue + ", every " + ticksPerBase + " tick (which comes every " + timeInNanoSeconds + " ns)";
	}
};
