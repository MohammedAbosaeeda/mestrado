package lukas.generator;

import lukas.control.Command;

public class ConfigElement {
	public Command cmd;
	public ConfigElement previous;
	public ConfigElement next;

	public ConfigElement(int mtype, int n) {
		cmd = new Command(mtype, n);
	}

	public ConfigElement getNext() { return next; }

	public String toString() {
		if ( cmd.type == Command.MOVE_START ) return "";
		StringBuffer s = new StringBuffer();
		s.append(cmd.type);
		s.append(",");
		s.append(cmd.n);
		if(next!=null)
			s.append(",");

		return s.toString();
	}
}
