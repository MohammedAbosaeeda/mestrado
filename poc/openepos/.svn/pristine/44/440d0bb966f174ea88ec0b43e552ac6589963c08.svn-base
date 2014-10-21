package josek;
import java.util.*;
import java.io.*;

public class CodeGenerator {
	private HighlevelConfiguration conf;
	private CommandLineOptions cmdLine;
	private BufferedWriter activeFile = null;
	private int taskNumber = 1;
	private Vector codeHooks;

	CodeGenerator(HighlevelConfiguration conf, CommandLineOptions cmdLine) {
		this.conf = conf;
		this.cmdLine = cmdLine;
		codeHooks = new Vector();
		codeHooks.add((new CodeHook_osh_symbols().init("osh_symbols", conf, cmdLine)));
		codeHooks.add((new CodeHook_osh_prototypes().init("osh_prototypes", conf, cmdLine)));
		codeHooks.add((new CodeHook_osc_main_signalhandler().init("osc_main_signalhandler", conf, cmdLine)));
		codeHooks.add((new CodeHook_osc_main_preschedule().init("osc_main_preschedule", conf, cmdLine)));
		codeHooks.add((new CodeHook_osc_schedule_pretaskhook().init("osc_schedule_pretaskhook", conf, cmdLine)));
		codeHooks.add((new CodeHook_osc_schedule_posttaskhook().init("osc_schedule_posttaskhook", conf, cmdLine)));
		codeHooks.add((new CodeHook_osc_tasks().init("osc_tasks", conf, cmdLine)));
		codeHooks.add((new CodeHook_osc_counters().init("osc_counters", conf, cmdLine)));
		codeHooks.add((new CodeHook_osc_settimer().init("osc_settimer", conf, cmdLine)));
		codeHooks.add((new CodeHook_osc_timerhandler().init("osc_timerhandler", conf, cmdLine)));
		codeHooks.add((new CodeHook_osc_stackprotector().init("osc_stackprotector", conf, cmdLine)));
		codeHooks.add((new CodeHook_osc_dumpstack().init("osc_dumpstack", conf, cmdLine)));
		codeHooks.add((new CodeHook_osc_ioisr().init("osc_ioisr", conf, cmdLine)));
		codeHooks.add((new CodeHook_osc_ArrayOptimizerCode().init("osc_ArrayOptimizerCode", conf, cmdLine)));
		codeHooks.add((new CodeHook_osc_ArrayOptimizerDef().init("osc_ArrayOptimizerDef", conf, cmdLine)));
		codeHooks.add((new CodeHook_osh_ArrayOptimizerDef().init("osh_ArrayOptimizerDef", conf, cmdLine)));
	}

	protected void writeLine(String line) throws IOException {
		activeFile.write(line);
		activeFile.write('\n');
	}
	
	protected void writeSingStr() throws IOException {
		activeFile.write(new String(SSingleton.get()));
		activeFile.write('\n');
	}

	private void includeFile(String file, boolean archDependent, boolean parse) throws IOException, FileNotFoundException {
		String independentFile;

		independentFile = cmdLine.getRuleDirectory() + "independent/" + file;
		if (cmdLine.isDefined("codecomments")) writeLine("/**** Code inserted by include " + file + ": ****/");
		if (archDependent) {
			String arch = cmdLine.getArchitecture();
			if (arch == null) {
				throw new RuntimeException("Architecture undefined in OIL file, but inclusion of architecture dependent file '" + file + "' requested by RULES.");
			}
			file = cmdLine.getRuleDirectory() + arch + "/" + file;
		} else {
			file = independentFile;
		}
		
		BufferedReader includeFile;
		if (file == independentFile) {
			includeFile = new BufferedReader(new FileReader(file));
		} else {
			try {
				includeFile = new BufferedReader(new FileReader(file));
			} catch (Exception e) {
				includeFile = new BufferedReader(new FileReader(independentFile));
			}
		}

		boolean currentlyParsing = true;
		String line;
		do {
			line = includeFile.readLine();
			if (line != null) {
				if (!parse) {
					writeLine(line);
				} else {
					if ((!currentlyParsing) && (line.length() >= 3) && (line.substring(0, 3).equals("//?"))) {
						currentlyParsing = true;
					} else {
						if ((line.length() >= 3) && (line.substring(0, 3).equals("//+"))) {
							activateHook(line.substring(4), file);
						} else if ((line.length() >= 5) && (line.substring(0, 3).equals("//?"))) {
							currentlyParsing = cmdLine.isDefined(line.substring(4));
						} else if (!((line.length() >= 3) && (line.substring(0, 3).equals("//?")))) {
							if (currentlyParsing) writeLine(line);
						}
					}
				}
			}
		} while (line != null);

		includeFile.close();
		if (cmdLine.isDefined("codecomments")) writeLine("/**** Code insertion done. ****/");
	}

	private void activateHook(String hook, String file) {
		for (int i = 0; i < codeHooks.size(); i++) {
			CodeHook c = (CodeHook)codeHooks.get(i);
			if (((CodeHook)codeHooks.get(i)).fire(hook)) return;
		}
		throw new RuntimeException("Unknown hook " + hook + " specified, included from " + file + ".");
	}

	public void generate() throws IOException, FileNotFoundException {
		BufferedReader rulesFile = new BufferedReader(new FileReader(cmdLine.getRuleDirectory() + "RULES"));
		String line;
		boolean currentlyParsing = true;
		int lineNr = 0;
		do {
			lineNr++;
			try {
				line = rulesFile.readLine();
			} catch (IOException e) {
				throw new RuntimeException("Error reading from rules file: " + e);
			}
			if (line != null) {
				if (line.length() == 0) continue;
				if (line.charAt(0) == '#') continue;

				StringTokenizer tok = new StringTokenizer(line, " \t");
				String tokens[] = new String[2];
				for (int i = 0; i < tokens.length; i++) {
					if (tok.hasMoreTokens()) tokens[i] = tok.nextToken();
						else tokens[i] = null;
				}
				char lineType = tokens[0].charAt(0);

				if ((lineType != '?') && (!currentlyParsing)) continue;

				switch (lineType) {
					case ':': {
						if (activeFile != null) activeFile.close();
						activeFile = new BufferedWriter(new FileWriter(cmdLine.getOutputDirectory() + tokens[1]));
						for (int i = 0; i < codeHooks.size(); i++) ((CodeHook)codeHooks.get(i)).changedFile(activeFile);
						break;
					}
					case '~': {
						boolean parseForInternalHooks;
						parseForInternalHooks = (tokens[0].length() >= 2) && (tokens[0].charAt(1) == '+');
						includeFile(tokens[1], false, parseForInternalHooks);
						break;
					}
					case '!': {
						boolean parseForInternalHooks;
						parseForInternalHooks = (tokens[0].length() >= 2) && (tokens[0].charAt(1) == '+');
						includeFile(tokens[1], true, parseForInternalHooks);
						break;
					}
					case '%': {
						activateHook(tokens[1], "RULES");
						break;
					}
					case '?': {
						if (tokens[1] == null) {
							currentlyParsing = true;
						} else {
							currentlyParsing = cmdLine.isDefined(tokens[1]);
						}
						break;
					}
					default: {
						throw new RuntimeException("Unknown token '" + lineType + "' in line " + lineNr + " of rules file.");
					}
				}
			}
		} while (line != null);
		try {
			if (activeFile != null) activeFile.close();
		} catch (IOException e) {
			throw new RuntimeException("IO-Error closing last file specified in rules: " + e);
		}
	}
}

