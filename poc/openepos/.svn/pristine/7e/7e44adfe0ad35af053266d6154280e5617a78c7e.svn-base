package josek;

public class ISRLevels {
	private String[] levelNames;
	private HighlevelConfiguration conf;

	public ISRLevels(HighlevelConfiguration conf) {
		this.conf = conf;
		if (conf.getISRCount() == 0) return;

		levelNames = new String[conf.getISRCount()];
		for (int i = 0; i < conf.getISRCount(); i++) {
			registerISR(conf.getISR(i));
		}
	}

	private void registerISR(HighlevelConfiguration_ISR isr) {
		String name = isr.getName();
		int lvl = isr.getLevel();
		if (lvl == -1) {
			int j;
			for (int i = 0; i < conf.getISRCount(); i++) if (levelNames[i] == null) {
				lvl = i;
				break;
			}
			System.err.println("Warning: Autoassigned ISR " + name + " IRQ level " + lvl + ", please specify explicitly.");
		}

		if (lvl >= conf.getISRCount()) {
			throw new RuntimeException("IRQ level of ISR " + name + " is not within bounds (must be from 0 to " + (conf.getISRCount() - 1) + ")");
		}
		if (levelNames[lvl] != null) {
			throw new RuntimeException("IRQ level of ISR " + name + " is already taken by ISR " + levelNames[lvl]);
		}
		levelNames[lvl] = name;
	}

	public void getArray(ArrayOptimizer a) {
		for (int i = 0; i < conf.getISRCount(); i++) {
			a.addValue(i, "JOSEK_ISR_" + levelNames[i]);
		}
	}

	public String getLevel(int i) {
		return levelNames[i];
	}
}
