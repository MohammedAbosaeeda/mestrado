package josek;
import java.util.Vector;

final public class HighlevelConfiguration {
	private Vector tasks;
	public HighlevelConfiguration_Hooks hooks;
	private Vector resources;
	private Vector events;
	public Priorities priorities;
	private Vector counters;
	private Vector alarms;
	private Vector ISRs;
	public ISRLevels ISRLevels;
	private int IOISRCount;
	private int timerInterval;
	private Vector arrayOptimizers;
	
	public void addArrayOptimizer(ArrayOptimizer a) {
		arrayOptimizers.add(a);
	}

	public ArrayOptimizer getArrayOptimizer(int i) {
		return (ArrayOptimizer)arrayOptimizers.get(i);
	}

	public int getArrayOptimizerCount() {
		return arrayOptimizers.size();
	}
	
	public int getTaskCount() {
		return tasks.size();
	}

	public HighlevelConfiguration_Task getTask(int i) {
		return (HighlevelConfiguration_Task)tasks.get(i);
	}

	public int getResourceCount() {
		return resources.size();
	}

	public HighlevelConfiguration_Resource getResource(int i) {
		return (HighlevelConfiguration_Resource)resources.get(i);
	}

	public int getEventCount() {
		return events.size();
	}

	public HighlevelConfiguration_Event getEvent(int i) {
		return (HighlevelConfiguration_Event)events.get(i);
	}

	public int getCounterCount() {
		return counters.size();
	}

	public HighlevelConfiguration_Counter getCounter(int i) {
		return (HighlevelConfiguration_Counter)counters.get(i);
	}
	
	public int getAlarmCount() {
		return alarms.size();
	}

	public HighlevelConfiguration_Alarm getAlarm(int i) {
		return (HighlevelConfiguration_Alarm)alarms.get(i);
	}
	
	public int getISRCount() {
		return ISRs.size();
	}

	public HighlevelConfiguration_ISR getISR(int i) {
		return (HighlevelConfiguration_ISR)ISRs.get(i);
	}

	public boolean haveIOISRs() {
		return IOISRCount > 0;
	}
	
	public int getIOISRCount() {
		return IOISRCount;
	}

	public int getTimerInterval() {
		return timerInterval;
	}

	private void parseTask(PrimitiveStringAttribute a) {
		String name = a.value;
		boolean autoActivation = false;
		int priority = 0;
		Vector usedResources = new Vector();
		Vector usedEvents = new Vector();

		for (int i = 0; i < a.children.size(); i++) {
			Attribute node = (Attribute)a.children.get(i);
			if (node instanceof PrimitiveBoolAttribute) {
				PrimitiveBoolAttribute bnode = (PrimitiveBoolAttribute)node;
				if (bnode.lValue.equals("AUTOSTART")) autoActivation = bnode.value;
			} else if (node instanceof PrimitiveIntAttribute) {
				PrimitiveIntAttribute inode = (PrimitiveIntAttribute)node;
				if (inode.lValue.equals("PRIORITY")) priority = inode.value;
			} else if (node instanceof PrimitiveStringAttribute) {
				PrimitiveStringAttribute snode = (PrimitiveStringAttribute)node;
				if (snode.lValue.equals("RESOURCE")) usedResources.add(snode.value);
					else if (snode.lValue.equals("EVENT")) usedEvents.add(snode.value);
			}
		}

		HighlevelConfiguration_Task t = new HighlevelConfiguration_Task(name, autoActivation, priority);
		for (int i = 0; i < usedResources.size(); i++) t.usesResource((String)usedResources.get(i));
		for (int i = 0; i < usedEvents.size(); i++) t.usesEvent((String)usedEvents.get(i));
		tasks.add(t);
	}

	private void parseOS(PrimitiveStringAttribute a) {
		for (int i = 0; i < a.children.size(); i++) {
			Attribute node = (Attribute)a.children.get(i);
			if (node instanceof PrimitiveBoolAttribute) {
				PrimitiveBoolAttribute bnode = (PrimitiveBoolAttribute)node;
				if (bnode.lValue.equals("PRETASKHOOK")) hooks.preTask = bnode.value;
					else if (bnode.lValue.equals("POSTTASKHOOK")) hooks.postTask = bnode.value;
					else if (bnode.lValue.equals("STARTUPHOOK")) hooks.startup = bnode.value;
					else if (bnode.lValue.equals("SHUTDOWNHOOK")) hooks.shutdown = bnode.value;
					else if (bnode.lValue.equals("ERRORHOOK")) hooks.error = bnode.value;
					else if (bnode.lValue.equals("PREISRHOOK")) hooks.preISR = bnode.value;
					else if (bnode.lValue.equals("POSTISRHOOK")) hooks.postISR = bnode.value;
			} 
		}
	}

