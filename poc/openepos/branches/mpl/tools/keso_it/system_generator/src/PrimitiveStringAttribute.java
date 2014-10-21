package josek;

public class PrimitiveStringAttribute extends PrimitiveAttribute {
	String value;

	public PrimitiveStringAttribute(Attribute parent, String lValue, String value) {
		super(parent, lValue);
		this.value = value;
	}

	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append("String '");
		s.append(lValue);
		s.append("' = '");
		s.append(value);
		s.append("'");
		return s.toString();
	}
}
