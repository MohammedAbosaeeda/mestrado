package josek;
import java.util.Vector;

public class Attribute {
	String lValue;
	Attribute parent;
	Vector children;

	public Attribute(Attribute parent, String lValue) {
		this.parent = parent;
		this.lValue = lValue;
		this.children = new Vector();
		if (parent != null) parent.addChild(this);
	}

	public void addChild(Attribute child) {
		children.addElement(child);
	}

	private void spacing(int level) {
		for (int i = 0; i < (level + 1); i++) System.out.print("   ");
	}

	public void dumpAttribute(int level) {
		for (int i = 0; i < children.size(); i++) {
			Attribute currentAttribute = (Attribute)children.get(i);
			spacing(level);
			System.out.println(currentAttribute);
			currentAttribute.dumpAttribute(level + 1);
		}
	}

	public void dumpAttribute() {
		System.out.println(this);
		dumpAttribute(0);
	}
}

