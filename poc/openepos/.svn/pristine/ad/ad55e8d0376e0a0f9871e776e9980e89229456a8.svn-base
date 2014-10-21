package josek;
import java.util.Vector;
import java.util.HashSet;

public class CommandLineOptions {
	boolean acceptUnknownAttributes;
	Vector includePaths;
	String commandFile;
	boolean generateResourceStatistics;
	boolean testVerify;
	String ruleDirectory;
	String outputDirectory;
	String architecture;
    String backend;
	HashSet defines;
	HashSet validSymbols;
	
	public String getCommandFile() {
		return commandFile;
	}

	public String getRuleDirectory() {
		return ruleDirectory;
	}

	public String getOutputDirectory() {
		return outputDirectory;
	}

	public boolean getGenerateResourceStatistics() {
		return generateResourceStatistics;
	}

	public boolean isDefined(String symbol) {
		if (!validSymbols.contains(symbol)) throw new RuntimeException("Query for undocumented symbol '" + symbol + "'");
		return defines.contains(symbol);
	}

	public void defineSymbol(String symbol) {
		if (!validSymbols.contains(symbol)) throw new RuntimeException("Define of undocumented symbol '" + symbol + "'");
		defines.add(symbol);
	}

	public String getArchitecture() {
		return architecture;
	}

    public String getBackend() {
        return backend;
    }

	private int parseSingleArgument(String[] args, int index) {
		if (args[index].equals("-a")) {
			acceptUnknownAttributes = true;
			return 0;
		} else if (args[index].equals("-i")) {
			includePaths.addElement(args[index + 1]);
			return 1;
		} else if (args[index].equals("-f")) {
			commandFile = args[index + 1];
			return 1;
		} else if (args[index].equals("-r")) {
			generateResourceStatistics = true;
			return 0;
		} else if (args[index].equals("-v")) {
			System.err.println("Version: 0.00");
			return 0;
		} else if (args[index].equals("-t")) {
			testVerify = true;
			return 0;
		} else if (args[index].equals("-c")) {
			ruleDirectory = args[index + 1];
			return 1;
		} else if (args[index].equals("-o")) {
			outputDirectory = args[index + 1];
			return 1;
		} else if (args[index].equals("-D")) {
			defineSymbol(args[index + 1]);
			return 1;
		} else if (args[index].equals("-h")) {
			architecture = args[index + 1];
			return 1;
        } else if (args[index].equals("-b")) {
            backend = args[index + 1];
            ruleDirectory = "backends/" + backend + "/";
            return 1;
		}
		displayHelp();
		throw new RuntimeException("Unparsable command line option (#" + (index + 1) + "): " + args[index]);
	}

	public CommandLineOptions(String[] args) {
		acceptUnknownAttributes = false;
		includePaths = new Vector();
		commandFile = null;
		generateResourceStatistics = false;
		testVerify = false;
		outputDirectory = "generated/";
		ruleDirectory = "backends/josek/";
        backend = "JOSEK";
		defines = new HashSet();
		validSymbols = new HashSet();
		validSymbols.add("debug");
		validSymbols.add("assertions");
		validSymbols.add("codecomments");
		validSymbols.add("signals");
		validSymbols.add("timer");
		validSymbols.add("stackprotector");
		validSymbols.add("stackprotector_simple");
		validSymbols.add("saveram");
		validSymbols.add("have_alarms");
		validSymbols.add("have_tasks");
		validSymbols.add("PC_framebuffer");
		architecture = "x86_32";

		if (args.length == 0) {
			displayHelp();
			throw new RuntimeException("No command line parameters given.");
		}

		for (int i = 0; i < args.length; i++) {
			int j = parseSingleArgument(args, i);
			i += j;
		}
	}

	void displayHelp() {
		System.err.println("usage: josek [-a] [-i Path] [-f File] [-r] [-v] [-t]");
		System.err.println("    -a          Accept unknown attributes");
		System.err.println("    -i Path     Include 'Path' in search for includes");
		System.err.println("    -f File     Use 'File' as command file");
		System.err.println("    -r          Generate resource statistics");
		System.err.println("    -v          Display version number");
		System.err.println("    -t          Enable test/verification mode");
		System.err.println("    -h Arch     Define architecture (x86_32/x86_64)");
		System.err.println("    -c Path     Specify rule directory");
		System.err.println("    -o Path     Specify output directory");
		System.err.println("    -D Symbol   Define symbol 'Symbol'");
        System.err.println("    -b Backend  Define backend (JOSEK, EPOS-OSEK4C, EPOS-OSEK4C++)");
		System.err.println();
		System.err.println("Known symbols (and the results on the generated code):");
		System.err.println("    signals        Signal handler functions");
		System.err.println("    codecomments   Comments from where the included code originates");
		System.err.println("    debug          Debugging functions (dump_xxx)");
		System.err.println("    assertions     Include assertions in code");
		System.err.println("    timer          Include code for timers");
		System.err.println("    stackprotector Stack protector code for debugging");
		System.err.println("    stackprotector_simple Stack protector code for debugging");
		System.err.println("    saveram        Preserve RAM by generating static lookup tables");
	}
}
