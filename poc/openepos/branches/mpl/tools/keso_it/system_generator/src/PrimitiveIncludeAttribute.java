package josek;

public class PrimitiveIncludeAttribute extends PrimitiveAttribute {
	boolean globalSearch;

	public PrimitiveIncludeAttribute(Attribute parent, String lValue, boolean globalSearch) {
		super(parent, lValue);
		this.globalSearch = globalSearch;
	}

	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append("Include ");
		if (globalSearch) s.append("<");
			else s.append("\"");
		s.append(lValue);
		if (globalSearch) s.append(">");
			else s.append("\"");
		return s.toString();
	}
}
