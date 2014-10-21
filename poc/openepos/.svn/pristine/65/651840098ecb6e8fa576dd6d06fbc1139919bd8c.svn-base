package josek;
import java.util.Set;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Map.Entry;

public class ArrayOptimizer {
	private String name;
	private String xType;
	private String yType;
	private TreeMap values;
	private boolean inRAM;
	private boolean finalized;
		
	public ArrayOptimizer(String name, String xType, String yType, boolean inRAM) {
		values = new TreeMap();
		this.name = name;
		this.xType = xType;
		this.yType = yType;
		this.inRAM = inRAM;
		finalized = false;
	}

	public void addValue(int a, int b) {
		if (finalized) throw new RuntimeException("Cannot add values to finalized array.");
		values.put(new Integer(a), new Integer(b));
	}

	public void finalize() {
		finalized = true;
	}

	public void generateDefine() {
		SSingleton.clear();
		SSingleton.add("#define ");
		SSingleton.add(name);
		SSingleton.add("(x)		(");
		SSingleton.add(name);
		SSingleton.add("Values");
		if (inRAM) {
			SSingleton.add("[x]");
		} else {
			SSingleton.add("(x)");
		}
		SSingleton.add(")");
	}

	public boolean generateArray() {
		if (!inRAM) return false;
		SSingleton.clear();
		SSingleton.add("static ");
		SSingleton.add(yType);
		SSingleton.add(" ");
		SSingleton.add(name);
		SSingleton.add("Values[] = {");
		Set s = values.entrySet();
		int j = 0;
		for (Iterator i = s.iterator(); i.hasNext(); ) {
			Entry e = (Entry)i.next();
			int key = ((Integer)(e.getKey())).intValue();
			int val = ((Integer)(e.getValue())).intValue();
			if (j++ != key) throw new RuntimeException("TODO: Using arbitary x/y-mappings using array currently not implemented.");
			SSingleton.add(val);
			if (i.hasNext()) SSingleton.add(", ");
		}
		SSingleton.add("};");
		return true;
	}
	
	public boolean generateFunction() {
		if (inRAM) return false;
		SSingleton.clear();
		SSingleton.add(yType);
		SSingleton.add(" ");
		SSingleton.add(name);
		SSingleton.add("Values(");
		SSingleton.add(xType);
		SSingleton.add(" x) {");
		SSingleton.incIndent();
		SSingleton.newLine();

		SSingleton.add("switch (x) {");
		SSingleton.incIndent();
		SSingleton.newLine();
	
		Set s = values.entrySet();
		for (Iterator i = s.iterator(); i.hasNext(); ) {
			Entry e = (Entry)i.next();
			int key = ((Integer)(e.getKey())).intValue();
			int val = ((Integer)(e.getValue())).intValue();
			SSingleton.add("case ");
			SSingleton.add(key);
			SSingleton.add(": return ");
			SSingleton.add(val);
			SSingleton.add(";");
			if (i.hasNext()) SSingleton.newLine();
		}
		
		SSingleton.decIndent();
		SSingleton.newLine();
		SSingleton.add("}");
		SSingleton.newLine();
		
		SSingleton.add("abort();	/* Array index out of bounds */");
		SSingleton.newLine();
		
		SSingleton.add("return 0;");
		SSingleton.decIndent();
		SSingleton.newLine();
		
		SSingleton.add("}");
		SSingleton.newLine();
		return true;
	}
}

