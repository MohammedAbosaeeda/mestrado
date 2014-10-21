package lukas.generator;

public class ProgramElement {
	public static final int START          =0;
	public static final int UP_CONTINUOUS   =1;
	public static final int DOWN_CONTINUOUS =2;
	public static final int OSC_CONTINUOUS  =3;
	public static final int UP_STEPWISE    =4;
	public static final int DOWN_STEPWISE  =5;
	public static final int OSC_STEPWISE   =6;
	public static final int HOLD           =7;

	int type;
	int n;
	int rounds;
	ProgramElement next;
	ProgramElement previous;

	public ProgramElement(int type, int n) {
		this.type=type;
		this.n = n;
	}

	public ProgramElement(int type, int n, int rounds) {
		this.type=type;
		this.n = n;
		this.rounds=rounds;
	}

	public ProgramElement getNext() { return next; }

	public String toString() {
		StringBuffer s = new StringBuffer();

		switch(type) {
			case START:
				s.append("START");
				break;
			case UP_CONTINUOUS:
				s.append("UP_CONTINUOUS");
				break;
			case DOWN_CONTINUOUS:
				s.append("DOWN_CONTINUOUS");
				break;
			case OSC_CONTINUOUS:
				s.append("OSC_CONTINUOUS");
				break;
			case UP_STEPWISE:
				s.append("UP_STEPWISE");
				break;
			case DOWN_STEPWISE:
				s.append("DOWN_STEPWISE");
				break;
			case OSC_STEPWISE:
				s.append("OSC_STEPWISE");
				break;
			case HOLD:
				s.append("HOLD");
				break;
			default:
				s.append("UNKNOWN COMMAND");
		}

		s.append("("); s.append(n);
		
		switch(type) {
			case OSC_CONTINUOUS:
			case OSC_STEPWISE:
				s.append(", ");
				s.append(rounds);
		}
		s.append(")");
		return s.toString();
	}
}