	private void parseResource(PrimitiveStringAttribute a) {
		HighlevelConfiguration_Resource r = new HighlevelConfiguration_Resource(a.value);
		resources.add(r);
	}
	
	private void parseEvent(PrimitiveStringAttribute a) {
		int mask = 0;
		for (int i = 0; i < a.children.size(); i++) {
			Attribute node = (Attribute)a.children.get(i);
			if (node instanceof PrimitiveIntAttribute) {
				PrimitiveIntAttribute inode = (PrimitiveIntAttribute)node;
				if (inode.lValue.equals("MASK")) mask = inode.value;
			}
		}
		HighlevelConfiguration_Event e = new HighlevelConfiguration_Event(a.value, mask);
		events.add(e);
	}
	
	private void parseCounter(PrimitiveStringAttribute a) {
		int cntrMaxValue = 100;
		int minCycle = 1;
		int ticksPerBase = 1;
		int timeInNanoSeconds = 1000000000;
		for (int i = 0; i < a.children.size(); i++) {
			Attribute node = (Attribute)a.children.get(i);
			if (node instanceof PrimitiveIntAttribute) {
				PrimitiveIntAttribute inode = (PrimitiveIntAttribute)node;
				if (inode.lValue.equals("MAXALLOWEDVALUE")) cntrMaxValue = inode.value;
					else if (inode.lValue.equals("MINCYCLE")) minCycle = inode.value;
					else if (inode.lValue.equals("TICKSPERBASE")) ticksPerBase = inode.value;
					else if (inode.lValue.equals("TIME_IN_NS")) timeInNanoSeconds = inode.value;
			}
		}
		HighlevelConfiguration_Counter c = new HighlevelConfiguration_Counter(a.value, cntrMaxValue, minCycle, ticksPerBase, timeInNanoSeconds);
		counters.add(c);
	}
	
	private void parseAlarm(PrimitiveStringAttribute a) {
		String counter = null;
		boolean autoStart = false;
		AlarmEvent event = null;
		int alarmTime = 1;
		int cycleTime = 1;

		for (int i = 0; i < a.children.size(); i++) {
			Attribute node = (Attribute)a.children.get(i);
			if (node instanceof PrimitiveBoolAttribute) {
				PrimitiveBoolAttribute bnode = (PrimitiveBoolAttribute)node;
				if (bnode.lValue.equals("AUTOSTART")) {
					autoStart = bnode.value;
					for (int j = 0; j < bnode.children.size(); j++) {
						Attribute innerNode = (Attribute)bnode.children.get(j);
						if (innerNode instanceof PrimitiveIntAttribute) {
							PrimitiveIntAttribute innerIntNode = (PrimitiveIntAttribute)innerNode;
							if (innerIntNode.lValue.equals("ALARMTIME")) alarmTime = innerIntNode.value;
								else if (innerIntNode.lValue.equals("CYCLETIME")) cycleTime = innerIntNode.value;
						}
					}
				}
			} else if (node instanceof PrimitiveStringAttribute) {
				PrimitiveStringAttribute snode = (PrimitiveStringAttribute)node;
				if (snode.lValue.equals("ACTION")) {
					if (snode.value.equals("SETEVENT")) {
						String eventName = null;
						String taskName = null;
						for (int j = 0; j < snode.children.size(); j++) {
							Attribute innernode = (Attribute)snode.children.get(j);
							if (innernode instanceof PrimitiveStringAttribute) {
								PrimitiveStringAttribute innersnode = (PrimitiveStringAttribute)innernode;
								if (innersnode.lValue.equals("TASK")) taskName = innersnode.value;
									else if (innersnode.lValue.equals("EVENT")) eventName = innersnode.value;
							}
						}
						event = new AlarmEvent_SetEvent(eventName, taskName);
					} else if (snode.value.equals("ACTIVATETASK")) {
						String taskName = null;
						for (int j = 0; j < snode.children.size(); j++) {
							Attribute innernode = (Attribute)snode.children.get(j);
							if (innernode instanceof PrimitiveStringAttribute) {
								PrimitiveStringAttribute innersnode = (PrimitiveStringAttribute)innernode;
								if (innersnode.lValue.equals("TASK")) taskName = innersnode.value;
							}
						}
						event = new AlarmEvent_ActivateTask(taskName);
					} else if (snode.value.equals("ALARMCALLBACK")) {
						String funcName = null;
						for (int j = 0; j < snode.children.size(); j++) {
							Attribute innernode = (Attribute)snode.children.get(j);
							if (innernode instanceof PrimitiveStringAttribute) {
								PrimitiveStringAttribute innersnode = (PrimitiveStringAttribute)innernode;
								if (innersnode.lValue.equals("ALARMCALLBACKNAME")) funcName = innersnode.value;
							}
						}
						event = new AlarmEvent_Callback(funcName);
					} else {
						throw new RuntimeException("Unknown action type for alarm '" + a.value + "': " + snode.value);
					}
				} else if (snode.lValue.equals("COUNTER")) {
					counter = snode.value;
				}
			}
		}
		
		HighlevelConfiguration_Alarm alrm = new HighlevelConfiguration_Alarm(a.value, counter, alarmTime, cycleTime, autoStart, event);
		alarms.add(alrm);
	}
	
