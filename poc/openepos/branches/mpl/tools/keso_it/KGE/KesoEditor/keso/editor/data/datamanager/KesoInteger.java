package keso.editor.data.datamanager;

public class KesoInteger {
	
	int value = 0;
	
	public KesoInteger() {
	}
	
	public KesoInteger(int value) {
		this.value = value;
	}
	
	public void increase() {
		this.value = this.value + 1; 
	}

	public void decrease() {
		this.value = this.value - 1;
	}
	
	public int intValue() {
		return this.value;
	}
	
}
