package josek;

public class PrimitiveBoolAttribute extends PrimitiveAttribute {
	boolean value;

	public PrimitiveBoolAttribute(Attribute parent, String lValue, boolean value) {
		super(parent, lValue);
		this.value = value;
	}

	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append("Boolean '");
		s.append(lValue);
		s.append("' = ");
		if (value) s.append("true");
			else s.append("false");
		return s.toString();
	}
}
