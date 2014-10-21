package josek;

public class Configuration {
	Attribute root;

	public Configuration() {
		root = new PrimitiveRootAttribute();
	}

	public Attribute getRoot() {
		return root;
	}

	public void dumpConfiguration() {
		root.dumpAttribute();
	}
}