	private void parseISR(PrimitiveStringAttribute a) {
		int category = 2;
		int priority = 99;
		int stackSize = 0;
		int signal = -1;
		int irqLevel = -1;
		for (int i = 0; i < a.children.size(); i++) {
			Attribute node = (Attribute)a.children.get(i);
			if (node instanceof PrimitiveIntAttribute) {
				PrimitiveIntAttribute inode = (PrimitiveIntAttribute)node;
				if (inode.lValue.equals("CATEGORY")) category = inode.value;
					else if (inode.lValue.equals("PRIORITY")) priority = inode.value;
					else if (inode.lValue.equals("STACKSIZE")) stackSize = inode.value;
					else if (inode.lValue.equals("IRQLEVEL")) irqLevel = inode.value;
			} else if (node instanceof PrimitiveStringAttribute) {
				PrimitiveStringAttribute snode = (PrimitiveStringAttribute)node;
				if (snode.lValue.equals("SIGNAL")) {
					if (snode.value.equals("SIGUSR1")) signal = 10;
						else if (snode.value.equals("SIGUSR2")) signal = 12;
						else if (snode.value.equals("IO")) signal = 0;
				}
			}
		}
		if (signal == -1) {
			throw new RuntimeException("Unknown or undefined signal for ISR '" + a.value);
		}
		HighlevelConfiguration_ISR i = new HighlevelConfiguration_ISR(a.value, category, priority, stackSize, signal, irqLevel);
		ISRs.add(i);
	}
	
	private boolean inspect(Attribute node, int level) {
		if (level == 2) {
			if (node instanceof PrimitiveStringAttribute) {
				PrimitiveStringAttribute a = (PrimitiveStringAttribute)node;
				if (a.lValue.equals("TASK")) {
					parseTask(a);
					return false;
				} else if (a.lValue.equals("OS")) {
					parseOS(a);
					return false;
				} else if (a.lValue.equals("RESOURCE")) {
					parseResource(a);
					return false;
				} else if (a.lValue.equals("EVENT")) {
					parseEvent(a);
					return false;
				} else if (a.lValue.equals("COUNTER")) {
					parseCounter(a);
					return false;
				} else if (a.lValue.equals("ALARM")) {
					parseAlarm(a);
					return false;
				} else if (a.lValue.equals("ISR")) {
					parseISR(a);
					return false;
				}
			}
		}
		return true;
	}
	
	private void parse(Attribute node, int level) {
		if (inspect(node, level)) {
			for (int i = 0; i < node.children.size(); i++) {
				parse((Attribute)node.children.get(i), level + 1);
			}
		}
	}

	int getTaskPriority(int taskNumber) {
		return priorities.externalToInternal(getTask(taskNumber).getPriority());
	}
	
