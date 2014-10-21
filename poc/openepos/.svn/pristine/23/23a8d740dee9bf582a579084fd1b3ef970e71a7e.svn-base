package lukas.control;

public class Command {
	public static final int MOVE_START   =0;
	public static final int MOVE_UP      =1;
	public static final int MOVE_DOWN    =2;
	public static final int MOVE_RELEASE =3;
	public static final int MOVE_HOLD    =4;
	public static final int MOVE_END     =5;

	public int type;

	/** movetype dependant number that will be used by the FSM */
	public int n;

	public void incN(int n) {
		this.n += n;
	}

	public Command(int type, int n) {
		this.type = type;
		this.n = n;
	}

	public String toString() {
		StringBuffer s = new StringBuffer();

		switch(type) {
			case MOVE_START:
				s.append("MOVE_START");
				break;
			case MOVE_UP:
				s.append("MOVE_UP");
				break;
			case MOVE_DOWN:
				s.append("MOVE_DOWN");
				break;
			case MOVE_RELEASE:
				s.append("MOVE_RELEASE");
				break;
			case MOVE_HOLD:
				s.append("MOVE_HOLD");
				break;
			case MOVE_END:
				s.append("MOVE_END");
				break;
			default:
				s.append("UNKNOWN");
		}
		s.append("(");
		s.append(n);
		s.append(")");
		return s.toString();
	}
}
