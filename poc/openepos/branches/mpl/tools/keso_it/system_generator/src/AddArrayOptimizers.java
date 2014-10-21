package josek;

public class AddArrayOptimizers {
	public AddArrayOptimizers() {
	}
	
	public static void work(HighlevelConfiguration conf, CommandLineOptions cmdLine) {
		ArrayOptimizer a;

		if (conf.getCounterCount() > 0) {
			a = new ArrayOptimizer("CntrMaxValue", "unsigned char", "unsigned char", !cmdLine.isDefined("saveram"));
			for (int i = 0; i < conf.getCounterCount(); i++) {
				a.addValue(i, conf.getCounter(i).getMaxValue());
			}
			conf.addArrayOptimizer(a);
		
			a = new ArrayOptimizer("CntrTickMaxValue", "unsigned char", "unsigned char", !cmdLine.isDefined("saveram"));
			for (int i = 0; i < conf.getCounterCount(); i++) {
				a.addValue(i, conf.getCounter(i).getTicksPerBase());
			}
			conf.addArrayOptimizer(a);
		}

		// I do not currently use these values during runtime
/*
		if (conf.getAlarmCount() > 0) {
			a = new ArrayOptimizer("AlrmMaxAllowedValue", "unsigned char", "unsigned char", !cmdLine.isDefined("saveram"));
			for (int i = 0; i < conf.getAlarmCount(); i++) {
//				a.addValue(i, conf.getAlarm(i).getMaxAllowedValue());
				a.addValue(i, 0);
				// TODO: WRONG VALUE
			}
			conf.addArrayOptimizer(a);
			
			a = new ArrayOptimizer("AlrmMinCycle", "unsigned char", "unsigned char", !cmdLine.isDefined("saveram"));
			for (int i = 0; i < conf.getAlarmCount(); i++) {
				//a.addValue(i, conf.getAlarm(i).getMinCycle());
				a.addValue(i, 0);
			}
			conf.addArrayOptimizer(a);
		}
*/
			
		a = new ArrayOptimizer("ResourcePriority", "PriorityType", "unsigned char", !cmdLine.isDefined("saveram"));
		for (int i = 0; i < conf.getResourceCount(); i++) {
			a.addValue(i, conf.getResource(i).getCeilingPriority());
		}
		conf.addArrayOptimizer(a);
	}
}
