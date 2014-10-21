package keso.editor.data.datamanager;

/**
 * @author  Wilhelm Haas
 */
public class UnaryKesoDataRestriction implements IKesoDataRestriction {
	
	String identifier;
	int min = 0;
	int max = IKesoDataRestriction.INFINIT;
	
	public UnaryKesoDataRestriction(String identifier) {
		this.setIdentifier(identifier);
	}
	
	public UnaryKesoDataRestriction(String identifier, int min) {
		this(identifier);
		this.setMin(min);
	}
	
	public UnaryKesoDataRestriction(String identifier, int min, int max) {
		this(identifier, min);
		this.setMax(max);
	}
	
	/**
	 * @param max  the max to set
	 * @uml.property  name="max"
	 */
	public void setMax(int max) {
		this.max = max;
	}
	
	/**
	 * @param min  the min to set
	 * @uml.property  name="min"
	 */
	public void setMin(int min) {
		this.min = min;
	}
	
	/**
	 * @param identifier  the identifier to set
	 * @uml.property  name="identifier"
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	/**
	 * @return  the max
	 * @uml.property  name="max"
	 */
	public int getMax() {
		return this.max;
	}
	
	/**
	 * @return  the min
	 * @uml.property  name="min"
	 */
	public int getMin() {
		return this.min;
	}
	
	/**
	 * @return  the identifier
	 * @uml.property  name="identifier"
	 */
	public String getIdentifier() {
		return this.identifier;
	}

	public boolean accepts(KesoDataCounter counter, int flag) {
		int count = counter.getCounter(this.getIdentifier());
		if (flag == EVALUATE || flag == ADD_CHILD) {
		if (this.getMax() != IKesoDataRestriction.INFINIT && count > this.getMax()) {
			return false;
		}
		}
		if (flag == EVALUATE || flag == REMOVE_CHILD) {
			if (count < this.getMin()) {
				return false;
			}
		}
		
		return true;
	}

}
