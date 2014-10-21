package lukas.generator;

public class Program {
	ProgramElement first;
	ProgramElement last;
	int errorPos;
	int currentCoil;

	public Program() {
		ProgramElement pe = new ProgramElement(ProgramElement.START, 0);
		first = last = pe;
		errorPos = -1;
	}

	private void addProgramElement(int type, int n) {
		ProgramElement pe = new ProgramElement(type, n);
		pe.previous = last;
		last.next = pe;
		last = pe;
	}

	private void addProgramElement(int type, int n, int rounds) {
		ProgramElement pe = new ProgramElement(type, n, rounds);
		pe.previous = last;
		last.next = pe;
		last = pe;
	}

	public void upContinuous(int n) {
		addProgramElement(ProgramElement.UP_CONTINUOUS, n);
	}

	public void downContinuous(int n) {
		addProgramElement(ProgramElement.DOWN_CONTINUOUS, n);
	}

	public void upStepwise(int n) {
		addProgramElement(ProgramElement.UP_STEPWISE, n);
	}

	public void downStepwise(int n) {
		addProgramElement(ProgramElement.DOWN_STEPWISE, n);
	}
	
	public void oscContinuous(int n, int rounds) {
		addProgramElement(ProgramElement.OSC_CONTINUOUS, n, rounds);
	}

	public void oscStepwise(int n, int rounds) {
		addProgramElement(ProgramElement.OSC_STEPWISE, n, rounds);
	}

	public void hold(int n) {
		addProgramElement(ProgramElement.HOLD, n);
	}

	public ProgramElement getFirst() { return first; }
}
