package lukas.generator;

import lukas.control.Command;

public class Config {
	public static final int NUM_COILS = 8;

	int currentCoil;
	public ConfigElement first;
	public ConfigElement last;

	public Config() {
		ConfigElement newEl = new ConfigElement(Command.MOVE_START, 0);

		currentCoil = 0;
		first = last = newEl;
	}

	private void addConfigElement(int n, int mtype) {
		ConfigElement ce = new ConfigElement(mtype, n);

		ce.previous = last;
		ce.next = null;
		last.next = ce;
		last = ce;

		System.err.print("  -> ");
		System.err.println(ce.cmd.toString());
	}

	public void addup(int n) {
		addConfigElement(n, Command.MOVE_UP);
	}

	public void adddown(int n) {
		addConfigElement(n, Command.MOVE_DOWN);
	}

	public void addhold(int timeInMS) {
		addConfigElement(timeInMS, Command.MOVE_HOLD);
	}

	public void addrelease(int timeInMS)  {
		addConfigElement(timeInMS, Command.MOVE_RELEASE);
	}

	public void addend() {
		addConfigElement(0, Command.MOVE_END);
	}

	public ConfigElement getLast() {
		return last;
	}
	public ConfigElement getFirst() {
		return first;
	}
}
