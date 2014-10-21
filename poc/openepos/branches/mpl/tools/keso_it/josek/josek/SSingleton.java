package josek;

class SSingleton {
	private static StringBuffer s;
	private static int indention;

	public static void clear() {
		s.delete(0, s.length());
		if (indention > 0) {
			for (int i = 0; i < indention; i++) add("\t");
		}
	}

	public static void incIndent() {
		indention++;
	}

	public static void decIndent() {
		indention--;
	}

	public static void setIndent(int i) {
		indention = i;
	}

	public static void add(String n) {
		s.append(n);
	}
	
	public static void add(int n) {
		s.append(n);
	}

	public static StringBuffer get() {
		return s;
	}

	public static void newLine() {
		add("\n");
		if (indention > 0) {
			for (int i = 0; i < indention; i++) add("\t");
		}
	}

	public static void addNL(String n) {
		add(n);
		newLine();
	}
	
	public static void init() {
		s = new StringBuffer();
		indention = 0;
	}
}
