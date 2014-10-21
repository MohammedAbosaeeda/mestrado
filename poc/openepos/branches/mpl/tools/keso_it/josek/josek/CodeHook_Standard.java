package josek;
import java.io.*;

abstract class CodeHook_Standard implements CodeHook {
	private String condition;
	private BufferedWriter file;
	protected HighlevelConfiguration conf;
	protected CommandLineOptions cmdLine;
	
	protected void writeLine(String line) {
		try {
			file.write(line);
			file.write('\n');
		} catch (IOException e) {
			throw new RuntimeException("Error writing line: " + e);
		}
	}
	
	protected void writeSingStr() {
		try {
			file.write(new String(SSingleton.get()));
			file.write('\n');
		} catch (IOException e) {
			throw new RuntimeException("Error writing line: " + e);
		}
	}
	
	public CodeHook init(String condition, HighlevelConfiguration conf, CommandLineOptions cmdLine) {
		this.condition = condition;
		this.conf = conf;
		this.cmdLine = cmdLine;
		return this;
	}

	public void changedFile(BufferedWriter file) {
		this.file = file;
	}

	abstract public void fireImmediately();
	
	public boolean fire(String condition) {
		if (condition.equals(this.condition)) {
			if (cmdLine.isDefined("codecomments")) writeLine("/**** Code included from hook " + condition + " ****/");
			fireImmediately();
			if (cmdLine.isDefined("codecomments")) writeLine("/**** Done with code included from hook " + condition + " ****/");
			return true;
		}
		return false;
	}
}

