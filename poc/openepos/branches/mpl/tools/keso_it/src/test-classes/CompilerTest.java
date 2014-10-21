
public class CompilerTest {

	public CompilerTest() {
	}

	private int argToInt(String arg) {
		if (arg.equals("world")) return 0;
		return -1;
	}

	public void exceptionAndFinally() {
		int a = 0;
		try {
			System.out.println("test");
		} catch (RuntimeException ex) {
			System.err.println(ex.toString());
		} finally {
			System.err.println("fin");
			if (a==1) {
				a++;
			} else {
				a--;
			}
		}	
	}

	public static void main(String argv[]) {
		if (argv.length==0) {
			System.out.println("tests: ");
			System.out.println("    helloworld ");
			System.exit(-1);
		}

		CompilerTest testobj = new CompilerTest();
		for (int i=0; i<argv.length; i++) {
			String arg = argv[i];

			int a = testobj.argToInt(arg);

			switch (a) {
				case 0:
					System.out.println(" hello world!");	
				case 1:
					System.out.println(" hello wawi!");	
				default:
					System.out.println(" unknown test : "+arg);
			}
		}
	}
}

