package test;

public final class Hooks {
	/*private static final String[] errors = { "E_OK", "E_OS_ACCESS",
		"E_OS_CALLEVEL", "E_OS_ID", "E_OS_LIMIT", "E_OS_NOFUNC",
		"E_OS_RESOURCE", "E_OS_STATE", "E_OS_VALUE" };
	*/
	
	public static void Startup() {
		DebugOut.println("Startup Hook called\n");
	}

	public static void Pretask() {
		DebugOut.println("Starting new Task\n");
	}

	public static void Posttask() {
		DebugOut.println("Current Task finished\n");
	}

	public static void Error(int status) {
		DebugOut.println("Error #" + status + "\n");
	}
}
