package josek;

public class HighlevelConfiguration_Alarm {
	private String name;
	private String basedOnCounter;
	private AlarmEvent event;
	private boolean autoStart;
	private int alarmTime;
	private int cycleTime;
	
	HighlevelConfiguration_Alarm(String name, String basedOnCounter, int alarmTime, int cycleTime, boolean autoStart, AlarmEvent event) {
		this.name = name;
		this.basedOnCounter = basedOnCounter;
		this.alarmTime = alarmTime;
		this.cycleTime = cycleTime;
		this.autoStart = autoStart;
		this.event = event;
	}

	public String getName() {
		return name;
	}

	public String getBaseCounterName() {
		return basedOnCounter;
	}

	public AlarmEvent getEvent() {
		return event;
	}

	public int getAlarmTime() {
		return alarmTime;
	}
	
	public int getCycleTime() {
		return cycleTime;
	}

	public boolean getAutostart() {
		return autoStart;
	}

	public String toString() {
		return "Alarm '" + name + "' (" + alarmTime + " / " + cycleTime + ") based on " + basedOnCounter + ": " + event;
	}
};

