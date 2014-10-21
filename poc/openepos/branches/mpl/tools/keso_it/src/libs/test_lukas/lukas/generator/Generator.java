package lukas.generator;

import lukas.control.Command;

public class Generator {
	public static void main(String[] args) {
		java.io.PrintStream out = System.out;

		Program prg = program_def();
		Control ctrl = new Control();
		Config cfg = ctrl.compile(prg);

		if(cfg == null) {
			System.err.println("Program did not compile");
			System.exit(-1);
		}

		out.print("CommandSeqence={");

		for(ConfigElement ce=cfg.getFirst(); ce != null; ce = ce.getNext()) {
			out.print(ce.toString());
		}
		out.println("};");
		out.close();
	}

	private static Program program_def() {
		Program prg = new Program();

		for(int i=1; i < 8; i++)
			prg.oscContinuous(i, 1);

		for(int i=1; i < 8; i++) {
			prg.oscContinuous(1, 4);
			prg.upContinuous(1);
		}

		prg.downContinuous(4);
		prg.oscContinuous(-3, 4);
		prg.downContinuous(3);

		return prg;
	}
}
