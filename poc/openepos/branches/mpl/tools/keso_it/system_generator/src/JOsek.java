package josek;
import josek.parser.*;
import java.io.*;

public class JOsek {
	public static void main(String[] args) {
		SSingleton.init();
		
		CommandLineOptions cmdLine = new CommandLineOptions(args);
		if (cmdLine.getCommandFile() == null) {
			throw new RuntimeException("No OIL file given.");
		}
		Configuration c = ConfigReader.parseDefinition(cmdLine.getCommandFile());
//		c.dumpConfiguration();
		HighlevelConfiguration h = new HighlevelConfiguration(c, cmdLine);
		if (cmdLine.getGenerateResourceStatistics()) h.dumpConfiguration();
		CodeGenerator g = new CodeGenerator(h, cmdLine);
		try {
			g.generate();
		} catch (IOException e) {
			throw new RuntimeException("Error generating code: " + e);
		}
	}
}