	HighlevelConfiguration(Configuration conf, CommandLineOptions cmdLine) {
		this.tasks = new Vector();
		this.hooks = new HighlevelConfiguration_Hooks();
		this.resources = new Vector();
		this.events = new Vector();
		this.counters = new Vector();
		this.alarms = new Vector();
		this.ISRs = new Vector();
		this.arrayOptimizers = new Vector();

		parse(conf.getRoot(), 0);

		/* Now calculate real priorities of tasks */
		priorities = new Priorities();
		for (int i = 0; i < getTaskCount(); i++) {
			priorities.registerPriority(getTask(i).getPriority());
		}
		priorities.finalizePriorities();

		/* Add scheduler resource */
		HighlevelConfiguration_Resource r = new HighlevelConfiguration_Resource("RES_SCHEDULER");
		resources.add(r);
		
		/* Now calculate priority ceiling */
		for (int i = 0; i < getTaskCount(); i++) {
			for (int j = 0; j < getTask(i).getUsedResourceCount(); j++) {
				for (int k = 0; k < getResourceCount(); k++) {
					if (getResource(k).getName().equals(getTask(i).getUsedResource(j))) {
						getResource(k).setUsedByPriority(getTaskPriority(i));
					}
				}
			}
		}

		/* Now calculate timer resoulution */
		if (getCounterCount() > 0) {
			timerInterval = getCounter(0).getTimeInNanoSeconds();
			for (int i = 1; i < getCounterCount(); i++) {
				timerInterval = Mathematik.ggT(timerInterval, getCounter(i).getTimeInNanoSeconds());
			}
			for (int i = 0; i < getCounterCount(); i++) {
				getCounter(i).setMultiplicator(getCounter(i).getTimeInNanoSeconds() / timerInterval);
			}
		}

		/* Reverse mapping of Counters <=> Alarms */
		for (int i = 0; i < getAlarmCount(); i++) {
			boolean match = false;
			for (int j = 0; j < getCounterCount(); j++) {
				if (getCounter(j).getName().equals(getAlarm(i).getBaseCounterName())) {
					getCounter(j).registerAlarm(getAlarm(i));
					match = true;
					break;
				}
			}
			if (!match) {
				throw new RuntimeException("Error in configuration file: Alarm '" + getAlarm(i).getName() + "' based on nonexistent counter '" + getAlarm(i).getBaseCounterName() + "'");
			}
		}

		if (getCounterCount() > 0) {
			cmdLine.defineSymbol("timer");
			cmdLine.defineSymbol("signals");
		}
		if (getAlarmCount() > 0) cmdLine.defineSymbol("have_alarms");
		if (getTaskCount() > 0) cmdLine.defineSymbol("have_tasks");

		/* Do we have any IO interrupts? */
		for (int i = 0; i < getISRCount(); i++) {
			if (getISR(i).getSignal() == 0) {
				IOISRCount++;
			}
		}
		if (IOISRCount > 0) {
			cmdLine.defineSymbol("have_ioisr");
			cmdLine.defineSymbol("signals");
		}

		/* Configure interrupt levels */
		ISRLevels = new ISRLevels(this);
		
		/* Optimizations on arrays must come last */
		AddArrayOptimizers.work(this, cmdLine);
	}

	void dumpConfiguration() {
		System.out.println("There are " + getTaskCount() + " tasks in the system:");
		for (int i = 0; i < getTaskCount(); i++) {
			System.out.print("Priority " + getTaskPriority(i) + " ");
			System.out.println(getTask(i));
		}
		System.out.println();
		
		System.out.println("There are " + getResourceCount() + " resources in the system:");
		for (int i = 0; i < getResourceCount(); i++) {
			System.out.println("    " + getResource(i));
		}
		System.out.println();

		System.out.println("There are " + getEventCount() + " events in the system:");
		for (int i = 0; i < getEventCount(); i++) {
			System.out.println("    " + getEvent(i));
		}
		System.out.println();
		
		System.out.println("There are " + getCounterCount() + " counters in the system (time = " + getTimerInterval() + "):");
		for (int i = 0; i < getCounterCount(); i++) {
			System.out.println("    " + getCounter(i));
		}
		System.out.println();
		
		System.out.println("There are " + getAlarmCount() + " alarms in the system:");
		for (int i = 0; i < getAlarmCount(); i++) {
			System.out.println("    " + getAlarm(i));
		}
		System.out.println();
		
		System.out.println(getISRCount() + " ISRs in the system:");
		for (int i = 0; i < getISRCount(); i++) {
			System.out.println("    " + getISR(i));
		}
		System.out.println();
	}
}
