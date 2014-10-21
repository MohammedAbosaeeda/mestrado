package lukas.generator;

import lukas.control.Command;
public final class Sequence {
	private static boolean started = false;
	private static int i=0;
	private static short[] cmds;

	public static void start() {
		cmds = keso.core.Config.getShortArray("CommandSeqence");
		started=true;
	}

	public static short getCommand() {
		short ret;
		if(started) {
			ret = cmds[i];
			i++;
		} else {
			ret = Command.MOVE_END;
		}

		return ret;
	}
}
