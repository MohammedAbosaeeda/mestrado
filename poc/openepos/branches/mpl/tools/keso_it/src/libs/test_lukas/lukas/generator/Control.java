package lukas.generator;

import lukas.common.Timing;
import lukas.control.Command;

public class Control {
	boolean checkAndTranslate(Program prog, Config cfg) {
		ProgramElement actPE = prog.getFirst();
		int pos =0;
		prog.errorPos=0;

		while(actPE != null) {
			boolean result=false;

			System.err.println(actPE.toString());

			switch(actPE.type) {
				case ProgramElement.UP_CONTINUOUS:
					result=upContinuous(actPE.n, cfg);
					break;

				case ProgramElement.DOWN_CONTINUOUS:
					result=downContinuous(actPE.n, cfg);
					break;

				case ProgramElement.OSC_CONTINUOUS:
					result=oscContinuous(actPE.n, actPE.rounds, cfg);
					break;

				case ProgramElement.UP_STEPWISE:
					result=upStepwise(actPE.n, cfg);
					break;

				case ProgramElement.DOWN_STEPWISE:
					result=downStepwise(actPE.n, cfg);
					break;

				case ProgramElement.OSC_STEPWISE:
					result=oscStepwise(actPE.n, actPE.rounds, cfg);
					break;

				case ProgramElement.HOLD:
					result=hold(actPE.n, cfg);
					break;
				case ProgramElement.START:
					result=true;
			}

			if(result == false) {
				prog.errorPos = pos;
				return false;
			}
			pos++;
			actPE = actPE.getNext();
		}
		return true;
	}

	boolean upContinuous(int n, Config cfg) {
		if( (cfg.currentCoil+n)>=Config.NUM_COILS || n<=0 )
			return false;
		cfg.currentCoil += n;

		switch(cfg.getLast().cmd.type) {
			case Command.MOVE_UP:
				cfg.getLast().cmd.incN(n);
				return true;
			case Command.MOVE_HOLD:
				cfg.addrelease(Timing.RELEASE_TIME);
				break;
			case Command.MOVE_DOWN:
				cfg.addrelease(Timing.DOWN_RELEASE_TIME);
		}

		cfg.addup(n);
		return true;
	}

	boolean upStepwise(int n, Config cfg) {
		if( (cfg.currentCoil+n)>=Config.NUM_COILS || n<=0 )
			return false;
		
		for(int i=0; i<n; ++i) {
			upContinuous(1, cfg);
			hold(Timing.HOLD_DURATION, cfg);
		}
		return true;
	}

	boolean downContinuous(int n, Config cfg) {
		if( (cfg.currentCoil-n)<0 || n<=0 )
			return false;

		cfg.currentCoil -= n;

		switch(cfg.getLast().cmd.type) {
			case Command.MOVE_DOWN:
				cfg.getLast().cmd.incN(n);
				return true;
			case Command.MOVE_UP:
				cfg.addhold(Timing.UP_BRAKE_TIME);
		}
		cfg.adddown(n);
		return true;
	}

	boolean downStepwise(int n, Config cfg) {
		if( (cfg.currentCoil-n)<0 || n<=0 )
			return false;

		for(int i=0; i<n; ++i) {
			downContinuous(1, cfg);
			hold(Timing.HOLD_DURATION, cfg);
		}

		return true;
	}

	boolean hold(int n, Config cfg) {
		if(n > Timing.MAX_HOLD_TIME ||  n<=0)
			return false;

		if(cfg.getLast().cmd.type == Command.MOVE_HOLD) {
			if((cfg.getLast().cmd.n + n) > Timing.MAX_HOLD_TIME)
				return false;
			cfg.getLast().cmd.incN(n);
			return true;
		}

		cfg.addhold(n);
		return true;
	}

	boolean oscContinuous(int n, int rounds, Config cfg) {
		if((n == 0) || (rounds <= 0) || ((cfg.currentCoil+n)<0)
				|| ((cfg.currentCoil+n)>=Config.NUM_COILS))
			return false;

		while(rounds --> 0) {
			if (n>0) {
				upContinuous(n, cfg);
				downContinuous(n, cfg);
			} else {
				downContinuous(-1*n, cfg);
				upContinuous(-1*n, cfg);
			}
		}
		return true;
	}

	boolean oscStepwise(int n, int rounds, Config cfg) {
		if((n == 0) || (rounds <= 0) || ((cfg.currentCoil+n)<0)
				|| ((cfg.currentCoil+n)>=Config.NUM_COILS))
			return false;

		while(rounds --> 0) {
			if (n>0) {
				upStepwise(n, cfg);
				downStepwise(n, cfg);
			} else {
				downStepwise(-1 * n, cfg);
				upStepwise(-1 * n, cfg);
			}
		}
		return true;
	}

	Config compile(Program prg) {
		Config cfg = new Config();

		if(checkAndTranslate(prg, cfg) == false) {
			System.err.println("checkandTranslate failed Pos " + prg.errorPos);
			return null;
		}

		System.err.println("SHUTDOWN");
		if(cfg.currentCoil > 0 &&
				downContinuous(cfg.currentCoil, cfg) == false) {
			System.err.println("Final settling failed");
			return null;
		}

		if((cfg.getLast().cmd.type != Command.MOVE_HOLD) ||
				(cfg.getLast().cmd.n < 300)) {
			if(hold(300, cfg) == false)
				return null;
		}

		cfg.addend();
		return cfg;
	}
}
