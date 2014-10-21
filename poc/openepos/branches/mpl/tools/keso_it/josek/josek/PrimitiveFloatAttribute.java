package josek;

public class PrimitiveFloatAttribute extends PrimitiveAttribute {
	double value;

	public PrimitiveFloatAttribute(Attribute parent, String lValue, double value) {
		super(parent, lValue);
		this.value = value;
	}

	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append("Float '");
		s.append(lValue);
		s.append("' = ");
		s.append(value);
		return s.toString();
	}
}
