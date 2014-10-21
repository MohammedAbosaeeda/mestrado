package test;

/**
 * Small class for buffered debug output. 
 */
final public class DebugOut {

	private final static StringBuffer buf = new StringBuffer();

	public static void print(double value) {
		buf.append(Double.toString(value));
	}

	public static void print(float value) {
		buf.append(Float.toString(value));
	}

	public static void print(long value) {
		buf.append(Long.toString(value));
	}

	public static void print(int value) {
		buf.append(Integer.toString(value));
	}

	public static void print(char value) {
		buf.append(value);
	}

	public static void print(String msg) {
		buf.append(msg);
	}

	public static void print(StringBuffer msg) {
		buf.append(msg);
	}

	public static void print(char[] msg) {
		buf.append(msg);
	}

	public static void print(Object msg) {
		buf.append(msg.toString());
	}

	public static void println(double value) {
		println(Double.toString(value));
	}

	public static void println(float value) {
		println(Float.toString(value));
	}

	public static void println(long value) {
		println(Long.toString(value));
	}

	public static void println(int value) {
		println(Integer.toString(value));
	}

	public static void println(char value) {
		buf.append(value);
		println();
	}

	public static void println(String msg) {
		buf.append(msg);
		println();
	}

	public static void println(Object msg) {
		buf.append(msg.toString());
		println();
	}

	public static void println(StringBuffer msg) {
		buf.append(msg);
		println();
	}

	public static void println(char[] msg) {
		buf.append(msg);
		println();
	}

	public static void println() {
		StringBuffer b = buf;
		b.append('\n');
		raw_print(b);
		b.setLength(0);
	}

	public static void flush() {
		raw_print(buf);
		buf.setLength(0);
	}

	private static void raw_print(StringBuffer msg) {
		/* we do nothing here! */
	}
}
